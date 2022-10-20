<!-- SPDX-License-Identifier: CC-BY-4.0 -->
<!-- Copyright Contributors to the ODPi Egeria project. -->

[![javadoc](https://javadoc.io/badge2/org.odpi.egeria/egeria-connector-ibm-igc-adapter/javadoc.svg)](https://javadoc.io/doc/org.odpi.egeria/egeria-connector-ibm-igc-adapter) [![Maven Central](https://img.shields.io/maven-central/v/org.odpi.egeria/egeria-connector-ibm-igc-adapter)](https://mvnrepository.com/artifact/org.odpi.egeria/egeria-connector-ibm-igc-adapter)

# IBM InfoSphere Information Governance Catalog connector

[IBM InfoSphere Information Governance Catalog](https://www.ibm.com/marketplace/information-governance-catalog) is the
metadata repository module within the suite, commonly referred to simply as "IGC". While the most recent versions of
the software provide their own connectivity to OMRS cohorts, an example implementation of such connectivity is also
provided here both for reference purposes and also to provide an integration point to older versions of the software
(from v11.5.0.1 onwards).

Note that this open connector is read-only: it only implements those methods necessary to search, retrieve, and
communicate metadata from IGC out into the cohort -- it does *not* implement the ability to update IGC based on
events received from other members of the cohort.

Furthermore, [only a subset of the overall Open Metadata Types are currently implemented](../docs/mappings/README.md).

For a write-supporting connector you should use the connector that is provided as part of IBM Information Server
itself, though be aware that this is only available in recent versions of v11.7.

## How it works

The IBM IGC Repository Connector works through a combination of the following:

- IBM IGC's REST API, itself abstracted through the [IGC REST Client Library](../igc-clientlibrary)
- IBM InfoSphere Information Server's embedded Apache Kafka event bus
    - specifically the `InfosphereEvents` topic (hence the need to enable events in the setup)
- Some [IGC extensions](../docs/ibm-igc-extensions.md) that implement specific additional functionality
- Egeria's Metadata Repository Proxy Services

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
    $ curl -k -X POST -H "Content-Type: application/json" --data '{"producer":{"bootstrap.servers":"localhost:9092"},"consumer":{"bootstrap.servers":"localhost:9092"}}' "https://localhost:9443/open-metadata/admin-services/users/admin/servers/igcproxy/event-bus?connectorProvider=org.odpi.openmetadata.adapters.eventbus.topic.kafka.KafkaOpenMetadataTopicProvider&topicURLRoot=OMRSTopic"
    $ curl -k -X POST "https://localhost:9443/open-metadata/admin-services/users/admin/servers/igcproxy/cohorts/mycohort"
    $ curl -k -X POST -H "Content-Type: application/json" --data '{"class":"Connection","connectorType":{"class":"ConnectorType","connectorProviderClassName":"org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSRepositoryConnectorProvider"},"endpoint":{"class":"Endpoint","address":"infosvr:9446","protocol":"https"},"userId":"isadmin","clearPassword":"isadmin","configurationProperties":{"defaultZones":["default"]}}' "https://localhost:9443/open-metadata/admin-services/users/admin/servers/igcproxy/local-repository/mode/repository-proxy/connection"
    $ curl -k -X POST "https://localhost:9443/open-metadata/admin-services/users/admin/servers/igcproxy/instance"
    ```

### Detailed steps for configuring the IGC connector

You will need to configure the OMAG Server Platform as follows (order is important) to make use of the IGC connector.
For example payloads and endpoints, see the [Postman samples](samples).

1. Configure your event bus for Egeria, by POSTing a payload like the following (replace the `localhost:9092` with the
   hostname and port number where your Kafka bus is running, and assuming you are running the OMAG Server Platform
   locally at its default port of `9443`):

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
    POST https://localhost:9443/open-metadata/admin-services/users/admin/servers/igcproxy/event-bus?connectorProvider=org.odpi.openmetadata.adapters.eventbus.topic.kafka.KafkaOpenMetadataTopicProvider&topicURLRoot=OMRSTopic
    ```

1. Configure the cohort, by POSTing something like the following:

    ```
    POST https://localhost:9443/open-metadata/admin-services/users/admin/servers/igcproxy/cohorts/mycohort
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
            "defaultZones": [ "default" ],
            "ignoreUnmappedInstances": true
        }
    }
    ```

   to:

    ```
    POST https://localhost:9443/open-metadata/admin-services/users/admin/servers/igcproxy/local-repository/mode/repository-proxy/connection
    ```

   To operate, the IGC user credentials must have (at a minimum) the following roles within Information Server:
   `Suite User` and `Information Governance Catalog User`. (These are both read-only, non-administrative roles.)

   You can optionally also provide:

    - a list of zone names that will be used as default zones for all Assets retrieved from IGC through the proxy (in
      the example above this is a single zone called `default`).
    - override the default behavior where if an unmapped instance in IGC is retrieved a RepositoryErrorException will
      be thrown, to instead simply log a warning and ignore the unmapped instance (not return it or throw an
      exception) by setting `ignoreUmappedInstances` to `true`.

   Note that you also need to provide the `connectorProvider` parameter, set to the name of the IGC
   connectorProvider class (value as given above).

1. (Optional and _experimental_) Configure the event mapper for IGC, by POSTing something like the following:

   The _experimental_ event mapper consumes events that are generated by IGC. By default this is disabled,
   and it should be treated as experimental even when enabled. When enabled, the IGC user credentials provided for the
   connector must have administrative authority to be able to automatically create the objects used by this
   experimental capability.

   Before enabling it, you will need to first enable event notification in your IGC environment:

    1. Navigate to "Administration": !["Administration"](docs/ibm-igc-setup1.png)
    1. Navigate to "Event Notification" within the "Setup" heading: !["Event Notification"](docs/ibm-igc-setup2.png)
    1. Toggle "Enable" and then "Save and Close": !["Enable" and "Save and Close"](docs/ibm-igc-setup3.png)

   There should not be any need to restart the environment after enabling the event notification.

    ```
    POST https://localhost:9443/open-metadata/admin-services/users/admin/servers/igcproxy/local-repository/event-mapper-details?connectorProvider=org.odpi.egeria.connectors.ibm.igc.eventmapper.IGCOMRSRepositoryEventMapperProvider&eventSource=my.igc.services.host.com:59092
    ```

   The hostname provided at the end (`my.igc.services.host.com`) should be replaced with the host on which your
   IGC-embedded kafka bus is running, and include the appropriate port number for connecting to that bus.
   (For v11.5 this is your domain (services) tier and port `59092`, whereas in the latest versions of 11.7 it may be
   running on your Unified Governance / Enterprise Search tier, on port `9092`. In both cases the port will need to be
   network-accessible by the host where you are running Egeria itself for any events to be picked up by Egeria.)

1. The connector (and optionally the event mapper) should now be configured, and you should now be able
   to start the instance by POSTing something like the following:

   ```
   POST https://localhost:9443/open-metadata/admin-services/users/admin/servers/igcproxy/instance
   ```

After following these instructions, your IGC instance will be participating in the Egeria cohort. For those objects
supported by the connector, any new instances or updates to existing instances should result in that metadata
automatically being communicated out to the rest of the cohort.

## Loading samples

If you have a completely empty IGC environment, you may want to load some sample metadata to further explore.

Samples are provided under [egeria/open-metadata-resources/open-metadata-deployment/sample-data/](https://github.com/odpi/egeria/tree/main/open-metadata-resources/open-metadata-deployment/sample-data).

For example, there you will find a [Coco Pharmaceuticals](https://github.com/odpi/egeria/tree/main/open-metadata-resources/open-metadata-deployment/sample-data/coco-pharmaceuticals)
set of samples. These samples are provided as a set of content that can be automatically loaded to IGC using Ansible,
and a number of publicly-available Ansible roles. (See instructions via the link itself.)

----
License: [CC BY 4.0](https://creativecommons.org/licenses/by/4.0/),
Copyright Contributors to the ODPi Egeria project.
