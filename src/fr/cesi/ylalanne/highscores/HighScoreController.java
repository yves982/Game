package fr.cesi.ylalanne.highscores;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.cesi.ylalanne.contracts.IHighScoreController;
import fr.cesi.ylalanne.highscores.model.HighScore;
import fr.cesi.ylalanne.highscores.model.HighScoresModel;
import fr.cesi.ylalanne.highscores.ui.HighScoreEntryView;
import fr.cesi.ylalanne.highscores.ui.HighScoreView;

public class HighScoreController implements PropertyChangeListener, IHighScoreController {
	private HighScoresModel model;
	private HighScoreView view;
	private boolean initialized;
	private JsonFactory factory;
	private HighScoreEntryView entryView;
	private int currentHighScore;
	private static final Path settingsPath = Paths.get("./highscores.json");
	
	/**
	 * Initialize the HighScoreController (load existing highscores if any)
	 */
	private void init() {
		loadHighScores();
		initialized = true;
		view.update(model);
	}
	
	private void loadHighScores() {
		if(Files.exists(settingsPath)) {
			try(
					InputStream input = Files.newInputStream(settingsPath);
					JsonParser parser = factory.createParser(input)
			) {
				parser.setCodec(new ObjectMapper(factory));
				model = parser.readValueAs(HighScoresModel.class);
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
	}
	
	
	private void updateName(String name) {
		HighScore highScore = new HighScore();
		highScore.setName(name);
		highScore.setScore(currentHighScore);
		
		model.getHighscores().add(highScore);
		writeHighScores();
	}

	public HighScoreController() {
		model = new HighScoresModel();
		view = new HighScoreView();
		
		factory = new JsonFactory();
		view.build();
		initialized = false;
	}
	
	public void writeHighScores() {
		if(!initialized) {
			init();
		}
		
		try(
				OutputStream output = Files.newOutputStream(settingsPath);
				JsonGenerator generator = factory.createGenerator(output, JsonEncoding.UTF8)
		) {
			generator.setCodec(new ObjectMapper(factory));
			generator.writeObject(model);
			generator.flush();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * @return the high scores
	 */
	public List<HighScore> getHighScores() {
		return model.getHighscores();
	}
	
	/**
	 * Starts this controller shows its view
	 */
	public void start() {
		if(!initialized) {
			init();
		}
		view.show();
	}
	
	public void askName(int highScore) {
		if(!initialized) {
			init();
		}
		currentHighScore = highScore;
		entryView = new HighScoreEntryView(highScore);
		entryView.build();
		entryView.show();
		entryView.addPropertyChangeListener(this);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propertyName = evt.getPropertyName();
		
		switch(propertyName) {
			case "name":
				updateName(evt.getNewValue().toString());
				break;
		}
	}
}
