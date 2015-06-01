package main;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.TimeUnit;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.Border;

public class Task extends JPanel{
	
	public static String[] categories = {"Health", "Work", "Study", "Entertainment"};
	
	private JButton timerBtn;
	private JLabel timeLbl;
	private JLabel taskLbl;
	private JLabel catLbl;
	private double startTime;
	private double endTime;
	private long duration;
	private boolean timerOn;
	private String taskName;
	private String taskCategory;
	private Thread timerThread;
	
	public Task(String name, String category){
		
		setSize(800,100);
		taskCategory = category;
		taskName = name;
		
		setBorder(BorderFactory.createMatteBorder(1, 1, 0, 1, Color.gray));
		
		timerOn = false;
		duration = 0;
		
		Thread newThread = new Thread(new TimeUpdate(this));
		timerThread = newThread;
		
		setWidgets();
		setLayout();
		
		
		
	}
	
	public void setWidgets(){
		
		Border empty = BorderFactory.createEmptyBorder(5,5,5,10 );
		Border emptyBorder = BorderFactory.createCompoundBorder(empty, null);
		
		catLbl = new JLabel("<html><font color='gray'>" +taskCategory + "</font></html>");	
		catLbl.setBorder(emptyBorder);
		
		taskLbl = new JLabel(taskName);
		taskLbl.setBorder(emptyBorder);
		
		timerBtn = new JButton("Off");
		timerBtn.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e){
				if (!timerOn){
					startTime = System.currentTimeMillis();
					timerOn = true;
					timerBtn.setBackground(Color.GREEN);
					timerBtn.setText("On");
					
					timerThread.start();
					
					
					setBorder(BorderFactory.createDashedBorder(Color.GREEN));
				} else {    
					endTime = System.currentTimeMillis();
					duration += endTime - startTime;
	
					timerThread.interrupt();
				
					timerOn = false;
					timerBtn.setBackground(new JButton().getBackground());
					timerBtn.setText("Off");
					
					setBorder(null);
				}
			}

			
		});
		
			
		timeLbl = new JLabel("0:0");
		timeLbl.setBorder(emptyBorder);
		
			
		
	}
	
	public void setLayout(){
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(5,5,5,5);
		c.gridwidth = 1;
		c.weightx = 1;
		add(catLbl,c);
		
		c.gridy = 1;
		c.insets = new Insets(5,5,5,5);
		add(taskLbl);
		
		c.gridy = 2;
		c.insets = new Insets(5,5,5,5);
		add(timeLbl);
		
		c.gridy = 3;
		c.insets = new Insets(5,5,5,5);
		add(timerBtn);
		
	}
	

	
	public void updateTime(){
		duration += System.currentTimeMillis() - startTime;
		startTime = System.currentTimeMillis();
		long minutes = TimeUnit.MILLISECONDS.toMinutes(duration);
		long hours =  TimeUnit.MILLISECONDS.toHours(duration);
	
		timeLbl.setText(hours + ":" + minutes);
		repaint();
		System.out.println("Test");
	}
	
	public String toString(){
		return taskLbl.getText();
	}
	
	
	public double getTime(){
		return (double) duration;
	}
	
	public String getCategory(){
		return taskCategory;
	}
	
	public void setName(String newName){
		taskLbl.setText(newName);
	}
	
	public void setCategory(String newCat){
		catLbl.setText("<html><font color='gray'>" + newCat + "</font></html>");
	}


}
