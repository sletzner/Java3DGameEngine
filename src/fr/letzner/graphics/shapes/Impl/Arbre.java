/**
 * 
 */
package fr.letzner.graphics.shapes.Impl;

import static javax.media.opengl.GL.GL_BLEND;
import static javax.media.opengl.GL.GL_ONE_MINUS_SRC_ALPHA;
import static javax.media.opengl.GL.GL_SRC_ALPHA;
import static javax.media.opengl.GL.GL_TEXTURE_2D;
import static javax.media.opengl.GL.GL_TRIANGLES;
import static javax.media.opengl.GL2GL3.GL_QUADS;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.media.opengl.GL2;

import com.jogamp.opengl.util.texture.Texture;

import fr.letzner.graphics.shapes.AbstractModel3D;
import fr.letzner.graphics.utils.GameConstants;
import fr.letzner.graphics.utils.RandomCoordonnees;
// GL2 constants

/**
 * @author stefan
 *
 */
public class Arbre extends AbstractModel3D {

	private float hauteurArbre = 0.5f;
	private float largeurArbre = 0.2f;
	
	/**
	 * Largeur d'un carré
	 */
	private int nbTypeArbres = 4;
	private int nbArbres = 0;
	
	private List<RandomCoordonnees> listeCoord = new ArrayList<RandomCoordonnees>();
	private float[][] tableauNiveaux;
	
	private int[] indicesTypeArbres;
	
	// Textures
	private String[] textures;
	
	/**
	 * 
	 */
	public Arbre() {
		
	}

	public Arbre(int nbArbres, float[][] tableauNiveaux) {
		
		// Initialisation des coordonnées
		this.nbArbres = nbArbres;
		this.tableauNiveaux = tableauNiveaux;
		this.indicesTypeArbres = new int[nbArbres];
		this.textures = new String[nbTypeArbres];
		
		// Types d'arbres
		for (int i = 0; i < nbArbres; i ++) {
			indicesTypeArbres[i] = new Random().nextInt(textures.length);
		}
		
		// Paysage
		for (int i = 0; i < nbArbres; i++) {
			// Indices aléatoires
			int indice1 = new Random().nextInt(tableauNiveaux.length);
			int indice2 = new Random().nextInt(tableauNiveaux[0].length);
			RandomCoordonnees ran = new RandomCoordonnees();
			ran.setX(indice1);
			ran.setY(indice2);
			
			listeCoord.add(ran);
		}
	}

	@Override
	public void draw(GL2 gl) {
		// Arbres triangles
		gl.glBegin(GL_TRIANGLES);
		
		gl.glColor3f(30.0f / 256.0f, 109.0f / 256.0f, 54.0f / 256.0f);
		gl.glVertex3f(0.0f, hauteurArbre, 0.0f);
		gl.glVertex3f(-largeurArbre, 0.0f, 0.0f);
		gl.glVertex3f(largeurArbre, 0.0f, 0.0f);
		gl.glColor3f(47.0f / 256.0f, 135.0f / 256.0f, 74.0f / 256.0f);
		gl.glVertex3f(0.0f, hauteurArbre, 0.0f);
		gl.glVertex3f(0.0f, 0.0f, -largeurArbre);
		gl.glVertex3f(0.0f, 0.0f, largeurArbre);
		
		gl.glEnd();
	}
	
	public void drawTrees(GL2 gl, Texture[] textures) {
		
		for(int i = 0; i < nbArbres; i++) {
			
			RandomCoordonnees ran = listeCoord.get(i);
			
			int x = ran.getX() - (tableauNiveaux.length / 2);
			int z = ran.getY() - (tableauNiveaux[0].length / 2);

			// Arbres textures
			
			
			textures[indicesTypeArbres[i]].enable(gl);
			textures[indicesTypeArbres[i]].bind(gl);
			
			// Transparence des PNG : Assombrit l'image !!!
			gl.glEnable(GL_BLEND);
			gl.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
			
	        gl.glBegin(GL_QUADS);
	        
	        /*
	         * Ordre des coins
	         * 10
	         * 00
	         * 01
	         * 11
	         * 
	         * */
	        
	        // Dessin des deux faces avec textures
	        gl.glTexCoord2d(1.0, 0.0);
	        gl.glVertex3f((x * GameConstants.LARGEUR_CARRE) + (largeurArbre / 2.0f), tableauNiveaux[ran.getX()][ran.getY()] + hauteurArbre, z * GameConstants.LARGEUR_CARRE);
	        gl.glTexCoord2d(0.0, 0.0);
	        gl.glVertex3f((x * GameConstants.LARGEUR_CARRE) - (largeurArbre / 2.0f), tableauNiveaux[ran.getX()][ran.getY()] + hauteurArbre, z * GameConstants.LARGEUR_CARRE);
	        gl.glTexCoord2d(0.0, 1.0);
	        gl.glVertex3f((x * GameConstants.LARGEUR_CARRE) - (largeurArbre / 2.0f), tableauNiveaux[ran.getX()][ran.getY()], z * GameConstants.LARGEUR_CARRE);
	        gl.glTexCoord2d(1.0, 1.0);
	        gl.glVertex3f((x * GameConstants.LARGEUR_CARRE) + (largeurArbre / 2.0f), tableauNiveaux[ran.getX()][ran.getY()], z * GameConstants.LARGEUR_CARRE);
	        
	        gl.glTexCoord2d(1.0, 0.0);
	        gl.glVertex3f(x * GameConstants.LARGEUR_CARRE, tableauNiveaux[ran.getX()][ran.getY()] + hauteurArbre, (z * GameConstants.LARGEUR_CARRE) + (largeurArbre / 2.0f));
	        gl.glTexCoord2d(0.0, 0.0);
	        gl.glVertex3f(x * GameConstants.LARGEUR_CARRE, tableauNiveaux[ran.getX()][ran.getY()] + hauteurArbre, (z * GameConstants.LARGEUR_CARRE) - (largeurArbre / 2.0f));
	        gl.glTexCoord2d(0.0, 1.0);
	        gl.glVertex3f(x * GameConstants.LARGEUR_CARRE, tableauNiveaux[ran.getX()][ran.getY()], (z * GameConstants.LARGEUR_CARRE) - (largeurArbre / 2.0f));
	        gl.glTexCoord2d(1.0, 1.0);
	        gl.glVertex3f(x * GameConstants.LARGEUR_CARRE, tableauNiveaux[ran.getX()][ran.getY()], (z * GameConstants.LARGEUR_CARRE) + (largeurArbre / 2.0f));
	        
			
			
			gl.glEnd();
			
			textures[indicesTypeArbres[i]].disable(gl);
			
			gl.glDisable(GL_TEXTURE_2D);
			gl.glDisable(GL_BLEND);
		}
	}

	@Override
	public void draw(GL2 gl, Texture texture) {
		// pas utilisé
	}
}


