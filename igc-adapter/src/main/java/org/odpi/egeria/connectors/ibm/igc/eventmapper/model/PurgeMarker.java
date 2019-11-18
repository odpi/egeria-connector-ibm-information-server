/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.eventmapper.model;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.RelationshipMapping;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.typedefs.RelationshipDef;

/**
 * A class to keep track of objects that should be recursively purged based on a parent object having been purged.
 */
public class PurgeMarker {

    private Reference triggerObject;
    private RelationshipDef relationshipDef;
    private RelationshipMapping mapping;

    /**
     * Create a new marker based on the provided inputs.
     *
     * @param triggerObject the IGC object that triggered the purge
     * @param relationshipDef the relationship definition that needs to cascade the purge
     * @param mapping the relationship mapping that needs to cascade the purge
     */
    public PurgeMarker(Reference triggerObject, RelationshipDef relationshipDef, RelationshipMapping mapping) {
        this.triggerObject = triggerObject;
        this.relationshipDef = relationshipDef;
        this.mapping = mapping;
    }

    /**
     * Retrieve the IGC object that triggered the purge.
     *
     * @return Reference
     */
    public Reference getTriggerObject() {
        return triggerObject;
    }

    /**
     * Retrieve the relationship definition that needs to cascade the purge.
     *
     * @return RelationshipDef
     */
    public RelationshipDef getRelationshipDef() {
        return relationshipDef;
    }

    /**
     * Retrieve the relationship mapping that needs to cascade the purge.
     *
     * @return RelationshipMapping
     */
    public RelationshipMapping getMapping() {
        return mapping;
    }

}
