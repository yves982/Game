package game;

import game.model.PlayerModel;
import game.model.geom.MutableRectangle;
import game.ui.PlayerInfosView;
import game.ui.PlayerView;

import java.util.ArrayList;
import java.util.List;

import main.IChildrenController;
import main.ui.IChildView;

public class Player implements IChildrenController {
	private PlayerModel model;
	private PlayerView mainView;
	private PlayerInfosView infosView;
	private List<IChildView> childrenView;
	
	private void fillChildrenView() {
		childrenView.add(mainView);
		childrenView.add(infosView);
	}

	private PlayerModel buildModel() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Default constructor
	 */
	public Player() {
		childrenView = new ArrayList<IChildView>();
		fillChildrenView();
		model = buildModel();
		infosView = new PlayerInfosView(model);
		mainView = new PlayerView(model);
		// TODO Complete this constructor
	}

	public boolean isWithin(int x, int y, int radius) {
		return model.getArea().intersects(x, y, radius);
	}
	
	public boolean collide(MutableRectangle area) {
		return model.getArea().intersects(area);
	}

	@Override
	public List<IChildView> getChildren() {
		return childrenView;
	}
	
	
}
