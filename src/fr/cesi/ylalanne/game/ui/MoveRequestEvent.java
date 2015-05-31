package fr.cesi.ylalanne.game.ui;

import java.awt.event.KeyEvent;

/**
 * Stores MoveRequest properties (Directions and associated KeyEvent).
 */
public enum MoveRequestEvent {
	UP(KeyEvent.VK_UP),
	DOWN(KeyEvent.VK_DOWN),
	RIGHT(KeyEvent.VK_RIGHT),
	LEFT(KeyEvent.VK_LEFT);
	
	private int keyCode;
	
	private MoveRequestEvent(int keyCode) {
		this.keyCode = keyCode;
	}
	
	/**
	 * Gets the key code.
	 *
	 * @return the key code
	 */
	public int getKeyCode() {
		return keyCode;
	}
	
	/**
	 * Gets the corresponding member from a given KeyEvent.
	 *
	 * @param keyCode the key code
	 * @return the matching MoveRequestEvent
	 */
	public static MoveRequestEvent parseInt(int keyCode) {
		MoveRequestEvent [] events = values();
		MoveRequestEvent seekedEvent = null;
		
		for(int i=0; i<events.length; i++) {
			if(events[i].getKeyCode() == keyCode) {
				seekedEvent = events[i];
				break;
			}
		}
		
		return seekedEvent;
	}
}
