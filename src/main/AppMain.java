package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import org.jfree.data.general.DefaultPieDataset;

public class AppMain extends JFrame{

	private JPanel mainPanel;
	private ArrayList<Task> tasks;
	//keeps track of task panel objects to that we can access task information
	
	private static File dataFile = new File("C:\\Users\\Lenovo\\Documents\\task.txt"); ;
	
	public AppMain(){
		
		super("Time Tracker");
		setSize(300,100);
		setMinimumSize(new Dimension(300,175));
		
		tasks = new ArrayList<Task>();
		
		addWindowListener(new FrameListener());
		
		setFrame();
		
		setVisible(true);
	}
	
	public void setFrame(){
		
		Font font = new Font("Calibri",Font.BOLD, 20);
		tasks = new ArrayList<Task>();
		
		JMenuBar menuBar = new JMenuBar();
		
		JMenuItem addJmi = new JMenuItem("+");
		addJmi.setFont(font);
		addJmi.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY));
		addJmi.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e){
				TaskDialog newTask = new TaskDialog();
				//New task dialog panel
				
				int result = JOptionPane.showConfirmDialog(null, newTask, "Please enter new task details",
						JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION){
					addTask(newTask.getText(), newTask.getCategory());
				}
			}
			
		});
		
		JMenuItem chartJmi = new JMenuItem("Chart");
		chartJmi.setFont(font);
		chartJmi.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 1, Color.GRAY));
		chartJmi.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e){
			
				if (tasks.isEmpty()){
					JOptionPane.showMessageDialog(null, "No tasks have been added!", 
							"Error", JOptionPane.INFORMATION_MESSAGE);
				} else{
					DefaultPieDataset dataset = new DefaultPieDataset();
					for (Task t: tasks){
						
						dataset.setValue(t.getCategory(), t.getTime());
					}
					
					PieChart pie = new PieChart("Chart", dataset);
				}
				
			}
		});
		
		menuBar.add(addJmi);
		menuBar.add(chartJmi);
		setJMenuBar(menuBar);
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		//Set to BoxLayout.Y_AXIS so that we can add new task panels to the bottom of the list
		
		add(BorderLayout.NORTH, mainPanel);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}
	
	/**
	 * @param name - Name of the task
	 * @param category - Category of the task
	 * Adds a new task to the main frame
	 */
	public void addTask(String name, String category){
		
		Task newTask = new Task(name, category);
		
		//Creates popup menu when right clicked
		PopUpListener popListen = new PopUpListener(this);
		newTask.addMouseListener(popListen);
		
		mainPanel.add(newTask);
		tasks.add(newTask);
		
		pack();
		
	}
	
	public JPanel deleteTask(Component c){
		
		JPanel panel = (JPanel) c;
		
		for (JPanel p: tasks){
			if (panel.equals(p)){
				tasks.remove(p);
				mainPanel.remove(p);
				mainPanel.revalidate();
				mainPanel.updateUI();
				repaint();
				pack();
				System.out.println("Packed");
				return p;
			}
		}
		
		
		return null;
		
	}
	
	
		
		public class FrameListener implements WindowListener {
			


			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosed(WindowEvent e) {
				

			}

			@Override
			public void windowClosing(WindowEvent e) {
				
				dataFile.getParentFile().mkdirs();
				
				
				
				try {
					PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(dataFile)));
					
					System.out.println(tasks.isEmpty());
					
					for (Task t : tasks){
						printTask(t, pw);
					}
					
				} catch (IOException e1) {
					System.out.println("IO error has occured");
				}
				

			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowOpened(WindowEvent e) {
					if (dataFile.exists() && !dataFile.isDirectory()){
					
					try {
						BufferedReader br = new BufferedReader(new FileReader(dataFile));
						String line;
						while ((line = br.readLine()) != null){
							String[] tempArray = line.split(" ");
							addTask(tempArray[0], tempArray[1]);
							
						}
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}

			}
			
			public void printTask(Task task, PrintWriter pw){
				
				String taskDetail = task.toString() + " " + task.getCategory() + " " +  task.getTime();
				pw.println(taskDetail);
				
				pw.flush();
				
				System.out.println(taskDetail);
			}

		}	
	
	
	public static void main (String args[]){
		
		new AppMain();
	}
	
}
