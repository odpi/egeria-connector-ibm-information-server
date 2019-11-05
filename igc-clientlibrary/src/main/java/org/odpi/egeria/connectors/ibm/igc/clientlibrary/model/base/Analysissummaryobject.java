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
 * POJO for the {@code analysissummaryobject} asset type in IGC, displayed as '{@literal AnalysisSummaryObject}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("analysissummaryobject")
public class Analysissummaryobject extends MainObject {

    @JsonProperty("is_viewable")
    protected Boolean isViewable;

    @JsonProperty("project_name")
    protected String projectName;

    @JsonProperty("promoted_by_principal")
    protected Steward promotedByPrincipal;

    @JsonProperty("review_date")
    protected Date reviewDate;

    @JsonProperty("reviewed_by_principal")
    protected Steward reviewedByPrincipal;

    /**
     * Retrieve the {@code is_viewable} property (displayed as '{@literal Is Viewable}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("is_viewable")
    public Boolean getIsViewable() { return this.isViewable; }

    /**
     * Set the {@code is_viewable} property (displayed as {@code Is Viewable}) of the object.
     * @param isViewable the value to set
     */
    @JsonProperty("is_viewable")
    public void setIsViewable(Boolean isViewable) { this.isViewable = isViewable; }

    /**
     * Retrieve the {@code project_name} property (displayed as '{@literal Project Name}') of the object.
     * @return {@code String}
     */
    @JsonProperty("project_name")
    public String getProjectName() { return this.projectName; }

    /**
     * Set the {@code project_name} property (displayed as {@code Project Name}) of the object.
     * @param projectName the value to set
     */
    @JsonProperty("project_name")
    public void setProjectName(String projectName) { this.projectName = projectName; }

    /**
     * Retrieve the {@code promoted_by_principal} property (displayed as '{@literal Promoted By Principal}') of the object.
     * @return {@code Steward}
     */
    @JsonProperty("promoted_by_principal")
    public Steward getPromotedByPrincipal() { return this.promotedByPrincipal; }

    /**
     * Set the {@code promoted_by_principal} property (displayed as {@code Promoted By Principal}) of the object.
     * @param promotedByPrincipal the value to set
     */
    @JsonProperty("promoted_by_principal")
    public void setPromotedByPrincipal(Steward promotedByPrincipal) { this.promotedByPrincipal = promotedByPrincipal; }

    /**
     * Retrieve the {@code review_date} property (displayed as '{@literal Review Date}') of the object.
     * @return {@code Date}
     */
    @JsonProperty("review_date")
    public Date getReviewDate() { return this.reviewDate; }

    /**
     * Set the {@code review_date} property (displayed as {@code Review Date}) of the object.
     * @param reviewDate the value to set
     */
    @JsonProperty("review_date")
    public void setReviewDate(Date reviewDate) { this.reviewDate = reviewDate; }

    /**
     * Retrieve the {@code reviewed_by_principal} property (displayed as '{@literal Reviewed By Principal}') of the object.
     * @return {@code Steward}
     */
    @JsonProperty("reviewed_by_principal")
    public Steward getReviewedByPrincipal() { return this.reviewedByPrincipal; }

    /**
     * Set the {@code reviewed_by_principal} property (displayed as {@code Reviewed By Principal}) of the object.
     * @param reviewedByPrincipal the value to set
     */
    @JsonProperty("reviewed_by_principal")
    public void setReviewedByPrincipal(Steward reviewedByPrincipal) { this.reviewedByPrincipal = reviewedByPrincipal; }

}
