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

package org.accada.ale.server.readers.rp;

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