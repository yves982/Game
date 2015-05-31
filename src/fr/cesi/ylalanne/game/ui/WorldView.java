package fr.cesi.ylalanne.game.ui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import fr.cesi.ylalanne.contracts.ui.IChildView;
import fr.cesi.ylalanne.contracts.ui.ILayeredParentView;
import fr.cesi.ylalanne.game.WorldManagerStrings;
import fr.cesi.ylalanne.lang.LocaleManager;

/**
 * The world view
 * <p>It has the following bound properties:</p>
 * <ul>
 * 	<li>width</li>
 *  <li>height</li>
 * </ul>.
 */
public class WorldView implements IChildView, ILayeredParentView {
	private List<IChildView> childViews;
	private JLayeredPane mainPanel;
	private JPanel backgroundPanel;
	private JPanel foregroundPanel;
	private Container parent;
	private PropertyChangeSupport propertyChange;
	private boolean built;
	
	private void buildComponents() {
		buildBackground();
		buildForeground();
		buildMain(); 
	}

	private void buildForeground() {
		foregroundPanel = new JPanel(null, true);
		foregroundPanel.setOpaque(false);
		foregroundPanel.setVisible(true);
	}

	private void buildBackground() {
		backgroundPanel = new JPanel(true);
		BoxLayout boxLayout = new BoxLayout(backgroundPanel, BoxLayout.PAGE_AXIS);
		backgroundPanel.setLayout(boxLayout);
		backgroundPanel.setBackground(Color.BLACK);
		backgroundPanel.setVisible(true);
	}

	private void buildMain() {
		mainPanel = new JLayeredPane();
		mainPanel.setLayout(null);
		mainPanel.setDoubleBuffered(true);
		mainPanel.add(foregroundPanel, 0);
		mainPanel.add(backgroundPanel, 1);
		mainPanel.setVisible(true);
		mainPanel.setFocusable(true);
	}

	private void checkBuild() {
		if(!built) {
			throw new RuntimeException("you should call build() before using this view.");
		}
	}

	/**
	 * Initialize the WorldView.
	 */
	public WorldView() {
		childViews = new ArrayList<IChildView>();
		built = false;
		propertyChange = new PropertyChangeSupport(this);
	}
	
	/**
	 * Shows this view.
	 */
	public void show() {
		checkBuild();
		try {
			SwingUtilities.invokeAndWait( () -> {
				mainPanel.setVisible(true);
			} );
		} catch (InvocationTargetException | InterruptedException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Tell the user the world has ended and there's nothing to do about it.
	 */
	public void showEnd() {
		JOptionPane.showMessageDialog(null, LocaleManager.getString(WorldManagerStrings.END_GAME.getKey()));
	}
	
	/**
	 * Adds the property change listener.
	 *
	 * @param listener the listener
	 * @see java.beans.PropertyChangeSupport#addPropertyChangeListener(java.beans.PropertyChangeListener)
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertyChange.addPropertyChangeListener(listener);
	}
	/**
	 * Removes the property change listener.
	 *
	 * @param listener the listener
	 * @see java.beans.PropertyChangeSupport#removePropertyChangeListener(java.beans.PropertyChangeListener)
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyChange.removePropertyChangeListener(listener);
	}
	/**
	 * Adds the property change listener.
	 *
	 * @param propertyName the property name
	 * @param listener the listener
	 * @see java.beans.PropertyChangeSupport#addPropertyChangeListener(java.lang.String, java.beans.PropertyChangeListener)
	 */
	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		propertyChange.addPropertyChangeListener(propertyName, listener);
	}
	/**
	 * Removes the property change listener.
	 *
	 * @param propertyName the property name
	 * @param listener the listener
	 * @see java.beans.PropertyChangeSupport#removePropertyChangeListener(java.lang.String, java.beans.PropertyChangeListener)
	 */
	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		propertyChange.removePropertyChangeListener(propertyName, listener);
	}

	/* (non-Javadoc)
	 * @see fr.cesi.ylalanne.contracts.ui.IParentView#addChild(fr.cesi.ylalanne.contracts.ui.IChildView)
	 */
	@Override
	public void addChild(IChildView childView) {
		addChild(childView, 0);
	}
	
	/* (non-Javadoc)
	 * @see fr.cesi.ylalanne.contracts.ui.ILayeredParentView#addChild(fr.cesi.ylalanne.contracts.ui.IChildView, int)
	 */
	@Override
	public void addChild(IChildView childView, int layer) {
		checkBuild();
		childView.setParent(mainPanel, mainPanel.getSize());
		childViews.add(childView);
		switch(layer) {
			case 0:
				foregroundPanel.add(childView.getComponent());
				foregroundPanel.revalidate();
				break;
			case 1:
				backgroundPanel.add(childView.getComponent());
				backgroundPanel.revalidate();
				break;
		}
		mainPanel.repaint();
	}

	/* (non-Javadoc)
	 * @see fr.cesi.ylalanne.contracts.ui.IChildView#getComponent()
	 */
	@Override
	public JComponent getComponent() {
		checkBuild();
		return mainPanel;
	}
	
	/* (non-Javadoc)
	 * @see fr.cesi.ylalanne.contracts.ui.IChildView#setParent(java.awt.Container, java.awt.Dimension)
	 */
	@Override
	public void setParent(Container container, Dimension availableSize) {
		checkBuild();
		this.parent = container;
		
		try {
			SwingUtilities.invokeAndWait(() -> {
				foregroundPanel.setSize(availableSize);
				backgroundPanel.setSize(availableSize);
				mainPanel.setSize(availableSize);
			});
			
			propertyChange.firePropertyChange("width", 0, availableSize.width);
			propertyChange.firePropertyChange("height", 0, availableSize.height);
		} catch (InvocationTargetException | InterruptedException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/* (non-Javadoc)
	 * @see fr.cesi.ylalanne.contracts.ui.IView#build()
	 */
	@Override
	public void build() {
		try {
			SwingUtilities.invokeAndWait(this::buildComponents);
			built = true;
		} catch (InvocationTargetException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}
