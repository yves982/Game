package settings.model;

public enum SettingsStrings {
	DIFFICULTY_TITLE("DifficultyPanel_Title"),
	RESOLUTION_TITLE("ResolutionPanel_Title"),
	MAIN_BUTTONS_OK("MainButtons_Ok"), 
	MAIN_BUTTONS_CANCEL("MainButtons_Cancel"),
	SETTINGS_DIALOG_TITLE("SettingsDialog_Title");
	
	private String key;
	
	private SettingsStrings(String key) {
		this.key = key;
	}
	
	public String getKey() {
		return key;
	}
}
