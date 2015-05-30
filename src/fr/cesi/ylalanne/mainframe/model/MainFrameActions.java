package fr.cesi.ylalanne.mainframe.model;

public enum MainFrameActions {
	
	START("MainFrame.menu_start"),
	HIGH_SCORE("MainFrame.menu_highscores"),
	SETTINGS("MainFrame.menu_options"),
	QUIT("MainFrame.menu_quit"),
	MUTE("MainFrame.menu_mute");
	
	private String key;
	
	MainFrameActions(String key) {
		this.key = key;
	}
	
	public String getKey() {
		return key;
	}
}
