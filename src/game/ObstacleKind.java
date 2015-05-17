package game;

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
