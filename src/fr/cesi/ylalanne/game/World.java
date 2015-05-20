package fr.cesi.ylalanne.game;

import fr.cesi.ylalanne.contracts.IRunnableChildController;
import fr.cesi.ylalanne.contracts.ui.IChildView;
import fr.cesi.ylalanne.game.ui.WorldView;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;



/**
 * 
 */
public class World implements IRunnableChildController, PropertyChangeListener {
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

	private void fillRowXSteps(List<Integer> rowXSteps, int rowCount) {
		for(int i=0; i < rowCount; i++) {
			rowXSteps.add(5);
		}
	}

	/**
	 * Spawns this world and start the fr.cesi.ylalanne.game
	 */
	private void spawn() {
		int rowsCount = 11;
		List<Integer> rowXSteps = new ArrayList<Integer>(rowsCount);
		fillRowXSteps(rowXSteps, rowsCount);
		WorldGenerator generator = new WorldGenerator(this, rowXSteps);
		Player player = generator.generatePlayer(3, 4000, 1);
		setPlayer(player);
		player.lives();
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

	@Override
	public void run() {
		spawn();
	}
}