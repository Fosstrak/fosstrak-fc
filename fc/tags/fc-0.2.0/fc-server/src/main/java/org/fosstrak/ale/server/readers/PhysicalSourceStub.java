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

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.accada.reader.rprm.core.msg.notification.EventTimeType;
import org.accada.reader.rprm.core.msg.notification.TagEventType;
import org.accada.reader.rprm.core.msg.notification.TagFieldValueParamType;
import org.accada.reader.rprm.core.msg.notification.TagType;
import org.accada.reader.rprm.core.msg.notification.TagEventType.EventTriggers;
import org.accada.reader.rprm.core.msg.reply.ReadReportType;
import org.accada.reader.rprm.core.msg.reply.ReadReportType.SourceReport;
import org.accada.reader.rp.proxy.DataSelector;
import org.accada.reader.rp.proxy.RPProxyException;
import org.accada.reader.rp.proxy.ReadReport;
import org.accada.reader.rp.proxy.Source;
import org.accada.reader.rp.proxy.Trigger;

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
					List<org.accada.reader.rprm.core.msg.reply.TagType> tags = sourceReports.get(0).getTag();
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
	private List<TagType> convertReplyTagTypeList2NotificationTagTypeList(List<org.accada.reader.rprm.core.msg.reply.TagType> tags) {
		
		List<TagType> newList = new Vector<TagType>(); 
		for (org.accada.reader.rprm.core.msg.reply.TagType oldTag : tags) {
			
			// create new tag
			TagType newTag = new TagType();
			
			// set tag ids
			newTag.setTagID(oldTag.getTagID());
			newTag.setTagIDAsPureURI(oldTag.getTagIDAsPureURI());
			newTag.setTagIDAsTagURI(oldTag.getTagIDAsTagURI());
			
			// set tag type
			newTag.setTagType(oldTag.getTagType());
			
			// set tag event types
			for (org.accada.reader.rprm.core.msg.reply.TagEventType oldType : oldTag.getTagEvent()) {
				
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
			for (org.accada.reader.rprm.core.msg.reply.TagFieldValueParamType oldField : oldTag.getTagFields()) {
				
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