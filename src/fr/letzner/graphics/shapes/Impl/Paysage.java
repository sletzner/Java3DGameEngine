/**
 * 
 */
package fr.letzner.graphics.shapes.Impl;

import static javax.media.opengl.GL.GL_TRIANGLES;

import javax.media.opengl.GL2;

import fr.letzner.graphics.images.ImageManager;
import fr.letzner.graphics.images.ImageManagerException;
import fr.letzner.graphics.shapes.Model3D;
import fr.letzner.graphics.utils.ColorManager;

/**
 * @author stefan
 *
 */
public class Paysage implements Model3D {

	private GL2 terrain = null;
	private ImageManager imageService = null;
	private int nbPoints = 0;
	private float[][] tableauAltitudes;
	private ColorManager colorManager = new ColorManager();

	/**
	 * Largeur d'un carré
	 */
	private float largeurCarre = 0.1f;
	
	/**
	 * Hauteur maxi
	 */
	private float hauteurCarre = 0.1f;

	

	/**
	 * 
	 */
	public Paysage(String imagePath) {
		try {
			// Chargement de l'image
			imageService = new ImageManager(imagePath);
			System.out.println("Chargement image " + imagePath + " : OK");
			
			nbPoints = (imageService.getDimension().width - 1)
					* (imageService.getDimension().height - 1) * 2 * 3;
			
			tableauAltitudes = new float[imageService.getDimension().width][imageService.getDimension().height];
			
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
		
		// Construction avec des triangles
		terrain.glBegin(GL_TRIANGLES);

		//System.out.print("Generation des facettes :");

		int decalX = imageService.getDimension().width / 2;
		int decalZ = imageService.getDimension().height / 2;

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
				
				terrain.glColor3i(colorManager.getColorFromLUT((int)valeur1).getRed(),
						colorManager.getColorFromLUT((int)valeur1).getGreen(),
						colorManager.getColorFromLUT((int)valeur1).getBlue());
				x1 = (x - decalX) * largeurCarre;
				y1 = valeur1;
				z1 = (z - decalZ) * largeurCarre;
				terrain.glNormal3f(0.0f, 1.0f, 0.0f);
				terrain.glVertex3f(x1, y1, z1);

				//System.out.println("X=" + (x - decalX) * largeurCarre + ", Y=" + valeur1 + ", Z=" + (z - decalZ) * largeurCarre);
				
				terrain.glColor3i(colorManager.getColorFromLUT((int)valeur2).getRed(),
						colorManager.getColorFromLUT((int)valeur2).getGreen(),
						colorManager.getColorFromLUT((int)valeur2).getBlue());
				x2 = (x - decalX) * largeurCarre;
				y2 = valeur2;
				z2 = (z + 1 - decalZ) * largeurCarre;
				terrain.glVertex3f(x2, y2, z2);

				terrain.glColor3i(colorManager.getColorFromLUT((int)valeur3).getRed(),
						colorManager.getColorFromLUT((int)valeur3).getGreen(),
						colorManager.getColorFromLUT((int)valeur3).getBlue());
				x3 = (x + 1 - decalX) * largeurCarre;
				y3 = valeur3;
				z3 = (z + 1 - decalZ) * largeurCarre;
				terrain.glVertex3f(x3, y3, z3);

				// Triangle 2
				int indiceTableau4 = (x * imageService.getDimension().width) + z;
				int indiceTableau5 = ((x + 1) * imageService.getDimension().width) + (z + 1);
				int indiceTableau6 = ((x + 1) * imageService.getDimension().width) + z;

				float valeur4 = getValeurAltitude(couleurs[indiceTableau4]);
				float valeur5 = getValeurAltitude(couleurs[indiceTableau5]);
				float valeur6 = getValeurAltitude(couleurs[indiceTableau6]);

				terrain.glColor3i(colorManager.getColorFromLUT((int)valeur4).getRed(),
						colorManager.getColorFromLUT((int)valeur4).getGreen(),
						colorManager.getColorFromLUT((int)valeur4).getBlue());
				terrain.glNormal3f(0.0f, 1.0f, 0.0f);
				x4 = (x - decalX) * largeurCarre;
				y4 = valeur4;
				z4 = (z - decalZ) * largeurCarre;
				terrain.glVertex3f(x4, y4, z4);

				terrain.glColor3i(colorManager.getColorFromLUT((int)valeur5).getRed(),
						colorManager.getColorFromLUT((int)valeur5).getGreen(),
						colorManager.getColorFromLUT((int)valeur5).getBlue());
				x5 = (x + 1 - decalX) * largeurCarre;
				y5 = valeur5;
				z5 = (z + 1 - decalZ) * largeurCarre;
				terrain.glVertex3f(x5, y5, z5);

				terrain.glColor3i(colorManager.getColorFromLUT((int)valeur6).getRed(),
						colorManager.getColorFromLUT((int)valeur6).getGreen(),
						colorManager.getColorFromLUT((int)valeur6).getBlue());
				x6 = (x + 1 - decalX) * largeurCarre;
				y6 = valeur6;
				z6 = (z - decalZ) * largeurCarre;
				terrain.glVertex3f(x6, y6, z6);
				
//				System.out.println("Traingle A -> X1=" + x1 + ", Y1=" + y1 + ", Z1=" + z1 + " - X2=" + x2 + ", Y2=" + y2 + ", Z2=" + z2 + " - X3=" + x3 + ", Y3=" + y3 + ", Z3=" + z3);
//				System.out.println("Traingle B -> X4=" + x4 + ", Y4=" + y4 + ", Z4=" + z4 + " - X5=" + x5 + ", Y5=" + y5 + ", Z5=" + z5 + " - X6=" + x6 + ", Y6=" + y6 + ", Z6=" + z6);
				
				tableauAltitudes[x][z] = valeur1;
			}
		}

		terrain.glEnd();
	}

	/**
	 * Calcule les altitudes
	 * 
	 * @param couleur
	 * @return
	 */
	private float getValeurAltitude(int couleur) {
		// Transforme les millions de couleurs en 256 puis 1.0f
		float valeur = //(256.0f /* Inversion des valeurs */
		(Float.valueOf(Math.abs(couleur)) / 256.0f / 256.0f); /*
																 * Passage de
																 * millions de
																 * couleurs en
																 * 256 NG
																 */
		valeur = valeur * hauteurCarre; /* Echelle du modele 3D */

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
	
	
}
