package project4.gui;

import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import project4.mapdata.Statistics;

public class OutputPanel extends JPanel 
{
	private JTextArea station;
	private JTextArea parameter;
	private JTextArea statistics;
	private JTextArea value;
	private JTextArea reportingStations;
	private JTextArea date;
	
	String format;
	
	public OutputPanel()
	{
		setLayout(null);
		station = new JTextArea();
		station.setEditable(false);
		station.setLocation(0,0);
		station.setSize(60,405);
		station.append("Station\n");
		add(station);
		
		parameter = new JTextArea();
		parameter.setEditable(false);
		parameter.setLocation(60,0);
		parameter.setSize(65,405);
		parameter.append("Parameter\n");
		add(parameter);
		
		statistics = new JTextArea();
		statistics.setEditable(false);
		statistics.setLocation(125,0);
		statistics.setSize(70,405);
		statistics.append("Statistics\n");
		add(statistics);
		
		value = new JTextArea();
		value.setEditable(false);
		value.setLocation(195,0);
		value.setSize(50,405);
		value.append("Value\n");
		add(value);
		
		reportingStations = new JTextArea();
		reportingStations.setEditable(false);
		reportingStations.setLocation(245,0);
		reportingStations.setSize(120,405);
		reportingStations.append("Reporting Stations\n");
		add(reportingStations);
		
		date = new JTextArea();
		date.setEditable(false);
		date.setLocation(365,0);
		date.setSize(370,405);
		date.append("Date\n");
		add(date);
	}
	
	
	public void showStatistics(Statistics statistics)
	{
		station.append(statistics.getStid()+"\n");
		parameter.append(statistics.getParameter()+ "\n");
		this.statistics.append(statistics.getStatType().name()+ "\n");
		value.append(String.format("%.01f\n", statistics.getValue()));
		reportingStations.append(statistics.getNumberOfReportingStations() + "\n");
		date.append(statistics.createStringForDate()+ "\n");
	}
}
