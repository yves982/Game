package fr.cesi.ylalanne.utils.ui;

import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Window;

/**
 * Utility class to center a graphical component within its parent.
 */
public class ComponentLocation {
	
	/**
	 * Gets the centered location.
	 *
	 * @param topLevelComponent the top level component
	 * @return the centered location
	 */
	public static Point getCenteredLocation(Window topLevelComponent) {
		Point centeredLocation = GraphicsEnvironment
				.getLocalGraphicsEnvironment()
				.getCenterPoint();
		centeredLocation.translate(-topLevelComponent.getWidth()/2, -topLevelComponent.getHeight()/2);
		return centeredLocation;
	}
}
