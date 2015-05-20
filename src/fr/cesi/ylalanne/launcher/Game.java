package fr.cesi.ylalanne.launcher;

import fr.cesi.ylalanne.game.World;
import fr.cesi.ylalanne.game.WorldGenerator;
import fr.cesi.ylalanne.lang.LocaleManager;
import fr.cesi.ylalanne.mainframe.MainFrameController;

import java.util.Locale;

public class Game {

	public static void main(String[] args) {
		LocaleManager.setLocale(Locale.FRENCH);
		World world = new World();
		WorldGenerator generator = new WorldGenerator(world);
		MainFrameController controller = new MainFrameController(world);
		
		try {
			controller.start();
			generator.spawnWorld();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
