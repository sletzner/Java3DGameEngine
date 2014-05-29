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
		//System.out.println("Key ID : " + paramKeyEvent.getKeyCode());

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
			CameraManager.getInstance().getCamera().setAngle_H(
					CameraManager.getInstance().getCamera().getAngle_H() - GameConstants.VITESSE_ROTATION);
			break;
		case 39:
			// Droite
			CameraManager.getInstance().getCamera().setAngle_H(
					CameraManager.getInstance().getCamera().getAngle_H() + GameConstants.VITESSE_ROTATION);
			break;
		case 107:
			// Descend
			CameraManager.getInstance().updateAltitudeCameraEtJoueur(false);
			break;
		case 109:
			// Monte
			CameraManager.getInstance().updateAltitudeCameraEtJoueur(true);
			break;
		case 112:
			// Activer mode VOL
			if (CameraManager.getInstance().isModeVolActif()) {
				CameraManager.getInstance().setModeVolActif(false);
			} else {
				CameraManager.getInstance().setModeVolActif(true);
			}
			
			System.out.println("Activation mode VOL : " + CameraManager.getInstance().isModeVolActif());
			
			break;
		}
	}

	@Override
	public void keyReleased(java.awt.event.KeyEvent paramKeyEvent) {
		//System.out.println("Key Released : " + paramKeyEvent.getKeyCode());

	}

	@Override
	public void keyTyped(java.awt.event.KeyEvent paramKeyEvent) {
		//System.out.println("Key Typed : " + paramKeyEvent.getKeyCode());

	}

}
