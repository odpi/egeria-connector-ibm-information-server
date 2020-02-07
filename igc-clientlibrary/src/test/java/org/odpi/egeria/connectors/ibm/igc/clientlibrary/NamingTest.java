/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.clientlibrary;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class NamingTest {

    public NamingTest() {
        // Do nothing...
    }

    @Test
    public void testCamelCases() {

        String compoundTypeName = "database_column";

        assertEquals(IGCRestConstants.getCamelCase(compoundTypeName), "DatabaseColumn");
        assertEquals(IGCRestConstants.getLowerCamelCase(compoundTypeName), "databaseColumn");
        assertEquals(IGCRestConstants.getGetterNameForProperty(compoundTypeName), "getDatabaseColumn");
        assertEquals(IGCRestConstants.getSetterNameForProperty(compoundTypeName), "setDatabaseColumn");

        String problemCharactersTypeName = "does\nnot actually[exist";

        assertEquals(IGCRestConstants.getCamelCase(problemCharactersTypeName), "DoesNotActuallyExist");

    }

}
