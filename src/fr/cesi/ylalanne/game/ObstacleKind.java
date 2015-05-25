package fr.cesi.ylalanne.game;

/**
 * The kind of an obstacle, determines the image and associated business rule for an {@link Obstacle}
 */
public enum ObstacleKind {
	TRUCK("/obstacle/truck.png", true),
	CAR("/obstacle/car.png", true),
	TRUNK("/obstacle/trunk.png", false), 
	TURTLE("/obstacle/turtle.png", false);
	
	private String imagePath;
	private boolean deadly;

	ObstacleKind(String imagePath, boolean deadly) {
		this.imagePath = imagePath;
		this.deadly = deadly;
	}

	/**
	 * @return the imagePath
	 */
	public String getImagePath() {
		return imagePath;
	}
	
	/**
	 * @return true if this ObstacleKind is deadly, false otherwise
	 */
	public boolean isDeadly() {
		return deadly;
	}
}
