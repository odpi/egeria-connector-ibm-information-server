/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;

import java.util.ArrayList;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.PUBLIC_ONLY;

/**
 * Provides a standard class for any relationship in IGC, by including 'paging' details and 'items' array.
 */
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class ItemList<T extends Reference> extends ObjectPrinter {

    /**
     * The 'items' property of a ReferenceList gives the actual items that are part of this particular page.
     * <br><br>
     * Will be a ArrayList of {@link Reference} objects.
     */
    protected Paging paging = new Paging();

    /**
     * The 'items' property of a ReferenceList gives the actual items that are part of this particular page.
     * <br><br>
     * Will be a ArrayList of {@link Reference} objects.
     */
    protected List<T> items = new ArrayList<>();

    /**
     * Retrieve the paging characteristics of this list.
     *
     * @return Paging
     * @see #paging
     */
    public Paging getPaging() {
        return paging;
    }

    /**
     * Set the paging characteristics of this list.
     *
     * @param paging characteristics of this list
     * @see #paging
     */
    public void setPaging(Paging paging) {
        this.paging = paging;
    }

    /**
     * Retrieve the items within this list (from the pages that are retrieved).
     *
     * @return {@code List<T>}
     * @see #items
     */
    public List<T> getItems() {
        return items;
    }

    /**
     * Set the items within this list (for the pages that are retrieved).
     *
     * @param items within this list
     * @see #items
     */
    public void setItems(List<T> items) {
        this.items = items;
    }

    /**
     * Returns true iff there are more (unretrieved) pages for the relationships that this object represents.
     *
     * @return Boolean
     */
    public boolean hasMorePages() {
        return (this.paging.hasMore());
    }

    /**
     * Retrieve all pages of relationships that this object represents.
     *
     * @param igcrest the IGCRestClient connection to use to retrieve the relationships
     */
    public void getAllPages(IGCRestClient igcrest) {
        if (this.items.size() < this.paging.getNumTotal()) {
            this.items = igcrest.getAllPages(this.items, this.paging);
            this.paging = new Paging(this.items.size());
        }
    }

    /**
     * Retrieve the next page of relationships that this object represents.
     *
     * @param igcrest the IGCRestClient connection to use to retrieve the relationships
     */
    public void getNextPage(IGCRestClient igcrest) {
        ItemList<T> nextPage = igcrest.getNextPage(this.paging);
        this.items = nextPage.getItems();
        this.paging = nextPage.getPaging();
    }

}
