package game;

import game.ui.WorldView;

import java.util.ArrayList;
import java.util.List;

import main.IChildController;
import main.ui.IChildView;



public class World implements IChildController {
	private List<Obstacle> obstacles;
	private List<Area> areas;
	private Player player;
	private WorldView view;
	
	public World() {
		obstacles = new ArrayList<Obstacle>();
		areas = new ArrayList<Area>();
		player = new Player();
	}
	
	public void addObstacle(Obstacle obstacle) {
		obstacles.add(obstacle);
		view.addChild(obstacle.getChild());
	}
	
	public void addArea(Area area) {
		areas.add(area);
		view.addChild(area.getChild());
	}
	
	/**
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}
	/**
	 * @param player the player to set
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

	@Override
	public IChildView getChild() {
		return view;
	}
}
