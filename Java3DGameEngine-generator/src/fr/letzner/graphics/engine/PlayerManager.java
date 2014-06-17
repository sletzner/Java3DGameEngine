/**
 * 
 */
package fr.letzner.graphics.engine;

import fr.letzner.graphics.actors.Player;

/**
 * @author stefan
 *
 */
public class PlayerManager {

	Player player = new Player();
	
	/**
	 * Constructeur prive
	 */
	private PlayerManager(){};
	
	/**
	 * Instance du singleton
	 */
	private static PlayerManager instance = null;
	
	/**
	 * Getter du singleton
	 * @return
	 */
	public static PlayerManager getInstance(){
		if (instance == null){
			instance = new PlayerManager();
		}
		
		return instance;
	}
	
	/**
	 * Getter du joueur
	 * @return
	 */
	public Player getPlayer() {
		return player;
	}
}
