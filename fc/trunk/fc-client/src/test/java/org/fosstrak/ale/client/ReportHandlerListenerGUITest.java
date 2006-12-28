package org.accada.ale.client;

import java.io.ByteArrayInputStream;

import org.accada.ale.util.DeserializerUtil;
import org.accada.ale.xsd.ale.epcglobal.ECReports;

import util.ECElementsUtils;

import junit.framework.TestCase;

public class ReportHandlerListenerGUITest extends TestCase {

	private static final int NOTIFICATION_PORT = 6789;

	private static final ECReports EC_REPORTS = ECElementsUtils.createECReports();
	
	private ReportHandlerListenerGUI gui;
	
	protected void setUp() throws Exception {

		super.setUp();
		gui = new ReportHandlerListenerGUI(NOTIFICATION_PORT);
		
	}
	
	public void testDataReceived() throws Exception {
		
		gui.dataReceived(EC_REPORTS);
		assertEquals(EC_REPORTS, DeserializerUtil.deserializeECReports(new ByteArrayInputStream(gui.getData().getBytes())));
		
	}
	
}