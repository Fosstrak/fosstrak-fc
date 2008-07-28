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

package org.fosstrak.ale.client;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.CharArrayWriter;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.fosstrak.ale.wsdl.ale.epcglobal.ImplementationException;
import org.fosstrak.ale.wsdl.ale.epcglobal.ImplementationExceptionResponse;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReports;

/**
 * This listener displays all ec reports it receives.
 * 
 * @author regli
 */
public class ReportHandlerListenerGUI extends JFrame implements ReportHandlerListener {

	/** default serial version uid */
	private static final long serialVersionUID = 1L;
	/** text area where the ec reports will be displayed */
	private final JTextArea ecReportArea = new JTextArea();
	/** scroll pane where the ecReportArea is added */
	private final JScrollPane scrollPane = new JScrollPane(ecReportArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	
	/**
	 * This constructor creates a new ReportHandlerListenerGUI and adds it to the specified ReportHandler.
	 * 
	 * @param reportHandler to which the ReportHandlerListenerGUI should be added
	 */
	public ReportHandlerListenerGUI(ReportHandler reportHandler) {
		
		reportHandler.addListener(this);
		initializeGUI();
		
	}

	/**
	 * This constructor creates a new ReportHandlerListenerGUI and a new ReportHandler on the specified port number.
	 * 
	 * @param port
	 */
	public ReportHandlerListenerGUI(int port) {
		
		try {
			ReportHandler reportHandler = new ReportHandler(port);
			reportHandler.addListener(this);
			initializeGUI();
		} catch (ImplementationExceptionResponse e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * This method is invoked if the ReportHandler receives reports
	 * 
	 * @param reports the ec reports which were received
	 */
	public void dataReceived(ECReports reports) {
		/* FIXME
		try {
			CharArrayWriter writer = new CharArrayWriter();
			SerializerUtil.serializeECReportsPretty(reports, writer);
			ecReportArea.append(writer.toString());
			ecReportArea.append("\n\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
	}
	
	/**
	 * This method returns the content of the ec report area.
	 * 
	 * @return content of the ec report area
	 */
	public String getData() {
		
		return ecReportArea.getText();
		
	}
	
	/**
	 * This method starts the ReportHandlerListenerGUI.
	 * 
	 * @param args command line arguments, which can contain the port number
	 */
	public static void main(String[] args) {
		
		int port = 9876;
		if (args.length == 1) {
			try {
				port = Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {}
		}
		
		new ReportHandlerListenerGUI(port);
		
	}
	
	/**
	 * This method initializes the GUI.
	 */
	private void initializeGUI() {
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800, 600);
		this.setTitle("ReportHandlerGUI");
		this.setJMenuBar(createMenuBar());
		
		this.setLayout(new BorderLayout());
		this.add(scrollPane, BorderLayout.CENTER);
		
		scrollPane.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("EC Reports"), BorderFactory.createEmptyBorder(5,5,5,5)));
		
		ecReportArea.setEditable(true);
		
		this.setVisible(true);
		
	}
	
	/**
	 * This method creates the menu bar.
	 * 
	 * @return menu bar
	 */
	private JMenuBar createMenuBar() {
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(createFileMenu());
		
		return menuBar;
		
	}
	
	/**
	 * This method creates the file menu.
	 * 
	 * @return file menu
	 */
	private Component createFileMenu() {
		
		JMenu fileMenuItem = new JMenu("File");
		
		JMenuItem exitMenuItem = new JMenuItem("Quit");
		exitMenuItem.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				System.exit(0);
			}
		});
		fileMenuItem.add(exitMenuItem);
		
		return fileMenuItem;
		
	}
	
}