/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Schedule {

    @JacksonXmlProperty(isAttribute = true) private Date startDate;
    @JacksonXmlProperty(isAttribute = true) private Date runUntil;
    @JacksonXmlProperty(isAttribute = true) private Integer nbOfInstances;
    private String description;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "ScheduleRepeat")
    private List<ScheduleRepeat> scheduleRepeats;

    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }

    public Date getRunUntil() { return runUntil; }
    public void setRunUntil(Date runUntil) { this.runUntil = runUntil; }

    public Integer getNbOfInstances() { return nbOfInstances; }
    public void setNbOfInstances(Integer nbOfInstances) { this.nbOfInstances = nbOfInstances; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<ScheduleRepeat> getScheduleRepeats() { return scheduleRepeats; }
    public void setScheduleRepeats(List<ScheduleRepeat> scheduleRepeats) { this.scheduleRepeats = scheduleRepeats; }

    @Override
    public String toString() {
        return "{ \"startDate\": \"" + startDate
                + "\", \"runUntil\": \"" + runUntil
                + "\", \"nbOfInstances\": " + nbOfInstances
                + ", \"description\": \"" + description
                + "\", \"ScheduleRepeats\": " + (scheduleRepeats == null ? "[]" : scheduleRepeats.toString())
                + " }";
    }

}
