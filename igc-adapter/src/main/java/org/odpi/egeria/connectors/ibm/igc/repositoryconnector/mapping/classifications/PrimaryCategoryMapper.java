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
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchCondition;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchConditionSet;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSRepositoryConnector;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCRepositoryHelper;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.Classification;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.InstanceProperties;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.search.PropertyCondition;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.search.SearchProperties;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.repositoryconnector.OMRSRepositoryHelper;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.FunctionNotSupportedException;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.RepositoryErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * The type Primary category mapper.
 */
public class PrimaryCategoryMapper extends ClassificationMapping {

    private static final Logger log = LoggerFactory.getLogger(PrimaryCategoryMapper.class);

    private static PrimaryCategoryMapper primaryCategoryMapper;

    private PrimaryCategoryMapper() {
        super(IGCRepositoryHelper.DEFAULT_IGC_TYPE, "assigned_to_terms", "GlossaryTerm",
                "PrimaryCategory");
        addMappedOmrsProperty("categoryQualifiedName");
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
                        "categoryQualifiedName",
                        catIdentity.getName(),
                        methodName
                );
                Classification classification = getMappedClassification(
                        igcomrsRepositoryConnector,
                        classificationProperties,
                        fromIgcObject,
                        userId
                );
                classifications.add(classification);
                log.debug("Add categoryQualifiedName property with value '{}' to classification {} of term {}", catIdentity.getName(),
                        classification.getName(), termIdentity.getName());
            } catch (NumberFormatException | IGCException e) {
                raiseRepositoryErrorException(IGCOMRSErrorCode.UNKNOWN_RUNTIME_ERROR, methodName, e);
            }
        }
    }

    /**
     * Search for PrimaryCategory by looking for a term assignment where the assigned term sits under a
     * PrimaryCategory parent category.
     *
     * @param repositoryHelper the repository helper
     * @param repositoryName name of the repository
     * @param matchProperties the criteria to use when searching for the classification
     * @return IGCSearchConditionSet - the IGC search criteria to find entities based on this classification
     * @throws FunctionNotSupportedException when an invalid enumeration is requested
     */
    @Override
    public IGCSearchConditionSet getIGCSearchCriteria(OMRSRepositoryHelper repositoryHelper,
                                                      String repositoryName,
                                                      SearchProperties matchProperties) throws FunctionNotSupportedException {

        IGCSearchCondition igcSearchCondition = new IGCSearchCondition(
                "assigned_to_terms.parent_category",
                "isNull",
                false
        );
        IGCSearchConditionSet igcSearchConditionSet = new IGCSearchConditionSet(igcSearchCondition);

        if (matchProperties != null) {
            IGCSearchConditionSet byProperties = getConditionsForProperties(matchProperties);
            if (byProperties.size() > 0) {
                igcSearchConditionSet.addNestedConditionSet(byProperties);
                igcSearchConditionSet.setMatchAnyCondition(false);
            }
        }
        return igcSearchConditionSet;
    }

    private IGCSearchConditionSet getConditionsForProperties(SearchProperties matchProperties) {

        IGCSearchConditionSet set = new IGCSearchConditionSet();

        List<PropertyCondition> propertyConditions = matchProperties.getConditions();
        for (PropertyCondition condition : propertyConditions) {
            SearchProperties nestedProperties = condition.getNestedConditions();
            if (nestedProperties != null) {
                IGCSearchConditionSet nestedSet = getConditionsForProperties(nestedProperties);
                IGCRepositoryHelper.setConditionsFromMatchCriteria(nestedSet, nestedProperties.getMatchCriteria());
                set.addNestedConditionSet(nestedSet);
            }
        }
        IGCRepositoryHelper.setConditionsFromMatchCriteria(set, matchProperties.getMatchCriteria());

        return set;
    }

}
