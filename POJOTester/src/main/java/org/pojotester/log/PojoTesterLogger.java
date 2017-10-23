/*******************************************************************************
 * Copyright 2017 Anindya Bandopadhyay (anindyabandopadhyay@gmail.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package org.pojotester.log;

public abstract class PojoTesterLogger {

	private static PojoTesterLogger logger;
	private static final Class<?>[] LOGGERS = {
																	Log4J2Logger.class,
																	Log4JLogger.class,
																	SLF4Jlogger.class,
																	TinylogLogger.class,
																	JavaLogger.class			
																  };
	static {
		try {
			createLoggerObject();
		} catch (ClassNotFoundException | NoClassDefFoundError e) {
			e.printStackTrace();
		}
	}

	private static void createLoggerObject() throws ClassNotFoundException, NoClassDefFoundError {
		for(Class<?> clazz : LOGGERS){
			try {
				logger = (PojoTesterLogger) clazz.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public static final void debugMessage(String message, Throwable object){
		if(logger != null) {
			logger.debug(message, object);
		}
	}
	
	abstract void debug(String message, Throwable object);
	
}
