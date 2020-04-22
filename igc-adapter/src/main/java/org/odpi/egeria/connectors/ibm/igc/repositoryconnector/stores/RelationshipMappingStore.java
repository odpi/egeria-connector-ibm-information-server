/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.stores;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSRepositoryConnector;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.RelationshipMapping;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.typedefs.TypeDef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Store of implemented relationship mappings for the repository.
 */
public class RelationshipMappingStore extends MappingStore {

    private static final Logger log = LoggerFactory.getLogger(RelationshipMappingStore.class);

    private Map<String, RelationshipMapping> omrsGuidToMapping;

    public RelationshipMappingStore(IGCOMRSRepositoryConnector igcomrsRepositoryConnector) {
        super(igcomrsRepositoryConnector);
        omrsGuidToMapping = new HashMap<>();
    }

    /**
     * Adds a relationship mapping for the provided TypeDef, using the provided Java class for the mapping.
     *
     * @param omrsTypeDef the OMRS TypeDef
     * @param mappingClass the RelationshipMapping Java class
     * @return boolean false when unable to retrieve RelationshipMapping from provided class
     */
    public boolean addMapping(TypeDef omrsTypeDef, Class<?> mappingClass) {

        RelationshipMapping mapping = getRelationshipMapper(mappingClass);

        if (mapping != null) {
            addTypeDef(omrsTypeDef);
            String guid = omrsTypeDef.getGUID();
            omrsGuidToMapping.put(guid, mapping);
        }

        return (mapping != null);

    }

    /**
     * Retrieves the relationship mapping for the provided TypeDef GUID.
     *
     * @param guid of the OMRS type definition
     * @return RelationshipMapping
     */
    public RelationshipMapping getMappingByOmrsTypeGUID(String guid) {
        if (omrsGuidToMapping.containsKey(guid)) {
            return omrsGuidToMapping.get(guid);
        } else {
            log.warn("Unable to find mapping for OMRS type: {}", guid);
            return null;
        }
    }

    /**
     * Retrieves the relationship mapping for the provided TypeDef name.
     *
     * @param name of the OMRS type definition
     * @return RelationshipMapping
     */
    public RelationshipMapping getMappingByOmrsTypeName(String name) {
        String guid = getGuidForName(name);
        if (guid == null) {
            log.warn("Unable to find mapping for OMRS type: {}", name);
            return null;
        } else {
            return getMappingByOmrsTypeGUID(guid);
        }
    }

    /**
     * Retrieves all relationship mappings that are implemented for this repository.
     *
     * @return {@code List<RelationshipMapping>}
     */
    public List<RelationshipMapping> getAllMappings() {
        return new ArrayList<>(omrsGuidToMapping.values());
    }

    /**
     * Retrieves a RelationshipMapping by OMRS relationship type from those that are listed as implemented.
     *
     * @param omrsRelationshipType the name of the OMRS relationship type for which to retrieve a mapping
     * @param proxyOneType the IGC asset type of the first end of the relationship
     * @param proxyTwoType the IGC asset type of the second end of the relationship
     * @return RelationshipMapping
     */
    public RelationshipMapping getMappingByTypes(String omrsRelationshipType,
                                                 String proxyOneType,
                                                 String proxyTwoType) {
        RelationshipMapping found = null;
        for (RelationshipMapping candidate : omrsGuidToMapping.values()) {
            // If the relationship mapping and its type equals the one we're looking for,
            // set it to found and short-circuit out of the loop
            if (candidate != null) {
                if (matchingRelationshipMapper(candidate, omrsRelationshipType, proxyOneType, proxyTwoType)) {
                    found = candidate;
                    break;
                } else if (candidate.hasSubTypes()) {
                    for (RelationshipMapping subMapping : candidate.getSubTypes()) {
                        if (matchingRelationshipMapper(subMapping, omrsRelationshipType, proxyOneType, proxyTwoType)) {
                            found = subMapping;
                            break;
                        }
                    }
                } else if (candidate.hasRelationshipLevelAsset()) {
                    String relationshipLevelAssetType = candidate.getRelationshipLevelIgcAsset();
                    if (proxyOneType.equals(relationshipLevelAssetType) && proxyTwoType.equals(relationshipLevelAssetType)) {
                        found = candidate;
                        break;
                    }
                }

            }
        }
        return found;
    }

    /**
     * Indicates whether the provided relationship mapping matches the provided criteria.
     *
     * @param candidate the relationship mapping to check
     * @param omrsRelationshipType the OMRS relationship type to confirm
     * @param proxyOneType the asset type of endpoint 1 of the relationship to confirm
     * @param proxyTwoType the asset type of endpoint 2 of the relationship to confirm
     * @return boolean
     */
    private boolean matchingRelationshipMapper(RelationshipMapping candidate,
                                               String omrsRelationshipType,
                                               String proxyOneType,
                                               String proxyTwoType) {
        return (candidate != null
                && candidate.getOmrsRelationshipType().equals(omrsRelationshipType)
                && candidate.getProxyOneMapping().matchesAssetType(proxyOneType)
                && candidate.getProxyTwoMapping().matchesAssetType(proxyTwoType));
    }

    /**
     * Introspect a mapping class to retrieve a RelationshipMapping.
     *
     * @param mappingClass the mapping class to retrieve an instance of
     * @return RelationshipMapping
     */
    private RelationshipMapping getRelationshipMapper(Class<?> mappingClass) {
        RelationshipMapping relationshipMapper = null;
        try {
            Method getInstance = mappingClass.getMethod("getInstance", IGCVersionEnum.class);
            relationshipMapper = (RelationshipMapping) getInstance.invoke(null, igcomrsRepositoryConnector.getIGCVersion());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            log.error("Unable to find or instantiate RelationshipMapping class: {}", mappingClass, e);
        }
        return relationshipMapper;
    }

}
