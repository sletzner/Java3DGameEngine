package fr.letzner.graphics.images;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageManager {

	private BufferedImage image = null;
	private Dimension dimension = null;
	private int[] imageColorsArray;

	public ImageManager(String path) throws ImageManagerException {
		loadImage(path);
		createImageColorArray();
	}

	/**
	 * Chargement de l'image dans le service
	 * 
	 * @param path
	 * @throws ImageManagerException
	 */
	private void loadImage(String path) throws ImageManagerException {
		try {
			System.out.print("Chargement de l'image " + path + " : ");
			image = ImageIO.read(new File(path));
			
			// Parametrage du service
			this.dimension = new Dimension(image.getWidth(), image.getHeight());
			System.out.println("OK");
		} catch (IOException e) {
			throw new ImageManagerException("IO Exceptiopn while reading file "
					+ path);
		}
	}
	
	/**
	 * Retourne une liste de listes de couleurs correspondant
	 * aux pixels de l'image. 
	 */
	private void createImageColorArray() {
		
		this.imageColorsArray = image.getRGB(0, 0, this.dimension.width, this.dimension.height, null, 0, this.dimension.width);
	}

	public Dimension getDimension() {
		return dimension;
	}

	public void setDimension(Dimension dimension) {
		this.dimension = dimension;
	}

	public int[] getImageColorsArray() {
		return imageColorsArray;
	}

	public void setImageColorsArray(int[] imageColorsArray) {
		this.imageColorsArray = imageColorsArray;
	}
	
}
