package org.pojotester.test.values.changer;

class CharacterValueChanger implements ValueChanger<Character> {

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
