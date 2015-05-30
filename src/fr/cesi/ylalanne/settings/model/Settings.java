package fr.cesi.ylalanne.settings.model;

public class Settings {
	private Difficulties difficulty;
	private Resolutions resolution;
	private Languages language;
	
	/**
	 * @return the difficulty
	 */
	public Difficulties getDifficulty() {
		return difficulty;
	}
	/**
	 * @param difficulty the difficulty to set
	 */
	public void setDifficulty(Difficulties difficulty) {
		this.difficulty = difficulty;
	}
	
	/**
	 * @return the resolution
	 */
	public Resolutions getResolution() {
		return resolution;
	}
	/**
	 * @param resolution the resolution to set
	 */
	public void setResolution(Resolutions resolution) {
		this.resolution = resolution;
	}
	
	/**
	 * @return the language
	 */
	public Languages getLanguage() {
		return language;
	}
	/**
	 * @param language the language to set
	 */
	public void setLanguage(Languages language) {
		this.language = language;
	}
}
