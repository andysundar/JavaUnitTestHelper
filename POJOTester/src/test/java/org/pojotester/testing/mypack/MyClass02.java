package org.pojotester.testing.mypack;

import java.util.Arrays;
import java.util.Date;

public class MyClass02 {

	private int[] ints;
	private boolean[] booleans;
	private short[] shorts;
	private long[] longs;
	private float[] floats;
	private Date[] dates;
	private MyClass03[] myClass03es;
	private MyClass01 myClass01;
	
	public int[] getInts() {
		return ints;
	}
	public void setInts(int[] ints) {
		this.ints = ints;
	}
	public boolean[] getBooleans() {
		return booleans;
	}
	public void setBooleans(boolean[] booleans) {
		this.booleans = booleans;
	}
	public short[] getShorts() {
		return shorts;
	}
	public void setShorts(short[] shorts) {
		this.shorts = shorts;
	}
	public long[] getLongs() {
		return longs;
	}
	public void setLongs(long[] longs) {
		this.longs = longs;
	}
	public float[] getFloats() {
		return floats;
	}
	public void setFloats(float[] floats) {
		this.floats = floats;
	}
	public Date[] getDates() {
		return dates;
	}
	public void setDates(Date[] dates) {
		this.dates = dates;
	}
	public MyClass03[] getMyClass03es() {
		return myClass03es;
	}
	public void setMyClass03es(MyClass03[] myClass03es) {
		this.myClass03es = myClass03es;
	}
	
	public MyClass01 getMyClass01() {
		return myClass01;
	}
	public void setMyClass01(MyClass01 myClass01) {
		this.myClass01 = myClass01;
	}
	@Override
	public String toString() {
		return "MyClass02 [ints=" + Arrays.toString(ints) + ", booleans=" + Arrays.toString(booleans) + ", shorts="
				+ Arrays.toString(shorts) + ", longs=" + Arrays.toString(longs) + ", floats=" + Arrays.toString(floats)
				+ ", dates=" + Arrays.toString(dates) + ", myClass03es=" + Arrays.toString(myClass03es) + ", myClass01="
				+ myClass01 + "]";
	}
	
}
