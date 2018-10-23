package org.pojotester.test.values.changer;

public class LongValueChanger implements ValueChanger<Long> {

	@Override
	public Long changedValue(Long value) {
		Long returnValue =  1L; 
		if(value != null) {
			if(value == Long.MIN_VALUE){
				returnValue = value++;
			} else {
				returnValue = value--;
			}
		}
		return returnValue;
	}

}
