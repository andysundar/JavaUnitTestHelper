package org.pojotester.test.values.changer;

public class ShortValueChanger implements ValueChanger<Short> {

	@Override
	public Short changedValue(Short value) {
		Short returnValue = (short) 1;
		if(value != null) {
			if(value == Short.MIN_VALUE){
				returnValue = value++;
			} else {
				returnValue = value--;
			}
		}
		return returnValue;
	}

}
