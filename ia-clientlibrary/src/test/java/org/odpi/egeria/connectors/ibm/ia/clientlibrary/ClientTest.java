/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary;

import org.odpi.egeria.connectors.ibm.ia.clientlibrary.model.*;
import org.odpi.egeria.connectors.ibm.information.server.mocks.MockConstants;
import org.odpi.openmetadata.http.HttpHelper;
import org.testng.annotations.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.*;

/**
 * Test the connector(s) using the mocked server resources.
 */
public class ClientTest {

    private IARestClient iaRestClient;

    public ClientTest() {
        HttpHelper.noStrictSSL();
        iaRestClient = new IARestClient(MockConstants.IGC_HOST, MockConstants.IGC_PORT, MockConstants.IGC_USER, MockConstants.IGC_PASS);
    }

    @Test
    public void testSimplifyName() {
        String simpleName = IARestClient.getUnqualifiedNameFromQualifiedName(MockConstants.IA_TABLE_NAME);
        assertEquals(simpleName, "CONTACTEMAIL");
    }

    @Test
    public void testGetProjectList() {
        List<Project> projects = iaRestClient.getProjectList();
        assertNotNull(projects);
        assertFalse(projects.isEmpty());
        for (Project project : projects) {
            assertNotNull(project.getName());
        }
    }

    @Test
    public void testGetProjectDetails() {
        Project project = iaRestClient.getProjectDetails(MockConstants.IA_PROJECT_NAME);
        assertNotNull(project);
        assertEquals(project.getName(), MockConstants.IA_PROJECT_NAME);
        List<String> tablesInProject = iaRestClient.getTablesInProject(project);
        assertNotNull(tablesInProject);
        assertFalse(tablesInProject.isEmpty());
    }

    @Test
    public void testGetPublishedResults() {
        Map<String, Date> publishedResults = iaRestClient.getPublishedResults(MockConstants.IA_PROJECT_NAME);
        assertNotNull(publishedResults);
        assertFalse(publishedResults.isEmpty());
        for (Map.Entry<String, Date> entry : publishedResults.entrySet()) {
            String tableName = entry.getKey();
            Date published = entry.getValue();
            assertNotNull(tableName);
            assertNotNull(published);
        }
    }

    @Test
    public void testGetColumnAnalysisResults() {
        Map<String, ColumnAnalysisResults> columnToAnalysisResults = iaRestClient.getColumnAnalysisResultsForTable(MockConstants.IA_PROJECT_NAME, MockConstants.IA_TABLE_NAME);
        assertNotNull(columnToAnalysisResults);
        assertFalse(columnToAnalysisResults.isEmpty());
    }

    @Test
    public void testGetDataQualityResults() {
        Map<String, List<DataQualityProblem>> columnToQualityResults = iaRestClient.getDataQualityAnalysisResultsForTable(MockConstants.IA_PROJECT_NAME, MockConstants.IA_TABLE_NAME);
        assertNotNull(columnToQualityResults);
        assertTrue(columnToQualityResults.isEmpty());
        columnToQualityResults = iaRestClient.getDataQualityAnalysisResultsForTable(MockConstants.IA_PROJECT_NAME, MockConstants.IA_TABLE_NAME_WITH_DQ_PROBLEMS);
        assertNotNull(columnToQualityResults);
        assertFalse(columnToQualityResults.isEmpty());
        for (Map.Entry<String, List<DataQualityProblem>> entry : columnToQualityResults.entrySet()) {
            String columnName = entry.getKey();
            List<DataQualityProblem> problems = entry.getValue();
            assertEquals(columnName, "BONUS");
            assertNotNull(problems);
            assertFalse(problems.isEmpty());
            assertEquals(problems.size(), 1);
            DataQualityProblem problem = problems.get(0);
            assertEquals(problem.getProblemCode(), "SuspectValues");
            assertEquals(problem.getProblemName(), "Suspect values");
            assertEquals(problem.getFrequency().longValue(), 1L);
            assertEquals(problem.getConfidence().doubleValue(), 1.0);
        }
    }

    @Test
    public void testRunColumnAnalysis() {
        TaskExecutionReport scheduled = iaRestClient.runColumnAnalysis(MockConstants.IA_PROJECT_NAME, MockConstants.IA_TABLE_NAME + ".*");
        testRunningThenCompleted(scheduled);
    }

    @Test
    public void testRunDataQualityAnalysis() {
        TaskExecutionReport scheduled = iaRestClient.runDataQualityAnalysis(MockConstants.IA_PROJECT_NAME, MockConstants.IA_TABLE_NAME);
        testRunningThenCompleted(scheduled);
    }

    private void testRunningThenCompleted(TaskExecutionReport scheduled) {
        assertNotNull(scheduled);
        // First check that it is running
        TaskExecution running = testStatus(iaRestClient.getTaskStatus(scheduled), "running");
        TaskExecution completed = testStatus(iaRestClient.getTaskStatus(scheduled), "successful");
        assertTrue(completed.getExecutionTime() > 0);
    }

    private TaskExecution testStatus(List<TaskExecutionSchedule> schedules, String expectedStatus) {
        assertNotNull(schedules);
        assertFalse(schedules.isEmpty());
        assertEquals(schedules.size(), 1);
        TaskExecutionSchedule onlySchedule = schedules.get(0);
        List<TaskExecution> taskExecutions = onlySchedule.getTaskExecutionList();
        assertNotNull(taskExecutions);
        assertFalse(taskExecutions.isEmpty());
        assertEquals(taskExecutions.size(), 1);
        TaskExecution onlyTaskExecution = taskExecutions.get(0);
        assertNotNull(onlyTaskExecution.getExecutionDate());
        ExecutionStatus status = onlyTaskExecution.getStatus();
        assertNotNull(status);
        assertEquals(status.getValue(), expectedStatus);
        return onlyTaskExecution;
    }

    @Test
    public void testPublishResults() {
        assertTrue(iaRestClient.publishResults(MockConstants.IA_PROJECT_NAME, MockConstants.IA_TABLE_NAME));
    }

    @AfterSuite
    void stopClient() {
        iaRestClient.disconnect();
    }

}
