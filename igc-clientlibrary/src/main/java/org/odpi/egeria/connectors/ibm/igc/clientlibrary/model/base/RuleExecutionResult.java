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
import java.util.List;

/**
 * POJO for the {@code Rule_Execution_Result} asset type in IGC, displayed as '{@literal Rule Execution Result}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=RuleExecutionResult.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("Rule_Execution_Result")
public class RuleExecutionResult extends InformationAsset {

    @JsonProperty("benchmark")
    protected List<String> benchmark;

    @JsonProperty("nbFailed")
    protected Number nbfailed;

    @JsonProperty("nbPassed")
    protected Number nbpassed;

    @JsonProperty("nbRecordsTested")
    protected Number nbrecordstested;

    /**
     * Retrieve the {@code benchmark} property (displayed as '{@literal Benchmark}') of the object.
     * @return {@code List<String>}
     */
    @JsonProperty("benchmark")
    public List<String> getBenchmark() { return this.benchmark; }

    /**
     * Set the {@code benchmark} property (displayed as {@code Benchmark}) of the object.
     * @param benchmark the value to set
     */
    @JsonProperty("benchmark")
    public void setBenchmark(List<String> benchmark) { this.benchmark = benchmark; }

    /**
     * Retrieve the {@code nbFailed} property (displayed as '{@literal Number of Records Not Met}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("nbFailed")
    public Number getNbfailed() { return this.nbfailed; }

    /**
     * Set the {@code nbFailed} property (displayed as {@code Number of Records Not Met}) of the object.
     * @param nbfailed the value to set
     */
    @JsonProperty("nbFailed")
    public void setNbfailed(Number nbfailed) { this.nbfailed = nbfailed; }

    /**
     * Retrieve the {@code nbPassed} property (displayed as '{@literal Number of Records Met}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("nbPassed")
    public Number getNbpassed() { return this.nbpassed; }

    /**
     * Set the {@code nbPassed} property (displayed as {@code Number of Records Met}) of the object.
     * @param nbpassed the value to set
     */
    @JsonProperty("nbPassed")
    public void setNbpassed(Number nbpassed) { this.nbpassed = nbpassed; }

    /**
     * Retrieve the {@code nbRecordsTested} property (displayed as '{@literal Number of Records Tested}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("nbRecordsTested")
    public Number getNbrecordstested() { return this.nbrecordstested; }

    /**
     * Set the {@code nbRecordsTested} property (displayed as {@code Number of Records Tested}) of the object.
     * @param nbrecordstested the value to set
     */
    @JsonProperty("nbRecordsTested")
    public void setNbrecordstested(Number nbrecordstested) { this.nbrecordstested = nbrecordstested; }

}
