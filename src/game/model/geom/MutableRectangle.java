package game.model.geom;

import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Mutable Rectangle, with proper bound properties (unlike java.awt.Rectangle)
 * <p>
 * It has the following bound properties:
 * <ul>
 * 	<li>x the x coordinate</li>
 *  <li>y the y coordinate</li>
 * </ul>
 * </p>
 */
public class MutableRectangle {
	private Rectangle rectangle;
	private PropertyChangeSupport propertyChange;

	/**
	 * Initialize a MutableRectangle
	 */
	public MutableRectangle() {
		rectangle = new Rectangle();
		propertyChange = new PropertyChangeSupport(this);
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
		rectangle.width = width;
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
		rectangle.height = height;
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
	 * @param otherRectangle
	 * @return true if the intersection of these {@code Rectangle} is not empty, false otherwise
	 * @see java.awt.Rectangle#intersects(java.awt.Rectangle)
	 */
	public boolean intersects(MutableRectangle otherRectangle) {
		return rectangle.intersects(otherRectangle.rectangle);
	}
	
	/**
	 * @param x the x coordinate of a circle
	 * @param y the y coordinate of a circle
	 * @param radius the radius of a circle
	 * @return true if this {@code Rectangle} intersects with the given circle, false otherwise.
	 */
	public boolean intersects(int x, int y, int radius) {
		Ellipse2D.Double ellipse2d = new Ellipse2D.Double(x, y, x+radius, y+radius);
		return ellipse2d.intersects(rectangle);
	}
	
}