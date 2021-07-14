<!-- SPDX-License-Identifier: CC-BY-4.0 -->
<!-- Copyright Contributors to the ODPi Egeria project. -->

# Testing overview

## Conformance Test Suite (CTS)

The Conformance Test Suite (CTS) measures conformance of the repository with the expected
behavior of an Egeria repository. Conformance indicates that the repository behaves precisely
as expected for an Egeria repository.

## Reproducibility

### Re-running the tests

The `cts/charts` directory contains a Helm chart to automate the execution of these suites
against an IGC proxy, to reproduce these results.

!!! attention "Must be a pre-populated IBM Information Server environment"
    Note that since the proxy operates in a read-only manner, the IBM Information Server
    environment it is configured to connect to must be pre-populated with metadata before
    the CTS can produce any meaningful results.

    Also be aware that the CTS is designed only to test the low-level protocols of a metadata
    repository, and therefore can only be run against the IGC proxy (not the DataStage proxy).

### Data points

The `cts/results` directory contains results of running the suites against the IGC proxy and
a set of pre-defined metadata representative of a fictitious "Coco Pharmaceuticals" company
against a number of different releases of IBM Information Governance Catalog (IGC).
For each release, you will find the following details:

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

--8<-- "snippets/abbr.md"
