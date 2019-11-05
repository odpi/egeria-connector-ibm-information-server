/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.clientlibrary.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestConstants;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.types.*;
import org.odpi.openmetadata.http.HttpHelper;
import org.springframework.web.util.HtmlUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Utility class to generate the IGC bean (POJO) classes that can be used for (de-)serialising the IGC REST API
 * JSON payloads.
 */
public class IGCBeanGenerator {

    private static final ObjectMapper mapper = new ObjectMapper();

    private static final String DIRECTORY = ""
            + "igc-clientlibrary" + File.separator
            + "src" + File.separator
            + "main" + File.separator
            + "java" + File.separator
            + "org" + File.separator
            + "odpi" + File.separator
            + "egeria" + File.separator
            + "connectors" + File.separator
            + "ibm" + File.separator
            + "igc" + File.separator
            + "clientlibrary" + File.separator
            + "model" + File.separator
            + "base";

    public static void main(String[] args) {

        if (args.length < 4) {
            System.out.println("Inadequate parameters provided.");
            printUsage();
            System.exit(1);
        }

        HttpHelper.noStrictSSL();

        IGCBeanGenerator generator = new IGCBeanGenerator(args[0], args[1], args[2], args[3]);
        generator.generateForAllIgcTypesInEnvironment();

    }

    private static void printUsage() {
        System.out.println("Usage: ");
        System.out.println("  IGCBeanGenerator hostname port username password");
    }

    private IGCRestClient igcRestClient;
    private TreeMap<String, String> typeToMainObjectClassNames;
    private TreeMap<String, String> typeToInformationAssetClassNames;

    private IGCBeanGenerator(String hostname, String port, String username, String password) {
        igcRestClient = new IGCRestClient(hostname, port, username, password);
        typeToMainObjectClassNames = new TreeMap<>();
        typeToInformationAssetClassNames = new TreeMap<>();
    }

    private void generateForAllIgcTypesInEnvironment() {

        // First ensure the target directory has been created / exists
        File dir = new File(DIRECTORY);
        if (!dir.exists()){
            System.out.println("Creating directory: " + DIRECTORY);
            if (!dir.mkdirs()) {
                System.err.println("Unable to create target directory: " + DIRECTORY);
            }
        }

        // Then generate the POJOs within that directory
        List<TypeHeader> types = igcRestClient.getTypes(mapper);
        for (TypeHeader igcType : types) {
            String type = igcType.getId();
            TypeDetails typeDetails = igcRestClient.getTypeDetails(type);
            createPOJOForType(typeDetails);
        }

        // Finally update the InformationAsset and MainObject classes with their subtypes
        System.out.println("Injecting subtype information into MainObject...");
        injectSubTypes(Paths.get(DIRECTORY + File.separator + "MainObject.java"), typeToMainObjectClassNames);
        System.out.println("Injecting subtype information into InformationAsset...");
        injectSubTypes(Paths.get(DIRECTORY + File.separator + "InformationAsset.java"), typeToInformationAssetClassNames);

        igcRestClient.disconnect();

    }

    private void createPOJOForType(TypeDetails typeDetails) {

        String packageName = IGCRestConstants.IGC_REST_BASE_MODEL_PKG;

        String id   = typeDetails.getId();
        String name = typeDetails.getName();

        System.out.println("Processing type: " + id);

        String className = IGCRestConstants.getClassNameForAssetType(id);

        // Write the file for any type that should not be ignored
        String filename = DIRECTORY + File.separator + className + ".java";
        try (BufferedWriter fs = new BufferedWriter(new FileWriter(filename))) {

            fs.append("/* SPDX-License-Identifier: Apache-2.0 */");
            fs.append(System.lineSeparator());
            fs.append("/* Copyright Contributors to the ODPi Egeria project. */");
            fs.append(System.lineSeparator());
            fs.append("package ").append(packageName).append(";");
            fs.append(System.lineSeparator());
            fs.append(System.lineSeparator());
            fs.append("import com.fasterxml.jackson.annotation.JsonAutoDetect;");
            fs.append(System.lineSeparator());
            fs.append("import com.fasterxml.jackson.annotation.JsonIgnoreProperties;");
            fs.append(System.lineSeparator());
            fs.append("import com.fasterxml.jackson.annotation.JsonInclude;");
            fs.append(System.lineSeparator());
            fs.append("import com.fasterxml.jackson.annotation.JsonTypeName;");
            fs.append(System.lineSeparator());
            fs.append("import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;");
            fs.append(System.lineSeparator());
            fs.append("import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.PUBLIC_ONLY;");
            fs.append(System.lineSeparator());

            TypeCharacteristics typeCharacteristics = new TypeCharacteristics(id, typeDetails.getViewInfo().getProperties());
            Collection<PropertyDetail> propertyDetails = typeCharacteristics.getPropertyDetails();

            if (!ALIAS_OBJECTS.containsKey(id)) {
                fs.append("import com.fasterxml.jackson.annotation.JsonProperty;");
                fs.append(System.lineSeparator());
                if (typeCharacteristics.getClassToExtend().equals("Reference")) {
                    fs.append("import " + IGCRestConstants.IGC_REST_COMMON_MODEL_PKG + ".Reference;");
                    fs.append(System.lineSeparator());
                }
                Set<String> dataTypes = typeCharacteristics.getDataTypes();
                if (dataTypes.contains("ItemList")) {
                    fs.append("import " + IGCRestConstants.IGC_REST_COMMON_MODEL_PKG + ".ItemList;");
                    fs.append(System.lineSeparator());
                }
                if (dataTypes.contains("Date")) {
                    fs.append("import java.util.Date;");
                    fs.append(System.lineSeparator());
                }
                if (dataTypes.contains("List")) {
                    fs.append("import java.util.List;");
                    fs.append(System.lineSeparator());
                }
            }

            fs.append(System.lineSeparator());
            fs.append(getClassHeading(name, id));
            fs.append("@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)");
            fs.append(System.lineSeparator());
            fs.append("@JsonInclude(JsonInclude.Include.NON_NULL)");
            fs.append(System.lineSeparator());
            fs.append("@JsonIgnoreProperties(ignoreUnknown=true)");
            fs.append(System.lineSeparator());
            fs.append("@JsonTypeName(\"").append(id).append("\")");
            fs.append(System.lineSeparator());

            if (ALIAS_OBJECTS.containsKey(id)) {
                fs.append("public class ").append(className).append(" extends ").append(IGCRestConstants.getClassNameForAssetType(ALIAS_OBJECTS.get(id))).append(" {");
                fs.append(System.lineSeparator()).append(System.lineSeparator());
            } else {

                String classToExtend = typeCharacteristics.getClassToExtend();

                // Only add the list of properties if this object isn't simply an alias for another
                fs.append("public class ").append(className).append(" extends ").append(classToExtend).append(" {");
                fs.append(System.lineSeparator()).append(System.lineSeparator());

                try {
                    // First output all of the members
                    for (PropertyDetail detail : propertyDetails) {
                        fs.append(detail.getMember());
                        fs.append(System.lineSeparator());
                    }
                    // Then the getters and setters for each
                    for (PropertyDetail detail : propertyDetails) {
                        fs.append(detail.getGetSet());
                        fs.append(System.lineSeparator());
                        fs.append(System.lineSeparator());
                    }
                } catch (IOException e) {
                    System.err.println("Unable to append property details.");
                    e.printStackTrace();
                }

                if (classToExtend.equals("InformationAsset")) {
                    typeToInformationAssetClassNames.put(id, className);
                } else if (classToExtend.equals("MainObject")) {
                    typeToMainObjectClassNames.put(id, className);
                }

            }

            fs.append("}");
            fs.append(System.lineSeparator());

        } catch (IOException e) {
            System.err.println("Unable to open file output: " + filename);
            e.printStackTrace();
        }

    }

    private String getClassHeading(String displayName, String typeName) {
        return "/**" +
                System.lineSeparator() +
                " * POJO for the {@code " +
                typeName +
                "} asset type in IGC, displayed as '{@literal " +
                displayName +
                "}' in the IGC UI." +
                System.lineSeparator() +
                " * <br><br>" +
                System.lineSeparator() +
                " * (this code has been created based on out-of-the-box IGC metadata types." +
                System.lineSeparator() +
                " *  If modifications are needed, eg. to handle custom attributes," +
                System.lineSeparator() +
                " *  extending from this class in your own custom class is the best approach.)" +
                System.lineSeparator() +
                " */" +
                System.lineSeparator();
    }

    private PropertyDetail getPropertyDetail(TypeProperty property) {

        PropertyDetail detail = null;

        String name = property.getName();
        // Skip properties that have no name or are of type 'external_asset_reference'
        if (name != null
                && !name.equals("null")
                && !IGNORE_PROPERTIES.contains(name)
                && !property.getType().getName().equals("external_asset_reference")) {
            detail = new PropertyDetail();
            //System.out.println(" ... adding property: " + property.getName());
            String javaType = IGCRestConstants.getJavaTypeForProperty(property);
            String propNameActual = property.getName();
            String propertyName = IGCRestConstants.getLowerCamelCase(propNameActual);
            if (RESERVED_WORDS.contains(propertyName)) {
                propertyName = "z" + propertyName;
            }
            detail.setJavaType(javaType);
            detail.setMember(getMemberDeclaration(property, propNameActual, propertyName, javaType));
            detail.setGetSet(getGetterAndSetter(property, propNameActual, propertyName, javaType));
        }

        return detail;

    }

    private String getMemberDeclaration(TypeProperty property, String propNameActual, String propertyName, String javaType) {
        StringBuilder declMember = new StringBuilder();
        List<ValidValue> validValues = property.getType().getValidValues();
        if (validValues != null && !validValues.isEmpty()) {
            declMember.append("    /**")
                    .append(System.lineSeparator())
                    .append("     * Valid values are:")
                    .append(System.lineSeparator())
                    .append("     * <ul>")
                    .append(System.lineSeparator());
            for (ValidValue value : validValues) {
                declMember.append("     *   <li>")
                        .append(HtmlUtils.htmlEscape(value.getId()))
                        .append(" (displayed in the UI as '")
                        .append(HtmlUtils.htmlEscape(value.getDisplayName()))
                        .append("')</li>")
                        .append(System.lineSeparator());
            }
            declMember.append("     * </ul>")
                    .append(System.lineSeparator())
                    .append("     */")
                    .append(System.lineSeparator());
        }
        declMember.append("    @JsonProperty(\"")
                .append(propNameActual)
                .append("\")");
        declMember.append(System.lineSeparator());
        declMember.append("    protected ")
                .append(javaType)
                .append(" ")
                .append(propertyName)
                .append(";");
        declMember.append(System.lineSeparator());
        return declMember.toString();
    }

    private String getGetterAndSetter(TypeProperty property, String propNameActual, String propertyName, String javaType) {
        StringBuilder getSetter = new StringBuilder();
        getSetter.append("    /**")
                .append(System.lineSeparator())
                .append("     * Retrieve the ").append("{@code ").append(propNameActual).append("}").append(" property (displayed as '{@literal ").append(property.getDisplayName()).append("}') of the object.")
                .append(System.lineSeparator())
                .append("     * @return {@code ").append(javaType).append("}")
                .append(System.lineSeparator())
                .append("     */");
        getSetter.append(System.lineSeparator());
        getSetter.append("    @JsonProperty(\"")
                .append(propNameActual)
                .append("\")");
        getSetter.append(System.lineSeparator());
        getSetter.append("    public ")
                .append(javaType)
                .append(" ")
                .append(IGCRestConstants.getGetterNameForProperty(propNameActual))
                .append("() { return this.")
                .append(propertyName)
                .append("; }");
        getSetter.append(System.lineSeparator());
        getSetter.append(System.lineSeparator());
        getSetter.append("    /**")
                .append(System.lineSeparator())
                .append("     * Set the ").append("{@code ").append(propNameActual).append("}").append(" property (displayed as {@code ").append(property.getDisplayName()).append("}) of the object.")
                .append(System.lineSeparator())
                .append("     * @param ").append(propertyName).append(" the value to set")
                .append(System.lineSeparator())
                .append("     */");
        getSetter.append(System.lineSeparator());
        getSetter.append("    @JsonProperty(\"")
                .append(propNameActual)
                .append("\")");
        getSetter.append(System.lineSeparator());
        getSetter.append("    public void ")
                .append(IGCRestConstants.getSetterNameForProperty(propNameActual))
                .append("(")
                .append(javaType)
                .append(" ")
                .append(propertyName)
                .append(") { this.")
                .append(propertyName)
                .append(" = ")
                .append(propertyName)
                .append("; }");
        return getSetter.toString();
    }

    private static final Set<String> IGNORE_PROPERTIES = createIgnoreProperties();
    private static final Set<String> RESERVED_WORDS = createReservedWords();
//    private static final Set<String> INFORMATION_ASSETS = createInformationAssets();
    private static final Map<String, String> ALIAS_OBJECTS = createAliasObjects();
    private static final Set<String> MAIN_OBJECT_PROPERTIES = createMainObjectProperties();

    private static Set<String> createIgnoreProperties() {
        Set<String> set = new HashSet<>();
        set.add("_name");
        set.add("_type");
        set.add("_url");
        set.add("_id");
        set.add("_context");
        set.add("notes");
        return set;
    }

    private static Set<String> createReservedWords() {
        Set<String> set = new HashSet<>();
        set.add("package");
        set.add("final");
        set.add("abstract");
        set.add("default");
        return set;
    }

    private static Map<String, String> createAliasObjects() {
        Map<String, String> map = new HashMap<>();
        map.put("host_(engine)", "host");
        return map;
    }

    private static Set<String> createMainObjectProperties() {
        Set<String> set = new HashSet<>();
        set.add("name");
        set.add("short_description");
        set.add("long_description");
        set.add("labels");
        set.add("stewards");
        set.add("assigned_to_terms");
        set.add("implements_rules");
        set.add("governed_by_rules");
        return set;
    }

/*    private static Set<String> createInformationAssets() {
        Set<String> set = new HashSet<>();
        set.add("term");
        set.add("category");
        set.add("information_governance_rule");
        set.add("information_governance_policy");
        set.add("data_class");
        set.add("host");
        set.add("database");
        set.add("database_alias");
        set.add("database_schema");
        set.add("database_table");
        set.add("view");
        set.add("database_column");
        set.add("stored_procedure");
        set.add("stored_procedure_parameter");
        set.add("data_file_folder");
        set.add("data_file");
        set.add("data_file_record");
        set.add("data_file_field");
        set.add("data_file_definition");
        set.add("data_file_definition_record");
        set.add("data_file_definition_field");
        set.add("amazon_s3_bucket");
        set.add("logical_data_model");
        set.add("subject_area");
        set.add("logical_entity");
        set.add("entity_attribute");
        set.add("physical_data_model");
        set.add("design_table");
        set.add("design_view");
        set.add("design_column");
        set.add("design_stored_procedure");
        set.add("design_stored_procedure_parameter");
        set.add("xml_schema_definition");
        set.add("xsd_element");
        set.add("xsd_attribute");
        set.add("xsd_complex_type");
        set.add("xsd_simple_type");
        set.add("xsd_element_group");
        set.add("xsd_attribute_group");
        set.add("mdm_model");
        set.add("physical_object");
        set.add("physical_object_attribute");
        set.add("member_type");
        set.add("attribute");
        set.add("composite_view");
        set.add("entity_type");
        set.add("attribute_type");
        set.add("attribute_type_field");
        set.add("application");
        set.add("object_type");
        set.add("method");
        set.add("input_parameter");
        set.add("output_value");
        set.add("file");
        set.add("stored_procedure_definition");
        set.add("inout_parameter");
        set.add("in_parameter");
        set.add("out_parameter");
        set.add("result_column");
        set.add("bi_server");
        set.add("bi_model");
        set.add("bi_collection");
        set.add("bi_collection_member");
        set.add("bi_cube");
        set.add("bi_report");
        set.add("bi_report_query");
        set.add("bi_report_query_item");
        set.add("data_rule_definition");
        set.add("data_rule_set_definition");
        set.add("data_rule");
        set.add("data_rule_set");
        set.add("metric");
        set.add("standardization_rule_set");
        set.add("match_specification");
        set.add("host_(engine)");
        set.add("dsjob");
        set.add("shared_container");
        set.add("table_definition");
        set.add("column_definition");
        set.add("stage");
        set.add("stage_column");
        set.add("ds_stage_column");
        set.add("stage_variable");
        set.add("dsstage_type");
        set.add("parameter_set");
        set.add("routine");
        set.add("machine_profile");
        set.add("mapping_project");
        set.add("mapping_specification");
        set.add("extension_mapping_document");
        set.add("blueprint");
        set.add("cdc_mapping_document");
        set.add("endpoint");
        return set;
    } */

    protected class TypeCharacteristics {

        // Default everything to a simple Reference as the base class
        private String extendClass = "Reference";
        private TreeMap<String, PropertyDetail> propertyDetails;
        private Set<String> dataTypes;

        TypeCharacteristics(String typeName, List<TypeProperty> properties) {
            propertyDetails = new TreeMap<>();
            dataTypes = new HashSet<>();
            for (TypeProperty property : properties) {
                PropertyDetail detail = getPropertyDetail(property);
                if (detail != null) {
                    addPropertyDetail(property.getName(), detail);
                    if (!typeName.equals("collection")) {
                        String propertyName = property.getName();
                        String propertyType = property.getType().getName();
                        if (propertyName.equals("in_collections") && propertyType.equals("collection")) {
                            // If the object can be put into a collection, and is not itself a collection, treat it as an InformationAsset
                            extendClass = "InformationAsset";
                        } else if (propertyName.equals("labels")
                                && propertyType.equals("label")
                                && !typeName.equals("main_object")
                                && !extendClass.equals("InformationAsset")) {
                            // If the object is not an InformationAsset, but can be assigned a label, treat it as a MainObject
                            extendClass = "MainObject";
                        }
                    }
                    dataTypes.add(detail.getJavaType());
                }
            }
            // Remove any pre-existing properties for InformationAsset / MainObject
            if (extendClass.equals("InformationAsset")) {
                for (String propertyName : IGCRestConstants.getModificationProperties()) {
                    removePropertyDetail(propertyName);
                }
                for (String propertyName : MAIN_OBJECT_PROPERTIES) {
                    removePropertyDetail(propertyName);
                }
            } else if (extendClass.equals("MainObject")) {
                for (String propertyName : MAIN_OBJECT_PROPERTIES) {
                    removePropertyDetail(propertyName);
                }
            }
            if (typeName.equals("information_asset")) {
                extendClass = "MainObject";
            }
        }

        void addPropertyDetail(String propertyName, PropertyDetail detail) {
            propertyDetails.put(propertyName, detail);
        }

        void removePropertyDetail(String propertyName) {
            propertyDetails.remove(propertyName);
        }

        Collection<PropertyDetail> getPropertyDetails() {
            return propertyDetails.values();
        }

        String getClassToExtend() {
            return extendClass;
        }

        Set<String> getDataTypes() {
            return dataTypes;
        }

    }

    protected class PropertyDetail {

        private String member;
        private String getSet;
        private String javaType;

        PropertyDetail() {
            // Nothing to do by default...
        }

        void setMember(String member) { this.member = member; }
        void setGetSet(String getSet) { this.getSet = getSet; }
        void setJavaType(String javaType) {
            if (javaType.startsWith("ItemList<")) {
                this.javaType = "ItemList";
            } else if (javaType.startsWith("List<")) {
                this.javaType = "List";
            } else {
                this.javaType = javaType;
            }
        }

        String getMember() { return this.member; }
        String getGetSet() { return this.getSet; }
        String getJavaType() { return this.javaType; }

    }

    private void injectSubTypes(Path path, Map<String, String> mappings) {

        try {
            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            int position;
            for (position = 0; position < lines.size(); position++) {
                String line = lines.get(position);
                if (line.startsWith("import com.fasterxml.jackson.annotation.JsonAutoDetect;")) {
                    break;
                }
            }
            position++;
            lines.add(position, "import com.fasterxml.jackson.annotation.JsonSubTypes;");
            for ( ; position < lines.size(); position++) {
                String line = lines.get(position);
                if (line.startsWith("@JsonAutoDetect")) {
                    break;
                }
            }
            position++;
            lines.add(position, "@JsonSubTypes({");
            position++;
            for (Map.Entry<String, String> entry : mappings.entrySet()) {
                String igcType = entry.getKey();
                String javaClass = entry.getValue();
                lines.add(position, "        @JsonSubTypes.Type(value = " + javaClass + ".class, name = \"" + igcType + "\"),");
                position++;
            }
            lines.add(position, "})");
            Files.write(path, lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.err.println("Unable to inject subtypes into file: " + path);
            e.printStackTrace();
        }

    }

}
