package model.settings;

public enum Resolution {
	LOW("Resolution_Low"),
	STANDARD("Resolution_Standard"),
	HIGH("Resolution_High");
	
	private String key;
	
	private Resolution(String key) {
		this.key = key;
	}
	
	public String getKey() {
		return key;
	}
}
