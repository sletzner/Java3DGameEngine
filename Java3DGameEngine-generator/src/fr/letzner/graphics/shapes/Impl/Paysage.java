/**
 * 
 */
package fr.letzner.graphics.shapes.Impl;

import static javax.media.opengl.GL2.GL_LINEAR;
import static javax.media.opengl.GL2.GL_REPEAT;
import static javax.media.opengl.GL2.GL_TEXTURE_2D;
import static javax.media.opengl.GL2.GL_TEXTURE_MAG_FILTER;
import static javax.media.opengl.GL2.GL_TEXTURE_MIN_FILTER;
import static javax.media.opengl.GL2.GL_TEXTURE_WRAP_S;
import static javax.media.opengl.GL2.GL_TEXTURE_WRAP_T;
import static javax.media.opengl.GL2.GL_TRIANGLES;
import static javax.media.opengl.GL2GL3.GL_QUADS;

import javax.media.opengl.GL2;

import com.jogamp.opengl.util.texture.Texture;

import fr.letzner.graphics.images.ImageManager;
import fr.letzner.graphics.images.ImageManagerException;
import fr.letzner.graphics.shapes.AbstractModel3D;
import fr.letzner.graphics.utils.ColorManager;
import fr.letzner.graphics.utils.GameConstants;

/**
 * @author stefan
 *
 */
public class Paysage extends AbstractModel3D {

	private final float RATIO_TEXTURE = 5f;
	private ImageManager imageService = null;
	private int nbPoints = 0;
	private float[][] tableauAltitudes;
	private ColorManager colorManager = new ColorManager();
	private Texture textureSol = null;
	private Texture textureEau = null;
	private int decalX = 0;
	private int decalZ = 0;

	/**
	 * 
	 */
	public Paysage(String imagePath) {
		try {
			// Chargement de l'image
			imageService = new ImageManager(imagePath);
			System.out.println("Chargement image " + imagePath + " : OK");
			
			// Calcul du nombre de points
			nbPoints = (imageService.getDimension().width - 1)
					* (imageService.getDimension().height - 1) * 2 * 3;
			
			// Chargement du tableau des altitudes
			tableauAltitudes = new float[imageService.getDimension().width][imageService.getDimension().height];
			
			// Centrage du paysage
			decalX = imageService.getDimension().width / 2;
			decalZ = imageService.getDimension().height / 2;
			
			System.out.println("Nombre de points : " + nbPoints);
		} catch (ImageManagerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Generation du tableau de facettes
	 */
	@Override
	public void draw(GL2 gl) {
		
		// Affectation du contexte OpenGL
		terrain = gl;
		
		genererPaysage(terrain);
		
		genererLacs(terrain);
	}
	
	private void genererLacs(GL2 gl) {
		// Texturage
		textureEau.enable(gl);
		textureEau.bind(gl);
		
		gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		
		// Construction avec un carre
		terrain.glBegin(GL_QUADS);
		
		// Dessin des deux faces avec textures
        setTextureSommet(gl, 1.0f * RATIO_TEXTURE, 0.0f * RATIO_TEXTURE);
        gl.glVertex3f(-decalX * GameConstants.LARGEUR_CARRE, GameConstants.NIVEAU_EAU, -decalZ * GameConstants.LARGEUR_CARRE);
        setTextureSommet(gl, 0.0f * RATIO_TEXTURE, 0.0f * RATIO_TEXTURE);
        gl.glVertex3f(decalX * GameConstants.LARGEUR_CARRE, GameConstants.NIVEAU_EAU, -decalZ * GameConstants.LARGEUR_CARRE);
        setTextureSommet(gl, 0.0f * RATIO_TEXTURE, 1.0f * RATIO_TEXTURE);
        gl.glVertex3f(decalX * GameConstants.LARGEUR_CARRE, GameConstants.NIVEAU_EAU, decalZ * GameConstants.LARGEUR_CARRE);
        setTextureSommet(gl, 1.0f * RATIO_TEXTURE, 1.0f * RATIO_TEXTURE);
        gl.glVertex3f(-decalX * GameConstants.LARGEUR_CARRE, GameConstants.NIVEAU_EAU, decalZ * GameConstants.LARGEUR_CARRE);
		
		
        textureEau.disable(gl);
	}

	private void genererPaysage(GL2 gl) {
		// Texturage
		textureSol.enable(gl);
		textureSol.bind(gl);
		
		gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		
		// Construction avec des triangles
		terrain.glBegin(GL_TRIANGLES);

		// Recuperation tableau couleurs
		int[] couleurs = imageService.getImageColorsArray();
		
		float x1, x2, x3, x4, x5, x6 = 0.0f;
		float y1, y2, y3, y4, y5, y6 = 0.0f;
		float z1, z2, z3, z4, z5, z6 = 0.0f;
		
		// Construction des facettes
		for (int x = 0; x < imageService.getDimension().width - 1; x++) {
			for (int z = 0; z < imageService.getDimension().height - 1; z++) {
				// Triangle 1
				int indiceTableau1 = (x * imageService.getDimension().width) + z;
				int indiceTableau2 = (x * imageService.getDimension().width) + (z + 1);
				int indiceTableau3 = ((x + 1) * imageService.getDimension().width) + (z + 1);

				float valeur1 = getValeurAltitude(couleurs[indiceTableau1]);
				float valeur2 = getValeurAltitude(couleurs[indiceTableau2]);
				float valeur3 = getValeurAltitude(couleurs[indiceTableau3]);
				
				setColorSommet((int)valeur1);
				//setTextureSommet(gl, (float)x * RATIO_TEXTURE, (float)z * RATIO_TEXTURE);
				setTextureSommet(gl, (float)x / (float)imageService.getDimension().width, (float)z / (float)imageService.getDimension().height);
				
				
				x1 = (x - decalX) * GameConstants.LARGEUR_CARRE;
				y1 = valeur1 / GameConstants.COEFFICIENT_REDUCTEUR;
				z1 = (z - decalZ) * GameConstants.LARGEUR_CARRE;
				terrain.glNormal3f(0.0f, 1.0f, 0.0f);
				terrain.glVertex3f(x1, y1, z1);

				setColorSommet((int)valeur2);
				setTextureSommet(gl, (float)x / (float)imageService.getDimension().width, (float)(z + 1) / (float)imageService.getDimension().height);
				x2 = (x - decalX) * GameConstants.LARGEUR_CARRE;
				y2 = valeur2 / GameConstants.COEFFICIENT_REDUCTEUR;
				z2 = (z + 1 - decalZ) * GameConstants.LARGEUR_CARRE;
				terrain.glVertex3f(x2, y2, z2);

				setColorSommet((int)valeur3);
				setTextureSommet(gl, (float)(x + 1) / (float)imageService.getDimension().width, (float)(z + 1) / (float)imageService.getDimension().height);
				x3 = (x + 1 - decalX) * GameConstants.LARGEUR_CARRE;
				y3 = valeur3 / GameConstants.COEFFICIENT_REDUCTEUR;
				z3 = (z + 1 - decalZ) * GameConstants.LARGEUR_CARRE;
				terrain.glVertex3f(x3, y3, z3);

				// Triangle 2
				int indiceTableau4 = (x * imageService.getDimension().width) + z;
				int indiceTableau5 = ((x + 1) * imageService.getDimension().width) + (z + 1);
				int indiceTableau6 = ((x + 1) * imageService.getDimension().width) + z;

				float valeur4 = getValeurAltitude(couleurs[indiceTableau4]);
				float valeur5 = getValeurAltitude(couleurs[indiceTableau5]);
				float valeur6 = getValeurAltitude(couleurs[indiceTableau6]);

				setColorSommet((int)valeur4);
				setTextureSommet(gl, (float)x / (float)imageService.getDimension().width, (float)z / (float)imageService.getDimension().height);
				x4 = (x - decalX) * GameConstants.LARGEUR_CARRE;
				y4 = valeur4 / GameConstants.COEFFICIENT_REDUCTEUR;
				z4 = (z - decalZ) * GameConstants.LARGEUR_CARRE;
				terrain.glNormal3f(0.0f, 1.0f, 0.0f);
				terrain.glVertex3f(x4, y4, z4);

				setColorSommet((int)valeur5);
				//setTextureSommet(gl, (float)(x + 1) * RATIO_TEXTURE, (float)(z + 1) * RATIO_TEXTURE);
				setTextureSommet(gl, (float)(x + 1) / (float)imageService.getDimension().width, (float)(z + 1) / (float)imageService.getDimension().height);
				
				x5 = (x + 1 - decalX) * GameConstants.LARGEUR_CARRE;
				y5 = valeur5 / GameConstants.COEFFICIENT_REDUCTEUR;
				z5 = (z + 1 - decalZ) * GameConstants.LARGEUR_CARRE;
				terrain.glVertex3f(x5, y5, z5);

				setColorSommet((int)valeur6);
				setTextureSommet(gl, (float)(x + 1) / (float)imageService.getDimension().width, (float)z / (float)imageService.getDimension().height);
				x6 = (x + 1 - decalX) * GameConstants.LARGEUR_CARRE;
				y6 = valeur6 / GameConstants.COEFFICIENT_REDUCTEUR;
				z6 = (z - decalZ) * GameConstants.LARGEUR_CARRE;
				terrain.glVertex3f(x6, y6, z6);
				
				// On memorise l'altitude du point courrant
				tableauAltitudes[x][z] = valeur1 / GameConstants.COEFFICIENT_REDUCTEUR;
			}
		}
		terrain.glEnd();
		
		textureSol.disable(gl);
	}
	
	/**
	 * Attribution de la couleur d'un sommet
	 * @param valeur
	 */
	private void setColorSommet(int valeur) {
		terrain.glColor3ub((byte)colorManager.getColorFromLUT(valeur).getRed(),
				(byte)colorManager.getColorFromLUT(valeur).getGreen(),
				(byte)colorManager.getColorFromLUT(valeur).getBlue());
	}
	
	/**
	 * Coordonnées de la texture
	 * @param gl
	 * @param x
	 * @param y
	 */
	private void setTextureSommet(GL2 gl, float x, float y) {
		gl.glTexCoord2f(x, y);
	}

	/**
	 * Calcule les altitudes
	 * 
	 * @param couleur
	 * @return
	 */
	private float getValeurAltitude(int couleur) {
		// Transforme les millions de couleurs en 256 puis 1.0f
		float valeur = 256.0f - /* Inversion des valeurs */
		(Float.valueOf(Math.abs(couleur)) / 256.0f / 256.0f); /*
																 * Passage de
																 * millions de
																 * couleurs en
																 * 256 NG
																 */
		return valeur;
	}

	/**
	 * Retourne le paysage
	 * 
	 * @return GL2
	 */
	public GL2 draw() {
		return terrain;
	}

	public float[][] getTableauAltitudes() {
		return tableauAltitudes;
	}

	@Override
	public void draw(GL2 gl, Texture texture) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(GL2 gl, Texture sol, Texture eau) {
		this.textureSol = sol;
		this.textureEau = eau;
		this.draw(gl);
	}

	/**
	 * @return the decalX
	 */
	public int getDecalX() {
		return decalX;
	}

	/**
	 * @return the decalZ
	 */
	public int getDecalZ() {
		return decalZ;
	}
	
	
}
