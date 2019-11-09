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
 * Captures the definition of / reference to another property type in the REST API.
 */
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class TypeReference extends ObjectPrinter {

    private String name;
    private String url;
    List<ValidValue> validValues;

    /**
     * Retrieve the name of this data type / reference.
     *
     * @return String
     */
    public String getName() { return name; }

    /**
     * Set the name of this data type / reference.
     *
     * @param name name of the data type / reference
     */
    public void setName(String name) { this.name = name; }

    /**
     * Retrieve the URL to more details about this type reference. Note that this is only non-null when the data type
     * of the property is a reference to a non-primitive IGC data type.
     *
     * @return String
     */
    public String getUrl() { return url; }

    /**
     * Set the URL to more details about this type reference.
     *
     * @param url the URL to more details about this type reference
     */
    public void setUrl(String url) { this.url = url; }

    /**
     * Retrieve the list of valid values for an enumeration. Note that this is only non-null / non-empty when the name
     * of the data type is {@code enum}.
     *
     * @return {@code List<ValidValues>}
     */
    public List<ValidValue> getValidValues() { return validValues; }

    /**
     * Set the list of valid values for an enumeration.
     *
     * @param validValues the list of valid values for an enumeration
     */
    public void setValidValues(List<ValidValue> validValues) { this.validValues = validValues; }

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
        TypeReference that = (TypeReference) objectToCompare;
        return Objects.equals(getName(), that.getName()) &&
                Objects.equals(getValidValues(), that.getValidValues());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(getName(), getValidValues());
    }

}
