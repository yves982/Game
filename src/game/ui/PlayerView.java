package game.ui;

import game.model.PlayerModel;
import game.model.geom.MutableRectangle;

import java.awt.Image;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import main.ui.ILayeredChildView;
import utils.ui.ImageLoader;

/**
 * a view for the Player
 * <p>
 * It has the following bound properties:
 * <ul>
 * 	<li>width</li>
 * <li>height</li>
 * </ul>
 * </p>
 */
public class PlayerView implements ILayeredChildView, PropertyChangeListener {
	private FutureTask<Void> buildTask;
	private PlayerModel model;
	private JComponent parent;
	private JLabel playerLabel;
	private PropertyChangeSupport modelChange;
	
	/**
	 * Default constructor
	 */
	public PlayerView(PlayerModel model) {
		this.model = model;
		modelChange = new PropertyChangeSupport(model);
		model.addPropertyChangeListener(this);
		buildTask = new FutureTask<Void>( () -> buildComponents(), null);
		SwingUtilities.invokeLater(buildTask);
	}

	private void loadImage() {
		Image playerImage = ImageLoader.LoadImage(model.getImagePath());
		ImageIcon playerIcon = new ImageIcon(playerImage);
		playerLabel.setIcon(playerIcon);
		modelChange.firePropertyChange("width", model.getArea().getWidth(), playerImage.getWidth(null));
		modelChange.firePropertyChange("height", model.getArea().getHeight(), playerImage.getHeight(null));
	}

	private void buildPlayer() {
		playerLabel = new JLabel();
		playerLabel.setVisible(true);
	}

	private void buildComponents() {
		buildPlayer();
	}

	@Override
	public JComponent getComponent() {
		try {
			buildTask.get();
			return playerLabel;
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public int getLayer() {
		return 1;
	}
	
	@Override
	public void setParent(JComponent parent) {
		this.parent = parent;
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
		Image collisionImage = ImageLoader.LoadImage("player/collision.png");
		ImageIcon collisionIcon = new ImageIcon(collisionImage);
		playerLabel.setIcon(collisionIcon);
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
			case "y":
				updateY();
			case "collide":
				collide();
				
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
