/*
 * Copyright (c) 2006, ETH Zurich
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * - Redistributions of source code must retain the above copyright notice,
 *   this list of conditions and the following disclaimer.
 *
 * - Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * - Neither the name of the ETH Zurich nor the names of its contributors may be
 *   used to endorse or promote products derived from this software without
 *   specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.accada.ale.server.readers;

import org.accada.reader.hal.HardwareException;
import org.accada.reader.hal.Observation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



/**
 * An IdentifyThread encapsulates all the methods necessary for polling the device. The identification process
 * itself is based on the method <code>identify()</code> of the associated <code>AutoIdController</code>. 
 * 
 * @version 19.01.2004
 * @author Stefan Schlegel (schlstef@student.ethz.ch)
 * 
 * @uml stereotypes = {"thread"}
 */

public class IdentifyThread implements Runnable {
	/**
	 *@uml property=adapter associationEnd={multiplicity={(1 1)} inverse={identifyThread:ch.mlab.rfidframework.hal.ContinuousIdentifyAdapter}}  
	 */
	//---------------fields----------------------------------------------------------------//
	/** the polling thread. */
	private Thread identification;
	/** indicates if the thread shall be suspended. */
	private boolean suspendThread = true;
	/** indicates if the thread is running. */
	private boolean isRunning = false;
	/** the controller instance associated with this thread. */
	private BaseReader adapter;
	/** reference to ObservationBuffer */
	//private ObservationBuffer buffer;
	/** the logger. */
	private static Log log = LogFactory.getLog(IdentifyThread.class); 
	/** the frequency in millisecons the thread has to poll the device with. */
	private long frequency;
	/** the parameters for the identify method... */
	private String prefix;
	private int estimateNoTags;
	private String mode;
	private String diagnostic;
	
	/** sourceIds are used for continuousIdentifying with a Multi-antenna reader (Multiplexer). 
	 * With a single antenna reader it should be: "sourceIds" = null .*/
	private String[] sourceIds;
	
	//------------------constructors------------------------------------------------------//
	
	/**
	 * Constructs an instance of IdentifyThread.
	 */
	public IdentifyThread() {
		super();
	}
	
	
	/**
	 * Constructs an instance of IdentifyThread.
	 * 
	 * @param adapter the associated controller
	 *@param buffer the buffer to put in the observations
	 */
	public IdentifyThread(BaseReader adapter) {
//	public IdentifyThread(ContinuousIdentifyAdapter adapter, ObservationBuffer buffer) {
		super();
		this.adapter = adapter;
		//this.buffer=buffer;
	}
	
	//------------------methods-----------------------------------------------------------------//
	
	/**
	 * Polls the RFID hardware for RFID tags in the
	 * fields.
	 * 
	 */
	public void run() {
		Thread thisThread = Thread.currentThread();
		log.debug("Scanning started...");
		while (this.identification == thisThread) {
			synchronized (this) {
				while (suspendThread) {
					log.debug("Scanning suspended...");
					try {
						wait();
					} catch (Exception e) {
						//TODO handle Exception
						this.stopIdentify();
					}
				}
			}
			// Do work
			log.debug("Continuous identify...");
			try {
				Observation[] obs = null;
				
				/*
				if (sourceIds == null) { //single reader mode
					log.debug("SingleReaderMode...");
					obs = this.adapter.identify(this.prefix, this.estimateNoTags, this.mode, this.diagnostic);
				}
				else { //MultiplexerMode
					log.debug("MultiplexerMode...");
					obs = this.adapter.multiplexIdentify(this.sourceIds, this.prefix, this.estimateNoTags, this.mode, this.diagnostic);
				}
				*/
				obs = this.adapter.identify(sourceIds);
				
//				put the observations into the buffer...
				/*
				if (obs != null) {
					;//buffer.putAll(obs);
				} else {
					log.debug("No observations to put into buffer");
				}
				*/
			}
			catch (HardwareException e){
				e.printStackTrace();
				//this.stopIdentify();
			}
			try {
				if (this.frequency > 0){
					log.debug("Thread is going to sleep...");
					Thread.sleep(this.frequency);
				}
			} catch (InterruptedException e){
				//TODO handle Exception
			}
		}
	}
	
	
	/**
	 * Initializes the polling thread.
	 *
	 */
	public void initPolling(String prefix, int estimateNoTags, String mode, String diagnostic){
	 this.prefix = prefix;
	 this.estimateNoTags = estimateNoTags;
	 this.mode = mode;
	 this.diagnostic = diagnostic;
	}
	
	
	/**
	 * Starts an instance of IdentifyThread
	 *
	 */
	public void start(){
		this.identification=new Thread(this);
		log.debug("Trying to start Scanning...");
		this.isRunning=true;
		this.identification.start();
		
	}
	
	
	/**
	 * Suspends the IdentifyThread
	 *
	 */
	public synchronized void suspendIdentify(){
		this.suspendThread=true;
		this.isRunning=false;
	}
	
	
	/**
	 * Resumes the IdentifyThread
	 */
	public synchronized void resumeIdentify(){
		this.suspendThread=false;
		log.debug("Scanning resumed...");
		this.isRunning=true;
		notify();
	}
	
	
	/**
	 * Stops the Thread 
	 *
	 */
	public synchronized void stopIdentify(){
		log.debug("Scanning stopped...");
		this.identification=null;
		this.isRunning=false;
	}
	
	
	/**
	 * Gets the current polling frequency
	 * 
	 * @return the current polling frequency
	 */
	public long getPollingFrequency() {
		return frequency;
	}


	/**
	 * Sets the polling frequency.
	 * The frequency is given in milliseconds indicating
	 * how long the thread has to sleep between two executions.
	 * 
	 * @param frequency the polling frequency in milliseconds.
	 */
	public void setPollingFrequency(long frequency) {
		this.frequency = frequency;
	}
	
	public void setSourceIds(String[] sourceIds){
		this.sourceIds = sourceIds;
	}
	
	
	/**
	 * Returns true if the thread is currently running, false 
	 * if it is not.
	 * 
	 * @return boolean indicating if the thread is currently 
	 * running or not
	 */
	public boolean isRunning(){
		return this.isRunning;
	}

	
	/**
	 * Gets the Controller instance associated with this IdentificationThread.
	 * 
	 */
	public BaseReader getAdapter() {
		return this.adapter;
	}

}
