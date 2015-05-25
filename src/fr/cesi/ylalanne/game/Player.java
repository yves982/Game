package fr.cesi.ylalanne.game;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import fr.cesi.ylalanne.contracts.ILayeredChildrenController;
import fr.cesi.ylalanne.contracts.ui.ILayeredChildView;
import fr.cesi.ylalanne.game.model.PlayerModel;
import fr.cesi.ylalanne.game.model.geom.MutableRectangle;
import fr.cesi.ylalanne.game.ui.MoveRequestEvent;
import fr.cesi.ylalanne.game.ui.MovesListener;
import fr.cesi.ylalanne.game.ui.PlayerInfosView;
import fr.cesi.ylalanne.game.ui.PlayerView;

/**
 * The Player : a moving area which can be checked for collisions and moves on command
 * <p>It has the following bound properties:</p>
 * <ul>
 * 	<li>liveLess</li>
 * </ul>
 */
public class Player implements ILayeredChildrenController, PropertyChangeListener {
	private PlayerModel model;
	private PlayerView playerView;
	private MovesListener movesListener;
	private PlayerInfosView infosView;
	private Obstacle collider;
	private ScheduledFuture<?> moveXFuture;
	private ScheduledFuture<?> moveYFuture;
	private List<ILayeredChildView> childrenView;
	private ScheduledExecutorService countDownExecutor;
	private ScheduledExecutorService movesExecutor;
	private boolean liveLess;
	private PropertyChangeSupport propertyChange;
	private int reservedHeight;
	
	private void fillChildrenView() {
		childrenView.add(infosView);
		childrenView.add(playerView);
	}

	private void fillModel(int maxLives, int maxLeftTimeMs, int movesStep) {
		model.setImagePath("/player/player.png");
		model.setLives(maxLives);
		model.setMaxLiveTimeMs(maxLeftTimeMs);
		model.setRemainingLiveTimeMs(maxLeftTimeMs);
		model.setMovesStep(movesStep);
	}

	private void countDown() {
		int remainingLiveTimeMs = model.getRemainingLiveTimeMs();
		remainingLiveTimeMs -= 300;
		
		if(remainingLiveTimeMs < 300) {
			dies();
			restartCountDown();
		} else {
			model.setRemainingLiveTimeMs(remainingLiveTimeMs);
		}
	}

	private void restartCountDown() {
		if(!countDownExecutor.isTerminated()) {
			try {
				countDownExecutor.shutdown();
				countDownExecutor.awaitTermination(500, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		countDownExecutor = Executors.newSingleThreadScheduledExecutor();
		countDownExecutor.scheduleAtFixedRate(this::countDown, 0, 300, TimeUnit.MILLISECONDS);
	}

	private void dies() {
		int lives = model.getLives();
		model.setLives(lives -1);
		model.setRemainingLiveTimeMs(model.getMaxLiveTimeMs());
		
		if(model.getLives() <= 0) {
			Object oldLifeLess = liveLess;
			liveLess = true;
			propertyChange.firePropertyChange("liveLess", oldLifeLess , true);
			model.setRemainingLiveTimeMs(0);
			countDownExecutor.shutdown();
			movesExecutor.shutdown();
			try {
				countDownExecutor.awaitTermination(500, TimeUnit.MILLISECONDS);
				movesExecutor.awaitTermination(500, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void updateWidth(int width) {
		model.getArea().setWidth(width);
	}

	private void updateHeight(int height) {
		model.getArea().setHeight(height);
	}
	
	
	private void move(MoveRequestEvent moveRequest) {
		if(liveLess || moveRequest == null) {
			return;
		}
		
		int movesStep = model.getMovesStep();
		MutableRectangle playerArea = model.getArea();
		switch(moveRequest) {
			case UP:
				if((moveYFuture == null || moveYFuture.isDone())) {
					moveYFuture = movesExecutor.scheduleAtFixedRate(() -> moveY(-movesStep, playerArea),
						0, 300, TimeUnit.MILLISECONDS);
				}
				break;
			case DOWN:
				if((moveYFuture == null || moveYFuture.isDone())) {
					moveYFuture = movesExecutor.scheduleAtFixedRate(() -> moveY(movesStep, playerArea),
						0, 300, TimeUnit.MILLISECONDS);
				}
				break;
			case LEFT:
				if((moveXFuture == null || moveXFuture.isDone())) {
					moveXFuture = movesExecutor.scheduleAtFixedRate(() -> moveX(-movesStep, playerArea),
						0, 300, TimeUnit.MILLISECONDS);
				}
				break;
			case RIGHT:
				if((moveXFuture == null || moveXFuture.isDone())) {
					moveXFuture = movesExecutor.scheduleAtFixedRate(() -> moveX(movesStep, playerArea),
						0, 300, TimeUnit.MILLISECONDS);
				}
				break;
		}
	}

	private void moveX(int movesStep, MutableRectangle playerArea) {
		playerArea.setX(playerArea.getX() + movesStep);
	}

	private void moveY(int movesStep, MutableRectangle playerArea) {
		playerArea.setY(playerArea.getY() + movesStep);
	}
	


	private void stopMoving(MoveRequestEvent moveRequest) {
		if(liveLess || moveRequest == null) {
			return;
		}
		switch(moveRequest) {
			case UP:
			case DOWN:
					moveYFuture.cancel(false);
				break;
			case RIGHT:
			case LEFT:
					moveXFuture.cancel(false);
				break;
		}
		
		
		try {
			movesExecutor.shutdown();
			movesExecutor.awaitTermination(500, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			if(!liveLess) {
				movesExecutor = Executors.newSingleThreadScheduledExecutor();
			}
		}
		
	}

	private void handleMoves() {
		movesListener.defineHandler(this::move);
		movesListener.defineHandler(this::stopMoving, false);
		
		playerView.addKeyListener(movesListener);
	}

	// we can't call this within live as we'd loose view parents, defined through dependency injection
	private void setupViews() {
		infosView = new PlayerInfosView(model);
		infosView.build();
		playerView = new PlayerView(model);
		playerView.build();
		fillChildrenView();
	}

	private void setupViewAndModel(int maxLives, int maxLeftTimeMs, int movesStep) {
		model = new PlayerModel();
		liveLess = true;
		setupViews();
		fillModel(maxLives, maxLeftTimeMs, movesStep);
	}

	private void setupExecutors() {
		countDownExecutor = Executors.newSingleThreadScheduledExecutor();
		movesExecutor = Executors.newSingleThreadScheduledExecutor();
	}

	/**
	 * Initialize a Player
	 * @param maxLives the maximum number of lives before the fr.cesi.ylalanne.game is over
	 * @param maxLeftTimeMs the maximum time every life can last (in milliseconds)
	 * @param movesStep the step for all moves
	 */
	public Player(int maxLives, int maxLeftTimeMs, int movesStep) {
		childrenView = new ArrayList<ILayeredChildView>();
		movesListener = new MovesListener();
		setupViewAndModel(maxLives, maxLeftTimeMs, movesStep);
		reservedHeight = infosView.getHeight();
		propertyChange = new PropertyChangeSupport(this);
		setupExecutors();
		playerView.addPropertyChangeListener(this);
	}

	/**
	 * Places the player in the world                                                                               
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
		if(movesExecutor != null) {
			try {
				movesExecutor.shutdown();
				movesExecutor.awaitTermination(500,  TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		movesExecutor = Executors.newSingleThreadScheduledExecutor();
		restartCountDown();
		handleMoves();
		liveLess = false;
	}

	/**
	 * Reset the player to his original state
	 */
	public void kill() {
		try {
			movesExecutor.shutdown();
			movesExecutor.awaitTermination(500, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		try {
			countDownExecutor.shutdown();
			countDownExecutor.awaitTermination(500, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		playerView.removeKeyListener(movesListener);
		playerView.removePropertyChangeListener(this);
		liveLess = true;
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
	 * @param area the area to check for collision with the player
	 * @return {@code true} if the player's area intersects with the given one, {@code false} otherwise.
	 */
	public boolean checkCollision(MutableRectangle area) {
		return model.getArea().intersects(area);
	}

	/**
	 * <p>
	 * a lifeless player cannot keep on player, the party should end
	 * </p>
	 * @return the liveLess state
	 */
	public boolean isLifeLess() {
		return liveLess;
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
	
	/**
	 * Gets the player's reserved height (reserved to display its infos)
	 * @return the reservedHeight
	 */
	public int getReservedHeight() {
		return reservedHeight;
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
