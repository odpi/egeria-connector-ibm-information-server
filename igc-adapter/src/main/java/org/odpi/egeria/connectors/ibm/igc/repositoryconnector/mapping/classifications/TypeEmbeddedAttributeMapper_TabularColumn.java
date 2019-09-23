/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.classifications;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;

/**
 * Singleton defining the mapping to the OMRS "TypeEmbeddedAttribute" classification.
 */
public class TypeEmbeddedAttributeMapper_TabularColumn extends TypeEmbeddedAttributeMapper {

    private static class Singleton {
        private static final TypeEmbeddedAttributeMapper_TabularColumn INSTANCE = new TypeEmbeddedAttributeMapper_TabularColumn();
    }
    public static TypeEmbeddedAttributeMapper_TabularColumn getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    private TypeEmbeddedAttributeMapper_TabularColumn() {
        super(
                "data_file_field",
                null,
                "TabularColumn",
                "TypeEmbeddedAttribute"
        );
        addSimplePropertyMapping("data_type", "dataType");
        addSimplePropertyMapping("default_value", "defaultValue");
    }

}
