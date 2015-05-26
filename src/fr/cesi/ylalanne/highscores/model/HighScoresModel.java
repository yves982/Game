package fr.cesi.ylalanne.highscores.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Model for the high scores
 */
public class HighScoresModel {
	private List<HighScore> highscores;
	
	/**
	 * Initialize an instance
	 */
	public HighScoresModel() {
		highscores = new ArrayList<HighScore>();
	}

	/**
	 * @return the high scores
	 */
	public List<HighScore> getHighscores() {
		return highscores;
	}
}
