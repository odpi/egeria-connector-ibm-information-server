<!-- SPDX-License-Identifier: CC-BY-4.0 -->
<!-- Copyright Contributors to the ODPi Egeria project. -->

# CTS results

All version numbers below refer to the version of the connector that was tested, against the
release of Egeria with precisely the same version number.

## [2.11](https://github.com/odpi/egeria-connector-ibm-information-server/tree/main/cts/results/2.11){ target=cts }

??? success "The 2.11 release is conformant with the mandatory profiles, with some limitations"
    Note that in v11.7.0.2 and above there is no longer support for searching against the
    `long_description` property of IGC in combination with other search criteria, and therefore
    these releases cannot fully support all of the `find...` methods
    (tracked under issue [#215](https://github.com/odpi/egeria-connector-ibm-information-server/issues/215))

| IGC version | Conformant profile(s) |
| :--- | :--- |
| v11.5.0.2 SP3 | (not tested)^[1](#fn:1)^ |
| v11.5.0.2 SP5 | (not tested)^[1](#fn:1)^ |
| v11.5.0.2 SP6 | (not tested)^[1](#fn:1)^ |
| v11.7.0.0 | Metadata sharing, Relationship search |
| v11.7.0.1 | Metadata sharing, Relationship search |
| v11.7.0.1 SP1 | Metadata sharing, Relationship search |
| v11.7.0.2 | Metadata sharing^[2](#fn:2)^, Relationship search |
| v11.7.1.0 | Metadata sharing^[2](#fn:2)^, Relationship search |
| v11.7.1.0 SP1 | Metadata sharing^[2](#fn:2)^, Relationship search |
| v11.7.1.0 SP2 | Metadata sharing^[2](#fn:2)^, Relationship search |

## [2.10](https://github.com/odpi/egeria-connector-ibm-information-server/tree/main/cts/results/2.10){ target=cts }

??? success "The 2.10 release is conformant with the mandatory profiles, with some limitations"
    Note that in v11.7.0.2 and above there is no longer support for searching against the
    `long_description` property of IGC in combination with other search criteria, and therefore
    these releases cannot fully support all of the `find...` methods
    (tracked under issue [#215](https://github.com/odpi/egeria-connector-ibm-information-server/issues/215))

| IGC version | Conformant profile(s) |
| :--- | :--- |
| v11.5.0.2 SP3 | (not tested)^[1](#fn:1)^ |
| v11.5.0.2 SP5 | (not tested)^[1](#fn:1)^ |
| v11.5.0.2 SP6 | (not tested)^[1](#fn:1)^ |
| v11.7.0.0 | Metadata sharing, Relationship search |
| v11.7.0.1 | Metadata sharing, Relationship search |
| v11.7.0.1 SP1 | Metadata sharing, Relationship search |
| v11.7.0.2 | Metadata sharing^[2](#fn:2)^, Relationship search |
| v11.7.1.0 | Metadata sharing^[2](#fn:2)^, Relationship search |
| v11.7.1.0 SP1 | Metadata sharing^[2](#fn:2)^, Relationship search |
| v11.7.1.0 SP2 | Metadata sharing^[2](#fn:2)^, Relationship search |

## [2.5](https://github.com/odpi/egeria-connector-ibm-information-server/tree/main/cts/results/2.5){ target=cts }

??? success "The 2.5 release is conformant with the mandatory profiles, with some limitations"
    Note that in v11.7.0.2 and above there is no longer support for searching against the
    `long_description` property of IGC in combination with other search criteria, and therefore
    these releases cannot fully support all of the `find...` methods
    (tracked under issue [#215](https://github.com/odpi/egeria-connector-ibm-information-server/issues/215))

    In addition, certain searches for `ContactDetails` are known to fail, as tracked under issue
    [#457](https://github.com/odpi/egeria-connector-ibm-information-server/issues/457)

| IGC version | Conformant profile(s) |
| :--- | :--- |
| v11.5.0.2 SP3 | (not tested)^[1](#fn:1)^ |
| v11.5.0.2 SP5 | Metadata sharing, Relationship search |
| v11.5.0.2 SP6 | (not tested)^[1](#fn:1)^ |
| v11.7.0.0 | Metadata sharing^[3](#fn:3)^, Relationship search |
| v11.7.0.1 | Metadata sharing^[3](#fn:3)^, Relationship search |
| v11.7.0.1 SP1 | Metadata sharing^[3](#fn:3)^, Relationship search |
| v11.7.0.2 | Metadata sharing^[2](#fn:2),[3](#fn:3)^, Relationship search |
| v11.7.1.0 | Metadata sharing^[2](#fn:2),[3](#fn:3)^, Relationship search |
| v11.7.1.0 SP1 | Metadata sharing^[2](#fn:2),[3](#fn:3)^, Relationship search |
| v11.7.1.0 SP2 | Metadata sharing^[2](#fn:2),[3](#fn:3)^, Relationship search |

## [2.2](https://github.com/odpi/egeria-connector-ibm-information-server/tree/main/cts/results/2.2){ target=cts }

??? success "The 2.2 release is conformant with the mandatory profiles, with some limitations"
    Note that in v11.7.0.2 and above there is no longer support for searching against the
    `long_description` property of IGC in combination with other search criteria, and therefore
    these releases cannot fully support all of the `find...` methods
    (tracked under issue [#215](https://github.com/odpi/egeria-connector-ibm-information-server/issues/215))

| IGC version | Conformant profile(s) |
| :--- | :--- |
| v11.5.0.2 SP3 | Metadata sharing, Relationship search |
| v11.5.0.2 SP5 | Metadata sharing, Relationship search |
| v11.5.0.2 SP6 | None^[4](#fn:4)^ |
| v11.7.0.0 | Metadata sharing, Relationship search |
| v11.7.0.1 | Metadata sharing, Relationship search |
| v11.7.0.1 SP1 | Metadata sharing, Relationship search |
| v11.7.0.2 | Metadata sharing^[2](#fn:2)^, Relationship search |
| v11.7.1.0 | Metadata sharing^[2](#fn:2)^, Relationship search |
| v11.7.1.0 SP1 | Metadata sharing^[2](#fn:2)^, Relationship search |
| v11.7.1.0 SP2 | Metadata sharing^[2](#fn:2)^, Relationship search |

## [2.1](https://github.com/odpi/egeria-connector-ibm-information-server/tree/main/cts/results/2.1){ target=cts }

??? success "The 2.1 release is conformant with the mandatory profiles, with some limitations"
    Note that in v11.7.0.2 and above there is no longer support for searching against the
    `long_description` property of IGC in combination with other search criteria, and therefore
    these releases cannot fully support all of the `find...` methods
    (tracked under issue [#215](https://github.com/odpi/egeria-connector-ibm-information-server/issues/215))

| IGC version | Conformant profile(s) |
| :--- | :--- |
| v11.5.0.2 SP3 | Metadata sharing, Relationship search |
| v11.5.0.2 SP5 | Metadata sharing, Relationship search |
| v11.5.0.2 SP6 | None^[4](#fn:4)^ |
| v11.7.0.0 | Metadata sharing, Relationship search |
| v11.7.0.1 | Metadata sharing, Relationship search |
| v11.7.0.1 SP1 | Metadata sharing, Relationship search |
| v11.7.0.2 | Metadata sharing^[2](#fn:2)^, Relationship search |
| v11.7.1.0 | Metadata sharing^[2](#fn:2)^, Relationship search |
| v11.7.1.0 SP1 | Metadata sharing^[2](#fn:2)^, Relationship search |
| v11.7.1.0 SP2 | Metadata sharing^[2](#fn:2)^, Relationship search |

## [2.0](https://github.com/odpi/egeria-connector-ibm-information-server/tree/main/cts/results/2.0){ target=cts }

??? success "The 2.0 release is conformant with the mandatory profiles, with some limitations"
    Note that in v11.7.0.2 and above there is no longer support for searching against the
    `long_description` property of IGC in combination with other search criteria, and therefore
    these releases cannot fully support all of the `find...` methods
    (tracked under issue [#215](https://github.com/odpi/egeria-connector-ibm-information-server/issues/215))

| IGC version | Conformant profile(s) |
| :--- | :--- |
| v11.5.0.2 SP3 | Metadata sharing, Relationship search |
| v11.5.0.2 SP5 | Metadata sharing, Relationship search |
| v11.5.0.2 SP6 | None^[4](#fn:4)^ |
| v11.7.0.0 | Metadata sharing, Relationship search |
| v11.7.0.1 | Metadata sharing, Relationship search |
| v11.7.0.1 SP1 | Metadata sharing, Relationship search |
| v11.7.0.2 | Metadata sharing^[2](#fn:2)^, Relationship search |
| v11.7.1.0 | Metadata sharing^[2](#fn:2)^, Relationship search |
| v11.7.1.0 SP1 | Metadata sharing^[2](#fn:2)^, Relationship search |
| v11.7.1.0 SP2 | Metadata sharing^[2](#fn:2)^, Relationship search |

## [1.8](https://github.com/odpi/egeria-connector-ibm-information-server/tree/main/cts/results/1.8){ target=cts }

??? failure "The 1.8 release is not conformant"
    The CTS in release 1.8 of Egeria included some changes that were [not handling regular
    expressions in searches as expected](https://github.com/odpi/egeria/issues/3202), causing
    conformance to fail across the board for the IGC proxy. These have since been resolved.

| IGC version | Conformant profile(s) |
| :--- | :--- |
| v11.5.0.2 SP3 | None |
| v11.5.0.2 SP5 | None |
| v11.5.0.2 SP6 | None^[4](#fn:4)^ |
| v11.7.0.0 | None |
| v11.7.0.1 | None |
| v11.7.0.1 SP1 | None |
| v11.7.0.2 | None |
| v11.7.1.0 | None |
| v11.7.1.0 SP1 | None |
| v11.7.1.0 SP2 | None |

## [1.6](https://github.com/odpi/egeria-connector-ibm-information-server/tree/main/cts/results/1.6){ target=cts }

??? success "The 1.6 release is conformant with the mandatory profiles, with some limitations"
    Note that in v11.7.0.2 and above there is no longer support for searching against the
    `long_description` property of IGC in combination with other search criteria, and therefore
    these releases cannot fully support all of the `find...` methods
    (tracked under issue [#215](https://github.com/odpi/egeria-connector-ibm-information-server/issues/215))

| IGC version | Conformant profile(s) |
| :--- | :--- |
| v11.5.0.2 SP3 | Metadata sharing, Relationship search |
| v11.5.0.2 SP5 | Metadata sharing, Relationship search |
| v11.5.0.2 SP6 | None^[4](#fn:4)^ |
| v11.7.0.0 | Metadata sharing, Relationship search |
| v11.7.0.1 | Metadata sharing, Relationship search |
| v11.7.0.1 SP1 | Metadata sharing, Relationship search |
| v11.7.0.2 | Metadata sharing^[2](#fn:2)^, Relationship search |
| v11.7.1.0 | Metadata sharing^[2](#fn:2)^, Relationship search |
| v11.7.1.0 SP1 | Metadata sharing^[2](#fn:2)^, Relationship search |
| v11.7.1.0 SP2 | Metadata sharing^[2](#fn:2)^, Relationship search |

## [1.5](https://github.com/odpi/egeria-connector-ibm-information-server/tree/main/cts/results/1.5){ target=cts }

??? success "The 1.5 release is conformant with the mandatory profiles, with some limitations"
    Note that in v11.7.0.2 and above there is no longer support for searching against the
    `long_description` property of IGC in combination with other search criteria, and therefore
    these releases cannot fully support all of the `find...` methods
    (tracked under issue [#215](https://github.com/odpi/egeria-connector-ibm-information-server/issues/215))

| IGC version | Conformant profile(s) |
| :--- | :--- |
| v11.5.0.2 SP3 | Metadata sharing, Relationship search |
| v11.5.0.2 SP5 | Metadata sharing, Relationship search |
| v11.5.0.2 SP6 | None^[4](#fn:4)^ |
| v11.7.0.0 | Metadata sharing, Relationship search |
| v11.7.0.1 | Metadata sharing, Relationship search |
| v11.7.0.1 SP1 | Metadata sharing, Relationship search |
| v11.7.0.2 | Metadata sharing^[2](#fn:2)^, Relationship search |
| v11.7.1.0 | Metadata sharing^[2](#fn:2)^, Relationship search |
| v11.7.1.0 SP1 | Metadata sharing^[2](#fn:2)^, Relationship search |
| v11.7.1.0 SP2 | Metadata sharing^[2](#fn:2)^, Relationship search |

## [1.4](https://github.com/odpi/egeria-connector-ibm-information-server/tree/main/cts/results/1.4){ target=cts }

??? success "The 1.4 release is conformant with the mandatory profiles, with some limitations"
    Note that in v11.7.0.2 and above there is no longer support for searching against the
    `long_description` property of IGC in combination with other search criteria, and therefore
    these releases cannot fully support all of the `find...` methods
    (tracked under issue [#215](https://github.com/odpi/egeria-connector-ibm-information-server/issues/215))

    In addition, the v11.7.0.0 release models a database column's length as an array of strings,
    causing deserialization to fail (tracked under issue [#229](https://github.com/odpi/egeria-connector-ibm-information-server/issues/229))

    Finally, note that there is no 1.4 release of the connector, so these results are based on
    testing release 1.3 of the connector against release 1.4 of Egeria.

| IGC version | Conformant profile(s) |
| :--- | :--- |
| v11.5.0.2 SP3 | Metadata sharing, Relationship search |
| v11.5.0.2 SP5 | Metadata sharing, Relationship search |
| v11.5.0.2 SP6 | None^[4](#fn:4)^ |
| v11.7.0.0 | None^[5](#fn:5)^ |
| v11.7.0.1 | Metadata sharing, Relationship search |
| v11.7.0.1 SP1 | Metadata sharing, Relationship search |
| v11.7.0.2 | Metadata sharing^[2](#fn:2)^, Relationship search |
| v11.7.1.0 | Metadata sharing^[2](#fn:2)^, Relationship search |
| v11.7.1.0 SP1 | Metadata sharing^[2](#fn:2)^, Relationship search |
| v11.7.1.0 SP2 | Metadata sharing^[2](#fn:2)^, Relationship search |

## [1.3](https://github.com/odpi/egeria-connector-ibm-information-server/tree/main/cts/results/1.3){ target=cts }

??? success "The 1.3 release is conformant with the mandatory profiles, with some limitations"
    Note that in v11.7.0.2 and above there is no longer support for searching against the
    `long_description` property of IGC in combination with other search criteria, and therefore
    these releases cannot fully support all of the `find...` methods
    (tracked under issue [#215](https://github.com/odpi/egeria-connector-ibm-information-server/issues/215))

    In addition, the v11.7.0.0 release models a database column's length as an array of strings,
    causing deserialization to fail (tracked under issue [#229](https://github.com/odpi/egeria-connector-ibm-information-server/issues/229))

| IGC version | Conformant profile(s) |
| :--- | :--- |
| v11.5.0.2 SP3 | Metadata sharing, Relationship search |
| v11.5.0.2 SP5 | Metadata sharing, Relationship search |
| v11.5.0.2 SP6 | None^[4](#fn:4)^ |
| v11.7.0.0 | None^[5](#fn:5)^ |
| v11.7.0.1 | Metadata sharing, Relationship search |
| v11.7.0.1 SP1 | Metadata sharing, Relationship search |
| v11.7.0.2 | Metadata sharing, Relationship search |
| v11.7.1.0 | Metadata sharing, Relationship search |
| v11.7.1.0 SP1 | Metadata sharing, Relationship search |
| v11.7.1.0 SP2 | Metadata sharing, Relationship search |

## [1.2](https://github.com/odpi/egeria-connector-ibm-information-server/tree/main/cts/results/1.2){ target=cts }

??? success "The 1.2 release is conformant with the mandatory profiles, with some limitations"
    Note that in v11.7.0.2 and above there is no longer support for searching against the
    `long_description` property of IGC in combination with other search criteria, and therefore
    these releases cannot fully support all of the `find...` methods
    (tracked under issue [#215](https://github.com/odpi/egeria-connector-ibm-information-server/issues/215))

| IGC version | Conformant profile(s) |
| :--- | :--- |
| v11.5.0.2 SP3 | Metadata sharing |
| v11.5.0.2 SP5 | Metadata sharing |
| v11.5.0.2 SP6 | None^[4](#fn:4)^ |
| v11.7.0.0 | Metadata sharing |
| v11.7.0.1 | Metadata sharing |
| v11.7.0.1 SP1 | Metadata sharing |
| v11.7.0.2 | Metadata sharing |
| v11.7.1.0 | Metadata sharing |
| v11.7.1.0 SP1 | Metadata sharing |
| v11.7.1.0 SP2 | Metadata sharing |

[^1]: Information Server v11.5.x uses the TLS v1.0 communication standard, which has now been deprecated for several years.
      This makes ongoing testing of these releases significantly more cumbersome, and therefore we will no longer continue
      testing the connector against these releases.
[^2]: In v11.7.0.2 and above of IBM Information Governance Catalog there is no longer support for
      searching against the `long_description` property in combination with other search criteria,
      and therefore these releases cannot fully support all of the `find...` methods (tracked under
      issue [#215](https://github.com/odpi/egeria-connector-ibm-information-server/issues/215){ target=issue })
[^3]: A [known issue](https://github.com/odpi/egeria-connector-ibm-information-server/issues/457){ target=issue }
      resulted in certain searches for `ContactDetails` to fail.
[^4]: v11.5.0.2 SP6 appears to contain a bug regarding pagination of complex search criteria,
      causing some of the `find...` methods to fail.
[^5]: v11.7.0.0 models a database column's length as an array of strings, which caused
      deserialization to fail in certain connector versions (tracked under issue
      [#229](https://github.com/odpi/egeria-connector-ibm-information-server/issues/229){ target=issue })

--8<-- "snippets/abbr.md"
