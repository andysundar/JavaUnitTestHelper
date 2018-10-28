package org.pojotester.test.values.changer;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.UUID;

public class ValueChangerFactory {

	public <T> ValueChanger<?> getValueChanger(T value, Class<?> type) {
		ValueChanger<?> valueChanger = null;
		if(BigDecimal.class.equals(type)){
			valueChanger = new BigDecimalValueChanger();
		} else if(BigInteger.class.equals(type)) {
			valueChanger = new BigIntegerValueChanger();
		} else if(Boolean.class.equals(type)) {
			valueChanger = new BooleanValueChanger();
		} else if(Byte.class.equals(type)) {
			valueChanger = new ByteValueChanger();
		} else if(Character.class.equals(type)) {
			valueChanger = new CharacterValueChanger();
		} else if(Double.class.equals(type)) {
			valueChanger = new DoubleValueChanger();
		} else if(Float.class.equals(type)) {
			valueChanger = new FloatValueChanger();
		} else if(Integer.class.equals(type)) {
			valueChanger = new IntegerValueChanger();
		} else if(Long.class.equals(type)) {
			valueChanger = new LongValueChanger();
		} else if(Short.class.equals(type)) {
			valueChanger = new ShortValueChanger();
		} else if(Short.class.equals(type)) {
			valueChanger = new ShortValueChanger();
		} else if(String.class.equals(type)) {
			valueChanger = new StringValueChanger();
		} else if(UUID.class.equals(type)) {
			valueChanger = new UUIDValueChanger();
		}
		
		return valueChanger;
	}
}
