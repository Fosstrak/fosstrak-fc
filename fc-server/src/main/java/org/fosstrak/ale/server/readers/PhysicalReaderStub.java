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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * This class represents a physical reader. It contains source stubs which represent the sources of the physical reader. 
 * 
 * @author regli
 */
public class PhysicalReaderStub {

	/** name of the representing physical reader */
	private final String name;
	/** map with the corresponding source stubs */
	private final Map<String, PhysicalSourceStub> sourceStubs = new HashMap<String, PhysicalSourceStub>();

	/**
	 * Constructor sets the name of this physical reader stub.
	 * 
	 * @param name of this physical reader stub
	 */
	public PhysicalReaderStub(String name) {
		
		this.name = name;
		
	}
	
	/**
	 * This method returns the name of this physical reader stub.
	 * 
	 * @return the name of this physical reader stub
	 */
	public String getName() {
		
		return name;
		
	}
	
	/**
	 * This method adds a physical source stub to this reader stub.
	 * 
	 * @param sourceStub to add
	 */
	public void addSourceStub(PhysicalSourceStub sourceStub) {
		
		sourceStubs.put(sourceStub.getName(), sourceStub);
		
	}
	
	/**
	 * This method returns the physical source stub of the specified source.
	 * 
	 * @param sourceName of the source stub to return
	 * @return physical source stub
	 */
	public PhysicalSourceStub getSourceStub(String sourceName) {
	
		return sourceStubs.get(sourceName);
		
	}
	
	/**
	 * This method returns all physical source stubs of the reader stub. 
	 * 
	 * @return set of physical source stubs
	 */
	public Set<PhysicalSourceStub> getSourceStubs() {
		
		return new HashSet<PhysicalSourceStub>(sourceStubs.values());

	}
	
}