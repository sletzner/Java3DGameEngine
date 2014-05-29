/**
 * 
 */
package fr.letzner.graphics.shapes.Impl;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.util.texture.Texture;

import fr.letzner.graphics.shapes.AbstractModel3D;
import static javax.media.opengl.GL2.*;
import static javax.media.opengl.GL2GL3.*;

/**
 * @author stefan
 *
 */
public class Ciel extends AbstractModel3D {

	

	@Override
	public void draw(GL2 gl, Texture textureCiel) {
		GLU glu = new GLU();
		textureCiel.enable(gl);
		textureCiel.bind(gl);

//		gl.glTexGeni(GL_S, GL_TEXTURE_GEN_MODE, GL_OBJECT_LINEAR);
//		gl.glTexGeni(GL_T, GL_TEXTURE_GEN_MODE, GL_SPHERE_MAP);
//		
//		glut.glutSolidSphere(256, 20, 20);
		
		
		
		
		
		// Set material properties.
        float[] rgba = {1f, 1f, 1f};
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT, rgba, 0);
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, rgba, 0);
        gl.glMaterialf(GL2.GL_FRONT, GL2.GL_SHININESS, 0.5f);

        // Draw sphere.
        GLUquadric earth = glu.gluNewQuadric();
        glu.gluQuadricTexture(earth, true);
		
		
		
		
		
		
		textureCiel.disable(gl);
	}

	@Override
	public void draw(GL2 gl, Texture sol, Texture eau) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void draw(GL2 gl) {
		// TODO Auto-generated method stub

	}
}
