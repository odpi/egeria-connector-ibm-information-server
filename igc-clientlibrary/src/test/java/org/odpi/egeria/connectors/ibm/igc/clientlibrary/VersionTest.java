/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.clientlibrary;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class VersionTest {

    private IGCVersionEnum OLD = IGCVersionEnum.V11502SP5;
    private IGCVersionEnum NEW = IGCVersionEnum.V11710;

    public VersionTest() {
        // Do nothing...
    }

    @Test
    public void versionComparison() {

        assertTrue(OLD.isLowerThan(NEW));
        assertTrue(NEW.isHigherThan(OLD));
        assertFalse(NEW.isEqualTo(OLD));

    }

}
