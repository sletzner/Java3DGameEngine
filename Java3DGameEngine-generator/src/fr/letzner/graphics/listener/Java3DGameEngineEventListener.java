/**
 * 
 */
package fr.letzner.graphics.listener;

import com.jogamp.opengl.util.texture.Texture;
import fr.letzner.graphics.engine.CameraManager;
import fr.letzner.graphics.shapes.Impl.ShapeManager;
import fr.letzner.graphics.textures.TextureManager;
import fr.letzner.graphics.utils.ColorManager;

import com.jogamp.opengl.*;
import com.jogamp.opengl.glu.GLU;
import java.awt.*;

import static com.jogamp.opengl.GL.GL_CULL_FACE;
import static com.jogamp.opengl.GL2.*;
import static com.jogamp.opengl.GL2ES1.GL_PERSPECTIVE_CORRECTION_HINT;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.GL_SMOOTH;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_MODELVIEW;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_PROJECTION;

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
		//CameraManager.getInstance().getCamera().setEye_z(-20.0f);
		
		// Screen size
		this.canvasWidth = gl.getContext().getGLDrawable().getSurfaceWidth();
		this.canvasHeight = gl.getContext().getGLDrawable().getSurfaceHeight();
		
		gl.glClearColor(0.2f, 0.0f, 1.0f, 0.0f); 				// set background (clear) color
		gl.glClearDepth(1.0f); 									// set clear depth value to farthest
		gl.glEnable(GL_DEPTH_TEST); 							// Active le test de profondeur
		gl.glDisable(GL_CULL_FACE);
		gl.glDepthFunc(GL_LEQUAL); 								// the type of depth test to do
		gl.glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST); 	// best perspective correction
		gl.glShadeModel(GL_SMOOTH); 							// blends colors nicely, and smoothes out lighting

		// Autres initialisations ici
		textureManager = new TextureManager(glp);
		
		//gl.glEnable(GL2.GL_AUTO_NORMAL); 
		//gl.glEnable(GL2.GL_NORMALIZE); 
		//gl.glShadeModel(GL2.GL_SMOOTH); 
		//gl.glDepthFunc(GL2.GL_LESS);
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

		// Enable the model-view transform
		gl.glMatrixMode(GL_MODELVIEW);
		gl.glLoadIdentity(); // reset
		
		// Textures
		gl.glEnable(GL_TEXTURE_2D);
		
		CameraManager.getInstance().setCamera(gl, glu, canvasWidth, canvasHeight);
		
	    // Rendu du paysage
		renderScene(drawable);
		
		gl.glFlush();
	}
	
	public void renderScene(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		
		// Lumiere
		//AmbianceManager.getInstance().genererLumiere(gl);
		
		// Generate Paysage3D
		ShapeManager.getInstance().getPaysage().draw(gl, 
				textureManager.getTextureSol(), 
				textureManager.getTextureEau());
		
		// Generation du ciel
		ShapeManager.getInstance().getCiel().draw(gl, textureManager.getTextureCiel());
		
		// Generation des arbres
		ShapeManager.getInstance().getArbre().drawTrees(gl, textureManager.getTexturesArbres());
		
		
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
