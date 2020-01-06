/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.clientlibrary.search;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Manages the criteria to use for sorting search results from IGC.
 */
public class IGCSearchSorting {

    private JsonNodeFactory nf = JsonNodeFactory.instance;

    private String property;
    private Boolean ascending;

    /**
     * Creates a new search sorting criteria directly. For example, the following would sort results by "name"
     * in descending order:
     * <ul>
     *     <li>property: "name"</li>
     *     <li>ascending: false</li>
     * </ul>
     *
     * @param property the property of an asset type to search against
     * @param ascending whether to sort results in ascending order (true) or descending order (false)
     */
    public IGCSearchSorting(String property, Boolean ascending) {
        this.property = property;
        this.ascending = ascending;
    }

    /**
     * Creates a new search sorting criteria directly, sorting by the provided property in ascending order.
     *
     * @param property the property of an asset type to search against
     */
    public IGCSearchSorting(String property) {
        this(property, true);
    }

    public String getProperty() { return this.property; }
    public void setProperty(String property) { this.property = property; }

    public Boolean getAscending() { return this.ascending; }
    public void setAscending(Boolean ascending) { this.ascending = ascending; }

    /**
     * Returns the JSON object representing the condition.
     *
     * @return ObjectNode
     */
    public ObjectNode getSortObject() {
        ObjectNode sortObj = nf.objectNode();
        sortObj.set("property", nf.textNode(getProperty()));
        sortObj.set("ascending", nf.booleanNode(getAscending()));
        return sortObj;
    }

}
