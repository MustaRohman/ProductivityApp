package main;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

public class PieChart extends JFrame {
	
	private JFreeChart chart;
	private DefaultPieDataset dataset;
	private ArrayList<TaskPanel> tasks;
	private HashMap<String, Double> catData;
	
	public PieChart(String title, ArrayList<TaskPanel> tasks){

		super(title);
		
		this.tasks = tasks;
		
		createDataset();
		
		chart = ChartFactory.createPieChart(title, dataset);
		ChartPanel chartPanel = new ChartPanel(chart);
		
		add(BorderLayout.CENTER, chartPanel);
		
		setSouthPanel();
		
		
		setSize(400,400);
		setVisible(true);
		
	
	}
	
	public void createDataset(){
		dataset = new DefaultPieDataset();
		System.out.println("Time data:");
		
		catData = new HashMap<String, Double>();
		//HashMap storing categories along with time
		
		for (String s : TaskPanel.categories){
			catData.put(s, new Double(0));
		}
		//Adds all categories to map with value of 0
		
		for (TaskPanel t: tasks){
			
			catData.put(t.getCategory(), 
					catData.get(t.getCategory()) + (double) t.getTime());
			//Increments the time for the appropriate category with the task duration	
	
		}
		
		
		Iterator<Entry<String, Double>> it = catData.entrySet().iterator();
		//Iterator for the entries within the map
		
		while (it.hasNext()){
			Map.Entry pair = (Map.Entry) it.next();
			dataset.setValue((Comparable) pair.getKey(), (Double) pair.getValue()); 
			
		}
	}
	
	private void setSouthPanel(){
		
		JPanel southPanel = new JPanel(new BorderLayout());
		
		JPanel southWestPanel = new JPanel();
		southWestPanel.setLayout(new BoxLayout(southWestPanel, BoxLayout.Y_AXIS));

		JPanel southEastPanel = new JPanel();
		southEastPanel.setLayout(new BoxLayout(southEastPanel, BoxLayout.Y_AXIS));
		
	
		
		
		Iterator<Entry<String, Double>> it = catData.entrySet().iterator();
		
		while (it.hasNext()){
			
			Map.Entry pair = (Map.Entry) it.next();
			
			southWestPanel.add(new JLabel(pair.getKey().toString()));
			
			Double duration = (Double) pair.getValue();
			Long durLong = duration.longValue();
			
			long min = TimeUnit.MILLISECONDS.toMinutes(durLong) % 60;
			long hour = TimeUnit.MILLISECONDS.toHours(durLong);
			
			southEastPanel.add(new JLabel(hour + " hours " + min +  " minutes"));
			
		}
		
		southPanel.add(BorderLayout.NORTH, new JLabel("Time Spent Today:"));
		southPanel.add(BorderLayout.WEST, southWestPanel);
		southPanel.add(BorderLayout.EAST, southEastPanel);
		
		add(BorderLayout.SOUTH, southPanel);
		
		
	}
	
	
}
