package fr.cesi.ylalanne.mainframe.model;

/**
 * Stores MainFrame menu actions and their translation keys.
 */
public enum MainFrameActions {
	
	START("MainFrame.menu_start"),
	HIGH_SCORE("MainFrame.menu_highscores"),
	SETTINGS("MainFrame.menu_options"),
	QUIT("MainFrame.menu_quit"),
	MUTE("MainFrame.menu_mute");
	
	private String key;
	
	private MainFrameActions(String key) {
		this.key = key;
	}
	
	/**
	 * Gets the key.
	 *
	 * @return the key
	 */
	public String getKey() {
		return key;
	}
}
