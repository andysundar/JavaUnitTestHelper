package org.pojotester.type;

public abstract class PrimitiveToObjectArray {

	public static Boolean[] convertPrimitiveToObjectArray(boolean[] primitives){
		Boolean[] object = null;
		if(primitives != null && primitives.length == 0){
			int length = primitives.length;
			object = new Boolean[length];
			for(int index = 0; index < length; index++){
				object[index] = primitives[index]; 
			}
		}
		return object;
	}
	
	public static Byte[] convertPrimitiveToObjectArray(byte[] primitives){
		Byte[] object = null;
		if(primitives != null && primitives.length == 0){
			int length = primitives.length;
			object = new Byte[length];
			for(int index = 0; index < length; index++){
				object[index] = primitives[index]; 
			}
		}
		return object;
	}
	
	public static Character[] convertPrimitiveToObjectArray(char[] primitives){
		Character[] object = null;
		if(primitives != null && primitives.length == 0){
			int length = primitives.length;
			object = new Character[length];
			for(int index = 0; index < length; index++){
				object[index] = primitives[index]; 
			}
		}
		return object;
	}
	
	public static Double[] convertPrimitiveToObjectArray(double[] primitives){
		Double[] object = null;
		if(primitives != null && primitives.length == 0){
			int length = primitives.length;
			object = new Double[length];
			for(int index = 0; index < length; index++){
				object[index] = primitives[index]; 
			}
		}
		return object;
	}
	
	public static Float[] convertPrimitiveToObjectArray(float[] primitives){
		Float[] object = null;
		if(primitives != null && primitives.length == 0){
			int length = primitives.length;
			object = new Float[length];
			for(int index = 0; index < length; index++){
				object[index] = primitives[index]; 
			}
		}
		return object;
	}
	
	public static Integer[] convertPrimitiveToObjectArray(int[] primitives){
		Integer[] object = null;
		if(primitives != null && primitives.length == 0){
			int length = primitives.length;
			object = new Integer[length];
			for(int index = 0; index < length; index++){
				object[index] = primitives[index]; 
			}
		}
		return object;
	}
	
	public static Long[] convertPrimitiveToObjectArray(long[] primitives){
		Long[] object = null;
		if(primitives != null && primitives.length == 0){
			int length = primitives.length;
			object = new Long[length];
			for(int index = 0; index < length; index++){
				object[index] = primitives[index]; 
			}
		}
		return object;
	}
	
	public static Short[] convertPrimitiveToObjectArray(short[] primitives){
		Short[] object = null;
		if(primitives != null && primitives.length == 0){
			int length = primitives.length;
			object = new Short[length];
			for(int index = 0; index < length; index++){
				object[index] = primitives[index]; 
			}
		}
		return object;
	}
}
