package fr.cesi.ylalanne.game;

/**
 * The kind of an {@link Area}, determines the image and associated business rule for an {@link Area}
 */
public enum AreaKind {
	START("/area/start.png", null),
	WAIT("/area/wait.png", null),
	FINISH("/area/finish.png", null);
	
	private String imagePath;
	private String secondImagePath;
	
	AreaKind(String imagePath, String secondImagePath) {
		this.imagePath = imagePath;
		this.secondImagePath = secondImagePath;
	}
	
	/**
	 * @return the imagePath
	 */
	public String getImagePath() {
		return imagePath;
	}
	
	public String getSecondImagePath() {
		return secondImagePath;
	}
}
