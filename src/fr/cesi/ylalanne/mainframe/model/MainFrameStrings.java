package fr.cesi.ylalanne.mainframe.model;

/**
 * Store MainFrame related Strings and their translations.
 */
public enum MainFrameStrings {
	TITLE("MainFrame.title"),
	MENU_TITLE("MainFrame.menu");
	
	private String key;
	
	private MainFrameStrings(String key) {
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
