package fr.cesi.ylalanne.contracts.ui;

/**
 * a view interface : all views are required to implement it<br>
 *  <b>Note: </b> {@link #build()} should be invoked on any IView Implementation before using it (adding it as child or updating it).
 */
public interface IView {
	/**
	 * builds this view before using it
	 * <p>
	 * Note: this method is intended to be invoked outside an event thread and eventually inside constructors.<br>
	 * So it should not throw any exception, but log them instead
	 * </p>
	 */
	void build();
}
