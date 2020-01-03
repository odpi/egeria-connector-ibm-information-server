/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector;

import org.odpi.egeria.connectors.ibm.igc.auditlog.IGCOMRSAuditCode;
import org.odpi.egeria.connectors.ibm.igc.auditlog.IGCOMRSErrorCode;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base.Category;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearch;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchCondition;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchConditionSet;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.update.IGCCreate;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.model.OMRSStub;
import org.odpi.openmetadata.frameworks.connectors.properties.ConnectionProperties;
import org.odpi.openmetadata.frameworks.connectors.properties.EndpointProperties;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.OMRSMetadataCollection;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.repositoryconnector.OMRSRepositoryConnector;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.OMRSRuntimeException;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.RepositoryErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class IGCOMRSRepositoryConnector extends OMRSRepositoryConnector {

    private static final Logger log = LoggerFactory.getLogger(IGCOMRSRepositoryConnector.class);

    private IGCRestClient igcRestClient;
    private IGCVersionEnum igcVersion;

    private List<String> defaultZones;
    private String defaultGlossaryName;
    private String defaultTermStatus;
    private String defaultGlossaryRID;

    private boolean successfulInit;

    /**
     * Default constructor used by the OCF Connector Provider.
     */
    public IGCOMRSRepositoryConnector() {
        defaultZones = new ArrayList<>();
    }

    /**
     * Call made by the ConnectorProvider to initialize the Connector with the base services.
     *
     * @param connectorInstanceId   unique id for the connector instance   useful for messages etc
     * @param connectionProperties   POJO for the configuration used to create the connector.
     */
    @Override
    public void initialize(String               connectorInstanceId,
                           ConnectionProperties connectionProperties) {

        super.initialize(connectorInstanceId, connectionProperties);

        final String methodName = "initialize";

        if (auditLog != null) {
            IGCOMRSAuditCode auditCode = IGCOMRSAuditCode.REPOSITORY_SERVICE_INITIALIZING;
            auditLog.logRecord(methodName,
                    auditCode.getLogMessageId(),
                    auditCode.getSeverity(),
                    auditCode.getFormattedLogMessage(),
                    null,
                    auditCode.getSystemAction(),
                    auditCode.getUserAction());
        }

        EndpointProperties endpointProperties = connectionProperties.getEndpoint();
        if (endpointProperties == null) {
            if (auditLog != null) {
                IGCOMRSAuditCode auditCode = IGCOMRSAuditCode.REST_CLIENT_FAILURE;
                auditLog.logRecord(methodName,
                        auditCode.getLogMessageId(),
                        auditCode.getSeverity(),
                        auditCode.getFormattedLogMessage(),
                        null,
                        auditCode.getSystemAction(),
                        auditCode.getUserAction());
            }
            IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.REST_CLIENT_FAILURE;
            String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage("null");
            throw new OMRSRuntimeException(
                    errorCode.getHTTPErrorCode(),
                    this.getClass().getName(),
                    methodName,
                    errorMessage,
                    errorCode.getSystemAction(),
                    errorCode.getUserAction()
            );
        }
        String address = endpointProperties.getProtocol() + "://" + endpointProperties.getAddress();

        String igcUser = connectionProperties.getUserId();
        String igcPass = connectionProperties.getClearPassword();

        // Setup meaningful defaults if nothing was provided (these will be overwritten below if they are provided)
        this.defaultGlossaryName = "Default Glossary";
        this.defaultTermStatus = "CANDIDATE";

        // Retrieve connection details
        Map<String, Object> proxyProperties = this.connectionBean.getConfigurationProperties();
        if (proxyProperties != null) {
            Object zones = proxyProperties.get(IGCOMRSRepositoryConnectorProvider.DEFAULT_ZONES);
            if (zones instanceof List) {
                for (Object zone : (List<?>) zones) {
                    if (zone instanceof String) {
                        this.defaultZones.add((String)zone);
                    }
                }
            }
            Object defaultGlossaryName = proxyProperties.get(IGCOMRSRepositoryConnectorProvider.DEFAULT_GLOSSARY_NAME);
            if (defaultGlossaryName != null && !defaultGlossaryName.equals("")) {
                this.defaultGlossaryName = (String) defaultGlossaryName;
            }
            Object defaultTermStatus = proxyProperties.get(IGCOMRSRepositoryConnectorProvider.DEFAULT_TERM_STATUS);
            if (defaultTermStatus != null && !defaultTermStatus.equals("")) {
                this.defaultTermStatus = (String) defaultTermStatus;
            }
        }

        // Create new REST API client (opens a new session)
        this.igcRestClient = new IGCRestClient(address, igcUser, igcPass);
        if (this.igcRestClient.start()) {
            if (getMaxPageSize() > 0) {
                this.igcRestClient.setDefaultPageSize(getMaxPageSize());
            }

            // Set the version based on the IGC client's auto-determination of the IGC environment's version
            this.igcVersion = this.igcRestClient.getIgcVersion();

            try {
                boolean success = upsertOMRSBundleZip();
                this.igcRestClient.registerPOJO(OMRSStub.class);
                successfulInit = success;
            } catch (RepositoryErrorException e) {
                if (auditLog != null) {
                    IGCOMRSAuditCode auditCode = IGCOMRSAuditCode.OMRS_BUNDLE_FAILURE;
                    auditLog.logRecord(methodName,
                            auditCode.getLogMessageId(),
                            auditCode.getSeverity(),
                            auditCode.getFormattedLogMessage(),
                            null,
                            auditCode.getSystemAction(),
                            auditCode.getUserAction());
                }
                log.error("Unable to create necessary OMRS objects -- failing.", e);
                successfulInit = false;
            }
        } else {
            successfulInit = false;
        }

        if (!successfulInit) {
            if (auditLog != null) {
                IGCOMRSAuditCode auditCode = IGCOMRSAuditCode.REST_CLIENT_FAILURE;
                auditLog.logRecord(methodName,
                        auditCode.getLogMessageId(),
                        auditCode.getSeverity(),
                        auditCode.getFormattedLogMessage(address),
                        null,
                        auditCode.getSystemAction(),
                        auditCode.getUserAction());
            }
            IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.REST_CLIENT_FAILURE;
            String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(address);
            throw new OMRSRuntimeException(
                    errorCode.getHTTPErrorCode(),
                    this.getClass().getName(),
                    methodName,
                    errorMessage,
                    errorCode.getSystemAction(),
                    errorCode.getUserAction()
            );
        } else {
            if (auditLog != null) {
                IGCOMRSAuditCode auditCode = IGCOMRSAuditCode.REPOSITORY_SERVICE_INITIALIZED;
                auditLog.logRecord(methodName,
                        auditCode.getLogMessageId(),
                        auditCode.getSeverity(),
                        auditCode.getFormattedLogMessage(getServerName()),
                        null,
                        auditCode.getSystemAction(),
                        auditCode.getUserAction());
            }
        }

    }

    /**
     * Retrieve the version identifier of the IGC environment.
     *
     * @return IGCVersionEnum
     */
    public IGCVersionEnum getIGCVersion() {
        return this.igcVersion;
    }

    /**
     * Set up the unique Id for this metadata collection.
     *
     * @param metadataCollectionId - String unique Id
     */
    @Override
    public void setMetadataCollectionId(String metadataCollectionId) {
        this.metadataCollectionId = metadataCollectionId;

        /*
         * Initialize the metadata collection only once the connector is properly set up.
         * (Meaning we will NOT initialise the collection if the connector failed to setup properly.)
         */
        if (successfulInit) {
            metadataCollection = new IGCOMRSMetadataCollection(this,
                    serverName,
                    repositoryHelper,
                    repositoryValidator,
                    metadataCollectionId);
        }
    }

    /**
     * Free up any resources held since the connector is no longer needed.
     */
    @Override
    public void disconnect() {

        // Close the session on the IGC REST client
        this.igcRestClient.disconnect();
        if (auditLog != null) {
            IGCOMRSAuditCode auditCode = IGCOMRSAuditCode.REPOSITORY_SERVICE_SHUTDOWN;
            auditLog.logRecord("disconnect",
                    auditCode.getLogMessageId(),
                    auditCode.getSeverity(),
                    auditCode.getFormattedLogMessage(getServerName()),
                    null,
                    auditCode.getSystemAction(),
                    auditCode.getUserAction());
        }

    }


    /**
     * Returns the metadata collection object that provides an OMRS abstraction of the metadata within
     * a metadata repository.
     *
     * @return OMRSMetadataCollection - metadata information retrieved from the metadata repository.
     */
    @Override
    public OMRSMetadataCollection getMetadataCollection() {

        if (metadataCollection == null) {
            throw new NullPointerException("Local metadata collection id is not set up");
        }

        return metadataCollection;
    }


    /**
     * Access the IGC REST API client directly.
     *
     * @return IGCRestClient
     */
    public IGCRestClient getIGCRestClient() { return this.igcRestClient; }

    /**
     * Retrieve the list of default zones to apply to assets.
     *
     * @return {@code List<String>}
     */
    public List<String> getDefaultZones() { return this.defaultZones; }

    /**
     * Retrieve the default IGC status to use when creating a new IGC term.
     *
     * @return String
     */
    public String getDefaultTermStatus() { return this.defaultTermStatus; }

    /**
     * Retrieve the IGC Repository ID (RID) of the default glossary.  Note: this method will also create the default
     * glossary if it does not yet already exist.
     *
     * @return String - the RID of the default glossary
     */
    public String getDefaultGlossaryRID() {
        if (defaultGlossaryRID == null) {
            // Search for the default glossary
            IGCSearchCondition byName = new IGCSearchCondition("name", "=", defaultGlossaryName);
            IGCSearchCondition noParent = new IGCSearchCondition("parent_category", "isNull", false);
            IGCSearchConditionSet conditions = new IGCSearchConditionSet(byName);
            conditions.addCondition(noParent);
            IGCSearch igcSearch = new IGCSearch("category", conditions);
            ItemList<Category> results = igcRestClient.search(igcSearch);
            if (results == null || results.getItems().isEmpty()) {
                // If the default glossary was not found, create it
                IGCCreate igcCreate = new IGCCreate("category");
                igcCreate.addProperty("name", defaultGlossaryName);
                defaultGlossaryRID = igcRestClient.create(igcCreate);
            } else {
                // Otherwise set the RID to the default glossary that was found
                if (results.getItems().size() > 1 && log.isWarnEnabled()) {
                    log.warn("Found multiple entries for default glossary named '{}', returning only the first.", defaultGlossaryName);
                }
                defaultGlossaryRID = results.getItems().get(0).getId();
            }
        }
        return defaultGlossaryRID;
    }

    /**
     * Generates a zip file for the OMRS OpenIGC bundle, needed to enable change tracking for the event mapper.
     *
     * @return boolean true on success, or if the bundle already exists and false otherwise
     */
    private boolean upsertOMRSBundleZip() throws RepositoryErrorException {

        final String methodName = "upsertOMRSBundleZip";

        ClassPathResource bundleResource = new ClassPathResource("OMRS.zip");
        boolean success = this.igcRestClient.upsertOpenIgcBundle("OMRS", bundleResource);
        if (!success) {
            IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.OMRS_BUNDLE_FAILURE;
            String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage("open");
            throw new RepositoryErrorException(errorCode.getHTTPErrorCode(),
                    this.getClass().getName(),
                    methodName,
                    errorMessage,
                    errorCode.getSystemAction(),
                    errorCode.getUserAction());
        }

        return success;

    }

}
