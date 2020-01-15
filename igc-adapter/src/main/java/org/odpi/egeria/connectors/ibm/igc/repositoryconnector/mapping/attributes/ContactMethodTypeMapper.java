/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.attributes;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;

/**
 * Singleton to map between OMRS "ContactMethodType" enum and corresponding IGC property values.
 */
public class ContactMethodTypeMapper extends EnumMapping {

    private static class Singleton {
        private static final ContactMethodTypeMapper INSTANCE = new ContactMethodTypeMapper();
    }
    public static ContactMethodTypeMapper getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    private ContactMethodTypeMapper() {
        super(
                "ContactMethodType"
        );
        addDefaultEnumMapping(99, "Other", "Another usage.");
        addEnumMapping("email", 0, "Email", "Contact through email.");
    }

}
