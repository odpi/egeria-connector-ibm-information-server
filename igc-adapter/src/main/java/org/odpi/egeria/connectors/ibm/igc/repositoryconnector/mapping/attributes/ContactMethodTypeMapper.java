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

    protected ContactMethodTypeMapper() {
        super(
                "ContactMethodType"
        );
        addDefaultEnumMapping(99, "Other", "Another usage.");
        addEnumMapping("email_address", 0, "Email", "Contact through email.");
        addEnumMapping("mobile_phone_number", 1, "Phone", "Contact through telephone number.");
        addEnumMapping("instant_message_id", 2, "Chat", "Contact through chat account.");
    }

}
