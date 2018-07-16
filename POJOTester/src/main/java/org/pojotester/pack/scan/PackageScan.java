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
package org.pojotester.pack.scan;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;

import org.pojotester.log.PojoTesterLogger;

/**
 * This class is responsible to scan the package and load the classes which is suppose to be unit tested.
 * @author Anindya Bandopadhyay
 * @since 1.0
 */
public abstract class PackageScan {
	
	private static final String WIN_PATH_SEPARATOR = "\\";
	private static final String FILE = "file";
	private static final char PATH_SEPARATOR_CHAR = File.separatorChar;
	private static final char WILDCARD_CHAR = '*';
	private static final char QUESTION_CHAR = '?';
	private static final String WILDCARD_REGX = "**";
	private static final String PATH_SEPARATOR = File.separator;
	private static final String DOT = "\\.";
	private static final String JAVA_PACKAGE_SEPARATOR = ".";
	private static final String CLASS_FILE_SUFFIX = ".class";
	private static final String CLASS_SUFFIX = "class";
	private static final String ILLEGAL_PACKAGE = "Package cannot start with " + WILDCARD_REGX ;
	
	/**
	 * This method load the qualified classes and return the unique list.
	 * @param packagesToScan
	 * @return unique list of qualified classes
	 * @since 1.0
	 */
	public Set<Class<?>> getClasses(final String... packagesToScan) {
		Set<Class<?>> classSet = Collections.emptySet();
		if (packagesToScan != null) {
			classSet = new HashSet<>();
			for (String location : packagesToScan) {
				if (location.startsWith(WILDCARD_REGX)) {
					throw new IllegalArgumentException(ILLEGAL_PACKAGE + " [ " + location + " ]");
				}
				String startPackage = location.split(DOT)[0];

				location = processLocations(location);
				String rootDirectory = determineRootDirectory(location);
				String patternString = "";
				if (!location.equals(rootDirectory)) {
					patternString = location.substring(rootDirectory.length() + 1);
				}

				if (patternString.isEmpty()) {
					// When exact path is given [e.g. mypack.MyClass.class]
					String binaryClassName = getQualifiedClassName(startPackage, rootDirectory);
					Class<?> clazz = loadClass(binaryClassName);
					if (isClass(clazz)) {
						classSet.add(clazz);
					}
				} else {
					// Goto root directory and match pattern to search
					// directories/files [e.g. mypack.**.My*.class,
					// mypack.**.MyClass.class]
					List<String> patterns = new LinkedList<>();
					String[] patternStringArray = patternString.split(Matcher.quoteReplacement(PATH_SEPARATOR));
					String classFilePattern = WILDCARD_CHAR + CLASS_FILE_SUFFIX;
					for (String pattern : patternStringArray) {
						if (pattern.endsWith(CLASS_FILE_SUFFIX)) {
							classFilePattern = pattern;
						} else if (!WILDCARD_REGX.equals(pattern)) {
							patterns.add(pattern);
						}
					}
					Set<String> classFiles = null;
					// Check for class 
					Set<File> packageDirectories = findPackageDirectory(rootDirectory, patternStringArray);
					for (File packageDirectory : packageDirectories) {
						Path path = packageDirectory.toPath();
						classFiles = findClassFiles(path, classFilePattern);
						for (String className : classFiles) {
							String binaryClassName = getQualifiedClassName(startPackage, className);
							Class<?> clazz = loadClass(binaryClassName);
							if (isClass(clazz)) {
								classSet.add(clazz);
							}
						}
					}

				}

			}
		}
		return classSet;
	}



	protected String determineRootDirectory(final String location){
		char[] sources = location.toCharArray();
		int endIndex = 0;
		String rootDirectory = location;
		int indexOfAsteriskOrQuestion = indexOfAsteriskOrQuestionChar(sources);
		
		if(indexOfAsteriskOrQuestion < sources.length){
			for(int index = indexOfAsteriskOrQuestion ; index > -1 ; index--){
				if(sources[index] == PATH_SEPARATOR_CHAR){
					break;
				}
				endIndex++;
			}
			endIndex = indexOfAsteriskOrQuestion - endIndex;
			char[] rootDirChars = Arrays.copyOfRange(sources, 0, endIndex);
			rootDirectory = new String(rootDirChars);
		}
		
		return rootDirectory;
	}

	private int indexOfAsteriskOrQuestionChar(char[] sources) {
		int indexOfWildcard = 0;
		for(char chr : sources){
			if(chr == WILDCARD_CHAR || chr == QUESTION_CHAR){
				break;
			}
			indexOfWildcard++;
		}
		return indexOfWildcard;
	}
	
	private String processLocations(String location) {
		location = location.replaceAll(DOT, Matcher.quoteReplacement(PATH_SEPARATOR));
		String pathSeparatorClassSuffix = PATH_SEPARATOR + CLASS_SUFFIX;
		if(location.endsWith(pathSeparatorClassSuffix)){
			int endIndex = location.length() - pathSeparatorClassSuffix.length();
			location = location.substring(0, endIndex);
			location += CLASS_FILE_SUFFIX;
		}
		if(!location.endsWith(CLASS_FILE_SUFFIX)){
			// When path end with *.* instead of *.class
			String pathSeparatorWildCard = PATH_SEPARATOR + WILDCARD_CHAR; 
			if(location.endsWith(pathSeparatorWildCard)){
				int endIndex = location.length() - pathSeparatorWildCard.length();
				location = location.substring(0, endIndex);
				location += pathSeparatorClassSuffix;
			} else {
				// When only folder is given 
				location += (PATH_SEPARATOR + WILDCARD_CHAR + CLASS_FILE_SUFFIX);
			}
		}
		return location;
	}
	
	private Set<File> findPackageDirectory(String rootDirectory, String[] patternStringArray) {
		Set<File> packageDirectories = new TreeSet<>();
		Enumeration<URL> urls = null;
		try {
			urls = ClassLoader.getSystemResources(rootDirectory);
		} catch (IOException e) {
			PojoTesterLogger.debugMessage("Not able to find resources in class path", e);
		}

        if(urls == null) {
        	PojoTesterLogger.debugMessage("No resource found in class path. ", null);
        } else {
        	while(urls.hasMoreElements()){
            	URL url = urls.nextElement();
            	PojoTesterLogger.debugMessage("In class path " + url.getFile(), null);
            	
            	if(!FILE.equals(url.getProtocol())) continue;
            	
            	URI uri = null;
        		
        		try {
        			uri = url.toURI();
        			Path startDirectory = Paths.get(uri);
        			
        			if (patternStringArray.length > 0 && !patternStringArray[0].endsWith(CLASS_FILE_SUFFIX)) {
        				patternStringArray = Arrays.copyOfRange(patternStringArray, 0, (patternStringArray.length - 1));
        				Set<File> directories = patternMaching(startDirectory, patternStringArray);
        				if(directories != null && !directories.isEmpty()) {
        					packageDirectories.addAll(directories);
        				}
        			} else {
        				packageDirectories.add(startDirectory.toFile());
        			}

        		} catch (URISyntaxException e) {
        			PojoTesterLogger.debugMessage("Class loader resource URL issue. ", e);
        		}
        		
            }
        }

		
		return packageDirectories;
	}

	private Set<File> patternMaching(Path startDirectory, String[] patternStringArray) {
		
		int index = 0;
		int previousIndex = -1;
		int lastIndex = patternStringArray.length - 1;
		DirectoryFinder visitor = new DirectoryFinder();
	
		
		for (String pattern : patternStringArray) {
			PojoTesterLogger.debugMessage("pattern " + pattern, null);
			visitor.setNumMatches(0);
			if (!isPattern(pattern) || index == lastIndex) {
				visitor.createMatcher(pattern);
				visitor.setFirstPattern((index == 0));
				visitor.setAfterWildCard((previousIndex != -1 && (isPattern(patternStringArray[previousIndex]))));
				visitor.setLastPattern((index == lastIndex));
				try {
					Files.walkFileTree(startDirectory, visitor);
				} catch (IOException e) {
					PojoTesterLogger.debugMessage("File tree walk failed." , e);
				}
				Path path = visitor.getCurrentPath();

				if (path != null) {
					startDirectory = path;
				}

			}

			index++;
			previousIndex++;
			if (!isPattern(pattern) && visitor.getNumMatches() == 0) {
				break;
			}
		}
		
		Set<File> directories = visitor.getDirectories();
		
		return directories;
	}

	
	private Set<String> findClassFiles(Path dir, String patternString) {
		Set<String> classFiles = Collections.emptySet();
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, patternString)){
			classFiles = new HashSet<>();
			for(Path path : stream){
				File file = path.toFile();
				if(file.isFile()){
					classFiles.add(file.getAbsolutePath());
				}
			}
		} catch (DirectoryIteratorException | IOException ex) {
	         PojoTesterLogger.debugMessage("Directory iterator failed", ex);
	     }
		return classFiles;
	}
	
	private String getQualifiedClassName(String startPackage, String classNamePath) {
		int endIndex = classNamePath.length() - CLASS_FILE_SUFFIX.length();
		int startIndex = classNamePath.indexOf(startPackage);
		classNamePath = classNamePath.substring(startIndex, endIndex);
		if(classNamePath.contains(File.separator)){
			String separator = File.separator.equals(WIN_PATH_SEPARATOR) ? (File.separator + File.separator) : File.separator;
			classNamePath = classNamePath.replaceAll(separator, JAVA_PACKAGE_SEPARATOR);
		} else {
			classNamePath = classNamePath.replaceAll(PATH_SEPARATOR, JAVA_PACKAGE_SEPARATOR);
		}
		
		return classNamePath;
	}

	private boolean isClass(Class<?> clazz) {
		return (clazz != null) && (!clazz.isAnnotation()) && (!clazz.isInterface()) && (!clazz.isEnum()) && (!Modifier.isAbstract(clazz.getModifiers()));
	}
	
	private boolean isPattern(String pattern) {
		return WILDCARD_REGX.equals(pattern) || Character.toString(WILDCARD_CHAR).equals(pattern) || Character.toString(QUESTION_CHAR).equals(pattern);
	}
	
	protected abstract Class<?> loadClass(String className);
	
}