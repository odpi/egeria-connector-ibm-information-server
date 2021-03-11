<!-- SPDX-License-Identifier: CC-BY-4.0 -->
<!-- Copyright Contributors to the ODPi Egeria project. -->

[![License](https://img.shields.io/github/license/odpi/egeria-connector-ibm-information-server)](LICENSE)
[![Build](https://github.com/odpi/egeria-connector-ibm-information-server/workflows/Maven%20Package/badge.svg)](https://github.com/odpi/egeria-connector-ibm-information-server/actions?query=workflow%3A%22Maven+Package%22)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=egeria-connector-ibm-information-server&metric=alert_status)](https://sonarcloud.io/dashboard?id=egeria-connector-ibm-information-server)
[![Snapshot](https://img.shields.io/maven-metadata/v?label=development&metadataUrl=https%3A%2F%2Ftoken%3A82d3aaedcfbb070176d9b30df29ea28768b90691%40maven.pkg.github.com%2Fodpi%2Fegeria-connector-ibm-information-server%2Forg%2Fodpi%2Fegeria%2Fegeria-connector-ibm-information-server%2Fmaven-metadata.xml)](https://github.com/odpi/egeria-connector-ibm-information-server/packages/617676)
[![Release](https://img.shields.io/maven-central/v/org.odpi.egeria/egeria-connector-ibm-information-server)](https://mvnrepository.com/artifact/org.odpi.egeria/egeria-connector-ibm-information-server)

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

## Quick links

- See our [Getting Started](https://odpi.github.io/egeria-connector-ibm-information-server/getting-started/) guide for
  step-by-step instructions on using these connectors to integrate IBM Information Server with Egeria.
- See the [CTS Results](cts/README.md) for details on its conformance and rough performance numbers.

## How it works

There are currently two connectors:

- A repository proxy connector that integrates IBM Information Governance Catalog with Egeria.
- A data engine proxy connector that integrates IBM DataStage lineage information with Egeria.

Both involve integrating into the Open Connector Framework (OCF) and implementing
certain methods that adhere to the interfaces required by each connector type. These then communicate with IBM Information
Server via IBM Information Server's own REST APIs to read information from an Information Server environment.

This requires an existing IBM Information Server environment to already be running elsewhere: the connectors simply
connect to this existing environment and "proxy" any requests from Egeria into a series of native REST API calls against
the environment.

![Overview](docs/overview.png)

> Overview of the connector implementations

Note that currently both connectors operate only in a read-only manner: no write operations (creates or updates) are
implemented against IBM Information Server. Furthermore, [only a subset of the open metadata types are currently
implemented](docs/mappings/README.md); however, the [connector is extensible](docs/extending/README.md).

----
License: [CC BY 4.0](https://creativecommons.org/licenses/by/4.0/),
Copyright Contributors to the ODPi Egeria project.
