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
 * </ul>.
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

	
	/**
	 * Cleans resources used by the World
	 */
	private void clean() {
		obstacles.clear();
		areas.clear();
		view.removePropertyChangeListener(this);
		
		if(player != null) {
			player.clean();
		}
	}

	/**
	 * Initializes the World.
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
	 * Adds the obstacle.
	 *
	 * @param obstacle the Obstacle to add to the World
	 */
	public void addObstacle(Obstacle obstacle) {
		obstacles.add(obstacle);
		view.addChild(obstacle.getChild(), 0);
	}
	
	/**
	 * Adds the area.
	 *
	 * @param area the Area to add to the world
	 */
	public void addArea(Area area) {
		areas.add(area);
		view.addChild(area.getChild(), 1);
	}
	
	/**
	 * Gets the player.
	 *
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}
	
	/**
	 * Sets the player.
	 *
	 * @param player the player to set
	 */
	public void setPlayer(Player player) {
		this.player = player;
		player.getChildren().forEach( layeredChildView -> view.addChild(layeredChildView, layeredChildView.getLayer()));
	}

	/**
	 * Gets the width.
	 *
	 * @return the world's width
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Gets the height.
	 *
	 * @return the world's height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Checks if it is reseted.
	 *
	 * @return the reseted state
	 */
	public boolean isReseted() {
		return reseted;
	}

	/**
	 * Reset this world.
	 */
	public void reset() {
		clean();
		view = new WorldView();
		view.build();
		
		view.addPropertyChangeListener(this);
		reseted = true;
		propertyChange.firePropertyChange("reseted", false, true);
	}

	/**
	 * Ends this World.
	 *
	 * @param playerWon true if the player has won, false otherwise
	 */
	public void end(boolean playerWon) {
		if(!playerWon) {
			view.showEnd();
		}
		clean();
	}

	/**
	 * Gets the obstacles.
	 *
	 * @return the obstacles
	 */
	public List<Obstacle> getObstacles() {
		return obstacles;
	}

	/**
	 * Gets the areas.
	 *
	 * @return the areas
	 */
	public List<Area> getAreas() {
		return areas;
	}

	/* (non-Javadoc)
	 * @see fr.cesi.ylalanne.contracts.IChildController#getChild()
	 */
	@Override
	public IChildView getChild() {
		return view;
	}

	/* (non-Javadoc)
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
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
	 * Adds the property change listener.
	 *
	 * @param listener the listener
	 * @see java.beans.PropertyChangeSupport#addPropertyChangeListener(java.beans.PropertyChangeListener)
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertyChange.addPropertyChangeListener(listener);
	}
	/**
	 * Removes the property change listener.
	 *
	 * @param listener the listener
	 * @see java.beans.PropertyChangeSupport#removePropertyChangeListener(java.beans.PropertyChangeListener)
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyChange.removePropertyChangeListener(listener);
	}
	/**
	 * Adds the property change listener.
	 *
	 * @param propertyName the property name
	 * @param listener the listener
	 * @see java.beans.PropertyChangeSupport#addPropertyChangeListener(java.lang.String, java.beans.PropertyChangeListener)
	 */
	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		propertyChange.addPropertyChangeListener(propertyName, listener);
	}
	/**
	 * Removes the property change listener.
	 *
	 * @param propertyName the property name
	 * @param listener the listener
	 * @see java.beans.PropertyChangeSupport#removePropertyChangeListener(java.lang.String, java.beans.PropertyChangeListener)
	 */
	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		propertyChange.removePropertyChangeListener(propertyName, listener);
	}
}
