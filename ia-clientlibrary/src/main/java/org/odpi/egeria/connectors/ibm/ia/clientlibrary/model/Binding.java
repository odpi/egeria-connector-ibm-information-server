/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Binding {

    @JacksonXmlProperty(isAttribute = true) private String var;
    @JacksonXmlProperty(localName = "Column")
    private Column column;
    @JacksonXmlProperty(localName = "Constant")
    private Constant constant;
    @JacksonXmlProperty(localName = "Variable")
    private Variable variable;

    public String getVar() { return var; }
    public void setVar(String var) { this.var = var; }

    public Column getColumn() { return column; }
    public void setColumn(Column column) { this.column = column; }

    public Constant getConstant() { return constant; }
    public void setConstant(Constant constant) { this.constant = constant; }

    public Variable getVariable() { return variable; }
    public void setVariable(Variable variable) { this.variable = variable; }

    @Override
    public String toString() {
        return "{ \"var\": \"" + var
                + "\", \"Column\": " + (column == null ? "{}" : column.toString())
                + ", \"Constant\": " + (constant == null ? "{}" : constant.toString())
                + ", \"Variable\": " + (variable == null ? "{}" : variable.toString())
                + " }";
    }

}
