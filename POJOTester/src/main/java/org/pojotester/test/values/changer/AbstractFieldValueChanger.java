package org.pojotester.test.values.changer;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.pojotester.test.AssertObjectCreator;
import org.pojotester.test.values.changer.dto.FieldState;
import org.pojotester.utils.ClassUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

abstract class AbstractFieldValueChanger<T> implements ValueChanger<T> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractFieldValueChanger.class);
	
	private static final Map<Class<?>,ValueChanger<?>> CLASS_VALUE_CHANGER_MAP = new HashMap<>();
	
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
	
	public abstract T changedValue(T value);
	
	@Override
	public FieldState<T> changerValue(Field field, Object obj) throws IllegalArgumentException, IllegalAccessException {
		FieldState<T> fieldState = null;
		if(field != null ) {
			Class<?> type = field.getType();
			field.setAccessible(true);
			@SuppressWarnings("unchecked")
			T value = (T) field.get(obj);
			
			@SuppressWarnings("unchecked")
			ValueChanger<T> valueChanger = (ValueChanger<T>) CLASS_VALUE_CHANGER_MAP.get(type);
			
			if(valueChanger != null) {
				T newValue = valueChanger.changedValue(value);
				fieldState = new FieldState<T>(value, newValue, field, obj);
			} else if(type.isArray()) {
				int size = 0;
				int dimention = 1 + type.getName().lastIndexOf('[');
				if (value != null) {
					if (Array.getLength(value) > 0) {
						Array.newInstance(type, size);
					} else {
						size++;
						Array.newInstance(type, size);
					}
				}
			} else if (type.isEnum()) {
				T newValue = null;

				T[] constants = (T[]) type.getEnumConstants();
				if(constants.length == 0 || constants.length == 1){
					LOGGER.debug(String.format("Enum value cannot be changed due to no/no other element to change. %s", constants.length));
				}
				for(T constant : constants) {
					if(!value.equals(constant)) {
						newValue = constant;
						break;
					}
				}
			
				fieldState = new FieldState<T>(value, newValue, field, obj);
			} else {
				T newValue = (T)ClassUtilities.createObject(type);
				fieldState = new FieldState<T>(value, newValue, field, obj);
			}
		}
		return fieldState;
	}


}
