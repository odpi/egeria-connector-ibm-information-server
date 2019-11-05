/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.types;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ObjectPrinter;

import java.util.Objects;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.PUBLIC_ONLY;

/**
 * Captures the most basic headline type information available from IGC REST API.
 */
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class TypeHeader extends ObjectPrinter {

    private boolean businessLineage;
    private String name;
    private String plural;
    private String id;
    private String className;
    private String url;
    private boolean dataLineage;
    private String group;

    /**
     * Retrieve whether this type is included in business lineage (true) or not (false).
     *
     * @return boolean
     */
    @JsonProperty("_businessLineage")
    public boolean includedInBusinessLineage() { return businessLineage; }

    /**
     * Set whether this type is included in business lineage (true) or not (false).
     *
     * @param businessLineage true to enable business lineage, false to disable it
     */
    @JsonProperty("_businessLineage")
    public void setBusinessLineage(boolean businessLineage) { this.businessLineage = businessLineage; }

    /**
     * Retrieve the display name of this type.
     *
     * @return String
     */
    @JsonProperty("_name")
    public String getName() { return name; }

    /**
     * Set the display name of this type.
     *
     * @param name the display name
     */
    @JsonProperty("_name")
    public void setName(String name) { this.name = name; }

    /**
     * Retrieve the plural form of the display name of this type.
     *
     * @return String
     */
    @JsonProperty("_plural")
    public String getPlural() { return plural; }

    /**
     * Set the plural form of the display name of this type.
     *
     * @param plural the plural form of the display name
     */
    @JsonProperty("_plural")
    public void setPlural(String plural) { this.plural = plural; }

    /**
     * Retrieve the ID of this type, as used in the REST API.
     *
     * @return String
     */
    @JsonProperty("_id")
    public String getId() { return this.id; }

    /**
     * Set the ID of this type, as used in the REST API.
     *
     * @param id the ID of this type
     */
    @JsonProperty("_id")
    public void setId(String id) { this.id = id; }

    /**
     * Retrieve the class name of this type, as used for xmeta.
     *
     * @return String
     */
    @JsonProperty("_class")
    public String getClassName() { return this.className; }

    /**
     * Set the class name of this type, as used for xmeta.
     *
     * @param className the class name of this type
     */
    @JsonProperty("_class")
    public void setClassName(String className) { this.className = className; }

    /**
     * Retrieve the URL to retrieve detailed information about the type via the REST API.
     *
     * @return String
     */
    @JsonProperty("_url")
    public String getUrl() { return url; }

    /**
     * Set the URL to retrieve detailed information about the type via the REST API.
     *
     * @param url the URL to retrieve detailed information about the type
     */
    @JsonProperty("_url")
    public void setUrl(String url) { this.url = url; }

    /**
     * Retrieve whether this type is included in data lineage (true) or not (false).
     *
     * @return boolean
     */
    @JsonProperty("_dataLineage")
    public boolean includedInDataLineage() { return dataLineage; }

    /**
     * Set whether this type is included in data lineage (true) or not (false).
     *
     * @param dataLineage true to enable data lineage, false to disable it
     */
    @JsonProperty("_dataLineage")
    public void setDataLineage(boolean dataLineage) { this.dataLineage = dataLineage; }

    /**
     * Retrieve optional field indicating the group of types to which this type belongs.
     *
     * @return String
     */
    @JsonProperty("_group")
    public String getGroup() { return group; }

    /**
     * Set optional field indicating the group of types to which this type belongs.
     *
     * @param group the group to which this type belongs
     */
    @JsonProperty("_group")
    public void setGroup(String group) { this.group = group; }

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
        TypeHeader that = (TypeHeader) objectToCompare;
        // Note that we will leave out the URL comparison, since URL is environment-specific
        return Objects.equals(includedInBusinessLineage(), that.includedInBusinessLineage()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getPlural(), that.getPlural()) &&
                Objects.equals(getId(), that.getId()) &&
                Objects.equals(getClassName(), that.getClassName()) &&
                Objects.equals(includedInDataLineage(), that.includedInDataLineage()) &&
                Objects.equals(getGroup(), that.getGroup());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        // Note that we leave out the URL since it is environment-specific
        return Objects.hash(includedInBusinessLineage(),
                getName(),
                getPlural(),
                getId(),
                getClassName(),
                includedInDataLineage(),
                getGroup());
    }

}
