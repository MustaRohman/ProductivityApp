package main;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

public class PieChart extends JFrame {
	
	private JFreeChart chart;
	
	public PieChart(String title, DefaultPieDataset dataset){

		super(title);
		
		chart = ChartFactory.createPieChart(title, dataset);
		ChartPanel chartPanel = new ChartPanel(chart);
		
		add(chartPanel);
		
		setSize(400,400);
		setVisible(true);
		
	
	}
}
