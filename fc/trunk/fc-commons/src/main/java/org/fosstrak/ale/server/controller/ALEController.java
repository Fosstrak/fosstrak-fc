package org.fosstrak.ale.server.controller;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * ORANGE
 * interface of ALE ECSpec Dynamic
 * @author benoit.plomion@orange.com
 */
@WebService
public interface ALEController {

	@WebMethod	
	public boolean ecSpecIsStarted(String specName) throws org.fosstrak.ale.wsdl.ale.epcglobal.NoSuchNameExceptionResponse;
	
	@WebMethod
	public List<String> getAllECSpecNameStarted();
	
	@WebMethod
	public void startECSpec(String specName) throws org.fosstrak.ale.wsdl.ale.epcglobal.NoSuchNameExceptionResponse;
		
	@WebMethod
	public void stopECSpec(String specName) throws org.fosstrak.ale.wsdl.ale.epcglobal.NoSuchNameExceptionResponse;
		
	@WebMethod
	public void stopAllECSpec();
		
	@WebMethod
	public void stopAllECSpec4LogicalReader(String logicalReaderName) throws org.fosstrak.ale.wsdl.alelr.epcglobal.NoSuchNameExceptionResponse;
	
	@WebMethod
	public void stopAllECSpec4LogicalReaderByECSpecName(String specName) throws org.fosstrak.ale.wsdl.ale.epcglobal.NoSuchNameExceptionResponse;
	
	@WebMethod
	public String[] getLogicalReaderNames(boolean isComposite);
	
}
