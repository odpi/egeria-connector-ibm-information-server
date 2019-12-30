/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.information.server.mocks;

import java.util.*;

/**
 * A set of constants that can be re-used across various modules' tests.
 */
public class MockConstants {

    public static final String GLOSSARY_RID = "6662c0f2.ee6a64fe.00263pfar.1a0mm9a.lfjd3c.rmgl1cdd5fcd4bijur3g3";
    public static final String GLOSSARY_NAME = "Coco Pharmaceuticals";
    public static final String GLOSSARY_DESC = "This glossary contains Glossary Terms and Categories that are related to the Coco Pharmaceuticals data";

    public static final String IGC_HOST = "localhost";
    public static final String IGC_PORT = "1080";
    public static final String IGC_ENDPOINT = IGC_HOST + ":" + IGC_PORT;
    public static final String IGC_USER = "isadmin";
    public static final String IGC_PASS = "isadmin";

    public static final String EGERIA_USER = "admin";

    private static final Map<String, String> JOB_RIDS = createJobRidMap();
    private static Map<String, String> createJobRidMap() {
        Map<String, String> map = new HashMap<>();
        map.put("file_to_EMPLOYEE", "c2e76d84.43058877.001mts4t0.icfuoe6.jgkei5.q2o0d2okuqi3seok0qf2h");
        map.put("file_to_WORKLOCATION", "c2e76d84.43058877.001mts4t1.1nphp9f.68terv.t09jpq302deq8omkncc9b");
        map.put("initial_load", "c2e76d84.43058877.001mts4t1.up6q6ea.ifgoj9.7sa3vhngl83fld1ohctvi");
        map.put("load_EMPLSANL", "c2e76d84.43058877.001mts4t1.dgor5h5.lm24al.fmgmk91dsv49nelg28fl0");
        return Collections.unmodifiableMap(map);
    }

    private static final Map<String, String> FILE_RECORD_RIDS = createFileRecordRidMap();
    private static Map<String, String> createFileRecordRidMap() {
        Map<String, String> map = new HashMap<>();
        map.put("Employee-Employee", "b1c497ce.54bd3a08.001mts4ph.b86tslg.b76kkf.nutbk7i9du0ba7i9r4ka4");
        map.put("Location-WorkLocation", "b1c497ce.54bd3a08.001mts4ph.b7lpq4j.d82i4j.07t39udu9cg9cr4pao1kd");
        return Collections.unmodifiableMap(map);
    }

    private static final Map<String, String> TABLE_RIDS = createTableRidMap();
    private static Map<String, String> createTableRidMap() {
        Map<String, String> map = new HashMap<>();
        map.put("EMPLOYEE", "b1c497ce.54bd3a08.001mts4qb.ntk38t5.g75l3m.h1o18j6lbnm86rj1f53qf");
        map.put("WORKLOCATION", "b1c497ce.54bd3a08.001mts4pv.ero7ma4.762e3s.vmiap4ol4t6bfumes65jg");
        map.put("EMPSALARYANALYSIS", "b1c497ce.54bd3a08.001mts4r2.p5ktjrv.hdk8rd.mi5vb5n6bcpsi129jrsu0");
        return Collections.unmodifiableMap(map);
    }

    /**
     * Get a mapping of job name to RID for the jobs that are mocked.
     * @return {@code Map<String, String>}
     */
    public static Map<String, String> getJobRids() { return JOB_RIDS; }

    /**
     * Get a mapping of file record name to RID for the file records that are mocked.
     * @return {@code Map<String, String>}
     */
    public static Map<String, String> getFileRecordRids() { return FILE_RECORD_RIDS; }

    /**
     * Get a mapping of database table name to RID for the tables that are mocked.
     * @return {@code Map<String, String>}
     */
    public static Map<String, String> getTableRids() { return TABLE_RIDS; }

}
