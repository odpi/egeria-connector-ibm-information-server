/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSRepositoryConnector;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCRepositoryHelper;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.attributes.TermAssignmentStatusMapper;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.classifications.ClassificationMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Singleton to map the OMRS "SemanticAssignment" relationship for IGC "term" assets.
 */
public class SemanticAssignmentMapper extends RelationshipMapping {

    private static final Logger log = LoggerFactory.getLogger(SemanticAssignmentMapper.class);

    private static class Singleton {
        private static final SemanticAssignmentMapper INSTANCE = new SemanticAssignmentMapper();
    }
    public static SemanticAssignmentMapper getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    protected SemanticAssignmentMapper() {
        super(
                IGCRepositoryHelper.DEFAULT_IGC_TYPE,
                "term",
                "assigned_to_terms",
                "assigned_assets",
                "SemanticAssignment",
                "assignedElements",
                "meaning"
        );

        // We will explicitly exclude terms from being applied a SemanticAssignment, as it would overlap with
        // classifications like Confidentiality (and is probably better done via other more meaningful
        // term-to-term relationships) - we also need to exclude other assets which have no 'assigned_to_terms'
        // relationships
        ProxyMapping pmOne = getProxyOneMapping();
        pmOne.addExcludedIgcAssetType("term");
        pmOne.addExcludedIgcAssetType("connector");
        pmOne.addExcludedIgcAssetType("data_connection");
        pmOne.addExcludedIgcAssetType("group");
        pmOne.addExcludedIgcAssetType("information_governance_policy");
        pmOne.addExcludedIgcAssetType("label");
        pmOne.addExcludedIgcAssetType("user");

        addLiteralPropertyMapping("description", null);
        addLiteralPropertyMapping("expression", null);
        addLiteralPropertyMapping("status", TermAssignmentStatusMapper.getInstance(null).getDefaultEnumValue());
        addLiteralPropertyMapping("confidence", 100);
        addLiteralPropertyMapping("steward", null);
        addLiteralPropertyMapping("source", null);

    }

    /**
     * If the object is a term under the Classifications category, do not include it as a relationship. In all other
     * scenarios, include a relationship for it.
     *
     * @param igcomrsRepositoryConnector connection to the IGC environment
     * @param oneObject the IGC object to consider for inclusion on one end of the relationship
     * @param otherObject the IGC object to consider for inclusion on the other end of the relationship
     * @return boolean
     */
    @Override
    public boolean includeRelationshipForIgcObjects(IGCOMRSRepositoryConnector igcomrsRepositoryConnector,
                                                    Reference oneObject,
                                                    Reference otherObject) {
        log.debug("Considering inclusion of objects: {} ({}) and {} ({})", oneObject.getName(), oneObject.getType(), otherObject.getName(), otherObject.getType());
        IGCRestClient igcRestClient = igcomrsRepositoryConnector.getIGCRestClient();
        boolean isClassification = ClassificationMapping.isClassification(igcRestClient, oneObject)
                || ClassificationMapping.isClassification(igcRestClient, otherObject);
        if (isClassification) {
            log.debug(" ... skipping, reserved Classification object.");
        }
        return !isClassification;
    }

}
