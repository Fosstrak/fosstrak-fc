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

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.Properties;

import org.easymock.EasyMock;
import org.fosstrak.ale.server.ALE;
import org.fosstrak.ale.server.llrp.LLRPControllerManager;
import org.fosstrak.ale.server.persistence.impl.ReadConfigImpl;
import org.fosstrak.ale.server.persistence.type.PersistenceConfig;
import org.fosstrak.ale.server.persistence.util.FileUtils;
import org.fosstrak.ale.server.readers.LogicalReaderManager;
import org.fosstrak.ale.util.SerializerUtil;
import org.fosstrak.ale.xsd.ale.epcglobal.ECSpec;
import org.fosstrak.ale.xsd.ale.epcglobal.LRSpec;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.llrp.ltk.generated.messages.ADD_ACCESSSPEC;
import org.llrp.ltk.generated.messages.ADD_ROSPEC;

import util.ECElementsUtils;

/**
 * test the loading API of the persistence API.
 * 
 * @author swieland
 *
 */
public class ReadConfigTest {
	
	@Rule
	public TemporaryFolder tempFolder = new TemporaryFolder();
	
	/**
	 * test the creation of persisted ECSpecs.
	 * @throws Exception test failure.
	 */
	@Test
	public void testReadECSpecs() throws Exception {
		tempFolder.create();
		File f = tempFolder.newFile("ecspec.xml");
		final String fileName = f.getName();
		// absolute path encodes the filename, thus remove it.
		final String path = f.getAbsolutePath().replace(fileName, "");
		
		ECSpec spec = ECElementsUtils.createECSpec();
		SerializerUtil.serializeECSpec(spec, new FileOutputStream(f));
		
		FileUtils fileUtils = EasyMock.createMock(FileUtils.class);
		EasyMock.expect(fileUtils.getFilesName(path, FileUtils.FILE_ENDING_XML)).andReturn(Arrays.asList(new String[] { fileName } ));
		EasyMock.replay(fileUtils);
		
		PersistenceConfig persistenceConfig = EasyMock.createMock(PersistenceConfig.class);
		EasyMock.expect(persistenceConfig.getRealPathECSpecDir()).andReturn(path).atLeastOnce();
		EasyMock.replay(persistenceConfig);
		
		ALE ale = EasyMock.createMock(ALE.class);
		ale.define(EasyMock.eq("ecspec"), EasyMock.isA(ECSpec.class));
		EasyMock.expectLastCall();
		EasyMock.replay(ale);
		
		ReadConfigImpl readConfig = new ReadConfigImpl();
		readConfig.setFileUtils(fileUtils);
		readConfig.setPersistenceConfig(persistenceConfig);
		readConfig.setAle(ale);
		
		// TEST
		readConfig.readECSpecs();
		
		EasyMock.verify(fileUtils);
		EasyMock.verify(persistenceConfig);
		EasyMock.verify(ale);
	}
	
	/**
	 * test the subscription.
	 * @throws Exception test failure.
	 */
	@Test
	public void testReadECSpecsSubscriber() throws Exception {
		tempFolder.create();
		File f = tempFolder.newFile("current.properties");
		final String fileName = f.getName();
		// absolute path encodes the filename, thus remove it.
		final String path = f.getAbsolutePath().replace(fileName, "");
		
		final String url1 = "url1";
		final String url2 = "url2";
		Properties p = new Properties();
		p.setProperty("url_1", url1);
		p.setProperty("url_2", url2);
		p.store(new FileOutputStream(f), null);

		
		FileUtils fileUtils = EasyMock.createMock(FileUtils.class);
		EasyMock.expect(fileUtils.getFilesName(path, FileUtils.FILE_ENDING_PROPERTES)).andReturn(Arrays.asList(new String[] { fileName } ));
		EasyMock.replay(fileUtils);
		
		PersistenceConfig persistenceConfig = EasyMock.createMock(PersistenceConfig.class);
		EasyMock.expect(persistenceConfig.getRealPathECSpecSubscriberDir()).andReturn(path).atLeastOnce();
		EasyMock.replay(persistenceConfig);
		
		ALE ale = EasyMock.createMock(ALE.class);
		ale.subscribe("current", url1);
		EasyMock.expectLastCall();
		ale.subscribe("current", url2);
		EasyMock.expectLastCall();
		EasyMock.replay(ale);
		
		ReadConfigImpl readConfig = new ReadConfigImpl();
		readConfig.setFileUtils(fileUtils);
		readConfig.setPersistenceConfig(persistenceConfig);
		readConfig.setAle(ale);
		
		// TEST
		readConfig.readECSpecsSubscriber();
		
		EasyMock.verify(fileUtils);
		EasyMock.verify(persistenceConfig);
		EasyMock.verify(ale);
	}
	
	/**
	 * test the loading of the logical readers.
	 * @throws Exception test failure.
	 */
	@Test
	public void testReadLRSpec() throws Exception {
		tempFolder.create();
		File f = tempFolder.newFile("LogicalReader1.xml");
		final String fileName = f.getName();
		// absolute path encodes the filename, thus remove it.
		final String path = f.getAbsolutePath().replace(fileName, "");
		FileOutputStream fos = new FileOutputStream(f);
		BufferedOutputStream bfos = new BufferedOutputStream(fos);
		bfos.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><ns3:LRSpec xmlns:ns2=\"urn:epcglobal:ale:wsdl:1\" xmlns:ns3=\"urn:epcglobal:ale:xsd:1\"><isComposite>false</isComposite><readers/><properties><property><name>ReaderType</name><value>org.fosstrak.ale.server.readers.hal.HALAdaptor</value></property><property><name>Description</name><value>My first HAL device reader</value></property><property><name>PhysicalReaderName</name><value>MyReader1</value></property><property><name>ReadTimeInterval</name><value>1000</value></property><property><name>PropertiesFile</name><value>/props/SimulatorController.xml</value></property></properties></ns3:LRSpec>".getBytes());
		bfos.close();
		
		FileUtils fileUtils = EasyMock.createMock(FileUtils.class);
		EasyMock.expect(fileUtils.getFilesName(path, FileUtils.FILE_ENDING_XML)).andReturn(Arrays.asList(new String[] { fileName } ));
		EasyMock.replay(fileUtils);
		
		PersistenceConfig persistenceConfig = EasyMock.createMock(PersistenceConfig.class);
		EasyMock.expect(persistenceConfig.getRealPathLRSpecDir()).andReturn(path).atLeastOnce();
		EasyMock.replay(persistenceConfig);
		
		LogicalReaderManager logicalReaderManager = EasyMock.createMock(LogicalReaderManager.class);
		logicalReaderManager.define(EasyMock.eq("LogicalReader1"), EasyMock.isA(LRSpec.class));
		EasyMock.replay(logicalReaderManager);
		
		ReadConfigImpl readConfig = new ReadConfigImpl();
		readConfig.setFileUtils(fileUtils);
		readConfig.setPersistenceConfig(persistenceConfig);
		readConfig.setLogicalReaderManager(logicalReaderManager);
		
		// TEST
		readConfig.readLRSpecs();
		
		EasyMock.verify(fileUtils);
		EasyMock.verify(persistenceConfig);
		EasyMock.verify(logicalReaderManager);
	}
	
	/**
	 * test the loading of the ro specs.
	 * @throws Exception test failure.
	 */
	@Test
	public void testReadAddROSpec() throws Exception {
		tempFolder.create();
		File f = tempFolder.newFile("rospec.llrp");
		final String fileName = f.getName();
		// absolute path encodes the filename, thus remove it.
		final String path = f.getAbsolutePath().replace(fileName, "");
		FileOutputStream fos = new FileOutputStream(f);
		BufferedOutputStream bfos = new BufferedOutputStream(fos);
		bfos.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?><llrp:ADD_ROSPEC xmlns:llrp=\"http://www.llrp.org/ltk/schema/core/encoding/xml/1.0\" xmlns:Impinj=\"http://developer.impinj.com/ltk/schema/encoding/xml/1.0\" Version=\"1\" MessageID=\"4\"><llrp:ROSpec><llrp:ROSpecID>1</llrp:ROSpecID><llrp:Priority>0</llrp:Priority><llrp:CurrentState>Disabled</llrp:CurrentState><llrp:ROBoundarySpec><llrp:ROSpecStartTrigger><llrp:ROSpecStartTriggerType>Null</llrp:ROSpecStartTriggerType></llrp:ROSpecStartTrigger><llrp:ROSpecStopTrigger><llrp:ROSpecStopTriggerType>Null</llrp:ROSpecStopTriggerType><llrp:DurationTriggerValue>0</llrp:DurationTriggerValue></llrp:ROSpecStopTrigger></llrp:ROBoundarySpec><llrp:AISpec><llrp:AntennaIDs>0</llrp:AntennaIDs><llrp:AISpecStopTrigger><llrp:AISpecStopTriggerType>Null</llrp:AISpecStopTriggerType><llrp:DurationTrigger>0</llrp:DurationTrigger></llrp:AISpecStopTrigger><llrp:InventoryParameterSpec><llrp:InventoryParameterSpecID>9</llrp:InventoryParameterSpecID><llrp:ProtocolID>EPCGlobalClass1Gen2</llrp:ProtocolID></llrp:InventoryParameterSpec></llrp:AISpec></llrp:ROSpec></llrp:ADD_ROSPEC>".getBytes());
		bfos.close();
		
		FileUtils fileUtils = EasyMock.createMock(FileUtils.class);
		EasyMock.expect(fileUtils.getFilesName(path, FileUtils.FILE_ENDING_LLRP)).andReturn(Arrays.asList(new String[] { fileName } ));
		EasyMock.replay(fileUtils);
		
		PersistenceConfig persistenceConfig = EasyMock.createMock(PersistenceConfig.class);
		EasyMock.expect(persistenceConfig.getRealPathROSpecDir()).andReturn(path).atLeastOnce();
		EasyMock.replay(persistenceConfig);
		
		LLRPControllerManager logicalReaderManager = EasyMock.createMock(LLRPControllerManager.class);
		logicalReaderManager.define(EasyMock.eq("rospec"), EasyMock.isA(ADD_ROSPEC.class));
		EasyMock.replay(logicalReaderManager);
		
		ReadConfigImpl readConfig = new ReadConfigImpl();
		readConfig.setFileUtils(fileUtils);
		readConfig.setPersistenceConfig(persistenceConfig);
		readConfig.setLlrpControllerManager(logicalReaderManager);
		
		// TEST
		readConfig.readAddROSpecs();
		
		EasyMock.verify(fileUtils);
		EasyMock.verify(persistenceConfig);
		EasyMock.verify(logicalReaderManager);
	}
	
	/**
	 * test the loading of the access specs.
	 * @throws Exception test failure.
	 */
	@Test
	public void testAddAccessSpec() throws Exception {
		tempFolder.create();
		File f = tempFolder.newFile("access.llrp");
		final String fileName = f.getName();
		// absolute path encodes the filename, thus remove it.
		final String path = f.getAbsolutePath().replace(fileName, "");
		FileOutputStream fos = new FileOutputStream(f);
		BufferedOutputStream bfos = new BufferedOutputStream(fos);
		bfos.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?><llrp:ADD_ACCESSSPEC xmlns:llrp=\"http://www.llrp.org/ltk/schema/core/encoding/xml/1.0\" xmlns:Impinj=\"http://developer.impinj.com/ltk/schema/encoding/xml/1.0\" Version=\"1\" MessageID=\"5\"><llrp:AccessSpec><llrp:AccessSpecID>1</llrp:AccessSpecID><llrp:AntennaID>0</llrp:AntennaID><llrp:ProtocolID>EPCGlobalClass1Gen2</llrp:ProtocolID><llrp:CurrentState>Disabled</llrp:CurrentState><llrp:ROSpecID>1</llrp:ROSpecID><llrp:AccessSpecStopTrigger><llrp:AccessSpecStopTrigger>Null</llrp:AccessSpecStopTrigger><llrp:OperationCountValue>0</llrp:OperationCountValue></llrp:AccessSpecStopTrigger><llrp:AccessCommand><llrp:C1G2TagSpec><llrp:C1G2TargetTag><llrp:MB>0</llrp:MB><llrp:Match>1</llrp:Match><llrp:Pointer>0</llrp:Pointer><llrp:TagMask>ffffffff</llrp:TagMask><llrp:TagData>01010101</llrp:TagData></llrp:C1G2TargetTag></llrp:C1G2TagSpec><llrp:C1G2Write><llrp:OpSpecID>1</llrp:OpSpecID><llrp:AccessPassword>0</llrp:AccessPassword><llrp:MB>0</llrp:MB><llrp:WordPointer>0</llrp:WordPointer><llrp:WriteData>ffff ffff</llrp:WriteData></llrp:C1G2Write></llrp:AccessCommand></llrp:AccessSpec></llrp:ADD_ACCESSSPEC>".getBytes());
		bfos.close();
		
		FileUtils fileUtils = EasyMock.createMock(FileUtils.class);
		EasyMock.expect(fileUtils.getFilesName(path, FileUtils.FILE_ENDING_LLRP)).andReturn(Arrays.asList(new String[] { fileName } ));
		EasyMock.replay(fileUtils);
		
		PersistenceConfig persistenceConfig = EasyMock.createMock(PersistenceConfig.class);
		EasyMock.expect(persistenceConfig.getRealPathAccessSpecDir()).andReturn(path).atLeastOnce();
		EasyMock.replay(persistenceConfig);
		
		LLRPControllerManager logicalReaderManager = EasyMock.createMock(LLRPControllerManager.class);
		logicalReaderManager.defineAccessSpec(EasyMock.eq("access"), EasyMock.isA(ADD_ACCESSSPEC.class));
		EasyMock.replay(logicalReaderManager);
		
		ReadConfigImpl readConfig = new ReadConfigImpl();
		readConfig.setFileUtils(fileUtils);
		readConfig.setPersistenceConfig(persistenceConfig);
		readConfig.setLlrpControllerManager(logicalReaderManager);
		
		// TEST
		readConfig.readAddAccessSpecs();
		
		EasyMock.verify(fileUtils);
		EasyMock.verify(persistenceConfig);
		EasyMock.verify(logicalReaderManager);
	}
}
