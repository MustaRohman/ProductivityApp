package main;

import java.util.concurrent.TimeUnit;

import javax.swing.JLabel;

public class TimeUpdate implements Runnable{

	private TaskPanel task;
	
	public TimeUpdate(TaskPanel task){
		
		this.task = task;
		
	}
	
	public void run(){
		try {
			task.updateTime();
			Thread.sleep(TimeUnit.MINUTES.toMillis(1));
			run();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
