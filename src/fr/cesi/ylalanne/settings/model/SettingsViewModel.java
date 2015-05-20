package fr.cesi.ylalanne.settings.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Map;

public class SettingsViewModel {
	private String difficultyTitle;
	private String resolutionTitle;
	private String settingsTitle;
	private Map<Difficulties, String> difficulties;
	private Difficulties selectedDifficulty;
	private Map<Resolutions, String> resolutions;
	private Resolutions selectedResolution;
	private Map<Actions, String> actions;
	private PropertyChangeSupport propertyChange;
	
	public SettingsViewModel() {
		propertyChange = new PropertyChangeSupport(this);
	}
	
	/**
	 * @param listener
	 * @see java.beans.PropertyChangeSupport#addPropertyChangeListener(java.beans.PropertyChangeListener)
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertyChange.addPropertyChangeListener(listener);
	}

	/**
	 * @param listener
	 * @see java.beans.PropertyChangeSupport#removePropertyChangeListener(java.beans.PropertyChangeListener)
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyChange.removePropertyChangeListener(listener);
	}

	/**
	 * @param propertyName
	 * @param listener
	 * @see java.beans.PropertyChangeSupport#addPropertyChangeListener(java.lang.String, java.beans.PropertyChangeListener)
	 */
	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		propertyChange.addPropertyChangeListener(propertyName, listener);
	}

	/**
	 * @param propertyName
	 * @param listener
	 * @see java.beans.PropertyChangeSupport#removePropertyChangeListener(java.lang.String, java.beans.PropertyChangeListener)
	 */
	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		propertyChange.removePropertyChangeListener(propertyName, listener);
	}

	/**
	 * @return the difficultyTitle
	 */
	public String getDifficultyTitle() {
		return difficultyTitle;
	}
	/**
	 * @param difficultyTitle the difficultyTitle to set
	 */
	public void setDifficultyTitle(String difficultyTitle) {
		this.difficultyTitle = difficultyTitle;
	}
	/**
	 * @return the resolutionTitle
	 */
	public String getResolutionTitle() {
		return resolutionTitle;
	}
	/**
	 * @param resolutionTitle the resolutionTitle to set
	 */
	public void setResolutionTitle(String resolutionTitle) {
		this.resolutionTitle = resolutionTitle;
	}
	
	/**
	 * @return the settingsTitle
	 */
	public String getSettingsTitle() {
		return settingsTitle;
	}
	/**
	 * @param settingsTitle the settingsTitle to set
	 */
	public void setSettingsTitle(String settingsTitle) {
		this.settingsTitle = settingsTitle;
	}

	/**
	 * @return the difficulties
	 */
	public Map<Difficulties, String> getDifficulties() {
		return difficulties;
	}
	/**
	 * @param difficulties the difficulties to set
	 */
	public void setDifficulties(Map<Difficulties, String> difficulties) {
		this.difficulties = difficulties;
	}
	
	/**
	 * @return the selectedDifficulty
	 */
	public Difficulties getSelectedDifficulty() {
		return selectedDifficulty;
	}
	/**
	 * @param selectedDifficulty the selectedDifficulty to set
	 */
	public void setSelectedDifficulty(Difficulties selectedDifficulty) {
		Difficulties oldDifficulty = this.selectedDifficulty;
		this.selectedDifficulty = selectedDifficulty;
		propertyChange.firePropertyChange("selectedDifficulty", oldDifficulty, selectedDifficulty);
	}
	
	/**
	 * @return the resolutions
	 */
	public Map<Resolutions, String> getResolutions() {
		return resolutions;
	}
	/**
	 * @param resolutions the resolutions to set
	 */
	public void setResolutions(Map<Resolutions, String> resolutions) {
		this.resolutions = resolutions;
	}
	
	/**
	 * @return the actions
	 */
	public Map<Actions, String> getActions() {
		return actions;
	}
	/**
	 * @param actions the actions to set
	 */
	public void setActions(Map<Actions, String> actions) {
		this.actions = actions;
	}

	/**
	 * @return the selectedResolution
	 */
	public Resolutions getSelectedResolution() {
		return selectedResolution;
	}
	/**
	 * @param selectedResolution the selectedResolution to set
	 */
	public void setSelectedResolution(Resolutions selectedResolution) {
		Resolutions oldResolution = this.selectedResolution;
		this.selectedResolution = selectedResolution;
		propertyChange.firePropertyChange("selectedResolution", oldResolution, selectedResolution);
	}
	
	/**
	 * @param difficulty the seeked difficulty
	 * @return the difficulty string representation
	 */
	public String getDifficulty(Difficulties difficulty) {
		return difficulties.get(difficulty);
	}
	
	/**
	 * @param resolution the seeked resolution
	 * @return the resolution string representation
	 */
	public String getResolution(Resolutions resolution) {
		return resolutions.get(resolution);
	}
	
	/**
	 * @param action the seeked action
	 * @return the action string representation
	 */
	public String getAction(Actions action) {
		return actions.get(action);
	}
}