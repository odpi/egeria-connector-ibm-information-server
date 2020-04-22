/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.classifications.TypeEmbeddedAttributeMapper_TabularColumn;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.AttributeForSchemaMapper_RecordField;

/**
 * Defines the mapping to the OMRS "TabularColumn" entity.
 */
public class TabularColumnMapper extends SchemaAttributeMapper {

    private static class Singleton {
        private static final TabularColumnMapper INSTANCE = new TabularColumnMapper();
    }
    public static TabularColumnMapper getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    protected TabularColumnMapper() {

        // Start by calling the superclass's constructor to initialise the Mapper
        super(
                "data_file_field",
                "Data File Field",
                "TabularColumn"
        );

        // The list of relationships that should be mapped
        addRelationshipMapper(AttributeForSchemaMapper_RecordField.getInstance(null));

        // The list of classifications that should be mapped
        addClassificationMapper(TypeEmbeddedAttributeMapper_TabularColumn.getInstance(null));

    }

}
