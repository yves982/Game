package launcher;

import java.util.Locale;

import controller.MainController;

public class Game {

	public static void main(String[] args) {
		MainController controller = new MainController(Locale.FRENCH);
		controller.start();
	}

}
