/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.SchemaAttributeTypeMapper_FileField;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSMetadataCollection;

/**
 * Defines the mapping to the OMRS "TabularColumnType" entity.
 */
public class TabularColumnTypeMapper extends ReferenceableMapper {

    public static final String IGC_RID_PREFIX = IGCOMRSMetadataCollection.generateTypePrefix("TCT");

    private static class Singleton {
        private static final TabularColumnTypeMapper INSTANCE = new TabularColumnTypeMapper();
    }
    public static TabularColumnTypeMapper getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    private TabularColumnTypeMapper() {

        // Start by calling the superclass's constructor to initialise the Mapper
        super(
                "data_file_field",
                "Data File Field",
                "TabularColumnType",
                IGC_RID_PREFIX
        );

        // The list of properties that should be mapped
        addSimplePropertyMapping("name", "displayName");
        addSimplePropertyMapping("data_type", "dataType");

        // The list of relationships that should be mapped
        addRelationshipMapper(SchemaAttributeTypeMapper_FileField.getInstance(null));

    }

}
