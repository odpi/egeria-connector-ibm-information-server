/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.classifications;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;

/**
 * Singleton defining the mapping to the OMRS "TypeEmbeddedAttribute" classification.
 */
public class TypeEmbeddedAttributeMapper_RelationalColumn extends TypeEmbeddedAttributeMapper {

    private static class Singleton {
        private static final TypeEmbeddedAttributeMapper_RelationalColumn INSTANCE = new TypeEmbeddedAttributeMapper_RelationalColumn();
    }
    public static TypeEmbeddedAttributeMapper_RelationalColumn getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    private TypeEmbeddedAttributeMapper_RelationalColumn() {
        super(
                "database_column",
                null,
                "RelationalColumn",
                "TypeEmbeddedAttribute"
        );
        addSimplePropertyMapping("data_type", "dataType");
    }

}
