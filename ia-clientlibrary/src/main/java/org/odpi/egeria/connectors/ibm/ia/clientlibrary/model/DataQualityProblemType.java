/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DataQualityProblemType {

    @JacksonXmlProperty(isAttribute = true) private String code;
    @JacksonXmlProperty(isAttribute = true) private String rule;

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getRule() { return rule; }
    public void setRule(String rule) { this.rule = rule; }

    @Override
    public String toString() {
        return "{ \"code\": \"" + code + "\", \"rule\": \"" + rule +"\" }";
    }

}
