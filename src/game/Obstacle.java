package game;

import game.model.ObstacleModel;
import game.ui.ObstacleView;
import main.IChildController;
import main.ui.IChildView;

public class Obstacle implements IChildController {
	private ObstacleModel model;
	private ObstacleView view;
	
	public boolean isWithin(int x, int y, int radius) {
		return model.getArea().intersects(x, y, radius);
	}
	
	public boolean collide(Obstacle obstacle) {
		return obstacle.model.getArea().intersects(model.getArea());
	}
	
	public boolean collide(Player player) {
		return player.collide(model.getArea());
	}

	@Override
	public IChildView getChild() {
		return view;
	}
}
