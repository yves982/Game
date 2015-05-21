package fr.cesi.ylalanne.game;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import fr.cesi.ylalanne.contracts.IChildController;
import fr.cesi.ylalanne.contracts.ui.IChildView;
import fr.cesi.ylalanne.game.model.AreaModel;
import fr.cesi.ylalanne.game.ui.AreaView;

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
		view.build();
		model.setImagePath(kind.getImagePath());
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

	/**
	 * @return the area's width
	 * @see fr.cesi.ylalanne.game.model.AreaModel#getWidth()
	 */
	public int getWidth() {
		return model.getWidth();
	}

	/**
	 * @return the area's height
	 * @see fr.cesi.ylalanne.game.model.AreaModel#getHeight()
	 */
	public int getHeight() {
		return model.getHeight();
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
