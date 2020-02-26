/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector;

import org.odpi.egeria.connectors.ibm.igc.auditlog.IGCOMRSAuditCode;
import org.odpi.egeria.connectors.ibm.igc.auditlog.IGCOMRSErrorCode;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.model.OMRSStub;
import org.odpi.openmetadata.frameworks.connectors.ffdc.ConnectorCheckedException;
import org.odpi.openmetadata.frameworks.connectors.properties.EndpointProperties;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.OMRSMetadataCollection;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.repositoryconnector.OMRSRepositoryConnector;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.RepositoryErrorException;
import org.springframework.core.io.ClassPathResource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class IGCOMRSRepositoryConnector extends OMRSRepositoryConnector {

    protected IGCRestClient igcRestClient;
    protected IGCVersionEnum igcVersion;

    protected List<String> defaultZones;

    /**
     * Default constructor used by the OCF Connector Provider.
     */
    public IGCOMRSRepositoryConnector() {
        defaultZones = new ArrayList<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OMRSMetadataCollection getMetadataCollection() throws RepositoryErrorException {
        final String methodName = "getMetadataCollection";
        if (metadataCollection == null) {
            // If the metadata collection has not yet been created, attempt to create it now
            try {
                connectToIGC(methodName);
            } catch (ConnectorCheckedException e) {
                raiseRepositoryErrorException(IGCOMRSErrorCode.REST_CLIENT_FAILURE, methodName, e, getServerName());
            }
        }
        return super.getMetadataCollection();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start() throws ConnectorCheckedException {

        super.start();
        final String methodName = "start";

        IGCOMRSAuditCode auditCode = IGCOMRSAuditCode.REPOSITORY_SERVICE_STARTING;
        auditLog.logRecord(methodName,
                auditCode.getLogMessageId(),
                auditCode.getSeverity(),
                auditCode.getFormattedLogMessage(),
                null,
                auditCode.getSystemAction(),
                auditCode.getUserAction());

        if (metadataCollection == null) {
            // If the metadata collection has not yet been created, attempt to create it now
            connectToIGC(methodName);
        }

        auditCode = IGCOMRSAuditCode.REPOSITORY_SERVICE_STARTED;
        auditLog.logRecord(methodName,
                auditCode.getLogMessageId(),
                auditCode.getSeverity(),
                auditCode.getFormattedLogMessage(getServerName(), getIGCVersion().getVersionString()),
                null,
                auditCode.getSystemAction(),
                auditCode.getUserAction());

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
     * Connect to the IBM Information Governance Catalog host.
     *
     * @param methodName the name of the method connecting to IGC
     * @throws ConnectorCheckedException on any error connecting to IGC
     */
    protected void connectToIGC(String methodName) throws ConnectorCheckedException {

        EndpointProperties endpointProperties = connectionProperties.getEndpoint();
        if (endpointProperties == null) {
            raiseConnectorCheckedException(IGCOMRSErrorCode.REST_CLIENT_FAILURE, methodName, null, "<null>");
        } else {

            String address = endpointProperties.getProtocol() + "://" + endpointProperties.getAddress();

            String igcUser = connectionProperties.getUserId();
            String igcPass = connectionProperties.getClearPassword();

            // Retrieve connection details
            Map<String, Object> proxyProperties = this.connectionBean.getConfigurationProperties();
            if (proxyProperties != null) {
                Object zones = proxyProperties.get(IGCOMRSRepositoryConnectorProvider.DEFAULT_ZONES);
                if (zones instanceof List) {
                    for (Object zone : (List<?>) zones) {
                        if (zone instanceof String) {
                            this.defaultZones.add((String) zone);
                        }
                    }
                }
            }

            boolean successfulInit = false;

            IGCOMRSAuditCode auditCode = IGCOMRSAuditCode.CONNECTING_TO_IGC;
            auditLog.logRecord(methodName,
                    auditCode.getLogMessageId(),
                    auditCode.getSeverity(),
                    auditCode.getFormattedLogMessage(address),
                    null,
                    auditCode.getSystemAction(),
                    auditCode.getUserAction());

            // Create new REST API client (opens a new session)
            try {
                this.igcRestClient = new IGCRestClient(address, igcUser, igcPass);
                if (this.igcRestClient.start()) {
                    if (getMaxPageSize() > 0) {
                        this.igcRestClient.setDefaultPageSize(getMaxPageSize());
                    }
                    // Set the version based on the IGC client's auto-determination of the IGC environment's version
                    this.igcVersion = this.igcRestClient.getIgcVersion();
                    boolean success = upsertOMRSBundleZip();
                    this.igcRestClient.registerPOJO(OMRSStub.class);
                    successfulInit = success;
                }
            } catch (RepositoryErrorException e) {
                raiseConnectorCheckedException(IGCOMRSErrorCode.OMRS_BUNDLE_FAILURE, methodName, e, address);
            } catch (Exception e) {
                raiseConnectorCheckedException(IGCOMRSErrorCode.REST_CLIENT_FAILURE, methodName, e, "<null>");
            }

            if (!successfulInit) {
                raiseConnectorCheckedException(IGCOMRSErrorCode.REST_CLIENT_FAILURE, methodName, null, "<null>");
            }

            auditCode = IGCOMRSAuditCode.CONNECTED_TO_IGC;
            auditLog.logRecord(methodName,
                    auditCode.getLogMessageId(),
                    auditCode.getSeverity(),
                    auditCode.getFormattedLogMessage(address),
                    null,
                    auditCode.getSystemAction(),
                    auditCode.getUserAction());

            metadataCollection = new IGCOMRSMetadataCollection(this,
                    serverName,
                    repositoryHelper,
                    repositoryValidator,
                    metadataCollectionId);

        }

    }

    /**
     * Generates a zip file for the OMRS OpenIGC bundle, needed to enable change tracking for the event mapper.
     *
     * @return boolean true on success, or if the bundle already exists and false otherwise
     * @throws RepositoryErrorException on any error upserting the bundle to IGC
     */
    protected boolean upsertOMRSBundleZip() throws RepositoryErrorException {

        final String methodName = "upsertOMRSBundleZip";

        ClassPathResource bundleResource = new ClassPathResource("OMRS.zip");
        boolean success = this.igcRestClient.upsertOpenIgcBundle("OMRS", bundleResource);
        if (!success) {
            raiseRepositoryErrorException(IGCOMRSErrorCode.OMRS_BUNDLE_FAILURE, methodName, null, "open");
        }

        return success;

    }

    /**
     * Throws a ConnectorCheckedException using the provided parameters.
     * @param errorCode the error code for the exception
     * @param methodName the name of the method throwing the exception
     * @param cause the underlying cause of the exception (or null if none)
     * @param params any parameters for formatting the error message
     * @throws ConnectorCheckedException always
     */
    protected void raiseConnectorCheckedException(IGCOMRSErrorCode errorCode, String methodName, Throwable cause, String ...params) throws ConnectorCheckedException {
        String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(params);
        throw new ConnectorCheckedException(
                errorCode.getHTTPErrorCode(),
                this.getClass().getName(),
                methodName,
                errorMessage,
                errorCode.getSystemAction(),
                errorCode.getUserAction(),
                cause
        );
    }

    /**
     * Throws a RepositoryErrorException using the provided parameters.
     * @param errorCode the error code for the exception
     * @param methodName the name of the method throwing the exception
     * @param cause the underlying cause of the exception (or null if none)
     * @param params any parameters for formatting the error message
     * @throws RepositoryErrorException always
     */
    protected void raiseRepositoryErrorException(IGCOMRSErrorCode errorCode, String methodName, Throwable cause, String ...params) throws RepositoryErrorException {
        String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(params);
        throw new RepositoryErrorException(errorCode.getHTTPErrorCode(),
                this.getClass().getName(),
                methodName,
                errorMessage,
                errorCode.getSystemAction(),
                errorCode.getUserAction(),
                cause);
    }

}
