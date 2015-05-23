package fr.cesi.ylalanne.launcher;

import java.util.Locale;

import fr.cesi.ylalanne.game.MainFrameMenuHandler;
import fr.cesi.ylalanne.game.World;
import fr.cesi.ylalanne.game.WorldGenerator;
import fr.cesi.ylalanne.lang.LocaleManager;
import fr.cesi.ylalanne.mainframe.MainFrameController;

public class Game {
	
	public static void main(String[] args) {
		LocaleManager.setLocale(Locale.FRANCE);
		World world = new World();
		WorldGenerator generator = new WorldGenerator(world);
		
		
		MainFrameController controller = new MainFrameController(world, 
				(action) -> MainFrameMenuHandler.handleAction(action, generator));
		
		try {
			controller.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
