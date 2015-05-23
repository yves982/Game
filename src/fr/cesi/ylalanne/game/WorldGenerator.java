package fr.cesi.ylalanne.game;

import java.util.ArrayList;
import java.util.List;

import fr.cesi.ylalanne.utils.Range;

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
	private boolean hasSpawn;
	
	private void buildRows() {
		int rowCount = rowXSteps.size();
		rows = new ArrayList<Range<Integer>>(rowCount);
		int worldHeight = world.getHeight();
		double freeHeightPerRow = (worldHeight - areasSpace) / rowCount;
		int rowSpace = (int)Math.ceil( 0.78 * freeHeightPerRow );
		int interRowSpace = (int)Math.ceil(0.12 * freeHeightPerRow);
		int start=0, end=rowSpace;
		
		for(int i=0; i<rowCount; i++) {
			if(i != 0) {
				start = i * (rowSpace + interRowSpace);
				end = (i * (rowSpace + interRowSpace)) + rowSpace;
			}
			rows.add(new Range<Integer>(start, end));
		}
	}

	private void buildRowXSteps(int ... xSteps) {
		rowXSteps = new ArrayList<Integer>(xSteps.length);
		for(int i=0; i < xSteps.length; i++) {
			rowXSteps.add(xSteps[i]);
		}
	}

	/**
	 * Initialize an ObstacleGenerator
	 * @param world the {@code World}
	 */
	public WorldGenerator(World world) {
		this.world = world;
		areasSpace = 0;
		hasSpawn = false;
	}
	
	/**
	 * generate an Area of a given kind in a specified row
	 * @param kind the kind of Area to generate
	 * @param row the row to place generated Area into
	 * @return the generated {@link Area}
	 */
	public Area generateArea(AreaKind kind, int row) {
		Area area = new Area(kind);
		area.startPosition(0, 0);
		areasSpace += area.getHeight();
		return area;
	}
	
	/**
	 * generate an Obstacle of a given kind in a specified row
	 * @param kind the kind of Obstacle to generate
	 * @param row the row to place the generated Obstacle into (<b>use at least 1</b>)
	 * @return the generated {@link Obstacle}
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
	 * @param maxLives The maximum number of lives this Player has
	 * @param maxLeftTimeMs the maximum lifetime this Player has (in milliseconds)
	 * @param row the virtual row the generated Player should be placed into (<b>use at least 1</b>)
	 * @return the generated {@link Player}
	 */
	public Player generatePlayer(int maxLives, int maxLeftTimeMs, int row) {
		Player player = new Player(maxLives, maxLeftTimeMs, 5);
		int startX = world.getWidth()/2 - player.getWidth()/2;
		int startY = (int)Math.ceil(
				rows.get(row -1).getStart() 
				+ (rows.get(row -1).size() / 6.5d) 
				+ (player.getHeight()/2.0d));
		
		player.startPosition(startX, startY);
		return player;
	}
	
	/**
	 * Spawn this world
	 * @param xSteps steps for automatic obstacle moves : we need one int per row
	 */
	public void spawnWorld(int ... xSteps) {
		buildRowXSteps(xSteps); 
		buildRows();
		
		Player player = generatePlayer(3, 4000, 11);
		world.setPlayer(player);
		player.lives();
		
		if(! hasSpawn) {
			hasSpawn = true;
		}
	}
}
