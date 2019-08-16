/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DataStore {

    @JacksonXmlProperty(isAttribute = true) private String name;
    @JacksonXmlProperty(isAttribute = true) private String host;
    @JacksonXmlProperty(isAttribute = true) private ConnectorType connector;
    @JacksonXmlProperty(isAttribute = true) private String connectionString;
    @JacksonXmlProperty(isAttribute = true) private String user;
    @JacksonXmlProperty(isAttribute = true) private String password;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getHost() { return host; }
    public void setHost(String host) { this.host = host; }

    public ConnectorType getConnector() { return connector; }
    public void setConnector(ConnectorType connector) { this.connector = connector; }

    public String getConnectionString() { return connectionString; }
    public void setConnectionString(String connectionString) { this.connectionString = connectionString; }

    public String getUser() { return user; }
    public void setUser(String user) { this.user = user; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    @Override
    public String toString() {
        return "{ \"name\": \"" + name
                + "\", \"host\": \"" + host
                + "\", \"connector\": \"" + connector
                + "\", \"connectionString\": \"" + connectionString
                + "\", \"user\": \"" + user
                + "\", \"password\": \"" + password
                + "\" }";
    }

}
