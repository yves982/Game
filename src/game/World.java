package game;

import game.ui.WorldView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import main.IChildController;
import main.ui.IChildView;



/**
 * 
 */
public class World implements IChildController {
	private List<Obstacle> obstacles;
	private List<Area> areas;
	private Player player;
	private WorldView view;
	
	/**
	 * Default constructor
	 */
	public World() {
		obstacles = new ArrayList<Obstacle>();
		areas = new ArrayList<Area>();
		view = new WorldView();
	}
	
	/**
	 * @param obstacle
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public void addObstacle(Obstacle obstacle) {
		obstacles.add(obstacle);
		view.addChild(obstacle.getChild(), 1);
	}
	
	/**
	 * @param area
	 */
	public void addArea(Area area) {
		areas.add(area);
		view.addChild(area.getChild(), 0);
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
		player.getChildren().forEach( layeredChildView -> view.addChild(layeredChildView, layeredChildView.getLayer()));
	}

	@Override
	public IChildView getChild() {
		return view;
	}
}
