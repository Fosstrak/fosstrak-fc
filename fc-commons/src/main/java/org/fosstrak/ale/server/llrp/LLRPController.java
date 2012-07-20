package org.fosstrak.ale.server.llrp;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.fosstrak.ale.wsdl.alelr.epcglobal.NoSuchNameExceptionResponse;

/**
 * ORANGE: Interface to define dynamic ROSPEC for LLRP Reader.
 *
 * FIXME: swieland: need to comment this interface...
 * 
 * @author wafa.soubra@orange.com
 */
@WebService(name="LLRPControllerServicePortType")
public interface LLRPController {

	/**
	 * define a new ROSpec on the given logical reader.
	 * @param readerName the name of the reader where to define the ROSpec.
	 * @param addRoSpec serialized AddROSpec
	 * @throws DuplicateNameExceptionResponse
	 * @throws NoSuchNameExceptionResponse
	 */
	@WebMethod
	public void define(String readerName, String addRoSpec) throws DuplicateNameExceptionResponse, NoSuchNameExceptionResponse;	
	
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

