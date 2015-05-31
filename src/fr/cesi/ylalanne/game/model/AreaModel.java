package fr.cesi.ylalanne.game.model;

import java.awt.Rectangle;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Immutable Area model with a position(x, y), a size(width, height) and an image
 * <p>It has the following bound properties:</p>
 * <ul>
 * 	<li>x</li>
 * 	<li>y</li>
 * 	<li>width</li>
 * 	<li>height</li>
 * 	<li>imagePath</li>
 *  <li>secondImagePath</li>
 *  <li>usedHeight</li>
 * </ul>.
 */
public class AreaModel {
	private Rectangle rectangle;
	private String imagePath;
	private String secondImagePath;
	private int usedHeight;
	private PropertyChangeSupport propertyChange;
	
	
	/**
	 * Initialize an AreaModel.
	 */
	public AreaModel() {
		rectangle = new Rectangle();
		propertyChange = new PropertyChangeSupport(this);
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
	 * Gets the second image path.
	 *
	 * @return the secondImagePath
	 */
	public String getSecondImagePath() {
		return secondImagePath;
	}
	
	/**
	 * Sets the second image path.
	 *
	 * @param secondImagePath the secondImagePath to set
	 */
	public void setSecondImagePath(String secondImagePath) {
		String oldSecondImagePath = this.secondImagePath;
		this.secondImagePath = secondImagePath;
		propertyChange.firePropertyChange("secondImagePath", oldSecondImagePath, secondImagePath);
	}


	/**
	 * Gets the x.
	 *
	 * @return the x coordinate
	 */
	public int getX() {
		return rectangle.x;
	}
	
	/**
	 * Sets the x.
	 *
	 * @param x the x coordinate to set
	 */
	public void setX(int x) {
		int oldX = rectangle.x;
		rectangle.x = x;
		propertyChange.firePropertyChange("x", oldX, x);
	}

	/**
	 * Gets the y.
	 *
	 * @return the y coordinate
	 */
	public int getY() {
		return rectangle.y;
	}
	
	/**
	 * Sets the y.
	 *
	 * @param y the y coordinate to set
	 */
	public void setY(int y) {
		int oldY = rectangle.y;
		rectangle.y = y;
		propertyChange.firePropertyChange("y", oldY, y);
	}

	/**
	 * Gets the width.
	 *
	 * @return the width
	 */
	public int getWidth() {
		return rectangle.width;
	}
	
	/**
	 * Sets the width.
	 *
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		int oldWidth = rectangle.width;
		rectangle.width = width;
		propertyChange.firePropertyChange("width", oldWidth, width);
	}

	/**
	 * Gets the height.
	 *
	 * @return the height
	 */
	public int getHeight() {
		return rectangle.height;
	}
	
	/**
	 * Sets the height.
	 *
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		int oldHeight = rectangle.height;
		rectangle.height = height;
		propertyChange.firePropertyChange("height", oldHeight, height);
	}

	/**
	 * Gets the used height.
	 *
	 * @return the usedHeight
	 */
	public int getUsedHeight() {
		return usedHeight;
	}
	
	/**
	 * Sets the used height.
	 *
	 * @param usedHeight the usedHeight to set
	 */
	public void setUsedHeight(int usedHeight) {
		int oldUsedHeight = this.usedHeight;
		this.usedHeight = usedHeight;
		propertyChange.firePropertyChange("usedHeight", oldUsedHeight, usedHeight);
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
