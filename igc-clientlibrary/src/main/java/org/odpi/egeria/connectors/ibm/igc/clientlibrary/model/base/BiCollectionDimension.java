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
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;

/**
 * POJO for the {@code bi_collection_dimension} asset type in IGC, displayed as '{@literal BI Collection Dimension}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=BiCollectionDimension.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("bi_collection_dimension")
public class BiCollectionDimension extends MainObject {

    @JsonProperty("bi_cube")
    protected BiCube biCube;

    @JsonProperty("business_name")
    protected String businessName;

    @JsonProperty("references_bi_collection")
    protected BiCollection referencesBiCollection;

    @JsonProperty("sequence")
    protected Number sequence;

    @JsonProperty("uses_olap_hierarchy")
    protected BiHierarchy usesOlapHierarchy;

    /**
     * Retrieve the {@code bi_cube} property (displayed as '{@literal BI Cube}') of the object.
     * @return {@code BiCube}
     */
    @JsonProperty("bi_cube")
    public BiCube getBiCube() { return this.biCube; }

    /**
     * Set the {@code bi_cube} property (displayed as {@code BI Cube}) of the object.
     * @param biCube the value to set
     */
    @JsonProperty("bi_cube")
    public void setBiCube(BiCube biCube) { this.biCube = biCube; }

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
     * Retrieve the {@code references_bi_collection} property (displayed as '{@literal References BI Collection}') of the object.
     * @return {@code BiCollection}
     */
    @JsonProperty("references_bi_collection")
    public BiCollection getReferencesBiCollection() { return this.referencesBiCollection; }

    /**
     * Set the {@code references_bi_collection} property (displayed as {@code References BI Collection}) of the object.
     * @param referencesBiCollection the value to set
     */
    @JsonProperty("references_bi_collection")
    public void setReferencesBiCollection(BiCollection referencesBiCollection) { this.referencesBiCollection = referencesBiCollection; }

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
     * Retrieve the {@code uses_olap_hierarchy} property (displayed as '{@literal Uses OLAP Hierarchy}') of the object.
     * @return {@code BiHierarchy}
     */
    @JsonProperty("uses_olap_hierarchy")
    public BiHierarchy getUsesOlapHierarchy() { return this.usesOlapHierarchy; }

    /**
     * Set the {@code uses_olap_hierarchy} property (displayed as {@code Uses OLAP Hierarchy}) of the object.
     * @param usesOlapHierarchy the value to set
     */
    @JsonProperty("uses_olap_hierarchy")
    public void setUsesOlapHierarchy(BiHierarchy usesOlapHierarchy) { this.usesOlapHierarchy = usesOlapHierarchy; }

}
