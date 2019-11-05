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
 * POJO for the {@code olap_join} asset type in IGC, displayed as '{@literal OLAP Join}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("olap_join")
public class OlapJoin extends Reference {

    @JsonProperty("bi_model")
    protected BiModel biModel;

    @JsonProperty("business_name")
    protected String businessName;

    @JsonProperty("condition")
    protected String condition;

    @JsonProperty("contains_references")
    protected ItemList<Olapjoinref> containsReferences;

    @JsonProperty("name")
    protected String name;

    @JsonProperty("referenced_by_an_olap_cube")
    protected ItemList<BiCube> referencedByAnOlapCube;

    @JsonProperty("short_description")
    protected String shortDescription;

    /**
     * Valid values are:
     * <ul>
     *   <li>INNER (displayed in the UI as 'INNER')</li>
     *   <li>FULL_OUTER (displayed in the UI as 'FULL_OUTER')</li>
     *   <li>LEFT_OUTER (displayed in the UI as 'LEFT_OUTER')</li>
     *   <li>RIGHT_OUTER (displayed in the UI as 'RIGHT_OUTER')</li>
     * </ul>
     */
    @JsonProperty("type")
    protected String type;

    /**
     * Retrieve the {@code bi_model} property (displayed as '{@literal OLAP Model}') of the object.
     * @return {@code BiModel}
     */
    @JsonProperty("bi_model")
    public BiModel getBiModel() { return this.biModel; }

    /**
     * Set the {@code bi_model} property (displayed as {@code OLAP Model}) of the object.
     * @param biModel the value to set
     */
    @JsonProperty("bi_model")
    public void setBiModel(BiModel biModel) { this.biModel = biModel; }

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
     * Retrieve the {@code condition} property (displayed as '{@literal Condition}') of the object.
     * @return {@code String}
     */
    @JsonProperty("condition")
    public String getCondition() { return this.condition; }

    /**
     * Set the {@code condition} property (displayed as {@code Condition}) of the object.
     * @param condition the value to set
     */
    @JsonProperty("condition")
    public void setCondition(String condition) { this.condition = condition; }

    /**
     * Retrieve the {@code contains_references} property (displayed as '{@literal Contains References}') of the object.
     * @return {@code ItemList<Olapjoinref>}
     */
    @JsonProperty("contains_references")
    public ItemList<Olapjoinref> getContainsReferences() { return this.containsReferences; }

    /**
     * Set the {@code contains_references} property (displayed as {@code Contains References}) of the object.
     * @param containsReferences the value to set
     */
    @JsonProperty("contains_references")
    public void setContainsReferences(ItemList<Olapjoinref> containsReferences) { this.containsReferences = containsReferences; }

    /**
     * Retrieve the {@code name} property (displayed as '{@literal Join Name}') of the object.
     * @return {@code String}
     */
    @JsonProperty("name")
    public String getTheName() { return this.name; }

    /**
     * Set the {@code name} property (displayed as {@code Join Name}) of the object.
     * @param name the value to set
     */
    @JsonProperty("name")
    public void setTheName(String name) { this.name = name; }

    /**
     * Retrieve the {@code referenced_by_an_olap_cube} property (displayed as '{@literal Referenced by an OLAP Cube}') of the object.
     * @return {@code ItemList<BiCube>}
     */
    @JsonProperty("referenced_by_an_olap_cube")
    public ItemList<BiCube> getReferencedByAnOlapCube() { return this.referencedByAnOlapCube; }

    /**
     * Set the {@code referenced_by_an_olap_cube} property (displayed as {@code Referenced by an OLAP Cube}) of the object.
     * @param referencedByAnOlapCube the value to set
     */
    @JsonProperty("referenced_by_an_olap_cube")
    public void setReferencedByAnOlapCube(ItemList<BiCube> referencedByAnOlapCube) { this.referencedByAnOlapCube = referencedByAnOlapCube; }

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
