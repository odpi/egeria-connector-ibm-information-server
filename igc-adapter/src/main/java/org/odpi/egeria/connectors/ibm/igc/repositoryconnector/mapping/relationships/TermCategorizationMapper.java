/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestConstants;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Identity;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSMetadataCollection;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSRepositoryConnector;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCRepositoryHelper;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.attributes.TermRelationshipStatusMapper;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.classifications.ClassificationMapping;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities.GlossaryMapper;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.model.IGCEntityGuid;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.Relationship;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Singleton to map the OMRS "TermCategorization" relationship between IGC "category" and "term" assets.
 */
public class TermCategorizationMapper extends RelationshipMapping {

    private static final Logger log = LoggerFactory.getLogger(TermCategorizationMapper.class);

    private static class Singleton {
        private static final TermCategorizationMapper INSTANCE = new TermCategorizationMapper();
    }
    public static TermCategorizationMapper getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    private TermCategorizationMapper() {
        super(
                "category",
                "term",
                "terms",
                "parent_category",
                "TermCategorization",
                "categories",
                "terms"
        );
        addAlternativePropertyFromTwo("referencing_categories");
        addLiteralPropertyMapping("description", null);
        addLiteralPropertyMapping("status", TermRelationshipStatusMapper.getInstance(null).getEnumMappingByIgcValue("Active"));
    }

    /**
     * If the object is a root-level category, do not include it as a relationship. In all other scenarios, include
     * a relationship for it.
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
        if (log.isDebugEnabled()) { log.debug("Considering inclusion of objects: {} ({}) and {} ({})", oneObject.getName(), oneObject.getType(), otherObject.getName(), otherObject.getType()); }
        IGCRestClient igcRestClient = igcomrsRepositoryConnector.getIGCRestClient();
        boolean isGlossary = GlossaryMapper.isGlossary(igcRestClient, oneObject) || GlossaryMapper.isGlossary(igcRestClient, otherObject);
        if (isGlossary) {
            if (log.isDebugEnabled()) { log.debug(" ... skipping, Glossary-level category."); }
        }
        boolean isClassification = ClassificationMapping.isClassification(igcRestClient, oneObject) || ClassificationMapping.isClassification(igcRestClient, otherObject);
        if (isClassification) {
            if (log.isDebugEnabled()) { log.debug(" ... skipping, classification object."); }
        }
        return !isGlossary && !isClassification;
    }

}
