/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.clientlibrary.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestConstants;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.types.*;
import org.odpi.openmetadata.http.HttpHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(IGCBeanGenerator.class);

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
        boolean skipInformationAssetGeneration = true;
        if (args.length > 4) {
            String upperCased = args[4].toUpperCase();
            skipInformationAssetGeneration = !(upperCased.equals("NO") || upperCased.equals("FALSE"));
        }
        generator.generateSuperTypes(skipInformationAssetGeneration);
        generator.generateForAllIgcTypesInEnvironment(skipInformationAssetGeneration);

    }

    private static void printUsage() {
        System.out.println("Usage: ");
        System.out.println("  IGCBeanGenerator hostname port username password");
    }

    private IGCRestClient igcRestClient;
    private TreeMap<String, TreeMap<String, String>> superTypeToSubTypeToClassName;

    private Map<String, Set<String>> superTypeToProperties;

    private IGCBeanGenerator(String hostname, String port, String username, String password) {
        igcRestClient = new IGCRestClient(hostname, port, username, password);
        superTypeToSubTypeToClassName = new TreeMap<>();
        superTypeToProperties = new HashMap<>();
    }

    private void generateSuperTypes(boolean skipInformationAssetGeneration) {

        // First ensure the target directory has been created / exists
        File dir = new File(BASE_DIRECTORY);
        if (!dir.exists()){
            log.info("Creating directory: " + BASE_DIRECTORY);
            if (!dir.mkdirs()) {
                log.error("Unable to create target directory: {}", BASE_DIRECTORY);
            }
        }

        // Before generating any other types, generate the supertypes from which they will extend
        // and as part of this keep a list of their properties, so any classes that extend them can skip
        // including the same properties and getter / setter methods as overrides
        for (String typeName : IGCRestConstants.getSuperTypes()) {
            if (!(skipInformationAssetGeneration && typeName.equals("information_asset")) ) {
                TypeDetails details = igcRestClient.getTypeDetails(typeName);
                createPOJOForType(details);
            } else {
                superTypeToProperties.put("information_asset", IGCRestConstants.getFixedInformationAssetProperties());
            }
        }

    }

    private void generateForAllIgcTypesInEnvironment(boolean skipInformationAssetGeneration) {

        // Then generate the POJOs within that directory
        List<TypeHeader> types = igcRestClient.getTypes(mapper);
        for (TypeHeader igcType : types) {
            String type = igcType.getId();
            // No need to re-generate the supertypes
            if (!IGCRestConstants.getSuperTypes().contains(type)) {
                TypeDetails typeDetails = igcRestClient.getTypeDetails(type);
                createPOJOForType(typeDetails);
            }
        }

        // Finally update the supertypes with their subtypes
        for (String superTypeName : IGCRestConstants.getSuperTypes()) {
            String superTypeClassName = IGCRestConstants.getClassNameForAssetType(superTypeName);
            if (!superTypeClassName.equals("Reference")) {
                log.info("Injecting subtype information into {}...", superTypeClassName);
                Path superTypePath = Paths.get(BASE_DIRECTORY + File.separator + superTypeClassName + ".java");
                if (superTypeClassName.equals("InformationAsset") && skipInformationAssetGeneration) {
                    // If we skipped generating InformationAsset itself, we need to remove any pre-existing injected
                    // subtypes before injecting any new ones
                    removeInjectedSubtypes(superTypePath);
                }
                injectSubTypes(superTypePath, superTypeToSubTypeToClassName.get(superTypeName));
            }
        }

        // Before injecting into Reference we need to remove any previous injections (since this file is not
        // actually generated)
        Path refPath = Paths.get(COMMON_DIRECTORY + File.separator + "Reference.java");
        removeInjectedSubtypes(refPath);
        log.info("Injecting subtype information into Reference...");
        injectSubTypes(refPath, superTypeToSubTypeToClassName.get("reference"));

        igcRestClient.disconnect();

    }

    private void createPOJOForType(TypeDetails typeDetails) {

        String packageName = IGCRestConstants.IGC_REST_BASE_MODEL_PKG;

        String id   = typeDetails.getId();
        String name = typeDetails.getName();

        log.info("Processing type: {}", id);

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
            String superType = typeCharacteristics.getTypeToExtend();

            if (!propertyDetails.isEmpty()) {
                fs.append("import com.fasterxml.jackson.annotation.JsonProperty;");
                fs.append(System.lineSeparator());
                Set<String> dataTypes = typeCharacteristics.getDataTypes();
                if (superType.equals("reference")
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
            } else if (superType.equals("reference")) {
                fs.append("import " + IGCRestConstants.IGC_REST_COMMON_MODEL_PKG + ".Reference;");
                fs.append(System.lineSeparator());
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

            String classToExtend = IGCRestConstants.getClassNameForAssetType(superType);

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
                log.error("Unable to append property details.", e);
            }

            if (!superTypeToSubTypeToClassName.containsKey(superType)) {
                superTypeToSubTypeToClassName.put(superType, new TreeMap<>());
            }
            superTypeToSubTypeToClassName.get(superType).put(id, className);

            fs.append("}");
            fs.append(System.lineSeparator());

        } catch (IOException e) {
            log.error("Unable to open file output: {}", filename, e);
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
            log.debug(" ... adding property: {}", property.getName());
            String javaType = IGCRestConstants.getJavaTypeForProperty(property);
            if (javaType == null) {
                log.error("Unable to determine Java type for: {}", property);
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

    private static final Set<String> RESERVED_WORDS = createReservedWords();
    private static final Set<String> CORE_TO_IGNORE = createCoreToIgnoreProperties();

    private static Set<String> createReservedWords() {
        Set<String> set = new HashSet<>();
        set.add("package");
        set.add("final");
        set.add("abstract");
        set.add("default");
        return set;
    }

    private static Set<String> createCoreToIgnoreProperties() {
        Set<String> set = new HashSet<>();
        set.add("_name");
        set.add("_type");
        set.add("_url");
        set.add("_id");
        set.add("_context");
        set.add("created_by");
        set.add("created_on");
        set.add("modified_by");
        set.add("modified_on");
        return set;
    }

    protected class TypeCharacteristics {

        // Default everything to a simple Reference as the base class
        private String typeToExtend = "reference";
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
            typeToExtend = IGCRestConstants.getSubTypeToSuperType().getOrDefault(typeName, "reference");
            boolean knownClass = !typeToExtend.equals("reference");

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
                            // already a more specific type, treat it as an information_asset
                            typeToExtend = "information_asset";
                        } else if (propertyName.equals("labels")
                                && propertyType.equals("label")
                                && !typeName.equals("main_object")
                                && !knownClass) {
                            // If the object can be assigned a label, and is not already a more specific type, treat it
                            // as a main_object
                            typeToExtend = "main_object";
                        }
                    }
                    dataTypes.addAll(detail.getJavaTypes());
                }
                allPropertyNames.add(property.getName());
            }
            if (IGCRestConstants.getSuperTypes().contains(typeName)) {
                superTypeToProperties.put(typeName, allPropertyNames);
            }

            // Remove all super type properties for the type we are extending
            removeAllSuperTypeProperties(typeToExtend);

        }

        void addPropertyDetail(String propertyName, PropertyDetail detail) {
            propertyDetails.put(propertyName, detail);
        }

        void removePropertyDetail(String propertyName) {
            propertyDetails.remove(propertyName);
        }

        private void removeAllSuperTypeProperties(String superTypeName) {
            if (!superTypeName.equals("reference")) {
                for (String propertyName : superTypeToProperties.get(superTypeName)) {
                    removePropertyDetail(propertyName);
                }
                for (Map.Entry<String, String> entry : IGCRestConstants.getSubTypeToSuperType().entrySet()) {
                    String subTypeName = entry.getKey();
                    String mappedSuperType = entry.getValue();
                    if (subTypeName.equals(superTypeName)) {
                        removeAllSuperTypeProperties(mappedSuperType);
                    }
                }
            }
        }

        Collection<PropertyDetail> getPropertyDetails() {
            return propertyDetails.values();
        }

        String getTypeToExtend() {
            return typeToExtend;
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
            log.error("Unable to inject subtypes into file: {}", path, e);
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
            log.error("Unable to remove injected subtypes from file: {}", path, e);
        }

    }

}
