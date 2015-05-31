package fr.cesi.ylalanne.mainframe;

import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.function.Consumer;
import java.util.function.Supplier;

import fr.cesi.ylalanne.contracts.IBoundChildController;
import fr.cesi.ylalanne.contracts.IChildController;
import fr.cesi.ylalanne.lang.LocaleManager;
import fr.cesi.ylalanne.mainframe.model.MainFrameActions;
import fr.cesi.ylalanne.mainframe.model.MainFrameModel;
import fr.cesi.ylalanne.mainframe.model.MainFrameStrings;
import fr.cesi.ylalanne.mainframe.model.MainMenuItemModel;
import fr.cesi.ylalanne.mainframe.ui.MainFrameView;

/**
 * Handles MainFrame view and model.
 * <p>Note: this controller holds an actionsHandler for external handling,<br> although some internal handling is performed.</p>
 */
public class MainFrameController implements PropertyChangeListener {

	private MainFrameView mainView;
	private IChildController child;
	private MainFrameModel mainModel;
	private Consumer<MainFrameActions> actionsHandler;
	private Supplier<Dimension> resolutionProvider;
	
	private MainMenuItemModel buildMenuItemModel(MainFrameActions action) {
		MainMenuItemModel itemModel = new MainMenuItemModel();
		itemModel.setAction(action);
		itemModel.setMnemonic(LocaleManager.getString(action.getKey() + "_mnemonic").charAt(0));
		itemModel.setValue(LocaleManager.getString(action.getKey()));
		return itemModel;
	}
	
	private void buildMainModel(int width, int height) {
		mainModel = new MainFrameModel();
		MainMenuItemModel startModel = buildMenuItemModel(MainFrameActions.START);
		MainMenuItemModel highScoresModel = buildMenuItemModel(MainFrameActions.HIGH_SCORE);
		MainMenuItemModel settingsModel = buildMenuItemModel(MainFrameActions.SETTINGS);
		MainMenuItemModel quitModel = buildMenuItemModel(MainFrameActions.QUIT);
		String menuTitle = LocaleManager.getString(MainFrameStrings.MENU_TITLE.getKey());
		String frameTitle = LocaleManager.getString(MainFrameStrings.TITLE.getKey());
		String mutedTitle = LocaleManager.getString(MainFrameActions.MUTE.getKey());
		
		mainModel.setStart(startModel);
		mainModel.setHighScores(highScoresModel);
		mainModel.setSettings(settingsModel);
		mainModel.setQuit(quitModel);
		mainModel.setMenuTitle(menuTitle);
		mainModel.setFrameTitle(frameTitle);
		mainModel.setWidth(width);
		mainModel.setHeight(height);
		mainModel.setMuted(true);
		mainModel.setMutedTitle(mutedTitle);
	}


	private void updateViewSize() {
		Dimension size = resolutionProvider.get();
		mainModel.setWidth(size.width);
		mainModel.setHeight(size.height);
		mainView.updateSize();
	}

	/**
	 * Initialize the MainFrameController.
	 *
	 * @param child the IChildController to run on start
	 * @param actionsHandler the handler for menu Actions
	 * @param resolutionProvider a delegate to provides Frame's size
	 */
	public MainFrameController(IBoundChildController child, Consumer<MainFrameActions> actionsHandler, Supplier<Dimension> resolutionProvider) {
		this.child = child;
		this.resolutionProvider = resolutionProvider;
		Dimension size = resolutionProvider.get();
		buildMainModel(size.width, size.height);
		this.actionsHandler = actionsHandler;
		child.addPropertyChangeListener("reseted", this);
		
	}

	/* (non-Javadoc)
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propertyName = evt.getPropertyName();
		Object newValue = evt.getNewValue();
		
		switch(propertyName) {
			case "action":
				MainFrameActions action = Enum.valueOf(MainFrameActions.class, newValue.toString());
				actionsHandler.accept(action);
				switch(action) {
					case MUTE:
						mainModel.setMuted(!mainModel.isMuted());
						break;
					case START:
						mainModel.setMuted(false);
						break;
					default:
						break;
				}
				break;
			case "reseted":
				mainView.removeLastChild();
				updateViewSize();
				mainView.addChild(child.getChild(), true);
				break;
		}
	}

	/**
	 * Starts this controller and display its view and its child's view.
	 */
	public void start() {
		mainView = new MainFrameView(mainModel);
		mainView.build();
		mainView.addPropertyChangeListener("action", this);
		if(child != null && child.getChild() != null) {
			mainView.addChild(child.getChild(), true);
		}
		mainView.show();
	}

}
