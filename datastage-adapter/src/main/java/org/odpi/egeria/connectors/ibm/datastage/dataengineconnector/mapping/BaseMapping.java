/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.mapping;

import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.model.DataStageCache;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.InformationAsset;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Identity;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;

class BaseMapping {

    DataStageCache cache;
    IGCRestClient igcRestClient;

    BaseMapping(DataStageCache cache) {
        this.cache = cache;
        this.igcRestClient = cache.getIgcRestClient();
    }

    /**
     * Retrieve a description from the provided object, preferring the long description if there is one but defaulting
     * to the short description if there is not.
     *
     * @param igcObj the IGC object for which to retrieve the description
     * @return String
     */
    String getDescription(InformationAsset igcObj) {
        String description = igcObj.getLongDescription();
        if (description == null) {
            description = igcObj.getShortDescription();
        }
        return description;
    }

    /**
     * Retrieve the fully-qualified name of the provided IGC object.
     *
     * @param igcObj the IGC object for which to retrieve the fully-qualified name
     * @return String
     */
    String getFullyQualifiedName(Reference igcObj) {
        if (igcObj != null) {
            Identity identity = igcObj.getIdentity(igcRestClient, cache.getIgcCache());
            if (identity != null) {
                return identity.toString();
            }
        }
        return null;
    }

    /**
     * Retrieve the fully-qualified name of the parent of the provided IGC object.
     *
     * @param igcObj the IGC object for which to retrieve the parent's fully-qualified name
     * @return String
     */
    String getParentQualifiedName(Reference igcObj) {
        String parentQN = null;
        if (igcObj != null) {
            Identity thisObjIdentity = igcObj.getIdentity(igcRestClient, cache.getIgcCache());
            Identity parentObjIdentity = thisObjIdentity.getParentIdentity();
            if (parentObjIdentity != null) {
                parentQN = parentObjIdentity.toString();
            }
        }
        return parentQN;
    }

    /**
     * Retrieve the display name of the parent of the provided IGC object.
     *
     * @param igcObj the IGC object for which to retrieve the parent's display name
     * @return String
     */
    String getParentDisplayName(Reference igcObj) {
        String parentDN = null;
        if (igcObj != null) {
            Identity thisObjIdentity = igcObj.getIdentity(igcRestClient, cache.getIgcCache());
            Identity parentObjIdentity = thisObjIdentity.getParentIdentity();
            if (parentObjIdentity != null) {
                parentDN = parentObjIdentity.getName();
            }
        }
        return parentDN;
    }

}
