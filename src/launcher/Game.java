package launcher;

import game.World;

import java.util.Locale;

import menu.MainMenuController;

public class Game {

	public static void main(String[] args) {
		World world = new World();
		MainMenuController controller = new MainMenuController(Locale.FRENCH, world);
		controller.start();
	}

}
