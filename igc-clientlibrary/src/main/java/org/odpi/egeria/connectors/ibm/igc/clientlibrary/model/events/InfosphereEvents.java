/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.events;

import com.fasterxml.jackson.annotation.*;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.PUBLIC_ONLY;

/**
 * The base class of all InfosphereEvents topic messages from IBM Information Server, including
 * Information Governance Catalog, Metadata Asset Manager and Information Analyzer.
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="eventType", visible=true, defaultImpl=InfosphereEventsAssetEvent.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonSubTypes({
        @JsonSubTypes.Type(value = InfosphereEventsIMAMEvent.class, name = "IMAM_SHARE_EVENT"),
        @JsonSubTypes.Type(value = InfosphereEventsDCEvent.class, name = "DC_CREATE_EVENT"),
        @JsonSubTypes.Type(value = InfosphereEventsDCEvent.class, name = "DC_MERGED_EVENT"),
        @JsonSubTypes.Type(value = InfosphereEventsIAEvent.class, name = "IA_PROJECT_CREATED_EVENT"),
        @JsonSubTypes.Type(value = InfosphereEventsIAEvent.class, name = "IA_TABLE_ADDED_TO_PROJECT"),
        @JsonSubTypes.Type(value = InfosphereEventsIAEvent.class, name = "IA_TABLES_ADDED_TO_PROJECT"),
        @JsonSubTypes.Type(value = InfosphereEventsIAEvent.class, name = "IA_TABLE_REMOVED_FROM_PROJECT"),
        @JsonSubTypes.Type(value = InfosphereEventsIAEvent.class, name = "IA_COLUMN_ANALYSIS_SUBMITTED_EVENT"),
        @JsonSubTypes.Type(value = InfosphereEventsIAEvent.class, name = "IA_COLUMN_ANALYSES_SUBMITTED_EVENT"),
        @JsonSubTypes.Type(value = InfosphereEventsIAEvent.class, name = "IA_DATAQUALITY_ANALYSIS_SUBMITTED"),
        @JsonSubTypes.Type(value = InfosphereEventsIAEvent.class, name = "IA_DATAQUALITY_ANALYSES_SUBMITTED_EVENT"),
        @JsonSubTypes.Type(value = InfosphereEventsIAEvent.class, name = "IA_DATAQUALITY_ANALYSIS_SUBMITTED_EVENT"),
        @JsonSubTypes.Type(value = InfosphereEventsIAEvent.class, name = "IA_COLUMN_ANALYSIS_STARTED_EVENT"),
        @JsonSubTypes.Type(value = InfosphereEventsIAEvent.class, name = "IA_COLUMN_ANALYSIS_FINISHED_EVENT"),
        @JsonSubTypes.Type(value = InfosphereEventsIAEvent.class, name = "IA_COLUMN_CLASSIFIED_EVENT"),
        @JsonSubTypes.Type(value = InfosphereEventsIAEvent.class, name = "IA_COLUMN_ANALYZED_EVENT"),
        @JsonSubTypes.Type(value = InfosphereEventsIAEvent.class, name = "IA_COLUMN_ANALYSIS_FAILED_EVENT"),
        @JsonSubTypes.Type(value = InfosphereEventsIAEvent.class, name = "IA_COLUMN_FAILED_EVENT"),
        @JsonSubTypes.Type(value = InfosphereEventsIAEvent.class, name = "IA_DATAQUALITY_ANALYSIS_FAILED_EVENT"),
        @JsonSubTypes.Type(value = InfosphereEventsIAEvent.class, name = "IA_PROFILE_BATCH_COMPLETED_EVENT"),
        @JsonSubTypes.Type(value = InfosphereEventsIAEvent.class, name = "IA_DATAQUALITY_ANALYSIS_STARTED_EVENT"),
        @JsonSubTypes.Type(value = InfosphereEventsIAEvent.class, name = "IA_DATAQUALITY_ANALYSIS_FINISHED_EVENT"),
        @JsonSubTypes.Type(value = InfosphereEventsIAEvent.class, name = "IA_TABLE_RESULTS_PUBLISHED"),
        @JsonSubTypes.Type(value = InfosphereEventsRuleEvent.class, name = "IA_DATARULE_CREATED_EVENT"),
        @JsonSubTypes.Type(value = InfosphereEventsRuleEvent.class, name = "IA_DATARULE_DEFINITION_CREATED_EVENT"),
        @JsonSubTypes.Type(value = InfosphereEventsRuleEvent.class, name = "IA_DATARULESET_DEFINITION_CREATED_EVENT"),
        @JsonSubTypes.Type(value = InfosphereEventsRuleEvent.class, name = "IA_DATARULESET_CREATED_EVENT"),
        @JsonSubTypes.Type(value = InfosphereEventsRuleEvent.class, name = "IA_DATARULE_DEFINITION_MODIFIED_EVENT"),
        @JsonSubTypes.Type(value = InfosphereEventsRuleEvent.class, name = "IA_DATARULE_MODIFIED_EVENT"),
        @JsonSubTypes.Type(value = InfosphereEventsRuleEvent.class, name = "IA_DATARULESET_DEFINITION_MODIFIED_EVENT"),
        @JsonSubTypes.Type(value = InfosphereEventsRuleEvent.class, name = "IA_DATARULESET_MODIFIED_EVENT"),
        @JsonSubTypes.Type(value = InfosphereEventsDiscoverEvent.class, name = "DISCOVER_IMPORT_COMPLETE")
})
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class InfosphereEvents {

    protected String eventType;

    /**
     * Retrieve the type of this event, for example 'IGC_BUSINESSTERM_EVENT', 'IGC_BUSINESSCATEGORY_EVENT', etc.
     * @return String
     */
    @JsonProperty("eventType")
    public String getEventType() { return this.eventType; }

    /**
     * Set the type of this event.
     * @param eventType of this event
     */
    @JsonProperty("eventType")
    public void setEventType(String eventType) { this.eventType = eventType; }

}
