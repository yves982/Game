package main.ui;

import javax.swing.JComponent;

/**
 * interface for a view with a main component to be added to some parent view.
 */
public interface IChildView {
	/**
	 * get the main component of this childView
	 * <p>
	 * If this method has to throw an Exception, makes sure it's an unchecked once<br />
	 *  (i.e: a {@code RuntimeException}).<br />
	 *  <b>Note:</b> {@code getComponent} should be called after {@code setParent}.
	 * </p>
	 * @return the view's main component to display in a parent view
	 * @throws RuntimeException in case the view fails to retrieve its child or to build herself
	 */
	JComponent getComponent();

	/**
	 * Sets the parent, allowing to place this IChildView inside its parent
	 * <p>
	 * <b>Note:</b> {@code setParent} should be called before {@code getComponent}.
	 * </p>
	 * @param parent the component that'll contains this view.
	 */
	void setParent(JComponent parent);
}
