/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JobLogEntry {

    @JacksonXmlProperty(isAttribute = true) private Integer id;
    @JacksonXmlProperty(isAttribute = true) private String messageId;
    @JacksonXmlProperty(isAttribute = true) private JobLogEntryType type;
    @JacksonXmlProperty(localName = "ShortMessage")
    private String shortMessage;
    @JacksonXmlProperty(localName = "FullMessage")
    private String fullMessage;
    @JacksonXmlProperty(localName = "UserInfo")
    private String userInfo;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getMessageId() { return messageId; }
    public void setMessageId(String messageId) { this.messageId = messageId; }

    public JobLogEntryType getType() { return type; }
    public void setType(JobLogEntryType type) { this.type = type; }

    public String getShortMessage() { return shortMessage; }
    public void setShortMessage(String shortMessage) { this.shortMessage = shortMessage; }

    public String getFullMessage() { return fullMessage; }
    public void setFullMessage(String fullMessage) { this.fullMessage = fullMessage; }

    public String getUserInfo() { return userInfo; }
    public void setUserInfo(String userInfo) { this.userInfo = userInfo; }

    @Override
    public String toString() {
        return "{ \"id\": " + id
                + ", \"messageId\": \"" + messageId
                + "\", \"type\": \"" + type
                + "\", \"shortMessage\": \"" + shortMessage
                + "\", \"fullMessage\": \"" + fullMessage
                + "\", \"userInfo\": \"" + userInfo
                + " }";
    }

}
