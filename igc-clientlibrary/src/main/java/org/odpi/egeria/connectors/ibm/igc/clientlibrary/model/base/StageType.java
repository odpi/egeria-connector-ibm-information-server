/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeName;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.PUBLIC_ONLY;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;

/**
 * POJO for the {@code stage_type} asset type in IGC, displayed as '{@literal Stage Type}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=StageType.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonSubTypes({
        @JsonSubTypes.Type(value = DsstageType.class, name = "dsstage_type"),
})
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("stage_type")
public class StageType extends InformationAsset {

    @JsonProperty("author")
    protected String author;

    @JsonProperty("copyright")
    protected String copyright;

    @JsonProperty("steward")
    protected ItemList<Steward> steward;

    @JsonProperty("vendor")
    protected String vendor;

    @JsonProperty("version")
    protected String version;

    /**
     * Retrieve the {@code author} property (displayed as '{@literal Author}') of the object.
     * @return {@code String}
     */
    @JsonProperty("author")
    public String getAuthor() { return this.author; }

    /**
     * Set the {@code author} property (displayed as {@code Author}) of the object.
     * @param author the value to set
     */
    @JsonProperty("author")
    public void setAuthor(String author) { this.author = author; }

    /**
     * Retrieve the {@code copyright} property (displayed as '{@literal Copyright}') of the object.
     * @return {@code String}
     */
    @JsonProperty("copyright")
    public String getCopyright() { return this.copyright; }

    /**
     * Set the {@code copyright} property (displayed as {@code Copyright}) of the object.
     * @param copyright the value to set
     */
    @JsonProperty("copyright")
    public void setCopyright(String copyright) { this.copyright = copyright; }

    /**
     * Retrieve the {@code steward} property (displayed as '{@literal Steward}') of the object.
     * @return {@code ItemList<Steward>}
     */
    @JsonProperty("steward")
    public ItemList<Steward> getSteward() { return this.steward; }

    /**
     * Set the {@code steward} property (displayed as {@code Steward}) of the object.
     * @param steward the value to set
     */
    @JsonProperty("steward")
    public void setSteward(ItemList<Steward> steward) { this.steward = steward; }

    /**
     * Retrieve the {@code vendor} property (displayed as '{@literal Vendor}') of the object.
     * @return {@code String}
     */
    @JsonProperty("vendor")
    public String getVendor() { return this.vendor; }

    /**
     * Set the {@code vendor} property (displayed as {@code Vendor}) of the object.
     * @param vendor the value to set
     */
    @JsonProperty("vendor")
    public void setVendor(String vendor) { this.vendor = vendor; }

    /**
     * Retrieve the {@code version} property (displayed as '{@literal Version}') of the object.
     * @return {@code String}
     */
    @JsonProperty("version")
    public String getVersion() { return this.version; }

    /**
     * Set the {@code version} property (displayed as {@code Version}) of the object.
     * @param version the value to set
     */
    @JsonProperty("version")
    public void setVersion(String version) { this.version = version; }

}
