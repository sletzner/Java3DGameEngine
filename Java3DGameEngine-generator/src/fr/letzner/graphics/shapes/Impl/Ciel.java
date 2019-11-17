/**
 * 
 */
package fr.letzner.graphics.shapes.Impl;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.util.texture.Texture;

import static com.jogamp.opengl.GL.GL_BLEND;
import static com.jogamp.opengl.GL.GL_ONE_MINUS_SRC_ALPHA;
import static com.jogamp.opengl.GL.GL_SRC_ALPHA;
import static com.jogamp.opengl.GL2.*;
import static com.jogamp.opengl.GL2GL3.*;

/*
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

 */



import fr.letzner.graphics.shapes.AbstractModel3D;

/*
import static com.jogamp.opengl.GL.GL_BLEND;
import static com.jogamp.opengl.GL.GL_ONE_MINUS_SRC_ALPHA;
import static com.jogamp.opengl.GL.GL_SRC_ALPHA;
import static com.jogamp.opengl.GL2.*;
import static com.jogamp.opengl.GL2GL3.*;
*/

 
/**
 * @author stefan
 *
 */
public class Ciel extends AbstractModel3D {

	

	@Override
	public void draw(GL2 gl, Texture textureCiel) {
		GLU glu = new GLU();
		GLUT glut = new GLUT();
		
		gl.glEnable(GL_BLEND);
		//gl.glEnable(GL_SMOOTH);
		//gl.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		gl.glTexGeni(GL_S, GL_TEXTURE_GEN_MODE, GL_SMOOTH);
		gl.glTexGeni(GL_T, GL_TEXTURE_GEN_MODE, GL_SPHERE_MAP);

//		gl.glEnable(GL_TEXTURE_GEN_S);
//		gl.glEnable(GL_TEXTURE_GEN_T);
//		gl.glEnable(GL_TEXTURE_GEN_R);
//		
//		gl.glTexGeni(GL_S, GL_TEXTURE_GEN_MODE, GL_NORMAL_MAP);
//		gl.glTexGeni(GL_T, GL_TEXTURE_GEN_MODE, GL_NORMAL_MAP);
//		gl.glTexGeni(GL_R, GL_TEXTURE_GEN_MODE, GL_NORMAL_MAP);
		
		float[] rgba = {1f, 1f, 1f};
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT, rgba, 0);
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, rgba, 0);
        gl.glMaterialf(GL2.GL_FRONT, GL2.GL_SHININESS, 0.0f);
		
		textureCiel.enable(gl);
		textureCiel.bind(gl);
		
        glut.glutSolidSphere(256, 20, 20);
		
		textureCiel.disable(gl);
		
		gl.glDisable(GL_TEXTURE_GEN_S);
		gl.glDisable(GL_TEXTURE_GEN_T);
		gl.glDisable(GL_TEXTURE_GEN_R);
		
		gl.glDisable(GL_BLEND);
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
