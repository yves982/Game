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

import main.IChildrenController;
import main.ResourcesManager;
import main.ui.IChildView;

public class Player implements IChildrenController, PropertyChangeListener {
	private PlayerModel model;
	private PlayerView mainView;
	private PlayerInfosView infosView;
	private List<IChildView> childrenView;
	
	private void fillChildrenView() {
		childrenView.add(mainView);
		childrenView.add(infosView);
	}

	private void builModel() {
		model = new PlayerModel();
		model.setImagePath(Paths.get(ResourcesManager.RESOURCES_BASE, "player/player.png").toString());
	}

	/**
	 * Default constructor
	 */
	public Player() {
		childrenView = new ArrayList<IChildView>();
		fillChildrenView();
		builModel();
		infosView = new PlayerInfosView(model);
		mainView = new PlayerView(model);
		mainView.addPropertyChangeListener(this);
	}
	
	public void move(int x, int y) {
		MutableRectangle area = model.getArea();
		area.setX(area.getX() + x);
		area.setY(area.getY() + y);
	}

	public boolean isWithin(int x, int y, int radius) {
		return model.getArea().intersects(x, y, radius);
	}
	
	public boolean collide(MutableRectangle area) {
		return model.getArea().intersects(area);
	}

	@Override
	public List<IChildView> getChildren() {
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
