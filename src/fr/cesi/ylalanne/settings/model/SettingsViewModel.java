package fr.cesi.ylalanne.settings.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Map;

/**
 * The model for the {@link fr.cesi.ylalanne.settings.ui.SettingsView SettingsView}.
 * <p>It has the following bound properties: </p>
 * <ul>
 *   <li>selectedDifficulty</li>
 *   <li>selectedResolution</li>
 *   <li>selectedLanguage</li>
 * </ul>
 */
public class SettingsViewModel {
	private String difficultyTitle;
	private String resolutionTitle;
	private String settingsTitle;
	private Map<Difficulties, String> difficulties;
	private Difficulties selectedDifficulty;
	private Map<Resolutions, String> resolutions;
	private Resolutions selectedResolution;
	private Map<Actions, String> actions;
	private Map<Languages, String> languages;
	private Languages selectedLanguage;
	private PropertyChangeSupport propertyChange;
	private String langTitle;
	
	/**
	 * Initializs a SettingsViewModel.
	 */
	public SettingsViewModel() {
		propertyChange = new PropertyChangeSupport(this);
	}
	
	/**
	 * Adds the property change listener.
	 *
	 * @param listener the listener
	 * @see java.beans.PropertyChangeSupport#addPropertyChangeListener(java.beans.PropertyChangeListener)
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertyChange.addPropertyChangeListener(listener);
	}
	/**
	 * Removes the property change listener.
	 *
	 * @param listener the listener
	 * @see java.beans.PropertyChangeSupport#removePropertyChangeListener(java.beans.PropertyChangeListener)
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyChange.removePropertyChangeListener(listener);
	}
	/**
	 * Adds the property change listener.
	 *
	 * @param propertyName the property name
	 * @param listener the listener
	 * @see java.beans.PropertyChangeSupport#addPropertyChangeListener(java.lang.String, java.beans.PropertyChangeListener)
	 */
	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		propertyChange.addPropertyChangeListener(propertyName, listener);
	}
	/**
	 * Removes the property change listener.
	 *
	 * @param propertyName the property name
	 * @param listener the listener
	 * @see java.beans.PropertyChangeSupport#removePropertyChangeListener(java.lang.String, java.beans.PropertyChangeListener)
	 */
	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		propertyChange.removePropertyChangeListener(propertyName, listener);
	}

	/**
	 * Gets the difficulty title.
	 *
	 * @return the difficultyTitle
	 */
	public String getDifficultyTitle() {
		return difficultyTitle;
	}
	
	/**
	 * Sets the difficulty title.
	 *
	 * @param difficultyTitle the difficultyTitle to set
	 */
	public void setDifficultyTitle(String difficultyTitle) {
		this.difficultyTitle = difficultyTitle;
	}
	
	/**
	 * Gets the resolution title.
	 *
	 * @return the resolutionTitle
	 */
	public String getResolutionTitle() {
		return resolutionTitle;
	}
	
	/**
	 * Sets the resolution title.
	 *
	 * @param resolutionTitle the resolutionTitle to set
	 */
	public void setResolutionTitle(String resolutionTitle) {
		this.resolutionTitle = resolutionTitle;
	}
	
	/**
	 * Gets the settings title.
	 *
	 * @return the settingsTitle
	 */
	public String getSettingsTitle() {
		return settingsTitle;
	}
	
	/**
	 * Sets the settings title.
	 *
	 * @param settingsTitle the settingsTitle to set
	 */
	public void setSettingsTitle(String settingsTitle) {
		this.settingsTitle = settingsTitle;
	}

	/**
	 * Gets the languages panel title.
	 *
	 * @return the langTitle
	 */
	public String getLangTitle() {
		return this.langTitle;
	}
	
	/**
	 * Sets the languages panel title.
	 *
	 * @param langTitle the langTitle to set
	 */
	public void setLangTitle(String langTitle) {
		this.langTitle = langTitle;
	}

	/**
	 * Gets the difficulties.
	 *
	 * @return the difficulties
	 */
	public Map<Difficulties, String> getDifficulties() {
		return difficulties;
	}
	
	/**
	 * Sets the difficulties.
	 *
	 * @param difficulties the difficulties to set
	 */
	public void setDifficulties(Map<Difficulties, String> difficulties) {
		this.difficulties = difficulties;
	}
	
	/**
	 * Gets the selected difficulty.
	 *
	 * @return the selectedDifficulty
	 */
	public Difficulties getSelectedDifficulty() {
		return selectedDifficulty;
	}
	
	/**
	 * Sets the selected difficulty.
	 *
	 * @param selectedDifficulty the selectedDifficulty to set
	 */
	public void setSelectedDifficulty(Difficulties selectedDifficulty) {
		Difficulties oldDifficulty = this.selectedDifficulty;
		this.selectedDifficulty = selectedDifficulty;
		propertyChange.firePropertyChange("selectedDifficulty", oldDifficulty, selectedDifficulty);
	}
	
	/**
	 * Gets the resolutions.
	 *
	 * @return the resolutions
	 */
	public Map<Resolutions, String> getResolutions() {
		return resolutions;
	}
	
	/**
	 * Sets the resolutions.
	 *
	 * @param resolutions the resolutions to set
	 */
	public void setResolutions(Map<Resolutions, String> resolutions) {
		this.resolutions = resolutions;
	}
	
	/**
	 * Gets the actions.
	 *
	 * @return the actions
	 */
	public Map<Actions, String> getActions() {
		return actions;
	}
	
	/**
	 * Sets the actions.
	 *
	 * @param actions the actions to set
	 */
	public void setActions(Map<Actions, String> actions) {
		this.actions = actions;
	}

	/**
	 * Gets the languages.
	 *
	 * @return the languages
	 */
	public Map<Languages, String> getLanguages() {
		return languages;
	}
	
	/**
	 * Sets the languages.
	 *
	 * @param languages the languages to set
	 */
	public void setLanguages(Map<Languages, String> languages) {
		this.languages = languages;
	}

	/**
	 * Gets the selected resolution.
	 *
	 * @return the selectedResolution
	 */
	public Resolutions getSelectedResolution() {
		return selectedResolution;
	}
	
	/**
	 * Sets the selected resolution.
	 *
	 * @param selectedResolution the selectedResolution to set
	 */
	public void setSelectedResolution(Resolutions selectedResolution) {
		Resolutions oldResolution = this.selectedResolution;
		this.selectedResolution = selectedResolution;
		propertyChange.firePropertyChange("selectedResolution", oldResolution, selectedResolution);
	}
	
	/**
	 * Gets the selected language.
	 *
	 * @return the selectedLanguage
	 */
	public Languages getSelectedLanguage() {
		return selectedLanguage;
	}
	
	/**
	 * Sets the selected language.
	 *
	 * @param selectedLanguage the selectedLanguage to set
	 */
	public void setSelectedLanguage(Languages selectedLanguage) {
		Languages oldSelectedLanguage = this.selectedLanguage;
		this.selectedLanguage = selectedLanguage;
		propertyChange.firePropertyChange("selectedLanguage", oldSelectedLanguage, selectedLanguage);
	}

	/**
	 * Gets the difficulty.
	 *
	 * @param difficulty the seeked difficulty
	 * @return the difficulty string representation
	 */
	public String getDifficulty(Difficulties difficulty) {
		return difficulties.get(difficulty);
	}
	
	/**
	 * Gets the resolution.
	 *
	 * @param resolution the seeked resolution
	 * @return the resolution string representation
	 */
	public String getResolution(Resolutions resolution) {
		return resolutions.get(resolution);
	}
	
	/**
	 * Gets the action.
	 *
	 * @param action the seeked action
	 * @return the action string representation
	 */
	public String getAction(Actions action) {
		return actions.get(action);
	}
	
	/**
	 * Gets the language.
	 *
	 * @param lang the seeked Languages
	 * @return the Languages string representation
	 */
	public String getLanguage(Languages lang) {
		return languages.get(lang);
	}
}
