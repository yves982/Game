package fr.cesi.ylalanne.game;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class WorldManager implements PropertyChangeListener {
	private World world;
	private Player player;
	private int playerRow;
	private List<GameRow> rows;
	private List<Obstacle> obstacles;
	private int lowestRow;
	private ScheduledExecutorService moveExecutor;
	
	
	private void checkEnd() {
		if (playerRow == 1) {
			int playerCenterX = player.getX() + (int)Math.ceil(player.getWidth() / 2.0d);
			boolean won = rows.get(0).getWinningAreas()
					.stream()
					.anyMatch( winningRow -> winningRow.isIn(playerCenterX) );
			if(won) {
				player.win();
				endWorld(true);
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
		for(Obstacle obstacle : world.getObstacles()) {
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
			}
		} else if(obstacle.isWithin((x+oldX)/2, player.getY(), 15) && obstacle.checkCollision(player)) {
			player.collides(obstacle);
		}
	}

	private void movesOnWater() {
		if(player.isCollided()) {
			Obstacle collider = player.getCollider();
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
		}
		
	}

	private void freeFromWaterMover() {
		if(player.isCollided()) {
			player.position(player.getX(), player.getY() - (int)rows.get(0).getBounds().size());
		}
	}

	public WorldManager(World world, Player player, int playerRow, List<GameRow> rows) {
		this.world = world;
		this.player = player;
		this.playerRow = playerRow;
		this.rows = rows;
		this.player.addPropertyChangeListener(this);
		lowestRow = rows.size();
		obstacles = world.getObstacles();
	}
	
	public void start() {
		moveExecutor = Executors.newSingleThreadScheduledExecutor();
		moveExecutor.scheduleAtFixedRate(this::moveObstacles, 0, 400, TimeUnit.MILLISECONDS);
		player.lives();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propertyName = evt.getPropertyName();
		
		switch(propertyName) {
			case "liveLess":
				endWorld(false);
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
		}
		
	}
}
