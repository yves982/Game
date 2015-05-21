package fr.cesi.ylalanne.game.ui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;

import fr.cesi.ylalanne.contracts.ui.IChildView;
import fr.cesi.ylalanne.game.model.ObstacleModel;
import fr.cesi.ylalanne.utils.ui.ImageLoader;

/**
 * An obstacle view, inputless, but generate data by loading model's image
 * <p>It has the following bound properties:</p>
 * <ul>
 * 	<li>width</li>
 *  <li>height</li>
 * </ul>
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
		Image image = ImageLoader.LoadImage(model.getImagePath());
		ImageIcon imageIcon = new ImageIcon(image);
		obstacleLabel.setIcon(imageIcon);
		modelChange.firePropertyChange("width", model.getWidth(), image.getWidth(null));
		modelChange.firePropertyChange("height", model.getHeight(), image.getHeight(null));
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
	 * Initialize the Obstacle view
	 * @param model the obstacle model
	 */
	public ObstacleView(ObstacleModel model) {
		this.model = model;
		built = false;
		modelChange = new PropertyChangeSupport(model);
		model.addPropertyChangeListener(this);
	}

	@Override
	public JComponent getComponent() {
		checkBuild();
		return obstacleLabel;
	}

	@Override
	public void setParent(Container container, Dimension availableSize) {
		checkBuild();
		this.parent = container;
	}

	public void build() {
		buildComponents();
		built=true;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propertyName = evt.getPropertyName();
		switch(propertyName) {
			case "imagePath":
				loadImage();
				break;
			case "x":
				updateX();
				break;
			case "y":
				updateY();
				break;
			case "dropped":
				handleDrop();
				break;
		}
		
	}

	/**
	 * @param listener
	 * @see java.beans.PropertyChangeSupport#addPropertyChangeListener(java.beans.PropertyChangeListener)
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		modelChange.addPropertyChangeListener(listener);
	}
	/**
	 * @param propertyName
	 * @param listener
	 * @see java.beans.PropertyChangeSupport#addPropertyChangeListener(java.lang.String, java.beans.PropertyChangeListener)
	 */
	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		modelChange.addPropertyChangeListener(propertyName, listener);
	}
	/**
	 * @param listener
	 * @see java.beans.PropertyChangeSupport#removePropertyChangeListener(java.beans.PropertyChangeListener)
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		modelChange.removePropertyChangeListener(listener);
	}
	/**
	 * @param propertyName
	 * @param listener
	 * @see java.beans.PropertyChangeSupport#removePropertyChangeListener(java.lang.String, java.beans.PropertyChangeListener)
	 */
	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		modelChange.removePropertyChangeListener(propertyName, listener);
	}
}
