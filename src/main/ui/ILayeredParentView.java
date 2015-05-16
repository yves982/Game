package main.ui;

/**
 * a view with a childView (sub part) and different layers
 */
public interface ILayeredParentView extends IParentView {
	/**
	 * Adds a childView to this view
	 * <p>
	 * If this method has to throw an Exception, makes sure it's an unchecked once<br/>
	 * (i.e: a {@code RuntimeException}).<br/>
	 * <b>Implementors must call {@link IChildView#setParent} before {@link IChildView#getComponent()}.</b>
	 * </p>
	 * @param childView the childView to add
	 * @param layer the childView's layer index (starting at 0)
	 */
	void addChild(IChildView childView, int layer);
}
