package fr.cesi.ylalanne.game;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import fr.cesi.ylalanne.lang.LocaleManager;

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
			.filter( gameRow -> gameRow.getBounds().isIn(y + (int)Math.ceil(player.getHeight() /2.0d)) )
			.findFirst();
		
		if(currentRow.isPresent()) {
			playerRow = rows.indexOf(currentRow.get());
		}
		
		checkPlayer();
	}

	private void checkPlayer() {
		Optional<Obstacle> collider = world.getObstacles()
				.parallelStream()
				.filter( obstacle -> obstacle.isWithin(playerX, playerY, 30) )
				.filter( closeObstacle -> closeObstacle.checkCollision(player) )
				.findFirst();
		if(collider.isPresent()) {
			player.collides(collider.get());
			JOptionPane.showMessageDialog(null, "collision detected 2");
		}
	}

	private void updateX(int x) {
		playerX = x;
		checkPlayer();
	}

	private void moveObstacles() {
		for(Obstacle obstacle : world.getObstacles()) {
			obstacle.move();
			if(!player.isCollided() && obstacle.isWithin(playerX, playerY, 30) && obstacle.checkCollision(player)) {
				player.collides(obstacle);
				JOptionPane.showMessageDialog(null, "collision detected");
			}
		}
	}
	
	private void endWorld() {
		try {
			moveExecutor.shutdown();
			moveExecutor.awaitTermination(500, TimeUnit.MILLISECONDS);
			JOptionPane.showMessageDialog(null, LocaleManager.getString(WorldManagerStrings.END_GAME.getKey()));
		} catch (InterruptedException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			world.end();
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
