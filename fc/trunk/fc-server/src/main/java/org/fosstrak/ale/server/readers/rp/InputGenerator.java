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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.accada.ale.server.Tag;
import org.accada.ale.wsdl.ale.epcglobal.ImplementationException;
import org.accada.ale.wsdl.ale.epcglobal.ImplementationExceptionSeverity;
import org.accada.reader.rprm.core.EventType;
import org.accada.reader.rprm.core.FieldName;
import org.accada.reader.rprm.core.msg.notification.Notification;
import org.accada.reader.rprm.core.msg.notification.ReadReportType;
import org.accada.reader.rprm.core.msg.notification.SourceInfoType;
import org.accada.reader.rprm.core.msg.notification.TagEventType;
import org.accada.reader.rprm.core.msg.notification.TagType;
import org.accada.reader.rprm.core.msg.notification.ReadReportType.SourceReport;
import org.accada.reader.rp.proxy.DataSelector;
import org.accada.reader.rp.proxy.NotificationChannel;
import org.accada.reader.rp.proxy.NotificationChannelEndPoint;
import org.accada.reader.rp.proxy.NotificationChannelListener;
import org.accada.reader.rp.proxy.RPProxyException;
import org.accada.reader.rp.proxy.ReaderDevice;
import org.accada.reader.rp.proxy.Source;
import org.accada.reader.rp.proxy.Trigger;
import org.accada.reader.rp.proxy.factories.DataSelectorFactory;
import org.accada.reader.rp.proxy.factories.NotificationChannelFactory;
import org.accada.reader.rp.proxy.factories.ReaderDeviceFactory;
import org.accada.reader.rp.proxy.factories.TriggerFactory;
import org.accada.reader.rp.proxy.msg.Handshake;
import org.apache.log4j.Logger;

/**
 * This class is the connector between the reader protocol and the ALE.
 * It creates the necessary objects on the reader device to get all required data.
 * 
 * @author sawielan
 * @author regli
 */
public class InputGenerator implements NotificationChannelListener {

	/** logger. */
	private static final Logger LOG = Logger.getLogger(InputGenerator.class);
	
	/** prefix for each object name in the reader device created by the input generator. */
	private static final String PREFIX = "InputGenerator_";
	
	/**
	 * the default read trigger name.
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
	/** command channel host name. */
	private String commandChannelHost;
	/** command channel port number. */
	private int commandChannelPort;
	
	// notification parameters
	/** notification channel host name. */
	private String notificationChannelHost;
	/** notification channel port number. */
	private int notificationChannelPort;
	/** notification channel read time interval. */
	private int readTimeInterval;
	/** notification channel mode. */
	private static final String NOTIFICATION_CHANNEL_MODE = "connect";
	
	/** reader device proxy. */
	private ReaderDevice readerDevice;
	/** notification channel proxy. */
	private NotificationChannel notificationChannel;
	/** data selector proxy. */
	private DataSelector dataSelector;
	/** read trigger proxy .*/
	private Trigger readTrigger;
	/** notification trigger proxy. */
	private Trigger notificationTrigger;
	/** notification channel end point. */
	private NotificationChannelEndPoint notificationChannelEndPoint;
	
	/** reader device name. */
	private String readerName;
	/** notification channel name. */
	private String notificationChannelName = DEFAULT_NOTIFICATION_CHANNEL_NAME;
	/** data selector name. */
	private String dataSelectorName = DEFAULT_DATA_SELECTOR_NAME;
	/** read trigger name. */
	private String readTriggerName = DEFAULT_READ_TRIGGER_NAME;
	/** notification trigger name. */
	private String notificationTriggerName = DEFAULT_NOTIFICATION_TRIGGER_NAME;

	/** indicates if this input generator is ready or not. */
	private boolean isReady = false;
	/** indicates if the initialization of this input generator is failed or not. */
	private boolean isFailed = false;
	
	/** the physical sources where tags are read (eg shelf1, shelf2). */
	private Set<String> physicalSources = new HashSet<String>();
	
	/** reader to which the input generator belongs. */
	private RPAdaptor reader = null;

	/**
	 * Constructor sets parameter and starts initializer.
	 * 
	 * @param commandChannelHost the host to connect for the commandChannel 
	 * @param commandChannelPort the port for the commandChannel
	 * @param notificationChannelHost the host for the notificationChannel
	 * @param notificationChannelPort the port for the notificationChannel
	 * @param readTimeIntervall in milliseconds
	 * @param reader the reader holding this inputGenerator
	 * @param physicalSources the physical sources where tags are read (eg shelf1, shelf2)
	 * @throws ImplementationException if an implementation exception occurs
	 */
	public InputGenerator(RPAdaptor reader, 
			String commandChannelHost, 
			int commandChannelPort, 
			String notificationChannelHost,
			int notificationChannelPort, 
			int readTimeIntervall,
			String [] physicalSources
			) throws ImplementationException {

		this.reader = reader;
				
		// set command channel parameters
		this.commandChannelHost = commandChannelHost;
		this.commandChannelPort = commandChannelPort;
		
		// set notification parameters
		this.notificationChannelHost = notificationChannelHost;
		this.notificationChannelPort = notificationChannelPort;
		this.readTimeInterval = readTimeIntervall;
		
		// add the physical sources
		for (String source : physicalSources) {
			this.physicalSources.add(source);
		}
		
		// start Initializer Thread
		try {
			new Initializer(this).start();
		} catch (Exception e) {
			throw new ImplementationException(e.getMessage(), ImplementationExceptionSeverity.ERROR);
		}
		
	}
	
	/**
	 * This method is invoked if a notification is arrived at the notification channel end-point.
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
										LOG.debug("Tag '" + tag.getTagIDAsPureURI() + "' fired Event of type '" + eventType + "'");
										if (EventType.EV_GLIMPSED.equals(eventType)) {
											LOG.debug("Tag '" + tag.getTagIDAsPureURI() + "' entered the range of source '" 
													+	sourceName + "' of reader '" +  readerName + "'.");
											
											Tag newTag = new Tag(reader.getName());
											newTag.setTagID(tag.getTagIDAsPureURI());
											newTag.setTimestamp(System.currentTimeMillis());
											addToReader(sourceName, newTag);
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
	 * the tag is then passed to the reader containing this input generator.
	 * 
	 * @param sourceName of the glimpes event
	 * @param tag of the glimpes event
	 */
	private void addToReader(String sourceName, Tag tag) {
		
		reader.addTag(tag);
	}
	
	/**
	 * This class initializes the input generator by creating objects on the reader device using the proxies.
	 * 
	 * @author regli
	 */
	private class Initializer extends Thread {
		
		/** this input generator. */
		private final InputGenerator generator;

		/**
		 * Constructor sets the input generator.
		 * 
		 * @param generator the inputGenerator for this reader.
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
			
			LOG.debug("-----------------------------------------------------------");
			LOG.debug("Start initializing InputGenerator...");
			LOG.info("Try connecting to reader devices...");
			
			// create ReaderDevice
			LOG.debug("Create ReaderDevice Proxy");
			Handshake handshake = new Handshake();
			handshake.setTransportProtocol(Handshake.HTTP);
			handshake.setMessageFormat(Handshake.FORMAT_XML);
			readerDevice = ReaderDeviceFactory.getReaderDevice(commandChannelHost,
					commandChannelPort, handshake);
			readerName = readerDevice.getName();

			// create notification channel endpoint and add listener
			LOG.debug("Try to create NotificationChannelEndpoint at port '" + notificationChannelPort + "'...");
			notificationChannelEndPoint = new NotificationChannelEndPoint(notificationChannelPort);
			notificationChannelEndPoint.addListener(generator);
			LOG.debug("NotificationChannelEndpoint at port '" + notificationChannelPort + "' created.");			
			
			
			// create read trigger
			readTrigger = TriggerFactory.createTrigger(readTriggerName, Trigger.TIMER, 
						String.format("ms=%d", readTimeInterval), readerDevice);

			LOG.debug("created read trigger: " + readTriggerName);

			// create notification trigger
			notificationTrigger = TriggerFactory.createTrigger(notificationTriggerName, Trigger.CONTINUOUS,
						"", readerDevice);
			LOG.debug("created notification trigger: " + notificationTriggerName);

			// create the data selector
			LOG.debug("create DataSelector");
			dataSelector = DataSelectorFactory.createDataSelector(dataSelectorName, readerDevice);
			LOG.debug("adding Fieldnames to dataSelector");
			dataSelector.addFieldNames(new String[] {FieldName.EVENT_TYPE, 
								FieldName.READER_NAME, 
								FieldName.TAG_ID, 
								FieldName.TAG_ID_AS_PURE_URI, 
								FieldName.TAG_ID_AS_TAG_URI, 
								FieldName.SOURCE_NAME});
			LOG.debug("adding eventFilters to dataSelector");
			dataSelector.addEventFilters(new String[] {EventType.EV_GLIMPSED});
			
			
			// create notification channel
			String notificationAddress = "tcp://" + notificationChannelHost + ":" + notificationChannelPort
						+ "?mode=" + NOTIFICATION_CHANNEL_MODE;

			LOG.debug("create NotificationChannel" + notificationChannelName);
			notificationChannel = NotificationChannelFactory.createNotificationChannel(
							notificationChannelName, 
							notificationAddress, readerDevice);
			LOG.debug("NotificationChannel " + notificationChannelName + " created");
			
			LOG.debug("adding dataSelector to notificationChannel");
			notificationChannel.setDataSelector(dataSelector);
			LOG.debug("adding notificationTrigger to notificationChannel");
			notificationChannel.addNotificationTriggers(new Trigger[] {notificationTrigger});


			// add sources to notification channel
			LOG.debug("Add Sources to NotificationChannel...");
			Source[] sources = readerDevice.getAllSources();
			notificationChannel.addSources(sources);
			LOG.debug("Sources were added to NotificationChannel.");
			
			LOG.debug("add readTriggers to the sources");
			for (Source source : sources) {
				if (physicalSources.contains(source.getName())) {
					LOG.debug("adding physical source " + source.getName());
					source.addReadTriggers(new Trigger[] {readTrigger});
				}
			}

			// set isReady
			isReady = true;
						
			// notify all when InputGenerator is ready
			synchronized (generator) {
				generator.notifyAll();
			}
		
			LOG.debug("InputGenerator initialized.");
			LOG.info("Connection to reader devices established.");
			LOG.debug("-----------------------------------------------------------");			
		}

		/**
		 * This method is invoked if the initialization is failed.
		 * An error message will be displayed in log.
		 * 
		 * @param exception exception thrown
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

