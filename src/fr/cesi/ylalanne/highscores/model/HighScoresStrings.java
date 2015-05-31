package fr.cesi.ylalanne.highscores.model;

/**
 * Store HighScores translation keys.
 */
public enum HighScoresStrings {
	NAME("highScores_nameColumn"),
	SCORE("highScores_scoreColumn"), 
	OK("highScores_ok"), 
	WARNING_EMPTY_NAME("highScoresEntry_emptyName"), 
	VIEW_TITLE("highScores_title"), 
	ENTRY_VIEW_TITLE("highScoresEntry_title");
	
	private String key;
	
	/**
	 * @param key the translation key to set
	 */
	private HighScoresStrings(String key) {
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
