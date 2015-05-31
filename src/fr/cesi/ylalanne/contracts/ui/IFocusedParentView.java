package fr.cesi.ylalanne.contracts.ui;

/**
 * An {@link IParentView} with the ability to manage focus (generally one with a top level container).
 */
public interface IFocusedParentView extends IParentView {
	
	/**
	 * Adds a childView to this view
	 * <p>
	 * If this method has to throw an Exception, makes sure it's an unchecked once<br>
	 * (i.e: a {@code RuntimeException}).<br>
	 * <b>Implementors must call {@link IChildView#setParent} before {@link IChildView#getComponent()}.</b>
	 * </p>
	 * @param childView the childView to add
	 * @param requestFocus {@code true} if the child should have the focus, {@code false} otherwise
	 */
	void addChild(IChildView childView, boolean requestFocus);
}
