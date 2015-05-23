package fr.cesi.ylalanne.settings.model;


public enum Difficulties {
	EASY("Difficulty_Easy"),
	NORMAL("Difficulty_Normal"),
	HARD("Difficulty_Hard");
	
	private String key;
	
	private Difficulties(String key) {
		this.key = key;
	}
	
	public String getKey() {
		return key;
	}
	
}
