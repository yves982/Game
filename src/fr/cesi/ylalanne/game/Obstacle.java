package fr.cesi.ylalanne.game;

import fr.cesi.ylalanne.game.model.ObstacleModel;
import fr.cesi.ylalanne.game.model.geom.MutableRectangle;
import fr.cesi.ylalanne.game.ui.ObstacleView;
import fr.cesi.ylalanne.main.IChildController;
import fr.cesi.ylalanne.main.ui.IChildView;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * An Obstacle : basically, a moving area, which can be checked for collisions
 */
public class Obstacle implements IChildController, PropertyChangeListener {
	private ObstacleKind kind;
	private ObstacleModel model;
	private ObstacleView view;
	
	private void updateWidth(int width) {
		model.getArea().setWidth(width);
	}

	private void updateHeight(int height) {
		model.getArea().setHeight(height);
	}

	/**
	 * Initialize an Obstacle
	 * @param kind the obstacle's kind
	 */
	public Obstacle(ObstacleKind kind) {
		this.kind = kind;
		model = new ObstacleModel();
		view = new ObstacleView(model);
		view.build();
		model.setImagePath(kind.getImagePath());
		view.addPropertyChangeListener(this);
	}
	
	/**
	 * @return the obstacle's kind
	 */
	public ObstacleKind getKind() {
		return kind;
	}

	/**
	 * Places this obstacle in the world
	 * <p>
	 * 	<b>Note: the obstacle must be added to the world before setting its position</b>
	 * </p>
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	public void startPosition(int x, int y) {
		MutableRectangle obstacleArea = model.getArea();
		obstacleArea.setX(x);
		obstacleArea.setY(y);
	}
	
	/**
	 * defines moving steps along X and Y axis
	 * @param dx X axis step
	 * @param dy Y axis step
	 */
	public void defineSteps(int dx, int dy) {
		model.setDx(dx);
		model.setDy(dy);
	}
	
	/**
	 * Moves the obstacle by predefined steps (dx on the X axis and dy on the Y one)
	 */
	public void move() {
		MutableRectangle obstacleArea = model.getArea();
		obstacleArea.setX(obstacleArea.getX() + model.getDx());
		obstacleArea.setY(obstacleArea.getY() + model.getDy());
	}
	
	/**
	 * Checks whether the obstacle's area is at least partially contained in the a given circular area
	 * @param x the center (x coordinate) of a circular area
	 * @param y the center (y coordinate) of a circular area
	 * @param radius the distance between circle's center and edge
	 * @return {@code true} if the obstacle's area intersects with the given circle, {@code false} otherwise.
	 */
	public boolean isWithin(int x, int y, int radius) {
		return model.getArea().intersects(x, y, radius);
	}
	
	/**
	 * Checks whether the obstacle's area collides the other's
	 * @param obstacle the other obstacle
	 * @return {@code true} if those area's intersection is non empty (if they share some common points) {@code false} otherwise.
	 */
	public boolean checkCollision(Obstacle obstacle) {
		return obstacle.model.getArea().intersects(model.getArea());
	}
	
	/**
	 * Checks whether this obstacle's area collide the player's area
	 * @param player the player
	 * @return {@code true} if those area's intersection is non empty (if they share some common points) {@code false} otherwise.
	 */
	public boolean checkCollision(Player player) {
		return player.checkCollision(model.getArea());
	}

	/**
	 * <p>
	 * A dropped Obstacle cannot be collided
	 * </p>
	 * @return whether this Obstacle is dropped or not
	 * @see fr.cesi.ylalanne.game.model.ObstacleModel#isDropped()
	 */
	public boolean isDropped() {
		return model.isDropped();
	}

	/**
	 * A deadly obstacle cannot be climbed upon : a collision with such obstacle would kill the player
	 * @return whether this Obstacle is deadly or not
	 * @see fr.cesi.ylalanne.game.model.ObstacleModel#isDeadly()
	 */
	public boolean isDeadly() {
		return model.isDeadly();
	}

	/**
	 * @return the obstacle's width
	 * @see fr.cesi.ylalanne.game.model.ObstacleModel#getWidth()
	 */
	public int getWidth() {
		return model.getWidth();
	}

	/**
	 * @return the obstacle's height
	 * @see fr.cesi.ylalanne.game.model.ObstacleModel#getHeight()
	 */
	public int getHeight() {
		return model.getHeight();
	}

	@Override
	public IChildView getChild() {
		return view;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propertyName = evt.getPropertyName();
		Object newValue = evt.getNewValue();
		
		switch(propertyName) {
			case "width":
				updateWidth((int)newValue);
				break;
			case "height":
				updateHeight((int)newValue);
				break;
		}
	}
}
