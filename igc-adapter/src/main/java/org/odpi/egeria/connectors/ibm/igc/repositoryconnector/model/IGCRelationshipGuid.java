/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.model;

import java.util.Objects;

/**
 * Captures the meaning and translation of the various components of an IGC relationship's GUID.
 *
 * This is necessary as internally IGC does not capture relationships as unique object instances, but only as a
 * property that inter-relates two entities.  Without an object instance, IGC has no GUID (nor even an RID) for a
 * relationship.  Therefore, we need to provide a mechanism to automatically ensure relationship GUIDs are generated
 * and unique, with minimal impact on persistence, etc.
 */
public class IGCRelationshipGuid extends IGCGuidHeader {

    private static final String GENERATED_TYPE_POSTFIX = "!";
    private static final String IGC_TYPE_POSTFIX = "@";
    private static final String RELATIONSHIP_TYPE_PREFIX = "<";
    private static final String RELATIONSHIP_TYPE_POSTFIX = ">";

    private String assetType1;
    private String assetType2;
    private String generatedPrefix1;
    private String generatedPrefix2;
    private String rid1;
    private String rid2;

    private String relationshipType;

    /**
     * Create a new IGC GUID that has no generated prefix (for an OMRS relationship type that exists in IGC and is not
     * generated)
     *
     * @param metadataCollectionId the metadata collection ID of the IGC repository
     * @param assetType1 the IGC asset type of the first endpoint of the relationship
     * @param assetType2 the IGC asset type of the second endpoint of the relationship
     * @param rid1 the Repository ID (RID) of the IGC asset at the first endpoint of the relationship
     * @param rid2 the Repository ID (RID) of the IGC asset at the second endpoint of the relationship
     * @param relationshipType the OMRS type name of the relationship
     */
    public IGCRelationshipGuid(String metadataCollectionId,
                               String assetType1,
                               String assetType2,
                               String rid1,
                               String rid2,
                               String relationshipType) {
        this(metadataCollectionId, assetType1, assetType2, null, null, rid1, rid2, relationshipType);
    }

    /**
     * Create a new IGC GUID that has a prefix (for an OMRS entity type that does not actually exist in IGC but is
     * generated from another entity type in IGC)
     *
     * @param metadataCollectionId the metadata collection ID of the IGC repository
     * @param assetType1 the IGC asset type of the first endpoint of the relationship
     * @param assetType2 the IGC asset type of the second endpoint of the relationship
     * @param prefix1 the prefix to use to uniquely identify the generated entity's GUID at the first endpoint of the relationship
     * @param prefix2 the prefix to use to uniquely identify the generated entity's GUID at the second endpoint of the relationship
     * @param rid1 the Repository ID (RID) of the IGC asset at the first endpoint of the relationship
     * @param rid2 the Repository ID (RID) of the IGC asset at the second endpoint of the relationship
     * @param relationshipType the OMRS type name of the relationship
     */
    public IGCRelationshipGuid(String metadataCollectionId,
                               String assetType1,
                               String assetType2,
                               String prefix1,
                               String prefix2,
                               String rid1,
                               String rid2,
                               String relationshipType) {
        super(metadataCollectionId);
        this.assetType1 = assetType1;
        this.assetType2 = assetType2;
        this.generatedPrefix1 = prefix1;
        this.generatedPrefix2 = prefix2;
        this.rid1 = rid1;
        this.rid2 = rid2;
        this.relationshipType = relationshipType;
    }

    /**
     * Turn this IGC GUID into a unique String representation of the GUID.
     *
     * The string representation will be something like the following:
     * {@literal 5e74232d-92df-4b81-a401-b100dbfea73a:database_schema@6662c0f2.ee6a64fe.o1h6eveh1.gbvjvq0.ols3j6.0oadmdn8gknhjvmojr3pt<SchemaAssetType>database_schema@RDBST!6662c0f2.ee6a64fe.o1h6eveh1.gbvjvq0.ols3j6.0oadmdn8gknhjvmojr3pt}
     *
     * @return String
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append(assetType1);
        sb.append(IGC_TYPE_POSTFIX);
        if (generatedPrefix1 != null) {
            sb.append(generatedPrefix1);
            sb.append(GENERATED_TYPE_POSTFIX);
        }
        sb.append(rid1);
        sb.append(RELATIONSHIP_TYPE_PREFIX);
        sb.append(relationshipType);
        sb.append(RELATIONSHIP_TYPE_POSTFIX);
        sb.append(assetType2);
        sb.append(IGC_TYPE_POSTFIX);
        if (generatedPrefix2 != null) {
            sb.append(generatedPrefix2);
            sb.append(GENERATED_TYPE_POSTFIX);
        }
        sb.append(rid2);
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof IGCRelationshipGuid)) return false;
        IGCRelationshipGuid that = (IGCRelationshipGuid) obj;
        return Objects.equals(getMetadataCollectionId(), that.getMetadataCollectionId()) &&
                Objects.equals(getAssetType1(), that.getAssetType1()) &&
                Objects.equals(getAssetType2(), that.getAssetType2()) &&
                Objects.equals(getGeneratedPrefix1(), that.getGeneratedPrefix1()) &&
                Objects.equals(getGeneratedPrefix2(), that.getGeneratedPrefix2()) &&
                Objects.equals(getRid1(), that.getRid1()) &&
                Objects.equals(getRid2(), that.getRid2()) &&
                Objects.equals(getRelationshipType(), that.getRelationshipType());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(getMetadataCollectionId(), getRelationshipType(),
                getAssetType1(), getGeneratedPrefix1(), getRid1(),
                getAssetType2(), getGeneratedPrefix2(), getRid2());
    }

    /**
     * Attempt to create a new IGC GUID from the provided GUID.
     *
     * @param guid the IGC GUID representation of the provided GUID, or null if it does not appear to be a valid IGC GUID
     * @return IGCRelationshipGuid
     */
    public static IGCRelationshipGuid fromGuid(String guid) {

        if (guid == null) {
            return null;
        }

        int indexOfMetadataColPostfix = guid.indexOf(METADATA_COL_POSTFIX);
        int indexOfRelationshipTypePrefix = guid.indexOf(RELATIONSHIP_TYPE_PREFIX);
        int indexOfRelationshipTypePostfix = guid.indexOf(RELATIONSHIP_TYPE_POSTFIX);

        if (indexOfMetadataColPostfix > 0 && indexOfRelationshipTypePrefix > indexOfMetadataColPostfix && indexOfRelationshipTypePostfix > indexOfRelationshipTypePrefix) {

            String metadataCollectionId = guid.substring(0, indexOfMetadataColPostfix);
            String endpoint1 = guid.substring(indexOfMetadataColPostfix + 1, indexOfRelationshipTypePrefix);
            String endpoint2 = guid.substring(indexOfRelationshipTypePostfix + 1);
            String relationshipType = guid.substring(indexOfRelationshipTypePrefix + 1, indexOfRelationshipTypePostfix);

            String rid1 = getRidFromEndpoint(endpoint1);
            String rid2 = getRidFromEndpoint(endpoint2);
            String assetType1 = getAssetTypeFromEndpoint(endpoint1);
            String assetType2 = getAssetTypeFromEndpoint(endpoint2);
            String generatedPrefix1 = getGeneratedPrefixFromEndpoint(endpoint1);
            String generatedPrefix2 = getGeneratedPrefixFromEndpoint(endpoint2);

            return new IGCRelationshipGuid(
                    metadataCollectionId,
                    assetType1,
                    assetType2,
                    generatedPrefix1,
                    generatedPrefix2,
                    rid1,
                    rid2,
                    relationshipType);

        }
        return null;

    }

    /**
     * Retrieve the asset type from an endpoint component of a relationship GUID.
     *
     * @param endpoint the endpoint component of a relationship GUID
     * @return String
     */
    private static String getAssetTypeFromEndpoint(String endpoint) {
        int indexOfTypePostfix = endpoint.indexOf(IGC_TYPE_POSTFIX);
        return endpoint.substring(0, indexOfTypePostfix);
    }

    /**
     * Retrieve the generated prefix (if any) from an endpoint component of a relationship GUID.
     *
     * @param endpoint the endpoint component of a relationship GUID
     * @return String
     */
    private static String getGeneratedPrefixFromEndpoint(String endpoint) {
        int indexOfTypePostfix = endpoint.indexOf(IGC_TYPE_POSTFIX);
        int indexOfGeneratedPostfix = endpoint.indexOf(GENERATED_TYPE_POSTFIX);
        String generatedPrefix = null;
        if (indexOfGeneratedPostfix > 0) {
            generatedPrefix = endpoint.substring(indexOfTypePostfix + 1, indexOfGeneratedPostfix);
        }
        return generatedPrefix;
    }

    /**
     * Retrieve the RID from an endpoint component of a relationship GUID.
     *
     * @param endpoint the endpoint component of a relationship GUID
     * @return String
     */
    private static String getRidFromEndpoint(String endpoint) {
        int indexOfTypePostfix = endpoint.indexOf(IGC_TYPE_POSTFIX);
        int indexOfGeneratedPostfix = endpoint.indexOf(GENERATED_TYPE_POSTFIX);
        int lastIndex = Math.max(indexOfTypePostfix, indexOfGeneratedPostfix);
        return endpoint.substring(lastIndex + 1);
    }

    /**
     * Retrieve the IGC asset type component of the first endpoint of this IGC relationship GUID.
     *
     * @return String
     */
    public String getAssetType1() { return assetType1; }

    /**
     * Retrieve the IGC asset type component of the second endpoint of this IGC relationship GUID.
     *
     * @return String
     */
    public String getAssetType2() { return assetType2; }

    /**
     * Retrieve the generated prefix component of the first endpoint of this IGC relationship GUID, if it is for a
     * generated entity (or null if the entity is not generated).
     *
     * @return String
     */
    public String getGeneratedPrefix1() { return generatedPrefix1; }

    /**
     * Retrieve the generated prefix component of the second endpoint of this IGC relationship GUID, if it is for a
     * generated entity (or null if the entity is not generated).
     *
     * @return String
     */
    public String getGeneratedPrefix2() { return generatedPrefix2; }

    /**
     * Retrieve the IGC Repository ID (RID) component of the first endpoint of this IGC relationship GUID.
     *
     * @return String
     */
    public String getRid1() { return rid1; }

    /**
     * Retrieve the IGC Repository ID (RID) component of the second endpoint of this IGC relationship GUID.
     *
     * @return String
     */
    public String getRid2() { return rid2; }

    /**
     * Retrieve the type component of this IGC relationship GUID.
     *
     * @return String
     */
    public String getRelationshipType() { return relationshipType; }

    /**
     * Indicates whether this GUID represents one of the very rare relationship-level objects in IGC rather than the
     * more typical entity-to-entity property-based relationships.
     *
     * @return boolean
     */
    public boolean isRelationshipLevelObject() {
        return rid1 != null &&
                rid1.equals(rid2) &&
                ((generatedPrefix1 == null && generatedPrefix2 == null) || (generatedPrefix1 != null && generatedPrefix1.equals(generatedPrefix2)));
    }

}
