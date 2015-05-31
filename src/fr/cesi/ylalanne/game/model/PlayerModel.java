package fr.cesi.ylalanne.game.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import fr.cesi.ylalanne.game.model.geom.MutableRectangle;

/**
 * Player model
 * <p>It has the following bound properties:</p>
 * <ul>
 * 	<li>collided</li>
 * 	<li>imagePath</li>
 * 	<li>score</li>
 * 	<li>lives</li>
 * 	<li>x (indirect)</li>
 * 	<li>y (indirect)</li>
 *  <li>remainingLiveTimeMs</li>
 * </ul>.
 */
public class PlayerModel {
	private MutableRectangle area;
	private String imagePath;
	private boolean collided;
	private boolean winning;
	private int score;
	private int lives;
	private int remainingLiveTimeMs;
	private int maxLiveTimeMs;
	private int movesStep;
	private PropertyChangeSupport propertyChange;
	
	/**
	 * Instantiates a new player model.
	 */
	public PlayerModel() {
		area = new MutableRectangle();
		score = 0;
		this.lives = 0;
		collided = false;
		propertyChange = new PropertyChangeSupport(this);
	}
	
	/**
	 * Checks if is collided.
	 *
	 * @return the collided
	 */
	public boolean isCollided() {
		return collided;
	}
	
	/**
	 * Sets the collided.
	 *
	 * @param collided the collided to set
	 */
	public void setCollided(boolean collided) {
		boolean oldCollided = this.collided;
		this.collided = collided;
		propertyChange.firePropertyChange("collided", oldCollided, collided);
	}

	/**
	 * Gets the image path.
	 *
	 * @return the imagePath
	 */
	public String getImagePath() {
		return imagePath;
	}
	
	/**
	 * Sets the image path.
	 *
	 * @param imagePath the imagePath to set
	 */
	public void setImagePath(String imagePath) {
		String oldImagePath = this.imagePath;
		this.imagePath = imagePath;
		propertyChange.firePropertyChange("imagePath", oldImagePath, imagePath);
	}
	
	/**
	 * Gets the area.
	 *
	 * @return the area
	 */
	public MutableRectangle getArea() {
		return area;
	}

	/**
	 * Gets the score.
	 *
	 * @return the score
	 */
	public int getScore() {
		return score;
	}
	
	/**
	 * Sets the score.
	 *
	 * @param score the score to set
	 */
	public void setScore(int score) {
		int oldScore = this.score;
		this.score = score;
		propertyChange.firePropertyChange("score", oldScore, score);
	}

	/**
	 * Gets the lives.
	 *
	 * @return the lives
	 */
	public int getLives() {
		return lives;
	}
	
	/**
	 * Sets the lives.
	 *
	 * @param lives the lives to set
	 */
	public void setLives(int lives) {
		int oldLives = this.lives;
		this.lives = lives;
		propertyChange.firePropertyChange("lives", oldLives, lives);
	}
	
	/**
	 * Gets the remaining live time ms.
	 *
	 * @return the remainingLiveTimeMs
	 */
	public int getRemainingLiveTimeMs() {
		return remainingLiveTimeMs;
	}
	
	/**
	 * Sets the remaining live time ms.
	 *
	 * @param remainingLiveTimeMs the remainingLiveTimeMs to set
	 */
	public void setRemainingLiveTimeMs(int remainingLiveTimeMs) {
		int oldRemainingLiveTimeMs = this.remainingLiveTimeMs;
		this.remainingLiveTimeMs = remainingLiveTimeMs;
		propertyChange.firePropertyChange("remainingLiveTimeMs", oldRemainingLiveTimeMs, remainingLiveTimeMs);
	}

	/**
	 * Gets the max live time ms.
	 *
	 * @return the maxLiveTimeMs
	 */
	public int getMaxLiveTimeMs() {
		return maxLiveTimeMs;
	}

	/**
	 * Sets the max live time ms.
	 *
	 * @param maxLiveTimeMs the maxLiveTimeMs to set
	 */
	public void setMaxLiveTimeMs(int maxLiveTimeMs) {
		this.maxLiveTimeMs = maxLiveTimeMs;
	}

	/**
	 * Gets the moves step.
	 *
	 * @return the movesStep
	 */
	public int getMovesStep() {
		return movesStep;
	}
	
	/**
	 * Sets the moves step.
	 *
	 * @param movesStep the movesStep to set
	 */
	public void setMovesStep(int movesStep) {
		this.movesStep = movesStep;
	}

	/**
	 * Checks if is winning.
	 *
	 * @return the winning
	 */
	public boolean isWinning() {
		return winning;
	}
	
	/**
	 * Sets the winning.
	 *
	 * @param winning the winning to set
	 */
	public void setWinning(boolean winning) {
		this.winning = winning;
	}

	/**
	 * Adds the property change listener.
	 *
	 * @param listener the listener
	 * @see java.beans.PropertyChangeSupport#addPropertyChangeListener(java.beans.PropertyChangeListener)
	 * @see fr.cesi.ylalanne.game.model.geom.MutableRectangle#addPropertyChangeListener(java.beans.PropertyChangeListener)
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertyChange.addPropertyChangeListener(listener);
		area.addPropertyChangeListener(listener);
	}
	/**
	 * Removes the property change listener.
	 *
	 * @param listener the listener
	 * @see java.beans.PropertyChangeSupport#removePropertyChangeListener(java.beans.PropertyChangeListener)
	 * @see fr.cesi.ylalanne.game.model.geom.MutableRectangle#removePropertyChangeListener(java.beans.PropertyChangeListener)
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyChange.removePropertyChangeListener(listener);
		area.removePropertyChangeListener(listener);
	}
	/**
	 * Adds the property change listener.
	 *
	 * @param propertyName the property name
	 * @param listener the listener
	 * @see java.beans.PropertyChangeSupport#addPropertyChangeListener(java.lang.String, java.beans.PropertyChangeListener)
	 * @see fr.cesi.ylalanne.game.model.geom.MutableRectangle#addPropertyChangeListener(java.lang.String, java.beans.PropertyChangeListener)
	 */
	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		propertyChange.addPropertyChangeListener(propertyName, listener);
		area.addPropertyChangeListener(propertyName, listener);
	}
	/**
	 * Removes the property change listener.
	 *
	 * @param propertyName the property name
	 * @param listener the listener
	 * @see java.beans.PropertyChangeSupport#removePropertyChangeListener(java.lang.String, java.beans.PropertyChangeListener)
	 * @see fr.cesi.ylalanne.game.model.geom.MutableRectangle#removePropertyChangeListener(java.lang.String, java.beans.PropertyChangeListener)
	 */
	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		propertyChange.removePropertyChangeListener(propertyName, listener);
		area.removePropertyChangeListener(propertyName, listener);
	}
}
