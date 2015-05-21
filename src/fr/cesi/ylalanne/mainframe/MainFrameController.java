package fr.cesi.ylalanne.mainframe;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.function.Consumer;

import fr.cesi.ylalanne.contracts.IChildController;
import fr.cesi.ylalanne.lang.LocaleManager;
import fr.cesi.ylalanne.mainframe.model.MainFrameActions;
import fr.cesi.ylalanne.mainframe.model.MainFrameModel;
import fr.cesi.ylalanne.mainframe.model.MainFrameStrings;
import fr.cesi.ylalanne.mainframe.model.MainMenuItemModel;
import fr.cesi.ylalanne.mainframe.ui.MainFrameView;

public class MainFrameController implements PropertyChangeListener {

	private MainFrameView mainView;
	private IChildController child;
	private MainFrameModel mainModel;
	private Consumer<MainFrameActions> actionsHandler;
	
	private MainMenuItemModel buildMenuItemModel(MainFrameActions action) {
		MainMenuItemModel itemModel = new MainMenuItemModel();
		itemModel.setAction(action);
		itemModel.setMnemonic(LocaleManager.getString(action.getKey() + "_mnemonic").charAt(0));
		itemModel.setValue(LocaleManager.getString(action.getKey()));
		return itemModel;
	}
	
	private void buildMainModel() {
		mainModel = new MainFrameModel();
		MainMenuItemModel startModel = buildMenuItemModel(MainFrameActions.START);
		MainMenuItemModel highScoresModel = buildMenuItemModel(MainFrameActions.HIGH_SCORE);
		MainMenuItemModel settingsModel = buildMenuItemModel(MainFrameActions.SETTINGS);
		MainMenuItemModel quitModel = buildMenuItemModel(MainFrameActions.QUIT);
		String menuTitle = LocaleManager.getString(MainFrameStrings.MENU_TITLE.getKey());
		String frameTitle = LocaleManager.getString(MainFrameStrings.TITLE.getKey());
		
		mainModel.setStart(startModel);
		mainModel.setHighScores(highScoresModel);
		mainModel.setSettings(settingsModel);
		mainModel.setQuit(quitModel);
		mainModel.setMenuTitle(menuTitle);
		mainModel.setFrameTitle(frameTitle);
	}



	/**
	 * Initialize the MainFrameController
	 * @param child the IChildController to run on start
	 * @param actionsHandler the handler for menu Actions
	 */
	public MainFrameController(IChildController child, Consumer<MainFrameActions> actionsHandler) {
		this.child = child;
		buildMainModel();
		this.actionsHandler = actionsHandler;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propertyName = evt.getPropertyName();
		Object newValue = evt.getNewValue();
		
		switch(propertyName) {
			case "action":
				MainFrameActions action = Enum.valueOf(MainFrameActions.class, newValue.toString());
				actionsHandler.accept(action);
				break;
		}
	}

	/**
	 * Starts this controller and display its view and its child's view
	 */
	public void start() {
		mainView = new MainFrameView(mainModel);
		mainView.build();
		mainView.addPropertyChangeListener("action", this);
		if(child != null && child.getChild() != null) {
			mainView.addChild(child.getChild());
		}
		mainView.show();
	}

}
