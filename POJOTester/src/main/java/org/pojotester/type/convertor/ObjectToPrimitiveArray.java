package org.pojotester.type.convertor;

public abstract class ObjectToPrimitiveArray {



	public static boolean[] convertPrimitiveToObjectArray(Boolean[] primitives){
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
	
	public static byte[] convertPrimitiveToObjectArray(Byte[] primitives){
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
	
	public static char[] convertPrimitiveToObjectArray(Character[] primitives){
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
	
	public static double[] convertPrimitiveToObjectArray(Double[] primitives){
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
	
	public static float[] convertPrimitiveToObjectArray(Float[] primitives){
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
	
	public static int[] convertPrimitiveToObjectArray(Integer[] primitives){
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
	
	public static long[] convertPrimitiveToObjectArray(Long[] primitives){
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
	
	public static short[] convertPrimitiveToObjectArray(Short[] primitives){
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
