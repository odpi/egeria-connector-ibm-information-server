<!-- SPDX-License-Identifier: CC-BY-4.0 -->
<!-- Copyright Contributors to the ODPi Egeria project. -->

# Release 1.1

Release 1.1 focuses on establishing a baseline connector that can be used to
integrate metadata from existing IBM Information Server environments.

Below are the highlights:

- Exposure of all basic read and find operations defined as part of the
    [Open Metadata Repository Services (OMRS)](https://egeria.odpi.org/open-metadata-implementation/repository-services/),
    allowing IBM Information Governance Catalog (IGC) to participate in a
    broader metadata cohort and federation through Egeria's Enterprise Connector.

- An initial set of [mapped metadata object types](../docs/mappings)
    that include those most commonly used within existing IGC environments.

- An Event Mapper to automatically publish changes to metadata within an IGC
    environment to the rest of the Egeria metadata cohort.

- Tech Preview of integration of IBM DataStage's design-time lineage information to Egeria through
    a [Data Engine Proxy Services](https://egeria.odpi.org/open-metadata-implementation/governance-servers/data-engine-proxy-services/)
    connector.

- Built on release 1.1 of Egeria, and compatible with IBM Information Server
    version 11.5 onwards.

----
License: [CC BY 4.0](https://creativecommons.org/licenses/by/4.0/),
Copyright Contributors to the ODPi Egeria project.