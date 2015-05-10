package settings;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.swing.ButtonModel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

import lang.Messages;
import settings.model.Difficulty;
import settings.model.DifficultyButtonModel;
import settings.model.Resolution;
import settings.model.ResolutionButtonModel;
import settings.model.SettingsStrings;
import settings.model.SettingsViewModel;
import settings.ui.SettingsView;

public class SettingsController implements ActionListener {
	private Messages messages;
	private SettingsView view;
	private SettingsViewModel model;
	
	public SettingsController(Locale locale) {
		messages = new Messages(locale);
		model = new SettingsViewModel();
		view = new SettingsView(this, model);
		
		buildModel();
	}
	
	private void fillResolutions(Map<Resolution, String> resolutions) {
		resolutions.put(Resolution.LOW, messages.getString(Resolution.LOW.getKey()));
		resolutions.put(Resolution.STANDARD, messages.getString(Resolution.STANDARD.getKey()));
		resolutions.put(Resolution.HIGH, messages.getString(Resolution.HIGH.getKey()));
	}

	private void fillDifficulties(Map<Difficulty, String> difficulties) {
		difficulties.put(Difficulty.EASY, messages.getString(Difficulty.EASY.getKey()));
		difficulties.put(Difficulty.NORMAL, messages.getString(Difficulty.NORMAL.getKey()));
		difficulties.put(Difficulty.HARD, messages.getString(Difficulty.HARD.getKey()));
	}

	private void buildModel() {
		Map<Difficulty, String> difficulties = new HashMap<Difficulty, String>();
		Map<Resolution, String> resolutions = new HashMap<Resolution, String>();
		model.setDifficulties(difficulties);
		model.setSelectedDifficulty(Difficulty.EASY);
		model.setResolutions(resolutions);
		model.setSelectedResolution(Resolution.LOW);
		
		fillDifficulties(difficulties);
		fillResolutions(resolutions);
		
		model.setOk(messages.getString(SettingsStrings.MAIN_BUTTONS_OK.getKey()));
		model.setCancel(messages.getString(SettingsStrings.MAIN_BUTTONS_CANCEL.getKey()));
		model.setDifficultyTitle(messages.getString(SettingsStrings.DIFFICULTY_TITLE.getKey()));
		model.setResolutionTitle(SettingsStrings.RESOLUTION_TITLE.getKey());
	}

	public void start() {
		view.show();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JRadioButton button = (JRadioButton)e.getSource();
		ButtonModel buttonModel = button.getModel();
		
		if(buttonModel instanceof DifficultyButtonModel) {
			Difficulty difficulty = ((DifficultyButtonModel)buttonModel).getDifficulty();
			model.setSelectedDifficulty(difficulty);
			JOptionPane.showMessageDialog(null, messages.getString(difficulty.getKey()), "Difficulty Selected", JOptionPane.OK_OPTION);
		} else if (buttonModel instanceof ResolutionButtonModel) {
			Resolution resolution = ((ResolutionButtonModel)buttonModel).getResolution();
			model.setSelectedResolution(resolution);
			JOptionPane.showMessageDialog(null, messages.getString(resolution.getKey()), "Resolution Selected", JOptionPane.OK_OPTION);
		}
	}
}
