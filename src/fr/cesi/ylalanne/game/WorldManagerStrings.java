package fr.cesi.ylalanne.game;

/**
 * Storage class for WorldManager's translation keys
 */
public enum WorldManagerStrings {
	END_GAME("end_game"), 
	WIN_GAME("win_game");
	
	private String key;
	
	/**
	 * @param key the key to set
	 */
	private WorldManagerStrings(String key) {
		this.key = key;
	}
	
	/**
	 * @return the translation key
	 */
	public String getKey() {
		return key;
	}
}
