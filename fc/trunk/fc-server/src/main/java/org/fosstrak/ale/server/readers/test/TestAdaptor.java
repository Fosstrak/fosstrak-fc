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
package org.fosstrak.ale.server.readers.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.epcglobalinc.tdt.LevelTypeList;
import org.fosstrak.ale.exception.ImplementationException;
import org.fosstrak.ale.server.Tag;
import org.fosstrak.ale.server.readers.BaseReader;
import org.fosstrak.ale.xsd.ale.epcglobal.LRProperty;
import org.fosstrak.ale.xsd.ale.epcglobal.LRSpec;
import org.fosstrak.hal.HardwareException;
import org.fosstrak.hal.Observation;
import org.fosstrak.tdt.TDTEngine;

/**
 * test adaptor for the filtering and collection triggering tag in regular time intervals.
 * @author swieland
 *
 */
public class TestAdaptor extends BaseReader {
	
	/** logger. */
	private static final Logger log = Logger.getLogger(TestAdaptor.class);

	private Thread thread = null;
	
	private Tag[] tagsAsArray;
	private int tagSize;
	
	/**
	 * loads a list of tags.
	 * @throws Exception upon problems.
	 */
	public void loadTags() throws Exception {
		List<Tag> tags = new ArrayList<Tag>();
		BufferedReader bis = new BufferedReader(new InputStreamReader(TestAdaptor.class.getResourceAsStream("/tags.txt")));
		
		TDTEngine tdt = new TDTEngine();
		Map<String, String> extraparms = new HashMap<String, String> ();
		extraparms.put("taglength", "96");
		extraparms.put("filter", "3");
		extraparms.put("gs1companyprefixlength", "7");
		
		String line = bis.readLine(); // drop first line with the header line.
		while ((line = bis.readLine()) != null) {
			String pureURI = tdt.convert(line, extraparms, LevelTypeList.TAG_ENCODING);
			
			Tag tag = new Tag();
			tag.setTagAsBinary(line);
			tag.setTagIDAsPureURI(pureURI);
			tags.add(tag);
		}
		
		tagSize = tags.size();
		tagsAsArray = new Tag[tagSize];
		int i=0;
		for (Tag t : tags) {
			tagsAsArray[i++] = t;
		}
	}
	
	private class TestAdaptorRunnable implements Runnable {

		private TestAdaptor tb = null;
		
		public TestAdaptorRunnable(TestAdaptor target) {
			tb = target;
		}
		
		public void run() {
			long tps = Long.parseLong(tb.logicalReaderProperties.get("tps"));
			long wt = Long.parseLong(tb.logicalReaderProperties.get("wt"));
			long gain = Long.parseLong(tb.logicalReaderProperties.get("gain"));
			TestAdaptor.log.debug(String.format("starting tagbomb\ntps: %s\nwt: %s\ngain: %s\n", tps, wt, gain));
			String readerName = tb.getName();
			
			long i = 0;
			while (true) {
				TestAdaptor.log.debug("firing " + tps + " tags");
				// generate tags 
				List<Tag> tags = new LinkedList<Tag>();
				for (long j=0; j<tps; j++) {
					Tag prototype = tagsAsArray[(int)i % tagSize];
					Tag tag = new Tag(readerName, null, null, System.currentTimeMillis());
					tag.setTagAsBinary(prototype.getTagAsBinary());
					tag.setTagIDAsPureURI(prototype.getTagIDAsPureURI());
					
					tags.add(tag);
					i++;
				}
				
				tb.addTags(tags);
				
				try {
					Thread.sleep(wt);
				} catch (InterruptedException e) {
					// we have been interrupted so stop the thread
					TestAdaptor.log.debug("we got an interrupt, so stop the tag bomb.");
					return;
				}
				tps += gain;
			}
		}
		
	}
	
	public TestAdaptor() {
		super();
	}
	
	@Override
	public void initialize(String name, LRSpec spec) throws ImplementationException {
		super.initialize(name, spec);
		try {
			loadTags();
		} catch (Exception e) {
			throw new ImplementationException(e);
		}
	}
	
	@Override
	public void addTag(Tag tag) {
		tag.addTrace(getName());
		
		setChanged();
		notifyObservers(tag);		
	}

	@Override
	public void addTags(List<Tag> tags) {
		setChanged();
		for (Tag tag : tags) {
			tag.addTrace(getName());
		}
		notifyObservers(tags);
	}

	@Override
	public void connectReader() {
		setConnected();
	}

	@Override
	public void disconnectReader() {
		stop();
		setDisconnected();
	}

	@Override
	public void start() {
		if (thread != null) {
			stop();
		}
		thread = new Thread(new TestAdaptorRunnable(this));
		thread.start();
		setStarted();
	}

	@Override
	public void stop() {
		if (thread != null) {
			thread.interrupt();
		}
		thread = null;
		setStopped();
	}

	@Override
	public void update(LRSpec spec) {
		boolean started = isStarted();
		
		disconnectReader();
		logicalReaderSpec = spec;
		logicalReaderProperties = new HashMap<String, String>();
		properties = new LinkedList<LRProperty>();
		
		if (spec.getProperties() != null) {
			for (LRProperty prop : spec.getProperties().getProperty()) {
				logicalReaderProperties.put(prop.getName(), prop.getValue());
				properties.add(prop);
			}	
		}
		connectReader();
		if (started) {
			start();
		}
	}
	
	@Override
	public Observation[] identify(String[] readPointNames)
	throws HardwareException {
		return null;
	}

	/**
	 * a handle to the tags contained in the test adapter.
	 * @return the tags.
	 */
	public Tag[] getTags() {
		return (Tag[]) ArrayUtils.clone(tagsAsArray);
	}
}
