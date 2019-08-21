/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VirtualColumn extends Column {

    @JacksonXmlProperty(localName = "Concatenation")
    private Concatenation concatenation;
    @JacksonXmlProperty(localName = "DataRuleExpression")
    private String dataRuleExpression;
    @JacksonXmlProperty(localName = "SQLExpression")
    private SQLExpression sqlExpression;

    public Concatenation getConcatenation() { return concatenation; }
    public void setConcatenation(Concatenation concatenation) { this.concatenation = concatenation; }

    public String getDataRuleExpression() { return dataRuleExpression; }
    public void setDataRuleExpression(String dataRuleExpression) { this.dataRuleExpression = dataRuleExpression; }

    public SQLExpression getSqlExpression() { return sqlExpression; }
    public void setSqlExpression(SQLExpression sqlExpression) { this.sqlExpression = sqlExpression; }

    @Override
    public String toString() {
        String parent = super.toString();
        return parent.substring(0, parent.length() - 2)
                + ", \"Concatenation\": " + (concatenation == null ? "{}" : concatenation.toString())
                + ", \"DataRuleExpression\": \"" + dataRuleExpression
                + "\", \"SQLExpression\": " + (sqlExpression == null ? "{}" : sqlExpression.toString())
                + " }";
    }
}
