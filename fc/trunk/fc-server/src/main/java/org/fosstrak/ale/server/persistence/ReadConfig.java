package org.fosstrak.ale.server.persistence;


/**
 * This class is called at the startup of tomcat and load all configuration on the the ALE.
 * @author benoit plomion Orange
 */
public interface ReadConfig {
	
	/**
	 * this method is called once at the startup of the application server by the PersistenceServlet and set the real path of the webapp
	 */
	void init();
	
	/**
	 * read and define the ec specs from the config path.
	 */
	void readECSpecs();
	
	/**
	 * read and define the subscribers on the ECSpecs.
	 */
	void readECSpecsSubscriber();
	
	/**
	 * read and define the LRSpecs.
	 */
	void readLRSpecs();
	
	/**
	 * read and add the ROSpecs.
	 */
	void readAddROSpecs();
	
	/**
	 * read and add the AccessSpecs.
	 */
	void readAddAccessSpecs();	
}
