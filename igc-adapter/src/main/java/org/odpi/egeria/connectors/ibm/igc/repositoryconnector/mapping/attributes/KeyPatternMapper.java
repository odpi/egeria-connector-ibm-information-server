/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.attributes;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;

/**
 * Singleton to map between OMRS "KeyPattern" enum and corresponding IGC property values.
 */
public class KeyPatternMapper extends EnumMapping {

    private static class Singleton {
        private static final KeyPatternMapper INSTANCE = new KeyPatternMapper();
    }
    public static KeyPatternMapper getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    private KeyPatternMapper() {
        super(
                "KeyPattern"
        );
        addDefaultEnumMapping(99, "Other", "Another key pattern.");
        addEnumMapping("LocalKey", 0, "LocalKey", "Unique key allocated and used within the scope of a single system.");
        addEnumMapping("RecycledKey", 1, "RecycledKey", "Key allocated and used within the scope of a single system that is periodically reused for different records.");
        addEnumMapping("NaturalKey", 2, "NaturalKey", "Key derived from an attribute of the entity, such as email address, passport number.");
        addEnumMapping("MirrorKey", 3, "MirrorKey", "Key value copied from another system.");
        addEnumMapping("AggregateKey", 4, "AggregateKey", "Key formed by combining keys from multiple systems.");
        addEnumMapping("CallersKey", 5, "CallersKey", "Key from another system can bey used if system name provided.");
        addEnumMapping("StableKey", 6, "StableKey", "Key value will remain active even if records are merged.");
    }

}
