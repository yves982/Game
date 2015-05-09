package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import lang.Messages;
import model.main.MainViewActions;
import model.main.MainViewMenuItemModel;
import model.main.MainViewModel;
import model.main.MainViewStrings;
import model.settings.SettingsStrings;
import model.settings.SettingsViewModel;
import ui.MainView;

public class MainController implements ActionListener {

	private Messages messages;
	private Locale locale;
	private MainView mainView;
	private MainViewModel mainModel;
	
	public MainController(Locale locale) {
		this.locale = locale;
		messages = new Messages(locale);
		buildMainModel();
		
	}
	
	private MainViewMenuItemModel buildMenuItemModel(MainViewActions action) {
		MainViewMenuItemModel itemModel = new MainViewMenuItemModel();
		itemModel.setAction(action);
		itemModel.setMnemonic(messages.getString(action.getKey() + "_mnemonic").charAt(0));
		itemModel.setValue(messages.getString(action.getKey()));
		return itemModel;
	}
	
	private void buildMainModel() {
		mainModel = new MainViewModel();
		MainViewMenuItemModel startModel = buildMenuItemModel(MainViewActions.START);
		MainViewMenuItemModel highScoresModel = buildMenuItemModel(MainViewActions.HIGH_SCORE);
		MainViewMenuItemModel settingsModel = buildMenuItemModel(MainViewActions.SETTINGS);
		MainViewMenuItemModel quitModel = buildMenuItemModel(MainViewActions.QUIT);
		String menuTitle = messages.getString(MainViewStrings.MENU_TITLE.getKey());
		String title = messages.getString(MainViewStrings.TITLE.getKey());
		
		mainModel.setStart(startModel);
		mainModel.setHighScores(highScoresModel);
		mainModel.setSettings(settingsModel);
		mainModel.setQuit(quitModel);
		mainModel.setMenuTitle(menuTitle);
		mainModel.setTitle(title);
	}

	public SettingsViewModel retrieveBaseSettingsModel() {
		SettingsViewModel settingsModel = new SettingsViewModel();
		settingsModel.setCancel(messages.getString(SettingsStrings.MAIN_BUTTONS_CANCEL.getKey()));
		settingsModel.setOk(messages.getString(SettingsStrings.MAIN_BUTTONS_CANCEL.getKey()));
		settingsModel.setSettingsTitle(messages.getString(SettingsStrings.SETTINGS_DIALOG_TITLE.getKey()));
		return settingsModel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JMenuItem menuItem = (JMenuItem)e.getSource();
		MainViewMenuItemModel menuItemModel = (MainViewMenuItemModel)menuItem.getModel();
		
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
			SettingsController settingsController = new SettingsController(locale);
			settingsController.start();
			break;
		}
	}

	public void start() {
		mainView = new MainView(this, mainModel);
		mainView.show();
	}

}
