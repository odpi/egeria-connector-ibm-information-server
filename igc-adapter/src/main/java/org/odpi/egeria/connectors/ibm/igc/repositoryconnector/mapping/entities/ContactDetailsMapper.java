/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities;

import org.odpi.egeria.connectors.ibm.igc.auditlog.IGCOMRSErrorCode;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestConstants;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.cache.ObjectCache;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.errors.IGCException;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Identity;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchCondition;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchConditionSet;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCRepositoryHelper;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.EntityMappingInstance;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.attributes.ContactMethodTypeMapper;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.ContactThroughMapper_Team;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSRepositoryConnector;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.ContactThroughMapper_Person;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.*;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.search.PropertyComparisonOperator;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.repositoryconnector.OMRSRepositoryHelper;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.FunctionNotSupportedException;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.RepositoryErrorException;

import java.util.List;

/**
 * Defines the mapping to the OMRS "ContactDetails" entity.
 */
public class ContactDetailsMapper extends ReferenceableMapper {

    public static final String IGC_RID_PREFIX = "CD";

    private static class Singleton {
        private static final ContactDetailsMapper INSTANCE = new ContactDetailsMapper();
    }
    public static ContactDetailsMapper getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    protected ContactDetailsMapper() {

        // Start by calling the superclass's constructor to initialise the Mapper
        super(
                "user",
                "User",
                "ContactDetails",
                IGC_RID_PREFIX
        );
        addOtherIGCAssetType("group");

        // The list of properties that should be mapped (only email_address is common across both users and groups)
        addComplexIgcProperty("email_address");
        addComplexOmrsProperty("contactMethodType");
        addComplexOmrsProperty("contactMethodValue");

        // The list of relationships that should be mapped
        addRelationshipMapper(ContactThroughMapper_Team.getInstance(null));
        addRelationshipMapper(ContactThroughMapper_Person.getInstance(null));

    }

    /**
     * Implement any complex property mappings that cannot be simply mapped one-to-one.
     *
     * @param cache a cache of information that may already have been retrieved about the provided object
     * @param entityMap the instantiation of a mapping to carry out
     * @param instanceProperties the instance properties to which to add the complex-mapped properties
     * @return InstanceProperties
     * @throws RepositoryErrorException if any issue interacting with IGC
     */
    @Override
    protected InstanceProperties complexPropertyMappings(ObjectCache cache,
                                                         EntityMappingInstance entityMap,
                                                         InstanceProperties instanceProperties) throws RepositoryErrorException {

        instanceProperties = super.complexPropertyMappings(cache, entityMap, instanceProperties);

        final String methodName = "complexPropertyMappings";

        Reference igcEntity = entityMap.getIgcEntity();
        IGCOMRSRepositoryConnector igcomrsRepositoryConnector = entityMap.getRepositoryConnector();
        IGCRestClient igcRestClient = igcomrsRepositoryConnector.getIGCRestClient();

        // Set the email address as a contact method (only if there is one present)
        try {
            String emailAddress = (String) igcRestClient.getPropertyByName(igcEntity, "email_address");
            if (emailAddress != null && !emailAddress.equals("")) {
                EnumPropertyValue contactMethod = ContactMethodTypeMapper.getInstance(igcomrsRepositoryConnector.getIGCVersion()).getEnumMappingByIgcValue("email_address");
                instanceProperties.setProperty("contactMethodType", contactMethod);
                instanceProperties = igcomrsRepositoryConnector.getRepositoryHelper().addStringPropertyToInstance(
                        igcomrsRepositoryConnector.getRepositoryName(),
                        instanceProperties,
                        "contactMethodValue",
                        emailAddress,
                        methodName
                );
            }
        } catch (IGCException e) {
            raiseRepositoryErrorException(IGCOMRSErrorCode.UNKNOWN_RUNTIME_ERROR, methodName, e);
        }

        // TODO: add mappings for other types (mobile_phone_number, instant_message_id)

        return instanceProperties;

    }

    /**
     * Handle the search for 'contactMethodValue' by searching against 'email_address' of the contact in IGC.
     *
     * @param repositoryHelper the repository helper
     * @param repositoryName name of the repository
     * @param igcRestClient connectivity to an IGC environment
     * @param igcSearchConditionSet the set of search criteria to which to add
     * @param igcPropertyName the IGC property name (or COMPLEX_MAPPING_SENTINEL) to search
     * @param omrsPropertyName the OMRS property name (or COMPLEX_MAPPING_SENTINEL) to search
     * @param operator the comparison operator to use
     * @param value the value for which to search
     * @throws FunctionNotSupportedException when a regular expression is used for the search which is not supported
     */
    @Override
    public void addComplexPropertySearchCriteria(OMRSRepositoryHelper repositoryHelper,
                                                 String repositoryName,
                                                 IGCRestClient igcRestClient,
                                                 IGCSearchConditionSet igcSearchConditionSet,
                                                 String igcPropertyName,
                                                 String omrsPropertyName,
                                                 PropertyComparisonOperator operator,
                                                 InstancePropertyValue value) throws FunctionNotSupportedException {

        super.addComplexPropertySearchCriteria(repositoryHelper, repositoryName, igcRestClient, igcSearchConditionSet, igcPropertyName, omrsPropertyName, operator, value);

        final String methodName = "addComplexPropertySearchCriteria";

        // TODO: add mappings for other types (mobile_phone_number, instant_message_id)
        if (omrsPropertyName.equals("contactMethodValue")) {
            String contactMethodValue = ((PrimitivePropertyValue) value).getPrimitiveValue().toString();
            IGCSearchCondition condition = IGCRepositoryHelper.getRegexSearchCondition(
                    repositoryHelper,
                    repositoryName,
                    methodName,
                    "email_address",
                    operator,
                    contactMethodValue
            );
            igcSearchConditionSet.addCondition(condition);
        } else if (omrsPropertyName.equals("contactMethodType")) {

            if (value instanceof EnumPropertyValue) {
                EnumPropertyValue toMatch = (EnumPropertyValue) value;
                // enum mappings for this are actually IGC property names rather than values
                String igcPropertyToSearch = ContactMethodTypeMapper.getInstance(null).getIgcValueForSymbolicName(toMatch.getSymbolicName());
                IGCRepositoryHelper.validateEnumOperator(operator, methodName);
                IGCSearchCondition igcSearchCondition = null;
                if (igcPropertyToSearch == null) {
                    // If there is no IGC property for this enumeration value, and we are looking for any match
                    // other than IS_NULL, we should ensure no results are returned
                    if (!operator.equals(PropertyComparisonOperator.IS_NULL)) {
                        igcSearchCondition = IGCRestConstants.getConditionToForceNoSearchResults();
                    }
                } else {
                    switch (operator) {
                        case IS_NULL:
                        case NEQ:
                            igcSearchCondition = new IGCSearchCondition(
                                    igcPropertyToSearch,
                                    "isNull",
                                    false
                            );
                            break;
                        case NOT_NULL:
                        case EQ:
                            igcSearchCondition = new IGCSearchCondition(
                                    igcPropertyToSearch,
                                    "isNull",
                                    true
                            );
                            break;
                        default:
                            // Do nothing...
                            break;
                    }
                }
                if (igcSearchCondition != null) {
                    igcSearchConditionSet.addCondition(igcSearchCondition);
                }
            }

        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addComplexStringSearchCriteria(OMRSRepositoryHelper repositoryHelper,
                                               String repositoryName,
                                               IGCRestClient igcRestClient,
                                               IGCSearchConditionSet igcSearchConditionSet,
                                               String searchCriteria) throws FunctionNotSupportedException, RepositoryErrorException {

        super.addComplexStringSearchCriteria(repositoryHelper, repositoryName, igcRestClient, igcSearchConditionSet, searchCriteria);

        // Add matching against a full / qualified name as well, since this is uniquely complex for user details
        String unqualifiedName = repositoryHelper.getUnqualifiedLiteralString(searchCriteria);
        unqualifiedName = IGCRepositoryHelper.getSearchableQualifiedName(unqualifiedName);
        Identity.getSearchCriteriaForUserName(igcSearchConditionSet, unqualifiedName);

    }

}
