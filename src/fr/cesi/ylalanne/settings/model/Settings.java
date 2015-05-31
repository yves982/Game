package fr.cesi.ylalanne.settings.model;

/**
 * The Settings serialization entity (used with Jackson library to save and load JSON).
 */
public class Settings {
	private Difficulties difficulty;
	private Resolutions resolution;
	private Languages language;
	
	/**
	 * Gets the difficulty.
	 *
	 * @return the difficulty
	 */
	public Difficulties getDifficulty() {
		return difficulty;
	}
	
	/**
	 * Sets the difficulty.
	 *
	 * @param difficulty the difficulty to set
	 */
	public void setDifficulty(Difficulties difficulty) {
		this.difficulty = difficulty;
	}
	
	/**
	 * Gets the resolution.
	 *
	 * @return the resolution
	 */
	public Resolutions getResolution() {
		return resolution;
	}
	
	/**
	 * Sets the resolution.
	 *
	 * @param resolution the resolution to set
	 */
	public void setResolution(Resolutions resolution) {
		this.resolution = resolution;
	}
	
	/**
	 * Gets the language.
	 *
	 * @return the language
	 */
	public Languages getLanguage() {
		return language;
	}
	
	/**
	 * Sets the language.
	 *
	 * @param language the language to set
	 */
	public void setLanguage(Languages language) {
		this.language = language;
	}
}
