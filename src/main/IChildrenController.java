package main;

import java.util.List;

import main.ui.IChildView;

/**
 * a Controller with many {@code IChildView}
 */
public interface IChildrenController {
	List<IChildView> getChildren();
}
