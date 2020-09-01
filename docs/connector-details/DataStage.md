<!-- SPDX-License-Identifier: CC-BY-4.0 -->
<!-- Copyright Contributors to the ODPi Egeria project. -->

# DataStage connector details

## Capabilities

- Data Engine Proxy Connector
- Poll-based change retrieval (no events)
- Integrates column-level lineage information from IBM Information Server
- Creates a process hierarchy in Egeria with the following levels:
    - each sequence is a process
    - each job is a process, with each sequence it appears in as a parent process
    - each stage is a process, with the job it is contained in as a parent process
- Able to configure:
    - inclusion / exclusion of virtual assets
    - maximum window of changes to include in each poll

## Limitations

- Unable to detect or communicate the deletion of jobs
- Some specific elements and scenarios may not _yet_ be handled, for example:
    - shared containers
    - nested sequences
    - routines
    - custom operators

----
License: [CC BY 4.0](https://creativecommons.org/licenses/by/4.0/),
Copyright Contributors to the ODPi Egeria project.
