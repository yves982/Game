package game;

import game.model.AreaModel;
import game.ui.AreaView;
import main.IChildController;
import main.ui.IChildView;

/**
 * An Area which does not handle collision nor provide any input
 * <p>
 * It's simply part of the background with no interaction whatsoever
 * </p>
 */
public class Area implements IChildController {
	AreaModel model;
	AreaView view;
	
	/**
	 * Initialize an Area
	 */
	public Area() {
		model = new AreaModel();
		view = new AreaView(model);
	}



	@Override
	public IChildView getChild() {
		return view;
	}
}
