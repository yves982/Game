package fr.cesi.ylalanne.launcher;

import java.awt.Dimension;
import java.util.Locale;
import java.util.function.Supplier;

import fr.cesi.ylalanne.game.MainFrameMenuHandler;
import fr.cesi.ylalanne.game.World;
import fr.cesi.ylalanne.game.WorldGenerator;
import fr.cesi.ylalanne.lang.LocaleManager;
import fr.cesi.ylalanne.mainframe.MainFrameController;
import fr.cesi.ylalanne.settings.SettingsController;
import fr.cesi.ylalanne.settings.model.Settings;

public class Game {
	
	public static void main(String[] args) {
		LocaleManager.setLocale(Locale.FRANCE);
		World world = new World();
		WorldGenerator generator = new WorldGenerator(world);
		SettingsController settingsController = new SettingsController();
		
		Supplier<Dimension> sizeProvider = () -> {
			Settings settings = settingsController.getSettings();
			Dimension size = new Dimension(settings.getResolution().getWidth(), settings.getResolution().getHeight());
			return size;
		};
		
		MainFrameController controller = new MainFrameController(world, 
				(action) -> MainFrameMenuHandler.handleAction(action, generator), sizeProvider);
		
		try {
			controller.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
