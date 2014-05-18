/**
 * 
 */
package fr.letzner.graphics.engine;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

import fr.letzner.graphics.actors.Camera;
import fr.letzner.graphics.utils.PaysageConstants;

/**
 * @author stefan
 *
 */
public class CameraManager {

	/**
	 * Constructeur prive
	 */
	private CameraManager(){};
	
	/**
	 * Instance du singleton
	 */
	private static CameraManager instance = null;
	
	
	
	/**
	 * Getter du singleton
	 * @return
	 */
	public static CameraManager getInstance(){
		if (instance == null) {
			instance = new CameraManager();
		}
		
		return instance;
	}
	
	/**
	 * Instance de la camera
	 */
	private Camera camera = new Camera();
	
	/**
	 * Getter de la camera
	 * @return
	 */
	public Camera getCamera() {
		return camera;
	}
	
	/**
	 * 
	 * @param gl
	 * @param glu
	 * @param distance
	 */
	public void setCamera(GL2 gl, GLU glu, float canvasWidth, float canvasHeight) {
        // Change to projection matrix.
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();

        // Perspective.
        float widthHeightRatio = canvasWidth / canvasHeight;
        glu.gluPerspective(45, widthHeightRatio, 1, 1000);

        // Mise a jour pour rotations
        updateRotationCameraEtJoueur();
        
        glu.gluLookAt(camera.getEye_x(), camera.getEye_y(), camera.getEye_z(), 		// Our place 	-> Position
        			camera.getAt_x(), camera.getAt_y(), camera.getAt_z(), 			// Target 		-> LookAt
        			camera.getUp_x(), camera.getUp_y(), camera.getUp_z());			// Head position

        //System.out.println(camera.toString());
        
        // Change back to model view matrix.
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }


	/**
	 * Gestion de la rotation avec la souris
	 * simultanée pour le joueur et la camera
	 */
	private void updateRotationCameraEtJoueur() {
		// La camera voit ce que le joueur voit
		camera.setAt_x(PlayerManager.getInstance().getPlayer().getX() - (float)Math.cos(camera.getAngle()) * camera.getOffset());
		camera.setAt_y(PlayerManager.getInstance().getPlayer().getY());
		camera.setAt_z(PlayerManager.getInstance().getPlayer().getZ() - (float)Math.sin(camera.getAngle()) * camera.getOffset());
	}
	
	/**
	 *  Gestion simultanée du déplacement de la camera et du joueur
	 * @param avance
	 */
	public void updatePositionCameraEtJoueur(boolean avance) {
		// Variables
		float x, z = 0.0f;
		
		if (avance) {
			// Le joueur avance
			x = PlayerManager.getInstance().getPlayer().getX() - ((float)Math.cos(camera.getAngle()) + PaysageConstants.VITESSE);
			z = PlayerManager.getInstance().getPlayer().getZ() - ((float)Math.sin(camera.getAngle()) + PaysageConstants.VITESSE);
		} else {
			// Le joueur recule
			x = PlayerManager.getInstance().getPlayer().getX() + ((float)Math.cos(camera.getAngle()) + PaysageConstants.VITESSE);
			z = PlayerManager.getInstance().getPlayer().getZ() + ((float)Math.sin(camera.getAngle()) + PaysageConstants.VITESSE);
		}
		
		// Mise a jour de la camera et du joueur
		PlayerManager.getInstance().getPlayer().setX(x);
		PlayerManager.getInstance().getPlayer().setZ(z);
		
		camera.setEye_x(x);
		camera.setEye_z(z);
	}
	
	/**
	 * Gestion simultanée de l'altitude de la camera et du joueur
	 * @param monte
	 */
	public void updateAltitudeCameraEtJoueur(boolean monte) {
		// Variables
		float y = 0.0f;
		
		if (monte) {
			// Le joueur avance
			y = PlayerManager.getInstance().getPlayer().getY() + PaysageConstants.VITESSE;
		} else {
			// Le joueur recule
			y = PlayerManager.getInstance().getPlayer().getY() - PaysageConstants.VITESSE;
		}
		
		// Mise a jour de la camera et du joueur
		PlayerManager.getInstance().getPlayer().setY(y);
		camera.setEye_y(y);
	}
}