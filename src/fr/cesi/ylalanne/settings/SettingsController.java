package fr.cesi.ylalanne.settings;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.cesi.ylalanne.lang.LocaleManager;
import fr.cesi.ylalanne.settings.model.Actions;
import fr.cesi.ylalanne.settings.model.Difficulties;
import fr.cesi.ylalanne.settings.model.Languages;
import fr.cesi.ylalanne.settings.model.Resolutions;
import fr.cesi.ylalanne.settings.model.Settings;
import fr.cesi.ylalanne.settings.model.SettingsProperties;
import fr.cesi.ylalanne.settings.model.SettingsViewModel;
import fr.cesi.ylalanne.settings.model.Titles;
import fr.cesi.ylalanne.settings.ui.SettingsView;

public class SettingsController implements PropertyChangeListener {
	private SettingsView view;
	private SettingsViewModel model;
	private static Settings settings;
	private static final Path settingsPath = Paths.get("./settings.json");
	private static JsonFactory factory = new JsonFactory();
	private boolean initialized;
	private boolean langChanged;
	
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

	private void fillLanguages(Map<Languages, String> languages) {
		languages.put(Languages.FRENCH, LocaleManager.getString(Languages.FRENCH.getKey()));
		languages.put(Languages.ENGLISH,  LocaleManager.getString(Languages.ENGLISH.getKey()));
	}

	private void buildModel() {
		Map<Difficulties, String> difficulties = new HashMap<Difficulties, String>();
		Map<Resolutions, String> resolutions = new HashMap<Resolutions, String>();
		Map<Actions, String> actions = new HashMap<Actions, String>();
		Map<Languages, String> languages = new HashMap<Languages, String>();
		
		model.setDifficulties(difficulties);
		model.setSelectedDifficulty(Difficulties.EASY);
		model.setResolutions(resolutions);
		model.setSelectedResolution(Resolutions.LOW);
		model.setActions(actions);
		model.setLanguages(languages);
		model.setSelectedLanguage(Languages.FRENCH);
		
		fillDifficulties(difficulties);
		fillResolutions(resolutions);
		fillActions(actions);
		fillLanguages(languages);
		
		model.setDifficultyTitle(LocaleManager.getString(Titles.DIFFICULTY.getKey()));
		model.setResolutionTitle(LocaleManager.getString(Titles.RESOLUTION.getKey()));
		model.setSettingsTitle(LocaleManager.getString(Titles.SETTINGS.getKey()));
		model.setLangTitle(LocaleManager.getString(Titles.LANG.getKey()));
	}

	private void handleChoice(Resolutions resolution) {
		model.setSelectedResolution(resolution);
	}

	private void handleChoice(Difficulties difficulty) {
		model.setSelectedDifficulty(difficulty);
	}

	private void handleAction(Actions action) {
			switch(action) {
				case OK:
					writeSettings();
					if(langChanged) {
						JOptionPane.showMessageDialog(null, LocaleManager.getString(SettingsStrings.CHANGE_LANG.getKey()));
					}
					break;
				default:
			}
			view.close();
	}

	private void handleLanguage(Languages lang) {
		model.setSelectedLanguage(lang);
		langChanged = !langChanged;
	}

	private void writeSettings() {
		settings = new Settings();
		settings.setDifficulty(model.getSelectedDifficulty());
		settings.setResolution(model.getSelectedResolution());
		settings.setLanguage(model.getSelectedLanguage());
		
		try(
				OutputStream output = Files.newOutputStream(settingsPath);
				JsonGenerator generator = factory.createGenerator(output, JsonEncoding.UTF8)
		) {
			generator.setCodec(new ObjectMapper(factory));
			generator.writeObject(settings);
			generator.flush();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private void loadSettings() {
		model.setSelectedDifficulty(settings.getDifficulty());
		model.setSelectedResolution(settings.getResolution());
		model.setSelectedLanguage(settings.getLanguage());
	}

	/**
	 * Initialize the SettingsController (load existing settings if any)
	 */
	private void init() {
		getSettings();
		loadSettings();
		initialized = true;
	}

	public SettingsController() {
		model = new SettingsViewModel();
		view = new SettingsView(model);
		langChanged = false;
		
		view.addPropertyChangeListener(this);
		buildModel();
		view.build();
	}
	
	/**
	 * Start the SettingsController (shows its view)
	 */
	public void start() {
		if(!initialized) {
			init();
		}
		view.show();
	}

	/**
	 * @return the settings
	 */
	public static Settings getSettings() {
		if(Files.exists(settingsPath)) {
			try(
					InputStream input = Files.newInputStream(settingsPath);
					JsonParser parser = factory.createParser(input)
			) {
				parser.setCodec(new ObjectMapper(factory));
				settings = parser.readValueAs(Settings.class);
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		
			if(settings == null) {
				settings = new Settings();
				settings.setDifficulty(Difficulties.EASY);
				settings.setResolution(Resolutions.LOW);
				settings.setLanguage(Languages.FRENCH);
			}
		}
		return settings;
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
			case LANG:
				handleLanguage((Languages)evt.getNewValue());
				break;
		}
	}

}
