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
    protected ArrayList<Reference> _context = new ArrayList<>();

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

    /** @see #_context */ @JsonProperty("_context") public ArrayList<Reference> getContext() { return this._context; }
    /** @see #_context */ @JsonProperty("_context") public void setContext(ArrayList<Reference> _context) { this._context = _context; }

    /** @see #_name */ @JsonProperty("_name") public String getName() { return this._name; }
    /** @see #_name */ @JsonProperty("_name") public void setName(String _name) { this._name = _name; }

    /** @see #_type */ @JsonProperty("_type") public String getType() { return this._type; }
    /** @see #_type */ @JsonProperty("_type") public void setType(String _type) { this._type = _type; }

    /** @see #_id */ @JsonProperty("_id") public String getId() { return this._id; }
    /** @see #_id */ @JsonProperty("_id") public void setId(String _id) { this._id = _id; }

    /** @see #_url */ @JsonProperty("_url") public String getUrl() { return this._url; }
    /** @see #_url */ @JsonProperty("_url") public void setUrl(String _url) { this._url = _url; }

    @JsonIgnore public boolean isFullyRetrieved() { return this.fullyRetrieved; }
    @JsonIgnore public void setFullyRetrieved() { this.fullyRetrieved = true; }

    @JsonIgnore private static final List<String> NON_RELATIONAL_PROPERTIES = Arrays.asList(
            "name"
    );
    @JsonIgnore public static List<String> getNonRelationshipProperties() { return NON_RELATIONAL_PROPERTIES; }

    @JsonIgnore public static Boolean includesModificationDetails() { return false; }

    /**
     * This will generally be the most performant method by which to retrieve asset information, when only
     * some subset of properties is required
     *
     * @param igcrest the IGCRestClient connection to use to retrieve the details
     * @param properties a list of the properties to retrieve
     * @param pageSize the maximum number of each of the asset's relationships to return on this request
     * @param sorting the sorting criteria to use for the results
     * @return Reference - the object including only the subset of properties specified
     */
    public Reference getAssetWithSubsetOfProperties(IGCRestClient igcrest,
                                                    String[] properties,
                                                    int pageSize,
                                                    IGCSearchSorting sorting) {
        Reference assetWithProperties = null;
        IGCSearchCondition idOnly = new IGCSearchCondition("_id", "=", this._id);
        IGCSearchConditionSet idOnlySet = new IGCSearchConditionSet(idOnly);
        IGCSearch igcSearch = new IGCSearch(Reference.getAssetTypeForSearch(this.getType()), properties, idOnlySet);
        if (pageSize > 0) {
            igcSearch.setPageSize(pageSize);
        }
        if (sorting != null) {
            igcSearch.addSortingCriteria(sorting);
        }
        ReferenceList assetsWithProperties = igcrest.search(igcSearch);
        if (!assetsWithProperties.getItems().isEmpty()) {
            assetWithProperties = assetsWithProperties.getItems().get(0);
        }
        return assetWithProperties;
    }

    /**
     * This will generally be the most performant method by which to retrieve asset information, when only
     * some subset of properties is required
     *
     * @param igcrest the IGCRestClient connection to use to retrieve the details
     * @param properties a list of the properties to retrieve
     * @param pageSize the maximum number of each of the asset's relationships to return on this request
     * @return Reference - the object including only the subset of properties specified
     */
    public Reference getAssetWithSubsetOfProperties(IGCRestClient igcrest, String[] properties, int pageSize) {
        return getAssetWithSubsetOfProperties(igcrest, properties, pageSize, null);
    }

    /**
     * This will generally be the most performant method by which to retrieve asset information, when only
     * some subset of properties is required
     *
     * @param igcrest the IGCRestClient connection to use to retrieve the details
     * @param properties a list of the properties to retrieve
     * @return Reference - the object including only the subset of properties specified
     */
    public Reference getAssetWithSubsetOfProperties(IGCRestClient igcrest, String[] properties) {
        return getAssetWithSubsetOfProperties(igcrest, properties, igcrest.getDefaultPageSize());
    }

    /**
     * Retrieve the asset details from a minimal reference stub.
     * <br><br>
     * Be sure to first use the IGCRestClient "registerPOJO" method to register your POJO(s) for interpretting the
     * type(s) for which you're interested in retrieving details.
     * <br><br>
     * Note that this will only include the first page of any relationships -- to also retrieve all
     * relationships see getFullAssetDetails.
     *
     * @param igcrest the IGCRestClient connection to use to retrieve the details
     * @return Reference - the object including all of its details
     */
    public Reference getAssetDetails(IGCRestClient igcrest) {
        return igcrest.getAssetById(this._id);
    }

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

        boolean bHasModificationDetails = igcrest.hasModificationDetails(this.getType());
        String createdBy = (String) igcrest.getPropertyByName(this, IGCRestConstants.MOD_CREATED_BY);

        if (bHasModificationDetails && createdBy == null) {

            IGCSearchCondition idOnly = new IGCSearchCondition("_id", "=", this.getId());
            IGCSearchConditionSet idOnlySet = new IGCSearchConditionSet(idOnly);
            IGCSearch igcSearch = new IGCSearch(this.getType(), idOnlySet);
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
    public boolean populateContext(IGCRestClient igcrest) {

        boolean success = true;
        // Only bother retrieving the context if it isn't already present

        if (this._context.isEmpty()) {

            boolean bHasModificationDetails = igcrest.hasModificationDetails(this.getType());

            IGCSearchCondition idOnly = new IGCSearchCondition("_id", "=", this.getId());
            IGCSearchConditionSet idOnlySet = new IGCSearchConditionSet(idOnly);
            IGCSearch igcSearch = new IGCSearch(this.getType(), idOnlySet);
            if (bHasModificationDetails) {
                igcSearch.addProperties(IGCRestConstants.getModificationProperties());
            }
            igcSearch.setPageSize(2);
            ReferenceList assetsWithCtx = igcrest.search(igcSearch);
            success = (!assetsWithCtx.getItems().isEmpty());
            if (success) {

                Reference assetWithCtx = assetsWithCtx.getItems().get(0);
                this._context = new ArrayList(assetWithCtx.getContext());

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
        if (this.identity == null) {
            this.populateContext(igcrest);
            this.identity = new Identity(this._context, this.getType(), this.getName(), this.getId());
        }
        return this.identity;
    }

    /**
     * Translates the type of asset into what should be used for searching.
     * This is necessary for certain types that are actually pseudo-aliases for other types, to ensure all
     * properties of that asset can be retrieved.
     *
     * @param assetType the asset type for which to retrieve the search type
     * @return String
     */
    public static String getAssetTypeForSearch(String assetType) {
        String typeForSearch = null;
        switch(assetType) {
            case "host_(engine)":
                typeForSearch = "host";
                break;
            case "non_steward_user":
            case "steward_user":
                typeForSearch = "user";
                break;
            default:
                typeForSearch = assetType;
                break;
        }
        return typeForSearch;
    }

    // TODO: eventually handle the '_expand' that exists for data classifications

}
