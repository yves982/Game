package fr.cesi.ylalanne.mainframe.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

import fr.cesi.ylalanne.contracts.ui.IChildView;
import fr.cesi.ylalanne.contracts.ui.IFocusedParentView;
import fr.cesi.ylalanne.mainframe.model.MainFrameActions;
import fr.cesi.ylalanne.mainframe.model.MainFrameModel;
import fr.cesi.ylalanne.mainframe.model.MainMenuItemModel;
import fr.cesi.ylalanne.utils.sound.SoundManager;
import fr.cesi.ylalanne.utils.ui.ComponentLocation;

/**
 * The main frame View
 * <p>It has the following bound properties:</p>
 * <ul>
 * 	<li>action</li>
 * </ul>
 */
public class MainFrameView extends WindowAdapter implements IFocusedParentView, ActionListener, PropertyChangeListener {
	private JFrame frame;
	private JMenuBar menuBar;
	private JMenu menu;
	private MainFrameModel viewModel;
	private PropertyChangeSupport propertyChange;
	private JMenuItem startMenuItem;
	private JMenuItem highScoreMenuItem;
	private JMenuItem settingsMenuItem;
	private JMenuItem quitMenuItem;
	private JCheckBoxMenuItem mutedMenuItem;
	private IChildView child;
	
	private void buildComponents() {
		buildMenu();
		buildMenuBar();
		buildFrame();
	}
	
	private void addHandlers(JMenuItem ... menuItems) {
		for(JMenuItem menuItem : menuItems) {
			menuItem.addActionListener(this);
		}
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
		mutedMenuItem = new JCheckBoxMenuItem(viewModel.getMutedTitle(), viewModel.isMuted());
		
		
		addHandlers(startMenuItem, highScoreMenuItem, settingsMenuItem, quitMenuItem, mutedMenuItem);
		fillModels(startMenuItem, highScoreMenuItem, settingsMenuItem, quitMenuItem);
		
		menu = new JMenu(viewModel.getMenuTitle());
		menu.setMnemonic(viewModel.getMenuMnemonic());
		menu.add(startMenuItem);
		menu.add(highScoreMenuItem);
		menu.add(mutedMenuItem);
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
		
		frame.setPreferredSize(new Dimension(viewModel.getWidth(), viewModel.getHeight()));
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(this);
		frame.pack();
		frame.setLocation(ComponentLocation.getCenteredLocation(frame));
	}

	private void updateMuted() {
		mutedMenuItem.setSelected(viewModel.isMuted());
	}

	public MainFrameView(MainFrameModel viewModel) {
		this.viewModel = viewModel;
		propertyChange = new PropertyChangeSupport(this);
		viewModel.addPropertyChangeListener(this);
	}

	public void show() {
		try {
			SwingUtilities.invokeAndWait( () -> {
				frame.setVisible(true);
			});
		} catch (InvocationTargetException | InterruptedException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public void updateSize() {
		frame.setPreferredSize(new Dimension(viewModel.getWidth(), viewModel.getHeight()));
		frame.pack();
	}
	
	@Override
	public void addChild(IChildView childView) {
		addChild(childView, false);
	}
	
	@Override
	public void addChild(IChildView childView, boolean requestFocus){
		Container contentPane = frame.getContentPane();
		Dimension availableSize = new Dimension(contentPane.getWidth(), contentPane.getHeight() - menuBar.getHeight());
		childView.setParent(contentPane, availableSize);
		frame.add(childView.getComponent());
		frame.pack();
		this.child = childView;
		
		if(requestFocus) {
			childView.getComponent().requestFocus();
		}
	}

	public void removeLastChild() {
		frame.remove(child.getComponent());
		frame.revalidate();
	}

	@Override
	public void build() {
		try {
			SwingUtilities.invokeAndWait(this::buildComponents);
		} catch (InvocationTargetException | InterruptedException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void windowClosing(WindowEvent e) {
		SoundManager.stop();
		System.exit(0);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JMenuItem menuItem = (JMenuItem)e.getSource();
		if(!e.getSource().equals(mutedMenuItem)) {
			MainMenuItemModel menuItemModel = (MainMenuItemModel)menuItem.getModel();
			propertyChange.firePropertyChange("action", null, menuItemModel.getAction());
		}else {
			propertyChange.firePropertyChange("action", null, MainFrameActions.MUTE);
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propertyName = evt.getPropertyName();
		
		switch(propertyName) {
			case "muted":
				updateMuted();
				break;
		}
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
