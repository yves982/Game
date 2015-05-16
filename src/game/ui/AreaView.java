package game.ui;

import game.model.AreaModel;

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

import main.ui.IChildView;
import utils.ui.ImageLoader;

/**
 * A view for an Area, inputless, but generate data by loading model's image
 */
public class AreaView implements IChildView, PropertyChangeListener {
	private JComponent parent;
	private JLabel areaLabel;
	private FutureTask<Void> buildTask;
	private AreaModel model;
	private PropertyChangeSupport modelChange;
	
	
	private void buildComponents() {
		areaLabel = new JLabel();
		areaLabel.setVisible(true);
	}

	/**
	 * Initialize an AreaView
	 */
	public AreaView(AreaModel model) {
		this.model = model;
		modelChange = new PropertyChangeSupport(model);
		model.addPropertyChangeListener(this);
		buildTask = new FutureTask<Void>( () -> buildComponents(), null );
		SwingUtilities.invokeLater(buildTask);
	}

	@Override
	public JComponent getComponent() {
		try {
			buildTask.get();
			return areaLabel;
		} catch(InterruptedException | ExecutionException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void setParent(JComponent parent) {
		this.parent = parent;
	}

	private void updateX() {
		int x = model.getX();
		areaLabel.setBounds(x, areaLabel.getY(), areaLabel.getWidth(), areaLabel.getHeight());
		parent.repaint();
	}

	private void updateY() {
		int y = model.getY();
		areaLabel.setBounds(areaLabel.getX(), y, areaLabel.getWidth(), areaLabel.getHeight());
		parent.repaint();
	}

	private void loadImage() {
		Image areaImage = ImageLoader.LoadImage(model.getImagePath());
		ImageIcon areaIcon = new ImageIcon(areaImage);
		areaLabel.setIcon(areaIcon);
		areaLabel.setVisible(true);
		modelChange.firePropertyChange("width", model.getWidth(), areaImage.getWidth(null));
		modelChange.firePropertyChange("height", model.getHeight(), areaImage.getHeight(null));
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propertyName = evt.getPropertyName();
		
		switch(propertyName) {
			case "x":
				updateX();
				break;
			case "y":
				updateY();
				break;
			case "imagePath":
				loadImage();
				break;
		}
	}

}
