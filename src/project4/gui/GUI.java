package project4.gui;

import javax.swing.JFrame;
import javax.swing.JMenuBar;

public class GUI extends JFrame 
{
	public GUI(String title)
	{
		super(title);
		setSize(900, 700);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
}

