/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.clientlibrary.mocks;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.Category;

/**
 * MockGlossary provides a concrete Glossary object to test various POJO-related methods.
 */
public class MockGlossary extends Category {

    /**
     * Create a mock Glossary object with some predefined values.
     */
    public MockGlossary() {
        super();
        setType("category");
        setId("6662c0f2.ee6a64fe.00263pfar.1a0mm9a.lfjd3c.rmgl1cdd5fcd4bijur3g3");
        setUrl("https://infosvr:9446/ibm/iis/igc/#dossierView/6662c0f2.ee6a64fe.00263pfar.1a0mm9a.lfjd3c.rmgl1cdd5fcd4bijur3g3");
        setName("Coco Pharmaceuticals");
        setShortDescription("This glossary contains Glossary Terms and Categories that are related to the Coco Pharmaceuticals data");
    }

}
