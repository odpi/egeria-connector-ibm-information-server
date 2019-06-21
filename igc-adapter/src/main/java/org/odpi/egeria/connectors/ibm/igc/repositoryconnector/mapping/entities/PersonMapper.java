/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.ContactThroughMapper_Person;

/**
 * Defines the mapping to the OMRS "Person" entity.
 */
public class PersonMapper extends ReferenceableMapper {

    private static class Singleton {
        private static final PersonMapper INSTANCE = new PersonMapper();
    }
    public static PersonMapper getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    private PersonMapper() {

        // Start by calling the superclass's constructor to initialise the Mapper
        super(
                "user",
                "User",
                "Person",
                null,
                false
        );
        addOtherIGCAssetType("steward_user");
        addOtherIGCAssetType("non_steward_user");

        // The list of properties that should be mapped
        addSimplePropertyMapping("principal_id", "name");
        addSimplePropertyMapping("full_name", "fullName");
        addSimplePropertyMapping("job_title", "jobTitle");

        addLiteralPropertyMapping("isPublic", true);

        // The classes to use for mapping any relationships
        addRelationshipMapper(ContactThroughMapper_Person.getInstance(null));

    }

}
