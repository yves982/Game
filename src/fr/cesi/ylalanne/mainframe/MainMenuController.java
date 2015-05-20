package fr.cesi.ylalanne.mainframe;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JOptionPane;

import fr.cesi.ylalanne.contracts.IRunnableChildController;
import fr.cesi.ylalanne.lang.LocaleManager;
import fr.cesi.ylalanne.mainframe.model.MainMenuActions;
import fr.cesi.ylalanne.mainframe.model.MainMenuItemModel;
import fr.cesi.ylalanne.mainframe.model.MainMenuModel;
import fr.cesi.ylalanne.mainframe.model.MainMenuStrings;
import fr.cesi.ylalanne.mainframe.ui.MainMenuView;
import fr.cesi.ylalanne.settings.SettingsController;

public class MainMenuController implements PropertyChangeListener {

	private MainMenuView mainView;
	private IRunnableChildController child;
	private MainMenuModel mainModel;
	
	private MainMenuItemModel buildMenuItemModel(MainMenuActions action) {
		MainMenuItemModel itemModel = new MainMenuItemModel();
		itemModel.setAction(action);
		itemModel.setMnemonic(LocaleManager.getString(action.getKey() + "_mnemonic").charAt(0));
		itemModel.setValue(LocaleManager.getString(action.getKey()));
		return itemModel;
	}
	
	private void buildMainModel() {
		mainModel = new MainMenuModel();
		MainMenuItemModel startModel = buildMenuItemModel(MainMenuActions.START);
		MainMenuItemModel highScoresModel = buildMenuItemModel(MainMenuActions.HIGH_SCORE);
		MainMenuItemModel settingsModel = buildMenuItemModel(MainMenuActions.SETTINGS);
		MainMenuItemModel quitModel = buildMenuItemModel(MainMenuActions.QUIT);
		String menuTitle = LocaleManager.getString(MainMenuStrings.MENU_TITLE.getKey());
		String frameTitle = LocaleManager.getString(MainMenuStrings.TITLE.getKey());
		
		mainModel.setStart(startModel);
		mainModel.setHighScores(highScoresModel);
		mainModel.setSettings(settingsModel);
		mainModel.setQuit(quitModel);
		mainModel.setMenuTitle(menuTitle);
		mainModel.setFrameTitle(frameTitle);
	}

	private void handleAction(MainMenuActions action) {
		switch(action) {
			case START:
				JOptionPane.showMessageDialog(null, "start was clicked", "Info", JOptionPane.INFORMATION_MESSAGE);
				break;
			case QUIT:
				System.exit(0);
				break;
		case HIGH_SCORE:
			JOptionPane.showMessageDialog(null, "high scores was clicked", "Info", JOptionPane.INFORMATION_MESSAGE);
			break;
		case SETTINGS:
			SettingsController settingsController = new SettingsController();
			settingsController.start();
			break;
		}
	}

	/**
	 * Initialize the MainMenuController
	 * @param child the IRunnableChildController to run on start
	 */
	public MainMenuController(IRunnableChildController child) {
		this.child = child;
		buildMainModel();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propertyName = evt.getPropertyName();
		Object newValue = evt.getNewValue();
		
		switch(propertyName) {
			case "action":
				MainMenuActions action = Enum.valueOf(MainMenuActions.class, newValue.toString());
				handleAction(action);
				break;
		}
	}

	/**
	 * Starts this controller and display its view and its child's view
	 */
	public void start() {
		mainView = new MainMenuView(mainModel);
		mainView.build();
		mainView.addPropertyChangeListener("action", this);
		if(child != null && child.getChild() != null) {
			mainView.addChild(child.getChild());
		}
		mainView.show();
		child.run();
	}

}
