<!-- SPDX-License-Identifier: CC-BY-4.0 -->
<!-- Copyright Contributors to the ODPi Egeria project. -->

[![License](https://img.shields.io/github/license/odpi/egeria-connector-ibm-information-server)](LICENSE)
[![Build](https://github.com/odpi/egeria-connector-ibm-information-server/workflows/Build/badge.svg)](https://github.com/odpi/egeria-connector-ibm-information-server/actions/workflows/merge.yml?query=workflow%3ABuild)
[![CodeQL](https://github.com/odpi/egeria-connector-ibm-information-server/workflows/CodeQL/badge.svg)](https://github.com/odpi/egeria-connector-ibm-information-server/actions/workflows/codeql-analysis.yml)
[![Release](https://img.shields.io/maven-central/v/org.odpi.egeria/egeria-connector-ibm-information-server-package?label=release)](http://repository.sonatype.org/service/local/artifact/maven/redirect?r=central-proxy&g=org.odpi.egeria&a=egeria-connector-ibm-information-server-package&v=RELEASE&c=jar-with-dependencies)
[![Development](https://img.shields.io/nexus/s/org.odpi.egeria/egeria-connector-ibm-information-server-package?label=development&server=https%3A%2F%2Foss.sonatype.org)](https://oss.sonatype.org/content/repositories/snapshots/org/odpi/egeria/egeria-connector-ibm-information-server-package/)

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

## [Documentation](https://odpi.github.io/egeria-connector-ibm-information-server)

[https://odpi.github.io/egeria-connector-ibm-information-server](https://odpi.github.io/egeria-connector-ibm-information-server)

----
License: [CC BY 4.0](https://creativecommons.org/licenses/by/4.0/),
Copyright Contributors to the ODPi Egeria project.
