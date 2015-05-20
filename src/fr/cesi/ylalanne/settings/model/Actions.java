package fr.cesi.ylalanne.settings.model;

public enum Actions {
	OK("MainButtons_Ok"),
	CANCEL("MainButtons_Cancel");
	
	private String key;
	
	private Actions(String key) {
		this.key = key;
	}
	
	public String getKey() {
		return key;
	}
}
