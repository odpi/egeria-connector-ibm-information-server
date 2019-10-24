/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.clientlibrary;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Set of constants for IGC REST API usage.
 */
public class IGCRestConstants {

    public static final String MOD_CREATED_BY = "created_by";
    public static final String MOD_CREATED_ON = "created_on";
    public static final String MOD_MODIFIED_BY = "modified_by";
    public static final String MOD_MODIFIED_ON = "modified_on";

    public static final Pattern INVALID_NAMING_CHARS = Pattern.compile("[()/&$\\- ]");

    public static final String IGC_REST_COMMON_MODEL_PKG = "org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common";
    public static final String IGC_REST_GENERATED_MODEL_PKG = "org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.generated";

    private static final List<String> MODIFICATION_DETAILS = createModificationDetails();

    private static List<String> createModificationDetails() {
        ArrayList<String> modDetails = new ArrayList<>();
        modDetails.add(MOD_CREATED_BY);
        modDetails.add(MOD_CREATED_ON);
        modDetails.add(MOD_MODIFIED_BY);
        modDetails.add(MOD_MODIFIED_ON);
        return Collections.unmodifiableList(modDetails);
    }

    private static final Map<String, String> NON_UNIQUE_CLASSNAMES = createNonUniqueClassnames();

    private static Map<String, String> createNonUniqueClassnames() {
        Map<String, String> map = new HashMap<>();
        map.put("valid_value_list", "ValidValueList");
        map.put("validvaluelist", "ValidValueList2");
        map.put("valid_value_range", "ValidValueRange");
        map.put("validvaluerange", "ValidValueRange2");
        map.put("parameter_set", "ParameterSet");
        map.put("parameterset", "ParameterSet2");
        map.put("function_call", "FunctionCall");
        map.put("functioncall", "FunctionCall2");
        return Collections.unmodifiableMap(map);
    }

    private static final List<String> FILE_TYPES = createFileTypes();

    private static List<String> createFileTypes() {
        ArrayList<String> fileTypes = new ArrayList<>();
        fileTypes.add("data_file_field");
        fileTypes.add("data_file_record");
        fileTypes.add("data_file");
        fileTypes.add("data_file_folder");
        return Collections.unmodifiableList(fileTypes);
    }

    private static final Map<String, String> IMAM_TYPE_TO_IGC_TYPE = createImamTypetoIgcType();

    private static Map<String, String> createImamTypetoIgcType() {
        Map<String, String> map = new HashMap<>();
        map.put("HostSystem", "host");
        map.put("DataConnection", "data_connection");
        map.put("DataFileFolder", "data_file_folder");
        map.put("DataFile", "data_file");
        map.put("DataCollection_in_DataFile", "data_file_record");
        map.put("DataField_in_DataFile", "data_file_field");
        map.put("Database", "database");
        map.put("DataSchema", "database_schema");
        map.put("DataCollection_in_Database", "database_table");
        map.put("DataField_in_Database", "database_column");
        return Collections.unmodifiableMap(map);
    }

    private static final Set<String> RELATIONSHIP_LEVEL_TYPES = createRelationshipLevelTypes();

    private static Set<String> createRelationshipLevelTypes() {
        Set<String> set = new HashSet<>();
        set.add("classification");
        return Collections.unmodifiableSet(set);
    }

    /**
     * Retrieve a list of the modification detail properties used by the IGC REST API.
     *
     * @return {@code List<String>}
     */
    public static List<String> getModificationProperties() { return MODIFICATION_DETAILS; }

    /**
     * Retrieve a list of the asset types that deal with files in some way.
     *
     * @return {@code List<String>}
     */
    public static List<String> getFileTypes() { return FILE_TYPES; }

    /**
     * Retrieve a mapping from IMAM type name to IGC type name.
     *
     * @return {@code Map<String, String>} where the key is the IMAM type name and value is IGC type name
     */
    public static Map<String, String> getImamTypeToIgcType() { return IMAM_TYPE_TO_IGC_TYPE; }

    /**
     * Retrieve a set of IGC asset types that are actual relationship-level assets (very rare).
     *
     * @return {@code Set<String>}
     */
    public static Set<String> getRelationshipLevelTypes() { return RELATIONSHIP_LEVEL_TYPES; }

    /**
     * Retrieve the name of a POJO class from the IGC asset type name.
     *
     * @param igcAssetType the name of the IGC asset type for which to retrieve a POJO classname
     * @return String
     */
    public static String getClassNameForAssetType(String igcAssetType) {
        if (NON_UNIQUE_CLASSNAMES.containsKey(igcAssetType)) {
            return NON_UNIQUE_CLASSNAMES.get(igcAssetType);
        } else {
            return getCamelCase(igcAssetType);
        }
    }

    /**
     * Converts an IGC type or property (something_like_this) into a camelcase class name (SomethingLikeThis).
     *
     * @param input the IGC type or property to convert into camelcase
     * @return String
     */
    public static String getCamelCase(String input) {
        Matcher m = INVALID_NAMING_CHARS.matcher(input);
        String invalidsRemoved = m.replaceAll("_");
        StringBuilder sb = new StringBuilder(invalidsRemoved.length());
        for (String token : invalidsRemoved.split("_")) {
            if (token.length() > 0) {
                sb.append(token.substring(0, 1).toUpperCase());
                sb.append(token.substring(1).toLowerCase());
            }
        }
        return sb.toString();
    }

    /**
     * Translates the type of asset into what should be used for searching.
     * This is necessary for certain types that are actually pseudo-aliases for other types, to ensure all
     * properties of that asset can be retrieved.
     *
     * @param assetType the asset type for which to retrieve the search type
     * @return String
     */
    public static String getAssetTypeForSearch(String assetType) {
        String typeForSearch;
        switch(assetType) {
            case "host_(engine)":
                typeForSearch = "host";
                break;
            case "non_steward_user":
            case "steward_user":
                typeForSearch = "user";
                break;
            default:
                typeForSearch = assetType;
                break;
        }
        return typeForSearch;
    }

    private IGCRestConstants() { }

}
