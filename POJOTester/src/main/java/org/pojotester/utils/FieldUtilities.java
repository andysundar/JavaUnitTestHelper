package org.pojotester.utils;

import java.lang.reflect.Field;

public abstract class FieldUtilities {

    public static Object getFieldValue(Field field, Object object) {
        makeAccessToField(field);
        Object value = null;
        try {
            value = field.get(object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return value;
    }

    public static void setFieldValue(Field field, Object object, Object value) {
        makeAccessToField(field);
        try {
            field.set(object, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void makeAccessToField(Field field) {
        field.setAccessible(true);
    }

    public static Class<?> getFieldType(Field field) {
        return field.getType();
    }
}
