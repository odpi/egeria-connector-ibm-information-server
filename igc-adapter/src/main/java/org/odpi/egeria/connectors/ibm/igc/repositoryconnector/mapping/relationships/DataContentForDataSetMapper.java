/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;

/**
 * Singleton to map the OMRS "DataContentForDataSet" relationship between IGC "database" and "database_schema" assets.
 */
public class DataContentForDataSetMapper extends RelationshipMapping {

    private static class Singleton {
        private static final DataContentForDataSetMapper INSTANCE = new DataContentForDataSetMapper();
    }
    public static DataContentForDataSetMapper getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    protected DataContentForDataSetMapper() {
        super(
                "database",
                "database_schema",
                "database_schemas",
                "database",
                "DataContentForDataSet",
                "dataContent",
                "supportedDataSets"
        );
        setContainedType(ContainedType.TWO);

        // Mapping to default
        addLiteralPropertyMapping("queryId", null);
        addLiteralPropertyMapping("query", null);
    }

}
