package fr.cesi.ylalanne.game;

/**
 * Stores WorldManager's translation keys.
 */
public enum WorldManagerStrings {
	END_GAME("end_game"), 
	WIN_GAME("win_game");
	
	private String key;
	
	/**
	 * @param key the translation key to set
	 */
	private WorldManagerStrings(String key) {
		this.key = key;
	}
	
	/**
	 * Gets the key.
	 *
	 * @return the translation key
	 */
	public String getKey() {
		return key;
	}
}
