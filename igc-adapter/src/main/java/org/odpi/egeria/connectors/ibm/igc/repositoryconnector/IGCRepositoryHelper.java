/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector;

import org.odpi.egeria.connectors.ibm.igc.auditlog.IGCOMRSErrorCode;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestConstants;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Identity;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Paging;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearch;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchCondition;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchConditionSet;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchSorting;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.EntityMappingInstance;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.InstanceMapping;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.classifications.ClassificationMapping;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities.EntityMapping;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.RelationshipMapping;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.model.IGCEntityGuid;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.model.IGCRelationshipGuid;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.model.OMRSStub;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.stores.*;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.MatchCriteria;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.SequencingOrder;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.*;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.search.*;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.typedefs.PrimitiveDefCategory;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.typedefs.TypeDef;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.typedefs.TypeDefPatch;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.repositoryconnector.OMRSRepositoryHelper;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.repositoryconnector.OMRSRepositoryValidator;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IGCRepositoryHelper {

    private static final Logger log = LoggerFactory.getLogger(IGCRepositoryHelper.class);

    public static final String MAPPING_PKG = "org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.";
    public static final String DEFAULT_IGC_TYPE = "main_object";
    public static final String DEFAULT_IGC_TYPE_DISPLAY_NAME = "Main Object";

    private static final String GENERATED_ENTITY_QNAME_PREFIX = "gen!";
    private static final String GENERATED_ENTITY_QNAME_POSTFIX = "@";

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
     * Updates the type def stored for a particular mapping, using the provided TypeDefPatch.
     *
     * @param typeDefPatch to apply
     * @return boolean false when unable to find any existing mapping to which the patch would apply
     * @throws InvalidParameterException if the typeDefPatch is null
     * @throws PatchErrorException if the patch is either badly formatted or does not apply to the defined type
     */
    boolean updateEntityMapping(TypeDefPatch typeDefPatch) throws InvalidParameterException, PatchErrorException {
        return entityMappingStore.updateMapping(typeDefPatch);
    }

    /**
     * Retrieves the entity TypeDef by its guid, or null if the typedef is not mapped.
     *
     * @param guid of the typedef to retrieve
     * @return TypeDef
     */
    TypeDef getEntityTypeDefByGUID(String guid) {
        return entityMappingStore.getTypeDefByGUID(guid);
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
     * Updates the type def stored for a particular mapping, using the provided TypeDefPatch.
     *
     * @param typeDefPatch to apply
     * @return boolean false when unable to find any existing mapping to which the patch would apply
     * @throws InvalidParameterException if the typeDefPatch is null
     * @throws PatchErrorException if the patch is either badly formatted or does not apply to the defined type
     */
    boolean updateClassificationMapping(TypeDefPatch typeDefPatch) throws InvalidParameterException, PatchErrorException {
        return classificationMappingStore.updateMapping(typeDefPatch);
    }

    /**
     * Retrieves the classification TypeDef by its guid, or null if the typedef is not mapped.
     *
     * @param guid of the typedef to retrieve
     * @return TypeDef
     */
    TypeDef getClassificationTypeDefByGUID(String guid) {
        return classificationMappingStore.getTypeDefByGUID(guid);
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
     * Updates the type def stored for a particular mapping, using the provided TypeDefPatch.
     *
     * @param typeDefPatch to apply
     * @return boolean false when unable to find any existing mapping to which the patch would apply
     * @throws InvalidParameterException if the typeDefPatch is null
     * @throws PatchErrorException if the patch is either badly formatted or does not apply to the defined type
     */
    boolean updateRelationshipMapping(TypeDefPatch typeDefPatch) throws InvalidParameterException, PatchErrorException {
        return relationshipMappingStore.updateMapping(typeDefPatch);
    }

    /**
     * Retrieves the relationship TypeDef by its guid, or null if the typedef is not mapped.
     *
     * @param guid of the typedef to retrieve
     * @return TypeDef
     */
    TypeDef getRelationshipTypeDefByGUID(String guid) {
        return relationshipMappingStore.getTypeDefByGUID(guid);
    }

    /**
     * Retrieve a listing of all of the relationship mappings between OMRS and IGC.
     *
     * @return {@code List<RelationshipMapping>}
     */
    List<RelationshipMapping> getAllRelationshipMappings() {
        return relationshipMappingStore.getAllMappings();
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
     * Retrieves all of the entity mappings for the IGC asset type (if any).
     *
     * @param assetType the IGC asset type
     * @return {@code List<EntityMapping>}
     */
    List<EntityMapping> getEntityMappingsByIgcType(String assetType) { return entityMappingStore.getMappingsByIgcAssetType(assetType); }

    /**
     * Retrieves the entity mapping by the IGC asset prefix alone.
     *
     * @param prefix the prefix (non-null)
     * @return {@code Set<EntityMapping>}
     */
    Set<EntityMapping> getEntityMappingsByPrefix(String prefix) { return entityMappingStore.getMappingsByIgcPrefix(prefix); }

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
     * @param entityTypeGUID the GUID of the entity type that was requested as part of the search (or null for all)
     * @param entitySubtypeGUIDs optional list of GUIDs of subtypes by which to further limit results
     * @param matchProperties Optional list of property-based conditions to match.
     * @param fromEntityElement the starting element number of the entities to return.
     *                                This is used when retrieving elements
     *                                beyond the first page of results. Zero means start from the first element.
     * @param matchClassifications optional list of classification-based conditions to match.
     * @param sequencingProperty String name of the entity property that is to be used to sequence the results.
     *                           Null means do not sequence on a property name (see SequencingOrder).
     * @param sequencingOrder Enum defining how the results should be ordered.
     * @param pageSize the maximum number of result entities that can be returned on this request.  Zero means
     *                 unrestricted return results size.
     * @throws FunctionNotSupportedException when a regular expression is used for the search that is not supported
     * @throws RepositoryErrorException on any other error
     */
    void processResultsForMapping(EntityMapping mapping,
                                  List<EntityDetail> entityDetails,
                                  String userId,
                                  String entityTypeGUID,
                                  List<String> entitySubtypeGUIDs,
                                  SearchProperties matchProperties,
                                  int fromEntityElement,
                                  SearchClassifications matchClassifications,
                                  String sequencingProperty,
                                  SequencingOrder sequencingOrder,
                                  int pageSize) throws
            FunctionNotSupportedException,
            RepositoryErrorException {

        final String methodName = "processResultsForMapping";

        IGCOMRSMetadataCollection igcomrsMetadataCollection = (IGCOMRSMetadataCollection)igcomrsRepositoryConnector.getMetadataCollection();

        String igcAssetType = mapping.getIgcAssetType();
        IGCSearchConditionSet classificationLimiters = getSearchCriteriaForClassifications(
                igcAssetType,
                matchClassifications
        );

        if (matchClassifications != null && !matchClassifications.getConditions().isEmpty() && classificationLimiters == null) {
            log.info("Classification limiters were specified, but none apply to the asset type {}, so excluding this asset type from search.", igcAssetType);
        } else {

            IGCSearch igcSearch = new IGCSearch();
            igcSearch.addType(igcAssetType);

            /* Provided there is a mapping, build up a list of IGC-specific search criteria,
             * based on the values of the InstanceProperties provided */
            IGCSearchConditionSet igcSearchConditionSet = new IGCSearchConditionSet();

            MatchCriteria outerMatchCriteria = matchProperties.getMatchCriteria();
            IGCRepositoryHelper.addTypeSpecificConditions(mapping,
                    outerMatchCriteria,
                    matchProperties,
                    igcSearchConditionSet);

            String qualifiedNameRegex = null;

            InstanceMapping.SearchFilter filter = mapping.getAllNoneOrSome(matchProperties);

            if (filter.equals(InstanceMapping.SearchFilter.NONE)) {
                igcSearchConditionSet.addCondition(IGCRestConstants.getConditionToForceNoSearchResults());
            } else if (filter.equals(InstanceMapping.SearchFilter.SOME)) {
                // Otherwise, cycle through the mappings and add them
                qualifiedNameRegex = addAllConditions(igcSearchConditionSet, matchProperties, mapping);
            } else {
                log.debug("Skipping detailed matchProperties iteration, as we should return all types based on criteria and literal mappings.");
            }

            // If we marked to get nothing, no point in proceeding with any further search setup as we should skip
            // searching entirely
            if (!filter.equals(InstanceMapping.SearchFilter.NONE)) {
                if (classificationLimiters != null) {
                    igcSearchConditionSet.addNestedConditionSet(classificationLimiters);
                }

                IGCSearchSorting igcSearchSorting = null;
                if (sequencingProperty == null && sequencingOrder != null) {
                    igcSearchSorting = IGCRepositoryHelper.sortFromNonPropertySequencingOrder(sequencingOrder);
                }

                igcSearch.addProperties(mapping.getAllPropertiesForEntityDetail(igcRestClient, igcAssetType));
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
                    boolean generatedQN = isQualifiedNameOfGeneratedEntity(unqualifiedName);
                    // If all entities were requested, include regardless, otherwise only if the combo of QN and prefix match
                    includeResult = entityTypeGUID == null || (generatedQN && prefix != null) || (!generatedQN && prefix == null);
                    if (!includeResult) {
                        // Finally, check if this is a subtype of the type requested for the search if we thus far are not
                        // meant to include it (eg. if the search is for Referenceable we should actually include it even
                        // if there is no prefix and it is generated, as long as it is a subtype of Referenceable)
                        String omrsTypeName = mapping.getOmrsTypeDefName();
                        try {
                            TypeDef entityTypeDef = repositoryHelper.getTypeDef(repositoryName,
                                    "entityTypeGUID",
                                    entityTypeGUID,
                                    methodName);
                            includeResult = repositoryHelper.isTypeOf(metadataCollectionId, omrsTypeName, entityTypeDef.getName());
                        } catch (TypeErrorException e) {
                            log.error("Unable to lookup type for inclusion comparison: {}", entityTypeGUID, e);
                        }
                    }
                    log.debug("Include result for name '{}' and prefix '{}'? {}", unqualifiedName, prefix, includeResult);
                }

                // And finally, ensure that the requested type matches at least one of the types in the subtype filter
                // (if it is non-null)
                if (entitySubtypeGUIDs != null) {
                    includeResult = false;
                    String omrsTypeName = mapping.getOmrsTypeDefName();
                    for (String subtypeGUID : entitySubtypeGUIDs) {
                        TypeDef subtype = igcomrsMetadataCollection.getAnyTypeDefByGUID(subtypeGUID);
                        if (subtype != null && repositoryHelper.isTypeOf(metadataCollectionId, omrsTypeName, subtype.getName())) {
                            includeResult = true;
                            break;
                        }
                    }
                }

                if (includeResult) {
                    processResults(
                            mapping,
                            this.igcRestClient.search(igcSearch),
                            entityDetails,
                            matchProperties,
                            null,
                            pageSize,
                            userId
                    );
                }
            }
        }
    }

    private String addAllConditions(IGCSearchConditionSet igcSearchConditionSet,
                                    SearchProperties matchProperties,
                                    EntityMapping mapping) throws FunctionNotSupportedException {
        String qualifiedNameRegex = null;
        List<PropertyCondition> conditionsToMatch = matchProperties.getConditions();
        if (conditionsToMatch != null) {
            for (PropertyCondition condition : conditionsToMatch) {
                SearchProperties nestedConditions = condition.getNestedConditions();
                if (nestedConditions != null) {
                    IGCSearchConditionSet nestedSet = new IGCSearchConditionSet();
                    // TODO: how to handle the fact that 'qualifiedName' can now appear across multiple conditions?
                    String qname = addAllConditions(nestedSet, nestedConditions, mapping);
                    igcSearchConditionSet.addNestedConditionSet(nestedSet);
                    if (qname != null) {
                        qualifiedNameRegex = qname;
                    }
                } else {
                    String omrsPropertyName = condition.getProperty();
                    PropertyComparisonOperator operator = condition.getOperator();
                    InstancePropertyValue value = condition.getValue();
                    if (omrsPropertyName.equals("qualifiedName")) {
                        // TODO: how to handle the fact that 'qualifiedName' can now appear across multiple
                        //  conditions?
                        qualifiedNameRegex = value.valueAsString();
                    }
                    addSearchCondition(
                            igcSearchConditionSet,
                            omrsPropertyName,
                            operator,
                            value,
                            mapping
                    );
                }
            }
            setConditionsFromMatchCriteria(igcSearchConditionSet, matchProperties.getMatchCriteria());
        }
        return qualifiedNameRegex;
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
     * @param matchProperties the set of properties that should be matched (or null if none)
     * @param searchCriteria the string search criteria that should be matched (or null if none)
     * @param pageSize the number of results per page (0 for all results)
     * @param userId the user making the request
     */
    void processResults(EntityMapping mapper,
                        ItemList<Reference> results,
                        List<EntityDetail> entityDetails,
                        SearchProperties matchProperties,
                        String searchCriteria,
                        int pageSize,
                        String userId) throws RepositoryErrorException {

        if (pageSize == 0) {
            // If the provided pageSize was 0, we need to retrieve ALL pages of results...
            List<Reference> allPages = igcRestClient.getAllPages(null, results);
            results.setAllPages(allPages);
        }
        for (Reference reference : results.getItems()) {
            /* Only proceed with retrieving the EntityDetail if the type from IGC is not explicitly
             * a 'main_object' (as these are non-API-accessible asset types in IGC like column analysis master,
             * etc and will simply result in 400-code Bad Request messages from the API) */
            if (!reference.getType().equals(DEFAULT_IGC_TYPE)) {
                EntityDetail ed = null;

                log.debug("processResults with mapper: {}", mapper.getClass().getCanonicalName());
                IGCEntityGuid idToLookup;
                if (mapper.igcRidNeedsPrefix()) {
                    log.debug(" ... prefix required, getEntityDetail with: {}", mapper.getIgcRidPrefix() + "!" + reference.getId());
                    idToLookup = new IGCEntityGuid(metadataCollectionId, reference.getType(), mapper.getIgcRidPrefix(), reference.getId());
                } else {
                    log.debug(" ... no prefix required, getEntityDetail with: {}", reference.getId());
                    idToLookup = new IGCEntityGuid(metadataCollectionId, reference.getType(), reference.getId());
                }
                try {
                    ed = getEntityDetailFromFullAsset(userId, idToLookup, reference);
                } catch (EntityNotKnownException e) {
                    log.error("Unable to find entity: {}", idToLookup, e);
                }
                if (ed != null && includeResult(ed, matchProperties, searchCriteria)) {
                    entityDetails.add(ed);
                    // Stop adding details if we have hit the page size
                    if (pageSize > 0 && entityDetails.size() == pageSize) {
                        break;
                    }
                }
            }
        }

        // If we haven't filled a page of results (because we needed to skip some above), recurse...
        if (results.hasMorePages() && entityDetails.size() < pageSize) {
            ItemList<Reference> nextPage = igcRestClient.getNextPage(null, results);
            processResults(mapper, nextPage, entityDetails, matchProperties, searchCriteria, pageSize, userId);
        }

    }

    /**
     * Process the search results into the provided list of Relationship objects.
     *
     * @param mapper the EntityMapping that should be used to translate the results
     * @param results the IGC search results
     * @param relationships the list of Relationships to append
     * @param pageSize the number of results per page (0 for all results)
     * @param userId the user making the request
     */
    @SuppressWarnings("unchecked")
    void processResults(RelationshipMapping mapper,
                        ItemList<Reference> results,
                        List<Relationship> relationships,
                        int pageSize,
                        String userId) throws RepositoryErrorException {

        if (pageSize == 0) {
            // If the provided pageSize was 0, we need to retrieve ALL pages of results...
            List<Reference> allPages = igcRestClient.getAllPages(null, results);
            results.setAllPages(allPages);
        }

        IGCOMRSMetadataCollection igcomrsMetadataCollection = (IGCOMRSMetadataCollection) igcomrsRepositoryConnector.getMetadataCollection();

        // Recall that our search should always be entities of proxy endpoint TWO
        for (Reference candidateTwo : results.getItems()) {

            String igcType = candidateTwo.getType();
            String relationshipLevelType = mapper.getRelationshipLevelIgcAsset();

            String relationshipLevelRid = null;
            List<Reference> endOnes = new ArrayList<>();
            List<Reference> endTwos = new ArrayList<>();

            if (relationshipLevelType != null && relationshipLevelType.equals(igcType)) {

                // If the type is a relationship-level type, then use a relationship-level ProxyMapping to determine
                // the appropriate relationship ends
                relationshipLevelRid = candidateTwo.getId();
                log.debug("processResults (relationship-level) with mapper: {}", mapper.getClass().getCanonicalName());
                RelationshipMapping.RelationshipLevelProxyMapping pmRelationship = mapper.getRelationshipLevelProxyMapping();
                String propertyToOne = pmRelationship.getIgcRelationshipPropertyToEndOne();
                String propertyToTwo = pmRelationship.getIgcRelationshipPropertyToEndTwo();
                Object endOne = igcRestClient.getPropertyByName(candidateTwo, propertyToOne);
                IGCRepositoryHelper.addReferencesToList(igcRestClient, endOnes, endOne);
                Object endTwo = igcRestClient.getPropertyByName(candidateTwo, propertyToTwo);
                IGCRepositoryHelper.addReferencesToList(igcRestClient, endTwos, endTwo);

            } else if (!igcType.equals(DEFAULT_IGC_TYPE)) {

                // Otherwise, only proceed with retrieving the Relationship if the type from IGC is not explicitly
                // a 'main_object' (as these are non-API-accessible asset types in IGC like column analysis master,
                // etc and will simply result in 400-code Bad Request messages from the API)
                endTwos.add(candidateTwo);

                log.debug("processResults with mapper: {}", mapper.getClass().getCanonicalName());
                RelationshipMapping.ProxyMapping pmTwo = mapper.getProxyTwoMapping();
                List<String> relationshipProperties = pmTwo.getIgcRelationshipProperties();
                for (String igcPropertyName : relationshipProperties) {
                    if (igcPropertyName.equals(RelationshipMapping.SELF_REFERENCE_SENTINEL)) {
                        endOnes.add(candidateTwo);
                    } else {
                        Object otherEnd = igcRestClient.getPropertyByName(candidateTwo, igcPropertyName);
                        if (otherEnd != null) {
                            if (otherEnd instanceof Reference) {
                                Reference other = (Reference) otherEnd;
                                if (other.getType() != null) {
                                    endOnes.addAll(mapper.getProxyOneAssetFromAsset(other, igcRestClient));
                                }
                            } else if (otherEnd instanceof ItemList) {
                                ItemList<Reference> otherEnds = (ItemList<Reference>) otherEnd;
                                List<Reference> allOtherEnds = igcRestClient.getAllPages(igcPropertyName, otherEnds);
                                for (Reference other : allOtherEnds) {
                                    endOnes.addAll(mapper.getProxyOneAssetFromAsset(other, igcRestClient));
                                }
                            } else {
                                log.warn("Not a relationship, skipping: {}", otherEnd);
                            }
                        }
                    }
                }
            }
            for (Reference endOne : endOnes) {
                for (Reference endTwo : endTwos) {
                    String endOneType = endOne.getType();
                    String endTwoType = endTwo.getType();
                    if (endOneType != null && !endOneType.equals(DEFAULT_IGC_TYPE)
                            && endTwoType != null && !endTwoType.equals(DEFAULT_IGC_TYPE)
                            && mapper.includeRelationshipForIgcObjects(igcomrsRepositoryConnector, endOne, endTwo)) {
                        // We do not need a property name when the proxy order is known...
                        IGCRelationshipGuid idToLookup = RelationshipMapping.getRelationshipGUID(
                                this,
                                mapper,
                                endOne,
                                endTwo,
                                null,
                                relationshipLevelRid,
                                true
                        );
                        Relationship relationship = null;
                        try {
                            relationship = igcomrsMetadataCollection.getRelationship(userId, idToLookup.toString());
                        } catch (InvalidParameterException | RelationshipNotKnownException e) {
                            log.error("Unable to find relationship: {}", idToLookup);
                        }
                        if (relationship != null) {
                            relationships.add(relationship);
                            // Stop adding relationships if we have hit the page size
                            if (pageSize > 0 && relationships.size() == pageSize) {
                                break;
                            }
                        }
                    }
                }
                // Stop adding relationships if we have hit the page size
                if (pageSize > 0 && relationships.size() == pageSize) {
                    break;
                }
            }
            // Stop adding relationships if we have hit the page size
            if (pageSize > 0 && relationships.size() == pageSize) {
                break;
            }
        }

        // If we haven't filled a page of results (because we needed to skip some above), recurse...
        if (results.hasMorePages() && relationships.size() < pageSize) {
            ItemList<Reference> nextPage = igcRestClient.getNextPage(null, results);
            processResults(mapper, nextPage, relationships, pageSize, userId);
        }

    }

    /**
     * Indicates whether we should include the provided EntityDetail as a search result. This is necessary to enforce
     * case-sensitivity, which IGC's REST-based searches are not able to enforce themselves.
     *
     * @param ed the EntityDetail to check
     * @param matchProperties the set of match properties against which to check (or null if none)
     * @param searchCriteria the single string-based property to match against (or null if none)
     * @return boolean
     */
    private boolean includeResult(EntityDetail ed,
                                  SearchProperties matchProperties,
                                  String searchCriteria) {
        if (matchProperties != null) {
            MatchCriteria matchCriteria = matchProperties.getMatchCriteria();
            List<PropertyCondition> conditions = matchProperties.getConditions();
            InstanceProperties edProperties = ed.getProperties();
            if (edProperties == null) {
                edProperties = new InstanceProperties();
            }
            if (conditions != null) {
                for (PropertyCondition conditionToMatch : conditions) {
                    boolean bValuesMatch;
                    SearchProperties nestedConditions = conditionToMatch.getNestedConditions();
                    if (nestedConditions != null) {
                        bValuesMatch = includeResult(ed, nestedConditions, searchCriteria);
                    } else {
                        String propertyName = conditionToMatch.getProperty();
                        PropertyComparisonOperator operator = conditionToMatch.getOperator();
                        InstancePropertyValue valueToMatch = conditionToMatch.getValue();
                        InstancePropertyValue edValue = edProperties.getPropertyValue(propertyName);
                        bValuesMatch = valuesMatch(valueToMatch, operator, edValue);
                    }
                    if (matchCriteria.equals(MatchCriteria.ANY) && bValuesMatch) {
                        // If we just need to match one of the criteria and the values match, immediately return true
                        return true;
                    } else if (matchCriteria.equals(MatchCriteria.NONE) && bValuesMatch) {
                        // If we need to match no criteria and one of them matches, immediately return false
                        return false;
                    } else if (matchCriteria.equals(MatchCriteria.ALL) && !bValuesMatch) {
                        // If we need to match all criteria and one of them does not match, immediately return false
                        return false;
                    }
                }
                // If we manage to get through the loop above without returning, we must have matched successfully
                // if we were either matching everything or nothing
                return !matchCriteria.equals(MatchCriteria.ANY);
            }
            // If there were no properties defined to match (empty list), then return true
            return true;
        } else if (searchCriteria != null && !searchCriteria.equals("")) {
            InstanceProperties edProperties = ed.getProperties();
            if (edProperties == null) {
                return false;
            }
            Pattern pattern = Pattern.compile(searchCriteria);
            Map<String, InstancePropertyValue> allProperties = edProperties.getInstanceProperties();
            for (InstancePropertyValue value : allProperties.values()) {
                if (value.getInstancePropertyCategory().equals(InstancePropertyCategory.PRIMITIVE)
                        && ((PrimitivePropertyValue)value).getPrimitiveDefCategory().equals(PrimitiveDefCategory.OM_PRIMITIVE_TYPE_STRING)) {
                    Matcher matcher = pattern.matcher(value.valueAsString());
                    // Return true immediately on the first match we find
                    if (matcher.matches()) {
                        return true;
                    }
                }
            }
            // If we manage to get through all of the properties without finding a match, return false
            return false;
        }
        // If either sets of criteria were empty, we should return true
        return true;

    }

    /**
     * Indicates whether the provided values match each other. This is necessary to check not only simple equality,
     * but also cases where one of the values could contain a regular expression and the other needs to be matched
     * against it.
     *
     * @param valueRequested the value that was requested by the search
     * @param operator the comparison to apply
     * @param valueToCheck the actual value on the instance, to check against the first value
     * @return boolean
     */
    private boolean valuesMatch(InstancePropertyValue valueRequested,
                                PropertyComparisonOperator operator,
                                InstancePropertyValue valueToCheck) {

        if (operator.equals(PropertyComparisonOperator.IS_NULL)) {
            return valueToCheck == null || valueToCheck.valueAsObject() == null;
        } else if (operator.equals(PropertyComparisonOperator.NOT_NULL)) {
            if (valueToCheck == null) {
                return false;
            } else {
                return valueToCheck.valueAsObject() != null;
            }
        }

        OMRSRepositoryValidator repositoryValidator = igcomrsRepositoryConnector.getRepositoryValidator();
        BigDecimal requestAsNumber = repositoryValidator.getNumericRepresentation(valueRequested);
        BigDecimal valueAsNumber = repositoryValidator.getNumericRepresentation(valueToCheck);

        // At this point, there should be a non-null requested value
        switch (operator) {
            case EQ:
                return valueRequested.equals(valueToCheck);
            case NEQ:
                return !valueRequested.equals(valueToCheck);
            case GTE:
                if (requestAsNumber != null && valueAsNumber != null) {
                    return valueAsNumber.compareTo(requestAsNumber) >= 0;
                }
            case GT:
                if (requestAsNumber != null && valueAsNumber != null) {
                    return valueAsNumber.compareTo(requestAsNumber) > 0;
                }
            case LTE:
                if (requestAsNumber != null && valueAsNumber != null) {
                    return valueAsNumber.compareTo(requestAsNumber) <= 0;
                }
            case LT:
                if (requestAsNumber != null && valueAsNumber != null) {
                    return valueAsNumber.compareTo(requestAsNumber) < 0;
                }
            case LIKE:
                if (valueToCheck == null) {
                    return false;
                } else {
                    return valueToCheck.valueAsString().matches(valueRequested.valueAsString());
                }
            case IN:
                if (valueRequested instanceof ArrayPropertyValue) {
                    ArrayPropertyValue apv = (ArrayPropertyValue) valueRequested;
                    InstanceProperties ip = apv.getArrayValues();
                    Map<String, InstancePropertyValue> map = ip.getInstanceProperties();
                    for (InstancePropertyValue valueCandidate : map.values()) {
                        if (valueCandidate.equals(valueToCheck)) {
                            // Short-circuit out the moment we find a matching value in the array
                            return true;
                        }
                    }
                } else {
                    // If the IN operator was used against a non-array value this was an invalid search, so terminate it
                    return false;
                }
            default:
                // do nothing...
                break;
        }

        // If we managed to get through all of the conditions above, default to a non-match
        return false;

    }

    /**
     * Returns true if the provided string appears to be an identity string of some kind (partial or complete), and
     * false otherwise.
     *
     * @param candidate the string to check for identity characteristics
     * @return boolean
     */
    public boolean isIdentityString(String candidate) {
        int count = Identity.isIdentityString(candidate);
        if (repositoryHelper.isStartsWithRegex(candidate)) {
            String unqualified = repositoryHelper.getUnqualifiedLiteralString(candidate);
            // For a startsWith search to be an identity String, it MUST start with either a generated name prefix or
            // a type prefix character
            return (unqualified.startsWith(IGCRepositoryHelper.GENERATED_ENTITY_QNAME_PREFIX) || unqualified.startsWith(Identity.TYPE_PREFIX));
        } else if (count > 0 && (repositoryHelper.isContainsRegex(candidate)
                || repositoryHelper.isEndsWithRegex(candidate)) ) {
            // We must find at least a score of 1 (any single identity string characteristic) to treat the string as
            // an identity string, otherwise we should just treat it as a normal string
            return true;
        } else if (repositoryHelper.isExactMatchRegex(candidate) && count >= 4) {
            // RID is optional in some identities, and there could be cases where only a single component is present,
            // but if it is an exact match we should expect to find at least the type prefix and postfix (2) and the
            // type and name separator (2) for a minimum score of 4
            return true;
        }
        // If we have found a score of 0, or the search is not for a regular expression, return false
        return false;
    }

    /**
     * Set the conditions on the search based on the provided match criteria.
     *
     * @param igcSearchConditionSet the conditions to set (or reset)
     * @param matchCriteria the match criteria on which to base the conditions
     */
    public static void setConditionsFromMatchCriteria(IGCSearchConditionSet igcSearchConditionSet,
                                                      MatchCriteria matchCriteria) {
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
    }

    /**
     * Retrieve the IGC search conditions to limit results by the provided classification. Will return null if the
     * provided classification cannot be applied to the provided IGC asset type.
     *
     * @param igcAssetType name of the IGC asset type for which to limit the search results
     * @param condition classification-based condition by which to limit results
     * @return IGCSearchConditionSet
     * @throws FunctionNotSupportedException when a regular expression is provided that is not supported
     */
    private IGCSearchConditionSet getSearchCriteriaForClassification(String igcAssetType,
                                                                     ClassificationCondition condition) throws FunctionNotSupportedException {

        IGCSearchConditionSet igcSearchConditionSet = new IGCSearchConditionSet();

        String classificationName = condition.getName();
        ClassificationMapping classificationMapping = classificationMappingStore.getMappingByTypes(classificationName, igcAssetType);
        if (classificationMapping != null) {
            igcSearchConditionSet = classificationMapping.getIGCSearchCriteria(repositoryHelper, repositoryName, condition.getMatchProperties());
        } else {
            log.warn("Classification {} cannot be applied to IGC asset type {} - excluding from search limitations.", classificationName, igcAssetType);
        }

        return (igcSearchConditionSet.size() > 0 ? igcSearchConditionSet : null);

    }

    /**
     * Retrieve the IGC search conditions to limit results by the provided list of classifications.
     *
     * @param igcAssetType name of the IGC asset type for which to limit the search results
     * @param matchClassifications list of classification conditions by which to limit results
     * @return IGCSearchConditionSet
     * @throws FunctionNotSupportedException when a regular expression is provided that is not supported
     */
    IGCSearchConditionSet getSearchCriteriaForClassifications(String igcAssetType,
                                                              SearchClassifications matchClassifications) throws FunctionNotSupportedException {

        IGCSearchConditionSet igcSearchConditionSet = new IGCSearchConditionSet();

        if (matchClassifications != null && !matchClassifications.getConditions().isEmpty()) {
            for (ClassificationCondition condition : matchClassifications.getConditions()) {
                IGCSearchConditionSet classificationLimiter = getSearchCriteriaForClassification(
                        igcAssetType,
                        condition
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
     * Create a new IGC GUID that has a prefix (for an OMRS entity type that does not actually exist in IGC but is
     * generated from another entity type in IGC)
     *
     * @param assetType the IGC asset type
     * @param prefix the prefix to use to uniquely identify this generated entity's GUID
     * @param rid the Repository ID (RID) of the IGC asset
     * @return IGCEntityGuid
     */
    public IGCEntityGuid getEntityGuid(String assetType, String prefix, String rid) {
        return new IGCEntityGuid(metadataCollectionId, assetType, prefix, rid);
    }

    /**
     * Create a new IGC GUID that has a prefix (for an OMRS entity type that does not actually exist in IGC but is
     * generated from another entity type in IGC)
     *
     * @param assetType1 the IGC asset type of the first endpoint of the relationship
     * @param assetType2 the IGC asset type of the second endpoint of the relationship
     * @param prefix1 the prefix to use to uniquely identify the generated entity's GUID at the first endpoint of the relationship
     * @param prefix2 the prefix to use to uniquely identify the generated entity's GUID at the second endpoint of the relationship
     * @param rid1 the Repository ID (RID) of the IGC asset at the first endpoint of the relationship
     * @param rid2 the Repository ID (RID) of the IGC asset at the second endpoint of the relationship
     * @param relationshipType the OMRS type name of the relationship
     * @return IGCRelationshipGuid
     */
    public IGCRelationshipGuid getRelationshipGuid(String assetType1,
                                                   String assetType2,
                                                   String prefix1,
                                                   String prefix2,
                                                   String rid1,
                                                   String rid2,
                                                   String relationshipType) {
        return new IGCRelationshipGuid(
                metadataCollectionId,
                assetType1,
                assetType2,
                prefix1,
                prefix2,
                rid1,
                rid2,
                relationshipType);
    }

    /**
     * Indicates whether the provided qualified name is one for a generated entity (true) or not (false).
     *
     * @param qualifiedName the qualified name to check
     * @return boolean
     */
    public static boolean isQualifiedNameOfGeneratedEntity(String qualifiedName) {
        return qualifiedName.startsWith(GENERATED_ENTITY_QNAME_PREFIX);
    }

    /**
     * Create a unique qualified name for a generated entity.
     *
     * @param prefix the prefix to use to make the generated entity unique
     * @param qualifiedName the base qualified name for the entity from which this entity is generated
     * @return String
     */
    public static String getQualifiedNameForGeneratedEntity(String prefix, String qualifiedName) {
        return GENERATED_ENTITY_QNAME_PREFIX + prefix + GENERATED_ENTITY_QNAME_POSTFIX + qualifiedName;
    }

    /**
     * Retrieve an IGC-searchable form of the qualified name from the provided qualified name, which may be for a
     * generated entity.
     *
     * @param qualifiedName the qualified name to make searchable
     * @return String
     */
    public static String getSearchableQualifiedName(String qualifiedName) {
        if (isQualifiedNameOfGeneratedEntity(qualifiedName)) {
            return qualifiedName.substring(qualifiedName.indexOf(GENERATED_ENTITY_QNAME_POSTFIX) + 1);
        } else {
            return qualifiedName;
        }
    }

    /**
     * Retrieve the prefix from the qualified name of a potentially-generated entity.  Will return null if there is
     * no prefix (in the case that the provided qualified name is not for a generated entity).
     *
     * @param qualifiedName the qualified name from which to retrieve the prefix
     * @return String
     */
    public static String getPrefixFromGeneratedQualifiedName(String qualifiedName) {
        if (isQualifiedNameOfGeneratedEntity(qualifiedName)) {
            return qualifiedName.substring(qualifiedName.indexOf(GENERATED_ENTITY_QNAME_PREFIX) + GENERATED_ENTITY_QNAME_PREFIX.length(), qualifiedName.indexOf(GENERATED_ENTITY_QNAME_POSTFIX));
        } else {
            return null;
        }
    }

    /**
     * Return the header, classifications and properties of a specific entity, using the provided IGC asset.
     *
     * Note: this method assumes that the provided IGC object is already fully-populated for an EntityDetail,
     * so will avoid any further retrieval of information.
     *
     * @param userId unique identifier for requesting user.
     * @param guid unique IGC identifier for the entity.
     * @param asset the IGC asset for which an EntityDetail should be constructed.
     * @return EntityDetail structure.
     * @throws RepositoryErrorException there is a problem communicating with the metadata repository where
     *                                  the metadata collection is stored.
     * @throws EntityNotKnownException the entity cannot be found in IGC
     */
    public EntityDetail getEntityDetailFromFullAsset(String userId, IGCEntityGuid guid, Reference asset)
            throws RepositoryErrorException, EntityNotKnownException {

        final String methodName = "getEntityDetailFromFullAsset";

        validateGuidAndType(guid, methodName);

        // Otherwise, retrieve the mapping dynamically based on the type of asset
        EntityMappingInstance entityMap = getMappingInstanceForParameters(guid, asset, userId);

        return getEntityDetailFromMapInstance(entityMap, guid.getGeneratedPrefix(), guid.getAssetType(), methodName);

    }

    /**
     * Validate that the provided GUID is available within the IGC environment.
     * @param guid the GUID to check
     * @param methodName the name of the method looking for the GUID
     * @throws EntityNotKnownException when the GUID is null
     * @throws RepositoryErrorException when the type of object described by the GUID is an IGC 'main_object'
     */
    private void validateGuidAndType(IGCEntityGuid guid,
                                     String methodName) throws EntityNotKnownException, RepositoryErrorException {

        log.debug("{} with guid = {}", methodName, guid);

        if (guid == null) {
            raiseEntityNotKnownException(methodName, "<null>", "<null>", repositoryName);
        } else {
            String igcType = guid.getAssetType();

            // If we could not find any asset by the provided guid, throw an ENTITY_NOT_KNOWN exception
            if (igcType.equals(DEFAULT_IGC_TYPE)) {
                /* If the asset type returned has an IGC-listed type of 'main_object', it isn't one that the REST API
                 * of IGC supports (eg. a data rule detail object, a column analysis master object, etc)...
                 * Trying to further process it will result in failed REST API requests; so we should skip these objects */
                raiseRepositoryErrorException(IGCOMRSErrorCode.UNSUPPORTED_OBJECT_TYPE, methodName, guid.toString(), igcType, repositoryName);
            }
        }

    }

    /**
     * Apply the mapping to the object and retrieve the resulting EntityDetail.
     * @param mappingInstance the mapping instance to apply
     * @param prefix the IGC prefix (or null if none)
     * @param igcType the IGC object type
     * @param methodName the name of the method applying the mapping
     * @return EntityDetail
     * @throws RepositoryErrorException if there is no mapping for the type
     */
    private EntityDetail getEntityDetailFromMapInstance(EntityMappingInstance mappingInstance,
                                                        String prefix,
                                                        String igcType,
                                                        String methodName) throws RepositoryErrorException {

        EntityDetail detail = null;
        if (mappingInstance != null) {
            detail = EntityMapping.getEntityDetail(mappingInstance);
        } else {
            raiseRepositoryErrorException(IGCOMRSErrorCode.TYPEDEF_NOT_MAPPED, methodName, (prefix == null ? "" : prefix) + igcType, repositoryName);
        }
        return detail;

    }

    /**
     * Return the header, classifications and properties of a specific entity, using the provided IGC GUID.
     *
     * @param userId unique identifier for requesting user.
     * @param guid unique IGC identifier for the entity.
     * @return EntityDetail structure.
     * @throws RepositoryErrorException there is a problem communicating with the metadata repository where
     *                                  the metadata collection is stored.
     * @throws EntityNotKnownException the entity cannot be found in IGC
     */
    public EntityDetail getEntityDetail(String userId, IGCEntityGuid guid)
            throws RepositoryErrorException, EntityNotKnownException {

        final String methodName = "getEntityDetail";

        EntityDetail detail;
        validateGuidAndType(guid, methodName);
        String prefix = guid.getGeneratedPrefix();
        String igcType = guid.getAssetType();

        // Otherwise, retrieve the mapping dynamically based on the type of asset
        EntityMappingInstance entityMap = getMappingInstanceForParameters(
                igcType,
                guid.getRid(),
                prefix,
                userId);

        return getEntityDetailFromMapInstance(entityMap, prefix, igcType, methodName);

    }

    /**
     * Retrieves an instance of a mapping that can be used for the provided parameters (or null if none exists).
     *
     * @param guid the IGC GUID for the entity to be mapped
     * @param asset the IGC object to be mapped
     * @param userId the user making the request
     * @return EntityMappingInstance
     */
    private EntityMappingInstance getMappingInstanceForParameters(IGCEntityGuid guid,
                                                                  Reference asset,
                                                                  String userId) {
        log.debug("Looking for mapper for retrieved asset with guid {}", guid);

        EntityMappingInstance entityMap = null;
        EntityMapping found = getEntityMappingByIgcType(guid.getAssetType(), guid.getGeneratedPrefix());
        if (found != null) {
            log.debug("Found mapper class: {} ({})", found.getClass().getCanonicalName(), found);
            entityMap = new EntityMappingInstance(
                    found,
                    igcomrsRepositoryConnector,
                    asset,
                    userId
            );
        } else {
            log.debug("No mapper class found!");
        }

        return entityMap;

    }

    /**
     * Retrieves an instance of a mapping that can be used for the provided parameters (or null if none exists).
     *
     * @param igcAssetType the type of IGC asset to be mapped
     * @param rid the Repository ID (RID) of the IGC asset to be mapped
     * @param prefix the prefix used for the asset (if any; null otherwise)
     * @param userId the user making the request
     * @return EntityMappingInstance
     */
    public EntityMappingInstance getMappingInstanceForParameters(String igcAssetType, String rid, String prefix, String userId) {

        log.debug("Looking for mapper for type {} with prefix {}", igcAssetType, prefix);

        EntityMappingInstance entityMap = null;
        EntityMapping found = getEntityMappingByIgcType(igcAssetType, prefix);
        if (found != null) {
            log.debug("Found mapper class: {} ({})", found.getClass().getCanonicalName(), found);
            entityMap = new EntityMappingInstance(
                    found,
                    igcomrsRepositoryConnector,
                    igcAssetType,
                    rid,
                    userId
            );
        } else {
            log.debug("No mapper class found!");
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

    private void addSearchCondition(IGCSearchConditionSet igcSearchConditionSet,
                                    String omrsPropertyName,
                                    PropertyComparisonOperator operator,
                                    InstancePropertyValue value,
                                    EntityMapping mapping) throws FunctionNotSupportedException {

        if (omrsPropertyName != null) {
            if (omrsPropertyName.equals(EntityMapping.COMPLEX_MAPPING_SENTINEL)) {
                log.warn("Unhandled search condition: complex OMRS mapping, unknown IGC property.");
            } else {

                String igcPropertyName = mapping.getIgcPropertyName(omrsPropertyName);

                if (igcPropertyName == null) {
                    log.warn("Unhandled search condition for unknown IGC property from OMRS property: {}", omrsPropertyName);
                } else if (igcPropertyName.equals(EntityMapping.COMPLEX_MAPPING_SENTINEL)) {

                    log.debug("Adding complex property search criteria for: {}", omrsPropertyName);
                    mapping.addComplexPropertySearchCriteria(
                            repositoryHelper,
                            repositoryName,
                            igcRestClient,
                            igcSearchConditionSet,
                            igcPropertyName,
                            omrsPropertyName,
                            operator,
                            value);

                } else if (!igcPropertyName.equals(EntityMapping.LITERAL_MAPPING_SENTINEL)) {

                    log.debug("Adding non-literal property search criteria for: {}", omrsPropertyName);
                    addIGCSearchCondition(
                            repositoryHelper,
                            repositoryName,
                            igcSearchConditionSet,
                            igcPropertyName,
                            operator,
                            value);

                }

            }
        }

    }

    /**
     * Adds the provided value to search criteria for IGC.
     *
     * @param repositoryHelper helper for the OMRS repository
     * @param repositoryName the name of the metadata repository
     * @param igcSearchConditionSet the search conditions to which to add the criteria
     * @param igcPropertyName the IGC property name to search
     * @param operator the operator to use when comparing the values
     * @param value the value for which to search
     * @throws FunctionNotSupportedException when a regular expression or comparison operator is used for the search
     *                                       that is not supported or does not fit the type of property being compared
     */
    public static void addIGCSearchCondition(OMRSRepositoryHelper repositoryHelper,
                                             String repositoryName,
                                             IGCSearchConditionSet igcSearchConditionSet,
                                             String igcPropertyName,
                                             PropertyComparisonOperator operator,
                                             InstancePropertyValue value) throws FunctionNotSupportedException {

        final String methodName = "addIGCSearchCondition";

        if (operator.equals(PropertyComparisonOperator.IS_NULL)) {
            igcSearchConditionSet.addCondition(new IGCSearchCondition(
                    igcPropertyName,
                    "isNull",
                    false
            ));
        } else if (operator.equals(PropertyComparisonOperator.NOT_NULL)) {
            igcSearchConditionSet.addCondition(new IGCSearchCondition(
                    igcPropertyName,
                    "isNull",
                    true
            ));
        } else {

            InstancePropertyCategory category = value.getInstancePropertyCategory();
            switch (category) {
                case PRIMITIVE:
                    PrimitivePropertyValue actualValue = (PrimitivePropertyValue) value;
                    PrimitiveDefCategory primitiveType = actualValue.getPrimitiveDefCategory();
                    switch (primitiveType) {
                        case OM_PRIMITIVE_TYPE_BOOLEAN:
                            validateBooleanOperator(operator, methodName);
                            igcSearchConditionSet.addCondition(new IGCSearchCondition(
                                    igcPropertyName,
                                    getIgcOperator(operator),
                                    actualValue.valueAsString()
                            ));
                            break;
                        case OM_PRIMITIVE_TYPE_SHORT:
                        case OM_PRIMITIVE_TYPE_INT:
                        case OM_PRIMITIVE_TYPE_LONG:
                        case OM_PRIMITIVE_TYPE_FLOAT:
                        case OM_PRIMITIVE_TYPE_DOUBLE:
                        case OM_PRIMITIVE_TYPE_BIGINTEGER:
                        case OM_PRIMITIVE_TYPE_BIGDECIMAL:
                            validateNumericOperator(operator, primitiveType.getName(), methodName);
                            igcSearchConditionSet.addCondition(new IGCSearchCondition(
                                    igcPropertyName,
                                    getIgcOperator(operator),
                                    actualValue.valueAsString()
                            ));
                            break;
                        case OM_PRIMITIVE_TYPE_DATE:
                            validateNumericOperator(operator, primitiveType.getName(), methodName);
                            // For dates, we need to search within the 1 second interval, as that is all that IGC exposes
                            // via the REST API (despite storing internally down to millisecond level)
                            Long epoch = (Long) actualValue.getPrimitiveValue();
                            switch (operator) {
                                case EQ:
                                    igcSearchConditionSet.addCondition(new IGCSearchCondition(
                                            igcPropertyName,
                                            epoch,
                                            epoch + 999
                                    ));
                                    break;
                                case NEQ:
                                    // Need conditions that allow either less than or greater than the requested date
                                    IGCSearchCondition less = new IGCSearchCondition(
                                            igcPropertyName,
                                            "<",
                                            "" + epoch
                                    );
                                    IGCSearchCondition more = new IGCSearchCondition(
                                            igcPropertyName,
                                            ">",
                                            "" + (epoch + 999)
                                    );
                                    IGCSearchConditionSet nested = new IGCSearchConditionSet();
                                    nested.addCondition(less);
                                    nested.addCondition(more);
                                    nested.setMatchAnyCondition(true);
                                    igcSearchConditionSet.addNestedConditionSet(nested);
                                    break;
                                case GTE:
                                    igcSearchConditionSet.addCondition(new IGCSearchCondition(
                                            igcPropertyName,
                                            ">=",
                                            "" + epoch
                                    ));
                                    break;
                                case GT:
                                    igcSearchConditionSet.addCondition(new IGCSearchCondition(
                                            igcPropertyName,
                                            ">",
                                            "" + epoch
                                    ));
                                    break;
                                case LTE:
                                    igcSearchConditionSet.addCondition(new IGCSearchCondition(
                                            igcPropertyName,
                                            "<=",
                                            "" + (epoch + 999)
                                    ));
                                    break;
                                case LT:
                                    igcSearchConditionSet.addCondition(new IGCSearchCondition(
                                            igcPropertyName,
                                            "<",
                                            "" + (epoch + 999)
                                    ));
                                    break;
                            }
                            break;
                        case OM_PRIMITIVE_TYPE_BYTE:
                        case OM_PRIMITIVE_TYPE_CHAR:
                        case OM_PRIMITIVE_TYPE_STRING:
                        default:
                            validateStringOperator(operator, primitiveType.getName(), methodName);
                            String candidateValue = actualValue.valueAsString();
                            IGCSearchCondition regex = IGCRepositoryHelper.getRegexSearchCondition(
                                    repositoryHelper,
                                    repositoryName,
                                    methodName,
                                    igcPropertyName,
                                    operator,
                                    candidateValue
                            );
                            igcSearchConditionSet.addCondition(regex);
                            break;
                    }
                    break;
                case ENUM:
                    validateEnumOperator(operator, methodName);
                    igcSearchConditionSet.addCondition(new IGCSearchCondition(
                            igcPropertyName,
                            getIgcOperator(operator),
                            ((EnumPropertyValue) value).getSymbolicName()
                    ));
                    break;
            /*case STRUCT:
                Map<String, InstancePropertyValue> structValues = ((StructPropertyValue) value).getAttributes().getInstanceProperties();
                for (Map.Entry<String, InstancePropertyValue> nextEntry : structValues.entrySet()) {
                    addSearchConditionFromValue(
                            igcSearchConditionSet,
                            nextEntry.getKey(),
                            mapping,
                            nextEntry.getValue()
                    );
                }
                break;*/
                case MAP:
                    // TODO: probably need to re-think what operators are valid for a map comparison
                    Map<String, InstancePropertyValue> mapValues = ((MapPropertyValue) value).getMapValues().getInstanceProperties();
                    for (Map.Entry<String, InstancePropertyValue> nextEntry : mapValues.entrySet()) {
                        addIGCSearchCondition(
                                repositoryHelper,
                                repositoryName,
                                igcSearchConditionSet,
                                nextEntry.getKey(),
                                operator,
                                nextEntry.getValue()
                        );
                    }
                    break;
                case ARRAY:
                    if (operator.equals(PropertyComparisonOperator.IN)) {
                        InstanceProperties ip = ((ArrayPropertyValue) value).getArrayValues();
                        if (ip != null) {
                            Map<String, InstancePropertyValue> map = ip.getInstanceProperties();
                            if (map != null) {
                                List<String> valueList = new ArrayList<>();
                                boolean primitivesOnly = true;
                                IGCSearchConditionSet nestedConditions = new IGCSearchConditionSet();
                                for (InstancePropertyValue oneValue : map.values()) {
                                    if (oneValue.getInstancePropertyCategory().equals(InstancePropertyCategory.PRIMITIVE)) {
                                        valueList.add(oneValue.valueAsString());
                                    } else {
                                        primitivesOnly = false;
                                        IGCSearchConditionSet nested = new IGCSearchConditionSet();
                                        // TODO: this will do EQ even for strings, should we be more flexible find some
                                        //  way to do LIKE for strings as well?
                                        addIGCSearchCondition(repositoryHelper,
                                                repositoryName,
                                                nested,
                                                igcPropertyName,
                                                PropertyComparisonOperator.EQ,
                                                oneValue);
                                        nestedConditions.addNestedConditionSet(nested);
                                    }
                                }
                                if (primitivesOnly) {
                                    // If all primitives, do a single in-list-based search
                                    igcSearchConditionSet.addCondition(new IGCSearchCondition(
                                            igcPropertyName,
                                            valueList
                                    ));
                                } else {
                                    // If complex, create multiple nested conditions and OR them
                                    nestedConditions.setMatchAnyCondition(true);
                                    igcSearchConditionSet.addNestedConditionSet(nestedConditions);
                                }
                            } else {
                                // Force no results if the array is empty
                                igcSearchConditionSet.addCondition(IGCRestConstants.getConditionToForceNoSearchResults());
                            }
                        } else {
                            // Force no results if the array is empty
                            igcSearchConditionSet.addCondition(IGCRestConstants.getConditionToForceNoSearchResults());
                        }
                    } else {
                        // TODO: should we not also allow an exact-match (EQ) and its opposite (NEQ)?
                        /*Map<String, InstancePropertyValue> arrayValues = ((ArrayPropertyValue) value).getArrayValues().getInstanceProperties();
                        for (Map.Entry<String, InstancePropertyValue> nextEntry : arrayValues.entrySet()) {
                            addIGCSearchCondition(
                                    repositoryHelper,
                                    repositoryName,
                                    igcSearchConditionSet,
                                    igcPropertyName,
                                    operator,
                                    nextEntry.getValue()
                            );
                        }*/
                        throw new FunctionNotSupportedException(IGCOMRSErrorCode.INVALID_SEARCH_COMPARISON.getMessageDefinition(operator.getName(), category.getName()),
                                IGCRepositoryHelper.class.getName(),
                                methodName);
                    }
                    break;
                default:
                    // Do nothing
                    log.warn("Unable to handle search criteria for value type: {}", category);
                    break;
            }

        }

    }

    /**
     * Translate the provided open metadata property comparison operator into an IGC equivalent comparison operator.
     *
     * @param operator the open metadata property comparison operator to translate
     * @return String
     */
    public static String getIgcOperator(PropertyComparisonOperator operator) {
        switch (operator) {
            case EQ:
                return "=";
            case NEQ:
                return "<>";
            case GTE:
                return ">=";
            case GT:
                return ">";
            case LTE:
                return "<=";
            case LT:
                return "<";
            case LIKE:
                return "like";
            case IN:
                return "in";
            case IS_NULL:
            case NOT_NULL:
                return "isNull";
            default:
                return null;
        }
    }

    /**
     * Validates the provided operator can be applied to booleans.
     *
     * @param operator the operator to check
     * @param methodName of the calling method
     * @throws FunctionNotSupportedException if an invalid comparison operator was requested
     */
    public static void validateBooleanOperator(PropertyComparisonOperator operator,
                                               String methodName) throws FunctionNotSupportedException {
        boolean is = operator.equals(PropertyComparisonOperator.EQ)
                || operator.equals(PropertyComparisonOperator.NEQ)
                || operator.equals(PropertyComparisonOperator.IS_NULL)
                || operator.equals(PropertyComparisonOperator.NOT_NULL);
        checkOperator(is, operator, PrimitiveDefCategory.OM_PRIMITIVE_TYPE_BOOLEAN.getName(), methodName);
    }

    /**
     * Validates the provided operator can be applied to enumerations.
     *
     * @param operator the operator to check
     * @param methodName of the calling method
     * @throws FunctionNotSupportedException if an invalid comparison operator was requested
     */
    public static void validateEnumOperator(PropertyComparisonOperator operator,
                                            String methodName) throws FunctionNotSupportedException {
        boolean is = operator.equals(PropertyComparisonOperator.EQ)
                || operator.equals(PropertyComparisonOperator.NEQ)
                || operator.equals(PropertyComparisonOperator.IS_NULL)
                || operator.equals(PropertyComparisonOperator.NOT_NULL);
        checkOperator(is, operator, InstancePropertyCategory.ENUM.getName(), methodName);
    }

    /**
     * Validates the provided operator can be applied to numbers.
     *
     * @param operator the operator to check
     * @param type description of the type being compared
     * @param methodName of the calling method
     * @throws FunctionNotSupportedException if an invalid comparison operator was requested
     */
    public static void validateNumericOperator(PropertyComparisonOperator operator,
                                               String type,
                                               String methodName) throws FunctionNotSupportedException {
        boolean is = operator.equals(PropertyComparisonOperator.EQ)
                || operator.equals(PropertyComparisonOperator.NEQ)
                || operator.equals(PropertyComparisonOperator.GTE)
                || operator.equals(PropertyComparisonOperator.GT)
                || operator.equals(PropertyComparisonOperator.LTE)
                || operator.equals(PropertyComparisonOperator.LT)
                || operator.equals(PropertyComparisonOperator.IS_NULL)
                || operator.equals(PropertyComparisonOperator.NOT_NULL);
        checkOperator(is, operator, type, methodName);
    }

    /**
     * Validates the provided operator can be applied to strings.
     *
     * @param operator the operator to check
     * @param type description of the type being compared
     * @param methodName of the calling method
     * @throws FunctionNotSupportedException if an invalid comparison operator was requested
     */
    public static void validateStringOperator(PropertyComparisonOperator operator,
                                              String type,
                                              String methodName) throws FunctionNotSupportedException {
        boolean is = operator.equals(PropertyComparisonOperator.LIKE)
                || operator.equals(PropertyComparisonOperator.EQ)
                || operator.equals(PropertyComparisonOperator.NEQ)
                || operator.equals(PropertyComparisonOperator.IS_NULL)
                || operator.equals(PropertyComparisonOperator.NOT_NULL);
        checkOperator(is, operator, type, methodName);
    }

    private static void checkOperator(boolean isValid,
                                      PropertyComparisonOperator operator,
                                      String type,
                                      String methodName) throws FunctionNotSupportedException {
        if (!isValid) {
            throw new FunctionNotSupportedException(IGCOMRSErrorCode.INVALID_SEARCH_COMPARISON.getMessageDefinition(operator.getName(), type),
                    IGCRepositoryHelper.class.getName(),
                    methodName);
        }
    }

    /**
     * Indicates whether the provided values are equivalent (true) or not (false).
     *
     * @param value the actual (literal) value to compare
     * @param operator the operator through which to compare the values
     * @param requestedValue the OMRS value that was requested (treated as a regular expression if the type is a string)
     * @return boolean
     */
    public static boolean equivalentValues(Object value,
                                           PropertyComparisonOperator operator,
                                           InstancePropertyValue requestedValue) {

        // Handle the null operators
        if (operator.equals(PropertyComparisonOperator.IS_NULL)) {
            return (value == null);
        } else if (operator.equals(PropertyComparisonOperator.NOT_NULL)) {
            return (value != null);
        } else {
            if (value == null && requestedValue == null) {
                return true;
            }
            if (value == null || requestedValue == null) {
                return false;
            }
        }

        // At this point both values should be non-null
        InstancePropertyCategory category = requestedValue.getInstancePropertyCategory();
        switch (category) {
            case PRIMITIVE:
                PrimitivePropertyValue actualValue = (PrimitivePropertyValue) requestedValue;
                PrimitiveDefCategory primitiveType = actualValue.getPrimitiveDefCategory();
                switch (primitiveType) {
                    case OM_PRIMITIVE_TYPE_BOOLEAN:
                        return compareBool(value, operator, actualValue);
                    case OM_PRIMITIVE_TYPE_SHORT:
                        return compareShort(value, operator, actualValue);
                    case OM_PRIMITIVE_TYPE_INT:
                        return compareInt(value, operator, actualValue);
                    case OM_PRIMITIVE_TYPE_LONG:
                        return compareLong(value, operator, actualValue);
                    case OM_PRIMITIVE_TYPE_FLOAT:
                        return compareFloat(value, operator, actualValue);
                    case OM_PRIMITIVE_TYPE_DOUBLE:
                        return compareDouble(value, operator, actualValue);
                    case OM_PRIMITIVE_TYPE_BIGINTEGER:
                        return compareBigInteger(value, operator, actualValue);
                    case OM_PRIMITIVE_TYPE_BIGDECIMAL:
                        return compareBigDecimal(value, operator, actualValue);
                    case OM_PRIMITIVE_TYPE_DATE:
                        return compareDate(value, operator, actualValue);
                    case OM_PRIMITIVE_TYPE_BYTE:
                    case OM_PRIMITIVE_TYPE_CHAR:
                    case OM_PRIMITIVE_TYPE_STRING:
                    default:
                        return compareString(value, operator, actualValue);
                }
            case ENUM:
                String symbolicName = ((EnumPropertyValue) requestedValue).getSymbolicName();
                return value.toString().equals(symbolicName);
            /*case STRUCT:
                break;
            case MAP:
                break;*/
            case ARRAY:
                return compareList(value, operator, (ArrayPropertyValue) requestedValue);
            default:
                // Do nothing
                log.warn("Unable to handle value equivalency for value type: {}", category);
                break;
        }

        return false;

    }

    private static boolean compareBool(Object value,
                                       PropertyComparisonOperator operator,
                                       PrimitivePropertyValue requestedValue) {
        boolean lValue = (value instanceof Boolean) ? (Boolean) value : value.toString().toLowerCase().equals("true");
        boolean rValue = (Boolean) requestedValue.getPrimitiveValue();
        switch (operator) {
            case EQ:
                return lValue == rValue;
            case NEQ:
                return lValue != rValue;
            default:
                // If a non-boolean comparison operator was used, return false
                return false;
        }
    }

    private static boolean compareShort(Object value,
                                        PropertyComparisonOperator operator,
                                        PrimitivePropertyValue requestedValue) {
        Short lValue = (value instanceof Short) ? (Short) value : Short.parseShort(value.toString());
        Short rValue = (Short) requestedValue.getPrimitiveValue();
        return compareNumber(lValue.compareTo(rValue), operator);
    }

    private static boolean compareInt(Object value,
                                      PropertyComparisonOperator operator,
                                      PrimitivePropertyValue requestedValue) {
        Integer lValue = (value instanceof Integer) ? (Integer) value : Integer.parseInt(value.toString());
        Integer rValue = (Integer) requestedValue.getPrimitiveValue();
        return compareNumber(lValue.compareTo(rValue), operator);
    }

    private static boolean compareLong(Object value,
                                       PropertyComparisonOperator operator,
                                       PrimitivePropertyValue requestedValue) {
        Long lValue = (value instanceof Long) ? (Long) value : Long.parseLong(value.toString());
        Long rValue = (Long) requestedValue.getPrimitiveValue();
        return compareNumber(lValue.compareTo(rValue), operator);
    }

    private static boolean compareFloat(Object value,
                                        PropertyComparisonOperator operator,
                                        PrimitivePropertyValue requestedValue) {
        Float lValue = (value instanceof Float) ? (Float) value : Float.parseFloat(value.toString());
        Float rValue = (Float) requestedValue.getPrimitiveValue();
        return compareNumber(lValue.compareTo(rValue), operator);
    }

    private static boolean compareDouble(Object value,
                                         PropertyComparisonOperator operator,
                                         PrimitivePropertyValue requestedValue) {
        Double lValue = (value instanceof Double) ? (Double) value : Double.parseDouble(value.toString());
        Double rValue = (Double) requestedValue.getPrimitiveValue();
        return compareNumber(lValue.compareTo(rValue), operator);
    }

    private static boolean compareBigInteger(Object value,
                                             PropertyComparisonOperator operator,
                                             PrimitivePropertyValue requestedValue) {
        BigInteger lValue = (value instanceof BigInteger) ? (BigInteger) value : new BigInteger(value.toString());
        BigInteger rValue = (BigInteger) requestedValue.getPrimitiveValue();
        return compareNumber(lValue.compareTo(rValue), operator);
    }

    private static boolean compareBigDecimal(Object value,
                                             PropertyComparisonOperator operator,
                                             PrimitivePropertyValue requestedValue) {
        BigDecimal lValue = (value instanceof BigDecimal) ? (BigDecimal) value : new BigDecimal(value.toString());
        BigDecimal rValue = (BigDecimal) requestedValue.getPrimitiveValue();
        return compareNumber(lValue.compareTo(rValue), operator);
    }

    private static boolean compareDate(Object value,
                                       PropertyComparisonOperator operator,
                                       PrimitivePropertyValue requestedValue) {
        Long lValue = -1L;
        if (value instanceof Long) {
            lValue = (Long) value;
        } else if (value instanceof Date) {
            lValue = ((Date) value).getTime();
        }
        Long rValue = (Long) requestedValue.getPrimitiveValue();
        return compareNumber(lValue.compareTo(rValue), operator);
    }

    private static boolean compareNumber(int compareToResult, PropertyComparisonOperator operator) {
        switch (operator) {
            case EQ:
                return compareToResult == 0;
            case NEQ:
                return compareToResult != 0;
            case GTE:
                return compareToResult >= 0;
            case GT:
                return compareToResult > 0;
            case LTE:
                return compareToResult <= 0;
            case LT:
                return compareToResult < 0;
            default:
                // If a non-numeric comparison operator was used, return false (IN fits this as it means the right-hand
                // argument must be a list rather than a primitive)
                return false;
        }
    }

    private static boolean compareString(Object value,
                                         PropertyComparisonOperator operator,
                                         PrimitivePropertyValue requestedValue) {
        String lValue = value.toString();
        String rValue = requestedValue.valueAsString();
        switch (operator) {
            case EQ:
                return lValue.equals(rValue);
            case NEQ:
                return !lValue.equals(rValue);
            case LIKE:
                return lValue.matches(rValue);
            default:
                // If a non-string comparison operator was used, return false (IN fits this as it means the right-hand
                // argument must be a list rather than a primitive)
                return false;
        }
    }

    private static boolean compareList(Object value,
                                       PropertyComparisonOperator operator,
                                       ArrayPropertyValue requestedValue) {
        if (operator.equals(PropertyComparisonOperator.IN)) {
            // Only check if we're using a list-comparison operator
            InstanceProperties ip = requestedValue.getArrayValues();
            if (ip != null) {
                Map<String, InstancePropertyValue> map = ip.getInstanceProperties();
                if (map != null) {
                    Collection<InstancePropertyValue> requestedValues = map.values();
                    boolean anyMatch = false;
                    for (InstancePropertyValue oneValue : requestedValues) {
                        if (equivalentValues(value, operator, oneValue)) {
                            anyMatch = true;
                            break;
                        }
                    }
                    return anyMatch;
                }
            }
        }
        // Note that if ultimately the array definition is empty, we will not match anything
        return false;
    }

    /**
     * Adds search criteria based on the provided regular expression.
     *
     * @param repositoryHelper the repository helper
     * @param repositoryName name of the respository
     * @param methodName method adding the criteria
     * @param igcPropertyToSearch the IGC property that should be searched
     * @param valueWithRegex the value that should be used for the search, as a regular expression
     * @return IGCSearchCondition
     * @throws FunctionNotSupportedException when a regular expression is provided for the search that is not supported
     */
    public static IGCSearchCondition getRegexSearchCondition(OMRSRepositoryHelper repositoryHelper,
                                                             String repositoryName,
                                                             String methodName,
                                                             String igcPropertyToSearch,
                                                             String valueWithRegex) throws FunctionNotSupportedException {
        return getRegexSearchCondition(repositoryHelper,
                repositoryName,
                methodName,
                igcPropertyToSearch,
                PropertyComparisonOperator.LIKE,
                valueWithRegex);
    }

    /**
     * Adds search criteria based on the provided regular expression.
     *
     * @param repositoryHelper the repository helper
     * @param repositoryName name of the respository
     * @param methodName method adding the criteria
     * @param igcPropertyToSearch the IGC property that should be searched
     * @param operator to use for comparison
     * @param valueWithRegex the value that should be used for the search, as a regular expression
     * @return IGCSearchCondition
     * @throws FunctionNotSupportedException when a regular expression is provided for the search that is not supported
     */
    public static IGCSearchCondition getRegexSearchCondition(OMRSRepositoryHelper repositoryHelper,
                                                             String repositoryName,
                                                             String methodName,
                                                             String igcPropertyToSearch,
                                                             PropertyComparisonOperator operator,
                                                             String valueWithRegex) throws FunctionNotSupportedException {

        IGCSearchCondition igcSearchCondition;
        String igcValueToSearch = repositoryHelper.getUnqualifiedLiteralString(valueWithRegex);
        if (operator.equals(PropertyComparisonOperator.LIKE)) {
            if (repositoryHelper.isContainsRegex(valueWithRegex)) {
                igcSearchCondition = new IGCSearchCondition(
                        igcPropertyToSearch,
                        "like %{0}%",
                        igcValueToSearch
                );
            } else if (repositoryHelper.isStartsWithRegex(valueWithRegex)) {
                igcSearchCondition = new IGCSearchCondition(
                        igcPropertyToSearch,
                        "like {0}%",
                        igcValueToSearch
                );
            } else if (repositoryHelper.isEndsWithRegex(valueWithRegex)) {
                igcSearchCondition = new IGCSearchCondition(
                        igcPropertyToSearch,
                        "like %{0}",
                        igcValueToSearch
                );
            } else if (repositoryHelper.isExactMatchRegex(valueWithRegex)) {
                igcSearchCondition = new IGCSearchCondition(
                        igcPropertyToSearch,
                        "=",
                        igcValueToSearch
                );
            } else {
                throw new FunctionNotSupportedException(IGCOMRSErrorCode.REGEX_NOT_IMPLEMENTED.getMessageDefinition(repositoryName, valueWithRegex),
                        IGCRepositoryHelper.class.getName(),
                        methodName);
            }
        } else if (operator.equals(PropertyComparisonOperator.EQ)) {
            igcSearchCondition = new IGCSearchCondition(
                    igcPropertyToSearch,
                    "=",
                    igcValueToSearch
            );
        } else if (operator.equals(PropertyComparisonOperator.NEQ)) {
            igcSearchCondition = new IGCSearchCondition(
                    igcPropertyToSearch,
                    "<>",
                    igcValueToSearch
            );
        } else {
            throw new FunctionNotSupportedException(IGCOMRSErrorCode.INVALID_SEARCH_COMPARISON.getMessageDefinition(operator.getName(), PrimitiveDefCategory.OM_PRIMITIVE_TYPE_STRING.getName()),
                    IGCRepositoryHelper.class.getName(),
                    methodName);
        }

        return igcSearchCondition;

    }

    /**
     * Adds conditions to the search to ensure we narrow to only the appropriate types (where feasible).
     *
     * @param mapping the mapping information
     * @param matchCriteria the criteria by which we should match during the search
     * @param matchProperties the properties that should be matched during the search
     * @param igcSearchConditionSet the set of IGC search conditions to which to append
     */
    public static void addTypeSpecificConditions(EntityMapping mapping,
                                                 MatchCriteria matchCriteria,
                                                 SearchProperties matchProperties,
                                                 IGCSearchConditionSet igcSearchConditionSet) {
        // Only include type-specific criteria if the matchCriteria is (effectively) 'ALL'
        // - if it is 'ANY' and there are any properties this will include far too many results
        // - if it is 'NONE' then it will exclude all of the types we are actually searching for
        if ( matchCriteria == null
                || matchCriteria.equals(MatchCriteria.ALL)
                || (matchCriteria.equals(MatchCriteria.ANY) && (matchProperties == null || matchProperties.getConditions().size() == 0))) {
            IGCSearchConditionSet typeSpecificConditions = mapping.getIGCSearchCriteria();
            if (typeSpecificConditions.size() > 0) {
                igcSearchConditionSet.addNestedConditionSet(typeSpecificConditions);
                igcSearchConditionSet.setMatchAnyCondition(false);
            }
        }
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
        ItemList<OMRSStub> results = igcRestClient.search(igcSearch);
        OMRSStub stub = null;
        if (results.getPaging().getNumTotal() > 0) {
            if (results.getPaging().getNumTotal() > 1) {
                log.warn("Found multiple stubs for asset, taking only the first: {}", stubName);
            }
            stub = results.getItems().get(0);
        } else {
            log.info("No stub found for asset: {}", stubName);
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
            log.error("Unable to write XML stream: {}", asset, e);
        }

        String stubXML = stringWriter.getBuffer().toString();
        log.debug("Constructed XML for stub: {}", stubName);

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
        log.debug("Constructed XML for stub deletion: {}", stubName);

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
     * @param assetType the type of IGC asset
     * @return Reference - the object including all of its details and relationships
     */
    @SuppressWarnings("unchecked")
    public Reference getFullAssetDetails(String rid, String assetType) {

        Reference fullAsset = null;

        if (assetType != null) {

            if (assetType.equals(IGCRepositoryHelper.DEFAULT_IGC_TYPE)) {
                log.debug("Received 'main_object' as type, looking up basic ref to determine actual type.");
                fullAsset = igcRestClient.getAssetRefById(rid);
                if (fullAsset != null) {
                    assetType = fullAsset.getType();
                }
            }

            if (!assetType.equals(IGCRepositoryHelper.DEFAULT_IGC_TYPE)) {
                // Introspect the full list of properties from the POJO of the asset
                List<String> allProps = igcRestClient.getAllPropertiesForType(assetType);
                if (allProps != null) {

                    // Retrieve all asset properties, via search, as this will allow larger page
                    // retrievals (and therefore be overall more efficient) than going by the GET of the asset
                    fullAsset = igcRestClient.getAssetWithSubsetOfProperties(
                            rid,
                            assetType,
                            allProps,
                            igcRestClient.getDefaultPageSize()
                    );

                    if (fullAsset != null) {

                        // Iterate through all the paged properties and retrieve all pages for each
                        List<String> allPaged = igcRestClient.getPagedRelationshipPropertiesForType(assetType);
                        for (String pagedProperty : allPaged) {
                            Object shouldBeItemList = igcRestClient.getPropertyByName(fullAsset, pagedProperty);
                            if (shouldBeItemList instanceof ItemList) {
                                ItemList<Reference> pagedValue = (ItemList<Reference>) shouldBeItemList;
                                List<Reference> allPages = igcRestClient.getAllPages(pagedProperty, pagedValue);
                                pagedValue.setAllPages(allPages);
                            }
                        }

                        // Set the asset as fully retrieved, so we do not attempt to retrieve parts of it again
                        fullAsset.setFullyRetrieved();

                    }
                } else {
                    log.info("No registered POJO for asset type {} -- returning basic reference.", assetType);
                    fullAsset = igcRestClient.getAssetRefById(rid);
                }
            } else {
                log.info("Object retrieved remained 'main_object' -- returning: {}", fullAsset);
            }

        } else {
            fullAsset = igcRestClient.getAssetRefById(rid);
            if (fullAsset == null) {
                log.info("Unable to retrieve any asset with RID {} -- assume it was deleted.", rid);
            } else {
                log.info("No asset type provided -- returning basic reference.");
            }
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

    /**
     * Check whether the provided candidate is one or multiple references, and if so add it into the provided list.
     *
     * @param igcRestClient connectivity to the IGC environment
     * @param list the list to which to append
     * @param candidate the candidate object to append into the list (if a reference or list of references)
     */
    public static void addReferencesToList(IGCRestClient igcRestClient, List<Reference> list, Object candidate) {
        if (candidate instanceof Reference) {
            Reference reference = (Reference) candidate;
            if (reference.getType() != null) {
                list.add(reference);
            }
        } else if (candidate instanceof ItemList) {
            ItemList<?> references = (ItemList<?>) candidate;
            if (references.hasMorePages()) {
                // Note: this assumes that this method is only ever called as the result of a search (not with property-
                // level paged results)
                list.addAll(igcRestClient.getAllPages(null, references));
            } else {
                list.addAll(references.getItems());
            }
        }
    }

    /**
     * Raise a RepositoryErrorException using the provided parameters.
     * @param errorCode the error code for the exception
     * @param methodName the method raising the exception
     * @param params any additional parameters for the formatting of the error message
     * @throws RepositoryErrorException always
     */
    private void raiseRepositoryErrorException(IGCOMRSErrorCode errorCode, String methodName, String ...params) throws RepositoryErrorException {
        throw new RepositoryErrorException(errorCode.getMessageDefinition(params),
                this.getClass().getName(),
                methodName);
    }

    /**
     * Raise an EntityNotKnownException using the provided parameters.
     * @param methodName the method raising the exception
     * @param params any additional parameters for the formatting of the error message
     * @throws EntityNotKnownException always
     */
    private void raiseEntityNotKnownException(String methodName, String ...params) throws EntityNotKnownException {
        throw new EntityNotKnownException(IGCOMRSErrorCode.ENTITY_NOT_KNOWN.getMessageDefinition(params),
                this.getClass().getName(),
                methodName);
    }

}
