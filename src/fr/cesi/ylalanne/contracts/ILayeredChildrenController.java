package fr.cesi.ylalanne.contracts;

import java.util.List;

import fr.cesi.ylalanne.contracts.ui.ILayeredChildView;
import fr.cesi.ylalanne.contracts.ui.ILayeredChildView;

/**
 * a Controller with many {@code IChildView}
 */
public interface ILayeredChildrenController {
	List<ILayeredChildView> getChildren();
}
