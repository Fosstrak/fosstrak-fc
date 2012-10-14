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

package org.fosstrak.ale.server;

import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import org.fosstrak.ale.exception.ECSpecValidationException;
import org.fosstrak.ale.exception.ImplementationException;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReport;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReportSpec;


/**
 * This interface represents an event cycle. It collects the tags and manages the 
 * reports.
 * 
 * @author regli
 * @author swieland
 * @author benoit.plomion@orange.com
 * @author nkef@ait.edu.gr
 */
public interface EventCycle extends Observer {

	/**
	 * This method adds a tag to this event cycle.
	 * 
	 * @param tag to add
	 * @throws ImplementationException if an implementation exception occurs
	 * @throws ECSpecValidationException if the tag is not valid
	 */
	void addTag(Tag tag);
	
	/**
	 * implementation of the observer interface for tags.
	 * @param o an observable object that triggered the update
	 * @param arg the arguments passed by the observable
	 */
	@Override
	void update(Observable o, Object arg);
	
	/**
	 * This method stops the thread.
	 */
	void stop();
	
	/**
	 * This method returns the name of this event cycle.
	 * 
	 * @return name of event cycle
	 */
	String getName();
	
	/**
	 * This method indicates if this event cycle is terminated or not.
	 * 
	 * @return true if this event cycle is terminated and false otherwise
	 */
	boolean isTerminated();
	
	/**
	 * starts this EventCycle.
	 */
	void launch();
	
	/**
	 * returns the set of tags from the previous EventCycle run.
	 * @return a set of tags from the previous EventCycle run
	 */
	Set<Tag> getLastEventCycleTags();

	/**
	 * This method return all tags of this event cycle.
	 * 
	 * @return set of tags
	 */
	Set<Tag> getTags();
	
	/**
	 * @return the number of rounds this event cycle has already run through.
	 */
	int getRounds();
	
	/**
	 * thread synchronizer for the end of this event cycle. if the event cycle 
	 * has already finished, then the method returns immediately. otherwise the 
	 * thread waits for the finish.
	 * @throws InterruptedException
	 */
	void join() throws InterruptedException;

	/**
	 * get the report spec identified by the given name.
	 * @param name the name of the spec to obtain.
	 * @return the ECReportSpec.
	 */
	ECReportSpec getReportSpecByName(String name);

	/**
	 * @return the lastReports
	 */
	Map<String, ECReport> getLastReports();
}