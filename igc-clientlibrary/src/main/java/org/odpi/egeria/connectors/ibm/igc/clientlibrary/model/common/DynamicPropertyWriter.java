/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestConstants;

import java.lang.invoke.*;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.function.BiConsumer;

/**
 * Faster, non-reflection approach for dynamically retrieving properties by name from the underlying Jackson POJOs.
 * Will need to instantiate one of these readers for every property * POJO combination (and best to cache them as they
 * are used to avoid re-bootstrapping when they'll be used multiple times)
 *
 * Note that this is only currently used by modification details, so looks up only based on 'modified_on', 'created_on',
 * 'modified_by' and 'created_by' -- no other properties are likely to work (more likely to result in an exception).
 *
 * Based on simple example at:
 * https://github.com/ge0ffrey/ge0ffrey-presentations/blob/master/code/fasterreflection/fasterreflection-framework/src/main/java/be/ge0ffrey/presentations/fasterreflection/framework/LambdaMetafactoryBeanPropertyReader.java
 */
public class DynamicPropertyWriter implements DynamicPropertySetter {

    private final BiConsumer setterFunction;

    public DynamicPropertyWriter(Class<?> pojoClass, String propertyName) {

        String setterName = IGCRestConstants.getSetterNameForProperty(propertyName);
        Method setterMethod;
        Class parameterType = null;
        try {
            if (propertyName.endsWith("by")) {
                parameterType = String.class;
            } else if (propertyName.endsWith("on")) {
                parameterType = Date.class;
            }
            setterMethod = pojoClass.getMethod(setterName, parameterType);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("The class (" + pojoClass + ") has doesn't have the setter method (" + setterName + ").", e);
        }

        MethodHandles.Lookup lookup = MethodHandles.lookup();
        CallSite site;
        try {
            site = LambdaMetafactory.metafactory(lookup,
                    "accept",
                    MethodType.methodType(BiConsumer.class),
                    MethodType.methodType(void.class, Object.class, Object.class),
                    lookup.findVirtual(pojoClass, setterName, MethodType.methodType(void.class, parameterType)),
                    MethodType.methodType(void.class, pojoClass, parameterType));
        } catch (LambdaConversionException | NoSuchMethodException | IllegalAccessException e) {
            throw new IllegalArgumentException("Lambda creation failed for method (" + setterMethod + ").", e);
        }
        try {
            setterFunction = (BiConsumer) site.getTarget().invokeExact();
        } catch (Throwable e) {
            throw new IllegalArgumentException("Lambda creation failed for method (" + setterMethod + ").", e);
        }
    }

    public void setProperty(Object pojo, Object value) { setterFunction.accept(pojo, value); }

}
