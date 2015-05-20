package fr.cesi.ylalanne.game;

/**
 * The kind of an {@link Area}, determines the image and associated business rule for an {@link Area}
 */
public enum AreaKind {
	START("/area/start.png"),
	WAIT("/area/wait.png"),
	FINISH("/area/finish.png");
	
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
