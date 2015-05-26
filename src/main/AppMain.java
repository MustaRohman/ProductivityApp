package main;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class AppMain extends JFrame{

	private JPanel mainPanel;

	private ArrayList<JPanel> tasks;
	//keeps track of task panel objects to that we can access task information
	
	public AppMain(){
		setSize(400,100);
		
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
				NewTask newTask = new NewTask();
				//New task dialog panel
				
				int result = JOptionPane.showConfirmDialog(null, newTask, "Please enter new task details",
						JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION){
					addTask(newTask.getText(), newTask.getSelection());
				}
			}
			
		});
		menuBar.add(addJmi);
		setJMenuBar(menuBar);
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		//Set to BoxLayout.Y_AXIS so that we can add new task panels to the bottom of the list
		
		add(BorderLayout.CENTER, mainPanel);
	}
	
	/**
	 * @param name - Name of the task
	 * @param category - Category of the task
	 * Adds a new task to the main frame
	 */
	public void addTask(String name, String category){
		
		JPanel newTask = new Task(name, category);
		mainPanel.add(newTask);
		tasks.add(newTask);
		pack();
		
	}
	
	public static void main (String args[]){
		
		new AppMain();
	}
	
}
