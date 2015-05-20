package fr.cesi.ylalanne.mainframe.model;

public enum MainFrameStrings {
	TITLE("MainFrame.title"),
	MENU_TITLE("MainFrame.menu");
	
	private String key;
	
	private MainFrameStrings(String key) {
		this.key = key;
	}
	
	public String getKey() {
		return key;
	}
}
