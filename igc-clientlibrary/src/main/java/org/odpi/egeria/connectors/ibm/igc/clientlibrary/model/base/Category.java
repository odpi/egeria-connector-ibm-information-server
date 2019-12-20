/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeName;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.PUBLIC_ONLY;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;
import java.util.List;

/**
 * POJO for the {@code category} asset type in IGC, displayed as '{@literal Category}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=Category.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("category")
public class Category extends InformationAsset {

    @JsonProperty("category_path")
    protected ItemList<Category> categoryPath;

    /**
     * Valid values are:
     * <ul>
     *   <li>PUBLISHED (displayed in the UI as 'PUBLISHED')</li>
     *   <li>DRAFT (displayed in the UI as 'DRAFT')</li>
     * </ul>
     */
    @JsonProperty("glossary_type")
    protected String glossaryType;

    @JsonProperty("language")
    protected String language;

    @JsonProperty("parent_category")
    protected Category parentCategory;

    @JsonProperty("subcategories")
    protected ItemList<Category> subcategories;

    @JsonProperty("terms")
    protected ItemList<Term> terms;

    @JsonProperty("translations")
    protected ItemList<Category> translations;

    /**
     * Valid values are:
     * <ul>
     *   <li>DRAFT (displayed in the UI as 'DRAFT')</li>
     *   <li>WAITING_APPROVAL (displayed in the UI as 'WAITING_APPROVAL')</li>
     *   <li>APPROVED (displayed in the UI as 'APPROVED')</li>
     * </ul>
     */
    @JsonProperty("workflow_current_state")
    protected List<String> workflowCurrentState;

    /**
     * Valid values are:
     * <ul>
     *   <li>DRAFT (displayed in the UI as 'DRAFT')</li>
     *   <li>WAITING_APPROVAL (displayed in the UI as 'WAITING_APPROVAL')</li>
     *   <li>APPROVED (displayed in the UI as 'APPROVED')</li>
     * </ul>
     */
    @JsonProperty("workflow_stored_state")
    protected List<String> workflowStoredState;

    /**
     * Retrieve the {@code category_path} property (displayed as '{@literal Category Path}') of the object.
     * @return {@code ItemList<Category>}
     */
    @JsonProperty("category_path")
    public ItemList<Category> getCategoryPath() { return this.categoryPath; }

    /**
     * Set the {@code category_path} property (displayed as {@code Category Path}) of the object.
     * @param categoryPath the value to set
     */
    @JsonProperty("category_path")
    public void setCategoryPath(ItemList<Category> categoryPath) { this.categoryPath = categoryPath; }

    /**
     * Retrieve the {@code glossary_type} property (displayed as '{@literal Glossary Type}') of the object.
     * @return {@code String}
     */
    @JsonProperty("glossary_type")
    public String getGlossaryType() { return this.glossaryType; }

    /**
     * Set the {@code glossary_type} property (displayed as {@code Glossary Type}) of the object.
     * @param glossaryType the value to set
     */
    @JsonProperty("glossary_type")
    public void setGlossaryType(String glossaryType) { this.glossaryType = glossaryType; }

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

    /**
     * Retrieve the {@code translations} property (displayed as '{@literal Translations}') of the object.
     * @return {@code ItemList<Category>}
     */
    @JsonProperty("translations")
    public ItemList<Category> getTranslations() { return this.translations; }

    /**
     * Set the {@code translations} property (displayed as {@code Translations}) of the object.
     * @param translations the value to set
     */
    @JsonProperty("translations")
    public void setTranslations(ItemList<Category> translations) { this.translations = translations; }

    /**
     * Retrieve the {@code workflow_current_state} property (displayed as '{@literal Workflow Current State}') of the object.
     * @return {@code List<String>}
     */
    @JsonProperty("workflow_current_state")
    public List<String> getWorkflowCurrentState() { return this.workflowCurrentState; }

    /**
     * Set the {@code workflow_current_state} property (displayed as {@code Workflow Current State}) of the object.
     * @param workflowCurrentState the value to set
     */
    @JsonProperty("workflow_current_state")
    public void setWorkflowCurrentState(List<String> workflowCurrentState) { this.workflowCurrentState = workflowCurrentState; }

    /**
     * Retrieve the {@code workflow_stored_state} property (displayed as '{@literal Workflow Stored State}') of the object.
     * @return {@code List<String>}
     */
    @JsonProperty("workflow_stored_state")
    public List<String> getWorkflowStoredState() { return this.workflowStoredState; }

    /**
     * Set the {@code workflow_stored_state} property (displayed as {@code Workflow Stored State}) of the object.
     * @param workflowStoredState the value to set
     */
    @JsonProperty("workflow_stored_state")
    public void setWorkflowStoredState(List<String> workflowStoredState) { this.workflowStoredState = workflowStoredState; }

}
