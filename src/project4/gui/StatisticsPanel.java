package project4.gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JRadioButton;

import project4.mapdata.StatsType;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class StatisticsPanel extends JPanel implements ActionListener
{
	
	private JRadioButton max;
	private JRadioButton min;
	private JRadioButton avg;
	private ButtonGroup buttons;
	private StatsType statsType;
	
	public StatisticsPanel()
	{
		
		setSize(75,500);
		setLayout(null);
		
		JLabel label = new JLabel("Statistics");
		label.setLocation(8,5);
		label.setSize(61,18);
		add(label);
		
		min = new JRadioButton("MINIMUM");
		min.setActionCommand("min");
		min.addActionListener(this);
		min.setSize(88,20);
		min.setLocation(9,76);
		add(min);
		
		avg = new JRadioButton("AVERAGE");
		avg.setLocation(9,200);
		avg.setSize(88,20);
		avg.setActionCommand("avg");
		avg.addActionListener(this);
		add(avg);
		
		max = new JRadioButton("MAXIMUM");
		max.setLocation(9,327);
		max.setSize(88,20);
		max.setActionCommand("max");
		max.addActionListener(this);
		add(max);
		
		buttons = new ButtonGroup();
		buttons.add(max);
		buttons.add(min);
		buttons.add(avg);
		
		setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getActionCommand().equals("max"))
		{
			statsType = StatsType.MAXIMUM;
		}
		if(e.getActionCommand().equals("min"))
		{
			statsType = StatsType.MINIMUM;
		}
		if(e.getActionCommand().equals("avg"))
		{
			statsType = StatsType.AVERAGE;
		}
		
	}
	
	public StatsType getStatType()
	{
		return statsType;
	}

}
