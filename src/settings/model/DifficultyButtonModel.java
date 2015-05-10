package settings.model;

import javax.swing.DefaultButtonModel;

public class DifficultyButtonModel extends DefaultButtonModel {
	private static final long serialVersionUID = 5930816189079552126L;
	private Difficulty difficulty;
	
	/**
	 * @param difficulty the difficulty to set
	 */
	public DifficultyButtonModel(Difficulty difficulty) {
		setDifficulty(difficulty);
	}
	
	/**
	 * @return the difficulty
	 */
	public Difficulty getDifficulty() {
		return difficulty;
	}
	/**
	 * @param difficulty the difficulty to set
	 */
	public void setDifficulty(Difficulty difficulty) {
		this.difficulty = difficulty;
	}

}
