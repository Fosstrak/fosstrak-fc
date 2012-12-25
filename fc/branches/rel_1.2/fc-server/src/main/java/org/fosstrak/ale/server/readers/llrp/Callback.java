package org.fosstrak.ale.server.readers.llrp;

import java.rmi.RemoteException;
import java.rmi.StubNotFoundException;
import java.rmi.server.UnicastRemoteObject;

import org.apache.log4j.Logger;
import org.fosstrak.llrp.adaptor.AsynchronousNotifiable;
import org.fosstrak.llrp.adaptor.exception.LLRPRuntimeException;

/**
 * creates a callback instance that retrieves asynchronous messages.
 * @author swieland
 *
 */
public class Callback extends UnicastRemoteObject implements AsynchronousNotifiable {
	
	/**
	 * serial id.
	 */
	private static final long serialVersionUID = 1L;
	
	/** the worker that holds this callback. */
	private LLRPAdaptor adaptor = null;

	/** logger */
	private static final Logger LOG = Logger.getLogger(Callback.class);
	
	/**
	 * creates a callback instance that retrieves asynchronous messages.
	 * @param adaptor the adaptor associated to this callback.
	 * @throws RemoteException when there is an rmi exception.
	 */
	public Callback(LLRPAdaptor adaptor) throws RemoteException {
		try {
			UnicastRemoteObject.exportObject(this);
		} catch (StubNotFoundException e) {
			// this exception is normal as exportObject is backwards compatible to 
			// java 1.4. since java 1.5 the stub gets auto-generated and so 
			// there is no stub available -> exception. we can safely 
			// ignore this exception.
		} catch (RemoteException e) {
			LOG.debug("expected remote object exception", e);
		}
		this.adaptor = adaptor;
	}
	

	/* (non-Javadoc)
	 * @see org.accada.llrp.client.adaptor.AsynchronousNotifiable#notify(byte[], java.lang.String)
	 */
	public void notify(byte[] message, String readerName) throws RemoteException {
		adaptor.notify(message, readerName);
	}
	
	/* (non-Javadoc)
	 * @see org.accada.llrp.client.adaptor.AsynchronousNotifiable#notify(byte[], java.lang.String)
	 */
	public void notifyError(LLRPRuntimeException e, String readerName) throws RemoteException {
		LOG.error("error occured on reader " + readerName, e);
	}
}