package game;

public enum AreaKind {
	START("area/start.png"),
	WAIT("area/wait.png"),
	FINISH("area/finish.png");
	
	private String imagePath;
	
	AreaKind(String imagePath) {
		this.imagePath = imagePath;
	}
	
	/**
	 * @return the imagePath
	 */
	public String getImagePath() {
		return imagePath;
	}
}
