/*
 * Copyright (C) 2007 ETH Zurich
 *
 * This file is part of Fosstrak (www.fosstrak.org).
 *
 * Fosstrak is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License version 2.1, as published by the Free Software Foundation.
 *
 * Fosstrak is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Fosstrak; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
 * Boston, MA  02110-1301  USA
 */

package org.fosstrak.ale.server;

import javax.annotation.PreDestroy;
import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.fosstrak.ale.server.persistence.PersistenceInit;
import org.fosstrak.ale.server.readers.LogicalReader;
import org.fosstrak.ale.server.readers.LogicalReaderManager;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;

/**
 * wrapper returning a reference to the spring application context 
 * for non spring enabled beans.
 * 
 * @author swieland
 *
 */
@Service("aleApplicationContext")
public final class ALEApplicationContext implements ApplicationContextAware, ServletContextAware {

	/** logger. */
	private static final Logger LOG = Logger.getLogger(ALEApplicationContext.class);
	private static ApplicationContext applicationContext;	
	
	
	/**
	 * private constructor for utility classes.
	 */
	private ALEApplicationContext() {
	}

	/**
	 * inject the application context at construction time.
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		LOG.info("initializing ALEApplicationContext");
		ALEApplicationContext.applicationContext = applicationContext;
		LOG.info("initialized ALEApplicationContext.");
	}

    @Override
	public void setServletContext(ServletContext servletContext) {
		initializeApplication(servletContext);
	}
	
	private void initializeApplication(ServletContext servletContext) {
    	LOG.debug("initializing application ...");
		try {
			LogicalReaderManager lrm = getBean(LogicalReaderManager.class);
			LOG.debug("instantiated LogicalReaderManager: " + lrm.getClass().getCanonicalName());
			ALE ale = getBean(ALE.class);
			LOG.debug("instantiated ALE: " + ale.getClass().getCanonicalName());
			PersistenceInit persistence = getBean(PersistenceInit.class);
			LOG.debug("instantiated Persistence: " + persistence.getClass().getCanonicalName());
			persistence.init(servletContext);
		} catch (Exception ex) {
			LOG.error("caught exception during setup of the critical components.", ex);
			throw new IllegalStateException("caught exception during setup of the critical components.", ex);
		}
    	LOG.debug("... initialization done.");
	}
	
	/**
	 * get a bean by its name and class. the bean is fetched via name and then automatically casted.
	 * @param beanName the name of the bean.
	 * @param beanClass the target cast.
	 * @return the bean.
	 */
	@SuppressWarnings("unchecked")
	public static <S> S getBeanByName(String beanName, Class<S> beanClass) {
		check();
		return (S) getBeanByName(beanName);
	}

	/**
	 * get a bean by its name.
	 * @param beanName the name of the bean.
	 * @return the bean.
	 */
	public static Object getBeanByName(String beanName) {
		check();
		return applicationContext.getBean(beanName);
	}
	
	/**
	 * returns the registered bean from the application context to the given class.
	 * @param beanClass the class of the desired bean.
	 * @return a handle onto the bean or null if not found.
	 */
	public static <S> S getBean(Class<S> beanClass) {
		check();
		return applicationContext.getBean(beanClass);
	}
	
	/**
	 * check that the application context is never null - should never occur if container is correctly initialized.
	 * @throws IllegalStateException when the application context is null.
	 */
	private static void check() throws IllegalStateException {
		if (null == applicationContext) {
			LOG.error("illegal application state: application context is null - aborting");
			throw new IllegalStateException("illegal application state: application context is null - aborting");
		}
	}
	
	@PreDestroy
	public void shutdown() {
		LOG.info("======================================== shutdown hook received. ========================================");
		try {
			// stop all the readers
			LOG.info("shutting down all the readers.");
			LogicalReaderManager lrm = applicationContext.getBean(LogicalReaderManager.class);
			if (null != lrm) {
				for (LogicalReader logicalReader : lrm.getLogicalReaders()) {
					LOG.info("Stopping reader: " + logicalReader.getName());
					logicalReader.stop();
				}
			}
			LOG.info("shutting down all reports generators and event cycles.");
			ALE ale = applicationContext.getBean(ALE.class);
			if (null != ale) {
				for (ReportsGenerator generator : ale.getReportGenerators().values()) {
					LOG.info("Setting reports generator state to unrequested for generator: " + generator.getName());
					generator.setStateUnRequested();
				}
			}
		} catch (Exception ex) {
			LOG.error("caught exception in shutdown hook: ", ex);
		}
		LOG.info("======================================== shutdown hook over. ========================================");
	}

}
