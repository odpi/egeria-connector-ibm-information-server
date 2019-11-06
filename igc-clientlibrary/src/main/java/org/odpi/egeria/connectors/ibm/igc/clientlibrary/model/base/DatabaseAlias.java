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
import java.util.Date;

/**
 * POJO for the {@code database_alias} asset type in IGC, displayed as '{@literal Database Alias}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=DatabaseAlias.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("database_alias")
public class DatabaseAlias extends Datagroup {

    @JsonProperty("alias_of_database_tables_or_views")
    protected Datagroup aliasOfDatabaseTablesOrViews;

    @JsonProperty("imported_from")
    protected String importedFrom;

    /**
     * Retrieve the {@code alias_of_database_tables_or_views} property (displayed as '{@literal Alias of Database Tables or Views}') of the object.
     * @return {@code Datagroup}
     */
    @JsonProperty("alias_of_database_tables_or_views")
    public Datagroup getAliasOfDatabaseTablesOrViews() { return this.aliasOfDatabaseTablesOrViews; }

    /**
     * Set the {@code alias_of_database_tables_or_views} property (displayed as {@code Alias of Database Tables or Views}) of the object.
     * @param aliasOfDatabaseTablesOrViews the value to set
     */
    @JsonProperty("alias_of_database_tables_or_views")
    public void setAliasOfDatabaseTablesOrViews(Datagroup aliasOfDatabaseTablesOrViews) { this.aliasOfDatabaseTablesOrViews = aliasOfDatabaseTablesOrViews; }

    /**
     * Retrieve the {@code imported_from} property (displayed as '{@literal Imported From}') of the object.
     * @return {@code String}
     */
    @JsonProperty("imported_from")
    public String getImportedFrom() { return this.importedFrom; }

    /**
     * Set the {@code imported_from} property (displayed as {@code Imported From}) of the object.
     * @param importedFrom the value to set
     */
    @JsonProperty("imported_from")
    public void setImportedFrom(String importedFrom) { this.importedFrom = importedFrom; }

}
