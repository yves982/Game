package fr.cesi.ylalanne.game;

import java.util.ArrayList;
import java.util.List;

import fr.cesi.ylalanne.utils.Range;

public class GameRow {
	private Range<Integer> bounds;
	private List<Range<Integer>> winningAreas;
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

	/**
	 * @return true if this GameRow has the option to end the game on certain spots
	 */
	public boolean isFinal() {
		return winningAreas != null &&  winningAreas.size() > 0;
	}
	
	/**
	 * @return the winningAreas (the range of x coordinates within which the Player wins the game : if his center gets in)
	 */
	public List<Range<Integer>> getWinningAreas() {
		return winningAreas;
	}
	/**
	 * @param winningAreas the winningAreas to set
	 */
	public void setWinningAreas(List<Range<Integer>> winningAreas) {
		this.winningAreas = winningAreas;
	}
}
