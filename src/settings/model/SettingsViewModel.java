package settings.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Map;

public class SettingsViewModel {
	private String difficultyTitle;
	private String resolutionTitle;
	private String ok;
	private String cancel;
	private String settingsTitle;
	private Map<Difficulty, String> difficulties;
	private Difficulty selectedDifficulty;
	private Map<Resolution, String> resolutions;
	private Resolution selectedResolution;
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
	 * @return the ok
	 */
	public String getOk() {
		return ok;
	}
	/**
	 * @param ok the ok to set
	 */
	public void setOk(String ok) {
		this.ok = ok;
	}
	/**
	 * @return the cancel
	 */
	public String getCancel() {
		return cancel;
	}
	/**
	 * @param cancel the cancel to set
	 */
	public void setCancel(String cancel) {
		this.cancel = cancel;
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
	public Map<Difficulty, String> getDifficulties() {
		return difficulties;
	}
	/**
	 * @param difficulties the difficulties to set
	 */
	public void setDifficulties(Map<Difficulty, String> difficulties) {
		this.difficulties = difficulties;
	}
	
	/**
	 * @return the selectedDifficulty
	 */
	public Difficulty getSelectedDifficulty() {
		return selectedDifficulty;
	}
	/**
	 * @param selectedDifficulty the selectedDifficulty to set
	 */
	public void setSelectedDifficulty(Difficulty selectedDifficulty) {
		Difficulty oldDifficulty = this.selectedDifficulty;
		this.selectedDifficulty = selectedDifficulty;
		propertyChange.firePropertyChange("selectedDifficulty", oldDifficulty, selectedDifficulty);
	}
	
	/**
	 * @return the resolutions
	 */
	public Map<Resolution, String> getResolutions() {
		return resolutions;
	}
	/**
	 * @param resolutions the resolutions to set
	 */
	public void setResolutions(Map<Resolution, String> resolutions) {
		this.resolutions = resolutions;
	}
	
	/**
	 * @return the selectedResolution
	 */
	public Resolution getSelectedResolution() {
		return selectedResolution;
	}
	/**
	 * @param selectedResolution the selectedResolution to set
	 */
	public void setSelectedResolution(Resolution selectedResolution) {
		Resolution oldResolution = this.selectedResolution;
		this.selectedResolution = selectedResolution;
		propertyChange.firePropertyChange("selectedResolution", oldResolution, selectedResolution);
	}
	
	/**
	 * @param difficulty the seeked difficulty
	 * @return the difficulty string representation
	 */
	public String getDifficulty(Difficulty difficulty) {
		return difficulties.get(difficulty);
	}
	
	/**
	 * @param resolution the seeked resolution
	 * @return the resolution string representation
	 */
	public String getResolution(Resolution resolution) {
		return resolutions.get(resolution);
	}
	
}
