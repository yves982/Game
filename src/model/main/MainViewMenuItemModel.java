package model.main;

import javax.swing.DefaultButtonModel;

public class MainViewMenuItemModel extends DefaultButtonModel {
	private static final long serialVersionUID = -987726507584486451L;
	private String value;
	private int mnemonic;
	private MainViewActions action;
	
	
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
	public MainViewActions getAction() {
		return action;
	}
	/**
	 * @param action the action to set
	 */
	public void setAction(MainViewActions action) {
		this.action = action;
	}
}
