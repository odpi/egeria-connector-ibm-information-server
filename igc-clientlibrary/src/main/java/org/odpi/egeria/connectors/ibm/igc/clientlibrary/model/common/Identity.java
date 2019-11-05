/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestConstants;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchCondition;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchConditionSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A representation of the unique characteristics of a particular asset, without relying on a unique ID string.
 * Two assets with the same identity may have different unique ID strings (eg. RIDs), but their Identity should
 * still be equal.
 */
public class Identity {

    private static final Logger log = LoggerFactory.getLogger(Identity.class);

    private List<Reference> context;

    private String assetType;
    private String assetName;
    private String rid;

    /**
     * Creates a new empty identity.
     */
    public Identity() {
        context = new ArrayList<>();
        assetType = "";
        assetName = "";
        rid = null;
    }

    /**
     * Creates a new identity based on the identity characteristics provided.
     *
     * @param context the populated '_context' array from an asset
     * @param assetType the type of the asset
     * @param assetName the name of the asset
     */
    public Identity(List<Reference> context, String assetType, String assetName) {
        this(context, assetType, assetName, null);
    }

    /**
     * Creates a new identity based on the identity characteristics provided.
     * (Also keeps a record of Repository ID (RID) for potential efficiency of parent traversal)
     *
     * @param context the populated '_context' array from an asset
     * @param assetType the type of the asset
     * @param assetName the name of the asset
     * @param rid the Repository ID (RID) of the asset
     */
    public Identity(List<Reference> context, String assetType, String assetName, String rid) {
        this();
        this.context = context;
        this.assetType = assetType;
        this.assetName = assetName;
        this.rid = rid;
    }

    /**
     * Returns the Identity of the parent that contains the entity identified by this Identity.
     * <br><br>
     * If there is no parent identity (ie. this Identity represents a root-level asset with no
     * container above it), will return null.
     *
     * @return Identity
     */
    public Identity getParentIdentity() {
        Identity parent = null;
        if (!context.isEmpty()) {
            int lastIndex = context.size() - 1;
            Reference endOfCtx = context.get(lastIndex);
            List<Reference> parentCtx = context.subList(0, lastIndex);
            parent = new Identity(parentCtx, endOfCtx.getType(), endOfCtx.getName(), endOfCtx.getId());
        }
        return parent;
    }

    /**
     * Returns the Identity of the ultimate parent (top-level ancestor) that contains the entity
     * identified by this Identity.
     * <br><br>
     * If there is no parent identity (ie. this Identity represents a root-level asset with no
     * container above it), will return itself.
     *
     * @return Identity
     */
    public Identity getUltimateParentIdentity() {
        Identity ultimate = this;
        if (!context.isEmpty()) {
            Reference top = context.get(0);
            ultimate = new Identity(new ArrayList<>(), top.getType(), top.getName(), top.getId());
        }
        return ultimate;
    }

    /**
     * Returns the Repository ID (RID) of this identity (if available), or null.
     *
     * @return String
     */
    public String getRid() { return this.rid; }

    /**
     * Returns the type of the asset represented by this identity.
     *
     * @return String
     */
    public String getAssetType() { return this.assetType; }

    /**
     * Returns the (display) name of the asset represented by this identity.
     *
     * @return String
     */
    public String getName() { return this.assetName; }

    /**
     * Indicates whether the asset type requires its Repository ID (RID) in order to be unique. In other words, the name
     * and context of the asset type are insufficient to make the identity unique.
     *
     * @param assetType the IGC asset type to check
     * @return boolean
     */
    private static boolean requiresRidToBeUnique(String assetType) {
        return (assetType.equals("data_connection"));
    }

    /**
     * Composes a unique identity string from the provided parameters.
     *
     * @param sb the object into which to compose the string
     * @param type the IGC asset type
     * @param name the name of the IGC asset
     * @param id the Repository ID (RID) of the IGC asset
     */
    private static void composeString(StringBuilder sb, String type, String name, String id) {
        sb.append("(");
        sb.append(type);
        sb.append(")=");
        sb.append(name);
        if (requiresRidToBeUnique(type)) {
            sb.append("_");
            sb.append(id);
        }
    }

    /**
     * Determine the property path to use (in a search) based on the provided information.
     *
     * @param assetType the asset type being searched
     * @param ctxAssetType the type of asset within the context of the asset being searched
     * @param pathSoFar the path that has been built-up so far
     * @return String
     */
    private static String getPropertyPath(String assetType, String ctxAssetType, String pathSoFar) {
        if (pathSoFar.length() > 0) {
            pathSoFar = pathSoFar + ".";
        }
        if (assetType.equals("database_column") && (ctxAssetType.equals("database_table") || ctxAssetType.equals("view"))) {
            pathSoFar = pathSoFar + "database_table_or_view";
        } else if (ctxAssetType.equals("category")) {
            pathSoFar = pathSoFar + "parent_category";
        } else {
            pathSoFar = pathSoFar + IGCRestConstants.getAssetTypeForSearch(ctxAssetType);
        }
        return pathSoFar;
    }

    /**
     * Add a specific search condition.
     *
     * @param igcSearchConditionSet the condition set to which to append
     * @param propertyPath the property path for the search condition
     * @param ctxType the type of the context asset for which to add the search condition
     * @param ctxName the name of the context asset for which to add the search condition
     * @param ctxId the RID of the context asset for which to add the search condition (only needed if required to
     *              uniquely identify that type of asset)
     */
    private void addSearchCondition(IGCSearchConditionSet igcSearchConditionSet,
                                    String propertyPath,
                                    String ctxType,
                                    String ctxName,
                                    String ctxId) {
        if (requiresRidToBeUnique(ctxType)) {
            if (log.isDebugEnabled()) { log.debug("Adding search condition: {} {} {}", (propertyPath == null ? "_id" : propertyPath), "=", ctxId); }
            IGCSearchCondition condition = new IGCSearchCondition(
                    (propertyPath == null ? "_id" : propertyPath),
                    "=",
                    ctxId
            );
            igcSearchConditionSet.addCondition(condition);
        } else {
            if (log.isDebugEnabled()) { log.debug("Adding search condition: {} {} {}", (propertyPath == null ? "name" : propertyPath + ".name"), "=", ctxName); }
            IGCSearchCondition condition = new IGCSearchCondition(
                    (propertyPath == null ? "name" : propertyPath + ".name"),
                    "=",
                    ctxName
            );
            igcSearchConditionSet.addCondition(condition);
        }
    }

    /**
     * Composes the search criteria necessary to retrieve an IGC object with this identity, without relying
     * on its RID.
     *
     * @return IGCSearchConditionSet
     */
    public final IGCSearchConditionSet getSearchCriteria() {

        // We need to break the qualified name down into its constituent parts and add each as a search condition
        // (setting the entire set as its own nested condition set)
        IGCSearchConditionSet igcSearchConditionSet = new IGCSearchConditionSet();
        igcSearchConditionSet.setMatchAnyCondition(false);

        // If this is a file-related entity, the search criteria is not as straightforward as otherwise
        if (IGCRestConstants.getFileTypes().contains(assetType)) {
            String propertyPath = "";
            String dataFilePath = "";
            String dataFileHost = "";
            for (int i = context.size() - 1; i >= 0; i--) {
                Reference item = context.get(i);
                String type = item.getType();
                if (type.equals("host_(engine)")) {
                    dataFileHost = item.getName();
                } else if (type.equals("data_file_folder")) {
                    String path = item.getName();
                    if (path.equals("/")) {
                        dataFilePath = "/" + dataFilePath;
                    } else {
                        dataFilePath = path + (dataFilePath.equals("") ? "" : "/" + dataFilePath);
                    }
                } else {
                    // Add context up as conditions until we hit 'data_file_folder' elements
                    propertyPath = getPropertyPath(assetType, type, propertyPath);
                    addSearchCondition(igcSearchConditionSet, propertyPath, type, item.getName(), item.getId());
                }
            }
            // If the propertyPath is empty, we must be searching for just a file folder on its own
            if (propertyPath.equals("")) {
                // Add the immediate parent folder (cannot do the entire hierarchy in the case of a data file folder
                int index = dataFilePath.lastIndexOf("/");
                if (index > 0) {
                    String parentName = dataFilePath.substring(index + 1);
                    if (log.isDebugEnabled()) { log.debug("Adding search condition: parent_folder.name {} {}", "=", parentName); }
                    IGCSearchCondition cPath = new IGCSearchCondition("parent_folder.name", "=", parentName);
                    igcSearchConditionSet.addCondition(cPath);
                }
                // Add the 'host' element as 'host.name'
                if (log.isDebugEnabled()) { log.debug("Adding search condition: host.name {} {}", "=", dataFileHost); }
                IGCSearchCondition cHost = new IGCSearchCondition("host.name", "=", dataFileHost);
                igcSearchConditionSet.addCondition(cHost);
            } else {
                // Otherwise, we are looking for some file-related asset within a folder, so add these further conditions
                // Concatenate the 'data_file_folder' elements into a string with '/' separators, and add as '...data_file.path'
                if (log.isDebugEnabled()) { log.debug("Adding search condition: {} {} {}", propertyPath + ".path", "=", dataFilePath); }
                IGCSearchCondition cPath = new IGCSearchCondition(propertyPath + ".path", "=", dataFilePath);
                igcSearchConditionSet.addCondition(cPath);
                // Add the 'host' element as '...datafile.host.name'
                if (log.isDebugEnabled()) { log.debug("Adding search condition: {} {} {}", propertyPath + ".host.name", "=", dataFileHost); }
                IGCSearchCondition cHost = new IGCSearchCondition(propertyPath + ".host.name", "=", dataFileHost);
                igcSearchConditionSet.addCondition(cHost);
            }
        } else {
            // Build up the search criteria for all of the context first
            String propertyPath = "";
            for (int i = context.size() - 1; i >= 0; i--) {
                Reference item = context.get(i);
                String type = item.getType();
                propertyPath = getPropertyPath(assetType, type, propertyPath);
                addSearchCondition(igcSearchConditionSet, propertyPath, type, item.getName(), item.getId());
            }
        }

        // Then add the final condition for this asset itself
        addSearchCondition(igcSearchConditionSet, null, assetType, assetName, rid);

        return igcSearchConditionSet;

    }

    /**
     * Builds an Identity based on an identity string (or null if unable to construct an Identity from the
     * string).
     *
     * @param identity the string representing a qualified identity
     * @param igcRestClient connectivity to an IGC environment
     * @return Identity
     * @see #toString()
     */
    public static Identity getFromString(String identity, IGCRestClient igcRestClient) {

        List<Reference> context = new ArrayList<>();

        String assetType = null;
        String assetName = null;
        String assetId = null;
        String[] components = identity.split("::");
        for (int i = 0; i < components.length; i++) {
            String component = components[i];
            String[] tokens = component.split("=");
            String type = tokens[0].substring(1, tokens[0].length() - 1);
            String name = tokens[1];
            String id = null;
            if (requiresRidToBeUnique(type)) {
                id = name.split("_")[1];
            }
            if (i == components.length - 1) {
                assetType = type;
                assetName = name;
                assetId = id;
            } else {
                Reference item = new Reference(name, type, id);
                context.add(item);
            }
        }

        Identity ident = null;
        try {
            Class pojo = igcRestClient.getPOJOForType(assetType);
            if (pojo != null) {
                ident = new Identity(context, assetType, assetName, assetId);
            }
        } catch (Exception e) {
            if (log.isErrorEnabled()) { log.error("Unable to find registered IGC type '{}' -- cannot construct an IGC identity.", assetType); }
        }
        return ident;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Identity)) return false;
        Identity that = (Identity) obj;
        return Objects.equals(toString(), that.toString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(context, assetName, getAssetType(), getRid());
    }

    /**
     * Returns a unique string representation of the identity.
     *
     * @return String
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Reference ref : context) {
            composeString(sb, ref.getType(), ref.getName(), ref.getId());
            sb.append("::");
        }
        composeString(sb, assetType, assetName, rid);
        return sb.toString();
    }

}
