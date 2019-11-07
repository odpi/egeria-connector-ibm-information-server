/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.types;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ObjectPrinter;

import java.util.List;
import java.util.Objects;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.PUBLIC_ONLY;

/**
 * Captures the detailed type information available from IGC REST API.
 */
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class PropertyGrouping extends ObjectPrinter {

    private List<TypeProperty> properties;

    /**
     * Retrieve the list of properties contained within this grouping.
     *
     * @return {@code List<TypeProperty>}
     */
    public List<TypeProperty> getProperties() { return properties; }

    /**
     * Set the list of properties contained within this grouping.
     *
     * @param properties the list of properties contained within this grouping
     */
    public void setProperties(List<TypeProperty> properties) { this.properties = properties; }

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
        PropertyGrouping that = (PropertyGrouping) objectToCompare;
        return Objects.equals(getProperties(), that.getProperties());
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() { return Objects.hash(getProperties()); }

}
