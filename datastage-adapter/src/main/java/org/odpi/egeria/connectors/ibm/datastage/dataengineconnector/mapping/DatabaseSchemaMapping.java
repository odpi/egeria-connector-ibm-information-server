/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.mapping;

import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.model.DataStageCache;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Identity;
import org.odpi.openmetadata.accessservices.dataengine.model.DatabaseSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Mappings for creating a DatabaseSchema.
 */
public class DatabaseSchemaMapping extends BaseMapping{
    private static final Logger log = LoggerFactory.getLogger(DatabaseSchemaMapping.class);

    public DatabaseSchemaMapping(DataStageCache cache) {
        super(cache);
    }

    /**
     * Creates a DatabaseSchema for the provided data store and field information.
     *
     * @param storeIdentity the store identity for which to create the database schema
     * @return DatabaseSchema
     */
    public DatabaseSchema getForDataStore(Identity storeIdentity) {
        DatabaseSchema databaseSchema = null;
        if (storeIdentity != null) {
            databaseSchema= new DatabaseSchema();

            String schemaTypeQN = getFullyQualifiedName(storeIdentity, null);
            if (schemaTypeQN != null) {
                log.debug("Constructing DatabaseSchema for data store: {}", schemaTypeQN);
                databaseSchema.setQualifiedName(schemaTypeQN);
                databaseSchema.setDisplayName(storeIdentity.getName());
            } else {
                log.error("Unable to determine identity of store: {}", storeIdentity);
            }
        }
        return databaseSchema;
    }
}
