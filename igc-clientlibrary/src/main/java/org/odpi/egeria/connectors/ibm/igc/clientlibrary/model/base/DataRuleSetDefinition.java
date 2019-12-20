/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeName;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.PUBLIC_ONLY;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;
import java.util.Date;

/**
 * POJO for the {@code data_rule_set_definition} asset type in IGC, displayed as '{@literal Data Rule Set Definition}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=DataRuleSetDefinition.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonSubTypes({
        @JsonSubTypes.Type(value = NonPublishedDataRuleSet.class, name = "non_published_data_rule_set"),
        @JsonSubTypes.Type(value = PublishedDataRuleSet.class, name = "published_data_rule_set"),
})
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("data_rule_set_definition")
public class DataRuleSetDefinition extends InformationAsset {

    @JsonProperty("contact")
    protected ItemList<Steward> contact;

    @JsonProperty("data_policies")
    protected ItemList<MainObject> dataPolicies;

    @JsonProperty("non_published_data_rule_set_definitions")
    protected NonPublishedDataRuleSet nonPublishedDataRuleSetDefinitions;

    @JsonProperty("project")
    protected String project;

    @JsonProperty("publication_date")
    protected Date publicationDate;

    @JsonProperty("published")
    protected Boolean published;

    @JsonProperty("published_data_rule_set_definitions")
    protected PublishedDataRuleSet publishedDataRuleSetDefinitions;

    /**
     * Valid values are:
     * <ul>
     *   <li>CANDIDATE (displayed in the UI as 'CANDIDATE')</li>
     *   <li>ACCEPTED (displayed in the UI as 'ACCEPTED')</li>
     *   <li>STANDARD (displayed in the UI as 'STANDARD')</li>
     *   <li>DEPRECATED (displayed in the UI as 'DEPRECATED')</li>
     *   <li>DRAFT (displayed in the UI as 'DRAFT')</li>
     *   <li>IN_PROCESS (displayed in the UI as 'IN_PROCESS')</li>
     *   <li>REJECTED (displayed in the UI as 'REJECTED')</li>
     *   <li>SUGGESTED (displayed in the UI as 'SUGGESTED')</li>
     *   <li>ERROR (displayed in the UI as 'ERROR')</li>
     * </ul>
     */
    @JsonProperty("status")
    protected String status;

    /**
     * Retrieve the {@code contact} property (displayed as '{@literal Contact}') of the object.
     * @return {@code ItemList<Steward>}
     */
    @JsonProperty("contact")
    public ItemList<Steward> getContact() { return this.contact; }

    /**
     * Set the {@code contact} property (displayed as {@code Contact}) of the object.
     * @param contact the value to set
     */
    @JsonProperty("contact")
    public void setContact(ItemList<Steward> contact) { this.contact = contact; }

    /**
     * Retrieve the {@code data_policies} property (displayed as '{@literal Data Policies}') of the object.
     * @return {@code ItemList<MainObject>}
     */
    @JsonProperty("data_policies")
    public ItemList<MainObject> getDataPolicies() { return this.dataPolicies; }

    /**
     * Set the {@code data_policies} property (displayed as {@code Data Policies}) of the object.
     * @param dataPolicies the value to set
     */
    @JsonProperty("data_policies")
    public void setDataPolicies(ItemList<MainObject> dataPolicies) { this.dataPolicies = dataPolicies; }

    /**
     * Retrieve the {@code non_published_data_rule_set_definitions} property (displayed as '{@literal Non Published Data Rule Set Definitions}') of the object.
     * @return {@code NonPublishedDataRuleSet}
     */
    @JsonProperty("non_published_data_rule_set_definitions")
    public NonPublishedDataRuleSet getNonPublishedDataRuleSetDefinitions() { return this.nonPublishedDataRuleSetDefinitions; }

    /**
     * Set the {@code non_published_data_rule_set_definitions} property (displayed as {@code Non Published Data Rule Set Definitions}) of the object.
     * @param nonPublishedDataRuleSetDefinitions the value to set
     */
    @JsonProperty("non_published_data_rule_set_definitions")
    public void setNonPublishedDataRuleSetDefinitions(NonPublishedDataRuleSet nonPublishedDataRuleSetDefinitions) { this.nonPublishedDataRuleSetDefinitions = nonPublishedDataRuleSetDefinitions; }

    /**
     * Retrieve the {@code project} property (displayed as '{@literal Project}') of the object.
     * @return {@code String}
     */
    @JsonProperty("project")
    public String getProject() { return this.project; }

    /**
     * Set the {@code project} property (displayed as {@code Project}) of the object.
     * @param project the value to set
     */
    @JsonProperty("project")
    public void setProject(String project) { this.project = project; }

    /**
     * Retrieve the {@code publication_date} property (displayed as '{@literal Publication Date}') of the object.
     * @return {@code Date}
     */
    @JsonProperty("publication_date")
    public Date getPublicationDate() { return this.publicationDate; }

    /**
     * Set the {@code publication_date} property (displayed as {@code Publication Date}) of the object.
     * @param publicationDate the value to set
     */
    @JsonProperty("publication_date")
    public void setPublicationDate(Date publicationDate) { this.publicationDate = publicationDate; }

    /**
     * Retrieve the {@code published} property (displayed as '{@literal Published}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("published")
    public Boolean getPublished() { return this.published; }

    /**
     * Set the {@code published} property (displayed as {@code Published}) of the object.
     * @param published the value to set
     */
    @JsonProperty("published")
    public void setPublished(Boolean published) { this.published = published; }

    /**
     * Retrieve the {@code published_data_rule_set_definitions} property (displayed as '{@literal Published Data Rule Set Definitions}') of the object.
     * @return {@code PublishedDataRuleSet}
     */
    @JsonProperty("published_data_rule_set_definitions")
    public PublishedDataRuleSet getPublishedDataRuleSetDefinitions() { return this.publishedDataRuleSetDefinitions; }

    /**
     * Set the {@code published_data_rule_set_definitions} property (displayed as {@code Published Data Rule Set Definitions}) of the object.
     * @param publishedDataRuleSetDefinitions the value to set
     */
    @JsonProperty("published_data_rule_set_definitions")
    public void setPublishedDataRuleSetDefinitions(PublishedDataRuleSet publishedDataRuleSetDefinitions) { this.publishedDataRuleSetDefinitions = publishedDataRuleSetDefinitions; }

    /**
     * Retrieve the {@code status} property (displayed as '{@literal Status}') of the object.
     * @return {@code String}
     */
    @JsonProperty("status")
    public String getStatus() { return this.status; }

    /**
     * Set the {@code status} property (displayed as {@code Status}) of the object.
     * @param status the value to set
     */
    @JsonProperty("status")
    public void setStatus(String status) { this.status = status; }

}
