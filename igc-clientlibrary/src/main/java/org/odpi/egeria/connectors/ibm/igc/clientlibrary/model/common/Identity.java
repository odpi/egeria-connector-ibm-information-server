/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestConstants;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.errors.IGCConnectivityException;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.errors.IGCIOException;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchCondition;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchConditionSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * A representation of the unique characteristics of a particular asset, without relying on a unique ID string.
 * Two assets with the same identity may have different unique ID strings (eg. RIDs), but their Identity should
 * still be equal.
 */
public class Identity {

    private static final Logger log = LoggerFactory.getLogger(Identity.class);
    private static final String SEPARATOR_FOR_COMPONENTS = "::";
    private static final String SEPARATOR_FOR_TYPE_AND_NAME = "=";
    public static final String TYPE_PREFIX = "(";
    private static final String TYPE_POSTFIX = ")";

    public enum StringType { EXACT, STARTS_WITH, ENDS_WITH, CONTAINS }

    private static final String LOG_ADDING_SEARCH_CONDITION = "Adding search condition: {} {} {}";
    private static final String SEARCH_STARTS_WITH = "like {0}%";
    private static final String COURTESY_TITLE = "courtesy_title";
    private static final String GIVEN_NAME = "given_name";
    private static final String FULL_NAME = "full_name";

    private List<Reference> context;

    private String assetType;
    private String assetName;
    private String rid;
    private boolean partial;

    /**
     * Creates a new empty identity.
     *
     * @param partial true if this is only a partial identity, false otherwise
     */
    public Identity(boolean partial) {
        context = new ArrayList<>();
        assetType = "";
        assetName = "";
        rid = null;
        this.partial = partial;
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
        this(context, assetType, assetName, rid, false);
    }

    /**
     * Creates a new identity based on the identity characteristics provided.
     *
     * @param context the populated '_context' array from an asset
     * @param assetType the type of the asset
     * @param assetName the name of the asset
     * @param rid the Repository ID (RID) of the asset
     * @param partial true if this is only a partial identity, false otherwise
     */
    public Identity(List<Reference> context, String assetType, String assetName, String rid, boolean partial) {
        this(partial);
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
     * Returns true if this is a partial identity, or false if it is a full identity.
     *
     * @return boolean
     */
    public boolean isPartial() { return this.partial; }

    /**
     * Composes a unique identity string from the provided parameters.
     *
     * @param sb the object into which to compose the string
     * @param type the IGC asset type
     * @param name the name of the IGC asset
     * @param id the Repository ID (RID) of the IGC asset
     */
    private static void composeString(StringBuilder sb, String type, String name, String id) {
        sb.append(TYPE_PREFIX);
        sb.append(type);
        sb.append(TYPE_POSTFIX);
        sb.append(SEPARATOR_FOR_TYPE_AND_NAME);
        sb.append(name);
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
        } else {
            switch (ctxAssetType) {
                case "category":
                    pathSoFar = pathSoFar + "parent_category";
                    break;
                case "information_governance_policy":
                    pathSoFar = pathSoFar + "parent_policy";
                    break;
                case "data_class":
                    pathSoFar = pathSoFar + "parent_data_class";
                    break;
                default:
                    pathSoFar = pathSoFar + IGCRestConstants.getAssetTypeForSearch(ctxAssetType);
                    break;
            }
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
     */
    private void addSearchCondition(IGCSearchConditionSet igcSearchConditionSet,
                                    String propertyPath,
                                    String ctxType,
                                    String ctxName) {
        String ctxTypeToSearch = IGCRestConstants.getAssetTypeForSearch(ctxType);
        String nameProperty = "name";
        String operator = "=";
        if (ctxTypeToSearch.equals("group")) {
            nameProperty = "group_name";
        }
        String searchFor = (propertyPath == null ? nameProperty : propertyPath + "." + nameProperty);
        if (log.isDebugEnabled()) { log.debug(LOG_ADDING_SEARCH_CONDITION, searchFor, operator, ctxName); }
        IGCSearchCondition condition = new IGCSearchCondition(
                searchFor,
                operator,
                ctxName
        );
        igcSearchConditionSet.addCondition(condition);
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
                    addSearchCondition(igcSearchConditionSet, propertyPath, type, item.getName());
                }
            }
            // If the propertyPath is empty, we must be searching for just a file folder on its own
            if (propertyPath.equals("")) {
                // Add the immediate parent folder (cannot do the entire hierarchy in the case of a data file folder)
                String parentName = "";
                if (!dataFilePath.equals("")) {
                    parentName = dataFilePath;
                    int index = dataFilePath.lastIndexOf("/");
                    if (index > 0) {
                        parentName = dataFilePath.substring(index + 1);
                    }
                }
                if (!parentName.equals("")) {
                    if (log.isDebugEnabled()) { log.debug(LOG_ADDING_SEARCH_CONDITION, "parent_folder.name", "=", parentName); }
                    IGCSearchCondition cPath = new IGCSearchCondition("parent_folder.name", "=", parentName);
                    igcSearchConditionSet.addCondition(cPath);
                }
                // Add the 'host' element as 'host.name' (but only if we found one)
                if (!dataFileHost.equals("")) {
                    if (log.isDebugEnabled()) { log.debug(LOG_ADDING_SEARCH_CONDITION, "host.name", "=", dataFileHost); }
                    IGCSearchCondition cHost = new IGCSearchCondition("host.name", "=", dataFileHost);
                    igcSearchConditionSet.addCondition(cHost);
                }
            } else {
                // Otherwise, we are looking for some file-related asset within a folder, so add these further conditions
                // Concatenate the 'data_file_folder' elements into a string with '/' separators, and add as '...data_file.path'
                if (!dataFilePath.equals("")) {
                    if (log.isDebugEnabled()) { log.debug(LOG_ADDING_SEARCH_CONDITION, propertyPath + ".path", "=", dataFilePath); }
                    IGCSearchCondition cPath = new IGCSearchCondition(propertyPath + ".path", "=", dataFilePath);
                    igcSearchConditionSet.addCondition(cPath);
                }
                // Add the 'host' element as '...datafile.host.name'
                if (!dataFileHost.equals("")) {
                    if (log.isDebugEnabled()) { log.debug(LOG_ADDING_SEARCH_CONDITION, propertyPath + ".host.name", "=", dataFileHost); }
                    IGCSearchCondition cHost = new IGCSearchCondition(propertyPath + ".host.name", "=", dataFileHost);
                    igcSearchConditionSet.addCondition(cHost);
                }
            }
        } else if (IGCRestConstants.getUserTypes().contains(assetType)) {
            // Similarly, users are complicated due to how their unique name is constructed
            String[] nameTokens = assetName.split(" ");
            if (nameTokens.length == 1) {
                // If there is only a single token, use it for an OR-based startsWith search across courtesy title and
                // first name as this could only be a partial identity, and either could be in the first position
                IGCSearchCondition cTitle = new IGCSearchCondition(COURTESY_TITLE, SEARCH_STARTS_WITH, nameTokens[0]);
                IGCSearchCondition fName = new IGCSearchCondition(GIVEN_NAME, SEARCH_STARTS_WITH, nameTokens[0]);
                IGCSearchConditionSet nested = new IGCSearchConditionSet();
                nested.addCondition(cTitle);
                nested.addCondition(fName);
                nested.setMatchAnyCondition(true);
                igcSearchConditionSet.addNestedConditionSet(nested);
            } else if (nameTokens.length == 2) {
                // If there are two tokens, this could be...
                IGCSearchCondition cTitleAlone = new IGCSearchCondition(COURTESY_TITLE, SEARCH_STARTS_WITH, nameTokens[0]);
                IGCSearchCondition fNameAlone1 = new IGCSearchCondition(GIVEN_NAME, SEARCH_STARTS_WITH, nameTokens[0]);
                IGCSearchCondition fNameAlone2 = new IGCSearchCondition(GIVEN_NAME, SEARCH_STARTS_WITH, nameTokens[1]);
                IGCSearchCondition lNameAlone = new IGCSearchCondition("surname", SEARCH_STARTS_WITH, nameTokens[1]);
                IGCSearchCondition cTitleCombined = new IGCSearchCondition(COURTESY_TITLE, SEARCH_STARTS_WITH, assetName);
                IGCSearchCondition fNameCombined = new IGCSearchCondition(GIVEN_NAME, SEARCH_STARTS_WITH, assetName);
                IGCSearchConditionSet nested = new IGCSearchConditionSet();
                // ... a multi-word courtesy title alone
                IGCSearchConditionSet titleCombined = new IGCSearchConditionSet(cTitleCombined);
                nested.addNestedConditionSet(titleCombined);
                // ... a courtesy title and first name
                IGCSearchConditionSet titleAndFname = new IGCSearchConditionSet();
                titleAndFname.addCondition(cTitleAlone);
                titleAndFname.addCondition(fNameAlone2);
                titleAndFname.setMatchAnyCondition(false);
                nested.addNestedConditionSet(titleAndFname);
                // ... a multi-word first name
                IGCSearchConditionSet multiWordFname = new IGCSearchConditionSet();
                multiWordFname.addCondition(fNameCombined);
                nested.addNestedConditionSet(multiWordFname);
                // ... or a first name and last name
                IGCSearchConditionSet firstAndLast = new IGCSearchConditionSet();
                firstAndLast.addCondition(fNameAlone1);
                firstAndLast.addCondition(lNameAlone);
                firstAndLast.setMatchAnyCondition(false);
                nested.addNestedConditionSet(firstAndLast);
                nested.setMatchAnyCondition(true);
                igcSearchConditionSet.addNestedConditionSet(nested);
            } else if (nameTokens.length > 2) {
                // If there are three tokens, this could be...
                IGCSearchCondition cTitleCombined = new IGCSearchCondition(COURTESY_TITLE, SEARCH_STARTS_WITH, assetName);
                IGCSearchCondition fullNameCombined = new IGCSearchCondition(FULL_NAME, SEARCH_STARTS_WITH, assetName);
                IGCSearchCondition cTitleAlone = new IGCSearchCondition(COURTESY_TITLE, SEARCH_STARTS_WITH, nameTokens[0]);
                StringBuilder combinedName = new StringBuilder();
                for (int i = 1; i < nameTokens.length; i++) {
                    combinedName.append(nameTokens[i]).append(" ");
                }
                // (remove the last space)
                combinedName.deleteCharAt(combinedName.length() - 1);
                IGCSearchCondition fullNameAlone = new IGCSearchCondition(FULL_NAME, SEARCH_STARTS_WITH, combinedName.toString());
                IGCSearchConditionSet nested = new IGCSearchConditionSet();
                // ... only a courtesy title, comprised of multi-words (startsWith)
                IGCSearchConditionSet titleOnly = new IGCSearchConditionSet(cTitleCombined);
                nested.addNestedConditionSet(titleOnly);
                // ... only a full name, comprised of multi-word first or last names (startsWith)
                IGCSearchConditionSet nameOnly = new IGCSearchConditionSet(fullNameCombined);
                nested.addNestedConditionSet(nameOnly);
                // ... a single courtesy title with the rest the full name (startsWith)
                IGCSearchConditionSet titleAndName = new IGCSearchConditionSet();
                titleAndName.addCondition(cTitleAlone);
                titleAndName.addCondition(fullNameAlone);
                titleAndName.setMatchAnyCondition(false);
                nested.addNestedConditionSet(titleAndName);
                nested.setMatchAnyCondition(true);
                igcSearchConditionSet.addNestedConditionSet(nested);
            }
        } else {
            // Build up the search criteria for all of the context first
            String propertyPath = "";
            for (int i = context.size() - 1; i >= 0; i--) {
                Reference item = context.get(i);
                String type = item.getType();
                propertyPath = getPropertyPath(assetType, type, propertyPath);
                addSearchCondition(igcSearchConditionSet, propertyPath, type, item.getName());
            }
        }

        // Then add the final condition for this asset itself
        if (!IGCRestConstants.getUserTypes().contains(assetType)) {
            addSearchCondition(igcSearchConditionSet, null, assetType, assetName);
        }

        return igcSearchConditionSet;

    }

    /**
     * Return true if the provided string appears to be an identity string (partial or complete), or false otherwise.
     *
     * @param candidate the string to test as an identity string
     * @return int the count of identifying characteristics of an identity string (max 6)
     */
    public static int isIdentityString(String candidate) {
        int charCount = 0;
        charCount += (candidate.contains(SEPARATOR_FOR_COMPONENTS) ? 2 : 0);
        charCount += (candidate.contains(SEPARATOR_FOR_TYPE_AND_NAME) ? 2 : 0);
        charCount += (candidate.contains(TYPE_PREFIX) ? 1 : 0);
        charCount += (candidate.contains(TYPE_POSTFIX) ? 1 : 0);
        return charCount;
    }

    /**
     * Builds an Identity based on an identity string (or null if unable to construct an Identity from the
     * string).
     *
     * @param identity the string representing a qualified identity
     * @param igcRestClient connectivity to an IGC environment
     * @param stringType the type of string from which to construct the identity
     * @return Identity
     * @see #toString()
     */
    public static Identity getFromString(String identity, IGCRestClient igcRestClient, StringType stringType) {
        return getFromString(identity, igcRestClient, stringType, true);
    }

    /**
     * Builds an Identity based on an identity string (or null if unable to construct an Identity from the
     * string).
     *
     * @param identity the string representing a qualified identity
     * @param igcRestClient connectivity to an IGC environment
     * @param stringType the type of string from which to construct the identity
     * @param warnOnNotFound indicates whether to log a warning (true) or not (false) in case the type inferred from
     *                       the identity cannot be found
     * @return Identity
     * @see #toString()
     */
    public static Identity getFromString(String identity, IGCRestClient igcRestClient, StringType stringType, boolean warnOnNotFound) {

        List<Reference> context = new ArrayList<>();

        String assetType = null;
        String assetName = null;
        String assetId = null;
        List<String> components = getComponentsOfIdentityString(identity);
        if (components.isEmpty()) {
            // If there are not multiple items in the identity string, try to parse just a single one out
            List<String> tokens = getTokensOfComponent(identity, igcRestClient);
            boolean nothingDone = parseTokensIntoContext(tokens, context);
            if (!context.isEmpty() && !nothingDone) {
                Reference item = popLastRefFromContext(context);
                assetType = item.getType();
                assetName = item.getName();
                assetId = item.getId();
            }
        } else {
            for (int i = 0; i < components.size(); i++) {
                String component = components.get(i);
                List<String> tokens = getTokensOfComponent(component, igcRestClient);
                boolean nothingDone = parseTokensIntoContext(tokens, context);
                if (i == components.size() - 1 && !context.isEmpty() && !nothingDone) {
                    Reference item = popLastRefFromContext(context);
                    assetType = item.getType();
                    assetName = item.getName();
                    assetId = item.getId();
                }
            }
        }

        Identity ident = null;
        try {
            if (igcRestClient != null) {
                String displayName = igcRestClient.getDisplayNameForType(assetType);
                if (displayName != null) {
                    if (stringType.equals(StringType.EXACT)) {
                        ident = new Identity(context, assetType, assetName);
                    } else {
                        ident = new Identity(context, assetType, assetName, assetId, true);
                    }
                }
            } else {
                if (stringType.equals(StringType.EXACT)) {
                    ident = new Identity(context, assetType, assetName);
                } else {
                    ident = new Identity(context, assetType, assetName, assetId, true);
                }
            }
        } catch (Exception e) {
            if (log.isWarnEnabled() && warnOnNotFound) { log.warn("Unable to find registered IGC type '{}' -- cannot construct an IGC identity.", assetType); }
        }
        return ident;

    }

    private static List<String> getComponentsOfIdentityString(String identity) {
        if (identity.contains(SEPARATOR_FOR_COMPONENTS)) {
            return Arrays.asList(identity.split(SEPARATOR_FOR_COMPONENTS));
        } else if (identity.contains(SEPARATOR_FOR_TYPE_AND_NAME)
                || (identity.contains(TYPE_PREFIX) && identity.contains(TYPE_POSTFIX))) {
            List<String> list = new ArrayList<>();
            list.add(identity);
            return list;
        } else {
            return Collections.emptyList();
        }
    }

    private static List<String> getTokensOfComponent(String component, IGCRestClient igcRestClient) {
        List<String> pair = new ArrayList<>();
        if (component.contains(SEPARATOR_FOR_TYPE_AND_NAME)) {
            String[] tokens = component.split(SEPARATOR_FOR_TYPE_AND_NAME);
            String type = getTypeFromComponentToken(tokens[0], igcRestClient);
            if (type != null) {
                pair.add(type);
                if (tokens.length == 2) {
                    String name = tokens[1];
                    if (name != null) {
                        pair.add(name);
                    }
                }
            }
        } else {
            String type = getTypeFromComponentToken(component, igcRestClient);
            if (type != null) {
                pair.add(type);
            }
        }
        return pair;
    }

    private static String getTypeFromComponentToken(String token, IGCRestClient igcRestClient) {
        if (token.contains(TYPE_PREFIX) && token.contains(TYPE_POSTFIX)) {
            // Only return the type name if it is one that we recognize (can resolve to a POJO class)
            String type = token.substring(token.indexOf(TYPE_PREFIX) + 1, token.lastIndexOf(TYPE_POSTFIX));
            try {
                Class<?> pojo = igcRestClient.getPOJOForType(type);
                if (pojo != null) {
                    return type;
                } else {
                    return null;
                }
            } catch (IGCIOException e) {
                log.warn("Unable to find type '{}', skipping from identity", type, e);
                return null;
            }
        } else {
            return null;
        }
    }

    private static boolean parseTokensIntoContext(List<String> tokens, List<Reference> context) {
        boolean nothingDone = false;
        if (tokens.size() == 2) {
            String type = tokens.get(0);
            String name = tokens.get(1);
            Reference item = new Reference(name, type);
            context.add(item);
        } else if (tokens.size() == 1) {
            // All we have is a type
            String type = tokens.get(0);
            Reference item = new Reference(null, type, null);
            context.add(item);
        } else {
            // We have nothing -- no type, no name
            nothingDone = true;
        }
        return nothingDone;
    }

    private static Reference popLastRefFromContext(List<Reference> context) {
        Reference item = context.get(context.size() - 1);
        context.remove(context.size() - 1);
        return item;
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
            sb.append(SEPARATOR_FOR_COMPONENTS);
        }
        composeString(sb, assetType, assetName, rid);
        return sb.toString();
    }

}
