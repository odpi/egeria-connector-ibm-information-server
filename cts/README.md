<!-- SPDX-License-Identifier: CC-BY-4.0 -->
<!-- Copyright Contributors to the ODPi Egeria project. -->

# Conformance Test Suite

This directory contains information regarding the Conformance Test Suite (CTS) and the IBM Information Server
connectors.

## Charts

The `charts` sub-directory contains a Helm chart to automate the execution of the CTS against the IBM Information Server
connectors, to produce a set of repeatable CTS results.

## Results

The `results` sub-directory contains results of running the CTS against the IBM Information Governance Catalog (IGC)
connector. For each release, you will find the following details:

- `openmetadata.conformance.testlab.results` - the detailed results, as produced by the CTS workbench itself
- The OMAG server configurations:
    - `omag.server.ibmigc.config` - the configuration of the IGC connector (proxy)
    - `omag.server.cts.config` - the configuration of the CTS workbench
- The cohort registrations:
    - `coco.cohort.ibmigc.local` - the local IGC connector (proxy) cohort registration information
    - `coco.cohort.ibmigc.remote` - the cohort members considered remote from the IGC connector (proxy)'s perspective
    - `coco.cohort.cts.local` - the local CTS Workbench cohort registration
    - `coco.cohort.cts.remote` - the cohort members considered remote from the CTS Workbench's perspective

## Egeria 1.2

| IGC version | Conformant profile(s) | Notes |
| :--- | :--- | :--- |
| [v11.5.0.2 SP3](1.2/11.5.0.2sp3) | Metadata sharing |
| [v11.5.0.2 SP5](1.2/11.5.0.2sp5) | Metadata sharing |
| [v11.5.0.2 SP6](1.2/11.5.0.2sp6) | None | There appears to be a bug in this release on pagination of complex search criteria, causing some of the `find...` methods to fail. |
| [v11.7.0.0](1.2/11.7.0.0) | Metadata sharing |
| [v11.7.0.1](1.2/11.7.0.1) | Metadata sharing |
| [v11.7.0.1 SP1](1.2/11.7.0.1sp1) | Pending |
| [v11.7.0.2](1.2/11.7.0.2) | Pending |
| [v11.7.1.0](1.2/11.7.1.0) | Pending |
| [v11.7.1.0 SP1](1.2/11.7.1.0sp1) | Pending |
| [v11.7.1.0 SP2](1.2/11.7.1.0sp2) | Pending |

## Known issues

The following releases of IGC have known issues that prevent the sample data from being loaded in order to run through
the CTS, and therefore the CTS is not run against them. If you are currently running one of these releases and wish to
make use of the IGC connector you are strongly encouraged to upgrade to the nearest release listed above.

- v11.5.0.1 - it is not possible to import certain _category_ to _term_ relationships needed for classifications
- v11.5.0.1 RU5 - as above for v11.5.0.1
- v11.5.0.2 - it is not possible to import _term_ to _data_file_field_ relationships

----
License: [CC BY 4.0](https://creativecommons.org/licenses/by/4.0/),
Copyright Contributors to the ODPi Egeria project.
