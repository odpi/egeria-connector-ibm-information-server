/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.mapping;

import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.model.DataStageCache;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Identity;
import org.odpi.openmetadata.accessservices.dataengine.model.RelationalTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Mappings for creating a RelationalTable.
 */
public class RelationalTableMapping  extends BaseMapping{
    private static final Logger log = LoggerFactory.getLogger(RelationalTableMapping.class);

    public RelationalTableMapping(DataStageCache cache) {
        super(cache);
    }

    /**
     * Creates a RelationalTable for the provided data store and field information.
     *
     * @param storeIdentity the store identity for which to create the virtual table
     * @return RelationalTable
     */
    public RelationalTable getForDataStore(Identity storeIdentity) {
        RelationalTable relationalTable = null;
        if (storeIdentity != null) {
            relationalTable = new RelationalTable();
            String relationalTableQN = getFullyQualifiedName(storeIdentity, null);
            if (relationalTableQN != null) {
                log.debug("Constructing RelationalTable for data store: {}", relationalTableQN);
                relationalTable.setQualifiedName(relationalTableQN);
                relationalTable.setDisplayName(storeIdentity.getName());
            } else {
                log.error("Unable to determine identity of store: {}", storeIdentity);
            }
        }
        return relationalTable;
    }
}
