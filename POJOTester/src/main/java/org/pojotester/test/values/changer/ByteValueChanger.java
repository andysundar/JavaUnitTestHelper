package org.pojotester.test.values.changer;

class ByteValueChanger extends AbstractFieldValueChanger<Byte> {

	@Override
	public Byte changedValue(Byte value) {
		Byte returnValue = -1;
		if(value != null) {
			if(value == Byte.MIN_VALUE){
				returnValue = value++;
			} else {
				returnValue = value--;
			}
		}
		return returnValue;
	}

}
