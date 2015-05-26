package main;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class NewTask extends JPanel {
	
	private JLabel nameLbl;
	private JTextField nameFld;
	private JLabel categoryLbl;
	private JComboBox categoryBox;
	
	public NewTask(){
		setSize(200,200);
		setWidgets();
		
		
		
	}
	
	public void setWidgets(){
		setLayout(new GridBagLayout());
		GridBagConstraints cs = new GridBagConstraints();
		
		
		nameLbl = new JLabel("Task Name:");
		cs.gridx = 0;
		cs.gridy = 0;
		cs.anchor = GridBagConstraints.WEST;
		cs.insets = new Insets(3,3,3,3);
		add(nameLbl, cs);
		
		nameFld = new JTextField(20);
		cs.gridx = 1;
		cs.gridy = 0;
		add(nameFld, cs);
		
		categoryLbl = new JLabel("Category:");
		cs.gridx = 0;
		cs.gridy = 1;
		add(categoryLbl, cs);
		
		categoryBox = new JComboBox(Task.categories);
		cs.gridx = 1;
		cs.gridy = 1;
		add(categoryBox, cs);
		
	}
	
	public String getText(){
		return nameFld.getText();
	}
	
	public String getSelection(){
		return categoryBox.getSelectedItem().toString();
	}
	

}
