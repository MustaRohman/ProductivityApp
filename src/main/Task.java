package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Task extends JPanel{
	
	private static String[] categories = {"Exercise", "Work", "Study"};
	
	private JButton startBtn;
	private JButton timerBtn;
	private JButton stopBtn;
	private JLabel timeLbl;
	private JLabel taskLbl;
	private double startTime;
	private double endTime;
	private long duration;
	private boolean timerOn;
	private String taskName;
	private String taskCategory;
	
	public Task(String name, String category){
		
		setSize(400,100);
		taskCategory = category;
		taskName = name;
		
		timerOn = false;
		duration = 0;
		setWidgets();
		
		setVisible(true);
		
	}
	
	public void setWidgets(){
		
		taskLbl = new JLabel(taskName);
		timerBtn = new JButton("Off");
		timerBtn.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e){
				if (!timerOn){
					startTime = System.currentTimeMillis();
					timerOn = true;
					timerBtn.setBackground(Color.GREEN);
					timerBtn.setText("On");
				} else {
					endTime = System.currentTimeMillis();
					duration += endTime - startTime;
					long minutes = TimeUnit.MILLISECONDS.toMinutes(duration);
					long hours =  TimeUnit.MILLISECONDS.toHours(duration);
					timeLbl.setText(hours + ":" + minutes);
					timerOn = false;
					timerBtn.setBackground(Color.RED);
					timerBtn.setText("Off");
				}
			}

			
		});
		
			
		timeLbl = new JLabel("0:0");
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(0,3));
		mainPanel.add(taskLbl);
		mainPanel.add(timeLbl);
		mainPanel.add(timerBtn);
		
		
		add(mainPanel, BorderLayout.CENTER);
		
		
	}
	
	public String getTask(){
		return taskLbl.getText();
	}
	
	public String getTime(){
		return timeLbl.getText();
	}
	
	public static double round(double value, int places){
		if (places < 0){
			throw new IllegalArgumentException();
		} else {
			
			BigDecimal bd = new BigDecimal((value));
			bd = bd.setScale(places, RoundingMode.HALF_UP);
			return bd.doubleValue();
		}
	}


}
