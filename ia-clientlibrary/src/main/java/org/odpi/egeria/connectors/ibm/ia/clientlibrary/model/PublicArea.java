/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PublicArea {

    @JacksonXmlProperty(localName = "DataRuleDefinitions")
    private DataRuleDefinitions dataRuleDefinitions;

    public DataRuleDefinitions getDataRuleDefinitions() { return dataRuleDefinitions; }
    public void setDataRuleDefinitions(DataRuleDefinitions dataRuleDefinitions) { this.dataRuleDefinitions = dataRuleDefinitions; }

    @Override
    public String toString() {
        return "{ \"DataRuleDefinitions\": " + (dataRuleDefinitions == null ? "{}" : dataRuleDefinitions.toString())
                + " }";
    }

}
