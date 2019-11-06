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
import java.util.Date;

/**
 * POJO for the {@code stage_type_detail} asset type in IGC, displayed as '{@literal Stage Type Detail}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=StageTypeDetail.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("stage_type_detail")
public class StageTypeDetail extends DataItem {

    @JsonProperty("display_caption")
    protected String displayCaption;

    @JsonProperty("of_stage_type")
    protected StageType ofStageType;

    /**
     * Retrieve the {@code display_caption} property (displayed as '{@literal Prompt}') of the object.
     * @return {@code String}
     */
    @JsonProperty("display_caption")
    public String getDisplayCaption() { return this.displayCaption; }

    /**
     * Set the {@code display_caption} property (displayed as {@code Prompt}) of the object.
     * @param displayCaption the value to set
     */
    @JsonProperty("display_caption")
    public void setDisplayCaption(String displayCaption) { this.displayCaption = displayCaption; }

    /**
     * Retrieve the {@code of_stage_type} property (displayed as '{@literal Stage Type}') of the object.
     * @return {@code StageType}
     */
    @JsonProperty("of_stage_type")
    public StageType getOfStageType() { return this.ofStageType; }

    /**
     * Set the {@code of_stage_type} property (displayed as {@code Stage Type}) of the object.
     * @param ofStageType the value to set
     */
    @JsonProperty("of_stage_type")
    public void setOfStageType(StageType ofStageType) { this.ofStageType = ofStageType; }

}
