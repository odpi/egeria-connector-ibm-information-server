<!-- SPDX-License-Identifier: CC-BY-4.0 -->
<!-- Copyright Contributors to the ODPi Egeria project. -->

# DataStage Adapter

The DataStage proxy connects an IBM DataStage environment (via Information Governance Catalog) to
an Egeria Data Engine OMAS.

!!! tip "Ensure you have already completed the [Setup](../common/) steps before proceeding."

## 5. Configure the connector

### a. Configure the OMAS

Egeria's OMAS's provide a set of typically more coarse-grained services through which specific
consumers or producers of metadata can directly integrate. For DataStage, by implementing
a data engine proxy, we are integrating through the Data Engine OMAS of Egeria. This allows
the integration to submit a large number of objects together in a single request rather than
the more fine-grained services of a repository connector (which would typically require many
fine-grained requests).

#### i. Set OMAS repository

```shell
curl -k -X POST "https://localhost:9443/open-metadata/admin-services/users/admin/servers/omas_server/local-repository/mode/local-graph-repository"
```

??? question "Detailed explanation"
    OMAS's need to be configured with a local repository, in this example we are configuring the
    server where we will enable the Data Engine OMAS with Egeria's built-in graph repository.

??? success "Response from OMAS repository configuration"
    ```json
    {"class":"VoidResponse","relatedHTTPCode":200}
    ```

#### ii. Configure the event bus

```shell
curl -k -X POST -H "Content-Type: application/json" \
  --data '{"producer":{"bootstrap.servers":"localhost:9092"},"consumer":{"bootstrap.servers":"localhost:9092"}}' \
  "https://localhost:9443/open-metadata/admin-services/users/admin/servers/omas_server/event-bus?connectorProvider=org.odpi.openmetadata.adapters.eventbus.topic.kafka.KafkaOpenMetadataTopicProvider"
```

??? question "Detailed explanation"
    The event bus is how Egeria coordinates communication amongst its various servers and
    repositories: for example, ensuring that any new type definitions are registered with each
    repository capable of handling them, notifying other repositories when the metadata in one
    repository changes, etc.

    The URL parameter `connectorProvider` defines the type of event bus to use (in
	this case Apache Kafka).

    The JSON payload gives details about how to connect to Apache Kafka, in this case assuming
    it is running on local machine (`localhost`) on its default port (`9092`).

??? success "Response from event bus configuration"
    ```json
    {"class":"VoidResponse","relatedHTTPCode":200}
    ```

#### iii. Enable OMAS's

```shell
curl -k -X POST "https://localhost:9443/open-metadata/admin-services/users/admin/servers/omas_server/access-services?serviceMode=ENABLED"
```

??? question "Detailed explanation"
    This call enables the access services that the `omas_server` should run: for simplicity here
    we are enabling all of the access services.

    This ensures that the Data Engine OMAS will be running and available for the DataStage
    connector to communicate any metadata through.

??? success "Response from enabling OMAS's"
    ```json
    {"class":"VoidResponse","relatedHTTPCode":200}
    ```

### b. Configure DataStage connector

#### i. Set repository

```shell
curl -k -X POST "https://localhost:9443/open-metadata/admin-services/users/admin/servers/datastage_proxy/local-repository/mode/in-memory-repository"
```

??? question "Detailed explanation"
    Just like any other server, we configure the persistence (if any) of the DataStage connector
    itself.

    In this example, we are configuring the `in-memory-repository`, so in fact
    will not have any persistence for the DataStage connector. For this connector, this is
    fine since our persistence is actually handled through the Data Engine OMAS rather
    than through this connector itself.

??? success "Response from connector repository configuration"
    ```json
    {"class":"VoidResponse","relatedHTTPCode":200}
    ```

#### ii. Configure connector

```shell
curl -k -X POST -H "Content-Type: application/json" \
  --data '{"class":"DataEngineProxyConfig","accessServiceRootURL":"https://localhost:9443","accessServiceServerName":"omas_server","dataEngineConnection":{"class":"Connection","connectorType":{"class":"ConnectorType","connectorProviderClassName":"org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.DataStageConnectorProvider"},"endpoint":{"class":"Endpoint","address":"infosvr:9446","protocol":"https"},"userId":"isadmin","clearPassword":"isadmin"},"pollIntervalInSeconds":60}' \
  "https://localhost:9443/open-metadata/admin-services/users/admin/servers/datastage_proxy/data-engine-proxy-service/configuration"
```

??? question "Detailed explanation"
    The JSON payload's contents define how this connector itself should be configured: specifically,
    which Java class should be used. Here we can see the payload refers to the
    `DataStageConnectorProvider`, which therefore tells the proxy to use this class
    -- specific to the DataStage connector -- in order to configure its connectivity to
    DataStage as a data engine.

??? success "Response from connector configuration"
    ```json
    {"class":"VoidResponse","relatedHTTPCode":200}
    ```

!!! attention "Be sure to replace hostname and credential information"
    If copy / pasting the command above, be sure to replace the hostname and credential information
    with the appropriate settings for your own environment before running it.

!!! attention "Required user permissions"
    To operate, the Information Server user credentials must have (at a minimum) the following roles:
    
    - Suite User
    - Information Governance Catalog User
    - Information Governance Catalog Glossary Author
    - Information Governance Catalog Asset Administrator (v11.7+)
    - Information Governance Catalog Information Asset Author (when `includeVirtualAssets` is set to true)

    ??? question "Detailed explanation"
        The first two are both read-only, non-administrative roles, while the last allows synchronization
        objects to be created to track the last synchronization point of the DataStage job information.

        For v11.7 and above, the Information Governance Catalog Asset Administrator role is necessary
        to automate the detection of lineage within IGC, prior to having a complete set of lineage for the DataStage
        connector itself to retrieve. (This is a necessary step to avoid potential race conditions between lineage being
        fully calculated within IGC and the DataStage connector polling for the lineage information.)

        The Information Governance Catalog Information Asset Author role is needed to be able to retrieve the
        full details of virtual assets.

## 6. Start the server instances

```shell
curl -k -X POST "https://localhost:9443/open-metadata/admin-services/users/admin/servers/omas_server/instance"
curl -k -X POST "https://localhost:9443/open-metadata/admin-services/users/admin/servers/datastage_proxy/instance"
```

??? question "Detailed explanation"
    Up to this point we have only configured the connector, but have not actually started it.

    These final API calls tell Egeria to start the servers needed: first for the OMAS's to which
    the connector will communicate, and second to start the connector itself.

??? success "Response from server instances startup"
    ```json
    {
        "class": "SuccessMessageResponse",
        "relatedHTTPCode": 200,
        "successMessage": "Thu Mar 11 12:54:46 GMT 2021 omas_server is running the following services: [Open Metadata Repository Services (OMRS), Connected Asset Services, Digital Service OMAS, Data Manager OMAS, Subject Area OMAS, Design Model OMAS, Glossary View OMAS, Asset Manager OMAS, Security Officer OMAS, IT Infrastructure OMAS, Data Science OMAS, Community Profile OMAS, Discovery Engine OMAS, Data Engine OMAS, Digital Architecture OMAS, Asset Owner OMAS, Stewardship Action OMAS, Governance Program OMAS, Asset Lineage OMAS, Analytics Modeling OMAS, Asset Consumer OMAS, Asset Catalog OMAS, DevOps OMAS, Software Developer OMAS, Project Management OMAS, Governance Engine OMAS, Data Privacy OMAS]"
    }
    ```

    It may take 10-15 seconds to complete, but the first response above indicates that the Egeria
    OMAS's are now running.

    ```json
    {
        "class": "SuccessMessageResponse",
        "relatedHTTPCode": 200,
        "successMessage": "Thu Mar 11 13:07:35 GMT 2021 datastage_proxy is running the following services: [Open Metadata Repository Services (OMRS), Data Engine Proxy Services]"
    }
    ```

    After another 10-15 seconds to complete, the example response above indicates that the
    DataStage connector instance is now running.

??? info "Other startup information of potential interest"
    Back in the console where the server chassis is running, you should see the audit log
    printing out a large amount of information as the startup is running. Most of this is
    related to the registration of type definition details with the repository.

    ```text linenums="1" hl_lines="39-48 53 64"
    Project Egeria - Open Metadata and Governance
       ____   __  ___ ___    ______   _____                                 ____   _         _     ___
      / __ \ /  |/  //   |  / ____/  / ___/ ___   ____ _   __ ___   ____   / _  \ / / __    / /  / _ /__   ____ _  _
     / / / // /|_/ // /| | / / __    \__ \ / _ \ / __/| | / // _ \ / __/  / /_/ // //   |  / _\ / /_ /  | /  _// || |
    / /_/ // /  / // ___ |/ /_/ /   ___/ //  __// /   | |/ //  __// /    /  __ // // /  \ / /_ /  _// / // /  / / / /
    \____//_/  /_//_/  |_|\____/   /____/ \___//_/    |___/ \___//_/    /_/    /_/ \__/\//___//_/   \__//_/  /_/ /_/
    
    :: Powered by Spring Boot (v2.5.0) ::
    
    2021-07-14 17:26:56.049  INFO 48498 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 9443 (https)
    2021-07-14 17:27:07.599  INFO 48498 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 9443 (https) with context path ''
    
    Wed Jul 14 17:26:58 BST 2021 No OMAG servers listed in startup configuration
    Wed Jul 14 17:27:07 BST 2021 OMAG server platform ready for more configuration
    Wed Jul 14 17:29:44 BST 2021 omas_server Startup OMRS-AUDIT-0064 The Open Metadata Repository Services (OMRS) has initialized the audit log for the Metadata Server called omas_server
    Wed Jul 14 17:29:44 BST 2021 omas_server Startup OMAG-ADMIN-0001 The omas_server server is configured with a max page size of 1000
    Wed Jul 14 17:29:44 BST 2021 omas_server Startup OMRS-AUDIT-0001 The Open Metadata Repository Services (OMRS) is initializing the subsystems to support a new server
    Wed Jul 14 17:29:44 BST 2021 omas_server Startup OMRS-AUDIT-0002 Enterprise access through the Enterprise Repository Services is initializing
    Wed Jul 14 17:29:44 BST 2021 omas_server Startup OMRS-AUDIT-0003 The local repository is initializing the metadata collection named omas_server with an id of 68616f45-6fb0-4e6a-8088-9de62d0d69ba
    Wed Jul 14 17:29:44 BST 2021 omas_server Startup OMRS-AUDIT-0029 The local repository outbound event manager is initializing
    Wed Jul 14 17:29:44 BST 2021 omas_server Startup OMRS-AUDIT-0030 Registering the Local Repository to Enterprise event consumer with the local repository outbound event manager
    Wed Jul 14 17:29:46 BST 2021 omas_server Information OMRS-GRAPH-REPOSITORY-0001 The OMRS Graph Repository has been created.
    Wed Jul 14 17:29:47 BST 2021 omas_server Information OMRS-AUDIT-0050 The Open Metadata Repository Services (OMRS) is about to process open metadata archive Open Metadata Types
    Wed Jul 14 17:29:47 BST 2021 omas_server Types OMRS-AUDIT-0301 The local server has added a new type called object with a unique identifier of 1c4b21f4-0b67-41a7-a6ed-2af185eb9b3b and a version number of 1 from Egeria (2.11)
    ...
    Wed Jul 14 17:29:53 BST 2021 omas_server Types OMRS-AUDIT-0303 The local server has updated an existing type called APIOperation with a unique identifier of f1c0af19-2729-4fac-996e-a7badff3c21c to version number of 2 using a patch from Egeria (2.11)
    Wed Jul 14 17:29:53 BST 2021 omas_server Information OMRS-AUDIT-0053 The Open Metadata Repository Services (OMRS) has processed 766 types and 0 instances from open metadata archive Open Metadata Types
    Wed Jul 14 17:29:53 BST 2021 omas_server Startup OMRS-AUDIT-0040 An enterprise OMRS connector has been created for the Repository REST Services
    Wed Jul 14 17:29:53 BST 2021 omas_server Startup OMRS-AUDIT-0041 The enterprise OMRS connector for the Repository REST Services has started
    Wed Jul 14 17:29:53 BST 2021 omas_server Startup OMRS-AUDIT-0007 The Open Metadata Repository Services (OMRS) has initialized
    Wed Jul 14 17:29:53 BST 2021 omas_server Startup OMRS-AUDIT-0031 The local repository outbound event manager is starting with 1 type definition event consumer(s) and 1 instance event consumer(s)
    Wed Jul 14 17:29:53 BST 2021 omas_server Startup OMRS-AUDIT-0032 The local repository outbound event manager is sending out the 766 type definition events that were generated and buffered during server initialization
    Wed Jul 14 17:29:53 BST 2021 omas_server Startup OMRS-AUDIT-0040 An enterprise OMRS connector has been created for the Connected Asset Services
    Wed Jul 14 17:29:53 BST 2021 omas_server Startup OMRS-AUDIT-0041 The enterprise OMRS connector for the Connected Asset Services has started
    Wed Jul 14 17:29:53 BST 2021 omas_server Startup OCF-METADATA-MANAGEMENT-0001 The Open Connector Framework (OCF) Metadata Management Service is initializing a new server instance
    Wed Jul 14 17:29:53 BST 2021 omas_server Startup OCF-METADATA-MANAGEMENT-0003 The Open Connector Framework (OCF) Metadata Management Service has initialized a new instance for server omas_server
    Wed Jul 14 17:29:53 BST 2021 omas_server Startup OMAG-ADMIN-0010 The Open Metadata Access Services (OMASs) are starting
    ...
    Wed Jul 14 17:29:57 BST 2021 omas_server Startup OMRS-AUDIT-0040 An enterprise OMRS connector has been created for the Data Engine OMAS
    Wed Jul 14 17:29:57 BST 2021 omas_server Startup OMRS-AUDIT-0041 The enterprise OMRS connector for the Data Engine OMAS has started
    Wed Jul 14 17:29:57 BST 2021 omas_server Startup OMAS-DATA-ENGINE-0001 The Data Engine Open Metadata Access Service (OMAS) is initializing a new server instance
    Wed Jul 14 17:29:57 BST 2021 omas_server Startup OCF-KAFKA-TOPIC-CONNECTOR-0001 Connecting to Apache Kafka Topic egeria.omag.server.omas_server.omas.dataengine.inTopic with a server identifier of 862ec1dd-97a0-4eed-936b-22a341cccbe8
    Wed Jul 14 17:29:57 BST 2021 omas_server Startup OCF-KAFKA-TOPIC-CONNECTOR-0015 The local server is attempting to connect to Kafka, attempt 1
    Wed Jul 14 17:29:57 BST 2021 omas_server Startup OCF-KAFKA-TOPIC-CONNECTOR-0003 10 properties passed to the Apache Kafka Consumer for topic egeria.omag.server.omas_server.omas.dataengine.inTopic
    Wed Jul 14 17:29:57 BST 2021 omas_server Startup OCF-KAFKA-TOPIC-CONNECTOR-0002 10 properties passed to the Apache Kafka Producer for topic egeria.omag.server.omas_server.omas.dataengine.inTopic
    Wed Jul 14 17:29:57 BST 2021 omas_server Startup OMAS-DATA-ENGINE-0002 The Data Engine Open Metadata Access Service (OMAS) has initialized a new instance for server omas_server
    Wed Jul 14 17:29:57 BST 2021 omas_server Startup OCF-KAFKA-TOPIC-CONNECTOR-0010 The Apache Kafka producer for topic egeria.omag.server.omas_server.omas.dataengine.inTopic is starting up with 0 buffered messages
    Wed Jul 14 17:29:57 BST 2021 omas_server Startup OMRS-AUDIT-0015 The listener thread for an OMRS Topic Connector for topic egeria.omag.server.omas_server.omas.dataengine.inTopic has started
    ...
    Wed Jul 14 17:29:58 BST 2021 omas_server Startup OMAG-ADMIN-0012 26 out of 26 configured Open Metadata Access Services (OMASs) have started.
    Wed Jul 14 17:29:58 BST 2021 omas_server Startup OMRS-AUDIT-0019 An OMRS Topic Connector has registered with an event bus connector for topic omas_server.openmetadata.repositoryservices.enterprise.omas_server.OMRSTopic
    Wed Jul 14 17:29:58 BST 2021 omas_server Startup OMRS-AUDIT-0020 An OMRS Topic Connector is ready to send and receive events on topic omas_server.openmetadata.repositoryservices.enterprise.omas_server.OMRSTopic
    Wed Jul 14 17:29:58 BST 2021 omas_server Startup OMAG-ADMIN-0004 The omas_server server has successfully completed start up.  The following services are running: [Open Metadata Repository Services (OMRS), Connected Asset Services, Digital Service OMAS, Data Manager OMAS, Subject Area OMAS, Design Model OMAS, Glossary View OMAS, Asset Manager OMAS, Security Officer OMAS, IT Infrastructure OMAS, Data Science OMAS, Community Profile OMAS, Discovery Engine OMAS, Data Engine OMAS, Digital Architecture OMAS, Asset Owner OMAS, Stewardship Action OMAS, Governance Program OMAS, Asset Lineage OMAS, Analytics Modeling OMAS, Asset Consumer OMAS, Asset Catalog OMAS, DevOps OMAS, Software Developer OMAS, Project Management OMAS, Governance Engine OMAS, Data Privacy OMAS, Security Manager OMAS]
    Wed Jul 14 17:29:58 BST 2021 omas_server Startup OMRS-AUDIT-0015 The listener thread for an OMRS Topic Connector for topic omas_server.openmetadata.repositoryservices.enterprise.omas_server.OMRSTopic has started
    Wed Jul 14 17:38:29 BST 2021 datastage_proxy Startup OMRS-AUDIT-0064 The Open Metadata Repository Services (OMRS) has initialized the audit log for the Data Engine Proxy called datastage_proxy
    Wed Jul 14 17:38:29 BST 2021 datastage_proxy Startup OMAG-ADMIN-0001 The datastage_proxy server is configured with a max page size of 1000
    Wed Jul 14 17:38:29 BST 2021 datastage_proxy Startup OMRS-AUDIT-0001 The Open Metadata Repository Services (OMRS) is initializing the subsystems to support a new server
    Wed Jul 14 17:38:29 BST 2021 datastage_proxy Startup OMRS-AUDIT-0007 The Open Metadata Repository Services (OMRS) has initialized
    Wed Jul 14 17:38:29 BST 2021 datastage_proxy Startup OMAG-ADMIN-0100 The governance services subsystem for the Data Engine Proxy called datastage_proxy is about to start
    Wed Jul 14 17:38:29 BST 2021 datastage_proxy Information DATA-ENGINE-PROXY-0001 The Data Engine Proxy is initializing a new server instance
    Wed Jul 14 17:38:32 BST 2021 datastage_proxy Information DATA-ENGINE-PROXY-0006 The Data Engine Proxy is initializing polling for changes
    Wed Jul 14 17:38:33 BST 2021 datastage_proxy Information DATA-ENGINE-PROXY-0005 The Data Engine Proxy has initialized a new instance for server org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.DataStageConnectorProvider
    Wed Jul 14 17:38:33 BST 2021 datastage_proxy Startup OMAG-ADMIN-0101 The governance services subsystem for the Data Engine Proxy called datastage_proxy has completed start up
    Wed Jul 14 17:38:33 BST 2021 datastage_proxy Startup OMAG-ADMIN-0004 The datastage_proxy server has successfully completed start up.  The following services are running: [Open Metadata Repository Services (OMRS), Data Engine Proxy Services]
    ```

    These lines indicate that the Data Engine OMAS has been configured and started up, and the
    DataStage proxy has also been started.

## Connector options

There are currently five configuration options for the connector itself:

| Option | Description |
|---|---|
| `pageSize` | An integer giving the number of results to include in each page of processing. |
| `includeVirtualAssets` | A boolean that indicates whether to include the creation of schemas for virtual assets (when true) or not (when false). |
| `createDataStoreSchemas` | A boolean that indicates whether to include the creation of data store-level schemas (when true) or not (when false). When the DataStage connector is used alone in a cohort, without an IGC proxy also running in the cohort, this should be set to true to ensure that the data stores used as sources or targets by DataStage exist in lineage. If an IGC proxy is also being used in the cohort, this should be left at the default value (false) to ensure that the IGC proxy remains the home metadata collection of data store entities. |
| `limitToProjects` | A list of projects to which any lineage information should be limited. When not specified, all projects will be included. When specified, only jobs within those projects will be included. |
| `limitToLineageEnabledJobs` | A boolean that indicates if the connector should only process lineage-enabled jobs. If this is set to true then only jobs having `include_for_lineage` set to true will be processed for lineage information. |

!!! example "Example configuration"
    ```json linenums="1" hl_lines="3-4 11-17 26"
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
                "address": "infosvr:9446",
                "protocol": "https"
            },
            "userId": "isadmin",
            "clearPassword": "isadmin",
            "configurationProperties": {
                "pageSize": 100,
                "includeVirtualAssets": true,
                "createDataStoreSchemas": false,
                "limitToProjects": [],
                "limitToLineageEnabledJobs": false
            }
        },
        "pollIntervalInSeconds": 60
    }
    ```

    Details about how to connect to both the Data Engine OMAS and the IBM Information Server
	environment must also be provided in the connection's configuration:
    
    - the URL and name of the OMAS server running within either this or another Egeria
      OMAG Server Platform (server chassis)
    - the endpoint details covering the hostname and port of the Information Server environment's
      domain (services) tier, and the username and password through which we can access its REST APIs

    For the additional options, if no values are provided in the configuration of the connector
    they will automatically default to the values given above.

--8<-- "snippets/abbr.md"
