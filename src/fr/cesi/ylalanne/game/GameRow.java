package fr.cesi.ylalanne.game;

import java.util.ArrayList;
import java.util.List;

import fr.cesi.ylalanne.utils.Range;

/**
 * A GameRow is a row (a range on the y axis) which will hold {@link Obstacle Obstacles} and eventually an {@link Area}.
 */
public class GameRow {
	private Range<Integer> bounds;
	private List<Range<Integer>> winningAreas;
	private int xStep;
	private List<Obstacle> obstacles;
	private Area area;
	
	/**
	 * Initialize a GameRow.
	 */
	public GameRow() {
		obstacles = new ArrayList<Obstacle>();
	}

	/**
	 * Gets the bounds along the y axis.
	 *
	 * @return the bounds
	 */
	public Range<Integer> getBounds() {
		return bounds;
	}
	
	/**
	 * Sets the bounds along the y axis.
	 *
	 * @param bounds the bounds to set
	 */
	public void setBounds(Range<Integer> bounds) {
		this.bounds = bounds;
	}

	/**
	 * Gets the step to be set on the Row's Obstacles.
	 *
	 * @return the xStep
	 */
	public int getxStep() {
		return xStep;
	}
	
	/**
	 * Sets the associated step.
	 *
	 * @param xStep the xStep to set
	 */
	public void setxStep(int xStep) {
		this.xStep = xStep;
	}

	/**
	 * Gets the obstacles.
	 *
	 * @return the obstacles
	 */
	public List<Obstacle> getObstacles() {
		return obstacles;
	}
	
	/**
	 * Sets the obstacles.
	 *
	 * @param obstacles the obstacles to set
	 */
	public void setObstacles(List<Obstacle> obstacles) {
		this.obstacles = obstacles;
	}

	/**
	 * Checks for area.
	 *
	 * @return true if this row holds an area, false otherwise
	 */
	public boolean hasArea() {
		return area == null;
	}
	
	/**
	 * Gets the area.
	 *
	 * @return the area
	 */
	public Area getArea() {
		return area;
	}
	
	/**
	 * Sets the area.
	 *
	 * @param area the area to set
	 */
	public void setArea(Area area) {
		this.area = area;
	}

	/**
	 * Checks if is final.
	 *
	 * @return true if this GameRow has the option to end the game on certain spots
	 */
	public boolean isFinal() {
		return winningAreas != null &&  winningAreas.size() > 0;
	}
	
	/**
	 * Gets the winning areas.
	 *
	 * @return the winningAreas (the range of x coordinates within which the Player wins the game : if his center gets in)
	 */
	public List<Range<Integer>> getWinningAreas() {
		return winningAreas;
	}
	
	/**
	 * Sets the winning areas.
	 *
	 * @param winningAreas the winningAreas to set
	 */
	public void setWinningAreas(List<Range<Integer>> winningAreas) {
		this.winningAreas = winningAreas;
	}
}
