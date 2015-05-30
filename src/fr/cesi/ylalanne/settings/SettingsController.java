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

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.cesi.ylalanne.lang.LocaleManager;
import fr.cesi.ylalanne.settings.model.Actions;
import fr.cesi.ylalanne.settings.model.Difficulties;
import fr.cesi.ylalanne.settings.model.Resolutions;
import fr.cesi.ylalanne.settings.model.Settings;
import fr.cesi.ylalanne.settings.model.SettingsProperties;
import fr.cesi.ylalanne.settings.model.SettingsViewModel;
import fr.cesi.ylalanne.settings.model.Titles;
import fr.cesi.ylalanne.settings.ui.SettingsView;

public class SettingsController implements PropertyChangeListener {
	private SettingsView view;
	private SettingsViewModel model;
	private Settings settings;
	private static final Path settingsPath = Paths.get("./settings.json");
	private JsonFactory factory;
	private boolean initialized;
	
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
	}

	private void handleChoice(Difficulties difficulty) {
		model.setSelectedDifficulty(difficulty);
	}

	private void handleAction(Actions action) {
			switch(action) {
				case OK:
					writeSettings();
					break;
				default:
			}
			view.close();
	}

	private void writeSettings() {
		settings = new Settings();
		settings.setDifficulty(model.getSelectedDifficulty());
		settings.setResolution(model.getSelectedResolution());
		
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
		if(Files.exists(settingsPath)) {
			try(
					InputStream input = Files.newInputStream(settingsPath);
					JsonParser parser = factory.createParser(input)
			) {
				parser.setCodec(new ObjectMapper(factory));
				settings = parser.readValueAs(Settings.class);
				model.setSelectedDifficulty(settings.getDifficulty());
				model.setSelectedResolution(settings.getResolution());
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * Initialize the SettingsController (load existing settings if any)
	 */
	private void init() {
		loadSettings();
		initialized = true;
	}

	public SettingsController() {
		model = new SettingsViewModel();
		view = new SettingsView(model);
		
		factory = new JsonFactory();
		
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
	public Settings getSettings() {
		init();
		
		if(settings == null) {
			settings.setDifficulty(model.getSelectedDifficulty());
			settings.setResolution(model.getSelectedResolution());
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
		}
	}

}
