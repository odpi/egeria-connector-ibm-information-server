/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.clientlibrary.mocks;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.Category;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.Term;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * MockTerm provides a concrete Term object to test various POJO-related methods.
 */
public class MockTerm extends Term {

    /**
     * Create a mock Term with some predefined values.
     */
    public MockTerm() {
        super();

        setType("term");
        setId("abcd123.45asdf.93942asdf.fdsa9234.328asfddw");
        setName("Sample Term");
        setUrl("https://infosvr:9445/ibm/iis/igc/somewhere/something");
        setShortDescription("Just a simple short description");
        setLongDescription("A longer, more complete description of this sample object.");
        setCreatedBy("someone");
        setModifiedBy("someone else");
        setCreatedOn(new Date());
        setModifiedOn(new Date());

        Category parentCategory = new MockCategory();
        setParentCategory(parentCategory);

        List<Reference> ctx = new ArrayList<>();
        ctx.add(new MockGlossary());
        ctx.add(parentCategory);
        setContext(ctx);

    }

}
