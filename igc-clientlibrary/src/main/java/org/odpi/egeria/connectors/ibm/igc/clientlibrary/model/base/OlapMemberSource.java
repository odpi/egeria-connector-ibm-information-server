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

/**
 * POJO for the {@code olap_member_source} asset type in IGC, displayed as '{@literal OLAP Member Source}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("olap_member_source")
public class OlapMemberSource extends Reference {

    @JsonProperty("data_field")
    protected DataItem dataField;

    @JsonProperty("olap_member")
    protected BiCollectionMember olapMember;

    /**
     * Retrieve the {@code data_field} property (displayed as '{@literal Data Field}') of the object.
     * @return {@code DataItem}
     */
    @JsonProperty("data_field")
    public DataItem getDataField() { return this.dataField; }

    /**
     * Set the {@code data_field} property (displayed as {@code Data Field}) of the object.
     * @param dataField the value to set
     */
    @JsonProperty("data_field")
    public void setDataField(DataItem dataField) { this.dataField = dataField; }

    /**
     * Retrieve the {@code olap_member} property (displayed as '{@literal OLAP Member}') of the object.
     * @return {@code BiCollectionMember}
     */
    @JsonProperty("olap_member")
    public BiCollectionMember getOlapMember() { return this.olapMember; }

    /**
     * Set the {@code olap_member} property (displayed as {@code OLAP Member}) of the object.
     * @param olapMember the value to set
     */
    @JsonProperty("olap_member")
    public void setOlapMember(BiCollectionMember olapMember) { this.olapMember = olapMember; }

}
