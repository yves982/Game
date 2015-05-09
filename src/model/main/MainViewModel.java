package model.main;


public class MainViewModel {
	private MainViewMenuItemModel start;
	private MainViewMenuItemModel highScores;
	private MainViewMenuItemModel settings;
	private MainViewMenuItemModel quit;
	private String menuTitle;
	private String title;
	
	/**
	 * @return the start
	 */
	public MainViewMenuItemModel getStart() {
		return start;
	}
	/**
	 * @param start the start to set
	 */
	public void setStart(MainViewMenuItemModel start) {
		this.start = start;
	}
	
	/**
	 * @return the highScores
	 */
	public MainViewMenuItemModel getHighScores() {
		return highScores;
	}
	/**
	 * @param highScores the highScores to set
	 */
	public void setHighScores(MainViewMenuItemModel highScores) {
		this.highScores = highScores;
	}
	
	/**
	 * @return the settings
	 */
	public MainViewMenuItemModel getSettings() {
		return settings;
	}
	/**
	 * @param settings the settings to set
	 */
	public void setSettings(MainViewMenuItemModel settings) {
		this.settings = settings;
	}
	
	/**
	 * @return the quit
	 */
	public MainViewMenuItemModel getQuit() {
		return quit;
	}
	/**
	 * @param quit the quit to set
	 */
	public void setQuit(MainViewMenuItemModel quit) {
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
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	public int getMenuMnemonic() {
		return 'M';
	}
}
