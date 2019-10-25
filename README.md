<!-- SPDX-License-Identifier: CC-BY-4.0 -->
<!-- Copyright Contributors to the ODPi Egeria project. -->

[![GitHub](https://img.shields.io/github/license/odpi/egeria-connector-ibm-information-server)](LICENSE) [![Azure](https://dev.azure.com/odpi/egeria/_apis/build/status/odpi.egeria-connector-ibm-information-server)](https://dev.azure.com/odpi/Egeria/_build) [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=egeria-connector-ibm-information-server&metric=alert_status)](https://sonarcloud.io/dashboard?id=egeria-connector-ibm-information-server)

# IBM InfoSphere Information Server Connectors

[IBM InfoSphere Information Server](https://www.ibm.com/marketplace/infosphere-information-server) is a
commercially-available data integration, quality and governance suite from IBM. It is comprised of multiple modules,
and this repository contains Egeria connectors for some of those modules:

- [IBM InfoSphere Information Governance Catalog](https://www.ibm.com/marketplace/information-governance-catalog) is the
    metadata repository module within the suite, commonly referred to simply as "IGC". While the most recent versions of
    the software provide their own connectivity to OMRS cohorts, an example implementation of such connectivity is also
    provided here both for reference purposes and also to provide an integration point to older versions of the software
    (from v11.5.0.1 onwards).

    Note that currently the implemented connector is read-only: it only implements those methods necessary to search,
    retrieve, and communicate metadata from IGC out into the cohort -- it does *not* currently implement the ability to
    update IGC based on events received from other members of the cohort.

    Furthermore, [only a subset of the overall Open Metadata Types are currently implemented](docs/mappings/README.md).

- [IBM InfoSphere DataStage](https://www.ibm.com/marketplace/datastage) is a high-performance ETL module within the
    suite, and is pre-integrated to IGC. The connector implemented for this module is a Data Engine Proxy, translating
    the creation and update of DataStage ETL routines (jobs and sequences) into the appropriate Egeria components to
    represent and participate in end-to-end data lineage.

## How it works

The IBM IGC Repository Connector works through a combination of the following:

- IBM IGC's REST API, itself abstracted through the [IGC REST Client Library](igc-clientlibrary)
- IBM InfoSphere Information Server's embedded Apache Kafka event bus
    - specifically the `InfosphereEvents` topic (hence the need to enable events in the setup)
- Some [IGC extensions](docs/ibm-igc-extensions.md) that implement specific additional functionality
- Egeria's Metadata Repository Proxy Services

The IBM DataStage Data Engine Proxy Connector works through a combination of the following:

- IBM IGC's REST API, itself abstracted through the [IGC REST Client Library](igc-clientlibrary)
- An Information Governance Rule in IGC named `Job metadata will be periodically synced through ODPi Egeria's Data Engine OMAS`
    and whose description provides the precise date and time at which synchronization last occurred
- Egeria's [Data Engine Proxy Services Governance Server](https://github.com/odpi/egeria/tree/master/open-metadata-implementation/governance-servers/data-engine-proxy-services)
    - which in turn makes use of an Egeria [Data Engine OMAS](https://github.com/odpi/egeria/tree/master/open-metadata-implementation/access-services/data-engine)

## Getting started

### Enable IGC's events

To start using the connector, you will need an IGC environment, running either version 11.5 or 11.7 of the software.
(The connector will automatically detect which version as part of its initialization.) You will need to first enable event
notification in your IGC environment:

1. Navigate to "Administration": !["Administration"](docs/ibm-igc-setup1.png)
1. Navigate to "Event Notification" within the "Setup" heading: !["Event Notification"](docs/ibm-igc-setup2.png)
1. Toggle "Enable" and then "Save and Close": !["Enable" and "Save and Close"](docs/ibm-igc-setup3.png)

There should not be any need to restart the environment after enabling the event notification.

### Build connector and copy to OMAG Server Platform

After building the connector project (`mvn clean install`) the connector is available as:

```text
distribution/target/egeria-connector-ibm-information-server-package-VERSION.jar
```

Simply copy this file to a location where it can be run alongside the OMAG Server
Platform from the Egeria core (in the example below, the file would be copied to
`/lib/egeria-connector-ibm-information-server-package-VERSION.jar`).

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

### Startup the OMAG Server Platform

You can startup the OMAG Server Platform with this connector ready-to-be-configured by running:

```bash
$ java -Dloader.path=/lib -jar server-chassis-spring-VERSION.jar
```

(This command will startup the OMAG Server Platform, including all libraries
in the `/lib` directory as part of the classpath of the OMAG Server Platform.)

### Configure the IGC connector

You will need to configure the OMAG Server Platform as follows (order is important) to make use of the IGC connector.
For example payloads and endpoints, see the [Postman samples](samples).

1. Configure your event bus for Egeria, by POSTing a payload like the following:

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
    POST http://localhost:8080/open-metadata/admin-services/users/{{user}}/servers/{{server}}/event-bus?connectorProvider=org.odpi.openmetadata.adapters.eventbus.topic.kafka.KafkaOpenMetadataTopicProvider&topicURLRoot=OMRSTopic
    ```

1. Configure the cohort, by POSTing something like the following:

    ```
    POST http://localhost:8080/open-metadata/admin-services/users/{{user}}/servers/{{server}}/cohorts/cocoCohort
    ```

1. Configure the IGC connector, by POSTing a payload like the following:

    ```json
    {
        "class": "Connection",
        "connectorType": {
            "class": "ConnectorType",
            "connectorProviderClassName": "org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSRepositoryConnectorProvider"
        },
        "endpoint": {
            "class": "Endpoint",
            "address": "{{igc_host}}:{{igc_port}}",
            "protocol": "https"
        },
        "userId": "{{igc_user}}",
        "clearPassword": "{{igc_password}}",
        "configurationProperties": {
            "defaultZones": [ "x", "y", "z" ]
        }
    }
    ```

    to:

    ```
    {{baseURL}}/open-metadata/admin-services/users/{{user}}/servers/{{server}}/local-repository/mode/repository-proxy/connection
    ```

    The payload should include the hostname and port of your IGC environment's domain (services) tier,
    and a `username` and `password` through which the REST API can be accessed.
    
    You can optionally also provide a list of zone names that will be used as default zones for all Assets retrieved
    from IGC through the proxy.

    Note that you also need to provide the `connectorProvider` parameter, set to the name of the IGC
    connectorProvider class (value as given above).

1. Configure the event mapper for IGC, by POSTing something like the following:

    ```
    POST http://localhost:8080/open-metadata/admin-services/users/{{user}}/servers/{{server}}/local-repository/event-mapper-details?connectorProvider=org.odpi.egeria.connectors.ibm.igc.eventmapper.IGCOMRSRepositoryEventMapperProvider&eventSource=my.igc.services.host.com:59092
    ```

    The hostname provided at the end should be the host on which your IGC-embedded kafka bus is running, and include
    the appropriate port number for connecting to that bus. (For v11.5 this is your domain (services) tier and port `59092`,
    whereas in the latest versions of 11.7 it may be running on your Unified Governance / Enterprise Search tier, on port
    `9092`.)

1. The connector and event mapper should now be configured, and you should now be able
    to start the instance by POSTing something like the following:

   ```
   POST http://localhost:8080/open-metadata/admin-services/users/{{user}}/servers/{{server}}/instance
   ```

After following these instructions, your IGC instance will be participating in the Egeria cohort. For those objects
supported by the connector, any new instances or updates to existing instances should result in that metadata
automatically being communicated out to the rest of the cohort.

### Configure the DataStage connector

You will need to configure the OMAG Server Platform as follows (order is important) to make use of the DataStage connector.
For example payloads and endpoints, see the [Postman samples](samples).

1. Configure a local Egeria metadata repository for the access services, by POSTing something like the following
    (to use the graph repository):

    ```
    POST http://localhost:8080/open-metadata/admin-services/users/{{user}}/servers/{{omas_server}}/local-repository/mode/local-graph-repository
    ```

1. Configure your event bus for the access services, by POSTing a payload like the following:

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
    POST http://localhost:8080/open-metadata/admin-services/users/{{user}}/servers/{{omas_server}}/event-bus?connectorProvider=org.odpi.openmetadata.adapters.eventbus.topic.kafka.KafkaOpenMetadataTopicProvider&topicURLRoot=OMRSTopic
    ```

1. Enable the access services by POSTing something like the following:

    ```
    POST http://localhost:8080/open-metadata/admin-services/users/{{user}}/servers/{{omas_server}}/access-services?serviceMode=ENABLED
    ```

1. The access services should now be configured, and you should now be able to start them by POSTing something like the
    following:

    ```
    POST http://localhost:8080/open-metadata/admin-services/users/{{user}}/servers/{{omas_server}}/instance
    ```

1. Configure a local metadata repository for the DataStage connector, by POSTing something like the following
    (to use the in-memory repository):

    ```
    POST http://localhost:8080/open-metadata/admin-services/users/{{user}}/servers/{{ds_server}}/local-repository/mode/in-memory-repository
    ```

1. Configure the DataStage connector, by POSTing a payload like the following:

    ```json
    {
        "class": "DataEngineProxyConfig",
        "accessServiceRootURL": "http://localhost:8080",
        "accessServiceServerName": "omas",
        "dataEngineConnection": {
            "class": "Connection",
            "connectorType": {
                "class": "ConnectorType",
                "connectorProviderClassName": "org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.DataStageConnectorProvider"
            },
            "endpoint": {
                "class": "Endpoint",
                "address": "{{ds_host}}:{{ds_port}}",
                "protocol": "https"
            },
            "userId": "{{username}}",
            "clearPassword": "{{password}}"
        },
        "pollIntervalInSeconds": 60
    }
    ```

    to:

    ```
    POST http://localhost:8080/open-metadata/admin-services/users/{{user}}/servers/{{ds_server}}/data-engine-proxy-service/configuration
    ```

    The payload should include the hostname and port of your Information Server environment's domain (services) tier,
    and a `username` and `password` through which the IGC module's REST API can be accessed.

    Note that you also need to provide the `connectorProvider` parameter, set to the name of the DataStage
    connectorProvider class (value as given above).

    Finally, note that we specify the connector should poll for changes at a particular interval. This is because
    changes to DataStage routines within DataStage do not trigger events into IGC's embedded Kafka topic (at least for
    older versions of Information Server), so we must busy-poll for changes. You can modify the interval if you want
    the connector to wait more or less time between each check for changes.

1. The connector should now be configured, and you should now be able to start the instance by POSTing something like
    the following:

   ```
   POST http://localhost:8080/open-metadata/admin-services/users/{{user}}/servers/{{ds_server}}/instance
   ```

After following these instructions, your DataStage environment will be polled for any changes (including creation of new)
DataStage jobs (including sequences). For those objects supported by the connector, any new instances or updates to
existing instances should result in that metadata automatically being communicated to the Data Engine OMAS within the
number of seconds specified by the `pollIntervalInSeconds` (though be aware that a large number of changes may take some
time to synchronize).

## Loading samples

If you have a completely empty IGC environment, you may want to load some sample metadata to further explore.

Samples are provided under [egeria/open-metadata-resources/open-metadata-deployment/sample-data/](https://github.com/odpi/egeria/tree/master/open-metadata-resources/open-metadata-deployment/sample-data).

For example, there you will find a [Coco Pharmaceuticals](https://github.com/odpi/egeria/tree/master/open-metadata-resources/open-metadata-deployment/sample-data/coco-pharmaceuticals)
set of samples. These samples are provided as a set of content that can be automatically loaded to IGC using Ansible,
and a number of publicly-available Ansible roles. (See instructions via the link itself.)

Assuming you have first setup and configured your IGC environment as part of an Egeria cohort, loading the samples will
generate numerous events out to the rest of the cohort for all of the different object types, relationships and
classifications covered by the samples: GlossaryTerms, RelationalTables, SemanticAssignments, Confidentiality, and so on.


----
License: [CC BY 4.0](https://creativecommons.org/licenses/by/4.0/),
Copyright Contributors to the ODPi Egeria project.
