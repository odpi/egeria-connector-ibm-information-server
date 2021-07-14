<!-- SPDX-License-Identifier: CC-BY-4.0 -->
<!-- Copyright Contributors to the ODPi Egeria project. -->

# IGC Adapter

The IGC proxy connects an IBM Information Governance Catalog environment to one or more Egeria
cohorts.

!!! tip "Ensure you have already completed the [Setup](../common/) steps before proceeding."

## 5. Configure the connector

### a. Configure the event bus

```shell
curl -k -X POST -H "Content-Type: application/json" \
  --data '{"producer":{"bootstrap.servers":"localhost:9092"},"consumer":{"bootstrap.servers":"localhost:9092"}}' \
  "https://localhost:9443/open-metadata/admin-services/users/admin/servers/igcproxy/event-bus?connectorProvider=org.odpi.openmetadata.adapters.eventbus.topic.kafka.KafkaOpenMetadataTopicProvider"
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

### b. Configure the cohort

```shell
curl -k -X POST \
  "https://localhost:9443/open-metadata/admin-services/users/admin/servers/igcproxy/cohorts/mycohort"
```

??? question "Detailed explanation"
    The cohort is a network of interacting metadata servers, which could be some subset of the
    overall Egeria landscape.

    We only need to supply a name for the cohort, which forms part of the URL itself: in this
    example using `mycohort`.

??? success "Response from cohort configuration"
    ```json
    {"class":"VoidResponse","relatedHTTPCode":200}
    ```

### c. Configure IGC connector

```shell
curl -k -X POST -H "Content-Type: application/json" \
  --data '{"class":"Connection","connectorType":{"class":"ConnectorType","connectorProviderClassName":"org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSRepositoryConnectorProvider"},"endpoint":{"class":"Endpoint","address":"infosvr:9446","protocol":"https"},"userId":"isadmin","clearPassword":"isadmin","configurationProperties":{"defaultZones":["default"]}}' \
  "https://localhost:9443/open-metadata/admin-services/users/admin/servers/igcproxy/local-repository/mode/repository-proxy/connection"
```

??? question "Detailed explanation"
    This final call to the API configures the behavior of the IGC connector itself.

    The URL to which we post indicates that we will use the Egeria server chassis's built-in
    repository proxy capability to access the IGC repository connector.

    The JSON payload's contents define how this connector itself should be configured: specifically,
    which Java class should be used for the repository connection. Here we can see the payload
    refers to the `IGCOMRSRepositoryConnectorProvider`, which therefore tells the
    proxy to use this class -- specific to the IGC repository connector -- in order to configure
    its repository connection.

    The details embedded within the `endpoint` key indicate how the connector itself should connect
    to IGC: the address (hostname and port) of the IGC instance and login credentials (username and
    password) for the account to use to access IGC via its API.

??? success "Response from connector configuration"
    ```json
    {"class":"VoidResponse","relatedHTTPCode":200}
    ```

!!! attention "Be sure to replace hostname and credential information"
    If copy / pasting the command above, be sure to replace the hostname and credential information
    with the appropriate settings for your own environment before running it.

## 6. Start the connector instance

```shell
curl -k -X POST "https://localhost:9443/open-metadata/admin-services/users/admin/servers/igcproxy/instance"
```

??? question "Detailed explanation"
    Up to this point we have only configured the connector, but have not actually started it.

    This final API call tells Egeria to start the connector, based on the configuration the previous
    API calls defined.

??? success "Response from connector instance startup"
    ```json
    {
        "class": "SuccessMessageResponse",
        "relatedHTTPCode": 200,
        "successMessage": "Fri Feb 12 20:34:16 GMT 2021 igcproxy is running the following services: [Open Metadata Repository Services (OMRS)]"
    }
    ```

    It may take 10-15 seconds to complete, but the example response above indicates that the
    connector instance is now running.

??? info "Other startup information of potential interest"
    Back in the console where the server chassis is running, you should see the audit log
    printing out a large amount of information as the startup is running. Most of this is
    related to the registration of type definition details with the repository.

    ```text hl_lines="53"
    Project Egeria - Open Metadata and Governance
       ____   __  ___ ___    ______   _____                                 ____   _         _     ___
      / __ \ /  |/  //   |  / ____/  / ___/ ___   ____ _   __ ___   ____   / _  \ / / __    / /  / _ /__   ____ _  _
     / / / // /|_/ // /| | / / __    \__ \ / _ \ / __/| | / // _ \ / __/  / /_/ // //   |  / _\ / /_ /  | /  _// || |
    / /_/ // /  / // ___ |/ /_/ /   ___/ //  __// /   | |/ //  __// /    /  __ // // /  \ / /_ /  _// / // /  / / / /
    \____//_/  /_//_/  |_|\____/   /____/ \___//_/    |___/ \___//_/    /_/    /_/ \__/\//___//_/   \__//_/  /_/ /_/
    
    :: Powered by Spring Boot (v2.5.0) ::
    
    2021-07-14 11:20:03.855  INFO 1 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 9443 (https)
    2021-07-14 11:20:30.683  INFO 1 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 9443 (https) with context path ''
    
    Wed Jul 14 11:20:13 GMT 2021 No OMAG servers listed in startup configuration
    Wed Jul 14 11:20:30 GMT 2021 OMAG server platform ready for more configuration
    Wed Jul 14 11:25:06 GMT 2021 igcproxy Startup OMRS-AUDIT-0064 The Open Metadata Repository Services (OMRS) has initialized the audit log for the Repository Proxy called ibmigc
    Wed Jul 14 11:25:06 GMT 2021 igcproxy Startup OMAG-ADMIN-0001 The ibmigc server is configured with a max page size of 1000
    Wed Jul 14 11:25:06 GMT 2021 igcproxy Startup OMRS-AUDIT-0001 The Open Metadata Repository Services (OMRS) is initializing the subsystems to support a new server
    Wed Jul 14 11:25:06 GMT 2021 igcproxy Startup OMRS-AUDIT-0003 The local repository is initializing the metadata collection named ibmigc with an id of 90a211ae-c052-412f-83c5-53a20221e7b7
    Wed Jul 14 11:25:06 GMT 2021 igcproxy Startup OMRS-AUDIT-0029 The local repository outbound event manager is initializing
    Wed Jul 14 11:25:06 GMT 2021 igcproxy Information OMRS-IGC-REPOSITORY-0002 The IBM Information Governance Catalog proxy is attempting to connect to IGC at https://infosvr:9446
    Wed Jul 14 11:25:08 GMT 2021 igcproxy Information OMRS-IGC-REPOSITORY-0003 The IBM Information Governance Catalog proxy has successfully connected to IGC at https://infosvr:9446
    Wed Jul 14 11:25:08 GMT 2021 igcproxy Information OMRS-IGC-REPOSITORY-0001 The IBM Information Governance Catalog proxy is starting a new server instance
    Wed Jul 14 11:25:08 GMT 2021 igcproxy Information OMRS-IGC-REPOSITORY-0004 The IBM Information Governance Catalog proxy has started a new instance for server ibmigc, detected IGC version v11.7.1.0
    Wed Jul 14 11:25:08 GMT 2021 igcproxy Startup OMRS-AUDIT-0024 The local repositorys event mapper connector {0} is ready to send and receive events
    Wed Jul 14 11:25:08 GMT 2021 igcproxy Information OMRS-IGC-REPOSITORY-0009 The IBM Information Governance Catalog event mapper is starting up
    Wed Jul 14 11:25:12 GMT 2021 igcproxy Information OMRS-IGC-REPOSITORY-0010 The IBM Information Governance Catalog event mapper is running
    Wed Jul 14 11:25:12 GMT 2021 igcproxy Information OMRS-AUDIT-0050 The Open Metadata Repository Services (OMRS) is about to process open metadata archive Open Metadata Types
    Wed Jul 14 11:25:12 GMT 2021 igcproxy Types OMRS-AUDIT-0301 The local server has added a new type called object with a unique identifier of 1c4b21f4-0b67-41a7-a6ed-2af185eb9b3b and a version number of 1 from Egeria (2.11)
    ...
    Wed Jul 14 11:25:17 GMT 2021 igcproxy Types OMRS-AUDIT-0303 The local server has updated an existing type called TermISATypeOFRelationship with a unique identifier of d5d588c3-46c9-420c-adff-6031802a7e51 to version number of 2 using a patch from Egeria (2.11)
    Wed Jul 14 11:25:17 GMT 2021 igcproxy Information OMRS-AUDIT-0053 The Open Metadata Repository Services (OMRS) has processed 766 types and 0 instances from open metadata archive Open Metadata Types
    Wed Jul 14 11:25:17 GMT 2021 igcproxy Startup OMRS-AUDIT-0004 Connecting to the metadata highway
    Wed Jul 14 11:25:17 GMT 2021 igcproxy Startup OMRS-AUDIT-0005 Connecting to open metadata repository cohort coco
    Wed Jul 14 11:25:17 GMT 2021 igcproxy Startup OMRS-AUDIT-0029 The coco cohort inbound event manager is initializing
    Wed Jul 14 11:25:17 GMT 2021 igcproxy Startup OMRS-AUDIT-0030 Registering the coco event consumer with the local repository outbound event manager
    Wed Jul 14 11:25:17 GMT 2021 igcproxy Startup OMRS-AUDIT-0030 Registering the Local Repository Content (TypeDef) Manager event consumer with the coco cohort inbound event manager
    Wed Jul 14 11:25:17 GMT 2021 igcproxy Startup OMRS-AUDIT-0030 Registering the Local Repository Inbound Instance Events event consumer with the coco cohort inbound event manager
    Wed Jul 14 11:25:17 GMT 2021 igcproxy Startup OMRS-AUDIT-0007 The Open Metadata Repository Services (OMRS) has initialized
    Wed Jul 14 11:25:17 GMT 2021 igcproxy Startup OMRS-AUDIT-0031 The coco cohort inbound event manager is starting with 1 type definition event consumer(s) and 1 instance event consumer(s)
    Wed Jul 14 11:25:17 GMT 2021 igcproxy Startup OMRS-AUDIT-0026 Initializing listener for cohort coco
    Wed Jul 14 11:25:17 GMT 2021 igcproxy Startup OMRS-AUDIT-0019 An OMRS Topic Connector has registered with an event bus connector for topic egeria.openmetadata.repositoryservices.cohort.coco.OMRSTopic
    Wed Jul 14 11:25:17 GMT 2021 igcproxy Startup OCF-KAFKA-TOPIC-CONNECTOR-0001 Connecting to Apache Kafka Topic egeria.openmetadata.repositoryservices.cohort.coco.OMRSTopic with a server identifier of 69824723-a395-4c1c-b61a-1b6a489e0ef9
    Wed Jul 14 11:25:17 GMT 2021 igcproxy Startup OCF-KAFKA-TOPIC-CONNECTOR-0015 The local server is attempting to connect to Kafka, attempt 1
    Wed Jul 14 11:25:17 GMT 2021 igcproxy Startup OCF-KAFKA-TOPIC-CONNECTOR-0003 10 properties passed to the Apache Kafka Consumer for topic egeria.openmetadata.repositoryservices.cohort.coco.OMRSTopic
    Wed Jul 14 11:25:17 GMT 2021 igcproxy Startup OCF-KAFKA-TOPIC-CONNECTOR-0002 10 properties passed to the Apache Kafka Producer for topic egeria.openmetadata.repositoryservices.cohort.coco.OMRSTopic
    Wed Jul 14 11:25:17 GMT 2021 igcproxy Startup OMRS-AUDIT-0020 An OMRS Topic Connector is ready to send and receive events on topic egeria.openmetadata.repositoryservices.cohort.coco.OMRSTopic
    Wed Jul 14 11:25:17 GMT 2021 igcproxy Startup OMRS-AUDIT-0015 The listener thread for an OMRS Topic Connector for topic egeria.openmetadata.repositoryservices.cohort.coco.OMRSTopic has started
    Wed Jul 14 11:25:17 GMT 2021 igcproxy Startup OCF-KAFKA-TOPIC-CONNECTOR-0010 The Apache Kafka producer for topic egeria.openmetadata.repositoryservices.cohort.coco.OMRSTopic is starting up with 0 buffered messages
    Wed Jul 14 11:25:17 GMT 2021 igcproxy Cohort OMRS-AUDIT-0060 Registering with open metadata repository cohort coco using metadata collection id 90a211ae-c052-412f-83c5-53a20221e7b7
    Wed Jul 14 11:25:17 GMT 2021 igcproxy Cohort OMRS-AUDIT-0062 Requesting registration information from other members of the open metadata repository cohort coco
    Wed Jul 14 11:25:17 GMT 2021 igcproxy Startup OMRS-AUDIT-0031 The local repository outbound event manager is starting with 1 type definition event consumer(s) and 1 instance event consumer(s)
    Wed Jul 14 11:25:17 GMT 2021 igcproxy Startup OMRS-AUDIT-0032 The local repository outbound event manager is sending out the 114 type definition events that were generated and buffered during server initialization
    Wed Jul 14 11:25:18 GMT 2021 igcproxy Startup OMAG-ADMIN-0004 The ibmigc server has successfully completed start up.  The following services are running: [Open Metadata Repository Services (OMRS)]
    ```

    This final line indicates that the server has now started up and completed its registration with the cohort.

## Connector options

There are currently two configuration options for the connector itself:

| Option | Description |
|---|---|
| `defaultZones` | a list of strings defining the default zones that should be applied to all assets homed in this repository proxy's metadata collection |
| `ignoreUnmappedInstances` | a boolean indicating whether to ignore instances that are not mapped (will log a warning, but not throw any exception when set to `true`) or to throw an exception if an unmapped instance is retrieved (when set to `false`) |

!!! example "Example configuration"
    ```json linenums="1" hl_lines="15-16"
    {
      "class": "Connection",
      "connectorType": {
        "class": "ConnectorType",
        "connectorProviderClassName": "org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSRepositoryConnectorProvider"
      },
      "endpoint": {
        "class": "Endpoint",
        "address": "infosvr:9446",
        "protocol": "https"
      },
      "userId": "isadmin",
      "clearPassword": "isadmin",
      "configurationProperties": {
        "defaultZones": ["default"],
        "ignoreUnmappedInstances": false
      }
    }
    ```

    If the options are not provided in the configuration of the connector, they will automatically
    default to the values given above.

--8<-- "snippets/abbr.md"
