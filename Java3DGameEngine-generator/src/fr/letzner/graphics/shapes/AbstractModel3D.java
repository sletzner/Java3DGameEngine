/**
 * 
 */
package fr.letzner.graphics.shapes;

import com.jogamp.opengl.GL2;

import com.jogamp.opengl.util.texture.Texture;

/**
 * @author stefan
 *
 */
public abstract class AbstractModel3D {
	
	protected GL2 terrain = null;
	
	/**
	 * Creation du model et chargement de facettes
	 */
	public abstract void draw(GL2 gl);
	
	/**
	 * Creation du model et chargement de facettes avec texture
	 */
	public abstract void draw(GL2 gl, Texture texture);
	
	/**
	 * Creation du model et chargement de facettes avec 2 textures
	 */
	public abstract void draw(GL2 gl, Texture sol, Texture eau);
	
}
