package fr.letzner.graphics.textures;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.IntBuffer;

import javax.imageio.ImageIO;
import javax.media.opengl.GLException;
import javax.media.opengl.GLProfile;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;

import fr.letzner.graphics.utils.GameConstants;

public class TextureManager {

	private String[] pathTexturesArbres = new String[4];
	private Texture[] texturesArbres = new Texture[4];
	private Texture textureSol = null;
	private String basePath = "/home/stefan/Dev/Ressources/Arbes/";
	private GLProfile gp = null;
	
	public TextureManager(GLProfile gp) {
		this.gp = gp;
		chargerTextures();
	}

	private void chargerTextures() {
		chargerTexturesArbres();
		
		chargerTextureSol();
	}

	private void chargerTextureSol() {
		try {
			textureSol = AWTTextureIO.newTexture(gp, (BufferedImage)ImageIO.read(new File(GameConstants.GRASS_TEXTURE_PATH)), true);
		} catch (GLException | IOException e) {
			System.out.println("Erreur lors du chargement de la texture du sol : " + e.getMessage());
		}
	}

	private void chargerTexturesArbres() {
		pathTexturesArbres[0] = "Arbre-boulot-petit-256-512.png";
		pathTexturesArbres[1] = "Arbre-chene-petit-256-256.png";
		pathTexturesArbres[2] = "Arbre-noisette-petit-256-512.png";
		pathTexturesArbres[3] = "Arbre-platane-petit-256-512.png";
		
		try {
			Texture texture0 = AWTTextureIO.newTexture(gp, (BufferedImage)ImageIO.read(new File(basePath + pathTexturesArbres[0])), true);
			Texture texture1 = AWTTextureIO.newTexture(gp, (BufferedImage)ImageIO.read(new File(basePath + pathTexturesArbres[1])), true);
			Texture texture2 = AWTTextureIO.newTexture(gp, (BufferedImage)ImageIO.read(new File(basePath + pathTexturesArbres[2])), true);
			Texture texture3 = AWTTextureIO.newTexture(gp, (BufferedImage)ImageIO.read(new File(basePath + pathTexturesArbres[3])), true);
			
			texturesArbres[0] = texture0;
			texturesArbres[1] = texture1;
			texturesArbres[2] = texture2;
			texturesArbres[3] = texture3;
		} catch (GLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Texture[] getTexturesArbres() {
		return texturesArbres;
	}

	public int getNbTextures() {
		// TODO Auto-generated method stub
		return 0;
	}

	public IntBuffer getByteArray() {
		// TODO Auto-generated method stub
		return null;
	}

	public Texture getTextureSol() {
		return textureSol;
	}

	
}
