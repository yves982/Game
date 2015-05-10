package game.model;

import game.model.geom.MutableRectangle;

import java.awt.Image;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class PlayerModel {
	private MutableRectangle area;
	private Image image;
	private boolean collided;
	private int score;
	private int lives;
	private PropertyChangeSupport propertyChange;
	
	public PlayerModel(int x, int y, int width, int height, int lives, Image image) {
		area = new MutableRectangle(x, y, width, height);
		this.image = image;
		score = 0;
		this.lives = lives;
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
	 * @return the image
	 */
	public Image getImage() {
		return image;
	}
	/**
	 * @param image the image to set
	 */
	public void setImage(Image image) {
		Image oldImage = this.image;
		this.image = image;
		propertyChange.firePropertyChange("image", oldImage, image);
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
