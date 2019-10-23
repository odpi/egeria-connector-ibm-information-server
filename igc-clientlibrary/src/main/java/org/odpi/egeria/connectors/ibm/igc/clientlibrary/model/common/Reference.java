/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common;

import com.fasterxml.jackson.annotation.*;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearch;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchCondition;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchConditionSet;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestConstants;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchSorting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    @JsonSubTypes.Type(value = MainObject.class, name = "main_object")
})
@JsonIgnoreProperties(ignoreUnknown=true)
public class Reference extends ObjectPrinter {

    public Reference() {}

    public Reference(String name, String type) {
        this(name, type, null);
    }

    public Reference(String name, String type, String id) {
        this._name = name;
        this._type = type;
        this._id = id;
    }

    @JsonIgnore private static final Logger log = LoggerFactory.getLogger(Reference.class);

    /**
     * Used to uniquely identify the object without relying on its ID (RID) remaining static.
     */
    @JsonIgnore private Identity identity = null;

    /**
     * Used to indicate whether this asset has been fully retrieved already (true) or not (false).
     */
    @JsonIgnore private boolean fullyRetrieved = false;

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

    /** @see #_context */ @JsonProperty("_context") public List<Reference> getContext() { return _context; }
    /** @see #_context */ @JsonProperty("_context") public void setContext(List<Reference> _context) { this._context = _context; }

    /** @see #_name */ @JsonProperty("_name") public String getName() { return _name; }
    /** @see #_name */ @JsonProperty("_name") public void setName(String _name) { this._name = _name; }

    /** @see #_type */ @JsonProperty("_type") public String getType() { return _type; }
    /** @see #_type */ @JsonProperty("_type") public void setType(String _type) { this._type = _type; }

    /** @see #_id */ @JsonProperty("_id") public String getId() { return _id; }
    /** @see #_id */ @JsonProperty("_id") public void setId(String _id) { this._id = _id; }

    /** @see #_url */ @JsonProperty("_url") public String getUrl() { return _url; }
    /** @see #_url */ @JsonProperty("_url") public void setUrl(String _url) { this._url = _url; }

    @JsonIgnore public boolean isFullyRetrieved() { return fullyRetrieved; }
    @JsonIgnore public void setFullyRetrieved() { fullyRetrieved = true; }

    @JsonIgnore private static final List<String> NON_RELATIONAL_PROPERTIES = Arrays.asList(
            "name"
    );
    @JsonIgnore public static List<String> getNonRelationshipProperties() { return NON_RELATIONAL_PROPERTIES; }

    @JsonIgnore public static Boolean includesModificationDetails() { return false; }

    /**
     * Returns true iff the provided object is a relationship (ie. of class Reference or one of its offspring).
     *
     * @param obj the object to check
     * @return Boolean
     */
    public static final Boolean isReference(Object obj) {
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
    public static final Boolean isReferenceList(Object obj) {
        return (obj.getClass() == ReferenceList.class);
    }

    /**
     * Returns true iff the provided object is a simple type (String, Number, Boolean, Date, etc).
     *
     * @param obj the object to check
     * @return Boolean
     */
    public static final Boolean isSimpleType(Object obj) {
        return (!Reference.isReference(obj) && !Reference.isReferenceList(obj));
    }

    /**
     * Ensures that the modification details of the asset are populated (takes no action if already populated or
     * the asset does not support them).
     *
     * @param igcrest a REST API connection to use in populating the modification details
     * @return boolean indicating whether details were successfully / already populated (true) or not (false)
     */
    public boolean populateModificationDetails(IGCRestClient igcrest) {

        boolean success = true;
        // Only bother retrieving the details if the object supports them and they aren't already present

        boolean bHasModificationDetails = igcrest.hasModificationDetails(getType());
        String createdBy = (String) igcrest.getPropertyByName(this, IGCRestConstants.MOD_CREATED_BY);

        if (bHasModificationDetails && createdBy == null) {

            if (log.isDebugEnabled()) { log.debug("Populating modification details that were missing..."); }

            IGCSearchCondition idOnly = new IGCSearchCondition("_id", "=", getId());
            IGCSearchConditionSet idOnlySet = new IGCSearchConditionSet(idOnly);
            IGCSearch igcSearch = new IGCSearch(getType(), idOnlySet);
            igcSearch.addProperties(IGCRestConstants.getModificationProperties());
            igcSearch.setPageSize(2);
            ReferenceList assetsWithModDetails = igcrest.search(igcSearch);
            success = (!assetsWithModDetails.getItems().isEmpty());
            if (success) {

                Reference assetWithModDetails = assetsWithModDetails.getItems().get(0);
                igcrest.setPropertyByName(this, IGCRestConstants.MOD_CREATED_ON, igcrest.getPropertyByName(assetWithModDetails, IGCRestConstants.MOD_CREATED_ON));
                igcrest.setPropertyByName(this, IGCRestConstants.MOD_CREATED_BY, igcrest.getPropertyByName(assetWithModDetails, IGCRestConstants.MOD_CREATED_BY));
                igcrest.setPropertyByName(this, IGCRestConstants.MOD_MODIFIED_ON, igcrest.getPropertyByName(assetWithModDetails, IGCRestConstants.MOD_MODIFIED_ON));
                igcrest.setPropertyByName(this, IGCRestConstants.MOD_MODIFIED_BY, igcrest.getPropertyByName(assetWithModDetails, IGCRestConstants.MOD_MODIFIED_BY));

            }

        }

        return success;

    }

    /**
     * Ensures that the _context of the asset is populated (takes no action if already populated).
     * In addition, if the asset type supports them, will also retrieve and set modification details.
     *
     * @param igcrest a REST API connection to use in populating the context
     * @return boolean indicating whether _context was successfully / already populated (true) or not (false)
     */
    private boolean populateContext(IGCRestClient igcrest) {

        boolean success = true;
        // Only bother retrieving the context if it isn't already present

        if (_context == null || _context.isEmpty()) {

            if (log.isDebugEnabled()) { log.debug("Context is empty, populating..."); }

            boolean bHasModificationDetails = igcrest.hasModificationDetails(getType());

            IGCSearchCondition idOnly = new IGCSearchCondition("_id", "=", getId());
            IGCSearchConditionSet idOnlySet = new IGCSearchConditionSet(idOnly);
            IGCSearch igcSearch = new IGCSearch(getType(), idOnlySet);
            if (bHasModificationDetails) {
                igcSearch.addProperties(IGCRestConstants.getModificationProperties());
            }
            igcSearch.setPageSize(2);
            ReferenceList assetsWithCtx = igcrest.search(igcSearch);
            success = (!assetsWithCtx.getItems().isEmpty());
            if (success) {

                Reference assetWithCtx = assetsWithCtx.getItems().get(0);
                _context = assetWithCtx.getContext();

                if (bHasModificationDetails) {
                    igcrest.setPropertyByName(this, IGCRestConstants.MOD_CREATED_ON, igcrest.getPropertyByName(assetWithCtx, IGCRestConstants.MOD_CREATED_ON));
                    igcrest.setPropertyByName(this, IGCRestConstants.MOD_CREATED_BY, igcrest.getPropertyByName(assetWithCtx, IGCRestConstants.MOD_CREATED_BY));
                    igcrest.setPropertyByName(this, IGCRestConstants.MOD_MODIFIED_ON, igcrest.getPropertyByName(assetWithCtx, IGCRestConstants.MOD_MODIFIED_ON));
                    igcrest.setPropertyByName(this, IGCRestConstants.MOD_MODIFIED_BY, igcrest.getPropertyByName(assetWithCtx, IGCRestConstants.MOD_MODIFIED_BY));
                }

            }

        }

        return success;

    }

    /**
     * Retrieves the semantic identity of the asset.
     *
     * @param igcrest a REST API connection to use in confirming the identity of the asset
     * @return Identity
     */
    public Identity getIdentity(IGCRestClient igcrest) {
        if (identity == null) {
            populateContext(igcrest);
            identity = new Identity(_context, getType(), getName(), getId());
        }
        return identity;
    }

    // TODO: eventually handle the '_expand' that exists for data classifications

}
