package game.ui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import main.ui.IChildView;
import main.ui.IParentView;

public class WorldView implements IChildView, IParentView {
	private List<IChildView> childViews;
	private JPanel mainPanel;
	private FutureTask<Void> buildTask;
	private JComponent parent;
	
	public WorldView() {
		childViews = new ArrayList<IChildView>();
		buildTask = new FutureTask<Void>(() -> buildComponents(), null);
		SwingUtilities.invokeLater( buildTask );
	}
	
	private void buildComponents() {
		mainPanel = new JPanel(null, true);
		mainPanel.setBackground(Color.BLACK);
		mainPanel.setFocusable(true);
		mainPanel.requestFocus();
	}

	public void show() throws InterruptedException, ExecutionException {
		buildTask.get();
		mainPanel.setVisible(true);
	}
	
	@Override
	public void addChild(IChildView child) {
		try {
			buildTask.get();
			child.setParent(mainPanel);
			childViews.add(child);
			mainPanel.add(child.getComponent());
			mainPanel.repaint();
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e);
		}
		
	}

	@Override
	public JComponent getComponent() {
		try {
			buildTask.get();
			return mainPanel;
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	@Override
	public void setParent(JComponent parent) {
		this.parent = parent;
	}

}
