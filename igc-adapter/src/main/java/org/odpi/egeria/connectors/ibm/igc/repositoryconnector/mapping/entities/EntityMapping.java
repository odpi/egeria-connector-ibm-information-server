/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestConstants;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchConditionSet;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchSorting;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSMetadataCollection;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSRepositoryConnector;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.InstanceMapping;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.EntityMappingInstance;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.attributes.AttributeMapping;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.classifications.ClassificationMapping;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.RelationshipMapping;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.SequencingOrder;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.*;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.typedefs.AttributeTypeDefCategory;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.typedefs.TypeDefAttribute;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.repositoryconnector.OMRSRepositoryHelper;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.FunctionNotSupportedException;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.RepositoryErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Provides the base class for all entity mappings.
 */
public abstract class EntityMapping extends InstanceMapping {

    private static final Logger log = LoggerFactory.getLogger(EntityMapping.class);

    private String igcAssetType;
    private String igcAssetTypeDisplayName;
    private String omrsTypeDefName;
    private String igcRidPrefix;

    private ArrayList<String> otherIgcTypes;

    public static final String COMPLEX_MAPPING_SENTINEL = "__COMPLEX_PROPERTY__";

    private Map<String, PropertyMapping> mappingByIgcProperty;
    private Map<String, PropertyMapping> mappingByOmrsProperty;

    private HashSet<String> complexIgcProperties;
    private HashSet<String> complexOmrsProperties;

    private ArrayList<RelationshipMapping> relationshipMappers;
    private ArrayList<ClassificationMapping> classificationMappers;

    private ArrayList<InstanceStatus> omrsSupportedStatuses;

    public EntityMapping(String igcAssetType,
                         String igcAssetTypeDisplayName,
                         String omrsTypeDefName,
                         String igcRidPrefix) {

        this.igcAssetType = igcAssetType;
        this.igcAssetTypeDisplayName = igcAssetTypeDisplayName;
        this.omrsTypeDefName = omrsTypeDefName;
        this.igcRidPrefix = igcRidPrefix;

        this.mappingByIgcProperty = new HashMap<>();
        this.mappingByOmrsProperty = new HashMap<>();
        this.complexIgcProperties = new HashSet<>();
        this.complexOmrsProperties = new HashSet<>();

        this.relationshipMappers = new ArrayList<>();
        this.classificationMappers = new ArrayList<>();

        this.omrsSupportedStatuses = new ArrayList<>();
        addSupportedStatus(InstanceStatus.ACTIVE);
        addSupportedStatus(InstanceStatus.DELETED);

        this.otherIgcTypes = new ArrayList<>();

    }

    /**
     * Subclass to contain individual mappings.
     */
    public static final class PropertyMapping {

        private String igcPropertyName;
        private String omrsPropertyName;

        public PropertyMapping(String igcPropertyName, String omrsPropertyName) {
            this.igcPropertyName = igcPropertyName;
            this.omrsPropertyName = omrsPropertyName;
        }

        public String getIgcPropertyName() { return this.igcPropertyName; }
        public String getOmrsPropertyName() { return this.omrsPropertyName; }

    }

    /**
     * Add the provided status as one supported by this entity mapping.
     *
     * @param status a status that is supported by the mapping
     */
    public final void addSupportedStatus(InstanceStatus status) { this.omrsSupportedStatuses.add(status); }

    /**
     * Retrieve the list of statuses that are supported by the entity mapping.
     *
     * @return {@code List<InstanceStatus>}
     */
    public final List<InstanceStatus> getSupportedStatuses() { return this.omrsSupportedStatuses; }

    /**
     * Retrieve the primary IGC asset type used by this mapping.
     *
     * @return String
     */
    public final String getIgcAssetType() { return this.igcAssetType; }

    /**
     * Retrieve the display name of the primary IGC asset type used by this mapping. (The display name is also the name
     * used by the InfosphereEvents topic of the event mapper.)
     *
     * @return String
     */
    public final String getIgcAssetTypeDisplayName() { return this.igcAssetTypeDisplayName; }

    /**
     * Retrieve the POJO used to translate the IGC REST API's JSON representation into a Java object.
     *
     * @param igcomrsRepositoryConnector connection to an IGC repository
     * @return Class
     */
    public final Class getIgcPOJO(IGCOMRSRepositoryConnector igcomrsRepositoryConnector) {
        return getIgcPOJO(igcomrsRepositoryConnector, igcAssetType);
    }

    /**
     * Retrieve the POJO for the provided IGC REST API's JSON representation into a Java object.
     *
     * @param igcomrsRepositoryConnector connection to an IGC repository
     * @param assetType the IGC REST API's JSON representation
     * @return Class
     */
    private final Class getIgcPOJO(IGCOMRSRepositoryConnector igcomrsRepositoryConnector, String assetType) {
        Class igcPOJO = null;
        if (assetType.equals(IGCOMRSMetadataCollection.DEFAULT_IGC_TYPE)) {
            StringBuilder sbPojoName = new StringBuilder();
            sbPojoName.append(IGCRestConstants.IGC_REST_COMMON_MODEL_PKG);
            sbPojoName.append(".MainObject");
            try {
                igcPOJO = Class.forName(sbPojoName.toString());
            } catch (ClassNotFoundException e) {
                if (log.isErrorEnabled()) { log.error("Unable to find POJO class: {}", sbPojoName.toString(), e); }
            }
        } else {
            igcPOJO = igcomrsRepositoryConnector.getIGCRestClient().findPOJOForType(assetType);
        }
        return igcPOJO;
    }

    /**
     * Indicates whether the IGC Repository ID (RID) requires a prefix (true) or not (false). A prefix is typically
     * required when the entity represented by the RID does not actually exist as a distinct entity in IGC, but is
     * rather a subset of properties, relationships and classifications from another IGC asset type. (The prefix allows
     * us to effectively split such a singular IGC object into multiple OMRS entities.)
     *
     * @return boolean
     * @see #getIgcRidPrefix()
     */
    public final boolean igcRidNeedsPrefix() { return (this.igcRidPrefix != null); }

    /**
     * Retrieves the IGC Repository ID (RID) prefix required by this entity, if any (or null if none is needed).
     *
     * @return String
     * @see #igcRidNeedsPrefix()
     */
    public final String getIgcRidPrefix() { return this.igcRidPrefix; }

    /**
     * Retrieves the name of the OMRS TypeDef that this mapping translates IGC objects into.
     *
     * @return String
     */
    public final String getOmrsTypeDefName() { return this.omrsTypeDefName; }

    /**
     * Indicates whether this entity mapping matches the provided IGC asset type: that is, this mapping
     * can be used to translate to the provided IGC asset type.
     *
     * @param igcAssetType the IGC asset type to check the mapping against
     * @return boolean
     */
    public final boolean matchesAssetType(String igcAssetType) {
        String matchType = Reference.getAssetTypeForSearch(igcAssetType);
        if (log.isDebugEnabled()) { log.debug("checking for matching asset between {} and {}", this.igcAssetType, matchType); }
        return (
                this.igcAssetType.equals(matchType)
                        || this.igcAssetType.equals(IGCOMRSMetadataCollection.DEFAULT_IGC_TYPE)
        );
    }

    /**
     * Add any other IGC asset type needed for this mapping.
     *
     * @param igcAssetTypeName name of additional IGC asset
     */
    public final void addOtherIGCAssetType(String igcAssetTypeName) {
        this.otherIgcTypes.add(igcAssetTypeName);
    }

    /**
     * Retrieve listing of any additional IGC asset types needed by this mapping.
     *
     * @return {@code List<String>}
     */
    public final List<String> getOtherIGCAssetTypes() { return this.otherIgcTypes; }

    /**
     * Retrieve listing of any additional IGC POJOs needed by this mapping.
     *
     * @param igcomrsRepositoryConnector connection to an IGC repository
     * @return {@code List<Class>}
     */
    public final List<Class> getOtherIGCPOJOs(IGCOMRSRepositoryConnector igcomrsRepositoryConnector) {
        ArrayList<Class> others = new ArrayList<>();
        for (String assetType : otherIgcTypes) {
            Class other = getIgcPOJO(igcomrsRepositoryConnector, assetType);
            if (other != null) {
                others.add(other);
            }
        }
        return others;
    }

    /**
     * Add a simple one-to-one property mapping between an IGC property and an OMRS property.
     *
     * @param igcPropertyName the IGC property name to be mapped
     * @param omrsPropertyName the OMRS property name to be mapped
     */
    public final void addSimplePropertyMapping(String igcPropertyName, String omrsPropertyName) {
        PropertyMapping pm = new PropertyMapping(igcPropertyName, omrsPropertyName);
        mappingByOmrsProperty.put(omrsPropertyName, pm);
        mappingByIgcProperty.put(igcPropertyName, pm);
    }

    /**
     * Note the provided IGC property name as requiring more than a simple one-to-one mapping to an OMRS property.
     *
     * @param igcPropertyName the IGC property name
     */
    public final void addComplexIgcProperty(String igcPropertyName) {
        complexIgcProperties.add(igcPropertyName);
    }

    /**
     * Note the provided OMRS property name as requiring more than a simple one-to-one mapping to an IGC property.
     *
     * @param omrsPropertyName the OMRS property name
     */
    public final void addComplexOmrsProperty(String omrsPropertyName) {
        complexOmrsProperties.add(omrsPropertyName);
    }

    /**
     * Returns only the subset of mapped IGC properties that are simple one-to-one mappings to OMRS properties.
     *
     * @return {@code Set<String>}
     */
    public final Set<String> getSimpleMappedIgcProperties() {
        return mappingByIgcProperty.keySet();
    }

    /**
     * Returns only the subset of mapped IGC properties that are complex mappings to OMRS properties.
     *
     * @return {@code Set<String>}
     */
    public final Set<String> getComplexMappedIgcProperties() {
        return complexIgcProperties;
    }

    /**
     * Returns the set of all IGC properties that are mapped to OMRS properties.
     *
     * @return {@code Set<String>}
     */
    public final Set<String> getAllMappedIgcProperties() {
        HashSet<String> igcProperties = new HashSet<>(getSimpleMappedIgcProperties());
        igcProperties.addAll(complexIgcProperties);
        return igcProperties;
    }

    /**
     * Returns only the subset of mapped OMRS properties that are simple one-to-one mappings to IGC properties.
     *
     * @return {@code Set<String>}
     */
    public final Set<String> getSimpleMappedOmrsProperties() {
        return mappingByOmrsProperty.keySet();
    }

    /**
     * Returns only the subset of mapped OMRS properties that are complex mappings to IGC properties.
     *
     * @return {@code Set<String>}
     */
    public final Set<String> getComplexMappedOmrsProperties() {
        return complexOmrsProperties;
    }

    /**
     * Returns the set of OMRS property names that are mapped: either to IGC properties, or as fixed (literal) values.
     *
     * @return {@code Set<String>}
     */
    public final Set<String> getAllMappedOmrsProperties() {
        HashSet<String> omrsProperties = new HashSet<>(getSimpleMappedOmrsProperties());
        omrsProperties.addAll(getComplexMappedOmrsProperties());
        omrsProperties.addAll(getLiteralPropertyMappings());
        return omrsProperties;
    }

    /**
     * Retrieves the IGC property name mapped to the provided OMRS property name.
     *
     * @param omrsPropertyName the OMRS property name for which to get the mapped IGC property name
     * @return String
     */
    public final String getIgcPropertyName(String omrsPropertyName) {
        String igcPropertyName = null;
        if (isOmrsPropertySimpleMapped(omrsPropertyName)) {
            igcPropertyName = mappingByOmrsProperty.get(omrsPropertyName).getIgcPropertyName();
        } else if (isOmrsPropertyComplexMapped(omrsPropertyName)) {
            igcPropertyName = COMPLEX_MAPPING_SENTINEL;
        }
        return igcPropertyName;
    }

    /**
     * Retrieves the OMRS property name mapped to the provided IGC property name.
     *
     * @param igcPropertyName the IGC property name for which to get the mapped OMRS property name.
     * @return String
     */
    public final String getOmrsPropertyName(String igcPropertyName) {
        String omrsPropertyName = null;
        if (isIgcPropertySimpleMapped(igcPropertyName)) {
            omrsPropertyName = mappingByIgcProperty.get(igcPropertyName).getOmrsPropertyName();
        } else if (isIgcPropertyComplexMapped(igcPropertyName)) {
            omrsPropertyName = COMPLEX_MAPPING_SENTINEL;
        }
        return omrsPropertyName;
    }

    /**
     * Determines whether the provided IGC property name is simple (one-to-one) mapped (true) or not (false).
     *
     * @param igcPropertyName the IGC property name to check for a simple mapping
     * @return boolean
     */
    public final boolean isIgcPropertySimpleMapped(String igcPropertyName) {
        return mappingByIgcProperty.containsKey(igcPropertyName);
    }

    /**
     * Determines whether the provided IGC property name is complex mapped (true) or not (false).
     *
     * @param igcPropertyName the IGC property name to check for a complex mapping
     * @return boolean
     */
    public final boolean isIgcPropertyComplexMapped(String igcPropertyName) {
        return complexIgcProperties.contains(igcPropertyName);
    }

    /**
     * Determines whether the provided OMRS property name is simple (one-to-one) mapped (true) or not (false).
     *
     * @param omrsPropertyName the OMRS property name to check for a simple mapping
     * @return boolean
     */
    public final boolean isOmrsPropertySimpleMapped(String omrsPropertyName) {
        return mappingByOmrsProperty.containsKey(omrsPropertyName);
    }

    /**
     * Determines whether the provided OMRS property name is complex mapped (true) or not (false).
     *
     * @param omrsPropertyName the OMRS property name to check for a complex mapping
     * @return boolean
     */
    public final boolean isOmrsPropertyComplexMapped(String omrsPropertyName) {
        return complexOmrsProperties.contains(omrsPropertyName);
    }

    /**
     * Add the provided relationship mapping as one that describes relationships for this entity.
     *
     * @param relationshipMapping
     */
    public final void addRelationshipMapper(RelationshipMapping relationshipMapping) {
        relationshipMappers.add(relationshipMapping);
    }

    /**
     * Retrieve the relationship mappings for this entity.
     *
     * @return {@code List<RelationshipMapping>}
     */
    public final List<RelationshipMapping> getRelationshipMappers() { return this.relationshipMappers; }

    /**
     * Add the provided classification mapping as one that describes classifications for this entity.
     *
     * @param classificationMapping
     */
    public final void addClassificationMapper(ClassificationMapping classificationMapping) {
        classificationMappers.add(classificationMapping);
    }

    /**
     * Retrieve the classification mappings for this entity.
     *
     * @return {@code List<ClassificationMapping>}
     */
    public final List<ClassificationMapping> getClassificationMappers() { return this.classificationMappers; }

    /**
     * Retrieve the base IGC asset expected for the mapper from one of its alternative assets. By default, and in the
     * vast majority of cases, there are no alternatives so will simply return the asset as-is. Override this method
     * in any mappers where alternative assets are defined.
     *
     * @param otherAsset the alternative asset to translate into a base asset
     * @param igcomrsRepositoryConnector connectivity to IGC repository
     * @return Reference - the base asset
     */
    public Reference getBaseIgcAssetFromAlternative(Reference otherAsset,
                                                    IGCOMRSRepositoryConnector igcomrsRepositoryConnector) {
        return otherAsset;
    }

    /**
     * This method needs to be overridden to define complex property mapping logic (if any).
     *
     * @param entityMap the instantiation of a mapping to carry out
     * @param instanceProperties the instance properties to which to add the complex-mapped properties
     * @return InstanceProperties
     */
    protected InstanceProperties complexPropertyMappings(EntityMappingInstance entityMap,
                                                         InstanceProperties instanceProperties) {
        // Nothing to do -- no complex properties by default
        // (only modification details, but because we want those on EntitySummary as well they're handled elsewhere)
        return instanceProperties;
    }

    /**
     * This method needs to be overridden to define how to search for an entity using a property value that has been
     * mapped in a complex way.
     *
     * @param repositoryHelper helper for the OMRS repository
     * @param repositoryName name of the repository
     * @param igcRestClient connectivity to an IGC environment
     * @param igcSearchConditionSet the set of search criteria to which to add
     * @param igcPropertyName the IGC property name (or COMPLEX_MAPPING_SENTINEL) to search
     * @param omrsPropertyName the OMRS property name (or COMPLEX_MAPPING_SENTINEL) to search
     * @param igcProperties the list of IGC properties to which to add for inclusion in the IGC search
     * @param value the value for which to search
     * @throws FunctionNotSupportedException when a regular expression is used for the search that is not supported
     */
    public void addComplexPropertySearchCriteria(OMRSRepositoryHelper repositoryHelper,
                                                 String repositoryName,
                                                 IGCRestClient igcRestClient,
                                                 IGCSearchConditionSet igcSearchConditionSet,
                                                 String igcPropertyName,
                                                 String omrsPropertyName,
                                                 List<String> igcProperties,
                                                 InstancePropertyValue value) throws FunctionNotSupportedException {
        // Nothing to do -- no complex properties by default
    }

    /**
     * Simple utility function to avoid implementing shared EntitySummary and EntityDetail setup twice.
     *
     * @param entityMap the instantiation of a mapping to carry out
     */
    private static final void setupEntityObj(EntityMappingInstance entityMap,
                                             EntitySummary omrsObj) {

        Reference igcEntity = entityMap.getIgcEntity();
        EntityMapping mapping = entityMap.getMapping();
        IGCOMRSRepositoryConnector igcomrsRepositoryConnector = entityMap.getRepositoryConnector();
        IGCRestClient igcRestClient = igcomrsRepositoryConnector.getIGCRestClient();
        String userId = entityMap.getUserId();

        if (igcRestClient.hasModificationDetails(igcEntity.getType())) {
            omrsObj.setCreatedBy((String)igcRestClient.getPropertyByName(igcEntity, IGCRestConstants.MOD_CREATED_BY));
            omrsObj.setCreateTime((Date)igcRestClient.getPropertyByName(igcEntity, IGCRestConstants.MOD_CREATED_ON));
            omrsObj.setUpdatedBy((String)igcRestClient.getPropertyByName(igcEntity, IGCRestConstants.MOD_MODIFIED_BY));
            omrsObj.setUpdateTime((Date)igcRestClient.getPropertyByName(igcEntity, IGCRestConstants.MOD_MODIFIED_ON));
            if (omrsObj.getUpdateTime() != null) {
                omrsObj.setVersion(omrsObj.getUpdateTime().getTime());
            }
        }

        List<Classification> omrsClassifications = entityMap.getOmrsClassifications();

        // Avoid doing this multiple times: if one has retrieved classifications it'll
        // be the same classifications for the other
        List<ClassificationMapping> classificationMappings = mapping.getClassificationMappers();
        if (!classificationMappings.isEmpty() && omrsClassifications.isEmpty()) {
            for (ClassificationMapping classificationMapping : classificationMappings) {
                classificationMapping.addMappedOMRSClassifications(
                        igcomrsRepositoryConnector,
                        omrsClassifications,
                        igcEntity,
                        userId
                );
            }
        }

        omrsObj.setClassifications(omrsClassifications);

    }

    /**
     * Map the IGC entity to an OMRS EntitySummary object.
     *
     * @param entityMap the instantiation of a mapping to carry out
     * @return EntitySummary
     */
    public static final EntitySummary getEntitySummary(EntityMappingInstance entityMap) {

        EntityMapping mapping = entityMap.getMapping();

        // Merge together all the properties we want to map
        ArrayList<String> allProperties = new ArrayList<>();
        for (ClassificationMapping classificationMapping : mapping.getClassificationMappers()) {
            allProperties.addAll(classificationMapping.getMappedIgcPropertyNames());
        }

        allProperties.addAll(mapping.getAllMappedIgcProperties());

        // Retrieve the full details we'll require for summary BEFORE handing off to superclass,
        // but only if the asset we've been initialised with was not already fully-retrieved
        entityMap.updateIgcEntityWithProperties(allProperties);

        // Handle any super-generic mappings first
        entityMap.initializeEntitySummary();

        // Then handle generic mappings and classifications
        setupEntityObj(entityMap, entityMap.getOmrsSummary());
        return entityMap.getOmrsSummary();

    }

    /**
     * Map the IGC entity to an OMRS EntityDetail object.
     *
     * @param entityMap the instantiation of a mapping to carry out
     * @return EntityDetail
     */
    public static final EntityDetail getEntityDetail(EntityMappingInstance entityMap) {

        Reference igcEntity = entityMap.getIgcEntity();
        EntityMapping mapping = entityMap.getMapping();
        IGCRestClient igcRestClient = entityMap.getRepositoryConnector().getIGCRestClient();

        // Retrieve the set of non-relationship properties for the asset
        List<String> nonRelationshipProperties = igcRestClient.getNonRelationshipPropertiesFromPOJO(igcEntity.getType());

        // Merge the detailed properties together (generic and more specific POJO mappings that were passed in)
        ArrayList<String> allProperties = new ArrayList<>();
        allProperties.addAll(mapping.getAllMappedIgcProperties());
        for (ClassificationMapping classificationMapping : mapping.getClassificationMappers()) {
            allProperties.addAll(classificationMapping.getMappedIgcPropertyNames());
        }
        allProperties.addAll(nonRelationshipProperties);

        // Retrieve only this set of properties for the object (no more, no less)
        // but only if the asset we've been initialised with was not already fully-retrieved
        entityMap.updateIgcEntityWithProperties(allProperties);

        // Handle any super-generic mappings first
        entityMap.initializeEntityDetail();

        // Then handle any generic mappings and classifications
        setupEntityObj(entityMap, entityMap.getOmrsDetail());

        // Use reflection to apply POJO-specific mappings
        InstanceProperties instanceProperties = getMappedInstanceProperties(entityMap);
        entityMap.updateOmrsDetailWithProperties(instanceProperties);

        return entityMap.getOmrsDetail();

    }

    /**
     * Retrieves the InstanceProperties based on the mappings provided.
     *
     * @param entityMap the instantiation of a mapping to carry out
     * @return InstanceProperties
     */
    private static final InstanceProperties getMappedInstanceProperties(EntityMappingInstance entityMap) {

        final String methodName = "getMappedInstanceProperties";

        IGCOMRSRepositoryConnector igcomrsRepositoryConnector = entityMap.getRepositoryConnector();
        IGCOMRSMetadataCollection igcomrsMetadataCollection = entityMap.getMetadataCollection();
        OMRSRepositoryHelper omrsRepositoryHelper = igcomrsRepositoryConnector.getRepositoryHelper();
        IGCRestClient igcRestClient = igcomrsRepositoryConnector.getIGCRestClient();
        Reference igcEntity = entityMap.getIgcEntity();
        String repositoryName = igcomrsRepositoryConnector.getRepositoryName();
        EntityMapping mapping = entityMap.getMapping();
        String omrsTypeDefName = mapping.getOmrsTypeDefName();

        Map<String, TypeDefAttribute> omrsAttributeMap = igcomrsMetadataCollection.getTypeDefAttributesForType(omrsTypeDefName);

        InstanceProperties instanceProperties = new InstanceProperties();

        // Then we'll iterate through the provided mappings to set an OMRS instance property for each one
        for (String igcPropertyName : mapping.getSimpleMappedIgcProperties()) {
            String omrsAttribute = mapping.getOmrsPropertyName(igcPropertyName);
            if (omrsAttributeMap.containsKey(omrsAttribute)) {
                TypeDefAttribute typeDefAttribute = omrsAttributeMap.get(omrsAttribute);
                instanceProperties = AttributeMapping.addPrimitivePropertyToInstance(
                        omrsRepositoryHelper,
                        repositoryName,
                        instanceProperties,
                        typeDefAttribute,
                        igcRestClient.getPropertyByName(igcEntity, igcPropertyName),
                        methodName
                );
            } else {
                if (log.isWarnEnabled()) { log.warn("No OMRS attribute {} defined for asset type {} -- skipping mapping.", omrsAttribute, omrsTypeDefName); }
            }
        }

        // Then any fixed (literal) property mappings
        for (String omrsPropertyName : mapping.getLiteralPropertyMappings()) {
            if (omrsAttributeMap.containsKey(omrsPropertyName)) {
                Object value = mapping.getOmrsPropertyLiteralValue(omrsPropertyName);
                if (value != null) {
                    TypeDefAttribute typeDefAttribute = omrsAttributeMap.get(omrsPropertyName);
                    AttributeTypeDefCategory attributeTypeDefCategory = typeDefAttribute.getAttributeType().getCategory();
                    if (attributeTypeDefCategory == AttributeTypeDefCategory.PRIMITIVE) {
                        instanceProperties = AttributeMapping.addPrimitivePropertyToInstance(
                                omrsRepositoryHelper,
                                repositoryName,
                                instanceProperties,
                                typeDefAttribute,
                                value,
                                methodName
                        );
                    } else {
                        instanceProperties.setProperty(omrsPropertyName, (InstancePropertyValue)value);
                    }
                }
            }
        }

        // Finally we'll apply any complex property mappings
        mapping.complexPropertyMappings(entityMap, instanceProperties);

        return instanceProperties;

    }

    /**
     * Retrieves the mapped relationships for the entity.
     *
     * @param entityMap the instantiation of a mapping to carry out
     * @param relationshipTypeGUID String GUID of the the type of relationship required (null for all).
     * @param fromRelationshipElement the starting element number of the relationships to return.
     *                                This is used when retrieving elements
     *                                beyond the first page of results. Zero means start from the first element.
     * @param sequencingOrder Enum defining how the results should be ordered.
     * @param pageSize the maximum number of result classifications that can be returned on this request.  Zero means
     *                 unrestricted return results size.
     * @return {@code List<Relationship>}
     */
    public static final List<Relationship> getMappedRelationships(EntityMappingInstance entityMap,
                                                                  String relationshipTypeGUID,
                                                                  int fromRelationshipElement,
                                                                  SequencingOrder sequencingOrder,
                                                                  int pageSize) {

        // TODO: handle multi-page results with different starting points (ie. fromRelationshipElement != 0)

        Reference igcEntity = entityMap.getIgcEntity();
        EntityMapping entityMapping = entityMap.getMapping();
        IGCOMRSRepositoryConnector igcomrsRepositoryConnector = entityMap.getRepositoryConnector();
        String userId = entityMap.getUserId();
        List<Relationship> omrsRelationships = entityMap.getOmrsRelationships();

        List<RelationshipMapping> relationshipMappers = entityMapping.getRelationshipMappers();

        // Retrieve the full details we'll require for the relationships
        // but only if the asset we've been initialised with was not already fully-retrieved
        if (!igcEntity.isFullyRetrieved()) {
            // Merge together all the properties we want to map
            ArrayList<String> allProperties = new ArrayList<>();
            for (RelationshipMapping mapping : relationshipMappers) {
                if (log.isDebugEnabled()) { log.debug("Adding properties from mapping: {}", mapping); }
                allProperties.addAll(mapping.getIgcRelationshipPropertiesForType(igcEntity.getType()));
            }
            allProperties.addAll(IGCRestConstants.getModificationProperties());
            IGCSearchSorting sort = IGCOMRSMetadataCollection.sortFromNonPropertySequencingOrder(sequencingOrder);
            igcEntity = igcEntity.getAssetWithSubsetOfProperties(
                    igcomrsRepositoryConnector.getIGCRestClient(),
                    allProperties.toArray(new String[0]),
                    pageSize,
                    sort
            );
        }

        RelationshipMapping.getMappedRelationships(
                igcomrsRepositoryConnector,
                omrsRelationships,
                relationshipMappers,
                relationshipTypeGUID,
                igcEntity,
                userId
        );

        return omrsRelationships;

    }

}
