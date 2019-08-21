/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LandingTable {

    @JacksonXmlProperty(isAttribute = true) private String name;
    @JacksonXmlProperty(isAttribute = true) private LandingTableType type;
    @JacksonXmlProperty(isAttribute = true) private String schema;
    @JacksonXmlProperty(isAttribute = true) private UpdateMethodType updateMethodType;
    @JacksonXmlProperty(isAttribute = true) private Integer maxRuns;
    @JacksonXmlProperty(isAttribute = true) private Boolean includeToProject;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public LandingTableType getType() { return type; }
    public void setType(LandingTableType type) { this.type = type; }

    public String getSchema() { return schema; }
    public void setSchema(String schema) { this.schema = schema; }

    public UpdateMethodType getUpdateMethodType() { return updateMethodType; }
    public void setUpdateMethodType(UpdateMethodType updateMethodType) { this.updateMethodType = updateMethodType; }

    public Integer getMaxRuns() { return maxRuns; }
    public void setMaxRuns(Integer maxRuns) { this.maxRuns = maxRuns; }

    public Boolean isIncludeToProject() { return includeToProject; }
    public void setIncludeToProject(Boolean includeToProject) { this.includeToProject = includeToProject; }

    @Override
    public String toString() {
        return "{ \"nme\": \"" + name
                + "\", \"type\": \"" + type
                + "\", \"schema\": \"" + schema
                + "\", \"updateMethodType\": \"" + updateMethodType
                + "\", \"maxRuns\": " + maxRuns
                + ", \"includeToProject\": " + includeToProject
                + " }";
    }

}
