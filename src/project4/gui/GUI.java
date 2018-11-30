package project4.gui;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

public class GUI extends JFrame 
{
	public GUI(String title)
	{
		
		//Sets title and size
		super(title);
		setSize(900, 700);
		
		//Enables visibility and sets default close operation
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		JMenu menu = new Menu();
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(menu);
		setJMenuBar(menuBar);
		
		
	}
}

