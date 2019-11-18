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
 * POJO for the {@code information_server_report_(database)} asset type in IGC, displayed as '{@literal Information Server Report (Database)}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=InformationServerReportDatabase.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("information_server_report_(database)")
public class InformationServerReportDatabase extends Reference {

    @JsonProperty("creator")
    protected String creator;

    @JsonProperty("data_source")
    protected ItemList<MainObject> dataSource;

    @JsonProperty("description")
    protected String description;

    @JsonProperty("name")
    protected String name;

    @JsonProperty("native_id")
    protected String nativeId;

    @JsonProperty("product")
    protected String product;

    /**
     * Retrieve the {@code creator} property (displayed as '{@literal Creator}') of the object.
     * @return {@code String}
     */
    @JsonProperty("creator")
    public String getCreator() { return this.creator; }

    /**
     * Set the {@code creator} property (displayed as {@code Creator}) of the object.
     * @param creator the value to set
     */
    @JsonProperty("creator")
    public void setCreator(String creator) { this.creator = creator; }

    /**
     * Retrieve the {@code data_source} property (displayed as '{@literal Data Source}') of the object.
     * @return {@code ItemList<MainObject>}
     */
    @JsonProperty("data_source")
    public ItemList<MainObject> getDataSource() { return this.dataSource; }

    /**
     * Set the {@code data_source} property (displayed as {@code Data Source}) of the object.
     * @param dataSource the value to set
     */
    @JsonProperty("data_source")
    public void setDataSource(ItemList<MainObject> dataSource) { this.dataSource = dataSource; }

    /**
     * Retrieve the {@code description} property (displayed as '{@literal Description}') of the object.
     * @return {@code String}
     */
    @JsonProperty("description")
    public String getDescription() { return this.description; }

    /**
     * Set the {@code description} property (displayed as {@code Description}) of the object.
     * @param description the value to set
     */
    @JsonProperty("description")
    public void setDescription(String description) { this.description = description; }

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
     * Retrieve the {@code product} property (displayed as '{@literal Product}') of the object.
     * @return {@code String}
     */
    @JsonProperty("product")
    public String getProduct() { return this.product; }

    /**
     * Set the {@code product} property (displayed as {@code Product}) of the object.
     * @param product the value to set
     */
    @JsonProperty("product")
    public void setProduct(String product) { this.product = product; }

}
