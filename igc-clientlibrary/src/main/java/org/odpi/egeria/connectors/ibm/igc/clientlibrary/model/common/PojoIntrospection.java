/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common;

import java.util.List;

/**
 * Interface through which to retrieve static details from Jackson POJO's, using LambdaMetafactory approach.
 *
 * Based on simple example at:
 * https://github.com/ge0ffrey/ge0ffrey-presentations/blob/master/code/fasterreflection/fasterreflection-framework/src/main/java/be/ge0ffrey/presentations/fasterreflection/framework/BeanPropertyReader.java
 */
public interface PojoIntrospection {
    Boolean canBeCreated();
    Boolean includesModificationDetails();
    String getIgcTypeDisplayName();
    List<String> getNonRelationshipProperties();
    List<String> getStringProperties();
    List<String> getAllProperties();
    List<String> getPagedRelationshipProperties();
}
