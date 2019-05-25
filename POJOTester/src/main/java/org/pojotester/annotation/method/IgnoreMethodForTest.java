package org.pojotester.annotation.method;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * {@code IgnoreMethodForTest} is an annotation that is used to ignore the annotated toString/hashCode/equals method(s) only.
 * 
 * @author Anindya Bandopadhyay
 * @since 1.0.4
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD})
public @interface IgnoreMethodForTest {

}
