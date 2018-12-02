package project4.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import project4.mapdata.MapData;

public class Menu extends JMenu implements ActionListener
{
	protected static File file;
	FileChooserFrame fileChooser;
	
	public Menu()
	{
		//Sets the title to File
		super("File");
		
		//Instantiate a menu item
		JMenuItem menuItem;

		//Instantiates the menu item and sets the text to the appropriate text
		//along with setting an action command and adding the menuItem to the
		//menu
		menuItem = new JMenuItem("Open Data File");
		menuItem.setActionCommand("file");
		menuItem.addActionListener(this);
		add(menuItem);
		
		menuItem = new JMenuItem("Exit");
		menuItem.setActionCommand("exit");
		menuItem.addActionListener(this);
		add(menuItem);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		//Starts a new file chooser frame and gets the file chosen
		if(e.getActionCommand().equals("file"))
		{
			fileChooser = new FileChooserFrame();
			fileChooser.setVisible(true);
			file = fileChooser.getFile();
		}
	}
	
	public MapData getMapData()
	{
		return fileChooser.getMapData();
	}
	



}
