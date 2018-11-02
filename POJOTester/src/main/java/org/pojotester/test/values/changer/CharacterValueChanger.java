package org.pojotester.test.values.changer;

class CharacterValueChanger extends AbstractFieldValueChanger<Character> {

	@Override
	public Character changedValue(Character value) {
		Character returnValue =  'Z'; 
		if(value != null) {
			if(value == Character.MIN_VALUE){
				returnValue = value++;
			} else {
				returnValue = value--;
			}
		}
		return returnValue;
	}

}
