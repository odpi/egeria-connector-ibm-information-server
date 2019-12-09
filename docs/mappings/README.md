<!-- SPDX-License-Identifier: CC-BY-4.0 -->
<!-- Copyright Contributors to the ODPi Egeria project. -->

# Implemented mappings

The following types are currently mapped from IGC to OMRS. Note that there are currently no
mappings from OMRS types to IGC types as this connector is entirely read-only (capable of
propagating or retrieving metadata _from_ IGC, but not adding metadata _to_ IGC).

Hoping for a mapping that isn't there?

- [Submit an issue](https://github.com/odpi/egeria-connector-ibm-information-server/issues/new), or
- Check out any of the linked code below for examples of what's needed to create a mapping,
    and create your own (and feel free to submit a PR with the result!)

## Entities

| IGC type(s) | OMRS type(s) | Notes |
| :--- | :--- | :--- |
| `category` | [Glossary](../../igc-adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/entities/GlossaryMapper.java)**, [GlossaryCategory](../../igc-adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/entities/GlossaryCategoryMapper.java) | All top-level categories in IGC that are _not_ named `Classifications` are considered a Glossary, all categories whose `parent_category` is not null and not `Classifications` are considered a GlossaryCategory |
| `connector` | [ConnectorType](../../igc-adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/entities/ConnectorTypeMapper.java) |
| `data_class` | [DataClass](../../igc-adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/entities/DataClassMapper.java) |
| `data_connection` | [Connection](../../igc-adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/entities/ConnectionMapper.java) |
| `data_file` | [DataFile](../../igc-adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/entities/DataFileMapper.java) |
| `data_file_field` | [TabularColumn](../../igc-adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/entities/TabularColumnMapper.java) |
| `data_file_folder` | [FileFolder](../../igc-adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/entities/FileFolderMapper.java) |
| `data_file_record` | [TabularSchemaType](../../igc-adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/entities/TabularSchemaTypeMapper.java) |
| `database` | [Database](../../igc-adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/entities/DatabaseMapper.java) |
| `database_column` | [RelationalColumn](../../igc-adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/entities/RelationalColumnMapper.java) |
| `database_schema` | [DeployedDatabaseSchema](../../igc-adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/entities/DeployedDatabaseSchemaMapper.java), [RelationalDBSchemaType](../../igc-adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/entities/RelationalDBSchemaTypeMapper.java) |
| `database_table` | [RelationalTable](../../igc-adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/entities/RelationalTableMapper.java) |
| `host`, `host_(engine)` | [Endpoint](../../igc-adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/entities/EndpointMapper.java) |
| `information_governance_policy` | [GovernancePolicy](../../igc-adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/entities/GovernancePolicyMapper.java) |
| `label` | [InformalTag](../../igc-adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/entities/InformalTagMapper.java) |
| `term` | [GlossaryTerm](../../igc-adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/entities/GlossaryTermMapper.java) |
| `user`, `group` | [ContactDetails](../../igc-adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/entities/ContactDetailsMapper.java), [Team](../../igc-adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/entities/TeamMapper.java) |
| `user`, `steward_user`, `non_steward_user` | [Person](../../igc-adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/entities/PersonMapper.java) |

## Relationships

| IGC types and properties | OMRS type | Notes |
| :--- | :--- | :--- |
| `database_schema` - `database_schema` | [AssetSchemaType](../../igc-adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/relationships/AssetSchemaTypeMapper_DatabaseSchema.java) | This is a generated relationship (all properties for both endpoints are on a single entity instance in IGC) |
| `data_file.data_file_records` - `data_file_record.data_file` | [AssetSchemaType](../../igc-adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/relationships/AssetSchemaTypeMapper_FileRecord.java) |
| `information_asset.labels` - `label.labeled_assets` | [AttachedTag](../../igc-adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/relationships/AttachedTagMapper.java) |
| `data_file_record.data_file_fields` - `data_file_field.data_file_record` | [AttributeForSchema](../../igc-adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/relationships/AttributeForSchemaMapper_RecordField.java) |
| `database_table.database_columns` - `database_column.database_table_or_view` | [NestedSchemaAttribute](../../igc-adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/relationships/NestedSchemaAttributeMapper.java) |
| `database_schema.database_tables` - `database_table.database_schema` | [AttributeForSchema](../../igc-adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/relationships/AttributeForSchemaMapper_TableSchema.java) |
| `category.subcategories` - `category.parent_category` | [CategoryAnchor](../../igc-adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/relationships/CategoryAnchorMapper.java) | Creates a relationship between the ultimate parent IGC category (Glossary) and any offspring |
| `category.subcategories` - `category.parent_category` | [CategoryHierarchyLink](../../igc-adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/relationships/CategoryHierarchyLinkMapper.java) |
| `data_connection.data_connectors` - `connector.data_connections` | [ConnectionConnectorType](../../igc-adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/relationships/ConnectionConnectorTypeMapper.java) |
| `host.data_connections` - `connector.data_connections` - `data_connection.data_connectors` | [ConnectionEndpoint](../../igc-adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/relationships/ConnectionEndpointMapper.java) | Linked through IGC's `connector` object in the middle |
| `data_connection.imports_database` - `database.data_connections` | [ConnectionToAsset](../../igc-adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/relationships/ConnectionToAssetMapper_Database.java) |
| `data_connection` - `data_file_folder.data_connection` | [ConnectionToAsset](../../igc-adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/relationships/ConnectionToAssetMapper_FileFolder.java) |
| `user` - `user` | [ContactThrough](../../igc-adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/relationships/ContactThroughMapper_Person.java) | This is a generated relationship (all properties for both endpoints are on a single entity instance in IGC) |
| `group` - `group` | [ContactThrough](../../igc-adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/relationships/ContactThroughMapper_Team.java) | This is a generated relationship (all properties for both endpoints are on a single entity instance in IGC) |
| `information_asset.detected_classifications` / `information_asset.selected_classification` - `classification` - `data_class.classified_assets_detected` / `data_class.classifications_selected` | [DataClassAssignment](../../igc-adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/relationships/DataClassAssignmentMapper.java) | Linked through IGC's `classification` object in the middle, which has some relationship-specific properties |
| `data_class.contains_data_classes` - `data_class.parent_data_class` | [DataClassHierarchy](../../igc-adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/relationships/DataClassHierarchyMapper.java) |
| `database.database_schemas` - `database_schema.database` | [DataContentForDataSet](../../igc-adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/relationships/DataContentForDataSetMapper.java) |
| `data_file_folder.data_file_folders` - `data_file_folder.parent_folder` | [FolderHierarchy](../../igc-adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/relationships/FolderHierarchyMapper.java) |
| `database_column.defined_foreign_key_referenced` / `database_column.selected_foreign_key_referenced` - `database_column.defined_foreign_key_references` / `database_column.selected_foreign_key_references` | [ForeignKey](../../igc-adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/relationships/ForeignKeyMapper.java) |
| `information_governance_policy.subpolicies` - `information_governance_policy.parent_policy` | [GovernancePolicyLink](../../igc-adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/relationships/GovernancePolicyLinkMapper.java) |
| `data_file_folder.data_files` - `data_file.parent_folder` | [NestedFile](../../igc-adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/relationships/NestedFileMapper.java) |
| `term.related_terms` - `term.related_terms` | [RelatedTerm](../../igc-adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/relationships/RelatedTermMapper.java) |
| `term.replaced_by` - `term.replaces` | [ReplacementTerm](../../igc-adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/relationships/ReplacementTermMapper.java) |
| `information_asset.assigned_to_terms` - `term.assigned_assets` | [SemanticAssignment](../../igc-adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/relationships/SemanticAssignmentMapper.java) |
| `term.synonyms` - `term.synonyms` | [Synonym](../../igc-adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/relationships/SynonymMapper.java) |
| `category.terms` - `term.parent_category` | [TermAnchor](../../igc-adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/relationships/TermAnchorMapper.java) | Creates a relationship between the ultimate parent IGC category (Glossary) and an IGC term |
| `category.terms` - `term.parent_category` | [TermCategorization](../../igc-adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/relationships/TermCategorizationMapper.java) | 
| `term.has_a_term` - `term.is_of` | [TermHASARelationship](../../igc-adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/relationships/TermHASARelationshipMapper.java) |
| `term.has_types` - `term.is_a_type_of` | [TermISATypeOFRelationship](../../igc-adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/relationships/TermISATypeOFRelationshipMapper.java) |
| `term.translations` - `term.translations` | [Translation](../../igc-adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/relationships/TranslationMapper.java) |

## Classifications

Because IGC has no "Classification" concept, the following are suggested implementations of
Classifications within IGC by overloading the use of other concepts. These can be changed to
alternative implementations simply by updating the linked mapping code to match your desired
implementation of the concept.

### [AssetZoneMembership](../../igc-adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/classifications/AssetZoneMembershipMapper.java)

The provided implementation simply assigns the list of default zones that have been [specified as `default.zones` in the configuration of
the connector](../../README.md) to each `Database`, `DataFile`, `DeployedDatabaseSchema` and `FileFolder` in the IGC repository
anytime these assets are retrieved.

### [Confidentiality](../../igc-adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/classifications/ConfidentialityMapper.java)

The provided implementation assigns a `Confidentiality` classification to a `GlossaryTerm` (only) using the
`assigned_to_term` relationship from one `term` to any `term` within the `Classifications/Confidentiality` parent
`category`. The terms contained within this _Confidentiality_ `category` in essence represent the `ConfidentialityLevel`
enumeration in OMRS. With this implementation, any `assigned_to_term` relationship on a `term`, where the assigned
`term` is within this _Confidentiality_ `category` in IGC, will be mapped to a `Confidentiality` classification in OMRS.

### [PrimaryKey](../../igc-adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/classifications/PrimaryKeyMapper.java)

The provided implementation looks for the presence of either the `defined_primary_key` or `selected_primary_key`
properties on a `database_column`, and if present add a `PrimaryKey` classification to that `RelationalColumn`.

### [SpineObject](../../igc-adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/classifications/SpineObjectMapper.java)

The provided implementation assigns a `SpineObject` classification to a `GlossaryTerm` based on the
`referencing_categories` of the `term` in IGC. Specifically, when the `term` has a `referencing_categories` named
link to `Classifications/SpineObject`, the `GlossaryTerm` to which that `term` maps will be assigned the `SpineObject`
classification.

### [SubjectArea](../../igc-adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/classifications/SubjectAreaMapper.java)

The provided implementation assigns a `SubjectArea` classification to a `GlossaryCategory` based on the
`assigned_to_term` relationship of the `category` in IGC. Specifically, when the `category` is has an `assigned_to_term`
relationship to the IGC `term` `Classifications/SubjectArea`, the `GlossaryCategory` will be assigned a `SubjectArea`
classification whose `name` will match the name of the IGC `category`.

### [TypeEmbeddedAttribute](../../igc-adapter/src/main/java/org/odpi/egeria/connectors/ibm/igc/repositoryconnector/mapping/classifications/TypeEmbeddedAttributeMapper.java)

The provided implementation adds this classification to all `RelationTable`, `RelationalColumn` and `TabularColumn`
instances to simplify the management of type information without requiring additional `SchemaType` subclasses (like
`RelationalTableType`, `RelationalColumnType` and `TabularColumnType`) and their additional relationships.


----
License: [CC BY 4.0](https://creativecommons.org/licenses/by/4.0/),
Copyright Contributors to the ODPi Egeria project.
