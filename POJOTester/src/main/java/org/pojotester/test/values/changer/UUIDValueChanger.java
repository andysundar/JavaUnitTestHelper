package org.pojotester.test.values.changer;

import java.util.UUID;

class UUIDValueChanger implements ValueChanger<UUID> {


	@Override
	public UUID changedValue(UUID value) {
		UUID returnValue = UUID.randomUUID();
		int attempt = 10;
		if(value != null) {
			while(value.equals(returnValue)) {
				returnValue = UUID.randomUUID();
				if(attempt == 0) {
					break;
				}
				
				attempt--;
			}
		}
		return returnValue;
	}

}
