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
		add(parameterPanel);
	  }
		
	public static void main(String args[])
	{
		GUI gui = new GUI();
		gui.setVisible(true);
		gui.setResizable(false);
	}
}

