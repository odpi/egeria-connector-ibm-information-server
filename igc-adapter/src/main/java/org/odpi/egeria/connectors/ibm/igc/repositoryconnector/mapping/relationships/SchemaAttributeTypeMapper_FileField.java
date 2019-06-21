/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities.TabularColumnTypeMapper;

/**
 * Singleton to map the OMRS "SchemaAttributeType" relationship for IGC "data_file_field" assets.
 */
public class SchemaAttributeTypeMapper_FileField extends RelationshipMapping {

    private static class Singleton {
        private static final SchemaAttributeTypeMapper_FileField INSTANCE = new SchemaAttributeTypeMapper_FileField();
    }
    public static SchemaAttributeTypeMapper_FileField getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    private SchemaAttributeTypeMapper_FileField() {
        super(
                "data_file_field",
                "data_file_field",
                SELF_REFERENCE_SENTINEL,
                SELF_REFERENCE_SENTINEL,
                "SchemaAttributeType",
                "usedInSchemas",
                "type",
                null,
                TabularColumnTypeMapper.IGC_RID_PREFIX
        );
    }

}
