/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ForeignKeyCandidate {

    @JacksonXmlProperty(localName = "PrimaryKey")
    private BaseColumns primaryKey;
    @JacksonXmlProperty(localName = "ForeignKey")
    private PairedColumns foreignKey;
    @JacksonXmlProperty(localName = "ForeignKeyToPrimaryKey")
    private ForeignKeyToPrimaryKey foreignKeyToPrimaryKey;
    @JacksonXmlProperty(localName = "PrimaryKeyToForeignKey")
    private PrimaryKeyToForeignKey primaryKeyToForeignKey;

    public BaseColumns getPrimaryKey() { return primaryKey; }
    public void setPrimaryKey(BaseColumns primaryKey) { this.primaryKey = primaryKey; }

    public PairedColumns getForeignKey() { return foreignKey; }
    public void setForeignKey(PairedColumns foreignKey) { this.foreignKey = foreignKey; }

    public ForeignKeyToPrimaryKey getForeignKeyToPrimaryKey() { return foreignKeyToPrimaryKey; }
    public void setForeignKeyToPrimaryKey(ForeignKeyToPrimaryKey foreignKeyToPrimaryKey) { this.foreignKeyToPrimaryKey = foreignKeyToPrimaryKey; }

    public PrimaryKeyToForeignKey getPrimaryKeyToForeignKey() { return primaryKeyToForeignKey; }
    public void setPrimaryKeyToForeignKey(PrimaryKeyToForeignKey primaryKeyToForeignKey) { this.primaryKeyToForeignKey = primaryKeyToForeignKey; }

    @Override
    public String toString() {
        return "{ \"PrimaryKey\": " + (primaryKey == null ? "{}" : primaryKey.toString())
                + ", \"ForeignKey\": " + (foreignKey == null ? "{}" : foreignKey.toString())
                + ", \"ForeignKeyToPrimaryKey\": " + (foreignKeyToPrimaryKey == null ? "{}" : foreignKeyToPrimaryKey.toString())
                + ", \"PrimaryKeyToForeignKey\": " + (primaryKeyToForeignKey == null ? "{}" : primaryKeyToForeignKey.toString())
                + " }";
    }

}
