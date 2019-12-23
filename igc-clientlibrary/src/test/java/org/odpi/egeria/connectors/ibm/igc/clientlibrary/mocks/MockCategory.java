/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.clientlibrary.mocks;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.Category;

/**
 * MockCategory provides a concrete Category object to test various POJO-related methods.
 */
public class MockCategory extends Category {

    /**
     * Create a mock Category with some predefined values
     */
    public MockCategory() {
        super();
        setType("category");
        setId("6662c0f2.ee6a64fe.o1h6evefs.3cd0db2.onm1g1.3auq0edm3j6k2gumuks96");
        setUrl("https://infosvr:9446/ibm/iis/igc/#dossierView/6662c0f2.ee6a64fe.o1h6evefs.3cd0db2.onm1g1.3auq0edm3j6k2gumuks96");
        setName("Organization");
    }

}
