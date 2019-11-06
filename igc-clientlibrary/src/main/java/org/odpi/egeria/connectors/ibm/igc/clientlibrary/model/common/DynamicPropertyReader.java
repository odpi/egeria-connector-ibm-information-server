/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestConstants;

import java.lang.invoke.*;
import java.lang.reflect.Method;
import java.util.function.Function;

/**
 * Faster, non-reflection approach for dynamically retrieving properties by name from the underlying Jackson POJOs.
 * Will need to instantiate one of these readers for every property * POJO combination (and best to cache them as they
 * are used to avoid re-bootstrapping when they'll be used multiple times)
 *
 * Based on simple example at:
 * https://github.com/ge0ffrey/ge0ffrey-presentations/blob/master/code/fasterreflection/fasterreflection-framework/src/main/java/be/ge0ffrey/presentations/fasterreflection/framework/LambdaMetafactoryBeanPropertyReader.java
 */
public class DynamicPropertyReader implements DynamicPropertyGetter {

    private final Function getterFunction;

    public DynamicPropertyReader(Class<?> pojoClass, String propertyName) {

        String getterName = IGCRestConstants.getGetterNameForProperty(propertyName);
        Method getterMethod;
        try {
            getterMethod = pojoClass.getMethod(getterName);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("The class (" + pojoClass + ") has doesn't have the getter method (" + getterName + ").", e);
        }
        Class<?> returnType = getterMethod.getReturnType();

        MethodHandles.Lookup lookup = MethodHandles.lookup();
        CallSite site;
        try {
            site = LambdaMetafactory.metafactory(lookup,
                    "apply",
                    MethodType.methodType(Function.class),
                    MethodType.methodType(Object.class, Object.class),
                    lookup.findVirtual(pojoClass, getterName, MethodType.methodType(returnType)),
                    MethodType.methodType(returnType, pojoClass));
        } catch (LambdaConversionException | NoSuchMethodException | IllegalAccessException e) {
            throw new IllegalArgumentException("Lambda creation failed for method (" + getterMethod + ").", e);
        }
        try {
            getterFunction = (Function) site.getTarget().invokeExact();
        } catch (Throwable e) {
            throw new IllegalArgumentException("Lambda creation failed for method (" + getterMethod + ").", e);
        }
    }

    public Object getProperty(Object pojo) {
        return getterFunction.apply(pojo);
    }

}
