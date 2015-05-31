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

/**
 * Launcher class.
 */
public class Game {
	
	/**
	 * The Entry point, grabs language, launch world generation.
	 *
	 * @param args the command line arguments (none handled)
	 */
	public static void main(String[] args) {
		Locale locale = SettingsController.getSettings().getLanguage().getLocale();
		LocaleManager.setLocale(locale);
		
		World world = new World();
		WorldGenerator generator = new WorldGenerator(world);
		
		Supplier<Dimension> sizeProvider = () -> {
			Settings settings = SettingsController.getSettings();
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
