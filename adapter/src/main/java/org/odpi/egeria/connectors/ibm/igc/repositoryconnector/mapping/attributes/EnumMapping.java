/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.attributes;

import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.EnumPropertyValue;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.typedefs.EnumElementDef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

/**
 * The base class for all Enum mappings between OMRS AttributeTypeDefs and IGC properties.
 */
public class EnumMapping extends AttributeMapping {

    private static final Logger log = LoggerFactory.getLogger(EnumMapping.class);

    private HashMap<String, EnumElementDef> enumDefByIgcValue;
    private EnumElementDef defaultEnum;

    public EnumMapping(String omrsAttributeTypeDefName) {
        super(IGCPropertyType.STRING, omrsAttributeTypeDefName);
        enumDefByIgcValue = new HashMap<>();
    }

    public EnumMapping(String omrsAttributeTypeDefName,
                       String igcAssetType,
                       String igcPropertyName) {
        super(igcAssetType, igcPropertyName, IGCPropertyType.STRING, omrsAttributeTypeDefName);
        enumDefByIgcValue = new HashMap<>();
    }

    public void addDefaultEnumMapping(int omrsOrdinal, String omrsSymbolicName) {
        defaultEnum = new EnumElementDef();
        defaultEnum.setOrdinal(omrsOrdinal);
        defaultEnum.setValue(omrsSymbolicName);
    }

    public void addEnumMapping(String igcValue, int omrsOrdinal, String omrsSymbolicName) {
        EnumElementDef enumElementDef = new EnumElementDef();
        enumElementDef.setOrdinal(omrsOrdinal);
        enumElementDef.setValue(omrsSymbolicName);
        enumDefByIgcValue.put(igcValue, enumElementDef);
    }

    public EnumPropertyValue getEnumMappingByIgcValue(String igcValue) {
        EnumPropertyValue value = new EnumPropertyValue();
        if (enumDefByIgcValue.containsKey(igcValue)) {
            EnumElementDef element = enumDefByIgcValue.get(igcValue);
            value.setOrdinal(element.getOrdinal());
            value.setSymbolicName(element.getValue());
        } else {
            value = getDefaultEnumValue();
        }
        return value;
    }

    public EnumPropertyValue getDefaultEnumValue() {
        EnumPropertyValue value = new EnumPropertyValue();
        if (defaultEnum != null) {
            value.setOrdinal(defaultEnum.getOrdinal());
            value.setSymbolicName(defaultEnum.getValue());
        } else {
            if (log.isErrorEnabled()) { log.error("Could not find default enum value for {}.", getOmrsAttributeTypeDefName()); }
        }
        return value;
    }

    public String getIgcValueForOrdinal(int omrsOrdinal) {

        String igcValue = null;
        for (String candidateValue : enumDefByIgcValue.keySet()) {
            EnumElementDef element = enumDefByIgcValue.get(candidateValue);
            if (element.getOrdinal() == omrsOrdinal) {
                igcValue = candidateValue;
                break;
            }
        }
        return igcValue;

    }

}
