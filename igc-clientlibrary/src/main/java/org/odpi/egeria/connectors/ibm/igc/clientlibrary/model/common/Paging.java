/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common;

import com.fasterxml.jackson.annotation.*;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.PUBLIC_ONLY;

/**
 * Represents the metadata related to a page of results or relationships.
 */
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("paging")
public class Paging extends ObjectPrinter {

    /**
     * Specifies the total number of items that exist across all pages.
     */
    protected Integer numTotal;

    /**
     * Specifies the URL to the next page of results, within a given IGC environment. If there is no
     * next page of results, this will be null.
     * <br><br>
     * (In other words: if this is null, you are on the last page of results.)
     */
    protected String next;

    /**
     * Specifies the URL to the previous page of results, within a given IGC environment. If there is no
     * previous page of results, this will be null.
     * <br><br>
     * (In other words: if this is null, you are on the first page of results.)
     */
    protected String previous;

    /**
     * Specifies the number of results within each page.
     */
    protected Integer pageSize;

    /**
     * Specifies the numeric index at which this page of results ends.
     */
    protected Integer end;

    /**
     * Specifies the numeric index at which this page of results starts.
     */
    protected Integer begin;

    /**
     * Default constructor sets up defaults for an empty paging object.
     */
    public Paging() {
        this.numTotal = 0;
        this.next = null;
        this.previous = null;
        this.pageSize = 0;
        this.end = 0;
        this.begin = 0;
    }

    /**
     * Creates a new "full" Paging object (without any previous or next pages).
     *
     * @param numTotal total number of objects that this "page" represents containing
     */
    public Paging(Integer numTotal) {
        this();
        this.numTotal = numTotal;
        this.pageSize = numTotal;
        this.end = numTotal;
    }

    /**
     * Retrieve the total number of items across all pages.
     * @return Integer
     */
    @JsonProperty("numTotal")
    public Integer getNumTotal() { return this.numTotal; }

    /**
     * Set the total number of items across all pages.
     * @param numTotal of items across all pages
     */
    @JsonProperty("numTotal")
    public void setNumTotal(Integer numTotal) { this.numTotal = numTotal; }

    /**
     * Retrieve the URL for the next page of results, or null if there are no more pages.
     * @return String
     */
    @JsonProperty("next")
    public String getNextPageURL() { return this.next; }

    /**
     * Set the URL for the next page of results, or null if there are no more pages.
     * @param next URL for the next page of results
     */
    @JsonProperty("next")
    public void setNextPageURL(String next) { this.next = next; }

    /**
     * Retrieve the URL for the previous page of results, or null if there is no previous page.
     * @return String
     */
    @JsonProperty("previous")
    public String getPreviousPageURL() { return this.previous; }

    /**
     * Set the URL for the previous page of results, or null if there is no previous page.
     * @param previous URL for the previous page of results
     */
    @JsonProperty("previous")
    public void setPreviousPageURL(String previous) { this.previous = previous; }

    /**
     * Retrieve the number of items included in each page.
     * @return Integer
     */
    @JsonProperty("pageSize")
    public Integer getPageSize() { return this.pageSize; }

    /**
     * Set the number of items included in each page.
     * @param pageSize number of items included in each page
     */
    @JsonProperty("pageSize")
    public void setPageSize(Integer pageSize) { this.pageSize = pageSize; }

    /**
     * Retrieve the numeric index representing the last item in this page.
     * @return Integer
     */
    @JsonProperty("end")
    public Integer getEndIndex() { return this.end; }

    /**
     * Set the numeric index representing the last item in this page.
     * @param end numeric index of last item in this page
     */
    @JsonProperty("end")
    public void setEndIndex(Integer end) { this.end = end; }

    /**
     * Retrieve the numeric index representing the first item in this page.
     * @return Integer
     */
    @JsonProperty("begin")
    public Integer getBeginIndex() { return this.begin; }

    /**
     * Set the numeric index representing the first item in this page.
     * @param begin numeric index of first item in this page
     */
    @JsonProperty("begin")
    public void setBeginIndex(Integer begin) { this.begin = begin; }

    /**
     * Returns true iff there are more (unretrieved) pages for the paging that this object represents.
     *
     * @return Boolean
     */
    public Boolean hasMore() {
        return (this.numTotal > (this.end + 1));
    }

}
