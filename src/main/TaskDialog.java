package main;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TaskDialog extends JPanel {
	
	private JLabel nameLbl;
	private JTextField nameFld;
	private JLabel categoryLbl;
	private JComboBox categoryBox;
	
	public TaskDialog(){
		setFrame();
		
	}
	
	public TaskDialog(String name, String cat){
		setFrame();
		
		nameFld.setText(name);
		categoryBox.setSelectedItem(cat);
	}
	
	public void setFrame(){
		
		setSize(200,200);
		
		setLayout(new GridBagLayout());
		GridBagConstraints cs = new GridBagConstraints();
		
		
		nameLbl = new JLabel("Task Name:");
		cs.gridx = 0;
		cs.gridy = 0;
		cs.anchor = GridBagConstraints.WEST;
		cs.insets = new Insets(3,3,3,3);
		add(nameLbl, cs);
		
		nameFld = new JTextField(20);
		nameFld.requestFocusInWindow();
		cs.gridx = 1;
		cs.gridy = 0;
		add(nameFld, cs);
		
		categoryLbl = new JLabel("Category:");
		cs.gridx = 0;
		cs.gridy = 1;
		add(categoryLbl, cs);
		
		categoryBox = new JComboBox(TaskPanel.categories);
		cs.gridx = 1;
		cs.gridy = 1;
		add(categoryBox, cs);
		
	}
	
	public String getText(){
		System.out.println(nameFld.getText());
		return nameFld.getText();
	}
	
	public String getCategory(){
		System.out.println(categoryBox.getSelectedItem().toString());
		return categoryBox.getSelectedItem().toString();
	}
	

	

}
