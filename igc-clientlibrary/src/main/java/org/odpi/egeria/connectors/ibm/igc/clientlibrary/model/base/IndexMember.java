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
 * POJO for the {@code index_member} asset type in IGC, displayed as '{@literal Index Member}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=IndexMember.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("index_member")
public class IndexMember extends Reference {

    @JsonProperty("of_index")
    protected DatabaseIndex ofIndex;

    @JsonProperty("sequence")
    protected Number sequence;

    @JsonProperty("uses_data_field")
    protected DataItem usesDataField;

    /**
     * Retrieve the {@code of_index} property (displayed as '{@literal Of Index}') of the object.
     * @return {@code DatabaseIndex}
     */
    @JsonProperty("of_index")
    public DatabaseIndex getOfIndex() { return this.ofIndex; }

    /**
     * Set the {@code of_index} property (displayed as {@code Of Index}) of the object.
     * @param ofIndex the value to set
     */
    @JsonProperty("of_index")
    public void setOfIndex(DatabaseIndex ofIndex) { this.ofIndex = ofIndex; }

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
     * Retrieve the {@code uses_data_field} property (displayed as '{@literal Uses Data Field}') of the object.
     * @return {@code DataItem}
     */
    @JsonProperty("uses_data_field")
    public DataItem getUsesDataField() { return this.usesDataField; }

    /**
     * Set the {@code uses_data_field} property (displayed as {@code Uses Data Field}) of the object.
     * @param usesDataField the value to set
     */
    @JsonProperty("uses_data_field")
    public void setUsesDataField(DataItem usesDataField) { this.usesDataField = usesDataField; }

}
