package fr.cesi.ylalanne.settings;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import fr.cesi.ylalanne.lang.LocaleManager;
import fr.cesi.ylalanne.settings.model.Actions;
import fr.cesi.ylalanne.settings.model.Difficulties;
import fr.cesi.ylalanne.settings.model.Resolutions;
import fr.cesi.ylalanne.settings.model.SettingsProperties;
import fr.cesi.ylalanne.settings.model.SettingsViewModel;
import fr.cesi.ylalanne.settings.model.Titles;
import fr.cesi.ylalanne.settings.ui.SettingsView;

public class SettingsController implements PropertyChangeListener {
	private SettingsView view;
	private SettingsViewModel model;
	
	private void fillResolutions(Map<Resolutions, String> resolutions) {
		resolutions.put(Resolutions.LOW, LocaleManager.getString(Resolutions.LOW.getKey()));
		resolutions.put(Resolutions.STANDARD, LocaleManager.getString(Resolutions.STANDARD.getKey()));
		resolutions.put(Resolutions.HIGH, LocaleManager.getString(Resolutions.HIGH.getKey()));
	}

	private void fillDifficulties(Map<Difficulties, String> difficulties) {
		difficulties.put(Difficulties.EASY, LocaleManager.getString(Difficulties.EASY.getKey()));
		difficulties.put(Difficulties.NORMAL, LocaleManager.getString(Difficulties.NORMAL.getKey()));
		difficulties.put(Difficulties.HARD, LocaleManager.getString(Difficulties.HARD.getKey()));
	}

	private void fillActions(Map<Actions, String> actions) {
		actions.put(Actions.OK, LocaleManager.getString(Actions.OK.getKey()));
		actions.put(Actions.CANCEL, LocaleManager.getString(Actions.CANCEL.getKey()));
	}

	private void buildModel() {
		Map<Difficulties, String> difficulties = new HashMap<Difficulties, String>();
		Map<Resolutions, String> resolutions = new HashMap<Resolutions, String>();
		Map<Actions, String> actions = new HashMap<Actions, String>();
		model.setDifficulties(difficulties);
		model.setSelectedDifficulty(Difficulties.EASY);
		model.setResolutions(resolutions);
		model.setSelectedResolution(Resolutions.LOW);
		model.setActions(actions);
		
		fillDifficulties(difficulties);
		fillResolutions(resolutions);
		fillActions(actions);
		
		model.setDifficultyTitle(LocaleManager.getString(Titles.DIFFICULTY.getKey()));
		model.setResolutionTitle(LocaleManager.getString(Titles.RESOLUTION.getKey()));
		model.setSettingsTitle(LocaleManager.getString(Titles.SETTINGS.getKey()));
	}

	private void handleChoice(Resolutions resolution) {
		model.setSelectedResolution(resolution);
		JOptionPane.showMessageDialog(null, LocaleManager.getString(resolution.getKey()), "Resolution Selected", JOptionPane.OK_OPTION);
	}

	public SettingsController() {
		model = new SettingsViewModel();
		view = new SettingsView(model);
		view.addPropertyChangeListener(this);
		buildModel();
	}

	public void start() {
		view.show();
	}

	public void handleChoice(Difficulties difficulty) {
		model.setSelectedDifficulty(difficulty);
		JOptionPane.showMessageDialog(null, LocaleManager.getString(difficulty.getKey()), "Difficulty Selected", JOptionPane.OK_OPTION);
	}

	public void handleAction(Actions action) {
			switch(action) {
				case OK:
					JOptionPane.showMessageDialog(null, String.format("Difficult�: %s, Resolution: %s", 
							model.getSelectedDifficulty(), model.getSelectedResolution()));
					break;
				default:
			}
			view.close();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propertyName = evt.getPropertyName();
		SettingsProperties property = SettingsProperties.valueOf(SettingsProperties.class, propertyName);
		
		switch(property) {
			case DIFFICULTY:
				handleChoice((Difficulties)evt.getNewValue());
				break;
			case RESOLUTION:
				handleChoice((Resolutions)evt.getNewValue());
				break;
			case ACTION:
				handleAction((Actions)evt.getNewValue());
				break;
		}
	}

}