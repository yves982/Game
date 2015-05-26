package fr.cesi.ylalanne.settings.model;


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
	 * @return the translation key
	 */
	public String getKey() {
		return key;
	}
	
	/**
	 * @return the minimum amount of space between obstacle of a same row
	 */
	public int getMinObstacleSpace() {
		return minObstacleSpace;
	}
}
