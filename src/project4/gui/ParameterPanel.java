package project4.gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.TreeSet;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import jdk.nashorn.internal.runtime.ECMAErrors;

public class ParameterPanel extends JPanel implements ActionListener
{

	GridBagConstraints c;
	JLabel label;
	JCheckBox tair;
	JCheckBox ta9m;
	JCheckBox srad;
	JCheckBox wspd;
	JCheckBox pres;
	String[] selected = new String[5];
	
	public ParameterPanel()
	{
		setSize(80,400);
		setLayout(null);
		c = new GridBagConstraints();
		
		label = new JLabel("Parameter");
		label.setLocation(8,5);
		label.setSize(68,16);
		add(label);
		
		tair = new JCheckBox("TAIR");
		tair.setActionCommand("tair");
		tair.addActionListener(this);
		tair.setLocation(6,49);
		tair.setSize(58,20);
		add(tair);
		
		ta9m = new JCheckBox("TA9M");
		ta9m.setActionCommand("ta9m");
		ta9m.addActionListener(this);
		ta9m.setLocation(6,125);
		ta9m.setSize(58,20);
		add(ta9m);
		
		srad = new JCheckBox("SRAD");
		srad.setActionCommand("srad");
		srad.addActionListener(this);
		srad.setLocation(6,202);
		srad.setSize(58,20);
		add(srad);

		wspd = new JCheckBox("WSPD");
		wspd.setActionCommand("wspd");
		wspd.addActionListener(this);
		wspd.setLocation(6,277);
		wspd.setSize(70,20);
		add(wspd);
				
		pres = new JCheckBox("PRES");
		pres.setActionCommand("wspd");
		pres.addActionListener(this);
		pres.setLocation(6,353);
		pres.setSize(58,20);
		add(pres);
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		selected[0] = "";
		selected[1] = "";
		selected[2] = "";
		selected[3] = "";
		selected[4] = "";
		if(tair.isSelected())
		{
			selected[0] = "TAIR";
		}
		if(ta9m.isSelected())
		{
			selected[1] = "TA9M";
		}
		if(srad.isSelected())
		{
			selected[2] = "SRAD";
		}
		if(wspd.isSelected())
		{
			selected[3] = "WSPD";
		}
		if(pres.isSelected())
		{
			selected[4] = "PRES";
		}
	}
	
	public String[] getSelected()
	{
		return selected;
	}
	
	
	



	
}
