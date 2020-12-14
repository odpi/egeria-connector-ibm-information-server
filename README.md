<!-- SPDX-License-Identifier: CC-BY-4.0 -->
<!-- Copyright Contributors to the ODPi Egeria project. -->

[![GitHub](https://img.shields.io/github/license/odpi/egeria-connector-ibm-information-server)](LICENSE) [![Azure](https://dev.azure.com/odpi/egeria/_apis/build/status/odpi.egeria-connector-ibm-information-server)](https://dev.azure.com/odpi/Egeria/_build) [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=egeria-connector-ibm-information-server&metric=alert_status)](https://sonarcloud.io/dashboard?id=egeria-connector-ibm-information-server) [![Maven Central](https://img.shields.io/maven-central/v/org.odpi.egeria/egeria-connector-ibm-information-server)](https://mvnrepository.com/artifact/org.odpi.egeria/egeria-connector-ibm-information-server)

# IBM InfoSphere Information Server Connectors

[IBM InfoSphere Information Server](https://www.ibm.com/marketplace/infosphere-information-server) is a
commercially-available data integration, quality and governance suite from IBM. It is comprised of multiple modules,
and this repository contains Egeria connectors for some of those modules:

- [IBM InfoSphere Information Governance Catalog connector](igc-adapter) implements read-only connectivity to
    the metadata repository module within the suite.
- [IBM InfoSphere DataStage connector](datastage-adapter) implements a data engine proxy connector for the ETL
    module within the suite.

The project also provides client libraries to ease integration with IBM InfoSphere Information Server even without
using Egeria:

- [IBM IGC REST Client Library](igc-clientlibrary) abstracts integration with Information Governance Catalog's REST API through a Java client.
- [IBM IA REST Client Library](ia-clientlibrary) abstracts integration with Information Analyzer's REST API through a Java client.

## Getting started

### TL;DR

The quick version (same for all connectors):

1. Download the latest IBM Information Server connector from: https://odpi.jfrog.io/odpi/egeria-snapshot-local/org/odpi/egeria/egeria-connector-ibm-information-server-package/2.5-SNAPSHOT/egeria-connector-ibm-information-server-package-2.5-SNAPSHOT-jar-with-dependencies.jar
1. Download the latest Egeria core from: https://odpi.jfrog.io/odpi/egeria-snapshot-local/org/odpi/egeria/server-chassis-spring/2.5-SNAPSHOT/server-chassis-spring-2.5-SNAPSHOT.jar
1. Rename the downloaded Egeria core file to `egeria-server-chassis-spring.jar`.
1. Download the `truststore.p12` file from: https://github.com/odpi/egeria/blob/master/truststore.p12
1. Run the following command to start Egeria from the command-line, waiting for the final line of output indicating the
    server is running and ready for configuration:
    ```bash
    $ export STRICT_SSL=false
    $ java -Dloader.path=. -jar egeria-server-chassis-spring.jar
     ODPi Egeria
        ____   __  ___ ___    ______   _____                                 ____   _         _     ___
       / __ \ /  |/  //   |  / ____/  / ___/ ___   ____ _   __ ___   ____   / _  \ / / __    / /  / _ /__   ____ _  _
      / / / // /|_/ // /| | / / __    \__ \ / _ \ / __/| | / // _ \ / __/  / /_/ // //   |  / _\ / /_ /  | /  _// || |
     / /_/ // /  / // ___ |/ /_/ /   ___/ //  __// /   | |/ //  __// /    /  __ // // /  \ / /_ /  _// / // /  / / / /
     \____//_/  /_//_/  |_|\____/   /____/ \___//_/    |___/ \___//_/    /_/    /_/ \__/\//___//_/   \__//_/  /_/ /_/
    
     :: Powered by Spring Boot (v2.2.2.RELEASE) ::
    
    
    No OMAG servers listed in startup configuration
    Thu Jan 02 11:30:10 GMT 2020 OMAG server platform ready for more configuration
    ```
1. Follow the detailed instructions for configuring the connector(s) you want to use, either [IGC](igc-adapter) or
    [DataStage](datastage-adapter).

### Obtain the connectors

You can either download the latest released or snapshot version of the connector directly from ODPi, or build the
connector yourself. In both cases, once you have the jar file for the connector
(`egeria-connector-ibm-information-server-package-VERSION-jar-with-dependencies.jar`) this needs to be copied to a
location where it can be run alongside the OMAG Server Platform from Egeria core itself. For example, this could be
placing the file into the `/lib` directory as `/lib/egeria-connector-ibm-information-server-package-VERSION-jar-with-dependencies.jar`.

#### Download from ODPi

To download a pre-built version of the connector, use either of the following URLs (depending on whether you want an
officially-released version or the latest snapshot):

- Release: https://odpi.jfrog.io/odpi/egeria-release-local/org/odpi/egeria/egeria-connector-ibm-information-server-package/2.5/egeria-connector-ibm-information-server-package-2.5-jar-with-dependencies.jar
- Snapshot: https://odpi.jfrog.io/odpi/egeria-snapshot-local/org/odpi/egeria/egeria-connector-ibm-information-server-package/2.5-SNAPSHOT/egeria-connector-ibm-information-server-package-2.5-SNAPSHOT-jar-with-dependencies.jar

#### Building the connectors yourself

Alternatively, you can build the connector yourself. To do this, you'll need to first clone this repository and then
build through Maven using `mvn clean install`. After building, the connector is available as:

```text
distribution/target/egeria-connector-ibm-information-server-package-VERSION-jar-with-dependencies.jar
```

### Configure security

There are [multiple options to configure the security of your environment](docs/security/README.md) for this connector,
but this must be done prior to starting up the connector itself (step below).

If you simply want to test things out, and are not concerned about security, the simplest (but most insecure) option
is to set the environment variable `STRICT_SSL` to `false` using something like the following prior to starting
up the OMAG Server Platform:

```bash
export STRICT_SSL=false
```

Note that this will disable all certificate validation for SSL connections made between Egeria and your IGC
environment, so is inherently insecure.

Note that in any case, having a `truststore.p12` file available to the server chassis is required -- the simplest is to
ensure that Egeria's own (https://github.com/odpi/egeria/blob/master/truststore.p12) is placed in the directory in which
you are running the server chassis.

### Startup the OMAG Server Platform

You can startup the OMAG Server Platform with this connector ready-to-be-configured by running the following
(this example assumes you've placed the connector jar file in the `/lib` directory, if you are using a different
location simply change the `-Dloader.path=` to point to the location you have used):

```bash
$ java -Dloader.path=/lib -jar server-chassis-spring-VERSION.jar
```

(This command will startup the OMAG Server Platform, including all libraries
in the `/lib` directory as part of the classpath of the OMAG Server Platform.)

### Configure the connector(s) you want to use

See the detailed instructions for configuring the connector(s) you want to use:

- [IGC](igc-adapter)
- [DataStage](datastage-adapter)

----
License: [CC BY 4.0](https://creativecommons.org/licenses/by/4.0/),
Copyright Contributors to the ODPi Egeria project.
