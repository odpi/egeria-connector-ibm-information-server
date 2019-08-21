/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskSequenceExecutionResult extends AbstractRuleExecutionResult {

    @JacksonXmlProperty(isAttribute = true) private Long nbTsksCompleted;
    @JacksonXmlProperty(isAttribute = true) private Long nbTsksWithErrors;
    @JacksonXmlProperty(isAttribute = true) private String taskSequenceName;

    public Long getNbTsksCompleted() { return nbTsksCompleted; }
    public void setNbTsksCompleted(Long nbTsksCompleted) { this.nbTsksCompleted = nbTsksCompleted; }

    public Long getNbTsksWithErrors() { return nbTsksWithErrors; }
    public void setNbTsksWithErrors(Long nbTsksWithErrors) { this.nbTsksWithErrors = nbTsksWithErrors; }

    public String getTaskSequenceName() { return taskSequenceName; }
    public void setTaskSequenceName(String taskSequenceName) { this.taskSequenceName = taskSequenceName; }

    @Override
    public String toString() {
        String parent = super.toString();
        return parent.substring(0, parent.length() - 2)
                + ", \"nbTsksCompleted\": " + nbTsksCompleted
                + ", \"nbTsksWithErrors\": " + nbTsksWithErrors
                + ", \"taskSequenceName\": \"" + taskSequenceName
                + "\" }";
    }

}
