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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(booleans);
		result = prime * result + Arrays.hashCode(dates);
		result = prime * result + Arrays.hashCode(floats);
		result = prime * result + Arrays.hashCode(ints);
		result = prime * result + Arrays.hashCode(longs);
		result = prime * result + ((myClass01 == null) ? 0 : myClass01.hashCode());
		result = prime * result + Arrays.hashCode(myClass03es);
		result = prime * result + Arrays.hashCode(shorts);
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof MyClass02)) {
			return false;
		}
		MyClass02 other = (MyClass02) obj;
		if (!Arrays.equals(booleans, other.booleans)) {
			return false;
		}
		if (!Arrays.equals(dates, other.dates)) {
			return false;
		}
		if (!Arrays.equals(floats, other.floats)) {
			return false;
		}
		if (!Arrays.equals(ints, other.ints)) {
			return false;
		}
		if (!Arrays.equals(longs, other.longs)) {
			return false;
		}
		if (myClass01 == null) {
			if (other.myClass01 != null) {
				return false;
			}
		} else if (!myClass01.equals(other.myClass01)) {
			return false;
		}
		if (!Arrays.equals(myClass03es, other.myClass03es)) {
			return false;
		}
		if (!Arrays.equals(shorts, other.shorts)) {
			return false;
		}
		return true;
	}
	
}
