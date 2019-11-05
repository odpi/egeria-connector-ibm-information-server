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
 * POJO for the {@code olapjoinref} asset type in IGC, displayed as '{@literal OLAPJoinRef}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("olapjoinref")
public class Olapjoinref extends Reference {

    @JsonProperty("alias")
    protected MainObject alias;

    /**
     * Valid values are:
     * <ul>
     *   <li>ONE_TO_ONE (displayed in the UI as 'ONE_TO_ONE')</li>
     *   <li>ONE_TO_MANY (displayed in the UI as 'ONE_TO_MANY')</li>
     *   <li>MANY_TO_ONE (displayed in the UI as 'MANY_TO_ONE')</li>
     *   <li>MANY_TO_MANY (displayed in the UI as 'MANY_TO_MANY')</li>
     * </ul>
     */
    @JsonProperty("cardinality")
    protected String cardinality;

    @JsonProperty("joins_database_table")
    protected Datagroup joinsDatabaseTable;

    @JsonProperty("joins_olap_collection")
    protected ItemList<BiCollection> joinsOlapCollection;

    @JsonProperty("sequence")
    protected Number sequence;

    /**
     * Retrieve the {@code alias} property (displayed as '{@literal Alias}') of the object.
     * @return {@code MainObject}
     */
    @JsonProperty("alias")
    public MainObject getAlias() { return this.alias; }

    /**
     * Set the {@code alias} property (displayed as {@code Alias}) of the object.
     * @param alias the value to set
     */
    @JsonProperty("alias")
    public void setAlias(MainObject alias) { this.alias = alias; }

    /**
     * Retrieve the {@code cardinality} property (displayed as '{@literal Cardinality}') of the object.
     * @return {@code String}
     */
    @JsonProperty("cardinality")
    public String getCardinality() { return this.cardinality; }

    /**
     * Set the {@code cardinality} property (displayed as {@code Cardinality}) of the object.
     * @param cardinality the value to set
     */
    @JsonProperty("cardinality")
    public void setCardinality(String cardinality) { this.cardinality = cardinality; }

    /**
     * Retrieve the {@code joins_database_table} property (displayed as '{@literal Joins Database Table}') of the object.
     * @return {@code Datagroup}
     */
    @JsonProperty("joins_database_table")
    public Datagroup getJoinsDatabaseTable() { return this.joinsDatabaseTable; }

    /**
     * Set the {@code joins_database_table} property (displayed as {@code Joins Database Table}) of the object.
     * @param joinsDatabaseTable the value to set
     */
    @JsonProperty("joins_database_table")
    public void setJoinsDatabaseTable(Datagroup joinsDatabaseTable) { this.joinsDatabaseTable = joinsDatabaseTable; }

    /**
     * Retrieve the {@code joins_olap_collection} property (displayed as '{@literal Joins OLAP Collection}') of the object.
     * @return {@code ItemList<BiCollection>}
     */
    @JsonProperty("joins_olap_collection")
    public ItemList<BiCollection> getJoinsOlapCollection() { return this.joinsOlapCollection; }

    /**
     * Set the {@code joins_olap_collection} property (displayed as {@code Joins OLAP Collection}) of the object.
     * @param joinsOlapCollection the value to set
     */
    @JsonProperty("joins_olap_collection")
    public void setJoinsOlapCollection(ItemList<BiCollection> joinsOlapCollection) { this.joinsOlapCollection = joinsOlapCollection; }

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

}
