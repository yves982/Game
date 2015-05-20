package fr.cesi.ylalanne.game.ui;

import java.awt.event.KeyEvent;
import java.util.Map;

public enum MoveRequestEvent {
	UP(KeyEvent.VK_UP),
	DOWN(KeyEvent.VK_DOWN),
	RIGHT(KeyEvent.VK_RIGHT),
	LEFT(KeyEvent.VK_LEFT);
	
	private int keyCode;
	private static Map<Integer, MoveRequestEvent> events;
	
	private MoveRequestEvent(int keyCode) {
		this.keyCode = keyCode;
	}
	
	public int getKeyCode() {
		return keyCode;
	}
	
	public static MoveRequestEvent parseInt(int keyCode) {
		return events.get(keyCode);
	}
}
