/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.clientlibrary;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Identity;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class IdentityTest {

    private String FULL_IDENTITY_STRING = "(host_(engine))=INFOSVR::(database)=SOMETHING::(database_schema)=ELSE::(database_table)=TABLE::(database_column)=COLUMN";
    private String PART_IDENTITY_STRING = "gine))=INFOSVR::(database)=SOME";

    public IdentityTest() {
        // Do nothing...
    }

    @Test
    public void testFromString() {

        Identity full = Identity.getFromString(FULL_IDENTITY_STRING, null, false);
        assertNotNull(full);
        assertEquals(full.getAssetType(), "database_column");
        assertEquals(full.getName(), "COLUMN");

        Identity part = Identity.getPartialFromString(PART_IDENTITY_STRING);
        assertNotNull(part);
        assertTrue(part.isPartial());
        assertEquals(part.getAssetType(), "database");
        assertEquals(part.getName(), "SOME");

    }

}
