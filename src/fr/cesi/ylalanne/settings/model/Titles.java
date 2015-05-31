package fr.cesi.ylalanne.settings.model;

/**
 * Stores Settings panel and dialog titles.
 */
public enum Titles {
	DIFFICULTY("DifficultyPanel_Title"),
	RESOLUTION("ResolutionPanel_Title"),
	SETTINGS("SettingsDialog_Title"), 
	LANG("LangPanel_Title");
	
	private String key;
	
	private Titles(String key) {
		this.key = key;
	}
	
	/**
	 * Gets the translation key.
	 *
	 * @return the key
	 */
	public String getKey() {
		return key;
	}
}
