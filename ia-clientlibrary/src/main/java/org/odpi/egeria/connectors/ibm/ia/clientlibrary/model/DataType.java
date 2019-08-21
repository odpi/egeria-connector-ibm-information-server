/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DataType {

    @JacksonXmlProperty(isAttribute = true) private DataTypeCode definedType;
    @JacksonXmlProperty(isAttribute = true) private DataTypeCode inferredType;
    @JacksonXmlProperty(isAttribute = true) private DataTypeCode selectedType;
    @JacksonXmlProperty(isAttribute = true) private Integer definedLength;
    @JacksonXmlProperty(isAttribute = true) private Integer inferredLength;
    @JacksonXmlProperty(isAttribute = true) private Integer selectedLength;
    @JacksonXmlProperty(isAttribute = true) private Integer definedPrecision;
    @JacksonXmlProperty(isAttribute = true) private Integer inferredPrecision;
    @JacksonXmlProperty(isAttribute = true) private Integer selectedPrecision;
    @JacksonXmlProperty(isAttribute = true) private Integer definedScale;
    @JacksonXmlProperty(isAttribute = true) private Integer inferredScale;
    @JacksonXmlProperty(isAttribute = true) private Integer selectedScale;
    @JacksonXmlProperty(isAttribute = true) private Boolean definedNullability;
    @JacksonXmlProperty(isAttribute = true) private Boolean inferredNullability;
    @JacksonXmlProperty(isAttribute = true) private Boolean selectedNullability;
    @JacksonXmlProperty(isAttribute = true) private Boolean definedIsEmpty;
    @JacksonXmlProperty(isAttribute = true) private Boolean inferredIsEmpty;
    @JacksonXmlProperty(isAttribute = true) private Boolean selectedIsEmpty;
    @JacksonXmlProperty(isAttribute = true) private Boolean inferredIsConstant;
    @JacksonXmlProperty(isAttribute = true) private Boolean selectedIsConstant;

    @Override
    public String toString() {
        return "{ \"definedType\": \"" + definedType
                + "\", \"inferredType\": \"" + inferredType
                + "\", \"selectedType\": \"" + selectedType
                + "\", \"definedLength\": " + definedLength
                + ", \"inferredLength\": " + inferredLength
                + ", \"selectedLength\": " + selectedLength
                + ", \"definedPrecision\": " + definedPrecision
                + ", \"inferredPrecision\": " + inferredPrecision
                + ", \"selectedPrecision\": " + selectedPrecision
                + ", \"definedScale\": " + definedScale
                + ", \"inferredScale\": " + inferredScale
                + ", \"selectedScale\": " + selectedScale
                + ", \"definedNullability\": " + definedNullability
                + ", \"inferredNullability\": " + inferredNullability
                + ", \"selectedNullability\": " + selectedNullability
                + ", \"definedIsEmpty\": " + definedIsEmpty
                + ", \"inferredIsEmpty\": " + inferredIsEmpty
                + ", \"selectedIsEmpty\": " + selectedIsEmpty
                + ", \"inferredIsConstant\": " + inferredIsConstant
                + ", \"selectedIsConstant\": " + selectedIsConstant
                + " }";
    }

}
