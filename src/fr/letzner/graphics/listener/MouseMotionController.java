/**
 * 
 */
package fr.letzner.graphics.listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import fr.letzner.graphics.engine.CameraManager;
import fr.letzner.graphics.utils.PaysageConstants;

/**
 * @author stefan
 *
 */
public class MouseMotionController implements MouseMotionListener {

	/**
	 * 
	 */
	public MouseMotionController() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void mouseMoved(MouseEvent paramMouseEvent) {
		float angle = ((float)paramMouseEvent.getXOnScreen() * ((float)Math.PI * 2.0f / (float)PaysageConstants.WINDOW_WIDTH));
		CameraManager.getInstance().getCamera().setAngle(angle);
	}
	
	@Override
	public void mouseDragged(MouseEvent paramMouseEvent) {
		//System.out.println("Dragged : " + paramMouseEvent.getX() + " - " + paramMouseEvent.getY());
	}

}
