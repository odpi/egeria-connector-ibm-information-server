/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.AttachedNoteLogMapper;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.ContactThroughMapper_Person;

/**
 * Defines the mapping to the OMRS "Person" entity.
 */
public class PersonMapper extends ActorProfileMapper {

    private static class Singleton {
        private static final PersonMapper INSTANCE = new PersonMapper();
    }
    public static PersonMapper getInstance(IGCVersionEnum version) {
        return Singleton.INSTANCE;
    }

    protected PersonMapper() {

        // Start by calling the superclass's constructor to initialise the Mapper
        super(
                "user",
                "User",
                "Person"
        );
        addOtherIGCAssetType("steward_user");
        addOtherIGCAssetType("non_steward_user");

        // The list of properties that should be mapped
        addSimplePropertyMapping("full_name", "fullName");
        addSimplePropertyMapping("job_title", "jobTitle");
        addLiteralPropertyMapping("isPublic", true);

        // Properties mapped to default values
        addLiteralPropertyMapping("title", null);
        addLiteralPropertyMapping("givenNames", null);
        addLiteralPropertyMapping("surname", null);
        addLiteralPropertyMapping("employeeNumber", null);
        addLiteralPropertyMapping("employeeType", null);
        addLiteralPropertyMapping("preferredLanguage", null);
        addLiteralPropertyMapping("initials", null);

        // The classes to use for mapping any relationships
        addRelationshipMapper(AttachedNoteLogMapper.getInstance(null));
        addRelationshipMapper(ContactThroughMapper_Person.getInstance(null));

    }

}
