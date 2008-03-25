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

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FileDialog;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.CharArrayWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.PropertyResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.xml.namespace.QName;


import org.accada.ale.util.DeserializerUtil;
import org.accada.ale.util.SerializerUtil;
import org.accada.ale.wsdl.ale.epcglobal.ALEServicePortType;
import org.accada.ale.wsdl.ale.epcglobal.Define;
import org.accada.ale.wsdl.ale.epcglobal.DuplicateNameExceptionResponse;
import org.accada.ale.wsdl.ale.epcglobal.DuplicateSubscriptionExceptionResponse;
import org.accada.ale.wsdl.ale.epcglobal.ECSpecValidationExceptionResponse;
import org.accada.ale.wsdl.ale.epcglobal.EmptyParms;
import org.accada.ale.wsdl.ale.epcglobal.GetECSpec;
import org.accada.ale.wsdl.ale.epcglobal.GetSubscribers;
import org.accada.ale.wsdl.ale.epcglobal.Immediate;
import org.accada.ale.wsdl.ale.epcglobal.ImplementationExceptionResponse;
import org.accada.ale.wsdl.ale.epcglobal.InvalidURIExceptionResponse;
import org.accada.ale.wsdl.ale.epcglobal.NoSuchNameExceptionResponse;
import org.accada.ale.wsdl.ale.epcglobal.NoSuchSubscriberExceptionResponse;
import org.accada.ale.wsdl.ale.epcglobal.Poll;
import org.accada.ale.wsdl.ale.epcglobal.SecurityExceptionResponse;
import org.accada.ale.wsdl.ale.epcglobal.Subscribe;
import org.accada.ale.wsdl.ale.epcglobal.Undefine;
import org.accada.ale.wsdl.ale.epcglobal.Unsubscribe;
import org.accada.ale.wsdl.ale.epcglobal.ArrayOfString;
import org.accada.ale.xsd.ale.epcglobal.ECReports;
import org.accada.ale.xsd.ale.epcglobal.ECSpec;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.log4j.PropertyConfigurator;


/**
 * This class implements a graphical user interface for the application level events client.
 * The client send all commands as SOAP messages to the ale server. The configuration
 * of this class is described in the file ALEClient.properties. The most important parameter
 * is the parameter endpoint, which specifies the address of the ale webservice which runs
 * on the server. 
 * 
 * @author regli
 * @author sawielan
 */
public class ALEClient extends JFrame {

	/** serial version uid */
	private static final long serialVersionUID = 1L;
	/** system default language */
	private static final Locale SYSTEM_DEFAULT_LOCALE = Locale.getDefault();
	/** default language (if language is not defined in property file and system default language does not exists) */
	private static final Locale DEFAULT_LOCALE = Locale.ENGLISH;
	
	/** properties file path */
	private static final String PROPERTIES_FILE_LOCATION = "/props/ALEClient.properties";
	/** properties */
	private final Properties props = new Properties();
	/** resource bundle containing all user visible texts in specified language */
	private PropertyResourceBundle guiText;

	/** ale proxy */
	private ALEServicePortType aleProxy = null;
	
	/** command panel */
	private final JPanel commandPanel = new JPanel();
	/** panel which contains the command panel */
	private final JPanel commandSuperPanel = new JPanel();
	/** text area which contains the results */
	private final JTextArea resultTextArea = new JTextArea(5, 30);
	/** scroll pane which contains the result text area */
	private final JScrollPane resultScrollPane = new JScrollPane(resultTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	/** combobox which contains all possible commands */
	private final JComboBox commandSelection = new JComboBox();
	/** panel which contains the execute button */
	private JPanel execButtonPanel = null;
	/** combobox which contains all defined specification names */
	private JComboBox specNameComboBox;
	/** text field which contains the notification uri */
	private JTextField notificationUriField;
	/** text field which contains the file path */
	private JTextField filePathField;
	
	private static final QName SERVICE_NAME = new QName("urn:epcglobal:ale:wsdl:1", "ALEService");
	
	/**
	 * This method initializes the class, by loading properties, texts and setting the endpoint.
	 * 
	 * @throws IOException
	 */
	public void initialize() throws IOException {

		// load properties
		InputStream inputStream = this.getClass().getResourceAsStream(PROPERTIES_FILE_LOCATION);
		if (inputStream == null) {
			throw new IllegalArgumentException("Could not load properties from classpath (" + PROPERTIES_FILE_LOCATION + ")");
		}
		props.load(inputStream);
		
		// load text
		InputStream languageStream = null;
		
		// try to get language form property file
		if (props.containsKey("Language")) {
			languageStream = this.getClass().getResourceAsStream("/props/ALEClient_" + props.getProperty("Language") + ".lang");
		}
		
		// try system default language
		if (languageStream == null) {
			languageStream = this.getClass().getResourceAsStream("/props/ALEClient_" + SYSTEM_DEFAULT_LOCALE.getLanguage() + ".lang");
		}
		
		// try default language
		if (languageStream == null) {
			languageStream = this.getClass().getResourceAsStream("/props/ALEClient_" + DEFAULT_LOCALE.getLanguage() + ".lang");
		}
		
		if (languageStream == null) {
			throw new IllegalArgumentException("Could not load language package from classpath (" + DEFAULT_LOCALE + ")");
		}
		guiText = new PropertyResourceBundle(languageStream);

		//--------------------
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setServiceClass(ALEServicePortType.class);
        String address = props.getProperty("EndPoint");
        if (address == null) {
        	 throw new IOException("no endpoint defined!");
        }
        factory.setAddress(address);
        aleProxy = (ALEServicePortType) factory.create();
        
		initializeGUI();
		
	}

	/**
	 * This method initializes the gui and set it visible.
	 */
	private void initializeGUI() {
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(getProperty("WindowWidth"), getProperty("WindowHeight"));
		this.setTitle(guiText.getString("ApplicationTitle"));
		this.setJMenuBar(createMenuBar());
		
		this.setLayout(new BorderLayout());
		this.add(createCommandSelectionPanel(), BorderLayout.NORTH);
		this.add(commandSuperPanel, BorderLayout.CENTER);
		this.add(resultScrollPane, BorderLayout.SOUTH);
		
		commandSuperPanel.setLayout(new BorderLayout());
		commandSuperPanel.add(commandPanel, BorderLayout.NORTH);
		
		resultScrollPane.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(guiText.getString("ResultPanelTitle")), BorderFactory.createEmptyBorder(5,5,5,5)));
		
		resultTextArea.setEditable(false);
		
		this.setVisible(true);
		
	}

	/**
	 * This method creates the command selection panel.
	 * 
	 * @return command selection panel
	 */
	private JPanel createCommandSelectionPanel() {
		
		JPanel selectionPanel = new JPanel();
		selectionPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(guiText.getString("SelectionPanelTitle")), BorderFactory.createEmptyBorder(5,5,5,5)));
		
		commandSelection.setMaximumRowCount(12);
		
		commandSelection.addItem(null);
		for (String item : getCommands()) {
			commandSelection.addItem(item);
		}
		
		commandSelection.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if ("comboBoxChanged".equals(e.getActionCommand())) {
					setCommandPanel(commandSelection.getSelectedIndex());
				}				
			}
		});
		
		selectionPanel.add(commandSelection);
		
		return selectionPanel;
		
	}

	/**
	 * This method sets the command selection panel depending on the command id.
	 * 
	 * @param command id
	 */
	private void setCommandPanel(int command) {
		
		if (command == -1) {
			commandPanel.removeAll();
			commandPanel.setBorder(null);
			this.setVisible(false);
			this.setVisible(true);
			return;
		}
		
		commandPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(guiText.getString("Command" + command)), BorderFactory.createEmptyBorder(5,5,5,5)));
		commandPanel.removeAll();

		switch(command) {
		
			case 4: //getECSpecNames
			case 10: // getStandardVersion
			case 11: // getVendorVersion
				commandPanel.setLayout(new GridLayout(1, 1, 5, 0));
				break;
				
			case 2: // undefine
			case 3: // getECSpec
			case 7: // poll
			case 9: // getSubscribers
				commandPanel.setLayout(new GridLayout(5, 1, 5, 0));
				addECSpecNameComboBox(commandPanel);
				addSeparator(commandPanel);
				break;
				
			case 5: // subscribe
			case 6: // unsubscribe
				commandPanel.setLayout(new GridLayout(7, 1, 5, 0));
				addECSpecNameComboBox(commandPanel);
				addNotificationURIField(commandPanel);
				addSeparator(commandPanel);
				break;
				
			case 8: // immediate
				commandPanel.setLayout(new GridLayout(6, 1, 5, 0));
				addChooseFileField(commandPanel);
				addSeparator(commandPanel);
				break;
				
			case 1: // define
				commandPanel.setLayout(new GridLayout(8, 1, 5, 0));
				addECSpecNameComboBox(commandPanel);
				addChooseFileField(commandPanel);
				addSeparator(commandPanel);
				break;
				
		}
		
		addExecuteButton(commandPanel);
		
		this.setVisible(false);
		this.setVisible(true);
		
	}
	
	/**
	 * This method adds a separator to the panel.
	 * 
	 * @param panel to which the separator should be added
	 */
	private void addSeparator(JPanel panel) {
	
		panel.add(new JPanel());
		panel.add(new JSeparator());
		
	}
	
	/**
	 * This method adds a specification name combobox to the panel.
	 * 
	 * @param panel to which the specification name combobox should be added
	 */
	private void addECSpecNameComboBox(JPanel panel) {
		
		specNameComboBox = new JComboBox();
		specNameComboBox.setEditable(true);
		specNameComboBox.addItem(null);
		
		List<String> ecSpecNames;
		try {
			ecSpecNames = aleProxy.getECSpecNames(new EmptyParms()).getString();
			if (ecSpecNames != null && ecSpecNames.size() > 0) {
				for (String specName : ecSpecNames) {
					specNameComboBox.addItem(specName);
				}
			} else {
				specNameComboBox.addItem("no specs defined");
			}
		} catch (Exception e) { 
			e.printStackTrace();
		}
		
		panel.add(new JLabel(guiText.getString("SpecNameLabel")));
		panel.add(specNameComboBox);
		
	}
	
	/**
	 * This method adds a notification uri field to the panel.
	 * 
	 * @param panel to which the norification uri field should be added
	 */
	private void addNotificationURIField(JPanel panel) {
		
		notificationUriField = new JTextField();
		
		panel.add(new JLabel(guiText.getString("NotificationURILabel")));
		panel.add(notificationUriField);
		
	}
	
	/**
	 * This method adds a choose file field to the panel.
	 * 
	 * @param panel to which the choose file field should be added
	 */
	private void addChooseFileField(JPanel panel) {
	
		filePathField = new JTextField();
		
		final FileDialog fileDialog = new FileDialog(this);
		fileDialog.setModal(true);
		fileDialog.addComponentListener(new ComponentAdapter() {
			public void componentHidden(ComponentEvent e) {
				if (fileDialog.getFile() != null) {
					filePathField.setText(fileDialog.getDirectory() + fileDialog.getFile());
				}
			}
		});
		
		final JButton chooseFileButton = new JButton(guiText.getString("ChooseFileButtonLabel"));
		chooseFileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fileDialog.setVisible(true);
			}
		});

		JPanel chooseFileButtonPanel = new JPanel();
		chooseFileButtonPanel.setLayout(new GridLayout(1, 3));
		chooseFileButtonPanel.add(new JPanel());
		chooseFileButtonPanel.add(chooseFileButton);
		chooseFileButtonPanel.add(new JPanel());
		
		panel.add(new JLabel(guiText.getString("FilePathLabel")));
		panel.add(filePathField);
		panel.add(chooseFileButtonPanel);
				
	}
	
	/**
	 * This method adds a execute button to the panel.
	 * 
	 * @param panel to which the execute button should be added
	 */
	private void addExecuteButton(JPanel panel) {
		
		if (execButtonPanel == null) {
			
			JButton execButton = new JButton(guiText.getString("ExecuteButtonLabel"));
			execButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					executeCommand();
				}
			});
			
			execButtonPanel = new JPanel();
			execButtonPanel.setLayout(new GridLayout(1, 3));
			execButtonPanel.add(new JPanel());
			execButtonPanel.add(execButton);
			execButtonPanel.add(new JPanel());
			
		}
		
		panel.add(execButtonPanel);
		
	}

	/**
	 * This method executes the command which is selected in the command selection combobox
	 * with the parameters which are set in the corresponding fields.
	 * To execute the commands, the methods of the ale proxy will be invoked. 
	 */
	private void executeCommand() {
		
		Object result = null;
		String specName = null;
		String notificationURI = null;
		
		try {
			
			switch(commandSelection.getSelectedIndex()) {
			
				case 4: //getECSpecNames
					result = aleProxy.getECSpecNames(new EmptyParms());
					break;
					
				case 10: // getStandardVersion
					result = aleProxy.getStandardVersion(new EmptyParms());
					break;
					
				case 11: // getVendorVersion
					result = aleProxy.getVendorVersion(new EmptyParms());
					break;
			
				case 2: // undefine
				case 3: // getECSpec
				case 7: // poll
				case 9: // getSubscribers
					// get specName
					specName = (String)specNameComboBox.getSelectedItem();
					if (specName == null || "".equals(specName)) {
						showExcpetionDialog(guiText.getString("SpecNameNotSpecifiedDialog"));
						break;
					}
					
					switch(commandSelection.getSelectedIndex()) {
					
					case 2: // undefine
						Undefine undefineParms = new Undefine();
						undefineParms.setSpecName(specName);
						aleProxy.undefine(undefineParms);
						result = guiText.getString("SuccessfullyUndefinedMessage");
						break;
						
					case 3: // getECSpec
						GetECSpec getECSpecParms = new GetECSpec();
						getECSpecParms.setSpecName(specName);
						result = aleProxy.getECSpec(getECSpecParms);
						break;
						
					case 7: // poll
						Poll pollParms = new Poll();
						pollParms.setSpecName(specName);
						result = aleProxy.poll(pollParms);
						break;
						
					case 9: // getSubscribers
						GetSubscribers getSubscribersParms = new GetSubscribers();
						getSubscribersParms.setSpecName(specName);
						result = aleProxy.getSubscribers(getSubscribersParms);
						break;
						
					}
					
					break;
					
				case 5: // subscribe
				case 6: // unsubscribe
					// get specName
					specName = (String)specNameComboBox.getSelectedItem();
					if (specName == null || "".equals(specName)) {
						showExcpetionDialog(guiText.getString("SpecNameNotSpecifiedDialog"));
						break;
					}
					
					// get notificationURI
					notificationURI = notificationUriField.getText();
					if (notificationURI == null || "".equals(notificationURI)) {
						showExcpetionDialog(guiText.getString("NotificationUriNotSpecifiedDialog"));
						break;
					}
					
					switch (commandSelection.getSelectedIndex()) {
					
					case 5:
						Subscribe subscribeParms = new Subscribe();
						subscribeParms.setSpecName(specName);
						subscribeParms.setNotificationURI(notificationURI);
						aleProxy.subscribe(subscribeParms);
						result = guiText.getString("SuccessfullySubscribedMessage");
						break;
						
					case 6:
						Unsubscribe unsubscribeParms = new Unsubscribe();
						unsubscribeParms.setSpecName(specName);
						unsubscribeParms.setNotificationURI(notificationURI);
						aleProxy.unsubscribe(unsubscribeParms);
						result = guiText.getString("SuccessfullyUnsubscribedMessage");
						break;
						
					}
					
					break;
					
				case 1: // define
				case 8: // immediate

					if (commandSelection.getSelectedIndex() == 1) {
						// get specName
						specName = (String)specNameComboBox.getSelectedItem();
						if (specName == null || "".equals(specName)) {
							showExcpetionDialog(guiText.getString("SpecNameNotSpecifiedDialog"));
							break;
						}
					}
					
					// get filePath
					String filePath = filePathField.getText();
					if (filePath == null || "".equals(filePath)) {
						showExcpetionDialog(guiText.getString("FilePathNotSpecifiedDialog"));
						break;
					}
					
					// get ecSpec
					ECSpec ecSpec;
					try {
						ecSpec = getECSpecFromFile(filePath);
					} catch(FileNotFoundException e) {
						showExcpetionDialog(guiText.getString("FileNotFoundDialog"));
						break;
					} catch(Exception e) {
						showExcpetionDialog(guiText.getString("UnexpectedFileFormatDialog"));
						break;
					}
					
					if (commandSelection.getSelectedIndex() == 1) {
						Define defineParms = new Define();
						defineParms.setSpecName(specName);
						defineParms.setSpec(ecSpec);
						aleProxy.define(defineParms);
						result = guiText.getString("SuccessfullyDefinedMessage");
					} else {
						Immediate immediateParms = new Immediate();
						immediateParms.setSpec(ecSpec);
						result = aleProxy.immediate(immediateParms);
					}
					break;
				
			}
			
		} catch (Exception e) {
			String reason = e.getMessage();
			if (e instanceof DuplicateNameExceptionResponse) {
				showExcpetionDialog(guiText.getString("DuplicateNameExceptionDialog"), reason);
			} else if (e instanceof DuplicateSubscriptionExceptionResponse) {
				showExcpetionDialog(guiText.getString("DuplicateSubscriptionExceptionDialog"), reason);
			} else if (e instanceof ECSpecValidationExceptionResponse) {
				showExcpetionDialog(guiText.getString("ECSpecValidationExceptionDialog"), reason);
			} else if (e instanceof ImplementationExceptionResponse) {
				showExcpetionDialog(guiText.getString("ImplementationExceptionDialog"), reason);
			} else if (e instanceof InvalidURIExceptionResponse) {
				showExcpetionDialog(guiText.getString("InvalidURIExceptionDialog"), reason);
			} else if (e instanceof NoSuchNameExceptionResponse) {
				showExcpetionDialog(guiText.getString("NoSuchNameExceptionDialog"), reason);
			} else if (e instanceof NoSuchSubscriberExceptionResponse) {
				showExcpetionDialog(guiText.getString("NoSuchSubscriberExceptionDialog"), reason);
			} else if (e instanceof SecurityExceptionResponse) {
				showExcpetionDialog(guiText.getString("SecurityExceptionDialog"), reason);
			}
		}
		
		showResult(result);
		
		// update spec name combobox
		List<String> ecSpecNames = null;
		try {
			ecSpecNames = aleProxy.getECSpecNames(new EmptyParms()).getString();
		} catch (Exception e) {}
		if (ecSpecNames != null && specNameComboBox != null && specNameComboBox.getSelectedObjects() != null && specNameComboBox.getSelectedObjects().length > 0) {
			String current = (String)specNameComboBox.getSelectedObjects()[0];
			specNameComboBox.removeAllItems();
			if (ecSpecNames != null && ecSpecNames.size() > 0) {
				for (String name : ecSpecNames) {
					specNameComboBox.addItem(name);
				}
			}
			specNameComboBox.setSelectedItem(current);
		}

	}
	
	/**
	 * This method creates a exception dialog and sets it visible.
	 * 
	 * @param message to display
	 * @param reason to display
	 */
	private void showExcpetionDialog(String message, String reason) {
		
		Rectangle guiBounds = this.getBounds();
		int width = getProperty("DialogWidth");
		int height = getProperty("DialogHeight");
		int xPos = guiBounds.x + (guiBounds.width - width) / 2;
		int yPos = guiBounds.y + (guiBounds.height - height) / 2;
		
		final JDialog dialog = new JDialog(this, true);
		dialog.setBounds(xPos, yPos, width, height);
		dialog.setLayout(new BorderLayout(10, 10));
		dialog.setTitle(guiText.getString("ExceptionDialogTitle"));
		
		// message label
		JLabel messageLabel = new JLabel(message);
		JPanel messageLabelPanel = new JPanel();
		messageLabelPanel.add(messageLabel);
		
		// reason panel
		JTextArea reasonTextArea = new JTextArea(reason);
		JScrollPane reasonScrollPane = new JScrollPane(reasonTextArea);
		reasonScrollPane.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(guiText.getString("DetailsPanelTitle")), BorderFactory.createEmptyBorder(5,5,5,5)));
		reasonTextArea.setEditable(false);

		// ok button
		JButton okButton = new JButton(guiText.getString("OkButtonLabel"));
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
	private void showExcpetionDialog(String text) {
		
		showExcpetionDialog(text, null);
		
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

	/**
	 * This method displays the result in the result text area.
	 * 
	 * @param result to display
	 */
	private void showResult(Object result) {
		
		resultTextArea.setText(null);
		if (result instanceof String) {
			resultTextArea.append((String)result);
		} else if (result instanceof ArrayOfString) {
			ArrayOfString resultStringArray = (ArrayOfString)result;
			if (resultStringArray.getString().size() == 0) {
				resultTextArea.append(guiText.getString("EmptyArray"));
			} else {
				for (String s : resultStringArray.getString()) {
					resultTextArea.append(s);
					resultTextArea.append("\n");
				}
			}
		} else if (result instanceof ECSpec) {
			CharArrayWriter writer = new CharArrayWriter();
			try {
				SerializerUtil.serializeECSpec((ECSpec)result, writer);
			} catch (IOException e) {
				showExcpetionDialog(guiText.getString("SerializationExceptionMessage"));
			}
			resultTextArea.append(writer.toString());

		} else if (result instanceof ECReports) {
			CharArrayWriter writer = new CharArrayWriter();
			try {
				SerializerUtil.serializeECReports((ECReports)result, writer);
			} catch (IOException e) {
				showExcpetionDialog(guiText.getString("SerializationExceptionMessage"));
			}
			resultTextArea.append(writer.toString());
		}
	}

	/**
	 * This method returns the names of all commands.
	 * 
	 * @return command names
	 */
	private String[] getCommands() {
		
		String[] commands = new String[11];
		for (int i = 1; i < 12; i++) {
			commands[i - 1] = guiText.getString("Command" + i);			
		}
		return commands;
		
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
		
		JMenu fileMenuItem = new JMenu(guiText.getString("FileMenu"));
		
		JMenuItem exitMenuItem = new JMenuItem(guiText.getString("QuitMenuItem"));
		exitMenuItem.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				System.exit(0);
			}
		});
		fileMenuItem.add(exitMenuItem);
		
		return fileMenuItem;
		
	}

	/**
	 * This method gets a property value as integer.
	 * 
	 * @param key of the property
	 * @return property as integer value
	 */
	private int getProperty(String key) {
		
		String error;
		String value = props.getProperty(key);
		if(value == null) {
			error =  "Value '" + key + "' not found in properties !";
		} else {
			try {
				return Integer.parseInt(value.trim());
			} catch (Exception e) {
				error = "Value '" + key + "' is not an integer !";
			}
		}
		throw new NumberFormatException(error);
		
	}
	
	/**
	 * This method starts the ale client.
	 * 
	 * @param args no args
	 * @throws IOException if the client could not be initialized properly
	 */
	public static void main(String[] args) throws IOException {
		
		// configure Logger with properties file
		PropertyConfigurator.configure(ALEClient.class.getResource("/props/log4j.properties"));
		PropertyConfigurator.configure(ALELRClient.class.getResource("/props/log4j.properties"));
		
		ALEClient client = new ALEClient();
		client.initialize();
		
		ALELRClient lrclient = new ALELRClient();
		lrclient.initialize();
		
	}

}