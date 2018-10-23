package org.pojotester.test.values.changer;

public class BooleanValueChanger implements ValueChanger<Boolean> {

	@Override
	public Boolean changedValue(Boolean value) {
		Boolean returnValue = false;
		if(value != null) {
			returnValue = !value;
		}
		return returnValue;
	}

}
