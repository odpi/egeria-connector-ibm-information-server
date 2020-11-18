/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.mapping;

import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.DataStageConnector;
import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.auditlog.DataStageErrorCode;
import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.model.DataStageCache;
import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.model.DataStageJob;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.errors.IGCException;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.Classificationenabledgroup;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.DataItem;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.Link;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.StageVariable;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.interfaces.ColumnLevelLineage;
import org.odpi.openmetadata.accessservices.dataengine.model.Attribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Mappings for creating a set of Attributes.
 */
class AttributeMapping extends BaseMapping {

    private static final Logger log = LoggerFactory.getLogger(AttributeMapping.class);

    private List<Attribute> attributes;

    /**
     * Creates a set of Attributes for the provided job and link information.
     *
     * @param cache used by this mapping
     * @param job the job for which to create the Attributes
     * @param link the link containing stage column detail for the Attributes
     * @param fullyQualifiedStageName to ensure each attribute is unique
     */
    AttributeMapping(DataStageCache cache, DataStageJob job, Link link, String fullyQualifiedStageName) {
        super(cache);
        final String methodName = "AttributeMapping";
        log.debug("Creating new AttributeMapping from job and link...");
        attributes = new ArrayList<>();
        if (link != null) {
            ItemList<DataItem> stageColumns = link.getStageColumns();
            try {
                List<DataItem> allStageColumns = igcRestClient.getAllPages("stage_columns", stageColumns);
                int index = 0;
                for (DataItem stageColumn : allStageColumns) {
                    log.debug("... calculating from detailed stage column: {}", stageColumn);
                    String colId = stageColumn.getId();
                    ColumnLevelLineage stageColumnObj = job.getColumnLevelLineageByRid(colId);
                    String stageColumnQN = getFullyQualifiedName(stageColumnObj);
                    if (stageColumnQN != null) {
                        Attribute attribute = new Attribute();
                        attribute.setQualifiedName(getFullyQualifiedName(stageColumnObj, fullyQualifiedStageName));
                        attribute.setDisplayName(stageColumnObj.getName());
                        attribute.setDataType(stageColumnObj.getOdbcType());
                        attribute.setPosition(index);
                        attributes.add(attribute);
                        index++;
                    } else {
                        log.error("Unable to determine identity for linked column -- not including it: {}", stageColumn);
                    }
                }
            } catch (IGCException e) {
                DataStageConnector.raiseRuntimeError(DataStageErrorCode.UNKNOWN_RUNTIME_ERROR,
                        this.getClass().getName(),
                        methodName,
                        e);
            }
        }
    }

    /**
     * Creates a set of Attributes for the provided data store field information.
     *
     * @param cache used by this mapping
     * @param fields the data store fields containing detail for the Attributes
     * @param fullyQualifiedStageName the qualified name of the stage to ensure each attribute is unique
     */
    AttributeMapping(DataStageCache cache, List<Classificationenabledgroup> fields, String fullyQualifiedStageName) {
        super(cache);
        final String methodName = "AttributeMapping";
        log.debug("Creating new AttributeMapping from job and fields...");
        attributes = new ArrayList<>();
        if (fields != null && !fields.isEmpty()) {
            try {
                for (Classificationenabledgroup field : fields) {
                    String fieldQN = getFullyQualifiedName(field);
                    if (fieldQN != null) {
                        Attribute attribute = new Attribute();
                        attribute.setQualifiedName(getFullyQualifiedName(field, fullyQualifiedStageName));
                        attribute.setDisplayName(field.getName());
                        String dataType = field.getDataType();
                        if (dataType != null) {
                            attribute.setDataType(dataType);
                        } else {
                            attribute.setDataType(field.getOdbcType());
                        }
                        Number position = field.getPosition();
                        if (position != null) {
                            attribute.setPosition(position.intValue());
                        }
                        attribute.setDefaultValue(field.getDefaultValue());
                        attributes.add(attribute);
                    } else {
                        log.error("Unable to determine identity for field -- not including it: {}", field);
                    }
                }
            } catch (IGCException e) {
                DataStageConnector.raiseRuntimeError(DataStageErrorCode.UNKNOWN_RUNTIME_ERROR,
                        this.getClass().getName(),
                        methodName,
                        e);
            }
        }
    }

    /**
     * Creates a set of Attributes for the provided data store field information (for virtual assets).
     *
     * @param cache used by this mapping
     * @param fields the data store fields containing detail for the Attributes
     */
    AttributeMapping(DataStageCache cache, List<Classificationenabledgroup> fields) {
        this(cache, fields, null);
    }

    /**
     * Creates a set of Attributes for the provided stage variable information.
     *
     * @param cache used by this mapping
     * @param job the job for which to create the Attributes
     * @param stageVariables for which to create a set of attributes
     * @param fullyQualifiedStageName (always empty)
     */
    AttributeMapping(DataStageCache cache, DataStageJob job, List<StageVariable> stageVariables, String fullyQualifiedStageName) {
        super(cache);
        final String methodName = "AttributeMapping";
        log.debug("Creating new AttributeMapping from stage variables...");
        attributes = new ArrayList<>();
        if (stageVariables != null && !stageVariables.isEmpty()) {
            try {
                for (StageVariable var : stageVariables) {
                    ColumnLevelLineage stageVar = job.getColumnLevelLineageByRid(var.getId());
                    String varQN = getFullyQualifiedName(stageVar, fullyQualifiedStageName);
                    if (varQN != null) {
                        Attribute attribute = new Attribute();
                        attribute.setQualifiedName(varQN);
                        attribute.setDisplayName(var.getName());
                        attribute.setDataType(var.getOdbcType());
                        attributes.add(attribute);
                    } else {
                        log.error("Unable to determine identity for variable -- not including it: {}", var);
                    }
                }
            } catch (IGCException e) {
                DataStageConnector.raiseRuntimeError(DataStageErrorCode.UNKNOWN_RUNTIME_ERROR,
                        this.getClass().getName(),
                        methodName,
                        e);
            }
        }
    }

    /**
     * Retrieve the Attributes that were setup.
     *
     * @return {@code List<Attribute>}
     */
    List<Attribute> getAttributes() { return attributes; }

}
