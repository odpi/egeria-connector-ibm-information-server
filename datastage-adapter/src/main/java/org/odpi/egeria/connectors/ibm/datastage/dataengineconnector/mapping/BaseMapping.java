/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.mapping;

import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.model.DataStageCache;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestConstants;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.InformationAsset;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Identity;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.interfaces.ColumnLevelLineage;

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
     * Retrieve the fully-qualified name for the provided IGC identity.
     *
     * @param identity the identity of an IGC object for which to retrieve the fully-qualified name
     * @param qualifier an additional qualifier to add, in particular for embedded elements
     * @return String
     */
    String getFullyQualifiedName(Identity identity, String qualifier) {
        if (identity != null) {
            String type = identity.getAssetType();
            String qualifiedName;
            if (IGCRestConstants.getDatastageSpecificTypes().contains(type) || IGCRestClient.isVirtualAssetRid(identity.getRid())) {
                // If this is a DataStage-specific asset type, or a virtual asset, prefix the qualifiedName
                // so that it is clearly distinguishable (and can be used to skip any attempt at searching for
                // the asset by qualifiedName in IGC)
                qualifiedName = IGCRestConstants.NON_IGC_PREFIX + identity.toString();
            } else {
                qualifiedName = identity.toString();
            }
            if (qualifier != null) {
                return qualifier + qualifiedName;
            } else {
                return qualifiedName;
            }
        }
        return null;
    }

    /**
     * Retrieve the fully-qualified name of the provided IGC object.
     *
     * @param igcObj the IGC object for which to retrieve the fully-qualified name
     * @param qualifier an additional qualifier to add, in particular for embedded elements
     * @return String
     */
    String getFullyQualifiedName(Reference igcObj, String qualifier) {
        if (igcObj != null) {
            Identity identity = igcObj.getIdentity(igcRestClient, cache.getIgcCache());
            return getFullyQualifiedName(identity, qualifier);
        }
        return null;
    }

    /**
     * Retrieve the fully-qualified name of the provided IGC object.
     *
     * @param igcObj the IGC object for which to retrieve the fully-qualified name
     * @return String
     */
    String getFullyQualifiedName(Reference igcObj) {
        return getFullyQualifiedName(igcObj, null);
    }

    /**
     * Retrieve the fully-qualified name of the provided IGC object.
     *
     * @param cll the column-level lineage object for which to retrieve the fully-qualified name
     * @param qualifier an additional qualifier to add, in particular for embedded elements
     * @return String
     */
    String getFullyQualifiedName(ColumnLevelLineage cll, String qualifier) {
        return getFullyQualifiedName((Reference) cll, qualifier);
    }

    /**
     * Retrieve the fully-qualified name of the provided IGC object.
     *
     * @param cll the column-level lineage object for which to retrieve the fully-qualified name
     * @return String
     */
    String getFullyQualifiedName(ColumnLevelLineage cll) {
        return getFullyQualifiedName((Reference) cll);
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
            Identity parentObjIdentity = getParentIdentity(igcObj);
            parentQN = getFullyQualifiedName(parentObjIdentity, null);
        }
        return parentQN;
    }

    /**
     * Retrieve the parent identity of the provided IGC object.
     *
     * @param igcObj the IGC object for which to retrieve the parent's identity
     * @return Identity
     */
    Identity getParentIdentity(Reference igcObj) {
        Identity parent = null;
        if (igcObj != null) {
            Identity identity = igcObj.getIdentity(igcRestClient, cache.getIgcCache());
            parent = identity.getParentIdentity();
        }
        return parent;
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
