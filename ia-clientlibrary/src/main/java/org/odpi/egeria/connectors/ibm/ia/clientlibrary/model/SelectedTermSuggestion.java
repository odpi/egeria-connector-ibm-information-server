/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SelectedTermSuggestion {

    @JacksonXmlProperty(isAttribute = true) private SuggestionType suggestionType;

    public SuggestionType getSuggestionType() { return suggestionType; }
    public void setSuggestionType(SuggestionType suggestionType) { this.suggestionType = suggestionType; }

    @Override
    public String toString() {
        return "{ \"suggestionType\": \"" + suggestionType + "\" }";
    }

}
