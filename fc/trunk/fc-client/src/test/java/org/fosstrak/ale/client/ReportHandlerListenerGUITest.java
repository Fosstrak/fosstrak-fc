/*
 * Copyright (C) 2007 ETH Zurich
 *
 * This file is part of Accada (www.accada.org).
 *
 * Accada is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License version 2.1, as published by the Free Software Foundation.
 *
 * Accada is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Accada; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
 * Boston, MA  02110-1301  USA
 */

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