package project4.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import project4.mapdata.MapData;
import project4.mapdata.Statistics;

public class GUI extends JFrame implements ActionListener
{
	  JButton button;
	
	  JLabel label;
	
	  ParameterPanel parameterPanel;
	  OutputPanel outputPanel;
	  StatisticsPanel statisticsPanel;
	
	  JMenuBar menuBar;
	  Menu menu;
	
	  JLabel errorMessage;
	
	  GUI()
	  {
	
		//Sets title and size
		super("Mesonet");
		setSize(900, 550);
		
		//Sets default close operation
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Creating a menu
		menu = new Menu();
		menuBar = new JMenuBar();
		menuBar.add(menu);
		setJMenuBar(menuBar);
		
		//Setting the layout to be a gridbag layout
		setLayout(null);
		
		//Creating the JLabel
		label = new JLabel("Mesonet - We don't set records, we record them!");
		label.setLocation(291,6);
		label.setSize(311,19);
		add(label);
			
		//Creating the parameter panel
		parameterPanel = new ParameterPanel();
		parameterPanel.setSize(77,403);
		parameterPanel.setLocation(2,27);
		add(parameterPanel);		//Creating the statistics panel
		statisticsPanel = new StatisticsPanel();
		statisticsPanel.setSize(105,402);
		statisticsPanel.setLocation(80,27);
		add(statisticsPanel);
		
		//Creating the output panel
		outputPanel = new OutputPanel();
		outputPanel.setLocation(190,28);
		outputPanel.setSize(709,395);
		add(outputPanel);
	
		//Creating the calculate button
		button = new JButton("Calculate");
		button.setActionCommand("calculate");
		button.addActionListener(this);
		button.setLocation(362,442);
		button.setSize(90,20);
		add(button);
		
		//Creating the exit button
		button = new JButton("Exit");
		button.setActionCommand("exit");
		button.addActionListener(this);
		button.setSize(62, 20);
		button.setLocation(471,442);
		add(button);
	}
	
	

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		
		if(e.getActionCommand().equals("calculate"))
		{
			try 
			{
				for(String x: parameterPanel.getSelected())
				{
					if(!x.equals(""))
					{
						outputPanel.showStatistics(menu.getMapData().getStatistics(statisticsPanel.getStatType(), x));
					}
					
				}
			}
			catch(NullPointerException exception)
			{
				JOptionPane.showMessageDialog(null, "Please select a file first!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		if(e.getActionCommand().equals("exit"))
		{
			setVisible(false);
		}
		
	}
	
		
	public static void main(String args[])
	{
		GUI gui = new GUI();
		gui.setVisible(true);
		gui.setResizable(false);
	}
}

