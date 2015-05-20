package fr.cesi.ylalanne.utils.ui;

import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Window;

public class ComponentLocation {
	public static Point getCenteredLocation(Window topLevelComponent) {
		Point centeredLocation = GraphicsEnvironment
				.getLocalGraphicsEnvironment()
				.getCenterPoint();
		centeredLocation.translate(-topLevelComponent.getWidth()/2, -topLevelComponent.getHeight()/2);
		return centeredLocation;
	}
}
