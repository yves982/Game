package fr.cesi.ylalanne.settings.model;

/**
 * Store MainFrame Button and their translation keys
 */
public enum Actions {
	OK("MainButtons_Ok"),
	CANCEL("MainButtons_Cancel");
	
	private String key;
	
	private Actions(String key) {
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
