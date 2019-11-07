/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.eventmapper.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.flipkart.zjsonpatch.DiffFlags;
import com.flipkart.zjsonpatch.JsonDiff;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.model.OMRSStub;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

/**
 * A class to capture differences between IGC objects through the JSON Patch notation.
 * (https://tools.ietf.org/html/rfc6902)
 */
public class ChangeSet {

    private static final Logger log = LoggerFactory.getLogger(ChangeSet.class);

    private ObjectMapper objectMapper;
    private IGCRestClient igcRestClient;

    private JsonNode patch;

    private HashMap<String, List<Change>> changesByProperty;

    /**
     * Create a new JSON Patch based on the provided asset details and stub.
     *
     * @param igcRestClient REST API connectivity to an IGC environment
     * @param asset the IGC asset (as a POJO) giving the most up-to-date definition of the asset
     * @param stub the OMRS stub giving the last-state of the asset (when an event was last triggered for it)
     */
    public ChangeSet(IGCRestClient igcRestClient, Reference asset, OMRSStub stub) {

        this.objectMapper = new ObjectMapper();
        this.changesByProperty = new HashMap<>();
        this.igcRestClient = igcRestClient;

        boolean bNoStub = false;
        // If we receive a null stub (eg. a new entity without any stub)
        if (stub == null) {
            // ... initialise an empty stub payload that we can (hopefully) use for the comparison
            stub = new OMRSStub();
            stub.setPayload("{}");
            bNoStub = true;
        }

        // Calculate the delta between the latest version and the previous saved stub
        try {
            JsonNode stubPayload = objectMapper.readTree(stub.getPayload());
            if (bNoStub) {
                if (log.isDebugEnabled()) { log.debug("No existing stub -- calculating differences."); }
                calculateDelta(asset, stubPayload);
            } else {
                Long stubModified = stubPayload.path("modified_on").asLong(0);
                if (stubModified == 0) {
                    if (log.isDebugEnabled()) { log.debug("No timestamp inside stub -- calculating differences."); }
                    calculateDelta(asset, stubPayload);
                } else {
                    // Short-circuit the delta calculation if the modification date inside the stub's payload is equal
                    // to the modification date of the asset itself.  This way we avoid updating the stub simply because
                    // in one case we had the inner context of a relationship and in another case we did not
                    Date assetModified = (Date) this.igcRestClient.getPropertyByName(asset, "modified_on");
                    if (assetModified == null) {
                        if (log.isDebugEnabled()) { log.debug("No timestamp inside asset -- calculating differences."); }
                        calculateDelta(asset, stubPayload);
                    } else if (!stubModified.equals(assetModified.getTime())) {
                        if (log.isDebugEnabled()) { log.debug("Modification timestamp of stub ({}) does not match asset ({}) -- calculating differences.", stubModified, assetModified.getTime()); }
                        calculateDelta(asset, stubPayload);
                    } else {
                        if (this.igcRestClient.getPagedRelationshipPropertiesForType(asset.getType()).contains("detected_classifications")) {
                            // One exception we must handle where the timestamps will match but there could still be
                            // changes is when there is the potential for a classification: data class detection does NOT
                            // update the modification timestamp of the entity that was classified
                            if (log.isDebugEnabled()) { log.debug("Modification timestamps matched, but may be classifications -- calculating differences."); }
                            calculateDelta(asset, stubPayload);
                        } else {
                            if (log.isDebugEnabled()) { log.debug("Modification timestamps between stub and asset matched -- skipping change calculation."); }
                        }
                    }
                }
            }

        } catch (IOException e) {
            if (log.isErrorEnabled()) { log.error("Unable to parse JSON for diff operation: {}, {}", asset, stub, e); }
        }

    }

    /**
     * Calculate the differences between the provided IGC entity and the payload of an OMRS stub representing a previous
     * version of the same entity
     *
     * @param asset the latest version of the IGC entity to compare
     * @param stubPayload the payload of a previous version of the IGC entity to compare
     * @throws IOException if there are any errors processing the information as JSON
     */
    private void calculateDelta(Reference asset, JsonNode stubPayload) throws IOException {

        EnumSet<DiffFlags> flags = DiffFlags.dontNormalizeOpIntoMoveAndCopy().clone();
        JsonNode currentAsset = objectMapper.readTree(this.igcRestClient.getValueAsJSON(asset));
        this.patch = JsonDiff.asJson(
                stubPayload,
                currentAsset,
                flags
        );
        if (log.isDebugEnabled()) {
            log.debug("Found the following changes: {}", this.patch.toString());
        }
        ArrayNode changes = (ArrayNode) this.patch;
        for (int i = 0; i < changes.size(); i++) {
            JsonNode change = changes.get(i);
            // Only add the change if it refers to a non-paging node (ie. it is /items/ or a basic property)
            // (Otherwise we end up with many changes for the same set of relationships)
            String changePath = change.path("path").asText();
            Change theChange = null;
            if (changePath.contains("/items/")) {
                // If we already have an object, use it directly
                if (change.path("value").getNodeType().equals(JsonNodeType.OBJECT)) {
                    theChange = new Change(change, stubPayload);
                    // Otherwise see if there is a set of individual changes we should consolidate (where _id will
                    // always be different if the relationship itself has changed)
                } else if (changePath.endsWith("_id")) {
                    JsonNode consolidatedChange = consolidateChangedObject(change, changePath, currentAsset);
                    theChange = new Change(consolidatedChange, stubPayload);
                }
            } else if (!changePath.contains("/paging/")) {
                // Skip any paging information changes
                if (changePath.endsWith("/_id") && !changePath.equals("/_id")) {
                    // This is likely an exclusive relationship (eg. 'parent_category')
                    if (log.isDebugEnabled()) {
                        log.debug("Found an exclusive relationship change: {}", change.toString());
                    }
                    JsonNode consolidatedChange = consolidateChangedObject(change, changePath, currentAsset);
                    if (log.isDebugEnabled()) {
                        log.debug(" ... consolidated to: {}", consolidatedChange.toString());
                    }
                    theChange = new Change(consolidatedChange, stubPayload);
                } else {
                    // Otherwise add simple changes
                    theChange = new Change(change, stubPayload);
                }
            }
            if (theChange != null) {
                String igcProperty = theChange.getIgcPropertyName();
                if (!this.changesByProperty.containsKey(igcProperty)) {
                    this.changesByProperty.put(igcProperty, new ArrayList<>());
                }
                this.changesByProperty.get(igcProperty).add(theChange);
            }
        }

    }

    /**
     * Retrieve the set of IGC property names that have some change.
     *
     * @return {@code Set<String>}
     */
    public Set<String> getChangedProperties() { return this.changesByProperty.keySet(); }

    /**
     * Retrieve the list of changes for the provided IGC property name.
     *
     * @param property name of the IGC property for which to retrieve changes
     * @return {@code List<Change>}
     */
    public List<Change> getChangesForProperty(String property) { return this.changesByProperty.get(property); }

    /**
     * Retrieve an object from the specified path (including index), from the provided asset JSON.
     *
     * @param objectPath the ../items/n path (including the index number)
     * @param asset the JSON representation of the asset from which to retrieve the path
     * @return JsonNode
     */
    private JsonNode getObjectFromIndex(String objectPath, JsonNode asset) {
        if (log.isDebugEnabled()) { log.debug(" ... retrieving object from index at path: {}", objectPath); }
        if (objectPath.contains("/items")) {
            // If we have an items array, determine the right index and retrieve the object
            String arrayIndex = objectPath.substring(objectPath.lastIndexOf('/') + 1);
            String listPath = objectPath.substring(1, objectPath.indexOf("/items"));
            int idx = Integer.parseInt(arrayIndex);
            ArrayNode references = (ArrayNode) asset.path(listPath).path("items");
            return references.get(idx);
        } else {
            // Otherwise just return the object directly (there is only one)
            // We need to remove the leading '/' before doing so...
            String relationshipPath = objectPath.substring(1);
            if (log.isDebugEnabled()) { log.debug(" ... returning object: {}", asset.path(relationshipPath).toString()); }
            return asset.path(relationshipPath);
        }
    }

    /**
     * Consolidate an individual '_id' into a full object change.
     *
     * @param change the change in which the '_id' piece is found
     * @param changePath the path of the change (ending with /_id)
     * @param currentAsset the JSON representation of the asset from which to retrieve the path
     * @return JsonNode
     */
    private JsonNode consolidateChangedObject(JsonNode change, String changePath, JsonNode currentAsset) {
        // Consolidate [/items/n]/_id entries into objects rather than separate changes
        ObjectNode consolidatedChange = (ObjectNode) change;
        String indexPath = changePath.substring(0, changePath.indexOf("/_id"));
        consolidatedChange.put("path", indexPath);
        // Add the complete Reference (_id, _name, _type, _url) as an object value
        // Consolidate this new object from the current asset, not the stub
        JsonNode relatedAsset = getObjectFromIndex(indexPath, currentAsset);
        consolidatedChange.set("value", relatedAsset);
        return consolidatedChange;
    }

    /**
     * A sub-class to capture individual differences.
     */
    public class Change {

        private JsonNode from;
        private String op;
        private String path;
        private JsonNode value;

        public Change(JsonNode patch, JsonNode from) {
            this.from = from;
            this.op = patch.get("op").asText();
            this.path = patch.get("path").asText();
            this.value = patch.get("value");
        }

        /**
         * Retrieve the 'op'eration indicated by the JSON Patch entry. Will be one of "replace", "add" or "remove".
         *
         * @return String
         */
        public String getOp() { return this.op; }

        /**
         * Retrieve the simple IGC property name indicated by the JSON Patch entry. The property name will be trimmed
         * from the full path of the JSON Patch entry.
         *
         * @return String
         * @see #getIgcPropertyPath()
         */
        public String getIgcPropertyName() {
            String[] aTokens = this.path.split("/");
            if (aTokens.length > 1) {
                return aTokens[1];
            } else {
                if (log.isErrorEnabled()) { log.error("Unable to find any property in path: {}", this.path); }
                return null;
            }
        }

        /**
         * Retrieve the full IGC property 'path' indicated by the JSON Patch entry. This will include the full path to
         * the property, including any object sub-entries, array index notation, etc. In general you probably want just
         * the IGC property name itself, accessible through the getIgcPropertyName method.
         *
         * @return String
         * @see #getIgcPropertyName()
         */
        public String getIgcPropertyPath() {
            return this.path;
        }

        /**
         * Retrieve the changed value indicated by the JSON Patch entry.
         *
         * @param referenceListProperties list of properties of the changed asset that are reference lists
         * @return Object
         */
        public Object getNewValue(List<String> referenceListProperties) {
            return getValueFromJSON(this.value, referenceListProperties, getIgcPropertyPath());
        }

        /**
         * Retrieve the original value that is being removed or replaced by the JSON Patch entry. (For 'add' operations
         * will return null.)
         *
         * @param referenceListProperties list of properties of the changed asset that are reference lists
         * @return Object
         */
        public Object getOldValue(List<String> referenceListProperties) {

            Object oldValue = null;

            // If the operation is an 'add', there will not be any old value so we should simply return null
            if (!getOp().equals("add")) {
                String path = getIgcPropertyPath();
                if (path.contains("/items/")) {
                    JsonNode obj = getObjectFromIndex(path, this.from);
                    oldValue = getValueFromJSON(obj, referenceListProperties, path);
                } else {
                    String candidatePath = path;
                    if (candidatePath.startsWith("/")) {
                        candidatePath = candidatePath.substring(1);
                    }
                    oldValue = getValueFromJSON(this.from.path(candidatePath), referenceListProperties, path);
                }
            }

            return oldValue;

        }

        /**
         * Retrieve an actual value from the provided JSON node.
         *
         * @param node the JSON node from which to retrieve a value
         * @param referenceListProperties at list of all IGC properties that are ReferenceLists
         * @param path the JSON path at which the node is found
         * @return Object
         */
        private Object getValueFromJSON(JsonNode node,
                                        List<String> referenceListProperties,
                                        String path) {

            Object value = null;
            JsonNodeType jsonType = node.getNodeType();
            switch (jsonType) {
                // TODO: how to translate an array?
                case NULL:
                    value = null;
                    break;
                case BOOLEAN:
                    value = node.asBoolean();
                    break;
                case NUMBER:
                    value = node.asDouble();
                    break;
                case OBJECT:
                    // If an object, must be a Reference (or ReferenceList) -- read it in as one
                    // Note that if the change path ends with /items/0 then we should only read a Reference, not
                    // a list, as the JSON Patch is only giving us a singular Reference (the paging we need for
                    // the list is split off on other change operations)
                    if (referenceListProperties.contains(getIgcPropertyName()) && !path.contains("/items/")) {
                        value = igcRestClient.readJSONIntoItemList(node.toString());
                    } else {
                        value = igcRestClient.readJSONIntoPOJO(node.toString());
                    }
                    break;
                case STRING:
                    value = node.asText();
                    break;
                default:
                    if (log.isWarnEnabled()) { log.warn("Unhandled value type '{}': {}", jsonType, node); }
                    break;
            }

            return value;

        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Change: {");
            sb.append(" op=");
            sb.append(this.op);
            sb.append(", path=");
            sb.append(this.path);
            sb.append(", value=");
            sb.append(this.value.toString());
            sb.append("}");
            return sb.toString();
        }

    }

}
