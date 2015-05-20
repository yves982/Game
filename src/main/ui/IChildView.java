package main.ui;

import java.awt.Container;

import javax.swing.JComponent;

/**
 * interface for a view with a main component to be added to some parent view.<br/>
 *  <b>Note: </b> {@code IView#build()} should be the first method ever called on any IView Implementation
 */
public interface IChildView extends IView {
	/**
	 * get the main component of this childView
	 * <p>
	 * If this method has to throw an Exception, makes sure it's an unchecked once<br />
	 *  (i.e: a {@code RuntimeException}).<br />
	 *  <b>Note: </b> {@code IView#build()} should be the first method ever called on any IView Implementation
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
	 * @param container the container that'll contains this view.
	 */
	void setParent(Container container);
}
