/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSRepositoryConnector;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.classifications.ClassificationMapping;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities.GlossaryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Singleton to map the OMRS "CategoryHierarchyLink" relationship for IGC "category" assets.
 */
public class CategoryHierarchyLinkMapper extends RelationshipMapping {

    private static final Logger log = LoggerFactory.getLogger(CategoryHierarchyLinkMapper.class);

    private static class Singleton {
        private static final CategoryHierarchyLinkMapper INSTANCE = new CategoryHierarchyLinkMapper();
    }
    public static CategoryHierarchyLinkMapper getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    private CategoryHierarchyLinkMapper() {
        super(
                "category",
                "category",
                "subcategories",
                "parent_category",
                "CategoryHierarchyLink",
                "superCategory",
                "subcategories"
        );
        setContainedType(ContainedType.TWO);
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
        return isCategoryRelationship(igcomrsRepositoryConnector, oneObject, otherObject);
    }

    /**
     * Indicates whether the relationship between the provided objects is category-related (true) or not (false). When
     * not category-related it is likely because one end is either a Classification or a Glossary.
     *
     * @param igcomrsRepositoryConnector connection to the IGC environment
     * @param oneObject the IGC object to consider for inclusion on one end of the relationship
     * @param otherObject the IGC object to consider for inclusion on the other end of the relationship
     * @return boolean
     */
    public static boolean isCategoryRelationship(IGCOMRSRepositoryConnector igcomrsRepositoryConnector,
                                                 Reference oneObject,
                                                 Reference otherObject) {
        if (log.isDebugEnabled()) { log.debug("Considering inclusion of objects: {} ({}) and {} ({})", oneObject.getName(), oneObject.getType(), otherObject.getName(), otherObject.getType()); }
        IGCRestClient igcRestClient = igcomrsRepositoryConnector.getIGCRestClient();
        boolean isGlossary = GlossaryMapper.isGlossary(igcRestClient, oneObject) || GlossaryMapper.isGlossary(igcRestClient, otherObject);
        if (isGlossary && log.isDebugEnabled()) { log.debug(" ... skipping, Glossary-level category."); }
        boolean isClassification = ClassificationMapping.isClassification(igcRestClient, oneObject) || ClassificationMapping.isClassification(igcRestClient, otherObject);
        if (isClassification && log.isDebugEnabled()) { log.debug(" ... skipping, classification object."); }
        return !isGlossary && !isClassification;
    }

}
