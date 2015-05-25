package fr.cesi.ylalanne.game;

import java.util.ArrayList;
import java.util.List;

import fr.cesi.ylalanne.utils.Range;

public class GameRow {
	private Range<Integer> bounds;
	private int xStep;
	private List<Obstacle> obstacles;
	private Area area;
	
	/**
	 * Initialize an instance
	 */
	public GameRow() {
		obstacles = new ArrayList<Obstacle>();
	}

	/**
	 * @return the bounds
	 */
	public Range<Integer> getBounds() {
		return bounds;
	}
	/**
	 * @param bounds the bounds to set
	 */
	public void setBounds(Range<Integer> bounds) {
		this.bounds = bounds;
	}

	/**
	 * @return the xStep
	 */
	public int getxStep() {
		return xStep;
	}
	/**
	 * @param xStep the xStep to set
	 */
	public void setxStep(int xStep) {
		this.xStep = xStep;
	}

	/**
	 * @return the obstacles
	 */
	public List<Obstacle> getObstacles() {
		return obstacles;
	}
	/**
	 * @param obstacles the obstacles to set
	 */
	public void setObstacles(List<Obstacle> obstacles) {
		this.obstacles = obstacles;
	}

	/**
	 * @return true if this row holds an area, false otherwise
	 */
	public boolean hasArea() {
		return area == null;
	}
	
	/**
	 * @return the area
	 */
	public Area getArea() {
		return area;
	}
	/**
	 * @param area the area to set
	 */
	public void setArea(Area area) {
		this.area = area;
	}
}
