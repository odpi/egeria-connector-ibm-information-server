/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.interfaces;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.DataItem;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;

import java.util.List;

/**
 * Interface through which column-level lineage details can be retrieved, across
 * multiple IGC asset types.
 */
public interface ColumnLevelLineage {

    /**
     * Retrieve the context of the IGC object instance.
     * @return {@code List<Reference>}
     */
    List<Reference> getContext();

    /**
     * Set the context of the IGC object instance.
     * @param _context of the IGC object instance
     */
    void setContext(List<Reference> _context);

    /**
     * Retrieve the name of the IGC object instance.
     * @return String
     */
    String getName();

    /**
     * Set the name of the IGC object instance.
     * @param _name of the IGC object instance
     */
    void setName(String _name);

    /**
     * Retrieve the {@code next_stage_columns} property (displayed as '{@literal Next Stage Columns or Variables}') of the object.
     * @return {@code ItemList<DataItem>}
     */
    ItemList<DataItem> getNextStageColumns();

    /**
     * Set the {@code next_stage_columns} property (displayed as {@code Next Stage Columns or Variables}) of the object.
     * @param nextStageColumns the value to set
     */
    void setNextStageColumns(ItemList<DataItem> nextStageColumns);

    /**
     * Retrieve the {@code odbc_type} property (displayed as '{@literal SQL Type}') of the object.
     * @return {@code String}
     */
    String getOdbcType();

    /**
     * Set the {@code odbc_type} property (displayed as {@code SQL Type}) of the object.
     * @param odbcType the value to set
     */
    void setOdbcType(String odbcType);

    /**
     * Retrieve the {@code previous_stage_columns} property (displayed as '{@literal Previous Stage Columns or Variables}') of the object.
     * @return {@code ItemList<DataItem>}
     */
    ItemList<DataItem> getPreviousStageColumns();

    /**
     * Set the {@code previous_stage_columns} property (displayed as {@code Previous Stage Columns or Variables}) of the object.
     * @param previousStageColumns the value to set
     */
    void setPreviousStageColumns(ItemList<DataItem> previousStageColumns);

}
