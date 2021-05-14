/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum AnalysisStatus implements IAEnum {
    NOT_DONE("not_done"),
    PROCESSING_STARTED("processing_started"),
    PROCESSING_COMPLETED("processing_completed"),
    REVIEW_COMPLETED("review_completed"),
    ERROR("error"),
    NOT_FOUND("not_found"),
    NOT_ANALYZABLE("not_analyzable"),
    REVIEW_ONLY("review_only"),
    LIGHTWEIGHT_REVIEW("lightweight_review");

    @JsonValue
    private String value;
    AnalysisStatus(String value) { this.value = value; }

    @Override
    public String getValue() { return value; }

}
