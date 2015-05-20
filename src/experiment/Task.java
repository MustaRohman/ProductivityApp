package experiment;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
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
	private double duration;
	private boolean timerOn;
	private String taskName;
	private String taskCategory;
	
	public Task(String name, String category){
		
		setSize(300,100);
		taskCategory = category;
		taskName = name;
		
		timerOn = false;
		duration = 0;
		setWidgets();
		
		setVisible(true);
		
	}
	
	public void setWidgets(){
		
		taskLbl = new JLabel(taskName);
		timerBtn = new JButton("Timer");
		timerBtn.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e){
				if (!timerOn){
					startTime = System.currentTimeMillis();
					timerOn = true;
					timerBtn.setBackground(Color.GREEN);
				} else {
					endTime = System.currentTimeMillis();
					duration += endTime - startTime;
					timeLbl.setText(duration/1000 + "");
					timerOn = false;
					timerBtn.setBackground(Color.RED);
				}
			}

			
		});
		
			
		timeLbl = new JLabel("0.00");
		
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


}
