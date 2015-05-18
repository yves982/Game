package game;

import game.model.PlayerModel;
import game.model.geom.MutableRectangle;
import game.ui.PlayerInfosView;
import game.ui.PlayerView;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import main.ILayeredChildrenController;
import main.ResourcesManager;
import main.ui.ILayeredChildView;

/**
 * The Player : a moving area which can be checked for collisions and moves on command
 * <p>
 * 	It has the following bound properties:
 * <ul>
 * 	<li>lifeLess</li>
 * </ul>
 * </p>
 */
public class Player implements ILayeredChildrenController, PropertyChangeListener {
	private PlayerModel model;
	private PlayerView playerView;
	private PlayerInfosView infosView;
	private Obstacle collider;
	private ScheduledFuture<?> countDownFuture;
	private List<ILayeredChildView> childrenView;
	private ScheduledExecutorService scheduled;
	private boolean lifeLess;
	private PropertyChangeSupport propertyChange;
	
	private void fillChildrenView() {
		childrenView.add(infosView);
		childrenView.add(playerView);
	}

	private void builModel(int maxLives, int maxLeftTimeMs) {
		model = new PlayerModel();
		model.setImagePath(Paths.get(ResourcesManager.RESOURCES_BASE, "/player/player.png").toString());
		model.setLives(maxLives);
		model.setMaxLiveTimeMs(maxLeftTimeMs);
		model.setRemainingLiveTimeMs(maxLeftTimeMs);
	}

	private void countDown() {
		int remainingLiveTimeMs = model.getRemainingLiveTimeMs();
		remainingLiveTimeMs -= 50;
		model.setRemainingLiveTimeMs(remainingLiveTimeMs);
		if(remainingLiveTimeMs == 0) {
			countDownFuture.cancel(true);
			dies();
			restartCountDown();
		}
	}

	private void restartCountDown() {
		countDownFuture = scheduled.scheduleAtFixedRate(this::countDown, 0, 50, TimeUnit.MILLISECONDS);
	}

	private void dies() {
		int lives = model.getLives();
		model.setLives(lives -1);
		model.setRemainingLiveTimeMs(model.getMaxLiveTimeMs());
		
		if(model.getLives() <= 0) {
			Object oldLifeLess = lifeLess;
			lifeLess = true;
			propertyChange.firePropertyChange("lifeLess", oldLifeLess , true);
			try {
				scheduled.shutdown();
				countDownFuture.cancel(true);
				scheduled.awaitTermination(1100, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}

	private void updateWidth(int width) {
		model.getArea().setWidth(width);
	}

	private void updateHeight(int height) {
		model.getArea().setHeight(height);
	}

	/**
	 * Initialize a Player
	 * @param maxLives the maximum number of lives before the game is over
	 * @param maxLeftTimeMs the maximum time every life can last (in milliseconds)
	 */
	public Player(int maxLives, int maxLeftTimeMs) {
		childrenView = new ArrayList<ILayeredChildView>();
		builModel(maxLives, maxLeftTimeMs);
		infosView = new PlayerInfosView(model);
		infosView.build();
		playerView = new PlayerView(model);
		playerView.build();
		fillChildrenView();
		propertyChange = new PropertyChangeSupport(this);
		scheduled = Executors.newSingleThreadScheduledExecutor();
		playerView.addPropertyChangeListener(this);
	}
	
	/**
	 * Places the player in the world
	 * <p>
	 * 	<b>Note: the player must be added to the world before setting its position</b>
	 * </p>                                                                                   
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	public void startPosition(int x, int y) {
		model.getArea().setX(x);
		model.getArea().setY(y);
	}

	/**
	 * Starts living (starts live time countdown)
	 */
	public void lives() {
		restartCountDown();
	}
	
	/**
	 * Collides this player with an {@code Obstacle}
	 * @param obstacle the collided {@code Obstacle}
	 */
	public void collides(Obstacle obstacle) {
		collider = obstacle;
		dies();
	}

	/**
	 * Moves this player along both axis (X and Y)
	 * @param dx the X axis step
	 * @param dy the Y axis step
	 */
	public void move(int dx, int dy) {
		MutableRectangle playerArea = model.getArea();
		playerArea.setX(playerArea.getX() + dx);
		playerArea.setY(playerArea.getY() + dy);
	}

	/**
	 * Checks whether the player's area is at least partially contained in the given circular area
	 * @param x the center (x coordinate) of a circular area
	 * @param y the center (y coordinate) of a circular area
	 * @param radius the distance between circle's center and edge
	 * @return {@code true} if its area intersects with the given circle, {@code false} otherwise.
	 */
	public boolean isWithin(int x, int y, int radius) {
		return model.getArea().intersects(x, y, radius);
	}
	
	/**
	 * Checks whether this player is at least partially in the given area
	 * @param player the player
	 * @return {@code true} if the player's area intersects with the given one, {@code false} otherwise.
	 */
	public boolean checkCollision(MutableRectangle area) {
		return model.getArea().intersects(area);
	}

	/**
	 * <p>
	 * a lifeless player cannot keep on player, the party should end
	 * </p>
	 * @return the lifeLess state
	 */
	public boolean isLifeLess() {
		return lifeLess;
	}

	/**
	 * @return the player's width
	 */
	public int getWidth() {
		return model.getArea().getWidth();
	}
	
	/**
	 * @return the player's height
	 */
	public int getHeight() {
		return model.getArea().getHeight();
	}
	
	@Override
	public List<ILayeredChildView> getChildren() {
		return childrenView;
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
