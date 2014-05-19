/**
 * 
 */
package fr.letzner.graphics.listener;

import static javax.media.opengl.GL.GL_DEPTH_TEST;
import static javax.media.opengl.GL.GL_LEQUAL;
import static javax.media.opengl.GL.GL_NICEST;
import static javax.media.opengl.GL2ES1.GL_PERSPECTIVE_CORRECTION_HINT;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_SMOOTH;
import static javax.media.opengl.fixedfunc.GLMatrixFunc.GL_MODELVIEW;
import static javax.media.opengl.fixedfunc.GLMatrixFunc.GL_PROJECTION;

import java.awt.Point;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;
import javax.media.opengl.glu.GLU;

import com.jogamp.opengl.util.texture.Texture;

import fr.letzner.graphics.engine.CameraManager;
import fr.letzner.graphics.shapes.Impl.ShapeManager;
import fr.letzner.graphics.utils.ColorManager;
import fr.letzner.textures.TextureManager;

/**
 * @author stefan
 *
 */
public class Java3DGameEngineEventListener implements GLEventListener {

	private GLU glu = null;
	private TextureManager textureManager = null;
	private GLProfile glp = null;
	private Point center = null;
	private Texture[] textures = null;
	private float canvasWidth = 0.0f;
	private float canvasHeight = 0.0f;
	private ColorManager colorManager = new ColorManager();
	
	public Java3DGameEngineEventListener() {
		glp = GLProfile.getDefault();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2(); 					// get the OpenGL graphics context
		glu = new GLU(); 										// get GL Utilities
		
		// Camera position initiale
		CameraManager.getInstance().getCamera().setEye_z(-20.0f);
		
		// Screen size
		this.canvasWidth = gl.getContext().getGLDrawable().getWidth();
		this.canvasHeight = gl.getContext().getGLDrawable().getHeight();
		
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f); 				// set background (clear) color
		gl.glClearDepth(1.0f); 									// set clear depth value to farthest
		gl.glEnable(GL_DEPTH_TEST); 							// enables depth testing
		gl.glDepthFunc(GL_LEQUAL); 								// the type of depth test to do
		gl.glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST); 	// best perspective correction
		gl.glShadeModel(GL_SMOOTH); 							// blends colors nicely, and smoothes out lighting

		// Autres initialisations ici
		textureManager = new TextureManager(glp);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dispose(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub

	}
	
	

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		
		// Setup perspective projection, with aspect ratio matches viewport
		gl.glMatrixMode(GL_PROJECTION); // choose projection matrix
		gl.glLoadIdentity(); // reset projection matrix
		glu.gluPerspective(45.0f, 1.5f, 0.1, 100.0); // fovy, aspect, zNear,
														// zFar
		// Enable the model-view transform
		gl.glMatrixMode(GL_MODELVIEW);
		gl.glLoadIdentity(); // reset
		
		//glu.gluLookAt(0, 0, -6.0, 0, 0, 0, 0, 1, 0);
		
		CameraManager.getInstance().setCamera(gl, glu, canvasWidth, canvasHeight);
		update(drawable);
		
		//glu.gluLookAt(0, 0, -6.0, 0, 0, 0, 0, 1, 0);
		//colorManager.ShowLUTPalette();
		//colorManager.drawPalette(gl);
		renderTerrain(drawable);
		//colorManager.drawPalette(gl);
		
		gl.glFlush();
	}
	
	public void renderTerrain(GLAutoDrawable drawable) {
		// Generate Paysage3D
		GL2 gl = drawable.getGL().getGL2();
		ShapeManager.getInstance().getPaysage().draw(gl);
		
		// Generation des arbres
		textures = textureManager.getTexturesArbres();
		ShapeManager.getInstance().getArbre().drawTrees(gl, textures);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width,
			int height) {
		GL2 gl = drawable.getGL().getGL2(); // get the OpenGL 2 graphics context

		if (height == 0)
			height = 1; // prevent divide by zero
		float aspect = (float) width / height;

		// Set the view port (display area) to cover the entire window
		gl.glViewport(0, 0, width, height);

		// Setup perspective projection, with aspect ratio matches viewport
		gl.glMatrixMode(GL_PROJECTION); // choose projection matrix
		gl.glLoadIdentity(); // reset projection matrix
		glu.gluPerspective(45.0, aspect, 0.1, 100.0); // fovy, aspect, zNear,
														// zFar

		// Enable the model-view transform
		gl.glMatrixMode(GL_MODELVIEW);
		gl.glLoadIdentity(); // reset
		
		// Recalcule le centre de la scene
		center = new Point(x + width / 2, y + height / 2);
	}
	
	private void update(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
		
	}
}
