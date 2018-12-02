package project4.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;

import project4.mapdata.MapData;

public class FileChooserFrame extends JFrame implements ActionListener
{

	JFileChooser fileChooser;
	File fileChosen;
	MapData mapData;
	public FileChooserFrame()
	{
		//Sets title and size
		super("Open");
		setSize(500, 400);
		
		//Sets default close operation
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		File entryFile = new File("C:\\Users\\18322\\eclipse-workspace\\project4\\data");
		fileChooser = new JFileChooser(entryFile);

		
		FileFilter filter = new FileChooserFrame.Filter();
		fileChooser.setFileFilter(filter);
		
		
		fileChooser.addActionListener(this);
		add(fileChooser);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
			setVisible(false);
			fileChosen = fileChooser.getSelectedFile();
			String name = fileChosen.getName();
			mapData = new MapData(Integer.parseInt(name.substring(0, 4)), Integer.parseInt(name.substring(4, 6)), 
					Integer.parseInt(name.substring(6, 8)), Integer.parseInt(name.substring(8, 10)), Integer.parseInt(name.substring(10, 12)), "data");
	}
	
	public File getFile()
	{
		return fileChosen;
	}
	
	public class Filter extends FileFilter
	{

		@Override
		public boolean accept(File f) 
		{
			String name = f.getName();
			return name.substring( name.length() - 3).equals("mdf");

		}

		@Override
		public String getDescription() 
		{
			return "Files that end with .mdf";
		}
		
	}
	
	public MapData getMapData()
	{
		mapData.parseFile();
		return mapData;
	}

}
