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

package org.pojotester.test.overriden.method;

import org.pojotester.test.values.AssertObject;

import java.util.ArrayList;
import java.util.List;

public class ToStringTester extends AbstractTester {

    public ToStringTester(Object sameObject1, Object sameObject2, Object sameObject3, Object differentObject) {
        super(sameObject1, sameObject2, sameObject3, differentObject);
    }

    @Override
    public List<AssertObject<?>> createTests() {
        Object sameObject1 = getSameObject1();
        Object sameObject2 = getSameObject2();

        Object differentObject = getDifferentObject();

        List<AssertObject<?>> assertObjectList = new ArrayList<>();
        String x1Str = sameObject1.toString();
        String x2Str = sameObject2.toString();
        AssertObject<String> whenBothSameValues = createAssertObject(x1Str, x2Str,
                "toString method when different objects but same value(s).");
        assertObjectList.add(whenBothSameValues);

        String y1Str = sameObject1.toString();
        String y2Str = differentObject.toString();
        AssertObject<Boolean> whenDifferentValues = createAssertObject(y2Str.equals(y1Str), false,
                "toString method when different objects and different value(s).");
        assertObjectList.add(whenDifferentValues);

        return assertObjectList;
    }
}
