package game.model.geom;


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
}

