package fr.cesi.ylalanne.game;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class WorldManager implements PropertyChangeListener {
	private World world;
	private Player player;
	private int playerRow;
	private List<GameRow> rows;
	private ScheduledExecutorService moveExecutor;
	
	private void updateY(int y) {
		// TODO Auto-generated method stub
		
	}

	private void updateX(int x) {
		// TODO Auto-generated method stub
		
	}

	private void moveObstacles() {
		for(Obstacle obstacle : world.getObstacles()) {
			obstacle.move();
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
			world.end();
		}
	}

	public WorldManager(World world, Player player, int playerRow, List<GameRow> rows) {
		this.world = world;
		this.player = player;
		this.playerRow = playerRow;
		this.rows = rows;
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
