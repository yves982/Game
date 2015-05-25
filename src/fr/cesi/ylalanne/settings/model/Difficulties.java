package fr.cesi.ylalanne.settings.model;


public enum Difficulties {
	EASY("Difficulty_Easy", 15000),
	NORMAL("Difficulty_Normal", 8000),
	HARD("Difficulty_Hard", 6000);
	
	private String key;
	private int maxLiveTimeMs;
	
	/**
	 * Initialize an instance
	 * @param key
	 * @param maxLivetimeMs
	 */
	private Difficulties(String key, int maxLivetimeMs) {
		this.key = key;
		this.maxLiveTimeMs = maxLivetimeMs;
	}
	
	/**
	 * @return the translation key
	 */
	public String getKey() {
		return key;
	}
	
	/**
	 * @return the maximum livetime in milliseconds
	 */
	public int getMaxLivetimeMs() {
		return maxLiveTimeMs;
	}
}
