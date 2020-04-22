/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.stores;

import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSRepositoryConnector;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.typedefs.TypeDef;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.typedefs.TypeDefPatch;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.InvalidParameterException;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.PatchErrorException;

import java.util.*;

/**
 * Store of implemented mappings for the repository.
 */
public abstract class MappingStore {

    protected IGCOMRSRepositoryConnector igcomrsRepositoryConnector;
    private Map<String, TypeDef> typeDefs;
    private Map<String, String> omrsNameToGuid;

    protected MappingStore(IGCOMRSRepositoryConnector igcomrsRepositoryConnector) {
        typeDefs = new TreeMap<>();
        omrsNameToGuid = new HashMap<>();
        this.igcomrsRepositoryConnector = igcomrsRepositoryConnector;
    }

    /**
     * Adds the provided TypeDef as one that has been mapped.
     *
     * @param typeDef to add
     */
    public void addTypeDef(TypeDef typeDef) {
        if (typeDef != null) {
            String guid = typeDef.getGUID();
            typeDefs.put(guid, typeDef);
            addNameToGuidMapping(typeDef.getName(), guid);
        }
    }

    /**
     * Adds a mapping between an OMRS type name and its GUID.
     *
     * @param name of the OMRS type
     * @param guid of the OMRS type
     */
    private void addNameToGuidMapping(String name, String guid) {
        if (name != null && guid != null) {
            omrsNameToGuid.put(name, guid);
        }
    }

    /**
     * Retrieve the GUID of an OMRS type given its name.
     *
     * @param name of the OMRS type
     * @return String giving its GUID, or null if it is not a mapped type
     */
    public String getGuidForName(String name) {
        return omrsNameToGuid.getOrDefault(name, null);
    }

    /**
     * Updates a mapping using the provided TypeDefPatch, where the mapping for the type being patched must already
     * exist.
     *
     * @param typeDefPatch the patch to apply
     * @return boolean false when unable to find the existing mapping to patch
     * @throws InvalidParameterException if the typeDefPatch is null
     * @throws PatchErrorException if the patch is either badly formatted or does not apply to the defined type
     */
    public boolean updateMapping(TypeDefPatch typeDefPatch) throws InvalidParameterException, PatchErrorException {

        String omrsTypeGUID = typeDefPatch.getTypeDefGUID();
        TypeDef existing = typeDefs.getOrDefault(omrsTypeGUID, null);
        if (existing != null) {
            TypeDef updated = igcomrsRepositoryConnector.getRepositoryHelper().applyPatch(igcomrsRepositoryConnector.getServerName(), existing, typeDefPatch);
            typeDefs.put(omrsTypeGUID, updated);
        }

        return (existing != null);

    }

    /**
     * Retrieves the listing of all TypeDefs for which classification mappings are implemented.
     *
     * @return {@code List<TypeDef>}
     */
    public List<TypeDef> getTypeDefs() { return new ArrayList<>(this.typeDefs.values()); }

    /**
     * Retrieve the OMRS TypeDef by its guid.
     *
     * @param guid of the OMRS TypeDef
     * @return TypeDef
     */
    public TypeDef getTypeDefByGUID(String guid) {
        return typeDefs.getOrDefault(guid, null);
    }

}
