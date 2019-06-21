/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchCondition;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchConditionSet;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.EntityMappingInstance;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.attributes.ContactMethodTypeMapper;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.ContactThroughMapper_Team;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSMetadataCollection;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSRepositoryConnector;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.ContactThroughMapper_Person;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.EnumPropertyValue;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.InstanceProperties;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.InstancePropertyValue;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.PrimitivePropertyValue;

import java.util.List;

/**
 * Defines the mapping to the OMRS "ContactDetails" entity.
 */
public class ContactDetailsMapper extends ReferenceableMapper {

    public static final String IGC_RID_PREFIX = IGCOMRSMetadataCollection.generateTypePrefix("CD");

    private static class Singleton {
        private static final ContactDetailsMapper INSTANCE = new ContactDetailsMapper();
    }
    public static ContactDetailsMapper getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    private ContactDetailsMapper() {

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
     * @param entityMap the instantiation of a mapping to carry out
     * @param instanceProperties the instance properties to which to add the complex-mapped properties
     * @return InstanceProperties
     */
    @Override
    protected InstanceProperties complexPropertyMappings(EntityMappingInstance entityMap,
                                                         InstanceProperties instanceProperties) {

        instanceProperties = super.complexPropertyMappings(entityMap, instanceProperties);

        final String methodName = "complexPropertyMappings";

        Reference igcEntity = entityMap.getIgcEntity();
        IGCOMRSRepositoryConnector igcomrsRepositoryConnector = entityMap.getRepositoryConnector();
        IGCRestClient igcRestClient = igcomrsRepositoryConnector.getIGCRestClient();

        // Set the email address as a contact method (only if there is one present)
        String emailAddress = (String) igcRestClient.getPropertyByName(igcEntity, "email_address");
        if (emailAddress != null && !emailAddress.equals("")) {
            EnumPropertyValue contactMethod = ContactMethodTypeMapper.getInstance(igcomrsRepositoryConnector.getIGCVersion()).getEnumMappingByIgcValue("email");
            instanceProperties.setProperty("contactMethodType", contactMethod);
            instanceProperties = igcomrsRepositoryConnector.getRepositoryHelper().addStringPropertyToInstance(
                    igcomrsRepositoryConnector.getRepositoryName(),
                    instanceProperties,
                    "contactMethodValue",
                    emailAddress,
                    methodName
            );
        }

        return instanceProperties;

    }

    /**
     * Handle the search for 'contactMethodValue' by searching against 'email_address' of the contact in IGC.
     *
     * @param igcRestClient connectivity to an IGC environment
     * @param igcSearchConditionSet the set of search criteria to which to add
     * @param igcPropertyName the IGC property name (or COMPLEX_MAPPING_SENTINEL) to search
     * @param omrsPropertyName the OMRS property name (or COMPLEX_MAPPING_SENTINEL) to search
     * @param igcProperties the list of IGC properties to which to add for inclusion in the IGC search
     * @param value the value for which to search
     */
    @Override
    public void addComplexPropertySearchCriteria(IGCRestClient igcRestClient,
                                                 IGCSearchConditionSet igcSearchConditionSet,
                                                 String igcPropertyName,
                                                 String omrsPropertyName,
                                                 List<String> igcProperties,
                                                 InstancePropertyValue value) {

        super.addComplexPropertySearchCriteria(igcRestClient, igcSearchConditionSet, igcPropertyName, omrsPropertyName, igcProperties, value);

        if (omrsPropertyName.equals("contactMethodValue")) {

            String contactMethodValue = ((PrimitivePropertyValue) value).getPrimitiveValue().toString();
            IGCSearchCondition igcSearchCondition = new IGCSearchCondition(
                    "email_address",
                    "=",
                    contactMethodValue
            );
            igcSearchConditionSet.addCondition(igcSearchCondition);

        }

    }

}
