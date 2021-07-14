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

----
License: [CC BY 4.0](https://creativecommons.org/licenses/by/4.0/),
Copyright Contributors to the ODPi Egeria project.
