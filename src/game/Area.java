package game;

import game.model.AreaModel;
import game.ui.AreaView;
import main.IChildController;
import main.ui.IChildView;

public class Area implements IChildController {
	AreaModel model;
	AreaView view;
	
	@Override
	public IChildView getChild() {
		return view;
	}
}
