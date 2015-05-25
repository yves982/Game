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
	private int playerX;
	private int playerY;
	private ScheduledExecutorService moveExecutor;
	
	private void updateY(int y) {
		playerY = y;
		Optional<GameRow> currentRow = rows.stream()
			.filter( gameRow -> gameRow.getBounds().isIn(y + (int)Math.ceil(player.getHeight() / 2.0d)) )
			.findFirst();
		
		if(currentRow.isPresent()) {
			playerRow = rows.indexOf(currentRow.get()) +1;
			int playerCenterX = player.getX() + (int)Math.ceil(player.getWidth() / 2.0d);
			if(playerRow == 1 && currentRow.get().getWinningAreas().stream().anyMatch( area -> area.isIn(playerCenterX))) {
				player.win();
				world.end(true);
			} else if (playerRow >= 2 && playerRow <= 4) {
				long potentialColliders = world.getObstacles()
					.stream()
					.filter( obstacle -> obstacle.isWithin(playerX, y, 2))
					.count();
				
				if(potentialColliders <= 0) {
					player.kill();
				}
			}
		}
		
		checkPlayer();
	}

	private void checkPlayer() {
		Optional<Obstacle> collider = world.getObstacles()
				.stream()
				.filter( obstacle -> obstacle.isWithin(playerX, playerY, 30) )
				.filter( closeObstacle -> closeObstacle.checkCollision(player) )
				.findFirst();
		if(collider.isPresent()) {
			player.collides(collider.get());
		}
	}

	private void updateX(int x) {
		playerX = x;
		checkPlayer();
	}

	private void moveObstacles() {
		for(Obstacle obstacle : world.getObstacles()) {
			obstacle.move();
			if(obstacle.isWithin(playerX, playerY, 30) && obstacle.checkCollision(player)) {
				player.collides(obstacle);
			}
		}
	}
	
	private void endWorld() {
		try {
			moveExecutor.shutdown();
			moveExecutor.awaitTermination(500, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			world.end(false);
		}
	}

	public WorldManager(World world, Player player, int playerRow, List<GameRow> rows) {
		this.world = world;
		this.player = player;
		this.playerRow = playerRow;
		this.rows = rows;
		playerX = player.getX();
		playerY = player.getY();
		this.player.addPropertyChangeListener(this);
	}
	
	public void start() {
		moveExecutor = Executors.newSingleThreadScheduledExecutor();
		moveExecutor.scheduleAtFixedRate(this::moveObstacles, 0, 400, TimeUnit.MILLISECONDS);
		player.lives();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propertyName = evt.getPropertyName();
		Object newValue = evt.getNewValue();
		
		switch(propertyName) {
			case "x":
				updateX((int)newValue);
				break;
			case "y":
				updateY((int)newValue);
				break;
			case "liveLess":
				endWorld();
				break;
		}
		
	}
}
