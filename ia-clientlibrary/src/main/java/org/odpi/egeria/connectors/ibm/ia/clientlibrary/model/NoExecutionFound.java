/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NoExecutionFound extends Error {

    @JacksonXmlProperty(isAttribute = true) private String ruleName;
    @JacksonXmlProperty(isAttribute = true) private String executionID;
    @JacksonXmlProperty(isAttribute = true) private ResourceType resourceType;

    public String getRuleName() { return ruleName; }
    public void setRuleName(String ruleName) { this.ruleName = ruleName; }

    public String getExecutionID() { return executionID; }
    public void setExecutionID(String executionID) { this.executionID = executionID; }

    public ResourceType getResourceType() { return resourceType; }
    public void setResourceType(ResourceType resourceType) { this.resourceType = resourceType; }

    @Override
    public String toString() {
        String parent = super.toString();
        return parent.substring(0, parent.length() - 2)
                + ", \"ruleName\": \"" + ruleName
                + "\", \"executionID\": \"" + executionID
                + "\", \"resourceType\": \"" + resourceType
                + "\" }";
    }

}
