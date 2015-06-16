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

public class TaskPanel extends JPanel{
	
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
	
	
	
	/**
	 * 
	 * Constructor for creating object with no existing data
	 * @param name
	 * @param category
	 */
	public TaskPanel(String name, String category){
		
		setFrame(name, category);
		duration = 0;
	
		
	}
	
	/**
	 * 
	 * Constructor for creating object with existing data
	 * @param name
	 * @param category
	 * @param time
	 */
	public TaskPanel(String name, String category, long time){
		
		duration = time;
		setFrame(name, category);
		
	}
	
	/**
	 * Sets the frame, saves constructor data within appropriate vars
	 * @param name
	 * @param category
	 */
	private void setFrame(String name, String category){

		setSize(1000,100);
		taskCategory = category;
		taskName = name;
		
		setBackground(Color.BLACK);
		
		setWidgets();
		setLayout();
		
		
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.gray, 5),
				"<html><font color='gray'>" +taskCategory + "</font></html>"));
		
		timerOn = false;
	}
	
	private void setWidgets(){

		//Updates the timer label every minute
		timer = new Timer(60000, new ActionListener(){
			public void actionPerformed(ActionEvent e){
				updateTime();
			}
		});
		
		//The font for all widgets
		Font font = new Font("Calibri",Font.BOLD, 20);
		
		Border empty = BorderFactory.createEmptyBorder(7,15,7,15);
		Border emptyBorder = BorderFactory.createCompoundBorder(empty, null);
		
		catLbl = new JLabel("<html><font color='gray'>" +taskCategory + "</font></html>");	
		catLbl.setBorder(emptyBorder);
		
		taskLbl = new JLabel(taskName);
		taskLbl.setFont(font);
		taskLbl.setForeground(Color.WHITE);
		taskLbl.setBorder(emptyBorder);
		
		timerBtn = new JButton("Off");
		timerBtn.setFocusable(false);
		timerBtn.setPreferredSize(new Dimension(80,40));
		timerBtn.setFont(font);
		timerBtn.setForeground(Color.white);
		timerBtn.setBackground(Color.BLACK);
		timerBtn.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e){
				if (!timerOn){
					startTime = System.currentTimeMillis();
					timerOn = true;
					timerBtn.setForeground(Color.RED);
					timerBtn.setText("On");
					
					timer.start();
					
					setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.RED, 5),
							"<html><font color='gray'>" +taskCategory + "</font></html>"));
				} else {    
					endTime = System.currentTimeMillis();
					duration += endTime - startTime;
	
					timer.stop();
				
					timerOn = false;
					timerBtn.setForeground(Color.WHITE);
					timerBtn.setText("Off");
					
					timerBtn.setSize(new Dimension(40,25));
					
					setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY, 5),
							"<html><font color='gray'>" +taskCategory + "</font></html>"));
				}
			}

			
		});
		
			
		long minutes = TimeUnit.MILLISECONDS.toMinutes(duration) % 60;
		long hours =  TimeUnit.MILLISECONDS.toHours(duration);
		
		timeLbl =  new JLabel(hours + ":" + minutes);
		timeLbl.setFont(font);
		timeLbl.setForeground(Color.WHITE);
		timeLbl.setBorder(emptyBorder);	
		
	}
	
	private void setLayout(){
		setLayout(new BorderLayout());
//		GridBagConstraints c = new GridBagConstraints();

//		c.gridx = 0;
//		c.gridy = 0;
//		c.insets = new Insets(7,7,7,7);
//		c.gridwidth = 1;
//		c.weightx = 1;
		
		JPanel eastPanel = new JPanel();
		eastPanel.setBackground(Color.BLACK);
		eastPanel.setLayout(new BorderLayout());
		eastPanel.add(timeLbl, BorderLayout.WEST);
		eastPanel.add(timerBtn, BorderLayout.EAST);

		add(taskLbl, BorderLayout.WEST);
		
//		c.gridy = 1;
//		c.insets = new Insets(7,7,7,7);
		
//		c.gridy = 2;
//		c.insets = new Insets(7,7,7,7);
		add(eastPanel, BorderLayout.EAST);
		
	}
	

	
	private void updateTime(){
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
	
	
	public long getTime(){
		return duration;
	}
	
	public String getTimeDisplay(){
		return timeLbl.getText();
	}
	
	public String getCategory(){
		return taskCategory;
	}
	
	public void setName(String newName){
		taskLbl.setText(newName);
	}
	
	public void setCategory(String newCat){
		taskCategory = newCat;
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.gray, 5),
				"<html><font color='gray'>" +taskCategory + "</font></html>"));
		repaint();
	}
	
	public void resetDuration(){
		duration = 0;
	
		timeLbl.setText(0 + ":" + 0);
		repaint();
	}
	
	public boolean isOn(){
		return timerOn;
	}


}
