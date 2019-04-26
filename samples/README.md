<!-- SPDX-License-Identifier: Apache-2.0 -->
<!-- Copyright Contributors to the ODPi Egeria project. -->

# Egeria IGC repo connector.postman_collection.json

This script can be used to configure Egeria for use with an existing IBM Information Governance Catalog ("IGC") environment.

Prerequisites:

- an existing IBM IGC environment, running v11.5.0.1 or later
- kafka running with the queue specified below created. 

Variables:

- `baseURL` the egeria URL
- `user` the userName to pass to Egeria
- `server` the server name for Egeria
- `cohort` the name of the cohort: used as the kafka queue name for OMRS
- `kafkaep` kafka endpoint for the cohort
- `igc_host` the hostname (or IP address) of the existing IGC environment (domain / servics tier)
- `igc_port` the port number of the domain tier console of the existing IGC environment
- `igc_user` the username of a user to access IGC's REST API
- `igc_password` the password of the user to access IGC's REST API
- `igc_kafka` the hostname (or IP address) and port of IGC's internal Kafka bus (`domain_host:59092` for non-UG environments; `ug_host:9092` for UG environments)

Each step is sequentially numbered so that they can be executed in-order as part of a Postman "Runner", if desired.

# Egeria IGC Connector.postman_environment.json

Provides an example environment definition, with all the variables needed pre-defined (you simply need to provide values specific to your own environment).


----
License: [CC BY 4.0](https://creativecommons.org/licenses/by/4.0/),
Copyright Contributors to the ODPi Egeria project.
