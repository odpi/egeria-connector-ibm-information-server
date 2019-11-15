/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.mapping;

import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.model.DSJob;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
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
    AttributeMapping(DSJob job, Reference link, String stageNameSuffix) {
        super(job.getIgcRestClient());
        if (log.isDebugEnabled()) { log.debug("Creating new AttributeMapping from job and link..."); }
        attributes = new ArrayList<>();
        if (link != null) {
            ItemList<Reference> stageColumns = (ItemList<Reference>) igcRestClient.getPropertyByName(link, "stage_columns");
            int index = 0;
            for (Reference stageColumn : stageColumns.getItems()) {
                String colId = stageColumn.getId();
                Reference stageColumnObj = job.getStageColumnByRid(colId);
                Attribute attribute = new Attribute();
                attribute.setQualifiedName(stageColumnObj.getIdentity(igcRestClient).toString() + stageNameSuffix);
                attribute.setDisplayName(stageColumnObj.getName());
                attribute.setDataType((String)igcRestClient.getPropertyByName(stageColumnObj, "odbc_type"));
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
    AttributeMapping(DSJob job, List<Reference> fields, String fullyQualifiedStageName) {
        super(job.getIgcRestClient());
        if (log.isDebugEnabled()) { log.debug("Creating new AttributeMapping from job and fields..."); }
        attributes = new ArrayList<>();
        if (fields != null && !fields.isEmpty()) {
            for (Reference field : fields) {
                Attribute attribute = new Attribute();
                attribute.setQualifiedName(getFullyQualifiedName(field) + fullyQualifiedStageName);
                attribute.setDisplayName(field.getName());
                String dataType = (String) igcRestClient.getPropertyByName(field, "data_type");
                if (dataType != null) {
                    attribute.setDataType(dataType);
                } else {
                    attribute.setDataType((String)igcRestClient.getPropertyByName(field, "odbc_type"));
                }
                Double position = (Double)igcRestClient.getPropertyByName(field, "position");
                if (position != null) {
                    attribute.setPosition(position.intValue());
                }
                attribute.setDefaultValue((String)igcRestClient.getPropertyByName(field, "default_value"));
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
    AttributeMapping(DSJob job, List<Reference> fields) {
        this(job, fields, "");
    }

    /**
     * Retrieve the Attributes that were setup.
     *
     * @return {@code List<Attribute>}
     */
    List<Attribute> getAttributes() { return attributes; }

}
