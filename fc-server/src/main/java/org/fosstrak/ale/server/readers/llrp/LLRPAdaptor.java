package org.fosstrak.ale.server.readers.llrp;

import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.fosstrak.ale.server.Tag;
import org.fosstrak.ale.server.readers.BaseReader;
import org.fosstrak.ale.wsdl.ale.epcglobal.ImplementationException;
import org.fosstrak.ale.wsdl.ale.epcglobal.ImplementationExceptionResponse;
import org.fosstrak.ale.xsd.ale.epcglobal.LRSpec;
import org.fosstrak.hal.HardwareException;
import org.fosstrak.hal.Observation;
import org.fosstrak.llrp.adaptor.Constants;
import org.fosstrak.llrp.adaptor.Reader;
import org.fosstrak.llrp.adaptor.exception.LLRPRuntimeException;
import org.fosstrak.tdt.TDTEngine;
import org.llrp.ltk.exceptions.InvalidLLRPMessageException;
import org.llrp.ltk.generated.LLRPMessageFactory;
import org.llrp.ltk.generated.interfaces.EPCParameter;
import org.llrp.ltk.generated.messages.RO_ACCESS_REPORT;
import org.llrp.ltk.generated.parameters.AntennaID;
import org.llrp.ltk.generated.parameters.EPC_96;
import org.llrp.ltk.generated.parameters.TagReportData;
import org.llrp.ltk.types.Integer96_HEX;
import org.llrp.ltk.types.LLRPMessage;
import org.llrp.ltk.types.UnsignedShort;

/**
 * this class implements the adaptor from a logical reader in the filtering and 
 * collection to the physical llrp readers. <br/>
 * the management of the readers is performed by the llrp-client-adaptor. This 
 * adaptor maintains a list of all the readers currently configured on this 
 * filtering and collection server and maintains the corresponding connections.<br/>
 * this adaptor will use the LLRPManager from the filtering and collection to 
 * get a reference to these llrp readers. the adaptor just registers itself for 
 * read notifications. whenever such a notification occurs the epc codes (if 
 * contained) are extracted from the notification and are then propagated to 
 * the filtering and collection framework.
 * @author sawielan
 *
 */
public class LLRPAdaptor extends BaseReader {

	/** logger. */
	private static final Logger log = Logger.getLogger(LLRPAdaptor.class);

	/** reference to the singleton of the llrp manager. */
	private LLRPManager manager = null;
	
	/** reference to the reader */
	private Reader reader = null;

	/** the name of the physical reader that this adaptor shall connect to (name in the llrp gui client adaptor) */
	private String physicalReaderName = null;
	
	/** the message callback. */
	private Callback callback = null;
	
	/** 
	 * if the hash set is empty, allow from all the antennas, otherwise only 
	 * tags arriving from the specified antenna IDs.
	 */
	private Set<Integer> acceptTagsFromAntennas = new HashSet<Integer>(); 
	
	/**
	 * constructor for the LLRP adaptor.
	 */
	public LLRPAdaptor() {
		super();
		manager = LLRPManager.getInstance();
		
		try {
			callback = new Callback(this);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * initializes a LLRPAdaptor. this method must be called before the Adaptor can
	 * be used.
 	 * @param name the name for the reader encapsulated by this adaptor.
	 * @param spec the specification that describes the current reader.
	 * @throws ImplementationException whenever an internal error occurs.

	 */
	public void initialize(String name, LRSpec spec) throws ImplementationExceptionResponse {
		super.initialize(name, spec);
		
		if ((name == null) || (spec == null)) {
			log.error("reader name or LRSpec is null.");
			throw new ImplementationExceptionResponse("reader name or LRSpec is null.");
		}
		
		physicalReaderName = logicalReaderProperties.get("PhysicalReaderName");
				
		try {
			log.debug("create a new LLRP reader");
			manager = LLRPManager.getInstance();
			if (manager != null) {
				
				// if the reader is not contained in the manager we just create a 
				// new one...
				if (!manager.getAdaptor().containsReader(physicalReaderName)) {
					String ip = logicalReaderProperties.get("ip");
					
					int port = Constants.DEFAULT_LLRP_PORT;
					if (logicalReaderProperties.get("port") != null) {
						port = Integer.parseInt(logicalReaderProperties.get("port"));
					}
					
					boolean clientInitiated = true;
					if (logicalReaderProperties.get("clientInitiated") == null) {
						log.warn("clientInitiated not set, assuming true");
					} else {
						clientInitiated = Boolean.parseBoolean(logicalReaderProperties.get("clientInitiated"));
					}
					
					if (ip == null) {
						log.error("llrp reader '" + physicalReaderName + "' is missing " +
								"and not enough parameters specified. (Needs PhysicalReaderName, ip, port) !");
						throw new ImplementationExceptionResponse("llrp reader is missing and not " +
								"enough parameters specified. (Needs PhysicalReaderName, ip, port, clientInitiated) !");
					}
					log.debug(String.format(
							"defining new reader with settings:" +
							"name: %s\nip: %s\nport: %d\nclientinitiated: %b",
							physicalReaderName,
							ip,
							port, 
							clientInitiated));
					
					// create the reader but do not immediately connect.
					manager.getAdaptor().define(physicalReaderName, ip, port, clientInitiated, false);
				}
				
				// get the antenna IDs to read from
				String antennaIDSStr = logicalReaderProperties.get("antennaID");
				if (null != antennaIDSStr) {
					String[] ai = antennaIDSStr.split(",");
					for (String str : ai) {
						try {
							int i = Integer.parseInt(str);
							acceptTagsFromAntennas.add(new Integer(i));
						} catch (Exception e) {
							log.debug(String.format("Illegal antennaID: %s", str));
						}
					}
				}
								
				reader = manager.getAdaptor().getReader(physicalReaderName);
				
				// register the adaptor for asynchronous notifications
				log.debug("register the adaptor for asynchronous notifications from the llrp reader");
				reader.registerForAsynchronous(callback);
				
				// if the reader is not yet connected do so
				if (!reader.isConnected()) {
					reader.connect(reader.isClientInitiated());
				}
			} else {
				log.error("could not get an instance of the LLRPManager - aborting");
				throw new ImplementationExceptionResponse("could not get an instance of the LLRPManager - aborting");
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error when initializing the Reader");
			throw new ImplementationExceptionResponse("Error when initializing the Reader");
		}
		
		manager.incReference(physicalReaderName);
		
		connectReader();
	}
	
	@Override
	public void addTag(Tag tag) {
		tag.setOrigin(getName());
		tag.setReader(getName());
		
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
	public void connectReader() throws ImplementationExceptionResponse {
		// get the required reader and register for asynchronous messages.
		try {
			Reader llrpReader = manager.getAdaptor().getReader(physicalReaderName);
			llrpReader.registerForAsynchronous(callback);
			setConnected();
			
		} catch (RemoteException e) {
			log.error("could not connect to the reader");
			throw new ImplementationExceptionResponse("Error when connecting the Reader");
		}
	}

	@Override
	public void disconnectReader() throws ImplementationExceptionResponse {
		// get the required reader and register for asynchronous messages.
		try {
			Reader llrpReader = manager.getAdaptor().getReader(physicalReaderName);
			llrpReader.deregisterFromAsynchronous(callback);
			setDisconnected();
			
		} catch (RemoteException e) {
			log.error("could not disconnect from the reader");
			throw new ImplementationExceptionResponse("Error when disconnecting the Reader");
		}
	}

	@Override
	public void start() {
		if (isConnected()) {
			setStarted();
		}
	}

	@Override
	public void stop() {
		setStopped();
	}
	
	@Override
	public void cleanup() {
		try {
			manager.decReferenceCount(physicalReaderName);
		} catch (RemoteException e) {
			log.error("there has been an rmi exception: ");
			e.printStackTrace();
		} catch (LLRPRuntimeException e) {
			log.error("there has been an exception in the reader module: ");
			e.printStackTrace();
		}
	}

	@Override
	public void update(LRSpec spec) throws ImplementationExceptionResponse {
		log.info("you cannot update the reader through fc yet. use the llrp gui client for this purpose please.");
	}

	@Override
	public Observation[] identify(String[] readPointNames)
			throws HardwareException {
		// the llrp readers do not support identify threads.
		return null;
	}

	public void notify(byte[] binaryMessage, String readerName) throws RemoteException {
		log.debug("notify");
		try {
			List<Tag> tags = new LinkedList<Tag>();
			LLRPMessage message = LLRPMessageFactory.createLLRPMessage(binaryMessage);
			
			if (message instanceof RO_ACCESS_REPORT) {
				RO_ACCESS_REPORT report = (RO_ACCESS_REPORT)message;
				List<TagReportData> tagDataList = report.getTagReportDataList();
				for (TagReportData tagData : tagDataList) {
					boolean include = false;
					if (0 == acceptTagsFromAntennas.size()) {
						include = true;
					} else {
						AntennaID antennaID = tagData.getAntennaID();
						if ((null != antennaID) && 
								(null != antennaID.getAntennaID())) {
							
							int id = antennaID.getAntennaID().intValue();
							if (acceptTagsFromAntennas.contains(new Integer(id))) {
								include = true;
							}
						}
					}
					EPCParameter epcParameter = tagData.getEPCParameter();
					if ((include) && (epcParameter instanceof EPC_96)) {
						EPC_96 epc96 = (EPC_96) epcParameter;
						Integer96_HEX hex = epc96.getEPC();
						String hx = hex.toString();
						Tag tag = null;
						TDTEngine tdt = Tag.getTDTEngine();
						try {
							String binary = tdt.hex2bin(hx);
							if (binary.startsWith("1") && 
									(binary.length() < 96)) {
								
								binary = "00" + binary;
							}
							
							tag = new Tag(readerName);
							tag.setTagAsBinary(binary);
							tag.setTagID(binary.getBytes());
							tag.setReader(readerName);
							tag.addTrace(getName());
							tag.setTimestamp(System.currentTimeMillis());

							// add the tag.
							tags.add(tag);
						} catch (Exception e) {
							log.debug("bad error, ignoring tag: " + e.getMessage());
						}
						
						// try to run a conversion on the tag...
						if (null != tag) {
							try {								
								String pureID = Tag.convert_to_PURE_IDENTITY(
										null, 
										null, 
										null, 
										tag.getTagAsBinary());
								tag.setTagIDAsPureURI(pureID);
							} catch (Exception e) {
								log.debug("could not convert provided tag: " + e.getMessage());
							}
						}
					}
				}
			}
			
			// send the tags to fc
			addTags(tags);
		} catch (InvalidLLRPMessageException e) {
			log.info("received invalid llrp message that could not be converted from binary");
		}
		
	}
}
