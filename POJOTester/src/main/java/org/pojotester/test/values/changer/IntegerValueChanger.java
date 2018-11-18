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

package org.pojotester.test.values.changer;

class IntegerValueChanger implements ValueChanger<Integer> {

	@Override
	public Integer changedValue(Integer value) {
		Integer returnValue = -1;
		if(value != null) {
			if(value.intValue() == Integer.MIN_VALUE) {
				returnValue = value++;
			} else  {
				returnValue = value--;
			}
		}
		return returnValue;
	}

}
