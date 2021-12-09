/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.mapping;

import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.model.DataStageCache;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Identity;
import org.odpi.openmetadata.accessservices.dataengine.model.Database;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Mappings for creating a Database.
 */
public class DatabaseMapping extends BaseMapping{
    private static final Logger log = LoggerFactory.getLogger(DatabaseMapping.class);

    public DatabaseMapping(DataStageCache cache) {
        super(cache);
    }

    /**
     * Creates a Database for the provided data store and field information.
     *
     * @param storeIdentity the store identity for which to create the database
     * @return Database
     */
    public Database getForDataStore(Identity storeIdentity) {
        Database database = null;
        if (storeIdentity != null) {
            database = new Database();

            String schemaTypeQN = getFullyQualifiedName(storeIdentity, null);
            if (schemaTypeQN != null) {
                log.debug("Constructing Database for data store: {}", schemaTypeQN);
                database.setQualifiedName(schemaTypeQN);
                database.setDisplayName(storeIdentity.getName());
            } else {
                log.error("Unable to determine identity of store: {}", storeIdentity);
            }
        }
        return database;
    }
}
