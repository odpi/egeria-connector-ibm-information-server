/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DataRuleDefinition extends AbstractRuleDefinition {

    private String expression;

    public String getExpression() { return expression; }
    public void setExpression(String expression) { this.expression = expression; }

    @Override
    public String toString() {
        String parent = super.toString();
        return parent.substring(0, parent.length() - 2)
                + ", \"expression\": \"" + expression + "\" }";
    }

}
