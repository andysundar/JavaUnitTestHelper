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

package org.pojotester.test.values.changer.dto;

import java.lang.reflect.Field;

public class FieldState<T> {
	
	private T previousValue;
	private T currentValue;
	private Field field;
	private Object obj;	
	
	public FieldState(T previousValue, T currentValue, Field field, Object obj) {
		this.previousValue = previousValue;
		this.currentValue = currentValue;
		this.field = field;
		this.obj = obj;
	}
	
	public T getPreviousValue() {
		return previousValue;
	}
	
	public T getCurrentValue() {
		return currentValue;
	}

	public Field getField() {
		return field;
	}

	public Object getObj() {
		return obj;
	}

}
