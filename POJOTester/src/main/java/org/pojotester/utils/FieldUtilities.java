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

import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class provide field level reflection utilities like getting/setting field value ,
 * making field accessible for use and get field type.
 * @author Anindya Bandopadhyay
 * @since 1.0.2
 */
public abstract class FieldUtilities {

	private static final Logger LOGGER = LoggerFactory.getLogger(FieldUtilities.class);

    /**
     * Get the value from the field directly.
     * @param field java field object
     * @param object of the class which contains the field.
     * @return object value
     *
     * @since 1.0.2
     */
    public static Object getFieldValue(Field field, Object object) {
        makeAccessToField(field);
        Object value = null;
        try {
            value = field.get(object);
        } catch (IllegalAccessException e) {
        	LOGGER.debug(String.format("Not able to read value from the field %s", field.getName()), e);
        }

        return value;
    }

    /**
     * Set the value into the field object directly.
     * @param field java field object
     * @param object of the class which contains the field.
     * @param value to be set
     *
     * @since 1.0.2
     */
    public static void setFieldValue(Field field, Object object, Object value) {
        makeAccessToField(field);
        try {
            field.set(object, value);
        } catch (IllegalAccessException e) {
        	LOGGER.debug(String.format("Not able to write value from the field %s", field.getName()), e);
        }
    }

    /**
     * Make a field accessible for reflection get/set operations.
     * @param field {@code Field} class.
     *
     * @since 1.0.2
     */
    public static void makeAccessToField(Field field) {
        field.setAccessible(true);
    }

    /**
     * Return the class type of the field.
     * @param field java field object
     * @return class type of the field.
     *
     * @since 1.0.2
     */
    public static Class<?> getFieldType(Field field) {
        return field.getType();
    }
}
