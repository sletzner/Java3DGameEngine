/**
 * 
 */
package fr.letzner.graphics.engine;

import com.jogamp.opengl.GL2;

import static com.jogamp.opengl.fixedfunc.GLLightingFunc.*;

/**
 * @author stefan
 *
 */
public class AmbianceManager {
	
	private AmbianceManager(){}
	
	private static AmbianceManager instance = null;
	
	public static AmbianceManager getInstance() {
		if (instance == null){
			instance = new AmbianceManager();
		}
		
		return instance;
	}
	
	private int fogMode;
	
	// Lumiere
	float[] mat_ambient = {0.2f , 0.2f , 0.2f , 1.0f};
    float[] mat_diffuse = { 1.0f , 1.0f , 1.0f , 1.0f };
    float[] mat_specular = { 1.0f , 1.0f , 1.0f , 1.0f };
    float mat_shininess = 110.0f ;
    float[] light_position = { 0f , 0f , 300f , 0.0f } ;
    float[] model_ambient = { 0.2f , 0.2f , 0.2f , 1.0f };
	
	public void genererLumiere(GL2 gl)
	{
//		gl.glEnable(GL_LIGHTING);	// Active l'éclairage
//	    gl.glEnable(GL_LIGHT0);	// Allume la lumière n°1
//		gl.glMaterialfv(GL2 .GL_FRONT, GL2 .GL_AMBIENT, mat_ambient , 0 ) ;
//	    gl.glMaterialfv(GL2 .GL_FRONT, GL2 . GL_DIFFUSE, mat_diffuse , 0 ) ;
//	    gl.glMaterialfv(GL2 .GL_FRONT, GL2 .GL_SPECULAR, mat_specular , 0 ) ;
//	    gl.glMaterialf(GL2 .GL_FRONT, GL2 . GL_SHININESS , mat_shininess ) ;
//	    gl.glLightfv(GL2 . GL_LIGHT0, GL2 . GL_POSITION, light_position , 0 ) ;
//	    gl.glLightModelfv(GL2 .GL_LIGHT_MODEL_AMBIENT, model_ambient , 0 ) ;
		
		float[] lightPos = new float[4];
	    lightPos[0] = 50005;
	    lightPos[1] = 30000;
	    lightPos[2] = 50000;
	    lightPos[3] = 1;
	    gl.glEnable(GL_LIGHTING);
	    gl.glEnable(GL_LIGHT0);
	    float[] noAmbient ={ 0.1f, 0.1f, 0.1f, 1f }; // low ambient light
	    float[] spec =    { 1f, 0.6f, 0f, 1f }; // low ambient light
	    float[] diffuse ={ 1f, 1f, 1f, 1f };
	    // properties of the light
	    gl.glLightfv(GL_LIGHT0, GL_AMBIENT, noAmbient, 0);
	    gl.glLightfv(GL_LIGHT0, GL_SPECULAR, spec, 0);
	    gl.glLightfv(GL_LIGHT0, GL_DIFFUSE, diffuse, 0);
	    gl.glLightfv(GL_LIGHT0, GL_POSITION, lightPos, 0);
	}
	
	private void genererBrouillard(GL2 gl) {
		fogMode = GL2.GL_EXP;
		gl.glFogf(GL2.GL_FOG_START, 5.0f);
	    gl.glFogf(GL2.GL_FOG_END, 50.0f);
	    gl.glFogi(GL2.GL_FOG_MODE, fogMode);
	}
}
