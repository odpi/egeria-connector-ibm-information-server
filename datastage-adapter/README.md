<!-- SPDX-License-Identifier: CC-BY-4.0 -->
<!-- Copyright Contributors to the ODPi Egeria project. -->

[![javadoc](https://javadoc.io/badge2/org.odpi.egeria/egeria-connector-ibm-datastage-adapter/javadoc.svg)](https://javadoc.io/doc/org.odpi.egeria/egeria-connector-ibm-datastage-adapter) [![Maven Central](https://img.shields.io/maven-central/v/org.odpi.egeria/egeria-connector-ibm-datastage-adapter)](https://mvnrepository.com/artifact/org.odpi.egeria/egeria-connector-ibm-datastage-adapter)

# IBM InfoSphere DataStage connector

[IBM InfoSphere DataStage](https://www.ibm.com/marketplace/datastage) is a high-performance ETL module within the
suite, and is pre-integrated to IGC. The connector implemented for this module is a Data Engine Proxy, translating
the creation and update of DataStage ETL routines (jobs and sequences) into the appropriate Egeria components to
represent and participate in end-to-end data lineage.

## How it works

The IBM DataStage Data Engine Proxy Connector works through a combination of the following:

- IBM IGC's REST API, itself abstracted through the [IGC REST Client Library](../igc-clientlibrary)
- An Information Governance Rule in IGC named `Job metadata will be periodically synced through ODPi Egeria's Data Engine OMAS`
  and whose description provides the precise date and time at which synchronization last occurred
- Egeria's [Data Engine Proxy Services Governance Server](https://github.com/odpi/egeria/tree/master/open-metadata-implementation/governance-servers/data-engine-proxy-services)
    - which in turn makes use of an Egeria [Data Engine OMAS](https://github.com/odpi/egeria/tree/master/open-metadata-implementation/access-services/data-engine)

## Getting started

### TL;DR

The quick version:

1. Start with the TL;DR instructions on the [main page](../README.md).
1. In another shell / command-line window, run the following commands to configure Egeria and startup its services --
   making sure to replace the hostnames and port numbers with those relevant to your own environment (`localhost:9092`
   for your own Kafka bus, `infosvr` with the hostname of your Information Server domain (services) tier, `9446` with
   the port number of your Information Server domain (services) tier, `isadmin` with the username for your Information
   Server environment, and `isadmin` with the password for your Information Server environment):
    ```bash
    $ curl -k -X POST "https://localhost:9443/open-metadata/admin-services/users/admin/servers/omas_server/local-repository/mode/local-graph-repository"
    $ curl -k -X POST -H "Content-Type: application/json" --data '{"producer":{"bootstrap.servers":"localhost:9092"},"consumer":{"bootstrap.servers":"localhost:9092"}}' "https://localhost:9443/open-metadata/admin-services/users/admin/servers/myserver/event-bus?connectorProvider=org.odpi.openmetadata.adapters.eventbus.topic.kafka.KafkaOpenMetadataTopicProvider&topicURLRoot=OMRSTopic"
    $ curl -k -X POST "https://localhost:9443/open-metadata/admin-services/users/admin/servers/omas_server/access-services?serviceMode=ENABLED"
    $ curl -k -X POST "https://localhost:9443/open-metadata/admin-services/users/admin/servers/omas_server/instance"
    $ curl -k -X POST "https://localhost:9443/open-metadata/admin-services/users/admin/servers/datastage_proxy/local-repository/mode/in-memory-repository"
    $ curl -k -X POST -H "Content-Type: application/json" --data '{"class":"DataEngineProxyConfig","accessServiceRootURL":"https://localhost:9443","accessServiceServerName":"omas_server","dataEngineConnection":{"class":"Connection","connectorType":{"class":"ConnectorType","connectorProviderClassName":"org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.DataStageConnectorProvider"},"endpoint":{"class":"Endpoint","address":"infosvr:9446","protocol":"https"},"userId":"isadmin","clearPassword":"isadmin"},"pollIntervalInSeconds":60}' "https://localhost:9443/open-metadata/admin-services/users/admin/servers/datastage_proxy/data-engine-proxy-service/configuration"
    $ curl -k -X POST "https://localhost:9443/open-metadata/admin-services/users/admin/servers/datastage_proxy/instance"
    ```

### Detailed steps for configuring the DataStage connector

You will need to configure the OMAG Server Platform as follows (order is important) to make use of the DataStage connector.
For example payloads and endpoints, see the [Postman samples](samples).

1. Configure a local Egeria metadata repository for the access services, by POSTing something like the following
   (to use the graph repository, and assuming you are running the OMAG Server Platform locally at its default port of
   `9443`):

    ```
    POST https://localhost:9443/open-metadata/admin-services/users/admin/servers/omas_server/local-repository/mode/local-graph-repository
    ```

1. Configure your event bus for the access services, by POSTing a payload like the following (replace the
   `localhost:9092` with the hostname and port number where your Kafka bus is running):

    ```json
    {
        "producer": {
            "bootstrap.servers":"localhost:9092"
        },
        "consumer": {
            "bootstrap.servers":"localhost:9092"
        }
    }
    ```

   to:

    ```
    POST https://localhost:9443/open-metadata/admin-services/users/admin/servers/omas_server/event-bus?connectorProvider=org.odpi.openmetadata.adapters.eventbus.topic.kafka.KafkaOpenMetadataTopicProvider&topicURLRoot=OMRSTopic
    ```

1. Enable the access services by POSTing something like the following:

    ```
    POST https://localhost:9443/open-metadata/admin-services/users/admin/servers/omas_server/access-services?serviceMode=ENABLED
    ```

1. The access services should now be configured, and you should now be able to start them by POSTing something like the
   following:

    ```
    POST https://localhost:9443/open-metadata/admin-services/users/admin/servers/omas_server/instance
    ```

1. Configure a local metadata repository for the DataStage connector, by POSTing something like the following
   (to use the in-memory repository):

    ```
    POST https://localhost:9443/open-metadata/admin-services/users/admin/servers/datastage_proxy/local-repository/mode/in-memory-repository
    ```

1. Configure the DataStage connector, by POSTing a payload like the following, replacing the `{{igc_host}}` with the
   hostname of your IGC instance, `{{igc_port}}` with the port number of its domain (services) tier, `{{igc_user}}`
   with the username of a user able to access the REST API (eg. `isadmin`), and `{{igc_password}}` with the password
   for that user:

    ```json
    {
        "class": "DataEngineProxyConfig",
        "accessServiceRootURL": "https://localhost:9443",
        "accessServiceServerName": "omas_server",
        "dataEngineConnection": {
            "class": "Connection",
            "connectorType": {
                "class": "ConnectorType",
                "connectorProviderClassName": "org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.DataStageConnectorProvider"
            },
            "endpoint": {
                "class": "Endpoint",
                "address": "{{igc_host}}:{{igc_port}}",
                "protocol": "https"
            },
            "userId": "{{igc_user}}",
            "clearPassword": "{{igc_password}}",
            "configurationProperties": {
                "createDataStoreSchemas": false,
                "includeVirtualAssets": true,
                "limitToProjects": [ "dstage1" ]
            }
        },
        "pollIntervalInSeconds": 60
    }
    ```

   to:

    ```
    POST https://localhost:9443/open-metadata/admin-services/users/admin/servers/datastage_proxy/data-engine-proxy-service/configuration
    ```

   To operate, the Information Server user credentials must have (at a minimum) the following roles:
   `Suite User`, `Information Governance Catalog User`, and `Information Governance Catalog Glossary Author`.
   (The first two are both read-only, non-administrative roles, while the last allows synchronization objects
   to be created to track the last synchronization point of the DataStage job information.) Finally, if using the
   connector option to include virtual assets (`"includeVirtualAssets": true`), the user will also need the
   `Information Governance Catalog Information Asset Author` role, as this role is needed to be able to retrieve the
   full details of virtual assets.

   For v11.7 and above, the user will also need the `Information Governance Catalog Asset Administrator` role in order
   to automate the detection of lineage within IGC, prior to having a complete set of lineage for the DataStage
   connector itself to retrieve. (This is a necessary step to avoid potential race conditions between lineage being
   fully calculated within IGC and the DataStage connector polling for the lineage information.)

   Note that you need to provide the `connectorProvider` parameter, set to the name of the DataStage
   connectorProvider class (value as given above).

   We have also provided some configuration options, set to their default values, that control how the DataStage proxy
   works:

    - `createDataStoreSchemas` is a boolean that indicates whether to include the creation of data store-level schemas
      (when true) or not (when false). When the DataStage connector is used alone in a cohort, without an IGC proxy
      also running in the cohort, this should be set to `true` to ensure that the data stores used as sources or
      targets by DataStage exist in lineage. If an IGC proxy is also being used in the cohort, this should be left at
      the default value (`false`) to ensure that the IGC proxy remains the home metadata collection of data store
      entities and is responsible for notifications of their changes, etc.
    - `includeVirtualAssets` is a boolean that indicates whether to include the creation of schemas for virtual assets
      (when true) or not (when false). By default this will be enabled (true) to ensure that virtual assets that might
      exist in lineage are still included (as IGC alone does not communicate any information about virtual assets).
    - `limitToProjects` is a list (or single string) that defines the DataStage projects from which we should retrieve
      jobs and sequences. By default (when not provided), jobs and sequences from all jobs will be included; however,
      if a list of projects is provided on this parameter, only jobs and sequences from those projects will be
      included.

   Finally, note that we specify the connector should poll for changes at a particular interval. This is because
   changes to DataStage routines within DataStage do not trigger events into IGC's embedded Kafka topic (at least for
   older versions of Information Server), so we must busy-poll for changes. You can modify the interval if you want
   the connector to wait more or less time between each check for changes.

1. The connector should now be configured, and you should now be able to start the instance by POSTing something like
   the following:

   ```
   POST https://localhost:9443/open-metadata/admin-services/users/admin/servers/datastage_proxy/instance
   ```

After following these instructions, your DataStage environment will be polled for any changes (including creation of new)
DataStage jobs (including sequences). For those objects supported by the connector, any new instances or updates to
existing instances should result in that metadata automatically being communicated to the Data Engine OMAS within the
number of seconds specified by the `pollIntervalInSeconds` (though be aware that a large number of changes may take some
time to synchronize).

## Loading samples

If you have a completely empty environment, you may want to load some sample metadata to further explore.

Lineage samples are provided under [sample-data/minimal](https://github.com/odpi/egeria-samples/tree/master/sample-data/minimal)
of the samples repository.

These samples are provided as a set of content that can be automatically loaded to Information Server (IGC and DataStage)
using Ansible, and a number of publicly-available Ansible roles. (See instructions via the link itself.)

----
License: [CC BY 4.0](https://creativecommons.org/licenses/by/4.0/),
Copyright Contributors to the ODPi Egeria project.
