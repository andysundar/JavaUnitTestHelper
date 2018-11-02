package org.pojotester.test.values.changer;

class IntegerValueChanger extends AbstractFieldValueChanger<Integer> {

	@Override
	public Integer changedValue(Integer value) {
		Integer returnValue = -1;
		if(value != null) {
			if(value.intValue() == Integer.MIN_VALUE) {
				returnValue = value++;
			} else  {
				returnValue = value--;
			}
		}
		return returnValue;
	}

}
