/**
 * 
 */
package fr.letzner.graphics.listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * @author stefan
 *
 */
public class MouseController implements MouseListener {


	/**
	 * Constructeur
	 */
	public MouseController() throws Exception {

	}

	@Override
	public void mouseReleased(MouseEvent paramMouseEvent) {
		System.out.println("Released : " + paramMouseEvent.getX() + " - " + paramMouseEvent.getY());
		
	}
	
	@Override
	public void mousePressed(MouseEvent paramMouseEvent) {
		System.out.println("Pressed : " + paramMouseEvent.getX() + " - " + paramMouseEvent.getY());
		
	}
	
	@Override
	public void mouseExited(MouseEvent paramMouseEvent) {
		System.out.println("Exited : " + paramMouseEvent.getX() + " - " + paramMouseEvent.getY());
		
	}
	
	@Override
	public void mouseEntered(MouseEvent paramMouseEvent) {
		System.out.println("Entered : " + paramMouseEvent.getX() + " - " + paramMouseEvent.getY());
		
	}
	
	@Override
	public void mouseClicked(MouseEvent paramMouseEvent) {
		System.out.println("Click : " + paramMouseEvent.getX() + " - " + paramMouseEvent.getY());
		
	}
	
	
}
