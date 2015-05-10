package game;

import java.util.ArrayList;
import java.util.List;

import game.ui.WorldView;



public class World {
	private List<Obstacle> obstacles;
	private List<Area> areas;
	private Player player;
	private WorldView view;
	
	public World() {
		obstacles = new ArrayList<Obstacle>();
		areas = new ArrayList<Area>();
	}
	
	public void addObstacle(Obstacle obstacle) {
		obstacles.add(obstacle);
	}
	
	public void addArea(Area area) {
		areas.add(area);
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
		
	}
}
