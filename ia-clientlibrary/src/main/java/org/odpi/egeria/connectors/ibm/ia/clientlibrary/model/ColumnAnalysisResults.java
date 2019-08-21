/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ColumnAnalysisResults {

    @JacksonXmlProperty(localName = "RuntimeMetaData")
    private RuntimeMetaData runtimeMetaData;
    @JacksonXmlProperty(localName = "Cardinality")
    private Cardinality cardinality;
    @JacksonXmlProperty(localName = "DataType")
    private DataType dataType;
    @JacksonXmlProperty(localName = "DataClass")
    private DataClass dataClass;
    @JacksonXmlProperty(localName = "Format")
    private Format format;
    @JacksonXmlProperty(localName = "CompletenessAnalysis")
    private CompletenessAnalysis completenessAnalysis;
    @JacksonXmlProperty(localName = "DomainAnalysis")
    private DomainAnalysis domainAnalysis;
    @JacksonXmlProperty(localName = "FrequencyDistribution")
    private FrequencyDistribution frequencyDistribution;
    @JacksonXmlElementWrapper(localName = "Notes")
    @JacksonXmlProperty(localName = "Note")
    private List<Note> notes;
    @JacksonXmlElementWrapper(localName = "Terms")
    @JacksonXmlProperty(localName = "Term")
    private List<Term> terms;
    @JacksonXmlProperty(localName = "FormatDistribution")
    private FormatDistribution formatDistribution;

    public RuntimeMetaData getRuntimeMetaData() { return runtimeMetaData; }
    public void setRuntimeMetaData(RuntimeMetaData runtimeMetaData) { this.runtimeMetaData = runtimeMetaData; }

    public Cardinality getCardinality() { return cardinality; }
    public void setCardinality(Cardinality cardinality) { this.cardinality = cardinality; }

    public DataType getDataType() { return dataType; }
    public void setDataType(DataType dataType) { this.dataType = dataType; }

    public DataClass getDataClass() { return dataClass; }
    public void setDataClass(DataClass dataClass) { this.dataClass = dataClass; }

    public Format getFormat() { return format; }
    public void setFormat(Format format) { this.format = format; }

    public CompletenessAnalysis getCompletenessAnalysis() { return completenessAnalysis; }
    public void setCompletenessAnalysis(CompletenessAnalysis completenessAnalysis) { this.completenessAnalysis = completenessAnalysis; }

    public DomainAnalysis getDomainAnalysis() { return domainAnalysis; }
    public void setDomainAnalysis(DomainAnalysis domainAnalysis) { this.domainAnalysis = domainAnalysis; }

    public FrequencyDistribution getFrequencyDistribution() { return frequencyDistribution; }
    public void setFrequencyDistribution(FrequencyDistribution frequencyDistribution) { this.frequencyDistribution = frequencyDistribution; }

    public List<Note> getNotes() { return notes; }
    public void setNotes(List<Note> notes) { this.notes = notes; }

    public List<Term> getTerms() { return terms; }
    public void setTerms(List<Term> terms) { this.terms = terms; }

    public FormatDistribution getFormatDistribution() { return formatDistribution; }
    public void setFormatDistribution(FormatDistribution formatDistribution) { this.formatDistribution = formatDistribution; }

    @Override
    public String toString() {
        return "{ \"RuntimeMetaData\": " + (runtimeMetaData == null ? "{}" : runtimeMetaData.toString())
                + ", \"Cardinality\": " + (cardinality == null ? "{}" : cardinality.toString())
                + ", \"DataType\": " + (dataType == null ? "{}" : dataType.toString())
                + ", \"DataClass\": " + (dataClass == null ? "{}" : dataClass.toString())
                + ", \"Format\": " + (format == null ? "{}" : format.toString())
                + ", \"CompletenessAnalysis\": " + (completenessAnalysis == null ? "{}" : completenessAnalysis.toString())
                + ", \"DomainAnalysis\": " + (domainAnalysis == null ? "{}" : domainAnalysis.toString())
                + ", \"FrequencyDistribution\": " + (frequencyDistribution == null ? "{}" : frequencyDistribution.toString())
                + ", \"Notes\": " + (notes == null ? "[]" : notes.toString())
                + ", \"Terms\": " + (terms == null ? "[]" : terms.toString())
                + ", \"FormatDistribution\": " + (formatDistribution == null ? "{}" : formatDistribution.toString())
                + " }";
    }

}
