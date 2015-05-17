package menu.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

import main.ui.IChildView;
import main.ui.IParentView;
import menu.model.MainMenuItemModel;
import menu.model.MainMenuModel;
import utils.ui.ComponentLocation;

/**
 * The main menu View
 * <p>
 * It has the following bound properties:
 * <ul>
 * 	<li>action</li>
 * </ul>
 * </p>
 */
public class MainMenuView implements IParentView, ActionListener {
	private JFrame frame;
	private JMenuBar menuBar;
	private JMenu menu;
	private MainMenuModel viewModel;
	private PropertyChangeSupport propertyChange;
	private JMenuItem startMenuItem;
	private JMenuItem highScoreMenuItem;
	private JMenuItem settingsMenuItem;
	private JMenuItem quitMenuItem;
	private Object buildAwaiter;
	
	public MainMenuView(MainMenuModel viewModel) {
		this.viewModel = viewModel;
		buildAwaiter = new Object();
		propertyChange = new PropertyChangeSupport(this);
		SwingUtilities.invokeLater(() -> buildComponents());
	}

	private void buildComponents() {
		synchronized(buildAwaiter) {
			buildMenu();
			buildMenuBar();
			buildFrame();
			
			buildAwaiter.notifyAll();
		}
	}
	
	private void addHandlers(JMenuItem startMenuItem, JMenuItem highScoreMenuItem, JMenuItem settingsMenuItem, JMenuItem quitMenuItem) {
		startMenuItem.addActionListener(this);
		highScoreMenuItem.addActionListener(this);
		settingsMenuItem.addActionListener(this);
		quitMenuItem.addActionListener(this);
	}

	private void fillModels(JMenuItem startMenuItem, JMenuItem highScoreMenuItem, JMenuItem settingsMenuItem, JMenuItem quitMenuItem) {
		startMenuItem.setModel(viewModel.getStart());
		highScoreMenuItem.setModel(viewModel.getHighScores());
		settingsMenuItem.setModel(viewModel.getSettings());
		quitMenuItem.setModel(viewModel.getQuit());
	}
	
	private void buildMenu() {
		
		startMenuItem = new JMenuItem(viewModel.getStart().getValue(), viewModel.getStart().getMnemonic());
		highScoreMenuItem = new JMenuItem(viewModel.getHighScores().getValue(), viewModel.getHighScores().getMnemonic());
		settingsMenuItem = new JMenuItem(viewModel.getSettings().getValue(), viewModel.getSettings().getMnemonic());
		quitMenuItem = new JMenuItem(viewModel.getQuit().getValue(), viewModel.getQuit().getMnemonic());
		
		addHandlers(startMenuItem, highScoreMenuItem, settingsMenuItem, quitMenuItem);
		fillModels(startMenuItem, highScoreMenuItem, settingsMenuItem, quitMenuItem);
		
		menu = new JMenu(viewModel.getMenuTitle());
		menu.setMnemonic(viewModel.getMenuMnemonic());
		menu.add(startMenuItem);
		menu.add(highScoreMenuItem);
		menu.add(settingsMenuItem);
		menu.add(quitMenuItem);
	}

	private void buildMenuBar() {
		menuBar = new JMenuBar();
		menuBar.add(menu);
	}

	private void buildFrame() {
		frame = new JFrame(viewModel.getFrameTitle());
		LayoutManager layout = new BorderLayout();
		frame.setLayout(layout);
		
		frame.add(menuBar, BorderLayout.PAGE_START);
		
		frame.setPreferredSize(new Dimension(800, 600));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocation(ComponentLocation.getCenteredLocation(frame));
	}

	public void show() {
		SwingUtilities.invokeLater( () -> {
				frame.setVisible(true); 
		});
	}
	
	@Override
	public void addChild(IChildView child){
		synchronized(buildAwaiter) {
			try {
				buildAwaiter.wait();
				child.setParent(frame.getRootPane());
				frame.add(child.getComponent());
				frame.pack();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JMenuItem menuItem = (JMenuItem)e.getSource();
		MainMenuItemModel menuItemModel = (MainMenuItemModel)menuItem.getModel();
		propertyChange.firePropertyChange("action", null, menuItemModel.getAction());
	}

	/**
	 * @param listener
	 * @see java.beans.PropertyChangeSupport#addPropertyChangeListener(java.beans.PropertyChangeListener)
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertyChange.addPropertyChangeListener(listener);
	}
	/**
	 * @param listener
	 * @see java.beans.PropertyChangeSupport#removePropertyChangeListener(java.beans.PropertyChangeListener)
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyChange.removePropertyChangeListener(listener);
	}
	/**
	 * @param propertyName
	 * @param listener
	 * @see java.beans.PropertyChangeSupport#addPropertyChangeListener(java.lang.String, java.beans.PropertyChangeListener)
	 */
	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		propertyChange.addPropertyChangeListener(propertyName, listener);
	}
	/**
	 * @param propertyName
	 * @param listener
	 * @see java.beans.PropertyChangeSupport#removePropertyChangeListener(java.lang.String, java.beans.PropertyChangeListener)
	 */
	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		propertyChange.removePropertyChangeListener(propertyName, listener);
	}
}
