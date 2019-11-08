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
 * POJO for the {@code bi_olap_level_element} asset type in IGC, displayed as '{@literal BI OLAP Level Element}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=BiOlapLevelElement.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("bi_olap_level_element")
public class BiOlapLevelElement extends Reference {

    @JsonProperty("business_name")
    protected String businessName;

    @JsonProperty("defined_as_an_olap_member")
    protected BiCollectionMember definedAsAnOlapMember;

    @JsonProperty("defined_in_level")
    protected BiLevel definedInLevel;

    @JsonProperty("key_level")
    protected BiLevel keyLevel;

    @JsonProperty("name")
    protected String name;

    @JsonProperty("native_id")
    protected String nativeId;

    @JsonProperty("short_description")
    protected String shortDescription;

    /**
     * Retrieve the {@code business_name} property (displayed as '{@literal Business Name}') of the object.
     * @return {@code String}
     */
    @JsonProperty("business_name")
    public String getBusinessName() { return this.businessName; }

    /**
     * Set the {@code business_name} property (displayed as {@code Business Name}) of the object.
     * @param businessName the value to set
     */
    @JsonProperty("business_name")
    public void setBusinessName(String businessName) { this.businessName = businessName; }

    /**
     * Retrieve the {@code defined_as_an_olap_member} property (displayed as '{@literal Defined as an OLAP Member}') of the object.
     * @return {@code BiCollectionMember}
     */
    @JsonProperty("defined_as_an_olap_member")
    public BiCollectionMember getDefinedAsAnOlapMember() { return this.definedAsAnOlapMember; }

    /**
     * Set the {@code defined_as_an_olap_member} property (displayed as {@code Defined as an OLAP Member}) of the object.
     * @param definedAsAnOlapMember the value to set
     */
    @JsonProperty("defined_as_an_olap_member")
    public void setDefinedAsAnOlapMember(BiCollectionMember definedAsAnOlapMember) { this.definedAsAnOlapMember = definedAsAnOlapMember; }

    /**
     * Retrieve the {@code defined_in_level} property (displayed as '{@literal Defined in Level}') of the object.
     * @return {@code BiLevel}
     */
    @JsonProperty("defined_in_level")
    public BiLevel getDefinedInLevel() { return this.definedInLevel; }

    /**
     * Set the {@code defined_in_level} property (displayed as {@code Defined in Level}) of the object.
     * @param definedInLevel the value to set
     */
    @JsonProperty("defined_in_level")
    public void setDefinedInLevel(BiLevel definedInLevel) { this.definedInLevel = definedInLevel; }

    /**
     * Retrieve the {@code key_level} property (displayed as '{@literal Key Level}') of the object.
     * @return {@code BiLevel}
     */
    @JsonProperty("key_level")
    public BiLevel getKeyLevel() { return this.keyLevel; }

    /**
     * Set the {@code key_level} property (displayed as {@code Key Level}) of the object.
     * @param keyLevel the value to set
     */
    @JsonProperty("key_level")
    public void setKeyLevel(BiLevel keyLevel) { this.keyLevel = keyLevel; }

    /**
     * Retrieve the {@code name} property (displayed as '{@literal Element Name}') of the object.
     * @return {@code String}
     */
    @JsonProperty("name")
    public String getTheName() { return this.name; }

    /**
     * Set the {@code name} property (displayed as {@code Element Name}) of the object.
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

}
