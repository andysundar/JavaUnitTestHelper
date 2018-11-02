package org.pojotester.test.values.changer;

class DoubleValueChanger extends AbstractFieldValueChanger<Double> {

	@Override
	public Double changedValue(Double value) {
		Double returnValue = -1.0;
		if(value != null) {
			if(value == Double.MIN_VALUE){
				returnValue = value++;
			} else {
				returnValue = value--;
			}
		}
		return returnValue;
	}

}
