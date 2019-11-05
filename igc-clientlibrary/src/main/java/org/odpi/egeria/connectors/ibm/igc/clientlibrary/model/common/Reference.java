/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common;

import com.fasterxml.jackson.annotation.*;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.MainObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.PUBLIC_ONLY;

/**
 * The ultimate parent object for all IGC assets, it contains only the most basic information common to every single
 * asset in IGC, and present in every single reference to an IGC asset (whether via relationship, search result,
 * etc):<br>
 * <ul>
 *     <li>_name</li>
 *     <li>_type</li>
 *     <li>_id</li>
 *     <li>_url</li>
 *     <li><i>_context</i> -- present for <i>almost</i> all assets</li>
 * </ul><br>
 *  POJOs to represent user-defined objects (OpenIGC) should not extend this class directly, but the MainObject class.
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="_type", visible=true, defaultImpl=Reference.class)
@JsonSubTypes({
        @JsonSubTypes.Type(value = MainObject.class, name = "main_object"),
})
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class Reference extends ObjectPrinter {

    @JsonIgnore
    private static final Logger log = LoggerFactory.getLogger(Reference.class);

    /**
     * Used to uniquely identify the object without relying on its ID (RID) remaining static.
     */
    @JsonIgnore
    private Identity identity = null;

    /**
     * Used to indicate whether this asset has been fully retrieved already (true) or not (false).
     */
    @JsonIgnore
    private boolean fullyRetrieved = false;

    /**
     * Provides the context to the unique identity of this asset. Note that while this will exist on
     * almost all IGC assets, it is not present on absolutely all of them -- also be aware that without
     * v11.7.0.2+ and an optional parameter it uses, this will always be 'null' in a ReferenceList
     */
    protected List<Reference> _context;

    /**
     * The '_name' property of a Reference is equivalent to its 'name' property, but will always be
     * populated on a reference ('name' may not yet be populated, depending on whether you have only a reference
     * to the asset, or the full asset itself).
     */
    protected String _name;

    /**
     * The '_type' property defines the type of asset this Reference represents. To allow a Reference to
     * be automatically translated into a Java object, you must first register the Java class that should
     * interpret this data type using {@link IGCRestClient#registerPOJO(Class)}.
     */
    protected String _type;

    /**
     * The '_id' property defines the unique Repository ID (RID) of the asset. This ID should be unique within
     * an instance (environment) of IGC.
     */
    protected String _id;

    /**
     * The '_url' property provides a navigable link directly to the full details of asset this Reference represents,
     * within a given IGC environment.
     */
    protected String _url;

    /**
     * Default constructor
     */
    public Reference() {}

    /**
     * Construct a simple stub representing an object instance in IGC.
     *
     * @param name name of the object instance
     * @param type type of the object instance
     */
    public Reference(String name, String type) {
        this(name, type, null);
    }

    /**
     * Construct a simple stub representing an object instance in IGC.
     *
     * @param name name of the object instance
     * @param type type of the object instance
     * @param id repository ID (RID) of the object instance
     */
    public Reference(String name, String type, String id) {
        this._name = name;
        this._type = type;
        this._id = id;
    }

    /**
     * Retrieve the context of the IGC object instance.
     *
     * @return {@code List<Reference>}
     * @see #_context
     */
    @JsonProperty("_context")
    public List<Reference> getContext() { return _context; }

    /**
     * Set the context of the IGC object instance.
     *
     * @param _context of the IGC object instance
     * @see #_context
     */
    @JsonProperty("_context")
    public void setContext(List<Reference> _context) { this._context = _context; }

    /**
     * Retrieve the name of the IGC object instance.
     *
     * @return String
     * @see #_name
     */
    @JsonProperty("_name")
    public String getName() { return _name; }

    /**
     * Set the name of the IGC object instance.
     *
     * @param _name of the IGC object instance
     * @see #_name
     */
    @JsonProperty("_name")
    public void setName(String _name) { this._name = _name; }

    /**
     * Retrieve the type of the IGC object instance.
     *
     * @return String
     * @see #_type
     */
    @JsonProperty("_type")
    public String getType() { return _type; }

    /**
     * Set the type of the IGC object instance.
     *
     * @param _type of the IGC object instance
     * @see #_type
     */
    @JsonProperty("_type")
    public void setType(String _type) { this._type = _type; }

    /**
     * Retrieve the Repository ID (RID) of the object instance.
     *
     * @return String
     * @see #_id
     */
    @JsonProperty("_id")
    public String getId() { return _id; }

    /**
     * Set the Repository ID (RID) of the object instance.
     *
     * @param _id of the IGC object instance
     * @see #_id
     */
    @JsonProperty("_id")
    public void setId(String _id) { this._id = _id; }

    /**
     * Retrieve the IGC REST API URL to the object instance's details.
     *
     * @return String
     * @see #_url
     */
    @JsonProperty("_url")
    public String getUrl() { return _url; }

    /**
     * Set the IGC REST API URL of the object instance's details.
     *
     * @param _url of the IGC object instance
     * @see #_url
     */
    @JsonProperty("_url")
    public void setUrl(String _url) { this._url = _url; }

    /**
     * Determine whether this object instance is fully retrieved (true) or only partially (false).
     *
     * @return boolean
     */
    @JsonIgnore
    public boolean isFullyRetrieved() { return fullyRetrieved; }

    /**
     * Mark this object instance as fully-retrieved from IGC.
     */
    @JsonIgnore
    public void setFullyRetrieved() { fullyRetrieved = true; }

    /**
     * Returns true iff the provided object is a relationship (ie. of class Reference or one of its offspring).
     *
     * @param obj the object to check
     * @return Boolean
     */
    public static boolean isReference(Object obj) {
        Class clazz = obj.getClass();
        while (clazz != null && clazz != Reference.class) {
            clazz = clazz.getSuperclass();
        }
        return (clazz == Reference.class);
    }

    /**
     * Returns true iff the provided object is a list of relationships (ie. of class ReferenceList).
     *
     * @param obj the object to check
     * @return Boolean
     */
    public static boolean isReferenceList(Object obj) {
        return (obj.getClass() == ReferenceList.class);
    }

    /**
     * Returns true iff the provided object is a simple type (String, Number, Boolean, Date, etc).
     *
     * @param obj the object to check
     * @return Boolean
     */
    public static boolean isSimpleType(Object obj) {
        return (!Reference.isReference(obj) && !Reference.isReferenceList(obj));
    }

    /**
     * Retrieves the semantic identity of the asset.
     *
     * @param igcrest a REST API connection to use in confirming the identity of the asset
     * @return Identity
     */
    public Identity getIdentity(IGCRestClient igcrest) {
        if (identity == null) {
            igcrest.populateContext(this);
            identity = new Identity(_context, getType(), getName(), getId());
        }
        return identity;
    }

    // TODO: eventually handle the '_expand' that exists for data classifications

}
