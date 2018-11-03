package org.pojotester.test.values.changer;

import java.lang.reflect.Field;

import org.pojotester.test.values.changer.dto.FieldState;

public interface FieldValueChanger {

	<T> FieldState<T> changeValue(Field field, Object obj) throws IllegalArgumentException, IllegalAccessException;

}
