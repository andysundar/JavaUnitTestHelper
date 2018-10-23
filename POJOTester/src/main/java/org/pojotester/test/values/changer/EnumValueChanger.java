package org.pojotester.test.values.changer;

import org.pojotester.test.AssertObjectCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnumValueChanger implements ValueChanger<Enum<?>> {

	private static final Logger LOGGER = LoggerFactory.getLogger(AssertObjectCreator.class);
	
	@Override
	public Enum<?> changedValue(Enum<?> value) {
		Enum<?> returnValue = value;
		if(value != null) {
			Class<?> clazz = value.getClass();
			Enum<?>[] constants = (Enum<?>[]) clazz.getEnumConstants();
			if(constants.length == 0 || constants.length == 1){
				LOGGER.debug(String.format("Enum value cannot be changed due to no/no other element to change. %s", constants.length));
			}
			for(Enum<?> constant : constants) {
				if(!value.equals(constant)) {
					returnValue = constant;
					break;
				}
			}
		}
		return returnValue;
	}

}
