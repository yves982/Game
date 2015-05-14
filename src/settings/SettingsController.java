package settings;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.swing.JOptionPane;

import lang.Messages;
import settings.model.Actions;
import settings.model.Difficulties;
import settings.model.Resolutions;
import settings.model.SettingsProperties;
import settings.model.SettingsViewModel;
import settings.model.Titles;
import settings.ui.SettingsView;

public class SettingsController implements PropertyChangeListener {
	private Messages messages;
	private SettingsView view;
	private SettingsViewModel model;
	
	public SettingsController(Locale locale) {
		messages = new Messages(locale);
		model = new SettingsViewModel();
		view = new SettingsView(model);
		view.addPropertyChangeListener(this);
		buildModel();
	}
	
	private void fillResolutions(Map<Resolutions, String> resolutions) {
		resolutions.put(Resolutions.LOW, messages.getString(Resolutions.LOW.getKey()));
		resolutions.put(Resolutions.STANDARD, messages.getString(Resolutions.STANDARD.getKey()));
		resolutions.put(Resolutions.HIGH, messages.getString(Resolutions.HIGH.getKey()));
	}

	private void fillDifficulties(Map<Difficulties, String> difficulties) {
		difficulties.put(Difficulties.EASY, messages.getString(Difficulties.EASY.getKey()));
		difficulties.put(Difficulties.NORMAL, messages.getString(Difficulties.NORMAL.getKey()));
		difficulties.put(Difficulties.HARD, messages.getString(Difficulties.HARD.getKey()));
	}

	private void fillActions(Map<Actions, String> actions) {
		actions.put(Actions.OK, messages.getString(Actions.OK.getKey()));
		actions.put(Actions.CANCEL, messages.getString(Actions.CANCEL.getKey()));
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
		
		model.setDifficultyTitle(messages.getString(Titles.DIFFICULTY.getKey()));
		model.setResolutionTitle(messages.getString(Titles.RESOLUTION.getKey()));
	}

	public void start() {
		view.show();
	}

	public void handleChoice(Difficulties difficulty) {
		model.setSelectedDifficulty(difficulty);
		JOptionPane.showMessageDialog(null, messages.getString(difficulty.getKey()), "Difficulty Selected", JOptionPane.OK_OPTION);
	}

	private void handleChoice(Resolutions resolution) {
		model.setSelectedResolution(resolution);
		JOptionPane.showMessageDialog(null, messages.getString(resolution.getKey()), "Resolution Selected", JOptionPane.OK_OPTION);
	}
	
	public void handleAction(Actions action) {
			switch(action) {
				case OK:
					JOptionPane.showMessageDialog(null, String.format("Difficulté: %s, Resolution: %s", 
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
