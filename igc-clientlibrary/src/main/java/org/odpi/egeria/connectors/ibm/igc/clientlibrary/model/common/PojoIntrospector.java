/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common;

import java.lang.invoke.*;
import java.lang.reflect.Method;
import java.util.List;
import java.util.function.Supplier;

/**
 * Faster, non-reflection approach for dynamically retrieving static details from the underlying Jackson POJOs.
 * Will need to instantiate one of these readers for each POJO (and best to cache them as they are used to avoid
 * re-bootstrapping when they'll be used multiple times)
 *
 * Based on simple example at:
 * https://github.com/ge0ffrey/ge0ffrey-presentations/blob/master/code/fasterreflection/fasterreflection-framework/src/main/java/be/ge0ffrey/presentations/fasterreflection/framework/LambdaMetafactoryBeanPropertyReader.java
 */
public class PojoIntrospector implements PojoIntrospection {

    private final Supplier canBeCreatedFunction;
    private final Supplier includesModificationDetailsFunction;
    private final Supplier getIgcTypeDisplayNameFunction;
    private final Supplier getNonRelationshipPropertiesFunction;
    private final Supplier getStringPropertiesFunction;
    private final Supplier getAllPropertiesFunction;
    private final Supplier getPagedRelationshipPropertiesFunction;

    public PojoIntrospector(Class<?> pojoClass) {

        try {
            canBeCreatedFunction = (Supplier) getCallSiteByName(pojoClass, "canBeCreated").getTarget().invokeExact();
            includesModificationDetailsFunction = (Supplier) getCallSiteByName(pojoClass, "includesModificationDetails").getTarget().invokeExact();
            getIgcTypeDisplayNameFunction = (Supplier) getCallSiteByName(pojoClass, "getIgcTypeDisplayName").getTarget().invokeExact();
            getNonRelationshipPropertiesFunction = (Supplier) getCallSiteByName(pojoClass, "getNonRelationshipProperties").getTarget().invokeExact();
            getStringPropertiesFunction = (Supplier) getCallSiteByName(pojoClass, "getStringProperties").getTarget().invokeExact();
            getAllPropertiesFunction = (Supplier) getCallSiteByName(pojoClass, "getAllProperties").getTarget().invokeExact();
            getPagedRelationshipPropertiesFunction = (Supplier) getCallSiteByName(pojoClass, "getPagedRelationshipProperties").getTarget().invokeExact();
        } catch (Throwable e) {
            throw new IllegalArgumentException("Lambda creation failed.", e);
        }
    }

    private CallSite getCallSiteByName(Class pojoClass, String methodName) {
        Method method;
        try {
            method = pojoClass.getMethod(methodName);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("The class (" + pojoClass + ") doesn't have the method (" + methodName + ").", e);
        }
        Class<?> returnType = method.getReturnType();

        MethodHandles.Lookup lookup = MethodHandles.lookup();
        CallSite site;
        try {
            site = LambdaMetafactory.metafactory(lookup,
                    "get",
                    MethodType.methodType(Supplier.class),
                    MethodType.methodType(Object.class),
                    lookup.findStatic(pojoClass, methodName, MethodType.methodType(returnType)),
                    MethodType.methodType(returnType));
        } catch (LambdaConversionException | NoSuchMethodException | IllegalAccessException e) {
            throw new IllegalArgumentException("Lambda creation failed for method (" + methodName + ").", e);
        }
        return site;
    }

    public Boolean canBeCreated() { return (Boolean) canBeCreatedFunction.get(); }
    public Boolean includesModificationDetails() { return (Boolean) includesModificationDetailsFunction.get(); }
    public String getIgcTypeDisplayName() { return (String) getIgcTypeDisplayNameFunction.get(); }
    public List<String> getNonRelationshipProperties() { return (List<String>) getNonRelationshipPropertiesFunction.get(); }
    public List<String> getStringProperties() { return (List<String>) getStringPropertiesFunction.get(); }
    public List<String> getAllProperties() { return (List<String>) getAllPropertiesFunction.get(); }
    public List<String> getPagedRelationshipProperties() { return (List<String>) getPagedRelationshipPropertiesFunction.get(); }

}
