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
	private int lowestRow;
	private ScheduledExecutorService moveExecutor;
	private ScheduledExecutorService collisionExecutor;
	
	
	private void checkCollision() {
		List<Obstacle> obstacles = world.getObstacles();
		Optional<Obstacle> collider = obstacles.stream()
		.filter(obstacle -> obstacle.isWithin(player.getX() + (int)Math.ceil(player.getWidth() / 2.0d), player.getY() + (int)Math.ceil(player.getHeight() / 2.0d), 25))
		.filter( closeObstacle -> closeObstacle.checkCollision(player))
		.findFirst();
		
		Optional<GameRow> currentRow = rows.stream()
				.filter(row -> row.getBounds().isIn(player.getY() + (int)Math.ceil(player.getHeight()/2.0d)))
				.findFirst();
		if(currentRow.isPresent()) {
			playerRow = rows.indexOf(currentRow.get()) +1;
			if(playerRow < lowestRow) {
				lowestRow = playerRow;
				player.gains(10);
			}
		}
		
		if(collider.isPresent()) {
			player.collides(collider.get());
		}
		
		int centerX = player.getX() + (int)Math.ceil(player.getWidth() / 2.0d);
		int centerY = player.getY() + (int)Math.ceil(player.getHeight() / 2.0d);
		
		boolean hasVeryCloseObstacles = 
				obstacles
				.stream()
				.anyMatch(obstacle -> obstacle.isWithin(centerX, centerY, 2));
		
		if(playerRow >=2 && playerRow <= 4 && !hasVeryCloseObstacles) {
			player.kill();
		} else if (playerRow == 1) {
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

	private void moveObstacles() {
		for(Obstacle obstacle : world.getObstacles()) {
			obstacle.move();
		}
	}

	private void endWorld(boolean won) {
		try {
			moveExecutor.shutdown();
			moveExecutor.awaitTermination(500, TimeUnit.MILLISECONDS);
			collisionExecutor.shutdown();
			collisionExecutor.awaitTermination(500, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			player.removePropertyChangeListener(this);
			world.end(won);
		}
	}

	public WorldManager(World world, Player player, int playerRow, List<GameRow> rows) {
		this.world = world;
		this.player = player;
		this.playerRow = playerRow;
		this.rows = rows;
		this.player.addPropertyChangeListener(this);
		lowestRow = rows.size();
	}
	
	public void start() {
		moveExecutor = Executors.newSingleThreadScheduledExecutor();
		moveExecutor.scheduleAtFixedRate(this::moveObstacles, 0, 400, TimeUnit.MILLISECONDS);
		collisionExecutor = Executors.newSingleThreadScheduledExecutor();
		collisionExecutor.scheduleAtFixedRate(this::checkCollision, 0, 80, TimeUnit.MILLISECONDS);
		player.lives();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propertyName = evt.getPropertyName();
		
		switch(propertyName) {
			case "liveLess":
				endWorld(false);
				break;
		}
		
	}
}
