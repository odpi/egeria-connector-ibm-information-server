/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.mapping;

import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.model.DataStageCache;
import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.model.DataStageJob;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestConstants;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.Classificationenabledgroup;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.DataItem;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.Link;
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
        log.debug("Creating new AttributeMapping from job and link...");
        attributes = new ArrayList<>();
        if (link != null) {
            ItemList<DataItem> stageColumns = link.getStageColumns();
            List<DataItem> allStageColumns = igcRestClient.getAllPages("stage_columns", stageColumns);
            int index = 0;
            for (DataItem stageColumn : allStageColumns) {
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
        log.debug("Creating new AttributeMapping from job and fields...");
        attributes = new ArrayList<>();
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
     * Retrieve the Attributes that were setup.
     *
     * @return {@code List<Attribute>}
     */
    List<Attribute> getAttributes() { return attributes; }

}
