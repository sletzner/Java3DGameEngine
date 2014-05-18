/**
 * 
 */
package fr.letzner.graphics.utils;

/**
 * @author stefan
 *
 */
public class ColorManager {
	
	private ColorRGB[] paletteInitiale = null;
	private ColorRGB[] paletteLUT = null;
	private final int nbCouleurs = 4;
	private final int nbCouleursPalette = 256;
	private final int etapesIntermediaires = nbCouleursPalette / nbCouleurs;
	
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
		ColorRGB blanc = new ColorRGB(255, 255, 255);
		
		paletteInitiale[0] = bleu;
		paletteInitiale[1] = vert;
		paletteInitiale[2] = marron;
		paletteInitiale[3] = blanc;
		
		creerPaletteLUT();
	}
	
	private void creerPaletteLUT() {
		// Calcul des etapes
		// Attention la dernière couleur ne compte pas
		int reste = nbCouleursPalette % (nbCouleurs - 1);
		int etapes = (nbCouleursPalette - reste) / (nbCouleurs - 1);
		
		paletteLUT = new ColorRGB[nbCouleursPalette];
		
		for (int i = 0; i < nbCouleurs-1; i++) {
			// Premiere couleur donnee comme etape
			int index = i * etapes;
			
			System.out.println("index : " + index);
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
				
				if (paletteInitiale[i+1].getRed() > paletteInitiale[i].getRed()){
					couleur.setRed(paletteInitiale[i].getRed() + ((int)plageR * j));
				} else {
					couleur.setRed(paletteInitiale[i].getRed() - ((int)plageR * j));
				}
				
				if (paletteInitiale[i+1].getGreen() > paletteInitiale[i].getGreen()){
					couleur.setGreen(paletteInitiale[i].getGreen() + ((int)plageG * j));
				} else {
					couleur.setGreen(paletteInitiale[i].getGreen() - ((int)plageG * j));
				}
				
				if (paletteInitiale[i+1].getBlue() > paletteInitiale[i].getBlue()){
					couleur.setBlue(paletteInitiale[i].getBlue() + ((int)plageB * j));
				} else {
					couleur.setBlue(paletteInitiale[i].getBlue() - ((int)plageB * j));
				}
				
				System.out.println("Index palette" + (index + j));
				
				paletteLUT[index + j] = couleur;
			}
			
			// Dernière couleur
			paletteLUT[nbCouleursPalette - 1] = paletteInitiale[nbCouleurs-1];
		}
	}

	// Retourne la couleur correspondante dans la palette LUT
	public ColorRGB getColorFromLUT(int greyScale) {
		return paletteLUT[greyScale];
	}
}
