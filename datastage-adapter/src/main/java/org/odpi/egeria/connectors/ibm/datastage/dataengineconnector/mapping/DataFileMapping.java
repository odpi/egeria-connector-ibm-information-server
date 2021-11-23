/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.mapping;

import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.model.DataStageCache;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Identity;
import org.odpi.openmetadata.accessservices.dataengine.model.DataFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Mappings for creating a DataFile.
 */
public class DataFileMapping extends BaseMapping{
    private static final Logger log = LoggerFactory.getLogger(DataFileMapping.class);

    public DataFileMapping(DataStageCache cache) {
        super(cache);
    }

    /**
     * Creates a DataFile for the provided data store and field information.
     *
     * @param storeIdentity the store identity for which to create the data file
     * @return DataFile
     */
    public DataFile getForDataStore(Identity storeIdentity) {
        DataFile dataFile = null;
        if (storeIdentity != null) {
            dataFile= new DataFile();
            String dataFileQN = getFullyQualifiedName(storeIdentity, null);
            if (dataFileQN != null) {
                log.debug("Constructing DataFile for data store: {}", dataFileQN);
                dataFile.setQualifiedName(dataFileQN);
                dataFile.setDisplayName(storeIdentity.getName());
            } else {
                log.error("Unable to determine identity of store: {}", storeIdentity);
            }
        }
        return dataFile;
    }
}
