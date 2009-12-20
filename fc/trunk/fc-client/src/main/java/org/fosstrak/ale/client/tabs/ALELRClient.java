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
import java.io.CharArrayWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.xml.ws.soap.SOAPFaultException;

import org.fosstrak.ale.client.FosstrakAleClient;
import org.fosstrak.ale.client.exception.FosstrakAleClientException;
import org.fosstrak.ale.client.exception.FosstrakAleClientServiceDownException;
import org.fosstrak.ale.util.DeserializerUtil;
import org.fosstrak.ale.util.SerializerUtil;
import org.fosstrak.ale.wsdl.alelr.epcglobal.ALELRServicePortType;
import org.fosstrak.ale.wsdl.alelr.epcglobal.AddReaders;
import org.fosstrak.ale.wsdl.alelr.epcglobal.ArrayOfString;
import org.fosstrak.ale.wsdl.alelr.epcglobal.Define;
import org.fosstrak.ale.wsdl.alelr.epcglobal.EmptyParms;
import org.fosstrak.ale.wsdl.alelr.epcglobal.GetLRSpec;
import org.fosstrak.ale.wsdl.alelr.epcglobal.GetPropertyValue;
import org.fosstrak.ale.wsdl.alelr.epcglobal.ImmutableReaderExceptionResponse;
import org.fosstrak.ale.wsdl.alelr.epcglobal.ImplementationExceptionResponse;
import org.fosstrak.ale.wsdl.alelr.epcglobal.InUseExceptionResponse;
import org.fosstrak.ale.wsdl.alelr.epcglobal.NoSuchNameExceptionResponse;
import org.fosstrak.ale.wsdl.alelr.epcglobal.RemoveReaders;
import org.fosstrak.ale.wsdl.alelr.epcglobal.SecurityExceptionResponse;
import org.fosstrak.ale.wsdl.alelr.epcglobal.SetProperties;
import org.fosstrak.ale.wsdl.alelr.epcglobal.SetReaders;
import org.fosstrak.ale.wsdl.alelr.epcglobal.Undefine;
import org.fosstrak.ale.wsdl.alelr.epcglobal.Update;
import org.fosstrak.ale.xsd.ale.epcglobal.LRSpec;


/**
 * This class implements a graphical user interface for the application level events client.
 * The client send all commands as SOAP messages to the ale server. The configuration
 * of this class is described in the file ALELRClient.properties. The most important parameter
 * is the parameter endpoint, which specifies the address of the ale webservice which runs
 * on the server. 
 * 
 * @author sawielan
 */
public class ALELRClient extends AbstractTab {

	/** serial version uid */
	private static final long serialVersionUID = 1L;
	
	/**
	 * endpoint parameter for the configuration.
	 */
	public static final String CFG_ENDPOINT = "org.fosstrak.ale.client.alelr.endpoint";
		
	/** 
	 * text field which contains the property value name.
	 */
	private JTextField m_propertyValueField;
	
	/** 
	 * text field which contains the reader name.
	 */
	private JTextField m_readerNameValueField;
	
	private static final int CMD__DEFINE = 1;
	private static final int CMD__UNDEFINE = 2;
	private static final int CMD__UPDATE = 3;
	private static final int CMD__GET_LR_SPEC_NAMES = 4;
	private static final int CMD__GET_LRSPEC = 5;
	private static final int CMD__ADD_READERS = 6;
	private static final int CMD__SET_READERS = 7;
	private static final int CMD__REMOVE_READERS = 8;
	private static final int CMD__SET_PROPERTIES = 9;
	private static final int CMD__GET_PROPERTY_VALUE = 10;
	private static final int CMD__GET_STANDARD_VERSION = 11;
	private static final int CMD__GET_VENDOR_VERSION = 12;
	
	/**
	 * @param parent the parent frame.
	 * @throws NoSuchMethodException given to the fact that we need to pass in a test method via reflection.
	 * @throws SecurityException given to the fact that we need to pass in a test method via reflection.
	 */
	public ALELRClient(JFrame parent) throws SecurityException, NoSuchMethodException {
		super(ALELRServicePortType.class, CFG_ENDPOINT, parent, ALELRServicePortType.class.getMethod("getStandardVersion", EmptyParms.class), new EmptyParms());
	}
	
	@Override
	public String getBaseNameKey() {
		return "org.fosstrak.ale.client.alelr.lang.base";
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
		
		m_commandPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(m_guiText.getString("Command" + command)), BorderFactory.createEmptyBorder(5,5,5,5)));
		m_commandPanel.removeAll();

		switch(command) {
		
			case CMD__GET_LR_SPEC_NAMES: //getLRSpecNames
			case CMD__GET_STANDARD_VERSION: // getStandardVersion
			case CMD__GET_VENDOR_VERSION: // getVendorVersion
				m_commandPanel.setLayout(new GridLayout(1, 1, 5, 0));
				break;
	
			case CMD__UNDEFINE: // undefine
			case CMD__GET_LRSPEC: // getLRSpec
				m_commandPanel.setLayout(new GridLayout(5, 1, 5, 0));
				addLRSpecNamesComboBox(m_commandPanel);
				addSeparator(m_commandPanel);
				break;

			case CMD__GET_PROPERTY_VALUE: // getPropertyValue
				m_commandPanel.setLayout(new GridLayout(7, 1, 5, 0));
				addLRSpecNamesComboBox(m_commandPanel);
				addPropertyValueField(m_commandPanel);
				addSeparator(m_commandPanel);
				break;
				
			case CMD__UPDATE: // update
			case CMD__ADD_READERS: // addReaders
			case CMD__SET_READERS: // setReaders
			case CMD__REMOVE_READERS: // removeReaders
			case CMD__SET_PROPERTIES: // setProperties
				m_commandPanel.setLayout(new GridLayout(8, 1, 5, 0));
				addLRSpecNamesComboBox(m_commandPanel);
				addChooseFileField(m_commandPanel);
				addSeparator(m_commandPanel);
				break;
				
			case CMD__DEFINE: // define
				m_commandPanel.setLayout(new GridLayout(8, 1, 5, 0));
				addReaderNameValueField(m_commandPanel); 
				addChooseFileField(m_commandPanel);
				addSeparator(m_commandPanel);
				break;
		}
		
		addExecuteButton(m_commandPanel);
		validate();
		this.setVisible(true);		
	}
	
	/**
	 * This method adds a specification name combobox to the panel.
	 * 
	 * @param panel to which the specification name combobox should be added
	 */
	private void addLRSpecNamesComboBox(JPanel panel) {
		
		m_specNameComboBox = new JComboBox();
		m_specNameComboBox.setEditable(true);
		m_specNameComboBox.addItem(null);
		
		List<String> lrSpecNames = null;
		try {
			lrSpecNames = getAleLRServiceProxy().getLogicalReaderNames(new EmptyParms()).getString();
		} catch (Exception e) { 
		}
		if (lrSpecNames != null && lrSpecNames.size() > 0) {
			for (String specName : lrSpecNames) {
				m_specNameComboBox.addItem(specName);
			}
		} else {
			m_specNameComboBox.addItem("no specs defined");
		}
		
		panel.add(new JLabel(m_guiText.getString("SpecNameLabel")));
		panel.add(m_specNameComboBox);
		
	}
	
	/**
	 * This method adds a notification property value field to the panel.
	 * 
	 * @param panel to which the property value field should be added
	 */
	private void addPropertyValueField(JPanel panel) {
		
		m_propertyValueField = new JTextField();
		
		panel.add(new JLabel(m_guiText.getString("PropertyNameLabel")));
		panel.add(m_propertyValueField);
	}
	
	/**
	 * This method adds a notification property value field to the panel.
	 * 
	 * @param panel to which the property value field should be added
	 */
	private void addReaderNameValueField(JPanel panel) {
		
		m_readerNameValueField = new JTextField();
		
		panel.add(new JLabel(m_guiText.getString("ReaderNameLabel")));
		panel.add(m_readerNameValueField);
	}
		
	@Override
	protected void executeCommand() {
		
		Object result = null;
		String specName = null;
		String textParameter = null;
		Exception ex = null;
		try {			
			switch(m_commandSelection.getSelectedIndex()) {
			
				case CMD__GET_LR_SPEC_NAMES: //getLogicalReaderNames
					result = getAleLRServiceProxy().getLogicalReaderNames(new EmptyParms());
					break;
					
				case CMD__GET_STANDARD_VERSION: // getStandardVersion
					result = getAleLRServiceProxy().getStandardVersion(new EmptyParms());
					break;
					
				case CMD__GET_VENDOR_VERSION: // getVendorVersion
					result = getAleLRServiceProxy().getVendorVersion(new EmptyParms());
					break;
		
				case CMD__UNDEFINE: // undefine
				case CMD__GET_LRSPEC: // getLRSpec
					// get specName
					specName = (String)m_specNameComboBox.getSelectedItem();
					if (specName == null || "".equals(specName)) {
						FosstrakAleClient.instance().showExceptionDialog(m_guiText.getString("SpecNameNotSpecifiedDialog"));
						break;
					}
					
					switch(m_commandSelection.getSelectedIndex()) {
					
						case CMD__UNDEFINE: // undefine
							Undefine undefineParms = new Undefine();
							undefineParms.setName(specName);
							getAleLRServiceProxy().undefine(undefineParms);
							result = m_guiText.getString("SuccessfullyUndefinedMessage");
							break;
							
						case CMD__GET_LRSPEC: // getLRSpec
							GetLRSpec getLRSpecParms = new GetLRSpec();
							getLRSpecParms.setName(specName);
							result = getAleLRServiceProxy().getLRSpec(getLRSpecParms);
							break;
						
					}
					
					break;
					
				case CMD__GET_PROPERTY_VALUE: // getPropertyValue
					// get specName
					
					// get the l
					textParameter = m_propertyValueField.getText();
					
					specName = (String)m_specNameComboBox.getSelectedItem();
					
					if (specName == null || "".equals(specName)) {
						FosstrakAleClient.instance().showExceptionDialog(m_guiText.getString("SpecNameNotSpecifiedDialog"));
						break;
					}
				
					GetPropertyValue getPropertyValue = new GetPropertyValue();
					getPropertyValue.setName(specName);
					getPropertyValue.setPropertyName(textParameter);
					result = getAleLRServiceProxy().getPropertyValue(getPropertyValue);
					
					break;
					
				case CMD__UPDATE: // update
				case CMD__ADD_READERS: // addReaders
				case CMD__SET_READERS: // setReaders
				case CMD__REMOVE_READERS: // removeReaders
				case CMD__SET_PROPERTIES: // setProperties
				case CMD__DEFINE: // define

					if (m_commandSelection.getSelectedIndex() != CMD__DEFINE) {
						// get specName
						specName = (String)m_specNameComboBox.getSelectedItem();
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
					try {
						switch (m_commandSelection.getSelectedIndex()) {
						case CMD__DEFINE: // define
						case CMD__UPDATE: // update
							// get the LRSpec
							LRSpec spec = getLRSpecFromFile(filePath);
							if (m_commandSelection.getSelectedIndex() == CMD__DEFINE) {
								textParameter = m_readerNameValueField.getText();
								Define define = new Define();
								define.setName(textParameter);
								define.setSpec(spec);
								getAleLRServiceProxy().define(define);
								result = m_guiText.getString("SuccessfullyDefinedMessage");
							} else {
								Update update = new Update();
								update.setName(specName);
								update.setSpec(spec);
								getAleLRServiceProxy().update(update);
								result = m_guiText.getString("SuccessfullyUpdateMessage");
							}
							break;
							
						case CMD__ADD_READERS: // addReaders
							AddReaders addReaders = DeserializerUtil.deserializeAddReaders(filePath);
							addReaders.setName(specName);
							getAleLRServiceProxy().addReaders(addReaders);
							result = m_guiText.getString("SuccessfullyAddReadersMessage");
							break;
						case CMD__SET_READERS: // setReaders
							SetReaders setReaders = DeserializerUtil.deserializeSetReaders(filePath);
							setReaders.setName(specName);
							getAleLRServiceProxy().setReaders(setReaders);
							result = m_guiText.getString("SuccessfullySetReadersMessage");
							break;
						case CMD__REMOVE_READERS: // removeReaders
							RemoveReaders removeReaders = DeserializerUtil.deserializeRemoveReaders(filePath);
							removeReaders.setName(specName);
							getAleLRServiceProxy().removeReaders(removeReaders);
							result = m_guiText.getString("SuccessfullyRemoveReadersMessage");
							break;
						case CMD__SET_PROPERTIES: // setProperties
							SetProperties setProperties = DeserializerUtil.deserializeSetProperties(filePath);
							setProperties.setName(specName);
							getAleLRServiceProxy().setProperties(setProperties);
							result = m_guiText.getString("SuccessfullySetPropertiesMessage");
							break;
							
						}
						
					} catch(FileNotFoundException e) {
						FosstrakAleClient.instance().showExceptionDialog(m_guiText.getString("FileNotFoundDialog"));
						ex = e;
						break;
					} 
					break;
			}
		} catch (Exception e) {
			String reason = e.getMessage();
			String text = "Unknown Error";
			if (e instanceof ImplementationExceptionResponse) {
				text = m_guiText.getString("ImplementationExceptionDialog");
			} else if (e instanceof SecurityExceptionResponse) {
				text = m_guiText.getString("SecurityExceptionDialog");
			} else if (e instanceof InUseExceptionResponse) {
				text = m_guiText.getString("InUseExceptionDialog");
			} else if (e instanceof NoSuchNameExceptionResponse) {
				text = m_guiText.getString("NoSuchNameExceptionDialog");
			} else if (e instanceof ImmutableReaderExceptionResponse) {
				text = m_guiText.getString("ImmutableReaderExceptionDialog");
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
		List<String> logicalReaderNames = null;
		try {
			logicalReaderNames = getAleLRServiceProxy().getLogicalReaderNames(new EmptyParms()).getString();
		} catch (Exception e) {}
		if (logicalReaderNames != null && m_specNameComboBox != null && m_specNameComboBox.getSelectedObjects() != null && m_specNameComboBox.getSelectedObjects().length > 0) {
			String current = (String)m_specNameComboBox.getSelectedObjects()[0];
			m_specNameComboBox.removeAllItems();
			if (logicalReaderNames != null && logicalReaderNames.size() > 0) {
				for (String name : logicalReaderNames) {
					m_specNameComboBox.addItem(name);
				}
			}
			m_specNameComboBox.setSelectedItem(current);
		}
	}
	
	/**
	 * This method loads the ec specification from a file.
	 * 
	 * @param filename of ec specification file
	 * @return ec specification
	 * @throws Exception if specification could not be loaded
	 */
	private LRSpec getLRSpecFromFile(String filename) throws Exception {
		FileInputStream inputStream = new FileInputStream(filename);
		return DeserializerUtil.deserializeLRSpec(inputStream);
		
	}

	@Override
	protected void decodeResult(StringBuffer sb, Object result) {
		if (result instanceof ArrayOfString) {
			ArrayOfString resultStringArray = (ArrayOfString)result;
			if (resultStringArray.getString().size() == 0) {
				sb.append(m_guiText.getString("EmptyArray"));
			} else {
				for (String s : resultStringArray.getString()) {
					sb.append(s);
					sb.append("\n");
				}
			}
		} else if (result instanceof LRSpec) {
			CharArrayWriter writer = new CharArrayWriter();
			try {
				SerializerUtil.serializeLRSpec((LRSpec)result, writer);
			} catch (IOException e) {
				FosstrakAleClient.instance().showExceptionDialog(m_guiText.getString("SerializationExceptionMessage"));
			}
			sb.append(writer.toString());
		}
	}

	@Override
	protected String[] getCommands() {
		
		String[] commands = new String[12];
		for (int i = 1; i < 13; i++) {
			commands[i - 1] = m_guiText.getString("Command" + i);			
		}
		return commands;	
	}
	
	/**
	 * @return returns the proxy object already casted.
	 */
	protected ALELRServicePortType getAleLRServiceProxy() throws FosstrakAleClientException {
		return (ALELRServicePortType) getProxy();
	}

}