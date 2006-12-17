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

package org.accada.ale.server;

import java.util.List;
import java.util.Set;

import org.accada.ale.server.readers.LogicalReaderManager;
import org.accada.ale.server.readers.LogicalReaderStub;
import org.accada.ale.server.readers.PhysicalReaderStub;
import org.accada.ale.server.readers.PhysicalSourceStub;
import org.accada.ale.wsdl.ale.epcglobal.ECSpecValidationException;
import org.accada.ale.wsdl.ale.epcglobal.ImplementationException;
import org.accada.ale.wsdl.ale.epcglobal.ImplementationExceptionSeverity;
import org.accada.reader.EventType;
import org.accada.reader.FieldName;
import org.accada.reader.msg.notification.Notification;
import org.accada.reader.msg.notification.ReadReportType;
import org.accada.reader.msg.notification.SourceInfoType;
import org.accada.reader.msg.notification.TagEventType;
import org.accada.reader.msg.notification.TagType;
import org.accada.reader.msg.notification.ReadReportType.SourceReport;
import org.accada.reader.proxy.DataSelector;
import org.accada.reader.proxy.NotificationChannel;
import org.accada.reader.proxy.NotificationChannelEndPoint;
import org.accada.reader.proxy.NotificationChannelListener;
import org.accada.reader.proxy.RPProxyException;
import org.accada.reader.proxy.ReaderDevice;
import org.accada.reader.proxy.Source;
import org.accada.reader.proxy.Trigger;
import org.accada.reader.proxy.factories.DataSelectorFactory;
import org.accada.reader.proxy.factories.NotificationChannelFactory;
import org.accada.reader.proxy.factories.ReaderDeviceFactory;
import org.accada.reader.proxy.factories.TriggerFactory;
import org.accada.reader.proxy.msg.Handshake;
import org.apache.log4j.Logger;

/**
 * This class is the connector between the reader protocol and the ALE.
 * It creates the necessary objects on the reader device to get all required data.
 * 
 * @author regli
 */
public class InputGenerator implements NotificationChannelListener {

	/** logger */
	private static final Logger LOG = Logger.getLogger(InputGenerator.class);
	
	/** prefix for each object name in the reader device created by the input generator */
	private static final String PREFIX = "InputGenerator_";
	
	/**
	 * the default read tigger name.
	 * if this name is already in use, a number will be added to the name automatically.
	 */ 
	private static final String DEFAULT_READ_TRIGGER_NAME = PREFIX + "ReadTrigger";
	
	/**
	 * the default notification channel name.
	 * if this name is already in use, a number will be added to the name automatically.
	 */
	private static final String DEFAULT_NOTIFICATION_CHANNEL_NAME = PREFIX + "NotificationChannel";

	/**
	 * the default data selector name.
	 * if this name is already in use, a number will be added to the name automatically.
	 */
	private static final String DEFAULT_DATA_SELECTOR_NAME = PREFIX + "DataSelector";

	/**
	 * the default notification trigger name.
	 * if this name is already in use, a number will be added to the name automatically.
	 */
	private static final String DEFAULT_NOTIFICATION_TRIGGER_NAME = PREFIX + "NotificationTrigger";

	// command channel parameters
	/** command channel host name */
	private final String commandChannelHost;
	/** command channel port number */
	private final int commandChannelPort;
	
	// notification parameters
	/** notification channel host name */
	private final String notificationChannelHost;
	/** notification channel port number */
	private final int notificationChannelPort;
	/** notification channel read time interval */
	private final int readTimeInterval;
	/** notification channel mode */
	private static final String NOTIFICATION_CHANNEL_MODE = "connect";
	
	/** reader device proxy */
	private ReaderDevice readerDevice;
	/** notification channel proxy */
	private NotificationChannel notificationChannel;
	/** data selector proxy */
	private DataSelector dataSelector;
	/** read trigger proxy */
	private Trigger readTrigger;
	/** notification trigger proxy */
	private Trigger notificationTrigger;
	/** notification channel end point */
	private NotificationChannelEndPoint notificationChannelEndPoint;
	
	/** reader device name */
	private String readerName;
	/** notification channel name */
	private String notificationChannelName = DEFAULT_NOTIFICATION_CHANNEL_NAME;
	/** data selector name */
	private String dataSelectorName = DEFAULT_DATA_SELECTOR_NAME;
	/** read trigger name */
	private String readTriggerName = DEFAULT_READ_TRIGGER_NAME;
	/** notification trigger name */
	private String notificationTriggerName = DEFAULT_NOTIFICATION_TRIGGER_NAME;

	/** indicates if this input generator is ready or not */
	private boolean isReady = false;
	/** indicatates if the initialization of this input generator is failed or not */
	private boolean isFailed = false;

	/**
	 * Contructor sets parameter and starts initializer.
	 * 
	 * @param commandChannelHost
	 * @param commandChannelPort
	 * @param notificationChannelHost
	 * @param notificationChannelPort
	 * @param readTimeIntervall in milliseconds
	 * @throws ImplementationException if an implementation exception occures
	 */
	public InputGenerator(String commandChannelHost, int commandChannelPort, String notificationChannelHost,
			int notificationChannelPort, int readTimeIntervall) throws ImplementationException {

		// set command channel parameters
		this.commandChannelHost = commandChannelHost;
		this.commandChannelPort = commandChannelPort;
		
		// set notification parameters
		this.notificationChannelHost = notificationChannelHost;
		this.notificationChannelPort = notificationChannelPort;
		this.readTimeInterval = readTimeIntervall;
		
		// start Initializer Thread
		try {
			new Initializer(this).start();
		} catch (Exception e) {
			throw new ImplementationException(e.getMessage(), ImplementationExceptionSeverity.ERROR);
		}
		
	}
	
	/**
	 * This method is invoked if a notification is arrived at the notification channel endpoint.
	 * 
	 * @param notification from the reader device
	 */
	public void dataReceived(Notification notification) {
		
		LOG.debug("Notification received");
		List<ReadReportType> readReports = notification.getReadReport();
		if (readReports != null) {
			for (ReadReportType readReport : readReports) {
				List<SourceReport> sourceReports = readReport.getSourceReport();
				if (sourceReports != null) {
					for (SourceReport sourceReport : sourceReports) {
						SourceInfoType sourceInfo = sourceReport.getSourceInfo();
						String sourceName = sourceInfo.getSourceName();
						List<TagType> tags = sourceReport.getTag();
						if (tags != null) {
							for (TagType tag : tags) {
								List<TagEventType> tagEvents = tag.getTagEvent();
								if (tagEvents != null) {
									for (TagEventType tagEvent : tagEvents) {
										String eventType = tagEvent.getEventType();
										LOG.debug("Tag '" + tag.getTagIDAsPureURI() + "' fired Event of type '" + eventType +"'");
										if (EventType.EV_GLIMPSED.equals(eventType)) {
											LOG.debug("Tag '" + tag.getTagIDAsPureURI() + "' entered the range of source '" +
													sourceName + "' of reader '" +  readerName + "'.");
											addToLogicalReaders(sourceName, tag);
										}
									}
								}
							}
						}
					}
				}
			}
		}
		
	}
	
	/**
	 * This method indicates if the input generator is ready or not.
	 * 
	 * @return true if the input generator is ready and false otherwise
	 */
	public boolean isReady() {
	
		return isReady;
		
	}
	
	/**
	 * This method indicates if the initialization of the input generator is failed or not.
	 * 
	 * @return true if the initialization is failed and false otherwise
	 */
	public boolean isFailed() {
		
		return isFailed;
		
	}
	
	/**
	 * This method removes all objects on the reader device which are created by this input generator.
	 */
	public void remove() {
		
		LOG.debug("Try to remove InputGenerator.");
		
		// remove Triggers, NotificationChannel and DataSelector
		if (readerDevice != null) {
			try {
				readerDevice.removeTriggers(new Trigger[] {readTrigger});
				LOG.debug("ReadTrigger '" + readTriggerName + "' successfully removed.");
			} catch (RPProxyException e) {
				e.printStackTrace();
			}
			try {
				readerDevice.removeTriggers(new Trigger[] {notificationTrigger});
				LOG.debug("NotificationTrigger '" + notificationTriggerName + "' successfully removed.");
			} catch (RPProxyException e) {
				e.printStackTrace();
			}
			try {
				readerDevice.removeNotificationChannels(new NotificationChannel[] {notificationChannel});
				LOG.debug("NotificationChannel '" + notificationChannelName + "' successfully removed.");
			} catch (RPProxyException e) {
				e.printStackTrace();
			}
			try {
				readerDevice.removeDataSelectors(new DataSelector[] {dataSelector});
				LOG.debug("DataSelector '" + dataSelectorName + "' successfully removed.");
			} catch (RPProxyException e) {
				e.printStackTrace();
			}
		}
		
		// stop NotificationChannelEndPoint
		if (notificationChannelEndPoint != null) {
			notificationChannelEndPoint.stop();
			LOG.debug("NotificationChannelEndPoint stopped.");
		}
		
		LOG.debug("InputGenerator removed");
		
	}
	
	/**
	 * This method is invoked if the notification contains a glimpsed event.
	 * The tag is added to the logical reader stubs which contains the corresponding source.
	 * 
	 * @param sourceName of the glimpes event
	 * @param tag of the glimpes event
	 */
	private void addToLogicalReaders(String sourceName, TagType tag) {
		
		// get logical reader stubs which belong to the current reader-source-pair
		Set<LogicalReaderStub> logicalReaderStubs = LogicalReaderManager.getLogicalReaderStubs(readerName, sourceName);
		
		LOG.debug("This source belongs to logical reader" + (logicalReaderStubs.size() != 1 ? "s:" : ":"));
		
		// iterate over logical reader stubs and add tag
		for (LogicalReaderStub logicalReaderStub : logicalReaderStubs) {
			LOG.debug(" - " + logicalReaderStub.getName());
			try {
				logicalReaderStub.addTag(tag);
			} catch (ImplementationException e) {
				e.printStackTrace();
			} catch (ECSpecValidationException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * This class initializes the input generator by creating objects on the reader device using the proxies.
	 * 
	 * @author regli
	 */
	private class Initializer extends Thread {
		
		/** this input generator */
		private final InputGenerator generator;

		/**
		 * Consturctor sets the input generator.
		 * 
		 * @param generator
		 */
		public Initializer(InputGenerator generator) {
			
			this.generator = generator;
			
		}
		
		/**
		 * This method contains the main loop.
		 */
		public void run() {
			
			try {
				initialize();
			} catch (RPProxyException e) {
				isFailed(e);
			}
			
		}
		
		/**
		 * This method creates objects on the reader device using the proxies.
		 * 
		 * @throws RPProxyException if a proxy operation fails
		 */
		private void initialize() throws RPProxyException {
			
			// create ReaderDevice
			LOG.debug("Create ReaderDevice Proxy");
			Handshake handshake = new Handshake();
			handshake.setTransportProtocol(Handshake.HTTP);
			handshake.setMessageFormat(Handshake.FORMAT_XML);
			readerDevice = ReaderDeviceFactory.getReaderDevice(commandChannelHost,
					commandChannelPort, handshake);
			readerName = readerDevice.getName();
			
			// create notification channel endpoint and add listener
			LOG.debug("Try to create NotificationChannelEndpoint at port '" + notificationChannelPort + "'");
			
			notificationChannelEndPoint = new NotificationChannelEndPoint(notificationChannelPort);
			notificationChannelEndPoint.addListener(generator);
			
			// create read trigger
			readTrigger = null;
			int i = 0;
			do {
				LOG.debug("Try to create ReadTrigger '" + readTriggerName + "'.");
				try {
					readTrigger = TriggerFactory.createTrigger(readTriggerName, Trigger.TIMER,
							"ms=" + readTimeInterval, readerDevice);
				} catch (RPProxyException e) {
					if ("ERROR_OBJECT_EXISTS".equals(e.getMessage())) {
						LOG.debug("Trigger '" + readTriggerName + "' already exists.");
						readTriggerName = DEFAULT_READ_TRIGGER_NAME + "_" + i++;
					} else {
						throw e;
					}
				}
			} while (readTrigger == null);
			
			// create notification channel
			notificationChannel = null;
			i = 0;
			String notificationAddress = "tcp://" + notificationChannelHost + ":" + notificationChannelPort +
					"?mode=" + NOTIFICATION_CHANNEL_MODE;
			do {
				LOG.debug("Try to create NotificationChannel '" + notificationChannelName + "'.");
				try {
					notificationChannel = NotificationChannelFactory.
							createNotificationChannel(notificationChannelName, notificationAddress, readerDevice);
				} catch (RPProxyException e) {
					if ("ERROR_OBJECT_EXISTS".equals(e.getMessage())) {
						LOG.debug("NotificationChannel '" + notificationChannelName + "' already exists.");
						notificationChannelName = DEFAULT_NOTIFICATION_CHANNEL_NAME + "_" + i++;
					} else {
						throw e;
					}
				}
			} while(notificationChannel == null);
			
			// create data selector and add it to notification channel
			dataSelector = null;
			i = 0;
			do {
				LOG.debug("Try to create DataSelector '" + dataSelectorName + "'.");
				try {
					dataSelector = DataSelectorFactory.createDataSelector(dataSelectorName, readerDevice);
				} catch (RPProxyException e) {
					if ("ERROR_OBJECT_EXISTS".equals(e.getMessage())) {
						LOG.debug("DataSelector '" + dataSelectorName + "' already exists.");
						dataSelectorName = DEFAULT_DATA_SELECTOR_NAME + "_" + i++;
					} else {
						throw e;
					}
				}
			} while (dataSelector == null);
			dataSelector.addFieldNames(new String[] {FieldName.EVENT_TYPE, FieldName.READER_NAME, FieldName.TAG_ID, 
					FieldName.TAG_ID_AS_PURE_URI, FieldName.TAG_ID_AS_TAG_URI, FieldName.SOURCE_NAME});
			dataSelector.addEventFilters(new String[] {EventType.EV_GLIMPSED});
			notificationChannel.setDataSelector(dataSelector);
			
			// add sources to notification channel
			LOG.debug("Add Sources to NotificationChannel");
			Source[] sources = readerDevice.getAllSources();
			notificationChannel.addSources(sources);
			
			// create notification trigger and add it to notification channel
			notificationTrigger = null;
			i = 0;
			do {
				LOG.debug("Try to create NotificationTrigger '" + notificationTriggerName + "'.");
				try {
					notificationTrigger = TriggerFactory.createTrigger(notificationTriggerName, Trigger.TIMER,
						"ms=" + readTimeInterval, readerDevice);
				} catch (RPProxyException e) {
					if ("ERROR_OBJECT_EXISTS".equals(e.getMessage())) {
						LOG.debug("Trigger '" + notificationTriggerName + "' already exists.");
						notificationTriggerName = DEFAULT_NOTIFICATION_TRIGGER_NAME + "_" + i++;
					} else {
						throw e;
					}
				}
			} while (notificationTrigger == null);
			LOG.debug("Add NotificationTrigger to NotificationChannel");
			notificationChannel.addNotificationTriggers(new Trigger[] {notificationTrigger});
			
			// create physical reader stub
			LOG.debug("Create PhysicalReaderStub '" + readerName + "'.");
			PhysicalReaderStub physicalReaderStub = new PhysicalReaderStub(readerName);
			
			// create a physical source stub for each source
			LOG.debug("Create a PhysicalSourceStub for each source.");
			sources = readerDevice.getAllSources();
			for (Source source : sources) {
				physicalReaderStub.addSourceStub(new PhysicalSourceStub(source, readTrigger, dataSelector));
			}
			
			// add physical reader stub to logical reader manager
			LOG.debug("Add PhysicalReaderStub '" + readerName + "' to LogicalReaderManager.");
			LogicalReaderManager.addPhysicalReaderStub(physicalReaderStub);
			
			// set isReady
			isReady = true;
			
			// notify all when InputGenerator is ready
			synchronized (generator) {
				generator.notifyAll();
			}
			
			LOG.info("Input Generator ready !");
			
		}

		/**
		 * This method is invoked if the initialization is failed.
		 * An error message will be displayed in log.
		 * 
		 * @param exception
		 */
		private void isFailed(Exception exception) {
			
			isFailed = true;
			LOG.error("InputGenerator '" + commandChannelHost + ":" + commandChannelPort + "' initialization failed. (" + exception.getMessage() + ")");
			
			// notify all when InputGenerator is failed
			synchronized (generator) {
				generator.notifyAll();
			}
			
		}
		
	}
	
}