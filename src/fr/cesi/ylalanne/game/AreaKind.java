package fr.cesi.ylalanne.game;

/**
 * The kind of an {@link Area}, determines the image and associated business rule for an {@link Area}.
 */
public enum AreaKind {
	START("/area/start.png", null),
	WAIT("/area/wait.png", "/area/waitBG.png"),
	FINISH("/area/finish.png", null);
	
	private String imagePath;
	private String secondImagePath;
	
	AreaKind(String imagePath, String secondImagePath) {
		this.imagePath = imagePath;
		this.secondImagePath = secondImagePath;
	}
	
	/**
	 * Gets the image path.
	 *
	 * @return the imagePath
	 */
	public String getImagePath() {
		return imagePath;
	}
	
	/**
	 * Gets the second image path.
	 *
	 * @return the second image path
	 */
	public String getSecondImagePath() {
		return secondImagePath;
	}
}
