/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.classifications;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSErrorCode;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSRepositoryConnector;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.InstanceProperties;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.InstancePropertyValue;
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
     * {@inheritDoc}
     */
    @Override
    public void addClassificationToIGCAsset(IGCOMRSRepositoryConnector igcomrsRepositoryConnector,
                                            Reference igcEntity,
                                            String entityGUID,
                                            InstanceProperties initialProperties,
                                            String userId) throws RepositoryErrorException {
        final String methodName = "addClassificationToIGCAsset";
        validateUnsupportedProperties(methodName, initialProperties);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeClassificationFromIGCAsset(IGCOMRSRepositoryConnector igcomrsRepositoryConnector,
                                                 Reference igcAsset,
                                                 String entityGUID,
                                                 String userId)
            throws RepositoryErrorException {
        final String methodName = "removeClassificationFromIGCAsset";
        reportImmutableClassification(methodName, entityGUID);
    }

}
