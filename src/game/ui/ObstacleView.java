package game.ui;

import javax.swing.JComponent;

import main.ui.IChildView;

public class ObstacleView implements IChildView {
	private JComponent parent;

	@Override
	public JComponent getComponent() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see main.ui.IChildView#getComponent()
	 */
	@Override
	public void setParent(JComponent parent) {
		this.parent = parent;
	}
}
