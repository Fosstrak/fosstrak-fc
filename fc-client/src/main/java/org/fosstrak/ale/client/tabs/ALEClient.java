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

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.CharArrayWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.xml.ws.soap.SOAPFaultException;

import org.apache.log4j.Logger;
import org.fosstrak.ale.client.FosstrakAleClient;
import org.fosstrak.ale.client.exception.FosstrakAleClientException;
import org.fosstrak.ale.client.exception.FosstrakAleClientServiceDownException;
import org.fosstrak.ale.util.DeserializerUtil;
import org.fosstrak.ale.util.SerializerUtil;
import org.fosstrak.ale.wsdl.ale.epcglobal.ALEServicePortType;
import org.fosstrak.ale.wsdl.ale.epcglobal.ArrayOfString;
import org.fosstrak.ale.wsdl.ale.epcglobal.Define;
import org.fosstrak.ale.wsdl.ale.epcglobal.DuplicateNameExceptionResponse;
import org.fosstrak.ale.wsdl.ale.epcglobal.DuplicateSubscriptionExceptionResponse;
import org.fosstrak.ale.wsdl.ale.epcglobal.ECSpecValidationExceptionResponse;
import org.fosstrak.ale.wsdl.ale.epcglobal.EmptyParms;
import org.fosstrak.ale.wsdl.ale.epcglobal.GetECSpec;
import org.fosstrak.ale.wsdl.ale.epcglobal.GetSubscribers;
import org.fosstrak.ale.wsdl.ale.epcglobal.Immediate;
import org.fosstrak.ale.wsdl.ale.epcglobal.ImplementationExceptionResponse;
import org.fosstrak.ale.wsdl.ale.epcglobal.InvalidURIExceptionResponse;
import org.fosstrak.ale.wsdl.ale.epcglobal.NoSuchNameExceptionResponse;
import org.fosstrak.ale.wsdl.ale.epcglobal.NoSuchSubscriberExceptionResponse;
import org.fosstrak.ale.wsdl.ale.epcglobal.Poll;
import org.fosstrak.ale.wsdl.ale.epcglobal.SecurityExceptionResponse;
import org.fosstrak.ale.wsdl.ale.epcglobal.Subscribe;
import org.fosstrak.ale.wsdl.ale.epcglobal.Undefine;
import org.fosstrak.ale.wsdl.ale.epcglobal.Unsubscribe;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReports;
import org.fosstrak.ale.xsd.ale.epcglobal.ECSpec;

/**
 * This class implements a graphical user interface for the application level
 * events client. The client send all commands as SOAP messages to the ale
 * server. The configuration of this class is described in the file
 * ALEClient.properties. The most important parameter is the parameter endpoint,
 * which specifies the address of the ale webservice which runs on the server.
 * 
 * @author regli
 * @author swieland
 */
public class ALEClient extends AbstractTab {

	/** serial version uid */
	private static final long serialVersionUID = 1L;
	
	/**
	 * endpoint parameter for the configuration.
	 */
	public static final String CFG_ENDPOINT = "org.fosstrak.ale.client.ale.endpoint";
	
	/** 
	 * text field which contains the notification uri.
	 */
	private JTextField m_notificationUriField;
	
	/**
	 * if the user checks this combo box, then an event sink is created for the subscription.
	 */
	private JCheckBox m_createEventSink;
	
	/** 
	 * text field which contains the reader name.
	 */
	private JTextField m_specNameValueField;
	
	/** 
	 * combobox which contains all defined subscribers for a selected event cycle.
	 */
	private JComboBox m_subscribersComboBox;
	
	// logger.
	private static final Logger s_log = Logger.getLogger(ALEClient.class);
	
	private static final int CMD__DEFINE = 1;
	private static final int CMD__UNDEFINE = 2;
	private static final int CMD__GET_ECSPEC = 3;
	private static final int CMD__GET_ECSPEC_NAMES = 4;
	private static final int CMD__SUBSCRIBE = 5;
	private static final int CMD__UNSUBSCRIBE = 6;
	private static final int CMD__POLL = 7;
	private static final int CMD__IMMEDIATE = 8;
	private static final int CMD__GET_SUBSCRIBERS = 9;
	private static final int CMD__GET_STANDARD_VERSION = 10;
	private static final int CMD__GET_VENDOR_VERSION = 11;
	/**
	 * @param parent the parent frame.
	 * @throws NoSuchMethodException given to the fact that we need to pass in a test method via reflection.
	 * @throws SecurityException given to the fact that we need to pass in a test method via reflection.
	 */
	public ALEClient(JFrame parent) throws SecurityException, NoSuchMethodException {
		super(ALEServicePortType.class, CFG_ENDPOINT, parent, ALEServicePortType.class.getMethod("getStandardVersion", EmptyParms.class), new EmptyParms());
	}

	@Override
	public String getBaseNameKey() {
		return "org.fosstrak.ale.client.ale.lang.base";
	}

	@Override
	protected void setCommandPanel(int command) {
		
		if (command == CMD__UNDEFINED_COMMAND) {
			m_commandPanel.removeAll();
			m_commandPanel.setBorder(null);
			this.setVisible(false);
			this.setVisible(true);
			return;
		}

		m_commandPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory
				.createTitledBorder(m_guiText.getString("Command" + command)),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		m_commandPanel.removeAll();

		switch (command) {

		case CMD__GET_ECSPEC_NAMES: // getECSpecNames
		case CMD__GET_STANDARD_VERSION: // getStandardVersion
		case CMD__GET_VENDOR_VERSION: // getVendorVersion
			m_commandPanel.setLayout(new GridLayout(1, 1, 5, 0));
			break;

		case CMD__UNDEFINE: // undefine
		case CMD__GET_ECSPEC: // getECSpec
		case CMD__POLL: // poll
		case CMD__GET_SUBSCRIBERS: // getSubscribers
			m_commandPanel.setLayout(new GridLayout(5, 1, 5, 0)); 
			addECSpecNameComboBox(m_commandPanel);
			addSeparator(m_commandPanel);
			break;

		case CMD__SUBSCRIBE: // subscribe
			m_commandPanel.setLayout(new GridLayout(11, 1, 5, 0));
			addECSpecNameComboBox(m_commandPanel);
			addNotificationURIField(m_commandPanel);
			
			m_createEventSink = new JCheckBox();
			JLabel lbl = new JLabel(m_guiText.getString("CreateEventSink"));
			lbl.setFont(m_font);
			m_commandPanel.add(lbl);
			m_commandPanel.add(m_createEventSink);
		
			addSeparator(m_commandPanel);
			break;

		case CMD__UNSUBSCRIBE: // unsubscribe
			m_commandPanel.setLayout(new GridLayout(9, 1, 5, 0));
			addECSpecNameComboBox(m_commandPanel);
			addNotificationURIComboBox(m_commandPanel);
					
			addSeparator(m_commandPanel);
			break;

		case CMD__IMMEDIATE: // immediate
			m_commandPanel.setLayout(new GridLayout(6, 1, 5, 0));
			addChooseFileField(m_commandPanel);
			addSeparator(m_commandPanel);
			break;

		case CMD__DEFINE: // define
			m_commandPanel.setLayout(new GridLayout(8, 1, 5, 0));
			addSpecNameValueField(m_commandPanel);
			addChooseFileField(m_commandPanel);
			addSeparator(m_commandPanel);
			break;

		}

		m_commandPanel.setFont(m_font);
		addExecuteButton(m_commandPanel);
		
		validate();
		this.setVisible(true);
	}

	/**
	 * This method adds a specification name combobox to the panel.
	 * 
	 * @param panel to which the specification name combobox should be added
	 */
	private void addECSpecNameComboBox(JPanel panel) {

		m_specNameComboBox = new JComboBox();
		m_specNameComboBox.setFont(m_font);
		m_specNameComboBox.setEditable(false);

		List<String> ecSpecNames = null;
		try {
			ecSpecNames = getAleServiceProxy().getECSpecNames(new EmptyParms()).getString();
		} catch (Exception e) {
		}
		if (ecSpecNames != null && ecSpecNames.size() > 0) {
			for (String specName : ecSpecNames) {
				m_specNameComboBox.addItem(specName);
			}
		} else {
			m_specNameComboBox.addItem("no specs defined");
		}
		JLabel lbl = new JLabel(m_guiText.getString("SpecNameLabel"));
		lbl.setFont(m_font);
		panel.add(lbl);
		panel.add(m_specNameComboBox);
	}
	
	/**
	 * This method adds the subscriber names combobox to the panel.
	 * 
	 * @param panel to which the specification name combobox should be added
	 */
	private void addNotificationURIComboBox(JPanel panel) {

		m_subscribersComboBox = new JComboBox();
		m_subscribersComboBox.setFont(m_font);
		m_subscribersComboBox.setEditable(false);
		fillSubscribersList();

		m_specNameComboBox.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				fillSubscribersList();			
			}
			
		});
		
		JLabel lbl = new JLabel(m_guiText.getString("NotificationURILabel"));
		lbl.setFont(m_font);
		panel.add(lbl);
		panel.add(m_subscribersComboBox);
	}
	
	/**
	 * populates the subscribers list.
	 */
	private void fillSubscribersList() {
		m_subscribersComboBox.removeAllItems();
		String spec = (String) m_specNameComboBox.getSelectedItem();
		if (null != spec) {
			ArrayOfString subscribers = null;
			try {
				GetSubscribers parms = new GetSubscribers();
				parms.setSpecName(spec);
				subscribers = getAleServiceProxy().getSubscribers(parms);
			} catch (Exception e) {
			}
			if (subscribers.getString() != null && subscribers.getString().size() > 0) {
				for (String subscriber : subscribers.getString()) {
					m_subscribersComboBox.addItem(subscriber);
				}
				return;
			}
		}
		m_subscribersComboBox.addItem("no subscribers defined");	
	}

	/**
	 * This method adds a notification property value field to the panel.
	 * 
	 * @param panel to which the property value field should be added
	 */
	private void addSpecNameValueField(JPanel panel) {
		
		m_specNameValueField = new JTextField();
		m_specNameValueField.setFont(m_font);
		
		JLabel lbl = new JLabel(m_guiText.getString("SpecNameLabel"));
		lbl.setFont(m_font);
		panel.add(lbl);
		panel.add(m_specNameValueField);
	}

	/**
	 * This method adds a notification uri field to the panel.
	 * 
	 * @param panel to which the norification uri field should be added
	 */
	private void addNotificationURIField(JPanel panel) {

		m_notificationUriField = new JTextField();
		m_notificationUriField.setFont(m_font);

		JLabel lbl = new JLabel(m_guiText.getString("NotificationURILabel"));
		lbl.setFont(m_font);
		panel.add(lbl);
		panel.add(m_notificationUriField);
	}

	@Override
	protected void executeCommand() {

		Object result = null;
		String specName = null;
		String notificationURI = null;
		Exception ex = null;
		try {

			switch (m_commandSelection.getSelectedIndex()) {

			case CMD__GET_ECSPEC_NAMES: // getECSpecNames
				result = getAleServiceProxy().getECSpecNames(new EmptyParms());
				break;

			case CMD__GET_STANDARD_VERSION: // getStandardVersion
				result = getAleServiceProxy().getStandardVersion(new EmptyParms());
				break;

			case CMD__GET_VENDOR_VERSION: // getVendorVersion
				result = getAleServiceProxy().getVendorVersion(new EmptyParms());
				break;

			case CMD__UNDEFINE: // undefine
			case CMD__GET_ECSPEC: // getECSpec
			case CMD__POLL: // poll
			case CMD__GET_SUBSCRIBERS: // getSubscribers
				// get specName
				specName = (String) m_specNameComboBox.getSelectedItem();
				if (specName == null || "".equals(specName)) {
					FosstrakAleClient.instance().showExceptionDialog(
							m_guiText.getString("SpecNameNotSpecifiedDialog"));
					break;
				}

				switch (m_commandSelection.getSelectedIndex()) {

				case CMD__UNDEFINE: // undefine
					Undefine undefineParms = new Undefine();
					undefineParms.setSpecName(specName);
					getAleServiceProxy().undefine(undefineParms);
					result = m_guiText.getString("SuccessfullyUndefinedMessage");
					break;

				case CMD__GET_ECSPEC: // getECSpec
					GetECSpec getECSpecParms = new GetECSpec();
					getECSpecParms.setSpecName(specName);
					result = getAleServiceProxy().getECSpec(getECSpecParms);
					break;

				case CMD__POLL: // poll
					Poll pollParms = new Poll();
					pollParms.setSpecName(specName);
					result = getAleServiceProxy().poll(pollParms);
					break;

				case CMD__GET_SUBSCRIBERS: // getSubscribers
					GetSubscribers getSubscribersParms = new GetSubscribers();
					getSubscribersParms.setSpecName(specName);
					result = getAleServiceProxy().getSubscribers(getSubscribersParms);
					break;

				}

				break;

			case CMD__SUBSCRIBE: // subscribe
			case CMD__UNSUBSCRIBE: // unsubscribe
				// get specName
				specName = (String) m_specNameComboBox.getSelectedItem();
				if (specName == null || "".equals(specName)) {
					FosstrakAleClient.instance().showExceptionDialog(m_guiText.getString("SpecNameNotSpecifiedDialog"));
					break;
				}

				switch (m_commandSelection.getSelectedIndex()) {

				case CMD__SUBSCRIBE:

					// get notificationURI
					notificationURI = m_notificationUriField.getText();
					if (notificationURI == null || "".equals(notificationURI)) {
						FosstrakAleClient.instance().showExceptionDialog(m_guiText.getString("NotificationUriNotSpecifiedDialog"));
						break;
					}
					
					if (m_createEventSink.isSelected()) {
						createEventSink(m_notificationUriField.getText());
					}
					
					Subscribe subscribeParms = new Subscribe();
					subscribeParms.setSpecName(specName);
					subscribeParms.setNotificationURI(notificationURI);
					getAleServiceProxy().subscribe(subscribeParms);
					result = m_guiText.getString("SuccessfullySubscribedMessage");
					break;

				case CMD__UNSUBSCRIBE:

					// get notificationURI
					notificationURI = (String) m_subscribersComboBox.getSelectedItem();
					if (notificationURI == null || "".equals(notificationURI)) {
						FosstrakAleClient.instance().showExceptionDialog(m_guiText.getString("NotificationUriNotSpecifiedDialog"));
						break;
					}
					
					Unsubscribe unsubscribeParms = new Unsubscribe();
					unsubscribeParms.setSpecName(specName);
					unsubscribeParms.setNotificationURI(notificationURI);
					getAleServiceProxy().unsubscribe(unsubscribeParms);
					result = m_guiText.getString("SuccessfullyUnsubscribedMessage");
					break;

				}

				break;

			case CMD__DEFINE: // define
			case CMD__IMMEDIATE: // immediate

				if (m_commandSelection.getSelectedIndex() == CMD__DEFINE) {
					// get specName
					specName = m_specNameValueField.getText();
					if (specName == null || "".equals(specName)) {
						FosstrakAleClient.instance().showExceptionDialog(m_guiText.getString("SpecNameNotSpecifiedDialog"));
						break;
					}
				}

				// get filePath
				String filePath = m_filePathField.getText();
				if (filePath == null || "".equals(filePath)) {
					FosstrakAleClient.instance().showExceptionDialog(m_guiText.getString("FilePathNotSpecifiedDialog"));
					break;
				}

				// get ecSpec
				ECSpec ecSpec;
				try {
					ecSpec = getECSpecFromFile(filePath);
				} catch (FileNotFoundException e) {
					FosstrakAleClient.instance().showExceptionDialog(m_guiText.getString("FileNotFoundDialog"));
					ex = e;
					break;
				} catch (Exception e) {
					FosstrakAleClient.instance().showExceptionDialog(m_guiText.getString("UnexpectedFileFormatDialog"));
					ex = e;
					break;
				}

				if (m_commandSelection.getSelectedIndex() == CMD__DEFINE) {
					Define defineParms = new Define();
					defineParms.setSpecName(specName);
					defineParms.setSpec(ecSpec);
					getAleServiceProxy().define(defineParms);
					result = m_guiText.getString("SuccessfullyDefinedMessage");
				} else {
					Immediate immediateParms = new Immediate();
					immediateParms.setSpec(ecSpec);
					result = getAleServiceProxy().immediate(immediateParms);
				}
				break;

			}

		} catch (Exception e) {
			String reason = e.getMessage();
			
			String text = "Unknown Error";
			if (e instanceof DuplicateNameExceptionResponse) {
				text = m_guiText.getString("DuplicateNameExceptionDialog");
			} else if (e instanceof DuplicateSubscriptionExceptionResponse) {
				text = m_guiText.getString("DuplicateSubscriptionExceptionDialog");
			} else if (e instanceof ECSpecValidationExceptionResponse) {
				text = m_guiText.getString("ECSpecValidationExceptionDialog");
			} else if (e instanceof ImplementationExceptionResponse) {
				text = m_guiText.getString("ImplementationExceptionDialog");
			} else if (e instanceof InvalidURIExceptionResponse) {
				text = m_guiText.getString("InvalidURIExceptionDialog");
			} else if (e instanceof NoSuchNameExceptionResponse) {
				text = m_guiText.getString("NoSuchNameExceptionDialog");
			} else if (e instanceof NoSuchSubscriberExceptionResponse) {
				text = m_guiText.getString("NoSuchSubscriberExceptionDialog");
			} else if (e instanceof SecurityExceptionResponse) {
				text = m_guiText.getString("SecurityExceptionDialog");
			} else if (e instanceof SOAPFaultException) {
				text = "Service error";
			} else if (e instanceof FosstrakAleClientServiceDownException) {
				text = "Unable to execute command.";
				reason = "Service is down or endpoint wrong.";
			}
			
			FosstrakAleClient.instance().showExceptionDialog(text, reason);
			ex = e;
		}

		if (null == ex) {
			showResult(result);
		} else {
			showResult(ex);
		}

		// update spec name combobox
		List<String> ecSpecNames = null;
		try {
			ecSpecNames = getAleServiceProxy().getECSpecNames(new EmptyParms()).getString();
		} catch (Exception e) {
		}
		if (ecSpecNames != null && m_specNameComboBox != null
				&& m_specNameComboBox.getSelectedObjects() != null
				&& m_specNameComboBox.getSelectedObjects().length > 0) {
			String current = (String) m_specNameComboBox.getSelectedObjects()[0];
			m_specNameComboBox.removeAllItems();
			if (ecSpecNames != null && ecSpecNames.size() > 0) {
				for (String name : ecSpecNames) {
					m_specNameComboBox.addItem(name);
				}
			}
			m_specNameComboBox.setSelectedItem(current);
		}
	}

	/**
	 * creates an event sink from a given url.
	 * @param text
	 */
	private void createEventSink(String eventSinkURL) {
		try {
			EventSink sink = new EventSink(eventSinkURL);
			FosstrakAleClient.instance().addTab(eventSinkURL, sink);
		} catch (Exception e) {
			s_log.error("Could not start requested event sink.");
			e.printStackTrace();
		}
	}

	/**
	 * This method loads the ec specification from a file.
	 * 
	 * @param filename of ec specification file
	 * @return ec specification
	 * @throws Exception if specification could not be loaded
	 */
	private ECSpec getECSpecFromFile(String filename) throws Exception {
		FileInputStream inputStream = new FileInputStream(filename);
		return DeserializerUtil.deserializeECSpec(inputStream);

	}
	
	@Override
	protected void decodeResult(StringBuffer sb, Object result) {
		if (result instanceof ArrayOfString) {
			ArrayOfString resultStringArray = (ArrayOfString) result;
			if (resultStringArray.getString().size() == 0) {
				sb.append(m_guiText.getString("EmptyArray"));
			} else {
				for (String s : resultStringArray.getString()) {
					sb.append(s);
					sb.append("\n");
				}
			}
		} else if (result instanceof ECSpec) {
			CharArrayWriter writer = new CharArrayWriter();
			try {
				SerializerUtil.serializeECSpec((ECSpec) result, writer);
			} catch (Exception e) {
				FosstrakAleClient.instance().showExceptionDialog(m_guiText.getString("SerializationExceptionMessage"));
			}
			sb.append(writer.toString());

		} else if (result instanceof ECReports) {
			CharArrayWriter writer = new CharArrayWriter();
			try {
				SerializerUtil.serializeECReports((ECReports) result, writer);
			} catch (Exception e) {
				FosstrakAleClient.instance().showExceptionDialog(m_guiText.getString("SerializationExceptionMessage"));
			}
			sb.append(writer.toString());
		}
	}

	@Override
	protected String[] getCommands() {

		String[] commands = new String[11];
		for (int i = 1; i < 12; i++) {
			commands[i - 1] = m_guiText.getString("Command" + i);
		}
		return commands;
	}

	/**
	 * @return returns the proxy object already casted.
	 */
	protected ALEServicePortType getAleServiceProxy() throws FosstrakAleClientException {
		return (ALEServicePortType) getProxy();
	}

}