package fr.cesi.ylalanne.highscores.model;

/**
 * The HighScore model.
 */
public class HighScore {
	private String name;
	private int score;
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the name.
	 *
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the score.
	 *
	 * @return the score
	 */
	public int getScore() {
		return score;
	}
	
	/**
	 * Sets the score.
	 *
	 * @param score the score to set
	 */
	public void setScore(int score) {
		this.score = score;
	}
}
