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

import java.util.ArrayList;
import java.util.List;

public class HashCodeTester extends AbstractTester {


    public HashCodeTester(Object sameObject1, Object sameObject2, Object sameObject3, Object differentObject) {
        super(sameObject1, sameObject2, sameObject3, differentObject);
    }

    @Override
    public List<AssertObject<?>> createTests() {
        Object sameObject1 = getSameObject1();
        Object sameObject2 = getSameObject2();
        Object sameObject3 = getSameObject3();
        Object differentObject = getDifferentObject();
        
        List<AssertObject<?>> assertObjectList = new ArrayList<>();
        AssertObject<Integer> reflexiveHashCode = createAssertObject(sameObject1.hashCode(), sameObject1.hashCode(),
                "Reflexive Test: x.hashCode() == x.hashCode()");
        assertObjectList.add(reflexiveHashCode);

        AssertObject<Integer> symmetricHashCode = createAssertObject(sameObject1.hashCode(), sameObject2.hashCode(),
                "Symmetric Test: If values of x & y are same then x.hashCode() == y.hashCode()");
        assertObjectList.add(symmetricHashCode);

        AssertObject<Boolean> transitiveHashCode = createAssertObject(
                (sameObject1.hashCode() == sameObject2.hashCode() && sameObject2.hashCode() == sameObject3.hashCode() ),
                (sameObject3.hashCode() == sameObject1.hashCode()),
                "Transitive Test: If values of x & y are same then x.hashCode() == y.hashCode()");
        assertObjectList.add(transitiveHashCode);

        AssertObject<Boolean> consistentHashCode = createAssertObject((sameObject2.hashCode() == differentObject.hashCode()),
                false,
                "Consistent Test: If x1 and x2 different object and different value(s) \n x1.hashCode() !+ x2.hashCode()");
        assertObjectList.add(consistentHashCode);

        return assertObjectList;
    }

}
