package game.ui;

import game.model.AreaModel;

import java.awt.Image;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import main.ui.IChildView;
import utils.ui.ImageLoader;

/**
 * A view for an Area, inputless, but generate data by loading model's image
 * <p>
 * It has the following bound properties:
 * <ul>
 * 	<li>width</li>
 * 	<li>height</li>
 * </ul>
 * </p>
 */
public class AreaView implements IChildView, PropertyChangeListener {
	private JComponent parent;
	private JLabel areaLabel;
	private FutureTask<Void> buildTask;
	private AreaModel model;
	private PropertyChangeSupport modelChange;
	private boolean built;
	
	
	private void buildComponents() {
		areaLabel = new JLabel();
		areaLabel.setVisible(true);
	}

	private void checkBuild() {
		if(!built) {
			throw new RuntimeException("you should call build() before using this view.");
		}
	}

	private void updateX() {
		int x = model.getX();
		areaLabel.setBounds(x, areaLabel.getY(), areaLabel.getWidth(), areaLabel.getHeight());
	}

	private void updateY() {
		int y = model.getY();
		areaLabel.setBounds(areaLabel.getX(), y, areaLabel.getWidth(), areaLabel.getHeight());
	}

	private void loadImage() {
		SwingWorker<ImageIcon, Void> worker = new SwingWorker<ImageIcon, Void>() {
			@Override
			protected ImageIcon doInBackground() throws Exception {
				Image areaImage = ImageLoader.LoadImage(model.getImagePath());
				ImageIcon areaIcon = new ImageIcon(areaImage);
				return areaIcon;
			}
			
			protected void done() {
				try {
					ImageIcon areaIcon = get();
					areaLabel.setIcon(areaIcon);
					areaLabel.setVisible(true);
					modelChange.firePropertyChange("width", model.getWidth(), areaIcon.getIconWidth());
					modelChange.firePropertyChange("height", model.getHeight(), areaIcon.getIconHeight());
				} catch (InterruptedException | ExecutionException e) {
					throw new RuntimeException(e);
				}
			};
		};
		
		worker.execute();
	}

	/**
	 * Initialize an AreaView
	 * @param model the Area model
	 */
	public AreaView(AreaModel model) {
		this.model = model;
		built = false;
		modelChange = new PropertyChangeSupport(model);
		model.addPropertyChangeListener(this);
	}

	@Override
	public JComponent getComponent() {
		checkBuild();
		return areaLabel;
	}

	@Override
	public void setParent(JComponent parent) {
		checkBuild();
		this.parent = parent;
	}

	public void build() {
		try {
			SwingUtilities.invokeAndWait(this::buildComponents);
			built = true;
		} catch (InvocationTargetException | InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		checkBuild();
		String propertyName = evt.getPropertyName();
		
		switch(propertyName) {
			case "x":
				SwingUtilities.invokeLater(this::updateX);
				break;
			case "y":
				SwingUtilities.invokeLater(this::updateY);
				break;
			case "imagePath":
				SwingUtilities.invokeLater(this::loadImage);
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
	 * @param listener
	 * @see java.beans.PropertyChangeSupport#removePropertyChangeListener(java.beans.PropertyChangeListener)
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		modelChange.removePropertyChangeListener(listener);
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
	 * @param propertyName
	 * @param listener
	 * @see java.beans.PropertyChangeSupport#removePropertyChangeListener(java.lang.String, java.beans.PropertyChangeListener)
	 */
	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		modelChange.removePropertyChangeListener(propertyName, listener);
	}

}
