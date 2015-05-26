package fr.cesi.ylalanne.highscores.model;

/**
 * Storage class for HighScores translation keys
 */
public enum HighScoresStrings {
	NAME("highScores_nameColumn"),
	SCORE("highScores_scoreColumn"), 
	OK("highScores_ok");
	
	private String key;
	
	/**
	 * @param key the translation key to set
	 */
	private HighScoresStrings(String key) {
		this.key = key;
	}
	
	/**
	 * @return the translation key
	 */
	public String getKey() {
		return key;
	}
}
