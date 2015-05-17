package game;

import game.model.ObstacleModel;
import game.model.geom.MutableRectangle;
import game.ui.ObstacleView;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.nio.file.Paths;

import main.IChildController;
import main.ResourcesManager;
import main.ui.IChildView;

/**
 * An Obstacle : basically, from a model's point of view, a box crossing game's board
 */
public class Obstacle implements IChildController, PropertyChangeListener {
	private ObstacleKind kind;
	private ObstacleModel model;
	private ObstacleView view;
	
	/**
	 * Initialize an Obstacle
	 * @param kind the obstacle's kind
	 */
	public Obstacle(ObstacleKind kind) {
		this.kind = kind;
		model = new ObstacleModel();
		view = new ObstacleView(model);
		model.setImagePath(Paths.get(ResourcesManager.RESOURCES_BASE, kind.getImagePath()).toString());
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
	 * Moves the box with predefined steps (dx on the X axis and dy on the Y one)
	 */
	public void move() {
		MutableRectangle obstacleArea = model.getArea();
		obstacleArea.setX(obstacleArea.getX() + model.getDx());
		obstacleArea.setY(obstacleArea.getY() + model.getDy());
	}
	
	/**
	 * Checks whether this box is at least partially contained in the a given circular area
	 * @param x the center (x coordinate) of a circular area
	 * @param y the center (y coordinate) of a circular area
	 * @param radius the distance between circle's center and edge
	 * @return {@code true} if this box intersects with the given circle, {@code false] otherwise.
	 */
	public boolean isWithin(int x, int y, int radius) {
		return model.getArea().intersects(x, y, radius);
	}
	
	/**
	 * Checks whether this box collide the other
	 * @param obstacle the other box
	 * @return {@code true} if those boxes's intersection is non empty (if they share some common points) false otherwise.
	 */
	public boolean collide(Obstacle obstacle) {
		return obstacle.model.getArea().intersects(model.getArea());
	}
	
	/**
	 * Checks whether this box collide the other (a {@code Player} is just a different kind of box)
	 * @param player the player
	 * @return {@code true} if those boxes's intersection is non empty (if they share some common points) false otherwise.
	 */
	public boolean collide(Player player) {
		return player.collide(model.getArea());
	}

	@Override
	public IChildView getChild() {
		return view;
	}

	private void updateHeight(int height) {
		model.getArea().setHeight(height);
	}

	private void updateWidth(int width) {
		model.getArea().setWidth(width);
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
