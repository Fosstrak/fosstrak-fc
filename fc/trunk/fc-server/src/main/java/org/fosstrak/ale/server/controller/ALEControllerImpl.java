package org.fosstrak.ale.server.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.apache.log4j.Logger;
import org.fosstrak.ale.server.ALEFactory;
import org.fosstrak.ale.server.ReportsGenerator;
import org.fosstrak.ale.server.ReportsGeneratorState;
import org.fosstrak.ale.server.readers.LogicalReader;
import org.fosstrak.ale.server.readers.LogicalReaderManagerFactory;

/**
 * this class is a webservice wich is control ALE. Can stop/start/... ECSpec.
 * @author benoit.plomion@orange.com
 */
@WebService(endpointInterface = "org.fosstrak.ale.server.controller.ALEController")
public class ALEControllerImpl implements ALEController {

	/**	logger. */
	private static final Logger LOG = Logger.getLogger(ALEControllerImpl.class.getName());
		
	//FIXME => modifier tous les NoSuchNameGPIOExceptionResponse avec les bons NoSuchNameException
	
	/**
	 * this method return the status of an ECSpec: started or not
	 * @param specName
	 * @throws NoSuchNameGPIOExceptionResponse 
	 */
	@WebMethod
	public boolean ecSpecIsStarted(String specName) throws org.fosstrak.ale.wsdl.ale.epcglobal.NoSuchNameExceptionResponse {			
		
		LOG.info("check if " + specName + " ECSpec is started");
		
		if (!ALEFactory.getALE().getReportGenerators().containsKey(specName)) {
			throw new org.fosstrak.ale.wsdl.ale.epcglobal.NoSuchNameExceptionResponse();			
		}
		
		boolean result = false;
		
		ReportsGenerator reportsGenerator = ALEFactory.getALE().getReportGenerators().get(specName);
		
		if (reportsGenerator != null) {
		
			if (reportsGenerator.getState() == ReportsGeneratorState.REQUESTED){
				result = true;
			}
			
		}
		
		LOG.debug("ECSpec" + specName + " is started = " + result);
		
		return result;
		
	}

	/**
	 * this method return all ECSpec which is started
	 */
	@WebMethod
	public List<String> getAllECSpecNameStarted() {

		LOG.info("get all ECSpec started");
		
		List<String> result = new ArrayList<String>();
		
		Map<String, ReportsGenerator> reportGeneratorList = ALEFactory.getALE().getReportGenerators();		
		Set<String> reportGeneratorNameList = reportGeneratorList.keySet();
		
		for (String reportGeneratorName : reportGeneratorNameList) {
			
			if (reportGeneratorList.get(reportGeneratorName).getState() == ReportsGeneratorState.REQUESTED){
				result.add(reportGeneratorName);
			}
			
		}
		
		LOG.debug("list of all ECSpec started = " + result);
		
		return result;
		
	}
	
	/**
	 * this method start a specified ECSpec
	 * @param specName
	 * @throws NoSuchNameGPIOExceptionResponse 
	 */
	@WebMethod
	public void startECSpec(String specName) throws org.fosstrak.ale.wsdl.ale.epcglobal.NoSuchNameExceptionResponse {
		
		LOG.info("start ECSpec " + specName);
		
		if (!ALEFactory.getALE().getReportGenerators().containsKey(specName)) {
			throw new org.fosstrak.ale.wsdl.ale.epcglobal.NoSuchNameExceptionResponse();			
		}
		
		ReportsGenerator reportsGenerator = ALEFactory.getALE().getReportGenerators().get(specName);		
		
		if (reportsGenerator != null) {			
			reportsGenerator.setState(ReportsGeneratorState.REQUESTED);			
		}	
		
	}

	/**
	 * this method stop a specified ECSpec
	 * @param specName
	 * @throws NoSuchNameGPIOExceptionResponse 
	 */
	@WebMethod
	public void stopECSpec(String specName) throws org.fosstrak.ale.wsdl.ale.epcglobal.NoSuchNameExceptionResponse {
		
		LOG.info("stop ECSpec " + specName);
				
		if (!ALEFactory.getALE().getReportGenerators().containsKey(specName)) {
			throw new org.fosstrak.ale.wsdl.ale.epcglobal.NoSuchNameExceptionResponse();			
		}
		
		ReportsGenerator reportsGenerator = ALEFactory.getALE().getReportGenerators().get(specName);	
		
		if (reportsGenerator != null) {			
			reportsGenerator.setState(ReportsGeneratorState.UNREQUESTED);			
		}		
		
	}
	
	/**
	 * this method is used to stop all ECSpec
	 */
	@WebMethod
	public void stopAllECSpec() {
		
		LOG.info("stop all ECSpec");
		
		List<String> ecSpecsName = getAllECSpecNameStarted();
		
		for (String ecSpecName : ecSpecsName) {
				
			try {
				LOG.info("stop ECSepcs " + ecSpecName);
				stopECSpec(ecSpecName);
			} catch (org.fosstrak.ale.wsdl.ale.epcglobal.NoSuchNameExceptionResponse e) {
				LOG.error("error to stop ecspec at startup", e);
			}
		
		}
		
	}
	
	/**
	 * this method is used to stop all ECSpec started for one LogicalReader
	 * @param logicalReaderName
	 * @throws NoSuchNameGPIOExceptionResponse 
	 */
	@WebMethod
	public void stopAllECSpec4LogicalReader(String logicalReaderName) throws org.fosstrak.ale.wsdl.alelr.epcglobal.NoSuchNameExceptionResponse {
		
		LOG.info("stop all ECSpec for the logical reader " + logicalReaderName);
		
		boolean logicalReaderFind = false;
		
		Map<String, ReportsGenerator> reportGeneratorList = ALEFactory.getALE().getReportGenerators();		
		Set<String> reportGeneratorNameList = reportGeneratorList.keySet();
		
		for (String reportGeneratorName : reportGeneratorNameList) {
			
			ReportsGenerator reportsGenerator = reportGeneratorList.get(reportGeneratorName);
			
			List<String> logicalReaderList = reportsGenerator.getSpec().getLogicalReaders().getLogicalReader();
			
			for (String logicalReaderName_for : logicalReaderList) {
				
				if (logicalReaderName.equalsIgnoreCase(logicalReaderName_for)) {
					
					reportsGenerator.setState(ReportsGeneratorState.UNREQUESTED);
					logicalReaderFind = true;
					
				}
				
			}
			
		}
		
		if (!logicalReaderFind) {
			throw new org.fosstrak.ale.wsdl.alelr.epcglobal.NoSuchNameExceptionResponse();
		}
		
	}

	/**
	 * this method is used to stop all ECSpec started for one LogicalReader searching by ecspec
	 * @param specName
	 * @throws NoSuchNameGPIOExceptionResponse 
	 */
	@WebMethod
	public void stopAllECSpec4LogicalReaderByECSpecName(String specName) throws org.fosstrak.ale.wsdl.ale.epcglobal.NoSuchNameExceptionResponse {
		
		LOG.info("stop all ECSpec for the logical reader by spec name " + specName);
		
		if (!ALEFactory.getALE().getReportGenerators().containsKey(specName)) {
			throw new org.fosstrak.ale.wsdl.ale.epcglobal.NoSuchNameExceptionResponse();			
		}
		
		List<String> logicalReaderList = ALEFactory.getALE().getReportGenerators().get(specName).getSpec().getLogicalReaders().getLogicalReader();
		
		if (logicalReaderList.size() > 0) {	
			
			String logicalReaderName = logicalReaderList.get(0);	
			
			Map<String, ReportsGenerator> reportGeneratorList = ALEFactory.getALE().getReportGenerators();
			Set<String> reportGeneratorNameList = reportGeneratorList.keySet();
			
			for (String reportGeneratorName : reportGeneratorNameList) {
				
				ReportsGenerator reportsGenerator = reportGeneratorList.get(reportGeneratorName);
				
				List<String> logicalReaderList_for = reportsGenerator.getSpec().getLogicalReaders().getLogicalReader();
				
				for (String logicalReaderName_for : logicalReaderList_for) {
					
					if (logicalReaderName.equalsIgnoreCase(logicalReaderName_for)) {
						
						reportsGenerator.setState(ReportsGeneratorState.UNREQUESTED);
						
					}
					
				}
				
			}
			
		}
		
	}
	
	@WebMethod
	public String[] getLogicalReaderNames(boolean isComposite) {
		
		ArrayList<String> result = new ArrayList<String>();		
		Collection<LogicalReader> logicalReaders = LogicalReaderManagerFactory.getLRM().getLogicalReaders();
		
		for (LogicalReader logicalReader : logicalReaders) {			
			if (isComposite == logicalReader.getLRSpec().isIsComposite()) {				
				result.add(logicalReader.getName());			
			}			
		}
		
		return result.toArray(new String[0]);
		
	}
	
}
