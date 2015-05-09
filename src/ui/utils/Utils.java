package ui.utils;

import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Window;

public class Utils {
	public static Point getCenteredLocation(Window topLevelComponent) {
		Point centeredLocation = GraphicsEnvironment
				.getLocalGraphicsEnvironment()
				.getCenterPoint();
		centeredLocation.translate(-topLevelComponent.getWidth()/2, -topLevelComponent.getHeight()/2);
		return centeredLocation;
	}
}
