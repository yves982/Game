package fr.cesi.ylalanne.settings.model;
/**
 * Stores Resolution related properties.
 */
public enum Resolutions {
	LOW("Resolution_Low", 800, 600),
	STANDARD("Resolution_Standard", 1280, 720),
	HIGH("Resolution_High", 1400, 1050);
	
	private String key;
	private int width;
	private int height;
	
	/**
	 * @param key the translation key
	 * @param width the resolution's width
	 * @param height the resolution's height
	 */
	private Resolutions(String key, int width, int height) {
		this.key = key;
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Gets the key.
	 *
	 * @return the translation key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * Gets the width.
	 *
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Gets the height.
	 *
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}
}
