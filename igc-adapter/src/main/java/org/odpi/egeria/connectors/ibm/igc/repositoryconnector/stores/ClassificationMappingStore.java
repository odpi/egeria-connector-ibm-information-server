/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.stores;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSRepositoryConnector;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.classifications.ClassificationMapping;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.typedefs.TypeDef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Store of implemented classification mappings for the repository.
 */
public class ClassificationMappingStore {

    private static final Logger log = LoggerFactory.getLogger(ClassificationMappingStore.class);

    private IGCOMRSRepositoryConnector igcomrsRepositoryConnector;

    private List<TypeDef> typeDefs;

    private Map<String, List<ClassificationMapping>> omrsGuidToMappings;
    private Map<String, List<String>> omrsNameToGuids;

    public ClassificationMappingStore(IGCOMRSRepositoryConnector igcomrsRepositoryConnector) {
        typeDefs = new ArrayList<>();
        omrsGuidToMappings = new HashMap<>();
        omrsNameToGuids = new HashMap<>();
        this.igcomrsRepositoryConnector = igcomrsRepositoryConnector;
    }

    /**
     * Adds a classification mapping for the provided TypeDef, using the provided Java class for the mapping.
     *
     * @param omrsTypeDef the OMRS TypeDef
     * @param mappingClass the ClassificationMapping Java class
     * @return boolean false when unable to retrieve ClassificationMapping from provided class
     */
    public boolean addMapping(TypeDef omrsTypeDef, Class mappingClass) {

        ClassificationMapping mapping = getClassificationMapper(mappingClass);

        if (mapping != null) {
            typeDefs.add(omrsTypeDef);
            String guid = omrsTypeDef.getGUID();
            String name = omrsTypeDef.getName();
            if (!omrsGuidToMappings.containsKey(guid)) {
                List<ClassificationMapping> list = new ArrayList<>();
                omrsGuidToMappings.put(guid, list);
            }
            if (!omrsNameToGuids.containsKey(name)) {
                List<String> list = new ArrayList<>();
                omrsNameToGuids.put(name, list);
            }
            omrsGuidToMappings.get(guid).add(mapping);
            omrsNameToGuids.get(name).add(guid);
        }

        return (mapping != null);

    }

    /**
     * Retrieves the listing of all TypeDefs for which classification mappings are implemented.
     *
     * @return {@code List<TypeDef>}
     */
    public List<TypeDef> getTypeDefs() { return this.typeDefs; }

    /**
     * Retrieves a classification mapping based on the GUID of the OMRS classification type.
     *
     * @param guid of the OMRS classification type
     * @return {@code List<ClassificationMapping>}
     */
    public List<ClassificationMapping> getMappingsByOmrsTypeGUID(String guid) {
        if (omrsGuidToMappings.containsKey(guid)) {
            return omrsGuidToMappings.get(guid);
        } else {
            if (log.isWarnEnabled()) { log.warn("Unable to find mapping for OMRS type: {}", guid); }
            return null;
        }
    }

    /**
     * Retrieves a classification mapping based ont he name of the OMRS classification type.
     *
     * @param name of the OMRS classification type
     * @return {@code List<ClassificationMapping>}
     */
    public List<ClassificationMapping> getMappingsByOmrsTypeName(String name) {
        if (omrsNameToGuids.containsKey(name)) {
            List<String> guids = omrsNameToGuids.get(name);
            List<ClassificationMapping> classificationMappings = new ArrayList<>();
            for (String guid : guids) {
                classificationMappings.addAll(getMappingsByOmrsTypeGUID(guid));
            }
            return classificationMappings;
        } else {
            if (log.isWarnEnabled()) { log.warn("Unable to find mapping for OMRS type: {}", name); }
            return null;
        }
    }

    /**
     * Retrieves a ClassificationMapping by OMRS classification type from those that are listed as implemented.
     *
     * @param omrsClassificationType the name of the OMRS classification type for which to retrieve a mapping
     * @param igcAssetType the IGC asset type
     * @return ClassificationMapping
     */
    public ClassificationMapping getMappingByTypes(String omrsClassificationType,
                                                   String igcAssetType) {
        ClassificationMapping found = null;
        List<ClassificationMapping> candidates = getMappingsByOmrsTypeName(omrsClassificationType);
        for (ClassificationMapping candidate : candidates) {
            String candidateIgcType = candidate.getIgcAssetType();
            Set<String> excludedIgcTypes = candidate.getExcludedIgcAssetTypes();
            if (!excludedIgcTypes.contains(igcAssetType)) {
                // If the IGC types also match, short-circuit out
                if (candidateIgcType.equals(igcAssetType) || candidateIgcType.equals("main_object")) {
                    found = candidate;
                    break;
                } else if (candidate.hasSubTypes()) {
                    // Otherwise, check any sub-types and short-circuit out if we find a match
                    for (ClassificationMapping subMapping : candidate.getSubTypes()) {
                        candidateIgcType = candidate.getIgcAssetType();
                        if (candidateIgcType.equals(igcAssetType) || candidateIgcType.equals("main_object")) {
                            found = subMapping;
                            break;
                        }
                    }
                }
            }
        }
        return found;
    }

    /**
     * Introspect a mapping class to retrieve a ClassificationMapping.
     *
     * @param mappingClass the mapping class to retrieve an instance of
     * @return ClassificationMapping
     */
    private ClassificationMapping getClassificationMapper(Class mappingClass) {
        ClassificationMapping classificationMapper = null;
        try {
            Method getInstance = mappingClass.getMethod("getInstance", IGCVersionEnum.class);
            classificationMapper = (ClassificationMapping) getInstance.invoke(null, igcomrsRepositoryConnector.getIGCVersion());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            if (log.isErrorEnabled()) { log.error("Unable to find or instantiate ClassificationMapping class: {}", mappingClass, e); }
        }
        return classificationMapper;
    }

}
