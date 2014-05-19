package fr.letzner.graphics.listener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.media.opengl.GL2;

import fr.letzner.graphics.engine.CameraManager;
import fr.letzner.graphics.utils.GameConstants;

public class KeyboardController implements KeyListener {

	private GL2 gl = null;

	public KeyboardController() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void keyPressed(KeyEvent paramKeyEvent) {
		// System.out.println("Key ID : " + paramKeyEvent.getKeyCode());

		switch (paramKeyEvent.getKeyCode()) {
		case 27:
			// Avance
			System.exit(0);
			break;
		case 38:
			// Avance
			CameraManager.getInstance().updatePositionCameraEtJoueur(true);

			break;
		case 40:
			// Recule
			CameraManager.getInstance().updatePositionCameraEtJoueur(false);
			break;
		case 37:
			// Gauche
			CameraManager.getInstance().getCamera().setAngle(
					CameraManager.getInstance().getCamera().getAngle() - GameConstants.VITESSE);
			break;
		case 39:
			// Droite
			CameraManager.getInstance().getCamera().setAngle(
					CameraManager.getInstance().getCamera().getAngle() + GameConstants.VITESSE);
			break;
		case 107:
			// Descend
			CameraManager.getInstance().updateAltitudeCameraEtJoueur(false);
			break;
		case 109:
			// Monte
			CameraManager.getInstance().updateAltitudeCameraEtJoueur(true);
			break;
		}
	}

	@Override
	public void keyReleased(java.awt.event.KeyEvent paramKeyEvent) {
		System.out.println("Key Released : " + paramKeyEvent.getKeyCode());

	}

	@Override
	public void keyTyped(java.awt.event.KeyEvent paramKeyEvent) {
		System.out.println("Key Typed : " + paramKeyEvent.getKeyCode());

	}

}
