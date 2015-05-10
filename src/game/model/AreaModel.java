package game.model;

import game.model.geom.Rectangle;

import java.awt.Image;


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
	 * @return the rectangle
	 */
	public Rectangle getRectangle() {
		return rectangle;
	}


	/**
	 * @return the image
	 */
	public Image getImage() {
		return image;
	}
}
