package model.main;

public enum MainViewActions {
	
	START("MainFrame.menu_start"),
	HIGH_SCORE("MainFrame.menu_highscores"),
	SETTINGS("MainFrame.menu_options"),
	QUIT("MainFrame.menu_quit");
	
	private String key;
	
	MainViewActions(String key) {
		this.key = key;
	}
	
	public String getKey() {
		return key;
	}
}
