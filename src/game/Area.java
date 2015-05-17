package game;

import game.model.AreaModel;
import game.ui.AreaView;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.nio.file.Paths;

import main.IChildController;
import main.ResourcesManager;
import main.ui.IChildView;

/**
 * An Area which does not handle collision nor provide any input
 * <p>
 * It's simply part of the background with no interaction whatsoever
 * </p>
 */
public class Area implements IChildController, PropertyChangeListener {
	private AreaKind kind;
	private AreaModel model;
	private AreaView view;
	
	private void updateWidth(int width) {
		model.setWidth(width);
	}

	private void updateHeight(int height) {
		model.setHeight(height);
	}

	/**
	 * Initialize an Area
	 * @param kind the kind of this area
	 */
	public Area(AreaKind kind) {
		this.kind = kind;
		model = new AreaModel();
		view = new AreaView(model);
		model.setImagePath(Paths.get(ResourcesManager.RESOURCES_BASE, kind.getImagePath()).toString());
		view.addPropertyChangeListener(this);
	}
	
	/**
	 * @return the area's kind
	 */
	public AreaKind getKind() {
		return kind;
	}
	
	/**
	 * Places this area in the world
	 * <p>
	 * 	<b>Note: the area must be added to the world before setting its position</b>
	 * </p>
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	public void startPosition(int x, int y) {
		model.setX(x);
		model.setY(y);
	}

	@Override
	public IChildView getChild() {
		return view;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propertyName = evt.getPropertyName();
		Object newValue = evt.getNewValue();
		
		switch(propertyName) {
			case "width":
				updateWidth((int)newValue);
				break;
			case "height":
				updateHeight((int)newValue);
				break;
		}
	}
}
