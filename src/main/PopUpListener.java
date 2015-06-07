package main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class PopUpListener implements MouseListener {

	private AppMain main;
	
	public PopUpListener(AppMain mainObj){
		main = mainObj;
	}
	
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
		PopUp menu = new PopUp(e.getComponent(), main);
		menu.show(e.getComponent(), e.getX(), e.getY());
		
	}
	
}


