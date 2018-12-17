package org.pojotester.test;

import org.pojotester.test.values.AssertObject;

import java.util.List;

public abstract class AbstractTester {

    public abstract List<AssertObject<?>> createTests();

    protected <T> AssertObject<T> createAssertObject(T returnedValue, T expectedValue, String message) {
        AssertObject<T> assertObject = new AssertObject<>();
        assertObject.setMessage(message);
        assertObject.setReturnedValue(returnedValue);
        assertObject.setExpectedValue(expectedValue);
        return assertObject;
    }
}
