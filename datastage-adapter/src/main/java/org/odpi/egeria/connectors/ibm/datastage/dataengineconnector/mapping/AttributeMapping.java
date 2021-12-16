/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.mapping;

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

    /**
     * Default constructor to pass in the cache for re-use.
     *
     * @param cache used by this mapping
     */
    AttributeMapping(DataStageCache cache) {
        super(cache);
    }

    /**
     * Creates a set of Attributes for the provided job and link information.
     *
     * @param link the link containing stage column detail for the Attributes
     * @param job the job for which to create the attributes
     * @param fullyQualifiedStageName to ensure each attribute is unique
     * @return {@code List<Attribute>}
     */
    List<Attribute> getForLink(Link link, DataStageJob job, String fullyQualifiedStageName) throws IGCException {
        final String methodName = "getForLink";
        log.debug("Creating new AttributeMapping from job and link...");
        List<Attribute> attributes = new ArrayList<>();
        if (link != null) {
            ItemList<DataItem> stageColumns = link.getStageColumns();

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
        }
        return attributes;
    }

    /**
     * Creates a set of Attributes for the provided data store field information.
     *
     * @param fields the data store fields containing detail for the Attributes
     * @param fullyQualifiedStageName the qualified name of the stage to ensure each attribute is unique
     * @return {@code List<Attribute>}
     */
    List<Attribute> getForDataStoreFields(List<Classificationenabledgroup> fields, String fullyQualifiedStageName) throws IGCException {
        final String methodName = "getForDataStoreFields";
        log.debug("Creating new AttributeMapping from job and fields...");
        List<Attribute> attributes = new ArrayList<>();
        if (fields != null && !fields.isEmpty()) {
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
        }
        return attributes;
    }

    /**
     * Creates a set of Attributes for the provided data store field information (for virtual assets).
     *
     * @param fields the data store fields containing detail for the Attributes
     * @return {@code List<Attribute>}
     */
    List<Attribute> getForDataStoreFields(List<Classificationenabledgroup> fields) throws IGCException {
        return getForDataStoreFields(fields, null);
    }

    /**
     * Creates a set of Attributes for the provided stage variable information.
     *
     * @param stageVariables for which to create a set of attributes
     * @param job the job for which to create the Attributes
     * @param fullyQualifiedStageName (always empty)
     * @return {@code List<Attribute>}
     */
    List<Attribute> getForStageVariables(List<StageVariable> stageVariables, DataStageJob job,
                                         String fullyQualifiedStageName) throws IGCException {
        final String methodName = "getForStageVariables";
        log.debug("Creating new AttributeMapping from stage variables...");
        List<Attribute> attributes = new ArrayList<>();
        if (stageVariables != null && !stageVariables.isEmpty()) {
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
        }
        return attributes;
    }

}
