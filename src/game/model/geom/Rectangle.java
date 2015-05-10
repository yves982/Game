package game.model.geom;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;


/**
 * Immutable Rectangle (unlike java.awt.Rectangle)
 *
 */
public class Rectangle {
	private java.awt.Rectangle rectangle;
	
	/**
	 * Initialize a {@code Rectangle}
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public Rectangle(int x, int y, int width, int height) {
		rectangle = new java.awt.Rectangle(x, y, width, height);
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return rectangle.x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return rectangle.y;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return rectangle.width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return rectangle.height;
	}

	/**
	 * @param x the x to set
	 * @return a new Rectangle at specified x location
	 */
	public Rectangle changeX(int x) {
		return new Rectangle(x, rectangle.y, rectangle.width, rectangle.height);
	}
	
	/**
	 * @param y the y to set
	 * @return a new Rectangle at specified y location
	 */
	public Rectangle changeY(int y) {
		return new Rectangle(rectangle.x, y, rectangle.width, rectangle.height);
	}

	/**
	 * @param rectangle
	 * @return
	 * @see java.awt.Rectangle#contains(java.awt.Rectangle)
	 */
	public boolean contains(Rectangle rectangle) {
		return this.rectangle.contains(rectangle.rectangle);
	}

	/**
	 * @param rectangle
	 * @return
	 * @see java.awt.Rectangle#intersects(java.awt.Rectangle)
	 */
	public boolean intersects(Rectangle rectangle) {
		return this.rectangle.intersects(rectangle.rectangle);
	}

	/**
	 * @param x the x coordinate of a circle
	 * @param y the y coordinate of a circle
	 * @param radius the radius of a circle
	 * @return true if this {@code Rectangle} intersects with the given circle, false otherwise.
	 */
	public boolean intersects(int x, int y, int radius) {
		Ellipse2D.Double ellipse2d = new Ellipse2D.Double(x, y, x+radius, y+radius);
		Rectangle2D.Double rectangle2d = new Rectangle2D.Double(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
		return ellipse2d.intersects(rectangle2d);
	}
}
