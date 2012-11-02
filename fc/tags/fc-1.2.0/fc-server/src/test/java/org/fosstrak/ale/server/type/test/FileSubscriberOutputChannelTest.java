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

package org.fosstrak.ale.server.type.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import junit.framework.Assert;

import org.easymock.EasyMock;
import org.fosstrak.ale.exception.ImplementationException;
import org.fosstrak.ale.exception.InvalidURIException;
import org.fosstrak.ale.server.type.FileSubscriberOutputChannel;
import org.fosstrak.ale.util.DeserializerUtil;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReports;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import util.ECElementsUtils;


/**
 * test the file subscriber output channel.
 * @author swieland
 *
 */
public class FileSubscriberOutputChannelTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

	@Test
	public void testFileURIs() throws InvalidURIException {
		
		FileSubscriberOutputChannel listener = new FileSubscriberOutputChannel("file:///dir");
		Assert.assertEquals("localhost", listener.getHost());
		Assert.assertEquals("dir", listener.getPath());
		
		listener = new FileSubscriberOutputChannel("file://localhost/dir/dir");
		Assert.assertEquals("localhost", listener.getHost());
		Assert.assertEquals("dir/dir", listener.getPath());
		
		listener = new FileSubscriberOutputChannel("file://localhost/dir/dir");
		Assert.assertEquals("localhost", listener.getHost());
		Assert.assertEquals("dir/dir", listener.getPath());
	}
	
	@Test(expected = InvalidURIException.class)
	public void testOnlyLocalFilesAllowed() throws InvalidURIException{		
		new FileSubscriberOutputChannel("file://192.168.1.1/dir/dir");		
	}
	
	@Test(expected = InvalidURIException.class)
	public void testOnlyLocalFilesAllowed2() throws InvalidURIException{		
		new FileSubscriberOutputChannel("file://myhost.com/dir/dir");		
	}

	@Test(expected = InvalidURIException.class)
	public void testInvalidFileURIs_noPath() throws Exception {
		new FileSubscriberOutputChannel("file://localhost");
	}

	@Test(expected = InvalidURIException.class)
	public void testInvalidFileURIs_noFile() throws Exception {
		new FileSubscriberOutputChannel("file://localhost/dir/dir/");
	}

	@Test(expected = InvalidURIException.class)
	public void testInvalidFileURIs_wrongScheme() throws Exception {
		new FileSubscriberOutputChannel("ftp://localhost/dir/dira");
	}
	
	@Test(expected = InvalidURIException.class)
	public void testInvalidFileURIs_InvalidURI() throws Exception {
		new FileSubscriberOutputChannel(null);
	}
	
	@Test
	public void testInvalidFileURIsdfs_InvalidURI() throws Exception {
		new FileSubscriberOutputChannel("file:///c:/test");
	}
	
	@Test(expected = ImplementationException.class)
	public void testFileNotExistsCannotCreate() throws Exception {
		final String filename = "file://localhost/dir/dir";
		
		File mock = EasyMock.createMock(File.class);
		EasyMock.expect(mock.exists()).andReturn(false);
		EasyMock.expect(mock.createNewFile()).andThrow(new IOException());
		EasyMock.replay(mock);
		FileSubscriberOutputChannel tcp = new NotifyFile(filename, mock);
		tcp.notify(ECElementsUtils.createECReports());
	}
	
	@Test(expected = ImplementationException.class)
	public void testFileNotFileCannotCreate() throws Exception {
		final String filename = "file://localhost/dir/dir";
		
		File mock = EasyMock.createMock(File.class);
		EasyMock.expect(mock.exists()).andReturn(true);
		EasyMock.expect(mock.isFile()).andReturn(false);
		EasyMock.expect(mock.createNewFile()).andThrow(new IOException());
		EasyMock.replay(mock);
		FileSubscriberOutputChannel tcp = new NotifyFile(filename, mock);
		tcp.notify(ECElementsUtils.createECReports());
	}
    
	@Test
	public void testNotify_File() throws Exception {
		// create file
		File notificationFile = folder.newFile("test");		
		// create notification listener
		FileSubscriberOutputChannel file = new FileSubscriberOutputChannel("file:///" + notificationFile.getAbsolutePath());
		// create reports
		ECReports reports = ECElementsUtils.createECReports();
		// notify listener about reports
		file.notify(reports);
		// read file
		ECReports resultReports = DeserializerUtil.deserializeECReports(new FileInputStream(notificationFile));
		// check result
		ECElementsUtils.assertEquals(reports, resultReports);
	}
	
	/**
	 * little helper class allowing us to nicely test the file subscriber without the need of a real socket.
	 * @author swieland
	 *
	 */
	private class NotifyFile extends FileSubscriberOutputChannel {

		private File mock;
		
		public NotifyFile(String notificationURI, File mock) throws InvalidURIException {
			super(notificationURI);
			this.mock = mock;
		}

		@Override
		protected File getFile() {
			return mock;
		}		
	}
}
