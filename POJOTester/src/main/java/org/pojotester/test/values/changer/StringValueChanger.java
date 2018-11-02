package org.pojotester.test.values.changer;

class StringValueChanger extends AbstractFieldValueChanger<String> {

	@Override
	public String changedValue(String value) {
		String returnValue = "";
		if(value!= null) {
			returnValue = value + "new";
		}
		return returnValue;
	}

}
