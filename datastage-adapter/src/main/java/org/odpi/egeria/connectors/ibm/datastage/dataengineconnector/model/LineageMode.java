/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Defines the mode in which the connector should operate for communicating lineage information.
 */
public enum LineageMode {

    GRANULAR("GRANULAR", "Full set of lineage information available (i.e. including individual stages within the jobs and column-level mappings)."),
    JOB_LEVEL("JOB_LEVEL", "Only communicate lineage information down to job level (not including stages within the jobs or column-level mappings).");

    private final String name;
    private final String description;

    /**
     * The constructor for LineageMode.
     *
     * @param name - symbolic name for the mode
     * @param description - description of the mode
     */
    LineageMode(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * Retrieve the unique name for the lineage mode.
     * @return String
     */
    @JsonValue
    public String getName() {
        return name;
    }

    /**
     * Translate the LineageMode into a printable form.
     * @return String
     */
    @Override
    public String toString() {
        return "LineageMode{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    /**
     * Instantiate the LineageMode represented by the symbolic name provided.
     * @param name from which to create a LineageMode
     * @return LineageMode
     */
    @JsonCreator
    public static LineageMode fromName(String name) {
        for (LineageMode b : LineageMode.values()) {
            if (b.name.equals(name)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unknown LineageMode: '" + name + "'");
    }

}
