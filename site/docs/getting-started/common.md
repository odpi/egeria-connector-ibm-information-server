<!-- SPDX-License-Identifier: CC-BY-4.0 -->
<!-- Copyright Contributors to the ODPi Egeria project. -->

# Common setup steps

Regardless of whether you are configuring the IBM Information Governance Catalog adapter or the
DataStage adapter (or both), there are a number of initial steps that are common.

## 1. Obtain connector

Start by downloading the IBM Information Server connectors:

=== "Latest release"
    [![Release](https://img.shields.io/maven-central/v/org.odpi.egeria/egeria-connector-ibm-information-server-package?label=release)](http://repository.sonatype.org/service/local/artifact/maven/redirect?r=central-proxy&g=org.odpi.egeria&a=egeria-connector-ibm-information-server-package&v=RELEASE&c=jar-with-dependencies)

=== "Latest snapshot"
    [![Development](https://img.shields.io/nexus/s/org.odpi.egeria/egeria-connector-ibm-information-server?label=development&server=https%3A%2F%2Foss.sonatype.org)](https://oss.sonatype.org/content/repositories/snapshots/org/odpi/egeria/egeria-connector-ibm-information-server-package/){ target=dl }

The connector is: `egeria-connector-ibm-information-server-package-{version}-jar-with-dependencies.jar`

## 2. Obtain server chassis

Download Egeria's server chassis:

=== "Latest release"
    [![Release](https://img.shields.io/maven-central/v/org.odpi.egeria/server-chassis-spring?label=release)](http://repository.sonatype.org/service/local/artifact/maven/redirect?r=central-proxy&g=org.odpi.egeria&a=server-chassis-spring&v=RELEASE)

=== "Latest snapshot"
    [![Development](https://img.shields.io/nexus/s/org.odpi.egeria/server-chassis-spring?label=development&server=https%3A%2F%2Foss.sonatype.org)](https://oss.sonatype.org/content/repositories/snapshots/org/odpi/egeria/server-chassis-spring/){ target=dl }

!!! tip "The connector version indicates the minimum required Egeria version"

The server chassis is: `server-chassis-spring-{version}.jar`

## 3. Configure security

To get an initial environment up-and-running just download the `truststore.p12`
file from: [https://github.com/odpi/egeria/raw/main/truststore.p12](https://github.com/odpi/egeria/raw/main/truststore.p12).

??? question "Transport-level security"
    The [truststore.p12](https://github.com/odpi/egeria/raw/main/truststore.p12)
    file provides a local truststore for Java. This allows the self-signed certificate embedded
    within the server chassis (by default) to be trusted.

    Without this trust, interactions with the server chassis (such as the REST calls that are made
    through Java to handle interaction between the chassis and the connector) will result in an
    `SSLHandshakeException`.

    While this `truststore.p12` file allows SSL-encrypted communication, the fact that
    it relies on a self-signed certificate means that there is no independent source of trust
    in the interactions (which would typically be achieved through an independent Certificate
    Authority).

    Additional details on TLS for Egeria can be found in [the Egeria documentation](https://egeria.ai/open-metadata-implementation/admin-services/docs/user/omag-server-platform-transport-level-security.html).

While the `truststore.p12` file sets up TLS for Egeria itself, there is also the
communication between the connector and the IBM Information Server environment to consider.
_One_ of the following options must be chosen when configuring the connector:

### Disable SSL for IBM Information Server

The simplest method to configure this layer of the transport is to disable SSL verification
itself, which you can do by exporting the environment variable `STRICT_SSL=false`
or passing the argument `-Dstrict.ssl=false` to the java command when running the
server chassis.

!!! danger "Insecure"
    Both of these effectively disable security by disabling SSL verification. While this is likely
    acceptable for some development or testing purposes, it is not recommended for production
    usage. For more robust (albeit also more cumbersome) approaches see the further details on
    other options below.

### Configure SSL for IBM Information Server

#### Configure Java keystore

With this approach you can tell Java to trust the certificate of one or more systems, even when
those systems only have self-signed certificates.

!!! warning "Somewhat secure"
    This is more secure than entirely disabling SSL
    verification, as it provides a sort of whitelist for certificates to trust; however, it does not
    rely on an independent party to verify that trust. Done without proper diligence, there is
    therefore still some risk in this approach.

Begin by obtaining the self-signed certificate of the IGC host (from any machine with a JVM: could
be the Egeria OMAG Server Platform so long as you're confident it is reaching the correct host):

```shell
keytool -printcert -sslserver my.igc.host.com:9446 -rfc > infosvr_self_signed.crt
```

!!! tip "Hostname must match"
    Note that you need to use the correct hostname and port number of the domain (services) tier of
    your environment. This means the hostname by which that server knows itself -- it should _not_
    be an IP address.

Next, import the certificate into the Java keystore used by the environment. For example, in an
Alpine openjdk docker image, this would be `/etc/ssl/certs/java/cacerts`:

```shell
keytool -trustcacerts -keystore /etc/ssl/certs/java/cacerts -storepass changeit -noprompt -alias my.igc.host.com -importcert -file infosvr_self_signed.crt
rm infosvr_self_signed.crt
```

??? question "Explanation of the command"
    - The keystore location should be specified, and it is best to use the one for the environment
      itself (if a user-specific keystore it will likely depend on the user under which the JVM
      itself is running whether it is picked up or not).
    - The default keystore password in Java is `changeit`; obviously replace this with the password
      for your actual keystore if it has been changed... (Both the `-storepass` and `-noprompt` can
      be skipped if you would rather be prompted for the password and confirmation of importing the
      certificate.)
    - The `-alias` should be specified and **must** match the hostname of the IGC machine itself
      (the services tier host): if different, the certificate won't match and won't be validated.

#### Use an independent certificate authority (CA)

This approach relies on a third party (a "certificate authority") to sign the certificates to be
used by your environments, and leverages inherent trust in these third parties in order to validate
such signed certificates automatically.

!!! success "Most secure"
    This approach is generally accepted as the most robust and secure.

    However, this option also relies on obtaining (typically at a cost) such certificate signatures, and
    updating the configuration of your system(s) to leverage these new signed certificates rather than
    the self-signed certificates they possess by default.

More information on doing this specifically for IBM Information Server can be found
in [IBM's Knowledge Center](https://www.ibm.com/support/knowledgecenter/en/SSZJPZ_11.7.0/com.ibm.swg.im.iis.found.admin.common.doc/topics/admin_mg_certs.html){ target=ibm }.

After configuring such a signed certificate, you should be able to connect to the IBM environment
without any further configuration of either the connector, OMAG Server Platform, or Java keystores.

## 4. Start the server chassis

Ensure the 3 files are in the same directory, and run:

```shell
java -Dloader.path=. -jar server-chassis-spring-*.jar
```

??? question "Explanation of the command"
    The `-Dloader.path=.` indicates that the server chassis should look for any
    connectors in the current directory. If you wanted to instead place the connector in a
    separate location (for example `/lib`), you could change the `.` to
    point to that other location (`-Dloader.path=/lib`).

??? success "Output for server chassis startup"
    ```text
    Project Egeria - Open Metadata and Governance
       ____   __  ___ ___    ______   _____                                 ____   _         _     ___
      / __ \ /  |/  //   |  / ____/  / ___/ ___   ____ _   __ ___   ____   / _  \ / / __    / /  / _ /__   ____ _  _
     / / / // /|_/ // /| | / / __    \__ \ / _ \ / __/| | / // _ \ / __/  / /_/ // //   |  / _\ / /_ /  | /  _// || |
    / /_/ // /  / // ___ |/ /_/ /   ___/ //  __// /   | |/ //  __// /    /  __ // // /  \ / /_ /  _// / // /  / / / /
    \____//_/  /_//_/  |_|\____/   /____/ \___//_/    |___/ \___//_/    /_/    /_/ \__/\//___//_/   \__//_/  /_/ /_/

    :: Powered by Spring Boot (v2.5.0) ::
    
    2021-07-14 11:20:03.855  INFO 1 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 9443 (https)
    2021-07-14 11:20:30.683  INFO 1 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 9443 (https) with context path ''
    
    Wed Jul 14 11:20:13 GMT 2021 No OMAG servers listed in startup configuration
    Wed Jul 14 11:20:30 GMT 2021 OMAG server platform ready for more configuration
    ```

!!! attention "Wait for these two final lines of output to appear before proceeding"
    ```text
    {timestamp} No OMAG servers listed in startup configuration
    {timestamp} OMAG server platform ready for more configuration
    ```

    These final two lines of output indicate that the server chassis has completed starting up
    and is now ready to be configured.

    Any attempt to configure the server chassis before these lines are output is likely to
    result in failures.

You are now ready to configure the connectors themselves.

--8<-- "snippets/abbr.md"
