package fr.cesi.ylalanne.game;

/**
 * The kind of an obstacle, determines the image and associated business rule for an {@link Obstacle}.
 */
public enum ObstacleKind {
	TRUCK("/obstacle/truck.png", true, false),
	CAR("/obstacle/car.png", true, false),
	TRUNK("/obstacle/trunk.png", false, false), 
	TURTLE("/obstacle/turtle.png", false, false),
	BEAR("/obstacle/bear.png", false, true);
	
	private String imagePath;
	private boolean deadly;
	private boolean isStatic;

	ObstacleKind(String imagePath, boolean deadly, boolean isStatic) {
		this.imagePath = imagePath;
		this.deadly = deadly;
		this.isStatic = isStatic;
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
	 * Checks if it is deadly.
	 * <p>A deadly Obstacle takes the Player's life on collision, a non deadly one can be climbed upon.</p>
	 *
	 * @return true if this ObstacleKind is deadly, false otherwise
	 */
	public boolean isDeadly() {
		return deadly;
	}
	
	/**
	 * Checks if it is static.
	 * <p>A static Obstacle should not be moved.</p>
	 *
	 * @return true if this ObstacleKind is static, false otherwise
	 */
	public boolean isStatic() {
		return isStatic;
	}
}
