package game.ui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import main.ui.IChildView;

public class WorldView implements IChildView {
	private List<IChildView> childViews;
	private JPanel mainPanel;
	
	public WorldView() {
		childViews = new ArrayList<IChildView>();
		SwingUtilities.invokeLater(() -> buildComponents() );
	}
	
	private void buildComponents() {
		mainPanel = new JPanel(null, true);
		mainPanel.setFocusable(true);
		mainPanel.requestFocus();
	}

	public void addChild(IChildView child) {
		childViews.add(child);
		mainPanel.add(child.getComponent());
		mainPanel.repaint();
	}
	
	@Override
	public JComponent getComponent() {
		return mainPanel;
	}

}
