/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ReferenceList;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearch;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchCondition;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchConditionSet;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchSorting;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.EntityMappingInstance;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.classifications.ClassificationMapping;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities.EntityMapping;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.RelationshipMapping;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.model.OMRSStub;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.stores.*;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.MatchCriteria;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.SequencingOrder;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.*;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.typedefs.PrimitiveDefCategory;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.typedefs.TypeDef;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.repositoryconnector.OMRSRepositoryHelper;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.StringWriter;
import java.util.*;

public class IGCRepositoryHelper {

    private static final Logger log = LoggerFactory.getLogger(IGCRepositoryHelper.class);

    public static final String MAPPING_PKG = "org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.";
    public static final String DEFAULT_IGC_TYPE = "main_object";
    public static final String DEFAULT_IGC_TYPE_DISPLAY_NAME = "Main Object";

    private static final String GENERATED_TYPE_PREFIX = "__|";
    private static final String GENERATED_TYPE_POSTFIX = "|__";

    private IGCOMRSRepositoryConnector igcomrsRepositoryConnector;
    private OMRSRepositoryHelper repositoryHelper;
    private IGCRestClient igcRestClient;

    private EntityMappingStore entityMappingStore;
    private RelationshipMappingStore relationshipMappingStore;
    private ClassificationMappingStore classificationMappingStore;

    private String repositoryName;
    private String metadataCollectionId;

    private XMLOutputFactory xmlOutputFactory;

    IGCRepositoryHelper(IGCOMRSRepositoryConnector igcomrsRepositoryConnector,
                        OMRSRepositoryHelper repositoryHelper,
                        IGCRestClient igcRestClient) {
        this.igcomrsRepositoryConnector = igcomrsRepositoryConnector;
        this.repositoryHelper = repositoryHelper;
        this.igcRestClient = igcRestClient;
        this.xmlOutputFactory = XMLOutputFactory.newInstance();
        this.repositoryName = igcomrsRepositoryConnector.getRepositoryName();
        this.metadataCollectionId = igcomrsRepositoryConnector.getMetadataCollectionId();
        this.entityMappingStore = new EntityMappingStore(igcomrsRepositoryConnector);
        this.relationshipMappingStore = new RelationshipMappingStore(igcomrsRepositoryConnector);
        this.classificationMappingStore = new ClassificationMappingStore(igcomrsRepositoryConnector);
    }

    /**
     * Adds an entity mapping for the provided TypeDef, using the provided EntityMapping Java class.
     *
     * @param omrsTypeDef the OMRS TypeDef for which the entity mapping is implemented
     * @param mappingClass the Java class providing the EntityMapping
     * @return boolean false if unable to configure an EntityMapping from the provided class
     */
    boolean addEntityMapping(TypeDef omrsTypeDef, Class mappingClass) {
        return entityMappingStore.addMapping(omrsTypeDef, mappingClass, igcomrsRepositoryConnector);
    }

    /**
     * Adds a classification mapping for the provided TypeDef, using the provided Java class for the mapping.
     *
     * @param omrsTypeDef the OMRS TypeDef
     * @param mappingClass the ClassificationMapping Java class
     * @return boolean false when unable to retrieve ClassificationMapping from provided class
     */
    boolean addClassificationMapping(TypeDef omrsTypeDef, Class mappingClass) {
        return classificationMappingStore.addMapping(omrsTypeDef, mappingClass);
    }

    /**
     * Adds a relationship mapping for the provided TypeDef, using the provided Java class for the mapping.
     *
     * @param omrsTypeDef the OMRS TypeDef
     * @param mappingClass the RelationshipMapping Java class
     * @return boolean false when unable to retrieve RelationshipMapping from provided class
     */
    boolean addRelationshipMapping(TypeDef omrsTypeDef, Class mappingClass) {
        return relationshipMappingStore.addMapping(omrsTypeDef, mappingClass);
    }

    /**
     * Retrieve a listing of all of the entity mappings between OMRS and IGC.
     *
     * @return {@code List<EntityMapping>}
     */
    List<EntityMapping> getAllEntityMappings() {
        return entityMappingStore.getAllMappings();
    }

    /**
     * Retrieve a listing of the OMRS entity type definitions that are mapped for IGC.
     *
     * @return {@code List<TypeDef>}
     */
    List<TypeDef> getMappedEntityTypes() {
        return entityMappingStore.getTypeDefs();
    }

    /**
     * Retrieve a listing of the OMRS classification type definitions that are mapped for IGC.
     *
     * @return {@code List<TypeDef>}
     */
    List<TypeDef> getMappedClassificationTypes() {
        return classificationMappingStore.getTypeDefs();
    }

    /**
     * Retrieve a listing of the OMRS relationship type definitions that are mapped for IGC.
     *
     * @return {@code List<TypeDef>}
     */
    List<TypeDef> getMappedRelationshipTypes() {
        return relationshipMappingStore.getTypeDefs();
    }

    /**
     * Retrieves the entity mapping by GUID of the OMRS TypeDef.
     *
     * @param guid of the OMRS TypeDef
     * @return EntityMapping
     */
    EntityMapping getEntityMappingByGUID(String guid) {
        return entityMappingStore.getMappingByOmrsTypeGUID(guid);
    }

    /**
     * Retrieves the entity mapping by the IGC asset type and prefix (if any).
     *
     * @param assetType the IGC asset type
     * @param prefix the prefix, or null if no prefix
     * @return EntityMapping
     */
    EntityMapping getEntityMappingByIgcType(String assetType, String prefix) { return entityMappingStore.getMappingByIgcAssetTypeAndPrefix(assetType, prefix); }

    /**
     * Retrieves the classification mapping by GUID of the OMRS TypeDef.
     *
     * @param guid of the OMRS TypeDef
     * @return ClassificationMapping
     */
    ClassificationMapping getClassificationMappingByGUID(String guid) {
        return classificationMappingStore.getMappingByOmrsTypeGUID(guid);
    }

    /**
     * Retrieves the relationship mapping by GUID of the OMRS TypeDef.
     *
     * @param guid of the OMRS TypeDef
     * @return RelationshipMapping
     */
    RelationshipMapping getRelationshipMappingByGUID(String guid) {
        return relationshipMappingStore.getMappingByOmrsTypeGUID(guid);
    }

    /**
     * Retrieve the classification mapping definition that fits the provided parameters.
     *
     * @param omrsClassificationType the name of the OMRS classification type definition
     * @param igcAssetType the name of the IGC asset type
     * @return ClassificationMapping
     */
    ClassificationMapping getClassificationMappingByTypes(String omrsClassificationType,
                                                          String igcAssetType) {
        return classificationMappingStore.getMappingByTypes(
                omrsClassificationType,
                igcAssetType);
    }

    /**
     * Retrieve the relationship mapping definition that fits the provided parameters.
     *
     * @param omrsRelationshipType the name of the OMRS relationship type definition
     * @param proxyOneType the name of the type for endpoint one of the relationship
     * @param proxyTwoType the name of the type for endpoint two of the relationship
     * @return RelationshipMapping
     */
    RelationshipMapping getRelationshipMappingByTypes(String omrsRelationshipType,
                                                      String proxyOneType,
                                                      String proxyTwoType) {
        return relationshipMappingStore.getMappingByTypes(
                omrsRelationshipType,
                proxyOneType,
                proxyTwoType);
    }

    /**
     * Retrieve a mapping from IGC property name to the OMRS relationship type it represents.
     *
     * @param assetType the IGC asset type for which to find mappings
     * @param userId the userId making the request
     * @return {@code Map<String, RelationshipMapping>} - keyed by IGC asset type with values of the RelationshipMappings
     */
    public Map<String, List<RelationshipMapping>> getIgcPropertiesToRelationshipMappings(String assetType, String userId) {

        HashMap<String, List<RelationshipMapping>> map = new HashMap<>();

        List<EntityMapping> mappers = getMappers(assetType, userId);
        for (EntityMapping mapper : mappers) {
            List<RelationshipMapping> relationshipMappings = mapper.getRelationshipMappers();
            for (RelationshipMapping relationshipMapping : relationshipMappings) {
                if (relationshipMapping.getProxyOneMapping().matchesAssetType(assetType)) {
                    List<String> relationshipNamesOne = relationshipMapping.getProxyOneMapping().getIgcRelationshipProperties();
                    for (String relationshipName : relationshipNamesOne) {
                        if (!map.containsKey(relationshipName)) {
                            map.put(relationshipName, new ArrayList<>());
                        }
                        if (!map.get(relationshipName).contains(relationshipMapping)) {
                            map.get(relationshipName).add(relationshipMapping);
                        }
                    }
                }
                if (relationshipMapping.getProxyTwoMapping().matchesAssetType(assetType)) {
                    List<String> relationshipNamesTwo = relationshipMapping.getProxyTwoMapping().getIgcRelationshipProperties();
                    for (String relationshipName : relationshipNamesTwo) {
                        if (!map.containsKey(relationshipName)) {
                            map.put(relationshipName, new ArrayList<>());
                        }
                        if (!map.get(relationshipName).contains(relationshipMapping)) {
                            map.get(relationshipName).add(relationshipMapping);
                        }
                    }
                }
            }
        }

        return map;

    }

    /**
     * Run a search against IGC and process the results, based on the provided parameters.
     *
     * @param mapping the mapping to use for running the search
     * @param entityDetails the list of results to append into
     * @param userId unique identifier for requesting user
     * @param matchProperties Optional list of entity properties to match (where any String property's value should
     *                        be defined as a Java regular expression, even if it should be an exact match).
     * @param matchCriteria Enum defining how the properties should be matched to the entities in the repository.
     * @param fromEntityElement the starting element number of the entities to return.
     *                                This is used when retrieving elements
     *                                beyond the first page of results. Zero means start from the first element.
     * @param limitResultsByClassification List of classifications that must be present on all returned entities.
     * @param sequencingProperty String name of the entity property that is to be used to sequence the results.
     *                           Null means do not sequence on a property name (see SequencingOrder).
     * @param sequencingOrder Enum defining how the results should be ordered.
     * @param pageSize the maximum number of result entities that can be returned on this request.  Zero means
     *                 unrestricted return results size.
     * @throws FunctionNotSupportedException when a regular expression is used for the search that is not supported
     * @throws RepositoryErrorException
     */
    void processResultsForMapping(EntityMapping mapping,
                                  List<EntityDetail> entityDetails,
                                  String userId,
                                  InstanceProperties matchProperties,
                                  MatchCriteria matchCriteria,
                                  int fromEntityElement,
                                  List<String> limitResultsByClassification,
                                  String sequencingProperty,
                                  SequencingOrder sequencingOrder,
                                  int pageSize)
            throws FunctionNotSupportedException, RepositoryErrorException {

        String igcAssetType = mapping.getIgcAssetType();
        IGCSearchConditionSet classificationLimiters = getSearchCriteriaForClassifications(
                igcAssetType,
                limitResultsByClassification
        );

        if (limitResultsByClassification != null && !limitResultsByClassification.isEmpty() && classificationLimiters == null) {
            if (log.isInfoEnabled()) { log.info("Classification limiters were specified, but none apply to the asset type {}, so excluding this asset type from search.", igcAssetType); }
        } else {

            IGCSearch igcSearch = new IGCSearch();
            igcSearch.addType(igcAssetType);

            /* Provided there is a mapping, build up a list of IGC-specific properties
             * and search criteria, based on the values of the InstanceProperties provided */
            ArrayList<String> properties = new ArrayList<>();
            IGCSearchConditionSet igcSearchConditionSet = new IGCSearchConditionSet();

            String qualifiedNameRegex = null;
            if (matchProperties != null) {
                Iterator iPropertyNames = matchProperties.getPropertyNames();
                Set<String> mappedProperties = mapping.getAllMappedIgcProperties();
                while (!mappedProperties.isEmpty() && iPropertyNames.hasNext()) {
                    String omrsPropertyName = (String) iPropertyNames.next();
                    InstancePropertyValue value = matchProperties.getPropertyValue(omrsPropertyName);
                    if (omrsPropertyName.equals("qualifiedName")) {
                        qualifiedNameRegex = (String) ((PrimitivePropertyValue) value).getPrimitiveValue();
                    }
                    addSearchConditionFromValue(
                            igcSearchConditionSet,
                            omrsPropertyName,
                            properties,
                            mapping,
                            value
                    );
                }
            }

            if (classificationLimiters != null) {
                igcSearchConditionSet.addNestedConditionSet(classificationLimiters);
            }

            IGCSearchSorting igcSearchSorting = null;
            if (sequencingProperty == null && sequencingOrder != null) {
                igcSearchSorting = IGCRepositoryHelper.sortFromNonPropertySequencingOrder(sequencingOrder);
            }

            if (matchCriteria != null) {
                switch (matchCriteria) {
                    case ALL:
                        igcSearchConditionSet.setMatchAnyCondition(false);
                        break;
                    case ANY:
                        igcSearchConditionSet.setMatchAnyCondition(true);
                        break;
                    case NONE:
                        igcSearchConditionSet.setMatchAnyCondition(false);
                        igcSearchConditionSet.setNegateAll(true);
                        break;
                }
            }

            igcSearch.addProperties(properties);
            igcSearch.addConditions(igcSearchConditionSet);

            setPagingForSearch(igcSearch, fromEntityElement, pageSize);

            if (igcSearchSorting != null) {
                igcSearch.addSortingCriteria(igcSearchSorting);
            }

            // If searching by qualifiedName, exact match (or starts with) we need to check results
            // to remove any (non-)generated type based on the qualifiedName (because the search results
            // will contain both from various iterations of this loop, and only one or the other should be
            // returned by the search)
            boolean includeResult = true;
            if (qualifiedNameRegex != null
                    && (repositoryHelper.isStartsWithRegex(qualifiedNameRegex) || repositoryHelper.isExactMatchRegex(qualifiedNameRegex))) {
                String unqualifiedName = repositoryHelper.getUnqualifiedLiteralString(qualifiedNameRegex);
                String prefix = mapping.getIgcRidPrefix();
                boolean generatedQN = isGeneratedRID(unqualifiedName);
                includeResult = (generatedQN && prefix != null) || (!generatedQN && prefix == null);
            }

            if (includeResult) {
                processResults(
                        mapping,
                        this.igcRestClient.search(igcSearch),
                        entityDetails,
                        pageSize,
                        userId
                );
            }
        }
    }

    /**
     * Add the type to search based on the provided mapping.
     *
     * @param mapping the mapping on which to base the search
     * @param igcSearch the IGC search object to which to add the criteria
     * @return String - the IGC asset type that will be used for the search
     */
    String addTypeToSearch(EntityMapping mapping, IGCSearch igcSearch) {
        String igcType = DEFAULT_IGC_TYPE;
        if (mapping == null) {
            // If no TypeDef was provided, run against all types
            igcSearch.addType(igcType);
        } else {
            igcType = mapping.getIgcAssetType();
            igcSearch.addType(igcType);
        }
        return igcType;
    }

    /**
     * Setup paging properties of the IGC search.
     *
     * @param igcSearch the IGC search object to which to add the criteria
     * @param beginAt the starting index for results
     * @param pageSize the number of results to include in each page
     */
    void setPagingForSearch(IGCSearch igcSearch, int beginAt, int pageSize) {
        if (pageSize > 0) {
            /* Only set pageSize if it has been provided; otherwise we'll end up defaulting to IGC's
             * minimal pageSize of 10 (so will need to make many calls to get all pages) */
            igcSearch.setPageSize(pageSize);
        } else {
            /* So if none has been specified, we'll set a large pageSize to be able to more efficiently
             * retrieve all pages of results */
            igcSearch.setPageSize(igcomrsRepositoryConnector.getMaxPageSize());
        }
        igcSearch.setBeginAt(beginAt);
    }

    /**
     * Process the search results into the provided list of EntityDetail objects.
     *
     * @param mapper the EntityMapping that should be used to translate the results
     * @param results the IGC search results
     * @param entityDetails the list of EntityDetails to append
     * @param pageSize the number of results per page (0 for all results)
     * @param userId the user making the request
     */
    void processResults(EntityMapping mapper,
                        ReferenceList results,
                        List<EntityDetail> entityDetails,
                        int pageSize,
                        String userId) throws RepositoryErrorException {

        if (pageSize == 0) {
            // If the provided pageSize was 0, we need to retrieve ALL pages of results...
            results.getAllPages(this.igcRestClient);
        }

        for (Reference reference : results.getItems()) {
            /* Only proceed with retrieving the EntityDetail if the type from IGC is not explicitly
             * a 'main_object' (as these are non-API-accessible asset types in IGC like column analysis master,
             * etc and will simply result in 400-code Bad Request messages from the API) */
            if (!reference.getType().equals(DEFAULT_IGC_TYPE)) {
                EntityDetail ed = null;

                if (log.isDebugEnabled()) { log.debug("processResults with mapper: {}", mapper.getClass().getCanonicalName()); }
                String idToLookup;
                if (mapper.igcRidNeedsPrefix()) {
                    if (log.isDebugEnabled()) { log.debug(" ... prefix required, getEntityDetail with: {}", mapper.getIgcRidPrefix() + reference.getId()); }
                    idToLookup = mapper.getIgcRidPrefix() + reference.getId();
                } else {
                    if (log.isDebugEnabled()) { log.debug(" ... no prefix required, getEntityDetail with: {}", reference.getId()); }
                    idToLookup = reference.getId();
                }
                try {
                    ed = getEntityDetail(userId, getGuidForRid(idToLookup), reference);
                } catch (EntityNotKnownException e) {
                    if (log.isErrorEnabled()) { log.error("Unable to find entity: {}", idToLookup); }
                }
                if (ed != null) {
                    entityDetails.add(ed);
                }
            }
        }

        // If we haven't filled a page of results (because we needed to skip some above), recurse...
        if (results.hasMorePages() && entityDetails.size() < pageSize) {
            results.getNextPage(this.igcRestClient);
            processResults(mapper, results, entityDetails, pageSize, userId);
        }

    }

    /**
     * Retrieve the IGC search conditions to limit results by the provided classification. Will return null if the
     * provided classification cannot be applied to the provided IGC asset type.
     *
     * @param igcAssetType name of the IGC asset type for which to limit the search results
     * @param classificationName name of the classification by which to limit results
     * @return IGCSearchConditionSet
     */
    private IGCSearchConditionSet getSearchCriteriaForClassification(String igcAssetType,
                                                                     String classificationName) {

        IGCSearchConditionSet igcSearchConditionSet = new IGCSearchConditionSet();

        ClassificationMapping classificationMapping = classificationMappingStore.getMappingByTypes(classificationName, igcAssetType);
        if (classificationMapping != null) {
            igcSearchConditionSet = classificationMapping.getIGCSearchCriteria(null);
        } else {
            if (log.isWarnEnabled()) { log.warn("Classification {} cannot be applied to IGC asset type {} - excluding from search limitations.", classificationName, igcAssetType); }
        }

        return (igcSearchConditionSet.size() > 0 ? igcSearchConditionSet : null);

    }

    /**
     * Retrieve the IGC search conditions to limit results by the provided list of classifications.
     *
     * @param igcAssetType name of the IGC asset type for which to limit the search results
     * @param classificationNames list of classification names by which to limit results
     * @return IGCSearchConditionSet
     */
    IGCSearchConditionSet getSearchCriteriaForClassifications(String igcAssetType,
                                                              List<String> classificationNames) {

        IGCSearchConditionSet igcSearchConditionSet = new IGCSearchConditionSet();

        if (classificationNames != null && !classificationNames.isEmpty()) {
            for (String classificationName : classificationNames) {
                IGCSearchConditionSet classificationLimiter = getSearchCriteriaForClassification(
                        igcAssetType,
                        classificationName
                );
                if (classificationLimiter != null) {
                    igcSearchConditionSet.addNestedConditionSet(classificationLimiter);
                    igcSearchConditionSet.setMatchAnyCondition(false);
                }
            }
        }

        return (igcSearchConditionSet.size() > 0 ? igcSearchConditionSet : null);

    }

    /**
     * Return the header, classifications and properties of a specific entity, using the provided IGC asset.
     *
     * @param userId unique identifier for requesting user.
     * @param guid String unique identifier for the entity.
     * @param asset the IGC asset for which an EntityDetail should be constructed.
     * @return EntityDetail structure.
     * @throws RepositoryErrorException there is a problem communicating with the metadata repository where
     *                                  the metadata collection is stored.
     */
    public EntityDetail getEntityDetail(String userId, String guid, Reference asset)
            throws RepositoryErrorException, EntityNotKnownException {

        final String methodName = "getEntityDetail";

        if (log.isDebugEnabled()) { log.debug("getEntityDetail with guid = {}", guid); }

        EntityDetail detail;
        String possiblyPrefixedRid = getRidFromGuid(guid);
        if (possiblyPrefixedRid == null) {
            IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.ENTITY_NOT_KNOWN;
            String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(guid,
                    "null",
                    repositoryName);
            throw new EntityNotKnownException(errorCode.getHTTPErrorCode(),
                    this.getClass().getName(),
                    methodName,
                    errorMessage,
                    errorCode.getSystemAction(),
                    errorCode.getUserAction());
        }
        String prefix = getPrefixFromGeneratedId(possiblyPrefixedRid);

        // If we could not find any asset by the provided guid, throw an ENTITY_NOT_KNOWN exception
        if (asset == null) {
            IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.ENTITY_NOT_KNOWN;
            String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                    guid,
                    getRidFromGuid(guid),
                    repositoryName);
            throw new EntityNotKnownException(errorCode.getHTTPErrorCode(),
                    this.getClass().getName(),
                    methodName,
                    errorMessage,
                    errorCode.getSystemAction(),
                    errorCode.getUserAction());
        } else if (asset.getType().equals(DEFAULT_IGC_TYPE)) {
            /* If the asset type returned has an IGC-listed type of 'main_object', it isn't one that the REST API
             * of IGC supports (eg. a data rule detail object, a column analysis master object, etc)...
             * Trying to further process it will result in failed REST API requests; so we should skip these objects */
            IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.UNSUPPORTED_OBJECT_TYPE;
            String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                    guid,
                    asset.getType(),
                    repositoryName);
            throw new RepositoryErrorException(errorCode.getHTTPErrorCode(),
                    this.getClass().getName(),
                    methodName,
                    errorMessage,
                    errorCode.getSystemAction(),
                    errorCode.getUserAction());
        } else {

            // Otherwise, retrieve the mapping dynamically based on the type of asset
            EntityMappingInstance entityMap = getMappingInstanceForParameters(asset, prefix, userId);

            if (entityMap != null) {
                // 2. Apply the mapping to the object, and retrieve the resulting EntityDetail
                detail = EntityMapping.getEntityDetail(entityMap);
            } else {
                IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.TYPEDEF_NOT_MAPPED;
                String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                        (prefix == null ? "" : prefix) + asset.getType(),
                        repositoryName);
                throw new RepositoryErrorException(errorCode.getHTTPErrorCode(),
                        this.getClass().getName(),
                        methodName,
                        errorMessage,
                        errorCode.getSystemAction(),
                        errorCode.getUserAction());
            }

        }

        return detail;

    }

    /**
     * Add the specified classification to the provided entity.
     *
     * @param userId the user requesting the addition of the classification
     * @param entityGUID the GUID of the entity to which to add the classification
     * @param classificationTypeDef the TypeDef of the classification to add
     * @param classificationProperties the properties to set on the classification that is to be added
     * @return Reference - the IGC asset that was classified
     * @throws RepositoryErrorException
     * @throws EntityNotKnownException
     * @throws ClassificationErrorException
     */
    public Reference classifyEntity(String userId,
                                    String entityGUID,
                                    TypeDef classificationTypeDef,
                                    InstanceProperties classificationProperties) throws
            RepositoryErrorException,
            EntityNotKnownException,
            ClassificationErrorException {

        final String methodName = "classifyEntity";

        String classificationName = classificationTypeDef.getName();

        String possiblyPrefixedRid = getRidFromGuid(entityGUID);
        if (possiblyPrefixedRid == null) {
            IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.ENTITY_NOT_KNOWN;
            String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                    entityGUID,
                    "null",
                    repositoryName);
            throw new EntityNotKnownException(errorCode.getHTTPErrorCode(),
                    this.getClass().getName(),
                    methodName,
                    errorMessage,
                    errorCode.getSystemAction(),
                    errorCode.getUserAction());
        }
        String rid = IGCRepositoryHelper.getRidFromGeneratedId(possiblyPrefixedRid);
        Reference igcEntity = this.igcRestClient.getAssetRefById(rid);

        if (igcEntity == null) {
            IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.ENTITY_NOT_KNOWN;
            String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                    entityGUID,
                    rid,
                    repositoryName
            );
            throw new EntityNotKnownException(errorCode.getHTTPErrorCode(),
                    this.getClass().getName(),
                    methodName,
                    errorMessage,
                    errorCode.getSystemAction(),
                    errorCode.getUserAction());
        }

        ClassificationMapping classificationMapping = getClassificationMappingByTypes(classificationName, igcEntity.getType());
        if (classificationMapping != null) {
            classificationMapping.addClassificationToIGCAsset(
                    igcomrsRepositoryConnector,
                    igcEntity,
                    entityGUID,
                    classificationProperties,
                    userId
            );
        } else {
            IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.TYPEDEF_NOT_MAPPED;
            String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                    classificationName,
                    repositoryName);
            throw new ClassificationErrorException(
                    errorCode.getHTTPErrorCode(),
                    this.getClass().getName(),
                    methodName,
                    errorMessage,
                    errorCode.getSystemAction(),
                    errorCode.getUserAction()
            );
        }

        return igcEntity;

    }

    /**
     * Remove the specified classification from the provided entity.
     *
     * @param userId the user requesting the classification removal
     * @param entityGUID the GUID of the entity from which classification should be removed
     * @param classificationTypeDef the TypeDef of the classification that should be removed
     * @return Reference - the IGC asset that was declassified
     * @throws RepositoryErrorException
     * @throws EntityNotKnownException
     * @throws ClassificationErrorException
     */
    public Reference declassifyEntity(String userId,
                                      String entityGUID,
                                      TypeDef classificationTypeDef) throws
            RepositoryErrorException,
            EntityNotKnownException,
            ClassificationErrorException {

        final String methodName = "declassifyEntity";

        String classificationName = classificationTypeDef.getName();

        String possiblyPrefixedRid = getRidFromGuid(entityGUID);
        if (possiblyPrefixedRid == null) {
            IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.ENTITY_NOT_KNOWN;
            String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                    entityGUID,
                    "null",
                    repositoryName);
            throw new EntityNotKnownException(errorCode.getHTTPErrorCode(),
                    this.getClass().getName(),
                    methodName,
                    errorMessage,
                    errorCode.getSystemAction(),
                    errorCode.getUserAction());
        }
        String rid = IGCRepositoryHelper.getRidFromGeneratedId(possiblyPrefixedRid);
        Reference igcEntity = this.igcRestClient.getAssetRefById(rid);

        if (igcEntity == null) {
            IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.ENTITY_NOT_KNOWN;
            String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                    entityGUID,
                    rid,
                    repositoryName
            );
            throw new EntityNotKnownException(errorCode.getHTTPErrorCode(),
                    this.getClass().getName(),
                    methodName,
                    errorMessage,
                    errorCode.getSystemAction(),
                    errorCode.getUserAction());
        }

        ClassificationMapping classificationMapping = getClassificationMappingByTypes(classificationName, igcEntity.getType());
        if (classificationMapping != null) {
            classificationMapping.removeClassificationFromIGCAsset(
                    igcomrsRepositoryConnector,
                    igcEntity,
                    entityGUID,
                    userId
            );
        } else {
            IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.TYPEDEF_NOT_MAPPED;
            String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                    classificationName,
                    repositoryName);
            throw new ClassificationErrorException(
                    errorCode.getHTTPErrorCode(),
                    this.getClass().getName(),
                    methodName,
                    errorMessage,
                    errorCode.getSystemAction(),
                    errorCode.getUserAction()
            );
        }

        return igcEntity;

    }

    /**
     * Retrieves an instance of a mapping that can be used for the provided parameters (or null if none exists).
     *
     * @param igcObject the IGC asset to be mapped
     * @param prefix the prefix used for the asset (if any; null otherwise)
     * @param userId the user making the request
     * @return EntityMappingInstance
     */
    public EntityMappingInstance getMappingInstanceForParameters(Reference igcObject, String prefix, String userId) {

        String igcAssetType = igcObject.getType();

        if (log.isDebugEnabled()) { log.debug("Looking for mapper for type {} with prefix {}", igcAssetType, prefix); }

        EntityMappingInstance entityMap = null;
        EntityMapping found = entityMappingStore.getMappingByIgcAssetTypeAndPrefix(igcAssetType, prefix);
        if (found != null) {
            if (log.isDebugEnabled()) { log.debug("Found mapper class: {} ({})", found.getClass().getCanonicalName(), found); }
            // Translate the provided asset to a base asset type for the mapper, if needed
            // (if not needed the 'getBaseIgcAssetFromAlternative' is effectively a NOOP and gives back same object)
            entityMap = new EntityMappingInstance(
                    found,
                    igcomrsRepositoryConnector,
                    found.getBaseIgcAssetFromAlternative(igcObject, igcomrsRepositoryConnector),
                    userId
            );
        } else {
            if (log.isDebugEnabled()) { log.debug("No mapper class found!"); }
        }

        return entityMap;

    }

    /**
     * Retrieves the classes to use for mapping the provided IGC asset type to an OMRS entity.
     *
     * @param igcAssetType the name of the IGC asset type
     * @param userId the user through which to retrieve the mappings (unused)
     * @return {@code List<EntityMapping>}
     */
    public List<EntityMapping> getMappers(String igcAssetType, String userId) {

        List<EntityMapping> mappers = entityMappingStore.getMappingsByIgcAssetType(igcAssetType);

        if (mappers == null) {
            mappers = new ArrayList<>();
        }
        if (mappers.isEmpty()) {
            EntityMapping defaultMapper = entityMappingStore.getDefaultEntityMapper();
            if (defaultMapper != null) {
                mappers.add(defaultMapper);
            }
        }

        return mappers;

    }

    /**
     * Retrieves the IGC asset type from the provided IGC asset display name (only for those assets that have
     * a mapping implemented). If none is found, will return null.
     *
     * @param igcAssetName the display name of the IGC asset type
     * @return String
     */
    public String getIgcAssetTypeForAssetName(String igcAssetName) {
        EntityMapping mapping = entityMappingStore.getMappingByIgcAssetDisplayName(igcAssetName);
        if (mapping != null) {
            return mapping.getIgcAssetType();
        } else {
            return null;
        }
    }

    /**
     * Adds the provided value to the search criteria for IGC (when we only know the OMRS property).
     *
     * @param igcSearchConditionSet the search conditions to which to add the criteria
     * @param omrsPropertyName the OMRS property name to search
     * @param igcProperties the list of IGC properties to which to add for inclusion in the IGC search
     * @param mapping the mapping definition for the entity for which we're searching
     * @param value the value for which to search
     * @throws FunctionNotSupportedException when a regular expression is used for the search that is not supported
     */
    private void addSearchConditionFromValue(IGCSearchConditionSet igcSearchConditionSet,
                                             String omrsPropertyName,
                                             List<String> igcProperties,
                                             EntityMapping mapping,
                                             InstancePropertyValue value) throws FunctionNotSupportedException {

        if (omrsPropertyName != null) {
            if (omrsPropertyName.equals(EntityMapping.COMPLEX_MAPPING_SENTINEL)) {

                log.warn("Unhandled search condition: complex OMRS mapping, unknown IGC property.");

            } else {

                String igcPropertyName = mapping.getIgcPropertyName(omrsPropertyName);

                if (igcPropertyName.equals(EntityMapping.COMPLEX_MAPPING_SENTINEL)) {

                    mapping.addComplexPropertySearchCriteria(
                            repositoryHelper,
                            repositoryName,
                            igcRestClient,
                            igcSearchConditionSet,
                            igcPropertyName,
                            omrsPropertyName,
                            igcProperties,
                            value);

                } else {

                    igcProperties.add(igcPropertyName);
                    addIGCSearchConditionFromValue(
                            repositoryHelper,
                            repositoryName,
                            igcSearchConditionSet,
                            igcPropertyName,
                            value);

                }

            }
        }

    }

    /**
     * Adds the provided value to search criteria for IGC (once we know the IGC property).
     *
     * @param repositoryHelper helper for the OMRS repository
     * @param igcSearchConditionSet the search conditions to which to add the criteria
     * @param igcPropertyName the IGC property name to search
     * @param value the value for which to search
     * @throws FunctionNotSupportedException when a regular expression is used for the search that is not supported
     */
    public static void addIGCSearchConditionFromValue(OMRSRepositoryHelper repositoryHelper,
                                                      String repositoryName,
                                                      IGCSearchConditionSet igcSearchConditionSet,
                                                      String igcPropertyName,
                                                      InstancePropertyValue value) throws FunctionNotSupportedException {

        final String methodName = "addIGCSearchConditionFromValue";

        InstancePropertyCategory category = value.getInstancePropertyCategory();
        switch (category) {
            case PRIMITIVE:
                PrimitivePropertyValue actualValue = (PrimitivePropertyValue) value;
                PrimitiveDefCategory primitiveType = actualValue.getPrimitiveDefCategory();
                switch (primitiveType) {
                    case OM_PRIMITIVE_TYPE_BOOLEAN:
                    case OM_PRIMITIVE_TYPE_BYTE:
                    case OM_PRIMITIVE_TYPE_CHAR:
                    case OM_PRIMITIVE_TYPE_SHORT:
                    case OM_PRIMITIVE_TYPE_INT:
                    case OM_PRIMITIVE_TYPE_LONG:
                    case OM_PRIMITIVE_TYPE_FLOAT:
                    case OM_PRIMITIVE_TYPE_DOUBLE:
                    case OM_PRIMITIVE_TYPE_BIGINTEGER:
                    case OM_PRIMITIVE_TYPE_BIGDECIMAL:
                        igcSearchConditionSet.addCondition(new IGCSearchCondition(
                                igcPropertyName,
                                "=",
                                actualValue.getPrimitiveValue().toString()
                        ));
                        break;
                    case OM_PRIMITIVE_TYPE_DATE:
                        Date date = (Date) actualValue.getPrimitiveValue();
                        igcSearchConditionSet.addCondition(new IGCSearchCondition(
                                igcPropertyName,
                                "=",
                                "" + date.getTime()
                        ));
                        break;
                    case OM_PRIMITIVE_TYPE_STRING:
                    default:
                        String candidateValue = actualValue.getPrimitiveValue().toString();
                        String unqualifiedValue = repositoryHelper.getUnqualifiedLiteralString(candidateValue);
                        if (repositoryHelper.isContainsRegex(candidateValue)) {
                            igcSearchConditionSet.addCondition(new IGCSearchCondition(
                                    igcPropertyName,
                                    "like %{0}%",
                                    unqualifiedValue
                            ));
                        } else if (repositoryHelper.isStartsWithRegex(candidateValue)) {
                            igcSearchConditionSet.addCondition(new IGCSearchCondition(
                                    igcPropertyName,
                                    "like {0}%",
                                    unqualifiedValue
                            ));
                        } else if (repositoryHelper.isEndsWithRegex(candidateValue)) {
                            igcSearchConditionSet.addCondition(new IGCSearchCondition(
                                    igcPropertyName,
                                    "like %{0}",
                                    unqualifiedValue
                            ));
                        } else if (repositoryHelper.isExactMatchRegex(candidateValue)) {
                            igcSearchConditionSet.addCondition(new IGCSearchCondition(
                                    igcPropertyName,
                                    "=",
                                    unqualifiedValue
                            ));
                        } else {
                            IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.REGEX_NOT_IMPLEMENTED;
                            String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                                    repositoryName,
                                    candidateValue);
                            throw new FunctionNotSupportedException(errorCode.getHTTPErrorCode(),
                                    IGCOMRSMetadataCollection.class.getName(),
                                    methodName,
                                    errorMessage,
                                    errorCode.getSystemAction(),
                                    errorCode.getUserAction());
                        }
                        break;
                }
                break;
            case ENUM:
                igcSearchConditionSet.addCondition(new IGCSearchCondition(
                        igcPropertyName,
                        "=",
                        ((EnumPropertyValue) value).getSymbolicName()
                ));
                break;
            /*case STRUCT:
                Map<String, InstancePropertyValue> structValues = ((StructPropertyValue) value).getAttributes().getInstanceProperties();
                for (Map.Entry<String, InstancePropertyValue> nextEntry : structValues.entrySet()) {
                    addSearchConditionFromValue(
                            igcSearchConditionSet,
                            nextEntry.getKey(),
                            igcProperties,
                            mapping,
                            nextEntry.getValue()
                    );
                }
                break;*/
            case MAP:
                Map<String, InstancePropertyValue> mapValues = ((MapPropertyValue) value).getMapValues().getInstanceProperties();
                for (Map.Entry<String, InstancePropertyValue> nextEntry : mapValues.entrySet()) {
                    addIGCSearchConditionFromValue(
                            repositoryHelper,
                            repositoryName,
                            igcSearchConditionSet,
                            nextEntry.getKey(),
                            nextEntry.getValue()
                    );
                }
                break;
            case ARRAY:
                Map<String, InstancePropertyValue> arrayValues = ((ArrayPropertyValue) value).getArrayValues().getInstanceProperties();
                for (Map.Entry<String, InstancePropertyValue> nextEntry : arrayValues.entrySet()) {
                    addIGCSearchConditionFromValue(
                            repositoryHelper,
                            repositoryName,
                            igcSearchConditionSet,
                            igcPropertyName,
                            nextEntry.getValue()
                    );
                }
                break;
            default:
                // Do nothing
                if (log.isWarnEnabled()) { log.warn("Unable to handle search criteria for value type: {}", category); }
                break;
        }

    }

    /**
     * Generate a GUID for the provided internal IGC repository ID (RID) of an instance.
     *
     * @param rid an internal IGC repository ID (RID)
     * @return String - a globally unique ID (GUID)
     */
    public String getGuidForRid(String rid) {
        // TODO: may need to be re-worked if we eventually handle reference copies in the connector
        return metadataCollectionId + "_" + rid;
    }

    /**
     * Retrieve the internal IGC repository ID (RID) of an instance from its GUID.
     *
     * @param guid a globally unique ID (GUID)
     * @return String - the internal IGC repository ID (RID)
     */
    public String getRidFromGuid(String guid) {
        // TODO: needs to be re-worked if we eventually handle reference copies in the connector
        if (!guid.startsWith(metadataCollectionId)) {
            // If the GUID is not from this metadata collection, it should not logically match any RID in the
            // environment (since we do not handle reference copies)
            return null;
        } else {
            // Otherwise, just strip off the metadata collection portion at the beginning to get the RID
            return guid.substring(metadataCollectionId.length() + 1);
        }
    }

    /**
     * Retrieves the RID from a generated RID (or the RID if it is not generated).
     *
     * @param rid the rid to translate
     * @return String
     */
    public static String getRidFromGeneratedId(String rid) {
        if (isGeneratedRID(rid)) {
            return rid
                    .substring(rid.indexOf(GENERATED_TYPE_POSTFIX) + GENERATED_TYPE_POSTFIX.length());
        } else {
            return rid;
        }
    }

    /**
     * Retrieves the generated prefix from a generated RID (or null if the RID is not generated).
     *
     * @param rid the rid from which to retrieve the prefix
     * @return String
     */
    public static String getPrefixFromGeneratedId(String rid) {
        if (isGeneratedRID(rid)) {
            return rid
                    .substring(0, rid.indexOf(GENERATED_TYPE_POSTFIX) + GENERATED_TYPE_POSTFIX.length());
        } else {
            return null;
        }
    }

    /**
     * Indicates whether the provided RID was generated (true) or not (false).
     *
     * @param rid the rid to test
     * @return boolean
     */
    public static boolean isGeneratedRID(String rid) {
        return rid.startsWith(GENERATED_TYPE_PREFIX);
    }

    /**
     * Generates a unique type prefix for RIDs based on the provided moniker.
     *
     * @param moniker a repeatable way by which to refer to the type
     * @return String
     */
    public static String generateTypePrefix(String moniker) {
        return GENERATED_TYPE_PREFIX + moniker + GENERATED_TYPE_POSTFIX;
    }

    /**
     * Retrieve an OMRS asset stub (shadow copy of last version of an asset) for the provided asset details.
     * If there is no existing stub, will return null.
     *
     * @param rid the Repository ID (RID) of the asset for which to retrieve the OMRS stub
     * @param type the IGC asset type of the asset for which to retrieve the OMRS stub
     * @return OMRSStub
     */
    public OMRSStub getOMRSStubForAsset(String rid, String type) {

        // We need to translate the provided asset into a unique name for the stub
        String stubName = getStubNameForAsset(rid, type);
        IGCSearchCondition condition = new IGCSearchCondition(
                "name",
                "=",
                stubName
        );
        String[] properties = new String[]{ "$sourceRID", "$sourceType", "$payload" };
        IGCSearchConditionSet conditionSet = new IGCSearchConditionSet(condition);
        IGCSearch igcSearch = new IGCSearch("$OMRS-Stub", properties, conditionSet);
        ReferenceList results = igcRestClient.search(igcSearch);
        OMRSStub stub = null;
        if (results.getPaging().getNumTotal() > 0) {
            if (results.getPaging().getNumTotal() > 1) {
                if (log.isWarnEnabled()) { log.warn("Found multiple stubs for asset, taking only the first: {}", stubName); }
            }
            stub = (OMRSStub) results.getItems().get(0);
        } else {
            if (log.isInfoEnabled()) { log.info("No stub found for asset: {}", stubName); }
        }
        return stub;

    }

    /**
     * Retrieve an OMRS asset stub (shadow copy of last version of an asset) for the provided asset.
     * If there is no existing stub, will return null.
     *
     * @param asset the asset for which to retrieve the OMRS stub
     * @return OMRSStub
     */
    public OMRSStub getOMRSStubForAsset(Reference asset) {
        // We need to translate the provided asset into a unique name for the stub
        return getOMRSStubForAsset(asset.getId(), asset.getType());
    }

    /**
     * Update (or create if it does not already exist) the OMRS asset stub for the provided asset.
     * (Note that this method assumes you have already retrieved the full asset being provided.)
     *
     * @param asset the asset for which to upsert the OMRS stub
     * @return String the Repository ID (RID) of the OMRS stub
     */
    public String upsertOMRSStubForAsset(Reference asset) {

        String stubName = getStubNameFromAsset(asset);

        // Get the full asset details as a singular JSON payload
        String payload = igcRestClient.getValueAsJSON(asset);

        // Construct the asset XML document, including the full asset payload
        StringWriter stringWriter = new StringWriter();
        try {

            XMLStreamWriter xmlStreamWriter = xmlOutputFactory.createXMLStreamWriter(stringWriter);
            xmlStreamWriter.writeStartDocument("UTF-8", "1.0");

            xmlStreamWriter.writeStartElement("doc");
            xmlStreamWriter.writeNamespace("xmlns", "http://www.ibm.com/iis/flow-doc");

            xmlStreamWriter.writeStartElement("assets");
            xmlStreamWriter.writeStartElement("asset");

            xmlStreamWriter.writeAttribute("class", "$OMRS-Stub");
            xmlStreamWriter.writeAttribute("repr", stubName);
            xmlStreamWriter.writeAttribute("ID", "stub1");

            addAttributeToAssetXML(xmlStreamWriter, "name", stubName);
            addAttributeToAssetXML(xmlStreamWriter, "$sourceType", asset.getType());
            addAttributeToAssetXML(xmlStreamWriter, "$sourceRID", asset.getId());
            addAttributeToAssetXML(xmlStreamWriter, "$payload", payload);

            xmlStreamWriter.writeEndElement(); // </asset>
            xmlStreamWriter.writeEndElement(); // </assets>

            xmlStreamWriter.writeStartElement("importAction");
            xmlStreamWriter.writeAttribute("completeAssetIDs", "stub1");
            xmlStreamWriter.writeEndElement(); // </importAction>

            xmlStreamWriter.writeEndElement(); // </doc>

            xmlStreamWriter.writeEndDocument();
            xmlStreamWriter.flush();
            xmlStreamWriter.close();

        } catch (XMLStreamException e) {
            if (log.isErrorEnabled()) { log.error("Unable to write XML stream: {}", asset, e); }
        }

        String stubXML = stringWriter.getBuffer().toString();
        if (log.isDebugEnabled()) { log.debug("Constructed XML for stub: {}", stubXML); }

        // Upsert using the constructed asset XML
        String results = igcRestClient.upsertOpenIgcAsset(stubXML);

        return results.substring("stub1".length() + 5, results.length() - 2);

    }

    /**
     * Adds the provided attribute to the asset XML being constructed.
     *
     * @param xmlStreamWriter the asset XML being constructed
     * @param attrName the name of the attribute to add
     * @param attrValue the value of the attribute
     * @throws XMLStreamException for any error during the XML processing
     */
    private void addAttributeToAssetXML(XMLStreamWriter xmlStreamWriter, String attrName, String attrValue) throws
            XMLStreamException {
        xmlStreamWriter.writeStartElement("attribute");
        xmlStreamWriter.writeAttribute("name", attrName);
        xmlStreamWriter.writeAttribute("value", attrValue);
        xmlStreamWriter.writeEndElement(); // </attribute>
    }

    /**
     * Delete the OMRS asset stub for the provided asset details (cannot require the asset itself since it has
     * already been removed).
     *
     * @param rid the Repository ID (RID) of the asset for which to delete the OMRS stub
     * @param assetType the IGC asset type of the asset for which to delete the OMRS stub
     * @return boolean - true on successful deletion, false otherwise
     */
    public boolean deleteOMRSStubForAsset(String rid, String assetType) {

        String stubName = getStubNameForAsset(rid, assetType);

        // Construct the asset XML document, including the full asset payload
        StringWriter stringWriter = new StringWriter();
        try {

            XMLStreamWriter xmlStreamWriter = xmlOutputFactory.createXMLStreamWriter(stringWriter);
            xmlStreamWriter.writeStartDocument("UTF-8", "1.0");

            xmlStreamWriter.writeStartElement("doc");
            xmlStreamWriter.writeNamespace("xmlns", "http://www.ibm.com/iis/flow-doc");

            xmlStreamWriter.writeStartElement("assets");
            xmlStreamWriter.writeStartElement("asset");

            xmlStreamWriter.writeAttribute("class", "$OMRS-Stub");
            xmlStreamWriter.writeAttribute("repr", stubName);
            xmlStreamWriter.writeAttribute("ID", "stub1");

            addAttributeToAssetXML(xmlStreamWriter, "name", stubName);

            xmlStreamWriter.writeEndElement(); // </asset>
            xmlStreamWriter.writeEndElement(); // </assets>

            xmlStreamWriter.writeStartElement("assetsToDelete");
            xmlStreamWriter.writeCharacters("stub1");
            xmlStreamWriter.writeEndElement(); // </assetsToDelete>

            xmlStreamWriter.writeEndElement(); // </doc>

            xmlStreamWriter.writeEndDocument();
            xmlStreamWriter.flush();
            xmlStreamWriter.close();

        } catch (XMLStreamException e) {
            log.error("Unable to write XML stream.", e);
        }

        String stubXML = stringWriter.getBuffer().toString();
        if (log.isDebugEnabled()) { log.debug("Constructed XML for stub deletion: {}", stubXML); }

        // Delete using the constructed asset XML
        return igcRestClient.deleteOpenIgcAsset(stubXML);

    }

    /**
     * Construct the unique name for the OMRS stub based on the provided asset.
     *
     * @param asset the asset for which to construct the unique OMRS stub name
     * @return String
     */
    private static String getStubNameFromAsset(Reference asset) {
        return getStubNameForAsset(asset.getId(), asset.getType());
    }

    /**
     * Construct the unique name for the OMRS stub based on the provided asset information.
     *
     * @param rid the Repository ID (RID) of the asset for which to construct the unique OMRS stub name
     * @param assetType the asset type (REST form) of the asset for which to construct the unique OMRS stub name
     * @return String
     */
    private static String getStubNameForAsset(String rid, String assetType) {
        return assetType + "_" + rid;
    }

    /**
     * Retrieve all of the asset details, including all relationships, from the RID.
     * <br><br>
     * Note that this is quite a heavy operation, relying on multiple REST calls, to build up what could be a very
     * large object; to simply retrieve the details without all relationships, see getAssetDetails.
     *
     * @param rid the Repository ID (RID) of the asset for which to retrieve all details
     * @return Reference - the object including all of its details and relationships
     */
    public Reference getFullAssetDetails(String rid) {

        // Start by retrieving the asset header, so we can introspect the class itself
        Reference assetRef = igcRestClient.getAssetRefById(rid);
        Reference fullAsset = null;

        if (assetRef != null) {

            // Introspect the full list of properties from the POJO of the asset
            String assetType = assetRef.getType();
            Class pojoClass = igcRestClient.getPOJOForType(assetType);
            if (pojoClass != null) {
                List<String> allProps = igcRestClient.getAllPropertiesFromPOJO(assetType);

                // Retrieve all asset properties, via search, as this will allow larger page
                // retrievals (and therefore be overall more efficient) than going by the GET of the asset
                fullAsset = assetRef.getAssetWithSubsetOfProperties(
                        igcRestClient,
                        allProps.toArray(new String[0]),
                        igcRestClient.getDefaultPageSize()
                );

                if (fullAsset != null) {

                    // Iterate through all the paged properties and retrieve all pages for each
                    List<String> allPaged = igcRestClient.getPagedRelationalPropertiesFromPOJO(assetType);
                    for (String pagedProperty : allPaged) {
                        ReferenceList pagedValue = (ReferenceList) igcRestClient.getPropertyByName(fullAsset, pagedProperty);
                        if (pagedValue != null) {
                            pagedValue.getAllPages(igcRestClient);
                        }
                    }

                    // Set the asset as fully retrieved, so we do not attempt to retrieve parts of it again
                    fullAsset.setFullyRetrieved();

                }
            } else {
                if (log.isDebugEnabled()) { log.debug("No registered POJO for asset type {} -- returning basic reference.", assetRef.getType()); }
                fullAsset = assetRef;
            }

        } else {
            if (log.isInfoEnabled()) { log.info("Unable to retrieve any asset with RID {} -- assume it was deleted.", rid); }
        }

        return fullAsset;

    }

    /**
     * Returns an IGCSearchSorting equivalent to the provided SequencingOrder, so long as the provided
     * sequencingOrder is not one of [ PROPERTY_ASCENDING, PROPERTY_DESCENDING ] (because these must
     * be explicitly mapped on a type-by-type basis).
     *
     * @param sequencingOrder the non-property SequencingOrder to create an IGC sort order from
     * @return IGCSearchSorting
     */
    public static IGCSearchSorting sortFromNonPropertySequencingOrder(SequencingOrder sequencingOrder) {
        IGCSearchSorting sort = null;
        if (sequencingOrder != null) {
            switch(sequencingOrder) {
                case GUID:
                    sort = new IGCSearchSorting("_id");
                    break;
                case CREATION_DATE_RECENT:
                    sort = new IGCSearchSorting("created_on", false);
                    break;
                case CREATION_DATE_OLDEST:
                    sort = new IGCSearchSorting("created_on", true);
                    break;
                case LAST_UPDATE_RECENT:
                    sort = new IGCSearchSorting("modified_on", false);
                    break;
                case LAST_UPDATE_OLDEST:
                    sort = new IGCSearchSorting("modified_on", true);
                    break;
                default:
                    sort = null;
                    break;
            }
        }
        return sort;
    }

}
