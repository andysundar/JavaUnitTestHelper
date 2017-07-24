package org.pojotester.test.values;

public class AssertObject<T> {

	T returnedValue;
	T expectedValue;
	
	public T getExpectedValue() {
		return expectedValue;
	}
	public void setExpectedValue(T expectedValue) {
		this.expectedValue = expectedValue;
	}
	public T getReturnedValue() {
		return returnedValue;
	}
	public void setReturnedValue(T returnedValue) {
		this.returnedValue = returnedValue;
	}
}
