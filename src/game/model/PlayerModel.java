package game.model;

import game.model.geom.MutableRectangle;

import java.awt.Image;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import lang.LocaleManager;

/**
 * Player model
 * <p>
 * 	It has the following bound properties:
 * <ul>
 * 	<li>collided</li>
 * 	<li>imagePath</li>
 * 	<li>score</li>
 * 	<li>lives</li>
 * 	<li>x</li>
 * 	<li>y</li>
 * </ul>
 * </p>
 */
public class PlayerModel {
	private MutableRectangle area;
	private String imagePath;
	private boolean collided;
	private int score;
	private int lives;
	private PropertyChangeSupport propertyChange;
	
	public PlayerModel() {
		area = new MutableRectangle();
		score = 0;
		this.lives = 0;
		collided = false;
		propertyChange = new PropertyChangeSupport(this);
	}
	
	/**
	 * @return the collided
	 */
	public boolean isCollided() {
		return collided;
	}
	/**
	 * @param collided the collided to set
	 */
	public void setCollided(boolean collided) {
		boolean oldCollided = this.collided;
		this.collided = collided;
		propertyChange.firePropertyChange("collided", oldCollided, collided);
	}

	/**
	 * @return the imagePath
	 */
	public String getImagePath() {
		return imagePath;
	}
	/**
	 * @param imagePath the imagePath to set
	 */
	public void setImagePath(String imagePath) {
		String oldImagePath = this.imagePath;
		this.imagePath = imagePath;
		propertyChange.firePropertyChange("imagePath", oldImagePath, imagePath);
	}
	
	/**
	 * @return the area
	 */
	public MutableRectangle getArea() {
		return area;
	}

	/**
	 * @return the score
	 */
	public int getScore() {
		return score;
	}
	/**
	 * @param score the score to set
	 */
	public void setScore(int score) {
		int oldScore = this.score;
		this.score = score;
		propertyChange.firePropertyChange("score", oldScore, score);
	}

	/**
	 * @return the lives
	 */
	public int getLives() {
		return lives;
	}
	/**
	 * @param lives the lives to set
	 */
	public void setLives(int lives) {
		int oldLives = this.lives;
		this.lives = lives;
		propertyChange.firePropertyChange("lives", oldLives, lives);
	}
	
	/**
	 * @param listener
	 * @see java.beans.PropertyChangeSupport#addPropertyChangeListener(java.beans.PropertyChangeListener)
	 * @see game.model.geom.MutableRectangle#addPropertyChangeListener(java.beans.PropertyChangeListener)
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertyChange.addPropertyChangeListener(listener);
		area.addPropertyChangeListener(listener);
	}

	/**
	 * @param listener
	 * @see java.beans.PropertyChangeSupport#removePropertyChangeListener(java.beans.PropertyChangeListener)
	 * @see game.model.geom.MutableRectangle#removePropertyChangeListener(java.beans.PropertyChangeListener)
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyChange.removePropertyChangeListener(listener);
		area.removePropertyChangeListener(listener);
	}

	/**
	 * @param propertyName
	 * @param listener
	 * @see java.beans.PropertyChangeSupport#addPropertyChangeListener(java.lang.String, java.beans.PropertyChangeListener)
	 * @see game.model.geom.MutableRectangle#addPropertyChangeListener(java.lang.String, java.beans.PropertyChangeListener)
	 */
	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		propertyChange.addPropertyChangeListener(propertyName, listener);
		area.addPropertyChangeListener(propertyName, listener);
	}

	/**
	 * @param propertyName
	 * @param listener
	 * @see java.beans.PropertyChangeSupport#removePropertyChangeListener(java.lang.String, java.beans.PropertyChangeListener)
	 * @see game.model.MutableRectangle#removePropertyChangeListener(java.lang.String, java.beans.PropertyChangeListener)
	 */
	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		propertyChange.removePropertyChangeListener(propertyName, listener);
		area.removePropertyChangeListener(propertyName, listener);
	}
}
