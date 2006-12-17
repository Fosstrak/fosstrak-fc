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
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.accada.reader.msg.notification.EventTimeType;
import org.accada.reader.msg.notification.TagEventType;
import org.accada.reader.msg.notification.TagFieldValueParamType;
import org.accada.reader.msg.notification.TagType;
import org.accada.reader.msg.notification.TagEventType.EventTriggers;
import org.accada.reader.msg.reply.ReadReportType;
import org.accada.reader.msg.reply.ReadReportType.SourceReport;
import org.accada.reader.proxy.DataSelector;
import org.accada.reader.proxy.RPProxyException;
import org.accada.reader.proxy.ReadReport;
import org.accada.reader.proxy.Source;
import org.accada.reader.proxy.Trigger;

/**
 * This class represents a physical source. It saves the state of the corresponding physical source.
 * 
 * @author regli
 */
public class PhysicalSourceStub {

	/** logger */
	private static final Logger LOG = Logger.getLogger(PhysicalSourceStub.class);
	
	/** name of the source */
	private final String name;
	
	/** source proxy to communicate with the source */
	private final Source source;
	/** read trigger to add to the source if notifications are necessary */
	private final Trigger readTrigger;
	/** data selector to read tags from the source */
	private final DataSelector dataSelector;
	/** set of logical readers which include this physical source */
	private final Set<LogicalReaderStub> logicalReaderStubs = new HashSet<LogicalReaderStub>();

	/**
	 * Constructor sets the parameters.
	 * 
	 * @param source proxy of the corresponding source
	 * @param readTrigger to add to the source
	 * @param dataSelector to read data from source
	 * @throws RPProxyException if initialization failed
	 */
	public PhysicalSourceStub(Source source, Trigger readTrigger, DataSelector dataSelector) throws RPProxyException {
		
		this.source = source;
		this.readTrigger = readTrigger;
		this.dataSelector = dataSelector;
		name = source.getName();
		
	}

	/**
	 * This method returns the name of this physical source stub.
	 * 
	 * @return name of this physical source stub
	 */
	public String getName() {
		
		return name;
		
	}
	
	/**
	 * This method adds a logical reader stub to this physical source stub.
	 * 
	 * @param logicalReaderStub to add
	 */
	public void addLogicalReaderStub(LogicalReaderStub logicalReaderStub) {

		logicalReaderStubs.add(logicalReaderStub);
		
	}
	
	/**
	 * This method returns all logical reader stubs of this physical source stub.
	 * 
	 * @return set of logical reader stubs
	 */
	public Set<LogicalReaderStub> getLogicalReaderStubs() {
		
		return logicalReaderStubs;
		
	}
	
	/**
	 * This method returns all tags which are in the range of the corresponding antennas at the moment.
	 * 
	 * @return set of tags
	 */
	public Set<TagType> getAllTags() {
		
		ReadReport readReport = null;
		
		try {
			readReport = source.readIDs(dataSelector);
		} catch (RPProxyException e) {
			e.printStackTrace();
		}
		
		if (readReport != null) {
			ReadReportType reports = readReport.getReport();
			if (reports != null) {
				List<SourceReport> sourceReports = reports.getSourceReport();
				if (sourceReports != null && sourceReports.size() == 1) {
					List<org.accada.reader.msg.reply.TagType> tags = sourceReports.get(0).getTag();
					return new HashSet<TagType>(convertReplyTagTypeList2NotificationTagTypeList(tags));
				}
			}
		}
		
		return new HashSet<TagType>();
		
	}
	
	/**
	 * This method adds the read trigger to the source.
	 * 
	 * @throws RPProxyException if operation failed
	 */
	public void addReadTrigger() throws RPProxyException {
		
		LOG.debug("Add ReadTrigger '" + readTrigger + "' to PhysicalSourceStub '" + name + "'");
		
		source.addReadTriggers(new Trigger[] {readTrigger});
		
	}

	/** 
	 * This method removes the read trigger from the source.
	 * 
	 * @throws RPProxyException if operation failed
	 */
	public void removeReadTrigger() throws RPProxyException {
		
		LOG.debug("Remove ReadTrigger '" + readTrigger + "' from PhysicalSourceStub '" + name + "'");
		
		try {
			source.removeReadTriggers(new Trigger[] {readTrigger});
		} catch (RPProxyException e) {
			e.getMessage();
		}
		
	}
	
	/**
	 * This method converts a list of reply tag types to a list of notification tag types.
	 * 
	 * @param tags as list in reply tag format
	 * @return tags as list in notification tag format
	 */
	private List<TagType> convertReplyTagTypeList2NotificationTagTypeList(List<org.accada.reader.msg.reply.TagType> tags) {
		
		List<TagType> newList = new Vector<TagType>(); 
		for (org.accada.reader.msg.reply.TagType oldTag : tags) {
			
			// create new tag
			TagType newTag = new TagType();
			
			// set tag ids
			newTag.setTagID(oldTag.getTagID());
			newTag.setTagIDAsPureURI(oldTag.getTagIDAsPureURI());
			newTag.setTagIDAsTagURI(oldTag.getTagIDAsTagURI());
			
			// set tag type
			newTag.setTagType(oldTag.getTagType());
			
			// set tag event types
			for (org.accada.reader.msg.reply.TagEventType oldType : oldTag.getTagEvent()) {
				
				// create new type
				TagEventType newType = new TagEventType();
				
				// set event triggers
				EventTriggers newTriggers = new EventTriggers();
				for (String eventTrigger : oldType.getEventTriggers().getTrigger()) {
					newTriggers.getTrigger().add(eventTrigger);
				}
				newType.setEventTriggers(newTriggers);
				
				// set event type
				newType.setEventType(oldType.getEventType());
				
				// set time
				EventTimeType newTime = new EventTimeType();
				newTime.setEventTimeTick(oldType.getTime().getEventTimeTick());
				newTime.setEventTimeUTC(oldType.getTime().getEventTimeUTC());
				newType.setTime(newTime);
				
				// add new type to new tag
				newTag.getTagEvent().add(newType);

			}
			
			// set tag fields
			for (org.accada.reader.msg.reply.TagFieldValueParamType oldField : oldTag.getTagFields()) {
				
				// create new field
				TagFieldValueParamType newField = new TagFieldValueParamType();
				newField.setTagFieldName(oldField.getTagFieldName());
				newField.setTagFieldValue(oldField.getTagFieldValue());
				
				// add new field to new tag
				newTag.getTagFields().add(newField);

			}
			
			// add new tag to list
			newList.add(newTag);
			
		}
		
		return newList;
		
	}
	
}