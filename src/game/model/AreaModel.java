package game.model;

import java.awt.Image;
import java.awt.Rectangle;


/**
 * Immutable Area model with a {@code Rectangle} and an imagePath
 */
public class AreaModel {
	private Rectangle rectangle;
	private Image image;
	
	/**
	 * Initialize an AreaModel
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param imagePath
	 */
	public AreaModel(int x, int y, int width, int height, Image image) {
		rectangle = new Rectangle(x, y, width, height);
		this.image = image;
	}


	/**
	 * @return the image
	 */
	public Image getImage() {
		return image;
	}


	/**
	 * @return
	 * @see java.awt.Rectangle#getX()
	 */
	public int getX() {
		return rectangle.x;
	}


	/**
	 * @return
	 * @see java.awt.Rectangle#getY()
	 */
	public int getY() {
		return rectangle.y;
	}


	/**
	 * @return
	 * @see java.awt.Rectangle#getWidth()
	 */
	public int getWidth() {
		return rectangle.width;
	}


	/**
	 * @return
	 * @see java.awt.Rectangle#getHeight()
	 */
	public int getHeight() {
		return rectangle.height;
	}
}
