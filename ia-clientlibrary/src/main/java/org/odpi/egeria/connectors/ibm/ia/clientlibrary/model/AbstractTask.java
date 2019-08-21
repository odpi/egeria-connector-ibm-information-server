/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AbstractTask {

    @JacksonXmlProperty(localName = "SampleOptions")
    private SampleOptions sampleOptions;
    @JacksonXmlProperty(localName = "EngineOptions")
    private EngineOptions engineOptions;
    @JacksonXmlProperty(localName = "JobOptions")
    private JobOptions jobOptions;
    @JacksonXmlProperty(localName = "Schedule")
    private Schedule schedule;

    public SampleOptions getSampleOptions() { return sampleOptions; }
    public void setSampleOptions(SampleOptions sampleOptions) { this.sampleOptions = sampleOptions; }

    public EngineOptions getEngineOptions() { return engineOptions; }
    public void setEngineOptions(EngineOptions engineOptions) { this.engineOptions = engineOptions; }

    public JobOptions getJobOptions() { return jobOptions; }
    public void setJobOptions(JobOptions jobOptions) { this.jobOptions = jobOptions; }

    public Schedule getSchedule() { return schedule; }
    public void setSchedule(Schedule schedule) { this.schedule = schedule; }

    @Override
    public String toString() {
        return "{ \"SampleOptions\": " + (sampleOptions == null ? "{}" : sampleOptions.toString())
                + ", \"EngineOptions\": " + (engineOptions == null ? "{}" : engineOptions.toString())
                + ", \"JobOptions\": " + (jobOptions == null ? "{}" : jobOptions.toString())
                + ", \"Schedule\": " + (schedule == null ? "{}" : schedule.toString())
                + " }";
    }

}
