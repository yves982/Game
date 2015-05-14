package game.ui;

import game.model.PlayerModel;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import javax.swing.JComponent;

import main.ui.IChildView;

public class PlayerView implements IChildView {
	private FutureTask<Void> buildTask;
	private PlayerModel model;
	private JComponent parent;
	
	/**
	 * Default constructor
	 */
	public PlayerView(PlayerModel model) {
		this.model = model;
		// TODO Complete this constructor
	}

	@Override
	public JComponent getComponent() {
		try {
			buildTask.get();
			// TODO Auto-generated method stub
			return null;
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void setParent(JComponent parent) {
		this.parent = parent;
	}

}
