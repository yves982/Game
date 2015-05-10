package game;

import game.model.PlayerModel;
import game.model.geom.MutableRectangle;
import game.ui.PlayerView;

public class Player {
	PlayerModel model;
	PlayerView view;
	
	public boolean isWithin(int x, int y, int radius) {
		return model.getArea().intersects(x, y, radius);
	}
	
	public boolean collide(MutableRectangle area) {
		return model.getArea().intersects(area);
	}
	
	
}
