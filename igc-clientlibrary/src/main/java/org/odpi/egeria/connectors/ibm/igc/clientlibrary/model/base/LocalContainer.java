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

/**
 * POJO for the {@code local_container} asset type in IGC, displayed as '{@literal Local Container}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=LocalContainer.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("local_container")
public class LocalContainer extends SharedContainer {

    @JsonProperty("job_or_container")
    protected ItemList<MainObject> jobOrContainer;

    /**
     * Retrieve the {@code job_or_container} property (displayed as '{@literal Job or Container}') of the object.
     * @return {@code ItemList<MainObject>}
     */
    @JsonProperty("job_or_container")
    public ItemList<MainObject> getJobOrContainer() { return this.jobOrContainer; }

    /**
     * Set the {@code job_or_container} property (displayed as {@code Job or Container}) of the object.
     * @param jobOrContainer the value to set
     */
    @JsonProperty("job_or_container")
    public void setJobOrContainer(ItemList<MainObject> jobOrContainer) { this.jobOrContainer = jobOrContainer; }

}
