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
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import java.util.Date;

/**
 * POJO for the {@code table_definition_properties} asset type in IGC, displayed as '{@literal Table Definition Properties}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("table_definition_properties")
public class TableDefinitionProperties extends Reference {

    @JsonProperty("a_xmeta_locking_root")
    protected String aXmetaLockingRoot;

    @JsonProperty("access_type")
    protected String accessType;

    @JsonProperty("allow_column_mapping")
    protected Boolean allowColumnMapping;

    @JsonProperty("apt_record_prop")
    protected String aptRecordProp;

    @JsonProperty("created_by")
    protected String createdBy;

    @JsonProperty("created_on")
    protected Date createdOn;

    @JsonProperty("import_location")
    protected String importLocation;

    @JsonProperty("locator")
    protected String locator;

    @JsonProperty("modified_by")
    protected String modifiedBy;

    @JsonProperty("modified_on")
    protected Date modifiedOn;

    @JsonProperty("multivalued")
    protected Boolean multivalued;

    @JsonProperty("nls_map_name")
    protected String nlsMapName;

    @JsonProperty("of_ds_table_definition")
    protected TableDefinition ofDsTableDefinition;

    @JsonProperty("pad_char")
    protected String padChar;

    @JsonProperty("platform_type")
    protected String platformType;

    @JsonProperty("registration_timestamp")
    protected String registrationTimestamp;

    @JsonProperty("seq_col_headers")
    protected Boolean seqColHeaders;

    @JsonProperty("seq_col_space")
    protected Number seqColSpace;

    @JsonProperty("seq_delimiter")
    protected String seqDelimiter;

    @JsonProperty("seq_fixed_width")
    protected Boolean seqFixedWidth;

    @JsonProperty("seq_null_string")
    protected String seqNullString;

    @JsonProperty("seq_omit_new_line")
    protected Boolean seqOmitNewLine;

    @JsonProperty("seq_quote_char")
    protected String seqQuoteChar;

    @JsonProperty("sp_error_codes")
    protected String spErrorCodes;

    @JsonProperty("version")
    protected String version;

    /**
     * Retrieve the {@code a_xmeta_locking_root} property (displayed as '{@literal A XMeta Locking Root}') of the object.
     * @return {@code String}
     */
    @JsonProperty("a_xmeta_locking_root")
    public String getAXmetaLockingRoot() { return this.aXmetaLockingRoot; }

    /**
     * Set the {@code a_xmeta_locking_root} property (displayed as {@code A XMeta Locking Root}) of the object.
     * @param aXmetaLockingRoot the value to set
     */
    @JsonProperty("a_xmeta_locking_root")
    public void setAXmetaLockingRoot(String aXmetaLockingRoot) { this.aXmetaLockingRoot = aXmetaLockingRoot; }

    /**
     * Retrieve the {@code access_type} property (displayed as '{@literal Access Type}') of the object.
     * @return {@code String}
     */
    @JsonProperty("access_type")
    public String getAccessType() { return this.accessType; }

    /**
     * Set the {@code access_type} property (displayed as {@code Access Type}) of the object.
     * @param accessType the value to set
     */
    @JsonProperty("access_type")
    public void setAccessType(String accessType) { this.accessType = accessType; }

    /**
     * Retrieve the {@code allow_column_mapping} property (displayed as '{@literal Allow Column Mapping}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("allow_column_mapping")
    public Boolean getAllowColumnMapping() { return this.allowColumnMapping; }

    /**
     * Set the {@code allow_column_mapping} property (displayed as {@code Allow Column Mapping}) of the object.
     * @param allowColumnMapping the value to set
     */
    @JsonProperty("allow_column_mapping")
    public void setAllowColumnMapping(Boolean allowColumnMapping) { this.allowColumnMapping = allowColumnMapping; }

    /**
     * Retrieve the {@code apt_record_prop} property (displayed as '{@literal APT Record Prop}') of the object.
     * @return {@code String}
     */
    @JsonProperty("apt_record_prop")
    public String getAptRecordProp() { return this.aptRecordProp; }

    /**
     * Set the {@code apt_record_prop} property (displayed as {@code APT Record Prop}) of the object.
     * @param aptRecordProp the value to set
     */
    @JsonProperty("apt_record_prop")
    public void setAptRecordProp(String aptRecordProp) { this.aptRecordProp = aptRecordProp; }

    /**
     * Retrieve the {@code created_by} property (displayed as '{@literal Created By}') of the object.
     * @return {@code String}
     */
    @JsonProperty("created_by")
    public String getCreatedBy() { return this.createdBy; }

    /**
     * Set the {@code created_by} property (displayed as {@code Created By}) of the object.
     * @param createdBy the value to set
     */
    @JsonProperty("created_by")
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    /**
     * Retrieve the {@code created_on} property (displayed as '{@literal Created On}') of the object.
     * @return {@code Date}
     */
    @JsonProperty("created_on")
    public Date getCreatedOn() { return this.createdOn; }

    /**
     * Set the {@code created_on} property (displayed as {@code Created On}) of the object.
     * @param createdOn the value to set
     */
    @JsonProperty("created_on")
    public void setCreatedOn(Date createdOn) { this.createdOn = createdOn; }

    /**
     * Retrieve the {@code import_location} property (displayed as '{@literal Import Location}') of the object.
     * @return {@code String}
     */
    @JsonProperty("import_location")
    public String getImportLocation() { return this.importLocation; }

    /**
     * Set the {@code import_location} property (displayed as {@code Import Location}) of the object.
     * @param importLocation the value to set
     */
    @JsonProperty("import_location")
    public void setImportLocation(String importLocation) { this.importLocation = importLocation; }

    /**
     * Retrieve the {@code locator} property (displayed as '{@literal Locator}') of the object.
     * @return {@code String}
     */
    @JsonProperty("locator")
    public String getLocator() { return this.locator; }

    /**
     * Set the {@code locator} property (displayed as {@code Locator}) of the object.
     * @param locator the value to set
     */
    @JsonProperty("locator")
    public void setLocator(String locator) { this.locator = locator; }

    /**
     * Retrieve the {@code modified_by} property (displayed as '{@literal Modified By}') of the object.
     * @return {@code String}
     */
    @JsonProperty("modified_by")
    public String getModifiedBy() { return this.modifiedBy; }

    /**
     * Set the {@code modified_by} property (displayed as {@code Modified By}) of the object.
     * @param modifiedBy the value to set
     */
    @JsonProperty("modified_by")
    public void setModifiedBy(String modifiedBy) { this.modifiedBy = modifiedBy; }

    /**
     * Retrieve the {@code modified_on} property (displayed as '{@literal Modified On}') of the object.
     * @return {@code Date}
     */
    @JsonProperty("modified_on")
    public Date getModifiedOn() { return this.modifiedOn; }

    /**
     * Set the {@code modified_on} property (displayed as {@code Modified On}) of the object.
     * @param modifiedOn the value to set
     */
    @JsonProperty("modified_on")
    public void setModifiedOn(Date modifiedOn) { this.modifiedOn = modifiedOn; }

    /**
     * Retrieve the {@code multivalued} property (displayed as '{@literal Multivalued}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("multivalued")
    public Boolean getMultivalued() { return this.multivalued; }

    /**
     * Set the {@code multivalued} property (displayed as {@code Multivalued}) of the object.
     * @param multivalued the value to set
     */
    @JsonProperty("multivalued")
    public void setMultivalued(Boolean multivalued) { this.multivalued = multivalued; }

    /**
     * Retrieve the {@code nls_map_name} property (displayed as '{@literal NLS Map Name}') of the object.
     * @return {@code String}
     */
    @JsonProperty("nls_map_name")
    public String getNlsMapName() { return this.nlsMapName; }

    /**
     * Set the {@code nls_map_name} property (displayed as {@code NLS Map Name}) of the object.
     * @param nlsMapName the value to set
     */
    @JsonProperty("nls_map_name")
    public void setNlsMapName(String nlsMapName) { this.nlsMapName = nlsMapName; }

    /**
     * Retrieve the {@code of_ds_table_definition} property (displayed as '{@literal Of DS Table Definition}') of the object.
     * @return {@code TableDefinition}
     */
    @JsonProperty("of_ds_table_definition")
    public TableDefinition getOfDsTableDefinition() { return this.ofDsTableDefinition; }

    /**
     * Set the {@code of_ds_table_definition} property (displayed as {@code Of DS Table Definition}) of the object.
     * @param ofDsTableDefinition the value to set
     */
    @JsonProperty("of_ds_table_definition")
    public void setOfDsTableDefinition(TableDefinition ofDsTableDefinition) { this.ofDsTableDefinition = ofDsTableDefinition; }

    /**
     * Retrieve the {@code pad_char} property (displayed as '{@literal Pad Char}') of the object.
     * @return {@code String}
     */
    @JsonProperty("pad_char")
    public String getPadChar() { return this.padChar; }

    /**
     * Set the {@code pad_char} property (displayed as {@code Pad Char}) of the object.
     * @param padChar the value to set
     */
    @JsonProperty("pad_char")
    public void setPadChar(String padChar) { this.padChar = padChar; }

    /**
     * Retrieve the {@code platform_type} property (displayed as '{@literal Platform Type}') of the object.
     * @return {@code String}
     */
    @JsonProperty("platform_type")
    public String getPlatformType() { return this.platformType; }

    /**
     * Set the {@code platform_type} property (displayed as {@code Platform Type}) of the object.
     * @param platformType the value to set
     */
    @JsonProperty("platform_type")
    public void setPlatformType(String platformType) { this.platformType = platformType; }

    /**
     * Retrieve the {@code registration_timestamp} property (displayed as '{@literal Registration Timestamp}') of the object.
     * @return {@code String}
     */
    @JsonProperty("registration_timestamp")
    public String getRegistrationTimestamp() { return this.registrationTimestamp; }

    /**
     * Set the {@code registration_timestamp} property (displayed as {@code Registration Timestamp}) of the object.
     * @param registrationTimestamp the value to set
     */
    @JsonProperty("registration_timestamp")
    public void setRegistrationTimestamp(String registrationTimestamp) { this.registrationTimestamp = registrationTimestamp; }

    /**
     * Retrieve the {@code seq_col_headers} property (displayed as '{@literal SEQ Col Headers}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("seq_col_headers")
    public Boolean getSeqColHeaders() { return this.seqColHeaders; }

    /**
     * Set the {@code seq_col_headers} property (displayed as {@code SEQ Col Headers}) of the object.
     * @param seqColHeaders the value to set
     */
    @JsonProperty("seq_col_headers")
    public void setSeqColHeaders(Boolean seqColHeaders) { this.seqColHeaders = seqColHeaders; }

    /**
     * Retrieve the {@code seq_col_space} property (displayed as '{@literal SEQ Col Space}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("seq_col_space")
    public Number getSeqColSpace() { return this.seqColSpace; }

    /**
     * Set the {@code seq_col_space} property (displayed as {@code SEQ Col Space}) of the object.
     * @param seqColSpace the value to set
     */
    @JsonProperty("seq_col_space")
    public void setSeqColSpace(Number seqColSpace) { this.seqColSpace = seqColSpace; }

    /**
     * Retrieve the {@code seq_delimiter} property (displayed as '{@literal SEQ Delimiter}') of the object.
     * @return {@code String}
     */
    @JsonProperty("seq_delimiter")
    public String getSeqDelimiter() { return this.seqDelimiter; }

    /**
     * Set the {@code seq_delimiter} property (displayed as {@code SEQ Delimiter}) of the object.
     * @param seqDelimiter the value to set
     */
    @JsonProperty("seq_delimiter")
    public void setSeqDelimiter(String seqDelimiter) { this.seqDelimiter = seqDelimiter; }

    /**
     * Retrieve the {@code seq_fixed_width} property (displayed as '{@literal SEQ Fixed Width}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("seq_fixed_width")
    public Boolean getSeqFixedWidth() { return this.seqFixedWidth; }

    /**
     * Set the {@code seq_fixed_width} property (displayed as {@code SEQ Fixed Width}) of the object.
     * @param seqFixedWidth the value to set
     */
    @JsonProperty("seq_fixed_width")
    public void setSeqFixedWidth(Boolean seqFixedWidth) { this.seqFixedWidth = seqFixedWidth; }

    /**
     * Retrieve the {@code seq_null_string} property (displayed as '{@literal SEQ Null String}') of the object.
     * @return {@code String}
     */
    @JsonProperty("seq_null_string")
    public String getSeqNullString() { return this.seqNullString; }

    /**
     * Set the {@code seq_null_string} property (displayed as {@code SEQ Null String}) of the object.
     * @param seqNullString the value to set
     */
    @JsonProperty("seq_null_string")
    public void setSeqNullString(String seqNullString) { this.seqNullString = seqNullString; }

    /**
     * Retrieve the {@code seq_omit_new_line} property (displayed as '{@literal SEQ Omit New Line}') of the object.
     * @return {@code Boolean}
     */
    @JsonProperty("seq_omit_new_line")
    public Boolean getSeqOmitNewLine() { return this.seqOmitNewLine; }

    /**
     * Set the {@code seq_omit_new_line} property (displayed as {@code SEQ Omit New Line}) of the object.
     * @param seqOmitNewLine the value to set
     */
    @JsonProperty("seq_omit_new_line")
    public void setSeqOmitNewLine(Boolean seqOmitNewLine) { this.seqOmitNewLine = seqOmitNewLine; }

    /**
     * Retrieve the {@code seq_quote_char} property (displayed as '{@literal SEQ Quote Char}') of the object.
     * @return {@code String}
     */
    @JsonProperty("seq_quote_char")
    public String getSeqQuoteChar() { return this.seqQuoteChar; }

    /**
     * Set the {@code seq_quote_char} property (displayed as {@code SEQ Quote Char}) of the object.
     * @param seqQuoteChar the value to set
     */
    @JsonProperty("seq_quote_char")
    public void setSeqQuoteChar(String seqQuoteChar) { this.seqQuoteChar = seqQuoteChar; }

    /**
     * Retrieve the {@code sp_error_codes} property (displayed as '{@literal SP Error Codes}') of the object.
     * @return {@code String}
     */
    @JsonProperty("sp_error_codes")
    public String getSpErrorCodes() { return this.spErrorCodes; }

    /**
     * Set the {@code sp_error_codes} property (displayed as {@code SP Error Codes}) of the object.
     * @param spErrorCodes the value to set
     */
    @JsonProperty("sp_error_codes")
    public void setSpErrorCodes(String spErrorCodes) { this.spErrorCodes = spErrorCodes; }

    /**
     * Retrieve the {@code version} property (displayed as '{@literal Version}') of the object.
     * @return {@code String}
     */
    @JsonProperty("version")
    public String getVersion() { return this.version; }

    /**
     * Set the {@code version} property (displayed as {@code Version}) of the object.
     * @param version the value to set
     */
    @JsonProperty("version")
    public void setVersion(String version) { this.version = version; }

}
