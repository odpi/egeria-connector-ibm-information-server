/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RunTaskSequences extends AbstractTask {

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "TaskSequence")
    private List<TaskSequence> taskSequences;

    public List<TaskSequence> getTaskSequences() { return taskSequences; }
    public void setTaskSequences(List<TaskSequence> taskSequences) { this.taskSequences = taskSequences; }

    @Override
    public String toString() {
        String parent = super.toString();
        return parent.substring(0, parent.length() - 2)
                + ", \"TaskSequences\": " + (taskSequences == null ? "[]" : taskSequences.toString())
                + " }";
    }

}
