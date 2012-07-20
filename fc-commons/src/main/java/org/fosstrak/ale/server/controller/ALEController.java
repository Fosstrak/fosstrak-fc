package org.fosstrak.ale.server.controller;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * ORANGE
 * interface of ALE ECSpec Dynamic
 * @author benoit.plomion@orange.com
 */
@WebService(name="ALEControllerServicePortType", endpointInterface = "org.fosstrak.ale.server.controller.ALEController")
public interface ALEController {

	/**
	 * this method return the status of an ECSpec: started or not
	 * @param specName the name of the specification to test.
	 * @return true if the specification is running, false otherwise.
	 * @throws org.fosstrak.ale.exception.NoSuchNameException the requested ECSpec does not exist. 
	 */
	@WebMethod	
	public boolean ecSpecIsStarted(String specName) throws org.fosstrak.ale.exception.NoSuchNameException;

	/**
	 * this method return all ECSpec which is started
	 */
	@WebMethod
	public List<String> getAllECSpecNameStarted();
	
	/**
	 * this method start a specified ECSpec
	 * @param specName
	 * @throws org.fosstrak.ale.exception.NoSuchNameException the requested ECSpec does not exist. 
	 */	
	@WebMethod
	public void startECSpec(String specName) throws org.fosstrak.ale.exception.NoSuchNameException;
	
	/**
	 * this method stop a specified ECSpec
	 * @param specName
	 * @throws org.fosstrak.ale.exception.NoSuchNameException the requested ECSpec does not exist. 
	 */
	@WebMethod
	public void stopECSpec(String specName) throws org.fosstrak.ale.exception.NoSuchNameException;
	
	/**
	 * this method is used to stop all ECSpec
	 */
	@WebMethod
	public void stopAllECSpec();
	
	/**
	 * this method is used to stop all ECSpec started for one LogicalReader
	 * @param logicalReaderName
	 * @throws org.fosstrak.ale.exception.NoSuchNameException the requested ECSpec does not exist. 
	 */
	@WebMethod
	public void stopAllECSpec4LogicalReader(String logicalReaderName) throws org.fosstrak.ale.exception.NoSuchNameException;

	/**
	 * this method is used to stop all ECSpec started for one LogicalReader searching by ecspec
	 * @param specName
	 * @throws org.fosstrak.ale.exception.NoSuchNameException the requested ECSpec does not exist. 
	 */
	@WebMethod
	public void stopAllECSpec4LogicalReaderByECSpecName(String specName) throws org.fosstrak.ale.exception.NoSuchNameException;
	
	/**
	 * obtain all the names of the logical readers.
	 * @param isComposite select either only composite readers (case true) or base readers (case false).
	 * @return the requested logical reader names.
	 */
	@WebMethod
	public String[] getLogicalReaderNames(boolean isComposite);
	
}
