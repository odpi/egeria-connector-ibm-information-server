/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.model;

public class ReportRow {
    private String sourceType;
    private String sourceAsset;
    private String sourceContext;
    private String sourceURL;
    private String sourceId;
    private String targetType;
    private String targetAsset;
    private String targetContext;
    private String targetURL;
    private String targetId;

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getSourceAsset() {
        return sourceAsset;
    }

    public void setSourceAsset(String sourceAsset) {
        this.sourceAsset = sourceAsset;
    }

    public String getSourceContext() {
        return sourceContext;
    }

    public void setSourceContext(String sourceContext) {
        this.sourceContext = sourceContext;
    }

    public String getSourceURL() {
        return sourceURL;
    }

    public void setSourceURL(String sourceURL) {
        this.sourceURL = sourceURL;
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    public String getTargetAsset() {
        return targetAsset;
    }

    public void setTargetAsset(String targetAsset) {
        this.targetAsset = targetAsset;
    }

    public String getTargetContext() {
        return targetContext;
    }

    public void setTargetContext(String targetContext) {
        this.targetContext = targetContext;
    }

    public String getTargetURL() {
        return targetURL;
    }

    public void setTargetURL(String targetURL) {
        this.targetURL = targetURL;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    @Override
    public String toString() {
        return "ReportRow{" +
                "sourceType='" + sourceType + '\'' +
                ", sourceAsset='" + sourceAsset + '\'' +
                ", sourceContext='" + sourceContext + '\'' +
                ", sourceURL='" + sourceURL + '\'' +
                ", targetType='" + targetType + '\'' +
                ", targetAsset='" + targetAsset + '\'' +
                ", targetContext='" + targetContext + '\'' +
                ", targetURL='" + targetURL + '\'' +
                '}';
    }
}
