package main;

import java.util.List;

import main.ui.ILayeredChildView;

/**
 * a Controller with many {@code IChildView}
 */
public interface ILayeredChildrenController {
	List<ILayeredChildView> getChildren();
}
