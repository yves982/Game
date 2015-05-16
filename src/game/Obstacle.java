package game;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.nio.file.Paths;

import game.model.ObstacleModel;
import game.model.geom.MutableRectangle;
import game.ui.ObstacleView;
import lang.Messages;
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
	 * Default constructor
	 */
	public Obstacle(ObstacleKind kind) {
		this.kind = kind;
		model = new ObstacleModel();
		view = new ObstacleView(model);
		model.setImagePath(Paths.get(ResourcesManager.RESOURCES_BASE, kind.getImagePath()).toString());
		view.addPropertyChangeListener(this);
	}
	
	public ObstacleKind getKind() {
		return kind;
	}

	/**
	 * Moves the box with predefined steps (dx on the X axis and dy on the Y one)
	 */
	public void move() {
		MutableRectangle area = model.getArea();
		area.setX(area.getX() + model.getDx());
		area.setY(area.getY() + model.getDy());
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
