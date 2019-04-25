<!-- SPDX-License-Identifier: CC-BY-4.0 -->
<!-- Copyright Contributors to the ODPi Egeria project. -->
  
# Distribution

This module collects together the Egeria artifacts that are necessary to make
use of the adapter for connecting to IBM Information Governance Catalog ("IGC")
and packages them into a single `jar` file that can be distributed as an
Open Connector Framework (OCF) connector.

After building the project (`mvn clean install`) the connector is available as:

```text
distribution/target/egeria-connector-ibm-igc-package-VERSION-dist.jar
```

Simply copy this file to a location where it can be run alongside the OMAG Server
Platform from the Egeria core (in the example below, the file would be copied to
`/lib/egeria-connector-ibm-igc-package-VERSION-dist.jar`), and you can startup
the OMAG Server Platform with this connector ready-to-be-configured by running:

```bash
$ java -Dloader.path=/lib -jar server-chassis-spring-VERSION.jar
```

(This command will startup the OMAG Server Platform, including all libraries
in the `/lib` directory as part of the classpath of the OMAG Server Platform.)

----
License: [CC BY 4.0](https://creativecommons.org/licenses/by/4.0/),
Copyright Contributors to the ODPi Egeria project.