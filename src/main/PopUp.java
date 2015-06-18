package main;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

public class PopUp extends JPopupMenu {

	private JMenuItem editItem;
	private JMenuItem resetItem;
	private JMenuItem deleteItem;
	private JMenuItem delAllItem;
	
	private TaskPanel selectedTask;
	
	private Component comp;
	private AppMain mainFrame;
	
	public PopUp(Component c, AppMain main){
		mainFrame = main;
		comp = c;
		selectedTask = (TaskPanel) c;
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
				
				System.out.println(selectedTask.toString());
				TaskDialog editTask = new TaskDialog(selectedTask.toString(), selectedTask.getCategory());
				int result = JOptionPane.showConfirmDialog(null, editTask, "Edit Task Information",
						JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION){
					selectedTask.setName(editTask.getText());
					selectedTask.setCategory(editTask.getCategory());
					repaint();
					pack();
					main.pack();
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
				System.out.println(main.deleteTask(c) + " deleted");
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		
		
		resetItem = new JMenuItem("Reset Time");
		resetItem.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e){
					selectedTask.resetDuration();
				
			}
			
		});
		
		
		
		add(editItem);
		add(resetItem);
		add(deleteItem);
	}
	
}
