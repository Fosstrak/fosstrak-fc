package org.fosstrak.ale.server.readers.llrp;

import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;
import org.fosstrak.ale.exception.ImplementationException;
import org.fosstrak.ale.server.Tag;
import org.fosstrak.ale.server.readers.BaseReader;
import org.fosstrak.ale.xsd.ale.epcglobal.LRSpec;
import org.fosstrak.hal.HardwareException;
import org.fosstrak.hal.Observation;
import org.fosstrak.llrp.adaptor.Constants;
import org.fosstrak.llrp.adaptor.Reader;
import org.fosstrak.llrp.adaptor.exception.LLRPRuntimeException;
import org.fosstrak.tdt.TDTEngine;
import org.llrp.ltk.exceptions.InvalidLLRPMessageException;
import org.llrp.ltk.generated.LLRPMessageFactory;
import org.llrp.ltk.generated.enumerations.C1G2ReadResultType;
import org.llrp.ltk.generated.enumerations.C1G2WriteResultType;
import org.llrp.ltk.generated.interfaces.AccessCommandOpSpecResult;
import org.llrp.ltk.generated.interfaces.EPCParameter;
import org.llrp.ltk.generated.messages.RO_ACCESS_REPORT;
import org.llrp.ltk.generated.parameters.AntennaID;
import org.llrp.ltk.generated.parameters.C1G2ReadOpSpecResult;
import org.llrp.ltk.generated.parameters.C1G2WriteOpSpecResult;
import org.llrp.ltk.generated.parameters.EPC_96;
import org.llrp.ltk.generated.parameters.TagReportData;
import org.llrp.ltk.types.Integer96_HEX;
import org.llrp.ltk.types.LLRPMessage;
import org.llrp.ltk.types.UnsignedShortArray_HEX;


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
 * @author swieland
 * @author wafa.soubra@orange.com
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

	/** ORANGE: the path to the properties file for the LLRPAdaptor. */
	private static final String LLRPADAPTOR_CONFIG_FILE = "/LLRPAdaptorConfig.properties";
	
	/** ORANGE: the name of the property corresponding to the OpSpecId of the C1G2Read for the MB=3. */
	/** MB=3 is the the memory bank for the user memory. */
	private static final String USER_MEM_C1G2READ_OPSPEC_ID = "UserMemoryC1G2ReadOpSpecId";
	
	/** ORANGE: the name of the property corresponding to the OpSpecId of the C1G2Write for the MB=3. */
	/** MB=3 is the the memory bank for the user memory. */
	private static final String USER_MEM_C1G2WRITE_OPSPEC_ID = "UserMemoryC1G2WriteOpSpecId";
		
	/** ORANGE: the tag length. */
	private static final String TAG_LENGTH = "tagLength";
	
	/** ORANGE: the tag filter. */
	private static final String TAG_FILTER = "tagFilter";
	
	/** ORANGE: the tag company prefix length. */
	private static final String TAG_COMPANY_PREFIX_LENGTH = "tagCompanyPrefixLength";
	
	/** ORANGE: the OpSpecID of the C1G2Read for the User Memory (MB=3).*/
	/** Will be initialized by the value of UserMemoryC1G2ReadOpSpecId.*/
	private static int userMemReadOpSpecID = -1;
	
	/** ORANGE: the OpSpecID of the C1G2Write for the User Memory (MB=3).*/
	/** Will be initialized by the value of UserMemoryC1G2WriteOpSpecId.*/
	private static int userMemWriteOpSpecID = -1;
	
	/** ORANGE: the tag length. */
	private static String length = null;
	
	/** ORANGE: the tag filter. */
	private static String filter = null;
	
	/** ORANGE: the tag company prefix length. */
	private static String companyPrefixLength = null;
	
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
			log.debug("caught exception", e);
		}
	}
	
	/**
	 * initializes a LLRPAdaptor. this method must be called before the Adaptor can
	 * be used.
 	 * @param name the name for the reader encapsulated by this adaptor.
	 * @param spec the specification that describes the current reader.
	 * @throws ImplementationException whenever an internal error occurs.

	 */
	public void initialize(String name, LRSpec spec) throws ImplementationException {
		super.initialize(name, spec);
		
		if ((name == null) || (spec == null)) {
			log.error("reader name or LRSpec is null.");
			throw new ImplementationException("reader name or LRSpec is null.");
		}
		
		//ORANGE: initialize properties for the LLRPAdaptor
		inititializeLLRPAdaptorProperties (LLRPADAPTOR_CONFIG_FILE);
	
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
						throw new ImplementationException("llrp reader is missing and not " +
								"enough parameters specified. (Needs PhysicalReaderName, ip, port, clientInitiated) !");
					}
					log.debug(String.format(
							"defining new reader with settings:" +
							"name: %s, ip: %s, port: %d, clientinitiated: %b",
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
				throw new ImplementationException("could not get an instance of the LLRPManager - aborting");
			}
			
			
		} catch (Exception e) {
			log.error("Error when initializing the Reader", e);
			throw new ImplementationException("Error when initializing the Reader");
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
	public void connectReader() throws ImplementationException {
		// get the required reader and register for asynchronous messages.
		try {
			Reader llrpReader = manager.getAdaptor().getReader(physicalReaderName);
			llrpReader.registerForAsynchronous(callback);
			setConnected();
			
		} catch (RemoteException e) {
			log.error("could not connect to the reader");
			throw new ImplementationException("Error when connecting the Reader");
		}
	}

	@Override
	public void disconnectReader() throws ImplementationException {
		// get the required reader and register for asynchronous messages.
		try {
			Reader llrpReader = manager.getAdaptor().getReader(physicalReaderName);
			llrpReader.deregisterFromAsynchronous(callback);
			setDisconnected();
			
		} catch (RemoteException e) {
			log.error("could not disconnect from the reader");
			throw new ImplementationException("Error when disconnecting the Reader");
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
			log.error("there has been an rmi exception: ", e);
		} catch (LLRPRuntimeException e) {
			log.error("there has been an exception in the reader module: ", e);
		}
	}

	@Override
	public void update(LRSpec spec) throws ImplementationException {
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

							//ORANGE: add additional values if they exist
							tag.setTagLength(length);
							tag.setFilter(filter);
							tag.setCompanyPrefixLength(companyPrefixLength);
							//ORANGE End.

							// add the tag.
							tags.add(tag);
						} catch (Exception e) {
							log.debug("bad error, ignoring tag: " + e.getMessage());
						}	
		
						//ORANGE: managing the User Memory in the RO_ACCESS_REPORT
						List<AccessCommandOpSpecResult> accessResultList = tagData.getAccessCommandOpSpecResultList();
						for (AccessCommandOpSpecResult accessResult : accessResultList) {
							
							//ORANGE: in case of reading the User Memory of a tag, 
							//retrieve the user memory from the RO_ACCESS_REPORT and store it in the tag.
							if (accessResult instanceof C1G2ReadOpSpecResult) {
								C1G2ReadOpSpecResult op = (C1G2ReadOpSpecResult)accessResult;
								if ((op.getResult().intValue() == C1G2ReadResultType.Success) && 
									(op.getOpSpecID().intValue() == userMemReadOpSpecID)){
									UnsignedShortArray_HEX userMemoryHex = op.getReadData();
									log.debug ("User Memory read from the tag is = " + userMemoryHex.toString());
									tag.setUserMemory(userMemoryHex.toString());
								}
							}
					
							//ORANGE: in case of writing in the User Memory of the tag,
							//log if needed that the C1G2Write Operation on the tag has succeeded. 
							if (accessResult instanceof C1G2WriteOpSpecResult) {
								C1G2WriteOpSpecResult op = (C1G2WriteOpSpecResult)accessResult;
								if ((op.getResult().intValue()== C1G2WriteResultType.Success)&&
									(op.getOpSpecID().intValue() == userMemWriteOpSpecID)) {
									log.debug ("Writing in the User Memory of the tag has succeeded.");
								}
							}
						}	
						//ORANGE End	
						
						// try to run a conversion on the tag...
						if (null != tag) {
							try {		
								//ORANGE : replace the following code ...
//								String pureID = Tag.convert_to_PURE_IDENTITY(
//										null, 
//										null, 
//										null, 
//										tag.getTagAsBinary());
								
								//ORANGE : by this one more generic.
								String pureID =	Tag.convert_to_PURE_IDENTITY(
										tag.getTagLength(),
										tag.getFilter(),
										tag.getCompanyPrefixLength(),
										tag.getTagAsBinary());	
								//ORANGE End.
							
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
	
	/**
	 * ORANGE: This method initalizes properties needed to manage an LLRPAdaptor.
	 * Properties are used to read the User Memory of a tag from an RO_ACCESS_REPORT or to log that
	 * a write operation in the User Memory of a tag has succeeded. 
	 * There are also properties linked to the creation of a tag like : length, filter and companyPrefixLength. 
	 * @param propertiesFilePath the filepath to the properties file
	 * @throws ImplementationException if properties could not be loaded
	 */
	public void inititializeLLRPAdaptorProperties (String propertiesFilePath) throws ImplementationException {
		Properties props = new Properties();
		//TODO : to test the different cases !!!!
		try {
			props.load(LLRPAdaptor.class.getResourceAsStream(propertiesFilePath));
		} catch (Exception e) {
			throw new ImplementationException
			("Error loading properties from LLRPAdaptor '" + propertiesFilePath + "'");
		}
		// we need to initialize the User Memory OpSpecID
		String readOpSpecID = props.getProperty(USER_MEM_C1G2READ_OPSPEC_ID);
		String writeOpSpecID = props.getProperty(USER_MEM_C1G2WRITE_OPSPEC_ID);
		if (readOpSpecID != null) {
			userMemReadOpSpecID = java.lang.Integer.parseInt(readOpSpecID);
		}
		if (writeOpSpecID != null) {
			userMemWriteOpSpecID = java.lang.Integer.parseInt(writeOpSpecID);
		}
		// init the parameters of a tag from the properties file
		length = props.getProperty(TAG_LENGTH);
		filter = props.getProperty(TAG_FILTER);
		companyPrefixLength = props.getProperty(TAG_COMPANY_PREFIX_LENGTH);
	}
}
