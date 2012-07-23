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

package org.fosstrak.ale.server.readers.rp;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.fosstrak.ale.exception.ImplementationException;
import org.fosstrak.ale.server.Tag;
import org.fosstrak.ale.server.util.TagHelper;
import org.fosstrak.reader.rp.proxy.DataSelector;
import org.fosstrak.reader.rp.proxy.NotificationChannel;
import org.fosstrak.reader.rp.proxy.NotificationChannelEndPoint;
import org.fosstrak.reader.rp.proxy.NotificationChannelListener;
import org.fosstrak.reader.rp.proxy.RPProxyException;
import org.fosstrak.reader.rp.proxy.ReadReport;
import org.fosstrak.reader.rp.proxy.ReaderDevice;
import org.fosstrak.reader.rp.proxy.Source;
import org.fosstrak.reader.rp.proxy.Trigger;
import org.fosstrak.reader.rp.proxy.factories.DataSelectorFactory;
import org.fosstrak.reader.rp.proxy.factories.NotificationChannelFactory;
import org.fosstrak.reader.rp.proxy.factories.ReaderDeviceFactory;
import org.fosstrak.reader.rp.proxy.factories.TriggerFactory;
import org.fosstrak.reader.rp.proxy.msg.Handshake;
import org.fosstrak.reader.rprm.core.EventType;
import org.fosstrak.reader.rprm.core.FieldName;
import org.fosstrak.reader.rprm.core.msg.notification.Notification;
import org.fosstrak.reader.rprm.core.msg.reply.ReadReportType;
import org.fosstrak.reader.rprm.core.msg.reply.ReadReportType.SourceReport;
import org.fosstrak.reader.rprm.core.msg.reply.TagEventType;
import org.fosstrak.reader.rprm.core.msg.reply.TagType;

/**
 * This class is the connector between the reader protocol and the ALE.
 * It creates the necessary objects on the reader device to get all required data.
 * 
 * @author swieland
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
	
	/** adaptor to which the input generator belongs. */
	private RPAdaptor adaptor = null;

	/**
	 * Constructor sets parameter and starts initializer.
	 * 
	 * @param adaptor the adaptor holding this inputGenerator
	 * @throws ImplementationException if an implementation exception occurs
	 */
	public InputGenerator(RPAdaptor adaptor) throws ImplementationException {

		this.adaptor = adaptor;

		// start Initializer Thread
		try {
			new Initializer(this).start();
		} catch (Exception e) {
			throw new ImplementationException("could not initialize the input generator", e);
		}
		
	}
	
	/**
	 * This method is invoked if a notification is arrived at the notification channel end-point.
	 * 
	 * @param notification from the reader device
	 */
	public void dataReceived(Notification notification) {
		LOG.debug("Notification received but not processed - polling mode is used");
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
				LOG.debug("caught exception", e);
			}
			try {
				readerDevice.removeTriggers(new Trigger[] {notificationTrigger});
				LOG.debug("NotificationTrigger '" + notificationTriggerName + "' successfully removed.");
			} catch (RPProxyException e) {
				LOG.debug("caught exception", e);
			}
			try {
				readerDevice.removeNotificationChannels(new NotificationChannel[] {notificationChannel});
				LOG.debug("NotificationChannel '" + notificationChannelName + "' successfully removed.");
			} catch (RPProxyException e) {
				LOG.debug("caught exception", e);
			}
			try {
				readerDevice.removeDataSelectors(new DataSelector[] {dataSelector});
				LOG.debug("DataSelector '" + dataSelectorName + "' successfully removed.");
			} catch (RPProxyException e) {
				LOG.debug("caught exception", e);
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
	 * polls the rp-proxy for all tags available.
	 * @throws RPProxyException when the polling failed
	 */
	public void poll() throws RPProxyException {
		
		//LOG.debug("Polling the rp-proxy");
		
		// get all the sources from the device
		Source[] sources = readerDevice.getAllSources();
		if (sources != null) {
			for (Source source : sources) {
				// remove all data-selectors on the source 
				// this is important !!!!!!!!!!!!! 
				// otherwise you will not get any tags
				source.removeAllTagSelectors();
				
				// read all ids on the source
				// just for safety pass an empty dataSelector
				ReadReport readReport = source.rawReadIDs(null);

				
				if (readReport != null) {
					// get the readReport holding the sourceReports
					ReadReportType report = readReport.getReport();
					// get the sourceReports
					List<SourceReport> sourceReports = report.getSourceReport();
					
					List<Tag> tagsToNotify = new LinkedList<Tag>();
					// for each sourceReport process the tags
					for (SourceReport sourceReport : sourceReports) {
						List<TagType> tags = sourceReport.getTag();
						if (tags != null) {
							// for each tag create a new reader api tag
							for (TagType tag : tags) {
								List<TagEventType> tagEvents = tag.getTagEvent();
								if (tagEvents != null) {
									for (TagEventType tagEvent : tagEvents) {
										String eventType = tagEvent.getEventType();
										//LOG.debug("Tag '" + tag.getTagIDAsPureURI() + "' fired Event of type '" + eventType + "'");
										if (EventType.EV_OBSERVED.equals(eventType)) {
											// somehow rp delivers binary instead 
											// pure uri
											String binary = tag.getTagIDAsPureURI();
											Tag nt = new Tag(
													adaptor.getName(), 
													tag.getTagID(), 
													TagHelper.convert_to_PURE_IDENTITY(
															"64",
															"1",
															"7",
															binary), 
													System.currentTimeMillis());

											nt.setTagAsBinary(binary);
											tagsToNotify.add(nt);
										}
									}
								}
							}
						}
					}
					
					if (tagsToNotify.size() > 0) {
						adaptor.addTags(tagsToNotify);
					}
					
				} else {
					LOG.debug("readReport null");
				}
			}
		} else {
			LOG.debug("sources null");
		}
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
			readerDevice = ReaderDeviceFactory.getReaderDevice(adaptor.getCommandChannelHost(),
					adaptor.getCommandChannelPort(), handshake);

			// create notification channel endpoint and add listener
			LOG.debug("Try to create NotificationChannelEndpoint at port '" + adaptor.getNotificationChannelPort() + "'...");
			notificationChannelEndPoint = new NotificationChannelEndPoint(adaptor.getNotificationChannelPort());
			notificationChannelEndPoint.addListener(generator);
			LOG.debug("NotificationChannelEndpoint at port '" + adaptor.getNotificationChannelPort() + "' created.");			
			
			
			// create read trigger
			readTrigger = TriggerFactory.createTrigger(readTriggerName, Trigger.TIMER, 
						String.format("ms=%d", adaptor.getReadTimeInterval()), readerDevice);

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
								FieldName.SOURCE_NAME});
			
			// create notification channel
			String notificationAddress = "tcp://" + adaptor.getNotificationChannelHost() + ":" + adaptor.getNotificationChannelPort()
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
				if (adaptor.getSources().contains(source.getName())) {
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
			LOG.error("InputGenerator '" + adaptor.getCommandChannelHost() + ":" + adaptor.getCommandChannelPort() + "' initialization failed. (" + exception.getMessage() + ")");
			
			// notify all when InputGenerator is failed
			synchronized (generator) {
				generator.notifyAll();
			}
			
		}		
	}
}

