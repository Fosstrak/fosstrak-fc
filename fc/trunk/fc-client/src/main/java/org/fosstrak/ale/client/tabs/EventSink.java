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
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import org.fosstrak.ale.client.FosstrakAleClient;
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
	
	private long m_index = 0;
	
	private final FosstrackListModel m_listModel = new FosstrackListModel();
	
	private final JList m_list = new JList(m_listModel);
	
	private final JCheckBox m_update = new JCheckBox("Refresh", true);
	private final JCheckBox m_accept = new JCheckBox("Accept Reports", true);
	
	private final Map<String, ECReports> ecReports = new HashMap<String, ECReports> ();
	
	/** 
	 * text area where the ec reports will be displayed 
	 */
	private final JTextArea m_ecReportArea = new JTextArea();
	
	/** 
	 * scroll pane where the ecReportArea is added 
	 */
	private final JScrollPane m_scrollPaneEcReport = new JScrollPane(m_ecReportArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	
	private final JScrollPane m_scrollPaneReportsList = new JScrollPane(m_list, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	
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
		
		Font font = FosstrakAleClient.instance().getConfiguration().getFont();
		try {
			URL url = new URL(eventSinkURL);
			ReportHandler reportHandler = new ReportHandler(url.getPort());
			reportHandler.addListener(this);
						
			//setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
			setLayout(new GridLayout());
			
			JLayeredPane contentPane = new JLayeredPane();

			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			add(contentPane);
			contentPane.setLayout(new BorderLayout(0, 0));
			
			m_ecReportArea.setText("enemene muuu");
			m_ecReportArea.setFont(font);
			contentPane.add(m_scrollPaneEcReport, BorderLayout.CENTER);

			m_list.setPreferredSize(new Dimension(200, 400));
			m_list.setFont(font);
			contentPane.add(m_scrollPaneReportsList, BorderLayout.WEST);
			m_list.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseClicked(MouseEvent e) {
					Object o = m_list.getSelectedValue();
					if (o instanceof String)
					{
						CharArrayWriter writer = new CharArrayWriter();
						String str = "";
						try {
							SerializerUtil.serializeECReportsPretty(ecReports.get((String) o), writer);
							str = writer.toString();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						m_ecReportArea.setText(str);
					}
				}
			});

			contentPane.add(getControlPanel(font), BorderLayout.NORTH);
			
			this.setVisible(true);
						
		} catch (Exception e) {
			throw new FosstrakAleClientException(e);
		}
		
	}
	
	private JPanel getControlPanel(Font font) 
	{
		JPanel control = new JPanel();
		control.setLayout(new GridLayout(1, 2));
		
		JPanel panel = new JPanel();
		control.add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 5));

		m_accept.setFont(font);
		m_update.setFont(font);
		panel.add(m_accept);
		panel.add(m_update);
		
		JPanel panel2 = new JPanel();
		control.add(panel2);
		panel2.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 5));
		JButton clear = new JButton("Clear");
		clear.setFont(font);
		panel2.add(clear);
		clear.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				m_ecReportArea.setText("");
				m_listModel.clearAll();
			}
		});
		JButton closeSink = new JButton("Close Sink");
		closeSink.setFont(font);
		closeSink.setEnabled(false);
		panel2.add(closeSink);
		
		return control;
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
			if (m_accept.isSelected())
			{
				String key = (m_index++) + "-" + reports.getALEID() + "-" + reports.getSpecName();
				ecReports.put(key, reports);
				m_listModel.addElement(key);
				if (m_update.isSelected())
				{
					m_ecReportArea.setText(writer.toString());
					m_list.setSelectedIndex(0);
				}
				else
				{
					// fix for multiple elements autoselected if 0-th element clicked...
					if (m_list.getSelectedIndices().length > 1)
					{
						m_list.setSelectedIndex(1);
					}
				}
				
			}
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
	
	private static final class FosstrackListModel extends AbstractListModel
	{
		
		private LinkedList<String> m_objects = new LinkedList<String> ();

		@Override
		public Object getElementAt(int index) 
		{
			try
			{
				return m_objects.get(index);
			}
			catch (Exception e)
			{
				return null;
			}
		}

		public void clearAll() {
			int index = getSize() - 1;
			m_objects.clear();
			fireContentsChanged(this, 0, index);
		}

		public void addElement(String key) 
		{
			int index = 0;
			m_objects.push(key);
			fireIntervalAdded(this, index, index);
		}

		@Override
		public int getSize() 
		{
			return m_objects.size(); 
		}
		
	}
}