package org.fosstrak.ale.server.readers.hal;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.fosstrak.ale.exception.ImplementationException;
import org.fosstrak.ale.server.Tag;
import org.fosstrak.ale.server.readers.BaseReader;
import org.fosstrak.ale.server.readers.IdentifyThread;
import org.fosstrak.ale.server.util.TagHelper;
import org.fosstrak.ale.xsd.ale.epcglobal.LRSpec;
import org.fosstrak.hal.HardwareAbstraction;
import org.fosstrak.hal.HardwareException;
import org.fosstrak.hal.Observation;
import org.fosstrak.hal.Trigger;
import org.fosstrak.tdt.TDTEngine;

/**
 * adaptor for all HAL devices.
 * this adaptor allows to attach HAL devices directly to the ale
 * @author swieland
 *
 */
public class HALAdaptor extends BaseReader {
	
	/** logger. */
	private static final Logger LOG = Logger.getLogger(HALAdaptor.class);
	
	/** whenever the hal device does not support auto-polling we need to install a polling thread. */
	private IdentifyThread identifyThread = null;
	
	/** interface to the HAL device. */
	private HardwareAbstraction hal = null;
	
	/** the time intervall in which the reader will look for new tags. */
	private long pollingFrequency = -1;
	
	/** indicates whether the hal needs a pollingThread or not . */
	private boolean autoPolling = false;

	/** the properties file for this adaptor. */
	private String propertiesFile = null;
	
	/** the name of the hal device. */
	private String halName = null;
	
	/** the readpoints where shall be read from. */
	private String [] readPoints = null;
	
	/** the name of the default implementing class to be chosen. */
	public static final String DEFAULT_IMPLCLASS = "org.fosstrak.hal.impl.sim.SimulatorController";
	
	// cfg param read time interval / polling frequency.
	private static final String PARAM_READTIME_INTERVAL = "ReadTimeInterval";
	
	// cfg param read points
	private static final String PARAM_READ_POINTS = "ReadPoints"; 
	
	// cfg param properties file
	private static final String PARAM_PROPERTIES_FILE = "PropertiesFile";
	
	// cfg param implementing class
	private static final String PARAM_IMPLEMENTING_CLASS = "ImplementingClass";
	
	private static final String PARAM_PYSICAL_READER_NAME = "PhysicalReaderName";

	/**
	 * constructor for the HAL adaptor.
	 */
	public HALAdaptor() {
		super();
	}

	/**
	 * initializes a HALAdaptor. this method must be called befor the Adaptor can
	 * be used.
 	 * @param name the name for the reader encapsulated by this adaptor.
	 * @param spec the specification that describes the current reader.
	 * @throws ImplementationException whenever an internal error occurs.
	 */
	public void initialize(String name, LRSpec spec) throws ImplementationException {
		super.initialize(name, spec);

		pollingFrequency = Long.parseLong(logicalReaderProperties.get(PARAM_READTIME_INTERVAL));
		halName = logicalReaderProperties.get(PARAM_PYSICAL_READER_NAME);
		String rpS = logicalReaderProperties.get(PARAM_READ_POINTS);
		if (rpS != null) {
			readPoints = rpS.split(",");
		}
		
		// there is a problem with the HAL simulators when they try to load
		// a relative path from within a jar.
		// the easiest solution is to provide the absolute path
		propertiesFile = logicalReaderProperties.get(PARAM_PROPERTIES_FILE);
		URL url = this.getClass().getResource(propertiesFile);
		propertiesFile = url.getFile();
		
		// get the implementing class
		String implClass = logicalReaderProperties.get(PARAM_IMPLEMENTING_CLASS);
		if (implClass == null) {
			// the implementing class is missing, 
			// therefore set to the default implementor
			implClass = DEFAULT_IMPLCLASS;

			LOG.error(String.format("The implementing class is missing, therefore using the default class '%s'", 
				DEFAULT_IMPLCLASS));
		}

		hal = HALManager.getInstance().define(halName, propertiesFile, implClass);

		setDisconnected();
		setStopped();
		
		// now need to determine whether the HAL device supports auto-polling or 
		// whether we need to install a polling thread
		if (hal.supportsAsynchronousIdentify()) {
			setAutoPolling(true);
		} else {
			setAutoPolling(false);
		}
	}

	/**
	 * sets up a reader.
	 * @throws ImplementationException whenever an internal error occured
	 */
	@Override
	public void connectReader() throws ImplementationException {
		if (!isConnected()) {
			LOG.debug("Connecting reader " + getName());
			if (!isAutoPolling()) {
				LOG.debug("reader " + getName() + " needs a polling thread - setting it up.");
				// create the polling thread
				identifyThread = new IdentifyThread(this);
				identifyThread.setPollingFrequency(pollingFrequency);
				identifyThread.start();
				identifyThread.suspendIdentify();
			}
			
			setConnected();
			LOG.debug("reader " + getName() + " is connected");
		}
	}

	/**
	 * destroys a reader.
	 * @throws ImplementationException whenever an internal error occured
	 */
	@Override
	public void disconnectReader() throws ImplementationException {
		if (isConnected()) {
			if (isAutoPolling()) {
				try {
					hal.stopAsynchronousIdentify();		
				} catch (Exception e) {
					LOG.error("Could not stop Asynchronous identify:", e);
				}
			
			} else {
				// use the identifyThread
				identifyThread.stopIdentify();
				identifyThread = null;
			}
			
			setDisconnected();
			setStopped();
		}
	}

	/**
	 * starts a base reader to read tags.
	 *
	 */
	@Override
	public synchronized  void start() {
		if (!isConnected()) {
				try {
					connectReader();
				} catch (ImplementationException e) {
					LOG.info("could not start the reader " + readerName, e);
					
					return;
				}
		}
		
		if (!isStarted()) {
			if (isAutoPolling()) {
				try {
					Trigger trigger = null;
					if (pollingFrequency == 0) {
						trigger = Trigger.createContinuousTrigger();
					} else {
						trigger = Trigger.createTimerTrigger(pollingFrequency);
					}
					hal.startAsynchronousIdentify(hal.getReadPointNames(), trigger);		
				} catch (Exception e) {
					LOG.error("Could not start asynchronous identify: ", e);
				}
				
			} else {
				// use the identify thread
				identifyThread.resumeIdentify();
			}
			
			setStarted();
		}
	}

	/**
	 * stops a reader from reading tags.
	 *
	 */
	@Override
	public synchronized  void stop() {
		if (isStarted()) {
			
			if (isAutoPolling()) {
				try {
					hal.stopAsynchronousIdentify();
				} catch (Exception e) {
					LOG.info("could not stop the reader " + readerName);
				}
			 
			} else {
				// use the identify Thread
				identifyThread.suspendIdentify();
			}
			
			setStopped();
		}
	}

	/**
	 * updates a reader according the specified LRSpec.
	 * @param spec LRSpec for the reader
	 * @throws ImplementationException whenever an internal error occurs
	 */
	@Override
	public synchronized  void update(LRSpec spec) throws ImplementationException {
		try {
			// we update the properties, so stop the reader from retrieving tags
			stop();
			// set the specification
			setLRSpec(spec);
			
			// extract the pollingFrequency
			String pf = logicalReaderProperties.get(PARAM_READTIME_INTERVAL);
			if (null != pf) {
				pollingFrequency = Long.parseLong(pf);
			}
			
			readPoints = null;
			String rpS = logicalReaderProperties.get(PARAM_READ_POINTS);
			if (rpS != null) {
				readPoints = rpS.split(",");
			}
			
			// restart the reader
			start();
		} catch (Exception e) {
			 throw new ImplementationException(e.getMessage(), e);
		}
	}
	
	/**
	 * whenever a new Tag is read a notification is sent to the observers.
	 * @param tag a tag read on the reader
	 */
	@Override
	public void addTag(Tag tag) {
		//LOG.debug("HALAdaptor: notifying observers");
		tag.addTrace(getName());
		
		setChanged();
		notifyObservers(tag);
	}
	
	/**
	 * whenever new Tags are read a notification is sent to the observers.
	 * @param tags a list of tags to be added to the reader
	 */
	@Override
	public void addTags(List<Tag> tags) {
		setChanged();
		for (Tag tag : tags) {
			tag.addTrace(getName());
		}
		LOG.debug("notifying observers about " + tags.size() + " tags");
		notifyObservers(tags);
	}

	/**
	 * Triggers the identification of all tags that are currently available 
	 * on the reader. this method is used when the IdentifyThread is used to poll the adaptor.
	 * @param readPointNames the readers/sources that have to be polled
	 * @return a set of Observations
	 * @throws HardwareException whenever an internal hardware error occurs (as reader not available...)
	 */
	@Override
	public Observation[] identify(String[] readPointNames)
			throws HardwareException {
		
		LOG.debug("got observation trigger");
		Observation[] observations = null;
		if (countObservers() > 0) {
			
			try {
				synchronized (hal) {
					// if there are no readPoints specified through the 
					// lrspec, just use all available readPoints
					if (readPoints == null) {
						observations = hal.identify(hal.getReadPointNames());	
					} else {
						observations = hal.identify(readPoints);
					}
				}
			} catch (Exception e) {
				LOG.error(String.format(
						"caught exception from hal. %s",
						e.getMessage()));
			}
			// don't process if observations are null.
			if (null == observations) {
				return null;
			}
			// only process if there are tags
			if (observations.length > 0) {
				List<Tag> tags = new LinkedList<Tag>();
				
				for (Observation observation : observations) {
					try {
						// For each tag create a new Tag
						for (String tagobserved : observation.getIds()) {
							Tag tag = new Tag(getName());
							
							TDTEngine tdt = TagHelper.getTDTEngine();
							String bin = tdt.hex2bin(tagobserved);
							tag.setTagAsBinary(bin);
							
							// 96 bit length
							if ((bin.length() > 64) && (bin.length() <= 96)) {	
								// leading 00 gets truncated away by the big int. 
								if (bin.startsWith("1") && 
										(bin.length() < 96)) {
									
									bin = "00" + bin;
									tag.setTagAsBinary(bin);
								}
							}
							
							try {
								tag.setTagIDAsPureURI(
										TagHelper.convert_to_PURE_IDENTITY(
											null,
											null,
											null,
											bin)
									);
							} catch (Exception myE) {
								LOG.debug("exception when converting tag: ", myE);
							}

							tag.setTimestamp(observation.getTimestamp());
							tags.add(tag);
						}	// \\END FOR
					} catch (Exception e) {
						LOG.debug("exception when processing tags: ", e);
					}
				}
				
				// send the tags as a list
				addTags(tags);
			}
		}
		return observations;
	}

	/**
	 * indicates whether this HAL device needs a polling mechanism or not.
	 * @return boolean indicating the polling - capabilities
	 */
	private boolean isAutoPolling() {
		return autoPolling;
	}

	/**
	 * sets the polling capabilities.
	 * @param autoPolling boolean indicating if polling is supported by the HAL device
	 */
	private void setAutoPolling(boolean autoPolling) {
		this.autoPolling = autoPolling;
	}
	
	@Override
	public void cleanup() {
		HALManager.getInstance().undefine(halName);
	}
}
