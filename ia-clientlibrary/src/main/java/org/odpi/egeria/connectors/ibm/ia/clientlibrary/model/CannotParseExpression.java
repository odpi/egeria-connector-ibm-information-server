/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CannotParseExpression extends Error {

    @JacksonXmlProperty(isAttribute = true) private Integer errorCode;
    @JacksonXmlProperty(isAttribute = true) private Integer startIndex;
    @JacksonXmlProperty(isAttribute = true) private Integer endIndex;
    private String expression;

    public Integer getErrorCode() { return errorCode; }
    public void setErrorCode(Integer errorCode) { this.errorCode = errorCode; }

    public Integer getStartIndex() { return startIndex; }
    public void setStartIndex(Integer startIndex) { this.startIndex = startIndex; }

    public Integer getEndIndex() { return endIndex; }
    public void setEndIndex(Integer endIndex) { this.endIndex = endIndex; }

    public String getExpression() { return expression; }
    public void setExpression(String expression) { this.expression = expression; }

    @Override
    public String toString() {
        String parent = super.toString();
        return parent.substring(0, parent.length() - 2)
                + ", \"errorCode\": " + errorCode
                + ", \"startIndex\": " + startIndex
                + ", \"endIndex\": " + endIndex
                + ", \"expression\": \"" + expression
                + "\" }";
    }

}
