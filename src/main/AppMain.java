package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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
	
	public AppMain(){
		
		super("Time Tracker");
		setSize(300,100);
		setMinimumSize(new Dimension(300,175));
		
	
		setFrame();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		setVisible(true);
	}
	
	public void setFrame(){
		tasks = new ArrayList<Task>();
		
		JMenuBar menuBar = new JMenuBar();
		
	
		JMenuItem addJmi = new JMenuItem("Add Task");
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
	}
	
	/**
	 * @param name - Name of the task
	 * @param category - Category of the task
	 * Adds a new task to the main frame
	 */
	public void addTask(String name, String category){
		
		Task newTask = new Task(name, category);
		
		//Creates popup menu when right clicked
		newTask.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				//Checks if the correct mouse button has been pressed for pop menu
				if (e.isPopupTrigger()){
					doPop(e);
				}
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()){
					doPop(e);
				}
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()){
					doPop(e);
				}
				
			}
			
			private void doPop(MouseEvent e){
				PopUp menu = new PopUp(e.getComponent());
				menu.show(e.getComponent(), e.getX(), e.getY());
				
			}
			
		});
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
	
	//Inner class for the popmenu
		public class PopUp extends JPopupMenu{
			
			private JMenuItem editItem;
			private JMenuItem deleteItem;
			private Component comp;
			
			public PopUp(Component c){
				comp = c;
				editItem = new JMenuItem("Edit");
				editItem.addMouseListener(new MouseListener(){

					@Override
					public void mouseClicked(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mouseEntered(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mouseExited(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mousePressed(MouseEvent e) {
						Task selectedTask = (Task) comp;
						System.out.println(selectedTask.toString());
						TaskDialog editTask = new TaskDialog(selectedTask.toString(), selectedTask.getCategory());
						int result = JOptionPane.showConfirmDialog(null, editTask, "Edit Task Information",
								JOptionPane.OK_CANCEL_OPTION);
						if (result == JOptionPane.OK_OPTION){
							selectedTask.setName(editTask.getText());
							selectedTask.setCategory(editTask.getCategory());
							repaint();
							pack();
						}
						
					}

					@Override
					public void mouseReleased(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
				});
				deleteItem = new JMenuItem("Delete");
				deleteItem.addMouseListener(new MouseListener(){

					@Override
					public void mouseClicked(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mouseEntered(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mouseExited(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mousePressed(MouseEvent e) {
						System.out.println(deleteTask(c) + " deleted");
						
					}

					@Override
					public void mouseReleased(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
				});
				
				add(editItem);
				add(deleteItem);
			}
			
			
		}
		
	
	
	public static void main (String args[]){
		
		new AppMain();
	}
	
}
