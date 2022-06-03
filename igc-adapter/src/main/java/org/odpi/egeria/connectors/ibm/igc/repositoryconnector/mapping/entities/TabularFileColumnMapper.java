/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.classifications.AnchorsMapper;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.classifications.TypeEmbeddedAttributeMapper_TabularFileColumn;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.AttributeForSchemaMapper_RecordField;

/**
 * Defines the mapping to the OMRS "TabularFileColumn" entity.
 */
public class TabularFileColumnMapper extends TabularColumnMapper {

    private static class Singleton {
        private static final TabularFileColumnMapper INSTANCE = new TabularFileColumnMapper();
    }
    public static TabularFileColumnMapper getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    protected TabularFileColumnMapper() {

        // Start by calling the superclass's constructor to initialise the Mapper
        super(
                "data_file_field",
                "Data File Field",
                "TabularFileColumn"
        );

        // The list of relationships that should be mapped
        addRelationshipMapper(AttributeForSchemaMapper_RecordField.getInstance(null));

        // The list of classifications that should be mapped
        addClassificationMapper(TypeEmbeddedAttributeMapper_TabularFileColumn.getInstance(null));
        addClassificationMapper(AnchorsMapper.getInstance(null));
    }

}
