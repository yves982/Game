package fr.cesi.ylalanne.settings.model;


/**
 * Stores Difficulties and their associated properties.
 * <p>These properties are:</p>
 * <ul>
 *  <li>minimum amount of space between obstacles for world generation (real space is partly random)</li>
 * </ul>
 */
public enum Difficulties {
	EASY("Difficulty_Easy", 140),
	NORMAL("Difficulty_Normal", 100),
	HARD("Difficulty_Hard", 80);
	
	private String key;
	private int minObstacleSpace;
	
	/**
	 * Initialize an instance
	 * @param key
	 * @param maxLivetimeMs
	 */
	private Difficulties(String key, int maxLivetimeMs) {
		this.key = key;
		this.minObstacleSpace = maxLivetimeMs;
	}
	
	/**
	 * Gets the key.
	 *
	 * @return the translation key
	 */
	public String getKey() {
		return key;
	}
	
	/**
	 * Gets the minimum space between {@link fr.cesi.ylalanne.game.Obstacle Obstacles} (at World generation).
	 *
	 * @return the minimum amount of space between obstacle of a same row
	 */
	public int getMinObstacleSpace() {
		return minObstacleSpace;
	}
}
