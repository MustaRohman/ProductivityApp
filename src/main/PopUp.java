package main;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

public class PopUp extends JPopupMenu {

	private JMenuItem editItem;
	private JMenuItem deleteItem;
	private Component comp;
	
	public PopUp(Component c, AppMain main){
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
				TaskPanel selectedTask = (TaskPanel) comp;
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
				System.out.println(main.deleteTask(c) + " deleted");
				
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
