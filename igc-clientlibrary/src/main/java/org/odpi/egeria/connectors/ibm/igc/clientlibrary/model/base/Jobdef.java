/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeName;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.PUBLIC_ONLY;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;

/**
 * POJO for the {@code jobdef} asset type in IGC, displayed as '{@literal JobDef}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("jobdef")
public class Jobdef extends MainObject {

    @JsonProperty("alias")
    protected ItemList<MainObject> alias;

    /**
     * Retrieve the {@code alias} property (displayed as '{@literal Alias}') of the object.
     * @return {@code ItemList<MainObject>}
     */
    @JsonProperty("alias")
    public ItemList<MainObject> getAlias() { return this.alias; }

    /**
     * Set the {@code alias} property (displayed as {@code Alias}) of the object.
     * @param alias the value to set
     */
    @JsonProperty("alias")
    public void setAlias(ItemList<MainObject> alias) { this.alias = alias; }

}
