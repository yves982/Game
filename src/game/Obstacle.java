package game;

import game.model.ObstacleModel;
import game.ui.ObstacleView;

public class Obstacle {
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
}
