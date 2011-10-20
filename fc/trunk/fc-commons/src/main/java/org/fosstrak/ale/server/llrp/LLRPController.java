package org.fosstrak.ale.server.llrp;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.fosstrak.ale.wsdl.alelr.epcglobal.NoSuchNameExceptionResponse;

/**
 * ORANGE: Interface to define dynamic ROSPEC for LLRP Reader.
 * 
 * @author wafa.soubra@orange.com
 */
@WebService
public interface LLRPController {

	@WebMethod
	public  void define(String lrSpecName, String  addRoSpec) throws DuplicateNameExceptionResponse, NoSuchNameExceptionResponse;
	
	@WebMethod
	public void undefine(String lrSpecName) throws NoSuchNameExceptionResponse;
	
	@WebMethod
	public void start (String lrSpecName) throws NoSuchNameExceptionResponse;
	
	@WebMethod
	public void stop(String lrSpecName) throws NoSuchNameExceptionResponse;
	
	@WebMethod
	public void enable(String lrSpecName) throws NoSuchNameExceptionResponse;
	
	@WebMethod
	public void disable(String lrSpecName) throws NoSuchNameExceptionResponse;
	
	@WebMethod
	public void disableAll();
	
}

