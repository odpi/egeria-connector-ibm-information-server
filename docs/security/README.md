<!-- SPDX-License-Identifier: CC-BY-4.0 -->
<!-- Copyright Contributors to the ODPi Egeria project. -->

# Securing your environment

## Transport-level security (TLS)

There are a few options you can use to secure the communication between Egeria's proxy (this connector) and your
IGC environment. The following outline the options, from the simplest (and least secure) to the most secure (and
most complex).

### Disable certificate validation entirely

The simplest option is to entirely disable certificate validation. This will accept _all_ certificates when
communicating between systems over SSL, and is inherently insecure. As a result, this approach is not recommended
for anything but the simplest testing or experimentation scenarios; however, it is by far the simplest to use.

You can use this option by making use of the `STRICT_SSL` environment variable or `-Dstrict.ssl` argument to the
OMAG Server Platform startup. Either should work, and there is no need to do both:

```bash
$ export STRICT_SSL=false
$ java -Dloader.path=/lib -jar server-chassis-spring-VERSION.jar
```

or

```bash
$ java -Dstrict.ssl=false -Dloader.path=/lib -jar server-chassis-spring-VERSION.jar
```

### Configure your Java keystore to accept specific self-signed certificates

With this approach you can tell Java to trust the certificate of one or more systems, even when those systems only
have self-signed certificates. This is more secure than the option above, as it provides a sort of whitelist for
certificates to trust (rather than the implied blanket whitelist of the option above); however, it does not rely on
an independent party to verify that trust. Done without proper diligence, there is therefore still some risk in this
approach.

Begin by obtaining the self-signed certificate of the IGC host (from any machine with a JVM: could be the Egeria
OMAG Server Platform so long as you're confident it is reaching the correct host):

```bash
$ keytool -printcert -sslserver my.igc.host.com:9446 -rfc > infosvr_self_signed.crt
```

Note that you need to use the correct hostname and port number of the domain (services) tier of your environment.
("Correct hostname" means the hostname by which that server knows itself -- it should _not_ be an IP address.)

Next, import the certificate into the Java keystore used by the environment. For example, in an Alpine openjdk docker
image, this would be `/etc/ssl/certs/java/cacerts`:

```bash
$ keytool -trustcacerts -keystore /etc/ssl/certs/java/cacerts -storepass changeit -noprompt -alias my.igc.host.com -importcert -file infosvr_self_signed.crt
$ rm infosvr_self_signed.crt
```

Note that:
- The keystore location should be specified, and it is best to use the one for the environment itself (if a
    user-specific keystore it will likely depend on the user under which the JVM itself is running whether it is
    picked up or not).
- The default keystore password in Java is `changeit`; obviously replace this with the password for your actual
    keystore if it has been changed... (Both the `-storepass` and `-noprompt` can be skipped if you would rather be
    prompted for the password and confirmation of importing the certificate.)
- The `-alias` should be specified and **must** match the hostname of the IGC machine itself (the services tier host):
    if different, the certificate won't match and won't be validated.

### Use an independent certificate authority

This approach is generally accepted as the most robust and secure. It relies on a third party (a
"certificate authority") to sign the certificates to be used by your environments, and leverages
inherent trust in these third parties in order to validate such signed certificates automatically.

However, this option also relies on obtaining (typically at a cost) such certificate signatures, and
updating the configuration of your system(s) to leverage these new signed certificates rather than the
self-signed certificates they possess by default.

More information on doing this specifically for IGC (IBM Information Server in general) can be found
in [IBM's Knowledge Center](https://www.ibm.com/support/knowledgecenter/en/SSZJPZ_11.7.0/com.ibm.swg.im.iis.found.admin.common.doc/topics/admin_mg_certs.html).

After configuring such a signed certificate, you should be able to connect to the IGC environment
without any further configuration of either the connector, OMAG Server Platform, or Java keystores.

----
License: [CC BY 4.0](https://creativecommons.org/licenses/by/4.0/),
Copyright Contributors to the ODPi Egeria project.
