package org.pojotester.test.values.changer;

interface ValueChanger<T> extends FieldValueChanger<T>{
	
	T changedValue(T value);

}
