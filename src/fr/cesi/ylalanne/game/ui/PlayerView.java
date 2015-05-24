package fr.cesi.ylalanne.game.ui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.KeyListener;
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

import fr.cesi.ylalanne.contracts.ui.ILayeredChildView;
import fr.cesi.ylalanne.game.model.PlayerModel;
import fr.cesi.ylalanne.game.model.geom.MutableRectangle;
import fr.cesi.ylalanne.utils.ui.ImageLoader;

/**
 * a view for the Player
 * <p>It has the following bound properties:</p>
 * <ul>
 * 	<li>width</li>
 * <li>height</li>
 * </ul>
 */
public class PlayerView implements ILayeredChildView, PropertyChangeListener {
	private PlayerModel model;
	private Container parent;
	private JLabel playerLabel;
	private PropertyChangeSupport modelChange;
	private boolean built;
	
	private void loadImage() {
		SwingWorker<ImageIcon, Void> worker = new SwingWorker<ImageIcon, Void>() {
			@Override
			protected ImageIcon doInBackground() throws Exception {
				Image playerImage = ImageLoader.LoadImage(model.getImagePath());
				ImageIcon playerIcon = new ImageIcon(playerImage);
				return playerIcon;
			}
			
			protected void done() {
				try {
					ImageIcon playerIcon = get();
					playerLabel.setIcon(playerIcon);
					playerLabel.setSize(playerIcon.getIconWidth(), playerIcon.getIconHeight());
					modelChange.firePropertyChange("width", model.getArea().getWidth(), playerIcon.getIconWidth());
					modelChange.firePropertyChange("height", model.getArea().getHeight(), playerIcon.getIconHeight());
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

	private void buildPlayer() {
		playerLabel = new JLabel();
		playerLabel.setVisible(true);
	}

	private void checkBuild() {
		if(!built) {
			throw new RuntimeException("you should call build() before using this view.");
		}
	}

	private void buildComponents() {
		buildPlayer();
	}

	private void updateX() {
		MutableRectangle playerArea = model.getArea();
		playerLabel.setBounds(playerArea.getX(), playerLabel.getY(), playerLabel.getWidth(), playerLabel.getHeight());
		
	}

	private void updateY() {
		MutableRectangle playerArea = model.getArea();
		playerLabel.setBounds(playerLabel.getX(), playerArea.getY(), playerLabel.getWidth(), playerLabel.getHeight());
	}

	private void collide() {
		SwingWorker<ImageIcon, Void> worker = new SwingWorker<ImageIcon, Void>() {
			@Override
			protected ImageIcon doInBackground() throws Exception {
				Image collisionImage = ImageLoader.LoadImage("/player/collision.png");
				ImageIcon collisionIcon = new ImageIcon(collisionImage);
				return collisionIcon;
			}
			@Override
			protected void done() {
				try {
					ImageIcon collisionIcon = get();
					playerLabel.setIcon(collisionIcon);
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}
		};
		
		try {
			worker.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * Initialize the Player view
	 * @param model the player model
	 */
	public PlayerView(PlayerModel model) {
		this.model = model;
		built = false;
		modelChange = new PropertyChangeSupport(model);
		model.addPropertyChangeListener(this);
	}
	
	@Override
	public JComponent getComponent() {
		checkBuild();
		return playerLabel;
	}
	
	@Override
	public int getLayer() {
		checkBuild();
		return 0;
	}

	@Override
	public void setParent(Container container, Dimension availableSize) {
		checkBuild();
		this.parent = container;
	}

	@Override
	public int getWidth() {
		checkBuild();
		return playerLabel.getWidth();
	}

	@Override
	public int getHeight() {
		checkBuild();
		return playerLabel.getHeight();
	}

	public void build() {
		try {
			SwingUtilities.invokeAndWait(this::buildComponents);
			built = true;
		} catch (InvocationTargetException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param l the {@code KeyListener} to add
	 * @see java.awt.Component#addKeyListener(java.awt.event.KeyListener)
	 */
	public void addKeyListener(KeyListener l) {
		parent.addKeyListener(l);
	}

	/**
	 * @param l the {@code KeyListener} to remove
	 * @see java.awt.Component#removeKeyListener(java.awt.event.KeyListener)
	 */
	public void removeKeyListener(KeyListener l) {
		parent.removeKeyListener(l);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		checkBuild();
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
			case "collide":
				SwingUtilities.invokeLater(this::collide);
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
