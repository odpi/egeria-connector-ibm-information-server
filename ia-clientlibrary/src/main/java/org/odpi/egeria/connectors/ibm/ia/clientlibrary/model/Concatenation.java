/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Concatenation {

    @JacksonXmlProperty(isAttribute = true) private String separator;
    @JacksonXmlProperty(isAttribute = true) private String nullValueReplacement;
    @JacksonXmlProperty(isAttribute = true) private String padChar;
    @JacksonXmlProperty(isAttribute = true) private String quoteChar;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Column")
    private List<Column> columns;

    public String getSeparator() { return separator; }
    public void setSeparator(String separator) { this.separator = separator; }

    public String getNullValueReplacement() { return nullValueReplacement; }
    public void setNullValueReplacement(String nullValueReplacement) { this.nullValueReplacement = nullValueReplacement; }

    public String getPadChar() { return padChar; }
    public void setPadChar(String padChar) { this.padChar = padChar; }

    public String getQuoteChar() { return quoteChar; }
    public void setQuoteChar(String quoteChar) { this.quoteChar = quoteChar; }

    public List<Column> getColumns() { return columns; }
    public void setColumns(List<Column> columns) { this.columns = columns; }

    @Override
    public String toString() {
        return "{ \"separator\": \"" + separator
                + "\", \"nullValueReplacement\": \"" + nullValueReplacement
                + "\", \"padChar\": \"" + padChar
                + "\", \"quoteChar\": \"" + quoteChar
                + "\", \"Columns\": " + (columns == null ? "[]" : columns.toString())
                + " }";
    }

}
