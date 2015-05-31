package fr.cesi.ylalanne.game.ui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.function.Consumer;

/**
 * class to handles ui move requests.
 *
 * @see MoveRequestEvent
 */
public class MovesListener extends KeyAdapter {
	
	private Consumer<MoveRequestEvent> pressedAction;
	private Consumer<MoveRequestEvent> releasedAction;
	
	/**
	 * defines an handler for a moveRequestEvent (on key pressed).
	 *
	 * @param moveConsumer the consumer to accept
	 */
	public void defineHandler(Consumer<MoveRequestEvent> moveConsumer) {
		defineHandler( moveConsumer, true);
	}
	
	/**
	 * defines an handler for a moveRequestEvent.
	 *
	 * @param moveConsumer the consumer to accept
	 * @param pressed true if the action is to be performed on key press, false if it's intended to manage key released
	 */
	public void defineHandler(Consumer<MoveRequestEvent> moveConsumer, boolean pressed) {
		if(pressed) {
			pressedAction = moveConsumer;
		} else {
			releasedAction = moveConsumer;
		}
	}
	
	
	/* (non-Javadoc)
	 * @see java.awt.event.KeyAdapter#keyPressed(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		pressedAction.accept(MoveRequestEvent.parseInt(keyCode));
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.KeyAdapter#keyReleased(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		releasedAction.accept(MoveRequestEvent.parseInt(keyCode));
	}
	
}
