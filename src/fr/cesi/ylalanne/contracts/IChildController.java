package fr.cesi.ylalanne.contracts;
import fr.cesi.ylalanne.contracts.ui.IChildView;

/**
 * a Controller with an {@code IChildView}
 */
public interface IChildController {
	/**
	 * @return this controller {@code IChildView}.
	 */
	IChildView getChild();
}
