package org.fosstrak.ale.server.llrp;


import org.apache.log4j.Logger;
import org.llrp.ltk.generated.messages.GET_ROSPECS;

/**
 * ORANGE: This class is a thread which checks that 
 * a physical reader has always at least one defined ROSpec.
 * 
 * @author wafa.soubra@orange.com
 */
public class LLRPChecking implements Runnable {

	/**	logger. */
	private static final Logger LOG = Logger.getLogger(LLRPChecking.class.getName());
	
	/** physical reader name */
	private String readerName = null;

	/** thread to run the main loop */
	private Thread thread;
	
	/** indicates if this thread is running or not */
	private boolean isRunning = false;
	
	public LLRPChecking (String readerName) {
		this.readerName = readerName;
		this.start();
	}
	
	/** 
	 * Gets the physical reader name associated to this thread.
	 */
	public String getReaderName() {
		return this.readerName;
	}
	
	/**
	 * This method starts the thread to check if a physical reader
	 * has always a defined ROSpec. 
	 */
	public void start() {		
		thread = new Thread(this);
		thread.start();
		isRunning = true;
		LOG.info("LLRP Thread of " + this.readerName + " is started.");		
	}
	
	/**
	 * This method stops the thread.
	 */
	public void stop() {		
		if (thread.isAlive()) {
			thread.interrupt();
		}
		isRunning = false;		
		LOG.info("LLRP Thread of " + this.readerName + " is stopped.");	
	}

	/**
	 * This method sends continuously the get_rospecs msg on the reader.
	 * 
	 */
	public void run() {
		GET_ROSPECS getRoSpecs = new GET_ROSPECS();
		while (isRunning) {
        	try {
        		AdaptorMgmt.sendLLRPMessage(this.readerName, getRoSpecs);
	    		Thread.sleep(10000);
        	}  catch (InterruptedException e) {
        		LOG.error("Error when LLRP thread is sleeping ", e);
        	}
        }
	}	
		
}
