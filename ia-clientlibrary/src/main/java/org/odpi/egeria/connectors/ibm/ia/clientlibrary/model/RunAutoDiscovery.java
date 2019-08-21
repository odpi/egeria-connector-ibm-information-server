/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RunAutoDiscovery extends AbstractTask {

    @JacksonXmlProperty(isAttribute = true) private String services;
    @JacksonXmlProperty(isAttribute = true) private String user;
    @JacksonXmlProperty(isAttribute = true) private String correlationId;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Table")
    private List<Table> tables;

    public String getServices() { return services; }
    public void setServices(String services) { this.services = services; }

    public String getUser() { return user; }
    public void setUser(String user) { this.user = user; }

    public String getCorrelationId() { return correlationId; }
    public void setCorrelationId(String correlationId) { this.correlationId = correlationId; }

    public List<Table> getTables() { return tables; }
    public void setTables(List<Table> tables) { this.tables = tables; }

    @Override
    public String toString() {
        String parent = super.toString();
        return parent.substring(0, parent.length() - 2)
                + ", \"services\": \"" + services
                + "\", \"user\": \"" + user
                + "\", \"correlationId\": \"" + correlationId
                + "\", \"Tables\": " + (tables == null ? "[]" : tables.toString())
                + " }";
    }

}
