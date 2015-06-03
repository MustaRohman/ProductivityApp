package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
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
	
	private Timer timer;
	
	public Task(String name, String category){
		
		setSize(1000,100);
		taskCategory = category;
		taskName = name;
		

		setWidgets();
		setLayout();
		
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.gray),
				"<html><font color='gray'>" +taskCategory + "</font></html>"));
		
		timerOn = false;
		duration = 0;
		
		
		
		
		
		
		
	}
	
	public void setWidgets(){
		//The font for all widgets
		Font font = new Font("Calibri",Font.BOLD, 20);
		
		timer = new Timer(60000, new ActionListener(){
			public void actionPerformed(ActionEvent e){
				updateTime();
			}
		});
		
		Border empty = BorderFactory.createEmptyBorder(7,15,7,15);
		Border emptyBorder = BorderFactory.createCompoundBorder(empty, null);
		
		catLbl = new JLabel("<html><font color='gray'>" +taskCategory + "</font></html>");	
		catLbl.setBorder(emptyBorder);
		
		taskLbl = new JLabel(taskName);
		taskLbl.setFont(font);
		taskLbl.setBorder(emptyBorder);
		
		timerBtn = new JButton("Off");
		timerBtn.setSize(new Dimension(40,25));
		timerBtn.setFont(font);
		timerBtn.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e){
				if (!timerOn){
					startTime = System.currentTimeMillis();
					timerOn = true;
					timerBtn.setBackground(Color.GREEN);
					timerBtn.setText("On");
					
					timer.start();
					
					setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GREEN),
							"<html><font color='gray'>" +taskCategory + "</font></html>"));
				} else {    
					endTime = System.currentTimeMillis();
					duration += endTime - startTime;
	
					timer.stop();
				
					timerOn = false;
					timerBtn.setBackground(new JButton().getBackground());
					timerBtn.setText("Off");
					
					setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY),
							"<html><font color='gray'>" +taskCategory + "</font></html>"));
				}
			}

			
		});
		
			
		timeLbl = new JLabel("0:0");
		timeLbl.setFont(font);
		timeLbl.setBorder(emptyBorder);
		
			
		
	}
	
	public void setLayout(){
		setLayout(new BorderLayout());
//		GridBagConstraints c = new GridBagConstraints();

//		c.gridx = 0;
//		c.gridy = 0;
//		c.insets = new Insets(7,7,7,7);
//		c.gridwidth = 1;
//		c.weightx = 1;
		
		JPanel eastPanel = new JPanel();
		eastPanel.setLayout(new GridLayout(0,2));
		eastPanel.add(timeLbl);
		eastPanel.add(timerBtn);

		add(taskLbl, BorderLayout.WEST);
		
//		c.gridy = 1;
//		c.insets = new Insets(7,7,7,7);
		
//		c.gridy = 2;
//		c.insets = new Insets(7,7,7,7);
		add(eastPanel, BorderLayout.EAST);
		
	}
	

	
	public void updateTime(){
		duration += System.currentTimeMillis() - startTime;
		startTime = System.currentTimeMillis();
		long minutes = TimeUnit.MILLISECONDS.toMinutes(duration) % 60;
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
		taskCategory = newCat;
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.gray),
				"<html><font color='gray'>" +taskCategory + "</font></html>"));
		repaint();
	}


}
