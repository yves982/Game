package fr.cesi.ylalanne.game.ui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * class to handles ui move requests
 */
public class MovesListener extends KeyAdapter {
	
	private Map<Integer, Runnable> pressedActions;
	private Map<Integer, Runnable> releasedActions;
	
	/**
	 * Initialize a MovesListener
	 */
	public MovesListener() {
		pressedActions = new HashMap<Integer, Runnable>();
		releasedActions = new HashMap<Integer, Runnable>();
	}
	
	/**
	 * defines an handler for a moveRequestEvent (on key pressed)
	 * @param moveRequestEvent the event to handle
	 * @param action the action to perform
	 */
	public void defineHandler(MoveRequestEvent moveRequestEvent, Runnable action) {
		defineHandler(moveRequestEvent, action, true);
	}
	
	/**
	 * defines an handler for a moveRequestEvent
	 * @param moveRequestEvent the event to handle
	 * @param action the action to perform
	 * @param pressed true if the action is to be performed on key press, false if it's intended to manage key released
	 */
	public void defineHandler(MoveRequestEvent moveRequestEvent, Runnable action, boolean pressed) {
		int keyCode = moveRequestEvent.getKeyCode();
		if(pressed && !pressedActions.containsKey(keyCode)) {
			pressedActions.put(keyCode, action);
		} else if (!releasedActions.containsKey(keyCode)) {
			releasedActions.put(keyCode, action);
		}
	}
	
	
	@Override
	public void keyPressed(KeyEvent e) {
		Integer keyCode = e.getKeyCode();
		if(pressedActions.containsKey(keyCode)) {
			pressedActions.get(keyCode).run();
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		Integer keyCode = e.getKeyCode();
		if(releasedActions.containsKey(keyCode)) {
			releasedActions.get(keyCode).run();
		}
	}
	
}
