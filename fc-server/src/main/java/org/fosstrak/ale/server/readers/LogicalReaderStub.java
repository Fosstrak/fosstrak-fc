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

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.accada.ale.server.EventCycle;
import org.accada.ale.wsdl.ale.epcglobal.ECSpecValidationException;
import org.accada.ale.wsdl.ale.epcglobal.ImplementationException;
import org.accada.reader.msg.notification.TagType;
import org.accada.reader.proxy.RPProxyException;

/**
 * This class represents a logical reader. It contains the corresponding physical source stubs and
 * notifies event cycles about new tags.
 * 
 * @author regli
 */
public class LogicalReaderStub {

	/** logger */
	private static final Logger LOG = Logger.getLogger(LogicalReaderStub.class);
	
	/** name of the representing logical reader */
	private final String name;
	/** set of physical source stubs which belong to this logical reader */
	private final Set<PhysicalSourceStub> physicalSourceStubs = new HashSet<PhysicalSourceStub>();
	/** set of event cycles which are subscribed to this logical reader */
	private final Set<EventCycle> eventCycles = new HashSet<EventCycle>();
	
	/**
	 * Constructor sets the name of the logical reader stub.
	 * 
	 * @param name of the logical reader stub
	 */
	public LogicalReaderStub(String name) {
		
		this.name = name;
		
	}

	/**
	 * This method returns the name of the logical reader stub.
	 * 
	 * @return name of this logical reader stub
	 */
	public String getName() {
		
		return name;
		
	}

	/**
	 * With this method an event cycle can be subscribed to this logical reader stub.
	 * 
	 * @param eventCycle to subscribe
	 * @return true if subscription was successful and false otherwise
	 * @throws RPProxyException if subscription fails
	 */
	public synchronized boolean subscribeEventCycle(EventCycle eventCycle) throws RPProxyException {
		
		LOG.debug("Subscribe EventCycle '" + eventCycle.getName() + "' to LogicalReaderStub '" + name + "'.");
		
		if (eventCycles.add(eventCycle)) {
			if (eventCycles.size() == 1) {
				for (PhysicalSourceStub physicalSourceStub : physicalSourceStubs) {
					physicalSourceStub.addReadTrigger();
				}
			}
			return true;
		}
		
		return false;
		
	}
	
	/**
	 * With this method an event cycle can be unsubscribed from this logical reader stub. 
	 * 
	 * @param eventCycle to unsubscribe
	 * @return true if unsubscription was successful and false otherwise
	 * @throws RPProxyException if unsubscription fails
	 */
	public synchronized boolean unsubscribeEventCycle(EventCycle eventCycle) throws RPProxyException {
		
		LOG.debug("Unsubscribe EventCycle '" + eventCycle.getName() + "' from LogicalReaderStub '" + name + "'.");
		
		if (eventCycles.remove(eventCycle)) {
			if (eventCycles.size() == 0) {
				for (PhysicalSourceStub physicalSourceStub : physicalSourceStubs) {
					physicalSourceStub.removeReadTrigger();
				}
			}
			return true;
		}
		
		return false;
		
	}
	
	/**
	 * This method is invoked, if a tag is detected in the range of a physical antenna, which belongs to this
	 * logical reader.
	 * 
	 * @param tag which is detected
	 * @throws ImplementationException if tag could not be added
	 * @throws ECSpecValidationException if tag format is not valid
	 */
	public void addTag(TagType tag) throws ImplementationException, ECSpecValidationException {
		
		for (EventCycle eventCycle : eventCycles) {
			eventCycle.addTag(tag);
		}
		
	}
	
	/**
	 * This method return all tags which are in the range of an antenna which belongs to this logical reader stub.
	 * 
	 * @return a set of tags 
	 */
	public Set<TagType> getAllTags() {
		
		// create result set
		Set<TagType> resultSet = new HashSet<TagType>();
		
		// iterate over physical source stubs and collect all tags
		for (PhysicalSourceStub physicalSourceStub : physicalSourceStubs) {
			resultSet.addAll(physicalSourceStub.getAllTags());
		}
		return resultSet;
		
	}

	/**
	 * This method adds a physical source stub.
	 * 
	 * @param physicalSourceStub to add
	 */
	public void addPhysicalSourceStub(PhysicalSourceStub physicalSourceStub) {

		physicalSourceStubs.add(physicalSourceStub);
		
	}
	
}