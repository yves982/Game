package fr.cesi.ylalanne.game;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import fr.cesi.ylalanne.contracts.IChildController;
import fr.cesi.ylalanne.contracts.ui.IChildView;
import fr.cesi.ylalanne.game.ui.WorldView;



/**
 * The world containing the entire game
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
	 * Initialize the World
	 */
	public World() {
		obstacles = new ArrayList<Obstacle>();
		areas = new ArrayList<Area>();
		view = new WorldView();
		view.build();
		view.addPropertyChangeListener(this);
	}
	
	/**
	 * @param obstacle the Obstacle to add to the World
	 * @throws RuntimeException in case the Obstacle fails to get its child
	 */
	public void addObstacle(Obstacle obstacle) {
		obstacles.add(obstacle);
		view.addChild(obstacle.getChild(), 0);
	}
	
	/**
	 * @param area the Area to add to the world
	 * @throws RuntimeException in case the area fails to get its child
	 */
	public void addArea(Area area) {
		areas.add(area);
		view.addChild(area.getChild(), 1);
	}
	
	/**
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}
	/**
	 * @param player the player to set
	 * @throws RuntimeException in case the player fails to get any of its children
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
