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
    void startConnector() {
        Connection mockConnection = new MockConnection();
        ConnectorBroker connectorBroker = new ConnectorBroker();
        try {
            Object connector = connectorBroker.getConnector(mockConnection);
            assertTrue(connector instanceof DataStageConnector);
            dataStageConnector = (DataStageConnector) connector;
        } catch (ConnectionCheckedException | ConnectorCheckedException e) {
            log.error("Unable to retrieve a connection through the broker.", e);
            assertNull(e);
        }
    }

    @Test
    void testEngineDetails() {
        DataEngineSoftwareServerCapability dataEngine = dataStageConnector.getDataEngineDetails();
        assertNotNull(dataEngine);
        SoftwareServerCapability capability = dataEngine.getSoftwareServerCapability();
        assertNotNull(capability);
        assertEquals(capability.getEngineType(), "IBM InfoSphere DataStage");
        assertEquals(capability.getQualifiedName(), "ibm-datastage@" + MockConstants.IGC_ENDPOINT);
    }

    @Test
    void testLastSync() {
        Date changesLastSynced = dataStageConnector.getChangesLastSynced();
        assertNull(changesLastSynced);
    }

    @Test
    void testChangedSchemas() {
        // TODO: these should not be empty once we add virtual asset handling
        List<DataEngineSchemaType> schemaTypes = dataStageConnector.getChangedSchemaTypes(null, now);
        assertTrue(schemaTypes.isEmpty());
    }

    @Test
    void testPortImplementations() {
        List<DataEnginePortImplementation> portImplementations = dataStageConnector.getChangedPortImplementations(null, now);
        assertTrue(portImplementations.isEmpty());
    }

    @Test
    void testPortAliases() {
        List<DataEnginePortAlias> portAliases = dataStageConnector.getChangedPortAliases(null, now);
        assertTrue(portAliases.isEmpty());
    }

    @Test
    void testProcesses() {
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
    void testLineageMappings() {
        List<DataEngineLineageMappings> lineageMappings = dataStageConnector.getChangedLineageMappings(null, now);
        assertTrue(lineageMappings.isEmpty());
    }

    @AfterSuite
    void stopConnector() {
        dataStageConnector.disconnect();
    }

}
