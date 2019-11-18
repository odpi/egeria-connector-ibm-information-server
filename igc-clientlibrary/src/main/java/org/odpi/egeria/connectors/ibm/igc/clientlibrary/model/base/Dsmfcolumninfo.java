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

/**
 * POJO for the {@code dsmfcolumninfo} asset type in IGC, displayed as '{@literal DSMFColumnInfo}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=Dsmfcolumninfo.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("dsmfcolumninfo")
public class Dsmfcolumninfo extends Reference {

    @JsonProperty("a_xmeta_locking_root")
    protected String aXmetaLockingRoot;

    @JsonProperty("aggregation")
    protected String aggregation;

    @JsonProperty("column_value")
    protected String columnValue;

    @JsonProperty("name")
    protected String name;

    @JsonProperty("of_ds_stage")
    protected Stage ofDsStage;

    @JsonProperty("sort_link")
    protected String sortLink;

    @JsonProperty("sort_order")
    protected Number sortOrder;

    @JsonProperty("usage_class")
    protected String usageClass;

    /**
     * Retrieve the {@code a_xmeta_locking_root} property (displayed as '{@literal A XMeta Locking Root}') of the object.
     * @return {@code String}
     */
    @JsonProperty("a_xmeta_locking_root")
    public String getAXmetaLockingRoot() { return this.aXmetaLockingRoot; }

    /**
     * Set the {@code a_xmeta_locking_root} property (displayed as {@code A XMeta Locking Root}) of the object.
     * @param aXmetaLockingRoot the value to set
     */
    @JsonProperty("a_xmeta_locking_root")
    public void setAXmetaLockingRoot(String aXmetaLockingRoot) { this.aXmetaLockingRoot = aXmetaLockingRoot; }

    /**
     * Retrieve the {@code aggregation} property (displayed as '{@literal Aggregation}') of the object.
     * @return {@code String}
     */
    @JsonProperty("aggregation")
    public String getAggregation() { return this.aggregation; }

    /**
     * Set the {@code aggregation} property (displayed as {@code Aggregation}) of the object.
     * @param aggregation the value to set
     */
    @JsonProperty("aggregation")
    public void setAggregation(String aggregation) { this.aggregation = aggregation; }

    /**
     * Retrieve the {@code column_value} property (displayed as '{@literal Column Value}') of the object.
     * @return {@code String}
     */
    @JsonProperty("column_value")
    public String getColumnValue() { return this.columnValue; }

    /**
     * Set the {@code column_value} property (displayed as {@code Column Value}) of the object.
     * @param columnValue the value to set
     */
    @JsonProperty("column_value")
    public void setColumnValue(String columnValue) { this.columnValue = columnValue; }

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
     * Retrieve the {@code of_ds_stage} property (displayed as '{@literal Of DS Stage}') of the object.
     * @return {@code Stage}
     */
    @JsonProperty("of_ds_stage")
    public Stage getOfDsStage() { return this.ofDsStage; }

    /**
     * Set the {@code of_ds_stage} property (displayed as {@code Of DS Stage}) of the object.
     * @param ofDsStage the value to set
     */
    @JsonProperty("of_ds_stage")
    public void setOfDsStage(Stage ofDsStage) { this.ofDsStage = ofDsStage; }

    /**
     * Retrieve the {@code sort_link} property (displayed as '{@literal Sort Link}') of the object.
     * @return {@code String}
     */
    @JsonProperty("sort_link")
    public String getSortLink() { return this.sortLink; }

    /**
     * Set the {@code sort_link} property (displayed as {@code Sort Link}) of the object.
     * @param sortLink the value to set
     */
    @JsonProperty("sort_link")
    public void setSortLink(String sortLink) { this.sortLink = sortLink; }

    /**
     * Retrieve the {@code sort_order} property (displayed as '{@literal Sort Order}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("sort_order")
    public Number getSortOrder() { return this.sortOrder; }

    /**
     * Set the {@code sort_order} property (displayed as {@code Sort Order}) of the object.
     * @param sortOrder the value to set
     */
    @JsonProperty("sort_order")
    public void setSortOrder(Number sortOrder) { this.sortOrder = sortOrder; }

    /**
     * Retrieve the {@code usage_class} property (displayed as '{@literal Usage Class}') of the object.
     * @return {@code String}
     */
    @JsonProperty("usage_class")
    public String getUsageClass() { return this.usageClass; }

    /**
     * Set the {@code usage_class} property (displayed as {@code Usage Class}) of the object.
     * @param usageClass the value to set
     */
    @JsonProperty("usage_class")
    public void setUsageClass(String usageClass) { this.usageClass = usageClass; }

}
