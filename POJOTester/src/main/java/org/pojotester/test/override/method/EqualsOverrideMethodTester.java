/*******************************************************************************
 * Copyright 2017 Anindya Bandopadhyay (anindyabandopadhyay@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package org.pojotester.test.override.method;

import org.pojotester.test.values.AssertObject;
import org.pojotester.test.values.changer.FieldValueChanger;
import org.pojotester.test.values.changer.dto.FieldState;
import org.pojotester.utils.FieldUtilities;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class EqualsOverrideMethodTester extends AbstractOverrideMethodTester {

    private List<Field> fieldList;

    public EqualsOverrideMethodTester(Object sameObject1, Object sameObject2,
                                      Object sameObject3, Object differentObject,
                                      List<Field> fieldList) {
        super(sameObject1, sameObject2, sameObject3, differentObject);
        this.fieldList = fieldList;
    }

    @Override
	public List<AssertObject<?>> createTests() {
		Object sameObject1 = getSameObject1();
		Object sameObject2 = getSameObject2();
		Object sameObject3 = getSameObject3();
		Object differentObject = getDifferentObject();

		List<AssertObject<?>> assertObjectList = new ArrayList<>();

		// object.equals(null) should return false
		AssertObject<Boolean> objectEqualsNull = createAssertObject(sameObject1.equals(null), false,
				String.format("%s object.equals(null) must return false", sameObject1.getClass()));
		assertObjectList.add(objectEqualsNull);

		// object.equals(objectEqualsOtherObject) should return false
		AssertObject<Boolean> objectEqualsOtherObject = createAssertObject(sameObject1.equals(new Object()), false,
				String.format("%s object.equals(otherObject) must return false", sameObject1.getClass()));
		assertObjectList.add(objectEqualsOtherObject);

		// Reflexive
		AssertObject<Boolean> reflexiveEquals = createAssertObject(sameObject1.equals(sameObject1), true,
				String.format("%s Reflexive Test: x.equals(x) return true.", sameObject1.getClass()));
		assertObjectList.add(reflexiveEquals);

		// Symmetric
		AssertObject<Boolean> symmetricEquals = createAssertObject(sameObject1.equals(sameObject2),
				sameObject2.equals(sameObject1),
				String.format("%s Symmetric Test: x.equals(y) == y.equals(x)", sameObject1.getClass()));
		assertObjectList.add(symmetricEquals);

		// Transitive
		AssertObject<Boolean> transitiveEquals = createAssertObject(
				sameObject1.equals(sameObject2) && sameObject2.equals(sameObject3), sameObject3.equals(sameObject1),
				String.format(
						"%s Transitive Test: if x.equals(y) return true and y.equals(z) return true then \n z.equals(x) also return true",
						sameObject1.getClass()));
		assertObjectList.add(transitiveEquals);

		// Consistent
		AssertObject<Boolean> consistentEquals = createAssertObject(sameObject1.equals(differentObject),
				differentObject.equals(sameObject1),
				String.format(
						"%s Consistent Test: If x1 and x2 different object and different value(s) \n"
								+ " x1.equals(x2) return false and x2.equals(x1) also return false.",
						sameObject1.getClass()));
		assertObjectList.add(consistentEquals);

		// Equals coverage

		FieldValueChanger fieldValueChanger = FieldValueChanger.getInstance();

		for (Field field : fieldList) {

			fieldValueChanger.changeValue(field, sameObject2);
			AssertObject<Boolean> coverageEquals = createAssertObject(sameObject1.equals(sameObject2),
					sameObject2.equals(sameObject1),
					String.format("%s Symmetric Test: x.equals(y) == y.equals(x)", sameObject1.getClass()));
			assertObjectList.add(coverageEquals);

			FieldState<?> fieldSate = fieldValueChanger.changeValue(field, sameObject1);
			FieldUtilities.setFieldValue(field, sameObject1, fieldSate.getCurrentValue());
			FieldUtilities.setFieldValue(field, sameObject2, fieldSate.getCurrentValue());
		}

		AssertObject<Boolean> coverageEquals = createAssertObject(sameObject1.equals(sameObject2),
				sameObject2.equals(sameObject1),
				String.format("%s Symmetric Test: x.equals(y) == y.equals(x)", sameObject1.getClass()));
		assertObjectList.add(coverageEquals);

		return assertObjectList;
	}
}
