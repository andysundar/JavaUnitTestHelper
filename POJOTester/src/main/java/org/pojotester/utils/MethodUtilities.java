/*******************************************************************************
 * Copyright 2017 Anindya Bandopadhyay (anindyabandopadhyay@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package org.pojotester.utils;

import org.pojotester.annotation.method.CreateObjectMethod;
import org.pojotester.reflection.annotation.ReflectionMethodLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * This class provide method level reflection utilities like invoking method to get/set value
 * and find an annotated {@link CreateObjectMethod} method.
 * @author Anindya Bandopadhyay
 * @since 1.1
 */

public abstract class MethodUtilities {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodUtilities.class);

    /**
     * This method will invoke using reflection.
     * @param method method {@link Method} object
     * @param obj on which method will be called , null if static method need to be invoked
     * @param args method parameters as Object[]
     * @return if the method return anything then that value will be returned else null.
     * @since 1.1
     */
    public static Object methodInvocation(Method method, Object obj, Object...args) {
        Object returnObj = null;

        if(method != null) {
            try {
                returnObj = method.invoke(obj, args);
            } catch (IllegalAccessException | InvocationTargetException e) {
                String message = String.format("%s cannot be invoked", method.getName());
                LOGGER.debug(message, e);
            }
        }

        return returnObj;
    }

    /**
     * This method will return a method object if annotated
     * {@link CreateObjectMethod} method found else null.
     * @param methods list of methods in a class.
     * @return method object if annotated {@link CreateObjectMethod} method found else null.
     * @since 1.1
     */
    public static Method findCreateObjectMethod(Method[] methods) {
        Method createObjectMethod = null;
        for (Method method : methods) {
            if (ReflectionMethodLevel.isCreateMethod(method)) {
                createObjectMethod = method;
                break;
            }
        }
        return createObjectMethod;
    }

}
