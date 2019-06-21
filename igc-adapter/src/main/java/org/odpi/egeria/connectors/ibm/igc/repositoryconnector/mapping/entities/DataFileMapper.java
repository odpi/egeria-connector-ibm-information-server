/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.AssetSchemaTypeMapper_FileRecord;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.NestedFileMapper;

/**
 * Defines the mapping to the OMRS "DataFile" entity.
 */
public class DataFileMapper extends ReferenceableMapper {

    private static class Singleton {
        private static final DataFileMapper INSTANCE = new DataFileMapper();
    }
    public static DataFileMapper getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    private DataFileMapper() {

        // Start by calling the superclass's constructor to initialise the Mapper
        super(
                "data_file",
                "Data File",
                "DataFile"
        );

        // The list of properties that should be mapped, none other than qualifiedName
        addSimplePropertyMapping("name", "name");
        addSimplePropertyMapping("short_description", "description");

        // The list of relationships that should be mapped
        addRelationshipMapper(AssetSchemaTypeMapper_FileRecord.getInstance(null));
        addRelationshipMapper(NestedFileMapper.getInstance(null));

    }

}
