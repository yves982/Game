package fr.cesi.ylalanne.mainframe.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Model for the MainFrameView
 * <p>It has the following bound properties:</p>
 * <ul>
 * <li>muted</li>
 * </ul>.
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
	
	/**
	 * Instantiates a new main frame model.
	 */
	public MainFrameModel() {
		propertyChange = new PropertyChangeSupport(this);
	}
	
	/**
	 * Gets the model for the start menu item.
	 *
	 * @return the start
	 */
	public MainMenuItemModel getStart() {
		return start;
	}
	
	/**
	 * Sets the start.
	 *
	 * @param start the model to set for the start menu item
	 */
	public void setStart(MainMenuItemModel start) {
		this.start = start;
	}
	
	/**
	 * Gets the model for highScores menu item.
	 *
	 * @return the highScores
	 */
	public MainMenuItemModel getHighScores() {
		return highScores;
	}
	
	/**
	 * Sets the high scores.
	 *
	 * @param highScores the highScores to set
	 */
	public void setHighScores(MainMenuItemModel highScores) {
		this.highScores = highScores;
	}
	
	/**
	 * Gets the model for the settings menu item.
	 *
	 * @return the settings
	 */
	public MainMenuItemModel getSettings() {
		return settings;
	}
	
	/**
	 * Sets the model for the settings menu item.
	 *
	 * @param settings the settings to set
	 */
	public void setSettings(MainMenuItemModel settings) {
		this.settings = settings;
	}
	
	/**
	 * Gets the model for the quit menu item.
	 *
	 * @return the quit
	 */
	public MainMenuItemModel getQuit() {
		return quit;
	}
	
	/**
	 * Sets the model for the quit menu item.
	 *
	 * @param quit the quit to set
	 */
	public void setQuit(MainMenuItemModel quit) {
		this.quit = quit;
	}
	
	/**
	 * Gets the menu title.
	 *
	 * @return the menuTitle
	 */
	public String getMenuTitle() {
		return menuTitle;
	}
	
	/**
	 * Sets the menu title.
	 *
	 * @param menuTitle the menuTitle to set
	 */
	public void setMenuTitle(String menuTitle) {
		this.menuTitle = menuTitle;
	}
	
	/**
	 * Gets the frame title.
	 *
	 * @return the frameTitle
	 */
	public String getFrameTitle() {
		return frameTitle;
	}
	
	/**
	 * Sets the frame title.
	 *
	 * @param frameTitle the frameTitle to set
	 */
	public void setFrameTitle(String frameTitle) {
		this.frameTitle = frameTitle;
	}
	
	/**
	 * Gets the menu mnemonic.
	 *
	 * @return the menu mnemonic
	 */
	public int getMenuMnemonic() {
		return 'M';
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
	 * Sets the width.
	 *
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}
	
	/**
	 * Gets the height.
	 *
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * Sets the height.
	 *
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}
	
	/**
	 * Checks if is muted.
	 *
	 * @return the muted
	 */
	public boolean isMuted() {
		return muted;
	}
	
	/**
	 * Sets the muted state.
	 *
	 * @param muted the muted to set
	 */
	public void setMuted(boolean muted) {
		boolean oldMuted = this.muted;
		this.muted = muted;
		propertyChange.firePropertyChange("muted", oldMuted, muted);
 	}
	
	/**
	 * Gets the muted title.
	 *
	 * @return the Muted title
	 */
	public String getMutedTitle() {
		return mutedTitle;
	}
	
	/**
	 * Sets the muted title.
	 *
	 * @param mutedTitle the mutedTitle to set
	 */
	public void setMutedTitle(String mutedTitle) {
		this.mutedTitle = mutedTitle;
	}

	/**
	 * Adds the property change listener.
	 *
	 * @param listener the listener
	 * @see java.beans.PropertyChangeSupport#addPropertyChangeListener(java.beans.PropertyChangeListener)
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertyChange.addPropertyChangeListener(listener);
	}
	/**
	 * Adds the property change listener.
	 *
	 * @param propertyName the property name
	 * @param listener the listener
	 * @see java.beans.PropertyChangeSupport#addPropertyChangeListener(java.lang.String, java.beans.PropertyChangeListener)
	 */
	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		propertyChange.addPropertyChangeListener(propertyName, listener);
	}
	/**
	 * Removes the property change listener.
	 *
	 * @param listener the listener
	 * @see java.beans.PropertyChangeSupport#removePropertyChangeListener(java.beans.PropertyChangeListener)
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyChange.removePropertyChangeListener(listener);
	}
	/**
	 * Removes the property change listener.
	 *
	 * @param propertyName the property name
	 * @param listener the listener
	 * @see java.beans.PropertyChangeSupport#removePropertyChangeListener(java.lang.String, java.beans.PropertyChangeListener)
	 */
	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		propertyChange.removePropertyChangeListener(propertyName, listener);
	}
}
