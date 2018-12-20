package org.pojotester.test.constructor;

import org.pojotester.test.AbstractTester;
import org.pojotester.test.values.AssertObject;
import org.pojotester.utils.ClassUtilities;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConstructorsTester  extends AbstractTester {

    private Class<?> clazz;

    private ConstructorsTester(final Class<?> clazz) {
            this.clazz = clazz;
    }

    @Override
    public List<AssertObject<?>> createTests() {
        List<AssertObject<?>> assertObjectList = new ArrayList<>();
        Object[] objects = ClassUtilities.createObjectsUsingAllConstructors(clazz);

        for(Object obj : objects) {
            AssertObject<Boolean> objectNotEqualsToNull = createAssertObject((obj != null), true,
                    "Fail to create object using constructor.");
            assertObjectList.add(objectNotEqualsToNull);
        }
        return assertObjectList;
    }

    public static Optional<ConstructorsTester> createIfClassHaveMoreConstructors(final Class<?> clazz,
                                                                                 final boolean testAllConstructors) {
        Optional<ConstructorsTester> optionalObject = Optional.empty();
        if(testAllConstructors) {
            Constructor<?>[] constructors = clazz.getConstructors();
            if(constructors.length > 1) {
            	optionalObject = Optional.of(new ConstructorsTester(clazz));
            }
        }

        return optionalObject;
    }
}
