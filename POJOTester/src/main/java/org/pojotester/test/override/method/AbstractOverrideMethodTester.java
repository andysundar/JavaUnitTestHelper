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

import org.pojotester.test.AbstractTester;

public abstract class AbstractOverrideMethodTester extends AbstractTester
{

    private Object sameObject1;
    private Object sameObject2;
    private Object sameObject3;
    private Object differentObject;

    public AbstractOverrideMethodTester(Object sameObject1, Object sameObject2, Object sameObject3, Object differentObject) {
        this.differentObject = differentObject;
        this.sameObject1 = sameObject1;
        this.sameObject2 = sameObject2;
        this.sameObject3 = sameObject3;
    }



    public Object getSameObject1() {
        return sameObject1;
    }

    public Object getSameObject2() {
        return sameObject2;
    }

    public Object getSameObject3() {
        return sameObject3;
    }

    public Object getDifferentObject() {
        return differentObject;
    }

}
