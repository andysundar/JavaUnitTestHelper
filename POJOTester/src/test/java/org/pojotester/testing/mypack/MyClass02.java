package org.pojotester.testing.mypack;

import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

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
		return "MyClass02{" +
				"ints=" + Arrays.toString(ints) +
				", booleans=" + Arrays.toString(booleans) +
				", shorts=" + Arrays.toString(shorts) +
				", longs=" + Arrays.toString(longs) +
				", floats=" + Arrays.toString(floats) +
				", dates=" + Arrays.toString(dates) +
				", myClass03es=" + Arrays.toString(myClass03es) +
				", myClass01=" + myClass01 +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		MyClass02 myClass02 = (MyClass02) o;
		return Arrays.equals(ints, myClass02.ints) &&
				Arrays.equals(booleans, myClass02.booleans) &&
				Arrays.equals(shorts, myClass02.shorts) &&
				Arrays.equals(longs, myClass02.longs) &&
				Arrays.equals(floats, myClass02.floats) &&
				Arrays.equals(dates, myClass02.dates) &&
				Arrays.equals(myClass03es, myClass02.myClass03es) &&
				Objects.equals(myClass01, myClass02.myClass01);
	}

	@Override
	public int hashCode() {
		int result = Objects.hash(myClass01);
		result = 31 * result + Arrays.hashCode(ints);
		result = 31 * result + Arrays.hashCode(booleans);
		result = 31 * result + Arrays.hashCode(shorts);
		result = 31 * result + Arrays.hashCode(longs);
		result = 31 * result + Arrays.hashCode(floats);
		result = 31 * result + Arrays.hashCode(dates);
		result = 31 * result + Arrays.hashCode(myClass03es);
		return result;
	}
}
