package fr.cesi.ylalanne.highscores.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Model for the high scores ( a list of {@link HighScore} ).
 */
public class HighScoresModel {
	private List<HighScore> highscores;
	
	/**
	 * Initialize an instance.
	 */
	public HighScoresModel() {
		highscores = new ArrayList<HighScore>();
	}

	/**
	 * Gets the high scores.
	 *
	 * @return the high scores
	 */
	public List<HighScore> getHighscores() {
		return highscores;
	}
}
