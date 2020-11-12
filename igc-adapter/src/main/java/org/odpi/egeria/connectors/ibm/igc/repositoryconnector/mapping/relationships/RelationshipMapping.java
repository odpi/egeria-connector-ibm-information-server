/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships;

import org.odpi.egeria.connectors.ibm.igc.auditlog.IGCOMRSErrorCode;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestConstants;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.cache.ObjectCache;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.errors.IGCException;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearch;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchCondition;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchConditionSet;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchSorting;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSMetadataCollection;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSRepositoryConnector;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCRepositoryHelper;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.EntityMappingInstance;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.InstanceMapping;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.attributes.AttributeMapping;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.model.IGCEntityGuid;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.model.IGCRelationshipGuid;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.SequencingOrder;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.*;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.search.SearchProperties;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.typedefs.AttributeTypeDefCategory;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.typedefs.RelationshipDef;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.typedefs.TypeDefAttribute;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.repositoryconnector.OMRSRepositoryHelper;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.FunctionNotSupportedException;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.RepositoryErrorException;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.TypeErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Provides the base class for all relationship mappings, as well as base methods for calculating the transformed
 * relationships.
 */
public abstract class RelationshipMapping extends InstanceMapping {

    private static final Logger log = LoggerFactory.getLogger(RelationshipMapping.class);
    public static final String SELF_REFERENCE_SENTINEL = "__SELF__";

    private ProxyMapping one;
    private ProxyMapping two;
    private RelationshipLevelProxyMapping relationshipLevelPM = null;
    private String omrsRelationshipType;
    private OptimalStart optimalStart;
    private ContainedType containedType;

    private List<RelationshipMapping> subtypes;
    private String linkingAssetType;

    private Set<String> mappedOmrsPropertyNames;

    /**
     * The optimal endpoint from which to retrieve the relationship:
     *  - ONE = from ProxyOne
     *  - TWO = from ProxyTwo
     *  - OPPOSITE = from whichever Proxy does not match the entity for which relationships are being retrieved
     *  - CUSTOM = must be custom implemented via a complexRelationshipMappings method
     */
    public enum OptimalStart { ONE, TWO, OPPOSITE, CUSTOM }

    /**
     * The endpoint which is contained within the other (if any):
     * - ONE = object represented by ProxyOne is the child of the object represented by ProxyTwo
     * - TWO = object represented by ProxyTwo is the child of the object represented by ProxyOne
     * - NONE = there is no containment in the relationship (endpoints are peers rather than parent / child)
     */
    public enum ContainedType { ONE, TWO, NONE }

    @Override
    public String toString() {
        return "RelationshipMapping: " +
                "omrsRelationshipType=" +
                omrsRelationshipType +
                ", one={ " +
                one +
                " }, two={ " +
                two +
                " }";
    }

    protected RelationshipMapping(String igcAssetTypeProxyOne,
                                  String igcAssetTypeProxyTwo,
                                  String igcRelationshipPropertyFromOne,
                                  String igcRelationshipPropertyFromTwo,
                                  String omrsRelationshipType,
                                  String omrsRelationshipProxyOneProperty,
                                  String omrsRelationshipProxyTwoProperty) {
        this(
                igcAssetTypeProxyOne,
                igcAssetTypeProxyTwo,
                igcRelationshipPropertyFromOne,
                igcRelationshipPropertyFromTwo,
                omrsRelationshipType,
                omrsRelationshipProxyOneProperty,
                omrsRelationshipProxyTwoProperty,
                null,
                null
        );
    }

    protected RelationshipMapping(String igcAssetTypeProxyOne,
                                  String igcAssetTypeProxyTwo,
                                  String igcRelationshipPropertyFromOne,
                                  String igcRelationshipPropertyFromTwo,
                                  String omrsRelationshipType,
                                  String omrsRelationshipProxyOneProperty,
                                  String omrsRelationshipProxyTwoProperty,
                                  String igcProxyOneRidPrefix,
                                  String igcProxyTwoRidPrefix) {
        this.one = new ProxyMapping(
                igcAssetTypeProxyOne,
                igcRelationshipPropertyFromOne,
                omrsRelationshipProxyOneProperty,
                igcProxyOneRidPrefix
        );
        this.two = new ProxyMapping(
                igcAssetTypeProxyTwo,
                igcRelationshipPropertyFromTwo,
                omrsRelationshipProxyTwoProperty,
                igcProxyTwoRidPrefix
        );
        this.omrsRelationshipType = omrsRelationshipType;
        this.optimalStart = OptimalStart.OPPOSITE;
        this.containedType = ContainedType.NONE;
        this.subtypes = new ArrayList<>();
        this.mappedOmrsPropertyNames = new HashSet<>();
    }

    /**
     * Add the provided property name as one supported by this relationship mapping.
     *
     * @param name the name of the OMRS property supported by the mapping
     */
    void addMappedOmrsProperty(String name) {
        if (name != null) {
            this.mappedOmrsPropertyNames.add(name);
        } else {
            log.warn("Attempted to add null property to mapping -- OMRS.");
        }
    }

    /**
     * Retrieve the set of OMRS properties that are supported by the relationship mapping.
     *
     * @return {@code Set<String>}
     */
    public Set<String> getMappedOmrsPropertyNames() {
        HashSet<String> omrsProperties = new HashSet<>();
        if (mappedOmrsPropertyNames != null) {
            omrsProperties.addAll(mappedOmrsPropertyNames);
        }
        omrsProperties.addAll(getLiteralPropertyMappings());
        return omrsProperties;
    }

    /**
     * Sets a relationship-level IGC asset type (these very rarely exist, only known example is 'classification').
     *
     * @param igcAssetType the name of the IGC asset that represents this relationship in IGC
     */
    void setRelationshipLevelIgcAsset(String igcAssetType, String igcPropertyToOne, String igcPropertyToTwo) {
        this.relationshipLevelPM = new RelationshipLevelProxyMapping(igcAssetType, igcPropertyToOne, igcPropertyToTwo);
    }

    /**
     * Indicates whether this mapping has an IGC asset that represents the relationship directly (true) or not (false).
     *
     * @return boolean
     */
    public boolean hasRelationshipLevelAsset() { return this.relationshipLevelPM != null; }

    /**
     * Retrieve the name of the IGC asset type that represents the relationship itself.
     *
     * @return String
     */
    public String getRelationshipLevelIgcAsset() {
        if (hasRelationshipLevelAsset()) {
            return relationshipLevelPM.getIgcAssetType();
        } else {
            return null;
        }
    }

    /**
     * Define an IGC asset type as linking together the two endpoints of the relationship translated by this mapping.
     *
     * @param igcAssetType the IGC asset type that links together the two endpoints of the relationship
     */
    void setLinkingAssetType(String igcAssetType) { this.linkingAssetType = igcAssetType; }

    /**
     * Indicates whether this mapping needs an IGC asset linking the endpoints (true) or not (false).
     *
     * @return boolean
     */
    public boolean hasLinkingAsset() { return this.linkingAssetType != null; }

    /**
     * Retrieve the name of the asset type needed to link together the two endpoints of the relationship (if any), or null.
     *
     * @return String
     */
    public String getLinkingAssetType() { return this.linkingAssetType; }

    /**
     * By default, return the asset itself. Override this method if the relationship mapping has a relationship-level
     * asset, or other complexity (multi-hops) to translate from a provided asset to the actual asset for the
     * first endpoint of the relationship in IGC.
     *
     * @param relationshipAsset the asset to use to lookup the actual first endpoint in IGC
     * @param igcRestClient REST API connectivity
     * @param cache a cache of information that may already have been retrieved about the provided object
     * @return Reference - the asset to be used for endpoint one of the relationship
     * @throws RepositoryErrorException if any issue interacting with IGC
     */
    public List<Reference> getProxyOneAssetFromAsset(Reference relationshipAsset, IGCRestClient igcRestClient, ObjectCache cache) throws RepositoryErrorException {
        List<Reference> referenceAsList = new ArrayList<>();
        referenceAsList.add(relationshipAsset);
        return referenceAsList;
    }

    /**
     * By default, return the asset itself. Override this method if the relationship mapping has a relationship-level
     * asset, or other complexity (multi-hops) to translate from a provided asset to the actual asset for the
     * second endpoint of the relationship in IGC.
     *
     * @param relationshipAsset the asset to use to lookup the actual second endpoint in IGC
     * @param igcRestClient REST API connectivity
     * @param cache a cache of information that may already have been retrieved about the provided object
     * @return Reference - the asset to be used for endpoint two of the relationship
     * @throws RepositoryErrorException if any issue interacting with IGC
     */
    public List<Reference> getProxyTwoAssetFromAsset(Reference relationshipAsset, IGCRestClient igcRestClient, ObjectCache cache) throws RepositoryErrorException {
        List<Reference> referenceAsList = new ArrayList<>();
        referenceAsList.add(relationshipAsset);
        return referenceAsList;
    }

    /**
     * Retrieve the IGC entity GUID for the first endpoint from the provided relationship GUID.
     *
     * @param igcRepositoryHelper repository helper for the IGC environment
     * @param igcRelationshipGuid the IGC relationship GUID from which to retrieve the first endpoint's entity GUID
     * @return IGCEntityGuid
     */
    public static IGCEntityGuid getProxyOneGuidFromRelationship(IGCRepositoryHelper igcRepositoryHelper,
                                                                IGCRelationshipGuid igcRelationshipGuid) {
        if (igcRelationshipGuid == null || igcRepositoryHelper == null) {
            return null;
        } else {
            return igcRepositoryHelper.getEntityGuid(
                    igcRelationshipGuid.getAssetType1(),
                    igcRelationshipGuid.getGeneratedPrefix1(),
                    igcRelationshipGuid.getRid1());
        }
    }

    /**
     * Retrieve the IGC entity GUID for the second endpoint from the provided relationship GUID.
     *
     * @param igcRepositoryHelper repository helper for the IGC environment
     * @param igcRelationshipGuid the IGC relationship GUID from which to retrieve the second endpoint's entity GUID
     * @return IGCEntityGuid
     */
    public static IGCEntityGuid getProxyTwoGuidFromRelationship(IGCRepositoryHelper igcRepositoryHelper,
                                                                IGCRelationshipGuid igcRelationshipGuid) {
        if (igcRelationshipGuid == null || igcRepositoryHelper == null) {
            return null;
        } else {
            return igcRepositoryHelper.getEntityGuid(
                    igcRelationshipGuid.getAssetType2(),
                    igcRelationshipGuid.getGeneratedPrefix2(),
                    igcRelationshipGuid.getRid2());
        }
    }

    /**
     * Indicates whether this relationship mapping has subtypes (true) or not (false).
     * Subtypes can be used where the same relationship may represent relationships between a number of different
     * IGC objects and each needs to be distinguished to appropriately apply a mapping.
     *
     * @return boolean
     */
    public boolean hasSubTypes() { return !this.subtypes.isEmpty(); }

    /**
     * Adds a subtype to this relationship mapping.
     * Subtypes can be used where the same relationship may represent relationships between a number of different
     * IGC objects and each needs to be distinguished to appropriately apply a mapping.
     *
     * @param subRelationshipMapping the relationship mapping that defines a subtype of this mapping
     */
    void addSubType(RelationshipMapping subRelationshipMapping) {
        this.subtypes.add(subRelationshipMapping);
    }

    /**
     * Retrieve the listing of sub-relationship mappings of this mapping.
     * Subtypes can be used where the same relationship may represent relationships between a number of different
     * IGC objects and each needs to be distinguished to appropriately apply a mapping.
     *
     * @return {@code List<RelationshipMapping>}
     */
    public List<RelationshipMapping> getSubTypes() { return this.subtypes; }

    /**
     * Must be implemented to define how to map the relationships defined by the mapper, if there are any complex
     * custom mappings to be done for relationships. (Simple mappings are handled automatically by the class.)
     *
     * @param igcomrsRepositoryConnector connection to the IGC environment
     * @param relationships list of relationships to which to append
     * @param cache a cache of information that may already have been retrieved about the provided object
     * @param fromIgcObject the entity starting point for the relationship
     * @param toIgcObject the other entity endpoint for the relationship (or null if unknown)
     * @param fromRelationshipElement the starting element number of the relationships to return.
     *                                This is used when retrieving elements
     *                                beyond the first page of results. Zero means start from the first element.
     * @param sequencingOrder Enum defining how the results should be ordered.
     * @param pageSize the maximum number of result classifications that can be returned on this request.  Zero means
     *                 unrestricted return results size.
     * @param userId the userId doing the mapping
     * @throws RepositoryErrorException if any issue interacting with IGC
     */
    public void addMappedOMRSRelationships(IGCOMRSRepositoryConnector igcomrsRepositoryConnector,
                                           List<Relationship> relationships,
                                           ObjectCache cache,
                                           Reference fromIgcObject,
                                           Reference toIgcObject,
                                           int fromRelationshipElement,
                                           SequencingOrder sequencingOrder,
                                           int pageSize,
                                           String userId) throws RepositoryErrorException {
        // By default there are no complex / custom mappings
    }

    /**
     * Indicates whether to include a relationship for the provided IGC object, in case there are any complex overrides
     * that need to be applied.
     *
     * @param igcomrsRepositoryConnector connection to the IGC environment
     * @param cache a cache of information that may already have been retrieved about the provided object
     * @param oneObject the IGC object to consider for inclusion on one end of the relationship
     * @param otherObject the IGC object to consider for inclusion on the other end of the relationship
     * @return boolean
     * @throws RepositoryErrorException if any issue interacting with IGC
     */
    public boolean includeRelationshipForIgcObjects(IGCOMRSRepositoryConnector igcomrsRepositoryConnector,
                                                    ObjectCache cache,
                                                    Reference oneObject,
                                                    Reference otherObject) throws RepositoryErrorException {
        return true;
    }

    /**
     * Retrieve the type of OMRS relationship this mapping handles.
     *
     * @return String
     */
    public String getOmrsRelationshipType() { return this.omrsRelationshipType; }

    /**
     * Set the optimal starting point for traversing this relationship.
     *
     * @param optimalStart the optimal starting point
     */
    void setOptimalStart(OptimalStart optimalStart) { this.optimalStart = optimalStart; }

    /**
     * Get the optimal starting point for traversing this relationship.
     *
     * @return OptimalStart
     */
    private OptimalStart getOptimalStart() { return this.optimalStart; }

    /**
     * This method needs to be overridden to define how to search for a relationship using a property value that has
     * been mapped in a complex way.
     *
     * @param repositoryConnector connector to the OMRS repository
     * @param matchProperties the set of properties against which to match (or null if none)
     * @return {@code List<IGCSearch>} - the search objects by which to find these relationships
     * @throws FunctionNotSupportedException when a regular expression is used for the search that is not supported
     * @throws RepositoryErrorException if any issue interacting with IGC
     */
    public List<IGCSearch> getComplexIGCSearchCriteria(IGCOMRSRepositoryConnector repositoryConnector,
                                                       SearchProperties matchProperties) throws FunctionNotSupportedException, RepositoryErrorException {
        // Nothing to do -- no relationship-level properties by default -- so return the simple search
        if (matchProperties == null || matchProperties.getConditions() == null || matchProperties.getConditions().size() == 0) {
            return getSimpleIGCSearchCriteria();
        } else {
            return buildDefaultComplexSearch(repositoryConnector, matchProperties);
        }
    }

    /**
     * This method needs to be overridden to define how to search for a relationship using a string-based regex match
     * against all of its potential String properties.
     *
     * @param repositoryConnector connector to the OMRS repository
     * @param searchCriteria the regular expression to attempt to match against any string properties
     * @return {@code List<IGCSearch>} - the search object by which to find these relationships
     * @throws FunctionNotSupportedException when a regular expression is used for the search that is not supported
     */
    public List<IGCSearch> getComplexIGCSearchCriteria(IGCOMRSRepositoryConnector repositoryConnector,
                                                       String searchCriteria) throws FunctionNotSupportedException {
        // Nothing to do -- no relationship-level properties by default -- so return the simple search
        if (searchCriteria == null || searchCriteria.equals("")) {
            return getSimpleIGCSearchCriteria();
        } else {
            // No need to check for literal-mapped values, as currently no relationship has a string-based literal value
            return buildDefaultComplexSearch(repositoryConnector, null);
        }
    }

    private List<IGCSearch> buildDefaultComplexSearch(IGCOMRSRepositoryConnector repositoryConnector,
                                                      SearchProperties matchProperties) {

        List<IGCSearch> searches;
        if (matchProperties == null || getAllNoneOrSome(repositoryConnector, matchProperties).equals(SearchFilter.NONE)) {
            searches = new ArrayList<>();
            ProxyMapping pm = getProxyTwoMapping();
            IGCSearch igcSearch = new IGCSearch(pm.getIgcAssetType());
            IGCSearchConditionSet conditions = new IGCSearchConditionSet(IGCRestConstants.getConditionToForceNoSearchResults());
            igcSearch.addConditions(conditions);
            searches.add(igcSearch);
        } else {
            searches = getSimpleIGCSearchCriteria();
        }
        return searches;
    }

    /**
     * Implement this method to define how IGC relationships can be searched.
     *
     * @return {@code List<IGCSearch>} - the search objects by which to find these relationships
     */
    public List<IGCSearch> getSimpleIGCSearchCriteria() {

        // Rather than looking for an optimal start, since ALL related objects are returned the result set size is
        // likely to be the same regardless -- instead always start from TWO and retrieve ONE
        List<IGCSearch> searches = new ArrayList<>();
        ProxyMapping pm = getProxyTwoMapping();
        IGCSearch igcSearch = new IGCSearch(pm.getIgcAssetType());

        List<String> relationshipProperties = pm.getRealIgcRelationshipProperties();

        // However, only retrieve objects when the relationship property itself is not null (that is, where there
        // actually is such a relationship set)
        if (!relationshipProperties.isEmpty()) {
            igcSearch.addProperties(relationshipProperties);
            IGCSearchConditionSet conditions = new IGCSearchConditionSet();
            for (String property : relationshipProperties) {
                IGCSearchCondition condition = new IGCSearchCondition(property, "isNull", true);
                conditions.addCondition(condition);
            }
            conditions.setMatchAnyCondition(true);
            igcSearch.addConditions(conditions);
        }

        searches.add(igcSearch);

        return searches;

    }

    /**
     * Set the child of a parent-child relationship.
     *
     * @param containedType the endpoint to treat as the child of the relationship
     */
    void setContainedType(ContainedType containedType) { this.containedType = containedType; }

    /**
     * Get the child of the parent-child relationship.
     *
     * @return ContainedType
     */
    public ContainedType getContainedType() { return this.containedType; }

    /**
     * Add an alternative IGC property that can be used to traverse the relationship from proxy one to proxy two.
     *
     * @param property the name of the additional IGC relationship property
     */
    void addAlternativePropertyFromOne(String property) { this.one.addAlternativeIgcRelationshipProperty(property); }

    /**
     * Add an alternative IGC property that can be used to traverse the relationship from proxy two to proxy one.
     *
     * @param property the name of the additional IGC relationship property
     */
    void addAlternativePropertyFromTwo(String property) { this.two.addAlternativeIgcRelationshipProperty(property); }

    /**
     * Indicates whether the relationship references the same IGC object (true) or not (false). This is useful for
     * determining if the relationship is actually to a type that will be generated for OMRS, but does not exist as a
     * distinct instance within IGC.
     *
     * @return boolean
     */
    public boolean isSelfReferencing() { return (this.one.isSelfReferencing() || this.two.isSelfReferencing()); }

    /**
     * Retrieves the mapping details for the relationship-level asset (if any, otherwise null).
     *
     * @return RelationshipLevelProxyMapping
     */
    public RelationshipLevelProxyMapping getRelationshipLevelProxyMapping() { return this.relationshipLevelPM; }

    /**
     * Retrieves the mapping details for endpoint 1 (proxy 1) of the OMRS relationship.
     *
     * @return ProxyMapping
     */
    public ProxyMapping getProxyOneMapping() { return this.one; }

    /**
     * Retrieves the mapping detals for endpoint 2 (proxy 2) of the OMRS relationship.
     *
     * @return ProxyMapping
     */
    public ProxyMapping getProxyTwoMapping() { return this.two; }

    /**
     * Indicates whether the same asset / entity type is used on both ends of the relationship (true) or not (false).
     *
     * @return boolean
     */
    public boolean sameTypeOnBothEnds() {
        return one.getIgcAssetType().equals(two.getIgcAssetType());
    }

    /**
     * Indicates whether the same relationship properties are used on both ends of the relationship (true) or not (false).
     *
     * @return boolean
     */
    public boolean samePropertiesOnBothEnds() {
        List<String> pOneProperties = one.getIgcRelationshipProperties();
        List<String> pTwoProperties = two.getIgcRelationshipProperties();
        return new HashSet<>(pOneProperties).equals(new HashSet<>(pTwoProperties));
    }

    /**
     * Retrieve the proxy details for the side of the relationship given by the asset type provided.
     *
     * @param igcAssetType the IGC asset type for which to find the same side of the relationship
     * @return ProxyMapping
     */
    private ProxyMapping getProxyFromType(String igcAssetType) {

        ProxyMapping same = null;

        if (igcAssetType == null) {
            log.error("No asset type provided.");
        } else {
            String simpleType = IGCRestConstants.getAssetTypeForSearch(igcAssetType);
            if (simpleType.equals(one.getIgcAssetType())) {
                same = this.one;
            } else if (simpleType.equals(two.getIgcAssetType())) {
                same = this.two;
            } else if (one.getIgcAssetType().equals(IGCRepositoryHelper.DEFAULT_IGC_TYPE) && !one.excludeIgcAssetType.contains(simpleType)) {
                same = this.one;
            } else if (two.getIgcAssetType().equals(IGCRepositoryHelper.DEFAULT_IGC_TYPE) && !two.excludeIgcAssetType.contains(simpleType)) {
                same = this.two;
            } else {
                log.error("getProxyFromType - Provided asset type does not match either proxy type (or was explicitly excluded): {}", simpleType);
            }
        }

        return same;

    }

    /**
     * Retrieve the proxy details for the other side of the relationship from the IGC asset type provided.
     *
     * @param igcAssetType the IGC asset type for which to find the other side of the relationship
     * @return ProxyMapping
     */
    private ProxyMapping getOtherProxyFromType(String igcAssetType) {

        ProxyMapping other = null;

        if (igcAssetType == null) {
            log.error("No asset type provided.");
        } else {
            String simpleType = IGCRestConstants.getAssetTypeForSearch(igcAssetType);
            if (simpleType.equals(one.getIgcAssetType())) {
                other = this.two;
            } else if (simpleType.equals(two.getIgcAssetType())) {
                other = this.one;
            } else if (one.getIgcAssetType().equals(IGCRepositoryHelper.DEFAULT_IGC_TYPE) && !one.excludeIgcAssetType.contains(simpleType)) {
                other = this.two;
            } else if (two.getIgcAssetType().equals(IGCRepositoryHelper.DEFAULT_IGC_TYPE) && !two.excludeIgcAssetType.contains(simpleType)) {
                other = this.one;
            } else {
                log.error("getOtherProxyFromType - Provided asset type does not match either proxy type (or was explicitly excluded): {}", simpleType);
            }
        }

        return other;

    }

    /**
     * Retrieve the IGC relationship properties that define the relationship for the provided IGC asset type.
     *
     * @param igcAssetType the IGC asset type for which to retrieve the relationship properties
     * @return {@code List<String>}
     */
    public List<String> getIgcRelationshipPropertiesForType(String igcAssetType) {

        Set<String> properties = new TreeSet<>();

        if (igcAssetType == null) {
            log.error("No asset type provided.");
        } else {
            String simpleType = IGCRestConstants.getAssetTypeForSearch(igcAssetType);
            if (sameTypeOnBothEnds() && simpleType.equals(one.getIgcAssetType())) {
                addRealPropertiesToSet(one.getIgcRelationshipProperties(), properties);
                addRealPropertiesToSet(two.getIgcRelationshipProperties(), properties);
            } else if (simpleType.equals(one.getIgcAssetType())) {
                addRealPropertiesToSet(one.getIgcRelationshipProperties(), properties);
            } else if (simpleType.equals(two.getIgcAssetType())) {
                addRealPropertiesToSet(two.getIgcRelationshipProperties(), properties);
            } else if (one.getIgcAssetType().equals(IGCRepositoryHelper.DEFAULT_IGC_TYPE) && !one.excludeIgcAssetType.contains(simpleType)) {
                addRealPropertiesToSet(one.getIgcRelationshipProperties(), properties);
            } else if (two.getIgcAssetType().equals(IGCRepositoryHelper.DEFAULT_IGC_TYPE) && !two.excludeIgcAssetType.contains(simpleType)) {
                addRealPropertiesToSet(two.getIgcRelationshipProperties(), properties);
            } else {
                log.warn("getIgcRelationshipPropertiesForType - Provided asset type does not match either proxy type (or was explicitly excluded): {}", simpleType);
            }
        }

        return new ArrayList<>(properties);

    }

    /**
     * Retrieve the IGC relationship properties that are directly looked up from the asset (without a search), based
     * on the optimal starting point of navigating a relationship. Note that for any custom or "opposite" optimal
     * starting point of a relationship, this method will return an empty list.
     *
     * @param igcAssetType IGC asset type for which to retrieve the relationship properties
     * @return {@code List<String>}
     */
    public List<String> getDirectRelationshipPropertiesForType(String igcAssetType) {

        Set<String> properties = new TreeSet<>();
        if (igcAssetType == null) {
            log.error("No asset type provided.");
        } else {
            String simpleType = IGCRestConstants.getAssetTypeForSearch(igcAssetType);
            if (getOptimalStart().equals(OptimalStart.ONE) && simpleType.equals(one.getIgcAssetType())) {
                addRealPropertiesToSet(one.getIgcRelationshipProperties(), properties);
            } else if (getOptimalStart().equals(OptimalStart.TWO) && simpleType.equals(two.getIgcAssetType())) {
                addRealPropertiesToSet(two.getIgcRelationshipProperties(), properties);
            }
        }
        return new ArrayList<>(properties);

    }

    /**
     * Keep unique properties in the list, and avoid the SELF_REFERENCE_SENTINEL value.
     *
     * @param candidates the candidate set to add to the list of unique, real properties
     * @param realProperties the set of unique, real properties
     */
    private void addRealPropertiesToSet(List<String> candidates, Set<String> realProperties) {
        for (String propertyName : candidates) {
            if (!propertyName.equals(SELF_REFERENCE_SENTINEL) && !propertyName.equals("")) {
                realProperties.add(propertyName);
            }
        }
    }

    /**
     * Class to represent each side of a relationship mapping, keeping the details of that side of the mapping together.
     */
    public class ProxyMapping {

        private String igcAssetType;
        private Set<String> igcRelationshipProperties;
        private String omrsRelationshipProperty;
        private String igcRidPrefix;
        private Set<String> excludeIgcAssetType;

        ProxyMapping(String igcAssetType,
                     String igcRelationshipProperty,
                     String omrsRelationshipProperty,
                     String igcRidPrefix) {

            this.igcAssetType = igcAssetType;
            this.igcRelationshipProperties = new TreeSet<>();
            if (igcRelationshipProperty != null) {
                this.igcRelationshipProperties.add(igcRelationshipProperty);
            }
            this.omrsRelationshipProperty = omrsRelationshipProperty;
            this.igcRidPrefix = igcRidPrefix;
            this.excludeIgcAssetType = new HashSet<>();

        }

        /**
         * Retrieve the type of IGC asset that is expected by this side of the relationship mapping.
         *
         * @return String
         */
        public String getIgcAssetType() { return this.igcAssetType; }

        /**
         * Retrieve the list of IGC relationship properties that can be used from this side of the relationship
         * mapping to traverse to the other side of the relationship. (In most cases this will just be a single
         * relationship in the list.)
         *
         * @return {@code List<String>}
         */
        public List<String> getIgcRelationshipProperties() { return new ArrayList<>(this.igcRelationshipProperties); }

        /**
         * Retrieve the list of IGC relationship properties that can be used from this side of the relationship
         * mapping to traverse to the other side of the relationship. Furthermore, REMOVE any self-referencing
         * relationship properties -- so only actual (real) IGC properties remain.
         *
         * @return {@code List<String>}
         */
        public List<String> getRealIgcRelationshipProperties() {
            List<String> realProperties = new ArrayList<>();
            for (String property : getIgcRelationshipProperties()) {
                if (!property.equals(SELF_REFERENCE_SENTINEL)) {
                    realProperties.add(property);
                }
            }
            return realProperties;
        }

        /**
         * Add an alternative IGC relationship property to this side of the relationship, that can be additionally
         * used to traverse to the other side of the relationship.
         *
         * @param igcRelationshipProperty the name of the additional IGC relationship property
         */
        void addAlternativeIgcRelationshipProperty(String igcRelationshipProperty) {
            if (igcRelationshipProperty != null) {
                this.igcRelationshipProperties.add(igcRelationshipProperty);
            } else {
                log.warn("Attempted to add null property to mapping -- IGC.");
            }
        }

        /**
         * Retrieve the prefix that should be added to the IGC Repository ID (RID) in order to make this side of the
         * relationship point to a generated IGC entity instance (ie. one that does not exist distinctly within IGC).
         * If no such entity is required, this will be null.
         *
         * @return String
         */
        public String getIgcRidPrefix() { return this.igcRidPrefix; }

        /**
         * Indicates whether this side of the relationship is self-referencing (true) or not (false). Generally when
         * the relationship is self-referencing, at least one end of the relationship will require a prefix.
         *
         * @return boolean
         */
        boolean isSelfReferencing() { return this.igcRelationshipProperties.contains(SELF_REFERENCE_SENTINEL); }

        /**
         * When the asset this applies to is a 'main_object', use this method to add any objects that should NOT be
         * included under that umbrella.
         *
         * @param igcAssetType the IGC asset type to exclude from 'main_object' consideration
         */
        void addExcludedIgcAssetType(String igcAssetType) {
            if (igcAssetType != null) {
                this.excludeIgcAssetType.add(igcAssetType);
            } else {
                log.warn("Attempted to add null type to mapping -- IGC.");
            }
        }

        /**
         * Indicates whether this side of the relationship matches the provided IGC asset type: that is, this side of
         * the relationship can be used to relate to the provided IGC asset type.
         *
         * @param igcAssetType the IGC asset type to check this side of the relationship against
         * @return boolean
         */
        public boolean matchesAssetType(String igcAssetType) {
            String simplifiedType = IGCRestConstants.getAssetTypeForSearch(igcAssetType);
            return (
                    this.igcAssetType.equals(simplifiedType)
                    || (this.igcAssetType.equals(IGCRepositoryHelper.DEFAULT_IGC_TYPE) && !this.excludeIgcAssetType.contains(simplifiedType))
                    || (hasLinkingAsset() && simplifiedType.equals(getLinkingAssetType()))
            );
        }

        @Override
        public String toString() {
            return "igcAssetType=" +
                    igcAssetType +
                    ", omrsRelationshipProperty=" +
                    omrsRelationshipProperty +
                    ", igcRidPrefix=" +
                    igcRidPrefix +
                    ", igcRelationshipProperties=" +
                    igcRelationshipProperties;
        }

    }

    /**
     * Capture a relationship-level proxy mapping (rare, but where in IGC there is an actual object that represents
     * the relationship)
     */
    public class RelationshipLevelProxyMapping {

        private String igcAssetType;
        private String igcRelationshipPropertyToEndOne;
        private String igcRelationshipPropertyToEndTwo;

        RelationshipLevelProxyMapping(String igcAssetType,
                                      String igcRelationshipPropertyToEndOne,
                                      String igcRelationshipPropertyToEndTwo) {

            this.igcAssetType = igcAssetType;
            this.igcRelationshipPropertyToEndOne = igcRelationshipPropertyToEndOne;
            this.igcRelationshipPropertyToEndTwo = igcRelationshipPropertyToEndTwo;

        }

        public String getIgcAssetType() { return igcAssetType; }
        public String getIgcRelationshipPropertyToEndOne() { return igcRelationshipPropertyToEndOne; }
        public String getIgcRelationshipPropertyToEndTwo() { return igcRelationshipPropertyToEndTwo; }

    }

    /**
     * Generates a unique relationship GUID based on the provided parameters. The method will check the provided
     * candidate endpoints against the mapping provided to ensure that they are appropriately matched to endpoint 1
     * and endpoint 2 of the OMRS relationship. (Therefore endOne and endTwo may not actually be proxyOne and proxyTwo
     * of the OMRS relationship, but could be reversed.)
     * <br><br>
     * The intention of the method is to guarantee a unique, consistent GUID irrespective of the direction in which
     * the relationship was traversed. For example: an IGC 'parent_category' relationship from B to A for a
     * CategoryHierarchyLink should result in the same GUID as an IGC 'subcategories' relationship from A to B.
     *
     * @param igcRepositoryHelper the helper connected to the IGC environment
     * @param relationshipMapping the relationship mapping defining how an IGC relationship maps to an OMRS relationship
     * @param endOne the candidate to consider for endpoint 1 of the relationship
     * @param endTwo the candidate to consider for endpoint 2 of the relationship
     * @param igcPropertyName the name of the IGC property for which the relationship is being generated
     * @param relationshipLevelRid the relationship-level RID (if any) within IGC (these are very rare)
     * @return IGCRelationshipGuid - the unique GUID for the relationship
     */
    public static IGCRelationshipGuid getRelationshipGUID(IGCRepositoryHelper igcRepositoryHelper,
                                                          RelationshipMapping relationshipMapping,
                                                          Reference endOne,
                                                          Reference endTwo,
                                                          String igcPropertyName,
                                                          String relationshipLevelRid) {
        return getRelationshipGUID(
                igcRepositoryHelper,
                relationshipMapping,
                endOne,
                endTwo,
                igcPropertyName,
                relationshipLevelRid,
                false
        );
    }

    /**
     * Generates a unique relationship GUID based on the provided parameters. The method will check the provided
     * candidate endpoints against the mapping provided to ensure that they are appropriately matched to endpoint 1
     * and endpoint 2 of the OMRS relationship. (Therefore endOne and endTwo may not actually be proxyOne and proxyTwo
     * of the OMRS relationship, but could be reversed.) UNLESS the proxyOrderKnown is true, in which case the method
     * will take endOne as proxyOne and endTwo as proxyTwo and not attempt any detection.
     * <br><br>
     * The intention of the method is to guarantee a unique, consistent GUID irrespective of the direction in which
     * the relationship was traversed. For example: an IGC 'parent_category' relationship from B to A for a
     * CategoryHierarchyLink should result in the same GUID as an IGC 'subcategories' relationship from A to B.
     *
     * @param igcRepositoryHelper the helper connected to the IGC environment
     * @param relationshipMapping the relationship mapping defining how an IGC relationship maps to an OMRS relationship
     * @param endOne the candidate to consider for endpoint 1 of the relationship
     * @param endTwo the candidate to consider for endpoint 2 of the relationship
     * @param igcPropertyName the name of the IGC property for which the relationship is being generated
     * @param relationshipLevelRid the relationship-level RID (if any) within IGC (these are very rare)
     * @param proxyOrderKnown should be true iff the provided candidate proxies are known to be in the correct order
     * @return IGCRelationshipGuid - the unique GUID for the relationship (or null if it could not be determined)
     */
    public static IGCRelationshipGuid getRelationshipGUID(IGCRepositoryHelper igcRepositoryHelper,
                                                          RelationshipMapping relationshipMapping,
                                                          Reference endOne,
                                                          Reference endTwo,
                                                          String igcPropertyName,
                                                          String relationshipLevelRid,
                                                          boolean proxyOrderKnown) {

        String omrsRelationshipName = relationshipMapping.getOmrsRelationshipType();
        // Lookup types via this helper function, to translate any alias types (eg. host_(engine) and host)

        IGCRelationshipGuid igcRelationshipGuid = null;

        // If we are provided a relationship-level RID, short-circuit the entire rest of this process
        if (relationshipLevelRid != null) {
            log.debug("Calculating relationship GUID for relationship-level RID {} (with mapper: {})", relationshipLevelRid, relationshipMapping.getClass().getName());
            String relationshipLevelType = relationshipMapping.getRelationshipLevelIgcAsset();
            igcRelationshipGuid = igcRepositoryHelper.getRelationshipGuid(
                    relationshipLevelType,
                    relationshipLevelType,
                    null,
                    null,
                    relationshipLevelRid,
                    relationshipLevelRid,
                    omrsRelationshipName
            );
        } else if (endOne != null && endTwo != null) {

            String endOneType = IGCRestConstants.getAssetTypeForSearch(endOne.getType());
            String endTwoType = IGCRestConstants.getAssetTypeForSearch(endTwo.getType());

            log.debug("Calculating relationship GUID from {} to {} via {} for {} (with mapper: {})", endOneType, endTwoType, igcPropertyName, omrsRelationshipName, relationshipMapping.getClass().getName());

            // If the relationship mapping includes a relationship-level asset, check if either provided endpoint is one
            // (and if so, setup the relationshipLevelRid based on its ID)
            String relationshipLevelType = null;
            if (relationshipMapping.hasRelationshipLevelAsset()) {
                relationshipLevelType = relationshipMapping.getRelationshipLevelIgcAsset();
                if (endOneType.equals(relationshipLevelType)) {
                    relationshipLevelRid = endOne.getId();
                } else if (endTwoType.equals(relationshipLevelType)) {
                    relationshipLevelRid = endTwo.getId();
                }
            }

            ProxyMapping pmOne = relationshipMapping.getProxyOneMapping();
            ProxyMapping pmTwo = relationshipMapping.getProxyTwoMapping();
            List<String> pmOneProperties = pmOne.getIgcRelationshipProperties();
            List<String> pmTwoProperties = pmTwo.getIgcRelationshipProperties();

            String proxyOneRid = null;
            String proxyTwoRid = null;

            if (proxyOrderKnown) {
                proxyOneRid = endOne.getId();
                proxyTwoRid = endTwo.getId();
            } else if (igcPropertyName != null && igcPropertyName.equals(SELF_REFERENCE_SENTINEL)) {
                // When self-referencing, it should be the same entity on both sides, but we need to
                // prefix the correct RID based on where the relationship mapping tells us it belongs
                // (ie. ordering IS important, unlike next conditional)
                // (the actual prefixing is done further below, for non-self-referencing as well)
                proxyOneRid = endOne.getId();
                proxyTwoRid = endTwo.getId();
                if (pmOne.getIgcRidPrefix() == null && pmTwo.getIgcRidPrefix() == null) {
                    log.warn("Self-referencing relationship expected, but no prefix found for relationship {} from {} to {} via {}", omrsRelationshipName, proxyOneRid, proxyTwoRid, igcPropertyName);
                }
                if (!proxyOneRid.equals(proxyTwoRid)) {
                    log.warn("Self-referencing relationship expected for {}, but RIDs of ends do not match: {} and {}", omrsRelationshipName, proxyOneRid, proxyTwoRid);
                }
            } else if (relationshipMapping.sameTypeOnBothEnds()
                    && pmOne.matchesAssetType(endOneType)) {
                if (relationshipMapping.samePropertiesOnBothEnds()) {
                    // If both the types and property names of both ends of the mapping are the same (eg. synonyms and
                    // translations on terms), then only option is to sort the RIDs themselves to give consistency
                    String endOneRid = endOne.getId();
                    String endTwoRid = endTwo.getId();
                    log.debug(" ... same types, same properties: alphabetically sorting RIDs.");
                    if (endOneRid.compareTo(endTwoRid) > 0) {
                        proxyOneRid = endOneRid;
                        proxyTwoRid = endTwoRid;
                    } else {
                        proxyOneRid = endTwoRid;
                        proxyTwoRid = endOneRid;
                    }
                } else {
                    // Otherwise if only the types are the same on both ends, the property is key to determining which
                    // end is which, and also relies on the direction in which they were retrieved
                    if (relationshipMapping.getOptimalStart().equals(OptimalStart.OPPOSITE)) {
                        if (pmOneProperties.contains(igcPropertyName)) {
                            log.debug(" ... same types, opposite lookup, property matches one: reversing RIDs.");
                            proxyOneRid = endTwo.getId();
                            proxyTwoRid = endOne.getId();
                        } else if (pmTwoProperties.contains(igcPropertyName)) {
                            log.debug(" ... same types, opposite lookup, property matches two: keeping RID direction.");
                            proxyOneRid = endOne.getId();
                            proxyTwoRid = endTwo.getId();
                        }
                    } else {
                        if (pmOneProperties.contains(igcPropertyName)) {
                            log.debug(" ... same types, direct lookup, property matches one: keeping RID direction.");
                            proxyOneRid = endOne.getId();
                            proxyTwoRid = endTwo.getId();
                        } else if (pmTwoProperties.contains(igcPropertyName)) {
                            log.debug(" ... same types, direct lookup, property matches two: reversing RIDs.");
                            proxyOneRid = endTwo.getId();
                            proxyTwoRid = endOne.getId();
                        }
                    }
                }
            } else if (pmOne.matchesAssetType(endOneType)
                    && (pmOneProperties.contains(igcPropertyName) || pmTwoProperties.contains(igcPropertyName))
                    && pmTwo.matchesAssetType(endTwoType)) {
                // Otherwise if one aligns with one and two aligns with two, and property appears somewhere, go with those
                // (this should also apply when the relationship is self-referencing)
                log.debug(" ... one matches one, two matches two: keeping RID direction.");
                proxyOneRid = endOne.getId();
                proxyTwoRid = endTwo.getId();
            } else if (pmTwo.matchesAssetType(endOneType)
                    && (pmOneProperties.contains(igcPropertyName) || pmTwoProperties.contains(igcPropertyName))
                    && pmOne.matchesAssetType(endTwoType)) {
                // Or if two aligns with one and one aligns with two, and property appears somewhere, reverse them
                log.debug(" ... two matches one, one matches two: reversing RIDs.");
                proxyOneRid = endTwo.getId();
                proxyTwoRid = endOne.getId();
                // We also need to reverse the types
                String tempType = endOneType;
                endOneType = endTwoType;
                endTwoType = tempType;
            } else if (relationshipLevelRid == null) {
                // Otherwise indicate something appears to be wrong
                log.error("Unable to find matching ends for relationship {} from {} to {} via {}", omrsRelationshipName, endOne.getId(), endTwo.getId(), igcPropertyName);
            }

            String proxyOnePrefix = pmOne.getIgcRidPrefix();
            String proxyTwoPrefix = pmTwo.getIgcRidPrefix();

            if (relationshipLevelRid != null) {
                igcRelationshipGuid = igcRepositoryHelper.getRelationshipGuid(
                        relationshipLevelType,
                        relationshipLevelType,
                        null,
                        null,
                        relationshipLevelRid,
                        relationshipLevelRid,
                        omrsRelationshipName
                );
            } else if (proxyOneRid != null && proxyTwoRid != null) {
                igcRelationshipGuid = igcRepositoryHelper.getRelationshipGuid(
                        endOneType,
                        endTwoType,
                        proxyOnePrefix,
                        proxyTwoPrefix,
                        proxyOneRid,
                        proxyTwoRid,
                        omrsRelationshipName
                );
            } else {
                log.error("One or both ends of the relationship {} between {} and {} cannot be resolved -- skipping the relationship.", omrsRelationshipName, endOneType, endTwoType);
            }
        }

        return igcRelationshipGuid;

    }

    /**
     * Retrieves an EntityProxy object for the provided IGC object.
     *
     * @param igcomrsRepositoryConnector OMRS connector to the IBM IGC repository
     * @param cache a cache of information that may already have been retrieved about the provided object
     * @param igcObj the IGC object for which to retrieve an EntityProxy
     * @param userId the user through which to retrieve the EntityProxy (unused)
     * @param ridPrefix any prefix required on the object's ID to make it unique
     * @return EntityProxy
     * @throws RepositoryErrorException on any issue interacting with IGC
     */
    private static EntityProxy getEntityProxyForObject(IGCOMRSRepositoryConnector igcomrsRepositoryConnector,
                                                       ObjectCache cache,
                                                       Reference igcObj,
                                                       String userId,
                                                       String ridPrefix) throws RepositoryErrorException {

        final String methodName = "getEntityProxyForObject";

        IGCRestClient igcRestClient = igcomrsRepositoryConnector.getIGCRestClient();
        String igcType = igcObj.getType();

        EntityProxy entityProxy = null;

        if (igcType != null) {

            IGCOMRSMetadataCollection igcomrsMetadataCollection = (IGCOMRSMetadataCollection) igcomrsRepositoryConnector.getMetadataCollection();
            IGCRepositoryHelper igcRepositoryHelper = igcomrsMetadataCollection.getIgcRepositoryHelper();
            EntityMappingInstance entityMap = igcRepositoryHelper.getMappingInstanceForParameters(
                    cache,
                    igcObj.getType(),
                    igcObj.getId(),
                    ridPrefix,
                    userId);

            if (entityMap != null) {

                // Construct 'qualifiedName' from the Identity of the object
                try {
                    String identity = igcObj.getIdentity(igcRestClient, cache).toString();
                    if (ridPrefix != null) {
                        identity = IGCRepositoryHelper.getQualifiedNameForGeneratedEntity(ridPrefix, identity);
                    }

                    InstanceProperties uniqueProperties = igcomrsRepositoryConnector.getRepositoryHelper().addStringPropertyToInstance(
                            igcomrsRepositoryConnector.getRepositoryName(),
                            null,
                            "qualifiedName",
                            identity,
                            methodName
                    );

                    entityProxy = igcomrsRepositoryConnector.getRepositoryHelper().getNewEntityProxy(
                            igcomrsRepositoryConnector.getRepositoryName(),
                            igcomrsRepositoryConnector.getMetadataCollectionId(),
                            InstanceProvenanceType.LOCAL_COHORT,
                            userId,
                            entityMap.getMapping().getOmrsTypeDefName(),
                            uniqueProperties,
                            null
                    );
                    IGCEntityGuid igcEntityGuid;
                    if (ridPrefix != null) {
                        igcEntityGuid = igcRepositoryHelper.getEntityGuid(igcObj.getType(), ridPrefix, igcObj.getId());
                    } else {
                        igcEntityGuid = igcRepositoryHelper.getEntityGuid(igcObj.getType(), null, igcObj.getId());
                    }
                    entityProxy.setGUID(igcEntityGuid.toString());

                    if (igcRestClient.hasModificationDetails(igcObj.getType())) {
                        Reference withModDetails = igcRestClient.getModificationDetails(igcObj, cache);
                        InstanceMapping.setupInstanceModDetails(entityProxy,
                                withModDetails.getCreatedBy(),
                                withModDetails.getCreatedOn(),
                                withModDetails.getModifiedBy(),
                                withModDetails.getModifiedOn());
                    }

                } catch (TypeErrorException e) {
                    log.error("Unable to create new EntityProxy.", e);
                } catch (IGCException e) {
                    raiseRepositoryErrorException(IGCOMRSErrorCode.UNKNOWN_RUNTIME_ERROR, methodName, e);
                }

            } else {
                log.error("Unable to find mapper for IGC object type '{}' with prefix '{}', cannot setup EntityProxy for {}", igcType, ridPrefix, igcObj.getId());
            }

        } else {
            log.error("Unable to find type for provided IGC object: {}", igcObj);
        }

        return entityProxy;

    }

    /**
     * Utility function that actually does the Relationship object setup and addition to 'relationships' member.
     *
     * @param igcomrsRepositoryConnector connectivity to an IGC environment
     * @param relationships the list of relationships to append to
     * @param mappings the mappings to use for retrieving the relationships
     * @param cache a cache of information that may already have been retrieved about the provided object
     * @param relationshipTypeGUID String GUID of the the type of relationship required (null for all).
     * @param fromIgcObject the IGC object that is the source of the relationships
     * @param fromRelationshipElement the starting element number of the relationships to return.
     *                                This is used when retrieving elements
     *                                beyond the first page of results. Zero means start from the first element.
     * @param sequencingOrder Enum defining how the results should be ordered.
     * @param pageSize the maximum number of result classifications that can be returned on this request.  Zero means
     *                 unrestricted return results size.
     * @param userId the user retrieving the mapped relationships
     * @throws RepositoryErrorException if any issues interacting with IGC
     */
    public static void getMappedRelationships(IGCOMRSRepositoryConnector igcomrsRepositoryConnector,
                                              List<Relationship> relationships,
                                              List<RelationshipMapping> mappings,
                                              ObjectCache cache,
                                              String relationshipTypeGUID,
                                              Reference fromIgcObject,
                                              int fromRelationshipElement,
                                              SequencingOrder sequencingOrder,
                                              int pageSize,
                                              String userId) throws RepositoryErrorException {
        getMappedRelationships(
                igcomrsRepositoryConnector,
                relationships,
                mappings,
                cache,
                relationshipTypeGUID,
                fromIgcObject,
                null,
                fromRelationshipElement,
                sequencingOrder,
                pageSize,
                userId
        );
    }

    /**
     * Utility function that actually does the Relationship object setup and addition to 'relationships' member.
     *
     * @param igcomrsRepositoryConnector connectivity to an IGC environment
     * @param relationships the list of relationships to append to
     * @param mappings the mappings to use for retrieving the relationships
     * @param cache a cache of information that may already have been retrieved about the provided object
     * @param relationshipTypeGUID String GUID of the the type of relationship required (null for all).
     * @param fromIgcObject the IGC object that is the source of the relationships
     * @param toIgcObject the IGC object that is the target of the relationship (or null if not known).
     * @param userId the user retrieving the mapped relationships
     * @throws RepositoryErrorException if any issues interacting with IGC
     */
    public static void getMappedRelationships(IGCOMRSRepositoryConnector igcomrsRepositoryConnector,
                                              List<Relationship> relationships,
                                              List<RelationshipMapping> mappings,
                                              ObjectCache cache,
                                              String relationshipTypeGUID,
                                              Reference fromIgcObject,
                                              Reference toIgcObject,
                                              String userId) throws RepositoryErrorException {
        getMappedRelationships(igcomrsRepositoryConnector,
                relationships,
                mappings,
                cache,
                relationshipTypeGUID,
                fromIgcObject,
                toIgcObject,
                0,
                null,
                100,
                userId);
    }

    /**
     * Utility function that actually does the Relationship object setup and addition to 'relationships' member.
     *
     * @param igcomrsRepositoryConnector connectivity to an IGC environment
     * @param relationships the list of relationships to append to
     * @param mappings the mappings to use for retrieving the relationships
     * @param cache a cache of information that may already have been retrieved about the provided object
     * @param relationshipTypeGUID String GUID of the the type of relationship required (null for all).
     * @param fromIgcObject the IGC object that is the source of the relationships
     * @param toIgcObject the IGC object that is the target of the relationship (or null if not known).
     * @param fromRelationshipElement the starting element number of the relationships to return.
     *                                This is used when retrieving elements
     *                                beyond the first page of results. Zero means start from the first element.
     * @param sequencingOrder Enum defining how the results should be ordered.
     * @param pageSize the maximum number of result classifications that can be returned on this request.  Zero means
     *                 unrestricted return results size.
     * @param userId the user retrieving the mapped relationships
     * @throws RepositoryErrorException if any issues interacting with IGC
     */
    public static void getMappedRelationships(IGCOMRSRepositoryConnector igcomrsRepositoryConnector,
                                              List<Relationship> relationships,
                                              List<RelationshipMapping> mappings,
                                              ObjectCache cache,
                                              String relationshipTypeGUID,
                                              Reference fromIgcObject,
                                              Reference toIgcObject,
                                              int fromRelationshipElement,
                                              SequencingOrder sequencingOrder,
                                              int pageSize,
                                              String userId) throws RepositoryErrorException {

        // Iterate through the provided mappings to create a number of OMRS relationships
        for (RelationshipMapping mapping : mappings) {

            RelationshipDef omrsRelationshipDef = (RelationshipDef) igcomrsRepositoryConnector.getRepositoryHelper().getTypeDefByName(
                    igcomrsRepositoryConnector.getRepositoryName(),
                    mapping.getOmrsRelationshipType()
            );

            // Only continue with a given relationship if we are mapping all relationships or it matches the
            // GUID of the relationship type we are mapping
            if (relationshipTypeGUID == null || relationshipTypeGUID.equals(omrsRelationshipDef.getGUID())) {

                RelationshipMapping.OptimalStart optimalStart = mapping.getOptimalStart();
                String fromAssetType = fromIgcObject.getType();
                ProxyMapping pmOne = mapping.getProxyOneMapping();
                ProxyMapping pmTwo = mapping.getProxyTwoMapping();

                if (mapping.isSelfReferencing()) {
                    if (mapping.includeRelationshipForIgcObjects(igcomrsRepositoryConnector, cache, fromIgcObject, fromIgcObject)) {
                        addSelfReferencingRelationship(igcomrsRepositoryConnector, mapping, relationships, cache, fromIgcObject, userId);
                    }
                } else if (!optimalStart.equals(RelationshipMapping.OptimalStart.CUSTOM)) {
                    if (fromIgcObject.isFullyRetrieved()
                            || (optimalStart.equals(OptimalStart.ONE) && pmOne.matchesAssetType(fromAssetType) )
                            || (optimalStart.equals(OptimalStart.TWO) && pmTwo.matchesAssetType(fromAssetType)) ) {
                        addDirectRelationship(igcomrsRepositoryConnector,
                                mapping,
                                relationships,
                                cache,
                                fromIgcObject,
                                toIgcObject,
                                fromRelationshipElement,
                                sequencingOrder,
                                pageSize,
                                userId);
                    } else if (optimalStart.equals(OptimalStart.OPPOSITE)
                            || (optimalStart.equals(OptimalStart.TWO) && pmOne.matchesAssetType(fromAssetType))
                            || (optimalStart.equals(OptimalStart.ONE) && pmTwo.matchesAssetType(fromAssetType)) ) {
                        addInvertedRelationship(igcomrsRepositoryConnector,
                                mapping,
                                relationships,
                                cache,
                                fromIgcObject,
                                toIgcObject,
                                fromRelationshipElement,
                                sequencingOrder,
                                pageSize,
                                userId);
                    } else {
                        log.warn("Ran out of options for finding the relationship: {}", omrsRelationshipDef.getName());
                    }
                }

                // Then call any complex / custom relationship mappings defined
                mapping.addMappedOMRSRelationships(
                        igcomrsRepositoryConnector,
                        relationships,
                        cache,
                        fromIgcObject,
                        toIgcObject,
                        fromRelationshipElement,
                        sequencingOrder,
                        pageSize,
                        userId
                );

            }

        }

    }

    /**
     * Adds a self-referencing relationship based on the provided mapping information.
     *
     * @param mapping the mapping for the self-referencing relationship
     * @param relationships the list of relationships to append to
     * @param cache a cache of information that may already have been retrieved about the provided object
     * @param fromIgcObject the IGC object that is the source (and target) of the self-referencing relationship
     * @param userId the user retrieving the mapped relationship
     */
    private static void addSelfReferencingRelationship(IGCOMRSRepositoryConnector igcomrsRepositoryConnector,
                                                       RelationshipMapping mapping,
                                                       List<Relationship> relationships,
                                                       ObjectCache cache,
                                                       Reference fromIgcObject,
                                                       String userId) {
        try {
            Relationship relationship = getMappedRelationship(
                    igcomrsRepositoryConnector,
                    mapping,
                    cache,
                    fromIgcObject,
                    fromIgcObject,
                    RelationshipMapping.SELF_REFERENCE_SENTINEL,
                    userId
            );
            log.debug("addSelfReferencingRelationship - adding relationship: {}", relationship.getGUID());
            relationships.add(relationship);
        } catch (RepositoryErrorException e) {
            log.error("Unable to add self-referencing relationship for: {} of type {}", fromIgcObject.getName(), fromIgcObject.getType(), e);
        }
    }

    /**
     * Adds a relationship that is straightforward to lookup directly from the originating IGC asset.
     *
     * @param igcomrsRepositoryConnector connectivity to the IGC repository
     * @param mapping the mapping for the direct relationship
     * @param relationships the list of relationships to append to
     * @param cache a cache of information that may already have been retrieved about the provided object
     * @param fromIgcObject the IGC object that is the source of the direct relationship
     * @param toIgcObject the IGC object that is the target of the direct relationship (if known, otherwise null)
     * @param fromRelationshipElement the starting element number of the relationships to return.
     *                                This is used when retrieving elements
     *                                beyond the first page of results. Zero means start from the first element.
     * @param sequencingOrder Enum defining how the results should be ordered.
     * @param pageSize the maximum number of result classifications that can be returned on this request.  Zero means
     *                 unrestricted return results size.
     * @param userId the user retrieving the mapped relationship
     * @throws RepositoryErrorException if any issues interacting with IGC
     */
    @SuppressWarnings("unchecked")
    private static void addDirectRelationship(IGCOMRSRepositoryConnector igcomrsRepositoryConnector,
                                              RelationshipMapping mapping,
                                              List<Relationship> relationships,
                                              ObjectCache cache,
                                              Reference fromIgcObject,
                                              Reference toIgcObject,
                                              int fromRelationshipElement,
                                              SequencingOrder sequencingOrder,
                                              int pageSize,
                                              String userId) throws RepositoryErrorException {

        final String methodName = "addDirectRelationship";
        IGCRestClient igcRestClient = igcomrsRepositoryConnector.getIGCRestClient();

        if (toIgcObject != null) {
            addSingleMappedRelationshipWithKnownOrder(
                    igcomrsRepositoryConnector,
                    mapping,
                    relationships,
                    cache,
                    fromIgcObject,
                    toIgcObject,
                    userId
            );
        } else {
            // If we already have all info about the entity, optimal path to retrieve relationships is to use
            // the ones that are already in-memory -- though if it is also not optimal (or possible) to retrieve
            // from a search (see below) we must also resort to this property-based retrieval
            for (String igcRelationshipName : mapping.getIgcRelationshipPropertiesForType(fromIgcObject.getType())) {

                try {
                    Object directRelationships = igcRestClient.getPropertyByName(fromIgcObject, igcRelationshipName);

                    // Handle single instance relationship one way
                    if (directRelationships instanceof Reference) {

                        Reference singleRelationship = (Reference) directRelationships;
                        if (mapping.includeRelationshipForIgcObjects(igcomrsRepositoryConnector, cache, fromIgcObject, singleRelationship)) {
                            addSingleMappedRelationship(
                                    igcomrsRepositoryConnector,
                                    mapping,
                                    relationships,
                                    cache,
                                    fromIgcObject,
                                    singleRelationship,
                                    igcRelationshipName,
                                    userId
                            );
                        }

                    } else if (directRelationships instanceof ItemList) { // and list of relationships another

                        // In this scenario we must retrieve all pages, as we cannot sort the results any other way
                        ItemList<Reference> allRelationships = (ItemList<Reference>) directRelationships;
                        List<Reference> allPages = igcomrsRepositoryConnector.getIGCRestClient().getAllPages(igcRelationshipName, allRelationships);
                        allRelationships.setAllPages(allPages);
                        if (sequencingOrder != null) {
                            // Sort the results before passing along to the next operation
                            switch (sequencingOrder) {
                                case GUID:
                                    allRelationships.getItems().sort(Comparator.comparing(Reference::getId));
                                    break;
                                case CREATION_DATE_OLDEST:
                                    allRelationships.getItems().sort(Comparator.comparing(Reference::getCreatedOn));
                                    break;
                                case CREATION_DATE_RECENT:
                                    allRelationships.getItems().sort(Comparator.comparing(Reference::getCreatedOn).reversed());
                                    break;
                                case LAST_UPDATE_OLDEST:
                                    allRelationships.getItems().sort(Comparator.comparing(Reference::getModifiedOn));
                                    break;
                                case LAST_UPDATE_RECENT:
                                    allRelationships.getItems().sort(Comparator.comparing(Reference::getModifiedOn).reversed());
                                    break;
                                default:
                                    log.warn("Sorting not implemented for the requested ordering: {}", sequencingOrder);
                                    break;
                            }
                        }
                        addListOfMappedRelationships(
                                igcomrsRepositoryConnector,
                                mapping,
                                relationships,
                                cache,
                                fromIgcObject,
                                allRelationships,
                                igcRelationshipName,
                                fromRelationshipElement,
                                pageSize,
                                userId
                        );

                    } else {
                        log.debug(" ... skipping relationship {}, either empty or neither reference or list: {}", igcRelationshipName, directRelationships);
                    }

                } catch (IGCException e) {
                    raiseRepositoryErrorException(IGCOMRSErrorCode.UNKNOWN_RUNTIME_ERROR, methodName, e);
                }

            }
        }

    }

    /**
     * Adds a relationship that is best looked-up in reverse (from target back to source asset).
     *
     * @param igcomrsRepositoryConnector connectivity to the IGC repository
     * @param mapping the mapping for the inverted relationship
     * @param relationships the list of relationships to append to
     * @param cache a cache of information that may already have been retrieved about the provided object
     * @param fromIgcObject the IGC object that is the source of the inverted relationship (or really the target)
     * @param toIgcObject the IGC object that is the target of the inverted relationship (if known, otherwise null)
     * @param fromRelationshipElement the starting element number of the relationships to return.
     *                                This is used when retrieving elements
     *                                beyond the first page of results. Zero means start from the first element.
     * @param sequencingOrder Enum defining how the results should be ordered.
     * @param pageSize the maximum number of result classifications that can be returned on this request.  Zero means
     *                 unrestricted return results size.
     * @param userId the user retrieving the mapped relationship
     * @throws RepositoryErrorException if any issues interacting with IGC
     */
    private static void addInvertedRelationship(IGCOMRSRepositoryConnector igcomrsRepositoryConnector,
                                                RelationshipMapping mapping,
                                                List<Relationship> relationships,
                                                ObjectCache cache,
                                                Reference fromIgcObject,
                                                Reference toIgcObject,
                                                int fromRelationshipElement,
                                                SequencingOrder sequencingOrder,
                                                int pageSize,
                                                String userId) throws RepositoryErrorException {

        String assetType = fromIgcObject.getType();

        log.debug("Adding inverted relationship for mapping: {}", mapping.getClass().getCanonicalName());

        if (toIgcObject != null) {
            addSingleMappedRelationshipWithKnownOrder(
                    igcomrsRepositoryConnector,
                    mapping,
                    relationships,
                    cache,
                    fromIgcObject,
                    toIgcObject,
                    userId
            );
        } else {

            if (mapping.sameTypeOnBothEnds()) {

                // If we have a hierarchical relationship, we need to run searches across both
                // properties and add both sets of relationships
                List<String> igcProperties = mapping.getIgcRelationshipPropertiesForType(assetType);
                for (String igcRelationshipName : igcProperties) {
                    IGCSearchCondition condition = new IGCSearchCondition(igcRelationshipName, "=", fromIgcObject.getId());
                    IGCSearchConditionSet igcSearchConditionSet = new IGCSearchConditionSet(condition);
                    addSearchResultsToRelationships(
                            igcomrsRepositoryConnector,
                            mapping,
                            relationships,
                            cache,
                            fromIgcObject,
                            igcSearchConditionSet,
                            assetType,
                            igcRelationshipName,
                            fromRelationshipElement,
                            sequencingOrder,
                            pageSize,
                            userId
                    );
                }

            } else {

                // Otherwise, use the optimal retrieval for the relationship (a search that will batch-retrieve _context)
                RelationshipMapping.ProxyMapping otherSide = mapping.getOtherProxyFromType(assetType);
                log.debug(" ... found other proxy: {} with prefix {}", otherSide == null ? "(null)" : otherSide.getIgcAssetType(), otherSide == null ? "(null)" : otherSide.getIgcRidPrefix());
                RelationshipMapping.ProxyMapping thisSide = mapping.getProxyFromType(assetType);
                log.debug(" ... found this proxy: {} with prefix {}", thisSide == null ? "(null)" : thisSide.getIgcAssetType(), thisSide == null ? "(null)" : thisSide.getIgcRidPrefix());

                String anIgcRelationshipProperty = null;
                IGCSearchConditionSet igcSearchConditionSet = new IGCSearchConditionSet();
                igcSearchConditionSet.setMatchAnyCondition(true);
                if (otherSide != null) {
                    for (String igcRelationshipName : otherSide.getIgcRelationshipProperties()) {
                        IGCSearchCondition condition = new IGCSearchCondition(igcRelationshipName, "=", fromIgcObject.getId());
                        igcSearchConditionSet.addCondition(condition);
                        anIgcRelationshipProperty = igcRelationshipName;
                    }
                    String sourceAssetType = otherSide.getIgcAssetType();
                    addSearchResultsToRelationships(
                            igcomrsRepositoryConnector,
                            mapping,
                            relationships,
                            cache,
                            fromIgcObject,
                            igcSearchConditionSet,
                            sourceAssetType,
                            anIgcRelationshipProperty,
                            fromRelationshipElement,
                            sequencingOrder,
                            pageSize,
                            userId
                    );
                } else {
                    log.error("Unable to determine other side of relationship -- cannot process inverted relationship.");
                }

            }
        }

    }

    /**
     * Executes a search against the IGC repository based on the provided criteria, and adds all results to the list
     * of relationships.
     *
     * @param igcomrsRepositoryConnector connectivity to the IGC repository
     * @param mapping the mapping for the inverted relationship
     * @param relationships the list of relationships to append to
     * @param cache a cache of information that may already have been retrieved about the provided object
     * @param fromIgcObject the object that is the source of the IGC relationship
     * @param igcSearchConditionSet the search criteria to use for the search
     * @param assetType the type of IGC asset for which to search
     * @param igcPropertyName the name of the IGC property to search against
     * @param fromRelationshipElement the starting element number of the relationships to return.
     *                                This is used when retrieving elements
     *                                beyond the first page of results. Zero means start from the first element.
     * @param sequencingOrder Enum defining how the results should be ordered.
     * @param pageSize the maximum number of result classifications that can be returned on this request.  Zero means
     *                 unrestricted return results size.
     * @param userId the user retrieving the mapped relationship
     * @throws RepositoryErrorException if any issues interacting with IGC
     */
    private static void addSearchResultsToRelationships(IGCOMRSRepositoryConnector igcomrsRepositoryConnector,
                                                        RelationshipMapping mapping,
                                                        List<Relationship> relationships,
                                                        ObjectCache cache,
                                                        Reference fromIgcObject,
                                                        IGCSearchConditionSet igcSearchConditionSet,
                                                        String assetType,
                                                        String igcPropertyName,
                                                        int fromRelationshipElement,
                                                        SequencingOrder sequencingOrder,
                                                        int pageSize,
                                                        String userId) throws RepositoryErrorException {

        final String methodName = "addSearchResultsToRelationships";
        IGCSearch igcSearch = new IGCSearch(assetType, igcSearchConditionSet);
        try {
            if (!assetType.equals(IGCRepositoryHelper.DEFAULT_IGC_TYPE)) {
                if (igcomrsRepositoryConnector.getIGCRestClient().hasModificationDetails(assetType)) {
                    igcSearch.addProperties(IGCRestConstants.getModificationProperties());
                }
            }
            IGCSearchSorting sorting = IGCRepositoryHelper.sortFromNonPropertySequencingOrder(sequencingOrder);
            if (sorting != null) {
                igcSearch.addSortingCriteria(sorting);
            }
            ItemList<Reference> foundRelationships = igcomrsRepositoryConnector.getIGCRestClient().search(igcSearch);
            addListOfMappedRelationships(
                    igcomrsRepositoryConnector,
                    mapping,
                    relationships,
                    cache,
                    fromIgcObject,
                    foundRelationships,
                    igcPropertyName,
                    fromRelationshipElement,
                    pageSize,
                    userId
            );
        } catch (IGCException e) {
            raiseRepositoryErrorException(IGCOMRSErrorCode.UNKNOWN_RUNTIME_ERROR, methodName, e);
        }

    }

    /**
     * Add the provided list of relationships as OMRS relationships. Note that this method assumes that the provided
     * list of IGC relationships is already sorted by whatever means was requested.
     *
     * @param igcomrsRepositoryConnector connectivity to the IGC repository
     * @param mapping the mapping to use in translating each relationship
     * @param relationships the list of relationships to append to
     * @param fromIgcObject the asset that is the source of the IGC relationship
     * @param igcRelationships the list of IGC relationships
     * @param igcPropertyName the name of the IGC relationship property
     * @param fromRelationshipElement the starting element number of the relationships to return.
     *                                This is used when retrieving elements
     *                                beyond the first page of results. Zero means start from the first element.
     * @param pageSize the maximum number of result classifications that can be returned on this request.  Zero means
     *                 unrestricted return results size.
     * @param userId the user retrieving the mapped relationship
     * @throws RepositoryErrorException if any issues interacting with IGC
     */
    private static void addListOfMappedRelationships(IGCOMRSRepositoryConnector igcomrsRepositoryConnector,
                                                     RelationshipMapping mapping,
                                                     List<Relationship> relationships,
                                                     ObjectCache cache,
                                                     Reference fromIgcObject,
                                                     ItemList<Reference> igcRelationships,
                                                     String igcPropertyName,
                                                     int fromRelationshipElement,
                                                     int pageSize,
                                                     String userId) throws RepositoryErrorException {

        final String methodName = "addListOfMappedRelationships";
        log.debug(" ... list of references: {}", mapping.getOmrsRelationshipType());

        List<Relationship> localPage = new ArrayList<>();

        int totalPotentialResults = fromRelationshipElement + pageSize;
        if (pageSize == 0) {
            // If the pageSize is 0, we should retrieve all results, so set the total to the total number of results...
            totalPotentialResults = igcRelationships.getPaging().getNumTotal();
        }

        // Always fill a full page of results here, but no more, as once final sorting is applied all results may
        // come from this set of relationships
        for (Reference relation : igcRelationships.getItems()) {
            if (localPage.size() < totalPotentialResults
                    && mapping.includeRelationshipForIgcObjects(igcomrsRepositoryConnector, cache, fromIgcObject, relation)) {
                addSingleMappedRelationship(
                        igcomrsRepositoryConnector,
                        mapping,
                        localPage,
                        cache,
                        fromIgcObject,
                        relation,
                        igcPropertyName,
                        userId
                );
            }
        }

        relationships.addAll(localPage);

        // If we haven't filled a page of results (because we needed to skip some above), recurse...
        if (igcRelationships.hasMorePages() && localPage.size() < totalPotentialResults) {
            // TODO: will we always have the property name here, or could it be null where ordering is known?
            try {
                ItemList<Reference> nextPage = igcomrsRepositoryConnector.getIGCRestClient().getNextPage(igcPropertyName, igcRelationships);
                addListOfMappedRelationships(igcomrsRepositoryConnector,
                        mapping,
                        relationships,
                        cache,
                        fromIgcObject,
                        nextPage,
                        igcPropertyName,
                        fromRelationshipElement,
                        pageSize,
                        userId
                );
            } catch (IGCException e) {
                raiseRepositoryErrorException(IGCOMRSErrorCode.UNKNOWN_RUNTIME_ERROR, methodName, e);
            }
        }

    }

    /**
     * Add the provided relationship as an OMRS relationship.
     *
     * @param igcomrsRepositoryConnector connectivity to the IGC repository
     * @param mapping the mapping to use in translating each relationship
     * @param relationships the list of relationships to append to
     * @param cache a cache of information that may already have been retrieved about the provided object
     * @param fromIgcObject the asset that is the source of the IGC relationship
     * @param igcRelationship the IGC relationship
     * @param igcPropertyName the name of the IGC relationship property
     * @param userId the user retrieving the mapped relationship
     */
    private static void addSingleMappedRelationship(IGCOMRSRepositoryConnector igcomrsRepositoryConnector,
                                                    RelationshipMapping mapping,
                                                    List<Relationship> relationships,
                                                    ObjectCache cache,
                                                    Reference fromIgcObject,
                                                    Reference igcRelationship,
                                                    String igcPropertyName,
                                                    String userId) {

        log.debug(" ... single reference: {} of type {}", igcRelationship.getName(), igcRelationship.getType());
        if (igcRelationship != null
                && igcRelationship.getType() != null
                && !igcRelationship.getType().equals("null")) {

            try {
                Relationship omrsRelationship = getMappedRelationship(
                        igcomrsRepositoryConnector,
                        mapping,
                        cache,
                        fromIgcObject,
                        igcRelationship,
                        igcPropertyName,
                        userId
                );
                log.debug("addSingleMappedRelationship - adding relationship: {}", omrsRelationship.getGUID());
                relationships.add(omrsRelationship);
            } catch (RepositoryErrorException e) {
                log.error("Unable to add relationship {} for object {}", mapping.getOmrsRelationshipType(), igcRelationship, e);
            }

        }

    }

    /**
     * Add the provided relationship as an OMRS relationship, when the order of the proxy endpoints is known.
     *
     * @param igcomrsRepositoryConnector connectivity to the IGC repository
     * @param mapping the mapping to use in translating each relationship
     * @param relationships the list of relationships to append to
     * @param cache a cache of information that may already have been retrieved about the provided object
     * @param proxyOne the asset that acts as proxy one in the relationship
     * @param proxyTwo the asset that acts as proxy two in the relationship
     * @param userId the user retrieving the mapped relationship
     */
    private static void addSingleMappedRelationshipWithKnownOrder(IGCOMRSRepositoryConnector igcomrsRepositoryConnector,
                                                                  RelationshipMapping mapping,
                                                                  List<Relationship> relationships,
                                                                  ObjectCache cache,
                                                                  Reference proxyOne,
                                                                  Reference proxyTwo,
                                                                  String userId) {
        log.debug(" ... single reference: {} of type {}", proxyTwo.getName(), proxyTwo.getType());
        if (proxyTwo != null
                && proxyTwo.getType() != null
                && !proxyTwo.getType().equals("null")) {

            try {
                RelationshipDef omrsRelationshipDef = (RelationshipDef) igcomrsRepositoryConnector.getRepositoryHelper().getTypeDefByName(
                        igcomrsRepositoryConnector.getRepositoryName(),
                        mapping.getOmrsRelationshipType()
                );
                Relationship omrsRelationship = getMappedRelationship(
                        igcomrsRepositoryConnector,
                        mapping,
                        omrsRelationshipDef,
                        cache,
                        proxyOne,
                        proxyTwo,
                        null,
                        userId,
                        null,
                        true
                );
                log.debug("addSingleMappedRelationshipWithKnownOrder - adding relationship: {}", omrsRelationship.getGUID());
                relationships.add(omrsRelationship);
            } catch (RepositoryErrorException e) {
                log.error("Unable to add relationship {} for object {}", mapping.getOmrsRelationshipType(), proxyTwo, e);
            }

        }
    }

    /**
     * Retrieve a Relationship instance based on the provided mapping information, automatically prefixing
     * where needed.
     *
     * @param igcomrsRepositoryConnector connectivity to the IGC repository
     * @param mapping the mapping details to use
     * @param cache a cache of information that may already have been retrieved about the provided object
     * @param fromIgcObject the asset that is the source of the IGC relationship
     * @param relation the related IGC object
     * @param igcPropertyName the name of the IGC relationship property
     * @param userId the user retrieving the mapped relationship
     * @return Relationship
     * @throws RepositoryErrorException there is a problem communicating with the metadata repository where
     *                                  the metadata collection is stored
     */
    private static Relationship getMappedRelationship(IGCOMRSRepositoryConnector igcomrsRepositoryConnector,
                                                      RelationshipMapping mapping,
                                                      ObjectCache cache,
                                                      Reference fromIgcObject,
                                                      Reference relation,
                                                      String igcPropertyName,
                                                      String userId) throws RepositoryErrorException {

        RelationshipDef omrsRelationshipDef = (RelationshipDef) igcomrsRepositoryConnector.getRepositoryHelper().getTypeDefByName(
                igcomrsRepositoryConnector.getRepositoryName(),
                mapping.getOmrsRelationshipType()
        );

        return getMappedRelationship(
                igcomrsRepositoryConnector,
                mapping,
                omrsRelationshipDef,
                cache,
                fromIgcObject,
                relation,
                igcPropertyName,
                userId
        );

    }

    /**
     * Retrieve a Relationship instance based on the provided definition and endpoints.
     *
     * @param igcomrsRepositoryConnector connectivity to the IGC repository
     * @param relationshipMapping the definition of how to map the relationship
     * @param omrsRelationshipDef the OMRS relationship definition
     * @param cache a cache of information that may already have been retrieved about the provided object
     * @param proxyOne the IGC asset to use for endpoint 1 of the relationship
     * @param proxyTwo the IGC asset to use for endpoint 2 of the relationship
     * @param igcPropertyName the name of the IGC relationship property
     * @param userId the user retrieving the mapped relationship
     * @return Relationship
     * @throws RepositoryErrorException there is a problem communicating with the metadata repository where
     *                                  the metadata collection is stored
     */
    protected static Relationship getMappedRelationship(IGCOMRSRepositoryConnector igcomrsRepositoryConnector,
                                                        RelationshipMapping relationshipMapping,
                                                        RelationshipDef omrsRelationshipDef,
                                                        ObjectCache cache,
                                                        Reference proxyOne,
                                                        Reference proxyTwo,
                                                        String igcPropertyName,
                                                        String userId) throws RepositoryErrorException {
        return getMappedRelationship(
                igcomrsRepositoryConnector,
                relationshipMapping,
                omrsRelationshipDef,
                cache,
                proxyOne,
                proxyTwo,
                igcPropertyName,
                userId,
                null
        );
    }

    /**
     * Retrieve a Relationship instance based on the provided definition, endpoints, and optional prefixes.
     *
     * @param igcomrsRepositoryConnector connectivity to the IGC repository
     * @param relationshipMapping the definition of how to map the relationship
     * @param omrsRelationshipDef the OMRS relationship definition
     * @param cache a cache of information that may already have been retrieved about the provided object
     * @param proxyOne the IGC asset to consider for endpoint 1 of the relationship
     * @param proxyTwo the IGC asset to consider for endpoint 2 of the relationship
     * @param igcPropertyName the name of the IGC relationship property
     * @param userId the user retrieving the mapped relationship
     * @param relationshipLevelRid the IGC RID for the relationship itself (in rare instances where it exists)
     * @return Relationship
     * @throws RepositoryErrorException there is a problem communicating with the metadata repository where
     *                                  the metadata collection is stored
     */
    public static Relationship getMappedRelationship(IGCOMRSRepositoryConnector igcomrsRepositoryConnector,
                                                     RelationshipMapping relationshipMapping,
                                                     RelationshipDef omrsRelationshipDef,
                                                     ObjectCache cache,
                                                     Reference proxyOne,
                                                     Reference proxyTwo,
                                                     String igcPropertyName,
                                                     String userId,
                                                     String relationshipLevelRid) throws RepositoryErrorException {
        return getMappedRelationship(
                igcomrsRepositoryConnector,
                relationshipMapping,
                omrsRelationshipDef,
                cache,
                proxyOne,
                proxyTwo,
                igcPropertyName,
                userId,
                relationshipLevelRid,
                false
        );
    }

    /**
     * Retrieve a Relationship instance based on the provided definition, endpoints, and optional prefixes.
     *
     * @param igcomrsRepositoryConnector connectivity to the IGC repository
     * @param relationshipMapping the definition of how to map the relationship
     * @param omrsRelationshipDef the OMRS relationship definition
     * @param cache a cache of information that may already have been retrieved about the provided object
     * @param proxyOne the IGC asset to consider for endpoint 1 of the relationship
     * @param proxyTwo the IGC asset to consider for endpoint 2 of the relationship
     * @param igcPropertyName the name of the IGC relationship property
     * @param userId the user retrieving the mapped relationship
     * @param relationshipLevelRid the IGC RID for the relationship itself (in rare instances where it exists)
     * @param proxyOrderKnown should be true iff the provided candidate proxies are known to be in the correct order
     * @return Relationship
     * @throws RepositoryErrorException there is a problem communicating with the metadata repository where
     *                                  the metadata collection is stored
     */
    public static Relationship getMappedRelationship(IGCOMRSRepositoryConnector igcomrsRepositoryConnector,
                                                     RelationshipMapping relationshipMapping,
                                                     RelationshipDef omrsRelationshipDef,
                                                     ObjectCache cache,
                                                     Reference proxyOne,
                                                     Reference proxyTwo,
                                                     String igcPropertyName,
                                                     String userId,
                                                     String relationshipLevelRid,
                                                     boolean proxyOrderKnown) throws RepositoryErrorException {

        final String methodName = "getMappedRelationship";
        final String repositoryName = igcomrsRepositoryConnector.getRepositoryName();

        IGCOMRSMetadataCollection igcomrsMetadataCollection = (IGCOMRSMetadataCollection) igcomrsRepositoryConnector.getMetadataCollection();
        IGCRepositoryHelper igcRepositoryHelper = igcomrsMetadataCollection.getIgcRepositoryHelper();
        OMRSRepositoryHelper omrsRepositoryHelper = igcomrsRepositoryConnector.getRepositoryHelper();
        String omrsRelationshipName = omrsRelationshipDef.getName();

        Relationship relationship = new Relationship();

        try {
            InstanceType instanceType = igcomrsRepositoryConnector.getRepositoryHelper().getNewInstanceType(
                    igcomrsRepositoryConnector.getRepositoryName(),
                    omrsRelationshipDef
            );
            relationship.setType(instanceType);
        } catch (TypeErrorException e) {
            log.error("Unable to construct and set InstanceType -- skipping relationship: {}", omrsRelationshipName);
            raiseRepositoryErrorException(IGCOMRSErrorCode.INVALID_INSTANCE, methodName, methodName, omrsRelationshipDef.getName());
        }

        IGCRelationshipGuid igcRelationshipGuid = RelationshipMapping.getRelationshipGUID(
                igcRepositoryHelper,
                relationshipMapping,
                proxyOne,
                proxyTwo,
                igcPropertyName,
                relationshipLevelRid,
                proxyOrderKnown
        );

        if (igcRelationshipGuid != null) {

            log.debug("Mapping relationship with GUID: {}", igcRelationshipGuid);

            relationship.setGUID(igcRelationshipGuid.toString());
            relationship.setMetadataCollectionId(igcomrsRepositoryConnector.getMetadataCollectionId());
            relationship.setStatus(InstanceStatus.ACTIVE);
            relationship.setInstanceProvenanceType(InstanceProvenanceType.LOCAL_COHORT);

            String ridForEP1 = igcRelationshipGuid.getRid1();
            String ridForEP2 = igcRelationshipGuid.getRid2();

            EntityProxy ep1 = null;
            EntityProxy ep2 = null;

            if (relationshipLevelRid != null
                    || (ridForEP1.equals(proxyOne.getId()) && ridForEP2.equals(proxyTwo.getId()))) {
                ep1 = RelationshipMapping.getEntityProxyForObject(
                        igcomrsRepositoryConnector,
                        cache,
                        proxyOne,
                        userId,
                        relationshipMapping.getProxyOneMapping().getIgcRidPrefix()
                );
                ep2 = RelationshipMapping.getEntityProxyForObject(
                        igcomrsRepositoryConnector,
                        cache,
                        proxyTwo,
                        userId,
                        relationshipMapping.getProxyTwoMapping().getIgcRidPrefix()
                );
            } else if (ridForEP2.equals(proxyOne.getId()) && ridForEP1.equals(proxyTwo.getId())) {
                ep1 = RelationshipMapping.getEntityProxyForObject(
                        igcomrsRepositoryConnector,
                        cache,
                        proxyTwo,
                        userId,
                        relationshipMapping.getProxyOneMapping().getIgcRidPrefix()
                );
                ep2 = RelationshipMapping.getEntityProxyForObject(
                        igcomrsRepositoryConnector,
                        cache,
                        proxyOne,
                        userId,
                        relationshipMapping.getProxyTwoMapping().getIgcRidPrefix()
                );
            } else {
                log.error("Unable to determine both ends of the relationship {} from {} to {}", omrsRelationshipName, proxyOne.getId(), proxyTwo.getId());
                String omrsEndOneProperty = omrsRelationshipDef.getEndDef1().getAttributeName();
                String omrsEndTwoProperty = omrsRelationshipDef.getEndDef2().getAttributeName();
                raiseRepositoryErrorException(IGCOMRSErrorCode.INVALID_RELATIONSHIP_ENDS, methodName, methodName, repositoryName, omrsRelationshipName, omrsEndOneProperty, omrsEndTwoProperty);
            }

            // Set the the version of the relationship to the epoch time of whichever end of the relationship has
            // modification details (they should be the same if both have modification details)
            if (ep1 != null && ep1.getUpdateTime() != null) {
                InstanceMapping.setupInstanceModDetails(relationship,
                        ep1.getCreatedBy(),
                        ep1.getCreateTime(),
                        ep1.getUpdatedBy(),
                        ep1.getUpdateTime());
            } else if (ep2 != null && ep2.getUpdateTime() != null) {
                InstanceMapping.setupInstanceModDetails(relationship,
                        ep2.getCreatedBy(),
                        ep2.getCreateTime(),
                        ep2.getUpdatedBy(),
                        ep2.getUpdateTime());
            }

            if (ep1 != null && ep2 != null) {
                relationship.setEntityOneProxy(ep1);
                relationship.setEntityTwoProxy(ep2);

                // Set any fixed (literal) relationship property values
                Map<String, TypeDefAttribute> omrsAttributeMap = igcomrsMetadataCollection.getTypeDefAttributesForType(omrsRelationshipName);
                InstanceProperties relationshipProperties = new InstanceProperties();
                for (String omrsPropertyName : relationshipMapping.getLiteralPropertyMappings()) {
                    if (omrsAttributeMap.containsKey(omrsPropertyName)) {
                        Object value = relationshipMapping.getOmrsPropertyLiteralValue(omrsPropertyName);
                        if (value != null) {
                            TypeDefAttribute typeDefAttribute = omrsAttributeMap.get(omrsPropertyName);
                            AttributeTypeDefCategory attributeTypeDefCategory = typeDefAttribute.getAttributeType().getCategory();
                            if (attributeTypeDefCategory == AttributeTypeDefCategory.PRIMITIVE) {
                                relationshipProperties = AttributeMapping.addPrimitivePropertyToInstance(
                                        omrsRepositoryHelper,
                                        repositoryName,
                                        relationshipProperties,
                                        typeDefAttribute,
                                        value,
                                        methodName
                                );
                            } else {
                                relationshipProperties.setProperty(omrsPropertyName, (InstancePropertyValue) value);
                            }
                        }
                    }
                }
                relationship.setProperties(relationshipProperties);
            } else {
                if (igcomrsRepositoryConnector.ignoreUnmappedInstances()) {
                    log.warn("Unable to construct proxies for both ends of relationship -- skipping: {} between {} and {}", omrsRelationshipName, proxyOne.getType(), proxyTwo.getType());
                } else {
                    log.error("Unable to construct proxies for both ends of relationship -- skipping: {} between {} and {}", omrsRelationshipName, proxyOne.getType(), proxyTwo.getType());
                    raiseRepositoryErrorException(IGCOMRSErrorCode.UNMAPPED_RELATIONSHIP_ENDS, methodName, omrsRelationshipName, proxyOne.getType(), proxyTwo.getType());
                }
            }
        } else {
            log.error("Unable to construct relationship GUID -- skipping relationship: {}", omrsRelationshipName);
            String omrsEndOneProperty = omrsRelationshipDef.getEndDef1().getAttributeName();
            String omrsEndTwoProperty = omrsRelationshipDef.getEndDef2().getAttributeName();
            raiseRepositoryErrorException(IGCOMRSErrorCode.INVALID_RELATIONSHIP_ENDS, methodName, methodName, repositoryName, omrsRelationshipName, omrsEndOneProperty, omrsEndTwoProperty);
        }

        return relationship;

    }

    /**
     * Throws a RepositoryErrorException using the provided parameters
     * @param errorCode the error code for the exception
     * @param methodName the name of the method throwing the exception
     * @param params the parameters used for formatting the message for the exception
     * @throws RepositoryErrorException always
     */
    private static void raiseRepositoryErrorException(IGCOMRSErrorCode errorCode, String methodName, String ...params) throws RepositoryErrorException {
        throw new RepositoryErrorException(errorCode.getMessageDefinition(params),
                RelationshipMapping.class.getName(),
                methodName);
    }

}
