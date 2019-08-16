/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ColumnConstraints {

    @JacksonXmlProperty(isAttribute = true) private DataTypeCode dataType;
    @JacksonXmlProperty(isAttribute = true) private Integer length;
    @JacksonXmlProperty(isAttribute = true) private Integer precision;
    @JacksonXmlProperty(isAttribute = true) private Integer scale;
    @JacksonXmlProperty(isAttribute = true) private String classCode;
    @JacksonXmlProperty(isAttribute = true) private Boolean isNullable;
    @JacksonXmlProperty(isAttribute = true) private Boolean isUnique;
    @JacksonXmlProperty(isAttribute = true) private String minValue;
    @JacksonXmlProperty(isAttribute = true) private String maxValue;
    @JacksonXmlElementWrapper(localName = "InvalidFormats")
    @JacksonXmlProperty(localName = "Format") private List<String> invalidFormats;

    public DataTypeCode getDataType() { return dataType; }
    public void setDataType(DataTypeCode dataType) { this.dataType = dataType; }

    public Integer getLength() { return length; }
    public void setLength(Integer length) { this.length = length; }

    public Integer getPrecision() { return precision; }
    public void setPrecision(Integer precision) { this.precision = precision; }

    public Integer getScale() { return scale; }
    public void setScale(Integer scale) { this.scale = scale; }

    public String getClassCode() { return classCode; }
    public void setClassCode(String classCode) { this.classCode = classCode; }

    public Boolean isNullable() { return isNullable; }
    public void setNullable(Boolean nullable) { isNullable = nullable; }

    public Boolean isUnique() { return isUnique; }
    public void setUnique(Boolean unique) { isUnique = unique; }

    public String getMinValue() { return minValue; }
    public void setMinValue(String minValue) { this.minValue = minValue; }

    public String getMaxValue() { return maxValue; }
    public void setMaxValue(String maxValue) { this.maxValue = maxValue; }

    public List<String> getInvalidFormats() { return invalidFormats; }
    public void setInvalidFormats(List<String> invalidFormats) { this.invalidFormats = invalidFormats; }

    @Override
    public String toString() {
        return "{ \"dataType\": \"" + dataType
                + "\", \"length\": " + length
                + ", \"precision\": " + precision
                + ", \"scale\": " + scale
                + ", \"classCode\": \"" + classCode
                + "\", \"isNullable\": " + isNullable
                + ", \"isUnique\": " + isUnique
                + ", \"minValue\": \"" + minValue
                + "\", \"maxValue\": \"" + maxValue
                + "\", \"InvalidFormats\": " + (invalidFormats == null ? "[]" : invalidFormats.toString())
                + " }";
    }

}
