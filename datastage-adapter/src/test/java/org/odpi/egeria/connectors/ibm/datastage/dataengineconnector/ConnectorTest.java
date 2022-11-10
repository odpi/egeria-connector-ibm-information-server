/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.datastage.dataengineconnector;

import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.mocks.MockConnection;
import org.odpi.egeria.connectors.ibm.information.server.mocks.MockConstants;
import org.odpi.openmetadata.accessservices.dataengine.model.*;
import org.odpi.openmetadata.accessservices.dataengine.model.Process;
import org.odpi.openmetadata.frameworks.connectors.ConnectorBroker;
import org.odpi.openmetadata.frameworks.connectors.ffdc.ConnectionCheckedException;
import org.odpi.openmetadata.frameworks.connectors.ffdc.ConnectorCheckedException;
import org.odpi.openmetadata.frameworks.connectors.ffdc.PropertyServerException;
import org.odpi.openmetadata.frameworks.connectors.properties.beans.Connection;
import org.odpi.openmetadata.frameworks.connectors.properties.beans.Endpoint;
import org.odpi.openmetadata.http.HttpHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public void testExtraConfig() {

        Connection extraConfigConnection = new MockConnection();

        Map<String, Object> configProperties = new HashMap<>();
        configProperties.put(DataStageConnectorProvider.CREATE_DATA_STORE_SCHEMAS, true);
        configProperties.put(DataStageConnectorProvider.INCLUDE_VIRTUAL_ASSETS, false);
        configProperties.put(DataStageConnectorProvider.PAGE_SIZE, 1000);
        extraConfigConnection.setConfigurationProperties(configProperties);

        ConnectorBroker connectorBroker = new ConnectorBroker();
        try {
            Object connector = connectorBroker.getConnector(extraConfigConnection);
            assertTrue(connector instanceof DataStageConnector);
            DataStageConnector dsConnector = (DataStageConnector) connector;
            dsConnector.start();
        } catch (ConnectionCheckedException | ConnectorCheckedException e) {
            log.error("Unable to retrieve connection through the broker.", e);
            assertNull(e);
        }

    }

    @Test
    public void testEngineDetails() {
        Engine dataEngine = dataStageConnector.getDataEngineDetails();
        assertNotNull(dataEngine);
        assertEquals(dataEngine.getEngineType(), "IBM InfoSphere DataStage");
        assertEquals(dataEngine.getQualifiedName(), "ibm-datastage@" + MockConstants.IGC_ENDPOINT);
    }

    @Test
    public void testLastSync() throws ConnectorCheckedException, PropertyServerException {
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
    public void testChangedSchemas() throws ConnectorCheckedException, PropertyServerException {
        List<SchemaType> schemaTypes = dataStageConnector.getChangedSchemaTypes(null, now);
        assertFalse(schemaTypes.isEmpty());
        assertEquals(schemaTypes.size(), 1);
        SchemaType only = schemaTypes.get(0);
        assertEquals(only.getAttributeList().size(), 8);
    }

    @Test
    public void testProcesses() throws ConnectorCheckedException, PropertyServerException{
        List<Process> processes = dataStageConnector.getChangedProcesses(null, now);
        assertFalse(processes.isEmpty());
        for (Process process : processes) {
            assertNotNull(process.getQualifiedName());
            List<PortImplementation> portImplementations = process.getPortImplementations();
            List<PortAlias> portAliases = process.getPortAliases();
            boolean hasOneOrTheOther = ((portImplementations == null || portImplementations.isEmpty()) && (portAliases != null && !portAliases.isEmpty()))
                    || ((portImplementations != null && !portImplementations.isEmpty()) && (portAliases == null || portAliases.isEmpty()));
            assertTrue(hasOneOrTheOther);
        }
    }

    @Test
    public void testLineageMappings() {
        List<LineageMapping> lineageMappings = dataStageConnector.getChangedLineageMappings(null, now);
        assertTrue(lineageMappings.isEmpty());
    }

    @AfterSuite
    public void stopConnector() throws ConnectorCheckedException {
        dataStageConnector.disconnect();
    }

}
