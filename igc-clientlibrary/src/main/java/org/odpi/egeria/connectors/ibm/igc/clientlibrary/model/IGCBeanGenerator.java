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
            + "model" + File.separator;
    private static final String COMMON_DIRECTORY = DIRECTORY + File.separator + "common";
    private static final String BASE_DIRECTORY = DIRECTORY + File.separator + "base";

    public static void main(String[] args) {

        if (args.length < 4) {
            System.out.println("Inadequate parameters provided.");
            printUsage();
            System.exit(1);
        }

        HttpHelper.noStrictSSL();

        IGCBeanGenerator generator = new IGCBeanGenerator(args[0], args[1], args[2], args[3]);
        generator.generateSuperTypes();
        generator.generateForAllIgcTypesInEnvironment();

    }

    private static void printUsage() {
        System.out.println("Usage: ");
        System.out.println("  IGCBeanGenerator hostname port username password");
    }

    private IGCRestClient igcRestClient;
    private TreeMap<String, String> typeToReferenceClassNames;
    private TreeMap<String, String> typeToMainObjectClassNames;
    private TreeMap<String, String> typeToInformationAssetClassNames;
    private TreeMap<String, String> typeToDatagroupClassNames;
    private TreeMap<String, String> typeToDataItemClassNames;
    private TreeMap<String, String> typeToDataItemDefinitionClassNames;

    private Map<String, Set<String>> superTypeToProperties;

    private IGCBeanGenerator(String hostname, String port, String username, String password) {
        igcRestClient = new IGCRestClient(hostname, port, username, password);
        typeToReferenceClassNames = new TreeMap<>();
        typeToMainObjectClassNames = new TreeMap<>();
        typeToInformationAssetClassNames = new TreeMap<>();
        typeToDatagroupClassNames = new TreeMap<>();
        typeToDataItemClassNames = new TreeMap<>();
        typeToDataItemDefinitionClassNames = new TreeMap<>();
        superTypeToProperties = new HashMap<>();
    }

    private void generateSuperTypes() {

        // First ensure the target directory has been created / exists
        File dir = new File(BASE_DIRECTORY);
        if (!dir.exists()){
            System.out.println("Creating directory: " + BASE_DIRECTORY);
            if (!dir.mkdirs()) {
                System.err.println("Unable to create target directory: " + BASE_DIRECTORY);
            }
        }

        // Before generating any other types, generate the supertypes from which they will extend
        // and as part of this keep a list of their properties, so any classes that extend them can skip
        // including the same properties and getter / setter methods as overrides
        for (String typeName : SUPER_TYPES) {
            TypeDetails details = igcRestClient.getTypeDetails(typeName);
            createPOJOForType(details);
        }

    }

    private void generateForAllIgcTypesInEnvironment() {

        // Then generate the POJOs within that directory
        List<TypeHeader> types = igcRestClient.getTypes(mapper);
        for (TypeHeader igcType : types) {
            String type = igcType.getId();
            // No need to re-generate the supertypes
            if (!SUPER_TYPES.contains(type)) {
                TypeDetails typeDetails = igcRestClient.getTypeDetails(type);
                createPOJOForType(typeDetails);
            }
        }

        // Finally update the supertypes with their subtypes
        System.out.println("Injecting subtype information into MainObject...");
        injectSubTypes(Paths.get(BASE_DIRECTORY + File.separator + "MainObject.java"), typeToMainObjectClassNames);
        System.out.println("Injecting subtype information into InformationAsset...");
        injectSubTypes(Paths.get(BASE_DIRECTORY + File.separator + "InformationAsset.java"), typeToInformationAssetClassNames);
        System.out.println("Injecting subtype information into Datagroup...");
        injectSubTypes(Paths.get(BASE_DIRECTORY + File.separator + "Datagroup.java"), typeToDatagroupClassNames);
        System.out.println("Injecting subtype information into DataItem...");
        injectSubTypes(Paths.get(BASE_DIRECTORY + File.separator + "DataItem.java"), typeToDataItemClassNames);
        System.out.println("Injecting subtype information into DataItemDefinition...");
        injectSubTypes(Paths.get(BASE_DIRECTORY + File.separator + "DataItemDefinition.java"), typeToDataItemDefinitionClassNames);

        // Before injecting into Reference we need to remove any previous injections (since this file is not
        // actually generated)
        Path refPath = Paths.get(COMMON_DIRECTORY + File.separator + "Reference.java");
        removeInjectedSubtypes(refPath);
        System.out.println("Injecting subtype information into Reference...");
        injectSubTypes(refPath, typeToReferenceClassNames);

        // And do the same for any alias objects (after creating the mappings we need first)
        Map<String, Map<String, String>> superClassToSubTypeMappings = new HashMap<>();
        for (Map.Entry<String, String> entry : IGCRestConstants.getAliasObjects().entrySet()) {
            String subTypeName = entry.getKey();
            String superTypeName = entry.getValue();
            String superClassName = IGCRestConstants.getClassNameForAssetType(superTypeName);
            if (!superClassToSubTypeMappings.containsKey(superClassName)) {
                superClassToSubTypeMappings.put(superClassName, new HashMap<>());
            }
            superClassToSubTypeMappings.get(superClassName).put(subTypeName, IGCRestConstants.getClassNameForAssetType(subTypeName));
        }
        for (Map.Entry<String, Map<String, String>> entry : superClassToSubTypeMappings.entrySet()) {
            String superClassName = entry.getKey();
            Map<String, String> mappings = entry.getValue();
            Path aliasPath = Paths.get(BASE_DIRECTORY + File.separator + superClassName + ".java");
            injectSubTypes(aliasPath, mappings);
        }

        igcRestClient.disconnect();

    }

    private void createPOJOForType(TypeDetails typeDetails) {

        String packageName = IGCRestConstants.IGC_REST_BASE_MODEL_PKG;

        String id   = typeDetails.getId();
        String name = typeDetails.getName();

        System.out.println("Processing type: " + id);

        String className = IGCRestConstants.getClassNameForAssetType(id);

        // Write the file for any type that should not be ignored
        String filename = BASE_DIRECTORY + File.separator + className + ".java";
        try (BufferedWriter fs = new BufferedWriter(new FileWriter(filename))) {

            fs.append("/* SPDX-License-Identifier: Apache-2.0 */");
            fs.append(System.lineSeparator());
            fs.append("/* Copyright Contributors to the ODPi Egeria project. */");
            fs.append(System.lineSeparator());
            fs.append("package ").append(packageName).append(";");
            fs.append(System.lineSeparator());
            fs.append(System.lineSeparator());
            fs.append("import com.fasterxml.jackson.annotation.JsonTypeInfo;");
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

            if (!IGCRestConstants.getAliasObjects().containsKey(id)) {
                fs.append("import com.fasterxml.jackson.annotation.JsonProperty;");
                fs.append(System.lineSeparator());
                Set<String> dataTypes = typeCharacteristics.getDataTypes();
                if (typeCharacteristics.getClassToExtend().equals("Reference")
                        || dataTypes.contains("Reference")) {
                    fs.append("import " + IGCRestConstants.IGC_REST_COMMON_MODEL_PKG + ".Reference;");
                    fs.append(System.lineSeparator());
                }
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
            fs.append("@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property=\"_type\", visible=true, defaultImpl=").append(className).append(".class)");
            fs.append(System.lineSeparator());
            fs.append("@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)");
            fs.append(System.lineSeparator());
            fs.append("@JsonInclude(JsonInclude.Include.NON_NULL)");
            fs.append(System.lineSeparator());
            fs.append("@JsonIgnoreProperties(ignoreUnknown=true)");
            fs.append(System.lineSeparator());
            fs.append("@JsonTypeName(\"").append(id).append("\")");
            fs.append(System.lineSeparator());

            if (IGCRestConstants.getAliasObjects().containsKey(id)) {
                fs.append("public class ").append(className).append(" extends ").append(IGCRestConstants.getClassNameForAssetType(IGCRestConstants.getAliasObjects().get(id))).append(" {");
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
                } else if (classToExtend.equals("Datagroup")) {
                    typeToDatagroupClassNames.put(id, className);
                } else if (classToExtend.equals("DataItem")) {
                    typeToDataItemClassNames.put(id, className);
                } else if (classToExtend.equals("DataItemDefinition")) {
                    typeToDataItemDefinitionClassNames.put(id, className);
                } else {
                    typeToReferenceClassNames.put(id, className);
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
                && !IGCRestConstants.getPropertiesToIgnore().contains(name)
                && !CORE_TO_IGNORE.contains(name)) {
            //System.out.println(" ... adding property: " + property.getName());
            String javaType = IGCRestConstants.getJavaTypeForProperty(property);
            if (javaType == null) {
                System.err.println("Unable to determine Java type for: " + property);
            } else {
                detail = new PropertyDetail();
                String propNameActual = property.getName();
                String propertyName = IGCRestConstants.getLowerCamelCase(propNameActual);
                if (RESERVED_WORDS.contains(propertyName)) {
                    propertyName = "z" + propertyName;
                }
                detail.setJavaType(javaType);
                detail.setMember(getMemberDeclaration(property, propNameActual, propertyName, javaType));
                detail.setGetSet(getGetterAndSetter(property, propNameActual, propertyName, javaType));
            }
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

    private static final List<String> SUPER_TYPES = createSuperTypes();
    private static final Set<String> RESERVED_WORDS = createReservedWords();
    private static final Set<String> MAIN_OBJECT_PROPERTIES = createMainObjectProperties();
    private static final Set<String> CORE_TO_IGNORE = createCoreToIgnoreProperties();

    private static List<String> createSuperTypes() {
        List<String> list = new ArrayList<>();
        list.add("main_object");
        list.add("information_asset");
        list.add("datagroup");
        list.add("data_item");
        list.add("data_item_definition");
        return list;
    }

    private static Set<String> createReservedWords() {
        Set<String> set = new HashSet<>();
        set.add("package");
        set.add("final");
        set.add("abstract");
        set.add("default");
        return set;
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

    private static Set<String> createCoreToIgnoreProperties() {
        Set<String> set = new HashSet<>();
        set.add("_name");
        set.add("_type");
        set.add("_url");
        set.add("_id");
        set.add("_context");
        return set;
    }

    protected class TypeCharacteristics {

        // Default everything to a simple Reference as the base class
        private String extendClass = "Reference";
        private TreeMap<String, PropertyDetail> propertyDetails;
        private Set<String> dataTypes;

        TypeCharacteristics() {
            propertyDetails = new TreeMap<>();
            dataTypes = new HashSet<>();
        }

        TypeCharacteristics(String typeName, List<TypeProperty> properties) {
            this();
            initialize(typeName, properties);
        }

        private void initialize(String typeName, List<TypeProperty> properties) {

            // Set known supertypes up-front
            boolean knownClass = false;
            if (IGCRestConstants.getDatagroupTypes().contains(typeName)) {
                extendClass = "Datagroup";
                knownClass = true;
            } else if (IGCRestConstants.getDataItemTypes().contains(typeName)) {
                extendClass = "DataItem";
                knownClass = true;
            } else if (IGCRestConstants.getDataItemDefinitionTypes().contains(typeName)) {
                extendClass = "DataItemDefinition";
                knownClass = true;
            } else if (typeName.equals("datagroup")) {
                extendClass = "InformationAsset";
                knownClass = true;
            } else if (typeName.equals("data_item")) {
                extendClass = "InformationAsset";
                knownClass = true;
            } else if (typeName.equals("data_item_definition")) {
                extendClass = "DataItem";
                knownClass = true;
            } else if (typeName.equals("information_asset")) {
                extendClass = "MainObject";
                knownClass = true;
            }

            // Iterate through all the properties to detect the data types in use, and the supertype for the class
            Set<String> allPropertyNames = new HashSet<>();
            for (TypeProperty property : properties) {
                PropertyDetail detail = getPropertyDetail(property);
                if (detail != null) {
                    addPropertyDetail(property.getName(), detail);
                    if (!typeName.equals("collection")) {
                        String propertyName = property.getName();
                        String propertyType = property.getType().getName();
                        if (propertyName.equals("in_collections")
                                && propertyType.equals("collection")
                                && !knownClass) {
                            // If the object can be put into a collection, is not itself a collection, and is not
                            // already a more specific type, treat it as an InformationAsset
                            extendClass = "InformationAsset";
                        } else if (propertyName.equals("labels")
                                && propertyType.equals("label")
                                && !typeName.equals("main_object")
                                && !knownClass) {
                            // If the object can be assigned a label, and is not already a more specific type, treat it
                            // as a MainObject
                            extendClass = "MainObject";
                        }
                    }
                    dataTypes.addAll(detail.getJavaTypes());
                }
                allPropertyNames.add(property.getName());
            }
            if (SUPER_TYPES.contains(typeName)) {
                superTypeToProperties.put(typeName, allPropertyNames);
            }
            if (extendClass.equals("MainObject")) {
                removeAllSuperTypeProperties("main_object");
            } else if (extendClass.equals("InformationAsset")) {
                removeAllSuperTypeProperties("main_object");
                removeAllSuperTypeProperties("information_asset");
            } else if (extendClass.equals("Datagroup")) {
                removeAllSuperTypeProperties("main_object");
                removeAllSuperTypeProperties("information_asset");
                removeAllSuperTypeProperties("datagroup");
            } else if (extendClass.equals("DataItem")) {
                removeAllSuperTypeProperties("main_object");
                removeAllSuperTypeProperties("information_asset");
                removeAllSuperTypeProperties("data_item");
            } else if (extendClass.equals("DataItemDefinition")) {
                removeAllSuperTypeProperties("main_object");
                removeAllSuperTypeProperties("information_asset");
                removeAllSuperTypeProperties("data_item");
                removeAllSuperTypeProperties("data_item_definition");
            }

        }

        void addPropertyDetail(String propertyName, PropertyDetail detail) {
            propertyDetails.put(propertyName, detail);
        }

        void removePropertyDetail(String propertyName) {
            propertyDetails.remove(propertyName);
        }

        private void removeAllSuperTypeProperties(String superTypeName) {
            for (String propertyName : superTypeToProperties.get(superTypeName)) {
                removePropertyDetail(propertyName);
            }
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
        private Set<String> javaTypes;

        PropertyDetail() {
            javaTypes = new HashSet<>();
        }

        void setMember(String member) { this.member = member; }
        void setGetSet(String getSet) { this.getSet = getSet; }
        void setJavaType(String javaType) {
            if (javaType.startsWith("ItemList<")) {
                this.javaTypes.add("ItemList");
                this.javaTypes.add(javaType.substring(javaType.indexOf("<") + 1, javaType.indexOf(">")));
            } else if (javaType.startsWith("List<")) {
                this.javaTypes.add("List");
                this.javaTypes.add(javaType.substring(javaType.indexOf("<") + 1, javaType.indexOf(">")));
            } else {
                this.javaTypes.add(javaType);
            }
        }

        String getMember() { return this.member; }
        String getGetSet() { return this.getSet; }
        Set<String> getJavaTypes() { return this.javaTypes; }

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

    private void removeInjectedSubtypes(Path path) {

        try {
            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            int position;
            boolean bFound = false;
            for (position = 0; position < lines.size(); position++) {
                String line = lines.get(position);
                if (line.startsWith("import com.fasterxml.jackson.annotation.JsonSubTypes;")) {
                    bFound = true;
                    break;
                }
            }
            if (bFound) {
                lines.remove(position);
                bFound = false;
            }
            for ( ; position < lines.size(); position++) {
                String line = lines.get(position);
                if (line.startsWith("@JsonSubTypes({")) {
                    bFound = true;
                    break;
                }
            }
            if (bFound) {
                lines.remove(position);
                bFound = false;
            }
            List<String> othersToRemove = new ArrayList<>();
            for ( ; position < lines.size(); position++) {
                String line = lines.get(position);
                if (line.startsWith("})")) {
                    bFound = true;
                    break;
                }
                othersToRemove.add(line);
            }
            if (bFound) {
                lines.remove(position);
                lines.removeAll(othersToRemove);
            }
            Files.write(path, lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.err.println("Unable to remove injected subtypes from file: " + path);
            e.printStackTrace();
        }

    }

}
