package org.pojotester.utils;

import org.pojotester.reflection.annotation.ReflectionMethodLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class MethodUtilities {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodUtilities.class);


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
