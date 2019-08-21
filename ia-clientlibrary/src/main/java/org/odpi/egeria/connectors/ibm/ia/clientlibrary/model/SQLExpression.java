/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SQLExpression {

    @JacksonXmlProperty(isAttribute = true) private LogicalDataType returnType;
    @JacksonXmlProperty(isAttribute = true) private Integer returnLength;
    @JacksonXmlText private String expression;

    public LogicalDataType getReturnType() { return returnType; }
    public void setReturnType(LogicalDataType returnType) { this.returnType = returnType; }

    public Integer getReturnLength() { return returnLength; }
    public void setReturnLength(Integer returnLength) { this.returnLength = returnLength; }

    public String getExpression() { return expression; }
    public void setExpression(String expression) { this.expression = expression; }

    @Override
    public String toString() {
        return "{ \"returnType\": \"" + returnType
                + "\", \"returnLength\": \"" + returnLength
                + "\", \"expression\": \"" + expression
                + "\" }";
    }

}
