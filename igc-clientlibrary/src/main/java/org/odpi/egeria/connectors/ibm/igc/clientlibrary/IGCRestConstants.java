/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.clientlibrary;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.types.TypeProperty;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.types.TypeReference;

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
    public static final String IGC_REST_BASE_MODEL_PKG = "org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base";

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

    private static final Set<String> QUALIFY_PROPERTIES = createQualifyProperties();
    private static Set<String> createQualifyProperties() {
        Set<String> set = new HashSet<>();
        set.add("name");
        set.add("type");
        set.add("url");
        set.add("id");
        set.add("context");
        return set;
    }

    private static final Map<String, String> BASIC_TYPE_TO_JAVA_TYPE = createBasicTypeToJavaType();
    private static Map<String, String> createBasicTypeToJavaType() {
        Map<String, String> map = new HashMap<>();
        map.put("string", "String");
        map.put("boolean", "Boolean");
        map.put("datetime", "Date");
        map.put("number", "Number");
        map.put("enum", "String");
        return map;
    }

    private static final Set<String> IGNORE_PROPERTIES = createIgnoreProperties();
    private static Set<String> createIgnoreProperties() {
        Set<String> set = new HashSet<>();
        set.add(null);
        set.add("null");
        set.add("notes");
        set.add("assigned_external_assets");
        set.add("implemented_by_external_assets");
        set.add("governs_external_assets");
        return set;
    }

    private static final Set<String> IGNORE_TYPES = createIgnoreTypes();
    private static Set<String> createIgnoreTypes() {
        Set<String> set = new HashSet<>();
        return set;
    }

    private static final List<String> SUPER_TYPES = createSuperTypes();
    private static List<String> createSuperTypes() {
        List<String> list = new ArrayList<>();
        list.add("main_object");
        list.add("information_asset");
        list.add("host");
        list.add("datagroup");
        list.add("data_item");
        list.add("data_item_definition");
        list.add("classificationenabledgroup");
        list.add("reportobject");
        return list;
    }

    private static final Map<String, String> SUB_TYPE_TO_SUPER_TYPE = createSubTypeToSuperType();
    private static Map<String, String> createSubTypeToSuperType() {
        Map<String, String> map = new HashMap<>();
        map.put("main_object", "reference");
        map.put("data_policy", "main_object");
        map.put("host_(engine)", "host");
        map.put("host", "information_asset");
        map.put("Rule_Execution_Result", "information_asset");
        map.put("customattributedef", "information_asset");
        map.put("information_asset", "main_object");
        map.put("database_alias", "datagroup");
        map.put("database_table", "datagroup");
        map.put("data_file_record", "datagroup");
        map.put("view", "datagroup");
        map.put("design_table", "datagroup");
        map.put("design_view", "datagroup");
        map.put("stored_procedure", "datagroup");
        map.put("design_stored_procedure", "datagroup");
        map.put("datagroup", "information_asset");
        map.put("ds_stage_column", "data_item");
        map.put("parameter", "data_item");
        map.put("routine_argument", "data_item");
        map.put("stage_type_detail", "data_item");
        map.put("transform_argument", "data_item");
        map.put("data_item", "information_asset");
        map.put("data_element", "data_item_definition");
        map.put("table_definition", "data_item_definition");
        map.put("data_item_definition", "data_item");
        map.put("column_definition", "data_field");
        map.put("data_field", "data_item");
        map.put("database_column", "classificationenabledgroup");
        map.put("data_file_field", "classificationenabledgroup");
        map.put("classificationenabledgroup", "data_field");
        map.put("bi_report_query_item", "reportobject");
        map.put("reportobject", "information_asset");
        return map;
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
     * Retrieve the set of properties that should be ignored for all assets.
     *
     * @return {@code Set<String>}
     */
    public static Set<String> getPropertiesToIgnore() { return IGNORE_PROPERTIES; }

    /**
     * Retrieve the list of IGC object super types, in order from the highest level of abstraction to lower levels
     * of granularity.
     *
     * @return {@code List<String>}
     */
    public static List<String> getSuperTypes() { return SUPER_TYPES; }

    /**
     * Retrieve the mapping of IGC object sub-types to granular super-types.
     *
     * @return {@code Map<String, String>}
     */
    public static Map<String, String> getSubTypeToSuperType() { return SUB_TYPE_TO_SUPER_TYPE; }

    /**
     * Retrieve the name of the Java type for the provided IGC type.
     *
     * @param property the IGC property for which to get the type
     * @return String
     */
    public static String getJavaTypeForProperty(TypeProperty property) {

        TypeReference typeReference = property.getType();
        String type = typeReference.getName();
        String nominalType = type;

        if (typeReference.getUrl() != null) {
            nominalType = "Reference";
        } else if (BASIC_TYPE_TO_JAVA_TYPE.containsKey(type)) {
            nominalType = BASIC_TYPE_TO_JAVA_TYPE.get(type);
        } else if (!BASIC_TYPE_TO_JAVA_TYPE.containsKey(type)) {
            return null;
        }

        String javaType;

        // When the maxCardinality is its default value (-1), allow for multiple...
        // ... UNLESS the data type is boolean (then there is only one value permitted)
        if (property.getMaxCardinality() < 0) {
            if (nominalType.equals("Reference")) {
                if (IGNORE_TYPES.contains(type)) {
                    javaType = "ItemList<Reference>";
                } else {
                    javaType = "ItemList<" + getClassNameForAssetType(type) + ">";
                }
            } else if (nominalType.equals("Boolean")) {
                javaType = nominalType;
            } else {
                javaType = "List<" + nominalType + ">";
            }
        } else if (nominalType.equals("Reference")) {
            // If there is only one, but it is a relationship, determine the correct class name for the type
            if (IGNORE_TYPES.contains(type)) {
                javaType = "Reference";
            } else {
                javaType = getClassNameForAssetType(type);
            }
        } else {
            // Otherwise it must be a primitive type, so take the earlier translation as-is
            javaType = nominalType;
        }

        return javaType;

    }

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
     * Converts an IGC type or property (something_like_this) into lower a camelcase name (somethingLikeThis).
     *
     * @param input the IGC type or property to convert into lower camelcase
     * @return String
     */
    public static String getLowerCamelCase(String input) {
        String cc = getCamelCase(input);
        return cc.substring(0, 1).toLowerCase() + cc.substring(1);
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

    /**
     * Retrieve the getter name for the specified property.
     *
     * @param propertyName the IGC property
     * @return String
     */
    public static String getGetterNameForProperty(String propertyName) {
        return "get" + getMethodNameForProperty(propertyName);
    }

    /**
     * Retrieve the setter name for the specified property.
     *
     * @param propertyName the IGC property
     * @return String
     */
    public static String getSetterNameForProperty(String propertyName) {
        return "set" + getMethodNameForProperty(propertyName);
    }

    /**
     * Retrieve the method name for the provided property.
     *
     * @param propertyName the IGC property
     * @return String
     */
    private static String getMethodNameForProperty(String propertyName) {
        String ccName;
        if (QUALIFY_PROPERTIES.contains(propertyName)) {
            ccName = IGCRestConstants.getCamelCase("the_" + propertyName);
        } else {
            ccName = IGCRestConstants.getCamelCase(propertyName);
        }
        return ccName;
    }

    private IGCRestConstants() { }

}
