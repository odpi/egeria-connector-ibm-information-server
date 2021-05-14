/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TaskType implements IAEnum {

    COLUMN_ANALYSIS("ColumnAnalysis"),
    DATA_RULE("DataRule"),
    METRIC("Metric"),
    MULTI_COLUMN_PK_ANALYSIS("MultiColumnPKAnalysis"),
    CROSS_DOMAIN_ANALYSIS("CrossDomainAnalysis"),
    REFERENTIAL_INTEGRITY_ANALYSIS("ReferentialIntegrityAnalysis"),
    TASK_SEQUENCE("TaskSequence"),
    DATA_QUALITY_ANALYSIS("DataQualityAnalysis"),
    AUTO_DISCOVERY("AutoDiscovery");

    @JsonValue
    private String value;
    TaskType(String value) { this.value = value; }

    @Override
    public String getValue() { return value; }

}
