/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.ia.clientlibrary;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.odpi.egeria.connectors.ibm.ia.clientlibrary.model.AnalysisStatus;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class EnumTest {

    private AnalysisStatus testStatus = AnalysisStatus.LIGHTWEIGHT_REVIEW;
    private String testString = "\"lightweight_review\"";
    private ObjectMapper mapper;

    public EnumTest() {
        mapper = new ObjectMapper();
    }

    @Test
    public void deserializeTests() {
        try {
            AnalysisStatus statusRead = mapper.readValue(testString, AnalysisStatus.class);
            assertEquals(statusRead, testStatus);
        } catch (JsonProcessingException e) {
            assertNull(e);
        }
    }

    @Test
    public void serializeTests() {
        try {
            String statusWritten = mapper.writeValueAsString(testStatus);
            assertEquals(statusWritten, testString);
        } catch (JsonProcessingException e) {
            assertNull(e);
        }
    }

}
