/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.attributes;

/**
 * Singleton to map between OMRS "KeyPattern" enum and corresponding IGC property values.
 */
public class KeyPatternMapper extends EnumMapping {

    private static class Singleton {
        private static final KeyPatternMapper INSTANCE = new KeyPatternMapper();
    }
    public static KeyPatternMapper getInstance() {
        return Singleton.INSTANCE;
    }

    private KeyPatternMapper() {
        super(
                "KeyPattern"
        );
        addDefaultEnumMapping(99, "Other");
        addEnumMapping("LocalKey", 0, "LocalKey");
        addEnumMapping("RecycledKey", 1, "RecycledKey");
        addEnumMapping("NaturalKey", 2, "NaturalKey");
        addEnumMapping("MirrorKey", 3, "MirrorKey");
        addEnumMapping("AggregateKey", 4, "AggregateKey");
        addEnumMapping("CallersKey", 5, "CallersKey");
        addEnumMapping("StableKey", 6, "StableKey");
    }

}
