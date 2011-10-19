package org.fosstrak.ale.server.persistence;

import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.apache.log4j.Logger;
import org.fosstrak.ale.server.ALE;

import org.fosstrak.ale.server.controller.ALEController;
import org.fosstrak.ale.server.controller.ALEControllerImpl;
import org.fosstrak.ale.server.llrp.LLRPController;
import org.fosstrak.ale.server.llrp.LLRPControllerImpl;

/**
 * Persistence servlet is run at the startup of tomcat
 * this servlet:
 * - get and set the real path of webapp to give it to the write config api
 * - call the read config api to load all configuration written before
 * @author benoit.plomion@orange.com
 */
public class PersistenceServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   
	private static final Logger LOG = Logger.getLogger(PersistenceServlet.class.getName());
	   	 
	static final long serialVersionUID = 1L;
    	
	public PersistenceServlet() {
		super();
	}   	
	
	public void init(ServletConfig config) throws ServletException {
			
		LOG.info("ALE Persistence => start");
		
		String path = config.getServletContext().getRealPath("./");
		LOG.debug("ALE Persistence real path of the webapp: " + path);
		Config.setRealPathWebapp(path);
		
		LOG.info("ALE Persistence initialize configuration");
		ReadConfig.initialize();
		
		LOG.info("ALE Persistence => end");
						
	}
	
	
	  	  	    
}