/*******************************************************************************
 * Copyright 2017 Anindya Bandopadhyay (anindyabandopadhyay@gmail.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package org.pojotester.type.convertor;

/**
 * This is an utility class which convert primitive type array object value into wrapper object type array.
 * @author Anindya Bandopadhyay
 * @since 1.0
 */
public abstract class PrimitiveToObjectArray {

	public static Boolean[] convertPrimitiveToObjectArray(boolean... primitives){
		Boolean[] object = null;
		if(primitives != null && primitives.length != 0){
			int length = primitives.length;
			object = new Boolean[length];
			for(int index = 0; index < length; index++){
				object[index] = primitives[index]; 
			}
		}
		return object;
	}
	
	public static Byte[] convertPrimitiveToObjectArray(byte... primitives){
		Byte[] object = null;
		if(primitives != null && primitives.length != 0){
			int length = primitives.length;
			object = new Byte[length];
			for(int index = 0; index < length; index++){
				object[index] = primitives[index]; 
			}
		}
		return object;
	}
	
	public static Character[] convertPrimitiveToObjectArray(char... primitives){
		Character[] object = null;
		if(primitives != null && primitives.length != 0){
			int length = primitives.length;
			object = new Character[length];
			for(int index = 0; index < length; index++){
				object[index] = primitives[index]; 
			}
		}
		return object;
	}
	
	public static Double[] convertPrimitiveToObjectArray(double... primitives){
		Double[] object = null;
		if(primitives != null && primitives.length != 0){
			int length = primitives.length;
			object = new Double[length];
			for(int index = 0; index < length; index++){
				object[index] = primitives[index]; 
			}
		}
		return object;
	}
	
	public static Float[] convertPrimitiveToObjectArray(float... primitives){
		Float[] object = null;
		if(primitives != null && primitives.length != 0){
			int length = primitives.length;
			object = new Float[length];
			for(int index = 0; index < length; index++){
				object[index] = primitives[index]; 
			}
		}
		return object;
	}
	
	public static Integer[] convertPrimitiveToObjectArray(int... primitives){
		Integer[] object = null;
		if(primitives != null && primitives.length != 0){
			int length = primitives.length;
			object = new Integer[length];
			for(int index = 0; index < length; index++){
				object[index] = primitives[index]; 
			}
		}
		return object;
	}
	
	public static Long[] convertPrimitiveToObjectArray(long... primitives){
		Long[] object = null;
		if(primitives != null && primitives.length != 0){
			int length = primitives.length;
			object = new Long[length];
			for(int index = 0; index < length; index++){
				object[index] = primitives[index]; 
			}
		}
		return object;
	}
	
	public static Short[] convertPrimitiveToObjectArray(short... primitives){
		Short[] object = null;
		if(primitives != null && primitives.length != 0){
			int length = primitives.length;
			object = new Short[length];
			for(int index = 0; index < length; index++){
				object[index] = primitives[index];  
			}
		}
		return object;
	}
}
