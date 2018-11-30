package project4.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class Menu extends JMenu implements ActionListener
{

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
		if(e.getActionCommand().equals("file"))
		{
			JFrame fileChooser = new FileChooser();
		}
	}

}
