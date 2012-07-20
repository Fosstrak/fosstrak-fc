package org.fosstrak.ale.client;

import java.net.URL;
import java.util.Properties;

import javax.swing.JFrame;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.fosstrak.ale.client.cfg.Configuration;
import org.fosstrak.ale.client.exception.FosstrakAleClientException;
import org.fosstrak.ale.client.tabs.EventSink;

/**
 * standalone event sink.
 * @author swieland
 *
 */
public class FosstrakEventSinkStandalone extends JFrame {

	/**
	 * serial version uid. 
	 */
	private static final long serialVersionUID = 3120488105356269361L;
	
	// logger.
	private static final Logger s_log = Logger.getLogger(FosstrakEventSinkStandalone.class);
	
	/**
	 * starts up the sink.
	 * @param url the url of the event sink.
	 * @throws FosstrakAleClientException upon error in startup procedure.
	 */
	public void execute(URL url) throws FosstrakAleClientException {
		EventSink sink = new EventSink(url.toString());
		add(sink);		
		setSize(FosstrakAleClient.instance().getConfiguration().getWindowSize());
		setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
	}
	
	/**
	 * display a nice help text.
	 */
	public static void help() {
		StringBuffer sb = new StringBuffer();
		sb.append("===============================================\n");
		sb.append("Usage: java -cp fc-client-VERSION.jar org.fosstrak.ale.client.FosstrakEventSinkStandalone URL\n");
		sb.append("URL: http://IP:PORT.\n");
		sb.append("-h|--help|help: display this dialog\n");
		sb.append("===============================================\n");
		System.out.println(sb.toString());
		System.exit(0);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws FosstrakAleClientException {
		for (String arg : args) {
			if ("help".equalsIgnoreCase(arg)) help();
			if ("-h".equalsIgnoreCase(arg)) help();
			if ("--help".equalsIgnoreCase(arg)) help();
		}
		
		if (args.length == 0)
		{
			help();
		}
		
		// configure Logger with properties file
		try {
			Properties p = new Properties();
			p.load(FosstrakAleClient.class.getResourceAsStream("/log4j.properties"));
			PropertyConfigurator.configure(p);
			s_log.debug("configured the logger.");
		} catch (Exception e) {
			s_log.info("Could not configure the logger.");
		}
		
		s_log.debug("preparing client for execution.");
		FosstrakAleClient.instance().configure(Configuration.getConfigurtionDefaultConfig());
		s_log.debug("executing client.");
		FosstrakEventSinkStandalone sink = new FosstrakEventSinkStandalone();
		URL url = null;
		try
		{
			 url = new URL(args[0]);
		}
		catch (Exception e)
		{
			help();
		}
		sink.execute(url);
	}

}
