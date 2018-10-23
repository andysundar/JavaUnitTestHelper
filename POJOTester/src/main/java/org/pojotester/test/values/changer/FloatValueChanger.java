package org.pojotester.test.values.changer;

public class FloatValueChanger implements ValueChanger<Float> {

	@Override
	public Float changedValue(Float value) {
		Float returnValue = -1.0F;
		if(value != null) {
			if(value == Float.MIN_VALUE){
				returnValue = value++;
			} else {
				returnValue = value--;
			}
		}
		return returnValue;
	}
	
}
