/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.classifications;

import org.odpi.egeria.connectors.ibm.igc.auditlog.IGCOMRSErrorCode;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.cache.ObjectCache;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.errors.IGCException;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.Term;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Identity;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSRepositoryConnector;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.Classification;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.InstanceProperties;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.RepositoryErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * The type Primary category mapper.
 */
public class PrimaryCategoryMapper extends ClassificationMapping {

    private static final Logger log = LoggerFactory.getLogger(PrimaryCategoryMapper.class);
    private static final String ADD_CATEGORY_QUALIFIED_NAME_PROPERTY_WITH_VALUE_TO_CLASSIFICATION_OF_TERM =
            "Add categoryQualifiedName property with value '{}' to classification {} of term {}";

    private static final String TERM = "term";
    private static final String PARENT_CATEGORY = "parent_category";
    private static final String GLOSSARY_TERM = "GlossaryTerm";
    private static final String PRIMARY_CATEGORY = "PrimaryCategory";
    private static final String CATEGORY_QUALIFIED_NAME = "categoryQualifiedName";

    private static PrimaryCategoryMapper primaryCategoryMapper;

    private PrimaryCategoryMapper() {
        super(TERM, PARENT_CATEGORY, GLOSSARY_TERM, PRIMARY_CATEGORY);
        addMappedOmrsProperty(CATEGORY_QUALIFIED_NAME);
    }

    public static ClassificationMapping getInstance(IGCVersionEnum version) {
        if(primaryCategoryMapper == null) {
            primaryCategoryMapper = new PrimaryCategoryMapper();
        }
        return primaryCategoryMapper;
    }

    /**
     * Implements the "PrimaryCategory" classification for IGC objects (by default we only apply to terms).
     * We use the 'assigned_to_term' relationship from one term to any term
     * within a parent category to represent the "PrimaryCategory" classification in OMRS.
     * Therefore, any 'assigned_to_term' relationship on a term, where the assigned term is within a
     * parent category in IGC, will be mapped to a "PrimaryCategory" classification in OMRS.
     *
     * @param igcomrsRepositoryConnector connectivity to the IGC environment
     * @param classifications the list of classifications to which to add
     * @param cache a cache of information that may already have been retrieved about the provided object
     * @param fromIgcObject the IGC object for which the classification should exist
     * @param userId the user requesting the mapped classifications
     * @throws RepositoryErrorException if any issue interacting with IGC
     */
    @Override
    public void addMappedOMRSClassifications(IGCOMRSRepositoryConnector igcomrsRepositoryConnector,
                                             List<Classification> classifications,
                                             ObjectCache cache,
                                             Reference fromIgcObject,
                                             String userId) throws RepositoryErrorException {

        final String methodName = "addMappedOMRSClassification";

        if (fromIgcObject instanceof Term) {
           try {
                IGCRestClient igcRestClient = igcomrsRepositoryConnector.getIGCRestClient();
                Identity termIdentity = fromIgcObject.getIdentity(igcRestClient, cache);
                Identity catIdentity = termIdentity.getParentIdentity();

                InstanceProperties classificationProperties = new InstanceProperties();

                classificationProperties = igcomrsRepositoryConnector.getRepositoryHelper().addStringPropertyToInstance(
                        igcomrsRepositoryConnector.getRepositoryName(),
                        classificationProperties,
                        CATEGORY_QUALIFIED_NAME,
                        catIdentity.toString(),
                        methodName
                );
                Classification classification = getMappedClassification(
                        igcomrsRepositoryConnector,
                        classificationProperties,
                        fromIgcObject,
                        userId
                );

                classifications.add(classification);

                log.debug(ADD_CATEGORY_QUALIFIED_NAME_PROPERTY_WITH_VALUE_TO_CLASSIFICATION_OF_TERM,
                        catIdentity.toString(), classification.getName(), termIdentity.getName());

            } catch (NumberFormatException | IGCException e) {
                raiseRepositoryErrorException(IGCOMRSErrorCode.UNKNOWN_RUNTIME_ERROR, methodName, e);
            }
        }
    }
}
