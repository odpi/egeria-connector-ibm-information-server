<!-- SPDX-License-Identifier: Apache-2.0 -->
<!-- Copyright Contributors to the ODPi Egeria project. -->

# Samples

These sample Postman collections illustrate configuring and using the IBM Information Server connectors
for ODPi Egeria.

Each should be used with the
[environment defined in the Egeria Core samples](https://github.com/odpi/egeria/blob/main/open-metadata-resources/open-metadata-samples/postman-rest-samples/README.md),
which has all of the needed variables defined within it.

# Egeria-IBM-IGC-config.postman_collection.json

This script can be used to configure Egeria for use with an existing IBM Information Governance Catalog ("IGC") environment.

Prerequisites:

- an existing IBM IGC environment, running v11.5.0.2 or later

IGC-specific variables:

- `igc_host` the hostname (or IP address) of the existing IGC environment (domain / servics tier)
- `igc_port` the port number of the domain tier console of the existing IGC environment
- `igc_user` the username of a user to access IGC's REST API
- `igc_password` the password of the user to access IGC's REST API
- `igc_kafka` the hostname (or IP address) and port of IGC's internal Kafka bus (`domain_host:59092` for non-UG environments; `ug_host:9092` for UG environments)

Each step is sequentially numbered so that they can be executed in-order as part of a Postman "Runner", if desired.

# Egeria-IBM-IGC-read.postman_collection.json

This script can be used to run through a number of different tests of the connector, assuming the IGC environment
has first been populated with the Coco Pharmaceutical samples and column analysis has been run (either automatically
in v11.7.x or manually in v11.5.x).

Prerequisites:

- an existing IBM IGC environment, running v11.5.0.1 or later
- [samples loaded for Coco Pharmaceuticals](https://github.com/odpi/egeria/blob/main/open-metadata-resources/open-metadata-deployment/sample-data/coco-pharmaceuticals/README.md)
- column analysis run against the tables in the Coco Pharmaceuticals sample databases
- connector configured (eg. using `Egeria-IBM-IGC-config.postman_collection.json` above)

# Egeria-IBM-DataStage-config.postman_collection.json

This script can be used to configure Egeria for use with an existing IBM InfoSphere DataStage environment.

Prerequisites:

- an existing IBM InfoSphere DataStage environment, running v11.5.0.2 or later
- IBM Information Governance Catalog also running within that same environment

DataStage-specific variables:

- `igc_host` the hostname (or IP address) of the existing IGC environment (domain / servics tier)
- `igc_port` the port number of the domain tier console of the existing IGC environment
- `igc_user` the username of a user to access IGC's REST API
- `igc_password` the password of the user to access IGC's REST API

Each step is sequentially numbered so that they can be executed in-order as part of a Postman "Runner", if desired.

(Note that in the sample the server name for the access services server is hard-coded to `omas` and the server for the
DataStage proxy is hard-coded to `datastage`.)

----
License: [CC BY 4.0](https://creativecommons.org/licenses/by/4.0/),
Copyright Contributors to the ODPi Egeria project.
