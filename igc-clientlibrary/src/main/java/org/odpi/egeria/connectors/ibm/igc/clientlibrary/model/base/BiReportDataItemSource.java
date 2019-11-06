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
 * POJO for the {@code bi_report_data_item_source} asset type in IGC, displayed as '{@literal BI Report Data Item Source}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=BiReportDataItemSource.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("bi_report_data_item_source")
public class BiReportDataItemSource extends Reference {

    @JsonProperty("defined_by_data_field")
    protected ItemList<DataItem> definedByDataField;

    @JsonProperty("defined_by_olap_member")
    protected BiCollectionMember definedByOlapMember;

    @JsonProperty("defined_in_report_data_item")
    protected BiReportQueryItem definedInReportDataItem;

    @JsonProperty("defined_of_report_field")
    protected ItemList<Reportobject> definedOfReportField;

    /**
     * Retrieve the {@code defined_by_data_field} property (displayed as '{@literal Defined by Data Field}') of the object.
     * @return {@code ItemList<DataItem>}
     */
    @JsonProperty("defined_by_data_field")
    public ItemList<DataItem> getDefinedByDataField() { return this.definedByDataField; }

    /**
     * Set the {@code defined_by_data_field} property (displayed as {@code Defined by Data Field}) of the object.
     * @param definedByDataField the value to set
     */
    @JsonProperty("defined_by_data_field")
    public void setDefinedByDataField(ItemList<DataItem> definedByDataField) { this.definedByDataField = definedByDataField; }

    /**
     * Retrieve the {@code defined_by_olap_member} property (displayed as '{@literal Defined by OLAP Member}') of the object.
     * @return {@code BiCollectionMember}
     */
    @JsonProperty("defined_by_olap_member")
    public BiCollectionMember getDefinedByOlapMember() { return this.definedByOlapMember; }

    /**
     * Set the {@code defined_by_olap_member} property (displayed as {@code Defined by OLAP Member}) of the object.
     * @param definedByOlapMember the value to set
     */
    @JsonProperty("defined_by_olap_member")
    public void setDefinedByOlapMember(BiCollectionMember definedByOlapMember) { this.definedByOlapMember = definedByOlapMember; }

    /**
     * Retrieve the {@code defined_in_report_data_item} property (displayed as '{@literal Defined in Report Data Item}') of the object.
     * @return {@code BiReportQueryItem}
     */
    @JsonProperty("defined_in_report_data_item")
    public BiReportQueryItem getDefinedInReportDataItem() { return this.definedInReportDataItem; }

    /**
     * Set the {@code defined_in_report_data_item} property (displayed as {@code Defined in Report Data Item}) of the object.
     * @param definedInReportDataItem the value to set
     */
    @JsonProperty("defined_in_report_data_item")
    public void setDefinedInReportDataItem(BiReportQueryItem definedInReportDataItem) { this.definedInReportDataItem = definedInReportDataItem; }

    /**
     * Retrieve the {@code defined_of_report_field} property (displayed as '{@literal Defined of Report Field}') of the object.
     * @return {@code ItemList<Reportobject>}
     */
    @JsonProperty("defined_of_report_field")
    public ItemList<Reportobject> getDefinedOfReportField() { return this.definedOfReportField; }

    /**
     * Set the {@code defined_of_report_field} property (displayed as {@code Defined of Report Field}) of the object.
     * @param definedOfReportField the value to set
     */
    @JsonProperty("defined_of_report_field")
    public void setDefinedOfReportField(ItemList<Reportobject> definedOfReportField) { this.definedOfReportField = definedOfReportField; }

}
