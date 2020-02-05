<!-- SPDX-License-Identifier: CC-BY-4.0 -->
<!-- Copyright Contributors to the ODPi Egeria project. -->

[![GitHub](https://img.shields.io/github/license/odpi/egeria-connector-ibm-information-server)](LICENSE) [![Azure](https://dev.azure.com/odpi/egeria/_apis/build/status/odpi.egeria-connector-ibm-information-server)](https://dev.azure.com/odpi/Egeria/_build) [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=egeria-connector-ibm-information-server&metric=alert_status)](https://sonarcloud.io/dashboard?id=egeria-connector-ibm-information-server) [![Maven Central](https://img.shields.io/maven-central/v/org.odpi.egeria/egeria-connector-ibm-information-server)](https://mvnrepository.com/artifact/org.odpi.egeria/egeria-connector-ibm-information-server)

# IBM InfoSphere Information Server Connectors

[IBM InfoSphere Information Server](https://www.ibm.com/marketplace/infosphere-information-server) is a
commercially-available data integration, quality and governance suite from IBM. It is comprised of multiple modules,
and this repository contains Egeria connectors for some of those modules:

- [IBM InfoSphere Information Governance Catalog](https://www.ibm.com/marketplace/information-governance-catalog) is the
    metadata repository module within the suite, commonly referred to simply as "IGC". While the most recent versions of
    the software provide their own connectivity to OMRS cohorts, an example implementation of such connectivity is also
    provided here both for reference purposes and also to provide an integration point to older versions of the software
    (from v11.5.0.1 onwards).

    Note that this open connector is read-only: it only implements those methods necessary to search, retrieve, and
    communicate metadata from IGC out into the cohort -- it does *not* implement the ability to update IGC based on
    events received from other members of the cohort.

    Furthermore, [only a subset of the overall Open Metadata Types are currently implemented](docs/mappings/README.md).
    
    For a write-supporting connector you should use the connector that is provided as part of IBM Information Server
    itself, though be aware that this is only available in recent versions of v11.7.

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

### TL;DR for IGC

The quick version:

1. Download the latest IBM Information Server connector from: https://odpi.jfrog.io/odpi/egeria-snapshot-local/org/odpi/egeria/egeria-connector-ibm-information-server-package/1.5-SNAPSHOT/egeria-connector-ibm-information-server-package-1.5-SNAPSHOT-jar-with-dependencies.jar
1. Download the latest Egeria core from: https://odpi.jfrog.io/odpi/egeria-snapshot-local/org/odpi/egeria/server-chassis-spring/1.5-SNAPSHOT/server-chassis-spring-1.5-SNAPSHOT.jar
1. Rename the downloaded Egeria core file to `egeria-server-chassis-spring.jar`.
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
1. In another shell / command-line window, run the following commands to configure Egeria and startup its services --
    making sure to replace the hostnames and port numbers with those relevant to your own environment (`localhost:9092`
    for your own Kafka bus, `infosvr:59092` with the Information Server-embedded Kafka host and port, `infosvr` with
    the hostname of your Information Server domain (services) tier, `9446` with the port number of your Information
    Server domain (services) tier, `isadmin` with the username for your Information Server environment, and `isadmin`
    with the password for your Information Server environment):
    ```bash
    $ curl -X POST -H "Content-Type: application/json" --data '{"producer":{"bootstrap.servers":"localhost:9092"},"consumer":{"bootstrap.servers":"localhost:9092"}}' "http://localhost:8080/open-metadata/admin-services/users/admin/servers/myserver/event-bus?connectorProvider=org.odpi.openmetadata.adapters.eventbus.topic.kafka.KafkaOpenMetadataTopicProvider&topicURLRoot=OMRSTopic"
    $ curl -X POST "http://localhost:8080/open-metadata/admin-services/users/admin/servers/myserver/cohorts/mycohort"
    $ curl -X POST -H "Content-Type: application/json" --data '{"class":"Connection","connectorType":{"class":"ConnectorType","connectorProviderClassName":"org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSRepositoryConnectorProvider"},"endpoint":{"class":"Endpoint","address":"infosvr:9446","protocol":"https"},"userId":"isadmin","clearPassword":"isadmin","configurationProperties":{"defaultZones":["default"]}}' "http://localhost:8080/open-metadata/admin-services/users/admin/servers/myserver/local-repository/mode/repository-proxy/connection"
    $ curl -X POST "http://localhost:8080/open-metadata/admin-services/users/admin/servers/myserver/local-repository/event-mapper-details?connectorProvider=org.odpi.egeria.connectors.ibm.igc.eventmapper.IGCOMRSRepositoryEventMapperProvider&eventSource=infosvr:59092"
    $ curl -X POST "http://localhost:8080/open-metadata/admin-services/users/admin/servers/myserver/instance"
    ```

### Enable IGC's events

To start using the connector, you will need an IGC environment, running either version 11.5 or 11.7 of the software.
(The connector will automatically detect which version as part of its initialization.) You will need to first enable event
notification in your IGC environment:

1. Navigate to "Administration": !["Administration"](docs/ibm-igc-setup1.png)
1. Navigate to "Event Notification" within the "Setup" heading: !["Event Notification"](docs/ibm-igc-setup2.png)
1. Toggle "Enable" and then "Save and Close": !["Enable" and "Save and Close"](docs/ibm-igc-setup3.png)

There should not be any need to restart the environment after enabling the event notification.

### Obtain the connector

You can either download the latest released or snapshot version of the connector directly from ODPi, or build the
connector yourself. In both cases, once you have the jar file for the connector
(`egeria-connector-ibm-information-server-package-VERSION-jar-with-dependencies.jar`) this needs to be copied to a
location where it can be run alongside the OMAG Server Platform from Egeria core itself. For example, this could be
placing the file into the `/lib` directory as `/lib/egeria-connector-ibm-information-server-package-VERSION-jar-with-dependencies.jar`.

#### Download from ODPi

To download a pre-built version of the connector, use either of the following URLs (depending on whether you want an
officially-released version or the latest snapshot):

- Release: https://odpi.jfrog.io/odpi/egeria-release-local/org/odpi/egeria/egeria-connector-ibm-information-server-package/1.3/egeria-connector-ibm-information-server-package-1.3-jar-with-dependencies.jar
- Snapshot: https://odpi.jfrog.io/odpi/egeria-snapshot-local/org/odpi/egeria/egeria-connector-ibm-information-server-package/1.5-SNAPSHOT/egeria-connector-ibm-information-server-package-1.5-SNAPSHOT-jar-with-dependencies.jar

#### Building the connector yourself

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

### Startup the OMAG Server Platform

You can startup the OMAG Server Platform with this connector ready-to-be-configured by running the following
(this example assumes you've placed the connector jar file in the `/lib` directory, if you are using a different
location simply change the `-Dloader.path=` to point to the location you have used):

```bash
$ java -Dloader.path=/lib -jar server-chassis-spring-VERSION.jar
```

(This command will startup the OMAG Server Platform, including all libraries
in the `/lib` directory as part of the classpath of the OMAG Server Platform.)

### Configure the IGC connector

You will need to configure the OMAG Server Platform as follows (order is important) to make use of the IGC connector.
For example payloads and endpoints, see the [Postman samples](samples).

1. Configure your event bus for Egeria, by POSTing a payload like the following (replace the `localhost:9092` with the
    hostname and port number where your Kafka bus is running, and assuming you are running the OMAG Server Platform
    locally at its default port of `8080`):

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
    POST http://localhost:8080/open-metadata/admin-services/users/admin/servers/myserver/event-bus?connectorProvider=org.odpi.openmetadata.adapters.eventbus.topic.kafka.KafkaOpenMetadataTopicProvider&topicURLRoot=OMRSTopic
    ```

1. Configure the cohort, by POSTing something like the following:

    ```
    POST http://localhost:8080/open-metadata/admin-services/users/admin/servers/myserver/cohorts/mycohort
    ```

1. Configure the IGC connector, by POSTing a payload like the following, replacing the `{{igc_host}}` with the hostname
    of your IGC instance, `{{igc_port}}` with the port number of its domain (services) tier, `{{igc_user}}` with the
    username of a user able to access the REST API (eg. `isadmin`), and `{{igc_password}}` with the password for that
    user:

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
            "defaultZones": [ "default" ]
        }
    }
    ```

    to:

    ```
    POST http://localhost:8080/open-metadata/admin-services/users/admin/servers/myserver/local-repository/mode/repository-proxy/connection
    ```

    You can optionally also provide a list of zone names that will be used as default zones for all Assets retrieved
    from IGC through the proxy (in the example above this is a single zone called `default`).

    Note that you also need to provide the `connectorProvider` parameter, set to the name of the IGC
    connectorProvider class (value as given above).

1. Configure the event mapper for IGC, by POSTing something like the following:

    ```
    POST http://localhost:8080/open-metadata/admin-services/users/admin/servers/myserver/local-repository/event-mapper-details?connectorProvider=org.odpi.egeria.connectors.ibm.igc.eventmapper.IGCOMRSRepositoryEventMapperProvider&eventSource=my.igc.services.host.com:59092
    ```

    The hostname provided at the end (`my.igc.services.host.com`) should be replaced with the host on which your
    IGC-embedded kafka bus is running, and include the appropriate port number for connecting to that bus.
    (For v11.5 this is your domain (services) tier and port `59092`, whereas in the latest versions of 11.7 it may be
    running on your Unified Governance / Enterprise Search tier, on port `9092`. In both cases the port will need to be
    network-accessible by the host where you are running Egeria itself for any events to be picked up by Egeria.)

1. The connector and event mapper should now be configured, and you should now be able
    to start the instance by POSTing something like the following:

   ```
   POST http://localhost:8080/open-metadata/admin-services/users/admin/servers/myserver/instance
   ```

After following these instructions, your IGC instance will be participating in the Egeria cohort. For those objects
supported by the connector, any new instances or updates to existing instances should result in that metadata
automatically being communicated out to the rest of the cohort.

### Configure the DataStage connector

You will need to configure the OMAG Server Platform as follows (order is important) to make use of the DataStage connector.
For example payloads and endpoints, see the [Postman samples](samples).

1. Configure a local Egeria metadata repository for the access services, by POSTing something like the following
    (to use the graph repository, and assuming you are running the OMAG Server Platform locally at its default port of
    `8080`):

    ```
    POST http://localhost:8080/open-metadata/admin-services/users/admin/servers/omas_server/local-repository/mode/local-graph-repository
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
    POST http://localhost:8080/open-metadata/admin-services/users/admin/servers/omas_server/event-bus?connectorProvider=org.odpi.openmetadata.adapters.eventbus.topic.kafka.KafkaOpenMetadataTopicProvider&topicURLRoot=OMRSTopic
    ```

1. Enable the access services by POSTing something like the following:

    ```
    POST http://localhost:8080/open-metadata/admin-services/users/admin/servers/omas_server/access-services?serviceMode=ENABLED
    ```

1. The access services should now be configured, and you should now be able to start them by POSTing something like the
    following:

    ```
    POST http://localhost:8080/open-metadata/admin-services/users/admin/servers/omas_server/instance
    ```

1. Configure a local metadata repository for the DataStage connector, by POSTing something like the following
    (to use the in-memory repository):

    ```
    POST http://localhost:8080/open-metadata/admin-services/users/admin/servers/datastage_proxy/local-repository/mode/in-memory-repository
    ```

1. Configure the DataStage connector, by POSTing a payload like the following, replacing the `{{igc_host}}` with the
    hostname of your IGC instance, `{{igc_port}}` with the port number of its domain (services) tier, `{{igc_user}}`
    with the username of a user able to access the REST API (eg. `isadmin`), and `{{igc_password}}` with the password
    for that user:

    ```json
    {
        "class": "DataEngineProxyConfig",
        "accessServiceRootURL": "http://localhost:8080",
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
                "includeVirtualAssets": true
            }
        },
        "pollIntervalInSeconds": 60
    }
    ```

    to:

    ```
    POST http://localhost:8080/open-metadata/admin-services/users/admin/servers/datastage_proxy/data-engine-proxy-service/configuration
    ```

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

    Finally, note that we specify the connector should poll for changes at a particular interval. This is because
    changes to DataStage routines within DataStage do not trigger events into IGC's embedded Kafka topic (at least for
    older versions of Information Server), so we must busy-poll for changes. You can modify the interval if you want
    the connector to wait more or less time between each check for changes.

1. The connector should now be configured, and you should now be able to start the instance by POSTing something like
    the following:

   ```
   POST http://localhost:8080/open-metadata/admin-services/users/admin/servers/datastage_proxy/instance
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
