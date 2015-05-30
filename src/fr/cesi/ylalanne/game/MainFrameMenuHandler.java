package fr.cesi.ylalanne.game;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import fr.cesi.ylalanne.highscores.HighScoreController;
import fr.cesi.ylalanne.mainframe.model.MainFrameActions;
import fr.cesi.ylalanne.settings.SettingsController;
import fr.cesi.ylalanne.utils.sound.SoundManager;

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
				SoundManager.stop();
				System.exit(0);
				break;
			case MUTE:
				mute();
				break;
		}
	}

	private static void highScores() {
		FutureTask<Void> highScoreTask = new FutureTask<Void>(() -> {
			HighScoreController highScoreController = new HighScoreController();
			highScoreController.start();
		}, null);
		ExecutorService exec = Executors.newSingleThreadExecutor();
		exec.execute(highScoreTask);
		exec.shutdown();
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
			if(generator.isHasSpawn()) {
				SoundManager.stop();
			}
			SoundManager.playContinuously("/ambiance.wav");
		}, null);
		ExecutorService exec = Executors.newSingleThreadExecutor();
		exec.execute(startTask);
		exec.shutdown();
	}

	private static void mute() {
		if(SoundManager.isPlaying()) {
			SoundManager.stop();
		} else {
			SoundManager.playContinuously("/ambiance.wav");
		}
	}
}
