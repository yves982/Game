package game;

import game.model.PlayerModel;
import game.model.geom.MutableRectangle;
import game.ui.PlayerInfosView;
import game.ui.PlayerView;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import main.ILayeredChildrenController;
import main.ResourcesManager;
import main.ui.ILayeredChildView;

public class Player implements ILayeredChildrenController, PropertyChangeListener {
	private PlayerModel model;
	private PlayerView mainView;
	private PlayerInfosView infosView;
	private List<ILayeredChildView> childrenView;
	
	private void fillChildrenView() {
		childrenView.add(infosView);
		childrenView.add(mainView);
	}

	private void builModel() {
		model = new PlayerModel();
		model.setImagePath(Paths.get(ResourcesManager.RESOURCES_BASE, "player/player.png").toString());
	}

	/**
	 * Default constructor
	 */
	public Player() {
		childrenView = new ArrayList<ILayeredChildView>();
		fillChildrenView();
		builModel();
		infosView = new PlayerInfosView(model);
		mainView = new PlayerView(model);
		mainView.addPropertyChangeListener(this);
	}
	
	/**
	 * Places the player in the world
	 * <p>
	 * 	<b>Note: the player must be added to the world before setting its position</b>
	 * </p>                                                                                   
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	public void startPosition(int x, int y) {
		model.getArea().setX(x);
		model.getArea().setY(y);
	}
	
	/**
	 * Moves this player along both axis (X and Y)
	 * @param dx the X axis step
	 * @param dy the Y axis step
	 */
	public void move(int dx, int dy) {
		MutableRectangle playerArea = model.getArea();
		playerArea.setX(playerArea.getX() + dx);
		playerArea.setY(playerArea.getY() + dy);
	}

	public boolean isWithin(int x, int y, int radius) {
		return model.getArea().intersects(x, y, radius);
	}
	
	public boolean collide(MutableRectangle area) {
		return model.getArea().intersects(area);
	}

	@Override
	public List<ILayeredChildView> getChildren() {
		return childrenView;
	}

	private void updateHeight(int height) {
		model.getArea().setHeight(height);
	}

	private void updateWidth(int width) {
		model.getArea().setWidth(width);
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
