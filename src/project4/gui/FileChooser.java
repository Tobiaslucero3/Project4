package project4.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;

public class FileChooser extends JFrame implements ActionListener
{

	JFileChooser fileChooser;
	File fileChosen;
	public FileChooser()
	{
		//Sets title and size
		super("Open");
		setSize(900, 700);
		
		//Sets default close operation
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Enables visibility
		setVisible(true);
		
		File entryFile = new File("C:\\Users\\18322\\eclipse-workspace\\project4\\data");
		fileChooser = new JFileChooser(entryFile);

		
		FileFilter filter = new FileChooser.Filter();
		fileChooser.setFileFilter(filter);
		
		
		fileChooser.addActionListener(this);
		add(fileChooser);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource().equals(fileChooser.getComponent(0)));
		{
			setVisible(false);
			
		}
	}
	
	public class Filter extends FileFilter
	{

		@Override
		public boolean accept(File f) 
		{
			String name = f.getName();
			return name.substring( name.length() - 3, name.length() ).equals("mdf");

		}

		@Override
		public String getDescription() 
		{
			return "Files that end with .mdf";
		}
		
	}

}
