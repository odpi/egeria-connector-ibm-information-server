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
- Description of the k8s environment
    - `<version>.deployment` - details of the deployed components used for the test
    - `<version>.configmap` - details of the variables used within the components of the test
- The OMAG server configurations:
    - `omag.server.ibmigc.config` - the configuration of the IGC connector (proxy)
    - `omag.server.cts.config` - the configuration of the CTS workbench
- The cohort registrations:
    - `cohort.coco.ibmigc.local` - the local IGC connector (proxy) cohort registration information
    - `cohort.coco.ibmigc.remote` - the cohort members considered remote from the IGC connector (proxy)'s perspective
    - `cohort.coco.cts.local` - the local CTS Workbench cohort registration
    - `cohort.coco.cts.remote` - the cohort members considered remote from the CTS Workbench's perspective

## Egeria 2.10

| IGC version | Conformant profile(s) | Notes |
| :--- | :--- | :--- |
| [v11.5.0.2 SP3](results/2.10/11.5.0.2sp3) | (not tested) | (see notes on testing the full breadth of v11.5.x) |
| [v11.5.0.2 SP5](results/2.10/11.5.0.2sp5) | (not tested) | (see notes on testing the full breadth of v11.5.x) |
| [v11.5.0.2 SP6](results/2.10/11.5.0.2sp6) | (not tested) | (see notes on testing the full breadth of v11.5.x) |
| [v11.7.0.0](results/2.10/11.7.0.0) | Metadata sharing, Relationship search |  |
| [v11.7.0.1](results/2.10/11.7.0.1) | Metadata sharing, Relationship search |  |
| [v11.7.0.1 SP1](results/2.10/11.7.0.1sp1) | Metadata sharing, Relationship search |  |
| [v11.7.0.2](results/2.10/11.7.0.2) | Metadata sharing (limited), Relationship search | (see known issues) |
| [v11.7.1.0](results/2.10/11.7.1.0) | Metadata sharing (limited), Relationship search | (see known issues) |
| [v11.7.1.0 SP1](results/2.10/11.7.1.0sp1) | Metadata sharing (limited), Relationship search | (see known issues) |
| [v11.7.1.0 SP2](results/2.10/11.7.1.0sp2) | Metadata sharing (limited), Relationship search | (see known issues) |

## Egeria 2.5

| IGC version | Conformant profile(s) | Notes |
| :--- | :--- | :--- |
| [v11.5.0.2 SP3](results/2.5/11.5.0.2sp3) | (not tested) | (see notes on testing the full breadth of v11.5.x) |
| [v11.5.0.2 SP5](results/2.5/11.5.0.2sp5) | Metadata sharing, Relationship search | (run locally using AdoptOpenJDK 1.8.0_275-b01) |
| [v11.5.0.2 SP6](results/2.5/11.5.0.2sp6) | (not tested) | (see notes on testing the full breadth of v11.5.x) |
| [v11.7.0.0](results/2.5/11.7.0.0) | Metadata sharing, Relationship search | [known issue](https://github.com/odpi/egeria-connector-ibm-information-server/issues/457) |
| [v11.7.0.1](results/2.5/11.7.0.1) | Metadata sharing, Relationship search | [known issue](https://github.com/odpi/egeria-connector-ibm-information-server/issues/457) |
| [v11.7.0.1 SP1](results/2.5/11.7.0.1sp1) | Metadata sharing, Relationship search | [known issue](https://github.com/odpi/egeria-connector-ibm-information-server/issues/457) |
| [v11.7.0.2](results/2.5/11.7.0.2) | Metadata sharing (limited), Relationship search | (see known issues and [known issue](https://github.com/odpi/egeria-connector-ibm-information-server/issues/457)) |
| [v11.7.1.0](results/2.5/11.7.1.0) | Metadata sharing (limited), Relationship search | (see known issues and [known issue](https://github.com/odpi/egeria-connector-ibm-information-server/issues/457)) |
| [v11.7.1.0 SP1](results/2.5/11.7.1.0sp1) | Metadata sharing (limited), Relationship search | (see known issues and [known issue](https://github.com/odpi/egeria-connector-ibm-information-server/issues/457)) |
| [v11.7.1.0 SP2](results/2.5/11.7.1.0sp2) | Metadata sharing (limited), Relationship search | (see known issues and [known issue](https://github.com/odpi/egeria-connector-ibm-information-server/issues/457)) |

## Egeria 2.2

| IGC version | Conformant profile(s) | Notes |
| :--- | :--- | :--- |
| [v11.5.0.2 SP3](results/2.2/11.5.0.2sp3) | Metadata sharing, Relationship search |
| [v11.5.0.2 SP5](results/2.2/11.5.0.2sp5) | Metadata sharing, Relationship search |
| [v11.5.0.2 SP6](results/2.2/11.5.0.2sp6) | None | (see known issues) |
| [v11.7.0.0](results/2.2/11.7.0.0) | Metadata sharing, Relationship search |
| [v11.7.0.1](results/2.2/11.7.0.1) | Metadata sharing, Relationship search |
| [v11.7.0.1 SP1](results/2.2/11.7.0.1sp1) | Metadata sharing, Relationship search |
| [v11.7.0.2](results/2.2/11.7.0.2) | Metadata sharing (limited), Relationship search | (see known issues) |
| [v11.7.1.0](results/2.2/11.7.1.0) | Metadata sharing (limited), Relationship search | (see known issues) |
| [v11.7.1.0 SP1](results/2.2/11.7.1.0sp1) | Metadata sharing (limited), Relationship search | (see known issues) |
| [v11.7.1.0 SP2](results/2.2/11.7.1.0sp2) | Metadata sharing (limited), Relationship search | (see known issues) |

## Egeria 2.1

| IGC version | Conformant profile(s) | Notes |
| :--- | :--- | :--- |
| [v11.5.0.2 SP3](results/2.1/11.5.0.2sp3) | Metadata sharing, Relationship search |
| [v11.5.0.2 SP5](results/2.1/11.5.0.2sp5) | Metadata sharing, Relationship search |
| [v11.5.0.2 SP6](results/2.1/11.5.0.2sp6) | None | (see known issues) |
| [v11.7.0.0](results/2.1/11.7.0.0) | Metadata sharing, Relationship search |
| [v11.7.0.1](results/2.1/11.7.0.1) | Metadata sharing, Relationship search |
| [v11.7.0.1 SP1](results/2.1/11.7.0.1sp1) | Metadata sharing, Relationship search |
| [v11.7.0.2](results/2.1/11.7.0.2) | Metadata sharing (limited), Relationship search | (see known issues) |
| [v11.7.1.0](results/2.1/11.7.1.0) | Metadata sharing (limited), Relationship search | (see known issues) |
| [v11.7.1.0 SP1](results/2.1/11.7.1.0sp1) | Metadata sharing (limited), Relationship search | (see known issues) |
| [v11.7.1.0 SP2](results/2.1/11.7.1.0sp2) | Metadata sharing (limited), Relationship search | (see known issues) |

## Egeria 2.0

| IGC version | Conformant profile(s) | Notes |
| :--- | :--- | :--- |
| [v11.5.0.2 SP3](results/2.0/11.5.0.2sp3) | Metadata sharing, Relationship search |
| [v11.5.0.2 SP5](results/2.0/11.5.0.2sp5) | Metadata sharing, Relationship search |
| [v11.5.0.2 SP6](results/2.0/11.5.0.2sp6) | None | (see known issues) |
| [v11.7.0.0](results/2.0/11.7.0.0) | Metadata sharing, Relationship search |
| [v11.7.0.1](results/2.0/11.7.0.1) | Metadata sharing, Relationship search |
| [v11.7.0.1 SP1](results/2.0/11.7.0.1sp1) | Metadata sharing, Relationship search |
| [v11.7.0.2](results/2.0/11.7.0.2) | Metadata sharing (limited), Relationship search | (see known issues) |
| [v11.7.1.0](results/2.0/11.7.1.0) | Metadata sharing (limited), Relationship search | (see known issues) |
| [v11.7.1.0 SP1](results/2.0/11.7.1.0sp1) | Metadata sharing (limited), Relationship search | (see known issues) |
| [v11.7.1.0 SP2](results/2.0/11.7.1.0sp2) | Metadata sharing (limited), Relationship search | (see known issues) |

## Egeria 1.8

| IGC version | Conformant profile(s) | Notes |
| :--- | :--- | :--- |
| [v11.5.0.2 SP3](results/1.8/11.5.0.2sp3) | None | see [Egeria core issue #3202](https://github.com/odpi/egeria/issues/3202) |
| [v11.5.0.2 SP5](results/1.8/11.5.0.2sp5) | None | see [Egeria core issue #3202](https://github.com/odpi/egeria/issues/3202) |
| [v11.5.0.2 SP6](results/1.8/11.5.0.2sp6) | None | (see known issues) |
| [v11.7.0.0](results/1.8/11.7.0.0) | None | see [Egeria core issue #3202](https://github.com/odpi/egeria/issues/3202) |
| [v11.7.0.1](results/1.8/11.7.0.1) | None | see [Egeria core issue #3202](https://github.com/odpi/egeria/issues/3202) |
| [v11.7.0.1 SP1](results/1.8/11.7.0.1sp1) | None | see [Egeria core issue #3202](https://github.com/odpi/egeria/issues/3202) |
| [v11.7.0.2](results/1.8/11.7.0.2) | None | see [Egeria core issue #3202](https://github.com/odpi/egeria/issues/3202) |
| [v11.7.1.0](results/1.8/11.7.1.0) | None | see [Egeria core issue #3202](https://github.com/odpi/egeria/issues/3202) |
| [v11.7.1.0 SP1](results/1.8/11.7.1.0sp1) | None | see [Egeria core issue #3202](https://github.com/odpi/egeria/issues/3202) |
| [v11.7.1.0 SP2](results/1.8/11.7.1.0sp2) | None | see [Egeria core issue #3202](https://github.com/odpi/egeria/issues/3202) |

## Egeria 1.6

| IGC version | Conformant profile(s) | Notes |
| :--- | :--- | :--- |
| [v11.5.0.2 SP3](results/1.6/11.5.0.2sp3) | Metadata sharing, Relationship search |
| [v11.5.0.2 SP5](results/1.6/11.5.0.2sp5) | Metadata sharing, Relationship search |
| [v11.5.0.2 SP6](results/1.6/11.5.0.2sp6) | None | (see known issues) |
| [v11.7.0.0](results/1.6/11.7.0.0) | Metadata sharing, Relationship search |
| [v11.7.0.1](results/1.6/11.7.0.1) | Metadata sharing, Relationship search |
| [v11.7.0.1 SP1](results/1.6/11.7.0.1sp1) | Metadata sharing, Relationship search |
| [v11.7.0.2](results/1.6/11.7.0.2) | Metadata sharing (limited), Relationship search | (see known issues) |
| [v11.7.1.0](results/1.6/11.7.1.0) | Metadata sharing (limited), Relationship search | (see known issues) |
| [v11.7.1.0 SP1](results/1.6/11.7.1.0sp1) | Metadata sharing (limited), Relationship search | (see known issues) |
| [v11.7.1.0 SP2](results/1.6/11.7.1.0sp2) | Metadata sharing (limited), Relationship search | (see known issues) |

## Egeria 1.5

| IGC version | Conformant profile(s) | Notes |
| :--- | :--- | :--- |
| [v11.5.0.2 SP3](results/1.5/11.5.0.2sp3) | Metadata sharing, Relationship search |
| [v11.5.0.2 SP5](results/1.5/11.5.0.2sp5) | Metadata sharing, Relationship search |
| [v11.5.0.2 SP6](results/1.5/11.5.0.2sp6) | None | (see known issues) |
| [v11.7.0.0](results/1.5/11.7.0.0) | Metadata sharing, Relationship search |
| [v11.7.0.1](results/1.5/11.7.0.1) | Metadata sharing, Relationship search |
| [v11.7.0.1 SP1](results/1.5/11.7.0.1sp1) | Metadata sharing, Relationship search |
| [v11.7.0.2](results/1.5/11.7.0.2) | Metadata sharing (limited), Relationship search | (see known issues) |
| [v11.7.1.0](results/1.5/11.7.1.0) | Metadata sharing (limited), Relationship search | (see known issues) |
| [v11.7.1.0 SP1](results/1.5/11.7.1.0sp1) | Metadata sharing (limited), Relationship search | (see known issues) |
| [v11.7.1.0 SP2](results/1.5/11.7.1.0sp2) | Metadata sharing (limited), Relationship search | (see known issues) |

## Egeria 1.4 (connector 1.3)

Note that there is no 1.4 release of the connector, so these results are based on testing release 1.3 of the connector
against release 1.4 of Egeria.

| IGC version | Conformant profile(s) | Notes |
| :--- | :--- | :--- |
| [v11.5.0.2 SP3](results/1.4/11.5.0.2sp3) | Metadata sharing, Relationship search |
| [v11.5.0.2 SP5](results/1.4/11.5.0.2sp5) | Metadata sharing, Relationship search |
| [v11.5.0.2 SP6](results/1.4/11.5.0.2sp6) | None | (see known issues) |
| [v11.7.0.0](results/1.4/11.7.0.0) | None | (see known issues) |
| [v11.7.0.1](results/1.4/11.7.0.1) | Metadata sharing, Relationship search |
| [v11.7.0.1 SP1](results/1.4/11.7.0.1sp1) | Metadata sharing, Relationship search |
| [v11.7.0.2](results/1.4/11.7.0.2) | Metadata sharing (limited), Relationship search | (see known issues) |
| [v11.7.1.0](results/1.4/11.7.1.0) | Metadata sharing (limited), Relationship search | (see known issues) |
| [v11.7.1.0 SP1](results/1.4/11.7.1.0sp1) | Metadata sharing (limited), Relationship search | (see known issues) |
| [v11.7.1.0 SP2](results/1.4/11.7.1.0sp2) | Metadata sharing (limited), Relationship search | (see known issues) |

## Egeria 1.3

| IGC version | Conformant profile(s) | Notes |
| :--- | :--- | :--- |
| [v11.5.0.2 SP3](results/1.3/11.5.0.2sp3) | Metadata sharing, Relationship search |
| [v11.5.0.2 SP5](results/1.3/11.5.0.2sp5) | Metadata sharing, Relationship search |
| [v11.5.0.2 SP6](results/1.3/11.5.0.2sp6) | None | (see known issues) |
| [v11.7.0.0](results/1.3/11.7.0.0) | None | (see known issues) |
| [v11.7.0.1](results/1.3/11.7.0.1) | Metadata sharing, Relationship search |
| [v11.7.0.1 SP1](results/1.3/11.7.0.1sp1) | Metadata sharing, Relationship search |
| [v11.7.0.2](results/1.3/11.7.0.2) | Metadata sharing, Relationship search |
| [v11.7.1.0](results/1.3/11.7.1.0) | Metadata sharing, Relationship search |
| [v11.7.1.0 SP1](results/1.3/11.7.1.0sp1) | Metadata sharing, Relationship search |
| [v11.7.1.0 SP2](results/1.3/11.7.1.0sp2) | Metadata sharing, Relationship search |

## Egeria 1.2

| IGC version | Conformant profile(s) | Notes |
| :--- | :--- | :--- |
| [v11.5.0.2 SP3](results/1.2/11.5.0.2sp3) | Metadata sharing |
| [v11.5.0.2 SP5](results/1.2/11.5.0.2sp5) | Metadata sharing |
| [v11.5.0.2 SP6](results/1.2/11.5.0.2sp6) | None | (see known issues) |
| [v11.7.0.0](results/1.2/11.7.0.0) | Metadata sharing |
| [v11.7.0.1](results/1.2/11.7.0.1) | Metadata sharing |
| [v11.7.0.1 SP1](results/1.2/11.7.0.1sp1) | Metadata sharing |
| [v11.7.0.2](results/1.2/11.7.0.2) | Metadata sharing |
| [v11.7.1.0](results/1.2/11.7.1.0) | Metadata sharing |
| [v11.7.1.0 SP1](results/1.2/11.7.1.0sp1) | Metadata sharing |
| [v11.7.1.0 SP2](results/1.2/11.7.1.0sp2) | Metadata sharing |

## Known issues

The following releases of IGC have known issues that prevent the sample data from being loaded in order to run through
the CTS, and therefore the CTS is not run against them. If you are currently running one of these releases and wish to
make use of the IGC connector you are strongly encouraged to upgrade to the nearest release listed above.

- v11.5.0.1 - it is not possible to import certain _category_ to _term_ relationships needed for classifications
- v11.5.0.1 RU5 - as above for v11.5.0.1
- v11.5.0.2 - it is not possible to import _term_ to _data_file_field_ relationships
- v11.5.0.2 SP6 - there appears to be a bug in this release on pagination of complex search criteria, causing some of the `find...` methods to fail
- v11.7.0.0 - this release models a database column's length as an array of strings, causing deserialization to fail (a workaround may be implemented under issue [#229](https://github.com/odpi/egeria-connector-ibm-information-server/issues/229))
- v11.7.0.2+ - these releases no longer support searching against the `long_description` property of IGC in combination with other search criteria, and therefore they cannot fully support all of the `find...` methods (tracked under issue [#215](https://github.com/odpi/egeria-connector-ibm-information-server/issues/215))

### No longer testing the full breadth of v11.5.x

Information Server v11.5.x uses the TLS v1.0 communication standard, which has now been deprecated for several years.
This makes ongoing testing of these releases significantly more cumbersome, and therefore we will only continue testing
a single v11.5.x release (v11.5.0.2sp5) for as long as remains feasible going forward.

----
License: [CC BY 4.0](https://creativecommons.org/licenses/by/4.0/),
Copyright Contributors to the ODPi Egeria project.
