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
 * </p>.
 */
public class Area implements IChildController, PropertyChangeListener {
	private AreaKind kind;
	private AreaModel model;
	private AreaView view;
	
	private void updateWidth(int width) {
		model.setWidth(width);
	}

	/**
	 * Initialize an Area.
	 *
	 * @param kind the kind of this area
	 */
	public Area(AreaKind kind) {
		this.kind = kind;
		model = new AreaModel();
		view = new AreaView(model);
		view.build();
		model.setImagePath(kind.getImagePath());
		if(kind.getSecondImagePath() !=  null) {
			model.setSecondImagePath(kind.getSecondImagePath());
		}
		view.addPropertyChangeListener(this);
	}
	
	/**
	 * Sets the bounds of this area.
	 *
	 * @param width the area's width
	 * @param height the area's filled height
	 * @param usedHeight the area's height within which some rules will apply
	 */
	public void setBounds(int width, int height, int usedHeight) {
		model.setWidth(width);
		model.setHeight(height);
		model.setUsedHeight(usedHeight);
	}
	
	/**
	 * Gets the kind.
	 *
	 * @return the area's kind
	 */
	public AreaKind getKind() {
		return kind;
	}

	/**
	 * Gives this area her position in the {@link World}.
	 *
	 * @param y the y coordinate of this area (depends on its row)
	 */
	public void startPosition(int y) {
		model.setY(y);
	}
	
	/**
	 * Gets the width.
	 *
	 * @return the area's width
	 * @see fr.cesi.ylalanne.game.model.AreaModel#getWidth()
	 */
	public int getWidth() {
		return model.getWidth();
	}

	/**
	 * Gets the height.
	 *
	 * @return the area's height
	 * @see fr.cesi.ylalanne.game.model.AreaModel#getHeight()
	 */
	public int getHeight() {
		return model.getHeight();
	}

	/**
	 * Gets the used height.
	 *
	 * @return the area's height
	 * @see fr.cesi.ylalanne.game.model.AreaModel#getUsedHeight()
	 */
	public int getUsedHeight() {
		return model.getUsedHeight();
	}
	
	/* (non-Javadoc)
	 * @see fr.cesi.ylalanne.contracts.IChildController#getChild()
	 */
	@Override
	public IChildView getChild() {
		return view;
	}

	/* (non-Javadoc)
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propertyName = evt.getPropertyName();
		Object newValue = evt.getNewValue();
		
		switch(propertyName) {
			case "width":
				updateWidth((int)newValue);
				break;
		}
	}
}
