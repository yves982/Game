package mainframe.model;

import javax.swing.DefaultButtonModel;

public class MainMenuItemModel extends DefaultButtonModel {
	private static final long serialVersionUID = -987726507584486451L;
	private String value;
	private int mnemonic;
	private MainMenuActions action;
	
	
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * @return the mnemonic
	 */
	public int getMnemonic() {
		return mnemonic;
	}
	/**
	 * @param mnemonic the mnemonic to set
	 */
	public void setMnemonic(int mnemonic) {
		this.mnemonic = mnemonic;
	}
	/**
	 * @return the action
	 */
	public MainMenuActions getAction() {
		return action;
	}
	/**
	 * @param action the action to set
	 */
	public void setAction(MainMenuActions action) {
		this.action = action;
	}
}
