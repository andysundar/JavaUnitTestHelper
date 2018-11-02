package org.pojotester.test.values.changer;

class BooleanValueChanger extends AbstractFieldValueChanger<Boolean> {

	@Override
	public Boolean changedValue(Boolean value) {
		Boolean returnValue = false;
		if(value != null) {
			returnValue = !value;
		}
		return returnValue;
	}

}
