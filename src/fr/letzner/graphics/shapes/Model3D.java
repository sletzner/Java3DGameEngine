/**
 * 
 */
package fr.letzner.graphics.shapes;

import javax.media.opengl.GL2;

/**
 * @author stefan
 *
 */
public interface Model3D {
	
	/**
	 * Creation du model et chargement de facettes
	 */
	public void draw(GL2 gl);
	
}
