package game.ui;

import java.awt.Color;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import main.ui.IChildView;
import main.ui.ILayeredParentView;

/**
 * A world view
 * <p>
 * It has the following bound properties:
 * <ul>
 * 	<li>width</li>
 *  <li>height</li>
 * </ul>
 * </p>
 */
public class WorldView implements IChildView, ILayeredParentView {
	private List<IChildView> childViews;
	private JLayeredPane mainPanel;
	private JPanel backgroundPanel;
	private JPanel foregroundPanel;
	private FutureTask<Void> buildTask;
	private JComponent parent;
	private PropertyChangeSupport propertyChange;
	
	private void buildComponents() {
		buildBackground();
		buildForeground();
		buildMain(); 
	}

	private void buildForeground() {
		foregroundPanel = new JPanel(null, true);
	}

	private void buildBackground() {
		backgroundPanel = new JPanel(true);
		BoxLayout boxLayout = new BoxLayout(backgroundPanel, BoxLayout.PAGE_AXIS);
		backgroundPanel.setLayout(boxLayout);
	}

	private void buildMain() {
		mainPanel = new JLayeredPane();
		mainPanel.setLayout(null);
		mainPanel.setDoubleBuffered(true);
		mainPanel.setBackground(Color.BLACK);
		mainPanel.add(backgroundPanel, 0);
		mainPanel.add(foregroundPanel, 1);
		mainPanel.setVisible(true);
		mainPanel.setFocusable(true);
		mainPanel.requestFocus();
	}

	public WorldView() {
		childViews = new ArrayList<IChildView>();
		buildTask = new FutureTask<Void>(() -> buildComponents(), null);
		propertyChange = new PropertyChangeSupport(this);
		SwingUtilities.invokeLater( buildTask );
	}

	public void show() throws InterruptedException, ExecutionException {
		buildTask.get();
		mainPanel.setVisible(true);
	}
	
	/**
	 * @param listener
	 * @see java.beans.PropertyChangeSupport#addPropertyChangeListener(java.beans.PropertyChangeListener)
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertyChange.addPropertyChangeListener(listener);
	}
	/**
	 * @param listener
	 * @see java.beans.PropertyChangeSupport#removePropertyChangeListener(java.beans.PropertyChangeListener)
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyChange.removePropertyChangeListener(listener);
	}
	/**
	 * @param propertyName
	 * @param listener
	 * @see java.beans.PropertyChangeSupport#addPropertyChangeListener(java.lang.String, java.beans.PropertyChangeListener)
	 */
	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		propertyChange.addPropertyChangeListener(propertyName, listener);
	}
	/**
	 * @param propertyName
	 * @param listener
	 * @see java.beans.PropertyChangeSupport#removePropertyChangeListener(java.lang.String, java.beans.PropertyChangeListener)
	 */
	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		propertyChange.removePropertyChangeListener(propertyName, listener);
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
			switch(layer) {
				case 0:
					backgroundPanel.add(childView.getComponent());
					break;
				case 1:
					foregroundPanel.add(childView.getComponent());
					break;
			}
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
	
	/**
	 * <p>
	 * the WorldView has no width and height properties, but that's not a problem for a one time event (per instance)
	 * </p>
	 */
	@Override
	public void setParent(JComponent parent) {
		this.parent = parent;
		foregroundPanel.setSize(this.parent.getSize());
		backgroundPanel.setSize(this.parent.getSize());
		propertyChange.firePropertyChange("width", 0, parent.getWidth());
		propertyChange.firePropertyChange("height", 0, parent.getHeight());
	}

}
