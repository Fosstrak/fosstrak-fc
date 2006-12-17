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

package org.accada.ale.client;

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

import org.accada.ale.util.SerializerUtil;
import org.accada.ale.wsdl.ale.epcglobal.ImplementationException;
import org.accada.ale.xsd.ale.epcglobal.ECReports;

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
		} catch (ImplementationException e) {
			e.printStackTrace();
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
			ecReportArea.append(writer.toString());
			ecReportArea.append("\n\n");
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