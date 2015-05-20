package fr.cesi.ylalanne.settings.model;

public enum Titles {
	DIFFICULTY("DifficultyPanel_Title"),
	RESOLUTION("ResolutionPanel_Title"),
	SETTINGS("SettingsDialog_Title");
	
	private String key;
	
	private Titles(String key) {
		this.key = key;
	}
	
	public String getKey() {
		return key;
	}
}
