package org.pojotester.annotation.fieldmethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD, ElementType.METHOD})
public @interface DoubleTestValue {
	double[] assignValues() default {Double.MAX_VALUE};
	double[] expectedValues();
}
