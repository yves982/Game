package fr.cesi.ylalanne.game;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import javax.swing.JOptionPane;

import fr.cesi.ylalanne.mainframe.model.MainFrameActions;
import fr.cesi.ylalanne.settings.SettingsController;

public class MainFrameMenuHandler {
	public static void handleAction(MainFrameActions action, WorldGenerator generator) {
		switch(action) {
			case SETTINGS:
			settings();
				break;
			case HIGH_SCORE:
			highScores();
				break;
			case START:
			start(generator);
				break;
			case QUIT:
				System.exit(0);
				break;
		}
	}

	private static void highScores() {
		JOptionPane.showMessageDialog(null, "high scores was clicked", "Info", JOptionPane.INFORMATION_MESSAGE);
	}

	private static void settings() {
		FutureTask<Void> settingsTask = new FutureTask<Void>(() -> {
			SettingsController settingsController = new SettingsController();
			settingsController.start();
		}, null);
		ExecutorService exec = Executors.newSingleThreadExecutor();
		exec.execute(settingsTask);
		exec.shutdown();
	}

	private static void start(WorldGenerator generator) {
		FutureTask<Void> startTask = new FutureTask<Void>( () -> {
			generator.spawnWorld();
		}, null);
		ExecutorService exec = Executors.newSingleThreadExecutor();
		exec.execute(startTask);
		exec.shutdown();
	}
}
