package game.model;

public enum PlayerInfosStrings {
	SCORE("player_infos.score"),
	TIME("player_infos.time");
	
	private String key;
	
	PlayerInfosStrings(String key) {
		this.key = key;
	}
	
	public String getKey() {
		return key;
	}
}
