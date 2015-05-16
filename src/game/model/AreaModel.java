package game.model;

import java.awt.Rectangle;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;


/**
 * Immutable Area model with a position(x, y), a size(width, height) and an image
 * <p>
 * It has the following bound properties:
 * <li>x</li>
 * <li>y</li>
 * <li>width</li>
 * <li>height</li>
 * <li>imagePath</li>
 * </p>
 */
public class AreaModel {
	private Rectangle rectangle;
	private String imagePath;
	private PropertyChangeSupport propertyChange;
	
	/**
	 * Initialize an AreaModel
	 */
	public AreaModel() {
		rectangle = new Rectangle();
		propertyChange = new PropertyChangeSupport(this);
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
	 * @return the x coordinate
	 */
	public int getX() {
		return rectangle.x;
	}
	/**
	 * @param x the x coordinate to set
	 */
	public void setX(int x) {
		int oldX = rectangle.x;
		rectangle.x = x;
		propertyChange.firePropertyChange("x", oldX, x);
	}

	/**
	 * @return the y coordinate
	 */
	public int getY() {
		return rectangle.y;
	}
	/**
	 * @param y the y coordinate to set
	 */
	public void setY(int y) {
		int oldY = rectangle.y;
		rectangle.y = y;
		propertyChange.firePropertyChange("y", oldY, y);
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return rectangle.width;
	}
	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		int oldWidth = rectangle.width;
		rectangle.width = width;
		propertyChange.firePropertyChange("width", oldWidth, width);
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return rectangle.height;
	}
	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		int oldHeight = rectangle.height;
		rectangle.height = height;
		propertyChange.firePropertyChange("height", oldHeight, height);
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
