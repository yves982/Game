package fr.cesi.ylalanne.contracts.ui;

/**
 * a view with a childView (sub part)<br>
 *  <b>Note: </b> {@link IView#build()} should be the first method ever called on any IView Implementation.
 */
public interface IParentView extends IView {
	/**
	 * Adds a childView to this view
	 * <p>
	 * If this method has to throw an Exception, makes sure it's an unchecked once<br>
	 * (i.e: a {@code RuntimeException}).<br>
	 * <b>Implementors must call {@link IChildView#setParent} before {@link IChildView#getComponent()}.</b>
	 * </p>
	 * @param childView the childView to add
	 */
	void addChild(IChildView childView);
}
