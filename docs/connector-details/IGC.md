<!-- SPDX-License-Identifier: CC-BY-4.0 -->
<!-- Copyright Contributors to the ODPi Egeria project. -->

# Information Governance Catalog connector details

## Capabilities

- Read-only Open Metadata Repository Connector
- Conformant to the mandatory CTS profile (metadata sharing), and some additional search operations
- Basic event mapper for notifying the cohort about metadata within the repository (only as create events)
- [Common metadata entities](../mappings/README.md#Entities) are pre-mapped:
    - Database information (host, database, schema, table, column)
    - File information (host, folder, file, record, field)
    - Glossary information (category, term)
- [Common relationships](../mappings/README.md#Relationships) are pre-mapped:
    - Technical metadata containment (for the entities above)
    - Technical / business metadata relationships (ie. semantic assignment)
- Some [classifications are pre-defined](../mappings/README.md#Classifications), through specific code and implementation choice:
    - eg. confidentiality

## Limitations

- Does not handle any create, update or delete operations from the rest of the cohort
- Only `NEW_*` (create) events can be propagated from the repository: no updates or deletes
- Duplicate `NEW_*` (create) events will be created, particularly for relationships, any time
    any information on an entity changes. (This is likely to result in _many_ duplicate create
    events over time.)

----
License: [CC BY 4.0](https://creativecommons.org/licenses/by/4.0/),
Copyright Contributors to the ODPi Egeria project.
