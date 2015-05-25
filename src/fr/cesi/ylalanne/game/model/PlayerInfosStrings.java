package fr.cesi.ylalanne.game.model;

/**
 * Storage class for PlayerInfos's translation keys
 */
public enum PlayerInfosStrings {
	SCORE("player_infos.score"),
	TIME("player_infos.time");
	
	private String key;
	
	/**
	 * @param key the key to set
	 */
	PlayerInfosStrings(String key) {
		this.key = key;
	}
	
	/**
	 * @return the translation key
	 */
	public String getKey() {
		return key;
	}
}
