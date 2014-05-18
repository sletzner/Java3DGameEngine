/**
 * 
 */
package fr.letzner.graphics.shapes.Impl;

/**
 * @author stefan
 *
 */
public class ShapeManager {
	
	private ShapeManager(){};
	
	private static ShapeManager instance = null;
	
	public static ShapeManager getInstance(){
		if (instance == null) {
			instance = new ShapeManager();
		}
		
		return instance;
	}
	
	/**
	 * Objets disponibles
	 */
	private Paysage paysage = null;
	private Arbre arbre = null;
	private Ciel ciel = null;

	public Paysage getPaysage() {
		return paysage;
	}
	public void setPaysage(Paysage paysage) {
		this.paysage = paysage;
	}
	public Arbre getArbre() {
		return arbre;
	}
	public void setArbre(Arbre arbre) {
		this.arbre = arbre;
	}
	public Ciel getCiel() {
		return ciel;
	}
	public void setCiel(Ciel ciel) {
		this.ciel = ciel;
	}
	
	
}
