/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

public enum AnalysisStatus {
    not_done,
    processing_started,
    processing_completed,
    review_completed,
    error,
    not_found,
    not_analyzable,
    review_only,
    lightweight_review
}
