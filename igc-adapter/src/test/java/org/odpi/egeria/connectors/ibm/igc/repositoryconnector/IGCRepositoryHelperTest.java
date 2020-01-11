/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchSorting;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.SequencingOrder;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.PrimitivePropertyValue;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.typedefs.PrimitiveDefCategory;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import static org.testng.Assert.*;

/**
 * Test static methods of the IGCRepositoryHelper class.
 */
public class IGCRepositoryHelperTest {

    public IGCRepositoryHelperTest() {
        // Do nothing...
    }

    @Test
    public void testQualifiedNames() {

        String qualifiedName = "(host_(engine))=INFOSVR";
        assertFalse(IGCRepositoryHelper.isQualifiedNameOfGeneratedEntity(qualifiedName));

        String testPrefix = "TESTPREFIX";
        String generatedQN = IGCRepositoryHelper.getQualifiedNameForGeneratedEntity(testPrefix, qualifiedName);
        assertTrue(IGCRepositoryHelper.isQualifiedNameOfGeneratedEntity(generatedQN));

        assertEquals(IGCRepositoryHelper.getPrefixFromGeneratedQualifiedName(generatedQN), testPrefix);
        assertEquals(IGCRepositoryHelper.getSearchableQualifiedName(generatedQN), qualifiedName);

    }

    @Test
    public void testEquivalentValues() {

        testEquivalence(false, true, PrimitiveDefCategory.OM_PRIMITIVE_TYPE_BOOLEAN);
        testEquivalence((short)1, (short)2, PrimitiveDefCategory.OM_PRIMITIVE_TYPE_SHORT);
        testEquivalence(3, 4, PrimitiveDefCategory.OM_PRIMITIVE_TYPE_INT);
        testEquivalence(5L, 6L, PrimitiveDefCategory.OM_PRIMITIVE_TYPE_LONG);
        testEquivalence((float)7.0, (float)7.1, PrimitiveDefCategory.OM_PRIMITIVE_TYPE_FLOAT);
        testEquivalence(8.0, 8.1, PrimitiveDefCategory.OM_PRIMITIVE_TYPE_DOUBLE);
        testEquivalence(BigInteger.valueOf(9), BigInteger.valueOf(10), PrimitiveDefCategory.OM_PRIMITIVE_TYPE_BIGINTEGER);
        testEquivalence(BigDecimal.valueOf(11.0), BigDecimal.valueOf(11.1), PrimitiveDefCategory.OM_PRIMITIVE_TYPE_BIGDECIMAL);
        long now = new Date().getTime();
        testEquivalence(now, now + 1, PrimitiveDefCategory.OM_PRIMITIVE_TYPE_DATE);
        testEquivalence("TestString", "OtherString", PrimitiveDefCategory.OM_PRIMITIVE_TYPE_STRING);

    }

    private void testEquivalence(Object test, Object otherValue, PrimitiveDefCategory category) {
        PrimitivePropertyValue testIPV = new PrimitivePropertyValue();
        testIPV.setPrimitiveDefCategory(category);
        testIPV.setPrimitiveValue(test);
        assertTrue(IGCRepositoryHelper.equivalentValues(test, testIPV));
        test = otherValue;
        assertFalse(IGCRepositoryHelper.equivalentValues(test, testIPV));
    }

    @Test
    public void testSortFromNonPropertySequencingOrder() {
        testSorting(SequencingOrder.GUID, "_id", true);
        testSorting(SequencingOrder.CREATION_DATE_RECENT, "created_on", false);
        testSorting(SequencingOrder.CREATION_DATE_OLDEST, "created_on", true);
        testSorting(SequencingOrder.LAST_UPDATE_RECENT, "modified_on", false);
        testSorting(SequencingOrder.LAST_UPDATE_OLDEST, "modified_on", true);
        IGCSearchSorting sorting = IGCRepositoryHelper.sortFromNonPropertySequencingOrder(SequencingOrder.ANY);
        assertNull(sorting);
    }

    private void testSorting(SequencingOrder order, String expectedProperty, Boolean expectedAscending) {
        IGCSearchSorting sorting = IGCRepositoryHelper.sortFromNonPropertySequencingOrder(order);
        String propertyName = sorting.getProperty();
        Boolean ascending = sorting.getAscending();
        assertEquals(propertyName, expectedProperty);
        assertEquals(ascending, expectedAscending);
    }

}
