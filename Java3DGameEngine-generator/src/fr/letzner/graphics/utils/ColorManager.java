/**
 * 
 */
package fr.letzner.graphics.utils;

import javax.media.opengl.GL2;
import static javax.media.opengl.GL.GL_LINES;

/**
 * @author stefan
 *
 */
public class ColorManager {
	
	private ColorRGB[] paletteInitiale = null;
	private ColorRGB[] paletteLUT = null;
	private final int nbCouleurs = 4;
	private final int nbCouleursPalette = 256;
	private final int etapesIntermediaires = nbCouleursPalette / (nbCouleurs-1);
	
	public ColorManager() {
		initialiserLut();
	}

	private void initialiserLut() {
		// Initialisation de la palette de couleurs
		paletteInitiale = new ColorRGB[nbCouleurs];
		
		// Creation de couleurs de base de la palette
		ColorRGB bleu = new ColorRGB(0, 94, 255);
		ColorRGB vert = new ColorRGB(30, 109, 54);
		ColorRGB marron = new ColorRGB(131, 56, 0);
		ColorRGB gris = new ColorRGB(128, 128, 128);
		ColorRGB blanc = new ColorRGB(255, 255, 255);
		
		paletteInitiale[0] = bleu;
		//paletteInitiale[1] = vert;
		paletteInitiale[1] = vert;
		//paletteInitiale[2] = marron;
		//paletteInitiale[2] = gris;
		paletteInitiale[2] = blanc;
		paletteInitiale[3] = blanc;
		
		creerPaletteLUT();
		//creerPaletteLUT_NB();
	}
	
	private void creerPaletteLUT() {
		// Calcul des etapes
		// Attention la dernière couleur ne compte pas
		int reste = nbCouleursPalette % (nbCouleurs - 1);
		int etapes = (nbCouleursPalette - reste) / (nbCouleurs - 1);
		int red, blue, green;
		
		paletteLUT = new ColorRGB[nbCouleursPalette];
		
		for (int i = 0; i < nbCouleurs-1; i++) {
			// Premiere couleur donnee comme etape
			int index = i * etapes;
			
			paletteLUT[index] = paletteInitiale[i];
			
			// Plage entre les deux valeurs a venir
			float plageR =  paletteInitiale[i+1].getRed() - paletteInitiale[i].getRed();
			if (plageR < 0)
				plageR =  paletteInitiale[i].getRed() - paletteInitiale[i+1].getRed();
			plageR = plageR / etapes;
			
			float plageG =  paletteInitiale[i+1].getGreen() - paletteInitiale[i].getGreen();
			if (plageG < 0)
				plageG =  paletteInitiale[i].getGreen() - paletteInitiale[i+1].getGreen();
			plageG = plageG / etapes;

			float plageB =  paletteInitiale[i+1].getBlue() - paletteInitiale[i].getBlue();
			if (plageB < 0)
				plageB =  paletteInitiale[i].getBlue() - paletteInitiale[i+1].getBlue();
			plageB = plageB / etapes;
			
			// Creation des couleurs indexées
			for (int j = 1; j < etapesIntermediaires; j++) {
				ColorRGB couleur = new ColorRGB();
				
				red = paletteInitiale[i].getRed();
				if (paletteInitiale[i+1].getRed() > red){
					couleur.setRed((int)(red + (plageR * j)));
				} else {
					couleur.setRed((int)(red - (plageR * j)));
				}
				
				green = paletteInitiale[i].getGreen();
				if (paletteInitiale[i+1].getGreen() > green){
					couleur.setGreen((int)(green + (plageG * j)));
				} else {
					couleur.setGreen((int)(green - (plageG * j)));
				}
				
				blue = paletteInitiale[i].getBlue();
				if (paletteInitiale[i+1].getBlue() > blue){
					couleur.setBlue((int)(blue + (plageB * j)));
				} else {
					couleur.setBlue((int)(blue - (plageB * j)));
				}
				
				paletteLUT[index + j] = couleur;
			}
			
			// Dernière couleur
			paletteLUT[nbCouleursPalette - 1] = paletteInitiale[nbCouleurs-1];
		}
	}
	
	private void creerPaletteLUT_NB() {
		// Calcul des etapes
		
		paletteLUT = new ColorRGB[nbCouleursPalette];
		
		for (int i = 0; i < nbCouleursPalette; i++) {
			ColorRGB couleur = new ColorRGB((byte)i, (byte)i, (byte)i);
			paletteLUT[i] = couleur;
		}
	}

	// Retourne la couleur correspondante dans la palette LUT
	public ColorRGB getColorFromLUT(int greyScale) {
		return paletteLUT[greyScale];
	}
	
	public void ShowLUTPalette() {
		System.out.println("Palette LUT");
		for(int i = 0; i < paletteLUT.length; i++) {
			if (paletteLUT[i] != null)
				System.out.println(i + "->" + paletteLUT[i].getRed() + "-" + paletteLUT[i].getGreen() + "-" + paletteLUT[i].getBlue());
		}
		System.out.println("Fin palette");
	}
	
	public void drawPalette(GL2 gl) {
		for(int i = 0; i < paletteLUT.length; i++){
			gl.glBegin(GL_LINES);
				gl.glColor3ub((byte)paletteLUT[i].getRed(), (byte)paletteLUT[i].getGreen(), (byte)paletteLUT[i].getBlue());
				gl.glVertex3f(-2, i * 0.01f, 0);
				gl.glVertex3f(2, i * 0.01f, 0);
			gl.glEnd();
		}
	}
}
