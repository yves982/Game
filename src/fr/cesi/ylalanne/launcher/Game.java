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
			int [] xSteps = new int[] {
					5,5,5,5,
					5,5,5,5,
					5,5,5
			};
			generator.spawnWorld(xSteps);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
