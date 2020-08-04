/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.cache.ObjectCache;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSRepositoryConnector;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.attributes.TermRelationshipStatusMapper;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.classifications.ClassificationMapping;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities.GlossaryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    protected TermCategorizationMapper() {
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
     * @param cache a cache of information that may already have been retrieved about the provided object
     * @param oneObject the IGC object to consider for inclusion on one end of the relationship
     * @param otherObject the IGC object to consider for inclusion on the other end of the relationship
     * @return boolean
     */
    @Override
    public boolean includeRelationshipForIgcObjects(IGCOMRSRepositoryConnector igcomrsRepositoryConnector,
                                                    ObjectCache cache,
                                                    Reference oneObject,
                                                    Reference otherObject) {
        return CategoryHierarchyLinkMapper.isCategoryRelationship(igcomrsRepositoryConnector, cache, oneObject, otherObject);
    }

}
