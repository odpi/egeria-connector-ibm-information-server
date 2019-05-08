<!-- SPDX-License-Identifier: CC-BY-4.0 -->
<!-- Copyright Contributors to the ODPi Egeria project. -->

# Implemented mappings

The following types are currently mapped from IGC to OMRS. Note that there are currently limited
mappings from OMRS types to IGC types as this connector is primarily read-only (primarily capable of
propagating or retrieving information _from_ IGC, and not _to_ IGC).

Hoping for a mapping that isn't there?

- [Submit an issue](https://github.com/odpi/egeria-connector-ibm-igc/issues/new), or
- Check out any of the linked code below for examples of what's needed to create a mapping,
    and create your own (and feel free to submit a PR with the result!)

## Entities

| IGC type(s) | OMRS type(s) |
| :--- | :--- |
| `main_object` (default) | [Referenceable](../../adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/entities/ReferenceableMapper.java) |
| `category` | [GlossaryCategory](../../adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/entities/GlossaryCategoryMapper.java) |
| `connector` | [ConnectorType](../../adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/entities/ConnectorTypeMapper.java) |
| `data_class` | [DataClass](../../adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/entities/DataClassMapper.java) |
| `data_connection` | [Connection](../../adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/entities/ConnectionMapper.java) |
| `data_file` | [DataFile](../../adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/entities/DataFileMapper.java) |
| `data_file_field` | [TabularColumn](../../adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/entities/TabularColumnMapper.java), [TabularColumnType](../../adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/entities/TabularColumnTypeMapper.java) |
| `data_file_folder` | [FileFolder](../../adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/entities/FileFolderMapper.java) |
| `data_file_record` | [TabularSchemaType](../../adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/entities/TabularSchemaTypeMapper.java) |
| `database` | [Database](../../adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/entities/DatabaseMapper.java) |
| `database_column` | [RelationalColumn](../../adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/entities/RelationalColumnMapper.java), [RelationalColumnType](../../adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/entities/RelationalColumnTypeMapper.java) |
| `database_schema` | [DeployedDatabaseSchema](../../adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/entities/DeployedDatabaseSchemaMapper.java), [RelationalDBSchemaType](../../adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/entities/RelationalDBSchemaTypeMapper.java) |
| `database_table` | [RelationalTable](../../adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/entities/RelationalTableMapper.java), [RelationalTableType](../../adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/entities/RelationalTableTypeMapper.java) |
| `host`, `host_(engine)` | [Endpoint](../../adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/entities/EndpointMapper.java) |
| `information_governance_policy` | [GovernancePolicy](../../adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/entities/GovernancePolicyMapper.java) |
| `label` | [InformalTag](../../adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/entities/InformalTagMapper.java) |
| `term` | [GlossaryTerm](../../adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/entities/GlossaryTermMapper.java) |
| `user`, `group` | [ContactDetails](../../adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/entities/ContactDetailsMapper.java), [Team](../../adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/entities/TeamMapper.java) |
| `user`, `steward_user`, `non_steward_user` | [Person](../../adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/entities/PersonMapper.java) |

## Relationships

| IGC type(s) | OMRS type(s) |
| :--- | :--- |
| `database_schema`-`database_schema` | [AssetSchemaType](../../adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/relationships/AssetSchemaTypeMapper_DatabaseSchema.java) |
| `data_file`-`data_file_record` | [AssetSchemaType](../../adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/relationships/AssetSchemaTypeMapper_FileRecord.java) |
| `main_object`-`label` | [AttachedTag](../../adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/relationships/AttachedTagMapper.java) |
| `data_file_record`-`data_file_field` | [AttributeForSchema](../../adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/relationships/AttributeForSchemaMapper_RecordField.java) |
| `database_table`-`database_column` | [AttributeForSchema](../../adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/relationships/AttributeForSchemaMapper_TableColumn.java) |
| `database_schema`-`database_table` | [AttributeForSchema](../../adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/relationships/AttributeForSchemaMapper_TableSchema.java) |
| `category`-`category` | [CategoryHierarchyLink](../../adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/relationships/CategoryHierarchyLinkMapper.java) |
| `data_connection`-`connector` | [ConnectionConnectorType](../../adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/relationships/ConnectionConnectorTypeMapper.java) |
| `host`-`connector`-`data_connection` | [ConnectionEndpoint](../../adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/relationships/ConnectionEndpointMapper.java) |
| `data_connection`-`database` | [ConnectionToAsset](../../adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/relationships/ConnectionToAssetMapper_Database.java) |
| `data_connection`-`data_file_folder` | [ConnectionToAsset](../../adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/relationships/ConnectionToAssetMapper_FileFolder.java) |
| `user`-`user` | [ContactThrough](../../adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/relationships/ContactThroughMapper_Person.java) |
| `group`-`group` | [ContactThrough](../../adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/relationships/ContactThroughMapper_Team.java) |
| `main_object`-`classification`-`data_class` | [DataClassAssignment](../../adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/relationships/DataClassAssignmentMapper.java) |
| `data_class`-`data_class` | [DataClassHierarchy](../../adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/relationships/DataClassHierarchyMapper.java) |
| `database`-`database_schema` | [DataContentForDataSet](../../adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/relationships/DataContentForDataSetMapper.java) |
| `data_file_folder`-`data_file_folder` | [FolderHierarchy](../../adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/relationships/FolderHierarchyMapper.java) |
| `database_column`-`database_column` | [ForeignKey](../../adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/relationships/ForeignKeyMapper.java) |
| `information_governance_policy`-`information_governance_policy` | [GovernancePolicyLink](../../adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/relationships/GovernancePolicyLinkMapper.java) |
| `data_file_folder`-`data_file` | [NestedFile](../../adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/relationships/NestedFileMapper.java) |
| `term`-`term` | [RelatedTerm](../../adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/relationships/RelatedTermMapper.java) |
| `term`-`term` | [ReplacementTerm](../../adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/relationships/ReplacementTermMapper.java) |
| `database_column`-`database_column` | [SchemaAttributeType](../../adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/relationships/SchemaAttributeTypeMapper_DatabaseColumn.java) |
| `database_table`-`database_table` | [SchemaAttributeType](../../adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/relationships/SchemaAttributeTypeMapper_DatabaseTable.java) |
| `data_file_field`-`data_file_field` | [SchemaAttributeType](../../adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/relationships/SchemaAttributeTypeMapper_FileField.java) |
| `main_object`-`term` | [SemanticAssignment](../../adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/relationships/SemanticAssignmentMapper.java) |
| `term`-`term` | [Synonym](../../adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/relationships/SynonymMapper.java) |
| `category`-`term` | [TermCategorization](../../adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/relationships/TermCategorizationMapper.java) |
| `term`-`term` | [TermHASARelationship](../../adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/relationships/TermHASARelationshipMapper.java) |
| `term`-`term` | [TermISATypeOFRelationship](../../adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/relationships/TermISATypeOFRelationshipMapper.java) |
| `term`-`term` | [Translation](../../adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/relationships/TranslationMapper.java) |

## Classifications

Because IGC has no "Classification" concept, the following are suggested implementations of
Classifications within IGC by overloading the use of other concepts. These can be changed to
alternative implementations simply by updating the linked mapping code to match your desired
implementation of the concept(s).

### [Confidentiality](../../adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/classifications/ConfidentialityMapper.java)

The provided implementation assigns a `Confidentiality` classification to a `GlossaryTerm` (only) using the
`assigned_to_term` relationship from one `term` to any `term` within a _Confidentiality_ parent `category`. The
subcategories of this _Confidentiality_ parent `category` in essence represent the `ConfidentialityLevel` enumeration in
OMRS. With this implementation, any `assigned_to_term` relationship on a `term`, where the assigned `term` is within a
"Confidentiality" ancestral `category` in IGC, will be mapped to a `Confidentiality` classification in OMRS.

### [PrimaryKey](../../adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/classifications/PrimaryKeyMapper.java)

The provided implementation looks for the presence of either the `defined_primary_key` or `selected_primary_key`
properties on a `database_column`, and if present add a `PrimaryKey` classification to that `RelationalColumn`.

### [SpineObject](../../adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/classifications/SpineObjectMapper.java)

The provided implementation assigns a `SpineObject` classification to a `GlossaryTerm` based on the `category_path` of
the `term` in IGC. Specifically, when the `term` is within a `category` named _Spine Objects_, the `GlossaryTerm` to
which that `term` maps will be assigned the `SpineObject` classification.

### [SubjectArea](../../adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/classifications/SubjectAreaMapper.java)

The provided implementation assigns a `SubjectArea` classification to a `GlossaryCategory` based on the `category_path`
of the `category` in IGC. Specifically, when the `category` is a direct subcategory of another `category` whose name
starts with _Subject Area_, the subcategory will be assigned a `SubjectArea` classification whose `name` will match the
name of the subcategory.


----
License: [CC BY 4.0](https://creativecommons.org/licenses/by/4.0/),
Copyright Contributors to the ODPi Egeria project.
