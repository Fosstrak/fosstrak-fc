/*
 * Copyright (C) 2007 ETH Zurich
 *
 * This file is part of Fosstrak (www.fosstrak.org).
 *
 * Fosstrak is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License version 2.1, as published by the Free Software Foundation.
 *
 * Fosstrak is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Fosstrak; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
 * Boston, MA  02110-1301  USA
 */

package org.fosstrak.ale.server.persistence.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

/**
 * provides facilities for file management in the persistence API.
 * @author swieland
 *
 */
@Repository("persistenceFileUtils")
public class FileUtils {

	public static final String FILE_ENDING_XML = "xml";
	public static final String FILE_ENDING_LLRP = "llrp";
	public static final String FILE_ENDING_PROPERTES = "properties";
	
	/**	logger. */
	private static final Logger LOG = Logger.getLogger(FileUtils.class.getName());
	
	/**
	 * check if a given file exists on the given path.
	 * @param fileName the filename to check.
	 * @param filePath the path where the file should be located.
	 * @return true if the file exists, false otherwise.
	 */
	public boolean fileExist(String fileName, String filePath) {
		File f = new File(filePath + File.separator + fileName);
		if (f.exists()) {
			return true;
		}
		return false;		
	}
	
	/**
	 * get the filenames contained in the given directory.
	 * @param directoryPath the path of the directory.
	 * @param fileEnding the fileEnding of the files. if null, return all the files.
	 * @return a list of all contained filenames.
	 */
	public List<String> getFilesName(String directoryPath, String fileEnding) {		
		String[] filesName;		
		filesName = new File(directoryPath).list();		
		ArrayList<String> filesNameList = new ArrayList<String>();		
				
		if (filesName != null) {		
			for (String fileName : filesName) {
				if (null == fileEnding) {
					filesNameList.add(fileName);
					LOG.debug("add file " + fileName + " to list to read");
				} else if (fileName.endsWith("." + fileEnding)) {
					filesNameList.add(fileName);	
					LOG.debug("add file " + fileName + " to list to read");					
				} else {
					LOG.debug("not adding file " + fileName + " to list to read");
				}		
			}
		}
		
		LOG.debug("list of file: " + filesNameList);
		return filesNameList;
	}
	
	/**
	 * delete a file from the given path.
	 * @param directoryPath the directory path.
	 * @param fileName the files name.
	 * @return whether the file was deleted or not.
	 */
	public boolean removeFile(String directoryPath, String fileName) {
		return new File(directoryPath + File.separator + fileName).delete(); 
	}

}
