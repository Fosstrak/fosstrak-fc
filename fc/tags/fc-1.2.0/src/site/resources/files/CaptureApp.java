import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.fosstrak.ale.util.DeserializerUtil;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReport;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReportGroup;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReportGroupListMember;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReports;
import org.fosstrak.ale.xsd.epcglobal.EPC;
import org.fosstrak.epcis.captureclient.CaptureClient;
import org.fosstrak.epcis.model.ActionType;
import org.fosstrak.epcis.model.BusinessLocationType;
import org.fosstrak.epcis.model.EPCISBodyType;
import org.fosstrak.epcis.model.EPCISDocumentType;
import org.fosstrak.epcis.model.EPCListType;
import org.fosstrak.epcis.model.EventListType;
import org.fosstrak.epcis.model.ObjectEventType;
import org.fosstrak.epcis.model.ReadPointType;

/**
 * @author wafa.soubra@orange.com
 *
 */

public class CaptureApp {
	
	private int port;
	
	private String epcisRepository;
	
	private CaptureClient client = null;
		
	/** ORANGE: the path to the properties file for the LLRPAdaptor. */
	private static final String LLRPADAPTOR_CONFIG_FILE = "/LLRPAdaptorConfig.properties";

	/** ORANGE: epcisUniqueTagInObjectEvent is the name of the boolean property.
	 * If true, it allows to get the User Memory of the tag and add it to the  
	 * ObjectEvent of the capture application in the EPCIS. 
	 * In this case, we generate an ObjectEvent per tag and store the User Memory
	 * of the tag in the extension of the ObjectEvent. 
	 * If we want to store a list of User Memory, the model of the ObjectEventType
	 * must evolve to allow the storage of a list of User Memory like the list of tags. 
	 **/
	private static final String EPCIS_UNIQUE_TAG = "epcisUniqueTagInObjectEvent";
	
	/** ORANGE: store the value of epcisUniqueTagInObjectEvent property.
	 * If true allows to store the User Memory of the tag in the ObjectEvent.
	 * In that case, each ObjectEvent will have a unique tag in the epcList.
	 */
	private static boolean epcisUniqueTag = false;
	
	public CaptureApp(int port, String epcisRepository) {
		this.port = port;
		this.epcisRepository = epcisRepository;
	}

	private void handleReports(ECReports reports) throws IOException, JAXBException, ImplementationExceptionResponse, Exception {
		System.out.println("Handling incomming reports");
		// ORANGE:
		initUniqueTagProperty();
		if (epcisUniqueTag) {
			handleReportsWithUserMemory(reports);
		}
		//ORANGE End
		else {
			List<ECReport> theReports = reports.getReports().getReport();
			// collect all the tags
			List<EPC> epcs = new LinkedList<EPC>();
			if (theReports != null) {
				for (ECReport report : theReports) {
					if (report.getGroup() != null) {
						for (ECReportGroup group : report.getGroup()) {
							if (group.getGroupList() != null) {
								for (ECReportGroupListMember member : group.getGroupList().getMember()) {
									if (member.getRawDecimal() != null) {
										epcs.add(member.getRawDecimal());
									}							
								}
							}
						}
					}
				}
			}
			if (epcs.size() == 0) {
				System.out.println("no epc received - generating no event");
				return;
			}

			// create the ecpis event
			ObjectEventType objEvent = new ObjectEventType();
	
			// get the current time and set the eventTime
			XMLGregorianCalendar now = null;
			try {
			    DatatypeFactory dataFactory = DatatypeFactory.newInstance();
			    now = dataFactory.newXMLGregorianCalendar(new GregorianCalendar());
			    objEvent.setEventTime(now);
			} catch (DatatypeConfigurationException e) {
			    e.printStackTrace();
			}
	
			// get the current time zone and set the eventTimeZoneOffset
			if (now != null) {
			    int timezone = now.getTimezone();
			    int h = Math.abs(timezone / 60);
			    int m = Math.abs(timezone % 60);
			    DecimalFormat format = new DecimalFormat("00");
			    String sign = (timezone < 0) ? "-" : "+";
			    objEvent.setEventTimeZoneOffset(sign + format.format(h) + ":" + format.format(m));
			}
			
			EPCListType epcList = new EPCListType();
			// add the epcs
			for (EPC epc : epcs) {
				org.fosstrak.epcis.model.EPC nepc = new org.fosstrak.epcis.model.EPC(); 
				nepc.setValue(epc.getValue());
				epcList.getEpc().add(nepc);
			}
			objEvent.setEpcList(epcList);
	
			// set action
			objEvent.setAction(ActionType.ADD);
	
			// set bizStep
			objEvent.setBizStep("urn:fosstrak:demo:bizstep:testing");
	
			// set disposition
			objEvent.setDisposition("urn:fosstrak:demo:disp:testing");
	
			// set readPoint
			ReadPointType readPoint = new ReadPointType();
			readPoint.setId("urn:fosstrak:demo:rp:1.1");
			objEvent.setReadPoint(readPoint);
	
			// set bizLocation
			BusinessLocationType bizLocation = new BusinessLocationType();
			bizLocation.setId("urn:fosstrak:demo:loc:1.1");
			objEvent.setBizLocation(bizLocation);
	
			// create the EPCISDocument containing a single ObjectEvent
			EPCISDocumentType epcisDoc = new EPCISDocumentType();
			EPCISBodyType epcisBody = new EPCISBodyType();
			EventListType eventList = new EventListType();
			eventList.getObjectEventOrAggregationEventOrQuantityEvent().add(objEvent);
			epcisBody.setEventList(eventList);
			epcisDoc.setEPCISBody(epcisBody);
			epcisDoc.setSchemaVersion(new BigDecimal("1.0"));
			epcisDoc.setCreationDate(now);
					
			int httpResponseCode = client.capture(epcisDoc);
			if (httpResponseCode != 200) {
			    System.out.println("The event could NOT be captured!");
			}
		}
	}	
	
	
	
	/** ORANGE: retrieve and test the value of the property just to know if we want to store 
	 * the User Memory in the ObjectEvent.
	 * @throws ImplementationExceptionResponse whenever an internal error occurs.
	 * 
	 */
	private void initUniqueTagProperty ()  throws ImplementationExceptionResponse {
		Properties props = new Properties();
		//TODO : à remplacer Subscriber.class par CaptureApp.class
		try {
			props.load(Subscriber.class.getResourceAsStream(LLRPADAPTOR_CONFIG_FILE));
		} catch (Exception e) {
			throw new ImplementationExceptionResponse
			("Error loading properties from Subscriber'" + LLRPADAPTOR_CONFIG_FILE + "'");
		}
		String uniqueTag = props.getProperty(EPCIS_UNIQUE_TAG);
		if (uniqueTag != null) {
			epcisUniqueTag = Boolean.valueOf(uniqueTag).booleanValue();
		}
	}
	
	/**
	 * ORANGE: handle reports by extracting the epc and the user memory of the tag. 
	 * For each tag, an ObjectEvent will be define containing these 2 informations.
	 * @param reports the ECReports
	 * @throws ImplementationException whenever an internal error occurs.
	 */
	private void handleReportsWithUserMemory(ECReports reports) throws IOException, JAXBException, Exception {
		System.out.println("Handling incomming reports with User Memory");
		
		List<ECReport> theReports = reports.getReports().getReport();
		// collect all the tags
		List<EPC> epcs = new LinkedList<EPC>();
		// collect all the "user memory" containing the complete payload
		List<String> userMemories = new ArrayList<String>();
		
		if (theReports != null) {
			for (ECReport report : theReports) {
				if (report.getGroup() != null) {
					for (ECReportGroup group : report.getGroup()) {
						if (group.getGroupList() != null) {
							for (ECReportGroupListMember member : group.getGroupList().getMember()) {
								if (member.getEpc() != null) {
									epcs.add(member.getEpc());
									String userMemory = null;
									ECReportGroupListMemberExtension extension = member.getExtension();
									if (extension!= null) {
										// TODO : to test if it works
										// on remplace 
										//String userMemory = member.getExtension().getFieldList().getField().get(0).getValue();									
										// par : 
										if (extension.getFieldList() != null) {
											for (ECReportMemberField reportMemberField : extension.getFieldList().getField()) {
												String fieldName = reportMemberField.getFieldspec().getFieldname();
												//TODO : test if the field name contains "UserMemory"
												if (fieldName.equalsIgnoreCase("UserMemory")) {
													userMemory = reportMemberField.getValue();
												}		
											}
										}
									}
									LOG.debug (" User Memory = " + userMemory);
									userMemories.add(userMemory);
									}
								}
							}							
						}
					}
				}
			}
		
		if (epcs.size() == 0) {
			System.out.println("no epc received - generating no event");
			return;
		} 
		captureObjectEvent (epcs, userMemories);
	}		
	
	/** ORANGE: For each epc in the list, we create an ObjectEvent.
	 * The UserMemory corresponding to this epc will be stored in the Extension of the ObjectEvent. 
	 * @param reports the ECReports
	 * @throws ImplementationException whenever an internal error occurs.
	 */
	private void captureObjectEvent (List<EPC> epcs, List<String> userMemories) throws IOException, JAXBException, Exception {
		System.out.println("Begining Process of all the epc .........." );
		int i = 0;
		for (EPC epc : epcs) {
			System.out.println ("Entering captureObjectEvent for One EPC");
			// create the ecpis event
			ObjectEventType objEvent = new ObjectEventType();

			// get the current time and set the eventTime
			XMLGregorianCalendar now = null;
			try {
			    DatatypeFactory dataFactory = DatatypeFactory.newInstance();
			    now = dataFactory.newXMLGregorianCalendar(new GregorianCalendar());
			    objEvent.setEventTime(now);
			} catch (DatatypeConfigurationException e) {
			    e.printStackTrace();
			}

			// get the current time zone and set the eventTimeZoneOffset
			if (now != null) {
			    int timezone = now.getTimezone();
			    int h = Math.abs(timezone / 60);
			    int m = Math.abs(timezone % 60);
			    DecimalFormat format = new DecimalFormat("00");
			    String sign = (timezone < 0) ? "-" : "+";
			    objEvent.setEventTimeZoneOffset(sign + format.format(h) + ":" + format.format(m));
			}
		
			EPCListType epcList = new EPCListType();
		
			// Each ObjectEvent contains 1 EPC in the EPCList 
			org.fosstrak.epcis.model.EPC nepc = new org.fosstrak.epcis.model.EPC(); 
			nepc.setValue(epc.getValue());
			epcList.getEpc().add(nepc);
			objEvent.setEpcList(epcList);

			// set action
			objEvent.setAction(ActionType.ADD);
	
			// set bizStep
			objEvent.setBizStep("urn:fosstrak:demo:bizstep:testing");
	
			// set disposition
			objEvent.setDisposition("urn:fosstrak:demo:disp:testing");
	
			// set readPoint
			ReadPointType readPoint = new ReadPointType();
			readPoint.setId("urn:fosstrak:demo:rp:1.1");
			objEvent.setReadPoint(readPoint);
	
			// set bizLocation
			BusinessLocationType bizLocation = new BusinessLocationType();
			bizLocation.setId("urn:fosstrak:demo:loc:1.1");
			objEvent.setBizLocation(bizLocation);
			
			// get the user memory of the tag and store it in the extension of the objectEvent. 
			String userMemory = userMemories.get(i);
		
			if (userMemory != null) {
				DocumentImpl  documentImpl = new DocumentImpl();
				ElementNSImpl elementNSImpl = (ElementNSImpl)documentImpl.createElementNS("http://com.orange.pangoo/epcis/extension/", "userMemory");
				TextImpl textImpl = (TextImpl)documentImpl.createTextNode(userMemory);
				elementNSImpl.appendChild(textImpl);
				objEvent.getAny().add(elementNSImpl);
				
				// the code below doesn't work. 
				// elementNSImpl.setAttribute("payload", userMemory);
				
				// ObjectEventExtensionType doesn't work either. 
				// ObjectEventExtensionType fieldExtension = new ObjectEventExtensionType();
				//fieldExtension.getAny().add(elementNSImpl);
				// fieldExtension.getOtherAttributes().put(new QName("http://pangoo.unique.namespace", "payload", "aaa"), userMemory);
				//objEvent.setExtension(fieldExtension);
			}
			
			// create the EPCISDocument containing a single ObjectEvent
			EPCISDocumentType epcisDoc = new EPCISDocumentType();
			EPCISBodyType epcisBody = new EPCISBodyType();
			EventListType eventList = new EventListType();
			eventList.getObjectEventOrAggregationEventOrQuantityEvent().add(objEvent);
			epcisBody.setEventList(eventList);
			epcisDoc.setEPCISBody(epcisBody);
			epcisDoc.setSchemaVersion(new BigDecimal("1.0"));
			epcisDoc.setCreationDate(now);

			// sending the xml document to the capture client
			int httpResponseCode = client.capture(epcisDoc);
			if (httpResponseCode != 200) {
				System.out.println("The event could NOT be captured!");
				}
			System.out.println("Ending captureObjectEvent for One EPC");
			
			// counter to get the next UserMemory for the next epc in the list
			i=i+1;
		}
		System.out.println("Ending Process for all epc and user memory list in the ECREport !!!!!!!!!!!" );
	}
	
	
	public void run() {
		client = new CaptureClient(epcisRepository);
				
		ServerSocket ss = null;
		try {
			ss = new ServerSocket(port);
			while(true) {
				try {
					Socket s = ss.accept();
	   				BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
	   			   
	   				String data = in.readLine();
	   				String buf = "";
	   				// ignore the http header
	 				data = in.readLine();
	   				data = in.readLine();
	   				data = in.readLine();
	   				data = in.readLine();
	   				
	   				while (data != null) {
	   					buf += data;
	   					data = in.readLine();
	   				}
	   				System.out.println(buf);
	   				
	   				// create a stream from the buf
	   				InputStream parseStream = new ByteArrayInputStream(buf.getBytes());
	   				
	   				// parse the string
	   				
	   				ECReports reports = DeserializerUtil.deserializeECReports(parseStream);
	   				if (reports != null) {
	   					handleReports(reports);
	   				}
            } catch (Exception e) {
            		System.out.println("ERROR: " + e.getMessage());
            }
   		}
      } catch (IOException e1) {
         System.out.println(e1.getMessage());
      }
	}
	
	public static void help() {
		System.out.println("You need to specify the port where to listen and the url of the epcis repository");
		System.out.println("Example: ");
	}
	
	/**
	 * starts the CaptureApp.
	 * 
	 * @param args the first command line parameter is the TCP port. if omitted port 9999 is used.
	 */
	public static void main(String[] args) {
        CaptureApp client;
        int port;
        String epcisRepository;
        // check if args[0] is tcp-port
        // and args[1] is epcis repository
        if (args.length == 2){
        	port = Integer.parseInt(args[0]);
        	epcisRepository = args[1];
            client = new CaptureApp(port, epcisRepository);
            
        } else {
        	help();
        	return;
        }
        
        client.run();
	}
}