/*******************************************************************************
 * Copyright 2017 Anindya Bandopadhyay (anindyabandopadhyay@gmail.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package org.pojotester.pack.scan;

import static java.nio.file.FileVisitResult.CONTINUE;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashSet;
import java.util.Set;

/**
 * This class extends {@link SimpleFileVisitor} to find matching package pattern. 
 * @author Anindya Bandopadhyay
 * @since 1.0
 */
class DirectoryFinder  extends SimpleFileVisitor<Path> {
	
	private PathMatcher matcher;
	private Set<File> directories = new HashSet<>();
    private Path currentPath;
    private int numMatches = 0;
    private boolean lastPattern; 
    private boolean firstPattern;
    private boolean afterWildCard;
    
    public DirectoryFinder() {
    	
    }

    public void createMatcher(String pattern){
        matcher = FileSystems.getDefault()
                .getPathMatcher("glob:" + pattern);
    }
    
	void find(Path file) {
		Path name = file.getFileName();
		if (name != null && matcher.matches(name)) {
			numMatches++;
			currentPath = file;
			if (lastPattern) {
				addDirectory(file.toFile());
			}
		}
	}

       
     @Override
     public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
    	 find(dir);
    	 return CONTINUE;
     }

    public void addDirectory(File directory){
    	directories.add(directory);
    }

	public Path getCurrentPath() {
		return currentPath;
	}

	public Set<File> getDirectories() {
		return directories;
	}

	public boolean isLastPattern() {
		return lastPattern;
	}

	public void setLastPattern(boolean lastPattern) {
		this.lastPattern = lastPattern;
	}

	public boolean isFirstPattern() {
		return firstPattern;
	}

	public void setFirstPattern(boolean firstPattern) {
		this.firstPattern = firstPattern;
	}

	public boolean isAfterWildCard() {
		return afterWildCard;
	}

	public void setAfterWildCard(boolean afterWildCard) {
		this.afterWildCard = afterWildCard;
	}

	public int getNumMatches() {
		return numMatches;
	}
     

}
