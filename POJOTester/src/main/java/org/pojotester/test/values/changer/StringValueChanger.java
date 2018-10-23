package org.pojotester.test.values.changer;

public class StringValueChanger implements ValueChanger<String> {

	@Override
	public String changedValue(String value) {
		String returnValue = "";
		if(value!= null) {
			returnValue = value + "new";
		}
		return returnValue;
	}

}