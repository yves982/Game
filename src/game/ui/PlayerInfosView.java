package game.ui;

import game.model.PlayerModel;

import java.awt.Image;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import main.ResourcesManager;
import main.ui.IChildView;

public class PlayerInfosView implements IChildView {
	private JComponent parent;
	private JPanel infosPanel;
	private JLabel livesLabel;
	private JLabel scoresLabel;
	private JLabel timeLabel;
	private PlayerModel model;
	private FutureTask<Void> buildTask;
	
	public PlayerInfosView(PlayerModel model) {
		this.model = model;
		buildTask = new FutureTask<Void>(() -> buildComponents(), null);
		SwingUtilities.invokeLater(buildTask);
	}

	private void buildLives() {
		try {
		livesLabel = new JLabel();
		BoxLayout boxLayout = new BoxLayout(livesLabel, BoxLayout.LINE_AXIS);
		livesLabel.setLayout(boxLayout);
		Image livesImage = ImageIO.read("".getClass().getResource(Paths.get(ResourcesManager.RESOURCES_BASE, "lives.png").toString()));
		ImageIcon livesIcon = new ImageIcon(livesImage);
		for(int i=0; i < model.getLives(); i++) {
			livesLabel.add(new JLabel(livesIcon));
		}
		} catch(IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	private void buildScore() {
		// TODO Auto-generated method stub
		
	}

	private void buildTime() {
		// TODO Auto-generated method stub
		
	}

	private void buildInfos() {
		// TODO Auto-generated method stub
		
	}

	private void buildComponents() {
		buildLives();
		buildScore();
		buildTime();
		buildInfos();
	}

	@Override
	public JComponent getComponent() {
		try {
			buildTask.get();
			return infosPanel;
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void setParent(JComponent parent) {
		this.parent = parent;
	}
}
