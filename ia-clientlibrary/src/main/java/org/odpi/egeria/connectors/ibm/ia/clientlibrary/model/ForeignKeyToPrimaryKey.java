/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ForeignKeyToPrimaryKey {

    @JacksonXmlProperty(isAttribute = true) private Long violationsDistinct;
    @JacksonXmlProperty(isAttribute = true) private Long violationsTotal;
    @JacksonXmlProperty(isAttribute = true) private Float violationsDistinctPercentage;
    @JacksonXmlProperty(isAttribute = true) private Float violationsTotalPercentage;
    @JacksonXmlProperty(isAttribute = true) private Long integrityDistinct;
    @JacksonXmlProperty(isAttribute = true) private Long integrityTotal;
    @JacksonXmlProperty(isAttribute = true) private Float integrityDistinctPercentage;
    @JacksonXmlProperty(isAttribute = true) private Float integrityTotalPercentage;
    @JacksonXmlProperty(isAttribute = true) private Long totalValuesDistinct;
    @JacksonXmlProperty(isAttribute = true) private Long totalValuesTotal;
    @JacksonXmlProperty(isAttribute = true) private Float totalDistinctPercentage;
    @JacksonXmlProperty(isAttribute = true) private Float totalTotalPercentage;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Violation")
    private List<Violation> violations;

    @Override
    public String toString() {
        return "{ \"violationsDistinct\": " + violationsDistinct
                + ", \"violationsTotal\": " + violationsTotal
                + ", \"violationsDistinctPercentage\": " + violationsDistinctPercentage
                + ", \"violationsTotalPercentage\": " + violationsTotalPercentage
                + ", \"integrityDistinct\": " + integrityDistinct
                + ", \"integrityTotal\": " + integrityTotal
                + ", \"integrityDistinctPercentage\": " + integrityDistinctPercentage
                + ", \"integrityTotalPercentage\": " + integrityTotalPercentage
                + ", \"totalValuesDistinct\": " + totalValuesDistinct
                + ", \"totalValuesTotal\": " + totalValuesTotal
                + ", \"totalDistinctPercentage\": " + totalDistinctPercentage
                + ", \"totalTotalPercentage\": " + totalTotalPercentage
                + ", \"Violations\": " + (violations == null ? "[]" : violations.toString())
                + " }";
    }

}
