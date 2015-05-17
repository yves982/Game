package game;

import java.util.ArrayList;
import java.util.List;

import utils.Range;

/**
 * An World Generator
 * <p>
 * It divides the world into rows ({@code Range} of y coordinates), for positioning ease
 * </p>
 */
public class WorldGenerator {
	private World world;
	private List<Range<Integer>> rows;
	private List<Integer> rowXSteps;
	private int areasSpace;
	
	private void buildRows() {
		int rowCount = rowXSteps.size();
		rows = new ArrayList<Range<Integer>>(rowCount);
		int worldHeight = world.getHeight();
		double freeHeightPerRow = (worldHeight - areasSpace) / rowCount;
		int rowSpace = (int)Math.ceil( 0.95 * freeHeightPerRow );
		int interRowSpace = (int)Math.ceil(0.04 * freeHeightPerRow);
		int start=0, end=rowSpace;
		
		for(int i=0; i<rowCount; i++) {
			if(i != 0) {
				start = i * (rowSpace + interRowSpace);
				end = (i * (rowSpace + interRowSpace)) + rowSpace;
			}
			rows.add(new Range<Integer>(start, end));
		}
	}

	/**
	 * Initialize an ObstacleGenerator
	 * @param world the {@code World}
	 * @param rowXSteps the dx steps along X axis for obstacles moves
	 */
	public WorldGenerator(World world, List<Integer> rowXSteps) {
		this.world = world;
		this.rowXSteps = rowXSteps;
		areasSpace = 0;
		buildRows();
	}
	
	/**
	 * generate an Area of a given kind in a specified row
	 * @param kind
	 * @param row
	 * @return
	 */
	public Area generateArea(AreaKind kind, int row) {
		Area area = new Area(kind);
		area.startPosition(0, 0);
		areasSpace += area.getHeight();
		return area;
	}
	
	/**
	 * generate an Obstacle of a given kind in a specified row
	 * @param kind
	 * @param row
	 * @return
	 */
	public Obstacle generateObstacle(ObstacleKind kind, int row) {
		Obstacle obstacle = new Obstacle(kind);
		obstacle.defineSteps(rowXSteps.get(row -1), 0);
		int startX = world.getWidth() - obstacle.getWidth();
		int startY = (int)(Math.floor(rows.get(row - 1).size())) / 2 - obstacle.getHeight()/2;
		obstacle.startPosition(startX, startY);
		return obstacle;
	}
	
	/**
	 * generate a Player in a specified row
	 * @param row
	 * @return
	 */
	public Player generatePlayer(int maxLives, int maxLeftTimeMs, int row) {
		Player player = new Player(maxLives, maxLeftTimeMs);
		int startX = world.getWidth()/2 - player.getWidth()/2;
		int startY = (int)(Math.floor(rows.get(row - 1).size())) / 2 - player.getHeight()/2;
		player.startPosition(startX, startY);
		return player;
	}
}
