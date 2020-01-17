/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.datastage.dataengineconnector;

import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.mocks.MockConnection;
import org.odpi.egeria.connectors.ibm.information.server.mocks.MockConstants;
import org.odpi.openmetadata.accessservices.dataengine.model.PortAlias;
import org.odpi.openmetadata.accessservices.dataengine.model.PortImplementation;
import org.odpi.openmetadata.accessservices.dataengine.model.Process;
import org.odpi.openmetadata.accessservices.dataengine.model.SoftwareServerCapability;
import org.odpi.openmetadata.frameworks.connectors.ConnectorBroker;
import org.odpi.openmetadata.frameworks.connectors.ffdc.ConnectionCheckedException;
import org.odpi.openmetadata.frameworks.connectors.ffdc.ConnectorCheckedException;
import org.odpi.openmetadata.frameworks.connectors.properties.beans.Connection;
import org.odpi.openmetadata.frameworks.connectors.properties.beans.Endpoint;
import org.odpi.openmetadata.http.HttpHelper;
import org.odpi.openmetadata.openconnectors.governancedaemonconnectors.dataengineproxy.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;

import java.util.Date;
import java.util.List;

import static org.testng.Assert.*;

/**
 * Test the connector(s) using the mocked server resources.
 */
public class ConnectorTest {

    private static final Logger log = LoggerFactory.getLogger(ConnectorTest.class);

    private Date now;
    private DataStageConnector dataStageConnector;

    public ConnectorTest() {
        HttpHelper.noStrictSSL();
        now = new Date();
    }

    @BeforeSuite
    public void startConnector() {
        Connection mockConnection = new MockConnection();
        ConnectorBroker connectorBroker = new ConnectorBroker();
        try {
            Object connector = connectorBroker.getConnector(mockConnection);
            assertTrue(connector instanceof DataStageConnector);
            dataStageConnector = (DataStageConnector) connector;
            dataStageConnector.start();
        } catch (ConnectionCheckedException | ConnectorCheckedException e) {
            log.error("Unable to retrieve a connection through the broker.", e);
            assertNull(e);
        }
    }

    @Test
    public void testNegativeConnector() {

        Connection emptyEndpoint = new MockConnection();
        emptyEndpoint.setEndpoint(null);
        confirmThrowsConnectorCheckedException(emptyEndpoint);

        Connection noAddress = new MockConnection();
        Endpoint endpoint = noAddress.getEndpoint();
        endpoint.setAddress(null);
        noAddress.setEndpoint(endpoint);
        confirmThrowsConnectorCheckedException(noAddress);

        endpoint.setAddress("");
        noAddress.setEndpoint(endpoint);
        confirmThrowsConnectorCheckedException(noAddress);

    }

    private void confirmThrowsConnectorCheckedException(Connection connection) {

        ConnectorBroker connectorBroker = new ConnectorBroker();
        DataStageConnector dsConnector = null;
        try {
            Object connector = connectorBroker.getConnector(connection);
            assertTrue(connector instanceof DataStageConnector);
            dsConnector = (DataStageConnector) connector;
        } catch (ConnectionCheckedException | ConnectorCheckedException e) {
            log.error("Unable to retrieve connection through the broker.", e);
            assertNull(e);
        }
        assertNotNull(dsConnector);
        DataStageConnector finalDsConnector = dsConnector;
        assertNotNull(finalDsConnector);
        assertThrows(ConnectorCheckedException.class, () -> finalDsConnector.start());

    }

    @Test
    public void testEngineDetails() {
        DataEngineSoftwareServerCapability dataEngine = dataStageConnector.getDataEngineDetails();
        assertNotNull(dataEngine);
        SoftwareServerCapability capability = dataEngine.getSoftwareServerCapability();
        assertNotNull(capability);
        assertEquals(capability.getEngineType(), "IBM InfoSphere DataStage");
        assertEquals(capability.getQualifiedName(), "ibm-datastage@" + MockConstants.IGC_ENDPOINT);
    }

    @Test
    public void testLastSync() {
        // First query is empty result
        Date changesLastSynced = dataStageConnector.getChangesLastSynced();
        assertNull(changesLastSynced);
        // This should result in a create
        dataStageConnector.setChangesLastSynced(now);
        changesLastSynced = dataStageConnector.getChangesLastSynced();
        assertNotNull(changesLastSynced);
        // This should result in an update
        dataStageConnector.setChangesLastSynced(now);
    }

    @Test
    public void testChangedSchemas() {
        // TODO: these should not be empty once we add virtual asset handling
        List<DataEngineSchemaType> schemaTypes = dataStageConnector.getChangedSchemaTypes(null, now);
        assertTrue(schemaTypes.isEmpty());
    }

    @Test
    public void testPortImplementations() {
        List<DataEnginePortImplementation> portImplementations = dataStageConnector.getChangedPortImplementations(null, now);
        assertTrue(portImplementations.isEmpty());
    }

    @Test
    public void testPortAliases() {
        List<DataEnginePortAlias> portAliases = dataStageConnector.getChangedPortAliases(null, now);
        assertTrue(portAliases.isEmpty());
    }

    @Test
    public void testProcesses() {
        List<DataEngineProcess> processes = dataStageConnector.getChangedProcesses(null, now);
        assertFalse(processes.isEmpty());
        for (DataEngineProcess deProcess : processes) {
            Process process = deProcess.getProcess();
            assertNotNull(process.getQualifiedName());
            List<PortImplementation> portImplementations = process.getPortImplementations();
            List<PortAlias> portAliases = process.getPortAliases();
            boolean hasOneOrTheOther = ( (portImplementations == null || portImplementations.isEmpty()) && (portAliases != null && !portAliases.isEmpty()) )
                    || ( (portImplementations != null && !portImplementations.isEmpty()) && (portAliases == null || portAliases.isEmpty()) );
            assertTrue(hasOneOrTheOther);
        }
    }

    @Test
    public void testLineageMappings() {
        List<DataEngineLineageMappings> lineageMappings = dataStageConnector.getChangedLineageMappings(null, now);
        assertTrue(lineageMappings.isEmpty());
    }

    @AfterSuite
    public void stopConnector() {
        dataStageConnector.disconnect();
    }

}
