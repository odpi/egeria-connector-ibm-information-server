/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.classifications;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;

/**
 * Singleton defining the mapping to the OMRS "TypeEmbeddedAttribute" classification.
 */
public class TypeEmbeddedAttributeMapper_RelationalTable extends TypeEmbeddedAttributeMapper {

    private static class Singleton {
        private static final TypeEmbeddedAttributeMapper_RelationalTable INSTANCE = new TypeEmbeddedAttributeMapper_RelationalTable();
    }
    public static TypeEmbeddedAttributeMapper_RelationalTable getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    private TypeEmbeddedAttributeMapper_RelationalTable() {
        super(
                "database_table",
                null,
                "RelationalTable",
                "TypeEmbeddedAttribute"
        );
        addLiteralPropertyMapping("dataType", null);
    }

}
