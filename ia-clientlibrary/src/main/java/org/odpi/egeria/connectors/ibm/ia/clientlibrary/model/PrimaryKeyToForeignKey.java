/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PrimaryKeyToForeignKey {

    @JacksonXmlProperty(isAttribute = true) private Long matchingValuesDistinct;
    @JacksonXmlProperty(isAttribute = true) private Long matchingValuesTotal;
    @JacksonXmlProperty(isAttribute = true) private Float matchDistinctPercentage;
    @JacksonXmlProperty(isAttribute = true) private Float matchTotalPercentage;
    @JacksonXmlProperty(isAttribute = true) private Long notMatchingValuesDistinct;
    @JacksonXmlProperty(isAttribute = true) private Long notMatchingValuesTotal;
    @JacksonXmlProperty(isAttribute = true) private Float notMatchingDistinctPercentage;
    @JacksonXmlProperty(isAttribute = true) private Float notMatchingTotalPercentage;
    @JacksonXmlProperty(isAttribute = true) private Long totalValuesDistinct;
    @JacksonXmlProperty(isAttribute = true) private Long totalValuesTotal;
    @JacksonXmlProperty(isAttribute = true) private Float totalDistinctPercentage;
    @JacksonXmlProperty(isAttribute = true) private Float totalTotalPercentage;

    @Override
    public String toString() {
        return "{ \"matchingValuesDistinct\": " + matchingValuesDistinct
                + ", \"matchingValuesTotal\": " + matchingValuesTotal
                + ", \"matchDistinctPercentage\": " + matchDistinctPercentage
                + ", \"matchTotalPercentage\": " + matchTotalPercentage
                + ", \"notMatchingValuesDistinct\": " + notMatchingValuesDistinct
                + ", \"notMatchingValuesTotal\": " + notMatchingValuesTotal
                + ", \"notMatchingDistinctPercentage\": " + notMatchingDistinctPercentage
                + ", \"notMatchingTotalPercentage\": " + notMatchingTotalPercentage
                + ", \"totalValuesDistinct\": " + totalValuesDistinct
                + ", \"totalValuesTotal\": " + totalValuesTotal
                + ", \"totalDistinctPercentage\": " + totalDistinctPercentage
                + ", \"totalTotalPercentage\": " + totalTotalPercentage
                + " }";
    }

}
