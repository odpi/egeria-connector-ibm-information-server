/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeName;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.PUBLIC_ONLY;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;
import java.util.Date;

/**
 * POJO for the {@code category} asset type in IGC, displayed as '{@literal Category}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("category")
public class Category extends InformationAsset {

    @JsonProperty("in_collections")
    protected ItemList<Collection> inCollections;

    @JsonProperty("language")
    protected String language;

    @JsonProperty("parent_category")
    protected Category parentCategory;

    @JsonProperty("subcategories")
    protected ItemList<Category> subcategories;

    @JsonProperty("terms")
    protected ItemList<Term> terms;

    /**
     * Retrieve the {@code in_collections} property (displayed as '{@literal In Collections}') of the object.
     * @return {@code ItemList<Collection>}
     */
    @JsonProperty("in_collections")
    public ItemList<Collection> getInCollections() { return this.inCollections; }

    /**
     * Set the {@code in_collections} property (displayed as {@code In Collections}) of the object.
     * @param inCollections the value to set
     */
    @JsonProperty("in_collections")
    public void setInCollections(ItemList<Collection> inCollections) { this.inCollections = inCollections; }

    /**
     * Retrieve the {@code language} property (displayed as '{@literal Language}') of the object.
     * @return {@code String}
     */
    @JsonProperty("language")
    public String getLanguage() { return this.language; }

    /**
     * Set the {@code language} property (displayed as {@code Language}) of the object.
     * @param language the value to set
     */
    @JsonProperty("language")
    public void setLanguage(String language) { this.language = language; }

    /**
     * Retrieve the {@code parent_category} property (displayed as '{@literal Parent Category}') of the object.
     * @return {@code Category}
     */
    @JsonProperty("parent_category")
    public Category getParentCategory() { return this.parentCategory; }

    /**
     * Set the {@code parent_category} property (displayed as {@code Parent Category}) of the object.
     * @param parentCategory the value to set
     */
    @JsonProperty("parent_category")
    public void setParentCategory(Category parentCategory) { this.parentCategory = parentCategory; }

    /**
     * Retrieve the {@code subcategories} property (displayed as '{@literal Subcategories}') of the object.
     * @return {@code ItemList<Category>}
     */
    @JsonProperty("subcategories")
    public ItemList<Category> getSubcategories() { return this.subcategories; }

    /**
     * Set the {@code subcategories} property (displayed as {@code Subcategories}) of the object.
     * @param subcategories the value to set
     */
    @JsonProperty("subcategories")
    public void setSubcategories(ItemList<Category> subcategories) { this.subcategories = subcategories; }

    /**
     * Retrieve the {@code terms} property (displayed as '{@literal Terms}') of the object.
     * @return {@code ItemList<Term>}
     */
    @JsonProperty("terms")
    public ItemList<Term> getTerms() { return this.terms; }

    /**
     * Set the {@code terms} property (displayed as {@code Terms}) of the object.
     * @param terms the value to set
     */
    @JsonProperty("terms")
    public void setTerms(ItemList<Term> terms) { this.terms = terms; }

}
