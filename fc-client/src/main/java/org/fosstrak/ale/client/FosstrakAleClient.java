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
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.fosstrak.ale.client.cfg.Configuration;
import org.fosstrak.ale.client.exception.FosstrakAleClientException;
import org.fosstrak.ale.client.tabs.ALEClient;
import org.fosstrak.ale.client.tabs.ALELRClient;
import org.fosstrak.ale.client.tabs.EventSink;

/**
 * @author sawielan
 * 
 * Main class for the fosstrak ale client. reads the configuration, 
 * generates the tabs and runs the client.
 *
 */
public class FosstrakAleClient extends JFrame  {
	
	// serial version uid.
	private static final long serialVersionUID = 7432114226072914098L;
	
	// handle to the singleton.
	private static FosstrakAleClient s_instance;
	
	// handle to the configuration.
	private Configuration m_configuration;
	
	// handle to the tabbed pane.
	private JTabbedPane m_tab;
	
	// logger.
	private static final Logger s_log = Logger.getLogger(FosstrakAleClient.class);
	
	/**
	 * private constructor for the singleton.
	 * @throws FosstrakAleClientException upon start problems.
	 */
	private FosstrakAleClient() throws FosstrakAleClientException {
		
	}
	
	/**
	 * allows to configure the client.
	 * @param cfg the configuration object.
	 * @throws FosstrakAleClientException upon error in startup procedure.
	 */
	public void configure(Configuration cfg) throws FosstrakAleClientException {
		m_configuration = cfg;
	}
	
	/**
	 * starts up the client.
	 * @throws FosstrakAleClientException upon error in startup procedure.
	 */
	public void execute() throws FosstrakAleClientException {

		Font font = FosstrakAleClient.instance().getConfiguration().getFont();
		m_tab = new JTabbedPane();
        m_tab.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        m_tab.setFont(font);
		
		// add the legacy clients into the tabs...
        try {
        	ALEClient aleClient = new ALEClient(this);;
			aleClient.initialize();
        	ALELRClient lrClient = new ALELRClient(this);
			lrClient.initialize();
			
			m_tab.addTab("Event Cycle", aleClient);
			m_tab.addTab("Logical Reader", lrClient);
        } catch (Exception e) {
        	s_log.error("Could not setup basic GUI components.");
        	throw new FosstrakAleClientException(e);
        }		
		add(m_tab);
				
		JMenu fileMenuItem = new JMenu("File");
		JMenuItem exitMenuItem = new JMenuItem("Quit");
		exitMenuItem.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				for (Component comp : m_tab.getComponents())
				{
					if (comp instanceof EventSink)
					{
						((EventSink)comp).quitSink();
					}
				}
				System.exit(0);
			}
		});
		fileMenuItem.add(exitMenuItem);
		
		JMenu esMenuItem = new JMenu("Sink");
		JMenuItem createSink = new JMenuItem("Create Sink");
		createSink.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Not implemented yet.");
			}
		});
		esMenuItem.add(createSink);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(fileMenuItem);
		menuBar.add(esMenuItem);
		
		setJMenuBar(menuBar);
		
		setSize(m_configuration.getWindowSize());
		setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
	}
	
	public void addTab(String title, JPanel tab) {
		m_tab.addTab(title, tab);
	}
	
	/**
	 * This method creates a exception dialog and sets it visible.
	 * 
	 * @param message to display
	 * @param reason to display
	 */
	public void showExceptionDialog(String message, String reason) {
		
		Rectangle guiBounds = this.getBounds();
		Dimension d = m_configuration.getExceptionWindowSize();
		int width = d.width;
		int height = d.height;
		int xPos = guiBounds.x + (guiBounds.width - width) / 2;
		int yPos = guiBounds.y + (guiBounds.height - height) / 2;
		
		final JDialog dialog = new JDialog(this, true);
		dialog.setBounds(xPos, yPos, width, height);
		dialog.setLayout(new BorderLayout(10, 10));
		dialog.setTitle("Error message:");
		
		// message label
		JLabel messageLabel = new JLabel(message);
		JPanel messageLabelPanel = new JPanel();
		messageLabelPanel.add(messageLabel);
		
		// reason panel
		JTextArea reasonTextArea = new JTextArea(reason);
		JScrollPane reasonScrollPane = new JScrollPane(reasonTextArea);
		reasonScrollPane.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Details"), BorderFactory.createEmptyBorder(5,5,5,5)));
		reasonTextArea.setEditable(false);
		
		// ok button
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dialog.setVisible(false);
			}
		});
		
		// put all together
		dialog.add(messageLabelPanel, BorderLayout.NORTH);
		dialog.add(reasonScrollPane, BorderLayout.CENTER);
		dialog.add(okButton, BorderLayout.SOUTH);
		dialog.setVisible(true);
	}
	
	/**
	 * This method creates a exception dialog and sets it visible.
	 * 
	 * @param text to display
	 */
	public void showExceptionDialog(String text) {
		showExceptionDialog(text, null);		
	}
	
	/**
	 * displays the connect dialog. the user is requested to insert the url of the service.
	 * @param defaultKey the key to the default setting.
	 * @return the connect url for the service.
	 */
	public String showConnectDialog(String defaultKey) {
		Rectangle guiBounds = this.getBounds();
		Dimension d = m_configuration.getEndpointWindowSize();
		int width = d.width;
		int height = d.height;
		int xPos = guiBounds.x + (guiBounds.width - width) / 2;
		int yPos = guiBounds.y + (guiBounds.height - height) / 2;
		
		final JDialog dialog = new JDialog(this, true);
		dialog.setBounds(xPos, yPos, width, height);
		dialog.setLayout(new BorderLayout(10, 10));
		dialog.setTitle("Endpoint connection");
		
		// message label
		JLabel messageLabel = new JLabel("Endpoint address:");
		JPanel messageLabelPanel = new JPanel();
		messageLabelPanel.add(messageLabel);
		
		// endpoint panel
		String ep = getConfiguration().getProperty(defaultKey);
		JTextField fldEndpoint = new JTextField(ep);
		fldEndpoint.setEditable(true);

		// ok button
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dialog.setVisible(false);
			}
		});
		
		// put all together
		dialog.add(messageLabelPanel, BorderLayout.NORTH);
		dialog.add(fldEndpoint, BorderLayout.CENTER);
		dialog.add(okButton, BorderLayout.SOUTH);
		dialog.setVisible(true);

		return fldEndpoint.getText();
	}
	
	/**
	 * @return a handle to the configuration.
	 */
	public final Configuration getConfiguration() {
		return m_configuration;
	}
	
	/**
	 * @return handle to the singleton of the ale client.
	 */
	public static FosstrakAleClient instance() {
		if (null == s_instance) {
			try {
				s_instance = new FosstrakAleClient();
			} catch (FosstrakAleClientException e) {
				e.printStackTrace();
				System.exit(-1);
			}
		}
		
		return s_instance;
	}
	
	/**
	 * display a nice help text.
	 */
	public static void help() {
		StringBuffer sb = new StringBuffer();
		sb.append("===============================================\n");
		sb.append("Usage: java -jar fc-client-VERSION.jar org.fosstrak.ale.client.FosstrakAleClient [configFile]\n");
		sb.append("configFile: path to a configuration file (override the default config file within the jar).\n");
		sb.append("-h|--help|help: display this dialog\n");
		sb.append("===============================================\n");
		System.out.println(sb.toString());
		System.exit(0);
	}
	
	/**
	 * @param args command line arguments.
	 */
	public static void main(String[] args) throws FosstrakAleClientException {
		for (String arg : args) {
			if ("help".equalsIgnoreCase(arg)) help();
			if ("-h".equalsIgnoreCase(arg)) help();
			if ("--help".equalsIgnoreCase(arg)) help();
		}
		
		// configure Logger with properties file
		try {
			Properties p = new Properties();
			p.load(FosstrakAleClient.class.getResourceAsStream("/log4j.properties"));
			PropertyConfigurator.configure(p);
			s_log.debug("configured the logger.");
		} catch (Exception e) {
			s_log.info("Could not configure the logger.");
		}
		
		s_log.debug("preparing client for execution.");
		FosstrakAleClient.instance().configure(Configuration.getConfiguration(args));
		s_log.debug("executing client.");
		FosstrakAleClient.instance().execute();
	}

	/**
	 * remove the event sink from the tab list.
	 * @param eventSink the event sink.
	 */
	public void removeTab(EventSink eventSink) 
	{
		m_tab.remove(eventSink);
	}

}
