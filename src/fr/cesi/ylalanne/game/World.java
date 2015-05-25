package fr.cesi.ylalanne.game;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import fr.cesi.ylalanne.contracts.IBoundChildController;
import fr.cesi.ylalanne.contracts.ui.IChildView;
import fr.cesi.ylalanne.game.ui.WorldView;



/**
 * The world containing the entire game
 * <p>It has the following bound properties:</p>
 * <ul>
 *   <li>reseted</li>
 * </ul>
 */
public class World implements IBoundChildController, PropertyChangeListener {
	private List<Obstacle> obstacles;
	private List<Area> areas;
	private Player player;
	private WorldView view;
	private int width;
	private int height;
	private boolean reseted;
	private PropertyChangeSupport propertyChange;
	
	private void updateWidth(int width) {
		this.width = width;
	}

	private void updateHeight(int height) {
		this.height = height;
	}

	private void clean() {
		obstacles.clear();
		areas.clear();
		view.removePropertyChangeListener(this);
		
		if(player != null) {
			player.kill();
		}
	}

	/**
	 * Initialize the World
	 */
	public World() {
		obstacles = new ArrayList<Obstacle>();
		areas = new ArrayList<Area>();
		reseted = false;
		propertyChange = new PropertyChangeSupport(this);
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

	/**
	 * @return the reseted state
	 */
	public boolean isReseted() {
		return reseted;
	}

	/**
	 * Reset this world
	 */
	public void reset() {
		clean();
		view = new WorldView();
		view.build();
		
		view.addPropertyChangeListener(this);
		reseted = true;
		propertyChange.firePropertyChange("reseted", false, true);
	}

	public void end() {
		view.showEnd();
		clean();
	}

	/**
	 * @return the obstacles
	 */
	public List<Obstacle> getObstacles() {
		return obstacles;
	}

	/**
	 * @return the areas
	 */
	public List<Area> getAreas() {
		return areas;
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

	/**
	 * @param listener
	 * @see java.beans.PropertyChangeSupport#addPropertyChangeListener(java.beans.PropertyChangeListener)
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertyChange.addPropertyChangeListener(listener);
	}
	/**
	 * @param listener
	 * @see java.beans.PropertyChangeSupport#removePropertyChangeListener(java.beans.PropertyChangeListener)
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyChange.removePropertyChangeListener(listener);
	}
	/**
	 * @param propertyName
	 * @param listener
	 * @see java.beans.PropertyChangeSupport#addPropertyChangeListener(java.lang.String, java.beans.PropertyChangeListener)
	 */
	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		propertyChange.addPropertyChangeListener(propertyName, listener);
	}
	/**
	 * @param propertyName
	 * @param listener
	 * @see java.beans.PropertyChangeSupport#removePropertyChangeListener(java.lang.String, java.beans.PropertyChangeListener)
	 */
	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		propertyChange.removePropertyChangeListener(propertyName, listener);
	}
}
