package org.fosstrak.ale.server.type;

import java.io.CharArrayWriter;

import org.apache.log4j.Logger;
import org.fosstrak.ale.exception.ImplementationException;
import org.fosstrak.ale.util.SerializerUtil;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReports;

/**
 * This abstract class allows to implement different notification channels for the ALE (like File/Socket/Raw-TCP).
 * @author swieland
 */
public abstract class AbstractSubscriberOutputChannel implements SubscriberOutputChannel {
	
	/** logger */
	private static final Logger LOG = Logger.getLogger(AbstractSubscriberOutputChannel.class);
	
	private final String notificationURI;
	
	/**
	 * constructor requiring notification uri.
	 * @param notificationURI
	 */
	public AbstractSubscriberOutputChannel(String notificationURI) {
		this.notificationURI = notificationURI;
	}
	
	/**
	 * This method serializes ec reports into a xml representation.
	 * 
	 * @param reports the report to be serialized.
	 * @return xml representation of the ec reports
	 * @throws ImplementationException if a implementation exception occurs
	 */
	protected String getXml(ECReports reports) throws ImplementationException {
	
		CharArrayWriter writer = new CharArrayWriter();
		try {			
			SerializerUtil.serializeECReports(reports, writer);
		} catch (Exception e) {
			LOG.debug("could not serialize the reports", e);
			throw new ImplementationException("Unable to serialize reports.", e);
		}
		return writer.toString();
		
	}
	
	/**
	 * This method serializes ec reports into a well formed xml representation. 
	 * 
	 * @param reports to serialize
	 * @return well formed xml representation of the ec reports
	 * @throws ImplementationException if a implementation exception occurs
	 */
	protected String getPrettyXml(ECReports reports) throws ImplementationException {
		
		CharArrayWriter writer = new CharArrayWriter();
		try {
			SerializerUtil.serializeECReports(reports, writer);
		} catch (Exception e) {
			throw new ImplementationException("Unable to serialize reports", e);
		}
		return writer.toString();
	}

	/**
	 * return a handle onto the notification URI.
	 * @return the notification URI.
	 */
	public String getNotificationURI() {
		return notificationURI;
	}
}

