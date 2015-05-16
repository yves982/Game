package game.ui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import javax.swing.JComponent;
import javax.swing.JLayeredPane;
import javax.swing.SwingUtilities;

import main.ui.IChildView;
import main.ui.ILayeredParentView;

public class WorldView implements IChildView, ILayeredParentView {
	private List<IChildView> childViews;
	private JLayeredPane mainPanel;
	private FutureTask<Void> buildTask;
	private JComponent parent;
	
	public WorldView() {
		childViews = new ArrayList<IChildView>();
		buildTask = new FutureTask<Void>(() -> buildComponents(), null);
		SwingUtilities.invokeLater( buildTask );
	}
	
	private void buildComponents() {
		mainPanel = new JLayeredPane();
		mainPanel.setLayout(null);
		mainPanel.setDoubleBuffered(true);
		mainPanel.setBackground(Color.BLACK);
		mainPanel.setFocusable(true);
		mainPanel.requestFocus();
	}

	public void show() throws InterruptedException, ExecutionException {
		buildTask.get();
		mainPanel.setVisible(true);
	}
	
	@Override
	public void addChild(IChildView childView) {
		addChild(childView, 0);
	}
	
	@Override
	public void addChild(IChildView childView, int layer) {
		try {
			buildTask.get();
			childView.setParent(mainPanel);
			childViews.add(childView);
			mainPanel.add(childView.getComponent(), layer);
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
