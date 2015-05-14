package menu.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

import main.ui.IChildView;
import main.ui.IParentView;
import menu.model.MainMenuModel;
import utils.ui.ComponentLocation;

public class MainMenuView implements IParentView {
	private JFrame frame;
	private JMenuBar menuBar;
	private JMenu menu;
	private ActionListener actionHandler;
	private MainMenuModel viewModel;
	private FutureTask<Void> buildTask;
	
	public MainMenuView(ActionListener listener, MainMenuModel viewModel) {
		actionHandler = listener;
		this.viewModel = viewModel;
		buildTask = new FutureTask<Void>(() -> buildComponents(), null);
		SwingUtilities.invokeLater(buildTask);
	}

	private void buildComponents() {
		buildMenu();
		buildMenuBar();
		buildFrame();
	}
	
	private void addHandlers(JMenuItem startMenuItem, JMenuItem highScoreMenuItem, JMenuItem settingsMenuItem, JMenuItem quitMenuItem) {
		startMenuItem.addActionListener(actionHandler);
		highScoreMenuItem.addActionListener(actionHandler);
		settingsMenuItem.addActionListener(actionHandler);
		quitMenuItem.addActionListener(actionHandler);
	}

	private void fillModels(JMenuItem startMenuItem, JMenuItem highScoreMenuItem, JMenuItem settingsMenuItem, JMenuItem quitMenuItem) {
		startMenuItem.setModel(viewModel.getStart());
		highScoreMenuItem.setModel(viewModel.getHighScores());
		settingsMenuItem.setModel(viewModel.getSettings());
		quitMenuItem.setModel(viewModel.getQuit());
	}

	private void buildMenu() {
		
		JMenuItem startMenuItem = new JMenuItem(viewModel.getStart().getValue(), viewModel.getStart().getMnemonic());
		JMenuItem highScoreMenuItem = new JMenuItem(viewModel.getHighScores().getValue(), viewModel.getHighScores().getMnemonic());
		JMenuItem settingsMenuItem = new JMenuItem(viewModel.getSettings().getValue(), viewModel.getSettings().getMnemonic());
		JMenuItem quitMenuItem = new JMenuItem(viewModel.getQuit().getValue(), viewModel.getQuit().getMnemonic());
		
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
		try {
			buildTask.get();
			frame.add(child.getComponent());
			frame.pack();
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e);
		}
	}
}
