package org.fosstrak.ale.server.persistence;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.log4j.Logger;
import org.fosstrak.ale.server.persistence.type.PersistenceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Persistence init is run at the startup of the application server:
 * - get and set the real path of webapp to give it to the write config api
 * - call the read config api to load all configuration written before
 * @author benoit.plomion@orange.com
 */
@Component("persistenceServlet")
public class PersistenceInit {
   
	private static final Logger LOG = Logger.getLogger(PersistenceInit.class.getName());
	   	 
	static final long serialVersionUID = 1L;
	
	// autowired
	private PersistenceConfig persistenceConfig;
	
	// autowired
	private ReadConfig persistenceReadConfig;
	
	public PersistenceInit() {
	}
	
	/**
	 * initialize the persistence.
	 * @param servletContext
	 * @throws ServletException
	 */
	public void init(ServletContext servletContext) throws ServletException {
		try {
			LOG.info("ALE Persistence => start");
			String path = servletContext.getRealPath("/");
			LOG.debug("ALE Persistence real path of the webapp: " + path);
			persistenceConfig.setRealPathWebapp(path);
			
			LOG.info("ALE Persistence initialize configuration");
			persistenceReadConfig.init();
			
			LOG.info("ALE Persistence => end");
		} catch (Exception ex) {
			LOG.error("could not initialize the persistence API.", ex);
			throw new ServletException(ex);
		}
	}

	/**
	 * allow to inject the persistence config API.
	 * @param persistenceRemoveAPI the persistence config API.
	 */
    @Autowired
	public void setPersistenceConfig(PersistenceConfig persistenceConfig) {
		this.persistenceConfig = persistenceConfig;
	}

    /**
     * allow to inject the persistence write API.
     * @param persistenceWriteAPI the persistence write API.
     */
    @Autowired
	public void setPersistenceReadConfig(ReadConfig persistenceReadConfig) {
		this.persistenceReadConfig = persistenceReadConfig;
	}
}