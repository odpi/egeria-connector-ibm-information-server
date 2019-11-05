/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeName;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.PUBLIC_ONLY;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;

/**
 * POJO for the {@code bi_report_section} asset type in IGC, displayed as '{@literal BI Report Section}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("bi_report_section")
public class BiReportSection extends Reference {

    @JsonProperty("alias_(business_name)")
    protected String aliasBusinessName;

    @JsonProperty("bi_report")
    protected BiReport biReport;

    @JsonProperty("bi_report_fields")
    protected ItemList<Reportobject> biReportFields;

    @JsonProperty("contained_in_report_section")
    protected BiReportSection containedInReportSection;

    @JsonProperty("contains_sub_section")
    protected ItemList<BiReportSection> containsSubSection;

    @JsonProperty("label")
    protected String label;

    @JsonProperty("name")
    protected String name;

    @JsonProperty("sequence")
    protected Number sequence;

    @JsonProperty("short_description")
    protected String shortDescription;

    /**
     * Valid values are:
     * <ul>
     *   <li>CHART (displayed in the UI as 'CHART')</li>
     *   <li>LIST (displayed in the UI as 'LIST')</li>
     *   <li>MATRIX (displayed in the UI as 'MATRIX')</li>
     *   <li>TABLE (displayed in the UI as 'TABLE')</li>
     *   <li>PAGE (displayed in the UI as 'PAGE')</li>
     *   <li>PAGEBODY (displayed in the UI as 'PAGEBODY')</li>
     *   <li>PAGEFOOTER (displayed in the UI as 'PAGEFOOTER')</li>
     *   <li>PAGEHEADER (displayed in the UI as 'PAGEHEADER')</li>
     *   <li>RECTANGLE (displayed in the UI as 'RECTANGLE')</li>
     *   <li>TEXT (displayed in the UI as 'TEXT')</li>
     * </ul>
     */
    @JsonProperty("type")
    protected String type;

    /**
     * Retrieve the {@code alias_(business_name)} property (displayed as '{@literal Alias (Business Name)}') of the object.
     * @return {@code String}
     */
    @JsonProperty("alias_(business_name)")
    public String getAliasBusinessName() { return this.aliasBusinessName; }

    /**
     * Set the {@code alias_(business_name)} property (displayed as {@code Alias (Business Name)}) of the object.
     * @param aliasBusinessName the value to set
     */
    @JsonProperty("alias_(business_name)")
    public void setAliasBusinessName(String aliasBusinessName) { this.aliasBusinessName = aliasBusinessName; }

    /**
     * Retrieve the {@code bi_report} property (displayed as '{@literal BI Report}') of the object.
     * @return {@code BiReport}
     */
    @JsonProperty("bi_report")
    public BiReport getBiReport() { return this.biReport; }

    /**
     * Set the {@code bi_report} property (displayed as {@code BI Report}) of the object.
     * @param biReport the value to set
     */
    @JsonProperty("bi_report")
    public void setBiReport(BiReport biReport) { this.biReport = biReport; }

    /**
     * Retrieve the {@code bi_report_fields} property (displayed as '{@literal BI Report Fields}') of the object.
     * @return {@code ItemList<Reportobject>}
     */
    @JsonProperty("bi_report_fields")
    public ItemList<Reportobject> getBiReportFields() { return this.biReportFields; }

    /**
     * Set the {@code bi_report_fields} property (displayed as {@code BI Report Fields}) of the object.
     * @param biReportFields the value to set
     */
    @JsonProperty("bi_report_fields")
    public void setBiReportFields(ItemList<Reportobject> biReportFields) { this.biReportFields = biReportFields; }

    /**
     * Retrieve the {@code contained_in_report_section} property (displayed as '{@literal Contained in Report Section}') of the object.
     * @return {@code BiReportSection}
     */
    @JsonProperty("contained_in_report_section")
    public BiReportSection getContainedInReportSection() { return this.containedInReportSection; }

    /**
     * Set the {@code contained_in_report_section} property (displayed as {@code Contained in Report Section}) of the object.
     * @param containedInReportSection the value to set
     */
    @JsonProperty("contained_in_report_section")
    public void setContainedInReportSection(BiReportSection containedInReportSection) { this.containedInReportSection = containedInReportSection; }

    /**
     * Retrieve the {@code contains_sub_section} property (displayed as '{@literal Contains Sub Section}') of the object.
     * @return {@code ItemList<BiReportSection>}
     */
    @JsonProperty("contains_sub_section")
    public ItemList<BiReportSection> getContainsSubSection() { return this.containsSubSection; }

    /**
     * Set the {@code contains_sub_section} property (displayed as {@code Contains Sub Section}) of the object.
     * @param containsSubSection the value to set
     */
    @JsonProperty("contains_sub_section")
    public void setContainsSubSection(ItemList<BiReportSection> containsSubSection) { this.containsSubSection = containsSubSection; }

    /**
     * Retrieve the {@code label} property (displayed as '{@literal Label}') of the object.
     * @return {@code String}
     */
    @JsonProperty("label")
    public String getLabel() { return this.label; }

    /**
     * Set the {@code label} property (displayed as {@code Label}) of the object.
     * @param label the value to set
     */
    @JsonProperty("label")
    public void setLabel(String label) { this.label = label; }

    /**
     * Retrieve the {@code name} property (displayed as '{@literal Name}') of the object.
     * @return {@code String}
     */
    @JsonProperty("name")
    public String getTheName() { return this.name; }

    /**
     * Set the {@code name} property (displayed as {@code Name}) of the object.
     * @param name the value to set
     */
    @JsonProperty("name")
    public void setTheName(String name) { this.name = name; }

    /**
     * Retrieve the {@code sequence} property (displayed as '{@literal Sequence}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("sequence")
    public Number getSequence() { return this.sequence; }

    /**
     * Set the {@code sequence} property (displayed as {@code Sequence}) of the object.
     * @param sequence the value to set
     */
    @JsonProperty("sequence")
    public void setSequence(Number sequence) { this.sequence = sequence; }

    /**
     * Retrieve the {@code short_description} property (displayed as '{@literal Short Description}') of the object.
     * @return {@code String}
     */
    @JsonProperty("short_description")
    public String getShortDescription() { return this.shortDescription; }

    /**
     * Set the {@code short_description} property (displayed as {@code Short Description}) of the object.
     * @param shortDescription the value to set
     */
    @JsonProperty("short_description")
    public void setShortDescription(String shortDescription) { this.shortDescription = shortDescription; }

    /**
     * Retrieve the {@code type} property (displayed as '{@literal Type}') of the object.
     * @return {@code String}
     */
    @JsonProperty("type")
    public String getTheType() { return this.type; }

    /**
     * Set the {@code type} property (displayed as {@code Type}) of the object.
     * @param type the value to set
     */
    @JsonProperty("type")
    public void setTheType(String type) { this.type = type; }

}
