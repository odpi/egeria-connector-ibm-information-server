/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeName;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.PUBLIC_ONLY;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;

/**
 * POJO for the {@code credentials} asset type in IGC, displayed as '{@literal Credentials}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=Credentials.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("credentials")
public class Credentials extends Reference {

    @JsonProperty("asb_credential")
    protected Credential asbCredential;

    @JsonProperty("external_credential")
    protected ItemList<Credential> externalCredential;

    @JsonProperty("of_user")
    protected User ofUser;

    /**
     * Retrieve the {@code asb_credential} property (displayed as '{@literal Asb Credential}') of the object.
     * @return {@code Credential}
     */
    @JsonProperty("asb_credential")
    public Credential getAsbCredential() { return this.asbCredential; }

    /**
     * Set the {@code asb_credential} property (displayed as {@code Asb Credential}) of the object.
     * @param asbCredential the value to set
     */
    @JsonProperty("asb_credential")
    public void setAsbCredential(Credential asbCredential) { this.asbCredential = asbCredential; }

    /**
     * Retrieve the {@code external_credential} property (displayed as '{@literal External Credential}') of the object.
     * @return {@code ItemList<Credential>}
     */
    @JsonProperty("external_credential")
    public ItemList<Credential> getExternalCredential() { return this.externalCredential; }

    /**
     * Set the {@code external_credential} property (displayed as {@code External Credential}) of the object.
     * @param externalCredential the value to set
     */
    @JsonProperty("external_credential")
    public void setExternalCredential(ItemList<Credential> externalCredential) { this.externalCredential = externalCredential; }

    /**
     * Retrieve the {@code of_user} property (displayed as '{@literal Of User}') of the object.
     * @return {@code User}
     */
    @JsonProperty("of_user")
    public User getOfUser() { return this.ofUser; }

    /**
     * Set the {@code of_user} property (displayed as {@code Of User}) of the object.
     * @param ofUser the value to set
     */
    @JsonProperty("of_user")
    public void setOfUser(User ofUser) { this.ofUser = ofUser; }

}
