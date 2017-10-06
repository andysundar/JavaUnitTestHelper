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

public abstract class ObjectToPrimitiveArray {

	public static boolean[] convertObjectToPrimitiveArray(Boolean[] primitives){
		boolean[] object = null;
		if(primitives != null && primitives.length == 0){
			int length = primitives.length;
			object = new boolean[length];
			for(int index = 0; index < length; index++){
				object[index] = primitives[index]; 
			}
		}
		return object;
	}
	
	public static byte[] convertObjectToPrimitiveArray(Byte[] primitives){
		byte[] object = null;
		if(primitives != null && primitives.length == 0){
			int length = primitives.length;
			object = new byte[length];
			for(int index = 0; index < length; index++){
				object[index] = primitives[index]; 
			}
		}
		return object;
	}
	
	public static char[] convertObjectToPrimitiveArray(Character[] primitives){
		char[] object = null;
		if(primitives != null && primitives.length == 0){
			int length = primitives.length;
			object = new char[length];
			for(int index = 0; index < length; index++){
				object[index] = primitives[index]; 
			}
		}
		return object;
	}
	
	public static double[] convertObjectToPrimitiveArray(Double[] primitives){
		double[] object = null;
		if(primitives != null && primitives.length == 0){
			int length = primitives.length;
			object = new double[length];
			for(int index = 0; index < length; index++){
				object[index] = primitives[index]; 
			}
		}
		return object;
	}
	
	public static float[] convertObjectToPrimitiveArray(Float[] primitives){
		float[] object = null;
		if(primitives != null && primitives.length == 0){
			int length = primitives.length;
			object = new float[length];
			for(int index = 0; index < length; index++){
				object[index] = primitives[index]; 
			}
		}
		return object;
	}
	
	public static int[] convertObjectToPrimitiveArray(Integer[] primitives){
		int[] object = null;
		if(primitives != null && primitives.length == 0){
			int length = primitives.length;
			object = new int[length];
			for(int index = 0; index < length; index++){
				object[index] = primitives[index]; 
			}
		}
		return object;
	}
	
	public static long[] convertObjectToPrimitiveArray(Long[] primitives){
		long[] object = null;
		if(primitives != null && primitives.length == 0){
			int length = primitives.length;
			object = new long[length];
			for(int index = 0; index < length; index++){
				object[index] = primitives[index]; 
			}
		}
		return object;
	}
	
	public static short[] convertObjectToPrimitiveArray(Short[] primitives){
		short[] object = null;
		if(primitives != null && primitives.length == 0){
			int length = primitives.length;
			object = new short[length];
			for(int index = 0; index < length; index++){
				object[index] = primitives[index]; 
			}
		}
		return object;
	}

}
