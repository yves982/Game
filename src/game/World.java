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
	public void addObstacle(Obstacle obstacle) throws InterruptedException, ExecutionException {
		obstacles.add(obstacle);
		view.addChild(obstacle.getChild());
	}
	
	/**
	 * @param area
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public void addArea(Area area) throws InterruptedException, ExecutionException {
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
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	public void setPlayer(Player player) throws InterruptedException, ExecutionException {
		this.player = player;
		player.getChildren().forEach( childView -> view.addChild(childView));
	}

	@Override
	public IChildView getChild() {
		return view;
	}
}
