package fr.cesi.ylalanne.game.ui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutionException;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import fr.cesi.ylalanne.contracts.ui.IChildView;
import fr.cesi.ylalanne.game.model.ObstacleModel;
import fr.cesi.ylalanne.utils.ui.ImageLoader;

/**
 * An obstacle view, inputless, but generate data by loading model's image
 * <p>It has the following bound properties:</p>
 * <ul>
 * 	<li>width</li>
 *  <li>height</li>
 * </ul>.
 */
public class ObstacleView implements IChildView, PropertyChangeListener {
	private Container parent;
	private ObstacleModel model;
	private JLabel obstacleLabel;
	private PropertyChangeSupport modelChange;
	private boolean built;

	private void buildComponents() {
		obstacleLabel = new JLabel();
		obstacleLabel.setVisible(true);
	}

	private void checkBuild() {
		if(!built) {
			throw new RuntimeException("you should call build() before using this view.");
		}
	}

	/**
	 * Load the image and raise propertyChange for width and height properties:
	 * <p>
	 * the ObstacleView has no width and height properties, but that's not a problem for a one time event (per instance)
	 * </p>
	 */
	private void loadImage() {
		SwingWorker<ImageIcon, Void> worker = new SwingWorker<ImageIcon, Void>() {
			@Override
			protected ImageIcon doInBackground() throws Exception {
				Image obstacleImage = ImageLoader.LoadImage(model.getImagePath());
				ImageIcon obstacleIcon = new ImageIcon(obstacleImage);
				return obstacleIcon;
			}
			
			@Override
			protected void done() {
				try {
					ImageIcon obstacleIcon = get();
					obstacleLabel.setIcon(obstacleIcon);
					obstacleLabel.setSize(obstacleIcon.getIconWidth(), obstacleIcon.getIconHeight());
					modelChange.firePropertyChange("width", model.getWidth(), obstacleIcon.getIconWidth());
					modelChange.firePropertyChange("height", model.getHeight(), obstacleIcon.getIconHeight());
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}
		};
		
		try {
			worker.execute();
			worker.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private void updateX() {
		int x = model.getArea().getX();
		obstacleLabel.setBounds(x, obstacleLabel.getY(), obstacleLabel.getWidth(), obstacleLabel.getHeight());
	}

	private void updateY() {
		int y = model.getArea().getY();
		obstacleLabel.setBounds(obstacleLabel.getX(), y, obstacleLabel.getWidth(), obstacleLabel.getHeight());
	}

	private void handleDrop() {
		parent.remove(obstacleLabel);
		parent.repaint();
	}

	/**
	 * Initializes an ObstacleView.
	 *
	 * @param model the obstacle model
	 */
	public ObstacleView(ObstacleModel model) {
		this.model = model;
		built = false;
		modelChange = new PropertyChangeSupport(model);
		model.addPropertyChangeListener(this);
	}

	/* (non-Javadoc)
	 * @see fr.cesi.ylalanne.contracts.ui.IChildView#getComponent()
	 */
	@Override
	public JComponent getComponent() {
		checkBuild();
		return obstacleLabel;
	}

	/* (non-Javadoc)
	 * @see fr.cesi.ylalanne.contracts.ui.IChildView#setParent(java.awt.Container, java.awt.Dimension)
	 */
	@Override
	public void setParent(Container container, Dimension availableSize) {
		checkBuild();
		this.parent = container;
	}

	/* (non-Javadoc)
	 * @see fr.cesi.ylalanne.contracts.ui.IView#build()
	 */
	public void build() {
		try {
			SwingUtilities.invokeAndWait(this::buildComponents);
			built=true;
		} catch (InvocationTargetException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propertyName = evt.getPropertyName();
		switch(propertyName) {
			case "imagePath":
				loadImage();
				break;
			case "x":
				SwingUtilities.invokeLater(this::updateX);
				break;
			case "y":
				SwingUtilities.invokeLater(this::updateY);
				break;
			case "dropped":
				SwingUtilities.invokeLater(this::handleDrop);
				break;
		}
		
	}

	/**
	 * Adds the property change listener.
	 *
	 * @param listener the listener
	 * @see java.beans.PropertyChangeSupport#addPropertyChangeListener(java.beans.PropertyChangeListener)
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		modelChange.addPropertyChangeListener(listener);
	}
	/**
	 * Adds the property change listener.
	 *
	 * @param propertyName the property name
	 * @param listener the listener
	 * @see java.beans.PropertyChangeSupport#addPropertyChangeListener(java.lang.String, java.beans.PropertyChangeListener)
	 */
	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		modelChange.addPropertyChangeListener(propertyName, listener);
	}
	/**
	 * Removes the property change listener.
	 *
	 * @param listener the listener
	 * @see java.beans.PropertyChangeSupport#removePropertyChangeListener(java.beans.PropertyChangeListener)
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		modelChange.removePropertyChangeListener(listener);
	}
	/**
	 * Removes the property change listener.
	 *
	 * @param propertyName the property name
	 * @param listener the listener
	 * @see java.beans.PropertyChangeSupport#removePropertyChangeListener(java.lang.String, java.beans.PropertyChangeListener)
	 */
	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		modelChange.removePropertyChangeListener(propertyName, listener);
	}
}
