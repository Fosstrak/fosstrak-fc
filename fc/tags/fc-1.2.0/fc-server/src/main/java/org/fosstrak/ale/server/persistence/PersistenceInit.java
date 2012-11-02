package org.fosstrak.ale.server.persistence;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * Persistence init is run at the startup of the application server:
 * <ul>
 * 	<li>get and set the real path of webapp to give it to the write config api</li>
 *  <li>call the read config api to load all configuration written before</li>
 * </ul>
 * 
 * the persistence init requires the servlet context in order to determine the store path where to 
 * save the LRSpecs and ECSpecs...
 * 
 * @author benoit.plomion@orange.com
 */
public interface PersistenceInit {
	
	/**
	 * initialize the persistence.
	 * @param servletContext the servlet context.
	 * @throws ServletException upon initialization error.
	 */
	public void init(ServletContext servletContext) throws ServletException;
}