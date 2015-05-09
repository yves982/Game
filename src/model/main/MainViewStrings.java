package model.main;

public enum MainViewStrings {
	TITLE("MainFrame.title"),
	MENU_TITLE("MainFrame.menu");
	
	private String key;
	
	private MainViewStrings(String key) {
		this.key = key;
	}
	
	public String getKey() {
		return key;
	}
}
