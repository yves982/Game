package fr.cesi.ylalanne.game.ui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutionException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import fr.cesi.ylalanne.contracts.ui.IChildView;
import fr.cesi.ylalanne.game.model.AreaModel;
import fr.cesi.ylalanne.utils.ui.ImageLoader;

/**
 * A view for an Area, inputless, but generate data by loading model's image
 * <p>It has the following bound properties:</p>
 * <ul>
 * 	<li>width</li>
 * </ul>
 */
public class AreaView implements IChildView, PropertyChangeListener {
	private Container parent;
	private JPanel areaPanel;
	private JLabel areaLabel;
	private AreaModel model;
	private PropertyChangeSupport modelChange;
	private boolean built;
	
	
	private void buildLabel() {
		areaLabel = new JLabel();
		areaLabel.setVisible(true);
	}

	private void buildPanel() {
		areaPanel = new JPanel();
		BoxLayout boxLayout = new BoxLayout(areaPanel, BoxLayout.Y_AXIS);
		areaPanel.setLayout(boxLayout);
		areaPanel.add(areaLabel);
	}

	private void buildComponents() {
		buildLabel();
		buildPanel();
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
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			};
		};
		
		try {
			worker.execute();
			worker.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private void loadSecondImage() {
		SwingWorker<ImageIcon, Void> worker = new SwingWorker<ImageIcon, Void>() {
			@Override
			protected ImageIcon doInBackground() throws Exception {
				Image backgroundImage = ImageLoader.LoadImage(model.getSecondImagePath());
				backgroundImage = backgroundImage.getScaledInstance(model.getWidth(), model.getHeight() - model.getUsedHeight(), Image.SCALE_FAST);
				ImageIcon backgroundIcon = new ImageIcon(backgroundImage);
				return backgroundIcon;
			}
			
			protected void done() {
				try {
					ImageIcon backgroundIcon = get();
					JLabel backgroundLabel = new JLabel();
					backgroundLabel.setIcon(backgroundIcon);
					backgroundLabel.setVisible(true);
					areaPanel.add(backgroundLabel);
					areaPanel.revalidate();
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			};
		};
		
		try {
			worker.execute();
			worker.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private void resizeImage() {
		SwingWorker<ImageIcon, Void> worker = new SwingWorker<ImageIcon, Void>() {
			@Override
			protected ImageIcon doInBackground() throws Exception {
				Image areaImage = ((ImageIcon)areaLabel.getIcon()).getImage();
				Image scaledImage = areaImage.getScaledInstance(model.getWidth(), model.getUsedHeight(), Image.SCALE_FAST);
				ImageIcon scaledAreaIcon = new ImageIcon(scaledImage);
				return scaledAreaIcon;
			}
			
			@Override
			protected void done() {
				try {
					ImageIcon scaledAreaIcon = get();
					areaLabel.setIcon(scaledAreaIcon);
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

	private void addsFiller() {
		int width = model.getWidth();
		int height = model.getHeight() - model.getUsedHeight();
		Component rigidArea = Box.createRigidArea(new Dimension(width, height));
		areaPanel.add(rigidArea);
		areaPanel.revalidate();
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
		return areaPanel;
	}

	@Override
	public void setParent(Container container, Dimension availableSize) {
		checkBuild();
		this.parent = container;
	}

	public void build() {
		try {
			SwingUtilities.invokeAndWait(this::buildComponents);
			built = true;
		} catch (InvocationTargetException | InterruptedException e) {
			e.printStackTrace();
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
				loadImage();
				break;
			case "secondImagePath":
				loadSecondImage();
				break;
			case "usedHeight":
				resizeImage();
				if(model.getSecondImagePath() == null) {
					addsFiller();
				}
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
