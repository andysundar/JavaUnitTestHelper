package org.pojotester.test.values.changer;

import java.math.BigDecimal;

public class BigDecimalValueChanger implements ValueChanger<BigDecimal> {

	@Override
	public BigDecimal changedValue(BigDecimal value) {
		BigDecimal returnValue = BigDecimal.ZERO;
		if(value != null) {
			if(BigDecimal.ZERO.equals(value)) {
				returnValue = BigDecimal.ONE;
			}
		}
		return returnValue;
	}

}
