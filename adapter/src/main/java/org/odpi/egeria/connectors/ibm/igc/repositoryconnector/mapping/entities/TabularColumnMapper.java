/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities;

import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.AttributeForSchemaMapper_RecordField;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.DataClassAssignmentMapper;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.SchemaAttributeTypeMapper_FileField;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSRepositoryConnector;

/**
 * Defines the mapping to the OMRS "TabularColumn" entity.
 */
public class TabularColumnMapper extends ReferenceableMapper {

    public TabularColumnMapper(IGCOMRSRepositoryConnector igcomrsRepositoryConnector, String userId) {

        // Start by calling the superclass's constructor to initialise the Mapper
        super(
                igcomrsRepositoryConnector,
                "data_file_field",
                "Data File Field",
                "TabularColumn",
                userId
        );

        // The list of properties that should be mapped
        addSimplePropertyMapping("name", "name");
        addSimplePropertyMapping("position", "position");

        // The list of relationships that should be mapped
        addRelationshipMapper(AttributeForSchemaMapper_RecordField.getInstance());
        addRelationshipMapper(SchemaAttributeTypeMapper_FileField.getInstance());
        addRelationshipMapper(DataClassAssignmentMapper.getInstance());

    }

}
