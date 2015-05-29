package fr.cesi.ylalanne.game;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import fr.cesi.ylalanne.contracts.IChildController;
import fr.cesi.ylalanne.contracts.ui.IChildView;
import fr.cesi.ylalanne.game.model.ObstacleModel;
import fr.cesi.ylalanne.game.model.geom.MutableRectangle;
import fr.cesi.ylalanne.game.ui.ObstacleView;
import fr.cesi.ylalanne.utils.Range;

/**
 * An Obstacle : basically, a moving area, which can be checked for collisions
 */
public class Obstacle implements IChildController, PropertyChangeListener {
	private ObstacleKind kind;
	private ObstacleModel model;
	private ObstacleView view;
	private boolean widthUpdated;
	private boolean heightUpdated;
	private Range<Integer> xBounds;
	private boolean isOutOfWorld;
	private int outOfWorldTicks;
	private int outOfWorldTicksCount;
	
	
	private synchronized void updateWidth(int width) {
		model.getArea().setWidth(width);
		widthUpdated = true;
		notifyAll();
	}

	private synchronized void updateHeight(int height) {
		model.getArea().setHeight(height);
		heightUpdated = true;
		notifyAll();
	}

	/**
	 * Initialize an Obstacle
	 * @param kind the obstacle's kind
	 * @param xBounds min/max x coordinates to stay in the World
	 * @param outOfWorldTicks Number of times it'll ignore move requests before going back to its starting position in the World
	 */
	public Obstacle(ObstacleKind kind, Range<Integer> xBounds, int outOfWorldTicks) {
		this.kind = kind;
		model = new ObstacleModel();
		view = new ObstacleView(model);
		view.build();
		this.xBounds = xBounds;
		this.outOfWorldTicks = outOfWorldTicks;
		isOutOfWorld = false;
		outOfWorldTicksCount = 0;
		model.setImagePath(kind.getImagePath());
		model.setDeadly(kind.isDeadly());
		model.setStatic(kind.isStatic());
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
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	public void position(int x, int y) {
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
		if(model.isStatic()) {
			throw new IllegalStateException("A static Obstacle should not be moved");
		}
		if(!isOutOfWorld) {
			MutableRectangle obstacleArea = model.getArea();
			obstacleArea.setX(obstacleArea.getX() + model.getDx());
			obstacleArea.setY(obstacleArea.getY() + model.getDy());
			
			if(model.getDx() > 0) { 
				isOutOfWorld = !xBounds.isIn(obstacleArea.getX());
			} else {
				isOutOfWorld = !xBounds.isIn(obstacleArea.getX() + obstacleArea.getWidth());
			}
		} else {
			outOfWorldTicksCount++;
			if(outOfWorldTicksCount == outOfWorldTicks) {
				int x = model.getDx() > 0 ? xBounds.getStart() : xBounds.getEnd() - model.getWidth();
				position(x, model.getArea().getY());
				isOutOfWorld = false;
				outOfWorldTicksCount = 0;
			}
		}
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
	 * Waits until this Obstacle is fully loaded
	 */
	public synchronized void waitUntilReady() {
		while(!widthUpdated || !heightUpdated) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
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
	 * @return the isStatic ( a static Obstacle should not be moved )
	 */
	public boolean isStatic() {
		return model.isStatic();
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
	
	/**
	 * @return the obstacle's x coordinate
	 */
	public int getX() {
		return model.getArea().getX();
	}
	
	/**
	 * @return the obstacle's y coordinate
	 */
	public int getY() {
		return model.getArea().getY();
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
