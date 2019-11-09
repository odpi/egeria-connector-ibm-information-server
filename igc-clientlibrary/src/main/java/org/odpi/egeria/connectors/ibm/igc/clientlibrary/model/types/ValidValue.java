/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.types;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ObjectPrinter;

import java.util.Objects;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.PUBLIC_ONLY;

/**
 * Captures a single valid value definition for an enumeration.
 */
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class ValidValue extends ObjectPrinter {

    private String displayName;
    private String id;

    /**
     * Retrieve the display name of this valid value.
     *
     * @return String
     */
    public String getDisplayName() { return displayName; }

    /**
     * Set the display name of this valid value.
     *
     * @param displayName the display name
     */
    public void setDisplayName(String displayName) { this.displayName = displayName; }

    /**
     * Retrieve the ID of this valid value.
     *
     * @return String
     */
    public String getId() { return id; }

    /**
     * Set the ID of this valid value
     *
     * @param id the ID
     */
    public void setId(String id) { this.id = id; }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object objectToCompare) {
        if (this == objectToCompare) {
            return true;
        }
        if (objectToCompare == null || getClass() != objectToCompare.getClass()) {
            return false;
        }
        ValidValue that = (ValidValue) objectToCompare;
        return Objects.equals(getDisplayName(), that.getDisplayName()) &&
                Objects.equals(getId(), that.getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(getDisplayName(), getId());
    }

}
