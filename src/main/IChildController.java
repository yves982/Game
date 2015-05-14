package main;
import main.ui.IChildView;

/**
 * a Controller with an {@code IChildView}
 */
public interface IChildController {
	/**
	 * @return this controller {@code IChildView}.
	 */
	IChildView getChild();
}
