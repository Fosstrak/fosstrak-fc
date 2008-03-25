package org.accada.ale.server.readers.hal;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import org.accada.ale.server.Tag;
import org.accada.ale.server.readers.BaseReader;
import org.accada.ale.server.readers.IdentifyThread;
import org.accada.ale.wsdl.ale.epcglobal.ImplementationException;
import org.accada.ale.wsdl.ale.epcglobal.ImplementationExceptionResponse;
import org.accada.ale.xsd.ale.epcglobal.LRSpec;
import org.accada.hal.HardwareAbstraction;
import org.accada.hal.HardwareException;
import org.accada.hal.Observation;
import org.accada.hal.Trigger;
import org.apache.log4j.Logger;

/**
 * adaptor for all HAL devices.
 * this adaptor allows to attach HAL devices directly to the ale
 * @author sawielan
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
	public void initialize(String name, LRSpec spec) throws ImplementationExceptionResponse {
		super.initialize(name, spec);

		pollingFrequency = Long.parseLong(logicalReaderProperties.get("ReadTimeInterval"));
		halName = logicalReaderProperties.get("PhysicalReaderName");
		String rpS = logicalReaderProperties.get("ReadPoints");
		if (rpS != null) {
			readPoints = rpS.split(",");
		}
		
		// there is a problem with the HAL simulators when they try to load
		// a relative path from within a jar.
		// the easiest solution is to provide the absolute path
		propertiesFile = logicalReaderProperties.get("PropertiesFile");
		URL url = this.getClass().getResource(propertiesFile);
		propertiesFile = url.getFile();
				
		hal = HALManager.getInstance().define(halName, propertiesFile);

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
	public void connectReader() throws ImplementationExceptionResponse {
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
	public void disconnectReader() throws ImplementationExceptionResponse {
		if (isConnected()) {
			if (isAutoPolling()) {
				try {
					hal.stopAsynchronousIdentify();		
				} catch (Exception e) {
					e.printStackTrace();
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
				} catch (ImplementationExceptionResponse e) {
					LOG.info("could not start the reader " + readerName);
					e.printStackTrace();
					
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
					e.printStackTrace();
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
	public synchronized  void update(LRSpec spec) throws ImplementationExceptionResponse {
		
		// we update the properties, so stop the reader from retrieving tags
		stop();
		// set the specification
		setLRSpec(spec);
		// extract the pollingFrequency
		pollingFrequency = Long.parseLong(logicalReaderProperties.get("pollingFrequency"));
		
		readPoints = null;
		String rpS = logicalReaderProperties.get("ReadPoints");
		if (rpS != null) {
			readPoints = rpS.split(",");
		}
		
		// restart the reader
		start();
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
			
			// if there are no readPoints specified through the 
			// lrspec, just use all available readPoints
			if (readPoints == null) {
				observations = hal.identify(hal.getReadPointNames());	
			} else {
				observations = hal.identify(readPoints);
			}
			
			// only process if there are tags
			if (observations.length > 0) {
				List<Tag> tags = new LinkedList<Tag>();
				for (Observation observation : observations) {
					
					// For each tag create a new Tag
					for (String tagobserved : observation.getIds()) {
						Tag tag = new Tag(getName());
						tag.setTagIDAsPureURI(tagobserved);
						tag.setTimestamp(observation.getTimestamp());
						tags.add(tag);
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
