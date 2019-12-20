/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.mapping;

import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.model.DataStageJob;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.Classificationenabledgroup;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.DataItem;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.Link;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.StageColumn;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;
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
     * @param job the job for which to create the Attributes
     * @param link the link containing stage column detail for the Attributes
     * @param stageNameSuffix the unique suffix (based on the stage name) to ensure each attribute is unique
     */
    AttributeMapping(DataStageJob job, Link link, String stageNameSuffix) {
        super(job.getIgcRestClient());
        if (log.isDebugEnabled()) { log.debug("Creating new AttributeMapping from job and link..."); }
        attributes = new ArrayList<>();
        if (link != null) {
            ItemList<DataItem> stageColumns = link.getStageColumns();
            stageColumns.getAllPages(igcRestClient);
            int index = 0;
            for (DataItem stageColumn : stageColumns.getItems()) {
                String colId = stageColumn.getId();
                StageColumn stageColumnObj = job.getStageColumnByRid(colId);
                Attribute attribute = new Attribute();
                attribute.setQualifiedName(stageColumnObj.getIdentity(igcRestClient).toString() + stageNameSuffix);
                attribute.setDisplayName(stageColumnObj.getName());
                attribute.setDataType(stageColumnObj.getOdbcType());
                attribute.setPosition(index);
                attributes.add(attribute);
                index++;
            }
        }
    }

    /**
     * Creates a set of Attributes for the provided job and data store field information.
     *
     * @param job the job for which to create the Attributes
     * @param fields the data store fields containing detail for the Attributes
     * @param fullyQualifiedStageName the qualified name of the stage to ensure each attribute is unique
     */
    AttributeMapping(DataStageJob job, List<Classificationenabledgroup> fields, String fullyQualifiedStageName) {
        super(job.getIgcRestClient());
        if (log.isDebugEnabled()) { log.debug("Creating new AttributeMapping from job and fields..."); }
        attributes = new ArrayList<>();
        if (fields != null && !fields.isEmpty()) {
            for (Classificationenabledgroup field : fields) {
                Attribute attribute = new Attribute();
                attribute.setQualifiedName(getFullyQualifiedName(field) + fullyQualifiedStageName);
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
            }
        }
    }

    /**
     * Creates a set of Attributes for the provided job and data store field information (for virtual assets).
     *
     * @param job the job for which to create the Attributes
     * @param fields the data store fields containing detail for the Attributes
     */
    AttributeMapping(DataStageJob job, List<Classificationenabledgroup> fields) {
        this(job, fields, "");
    }

    /**
     * Retrieve the Attributes that were setup.
     *
     * @return {@code List<Attribute>}
     */
    List<Attribute> getAttributes() { return attributes; }

}
