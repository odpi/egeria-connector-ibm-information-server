/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;

/**
 * Singleton to map the OMRS "ContactThrough" relationship.
 * @see ContactThroughMapper_Person
 * @see ContactThroughMapper_Team
 */
public class ContactThroughMapper extends RelationshipMapping {

    private static class Singleton {
        private static final ContactThroughMapper INSTANCE = new ContactThroughMapper();
    }
    public static ContactThroughMapper getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    private ContactThroughMapper() {
        super(
                "",
                "",
                "",
                "",
                "ContactThrough",
                "contactDetails",
                "contacts"
        );
        addSubType(ContactThroughMapper_Person.getInstance(null));
        addSubType(ContactThroughMapper_Team.getInstance(null));
    }

}
