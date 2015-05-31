package fr.cesi.ylalanne.settings;

/**
 * SettingsController translations keys.
 */
public enum SettingsStrings {
	CHANGE_LANG("Lang_Change");
	
	private String key;
	
	/**
	 * @param key the translation key to set
	 */
	private SettingsStrings(String key) {
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
