package model.settings;

public enum Difficulty {
	EASY("Difficulty_Easy"),
	NORMAL("Difficulty_Normal"),
	HARD("Difficulty_Hard");
	
	private String key;
	
	private Difficulty(String key) {
		this.key = key;
	}
	
	public String getKey() {
		return key;
	}
	
}
