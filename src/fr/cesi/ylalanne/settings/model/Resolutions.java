package fr.cesi.ylalanne.settings.model;

public enum Resolutions {
	LOW("Resolution_Low"),
	STANDARD("Resolution_Standard"),
	HIGH("Resolution_High");
	
	private String key;
	
	private Resolutions(String key) {
		this.key = key;
	}
	
	public String getKey() {
		return key;
	}
}
