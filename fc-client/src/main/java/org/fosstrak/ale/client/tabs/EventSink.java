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

package org.fosstrak.ale.client.tabs;

import java.awt.BorderLayout;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.fosstrak.ale.client.ReportHandler;
import org.fosstrak.ale.client.ReportHandlerListener;
import org.fosstrak.ale.client.exception.FosstrakAleClientException;
import org.fosstrak.ale.util.SerializerUtil;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReports;

/**
 * Event sink tab to display Event Cycle reports.
 * 
 * @author sawielan
 */
public class EventSink extends JPanel implements ReportHandlerListener {

	/** 
	 * default serial version uid 
	 */
	private static final long serialVersionUID = 1L;
	
	/** 
	 * text area where the ec reports will be displayed 
	 */
	private final JTextArea m_ecReportArea = new JTextArea();
	
	/** 
	 * scroll pane where the ecReportArea is added 
	 */
	private final JScrollPane m_scrollPane = new JScrollPane(m_ecReportArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	
	/**
	 * how many reports received.
	 */
	private int m_numberReceived = 0;
	
	/**
	 * This constructor creates a new sink listening on the specified url.
	 * 
	 * @param eventSinkURL the URL. 
	 */
	public EventSink(String eventSinkURL) throws FosstrakAleClientException {
		
		try {
			URL url = new URL(eventSinkURL);
			ReportHandler reportHandler = new ReportHandler(url.getPort());
			reportHandler.addListener(this);
						
			this.setLayout(new BorderLayout());
			this.add(m_scrollPane, BorderLayout.CENTER);
			
			m_scrollPane.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("EC Reports"), BorderFactory.createEmptyBorder(5,5,5,5)));
			
			m_ecReportArea.setEditable(true);
			
			this.setVisible(true);
		} catch (Exception e) {
			throw new FosstrakAleClientException(e);
		}
		
	}
	
	/**
	 * This method is invoked if the ReportHandler receives reports
	 * 
	 * @param reports the ec reports which were received
	 */
	public void dataReceived(ECReports reports) {
		try {
			CharArrayWriter writer = new CharArrayWriter();
			SerializerUtil.serializeECReportsPretty(reports, writer);
			
			StringBuffer sb = new StringBuffer();
			sb.append(String.format("Received Report number %d. Time %s\n\n", ++m_numberReceived, new Date(System.currentTimeMillis()).toString()));
			sb.append(writer.toString());
			sb.append("\n===========================================\n");
			sb.append(m_ecReportArea.getText());
			
			m_ecReportArea.setText(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method returns the content of the ec report area.
	 * 
	 * @return content of the ec report area
	 */
	public String getData() {
		
		return m_ecReportArea.getText();
		
	}
}