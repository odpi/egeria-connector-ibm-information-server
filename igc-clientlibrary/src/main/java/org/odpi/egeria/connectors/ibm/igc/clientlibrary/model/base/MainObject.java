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
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;

/**
 * POJO for the {@code main_object} asset type in IGC, displayed as '{@literal Main Object}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=MainObject.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Analysissummaryobject.class, name = "analysissummaryobject"),
        @JsonSubTypes.Type(value = ApplicationInstall.class, name = "application_install"),
        @JsonSubTypes.Type(value = Array.class, name = "array"),
        @JsonSubTypes.Type(value = BiCollectionDimension.class, name = "bi_collection_dimension"),
        @JsonSubTypes.Type(value = BiCollectionFact.class, name = "bi_collection_fact"),
        @JsonSubTypes.Type(value = BiHierarchy.class, name = "bi_hierarchy"),
        @JsonSubTypes.Type(value = BiLevel.class, name = "bi_level"),
        @JsonSubTypes.Type(value = DataClassOld.class, name = "data_class_old"),
        @JsonSubTypes.Type(value = DataPolicy.class, name = "data_policy"),
        @JsonSubTypes.Type(value = Directory.class, name = "directory"),
        @JsonSubTypes.Type(value = DsdesignView.class, name = "dsdesign_view"),
        @JsonSubTypes.Type(value = Dsjcltemplate.class, name = "dsjcltemplate"),
        @JsonSubTypes.Type(value = Filterconstraint.class, name = "filterconstraint"),
        @JsonSubTypes.Type(value = Function.class, name = "function"),
        @JsonSubTypes.Type(value = InformationAsset.class, name = "information_asset"),
        @JsonSubTypes.Type(value = InformationServicesOperation.class, name = "information_services_operation"),
        @JsonSubTypes.Type(value = InformationServicesService.class, name = "information_services_service"),
        @JsonSubTypes.Type(value = Job.class, name = "job"),
        @JsonSubTypes.Type(value = JobStageRecord.class, name = "job_stage_record"),
        @JsonSubTypes.Type(value = Jobdef.class, name = "jobdef"),
        @JsonSubTypes.Type(value = Olapassociation.class, name = "olapassociation"),
        @JsonSubTypes.Type(value = Olaphierarchyassociation.class, name = "olaphierarchyassociation"),
        @JsonSubTypes.Type(value = Olapmodelgroup.class, name = "olapmodelgroup"),
        @JsonSubTypes.Type(value = Olapobject.class, name = "olapobject"),
        @JsonSubTypes.Type(value = ParameterSet2.class, name = "parameterset"),
        @JsonSubTypes.Type(value = SystemRole.class, name = "system_role"),
})
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("main_object")
public class MainObject extends Reference {

    @JsonProperty("assigned_to_terms")
    protected ItemList<Term> assignedToTerms;

    @JsonProperty("governed_by_rules")
    protected ItemList<InformationGovernanceRule> governedByRules;

    @JsonProperty("implements_rules")
    protected ItemList<InformationGovernanceRule> implementsRules;

    @JsonProperty("labels")
    protected ItemList<Label> labels;

    @JsonProperty("long_description")
    protected String longDescription;

    @JsonProperty("name")
    protected String name;

    @JsonProperty("short_description")
    protected String shortDescription;

    @JsonProperty("stewards")
    protected ItemList<AsclSteward> stewards;

    /**
     * Retrieve the {@code assigned_to_terms} property (displayed as '{@literal Assigned to Terms}') of the object.
     * @return {@code ItemList<Term>}
     */
    @JsonProperty("assigned_to_terms")
    public ItemList<Term> getAssignedToTerms() { return this.assignedToTerms; }

    /**
     * Set the {@code assigned_to_terms} property (displayed as {@code Assigned to Terms}) of the object.
     * @param assignedToTerms the value to set
     */
    @JsonProperty("assigned_to_terms")
    public void setAssignedToTerms(ItemList<Term> assignedToTerms) { this.assignedToTerms = assignedToTerms; }

    /**
     * Retrieve the {@code governed_by_rules} property (displayed as '{@literal Governed by Rules}') of the object.
     * @return {@code ItemList<InformationGovernanceRule>}
     */
    @JsonProperty("governed_by_rules")
    public ItemList<InformationGovernanceRule> getGovernedByRules() { return this.governedByRules; }

    /**
     * Set the {@code governed_by_rules} property (displayed as {@code Governed by Rules}) of the object.
     * @param governedByRules the value to set
     */
    @JsonProperty("governed_by_rules")
    public void setGovernedByRules(ItemList<InformationGovernanceRule> governedByRules) { this.governedByRules = governedByRules; }

    /**
     * Retrieve the {@code implements_rules} property (displayed as '{@literal Implements Rules}') of the object.
     * @return {@code ItemList<InformationGovernanceRule>}
     */
    @JsonProperty("implements_rules")
    public ItemList<InformationGovernanceRule> getImplementsRules() { return this.implementsRules; }

    /**
     * Set the {@code implements_rules} property (displayed as {@code Implements Rules}) of the object.
     * @param implementsRules the value to set
     */
    @JsonProperty("implements_rules")
    public void setImplementsRules(ItemList<InformationGovernanceRule> implementsRules) { this.implementsRules = implementsRules; }

    /**
     * Retrieve the {@code labels} property (displayed as '{@literal Labels}') of the object.
     * @return {@code ItemList<Label>}
     */
    @JsonProperty("labels")
    public ItemList<Label> getLabels() { return this.labels; }

    /**
     * Set the {@code labels} property (displayed as {@code Labels}) of the object.
     * @param labels the value to set
     */
    @JsonProperty("labels")
    public void setLabels(ItemList<Label> labels) { this.labels = labels; }

    /**
     * Retrieve the {@code long_description} property (displayed as '{@literal Long Description}') of the object.
     * @return {@code String}
     */
    @JsonProperty("long_description")
    public String getLongDescription() { return this.longDescription; }

    /**
     * Set the {@code long_description} property (displayed as {@code Long Description}) of the object.
     * @param longDescription the value to set
     */
    @JsonProperty("long_description")
    public void setLongDescription(String longDescription) { this.longDescription = longDescription; }

    /**
     * Retrieve the {@code name} property (displayed as '{@literal Name}') of the object.
     * @return {@code String}
     */
    @JsonProperty("name")
    public String getTheName() { return this.name; }

    /**
     * Set the {@code name} property (displayed as {@code Name}) of the object.
     * @param name the value to set
     */
    @JsonProperty("name")
    public void setTheName(String name) { this.name = name; }

    /**
     * Retrieve the {@code short_description} property (displayed as '{@literal Short Description}') of the object.
     * @return {@code String}
     */
    @JsonProperty("short_description")
    public String getShortDescription() { return this.shortDescription; }

    /**
     * Set the {@code short_description} property (displayed as {@code Short Description}) of the object.
     * @param shortDescription the value to set
     */
    @JsonProperty("short_description")
    public void setShortDescription(String shortDescription) { this.shortDescription = shortDescription; }

    /**
     * Retrieve the {@code stewards} property (displayed as '{@literal Stewards}') of the object.
     * @return {@code ItemList<AsclSteward>}
     */
    @JsonProperty("stewards")
    public ItemList<AsclSteward> getStewards() { return this.stewards; }

    /**
     * Set the {@code stewards} property (displayed as {@code Stewards}) of the object.
     * @param stewards the value to set
     */
    @JsonProperty("stewards")
    public void setStewards(ItemList<AsclSteward> stewards) { this.stewards = stewards; }

}
