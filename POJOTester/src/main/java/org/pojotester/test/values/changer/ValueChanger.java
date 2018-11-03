package org.pojotester.test.values.changer;

interface ValueChanger<T> {
	
	T changedValue(T value);

}
