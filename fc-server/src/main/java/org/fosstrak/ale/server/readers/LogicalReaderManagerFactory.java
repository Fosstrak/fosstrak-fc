package org.fosstrak.ale.server.readers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Static factory giving access to the logical reader manager. this factory is provided for backwards compatibility to 
 * components that do not use the spring bean wiring.<br/>
 * <br/>
 * <strong>NOTICE: </strong> This component will be removed in future releases.
 * <br/>
 * <br/>
 * Instead define your services/beans as spring beans. then you can automatically profit from nice features like 
 * spring autowiring and dependency injection.
 * 
 * @author swieland
 *
 * @deprecated please use the logical reader manager as an autowired bean.
 */
@Component("logicalReaderManagerFactory")
public class LogicalReaderManagerFactory {

	private static final Logger LOG = Logger.getLogger(LogicalReaderManagerFactory.class);
	
	private static LogicalReaderManager logicalReaderManager;
	
	/**
	 * private utility constructor.
	 */
	private LogicalReaderManagerFactory() {
		
	}
	
	/**
	 * little trick to inject the autowired bean logical reader manager into the static factory class.
	 * @param lrm
	 */
	@Autowired
	public void setLogicalReaderManagerFactory(LogicalReaderManager lrm) {
		logicalReaderManager = lrm;
	}
	
	/**
	 * @deprecated please use the logical reader manager as an autowired bean.
	 * gives a handle onto the currently parameterized logical reader manager.
	 * @return the currently parameterized logical reader manager.
	 */
	public static LogicalReaderManager getLRM() {
		LOG.info("do not use this method anymore - deprecated.");
		return logicalReaderManager;
	}
}
