/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.clientlibrary.search;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.List;

/**
 * Manages a single condition to use as part of an {@link IGCSearch}.
 */
public class IGCSearchCondition {

    private JsonNodeFactory nf = JsonNodeFactory.instance;

    private String property;
    private String operator;
    private String value = null;
    private List<String> values = null;
    private Long min = null;
    private Long max = null;

    private Boolean negated = null;

    /**
     * Creates a new search condition directly. For example, the following would search for any assets
     * where the "name" is exactly "Account Number":
     * <ul>
     *     <li>property: "name"</li>
     *     <li>operator: "="</li>
     *     <li>value: "Account Number"</li>
     * </ul>
     *
     * @param property the property of an asset type to search against
     * @param operator the comparison operator to use
     * @param value the value to compare the property against
     */
    public IGCSearchCondition(String property, String operator, String value) {
        this(property, "isNull", false);
        if (value != null) {
            this.operator = operator;
            this.value = value;
            this.negated = null;
        }
    }

    /**
     * Creates a new search condition directly. For example, the following would search
     * for any assets where the "assigned_to_terms" property is not null:
     * <ul>
     *     <li>property: "assigned_to_terms"</li>
     *     <li>operator: "isNull"</li>
     *     <li>negated: true</li>
     * </ul>"property"
     *
     * @param property the property of an asset type to search against
     * @param operator the comparison operator to use
     * @param negated whether to invert (negate) the comparison operator or not
     */
    public IGCSearchCondition(String property, String operator, Boolean negated) {
        this.property = property;
        this.operator = operator;
        this.negated = negated;
    }

    /**
     * Creates a new search condition directly. For example, the following would search for any assets
     * where the "name" is not exactly "Account Number":
     * <ul>
     *     <li>property: "name"</li>
     *     <li>operator: "="</li>
     *     <li>value: "Account Number"</li>
     *     <li>negated: true</li>
     * </ul>
     *
     * @param property the property of an asset type to search against
     * @param operator the comparison operator to use
     * @param value the value to compare the property against
     * @param negated whether to invert (negate) the comparison operator or not
     */
    public IGCSearchCondition(String property, String operator, String value, Boolean negated) {
        this(property, "isNull", negated);
        if (value != null) {
            if (operator.equals("=") && negated) {
                // This is a more reliable 'does not equal' for both strings and numbers, the
                // negated condition does not work for numbers
                this.operator = "<>";
                this.negated = null;
            } else {
                this.operator = operator;
            }
            this.value = value;
        }
    }

    /**
     * Creates a new search condition directly. For example, the following would search for any assets
     * where the "name" is one of the values in the provided list "A", "B", or "C":
     *
     * <ul>
     *     <li>property: "name"</li>
     *     <li>listOfValues: [ "A", "B", "C" ]</li>
     * </ul>
     *
     * @param property the property of an asset type to search against
     * @param listOfValues the list of values to compare the property against
     */
    public IGCSearchCondition(String property, List<String> listOfValues) {
        this.property = property;
        this.operator = "in";
        this.values = listOfValues;
    }

    /**
     * Creates a new search condition directly. For example, the following would search for any assets
     * where the "name" is NOT one of the values in the provided list "A", "B", or "C":
     * <ul>
     *     <li>property: "name"</li>
     *     <li>listOfValues: [ "A", "B", "C" ]</li>
     *     <li>negated: true</li>
     * </ul>
     *
     * @param property the property of an asset type to search against
     * @param listOfValues the list of values to compare the property against
     * @param negated whether to invert (negate) the comparison operator or not
     */
    public IGCSearchCondition(String property, List<String> listOfValues, Boolean negated) {
        this(property, listOfValues);
        this.negated = negated;
    }

    /**
     * Creates a new search condition for a date field with a range of date / time between the two values provided.
     *
     * @param property the (date / time) property of an asset type to search against
     * @param from the starting time (epoch) for the date / time
     * @param to the ending time (epoch) for the date / time
     */
    public IGCSearchCondition(String property, long from, long to) {
        this.property = property;
        this.operator = "between";
        this.min = from;
        this.max = to;
    }

    public String getProperty() { return this.property; }
    public void setProperty(String property) { this.property = property; }

    public String getOperator() { return this.operator; }
    public void setOperator(String operator) { this.operator = operator; }

    public String getValue() { return this.value; }
    public void setValue(String value) { this.value = value; }

    public List<String> getValues() { return this.values; }
    public void setValues(List<String> values) { this.values = values; }

    public Boolean getNegated() { return this.negated; }
    public void setNegated(Boolean negated) { this.negated = negated; }

    /**
     * Returns the JSON object representing the condition.
     *
     * @return ObjectNode
     */
    public ObjectNode getConditionObject() {
        ObjectNode condObj = nf.objectNode();
        condObj.set("property", nf.textNode(getProperty()));
        condObj.set("operator", nf.textNode(getOperator()));
        if (this.values != null && !this.values.isEmpty()) {
            ArrayNode arrayNode = nf.arrayNode(getValues().size());
            for (String oneValue : getValues()) {
                arrayNode.add(oneValue);
            }
            condObj.set("value", arrayNode);
        } else if (this.value != null) {
            condObj.set("value", nf.textNode(getValue()));
        } else if (this.min != null && this.max != null) {
            condObj.set("min", nf.numberNode(min));
            condObj.set("max", nf.numberNode(max));
        }
        if (this.negated != null) {
            condObj.set("negated", nf.booleanNode(getNegated()));
        }
        return condObj;
    }

}
