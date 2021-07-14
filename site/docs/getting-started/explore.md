<!-- SPDX-License-Identifier: CC-BY-4.0 -->
<!-- Copyright Contributors to the ODPi Egeria project. -->

# Explore

You should now be able to start accessing metadata in IBM Information Server via Egeria.
Without any additional dependencies, this is most easily illustrated using direct REST API
interactions with the server chassis's repository services.

## Run a query

Run a query for all terms in IGC that contain the word "address" in their name:

```shell
curl -k -X POST -H "Content-Type: application/json" \
  --data '{"class":"EntityPropertyFindRequest","typeGUID":"0db3e6ec-f5ef-4d75-ae38-b7ee6fd6ec0a","pageSize":10,"matchCriteria":"ALL","matchProperties":{"class":"InstanceProperties","instanceProperties":{"displayName":{"class":"PrimitivePropertyValue","instancePropertyCategory":"PRIMITIVE","primitiveDefCategory":"OM_PRIMITIVE_TYPE_STRING","primitiveValue":".*\\QAddress\\E.*"}}}}' \
  "https://localhost:9443/servers/igcproxy/open-metadata/repository-services/users/admin/instances/entities/by-property"
```

??? question "Explanation of the payload"
    ```json linenums="1" hl_lines="3 9 13"
    {
      "class": "EntityPropertyFindRequest",
      "typeGUID": "0db3e6ec-f5ef-4d75-ae38-b7ee6fd6ec0a",
      "pageSize": 10,
      "matchCriteria": "ALL",
      "matchProperties": {
        "class": "InstanceProperties",
        "instanceProperties": {
          "displayName": {
            "class": "PrimitivePropertyValue",
            "instancePropertyCategory": "PRIMITIVE",
            "primitiveDefCategory": "OM_PRIMITIVE_TYPE_STRING",
            "primitiveValue": ".*\\QAddress\\E.*"
          }
        }
      }
    }
    ```

    The `typeGUID` parameter limits our search only to `GlossaryTerm` objects, while the
    matchProperties define which properties of the terms we should search (in this case, for any
    terms whose `displayName` contains precisely the case-sensitive string `Address`).

??? success "Response from search"
    ```json
    {
      "class": "EntityListResponse",
      "relatedHTTPCode": 200,
      "offset": 0,
      "pageSize": 10,
      "entities": [
        {
          "class": "EntityDetail",
          "headerVersion": 1,
          "type": {
            "class": "InstanceType",
            "typeDefCategory": "ENTITY_DEF",
            "typeDefGUID": "0db3e6ec-f5ef-4d75-ae38-b7ee6fd6ec0a",
            "typeDefName": "GlossaryTerm",
            "typeDefVersion": 1,
            "typeDefDescription": "A semantic description of something, such as a concept, object, asset, technology, role or group.",
            "typeDefSuperTypes": [
              {
                "headerVersion": 1,
                "guid": "a32316b8-dc8c-48c5-b12b-71c1b2a080bf",
                "name": "Referenceable",
                "status": "ACTIVE_TYPEDEF"
              },
              {
                "headerVersion": 1,
                "guid": "4e7761e8-3969-4627-8f40-bfe3cde85a1d",
                "name": "OpenMetadataRoot",
                "status": "ACTIVE_TYPEDEF"
              }
            ],
            "validInstanceProperties": [
              "qualifiedName",
              "additionalProperties",
              "displayName",
              "summary",
              "description",
              "examples",
              "abbreviation",
              "usage"
            ]
          },
          "instanceProvenanceType": "LOCAL_COHORT",
          "metadataCollectionId": "5ce8e35a-2df3-409d-b085-bb64356407c8",
          "metadataCollectionName": "igcproxy",
          "createdBy": "Administrator IIS",
          "updatedBy": "Administrator IIS",
          "maintainedBy": [
            "Administrator IIS"
          ],
          "createTime": "2019-12-10T16:19:48.000+00:00",
          "updateTime": "2019-12-10T16:20:14.000+00:00",
          "version": 1575994814000,
          "guid": "term@5ce8e35a-2df3-409d-b085-bb64356407c8:6662c0f2.e1b1ec6c.00263sgva.eo5q0s7.o5rf5s.q22k66bdjh8h341n1hi1e",
          "classifications": [
            {
              "class": "Classification",
              "headerVersion": 1,
              "type": {
                "class": "InstanceType",
                "typeDefCategory": "CLASSIFICATION_DEF",
                "typeDefGUID": "742ddb7d-9a4a-4eb5-8ac2-1d69953bd2b6",
                "typeDefName": "Confidentiality",
                "typeDefVersion": 2,
                "typeDefDescription": "Defines the level of confidentiality of related data items.",
                "validInstanceProperties": [
                  "notes",
                  "steward",
                  "level",
                  "confidence",
                  "source",
                  "status",
                  "levelIdentifier",
                  "confidentialityLevel"
                ]
              },
              "instanceProvenanceType": "LOCAL_COHORT",
              "metadataCollectionId": "5ce8e35a-2df3-409d-b085-bb64356407c8",
              "metadataCollectionName": "igcproxy",
              "createdBy": "Administrator IIS",
              "updatedBy": "Administrator IIS",
              "maintainedBy": [
                "Administrator IIS"
              ],
              "createTime": "2019-12-10T16:19:48.000+00:00",
              "updateTime": "2019-12-10T16:20:14.000+00:00",
              "version": 1575994814000,
              "classificationOrigin": "ASSIGNED",
              "name": "Confidentiality",
              "properties": {
                "class": "InstanceProperties",
                "instanceProperties": {
                  "confidence": {
                    "class": "PrimitivePropertyValue",
                    "instancePropertyCategory": "PRIMITIVE",
                    "typeGUID": "7fc49104-fd3a-46c8-b6bf-f16b6074cd35",
                    "typeName": "int",
                    "primitiveDefCategory": "OM_PRIMITIVE_TYPE_INT",
                    "primitiveValue": 100
                  },
                  "levelIdentifier": {
                    "class": "PrimitivePropertyValue",
                    "instancePropertyCategory": "PRIMITIVE",
                    "typeGUID": "7fc49104-fd3a-46c8-b6bf-f16b6074cd35",
                    "typeName": "int",
                    "primitiveDefCategory": "OM_PRIMITIVE_TYPE_INT",
                    "primitiveValue": 3
                  },
                  "status": {
                    "class": "EnumPropertyValue",
                    "instancePropertyCategory": "ENUM",
                    "ordinal": 99,
                    "symbolicName": "Other",
                    "description": "Another classification assignment status."
                  }
                },
                "propertyNames": [
                  "confidence",
                  "levelIdentifier",
                  "status"
                ],
                "propertyCount": 3
              },
              "status": "ACTIVE"
            }
          ],
          "properties": {
            "class": "InstanceProperties",
            "instanceProperties": {
              "additionalProperties": {
                "class": "MapPropertyValue",
                "instancePropertyCategory": "MAP",
                "mapValues": {
                  "class": "InstanceProperties",
                  "instanceProperties": {
                    "glossary_type": {
                      "class": "PrimitivePropertyValue",
                      "instancePropertyCategory": "PRIMITIVE",
                      "typeGUID": "b34a64b9-554a-42b1-8f8a-7d5c2339f9c4",
                      "typeName": "string",
                      "primitiveDefCategory": "OM_PRIMITIVE_TYPE_STRING",
                      "primitiveValue": "PUBLISHED"
                    },
                    "is_modifier": {
                      "class": "PrimitivePropertyValue",
                      "instancePropertyCategory": "PRIMITIVE",
                      "typeGUID": "b34a64b9-554a-42b1-8f8a-7d5c2339f9c4",
                      "typeName": "string",
                      "primitiveDefCategory": "OM_PRIMITIVE_TYPE_STRING",
                      "primitiveValue": "false"
                    },
                    "type": {
                      "class": "PrimitivePropertyValue",
                      "instancePropertyCategory": "PRIMITIVE",
                      "typeGUID": "b34a64b9-554a-42b1-8f8a-7d5c2339f9c4",
                      "typeName": "string",
                      "primitiveDefCategory": "OM_PRIMITIVE_TYPE_STRING",
                      "primitiveValue": "NONE"
                    },
                    "status": {
                      "class": "PrimitivePropertyValue",
                      "instancePropertyCategory": "PRIMITIVE",
                      "typeGUID": "b34a64b9-554a-42b1-8f8a-7d5c2339f9c4",
                      "typeName": "string",
                      "primitiveDefCategory": "OM_PRIMITIVE_TYPE_STRING",
                      "primitiveValue": "ACCEPTED"
                    }
                  },
                  "propertyNames": [
                    "glossary_type",
                    "is_modifier",
                    "type",
                    "status"
                  ],
                  "propertyCount": 4
                },
                "mapElementCount": 4
              },
              "displayName": {
                "class": "PrimitivePropertyValue",
                "instancePropertyCategory": "PRIMITIVE",
                "typeGUID": "b34a64b9-554a-42b1-8f8a-7d5c2339f9c4",
                "typeName": "string",
                "primitiveDefCategory": "OM_PRIMITIVE_TYPE_STRING",
                "primitiveValue": "Email Address"
              },
              "qualifiedName": {
                "class": "PrimitivePropertyValue",
                "instancePropertyCategory": "PRIMITIVE",
                "typeGUID": "b34a64b9-554a-42b1-8f8a-7d5c2339f9c4",
                "typeName": "string",
                "primitiveDefCategory": "OM_PRIMITIVE_TYPE_STRING",
                "primitiveValue": "(category)=Coco Pharmaceuticals::(term)=Email Address"
              }
            },
            "propertyNames": [
              "displayName",
              "qualifiedName",
              "additionalProperties"
            ],
            "propertyCount": 3
          },
          "status": "ACTIVE"
        },
        ...
      ]
    }
    ```

Note that the results are returned in Egeria's type structure, as `GlossaryTerm`s with
`Classification`s and so on.

These are the same results that Egeria would see from IGC when running a federated query out
to a cohort that is running the IGC proxy connector, and would therefore be able to combine
with other metadata from other members of the cohort.

--8<-- "snippets/abbr.md"
