package game.model;

import game.model.geom.MutableRectangle;

import java.awt.Image;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.nio.file.Paths;

import main.ResourcesManager;

/**
 * Model for an Obstacle with an {@link MutableRectangle area}, and an imagePath
 * <p>
 * It has the following bound properties:
 * <ul>
 * 	<li>imagePath</li>
 *  <li>x</li>
 *  <li>y</li>
 * </ul>
 * </p>
 */
public class ObstacleModel{
	private MutableRectangle area;
	private String imagePath;
	private int dx;
	private int dy;
	private PropertyChangeSupport propertyChange;
	
	public ObstacleModel() {
		area = new MutableRectangle();
		dx=0;
		dy=0;
		propertyChange = new PropertyChangeSupport(this);
	}
	
	/**
	 * @return the dx
	 */
	public int getDx() {
		return dx;
	}
	/**
	 * @param dx the dx to set
	 */
	public void setDx(int dx) {
		this.dx = dx;
	}
	
	/**
	 * @return the dy
	 */
	public int getDy() {
		return dy;
	}
	/**
	 * @param dy the dy to set
	 */
	public void setDy(int dy) {
		this.dy = dy;
	}
	
	/**
	 * @return the area
	 */
	public MutableRectangle getArea() {
		return area;
	}
	
	/**
	 * @return the area's width
	 * @see MutableRectangle#getWidth()
	 */
	public int getWidth() {
		return area.getWidth();
	}
	
	/**
	 * @return the area's height
	 * @see MutableRectangle#getHeight()
	 */
	public int getHeight() {
		return area.getHeight();
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
	 * @see game.model.geom.MutableRectangle#removePropertyChangeListener(java.lang.String, java.beans.PropertyChangeListener)
	 */
	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		propertyChange.removePropertyChangeListener(propertyName, listener);
		area.removePropertyChangeListener(propertyName, listener);
	}
}
