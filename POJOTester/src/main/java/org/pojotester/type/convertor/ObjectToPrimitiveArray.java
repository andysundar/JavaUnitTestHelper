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
 * This is an utility class which convert wrapper type array object value into primitive type array.
 * @author Anindya Bandopadhyay
 * @since 1.0
 */
public abstract class ObjectToPrimitiveArray {

	public static boolean[] convertObjectToPrimitiveArray(Boolean[] objects){
		boolean[] primitives = null;
		if(objects != null && objects.length != 0){
			int length = objects.length;
			primitives = new boolean[length];
			for(int index = 0; index < length; index++){
				primitives[index] = objects[index]; 
			}
		}
		return primitives;
	}
	
	public static byte[] convertObjectToPrimitiveArray(Byte[] objects){
		byte[] primitives = null;
		if(objects != null && objects.length != 0){
			int length = objects.length;
			primitives = new byte[length];
			for(int index = 0; index < length; index++){
				primitives[index] = objects[index];  
			}
		}
		return primitives;
	}
	
	public static char[] convertObjectToPrimitiveArray(Character[] objects){
		char[] primitives = null;
		if(objects != null && objects.length != 0){
			int length = objects.length;
			primitives = new char[length];
			for(int index = 0; index < length; index++){
				primitives[index] = objects[index]; 
			}
		}
		return primitives;
	}
	
	public static double[] convertObjectToPrimitiveArray(Double[] objects){
		double[] primitives = null;
		if(objects != null && objects.length != 0){
			int length = objects.length;
			primitives = new double[length];
			for(int index = 0; index < length; index++){
				primitives[index] = objects[index]; 
			}
		}
		return primitives;
	}
	
	public static float[] convertObjectToPrimitiveArray(Float[] objects){
		float[] primitives = null;
		if(objects != null && objects.length != 0){
			int length = objects.length;
			primitives = new float[length];
			for(int index = 0; index < length; index++){
				primitives[index] = objects[index]; 
			}
		}
		return primitives;
	}
	
	public static int[] convertObjectToPrimitiveArray(Integer[] objects){
		int[] primitives = null;
		if(objects != null && objects.length != 0){
			int length = objects.length;
			primitives = new int[length];
			for(int index = 0; index < length; index++){
				primitives[index] = objects[index]; 
			}
		}
		return primitives;
	}
	
	public static long[] convertObjectToPrimitiveArray(Long[] objects){
		long[] primitives = null;
		if(objects != null && objects.length != 0){
			int length = objects.length;
			primitives = new long[length];
			for(int index = 0; index < length; index++){
				primitives[index] = objects[index]; 
			}
		}
		return primitives;
	}
	
	public static short[] convertObjectToPrimitiveArray(Short[] objects){
		short[] primitives = null;
		if(objects != null && objects.length != 0){
			int length = objects.length;
			primitives = new short[length];
			for(int index = 0; index < length; index++){
				primitives[index] = objects[index]; 
			}
		}
		return primitives;
	}

}
