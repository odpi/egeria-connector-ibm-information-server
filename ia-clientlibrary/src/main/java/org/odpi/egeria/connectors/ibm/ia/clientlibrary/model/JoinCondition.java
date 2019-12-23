/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JoinCondition {

    @JacksonXmlProperty(isAttribute = true) private String leftKey;
    @JacksonXmlProperty(isAttribute = true) private String rightKey;
    @JacksonXmlProperty(isAttribute = true) private String condition;
    @JacksonXmlProperty(isAttribute = true) private JoinType joinType = JoinType.INNER;

    public String getLeftKey() { return leftKey; }
    public void setLeftKey(String leftKey) { this.leftKey = leftKey; }

    public String getRightKey() { return rightKey; }
    public void setRightKey(String rightKey) { this.rightKey = rightKey; }

    public String getCondition() { return condition; }
    public void setCondition(String condition) { this.condition = condition; }

    public JoinType getJoinType() { return joinType; }
    public void setJoinType(JoinType joinType) { this.joinType = joinType; }

    @Override
    public String toString() {
        return "{ \"leftKey\": \"" + leftKey
                + "\", \"rightKey\": \"" + rightKey
                + "\", \"condition\": \"" + condition
                + "\", \"joinType\": \"" + joinType
                + "\" }";
    }

}
