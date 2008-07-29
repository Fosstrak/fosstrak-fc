import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DecimalFormat;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

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

public class CaptureApp {
	
	private int port;
	
	private String epcisRepository;
	
	private CaptureClient client = null;
	
	public CaptureApp(int port, String epcisRepository) {
		this.port = port;
		this.epcisRepository = epcisRepository;
	}

	private void handleReports(ECReports reports) throws IOException, JAXBException {
		System.out.println("Handling incomming reports");
			
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