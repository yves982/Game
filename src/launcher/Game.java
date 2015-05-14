package launcher;

import game.World;

import java.util.Locale;
import java.util.concurrent.ExecutionException;

import menu.MainMenuController;

public class Game {

	public static void main(String[] args) {
		World world = new World();
		MainMenuController controller = new MainMenuController(Locale.FRENCH, world);
		try {
			controller.start();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}

}
