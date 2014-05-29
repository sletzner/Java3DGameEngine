/**
 * 
 */
package fr.letzner.graphics.listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import fr.letzner.graphics.engine.CameraManager;
import fr.letzner.graphics.utils.GameConstants;

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
		float angle_H = ((float)paramMouseEvent.getXOnScreen() * ((float)Math.PI * 2.0f / (float)GameConstants.WINDOW_WIDTH));
		float angle_V = ((float)paramMouseEvent.getYOnScreen() * ((float)Math.PI * 2.0f / (float)GameConstants.WINDOW_HEIGHT));
		
		CameraManager.getInstance().getCamera().setAngle_H(angle_H);
		CameraManager.getInstance().getCamera().setAngle_V(angle_V);
		
		//System.out.println(angle_H + " - " + angle_V);
	}
	
	@Override
	public void mouseDragged(MouseEvent paramMouseEvent) {
		//System.out.println("Dragged : " + paramMouseEvent.getX() + " - " + paramMouseEvent.getY());
	}

}
