/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.classifications;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;

/**
 * Singleton defining the mapping to the OMRS "TypeEmbeddedAttribute" classification.
 */
public class TypeEmbeddedAttributeMapper_TabularFileColumn extends TypeEmbeddedAttributeMapper {

    private static class Singleton {
        private static final TypeEmbeddedAttributeMapper_TabularFileColumn INSTANCE = new TypeEmbeddedAttributeMapper_TabularFileColumn();
    }
    public static TypeEmbeddedAttributeMapper_TabularFileColumn getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    protected TypeEmbeddedAttributeMapper_TabularFileColumn() {
        super(
                "data_file_field",
                null,
                "TabularFileColumn",
                "TypeEmbeddedAttribute"
        );
        addSimplePropertyMapping("data_type", "dataType");
    }

}
