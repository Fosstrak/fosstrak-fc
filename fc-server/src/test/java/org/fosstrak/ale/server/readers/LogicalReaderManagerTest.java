/*
 * Copyright (C) 2007 ETH Zurich
 *
 * This file is part of Accada (www.accada.org).
 *
 * Accada is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License version 2.1, as published by the Free Software Foundation.
 *
 * Accada is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Accada; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
 * Boston, MA  02110-1301  USA
 */

package org.accada.ale.server.readers;

import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import org.accada.ale.server.readers.LogicalReaderManager;
import org.accada.ale.server.readers.LogicalReaderStub;
import org.accada.ale.server.readers.PhysicalReaderStub;
import org.accada.ale.server.readers.PhysicalSourceStub;
import org.apache.log4j.PropertyConfigurator;

import util.SourceMock;

/**
 * This test is implemented for the following configuration which is defined in "src/test/config/LogicalReadersTestConfiguration.xml"
 * 
 * <ul>
 *   <li>
 *     LogicalReader1
 *     <ul>
 *       <li>
 *         MyReader
 *         <ul>
 *           <li>
 *             include: source1, source3
 *           </li>
 *         </ul>
 *       </li>
 *     </ul>
 *   </li>
 *   <li>
 *     LogicalReader2
 *     <ul>
 *       <li>
 *         MyReader
 *         <ul>
 *           <li>
 *             exclude: source1
 *           </li>
 *         </ul>
 *       </li>
 *     </ul>
 *   </li>
 *   <li>
 *     LogicalReader3
 *     <ul>
 *       <li>
 *         MyReader
 *       </li>
 *     </ul>
 *   </li>
 * </ul>
 * 
 * @author regli
 */
public class LogicalReaderManagerTest extends TestCase {

	private static final String CONFIGURATION_FILEPATH = "/LogicalReadersTestConfiguration.xml";
	
	private static final String PHYSICAL_READER_NAME = "MyReader";
	private static final String[] PHYSICAL_ANTENNA_NAME = new String[] {"source1", "source2", "source3"};
	private static final String[] LOGICAL_READER_NAMES_1 = new String[] {"LogicalReader3", "LogicalReader1"};
	private static final String[] LOGICAL_READER_NAMES_2 = new String[] {"LogicalReader3", "LogicalReader2"};
	private static final String[] LOGICAL_READER_NAMES_3 = new String[] {"LogicalReader3", "LogicalReader1", "LogicalReader2"};
	
	protected void setUp() throws Exception {

		super.setUp();
		
		// configure Logger with properties file
		URL url = this.getClass().getResource("/log4j.properties");
		PropertyConfigurator.configure(url);
		
		// create physical reader and source stubs
		PhysicalReaderStub physicalReaderStub = new PhysicalReaderStub(PHYSICAL_READER_NAME);
		for (String antennaName : PHYSICAL_ANTENNA_NAME) {
			physicalReaderStub.addSourceStub(new PhysicalSourceStub(new SourceMock(antennaName), null, null));
		}
		
		// set configuration file
		LogicalReaderManager.initialize(CONFIGURATION_FILEPATH);
		
		// add physical readers stub
		LogicalReaderManager.addPhysicalReaderStub(physicalReaderStub);
		
	}
	
	protected void tearDown() throws Exception {
		
		super.tearDown();
		LogicalReaderManager.initialize();
		
	}
	
	public void testGetLogicalReaders_1() throws Exception {
		
		Set<LogicalReaderStub> logicalReaderStubs = LogicalReaderManager.getLogicalReaderStubs(PHYSICAL_READER_NAME, PHYSICAL_ANTENNA_NAME[0]);
		assertEquals(LOGICAL_READER_NAMES_1.length, logicalReaderStubs.size());
		Set<String> names = new HashSet<String>();
		for (LogicalReaderStub logicalReaderStub : logicalReaderStubs) {
			names.add(logicalReaderStub.getName());
		}
		for (int i = 0; i < LOGICAL_READER_NAMES_1.length; i++) {
			assertTrue(names.contains(LOGICAL_READER_NAMES_1[i]));
		}
		
	}
	
	public void testGetLogicalReaders_2() throws Exception {
		
		Set<LogicalReaderStub> logicalReaderStubs = LogicalReaderManager.getLogicalReaderStubs(PHYSICAL_READER_NAME, PHYSICAL_ANTENNA_NAME[1]);
		assertEquals(LOGICAL_READER_NAMES_2.length, logicalReaderStubs.size());
		Set<String> names = new HashSet<String>();
		for (LogicalReaderStub logicalReaderStub : logicalReaderStubs) {
			names.add(logicalReaderStub.getName());
		}
		for (int i = 0; i < LOGICAL_READER_NAMES_2.length; i++) {
			assertTrue(names.contains(LOGICAL_READER_NAMES_2[i]));
		}
		
	}
	
	public void testGetLogicalReaders_3() throws Exception {
		
		Set<LogicalReaderStub> logicalReaderStubs = LogicalReaderManager.getLogicalReaderStubs(PHYSICAL_READER_NAME, PHYSICAL_ANTENNA_NAME[2]);
		assertEquals(LOGICAL_READER_NAMES_3.length, logicalReaderStubs.size());
		Set<String> names = new HashSet<String>();
		for (LogicalReaderStub logicalReaderStub : logicalReaderStubs) {
			names.add(logicalReaderStub.getName());
		}
		for (int i = 0; i < LOGICAL_READER_NAMES_3.length; i++) {
			assertTrue(names.contains(LOGICAL_READER_NAMES_3[i]));
		}
		
	}
	
	public void testGetLogicalReaders_4() throws Exception {
		
		Set<LogicalReaderStub> logicalReaderStubs = LogicalReaderManager.getLogicalReaderStubs(PHYSICAL_READER_NAME, null);
		assertEquals(LOGICAL_READER_NAMES_3.length, logicalReaderStubs.size());
		Set<String> names = new HashSet<String>();
		for (LogicalReaderStub logicalReaderStub : logicalReaderStubs) {
			names.add(logicalReaderStub.getName());
		}
		for (int i = 0; i < LOGICAL_READER_NAMES_3.length; i++) {
			assertTrue(names.contains(LOGICAL_READER_NAMES_3[i]));
		}
		
	}
	
	public void testGetLogicalReaders_5() throws Exception {
		
		Set<LogicalReaderStub> logicalReaderStubs = LogicalReaderManager.getLogicalReaderStubs(PHYSICAL_READER_NAME, "unknownSource");
		assertNull(logicalReaderStubs);
		
	}
}