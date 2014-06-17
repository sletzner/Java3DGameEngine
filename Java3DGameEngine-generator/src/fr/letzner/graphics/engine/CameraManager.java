/**
 * 
 */
package fr.letzner.graphics.engine;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

import fr.letzner.graphics.actors.Camera;
import fr.letzner.graphics.shapes.Impl.ShapeManager;
import fr.letzner.graphics.utils.GameConstants;

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
	 * Possibilité de voler ou rester sur le terrain
	 */
	private boolean modeVolActif = true;
	
	
	
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

        System.out.println(camera.toString());
        
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
		camera.setAt_x(PlayerManager.getInstance().getPlayer().getX() - (float)Math.cos(camera.getAngle_H()) * camera.getOffset());
		camera.setAt_z(PlayerManager.getInstance().getPlayer().getZ() - (float)Math.sin(camera.getAngle_H()) * camera.getOffset());
		camera.setAt_y(PlayerManager.getInstance().getPlayer().getY() - (float)Math.tan(camera.getAngle_V()) * camera.getOffset());
	}
	
	/**
	 *  Gestion simultanée du déplacement de la camera et du joueur
	 * @param avance
	 */
	public void updatePositionCameraEtJoueur(boolean avance) {
		// Variables
		float x = 0.0f;
		float y = 0.0f;
		float z = 0.0f;
		
		if (avance) {
			// Le joueur avance
			x = PlayerManager.getInstance().getPlayer().getX() - ((float)Math.cos(camera.getAngle_H()) * GameConstants.VITESSE_ROTATION);
			z = PlayerManager.getInstance().getPlayer().getZ() - ((float)Math.sin(camera.getAngle_H()) * GameConstants.VITESSE_ROTATION);
			
			if (this.isModeVolActif()) {
				// Calcul de la hauteur uniquement si en mode VOL
				y = PlayerManager.getInstance().getPlayer().getY() - ((float)Math.tan(camera.getAngle_V()) + GameConstants.VITESSE_ROTATION);
			} else {
				// Hauteur par rapport au terrain
				y = getHauteurTerrain(x, z);
			}
		} else {
			// Le joueur recule
			x = PlayerManager.getInstance().getPlayer().getX() + ((float)Math.cos(camera.getAngle_H()) * GameConstants.VITESSE_ROTATION);
			z = PlayerManager.getInstance().getPlayer().getZ() + ((float)Math.sin(camera.getAngle_H()) * GameConstants.VITESSE_ROTATION);
			
			if (this.isModeVolActif()) {
				// Calcul de la hauteur uniquement si en mode VOL
				y = PlayerManager.getInstance().getPlayer().getY() + ((float)Math.tan(camera.getAngle_V()) + GameConstants.VITESSE_ROTATION);
			} else {
				// Hauteur par rapport au terrain
				y = getHauteurTerrain(x, z);
			}
		}
		
		// Mise a jour de la camera et du joueur
		PlayerManager.getInstance().getPlayer().setX(x);
		PlayerManager.getInstance().getPlayer().setY(y);
		PlayerManager.getInstance().getPlayer().setZ(z);
		
		camera.setEye_x(x);
		camera.setEye_y(y);
		camera.setEye_z(z);
	}
	
	public void updatePositionCameraEtJoueur() {
		// Variables
		float x = 0.0f;
		float y = 0.0f;
		float z = 0.0f;
		
		x = PlayerManager.getInstance().getPlayer().getX();
		z = PlayerManager.getInstance().getPlayer().getZ();
		y = getHauteurTerrain(x, z);
		
		// Mise a jour de la camera et du joueur
		PlayerManager.getInstance().getPlayer().setX(x);
		PlayerManager.getInstance().getPlayer().setY(y);
		PlayerManager.getInstance().getPlayer().setZ(z);
		
		camera.setEye_x(x);
		camera.setEye_y(y);
		camera.setEye_z(z);
	}
	
	/**
	 * Calcul de la hauteur par rapport au terrain
	 * @param x
	 * @param z
	 * @return altitude
	 */
	private float getHauteurTerrain(float x, float z) {
		float[][] altTab = ShapeManager.getInstance().getPaysage().getTableauAltitudes();
		x = x + ShapeManager.getInstance().getPaysage().getDecalX();
		if (x > (altTab.length - 1)) x = (altTab.length - 1);
		if (x < 0) x = 0;
		
		z = z + ShapeManager.getInstance().getPaysage().getDecalZ();
		if (z > (altTab[0].length - 1)) z = (altTab[0].length - 1);
		if (z < 0) z = 0;
		
		float alt = ShapeManager.getInstance().getPaysage().getTableauAltitudes()[(int)(x)][(int)z] + 0.5f;
		
		if (alt < (GameConstants.NIVEAU_EAU + 1.0f)) alt = GameConstants.NIVEAU_EAU + 1.0f; 
		
		System.out.println("Altitude = " + alt);
		
		return alt;
	}

	/**
	 * Gestion simultanée de l'altitude de la camera et du joueur
	 * @param monte
	 */
	public void updateAltitudeCameraEtJoueur(boolean monte) {
		// Que si le mode VOL est activé
		if (this.isModeVolActif()) {
			// Variables
			float y = 0.0f;
			
			if (monte) {
				// Le joueur monte
				y = PlayerManager.getInstance().getPlayer().getY() + GameConstants.VITESSE_MONTEE;
			} else {
				// Le joueur descend
				y = PlayerManager.getInstance().getPlayer().getY() - GameConstants.VITESSE_MONTEE;
			}
			
			// Mise a jour de la camera et du joueur
			PlayerManager.getInstance().getPlayer().setY(y);
			camera.setEye_y(y);
		}
	}

	public boolean isModeVolActif() {
		return modeVolActif;
	}

	public void setModeVolActif(boolean modeVolActif) {
		this.modeVolActif = modeVolActif;
	}

	
}
