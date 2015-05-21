package fr.cesi.ylalanne.game;

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
		SettingsController settingsController = new SettingsController();
		settingsController.start();
	}

	private static void start(WorldGenerator generator) {
		int [] xSteps = new int[] {
				5,5,5,5,
				5,5,5,5,
				5,5,5
		};
		generator.spawnWorld(xSteps);
	}
}
