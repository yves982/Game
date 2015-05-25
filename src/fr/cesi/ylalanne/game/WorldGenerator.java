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
	private List<GameRow> rows;
	private int playerReservedHeight;
	private boolean hasSpawn;
	private int interRowSpace;
	private int areasSpace;
	
	private void buildRows(int ... xSteps) {
		int rowCount = xSteps.length;
		rows = new ArrayList<GameRow>(rowCount);
		int worldHeight = world.getHeight();
		double freeHeightPerRow = worldHeight / rowCount;
		int rowSpace = (int)Math.ceil( 0.78 * freeHeightPerRow );
		interRowSpace = (int)Math.ceil(0.08 * freeHeightPerRow);
		int start=0, end=rowSpace;
		
		for(int i=0; i<rowCount; i++) {
			if(i != 0) {
				start = i * (rowSpace + interRowSpace);
				end = (i * (rowSpace + interRowSpace)) + rowSpace;
			}
			GameRow row = new GameRow();
			row.setBounds(new Range<Integer>(start, end));
			row.setxStep(xSteps[i]);
			row.setObstacles(new ArrayList<Obstacle>());
			rows.add(row);
		}
	}

	/**
	 * Computes an Obstacle's starting x coordinate within a {@link GameRow}
	 * @param gameRow
	 * @param obstacle
	 * @param spaceAfterPrevious
	 * @return the starting x coordinate or -1 if the obstacle cannot fit in the given row
	 */
	private int findObstacleStartX(GameRow gameRow, Obstacle obstacle, int spaceAfterPrevious) {
		int startX = -1;
		List<Obstacle> obstacles = gameRow.getObstacles();
		int obstaclesCount = obstacles.size();
		boolean hasPrevious = obstaclesCount > 0;
		Obstacle previousObstacle = hasPrevious ? obstacles.get(obstaclesCount -1) : null;
				
		if(gameRow.getxStep() > 0) {
			startX = hasPrevious ? 
					previousObstacle.getX() + previousObstacle.getWidth() + spaceAfterPrevious 
					: 0;
		} else {
			startX = hasPrevious ?
					previousObstacle.getX() - spaceAfterPrevious - obstacle.getWidth()
					: world.getWidth() -  obstacle.getWidth();
		}
		
		if(startX < 0 || startX >= world.getWidth() - obstacle.getWidth()) {
			startX = -1;
		}
		
		return startX;
	}

	private void buildAreas() {
		Area finish = generateArea(AreaKind.FINISH, 1);
		Area wait = generateArea(AreaKind.WAIT, 5);
		Area start = generateArea(AreaKind.START, 9);
		
		world.addArea(finish);
		world.addArea(wait);
		world.addArea(start);
	}

	private Player buildPlayer(int row) {
		Player player = generatePlayer(3, 4000, row);
		playerReservedHeight = player.getReservedHeight();
		
		world.setPlayer(player);
		return player;
	}

	private void fillObstacleRow(ObstacleKind kind, int row, int spaceAfterPrevious) {
		Obstacle obstacle = generateObstacle(kind, row, spaceAfterPrevious);
		while(obstacle != null) {
			obstacle.waitUntilReady();
			world.addObstacle(obstacle);
			rows.get(row -1).getObstacles().add(obstacle);
			obstacle = generateObstacle(kind, row, spaceAfterPrevious);
		}
	}

	private void buildBottomRows() {
		int rowCount = rows.size();
		for(int i = 1; i < 4; i++) {
			int space = (int)Math.ceil(Math.random() * 12) + 7;
			ObstacleKind kind = i%2 == 0 ? ObstacleKind.TRUCK : ObstacleKind.CAR;
			fillObstacleRow(kind, rowCount -i, space);
		}
	}

	private void buildTopRows() {
		for(int i = 1; i < 4; i++) {
			int space = (int)Math.ceil(Math.random() * 12) + 7;
			ObstacleKind kind = i == 1 ? ObstacleKind.TRUNK : ObstacleKind.TURTLE;
			fillObstacleRow(kind, i + 1, space);
		}
	}

	private void buildObstacles() {
		buildBottomRows();
		buildTopRows();
	}

	/**
	 * Initialize an ObstacleGenerator
	 * @param world the {@code World}
	 */
	public WorldGenerator(World world) {
		this.world = world;
		hasSpawn = false;
	}
	
	/**
	 * generate an Area of a given kind in a specified row
	 * @param kind the kind of Area to generate
	 * @param row the row to set the generated Area into
	 * @return the generated {@link Area}
	 */
	public Area generateArea(AreaKind kind, int row) {
		Area area = new Area(kind);
		area.startPosition(areasSpace + playerReservedHeight);
		
		int width, height, usedHeight;
		width = world.getWidth();
		height = rows.get(row -1).getBounds().getEnd() - areasSpace;
		usedHeight = (int)rows.get(0).getBounds().size();
		
		area.setBounds(width, height, usedHeight);
		areasSpace += height;
		
		rows.get(row -1).setArea(area);
		
		return area;
	}
	
	/**
	 * generate an Obstacle of a given kind in a specified row
	 * @param kind the kind of Obstacle to generate
	 * @param row the row to place the generated Obstacle into (<b>use at least 1</b>)
	 * @param spaceAfterPrevious amount of pixel to place the generated Obstacle after the previous one on that row
	 * @return the generated {@link Obstacle} or null if there's not enough space left
	 */
	public Obstacle generateObstacle(ObstacleKind kind, int row, int spaceAfterPrevious) {
		Obstacle obstacle = new Obstacle(kind);
		GameRow gameRow = rows.get(row -1);
		Range<Integer> bounds = gameRow.getBounds();
		obstacle.defineSteps(gameRow.getxStep(), 0);
		
		int startX = findObstacleStartX(gameRow, obstacle, spaceAfterPrevious);
		int startY = bounds.getStart();
		
		if(startX != -1) {
			obstacle.startPosition(startX, startY);
		} else {
			obstacle = null;
		}
		
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
		Range<Integer> bounds = rows.get(row-1).getBounds();
		int startX = world.getWidth()/2 - player.getWidth()/2;
		int startY = bounds.getStart();
		
		player.startPosition(startX, startY);
		return player;
	}
	
	/**
	 * Spawn this world
	 */
	public void spawnWorld() {
		int [] xSteps = new int[] {
				5,5,5,5,
				5,5,5,5,
				5
		};
		
		buildRows(xSteps);
		
		if(hasSpawn) {
			world.reset();
			areasSpace = 0;
		}
		
		buildAreas();
		Player player = buildPlayer(xSteps.length);
		buildObstacles();
		
		WorldManager manager = new WorldManager(world, player, xSteps.length, rows);
		manager.start();
		
		if(! hasSpawn) {
			hasSpawn = true;
		}
	}
}
