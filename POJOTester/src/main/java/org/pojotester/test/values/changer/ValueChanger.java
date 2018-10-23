package org.pojotester.test.values.changer;

public interface ValueChanger<T> {

	T changedValue(T value);

}
