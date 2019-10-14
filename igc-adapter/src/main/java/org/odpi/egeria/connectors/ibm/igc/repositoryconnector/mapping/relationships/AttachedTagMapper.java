/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCRepositoryHelper;

/**
 * Singleton to map the OMRS "AttachedTag" relationship for IGC "label" assets.
 */
public class AttachedTagMapper extends RelationshipMapping {

    private static class Singleton {
        private static final AttachedTagMapper INSTANCE = new AttachedTagMapper();
    }
    public static AttachedTagMapper getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    private AttachedTagMapper() {
        super(
                IGCRepositoryHelper.DEFAULT_IGC_TYPE,
                "label",
                "labels",
                "labeled_assets",
                "AttachedTag",
                "taggedElement",
                "tags"
        );
        addLiteralPropertyMapping("isPublic", true);
    }

}
