package org.pojotester.test.values.changer;

class LongValueChanger extends AbstractFieldValueChanger<Long> {

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
