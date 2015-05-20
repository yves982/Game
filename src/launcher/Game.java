package launcher;

import game.World;

import java.util.Locale;

import lang.LocaleManager;
import mainframe.MainMenuController;

public class Game {

	public static void main(String[] args) {
		LocaleManager.setLocale(Locale.FRENCH);
		World world = new World();
		MainMenuController controller = new MainMenuController(world);
		try {
			controller.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
