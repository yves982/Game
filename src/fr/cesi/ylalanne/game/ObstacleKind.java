package fr.cesi.ylalanne.game;

/**
 * The kind of an obstacle, determines the image and associated business rule for an {@link Obstacle}
 */
public enum ObstacleKind {
	TRUCK("/obstacle/truck.png"),
	CAR("/obstacle/car.png"),
	TRUNK("/obstacle/trunk.png");
	
	private String imagePath;

	ObstacleKind(String imagePath) {
		this.imagePath = imagePath;
	}

	/**
	 * @return the imagePath
	 */
	public String getImagePath() {
		return imagePath;
	}
}
