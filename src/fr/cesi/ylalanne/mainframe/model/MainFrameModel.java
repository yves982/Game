package fr.cesi.ylalanne.mainframe.model;


public class MainFrameModel {
	private MainMenuItemModel start;
	private MainMenuItemModel highScores;
	private MainMenuItemModel settings;
	private MainMenuItemModel quit;
	private String menuTitle;
	private String frameTitle;
	private int width;
	private int height;
	
	/**
	 * @return the start
	 */
	public MainMenuItemModel getStart() {
		return start;
	}
	/**
	 * @param start the start to set
	 */
	public void setStart(MainMenuItemModel start) {
		this.start = start;
	}
	
	/**
	 * @return the highScores
	 */
	public MainMenuItemModel getHighScores() {
		return highScores;
	}
	/**
	 * @param highScores the highScores to set
	 */
	public void setHighScores(MainMenuItemModel highScores) {
		this.highScores = highScores;
	}
	
	/**
	 * @return the settings
	 */
	public MainMenuItemModel getSettings() {
		return settings;
	}
	/**
	 * @param settings the settings to set
	 */
	public void setSettings(MainMenuItemModel settings) {
		this.settings = settings;
	}
	
	/**
	 * @return the quit
	 */
	public MainMenuItemModel getQuit() {
		return quit;
	}
	/**
	 * @param quit the quit to set
	 */
	public void setQuit(MainMenuItemModel quit) {
		this.quit = quit;
	}
	
	/**
	 * @return the menuTitle
	 */
	public String getMenuTitle() {
		return menuTitle;
	}
	/**
	 * @param menuTitle the menuTitle to set
	 */
	public void setMenuTitle(String menuTitle) {
		this.menuTitle = menuTitle;
	}
	
	/**
	 * @return the frameTitle
	 */
	public String getFrameTitle() {
		return frameTitle;
	}
	/**
	 * @param frameTitle the frameTitle to set
	 */
	public void setFrameTitle(String frameTitle) {
		this.frameTitle = frameTitle;
	}
	public int getMenuMnemonic() {
		return 'M';
	}
	
	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}
	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}
	
	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}
	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}
}
