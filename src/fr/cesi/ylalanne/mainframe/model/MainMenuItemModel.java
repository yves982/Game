package fr.cesi.ylalanne.mainframe.model;

import javax.swing.DefaultButtonModel;

/**
 * Model for MainFrame menu items.
 */
public class MainMenuItemModel extends DefaultButtonModel {
	private static final long serialVersionUID = -987726507584486451L;
	private String value;
	private int mnemonic;
	private MainFrameActions action;
	
	
	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * Sets the value.
	 *
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
	/**
	 * Gets the mnemonic.
	 *
	 * @return the mnemonic
	 */
	public int getMnemonic() {
		return mnemonic;
	}
	
	/**
	 * Sets the mnemonic.
	 *
	 * @param mnemonic the mnemonic to set
	 */
	public void setMnemonic(int mnemonic) {
		this.mnemonic = mnemonic;
	}
	
	/**
	 * Gets the main frame action.
	 *
	 * @return the action
	 */
	public MainFrameActions getAction() {
		return action;
	}
	
	/**
	 * Sets the main frame action.
	 *
	 * @param action the action to set
	 */
	public void setAction(MainFrameActions action) {
		this.action = action;
	}
}
