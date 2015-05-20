package fr.cesi.ylalanne.main;

import java.util.List;

import fr.cesi.ylalanne.main.ui.ILayeredChildView;

/**
 * a Controller with many {@code IChildView}
 */
public interface ILayeredChildrenController {
	List<ILayeredChildView> getChildren();
}
