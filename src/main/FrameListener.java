package main;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class FrameListener implements WindowListener {
	
	private ArrayList<TaskPanel> taskList;
	
	public FrameListener(ArrayList<TaskPanel> tasks, AppMain main){
		taskList = tasks;
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent e) {
		

	}

	@Override
	public void windowClosing(WindowEvent e) {
		File newFile = newFile = new File("C:\\Users\\Lenovo\\Documents\\task.txt");
		
		newFile.getParentFile().mkdirs();
		
		PrintWriter pw = null;
		
		try {
			pw = new PrintWriter(new BufferedWriter(new FileWriter(newFile)));
			
			System.out.println(taskList.isEmpty());
			
			for (TaskPanel t : taskList){
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
		

	}
	
	public void printTask(TaskPanel task, PrintWriter pw){
		
		String taskDetail = task.getName() + " " + task.getCategory() + " " +  task.getTime();
		pw.println(taskDetail);
		
		System.out.println(taskDetail);
	}

}
