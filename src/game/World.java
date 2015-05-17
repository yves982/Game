package game;

import game.ui.WorldView;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import main.IChildController;
import main.ui.IChildView;



/**
 * 
 */
public class World implements IChildController, PropertyChangeListener {
	private List<Obstacle> obstacles;
	private List<Area> areas;
	private Player player;
	private WorldView view;
	private int width;
	private int height;
	
	private void updateWidth(int width) {
		this.width = width;
	}

	private void updateHeight(int height) {
		this.height = height;
	}

	/**
	 * Default constructor
	 */
	public World() {
		obstacles = new ArrayList<Obstacle>();
		areas = new ArrayList<Area>();
		view = new WorldView();
		view.addPropertyChangeListener(this);
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

	/**
	 * @return the world's width
	 */
	public int getWidth() {
		return width;
	}
	/**
	 * @return the world's height
	 */
	public int getHeight() {
		return height;
	}

	@Override
	public IChildView getChild() {
		return view;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propertyName = evt.getPropertyName();
		Object newValue = evt.getNewValue();
		
		switch(propertyName) {
			case "width":
				updateWidth((int)newValue);
				break;
			case "height":
				updateHeight((int)newValue);
				break;
		}
	}
}
