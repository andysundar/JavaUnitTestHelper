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

package org.pojotester.test.values;

/**
 * This class hold the value of field return value and expected result value.
 * @author Anindya Bandopadhyay
 * @since 1.0
 * @param <T> field data type
 */
public class AssertObject<T> {
	private String message;
	
	private T returnedValue;
	private T expectedValue;
	
	public T getExpectedValue() {
		return expectedValue;
	}
	public void setExpectedValue(T expectedValue) {
		this.expectedValue = expectedValue;
	}
	public T getReturnedValue() {
		return returnedValue;
	}
	public void setReturnedValue(T returnedValue) {
		this.returnedValue = returnedValue;
	}
	/**
	 * This method is replaced by {@code getMessage}
	 * @return field or method name 
	 */
	@Deprecated
	public String getClassFieldName() {
		return message;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String classFieldName) {
		this.message = classFieldName;
	}
}
