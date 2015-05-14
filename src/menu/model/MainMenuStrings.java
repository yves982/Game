package menu.model;

public enum MainMenuStrings {
	TITLE("MainFrame.title"),
	MENU_TITLE("MainFrame.menu");
	
	private String key;
	
	private MainMenuStrings(String key) {
		this.key = key;
	}
	
	public String getKey() {
		return key;
	}
}
