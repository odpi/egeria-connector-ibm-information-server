/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector;

import org.odpi.egeria.connectors.ibm.igc.auditlog.IGCOMRSErrorCode;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestConstants;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Identity;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearch;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchConditionSet;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchSorting;
import org.odpi.egeria.connectors.ibm.igc.eventmapper.IGCOMRSRepositoryEventMapper;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.EntityMappingInstance;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities.EntityMapping;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.model.IGCEntityGuid;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.model.IGCRelationshipGuid;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.stores.*;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.classifications.ClassificationMapping;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.RelationshipMapping;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.OMRSMetadataCollectionBase;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.MatchCriteria;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.SequencingOrder;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.*;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.typedefs.*;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.repositoryconnector.OMRSRepositoryHelper;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.repositoryconnector.OMRSRepositoryValidator;
import org.odpi.openmetadata.repositoryservices.ffdc.OMRSErrorCode;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Provides the OMRSMetadataCollection implementation for IBM InfoSphere Information Governance Catalog ("IGC").
 */
public class IGCOMRSMetadataCollection extends OMRSMetadataCollectionBase {

    private static final Logger log = LoggerFactory.getLogger(IGCOMRSMetadataCollection.class);

    private IGCRestClient igcRestClient;
    private IGCOMRSRepositoryConnector igcomrsRepositoryConnector;
    private IGCRepositoryHelper igcRepositoryHelper;
    private IGCOMRSRepositoryEventMapper eventMapper = null;

    private TypeDefStore typeDefStore;
    private AttributeMappingStore attributeMappingStore;

    /**
     * @param parentConnector      connector that this metadata collection supports.
     *                             The connector has the information to call the metadata repository.
     * @param repositoryName       name of this repository.
     * @param repositoryHelper     helper that provides methods to repository connectors and repository event mappers
     *                             to build valid type definitions (TypeDefs), entities and relationships.
     * @param repositoryValidator  validator class for checking open metadata repository objects and parameters
     * @param metadataCollectionId unique identifier for the repository
     */
    public IGCOMRSMetadataCollection(IGCOMRSRepositoryConnector parentConnector,
                                     String repositoryName,
                                     OMRSRepositoryHelper repositoryHelper,
                                     OMRSRepositoryValidator repositoryValidator,
                                     String metadataCollectionId) {
        super(parentConnector, repositoryName, repositoryHelper, repositoryValidator, metadataCollectionId);
        if (log.isDebugEnabled()) { log.debug("Constructing IGCOMRSMetadataCollection with name: {}", repositoryName); }
        parentConnector.setRepositoryName(repositoryName);
        this.igcRestClient = parentConnector.getIGCRestClient();
        this.igcomrsRepositoryConnector = parentConnector;
        this.igcRepositoryHelper = new IGCRepositoryHelper(igcomrsRepositoryConnector, repositoryHelper, igcRestClient);
        this.typeDefStore = new TypeDefStore();
        this.attributeMappingStore = new AttributeMappingStore(parentConnector);
    }

    /**
     * Retrieve the helper class for IGC-specific (deeper-dive) methods than what is provided by the
     * OMRSMetadataCollection interfaces.
     *
     * @return IGCRepositoryHelper
     */
    public IGCRepositoryHelper getIgcRepositoryHelper() { return this.igcRepositoryHelper; }

    /**
     * {@inheritDoc}
     */
    @Override
    public TypeDefGallery getAllTypes(String userId) throws
            RepositoryErrorException,
            InvalidParameterException {

        final String methodName = "getAllTypes";
        super.basicRequestValidation(userId, methodName);

        TypeDefGallery typeDefGallery = new TypeDefGallery();
        List<TypeDef> typeDefs = typeDefStore.getAllTypeDefs();
        if (log.isDebugEnabled()) { log.debug("Retrieved {} implemented TypeDefs for this repository.", typeDefs.size()); }
        typeDefGallery.setTypeDefs(typeDefs);

        List<AttributeTypeDef> attributeTypeDefs = attributeMappingStore.getAllAttributeTypeDefs();
        if (log.isDebugEnabled()) { log.debug("Retrieved {} implemented AttributeTypeDefs for this repository.", attributeTypeDefs.size()); }
        typeDefGallery.setAttributeTypeDefs(attributeTypeDefs);

        return typeDefGallery;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TypeDef> findTypeDefsByCategory(String userId, TypeDefCategory category) throws
            InvalidParameterException,
            RepositoryErrorException {

        final String methodName = "findTypeDefsByCategory";
        final String categoryParameterName = "category";
        super.typeDefCategoryParameterValidation(userId, category, categoryParameterName, methodName);

        List<TypeDef> typeDefs = new ArrayList<>();
        switch(category) {
            case ENTITY_DEF:
                typeDefs = igcRepositoryHelper.getMappedEntityTypes();
                break;
            case RELATIONSHIP_DEF:
                typeDefs = igcRepositoryHelper.getMappedRelationshipTypes();
                break;
            case CLASSIFICATION_DEF:
                typeDefs = igcRepositoryHelper.getMappedClassificationTypes();
                break;
        }
        return typeDefs;

    }


    /**
     * {@inheritDoc}
     */
    @Override
    public List<AttributeTypeDef> findAttributeTypeDefsByCategory(String userId, AttributeTypeDefCategory category) throws
            InvalidParameterException,
            RepositoryErrorException {

        final String methodName = "findAttributeTypeDefsByCategory";
        final String categoryParameterName = "category";
        super.attributeTypeDefCategoryParameterValidation(userId, category, categoryParameterName, methodName);

        return attributeMappingStore.getAttributeTypeDefsByCategory(category);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TypeDef> findTypeDefsByProperty(String userId, TypeDefProperties matchCriteria) throws
            InvalidParameterException,
            RepositoryErrorException {

        final String methodName = "findTypeDefsByProperty";
        final String matchCriteriaParameterName = "matchCriteria";
        super.typeDefPropertyParameterValidation(userId, matchCriteria, matchCriteriaParameterName, methodName);

        List<TypeDef> typeDefs = typeDefStore.getAllTypeDefs();
        List<TypeDef> results = new ArrayList<>();
        if (matchCriteria != null) {
            Map<String, Object> properties = matchCriteria.getTypeDefProperties();
            for (TypeDef candidate : typeDefs) {
                List<TypeDefAttribute> candidateProperties = candidate.getPropertiesDefinition();
                if (candidateProperties != null) {
                    for (TypeDefAttribute candidateAttribute : candidateProperties) {
                        String candidateName = candidateAttribute.getAttributeName();
                        if (properties.containsKey(candidateName)) {
                            results.add(candidate);
                        }
                    }
                }
            }
        }

        return results;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TypeDef> findTypesByExternalID(String userId,
                                               String standard,
                                               String organization,
                                               String identifier) throws
            InvalidParameterException,
            RepositoryErrorException {

        final String methodName = "findTypesByExternalID";
        super.typeDefExternalIDParameterValidation(userId, standard, organization, identifier, methodName);

        List<TypeDef> typeDefs = typeDefStore.getAllTypeDefs();
        List<TypeDef> results;
        if (standard == null && organization == null && identifier == null) {
            results = typeDefs;
        } else {
            results = new ArrayList<>();
            for (TypeDef typeDef : typeDefs) {
                List<ExternalStandardMapping> externalStandardMappings = typeDef.getExternalStandardMappings();
                for (ExternalStandardMapping externalStandardMapping : externalStandardMappings) {
                    String candidateStandard = externalStandardMapping.getStandardName();
                    String candidateOrg = externalStandardMapping.getStandardOrganization();
                    String candidateId = externalStandardMapping.getStandardTypeName();
                    if ( (standard == null || standard.equals(candidateStandard))
                            && (organization == null || organization.equals(candidateOrg))
                            && (identifier == null || identifier.equals(candidateId))) {
                        results.add(typeDef);
                    }
                }
            }
        }

        return results;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TypeDef> searchForTypeDefs(String userId, String searchCriteria) throws
            InvalidParameterException,
            RepositoryErrorException {

        final String methodName = "searchForTypeDefs";
        final String searchCriteriaParameterName = "searchCriteria";
        super.typeDefSearchParameterValidation(userId, searchCriteria, searchCriteriaParameterName, methodName);

        List<TypeDef> typeDefs = new ArrayList<>();
        for (TypeDef candidate : igcRepositoryHelper.getMappedEntityTypes()) {
            if (candidate.getName().matches(searchCriteria)) {
                typeDefs.add(candidate);
            }
        }
        for (TypeDef candidate : igcRepositoryHelper.getMappedRelationshipTypes()) {
            if (candidate.getName().matches(searchCriteria)) {
                typeDefs.add(candidate);
            }
        }
        for (TypeDef candidate : igcRepositoryHelper.getMappedClassificationTypes()) {
            if (candidate.getName().matches(searchCriteria)) {
                typeDefs.add(candidate);
            }
        }

        return typeDefs;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TypeDef getTypeDefByGUID(String userId, String guid) throws
            InvalidParameterException,
            RepositoryErrorException,
            TypeDefNotKnownException {

        final String methodName = "getTypeDefByGUID";
        final String guidParameterName = "guid";
        super.typeGUIDParameterValidation(userId, guid, guidParameterName, methodName);

        TypeDef found = typeDefStore.getTypeDefByGUID(guid);

        if (found == null) {
            raiseTypeDefNotKnownException(IGCOMRSErrorCode.TYPEDEF_NOT_MAPPED, guid, methodName);
        }

        return found;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AttributeTypeDef getAttributeTypeDefByGUID(String userId, String guid) throws
            InvalidParameterException,
            RepositoryErrorException,
            TypeDefNotKnownException {

        final String methodName = "getAttributeTypeDefByGUID";
        final String guidParameterName = "guid";
        super.typeGUIDParameterValidation(userId, guid, guidParameterName, methodName);

        AttributeTypeDef found = attributeMappingStore.getAttributeTypeDefByGUID(guid);
        if (found == null) {
            raiseTypeDefNotKnownException(IGCOMRSErrorCode.ATTRIBUTE_TYPEDEF_NOT_MAPPED, guid, methodName);
        }
        return found;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TypeDef getTypeDefByName(String userId, String name) throws
            InvalidParameterException,
            RepositoryErrorException,
            TypeDefNotKnownException {

        final String methodName = "getTypeDefByName";
        final String nameParameterName = "name";
        super.typeNameParameterValidation(userId, name, nameParameterName, methodName);

        TypeDef found = typeDefStore.getTypeDefByName(name);

        if (found == null) {
            raiseTypeDefNotKnownException(IGCOMRSErrorCode.TYPEDEF_NOT_MAPPED, name, methodName);
        }

        return found;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AttributeTypeDef getAttributeTypeDefByName(String userId, String name) throws
            InvalidParameterException,
            RepositoryErrorException,
            TypeDefNotKnownException {

        final String methodName = "getAttributeTypeDefByName";
        final String nameParameterName = "name";
        super.typeNameParameterValidation(userId, name, nameParameterName, methodName);

        AttributeTypeDef found = attributeMappingStore.getAttributeTypeDefByName(name);
        if (found == null) {
            raiseTypeDefNotKnownException(IGCOMRSErrorCode.ATTRIBUTE_TYPEDEF_NOT_MAPPED, name, methodName);
        }
        return found;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addTypeDef(String userId, TypeDef newTypeDef) throws
            InvalidParameterException,
            RepositoryErrorException,
            TypeDefNotSupportedException,
            TypeDefKnownException,
            TypeDefConflictException,
            InvalidTypeDefException {

        final String methodName = "addTypeDef";
        final String typeDefParameterName = "newTypeDef";
        super.newTypeDefParameterValidation(userId, newTypeDef, typeDefParameterName, methodName);

        TypeDefCategory typeDefCategory = newTypeDef.getCategory();
        String omrsTypeDefName = newTypeDef.getName();
        if (log.isDebugEnabled()) { log.debug("Looking for mapping for {} of type {}", omrsTypeDefName, typeDefCategory.getName()); }

        // See if we have a Mapper defined for the class -- if so, it's implemented
        StringBuilder sbMapperClassname = new StringBuilder();
        sbMapperClassname.append(IGCRepositoryHelper.MAPPING_PKG);
        switch(typeDefCategory) {
            case RELATIONSHIP_DEF:
                sbMapperClassname.append("relationships.");
                break;
            case CLASSIFICATION_DEF:
                sbMapperClassname.append("classifications.");
                break;
            case ENTITY_DEF:
                sbMapperClassname.append("entities.");
                break;
        }
        sbMapperClassname.append(omrsTypeDefName);
        sbMapperClassname.append("Mapper");

        try {

            Class<?> mappingClass = Class.forName(sbMapperClassname.toString());
            if (log.isDebugEnabled()) { log.debug(" ... found mapping class: {}", mappingClass.getCanonicalName()); }

            boolean success = false;

            switch(typeDefCategory) {
                case RELATIONSHIP_DEF:
                    success = igcRepositoryHelper.addRelationshipMapping(newTypeDef, mappingClass);
                    break;
                case CLASSIFICATION_DEF:
                    success = igcRepositoryHelper.addClassificationMapping(newTypeDef, mappingClass);
                    break;
                case ENTITY_DEF:
                    success = igcRepositoryHelper.addEntityMapping(newTypeDef, mappingClass);
                    break;
            }

            if (!success) {
                typeDefStore.addUnimplementedTypeDef(newTypeDef);
                raiseTypeDefNotSupportedException(IGCOMRSErrorCode.TYPEDEF_NOT_MAPPED, omrsTypeDefName, methodName);
            } else {
                typeDefStore.addTypeDef(newTypeDef);
            }

        } catch (ClassNotFoundException e) {
            typeDefStore.addUnimplementedTypeDef(newTypeDef);
            raiseTypeDefNotSupportedException(IGCOMRSErrorCode.TYPEDEF_NOT_MAPPED, omrsTypeDefName, methodName);
        }

        checkEventMapperIsConfigured(methodName);
        eventMapper.sendNewTypeDefEvent(newTypeDef);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addAttributeTypeDef(String userId, AttributeTypeDef newAttributeTypeDef) throws
            InvalidParameterException,
            RepositoryErrorException,
            TypeDefNotSupportedException,
            TypeDefKnownException,
            TypeDefConflictException,
            InvalidTypeDefException {

        final String methodName = "addAttributeTypeDef";
        final String typeDefParameterName = "newAttributeTypeDef";
        super.newAttributeTypeDefParameterValidation(userId, newAttributeTypeDef, typeDefParameterName, methodName);

        // Note this is only implemented for Enums, support for other types is indicated directly
        // in the verifyAttributeTypeDef method
        AttributeTypeDefCategory attributeTypeDefCategory = newAttributeTypeDef.getCategory();
        String omrsTypeDefName = newAttributeTypeDef.getName();
        if (log.isDebugEnabled()) { log.debug("Looking for mapping for {} of type {}", omrsTypeDefName, attributeTypeDefCategory.getName()); }

        // See if we have a Mapper defined for the class -- if so, it's implemented
        StringBuilder sbMapperClassname = new StringBuilder();
        sbMapperClassname.append(IGCRepositoryHelper.MAPPING_PKG);
        sbMapperClassname.append("attributes.");
        sbMapperClassname.append(omrsTypeDefName);
        sbMapperClassname.append("Mapper");

        try {
            Class<?> mappingClass = Class.forName(sbMapperClassname.toString());
            if (log.isDebugEnabled()) { log.debug(" ... found mapping class: {}", mappingClass.getCanonicalName()); }
            attributeMappingStore.addMapping(newAttributeTypeDef, mappingClass);
        } catch (ClassNotFoundException e) {
            raiseTypeDefNotSupportedException(IGCOMRSErrorCode.ATTRIBUTE_TYPEDEF_NOT_MAPPED, omrsTypeDefName, methodName);
        }

        checkEventMapperIsConfigured(methodName);
        eventMapper.sendNewAttributeTypeDefEvent(newAttributeTypeDef);

    }

    /**
     * Verify that the mapped properties provided support all of the properties defined on the provided type definition
     * (and its supertypes).
     *
     * @param typeDef the type definition to verify
     * @param mappedProperties the list of properties that are mapped for the type
     * @param gaps the list of names of any properties that are not mapped
     */
    private void verifyPropertiesForTypeDef(TypeDef typeDef, Set<String> mappedProperties, List<String> gaps) {
        List<TypeDefAttribute> properties = typeDef.getPropertiesDefinition();
        if (properties != null) {
            for (TypeDefAttribute typeDefAttribute : properties) {
                String omrsPropertyName = typeDefAttribute.getAttributeName();
                if (!mappedProperties.contains(omrsPropertyName)) {
                    gaps.add(omrsPropertyName);
                }
            }
        }
        TypeDefLink superTypeLink = typeDef.getSuperType();
        if (superTypeLink != null) {
            String superTypeGUID = superTypeLink.getGUID();
            TypeDef superType = typeDefStore.getTypeDefByGUID(superTypeGUID);
            if (superType == null) {
                superType = typeDefStore.getUnimplementedTypeDefByGUID(superTypeGUID, true);
            }
            if (superType != null) {
                verifyPropertiesForTypeDef(superType, mappedProperties, gaps);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean verifyTypeDef(String userId, TypeDef typeDef) throws
            InvalidParameterException,
            RepositoryErrorException,
            TypeDefNotSupportedException,
            InvalidTypeDefException {

        final String methodName = "verifyTypeDef";
        final String typeDefParameterName = "typeDef";
        super.typeDefParameterValidation(userId, typeDef, typeDefParameterName, methodName);

        String guid = typeDef.getGUID();

        // If we know the TypeDef is unimplemented, immediately throw an exception stating as much
        if (typeDefStore.getUnimplementedTypeDefByGUID(guid, false) != null) {
            raiseTypeDefNotSupportedException(IGCOMRSErrorCode.TYPEDEF_NOT_MAPPED, typeDef.getName(), methodName);
            return false;
        } else if (typeDefStore.getTypeDefByGUID(guid) != null) {

            List<String> gaps = new ArrayList<>();

            Set<String> mappedProperties = new HashSet<>();
            switch (typeDef.getCategory()) {
                case ENTITY_DEF:
                    EntityMapping entityMapping = igcRepositoryHelper.getEntityMappingByGUID(guid);
                    if (entityMapping != null) {
                        mappedProperties = entityMapping.getAllMappedOmrsProperties();
                    }
                    break;
                case RELATIONSHIP_DEF:
                    RelationshipMapping relationshipMapping = igcRepositoryHelper.getRelationshipMappingByGUID(guid);
                    if (relationshipMapping != null) {
                        mappedProperties = relationshipMapping.getMappedOmrsPropertyNames();
                    }
                    break;
                case CLASSIFICATION_DEF:
                    ClassificationMapping classificationMapping = igcRepositoryHelper.getClassificationMappingByGUID(guid);
                    if (classificationMapping != null) {
                        mappedProperties = classificationMapping.getMappedOmrsPropertyNames();
                    }
                    break;
            }

            // Validate that we support all of the possible properties before deciding whether we
            // fully-support the TypeDef or not
            verifyPropertiesForTypeDef(typeDef, mappedProperties, gaps);

            // If we were unable to verify everything, throw exception indicating it is not a supported TypeDef
            if (!gaps.isEmpty()) {
                if (log.isWarnEnabled()) { log.warn("Unable to verify type definition {} due to missing property mappings for: {}", typeDef.getName(), String.join(", ", gaps)); }
                raiseTypeDefNotSupportedException(IGCOMRSErrorCode.TYPEDEF_NOT_MAPPED, typeDef.getName(), methodName);
                return false;
            } else {
                // Everything checked out, so return true
                return true;
            }

        } else {
            // It is completely unknown to us, so go ahead and try to addTypeDef
            return false;
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean verifyAttributeTypeDef(String userId, AttributeTypeDef attributeTypeDef) throws
            InvalidParameterException,
            RepositoryErrorException,
            InvalidTypeDefException {

        final String methodName = "verifyAttributeTypeDef";
        final String typeDefParameterName = "attributeTypeDef";
        super.attributeTypeDefParameterValidation(userId, attributeTypeDef, typeDefParameterName, methodName);

        boolean bImplemented;
        switch (attributeTypeDef.getCategory()) {
            case PRIMITIVE:
            case COLLECTION:
                bImplemented = true;
                break;
            case ENUM_DEF:
                bImplemented = (attributeMappingStore.getAttributeTypeDefByGUID(attributeTypeDef.getGUID()) != null);
                break;
            default:
                bImplemented = false;
                break;
        }

        return bImplemented;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityDetail isEntityKnown(String userId, String guid) throws
            InvalidParameterException,
            RepositoryErrorException {

        final String methodName = "isEntityKnown";
        super.getInstanceParameterValidation(userId, guid, methodName);

        EntityDetail detail = null;
        try {
            detail = getEntityDetail(userId, guid);
        } catch (EntityNotKnownException e) {
            if (log.isInfoEnabled()) { log.info("Entity {} not known to the repository.", guid, e); }
        }
        return detail;

    }


    /**
     * {@inheritDoc}
     */
    @Override
    public EntitySummary getEntitySummary(String userId, String guid) throws
            InvalidParameterException,
            RepositoryErrorException,
            EntityNotKnownException {

        final String methodName = "getEntitySummary";
        super.getInstanceParameterValidation(userId, guid, methodName);

        if (log.isDebugEnabled()) { log.debug("getEntitySummary with guid = {}", guid); }

        // Lookup the basic asset based on the RID (strip off prefix (indicating a generated type), if there)
        IGCEntityGuid igcGuid = IGCEntityGuid.fromGuid(guid);
        if (igcGuid == null) {
            raiseEntityNotKnownException(IGCOMRSErrorCode.ENTITY_NOT_KNOWN, guid, "<null>", methodName);
        }

        EntitySummary summary = null;
        String igcType = igcGuid.getAssetType();
        String prefix = igcGuid.getGeneratedPrefix();

        if (igcType.equals(IGCRepositoryHelper.DEFAULT_IGC_TYPE)) {
            /* If the asset type returned has an IGC-listed type of 'main_object', it isn't one that the REST API
             * of IGC supports (eg. a data rule detail object, a column analysis master object, etc)...
             * Trying to further process it will result in failed REST API requests; so we should skip these objects */
            IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.UNSUPPORTED_OBJECT_TYPE;
            String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                    guid,
                    igcType,
                    repositoryName);
            raiseRepositoryErrorException(errorCode, errorMessage, methodName);
        } else {

            // Otherwise, retrieve the mapping dynamically based on the type of asset
            EntityMappingInstance entityMap = igcRepositoryHelper.getMappingInstanceForParameters(
                    igcGuid.getAssetType(),
                    igcGuid.getRid(),
                    prefix,
                    userId);

            if (entityMap != null) {
                // 2. Apply the mapping to the object, and retrieve the resulting EntityDetail
                summary = EntityMapping.getEntitySummary(entityMap);
            } else {
                IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.TYPEDEF_NOT_MAPPED;
                String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                        prefix + igcType,
                        repositoryName);
                raiseRepositoryErrorException(errorCode, errorMessage, methodName);
            }

        }

        return summary;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityDetail getEntityDetail(String userId, String guid) throws
            InvalidParameterException,
            RepositoryErrorException,
            EntityNotKnownException {

        final String methodName = "getEntityDetail";
        super.getInstanceParameterValidation(userId, guid, methodName);

        // Lookup the basic asset based on the RID (strip off prefix (indicating a generated type), if there)
        IGCEntityGuid igcGuid = IGCEntityGuid.fromGuid(guid);
        if (igcGuid == null) {
            raiseEntityNotKnownException(IGCOMRSErrorCode.ENTITY_NOT_KNOWN, guid, "<null>", methodName);
        }

        return igcRepositoryHelper.getEntityDetail(userId, igcGuid);

    }

    /**
     * Return the relationships for a specific entity. Note that currently this will only work for relationships known
     * to (originated within) IGC, and that not all parameters are (yet) implemented.
     *
     * @param userId unique identifier for requesting user.
     * @param entityGUID String unique identifier for the entity.
     * @param relationshipTypeGUID String GUID of the the type of relationship required (null for all).
     * @param fromRelationshipElement the starting element number of the relationships to return.
     *                                This is used when retrieving elements
     *                                beyond the first page of results. Zero means start from the first element.
     * @param limitResultsByStatus Not implemented for IGC -- will only retrieve ACTIVE entities.
     * @param asOfTime Must be null (history not implemented for IGC).
     * @param sequencingProperty Must be null (there are no properties on IGC relationships).
     * @param sequencingOrder Enum defining how the results should be ordered.
     * @param pageSize -- the maximum number of result classifications that can be returned on this request.  Zero means
     *                 unrestricted return results size.
     * @return Relationships list.  Null means no relationships associated with the entity.
     * @throws InvalidParameterException a parameter is invalid or null.
     * @throws TypeErrorException the type guid passed on the request is not known by the
     *                              metadata collection.
     * @throws RepositoryErrorException there is a problem communicating with the metadata repository where
     *                                  the metadata collection is stored.
     * @throws EntityNotKnownException the requested entity instance is not known in the metadata collection.
     * @throws PropertyErrorException the sequencing property is not valid for the attached classifications.
     * @throws PagingErrorException the paging/sequencing parameters are set up incorrectly.
     * @throws FunctionNotSupportedException the repository does not support the asOfTime parameter.
     * @throws UserNotAuthorizedException the userId is not permitted to perform this operation.
     */
    @Override
    public List<Relationship> getRelationshipsForEntity(String userId,
                                                        String entityGUID,
                                                        String relationshipTypeGUID,
                                                        int fromRelationshipElement,
                                                        List<InstanceStatus> limitResultsByStatus,
                                                        Date asOfTime,
                                                        String sequencingProperty,
                                                        SequencingOrder sequencingOrder,
                                                        int pageSize) throws
            InvalidParameterException,
            TypeErrorException,
            RepositoryErrorException,
            EntityNotKnownException,
            PropertyErrorException,
            PagingErrorException,
            FunctionNotSupportedException,
            UserNotAuthorizedException {

        final String methodName = "getRelationshipsForEntity";
        super.getRelationshipsForEntityParameterValidation(
                userId,
                entityGUID,
                relationshipTypeGUID,
                fromRelationshipElement,
                limitResultsByStatus,
                asOfTime,
                sequencingProperty,
                sequencingOrder,
                pageSize
        );

        ArrayList<Relationship> alRelationships = new ArrayList<>();

        // Immediately throw unimplemented exception if trying to retrieve historical view or sequence by property
        if (asOfTime != null) {
            raiseFunctionNotSupportedException(IGCOMRSErrorCode.NO_HISTORY, methodName);
        } else if (sequencingProperty != null
                || (sequencingOrder != null
                &&
                (sequencingOrder.equals(SequencingOrder.PROPERTY_ASCENDING)
                        || sequencingOrder.equals(SequencingOrder.PROPERTY_DESCENDING)))
        ) {
            IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.NO_RELATIONSHIP_PROPERTIES;
            String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(repositoryName);
            throw new PropertyErrorException(errorCode.getHTTPErrorCode(),
                    this.getClass().getName(),
                    methodName,
                    errorMessage,
                    errorCode.getSystemAction(),
                    errorCode.getUserAction());
        } else if (limitResultsByStatus == null
                || (limitResultsByStatus.size() == 1 && limitResultsByStatus.contains(InstanceStatus.ACTIVE))) {

            // Otherwise, only bother searching if we are after ACTIVE (or "all") entities -- non-ACTIVE means we
            // will just return an empty list

            // 0. see if the entityGUID has a prefix (indicating a generated type)
            IGCEntityGuid igcGuid = IGCEntityGuid.fromGuid(entityGUID);
            if (igcGuid == null) {
                raiseEntityNotKnownException(IGCOMRSErrorCode.ENTITY_NOT_KNOWN, entityGUID, "<null>", methodName);
            }
            String rid = igcGuid.getRid();
            String prefix = igcGuid.getGeneratedPrefix();
            String igcType = igcGuid.getAssetType();

            // Ensure the entity actually exists (if not, throw error to that effect)
            EntityMappingInstance entityMap = igcRepositoryHelper.getMappingInstanceForParameters(
                    igcType,
                    rid,
                    prefix,
                    userId);

            if (entityMap != null) {
                // 2. Apply the mapping to the object, and retrieve the resulting relationships
                alRelationships.addAll(
                        EntityMapping.getMappedRelationships(
                                igcGuid,
                                entityMap,
                                relationshipTypeGUID,
                                fromRelationshipElement,
                                sequencingOrder,
                                pageSize)
                );
            } else {
                IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.TYPEDEF_NOT_MAPPED;
                String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                        prefix + igcType,
                        repositoryName);
                raiseRepositoryErrorException(errorCode, errorMessage, methodName);
            }

        }

        return alRelationships.isEmpty() ? null : alRelationships;

    }

    /**
     * Return a list of entities that match the supplied properties according to the match criteria.  The results
     * can be returned over many pages.
     *
     * @param userId unique identifier for requesting user.
     * @param entityTypeGUID String unique identifier for the entity type of interest (null means any entity type).
     * @param matchProperties Optional list of entity properties to match (where any String property's value should
     *                        be defined as a Java regular expression, even if it should be an exact match).
     * @param matchCriteria Enum defining how the properties should be matched to the entities in the repository.
     * @param fromEntityElement the starting element number of the entities to return.
     *                                This is used when retrieving elements
     *                                beyond the first page of results. Zero means start from the first element.
     * @param limitResultsByStatus Not implemented for IGC, only ACTIVE entities will be returned.
     * @param limitResultsByClassification List of classifications that must be present on all returned entities.
     * @param asOfTime Must be null (history not implemented for IGC).
     * @param sequencingProperty String name of the entity property that is to be used to sequence the results.
     *                           Null means do not sequence on a property name (see SequencingOrder).
     * @param sequencingOrder Enum defining how the results should be ordered.
     * @param pageSize the maximum number of result entities that can be returned on this request.  Zero means
     *                 unrestricted return results size.
     * @return a list of entities matching the supplied criteria where null means no matching entities in the metadata
     * collection.
     *
     * @throws InvalidParameterException a parameter is invalid or null.
     * @throws TypeErrorException the type guid passed on the request is not known by the
     *                              metadata collection.
     * @throws RepositoryErrorException there is a problem communicating with the metadata repository where
     *                                    the metadata collection is stored.
     * @throws PropertyErrorException the properties specified are not valid for any of the requested types of
     *                                  entity.
     * @throws PagingErrorException the paging/sequencing parameters are set up incorrectly.
     * @throws FunctionNotSupportedException the repository does not support one of the provided parameters.
     * @throws UserNotAuthorizedException the userId is not permitted to perform this operation.
     * @see OMRSRepositoryHelper#getExactMatchRegex(String)
     */
    @Override
    public List<EntityDetail> findEntitiesByProperty(String userId,
                                                     String entityTypeGUID,
                                                     InstanceProperties matchProperties,
                                                     MatchCriteria matchCriteria,
                                                     int fromEntityElement,
                                                     List<InstanceStatus> limitResultsByStatus,
                                                     List<String> limitResultsByClassification,
                                                     Date asOfTime,
                                                     String sequencingProperty,
                                                     SequencingOrder sequencingOrder,
                                                     int pageSize) throws
            InvalidParameterException,
            TypeErrorException,
            RepositoryErrorException,
            PropertyErrorException,
            PagingErrorException,
            FunctionNotSupportedException,
            UserNotAuthorizedException {

        final String methodName = "findEntitiesByProperty";
        super.findEntitiesByPropertyParameterValidation(
                userId,
                entityTypeGUID,
                matchProperties,
                matchCriteria,
                fromEntityElement,
                limitResultsByStatus,
                limitResultsByClassification,
                asOfTime,
                sequencingProperty,
                sequencingOrder,
                pageSize
        );

        ArrayList<EntityDetail> entityDetails = new ArrayList<>();

        // Immediately throw unimplemented exception if trying to retrieve historical view
        if (asOfTime != null) {
            raiseFunctionNotSupportedException(IGCOMRSErrorCode.NO_HISTORY, methodName);
        } else if (limitResultsByStatus == null
                || (limitResultsByStatus.size() == 1 && limitResultsByStatus.contains(InstanceStatus.ACTIVE))) {

            // Otherwise, only bother searching if we are after ACTIVE (or "all") entities -- non-ACTIVE means we
            // will just return an empty list

            // Short-circuit iterating through mappings if we are searching for something by qualifiedName,
            // in which case we should be able to infer the type we need to search based on the Identity implied
            // by the qualifiedName provided
            if (matchProperties != null
                    && matchProperties.getPropertyCount() == 1
                    && matchProperties.getPropertyNames().next().equals("qualifiedName")) {
                String qualifiedNameToFind = (String) ((PrimitivePropertyValue)matchProperties.getInstanceProperties().get("qualifiedName")).getPrimitiveValue();
                log.debug("Short-circuiting find to qualifiedName search: {}", qualifiedNameToFind);
                if (repositoryHelper.isExactMatchRegex(qualifiedNameToFind) || repositoryHelper.isEndsWithRegex(qualifiedNameToFind)) {

                    List<EntityMapping> mappers = new ArrayList<>();
                    // If we are doing a NONE-based search, we still need to consider all mappings and cannot short-
                    // circuit based on the type implicit in the qualifiedName
                    if (matchCriteria != null && matchCriteria.equals(MatchCriteria.NONE)) {
                        mappers = findMappingsForInputs(entityTypeGUID, null, userId);
                    } else {
                        // Otherwise we should be able to optimise by trying to pull out the type implicit in the
                        // qualifiedName itself
                        String unqualifiedName = repositoryHelper.getUnqualifiedLiteralString(qualifiedNameToFind);
                        String qualifiedName = unqualifiedName;
                        String prefix = null;
                        if (IGCRepositoryHelper.isQualifiedNameOfGeneratedEntity(unqualifiedName)) {
                            prefix = IGCRepositoryHelper.getPrefixFromGeneratedQualifiedName(unqualifiedName);
                            qualifiedName = IGCRepositoryHelper.getSearchableQualifiedName(unqualifiedName);
                            if (log.isDebugEnabled()) {
                                log.debug(" ... generated name with prefix {} and name: {}", prefix, qualifiedName);
                            }
                        }
                        Identity.StringType stringType = Identity.StringType.EXACT;
                        if (repositoryHelper.isEndsWithRegex(qualifiedNameToFind)) {
                            stringType = Identity.StringType.ENDS_WITH;
                        }
                        Identity identity = Identity.getFromString(qualifiedName, igcRestClient, stringType);
                        if (identity != null && !identity.isPartial()) {
                            // Resolve the asset type directly from the identity, if we can (only possible if it is not
                            // partial, because if it is partial it may actually need a prefix which is not there)
                            if (log.isDebugEnabled()) { log.debug(" ... proceeding on basis of identity: {}", identity); }
                            String igcType = identity.getAssetType();
                            mappers.add(igcRepositoryHelper.getEntityMappingByIgcType(igcType, prefix));
                        } else if (identity != null) {
                            // If all we have is a partial identity, get all of the mappers for that asset type
                            // (to ensure we include both direct-mapped and generated entities - if one is not
                            // applicable based on the type requested by the method, that will be excluded below)
                            String igcType = identity.getAssetType();
                            mappers = igcRepositoryHelper.getEntityMappingsByIgcType(igcType);
                        } else {
                            // Otherwise fall-back to taking the mappings from the entity information received on the
                            // method itself
                            mappers = findMappingsForInputs(entityTypeGUID, prefix, userId);
                        }
                    }
                    for (EntityMapping mapper : mappers) {
                        // validate mapped OMRS type against the provided entityTypeGUID (if non-null), and
                        // only proceed with the search if IGC identity is a (sub)type of the one requested
                        boolean runSearch = true;
                        if (entityTypeGUID != null) {
                            String mappedOmrsTypeName = mapper.getOmrsTypeDefName();
                            TypeDef entityTypeDef = repositoryHelper.getTypeDef(repositoryName,
                                    "entityTypeGUID",
                                    entityTypeGUID,
                                    methodName);
                            runSearch = repositoryHelper.isTypeOf(metadataCollectionId, mappedOmrsTypeName, entityTypeDef.getName());
                        }
                        if (runSearch) {
                            igcRepositoryHelper.processResultsForMapping(
                                    mapper,
                                    entityDetails,
                                    userId,
                                    entityTypeGUID,
                                    matchProperties,
                                    matchCriteria,
                                    fromEntityElement,
                                    limitResultsByClassification,
                                    sequencingProperty,
                                    sequencingOrder,
                                    pageSize
                            );
                        } else {
                            log.warn("Unable to confirm that the qualifiedName-embedded type ({}) is a subtype of the requested type ({}) -- skipping qualifiedName search.", mapper.getOmrsTypeDefName(), entityTypeGUID);
                        }
                    }

                } else if (repositoryHelper.isStartsWithRegex(qualifiedNameToFind) || repositoryHelper.isContainsRegex(qualifiedNameToFind)) {

                    List<EntityMapping> mappers;
                    // If we are doing a NONE-based search, we still need to consider all mappings and cannot short-
                    // circuit based on the type implicit in the qualifiedName
                    if (matchCriteria != null && matchCriteria.equals(MatchCriteria.NONE)) {
                        mappers = findMappingsForInputs(entityTypeGUID, null, userId);
                    } else {
                        // Otherwise try to optimise based on whatever information we can find implicitly in the
                        // qualifiedName itself
                        String unqualifiedName = repositoryHelper.getUnqualifiedLiteralString(qualifiedNameToFind);
                        String prefix = null;
                        if (IGCRepositoryHelper.isQualifiedNameOfGeneratedEntity(unqualifiedName)) {
                            prefix = IGCRepositoryHelper.getPrefixFromGeneratedQualifiedName(unqualifiedName);
                        }
                        mappers = findMappingsForInputs(entityTypeGUID, prefix, userId);
                    }
                    for (EntityMapping mapper : mappers) {
                        igcRepositoryHelper.processResultsForMapping(
                                mapper,
                                entityDetails,
                                userId,
                                entityTypeGUID,
                                matchProperties,
                                matchCriteria,
                                fromEntityElement,
                                limitResultsByClassification,
                                sequencingProperty,
                                sequencingOrder,
                                pageSize
                        );
                    }
                }

            } else {

                // If we're searching for anything else, however, we need to iterate through all of the possible mappings
                // to ensure a full set of search results, so construct and run an appropriate search for each one
                List<EntityMapping> mappingsToSearch = getMappingsToSearch(entityTypeGUID, userId);

                for (EntityMapping mapping : mappingsToSearch) {

                    // Only continue to add results to the list if we are after all results (pageSize of 0) or we have
                    // not yet filled up the page size in the list
                    if (pageSize == 0 || (pageSize > 0 && entityDetails.size() < pageSize)) {
                        igcRepositoryHelper.processResultsForMapping(
                                mapping,
                                entityDetails,
                                userId,
                                entityTypeGUID,
                                matchProperties,
                                matchCriteria,
                                fromEntityElement,
                                limitResultsByClassification,
                                sequencingProperty,
                                sequencingOrder,
                                pageSize
                        );
                    }

                }

            }

        }

        return entityDetails.isEmpty() ? null : entityDetails;

    }

    /**
     * Return a list of entities that have the requested type of classification attached.
     *
     * @param userId unique identifier for requesting user.
     * @param entityTypeGUID unique identifier for the type of entity requested.  Null mans any type of entity.
     * @param classificationName name of the classification a null is not valid.
     * @param matchClassificationProperties list of classification properties used to narrow the search (where any String
     *                                      property's value should be defined as a Java regular expression, even if it
     *                                      should be an exact match).
     * @param matchCriteria Enum defining how the properties should be matched to the classifications in the repository.
     * @param fromEntityElement the starting element number of the entities to return.
     *                                This is used when retrieving elements
     *                                beyond the first page of results. Zero means start from the first element.
     * @param limitResultsByStatus Not implemented for IGC, only ACTIVE entities will be returned.
     * @param asOfTime Requests a historical query of the entity.  Null means return the present values. (not implemented
     *                 for IGC, must be null)
     * @param sequencingProperty String name of the entity property that is to be used to sequence the results.
     *                           Null means do not sequence on a property name (see SequencingOrder).
     * @param sequencingOrder Enum defining how the results should be ordered.
     * @param pageSize the maximum number of result entities that can be returned on this request.  Zero means
     *                 unrestricted return results size.
     * @return a list of entities matching the supplied criteria where null means no matching entities in the metadata
     * collection.
     * @throws InvalidParameterException a parameter is invalid or null.
     * @throws TypeErrorException the type guid passed on the request is not known by the
     *                              metadata collection.
     * @throws RepositoryErrorException there is a problem communicating with the metadata repository where
     *                                    the metadata collection is stored.
     * @throws ClassificationErrorException the classification request is not known to the metadata collection.
     * @throws PropertyErrorException the properties specified are not valid for the requested type of
     *                                  classification.
     * @throws PagingErrorException the paging/sequencing parameters are set up incorrectly.
     * @throws FunctionNotSupportedException the repository does not support one of the provided parameters.
     * @throws UserNotAuthorizedException the userId is not permitted to perform this operation.
     * @see OMRSRepositoryHelper#getExactMatchRegex(String)
     */
    @Override
    public List<EntityDetail> findEntitiesByClassification(String userId,
                                                           String entityTypeGUID,
                                                           String classificationName,
                                                           InstanceProperties matchClassificationProperties,
                                                           MatchCriteria matchCriteria,
                                                           int fromEntityElement,
                                                           List<InstanceStatus> limitResultsByStatus,
                                                           Date asOfTime,
                                                           String sequencingProperty,
                                                           SequencingOrder sequencingOrder,
                                                           int pageSize) throws
            InvalidParameterException,
            TypeErrorException,
            RepositoryErrorException,
            ClassificationErrorException,
            PropertyErrorException,
            PagingErrorException,
            FunctionNotSupportedException,
            UserNotAuthorizedException {

        final String methodName = "findEntitiesByClassification";
        this.findEntitiesByClassificationParameterValidation(
                userId,
                entityTypeGUID,
                classificationName,
                matchClassificationProperties,
                matchCriteria,
                fromEntityElement,
                limitResultsByStatus,
                asOfTime,
                sequencingProperty,
                sequencingOrder,
                pageSize
        );

        ArrayList<EntityDetail> entityDetails = new ArrayList<>();

        // Immediately throw unimplemented exception if trying to retrieve historical view
        if (asOfTime != null) {
            raiseFunctionNotSupportedException(IGCOMRSErrorCode.NO_HISTORY, methodName);
        } else if (limitResultsByStatus == null
                || (limitResultsByStatus.size() == 1 && limitResultsByStatus.contains(InstanceStatus.ACTIVE))) {

            // Otherwise, only bother searching if we are after ACTIVE (or "all") entities -- non-ACTIVE means we
            // will just return an empty list

            List<EntityMapping> mappingsToSearch = getMappingsToSearch(entityTypeGUID, userId);

            // Now iterate through all of the mappings we need to search, construct and run an appropriate search
            // for each one
            for (EntityMapping mapping : mappingsToSearch) {

                ClassificationMapping foundMapping = null;

                // Check which classifications (if any) are implemented for the entity mapping
                List<ClassificationMapping> classificationMappings = mapping.getClassificationMappers();
                for (ClassificationMapping classificationMapping : classificationMappings) {

                    // Check whether the implemented classification matches the one we're searching based on
                    String candidateName = classificationMapping.getOmrsClassificationType();
                    if (candidateName.equals(classificationName)) {
                        foundMapping = classificationMapping;
                        break;
                    }

                }

                // Only proceed if we have found a classification mapping for this entity that matches the search
                // criteria provided
                if (foundMapping != null) {

                    IGCSearch igcSearch = new IGCSearch();
                    igcSearch.addType(mapping.getIgcAssetType());
                    IGCSearchConditionSet igcSearchConditionSet = new IGCSearchConditionSet();

                    IGCRepositoryHelper.addTypeSpecificConditions(mapping,
                            matchCriteria,
                            null,
                            igcSearchConditionSet);

                    // Compose the search criteria for the classification as a set of nested conditions, so that
                    // matchCriteria does not change the meaning of what we're searching
                    IGCSearchConditionSet baseCriteria = foundMapping.getIGCSearchCriteria(matchClassificationProperties);
                    igcSearchConditionSet.addNestedConditionSet(baseCriteria);

                    IGCSearchSorting igcSearchSorting = null;
                    if (sequencingProperty == null && sequencingOrder != null) {
                        igcSearchSorting = IGCRepositoryHelper.sortFromNonPropertySequencingOrder(sequencingOrder);
                    }

                    IGCRepositoryHelper.setConditionsFromMatchCriteria(igcSearchConditionSet, matchCriteria);
                    igcSearch.addProperties(mapping.getAllPropertiesForEntityDetail(igcRestClient, mapping.getIgcAssetType()));
                    igcSearch.addConditions(igcSearchConditionSet);

                    igcRepositoryHelper.setPagingForSearch(igcSearch, fromEntityElement, pageSize);

                    if (igcSearchSorting != null) {
                        igcSearch.addSortingCriteria(igcSearchSorting);
                    }

                    igcRepositoryHelper.processResults(
                            mapping,
                            this.igcRestClient.search(igcSearch),
                            entityDetails,
                            null,
                            null,
                            null,
                            pageSize,
                            userId
                    );

                } else {
                    if (log.isInfoEnabled()) { log.info("No classification mapping has been implemented for {} on entity {} -- skipping from search.", classificationName, mapping.getOmrsTypeDefName()); }
                }

            }

        }

        return entityDetails.isEmpty() ? null : entityDetails;

    }

    /**
     * Return a list of entities whose string based property values match the search criteria.  The
     * search criteria may include regex style wild cards.
     *
     * @param userId unique identifier for requesting user.
     * @param entityTypeGUID GUID of the type of entity to search for. Null means all types will
     *                       be searched (could be slow so not recommended).
     * @param searchCriteria String Java regular expression used to match against any of the String property values
     *                       within the entities of the supplied type, even if it should be an exact match.
     *                       (Retrieve all entities of the supplied type if this is either null or an empty string.)
     * @param fromEntityElement the starting element number of the entities to return.
     *                                This is used when retrieving elements
     *                                beyond the first page of results. Zero means start from the first element.
     * @param limitResultsByStatus Not implemented for IGC, only ACTIVE entities will be returned.
     * @param limitResultsByClassification List of classifications that must be present on all returned entities.
     * @param asOfTime Must be null (history not implemented for IGC).
     * @param sequencingProperty String name of the property that is to be used to sequence the results.
     *                           Null means do not sequence on a property name (see SequencingOrder).
     *                           (currently not implemented for IGC)
     * @param sequencingOrder Enum defining how the results should be ordered.
     * @param pageSize the maximum number of result entities that can be returned on this request.  Zero means
     *                 unrestricted return results size.
     * @return a list of entities matching the supplied criteria; null means no matching entities in the metadata
     * collection.
     * @throws InvalidParameterException a parameter is invalid or null.
     * @throws TypeErrorException the type guid passed on the request is not known by the
     *                              metadata collection.
     * @throws RepositoryErrorException there is a problem communicating with the metadata repository where
     *                                    the metadata collection is stored.
     * @throws PropertyErrorException the sequencing property specified is not valid for any of the requested types of
     *                                  entity.
     * @throws PagingErrorException the paging/sequencing parameters are set up incorrectly.
     * @throws FunctionNotSupportedException the repository does not support one of the provided parameters.
     * @throws UserNotAuthorizedException the userId is not permitted to perform this operation.
     * @see OMRSRepositoryHelper#getExactMatchRegex(String)
     * @see OMRSRepositoryHelper#getContainsRegex(String)
     */
    @Override
    public List<EntityDetail> findEntitiesByPropertyValue(String userId,
                                                          String entityTypeGUID,
                                                          String searchCriteria,
                                                          int fromEntityElement,
                                                          List<InstanceStatus> limitResultsByStatus,
                                                          List<String> limitResultsByClassification,
                                                          Date asOfTime,
                                                          String sequencingProperty,
                                                          SequencingOrder sequencingOrder,
                                                          int pageSize) throws
            InvalidParameterException,
            TypeErrorException,
            RepositoryErrorException,
            PropertyErrorException,
            PagingErrorException,
            FunctionNotSupportedException,
            UserNotAuthorizedException {

        final String methodName = "findEntitiesByPropertyValue";
        super.findEntitiesByPropertyValueParameterValidation(
                userId,
                entityTypeGUID,
                searchCriteria,
                fromEntityElement,
                limitResultsByStatus,
                limitResultsByClassification,
                asOfTime,
                sequencingProperty,
                sequencingOrder,
                pageSize
        );

        ArrayList<EntityDetail> entityDetails = new ArrayList<>();

        // Immediately throw unimplemented exception if trying to retrieve historical view
        if (asOfTime != null) {
            raiseFunctionNotSupportedException(IGCOMRSErrorCode.NO_HISTORY, methodName);
        } else if (limitResultsByStatus == null
                || (limitResultsByStatus.size() == 1 && limitResultsByStatus.contains(InstanceStatus.ACTIVE))) {

            // If the string we're looking for can in any way be interpreted as an identity string, treat it as such
            // and search based on qualifiedName
            if (igcRepositoryHelper.isIdentityString(searchCriteria)) {

                if (log.isDebugEnabled()) { log.debug("Treating {} as an identity-string (qualifiedName) search.", searchCriteria); }
                // Only proceed down this path if there is any kind of qualifiedName that was received
                InstanceProperties matchProperties = repositoryHelper.addStringPropertyToInstance(
                        repositoryName,
                        null,
                        "qualifiedName",
                        searchCriteria,
                        methodName
                );
                return findEntitiesByProperty(
                        userId,
                        entityTypeGUID,
                        matchProperties,
                        MatchCriteria.ALL,
                        fromEntityElement,
                        limitResultsByStatus,
                        limitResultsByClassification,
                        null,
                        sequencingProperty,
                        sequencingOrder,
                        pageSize
                );

            } else {
                if (log.isDebugEnabled()) { log.debug("Treating {} as a normal (non-identity-string / non-qualifiedName) search.", searchCriteria); }
            }

            // Otherwise, only bother searching if we are after ACTIVE (or "all") entities -- non-ACTIVE means we
            // will just return an empty list
            List<EntityMapping> mappingsToSearch = getMappingsToSearch(entityTypeGUID, userId);

            // Now iterate through all of the mappings we need to search, construct and run an appropriate search
            // for each one
            for (EntityMapping mapping : mappingsToSearch) {

                if (pageSize == 0 || (pageSize > 0 && entityDetails.size() < pageSize)) {
                    IGCSearch igcSearch = new IGCSearch();
                    String igcAssetType = igcRepositoryHelper.addTypeToSearch(mapping, igcSearch);

                    // If the type we are searching for is a user type, we need to consider complexity in the search
                    // criteria as it could be from the qualifiedName, which in this one case is actually a combination
                    // of various fields on the instance
                    StringBuilder sbNewCriteria = new StringBuilder();
                    if (IGCRestConstants.getUserTypes().contains(igcAssetType) && searchCriteria != null) {
                        // In all cases we should take out what is likely to be the full name
                        String[] tokens = searchCriteria.split(" ");
                        if (tokens.length > 1) {
                            if (repositoryHelper.isExactMatchRegex(searchCriteria) || repositoryHelper.isStartsWithRegex(searchCriteria)) {
                                sbNewCriteria.append("\\Q");
                                if (tokens.length == 2) {
                                    sbNewCriteria.append(tokens[1]);
                                } else {
                                    int iLastToken = tokens.length - 1;
                                    sbNewCriteria.append(tokens[iLastToken - 1]).append(" ").append(tokens[iLastToken]);
                                }
                            } else if (repositoryHelper.isEndsWithRegex(searchCriteria) || repositoryHelper.isContainsRegex(searchCriteria)) {
                                sbNewCriteria.append(".*\\Q");
                                if (tokens.length == 2) {
                                    sbNewCriteria.append(tokens[1]);
                                } else {
                                    int iLastToken = tokens.length - 1;
                                    sbNewCriteria.append(tokens[iLastToken - 1]).append(" ").append(tokens[iLastToken]);
                                }
                            }
                        }
                    }

                    // Get list of string properties from the asset type -- these are the list of properties we should use
                    // for the search
                    List<String> properties = igcRestClient.getAllStringPropertiesForType(igcAssetType);
                    Set<String> simpleMappedIgcProperties = mapping.getSimpleMappedIgcProperties();
                    if (properties != null) {

                        IGCSearchConditionSet classificationLimiters = igcRepositoryHelper.getSearchCriteriaForClassifications(
                                igcAssetType,
                                limitResultsByClassification
                        );

                        if (limitResultsByClassification != null && !limitResultsByClassification.isEmpty() && classificationLimiters == null) {
                            if (log.isInfoEnabled()) {
                                log.info("Classification limiters were specified, but none apply to the asset type {}, so excluding this asset type from search.", igcAssetType);
                            }
                        } else {

                            IGCSearchConditionSet outerConditions = new IGCSearchConditionSet();
                            IGCRepositoryHelper.addTypeSpecificConditions(mapping,
                                    MatchCriteria.ALL,
                                    null,
                                    outerConditions);

                            // If the searchCriteria is empty, retrieve all entities of the type (no conditions)
                            String newCriteria = sbNewCriteria.toString();
                            if (newCriteria.equals("")) {
                                newCriteria = searchCriteria;
                            }
                            if (newCriteria != null && !newCriteria.equals("")) {

                                // POST'd search to IGC doesn't work on v11.7.0.2 using long_description
                                // Using "searchText" requires using "searchProperties" (no "where" conditions) -- but does not
                                // work with 'main_object', must be used with a specific asset type
                                // Therefore for v11.7.0.2 we will simply drop long_description from the fields we search
                                if (igcRestClient.getIgcVersion().isEqualTo(IGCVersionEnum.V11702)) {
                                    ArrayList<String> propertiesWithoutLongDescription = new ArrayList<>();
                                    for (String property : properties) {
                                        if (!property.equals("long_description")) {
                                            propertiesWithoutLongDescription.add(property);
                                        }
                                    }
                                    properties = propertiesWithoutLongDescription;
                                }

                                IGCSearchConditionSet innerConditions = new IGCSearchConditionSet();
                                innerConditions.setMatchAnyCondition(true);
                                for (String property : properties) {
                                    // Only include the simple-mapped properties in the search here, as any complex-mapped
                                    // properties should be included by the criteria below, thereby excluding results for
                                    // things like 'modified_by' and 'created_by'
                                    if (simpleMappedIgcProperties.contains(property)) {
                                        innerConditions.addCondition(
                                                IGCRepositoryHelper.getRegexSearchCondition(
                                                        repositoryHelper,
                                                        repositoryName,
                                                        methodName,
                                                        property,
                                                        newCriteria
                                                ));
                                    }
                                }
                                // Add any complex mappings needed by the mapping (a no-op if there are none)
                                mapping.addComplexStringSearchCriteria(repositoryHelper,
                                        repositoryName,
                                        igcRestClient,
                                        innerConditions,
                                        newCriteria);
                                outerConditions.addNestedConditionSet(innerConditions);

                            }

                            if (classificationLimiters != null) {
                                outerConditions.addNestedConditionSet(classificationLimiters);
                                outerConditions.setMatchAnyCondition(false);
                            }

                            IGCSearchSorting igcSearchSorting = null;
                            if (sequencingProperty == null && sequencingOrder != null) {
                                igcSearchSorting = IGCRepositoryHelper.sortFromNonPropertySequencingOrder(sequencingOrder);
                            }

                            igcSearch.addConditions(outerConditions);

                            igcRepositoryHelper.setPagingForSearch(igcSearch, fromEntityElement, pageSize);

                            if (igcSearchSorting != null) {
                                igcSearch.addSortingCriteria(igcSearchSorting);
                            }

                            // Add properties for this IGC asset type to the search, since ultimately we will
                            // be retrieving EntityDetails for each result
                            igcSearch.addProperties(mapping.getAllPropertiesForEntityDetail(igcRestClient, igcAssetType));

                            igcRepositoryHelper.processResults(
                                    mapping,
                                    this.igcRestClient.search(igcSearch),
                                    entityDetails,
                                    null,
                                    null,
                                    searchCriteria,
                                    pageSize,
                                    userId
                            );

                        }

                    } else {
                        if (log.isWarnEnabled()) {
                            log.warn("Unable to find POJO to handle IGC asset type '{}' -- skipping search against this asset type.", igcAssetType);
                        }
                    }

                }
            }

        }

        return entityDetails.isEmpty() ? null : entityDetails;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Relationship isRelationshipKnown(String userId, String guid) throws
            InvalidParameterException,
            RepositoryErrorException {

        final String methodName = "isRelationshipKnown";
        super.getInstanceParameterValidation(userId, guid, methodName);

        Relationship relationship = null;
        try {
            relationship = getRelationship(userId, guid);
        } catch (RelationshipNotKnownException e) {
            if (log.isInfoEnabled()) { log.info("Could not find relationship {} in repository.", guid, e); }
        }
        return relationship;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Relationship getRelationship(String userId, String guid) throws
            InvalidParameterException,
            RepositoryErrorException,
            RelationshipNotKnownException {

        final String methodName = "getRelationship";
        super.getInstanceParameterValidation(userId, guid, methodName);

        if (log.isDebugEnabled()) { log.debug("Looking up relationship: {}", guid); }

        // Translate the key properties of the GUID into IGC-retrievables
        IGCRelationshipGuid igcRelationshipGuid = IGCRelationshipGuid.fromGuid(guid);
        if (igcRelationshipGuid == null) {
            raiseRelationshipNotKnownException(IGCOMRSErrorCode.RELATIONSHIP_NOT_KNOWN, guid, methodName);
        }
        String proxyOneRid = igcRelationshipGuid.getRid1();
        String proxyTwoRid = igcRelationshipGuid.getRid2();
        String proxyOneType = igcRelationshipGuid.getAssetType1();
        String proxyTwoType = igcRelationshipGuid.getAssetType2();
        String omrsRelationshipName = igcRelationshipGuid.getRelationshipType();

        List<RelationshipMapping> mappings = new ArrayList<>();
        List<Relationship> relationships = new ArrayList<>();

        // Should not need to translate from proxyone / proxytwo to alternative assets, as the RIDs provided
        // in the relationship GUID should already be pointing to the correct assets
        String relationshipLevelRid = igcRelationshipGuid.isRelationshipLevelObject() ? proxyOneRid : null;
        Reference proxyOne;
        Reference proxyTwo;
        RelationshipMapping relationshipMapping;
        if (relationshipLevelRid != null) {

            // TODO: replace with singular retrieval?
            Reference relationshipAsset = igcRestClient.getAssetRefById(relationshipLevelRid);
            String relationshipAssetType = relationshipAsset.getType();
            relationshipMapping = igcRepositoryHelper.getRelationshipMappingByTypes(
                    omrsRelationshipName,
                    relationshipAssetType,
                    relationshipAssetType
            );
            proxyOne = relationshipMapping.getProxyOneAssetFromAsset(relationshipAsset, igcRestClient).get(0);
            proxyTwo = relationshipMapping.getProxyTwoAssetFromAsset(relationshipAsset, igcRestClient).get(0);
            mappings.add(relationshipMapping);

        } else {

            // TODO: replace with singular retrieval?
            Reference oneEnd = igcRestClient.getAssetRefById(proxyOneRid);
            proxyTwo = igcRestClient.getAssetRefById(proxyTwoRid);
            relationshipMapping = igcRepositoryHelper.getRelationshipMappingByTypes(
                    omrsRelationshipName,
                    proxyOneType,
                    proxyTwoType
            );
            proxyOne = relationshipMapping.getProxyOneAssetFromAsset(oneEnd, igcRestClient).get(0);
            mappings.add(relationshipMapping);

        }

        // If no mapping was found, throw exception indicating the relationship type is not mapped
        if (mappings.isEmpty()) {
            IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.TYPEDEF_NOT_MAPPED;
            String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                    omrsRelationshipName,
                    repositoryName);
            raiseRepositoryErrorException(errorCode, errorMessage, methodName);
        }

        Relationship found = null;

        // Otherwise proceed by obtaining all relationships that are mapped
        try {
            TypeDef relationshipTypeDef = getTypeDefByName(userId, omrsRelationshipName);
            RelationshipMapping.getMappedRelationships(
                    igcomrsRepositoryConnector,
                    relationships,
                    mappings,
                    relationshipTypeDef.getGUID(),
                    proxyOne,
                    proxyTwo,
                    userId
            );
        } catch (TypeDefNotKnownException e) {
            IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.TYPEDEF_NOT_MAPPED;
            String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                    omrsRelationshipName,
                    repositoryName);
            raiseRepositoryErrorException(errorCode, errorMessage, methodName);
        }
        if (relationships.isEmpty()) {
            raiseRelationshipNotKnownException(IGCOMRSErrorCode.RELATIONSHIP_NOT_KNOWN, guid, methodName);
        } else if (relationships.size() > 1) {
            // Iterate through the found relationships if there is more than one, and return the first one whose
            // GUID matches the one requested
            for (Relationship relationship : relationships) {
                if (relationship.getGUID().equals(guid)) {
                    found = relationship;
                }
            }
        } else {
            found = relationships.get(0);
        }

        if (found == null) {
            raiseRelationshipNotKnownException(IGCOMRSErrorCode.RELATIONSHIP_NOT_KNOWN, guid, methodName);
        }

        return found;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Relationship> findRelationshipsByProperty(String userId,
                                                          String relationshipTypeGUID,
                                                          InstanceProperties matchProperties,
                                                          MatchCriteria matchCriteria,
                                                          int fromRelationshipElement,
                                                          List<InstanceStatus> limitResultsByStatus,
                                                          Date asOfTime,
                                                          String sequencingProperty,
                                                          SequencingOrder sequencingOrder,
                                                          int pageSize) throws
            InvalidParameterException,
            TypeErrorException,
            RepositoryErrorException,
            PropertyErrorException,
            PagingErrorException,
            FunctionNotSupportedException,
            UserNotAuthorizedException {

        final String  methodName = "findRelationshipsByProperty";

        this.findRelationshipsByPropertyParameterValidation(userId,
                relationshipTypeGUID,
                matchProperties,
                matchCriteria,
                fromRelationshipElement,
                limitResultsByStatus,
                asOfTime,
                sequencingProperty,
                sequencingOrder,
                pageSize);

        List<Relationship> relationships = new ArrayList<>();

        // Immediately throw unimplemented exception if trying to retrieve historical view
        if (asOfTime != null) {
            raiseFunctionNotSupportedException(IGCOMRSErrorCode.NO_HISTORY, methodName);
        } else if (limitResultsByStatus == null
                || (limitResultsByStatus.size() == 1 && limitResultsByStatus.contains(InstanceStatus.ACTIVE))) {

            // Otherwise, only bother searching if we are after ACTIVE (or "all") relationships -- non-ACTIVE means we
            // will just return an empty list
            // This method should give us only the leaf-level relationship mappings (those WITHOUT any subtypes)
            List<RelationshipMapping> mappingsToSearch = getRelationshipMappingsToSearch(relationshipTypeGUID, userId);

            // Now iterate through all of the mappings we need to search, construct and run an appropriate search
            // for each one
            for (RelationshipMapping mapping : mappingsToSearch) {

                // This will default to giving us the simple search criteria, if no complex criteria are defined
                // for the mapping.
                IGCSearch igcSearch = mapping.getComplexIGCSearchCriteria(
                        repositoryHelper,
                        repositoryName,
                        igcRestClient,
                        matchProperties,
                        matchCriteria
                );

                // TODO: handle sequencing -- here or as part of method above?
                igcRepositoryHelper.setPagingForSearch(igcSearch, fromRelationshipElement, pageSize);

                // Ensure we handle NONE semantics and literal values, as we do for findEntitiesByProperty
                boolean getNothing = false;
                if (matchProperties != null) {

                    Set<String> mappedOmrsProperties = mapping.getMappedOmrsPropertyNames();
                    Map<String, InstancePropertyValue> propertiesToMatch = matchProperties.getInstanceProperties();
                    if (propertiesToMatch == null) {
                        propertiesToMatch = new HashMap<>();
                    }

                    if (!mappedOmrsProperties.containsAll(propertiesToMatch.keySet()) && matchCriteria.equals(MatchCriteria.ALL)) {
                        // If there is some property we are being asked to search but it has no mapping,
                        // ensure we will get no results
                        getNothing = true;
                    } else {

                        if (matchCriteria.equals(MatchCriteria.ANY) || matchCriteria.equals(MatchCriteria.NONE)) {
                            for (Map.Entry<String, InstancePropertyValue> entry : propertiesToMatch.entrySet()) {
                                String omrsPropertyName = entry.getKey();
                                if (mapping.isOmrsPropertyLiteralMapped(omrsPropertyName)) {
                                    Object literalValue = mapping.getOmrsPropertyLiteralValue(omrsPropertyName);
                                    boolean valuesAreEqual = IGCRepositoryHelper.equivalentValues(literalValue, entry.getValue());
                                    getNothing = valuesAreEqual && matchCriteria.equals(MatchCriteria.NONE);
                                    if (getNothing) {
                                        break;
                                    }
                                }
                            }
                        }

                    }

                }

                if (!getNothing) {
                    igcRepositoryHelper.processResults(mapping,
                            igcRestClient.search(igcSearch),
                            relationships,
                            pageSize,
                            userId);
                }

            }

        }

        return relationships.isEmpty() ? null : relationships;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Relationship> findRelationshipsByPropertyValue(String userId,
                                                               String relationshipTypeGUID,
                                                               String searchCriteria,
                                                               int fromRelationshipElement,
                                                               List<InstanceStatus> limitResultsByStatus,
                                                               Date asOfTime,
                                                               String sequencingProperty,
                                                               SequencingOrder sequencingOrder,
                                                               int pageSize) throws
            InvalidParameterException,
            TypeErrorException,
            RepositoryErrorException,
            PropertyErrorException,
            PagingErrorException,
            FunctionNotSupportedException,
            UserNotAuthorizedException {

        final String methodName = "findRelationshipsByPropertyValue";

        this.findRelationshipsByPropertyValueParameterValidation(userId,
                relationshipTypeGUID,
                searchCriteria,
                fromRelationshipElement,
                limitResultsByStatus,
                asOfTime,
                sequencingProperty,
                sequencingOrder,
                pageSize);

        List<Relationship> relationships = new ArrayList<>();

        // Immediately throw unimplemented exception if trying to retrieve historical view
        if (asOfTime != null) {
            raiseFunctionNotSupportedException(IGCOMRSErrorCode.NO_HISTORY, methodName);
        } else if (limitResultsByStatus == null
                || (limitResultsByStatus.size() == 1 && limitResultsByStatus.contains(InstanceStatus.ACTIVE))) {

            // Otherwise, only bother searching if we are after ACTIVE (or "all") relationships -- non-ACTIVE means we
            // will just return an empty list
            // This method should give us only the leaf-level relationship mappings (those WITHOUT any subtypes)
            List<RelationshipMapping> mappingsToSearch = getRelationshipMappingsToSearch(relationshipTypeGUID, userId);

            // Now iterate through all of the mappings we need to search, construct and run an appropriate search
            // for each one
            for (RelationshipMapping mapping : mappingsToSearch) {

                // This will default to giving us the simple search criteria, if no complex criteria are defined
                // for the mapping.
                IGCSearch igcSearch = mapping.getComplexIGCSearchCriteria(
                        repositoryHelper,
                        repositoryName,
                        igcRestClient,
                        searchCriteria
                );

                // TODO: handle sequencing -- here or as part of method above?
                igcRepositoryHelper.setPagingForSearch(igcSearch, fromRelationshipElement, pageSize);

                igcRepositoryHelper.processResults(mapping,
                        igcRestClient.search(igcSearch),
                        relationships,
                        pageSize,
                        userId);

            }

        }

        return relationships.isEmpty() ? null : relationships;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void refreshEntityReferenceCopy(String userId,
                                           String entityGUID,
                                           String typeDefGUID,
                                           String typeDefName,
                                           String homeMetadataCollectionId) throws
            InvalidParameterException,
            RepositoryErrorException,
            HomeEntityException,
            UserNotAuthorizedException {

        final String methodName = "refreshEntityReferenceCopy";
        final String entityParameterName = "entityGUID";
        final String homeParameterName = "homeMetadataCollectionId";

        /*
         * Validate parameters
         */
        super.manageReferenceInstanceParameterValidation(userId,
                entityGUID,
                typeDefGUID,
                typeDefName,
                entityParameterName,
                homeMetadataCollectionId,
                homeParameterName,
                methodName);

        /*
         * Validate that the entity GUID is ok
         */
        EntityDetail entity = this.isEntityKnown(userId, entityGUID);
        if (entity != null && metadataCollectionId.equals(entity.getMetadataCollectionId())) {
            OMRSErrorCode errorCode = OMRSErrorCode.HOME_REFRESH;
            String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(methodName,
                    entityGUID,
                    metadataCollectionId,
                    repositoryName);
            throw new HomeEntityException(errorCode.getHTTPErrorCode(),
                    this.getClass().getName(),
                    methodName,
                    errorMessage,
                    errorCode.getSystemAction(),
                    errorCode.getUserAction());
        }

        /*
         * Send refresh message
         */
        checkEventMapperIsConfigured(methodName);
        eventMapper.sendRefreshEntityRequest(
                typeDefGUID,
                typeDefName,
                entityGUID,
                homeMetadataCollectionId
        );

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void refreshRelationshipReferenceCopy(String userId,
                                                 String relationshipGUID,
                                                 String typeDefGUID,
                                                 String typeDefName,
                                                 String homeMetadataCollectionId) throws
            InvalidParameterException,
            RepositoryErrorException,
            HomeRelationshipException,
            UserNotAuthorizedException {

        final String methodName = "refreshRelationshipReferenceCopy";
        final String relationshipParameterName = "relationshipGUID";
        final String homeParameterName = "homeMetadataCollectionId";

        /*
         * Validate parameters
         */
        super.manageReferenceInstanceParameterValidation(userId,
                relationshipGUID,
                typeDefGUID,
                typeDefName,
                relationshipParameterName,
                homeMetadataCollectionId,
                homeParameterName,
                methodName);

        Relationship relationship = this.isRelationshipKnown(userId, relationshipGUID);
        if (relationship != null && metadataCollectionId.equals(relationship.getMetadataCollectionId())) {
            OMRSErrorCode errorCode = OMRSErrorCode.HOME_REFRESH;
            String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(methodName,
                    relationshipGUID,
                    metadataCollectionId,
                    repositoryName);

            throw new HomeRelationshipException(errorCode.getHTTPErrorCode(),
                    this.getClass().getName(),
                    methodName,
                    errorMessage,
                    errorCode.getSystemAction(),
                    errorCode.getUserAction());
        }

        /*
         * Process refresh request
         */
        checkEventMapperIsConfigured(methodName);
        eventMapper.sendRefreshRelationshipRequest(
                typeDefGUID,
                typeDefName,
                relationshipGUID,
                homeMetadataCollectionId
        );

    }

    /**
     * Configure the event mapper that should be used to send any outbound events.
     *
     * @param eventMapper the event mapper to use
     */
    public void setEventMapper(IGCOMRSRepositoryEventMapper eventMapper) {
        this.eventMapper = eventMapper;
    }

    /**
     * Retrieves a mapping from attribute name to TypeDefAttribute for all OMRS attributes defined for the provided
     * OMRS TypeDef name.
     *
     * @param omrsTypeName the name of the OMRS TypeDef for which to retrieve the attributes
     * @return {@code Map<String, TypeDefAttribute>}
     */
    public Map<String, TypeDefAttribute> getTypeDefAttributesForType(String omrsTypeName) {
        return typeDefStore.getAllTypeDefAttributesForName(omrsTypeName);
    }

    /**
     * Ensure that the event mapper is configured and throw exception if it is not.
     *
     * @param methodName the method attempting to use the event mapper
     */
    private void checkEventMapperIsConfigured(String methodName) throws RepositoryErrorException {
        if (eventMapper == null) {
            IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.EVENT_MAPPER_NOT_INITIALIZED;
            String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(repositoryName);
            raiseRepositoryErrorException(errorCode, errorMessage, methodName);
        }
    }

    /**
     * Retrieve the listing of implemented mappings that should be used for an entity search, including navigating
     * subtypes when a supertype is the entity type provided.
     *
     * @param entityTypeGUID the GUID of the OMRS entity type for which to search
     * @param userId the userId through which to search
     * @return {@code List<EntityMapping>}
     */
    private List<EntityMapping> getMappingsToSearch(String entityTypeGUID, String userId) throws
            InvalidParameterException,
            RepositoryErrorException {

        List<EntityMapping> mappingsToSearch = new ArrayList<>();

        // If no entityType was provided, add all implemented types (except Referenceable, as that could itself
        // include many objects that are not implemented)
        if (entityTypeGUID == null) {
            for (EntityMapping candidate : igcRepositoryHelper.getAllEntityMappings()) {
                if (!candidate.getOmrsTypeDefName().equals("Referenceable")) {
                    mappingsToSearch.add(candidate);
                }
            }
        } else {

            EntityMapping mappingExact = igcRepositoryHelper.getEntityMappingByGUID(entityTypeGUID);
            String requestedTypeName;
            // If no implemented mapping could be found, at least retrieve the TypeDef for further introspection
            // (so that if it has any implemented subtypes, we can still search for those)
            if (mappingExact == null) {
                TypeDef unimplemented = typeDefStore.getUnimplementedTypeDefByGUID(entityTypeGUID);
                requestedTypeName = unimplemented.getName();
            } else {
                requestedTypeName = mappingExact.getOmrsTypeDefName();
            }

            // Walk the hierarchy of types to ensure we search across all subtypes of the requested TypeDef as well
            List<TypeDef> allEntityTypes = findTypeDefsByCategory(userId, TypeDefCategory.ENTITY_DEF);

            for (TypeDef typeDef : allEntityTypes) {
                EntityMapping implementedMapping = igcRepositoryHelper.getEntityMappingByGUID(typeDef.getGUID());
                if (implementedMapping != null) {
                    String typeDefName = typeDef.getName();
                    if (!typeDefName.equals("Referenceable") && repositoryHelper.isTypeOf(metadataCollectionId, typeDefName, requestedTypeName)) {
                        // Add any subtypes of the requested type into the search
                        mappingsToSearch.add(implementedMapping);
                    }
                }
            }

        }

        return mappingsToSearch;

    }

    /**
     * Retrieve the listing of implemented mappings that should be used for a relationship search.
     *
     * @param relationshipTypeGUID the GUID of the OMRS relationship type for which to search
     * @param userId the userId through which to search
     * @return {@code List<RelationshipMapping>}
     */
    private List<RelationshipMapping> getRelationshipMappingsToSearch(String relationshipTypeGUID, String userId) {

        List<RelationshipMapping> mappingsToSearch = new ArrayList<>();

        // If no entityType was provided, add all implemented types
        if (relationshipTypeGUID == null) {
            for (RelationshipMapping candidate : igcRepositoryHelper.getAllRelationshipMappings()) {
                if (!candidate.hasSubTypes()) {
                    // Only list the actual mappings, not the super-type mappings
                    mappingsToSearch.add(candidate);
                }
            }
        } else {
            RelationshipMapping mappingExact = igcRepositoryHelper.getRelationshipMappingByGUID(relationshipTypeGUID);
            if (mappingExact != null) {
                if (mappingExact.hasSubTypes()) {
                    mappingsToSearch.addAll(mappingExact.getSubTypes());
                } else {
                    mappingsToSearch.add(mappingExact);
                }
            }
        }

        return mappingsToSearch;

    }

    /**
     * Find the minimal set of mappings that we should use for searching based on the provided parameters.
     *
     * @param entityTypeGUID the GUID of the entity type to be searched (if any, null otherwise)
     * @param prefix the prefix of the entity type to be searched (if any, null otherwise)
     * @param userId of the user making the request
     * @return {@code List<EntityMapping>}
     */
    private List<EntityMapping> findMappingsForInputs(String entityTypeGUID,
                                                      String prefix,
                                                      String userId) {
        if (log.isDebugEnabled()) { log.debug("Looking for mappers for GUID {} and prefix: {}", entityTypeGUID, prefix); }
        List<EntityMapping> mappers = new ArrayList<>();
        if (entityTypeGUID != null) {
            // Prefer the specific mapping(s) requested as the first choice (if any were requested)
            try {
                mappers = getMappingsToSearch(entityTypeGUID, userId);
            } catch (InvalidParameterException | RepositoryErrorException e) {
                log.error("Unable to retrieve mappers for: {}", entityTypeGUID, e);
            }
        } else if (prefix != null) {
            // Next preference is to optimise to only those needed for any provided prefix
            mappers = new ArrayList<>(igcRepositoryHelper.getEntityMappingsByPrefix(prefix));
        } else {
            // And finally fall-back to a full set of mappings (same call as earlier but this will resolve to all
            // mappers that are implemented)
            try {
                mappers = getMappingsToSearch(entityTypeGUID, userId);
            } catch (InvalidParameterException | RepositoryErrorException e) {
                log.error("Unable to retrieve all mappers (no entityTypeGUID requested).", e);
            }
        }
        return mappers;
    }

    /**
     * Throw a TypeDefNotKnownException using the provided information.
     * @param errorCode the error code to use for the exception
     * @param typeDef the type def that is not known
     * @param methodName the name of the method throwing the exception
     * @throws TypeDefNotKnownException always
     */
    private void raiseTypeDefNotKnownException(IGCOMRSErrorCode errorCode, String typeDef, String methodName) throws TypeDefNotKnownException {
        String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                typeDef,
                repositoryName);
        throw new TypeDefNotKnownException(errorCode.getHTTPErrorCode(),
                this.getClass().getName(),
                methodName,
                errorMessage,
                errorCode.getSystemAction(),
                errorCode.getUserAction());
    }

    /**
     * Throw a TypeDefNotSupportedException using the provided information.
     * @param errorCode the error code to use for the exception
     * @param typeDef the type def that is not supported
     * @param methodName the name of the method throwing the exception
     * @throws TypeDefNotSupportedException always
     */
    private void raiseTypeDefNotSupportedException(IGCOMRSErrorCode errorCode, String typeDef, String methodName) throws TypeDefNotSupportedException {
        String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                typeDef,
                repositoryName);
        throw new TypeDefNotSupportedException(errorCode.getHTTPErrorCode(),
                this.getClass().getName(),
                methodName,
                errorMessage,
                errorCode.getSystemAction(),
                errorCode.getUserAction());
    }

    /**
     * Throw an EntityNotKnownException using the provided information.
     * @param errorCode the error code to use for the exception
     * @param guid the GUID of the entity that is not known
     * @param rid the IGC RID of the entity that is not known
     * @param methodName the name of the method throwing the exception
     * @throws EntityNotKnownException always
     */
    private void raiseEntityNotKnownException(IGCOMRSErrorCode errorCode, String guid, String rid, String methodName) throws EntityNotKnownException {
        String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                guid,
                rid,
                repositoryName);
        throw new EntityNotKnownException(errorCode.getHTTPErrorCode(),
                this.getClass().getName(),
                methodName,
                errorMessage,
                errorCode.getSystemAction(),
                errorCode.getUserAction());
    }

    /**
     * Throw a RepositoryErrorException using the provided information.
     * @param errorCode the error code to use for the exception
     * @param errorMessage the formatted error message
     * @param methodName the name of the method throwing the exception
     * @throws RepositoryErrorException always
     */
    private void raiseRepositoryErrorException(IGCOMRSErrorCode errorCode, String errorMessage, String methodName) throws RepositoryErrorException {
        throw new RepositoryErrorException(errorCode.getHTTPErrorCode(),
                this.getClass().getName(),
                methodName,
                errorMessage,
                errorCode.getSystemAction(),
                errorCode.getUserAction());
    }

    /**
     * Throw a FunctionNotSupportedException using the provided information.
     * @param errorCode the error code to use for the exception
     * @param methodName the name of the method throwing the exception
     * @throws FunctionNotSupportedException always
     */
    private void raiseFunctionNotSupportedException(IGCOMRSErrorCode errorCode, String methodName) throws FunctionNotSupportedException {
        String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(repositoryName);
        throw new FunctionNotSupportedException(errorCode.getHTTPErrorCode(),
                this.getClass().getName(),
                methodName,
                errorMessage,
                errorCode.getSystemAction(),
                errorCode.getUserAction());
    }

    /**
     * Throw a RelationshipNotKnownException using the provided information.
     * @param errorCode the error code to use for the exception
     * @param relationship information about the relationship that is not known
     * @param methodName the name of the method throwing the exception
     * @throws RelationshipNotKnownException always
     */
    private void raiseRelationshipNotKnownException(IGCOMRSErrorCode errorCode, String relationship, String methodName) throws RelationshipNotKnownException {
        String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                relationship,
                repositoryName);
        throw new RelationshipNotKnownException(errorCode.getHTTPErrorCode(),
                this.getClass().getName(),
                methodName,
                errorMessage,
                errorCode.getSystemAction(),
                errorCode.getUserAction());
    }

}
