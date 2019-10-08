/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.classifications;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSErrorCode;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSMetadataCollection;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSRepositoryConnector;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.EntityDetail;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.InstanceProperties;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.InstancePropertyValue;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.EntityNotKnownException;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.RepositoryErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Singleton to map the OMRS "TypeEmbeddedAttribute" classification.
 * @see TypeEmbeddedAttributeMapper_RelationalColumn
 * @see TypeEmbeddedAttributeMapper_RelationalTable
 * @see TypeEmbeddedAttributeMapper_TabularColumn
 */
public class TypeEmbeddedAttributeMapper extends ClassificationMapping {

    private static final Logger log = LoggerFactory.getLogger(TypeEmbeddedAttributeMapper.class);

    private static class Singleton {
        private static final TypeEmbeddedAttributeMapper INSTANCE = new TypeEmbeddedAttributeMapper();
    }
    public static TypeEmbeddedAttributeMapper getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    private TypeEmbeddedAttributeMapper() {
        super(
                "",
                "",
                "SchemaAttribute",
                "TypeEmbeddedAttribute"
        );
        addLiteralPropertyMapping("encodingStandard", null);
        addMappedOmrsProperty("dataType");
        addSubType(TypeEmbeddedAttributeMapper_RelationalTable.getInstance(null));
        addSubType(TypeEmbeddedAttributeMapper_RelationalColumn.getInstance(null));
    }

    protected TypeEmbeddedAttributeMapper(String igcAssetType,
                                          String igcRelationshipProperty,
                                          String omrsEntityType,
                                          String omrsClassificationType) {
        super(
                igcAssetType,
                igcRelationshipProperty,
                omrsEntityType,
                omrsClassificationType
        );
    }

    /**
     * Implement this method to define how to add an OMRS classification to an existing IGC asset. (Since IGC has no
     * actual concept of classification, this is left as a method to-be-implemented depending on how the implementation
     * desires the classification to be represented within IGC.)
     *
     * @param igcomrsRepositoryConnector connectivity to the IGC repository via OMRS connector
     * @param igcEntity the IGC object to which to add the OMRS classification
     * @param entityGUID the GUID of the OMRS entity (ie. including any prefix)
     * @param initialProperties the set of classification-specific properties to add to the classification
     * @param userId the user requesting the classification to be added (currently unused)
     * @return EntityDetail the updated entity with the OMRS classification added
     * @throws RepositoryErrorException
     */
    @Override
    public EntityDetail addClassificationToIGCAsset(IGCOMRSRepositoryConnector igcomrsRepositoryConnector,
                                                    Reference igcEntity,
                                                    String entityGUID,
                                                    InstanceProperties initialProperties,
                                                    String userId) throws RepositoryErrorException, EntityNotKnownException {

        final String methodName = "addClassificationToIGCAsset";

        Map<String, InstancePropertyValue> classificationProperties = null;
        if (initialProperties != null) {
            classificationProperties = initialProperties.getInstanceProperties();
        }

        if (classificationProperties != null || !classificationProperties.isEmpty()) {

            log.error("Classification properties are immutable in IGC.");
            IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.CLASSIFICATION_EXCEEDS_REPOSITORY;
            String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                    getOmrsClassificationType(),
                    getIgcAssetType()
            );
            throw new RepositoryErrorException(
                    errorCode.getHTTPErrorCode(),
                    this.getClass().getName(),
                    methodName,
                    errorMessage,
                    errorCode.getSystemAction(),
                    errorCode.getUserAction()
            );

        }

        IGCOMRSMetadataCollection collection = (IGCOMRSMetadataCollection) igcomrsRepositoryConnector.getMetadataCollection();
        return collection.getIgcRepositoryHelper().getEntityDetail(userId, entityGUID, igcEntity);

    }

}
