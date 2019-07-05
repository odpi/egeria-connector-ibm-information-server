/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.mapping;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Identity;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;

public class BaseMapping {

    protected IGCRestClient igcRestClient;

    public BaseMapping(IGCRestClient igcRestClient) {
        this.igcRestClient = igcRestClient;
    }

    /**
     * Retrieve a description from the provided object, preferring the long description if there is one but defaulting
     * to the short description if there is not.
     *
     * @param igcObj the IGC object for which to retrieve the description
     * @return String
     */
    public String getDescription(Reference igcObj) {
        Object desc = igcRestClient.getPropertyByName(igcObj, "long_description");
        String description = null;
        if (desc == null) {
            desc = igcRestClient.getPropertyByName(igcObj, "short_description");
        }
        if (desc != null) {
            description = (String) desc;
        }
        return description;
    }

    /**
     * Retrieve the fully-qualified name of the provided IGC object.
     *
     * @param igcObj the IGC object for which to retrieve the fully-qualified name
     * @return String
     */
    public String getFullyQualifiedName(Reference igcObj) {
        return igcObj.getIdentity(igcRestClient).toString();
    }

    /**
     * Retrieve the fully-qualified name of the parent of the provided IGC object.
     *
     * @param igcObj the IGC object for which to retrieve the parent's fully-qualified name
     * @return String
     */
    public String getParentQualifiedName(Reference igcObj) {
        String parentQN = null;
        if (igcObj != null) {
            Identity thisObjIdentity = igcObj.getIdentity(igcRestClient);
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
    public String getParentDisplayName(Reference igcObj) {
        String parentDN = null;
        if (igcObj != null) {
            Identity thisObjIdentity = igcObj.getIdentity(igcRestClient);
            Identity parentObjIdentity = thisObjIdentity.getParentIdentity();
            if (parentObjIdentity != null) {
                parentDN = parentObjIdentity.getName();
            }
        }
        return parentDN;
    }

}
