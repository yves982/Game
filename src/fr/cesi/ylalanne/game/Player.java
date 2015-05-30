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

import fr.cesi.ylalanne.contracts.IHighScoreController;
import fr.cesi.ylalanne.contracts.ILayeredChildrenController;
import fr.cesi.ylalanne.contracts.ui.ILayeredChildView;
import fr.cesi.ylalanne.game.model.PlayerModel;
import fr.cesi.ylalanne.game.model.geom.MutableRectangle;
import fr.cesi.ylalanne.game.ui.MoveRequestEvent;
import fr.cesi.ylalanne.game.ui.MovesListener;
import fr.cesi.ylalanne.game.ui.PlayerInfosView;
import fr.cesi.ylalanne.game.ui.PlayerView;
import fr.cesi.ylalanne.utils.Range;

/**
 * The Player : a moving area which can be checked for collisions and moves on command
 * <p>It has the following bound properties:</p>
 * <ul>
 * 	<li>liveLess</li>
 * <li>lives</li>
 *  <li>x</li>
 *  <li>y</li>
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
	private int startX;
	private int startY;
	private Range<Integer> xBounds;
	private Range<Integer> yBounds;
	private IHighScoreController highScore;
	private MoveRequestEvent currentMoveRequest;
	
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
		model.setWinning(false);
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
		propertyChange.firePropertyChange("lives", lives,  model.getLives());
		
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
		currentMoveRequest = moveRequest;
		
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
		int oldX = playerArea.getX();
		int newX = playerArea.getX() + movesStep;
		if(!xBounds.isIn(newX) || !xBounds.isIn(newX + playerArea.getWidth())) {
			return;
		}
		playerArea.setX(newX);
		propertyChange.firePropertyChange("x", oldX, playerArea.getX());
	}

	private void moveY(int movesStep, MutableRectangle playerArea) {
		int oldY = playerArea.getY();
		int newY = playerArea.getY() + movesStep;
		if(!yBounds.isIn(newY) || !yBounds.isIn(newY + playerArea.getHeight())) {
			return;
		}
		playerArea.setY(playerArea.getY() + movesStep);
		propertyChange.firePropertyChange("y", oldY, playerArea.getY());
	}
	


	private void stopMoving(MoveRequestEvent moveRequest) {
		if(liveLess || moveRequest == null) {
			return;
		}
		switch(moveRequest) {
			case UP:
			case DOWN:
				if(moveYFuture != null) {
					moveYFuture.cancel(false);
				}
				break;
			case RIGHT:
			case LEFT:
				if(moveXFuture != null) {
					moveXFuture.cancel(false);
				}
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
	 * @param xBounds min/max x coordinates to stay in the World
	 * @param yBounds min/max y coordinates to stay in the World
	 */
	public Player(int maxLives, int maxLeftTimeMs, int movesStep, Range<Integer> xBounds, Range<Integer> yBounds) {
		childrenView = new ArrayList<ILayeredChildView>();
		movesListener = new MovesListener();
		setupViewAndModel(maxLives, maxLeftTimeMs, movesStep);
		reservedHeight = infosView.getHeight();
		propertyChange = new PropertyChangeSupport(this);
		setupExecutors();
		collider = null;
		this.xBounds = xBounds;
		this.yBounds = yBounds;
		playerView.addPropertyChangeListener(this);
		startX = -43;
		startY=-43;
	}

	/**
	 * Places the player in the world                                                                               
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @return true if the requested position would place the Player in its bounds, false otherwise
	 */
	public boolean position(int x, int y) {
		MutableRectangle playerArea = model.getArea();
		
		if(startY == -43) {
			playerArea.setY(y);
			startY = y;
		} else if(yBounds.isIn(y) && xBounds.isIn(y + playerArea.getHeight())) {
			playerArea.setY(y);
		}
		
		
		boolean inXBounds = xBounds.isIn(x) && xBounds.isIn(x + playerArea.getWidth());
		if(inXBounds) {
			playerArea.setX(x);
			if(startX == -43) {
				startX = x;
			}
		}
		return inXBounds;
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
	 * Cleans the resources used by the Player
	 */
	public void clean() {
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
	 * Kills the Player
	 */
	public void kill() {
		dies();
		if(!liveLess) {
			model.getArea().setX(startX);
			model.getArea().setY(startY);
		}
		collider = null;
	}
	
	/**
	 * Collides this player with an {@code Obstacle}
	 * @param obstacle the collided {@code Obstacle}
	 */
	public void collides(Obstacle obstacle) {
		collider = obstacle;
		if(collider.isDeadly()) {
			kill();
		}
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
	 * @return true if the Player is at least partially within an Obstacle, false otherwise
	 */
	public boolean isCollided() {
		return collider != null;
	}
	
	/**
	 * @return the winning state of the Player (true if he's won, false otherwise)
	 */
	public boolean isWinning() {
		return model.isWinning();
	}
	
	/**
	 * @return the x coordinate of the player
	 */
	public int getX() {
		return model.getArea().getX();
	}
	
	/**
	 * @return the y coordinate of the Player
	 */
	public int getY() {
		return model.getArea().getY();
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
	
	/**
	 * Get the Obstacle in collision with the Player, if it is collided, null otherwise
	 */
	public Obstacle getCollider() {
		return collider;
	}

	/**
	 * @return the highScore
	 */
	public IHighScoreController getHighScore() {
		return highScore;
	}
	/**
	 * @param highScore the highScore to set
	 */
	public void setHighScore(IHighScoreController highScore) {
		this.highScore = highScore;
	}

	/**
	 * @return the currentMoveRequest
	 */
	public MoveRequestEvent getCurrentMoveRequest() {
		return currentMoveRequest;
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

	/**
	 * Makes the Player win the game
	 */
	public void win() {
		int score = model.getScore();
		int remainingLiveTimeMs = model.getRemainingLiveTimeMs();
		int maxLiveTimeMs = model.getMaxLiveTimeMs();
		int lives = model.getLives();
		model.setScore(score * remainingLiveTimeMs + (maxLiveTimeMs / 1000) * (lives -1));
		model.setWinning(true);
		infosView.showWin();
		highScore.askName(model.getScore());
		clean();
	}

	/**
	 * Increases the player's score
	 * @param amount the amount to increase the score by
	 */
	public void gains(int amount) {
		model.setScore(model.getScore() + amount);
	}
	
	
}
