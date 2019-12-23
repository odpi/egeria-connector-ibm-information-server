/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.clientlibrary;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class NamingTest {

    private String COMPOUND_TYPE_NAME = "database_column";

    public NamingTest() {
        // Do nothing...
    }

    @Test
    public void testCamelCases() {

        assertEquals(IGCRestConstants.getCamelCase(COMPOUND_TYPE_NAME), "DatabaseColumn");
        assertEquals(IGCRestConstants.getLowerCamelCase(COMPOUND_TYPE_NAME), "databaseColumn");
        assertEquals(IGCRestConstants.getGetterNameForProperty(COMPOUND_TYPE_NAME), "getDatabaseColumn");
        assertEquals(IGCRestConstants.getSetterNameForProperty(COMPOUND_TYPE_NAME), "setDatabaseColumn");

    }

}
