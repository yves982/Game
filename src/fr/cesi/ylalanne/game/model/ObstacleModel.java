package fr.cesi.ylalanne.game.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import fr.cesi.ylalanne.game.model.geom.MutableRectangle;

/**
 * Model for an Obstacle with an {@link MutableRectangle area}, and an imagePath
 * <p> It has the following bound properties:</p>
 * <ul>
 * 	<li>imagePath</li>
 *  <li>x (indirect)</li>
 *  <li>y (indirect)</li>
 *   <li>dropped</li>
 * </ul>.
 */
public class ObstacleModel{
	private MutableRectangle area;
	private String imagePath;
	private int dx;
	private int dy;
	private boolean dropped;
	private boolean deadly;
	private boolean isStatic;
	private PropertyChangeSupport propertyChange;
	
	/**
	 * Instantiates a new obstacle model.
	 */
	public ObstacleModel() {
		area = new MutableRectangle();
		dx=0;
		dy=0;
		propertyChange = new PropertyChangeSupport(this);
	}
	
	/**
	 * Gets the dx.
	 *
	 * @return the dx
	 */
	public int getDx() {
		return dx;
	}
	
	/**
	 * Sets the dx.
	 *
	 * @param dx the dx to set
	 */
	public void setDx(int dx) {
		this.dx = dx;
	}
	
	/**
	 * Gets the dy.
	 *
	 * @return the dy
	 */
	public int getDy() {
		return dy;
	}
	
	/**
	 * Sets the dy.
	 *
	 * @param dy the dy to set
	 */
	public void setDy(int dy) {
		this.dy = dy;
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
	 * Gets the width.
	 *
	 * @return the area's width
	 * @see MutableRectangle#getWidth()
	 */
	public int getWidth() {
		return area.getWidth();
	}
	
	/**
	 * Gets the height.
	 *
	 * @return the area's height
	 * @see MutableRectangle#getHeight()
	 */
	public int getHeight() {
		return area.getHeight();
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
	 * <p>
	 * A dropped Obstacle cannot be climbed upon if it's not deadly, and it cannot be collided.
	 * </p>
	 * @return the dropped
	 */
	public boolean isDropped() {
		return dropped;
	}
	
	/**
	 * Sets the dropped.
	 *
	 * @param dropped the dropped to set
	 */
	public void setDropped(boolean dropped) {
		boolean oldDropped = this.dropped;
		this.dropped = dropped;
		propertyChange.firePropertyChange("dropped", oldDropped, dropped);
	}

	/**
	 * <p>
	 * A deadly obstacle cannot be climbed upon, it'd kill the player on contact
	 * </p>.
	 *
	 * @return the deadly
	 */
	public boolean isDeadly() {
		return deadly;
	}
	
	/**
	 * Sets the deadly.
	 *
	 * @param deadly the deadly to set
	 */
	public void setDeadly(boolean deadly) {
		this.deadly = deadly;
	}

	/**
	 * Checks if is static.
	 *
	 * @return the isStatic
	 */
	public boolean isStatic() {
		return isStatic;
	}
	
	/**
	 * Sets the static.
	 *
	 * @param isStatic the isStatic to set
	 */
	public void setStatic(boolean isStatic) {
		this.isStatic = isStatic;
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
