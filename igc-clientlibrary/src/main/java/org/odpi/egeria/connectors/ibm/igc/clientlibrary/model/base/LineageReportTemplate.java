/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
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
 * POJO for the {@code lineage_report_template} asset type in IGC, displayed as '{@literal Lineage Report Template}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=LineageReportTemplate.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("lineage_report_template")
public class LineageReportTemplate extends Reference {

    @JsonProperty("asset_display_properties")
    protected InformationAsset assetDisplayProperties;

    @JsonProperty("long_description")
    protected String longDescription;

    @JsonProperty("name")
    protected String name;

    @JsonProperty("native_id")
    protected String nativeId;

    @JsonProperty("uses_lineage_filter")
    protected ItemList<Lineagefilter> usesLineageFilter;

    /**
     * Retrieve the {@code asset_display_properties} property (displayed as '{@literal Asset Type Properties}') of the object.
     * @return {@code InformationAsset}
     */
    @JsonProperty("asset_display_properties")
    public InformationAsset getAssetDisplayProperties() { return this.assetDisplayProperties; }

    /**
     * Set the {@code asset_display_properties} property (displayed as {@code Asset Type Properties}) of the object.
     * @param assetDisplayProperties the value to set
     */
    @JsonProperty("asset_display_properties")
    public void setAssetDisplayProperties(InformationAsset assetDisplayProperties) { this.assetDisplayProperties = assetDisplayProperties; }

    /**
     * Retrieve the {@code long_description} property (displayed as '{@literal Description}') of the object.
     * @return {@code String}
     */
    @JsonProperty("long_description")
    public String getLongDescription() { return this.longDescription; }

    /**
     * Set the {@code long_description} property (displayed as {@code Description}) of the object.
     * @param longDescription the value to set
     */
    @JsonProperty("long_description")
    public void setLongDescription(String longDescription) { this.longDescription = longDescription; }

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
     * Retrieve the {@code native_id} property (displayed as '{@literal Native ID}') of the object.
     * @return {@code String}
     */
    @JsonProperty("native_id")
    public String getNativeId() { return this.nativeId; }

    /**
     * Set the {@code native_id} property (displayed as {@code Native ID}) of the object.
     * @param nativeId the value to set
     */
    @JsonProperty("native_id")
    public void setNativeId(String nativeId) { this.nativeId = nativeId; }

    /**
     * Retrieve the {@code uses_lineage_filter} property (displayed as '{@literal Uses Lineage Filter}') of the object.
     * @return {@code ItemList<Lineagefilter>}
     */
    @JsonProperty("uses_lineage_filter")
    public ItemList<Lineagefilter> getUsesLineageFilter() { return this.usesLineageFilter; }

    /**
     * Set the {@code uses_lineage_filter} property (displayed as {@code Uses Lineage Filter}) of the object.
     * @param usesLineageFilter the value to set
     */
    @JsonProperty("uses_lineage_filter")
    public void setUsesLineageFilter(ItemList<Lineagefilter> usesLineageFilter) { this.usesLineageFilter = usesLineageFilter; }

}
