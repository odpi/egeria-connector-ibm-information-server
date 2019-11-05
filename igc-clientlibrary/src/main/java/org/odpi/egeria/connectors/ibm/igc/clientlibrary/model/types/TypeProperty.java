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
 * Captures the detailed information about a single property available from IGC REST API.
 */
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class TypeProperty extends ObjectPrinter {

    private int minCardinality = 0;
    private int maxCardinality = -1;
    private String name;
    private String displayName;
    private TypeReference type;

    /**
     * Retrieve the maximum number of occurrences of this property, where -1 means infinite.
     *
     * @return int
     */
    public int getMaxCardinality() { return maxCardinality; }

    /**
     * Set the maximum number of occurrences of this property, where -1 means infinite.
     *
     * @param maxCardinality maximum number of occurrences of this property
     */
    public void setMaxCardinality(int maxCardinality) { this.maxCardinality = maxCardinality; }

    /**
     * Retrieve the minimum number of occurrences of this property, where 1 means the property is mandatory.
     *
     * @return int
     */
    public int getMinCardinality() { return minCardinality; }

    /**
     * Set the minimum number of occurrences of this property, where 1 means the property is mandatory.
     *
     * @param minCardinality minimum number of occurrences of this property
     */
    public void setMinCardinality(int minCardinality) { this.minCardinality = minCardinality; }

    /**
     * Retrieve the name of this property.
     *
     * @return String
     */
    public String getName() { return name; }

    /**
     * Set the name of this property.
     *
     * @param name the name of this property
     */
    public void setName(String name) { this.name = name; }

    /**
     * Retrieve the display name of this property.
     *
     * @return String
     */
    public String getDisplayName() { return displayName; }

    /**
     * Set the display name of this property.
     *
     * @param displayName the display name of this property
     */
    public void setDisplayName(String displayName) { this.displayName = displayName; }

    /**
     * Retrieve the data type / reference of this property.
     *
     * @return TypeReference
     */
    public TypeReference getType() { return type; }

    /**
     * Set the data type / reference of this property.
     *
     * @param type the data type / reference of this property
     */
    public void setType(TypeReference type) { this.type = type; }

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
        TypeProperty that = (TypeProperty) objectToCompare;
        return Objects.equals(getMaxCardinality(), that.getMaxCardinality()) &&
                Objects.equals(getMinCardinality(), that.getMinCardinality()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getDisplayName(), that.getDisplayName()) &&
                Objects.equals(getType(), that.getType());
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(getMaxCardinality(),
                getMinCardinality(),
                getName(),
                getDisplayName(),
                getType());
    }

}
