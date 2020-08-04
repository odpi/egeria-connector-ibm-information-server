/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.clientlibrary.cache;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;

import java.util.HashMap;
import java.util.Map;

/**
 * Provides a general mechanism to cache IGC objects temporarily, to avoid the need to repeatedly retrieve them and
 * incur the penalties of calling the REST API multiple times to retrieve the same information.
 */
public class ObjectCache {

    private Map<String, Reference> cache = new HashMap<>();

    /**
     * Add the provided entry into the cache.
     * @param entry to add
     */
    public void add(Reference entry) {
        cache.put(entry.getId(), entry);
    }

    /**
     * Retrieve an entry by its Repository ID (RID) from the cache.
     * @param id RID of the entry to retrieve
     * @return the IGC object, or null if not in the cache
     */
    public Reference get(String id) {
        return cache.getOrDefault(id, null);
    }

}
