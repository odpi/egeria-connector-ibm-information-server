/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.clientlibrary.update;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages the criteria to use for updating assets in IGC.
 */
public class IGCCreate {

    private JsonNodeFactory nf = JsonNodeFactory.instance;

    private String type;
    private Map<String, String> properties;

    public IGCCreate(String type) {
        this.type = type;
        this.properties = new HashMap<>();
    }

    /**
     * Adds a property to the object that should be created.
     *
     * @param propertyName the name of the property to set
     * @param value the value to which to set the property
     */
    public void addProperty(String propertyName, String value) {
        properties.put(propertyName, value);
    }

    /**
     * Retrieves the create string for this create object.
     *
     * @return JsonNode - the JSON structure representing the creation operation
     */
    public JsonNode getCreate() {
        ObjectNode create = nf.objectNode();
        create.put("_type", type);
        for (String propertyName : properties.keySet()) {
            create.set(propertyName, nf.textNode(properties.get(propertyName)));
        }
        return create;
    }

    /**
     * Retrieves a printable version of the update object.
     *
     * @return String
     */
    @Override
    public String toString() {
        return getCreate().toString();
    }

}
