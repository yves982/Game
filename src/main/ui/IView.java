package main.ui;

/**
 * a view interface : all views are required to implement it<br/>
 *  <b>Note: </b> {@code build()} should be the first method ever called on any IView Implementation
 */
public interface IView {
	/**
	 * builds this view before using it
	 */
	void build();
}
