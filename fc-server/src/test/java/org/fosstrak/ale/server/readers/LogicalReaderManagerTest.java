/*
 * Copyright (c) 2006, ETH Zurich
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * - Redistributions of source code must retain the above copyright notice,
 *   this list of conditions and the following disclaimer.
 *
 * - Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * - Neither the name of the ETH Zurich nor the names of its contributors may be
 *   used to endorse or promote products derived from this software without
 *   specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
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