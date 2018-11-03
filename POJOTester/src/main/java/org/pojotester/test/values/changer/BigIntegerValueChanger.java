package org.pojotester.test.values.changer;

import java.math.BigInteger;

class BigIntegerValueChanger implements ValueChanger<BigInteger> {

	@Override
	public BigInteger changedValue(BigInteger value) {
		BigInteger returnValue = BigInteger.ZERO;
		if(value != null) {
			if(BigInteger.ZERO.equals(value)) {
				returnValue = BigInteger.ONE;
			}
		}
		return returnValue;
	}

}
