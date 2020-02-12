/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Paging;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchConditionSet;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchSorting;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.SequencingOrder;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.*;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.typedefs.PrimitiveDefCategory;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.repositoryconnector.OMRSRepositoryHelper;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.FunctionNotSupportedException;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

        assertTrue(IGCRepositoryHelper.equivalentValues(null, null));
        assertFalse(IGCRepositoryHelper.equivalentValues(null, new PrimitivePropertyValue()));
        assertFalse(IGCRepositoryHelper.equivalentValues("a", null));

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

    @Test
    public void testSearchConditionsFromValues() {

        IGCSearchConditionSet set = new IGCSearchConditionSet();
        PrimitivePropertyValue value = new PrimitivePropertyValue();
        value.setPrimitiveDefCategory(PrimitiveDefCategory.OM_PRIMITIVE_TYPE_INT);
        value.setPrimitiveValue(10);
        try {
            IGCRepositoryHelper.addIGCSearchConditionFromValue(null,
                    "TestRepo",
                    set,
                    "testProperty",
                    value);
        } catch (FunctionNotSupportedException e) {
            assertNull(e);
        }
        assertEquals(set.size(), 1);
        assertEquals(set.getConditionSetObject().toString(), "{\"conditions\":[{\"property\":\"testProperty\",\"operator\":\"=\",\"value\":\"10\"}],\"operator\":\"and\"}");

        set = new IGCSearchConditionSet();
        MapPropertyValue map = new MapPropertyValue();
        map.setMapValue("testProperty", value);
        try {
            IGCRepositoryHelper.addIGCSearchConditionFromValue(null,
                    "TestRepo",
                    set,
                    "testProperty",
                    map);
        } catch (FunctionNotSupportedException e) {
            assertNull(e);
        }
        assertEquals(set.size(), 1);
        assertEquals(set.getConditionSetObject().toString(), "{\"conditions\":[{\"property\":\"testProperty\",\"operator\":\"=\",\"value\":\"10\"}],\"operator\":\"and\"}");

        set = new IGCSearchConditionSet();
        ArrayPropertyValue array = new ArrayPropertyValue();
        array.setArrayCount(1);
        array.setArrayValue(0, value);
        try {
            IGCRepositoryHelper.addIGCSearchConditionFromValue(null,
                    "TestRepo",
                    set,
                    "testProperty",
                    array);
        } catch (FunctionNotSupportedException e) {
            assertNull(e);
        }
        assertEquals(set.size(), 1);
        assertEquals(set.getConditionSetObject().toString(), "{\"conditions\":[{\"property\":\"testProperty\",\"operator\":\"=\",\"value\":\"10\"}],\"operator\":\"and\"}");

        set = new IGCSearchConditionSet();
        value = new PrimitivePropertyValue();
        value.setPrimitiveDefCategory(PrimitiveDefCategory.OM_PRIMITIVE_TYPE_DATE);
        value.setPrimitiveValue(10L);
        try {
            IGCRepositoryHelper.addIGCSearchConditionFromValue(null,
                    "TestRepo",
                    set,
                    "testProperty",
                    value);
        } catch (FunctionNotSupportedException e) {
            assertNull(e);
        }
        assertEquals(set.size(), 1);
        assertEquals(set.getConditionSetObject().toString(), "{\"conditions\":[{\"property\":\"testProperty\",\"operator\":\"between\",\"min\":10,\"max\":1009}],\"operator\":\"and\"}");

        set = new IGCSearchConditionSet();
        EnumPropertyValue ev = new EnumPropertyValue();
        ev.setSymbolicName("TestSymbolicName");
        try {
            IGCRepositoryHelper.addIGCSearchConditionFromValue(null,
                    "TestRepo",
                    set,
                    "testProperty",
                    ev);
        } catch (FunctionNotSupportedException e) {
            assertNull(e);
        }
        assertEquals(set.size(), 1);
        assertEquals(set.getConditionSetObject().toString(), "{\"conditions\":[{\"property\":\"testProperty\",\"operator\":\"=\",\"value\":\"TestSymbolicName\"}],\"operator\":\"and\"}");

    }

    @Test
    public void testReferenceHandling() {

        List<Reference> list = new ArrayList<>();
        IGCRepositoryHelper.addReferencesToList(null, list, null);
        assertTrue(list.isEmpty());
        ItemList<Reference> itemList = new ItemList<>();
        List<Reference> items = new ArrayList<>();
        items.add(new Reference("TestName1", "term", "123"));
        items.add(new Reference("TestName2", "term", "456"));
        itemList.setItems(items);
        Paging paging = new Paging();
        paging.setNumTotal(2);
        paging.setPageSize(10);
        itemList.setPaging(paging);
        IGCRepositoryHelper.addReferencesToList(null, list, itemList);
        assertFalse(list.isEmpty());
        assertEquals(list.size(), 2);

    }

}
