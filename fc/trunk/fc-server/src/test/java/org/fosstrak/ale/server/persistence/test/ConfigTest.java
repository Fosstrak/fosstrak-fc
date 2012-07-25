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
package org.fosstrak.ale.server.persistence.test;

import java.io.File;
import java.util.List;

import junit.framework.Assert;

import org.fosstrak.ale.server.persistence.Config;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * test the config class of the persistence framework.
 * @author swieland
 *
 */
public class ConfigTest {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();
	
    /**
     * test set real path.
     */
    @Test
    public void testSetRealPath() {
    	Config.setRealPathWebapp("myRealPath");
    	Assert.assertEquals("myRealPath", Config.getRealPathWebapp());
    }
    
	/**
	 * test the method file exists.
	 * @throws Exception test failure.
	 */
	@Test
	public void testFileExists() throws Exception {
		File f = File.createTempFile("testfile", ".tmp");
		String fileName = f.getName();
		// absolute path encodes the filename, thus remove it.
		String path = f.getAbsolutePath().replace(fileName, "");
		
		Assert.assertTrue(Config.fileExist(fileName, path));
		Assert.assertFalse(Config.fileExist("test" + fileName, path));
		Assert.assertFalse(Config.fileExist(fileName, "someotherpath"));
	}
	
	/**
	 * test the method getFileNames that is reading filenames contained in a folder.
	 * @throws Exception test failure.
	 */
	@Test
	public void testGetFileNames() throws Exception {
		tempFolder.create();
		final String fileXML = "file1.xml";
		final String fileLLRP = "file2.llrp";
		final String fileProperties = "file3.properties";
		
		tempFolder.newFile(fileXML);
		tempFolder.newFile(fileLLRP);
		tempFolder.newFile(fileProperties);
		
		List<String> result;
		result = Config.getFilesName(tempFolder.getRoot().getAbsolutePath(), null);
		Assert.assertNotNull(result);
		for (String file : new String[] {fileXML, fileLLRP, fileProperties} ) {
			Assert.assertTrue(result.contains(file));
		}
		
		result = Config.getFilesName(tempFolder.getRoot().getAbsolutePath(), "xml");
		Assert.assertNotNull(result);
		Assert.assertEquals(1, result.size());
		Assert.assertTrue(result.contains(fileXML));
		
		result = Config.getFilesName(tempFolder.getRoot().getAbsolutePath(), "llrp");
		Assert.assertNotNull(result);
		Assert.assertEquals(1, result.size());
		Assert.assertTrue(result.contains(fileLLRP));		

		Assert.assertNotNull(Config.getFilesName("someOtherPath", null));
	}
	
	/**
	 * test the proper removal of a file.
	 * @throws Exception test failure.
	 */
	@Test
	public void testRemoveFile() throws Exception {
		tempFolder.create();
		final String fileXML = "file1.xml";
		File f = tempFolder.newFile(fileXML);
		// absolute path encodes the filename, thus remove it.
		String path = f.getAbsolutePath().replace(fileXML, "");
		
		Assert.assertFalse(Config.removeFile(path, "nonexistingFile"));
		Assert.assertTrue(Config.removeFile(path, fileXML));
		Assert.assertFalse(Config.removeFile(path, fileXML));
	}
}
