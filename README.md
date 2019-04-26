<!-- SPDX-License-Identifier: CC-BY-4.0 -->
<!-- Copyright Contributors to the ODPi Egeria project. -->

# IBM InfoSphere Information Governance Catalog Repository Connector

[IBM InfoSphere Information Governance Catalog](https://www.ibm.com/marketplace/information-governance-catalog) is a
commercially-available metadata repository from IBM, commonly referred to simply as "IGC". While the most recent versions
of the software provide their own connectivity to OMRS cohorts, an example implementation of such connectivity is also
provided here both for reference purposes and also to provide an integration point to older versions of the software
(from v11.5.0.1 onwards).

Note that currently the implemented connector is read-only: it only implements those methods necessary to search, retrieve,
and communicate metadata from IGC out into the cohort -- it does *not* currently implement the ability to update IGC based
on events received from other members of the cohort.

## How it works

The IBM IGC Repository Connector works through a combination of the following:

- IBM IGC's REST API, itself abstracted through the [IGC REST Client Library](ibm-igc-rest-client-library)
- IBM InfoSphere Information Server's embedded Apache Kafka event bus
    - specifically the `InfosphereEvents` topic (hence the need to enable events in the setup)
- Some [IGC extensions](docs/ibm-igc-extensions.md) that implement specific additional functionality

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
distribution/target/egeria-connector-ibm-igc-package-VERSION-dist.jar
```

Simply copy this file to a location where it can be run alongside the OMAG Server
Platform from the Egeria core (in the example below, the file would be copied to
`/lib/egeria-connector-ibm-igc-package-VERSION-dist.jar`), and you can startup
the OMAG Server Platform with this connector ready-to-be-configured by running:

```bash
$ java -Dloader.path=/lib -jar server-chassis-spring-VERSION.jar
```

(This command will startup the OMAG Server Platform, including all libraries
in the `/lib` directory as part of the classpath of the OMAG Server Platform.)


### Configure this Egeria connector

You will need to configure the OMAG Server Platform as follows (order is important) to make use of this Egeria connector.
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
        "ibm.igc.services.host": "{{igc_host}}",
        "ibm.igc.services.port": "{{igc_port}}",
        "ibm.igc.username": "{{igc_user}}",
        "ibm.igc.password": "{{igc_password}}"
    }
    ```

    to:

    ```
    {{baseURL}}/open-metadata/admin-services/users/{{user}}/servers/{{server}}/local-repository/mode/repository-proxy/details?connectorProvider=org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSRepositoryConnectorProvider
    ```

    The payload should include the hostname and port of your IGC environment's domain (services) tier,
    and a `username` and `password` through which the REST API can be accessed.

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
