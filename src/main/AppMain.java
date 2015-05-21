package main;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class AppMain extends JFrame{

	private JPanel mainPanel;

	private ArrayList<JPanel> tasks;
	
	public AppMain(){
		setSize(222,100);
		
		setFrame();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		setVisible(true);
	}
	
	public void setFrame(){
		tasks = new ArrayList<JPanel>();
		
		JMenuBar menuBar = new JMenuBar();
		JMenuItem addJmi = new JMenuItem("Add Task");
		addJmi.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e){
				addTask();
			}
			
		});
		menuBar.add(addJmi);
		setJMenuBar(menuBar);
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		add(BorderLayout.CENTER, mainPanel);
	}
	
	public void addTask(){
		
		JPanel newTask = new Task("Test", "Education");
		mainPanel.add(newTask);
		tasks.add(newTask);
		pack();
		
	}
	
	public static void main (String args[]){
		
		new AppMain();
	}
	
}
