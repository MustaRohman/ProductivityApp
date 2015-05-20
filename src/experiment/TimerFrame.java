package experiment;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class TimerFrame extends JFrame{

	private JPanel mainPanel;

	
	public TimerFrame(){
		setSize(300,300);
		
		setFrame();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		setVisible(true);
	}
	
	public void setFrame(){
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
		
		JPanel newTask = new Task("Test", "Random");
		mainPanel.add(newTask);
		repaint();
		
	}
	
	public static void main (String args[]){
		
		new TimerFrame();
	}
	
}
