<!-- SPDX-License-Identifier: CC-BY-4.0 -->
<!-- Copyright Contributors to the ODPi Egeria project. -->

# Extending the connector

The IGC connector is intended to be extensible, with the intention of allowing you to re-use the base logic already
contained in the connector while adding only the extra portions required for your particular implementation or desired
behavior.

The following sections describe the recommended approach to developing such extensions, without needing to modify or
re-build the base connector itself.

## Project setup

The basic approach is to create a new project in which you will develop your extensions. This project should have as
dependencies the base connector.

!!! example "Root-level `pom.xml` snippet"
    ```xml linenums="1"
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.odpi.egeria</groupId>
                <artifactId>ibm-igc-rest-client-library</artifactId>
                <version>${open-metadata.version}</version>
                <scope>compile</scope>
            </dependency>
            <dependency>
                <groupId>org.odpi.egeria</groupId>
                <artifactId>egeria-connector-ibm-igc-adapter</artifactId>
                <version>${open-metadata.version}</version>
                <scope>compile</scope>
            </dependency>
            <dependency>
                <groupId>org.odpi.egeria</groupId>
                <artifactId>repository-services-apis</artifactId>
                <version>${open-metadata.version}</version>
                <scope>provided</scope>
            </dependency>
            <dependency>
                <groupId>org.odpi.egeria</groupId>
                <artifactId>open-connector-framework</artifactId>
                <version>${open-metadata.version}</version>
                <scope>provided</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
    ```

    These are the minimal dependencies to re-use the logic already provided by the base connector.

Like the base connector project, you should then also have modules in your own project for:

- the client library, where you'll put any extensions to the data model, e.g. for custom attributes or OpenIGC objects
- the adapter, where you'll put any extensions to or net-new mapping logic

!!! example "Client library `pom.xml` snippet"
    ```xml linenums="1"
    <dependencies>
        <dependency>
            <groupId>org.odpi.egeria</groupId>
            <artifactId>ibm-igc-rest-client-library</artifactId>
        </dependency>
    </dependencies>
    ```
    
    This includes the base client library as a dependency.

!!! example "Adapter module `pom.xml` snippet"
    ```xml linenums="1" hl_lines="7-8"
    <dependencies>
        <dependency>
            <groupId>org.odpi.egeria</groupId>
            <artifactId>egeria-connector-ibm-igc-adapter</artifactId>
        </dependency>
        <dependency>
            <groupId>${your-group-for-your-extensions}</groupId>
            <artifactId>${your-extended-client-library-artifact-id}</artifactId>
        </dependency>
        <dependency>
            <groupId>org.odpi.egeria</groupId>
            <artifactId>open-connector-framework</artifactId>
        </dependency>
        <dependency>
            <groupId>org.odpi.egeria</groupId>
            <artifactId>repository-services-apis</artifactId>
        </dependency>
    </dependencies>
    ```

    Note that you will need to replace the highlighted lines with the group and artifact IDs for
    your extended client library.

## Extending the vanilla IGC model

To avoid potential injection exposure risks, the (de)serialization is strongly typed and will not dynamically handle
arbitrary JSON or additions to the Java beans (POJOs). As a result, the connector out-of-the-box will *not* handle any
custom attributes in IGC or any new asset types developed through OpenIGC. All of these will be automatically ignored.
Their values will not even be included in the `additionalProperties` map of a Referenceable, for example.

Therefore, if you have extended the vanilla IGC model with either custom attributes or OpenIGC assets, and you want to
be able to make use of them as part of your connector, you will need to extend the connector with this handling.

### Handling custom attributes

In your own client library module, create new POJO objects for each IGC asset type on which you have a custom attribute.
These should extend the base connectors' model objects.

For example, if you have a new custom attribute called `Test Attribute` defined against IGC's `term` objects, you would
write a new class like the following:

!!! example "Example class extending the `term` object with a `Test Attribute` custom attribute"
    ```java linenums="1"
    package whatever.structure.you.desire;
    
    import com.fasterxml.jackson.annotation.*;
    import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.Term;
    
    import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;
    import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.PUBLIC_ONLY;
    
    @JsonTypeInfo(
        use=JsonTypeInfo.Id.NAME,
        include=JsonTypeInfo.As.EXISTING_PROPERTY,
        property="_type",
        visible=true,
        defaultImpl=Term.class
    )
    @JsonAutoDetect(
        getterVisibility=PUBLIC_ONLY,
        setterVisibility=PUBLIC_ONLY,
        fieldVisibility=NONE
    )
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown=true)
    @JsonTypeName("term")
    public class ExtendedTerm extends Term {
    
        @JsonProperty("custom_Test Attribute")
        protected String customTestAttribute;
    
        @JsonProperty("custom_Test Attribute")
        public String getCustomTestAttribute() {
            return this.customTestAttribute;
        }
    
        @JsonProperty("custom_Test Attribute")
        public void setCustomTestAttribute(String customTestAttribute) {
            this.customTestAttribute = customTestAttribute;
        }
    
    }
    ```

There are a few important points to follow when developing such extensions. These must be followed precisely as a number
of the default mechanisms for mapping rely on these precise constraints:

- Include the Jackson annotations as illustrated in the example: in particular ensure that `@JsonTypeName` is set to
  the name of the IGC asset type (as it is known via the IGC REST API)
- Ensure that your new class extends the base class (in this case `org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.Term`)
  to ensure that all of the other base properties, getters, setters and (de)serialization are appropriately handled
- Add any new properties as protected members of the class:
    - Name them using lower camel-case (remove spaces, underscores, etc and capitalize every word except the first)
    - Always start them with `custom`
    - Annotate each property with the name of the custom attribute as known to the IGC REST API (this will always
      follow the convention `custom_<Attribute Name>`, where `<Attribute Name>` is as you have defined it: including
      any spaces)
- Add a single getter for each new property:
    - Name the method using lower camel-case
    - Always start the method with `get` followed by the camel-case of the attribute name
    - Annotate each property with the name of the custom attribute as known to the IGC REST API (see above)
- Add a single setter for each new property:
    - Name the method using lower camel-case
    - Always start the method with `set` followed by the camel-case of the attribute name
    - Annotate each property with the name of the custom attribute as known to the IGC REST API (see above)

### Handling new asset types ("OpenIGC")

As with the custom attributes, the connector will not handle any new asset types that you may have added through
OpenIGC. You will need to extend the model (and mapping logic) similarly to what is suggested above for custom
attributes in order to make use of any OpenIGC assets in your implementation through the connector.

Since OpenIGC assets are entirely new objects rather than new properties on existing objects, these should be defined
as new classes in your model. They should always extend the base `InformationAsset` object.

For example, if you have a new OpenIGC object type named `Bar` within a bundle named `Foo`, you would write a new class
like the following:

!!! example "Example class for a new object type named `Bar` in OpenIGC bundle `Foo`"
    ```java linenums="1"
    package whatever.structure.you.desire;
    
    import com.fasterxml.jackson.annotation.*;
    import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.InformationAsset;
    
    import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;
    import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.PUBLIC_ONLY;
    
    @JsonAutoDetect(
        getterVisibility=PUBLIC_ONLY,
        setterVisibility=PUBLIC_ONLY,
        fieldVisibility=NONE
    )
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown=true)
    @JsonTypeName("$Foo-Bar")
    public class FooBar extends InformationAsset {
        
        protected String $firstProperty; 
        protected String $secondProperty;
    
        @JsonProperty("$firstProperty")
        public String getFirstProperty() {
            return this.$firstProperty;
        }
    
        @JsonProperty("$firstProperty")
        public void setFirstProperty(String firstProperty) {
            this.$firstProperty = firstProperty;
        }
    
        @JsonProperty("$secondProperty")
        public String getSecondProperty() {
            return this.$secondProperty;
        }
    
        @JsonProperty("$secondProperty")
        public void setSecondProperty(String secondProperty) {
            this.$secondProperty = secondProperty;
        }

    }
    ```

Once again, the example should be followed precisely:

- Use the Jackson annotations, including the `@JsonTypeName` giving the name of the object as it will be known
  through the IGC REST API (`$<bundleId>-<class>`)
- Extend the base `InformationAsset` object
- Name all of the properties defined on the bundle itself starting with `$`
- Give each property a single getter and setter, which follow:
    - the camel-case conventions outlined above for custom attributes
    - the annotation conventions outlined above for custom attributes

## Write your own connector

To pickup these extensions, we need to implement our own connector. To maximize re-use, this connector can extend the
base IGC connector.

Within your new adapter module, you will need to implement two classes:

### ConnectorProvider class

The ConnectorProvider class defines how your custom (extended) connector should be instantiated. 

!!! example "Template code for a new `ConnectorProvider` class"
    ```java linenums="1" hl_lines="1 9 11-13 19"
    package whatever.structure.you.desire;
    
    import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSRepositoryConnectorProvider;
    import org.odpi.openmetadata.frameworks.connectors.properties.beans.ConnectorType;
    
    import java.util.ArrayList;
    import java.util.List;
    
    public class ExtendedIGCOMRSRepositoryConnectorProvider extends IGCOMRSRepositoryConnectorProvider {
    
        static final String CONNECTOR_TYPE_GUID = "<unique UUID>";
        static final String CONNECTOR_TYPE_NAME = "<name for your connector>";
        static final String CONNECTOR_TYPE_DESC = "<description of your connector>";
    
        public static final String DEFAULT_ZONES = "defaultZones";
    
        public ExtendedIGCOMRSRepositoryConnectorProvider() {
    
            Class<?> connectorClass = ExtendedIGCOMRSRepositoryConnector.class;
            super.setConnectorClassName(connectorClass.getName());
    
            ConnectorType connectorType = new ConnectorType();
            connectorType.setType(ConnectorType.getConnectorTypeType());
            connectorType.setGUID(CONNECTOR_TYPE_GUID);
            connectorType.setQualifiedName(CONNECTOR_TYPE_NAME);
            connectorType.setDisplayName(CONNECTOR_TYPE_NAME);
            connectorType.setDescription(CONNECTOR_TYPE_DESC);
            connectorType.setConnectorProviderClassName(this.getClass().getName());
    
            List<String> recognizedConfigurationProperties = new ArrayList<>();
            recognizedConfigurationProperties.add(DEFAULT_ZONES);
            connectorType.setRecognizedConfigurationProperties(recognizedConfigurationProperties);
    
            super.connectorTypeBean = connectorType;
    
        }
    }
    ```

    This is almost a copy/paste of the base ConnectorProvider, with the following critical changes:

    - Change the package structure and name of the class itself to whatever makes sense for your situation
    - You can extend the `IGCOMRSRepositoryConnectorProvider` instead of the `OMRSRepositoryConnectorProviderBase`
    - Replace the static constants with your own (the GUID, name and description)
    - Change the connectorClass to the class defined in the next section

### Connector class

The Connector class defines how your connector will actually be started up. This must be overridden to define at a
minimum the overrides for your extended POJO models. If you are only overriding the models, this can be as simple as overriding
the `connectToIGC` method:

!!! example "Registering the extended POJO models"
    ```java linenums="1" hl_lines="12"
    package whatever.structure.you.desire;
    
    import com.ibm.custom.connectors.ibm.igc.clientlibrary.model.base.ExtendedTerm;
    import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSRepositoryConnector;
    import org.odpi.openmetadata.frameworks.connectors.ffdc.ConnectorCheckedException;
    
    public class ExtendedIGCOMRSRepositoryConnector extends IGCOMRSRepositoryConnector {
    
        @Override
        protected void connectToIGC(String methodName) throws ConnectorCheckedException {
            super.connectToIGC(methodName);
            igcRestClient.registerPOJO(ExtendedTerm.class);
        }
    
    }
    ```

    Note that we are only registering our extensions to the `Term` object by registering our `ExtendedTerm` class.
    If we also had custom POJOs defined for OpenIGC assets, or other IGC types that were extended with
    custom attributes, these would simply need additional calls to the `igcRestClient.registerPOJO()` method for each
    such class.

This will ensure that your extensions are preferred for (de)serialization over any vanilla objects included in the base
connector. For any IGC object types that are not extended, the connector will still automatically "fall-back" to the
vanilla object definitions included in the base connector -- so the base connector is re-used, and all you need to do
is define extensions.

## Building your connector

To use these extensions, we finally need to build this project as its own connector. For consistency, it is probably
simplest to do this following the same convention used in the base connector -- a `distribution` module that defines
the assembly that should be used for this connector.

!!! example "New `distribution` module's `pom.xml`"
    ```xml linenums="1" hl_lines="5-6 26"
    <packaging>jar</packaging>
    
    <dependencies>
        <dependency>
            <groupId>${your-group-for-your-extensions}</groupId>
            <artifactId>${your-extended-adapter-artifact-id}</artifactId>
        </dependency>
    </dependencies>
    
    <build>
        <plugins>
            <!-- Packaging -->
            <plugin>
                <artifactId>maven-assembly-plugin</artifactId>
                <executions>
                    <execution>
                        <goals>
                            <goal>single</goal>
                        </goals>
                        <phase>prepare-package</phase>
                        <configuration>
                            <skipAssembly>false</skipAssembly>
                            <descriptors>
                                <descriptor>src/main/assemblies/connector.xml</descriptor>
                            </descriptors>
                            <finalName>${the-name-you-want-for-your-connector-jar-file}</finalName>
                            <tarLongFileMode>gnu</tarLongFileMode>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
    ```

    Be sure to replace the group, artifact and final jar file name accordingly.

!!! example "Example `connector.xml` assembly"
    ```xml linenums="1" hl_lines="17-18"
    <?xml version="1.0" encoding="UTF-8"?>
    <assembly xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
              xmlns="http://maven.apache.org/plugins/maven-assembly-plugin/assembly/1.1.2"
              xsi:schemaLocation="http://maven.apache.org/plugins/maven-assembly-plugin/assembly/1.1.2 http://maven.apache.org/xsd/assembly-1.1.2.xsd">
        <id>jar-with-dependencies</id>
        <formats>
            <format>jar</format>
        </formats>
        <includeBaseDirectory>false</includeBaseDirectory>
        <dependencySets>
            <dependencySet>
                <outputDirectory>/</outputDirectory>
                <useProjectArtifact>false</useProjectArtifact>
                <unpack>true</unpack>
                <scope>compile</scope>
                <includes>
                    <include>${your-group-for-your-extensions}:${your-extended-adapter-artifact-id}</include>
                    <include>${your-group-for-your-extensions}:${your-extended-client-library-artifact-id}</include>
                    <include>org.odpi.egeria:egeria-connector-ibm-igc-adapter</include>
                    <include>org.odpi.egeria:ibm-igc-rest-client-library</include>
                    <include>com.flipkart.zjsonpatch:zjsonpatch</include>
                    <include>org.apache.commons:commons-collections4</include>
                </includes>
            </dependencySet>
        </dependencySets>
    </assembly>
    ```

    The explicit includes will ensure that the base IGC connector is included (for fall-backs to vanilla models and
    behavior) as well as your extensions and overrides. (Of course, don't forget to replace the highlighted placeholder
    values with your extended connector's group and artifact IDs.)

You then simply need to:

- Ensure this jar file is included in the path defined by your `LOADER_PATH` variable to be picked up by the OMAG Platform
- When calling the OMAG admin services to configure the connector, ensure that the `connectorProviderClassName` for the
  `connectorType` is set to the canonical class name of your extended ConnectorProvider class rather than the base
  IGC connector's ConnectorProvider class name

## Extending the mappings with your own logic

The walkthrough above simply covers extending the model. With the changes above, the values of non-relationship custom
attributes should automatically start appearing in the `additionalProperties` map of any Egeria (Referenceable-derived)
entity.

Of course, if you want to use this custom information to drive some other mapping to the Egeria types, then you will
also need to extend the mapping logic of the base connector (not only the model).

To do this, begin by creating a new package in your extended adapter module with the following constraints:

- It should be a unique structure under which all mapping classes can be contained (eg. `whatever.structure.you.desire.mappings`).
- Create sub-packages for each type of mapping, whose names must be:
    - `attributes` for AttributeMappings
    - `entities` for EntityMappings
    - `classifications` for ClassificationMappings
    - `relationships` for RelationshipMappings

### Extending the existing mappings

To extend an existing mapping, simply define a new class in your mapping package with a name identical to the base
connectors' mapping class. Ensure this class is in the sub-package relevant to the type it is mapping.

!!! example "Example: extending `GlossaryTerm` mapping"
    ```java linenums="1"
    package whatever.structure.you.desire.mappings.entities;
    
    import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
    
    public class GlossaryTermMapper extends org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities.GlossaryTermMapper {
    
        private static class Singleton {
            private static final GlossaryTermMapper INSTANCE = new GlossaryTermMapper();
        }
        public static GlossaryTermMapper getInstance(IGCVersionEnum version) {
            return Singleton.INSTANCE;
        }
    
        private GlossaryTermMapper() {
            super();
            addSimplePropertyMapping("custom_Test Attribute", "usage");
        }
    
    }
    ```

    Note the following:

    - This mapping class is defined under the `.entities` sub-package of the overall mappings package.
    - Define a `private static class Singleton` that captures a `static final INSTANCE` of your class.
    - Define a `public static getInstance()` method that returns this `Singleton.INSTANCE`, and is passed an
      `IGCVersionEnum`. While typically unused, if you want to have different logic depending on the version of IGC being
      used, this enum allows you to do so (for an example, see the base connectors' [DataClassMapper](https://github.com/odpi/egeria-connector-ibm-information-server/blob/main/igc-adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/entities/DataClassMapper.java){ target=code })
    - Define a `private` (or `protected`) no-args constructor that first calls the no-args constructor of the parent class.
    - Add your extra logic or mappings (in the example above, the `addSimplePropertyMapping()` maps our custom attribute
      to the `usage` OMRS property).

### Implementing your own mappings

To implement your own mappings, follow the same basic guidance as above, but naturally do not extend a base connectors'
mapping class. Instead, directly extend one of the following:

- `org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.attributes.EnumMapping` for an AttributeMapping
  (primitive types are already mapped, so this is assuming the only attributes you would need to map are enumerations)
- `org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities.ReferenceableMapper` for an EntityMapping
  (this ensures automatic mapping for complex properties like `qualifiedName` and `additionalProperties`)
- `org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.classifications.ClassificationMapping` for a ClassificationMapping
- `org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.RelationshipMapping` for a RelationshipMapping

Note that in these cases there are various methods you may also want or need to override to influence the behavior of
your mapping. See any of the base connectors' mapping classes for examples.

--8<-- "snippets/abbr.md"
