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
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import java.util.Date;
import java.util.List;

/**
 * POJO for the {@code job_read_event} asset type in IGC, displayed as '{@literal Job Read Event}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=JobReadEvent.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("job_read_event")
public class JobReadEvent extends Reference {

    @JsonProperty("job_run_activity")
    protected InformationAsset jobRunActivity;

    @JsonProperty("reads_from")
    protected List<String> readsFrom;

    @JsonProperty("row_count")
    protected Number rowCount;

    @JsonProperty("time")
    protected Date time;

    /**
     * Retrieve the {@code job_run_activity} property (displayed as '{@literal Job Run Activity}') of the object.
     * @return {@code InformationAsset}
     */
    @JsonProperty("job_run_activity")
    public InformationAsset getJobRunActivity() { return this.jobRunActivity; }

    /**
     * Set the {@code job_run_activity} property (displayed as {@code Job Run Activity}) of the object.
     * @param jobRunActivity the value to set
     */
    @JsonProperty("job_run_activity")
    public void setJobRunActivity(InformationAsset jobRunActivity) { this.jobRunActivity = jobRunActivity; }

    /**
     * Retrieve the {@code reads_from} property (displayed as '{@literal Reads From}') of the object.
     * @return {@code List<String>}
     */
    @JsonProperty("reads_from")
    public List<String> getReadsFrom() { return this.readsFrom; }

    /**
     * Set the {@code reads_from} property (displayed as {@code Reads From}) of the object.
     * @param readsFrom the value to set
     */
    @JsonProperty("reads_from")
    public void setReadsFrom(List<String> readsFrom) { this.readsFrom = readsFrom; }

    /**
     * Retrieve the {@code row_count} property (displayed as '{@literal Rows Processed}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("row_count")
    public Number getRowCount() { return this.rowCount; }

    /**
     * Set the {@code row_count} property (displayed as {@code Rows Processed}) of the object.
     * @param rowCount the value to set
     */
    @JsonProperty("row_count")
    public void setRowCount(Number rowCount) { this.rowCount = rowCount; }

    /**
     * Retrieve the {@code time} property (displayed as '{@literal Time}') of the object.
     * @return {@code Date}
     */
    @JsonProperty("time")
    public Date getTime() { return this.time; }

    /**
     * Set the {@code time} property (displayed as {@code Time}) of the object.
     * @param time the value to set
     */
    @JsonProperty("time")
    public void setTime(Date time) { this.time = time; }

}
