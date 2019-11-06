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
import java.util.Date;

/**
 * POJO for the {@code data_item_definition} asset type in IGC, displayed as '{@literal Data Item Definition}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=DataItemDefinition.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonSubTypes({
        @JsonSubTypes.Type(value = DataElement.class, name = "data_element"),
        @JsonSubTypes.Type(value = TableDefinition.class, name = "table_definition"),
})
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("data_item_definition")
public class DataItemDefinition extends DataItem {

    @JsonProperty("contains_data_fields")
    protected ItemList<DataItem> containsDataFields;

    @JsonProperty("database_schema")
    protected DatabaseSchema databaseSchema;

    /**
     * Retrieve the {@code contains_data_fields} property (displayed as '{@literal Contains Data Fields}') of the object.
     * @return {@code ItemList<DataItem>}
     */
    @JsonProperty("contains_data_fields")
    public ItemList<DataItem> getContainsDataFields() { return this.containsDataFields; }

    /**
     * Set the {@code contains_data_fields} property (displayed as {@code Contains Data Fields}) of the object.
     * @param containsDataFields the value to set
     */
    @JsonProperty("contains_data_fields")
    public void setContainsDataFields(ItemList<DataItem> containsDataFields) { this.containsDataFields = containsDataFields; }

    /**
     * Retrieve the {@code database_schema} property (displayed as '{@literal Database Schema}') of the object.
     * @return {@code DatabaseSchema}
     */
    @JsonProperty("database_schema")
    public DatabaseSchema getDatabaseSchema() { return this.databaseSchema; }

    /**
     * Set the {@code database_schema} property (displayed as {@code Database Schema}) of the object.
     * @param databaseSchema the value to set
     */
    @JsonProperty("database_schema")
    public void setDatabaseSchema(DatabaseSchema databaseSchema) { this.databaseSchema = databaseSchema; }

}
