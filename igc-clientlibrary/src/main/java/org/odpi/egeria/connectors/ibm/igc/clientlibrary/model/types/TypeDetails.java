/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.types;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Objects;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.PUBLIC_ONLY;

/**
 * Captures the detailed type information available from IGC REST API.
 */
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class TypeDetails extends TypeHeader {

    private PropertyGrouping viewInfo;
    private PropertyGrouping editInfo;
    private PropertyGrouping createInfo;

    /**
     * Retrieve the properties that can be viewed for this type.
     *
     * @return PropertyGrouping
     */
    public PropertyGrouping getViewInfo() { return viewInfo; }

    /**
     * Set the properties that can be viewed for this type.
     *
     * @param viewInfo the properties that can be viewed
     */
    public void setViewInfo(PropertyGrouping viewInfo) { this.viewInfo = viewInfo; }

    /**
     * Retrieve the properties that can be edited for this type.
     *
     * @return PropertyGrouping
     */
    public PropertyGrouping getEditInfo() { return editInfo; }

    /**
     * Set the properties that can be edited for this type.
     *
     * @param editInfo the properties that can be edited
     */
    public void setEditInfo(PropertyGrouping editInfo) { this.editInfo = editInfo; }

    /**
     * Retrieve the properties that can be included when creating a new instance of this type.
     *
     * @return PropertyGrouping
     */
    public PropertyGrouping getCreateInfo() { return createInfo; }

    /**
     * Set the properties that can be included when creating a new instance of this type.
     *
     * @param createInfo the properties that can be included when creating a new instance
     */
    public void setCreateInfo(PropertyGrouping createInfo) { this.createInfo = createInfo; }

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
        if (!super.equals(objectToCompare)) {
            return false;
        }
        TypeDetails that = (TypeDetails) objectToCompare;
        return Objects.equals(getViewInfo(), that.getViewInfo()) &&
                Objects.equals(getEditInfo(), that.getEditInfo()) &&
                Objects.equals(getCreateInfo(), that.getCreateInfo());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(),
                getViewInfo(),
                getEditInfo(),
                getCreateInfo());
    }

}
