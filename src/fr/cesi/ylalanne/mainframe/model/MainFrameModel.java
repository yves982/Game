package fr.cesi.ylalanne.mainframe.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;


/**
 * Model for the MainFrameView
 * <p>It has the following bound properties:</p>
 * <ul>
 * <li>muted</li>
 * </ul>
 */
public class MainFrameModel {
	private MainMenuItemModel start;
	private MainMenuItemModel highScores;
	private MainMenuItemModel settings;
	private MainMenuItemModel quit;
	private String menuTitle;
	private String frameTitle;
	private int width;
	private int height;
	private boolean muted;
	private String mutedTitle;
	private PropertyChangeSupport propertyChange;
	
	public MainFrameModel() {
		propertyChange = new PropertyChangeSupport(this);
	}
	
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
	
	/**
	 * @return the muted
	 */
	public boolean isMuted() {
		return muted;
	}
	/**
	 * @param muted the muted to set
	 */
	public void setMuted(boolean muted) {
		boolean oldMuted = this.muted;
		this.muted = muted;
		propertyChange.firePropertyChange("muted", oldMuted, muted);
 	}
	
	/**
	 * @return the Muted title
	 */
	public String getMutedTitle() {
		return mutedTitle;
	}
	/**
	 * @param mutedTitle the mutedTitle to set
	 */
	public void setMutedTitle(String mutedTitle) {
		this.mutedTitle = mutedTitle;
	}

	/**
	 * @param listener
	 * @see java.beans.PropertyChangeSupport#addPropertyChangeListener(java.beans.PropertyChangeListener)
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertyChange.addPropertyChangeListener(listener);
	}
	/**
	 * @param propertyName
	 * @param listener
	 * @see java.beans.PropertyChangeSupport#addPropertyChangeListener(java.lang.String, java.beans.PropertyChangeListener)
	 */
	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		propertyChange.addPropertyChangeListener(propertyName, listener);
	}
	/**
	 * @param listener
	 * @see java.beans.PropertyChangeSupport#removePropertyChangeListener(java.beans.PropertyChangeListener)
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyChange.removePropertyChangeListener(listener);
	}
	/**
	 * @param propertyName
	 * @param listener
	 * @see java.beans.PropertyChangeSupport#removePropertyChangeListener(java.lang.String, java.beans.PropertyChangeListener)
	 */
	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		propertyChange.removePropertyChangeListener(propertyName, listener);
	}
}
