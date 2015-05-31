package fr.cesi.ylalanne.game.model.geom;

import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Mutable Rectangle, with proper bound properties (unlike java.awt.Rectangle)
 * <p>It has the following bound properties:</p>
 * <ul>
 * 	<li>x the x coordinate</li>
 *  <li>y the y coordinate</li>
 * </ul>
 */
public class MutableRectangle {
	private Rectangle rectangle;
	private PropertyChangeSupport propertyChange;

	/**
	 * Initializes a MutableRectangle.
	 */
	public MutableRectangle() {
		rectangle = new Rectangle();
		propertyChange = new PropertyChangeSupport(this);
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
		rectangle.width = width;
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
		rectangle.height = height;
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
	/**
	 * Checks if this obstacle intersects with the given rectangle.
	 *
	 * @param otherRectangle the {@link MutableRectangle} checked for intersection with current instance
	 * @return true if the intersection of these {@code Rectangle} is not empty, false otherwise
	 * @see java.awt.Rectangle#intersects(java.awt.Rectangle)
	 */
	public boolean intersects(MutableRectangle otherRectangle) {
		return rectangle.intersects(otherRectangle.rectangle);
	}
	
	/**
	 * Checks if this obstacle is at least partly within the given circle.
	 *
	 * @param x the x coordinate of a circle
	 * @param y the y coordinate of a circle
	 * @param radius the radius of a circle
	 * @return true if this {@code Rectangle} intersects with the given circle, false otherwise.
	 */
	public boolean intersects(int x, int y, int radius) {
		Ellipse2D.Double ellipse2d = new Ellipse2D.Double(x, y, 2*radius, 2*radius);
		return ellipse2d.intersects(rectangle);
	}
	
}