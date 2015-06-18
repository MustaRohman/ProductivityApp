package main;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.jfree.data.general.DefaultPieDataset;

public class AppMain extends JFrame{

	private JPanel mainPanel;
	private ArrayList<TaskPanel> tasks;
	//keeps track of task panel objects to that we can access task information
	
	private final SimpleDateFormat SDF = new SimpleDateFormat("MM-dd-yyyy");
	private Font font = new Font("Calibri",Font.BOLD, 20);
	
	private File dataFile = new File("C:\\Users\\Lenovo\\Documents\\TimeTracker\\taskDetails");
	
	public AppMain(){
		
		super("Time Tracker");
		setSize(300,100);
		getContentPane().setBackground(Color.black);
		setMinimumSize(new Dimension(300,175));
		
		tasks = new ArrayList<TaskPanel>();
		
		//Initialises dataFile with current date
		Calendar cal = Calendar.getInstance();
		String path = dataFile.getAbsolutePath();
		dataFile = new File(path + SDF.format(cal.getTime()) + ".txt");
		
		
		
		addWindowListener(new FrameListener());
		
		setFrame();
		
		setVisible(true);
	}
	
	public void setFrame(){
		
		tasks = new ArrayList<TaskPanel>();
		
		
		setTrayIcon();
		//Sets icon in System tray
		
		
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("C:\\Users\\Lenovo\\Pictures\\clock.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		setIconImage(img);
		//Sets the icon for the main application

		
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(Color.BLACK);
		menuBar.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		
		JMenu tasksJm = new JMenu("Tasks");
		formatComponent(tasksJm);
		
		JMenuItem addJmi = new JMenuItem("Add Task");
		formatComponent(addJmi);
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
		JMenuItem delAllJmi = new JMenuItem("Delete All Tasks");
		formatComponent(delAllJmi);
		delAllJmi.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				int option = JOptionPane.showConfirmDialog(null, 
						"Are you sure you want to delete all tasks?");
				if (option == JOptionPane.OK_OPTION){
					deleteAll();
				}
				
			}
			
		});
		
		JMenuItem resetAllJmi = new JMenuItem("Reset All Tasks");
		formatComponent(resetAllJmi);
		resetAllJmi.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				for (TaskPanel tp: tasks){
					tp.resetDuration();
				}
				
				JOptionPane.showMessageDialog(null, "Times for all tasks have been reset");
				
			}
			
		});
		
		tasksJm.add(addJmi);
		tasksJm.add(resetAllJmi);
		tasksJm.add(delAllJmi);
		
		JMenu dataJm = new JMenu("Data");
		formatComponent(dataJm);
		
		JMenuItem chartJmi = new JMenuItem("Chart");
		formatComponent(chartJmi);
		chartJmi.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e){
			
				if (tasks.isEmpty()){
					JOptionPane.showMessageDialog(null, "No tasks have been added!", 
							"Error", JOptionPane.INFORMATION_MESSAGE);
				} else{
					DefaultPieDataset dataset = new DefaultPieDataset();
					System.out.println("Time data:");
					
					HashMap<String, Double> catData = new HashMap<String, Double>();
					
					for (String s : TaskPanel.categories){
						catData.put(s, new Double(0));
					}
					
					
					for (TaskPanel t: tasks){
						
						catData.put(t.getCategory(), 
								catData.get(t.getCategory()) + (double) t.getTime());
						//Increments the time for the appropriate category with the task duration
						
				
					}
					
					
					Iterator<Entry<String, Double>> it = catData.entrySet().iterator();
					
					while (it.hasNext()){
						Map.Entry pair = (Map.Entry) it.next();
						dataset.setValue((Comparable) pair.getKey(), (Double) pair.getValue()); 
						
					}
					
					PieChart pie = new PieChart("Chart", dataset);
				}
				
			}
		});
		
		dataJm.add(chartJmi);
		
		menuBar.add(tasksJm);
		menuBar.add(dataJm);
		setJMenuBar(menuBar);
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		//Set to BoxLayout.Y_AXIS so that we can add new task panels to the bottom of the list
		
		add(BorderLayout.NORTH, mainPanel);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}
	
	public void setTrayIcon(){
		if (SystemTray.isSupported()) {

		    SystemTray tray = SystemTray.getSystemTray();
		    Image image = Toolkit.getDefaultToolkit().getImage("C:\\Users\\Lenovo\\Pictures\\clock.png");
		            
		    PopupMenu popup = new PopupMenu();
		    MenuItem defaultItem = new MenuItem("Exit");
		    defaultItem.addActionListener(new ActionListener(){
		    	
		    	 public void actionPerformed(ActionEvent e) {
			            System.out.println("Exiting...");
			            System.exit(0);
			        }
		    	
		    });
		    popup.add(defaultItem);
		    
		    final  TrayIcon trayIcon = new TrayIcon(image, "Time Tracker", popup);	             
		            
		    trayIcon.setImageAutoSize(true);
		    trayIcon.addActionListener(new ActionListener(){
		    	
		    	 public void actionPerformed(ActionEvent e) {
			            trayIcon.displayMessage("Action Event", 
			                "An Action Event Has Been Performed!",
			                TrayIcon.MessageType.INFO);
			        }
		    	 
		    });
		    trayIcon.addMouseListener(new MouseListener(){
		    	
		    	public void mouseClicked(MouseEvent e) {
		    		
		        	TaskPanel tp = null;
		        	for (TaskPanel t: tasks){
		        		if (t.isOn()){
		        			tp = t;
		        		}
		        	}
		        	
		        	if (tp != null){
		        		trayIcon.displayMessage(tp.toString(), tp.getTimeDisplay(),TrayIcon.MessageType.INFO);
		        	}
		        }

		        public void mouseEntered(MouseEvent e) {
		            System.out.println("Tray Icon - Mouse entered!");                 
		        }

		        public void mouseExited(MouseEvent e) {
		            System.out.println("Tray Icon - Mouse exited!");                 
		        }

		        public void mousePressed(MouseEvent e) {
		            System.out.println("Tray Icon - Mouse pressed!");                 
		        }

		        public void mouseReleased(MouseEvent e) {
		            System.out.println("Tray Icon - Mouse released!");                 
		        }
		    	
		    });

		    try {
		        tray.add(trayIcon);
		    } catch (AWTException e) {
		        System.err.println("TrayIcon could not be added.");
		    }

		} else {

		    //  System Tray is not supported

		}
	}
	
	/**
	 * @param name - Name of the task
	 * @param category - Category of the task
	 * Adds a new task to the main frame
	 */
	public void addTask(String name, String category){
		
		TaskPanel newTask = new TaskPanel(name, category);
		
		//Creates popup menu when right clicked
		PopUpListener popListen = new PopUpListener(this);
		newTask.addMouseListener(popListen);
		
		mainPanel.add(newTask);
		tasks.add(newTask);
		
		pack();
		
	}
	
	public TaskPanel addTask(String name, String category, String time){
		
		long longTime = Long.parseLong(time);
		
		TaskPanel newTask = new TaskPanel(name, category, longTime);
		
		//Creates popup menu when right clicked
		PopUpListener popListen = new PopUpListener(this);
		newTask.addMouseListener(popListen);
		
		mainPanel.add(newTask);
		tasks.add(newTask);
		
		pack();
		
		return newTask;
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
	
	public void deleteAll(){
		mainPanel.removeAll();
		tasks.clear();
		repaint();
		pack();
	}
	
	private void formatComponent(JComponent c){
		
		c.setFont(new Font("Calibri",Font.BOLD, 16));
		c.setForeground(Color.WHITE);
		c.setBackground(Color.BLACK);
		c.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 1, Color.GRAY));
	}
		
	
	public void readFile(File file) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		while ((line = br.readLine()) != null){
			String[] tempArray = line.split(",");
			TaskPanel newTask = addTask(tempArray[0], tempArray[1], tempArray[2]);
			System.out.println(newTask.toString() + " Duration : " + newTask.getTime());
			setLocationRelativeTo(null);
		}
		System.out.println("Task Data Loaded");
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
					
					for (TaskPanel t : tasks){
						printTask(t, pw);
					}
					
				} catch (IOException e1) {
					System.out.println("IO error has occured");
				}
				
				
				

			}// End of method

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
							readFile(dataFile);	
							
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					
				} else {
					
				File previousFile = new File("C:\\Users\\Lenovo\\Documents\\TimeTracker\\taskDetails");
				
				
					
				}//End of If/Else 
										
				System.out.println(dataFile.lastModified());
				
				System.out.println(SDF.format(dataFile.lastModified()));

			}// End of method
			
			public void printTask(TaskPanel task, PrintWriter pw){
				
				String taskDetail = task.toString() + "," + task.getCategory() + "," +  task.getTime();
				pw.println(taskDetail);
				
				pw.flush();
				
				System.out.println(taskDetail);
			}

		} //End of FrameListener Class
	
	
	public static void main (String args[]){
		
		new AppMain();
	}//End of main
	
}//End of AppMain Class
