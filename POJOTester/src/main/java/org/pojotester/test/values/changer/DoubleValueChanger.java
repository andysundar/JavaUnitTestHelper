package org.pojotester.test.values.changer;

public class DoubleValueChanger implements ValueChanger<Double> {

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
