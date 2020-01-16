/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base;

import com.fasterxml.jackson.annotation.*;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.PUBLIC_ONLY;

/**
 * POJO for the {@code classificationenabledgroup} asset type in IGC, displayed as '{@literal ClassificationEnabledGroup}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=Classificationenabledgroup.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonSubTypes({
        @JsonSubTypes.Type(value = AmazonS3DataFileField.class, name = "amazon_s3_data_file_field"),
        @JsonSubTypes.Type(value = DataFileField.class, name = "data_file_field"),
        @JsonSubTypes.Type(value = DatabaseColumn.class, name = "database_column"),
})
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("classificationenabledgroup")
public class Classificationenabledgroup extends DataField {

    @JsonProperty("detected_classifications")
    protected ItemList<Classification> detectedClassifications;

    @JsonProperty("hasDataClassification")
    protected ItemList<Classification> hasdataclassification;

    @JsonProperty("selected_classification")
    protected DataClass selectedClassification;

    /**
     * Retrieve the {@code detected_classifications} property (displayed as '{@literal Detected Data Classifications}') of the object.
     * @return {@code ItemList<Classification>}
     */
    @JsonProperty("detected_classifications")
    public ItemList<Classification> getDetectedClassifications() { return this.detectedClassifications; }

    /**
     * Set the {@code detected_classifications} property (displayed as {@code Detected Data Classifications}) of the object.
     * @param detectedClassifications the value to set
     */
    @JsonProperty("detected_classifications")
    public void setDetectedClassifications(ItemList<Classification> detectedClassifications) { this.detectedClassifications = detectedClassifications; }

    /**
     * Retrieve the {@code hasDataClassification} property (displayed as '{@literal Detected Data Classifications}') of the object.
     * @return {@code ItemList<Classification>}
     */
    @JsonProperty("hasDataClassification")
    public ItemList<Classification> getHasdataclassification() { return this.hasdataclassification; }

    /**
     * Set the {@code hasDataClassification} property (displayed as {@code Detected Data Classifications}) of the object.
     * @param hasdataclassification the value to set
     */
    @JsonProperty("hasDataClassification")
    public void setHasdataclassification(ItemList<Classification> hasdataclassification) { this.hasdataclassification = hasdataclassification; }

    /**
     * Retrieve the {@code selected_classification} property (displayed as '{@literal Selected Data Classification}') of the object.
     * @return {@code DataClass}
     */
    @JsonProperty("selected_classification")
    public DataClass getSelectedClassification() { return this.selectedClassification; }

    /**
     * Set the {@code selected_classification} property (displayed as {@code Selected Data Classification}) of the object.
     * @param selectedClassification the value to set
     */
    @JsonProperty("selected_classification")
    public void setSelectedClassification(DataClass selectedClassification) { this.selectedClassification = selectedClassification; }

}
