/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.AttachedTagMapper;

/**
 * Defines the mapping to the OMRS "InformalTag" entity.
 */
public class InformalTagMapper extends ReferenceableMapper {

    private static class Singleton {
        private static final InformalTagMapper INSTANCE = new InformalTagMapper();
    }
    public static InformalTagMapper getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    protected InformalTagMapper() {

        // Start by calling the superclass's constructor to initialise the Mapper
        super(
                "label",
                "Label",
                "InformalTag",
                null,
                false
        );

        // The list of properties that should be mapped
        addSimplePropertyMapping("name", "tagName");
        addSimplePropertyMapping("description", "tagDescription");

        addLiteralPropertyMapping("isPublic", true);

        // The list of relationships that should be mapped
        addRelationshipMapper(AttachedTagMapper.getInstance(null));

    }

}
