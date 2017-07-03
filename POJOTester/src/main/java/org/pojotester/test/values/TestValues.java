package org.pojotester.test.values;

public class TestValues<T> {

	private T[] assignedValues;
	private T[] expectedValues;
	
	public T[] getAssignedValues() {
		return assignedValues;
	}
	public void setAssignedValues(T[] assignedValues) {
		this.assignedValues = assignedValues;
	}
	public T[] getExpectedValues() {
		return expectedValues;
	}
	public void setExpectedValues(T[] expectedValues) {
		this.expectedValues = expectedValues;
	}
	
	
}
