package fr.cesi.ylalanne.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import fr.cesi.ylalanne.contracts.IHighScoreController;
import fr.cesi.ylalanne.highscores.HighScoreController;
import fr.cesi.ylalanne.settings.SettingsController;
import fr.cesi.ylalanne.settings.model.Settings;
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
	private Settings settings;
	private Random random;
	
	private void defineWinningAreas() {
		List<Range<Integer>> winningAreas = new ArrayList<Range<Integer>>();
		winningAreas.add(new Range<Integer>(30, 58));
		winningAreas.add(new Range<Integer>(190, 222));
		winningAreas.add(new Range<Integer>(366, 398));
		winningAreas.add(new Range<Integer>(522, 555));
		winningAreas.add(new Range<Integer>(700, 727));
		rows.get(0).setWinningAreas(winningAreas);
	}

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
		
		defineWinningAreas();
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
				
		startX = hasPrevious ? 
				previousObstacle.getX() + previousObstacle.getWidth() + spaceAfterPrevious 
				: 0;
		
		if(startX < 0 || startX > world.getWidth() - obstacle.getWidth()) {
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
		Range<Integer> xBounds = new Range<Integer>(0, world.getWidth());
		// 22 is half the size of Player's sprite
		Range<Integer> yBounds = new Range<Integer>((int)rows.get(0).getBounds().size() -28, world.getHeight() - (int)rows.get(0).getBounds().size() +28);
		Player player = generatePlayer(3, 60000, row, 5, xBounds, yBounds);
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
		int minObstacleSpace = settings.getDifficulty().getMinObstacleSpace();
		for(int i = 1; i < 4; i++) {
			int space = random.nextInt(17) + minObstacleSpace;
			ObstacleKind kind = i%2 == 0 ? ObstacleKind.TRUCK : ObstacleKind.CAR;
			fillObstacleRow(kind, rowCount -i, space);
		}
	}

	private void buildTopRows() {
		int minObstacleSpace = settings.getDifficulty().getMinObstacleSpace();
		for(int i = 1; i < 4; i++) {
			int space = random.nextInt(17) + minObstacleSpace;
			ObstacleKind kind = i == 1 ? ObstacleKind.TRUNK : ObstacleKind.TURTLE;
			fillObstacleRow(kind, i + 1, space);
		}
	}

	private void buildObstacles() {
		buildBottomRows();
		buildTopRows();
	}

	private void buildBear() {
		Range<Integer> xBounds = new Range<Integer>(0, world.getWidth());
		Obstacle bear = new Obstacle(ObstacleKind.BEAR, xBounds, 0);
		int index = random.nextInt(5);
		Range<Integer> firstRowBearSlot = rows.get(0).getWinningAreas().get(index);
		int bearX = firstRowBearSlot.getStart() + (int)Math.ceil(firstRowBearSlot.size() / 2.0d) -(int)Math.ceil(bear.getWidth() / 2.0d);
		int bearY = (int) Math.ceil(rows.get(0).getBounds().size() / 2.0d) -8;
		bear.position(bearX, bearY);
		bear.waitUntilReady();
		world.addObstacle(bear);
	}

	/**
	 * Initialize an ObstacleGenerator
	 * @param world the {@code World}
	 */
	public WorldGenerator(World world) {
		this.world = world; 
		hasSpawn = false;
		random = new Random();
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
		Range<Integer> xBounds = new Range<Integer>(0, world.getWidth());
		GameRow gameRow = rows.get(row -1);
		Obstacle obstacle = new Obstacle(kind, xBounds, 3 * Math.abs(gameRow.getxStep()));
		Range<Integer> bounds = gameRow.getBounds();
		obstacle.defineSteps(gameRow.getxStep(), 0);
		
		int startX = findObstacleStartX(gameRow, obstacle, spaceAfterPrevious);
		int startY = bounds.getStart();
		
		if(startX != -1) {
			obstacle.position(startX, startY);
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
	 * @param movesStep the size of each Player's move
	 * @param xBounds the x min/max coordinates to stay in the World
	 * @param xBounds the y min/max coordinates to stay in the World
	 * @return the generated {@link Player}
	 */
	public Player generatePlayer(int maxLives, int maxLeftTimeMs, int row, int movesStep, Range<Integer> xBounds, Range<Integer> yBounds) {
		Player player = new Player(maxLives, maxLeftTimeMs, movesStep, xBounds, yBounds);
		IHighScoreController highScore = new HighScoreController();
		player.setHighScore(highScore);
		Range<Integer> bounds = rows.get(row-1).getBounds();
		int startX = world.getWidth()/2 - player.getWidth()/2;
		int startY = bounds.getStart();
		
		player.position(startX, startY);
		return player;
	}
	
	/**
	 * Spawn this world
	 */
	public void spawnWorld() {
		int [] xSteps = new int[] {
				5,3,5,7,
				5,-5,5,-5,
				5
		};
		
		buildRows(xSteps);
		settings = SettingsController.getSettings();
		
		if(hasSpawn) {
			world.reset();
			areasSpace = 0;
		}
		
		buildAreas();
		Player player = buildPlayer(xSteps.length);
		buildBear();
		buildObstacles();
		
		WorldManager manager = new WorldManager(world, player, xSteps.length, rows);
		manager.start();
		
		if(! hasSpawn) {
			hasSpawn = true;
		}
	}

	/**
	 * @return the hasSpawn
	 */
	public boolean isHasSpawn() {
		return hasSpawn;
	}
}
