package menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import lang.LocaleManager;
import lang.Messages;
import main.IChildController;
import menu.model.MainMenuActions;
import menu.model.MainMenuItemModel;
import menu.model.MainMenuModel;
import menu.model.MainMenuStrings;
import menu.ui.MainMenuView;
import settings.SettingsController;

public class MainMenuController implements ActionListener {

	private MainMenuView mainView;
	private IChildController child;
	private MainMenuModel mainModel;
	
	public MainMenuController(IChildController child) {
		this.child = child;
		buildMainModel();
	}
	
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

	@Override
	public void actionPerformed(ActionEvent e) {
		JMenuItem menuItem = (JMenuItem)e.getSource();
		MainMenuItemModel menuItemModel = (MainMenuItemModel)menuItem.getModel();
		
		switch(menuItemModel.getAction()) {
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

	public void start() throws InterruptedException, ExecutionException {
		mainView = new MainMenuView(this, mainModel);
		if(child != null && child.getChild() != null) {
			mainView.addChild(child.getChild());
		}
		mainView.show();
	}

}
