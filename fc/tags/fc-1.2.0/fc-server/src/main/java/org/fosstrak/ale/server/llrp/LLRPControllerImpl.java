package org.fosstrak.ale.server.llrp;


import javax.jws.WebMethod;
import javax.jws.WebService;

import org.fosstrak.ale.exception.DuplicateNameException;
import org.fosstrak.ale.exception.NoSuchNameException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * ORANGE: This class defines the implementation of the Web Services LLRPController.
 * Define, Start, Stop, Enable, Disable ... an ROSPEC on a LLRP Reader. 
 * 
 * @author wafa.soubra@orange.com
 */
@WebService(endpointInterface = "org.fosstrak.ale.server.llrp.LLRPController")
public class LLRPControllerImpl implements LLRPController {
	
	@Autowired
	LLRPControllerManager llrpControllerManager;

	@WebMethod
	public void define(String lrSpecName, String addRoSpec) throws DuplicateNameException, NoSuchNameException {	
		llrpControllerManager.define(lrSpecName, addRoSpec);		
	}

	@WebMethod
	public void disable(String lrSpecName) throws NoSuchNameException {
		llrpControllerManager.disable(lrSpecName);
	}

	@WebMethod
	public void disableAll() {
		llrpControllerManager.disableAll();
	}

	@WebMethod
	public void enable(String lrSpecName) throws NoSuchNameException {
		llrpControllerManager.enable(lrSpecName);
	}

	@WebMethod
	public void start(String lrSpecName) throws NoSuchNameException {
		llrpControllerManager.start(lrSpecName);
	}

	@WebMethod
	public void stop(String lrSpecName) throws NoSuchNameException {
		llrpControllerManager.stop(lrSpecName);
	}

	@WebMethod
	public void undefine(String lrSpecName) throws NoSuchNameException {
		llrpControllerManager.undefine(lrSpecName);
	}

	
	
}
