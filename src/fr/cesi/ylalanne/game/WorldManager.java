package fr.cesi.ylalanne.game;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import fr.cesi.ylalanne.game.ui.MoveRequestEvent;
import fr.cesi.ylalanne.utils.Range;

/**
 * The WorldManager (manages moves and Player/Obstacles interactions).
 */
public class WorldManager implements PropertyChangeListener {
	private World world;
	private Player player;
	private int playerRow;
	private List<GameRow> rows;
	private List<Obstacle> obstacles;
	private List<Range<Integer>> winningAreas;
	private int lowestRow;
	private ScheduledExecutorService moveExecutor;
	private Random random;
	
	
	private void checkEnd() {
		if (playerRow == 1) {
			int playerCenterX = player.getX() + (int)(player.getWidth() / 2.0d);
			boolean won = false;
			for(Range<Integer> winningRange : winningAreas) {
				if(winningRange.isIn(playerCenterX)) {
					won = true;
					break;
				}
			}
			
			if(won && (!player.isCollided() || !player.getCollider().isStatic())) {
				player.win();
				endWorld(true);
			} else if (player.isCollided() && player.getCollider().isStatic()) {
				player.position(player.getX(), player.getY() - player.getHeight()/2 - (int)rows.get(0).getBounds().size());
			}
		}
	}

	private void updateRow() {
		Optional<GameRow> currentRow = rows.stream()
				.filter(row -> row.getBounds().isIn(player.getY() + (int)Math.ceil(player.getHeight()/2.0d)))
				.findFirst();
		if(currentRow.isPresent()) {
			playerRow = rows.indexOf(currentRow.get()) +1;
		}
	}
	
	private void updateScore() {
			if(playerRow < lowestRow) {
				lowestRow = playerRow;
				player.gains(10);
			}
	}

	private void moveObstacles() {
		Iterator<Obstacle> movingObstaclesIterator = obstacles.stream().filter( obstacle -> !obstacle.isStatic()).iterator();
		Iterable<Obstacle> movingObstacles = () -> movingObstaclesIterator;
		
		for(Obstacle obstacle : movingObstacles) {
			int oldX = obstacle.getX();
			obstacle.move();
			checkX(oldX, obstacle.getX(), obstacle);
			if(player.isCollided()) {
				movesOnWater();
			}
		}
	}

	private void endWorld(boolean won) {
		try {
			moveExecutor.shutdown();
			moveExecutor.awaitTermination(500, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			player.removePropertyChangeListener(this);
			world.end(won);
		}
	}

	private void checkY(int oldY, int y) {
		List<Obstacle> obstacles = world.getObstacles();
		
		Optional<Obstacle> collider = obstacles.stream()
		.filter(obstacle -> obstacle.isWithin(player.getX(), (y+oldY)/2, 15))
		.filter( obstacle -> obstacle.checkCollision(player))
		.findFirst();
		
		if(collider.isPresent()) {
			player.collides(collider.get());
		} else if(player.isCollided()) {
			player.unCollides();
		}
	}

	private void checkX(int oldX, int x, Obstacle obstacle) {
		List<Obstacle> obstacles = world.getObstacles();
		
		if(obstacle == null) {
			Optional<Obstacle> collider = obstacles.stream()
			.filter(obs -> obs.isWithin((x+oldX)/2, player.getY(), 15))
			.filter( obs -> obs.checkCollision(player))
			.findFirst();
			
			if(collider.isPresent()) {
				player.collides(collider.get());
			} else if(player.isCollided()) {
				player.unCollides();
			}
		} else if(obstacle.isWithin((x+oldX)/2, player.getY(), 15) && obstacle.checkCollision(player)) {
			player.collides(obstacle);
		}
	}

	private void movesOnWater() {
		Obstacle collider = player.getCollider();
		if(player.isCollided() && !collider.isDeadly()) {
			int playerWidth = player.getWidth();
			int colliderWidth = collider.getWidth();
			int colliderX = collider.getX();
			
			int x = colliderX + (int)Math.ceil(Math.abs(playerWidth - colliderWidth) / 2.0d);
			int y = player.getY();
			boolean inBounds = player.position(x, y);
			if(!inBounds && !player.getCollider().checkCollision(player)) {
				player.kill();
			}
		}
	}
	
	private void checkWater() {
		
		boolean hasVeryCloseObstacles = 
				obstacles
				.stream()
				.anyMatch(obstacle -> obstacle.isWithin(player.getX(), player.getY(), 22));
		
		if(!hasVeryCloseObstacles) {
			player.kill();
			playerRow = rows.size();
		}
		
	}

	private void freeFromWaterMover() {
		if(player.isCollided()) {
			int y=0;
			if(player.getCurrentMoveRequest().equals(MoveRequestEvent.UP)) {
				y = player.getY() - (int)rows.get(0).getBounds().size();
			} else {
				y = player.getY() + (int)rows.get(0).getBounds().size();
			}
			player.position(player.getX(), y);
			player.unCollides();
		}
	}

	private void placeBear() {
		Obstacle bear = obstacles.stream().filter( obstacle -> obstacle.getKind().equals(ObstacleKind.BEAR) ).findFirst().get();
		int index = random.nextInt(5);
		Range<Integer> firstRowBearSlot = rows.get(0).getWinningAreas().get(index);
		int bearX = firstRowBearSlot.getStart() + (int)Math.ceil(firstRowBearSlot.size() / 2.0d) -(int)Math.ceil(bear.getWidth() / 2.0d);
		int bearY = (int) Math.ceil(rows.get(0).getBounds().size() / 2.0d) -(int)Math.ceil(4.78d * bear.getHeight());
		bear.position(bearX, bearY);
	}

	/**
	 * Initializes a WorldManager.
	 *
	 * @param world the world
	 * @param player the player
	 * @param playerRow the current playerRow
	 * @param rows the game rows
	 */
	public WorldManager(World world, Player player, int playerRow, List<GameRow> rows) {
		this.world = world;
		this.player = player;
		this.playerRow = playerRow;
		this.rows = rows;
		this.player.addPropertyChangeListener(this);
		lowestRow = rows.size();
		obstacles = world.getObstacles();
		winningAreas = rows.get(0).getWinningAreas();
		random = new Random();
	}
	
	/**
	 * Starts animating the World.
	 */
	public void start() {
		moveExecutor = Executors.newSingleThreadScheduledExecutor();
		moveExecutor.scheduleAtFixedRate(this::moveObstacles, 0, 400, TimeUnit.MILLISECONDS);
		player.lives();
	}

	/* (non-Javadoc)
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propertyName = evt.getPropertyName();
		
		switch(propertyName) {
			case "liveLess":
				if(!player.isWinning()) {
					endWorld(false);
				}
				break;
			case "x":
				int oldX = (int)evt.getOldValue();
				int x = (int)evt.getNewValue();
				checkX(oldX, x, null);
				if(playerRow >=2 && playerRow <= 4) {
					checkWater();
				}
				checkEnd();
				
				break;
			case "y":
				int oldY = (int)evt.getOldValue();
				int y = (int)evt.getNewValue();
				checkY(oldY, y);
				updateRow();
				if(playerRow>=2 && playerRow <= 4) {
					checkWater();
					freeFromWaterMover();
				}
				updateScore();
				checkEnd();
				break;
			case "lives":
				placeBear();
				break;
		}
		
	}
}
