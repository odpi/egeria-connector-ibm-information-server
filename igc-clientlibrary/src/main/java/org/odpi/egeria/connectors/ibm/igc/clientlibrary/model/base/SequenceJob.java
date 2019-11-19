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
 * POJO for the {@code sequence_job} asset type in IGC, displayed as '{@literal Sequence Job}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=SequenceJob.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("sequence_job")
public class SequenceJob extends Dsjob {

    @JsonProperty("sequences_jobs")
    protected ItemList<Dsjob> sequencesJobs;

    /**
     * Retrieve the {@code sequences_jobs} property (displayed as '{@literal Sequences Jobs}') of the object.
     * @return {@code ItemList<Dsjob>}
     */
    @JsonProperty("sequences_jobs")
    public ItemList<Dsjob> getSequencesJobs() { return this.sequencesJobs; }

    /**
     * Set the {@code sequences_jobs} property (displayed as {@code Sequences Jobs}) of the object.
     * @param sequencesJobs the value to set
     */
    @JsonProperty("sequences_jobs")
    public void setSequencesJobs(ItemList<Dsjob> sequencesJobs) { this.sequencesJobs = sequencesJobs; }

}
