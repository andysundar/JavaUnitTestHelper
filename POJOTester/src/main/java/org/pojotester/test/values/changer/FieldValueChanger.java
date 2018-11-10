package org.pojotester.test.values.changer;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.pojotester.test.values.changer.dto.FieldState;
import org.pojotester.utils.ClassUtilities;
import org.pojotester.utils.FieldUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FieldValueChanger  {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FieldValueChanger.class);
	
	private static final Map<Class<?>,ValueChanger<?>> CLASS_VALUE_CHANGER_MAP = new HashMap<>();
	
	private static final transient FieldValueChanger INSTANCE = new FieldValueChanger();
	
	static {
		CLASS_VALUE_CHANGER_MAP.put(BigDecimal.class, new BigDecimalValueChanger());
		CLASS_VALUE_CHANGER_MAP.put(BigInteger.class, new BigIntegerValueChanger());
		
		CLASS_VALUE_CHANGER_MAP.put(Boolean.class, new BooleanValueChanger());
		CLASS_VALUE_CHANGER_MAP.put(Byte.class, new ByteValueChanger());
		CLASS_VALUE_CHANGER_MAP.put(Character.class, new CharacterValueChanger());
		CLASS_VALUE_CHANGER_MAP.put(Double.class, new DoubleValueChanger());
		CLASS_VALUE_CHANGER_MAP.put(Float.class, new FloatValueChanger());
		CLASS_VALUE_CHANGER_MAP.put(Integer.class, new IntegerValueChanger());
		CLASS_VALUE_CHANGER_MAP.put(Long.class, new LongValueChanger());
		CLASS_VALUE_CHANGER_MAP.put(Short.class, new ShortValueChanger());
		
		CLASS_VALUE_CHANGER_MAP.put(String.class, new StringValueChanger());
		CLASS_VALUE_CHANGER_MAP.put(UUID.class, new UUIDValueChanger());

	}

	private FieldValueChanger(){
		
	}
	
	public <T> FieldState<T> changeValue(Field field, Object obj)
			throws IllegalArgumentException {
		FieldState<T> fieldState = null;
		if (field != null) {
			Class<?> type = FieldUtilities.getFieldType(field);

			@SuppressWarnings("unchecked")
			T value = (T) FieldUtilities.getFieldValue(field, obj);

			@SuppressWarnings("unchecked")
			ValueChanger<T> valueChanger = (ValueChanger<T>) CLASS_VALUE_CHANGER_MAP.get(type);

			if (valueChanger != null) {
				T newValue = valueChanger.changedValue(value);
				fieldState = new FieldState<>(value, newValue, field, obj);
			} else if (type.isArray()) {
				int arraySize = 0;
				int dimension = 1 + type.getName().lastIndexOf('[');
				int[] length = new int[dimension];

				if (value != null) {
					if (Array.getLength(value) == 0) {
						arraySize++;
					}
					for (int index = 0; index < dimension; index++) {
						length[index] = arraySize;
					}
				}
				@SuppressWarnings("unchecked")
				T newValue = (T)Array.newInstance(type.getComponentType(), length);
				fieldState = new FieldState<>(value, newValue, field, obj);
			} else if (type.isEnum()) {
				T newValue = null;

				@SuppressWarnings("unchecked")
				T[] constants = (T[]) type.getEnumConstants();
				if (constants.length == 0 || constants.length == 1) {
					LOGGER.debug(String.format("Enum value cannot be changed due to no/no other element to change. %s",
							constants.length));
				}
				for (T constant : constants) {
					if (!value.equals(constant)) {
						newValue = constant;
						break;
					}
				}

				fieldState = new FieldState<T>(value, newValue, field, obj);
			} else {
				@SuppressWarnings("unchecked")
				T newValue = (T) ClassUtilities.createObject(type);
				fieldState = new FieldState<>(value, newValue, field, obj);
			}
		}
		return fieldState;
	}


	public static FieldValueChanger getInstance() {
		return INSTANCE;
	}
}
