package game.ui;

import game.model.PlayerInfosStrings;
import game.model.PlayerModel;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.LayoutManager;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import lang.LocaleManager;
import main.ui.ILayeredChildView;
import utils.ui.GridBagConstraintsAnchor;
import utils.ui.GridBagConstraintsBuilder;
import utils.ui.ImageLoader;

/**
 * a view for player infos, this one does not raise events as it's inputless
 */
public class PlayerInfosView implements ILayeredChildView, PropertyChangeListener {
	private JComponent parent;
	private JPanel infosPanel;
	private JPanel livesPanel;
	private JLabel scoresLabel;
	private JLabel timeLabel;
	private JLabel timeLeftLabel;
	private GridBagConstraintsBuilder gridBagConstraintsBuilder;
	private PlayerModel model;
	
	private static final String TIME = "time";
	private static final String TIME_LEFT = "timeLeft";
	private static final String LIVES = "lives";
	private static final String SCORE = "score";
	private ImageIcon livesIcon;
	private int timeLeftMaxSize;
	private boolean built;
	
	private void updateLives() {
		livesPanel.removeAll();
		
		for(int i=0; i < model.getLives(); i++) {
			JLabel liveLabel = new JLabel(livesIcon);
			liveLabel.setVisible(true);
			livesPanel.add(liveLabel);
		}
		livesPanel.revalidate();
	}

	private void buildLives() {
		livesPanel = new JPanel(true);
		BoxLayout boxLayout = new BoxLayout(livesPanel, BoxLayout.LINE_AXIS);
		livesPanel.setLayout(boxLayout);
		Image livesImage = ImageLoader.LoadImageWithShortPath("/player/infos/lives.gif");
		livesIcon = new ImageIcon(livesImage);
		updateLives();
		livesPanel.setVisible(true);
	}

	private void buildScore() {
		scoresLabel = new JLabel();
		scoresLabel.setText(String.format("%s %d", LocaleManager.getString(PlayerInfosStrings.SCORE.getKey()), model.getScore()));
		scoresLabel.setVisible(true);
	}

	private void buildTime() {
		timeLabel = new JLabel();
		timeLabel.setText(LocaleManager.getString(PlayerInfosStrings.TIME.getKey()));
		timeLeftLabel = new JLabel();
		timeLeftLabel.setBackground(Color.green);
		timeLeftLabel.setVisible(true);
	}

	private Map<String, GridBagConstraints> buildConstraints() {
		Map<String, GridBagConstraints> constraints = new HashMap<String, GridBagConstraints>();
		
		GridBagConstraints livesConstraint = gridBagConstraintsBuilder
				.position(0, 0)
				.weight(1, 1)
				.anchor(GridBagConstraintsAnchor.FIRST_LINE_START)
				.build();
		constraints.put(LIVES, livesConstraint);
		
		GridBagConstraints scoreConstraint = gridBagConstraintsBuilder
				.position(0, 1)
				.weight(1, 1)
				.anchor(GridBagConstraintsAnchor.BELOW_BASELINE_TRAILING)
				.build();
		constraints.put(SCORE, scoreConstraint);
		
		GridBagConstraints timeLeftConstraint = gridBagConstraintsBuilder
				.position(1, 1)
				.weight(1, 1)
				.anchor(GridBagConstraintsAnchor.BASELINE)
				.build();
		constraints.put(TIME_LEFT, timeLeftConstraint);
		
		GridBagConstraints timeConstraint = gridBagConstraintsBuilder
				.position(2, 1)
				.weight(1, 1)
				.anchor(GridBagConstraintsAnchor.FIRST_LINE_END)
				.build();
		constraints.put(TIME, timeConstraint);
		
		return constraints;
	}

	private void buildInfos() {
		infosPanel = new JPanel();
		LayoutManager gridBagLayout = new GridBagLayout();
		infosPanel.setLayout(gridBagLayout);
		
		Map<String, GridBagConstraints> constraints = buildConstraints();
		
		
		
		infosPanel.add(livesPanel, constraints.get(LIVES));
		infosPanel.add(scoresLabel, constraints.get(SCORE));
		infosPanel.add(timeLeftLabel, constraints.get(TIME_LEFT));
		infosPanel.add(timeLabel, constraints.get(TIME));
		infosPanel.setVisible(true);
	}

	private void buildComponents() {
		buildLives();
		buildScore();
		buildTime();
		buildInfos();
	}

	private void checkBuild() {
		if(!built) {
			throw new RuntimeException("you should call build() before using this view.");
		}
	}
	
	private void updateScore() {
		scoresLabel.setText(String.format("%s %i", 
				LocaleManager.getString(PlayerInfosStrings.SCORE.getKey()), 
				model.getScore()));
	}

	private void updateTimeLeft() {
		double timeLeftMs = model.getRemainingLiveTimeMs();
		double totalTimeMs = model.getMaxLiveTimeMs();
		int currentWidth = (int)Math.ceil(timeLeftMaxSize * (timeLeftMs / totalTimeMs));
		timeLeftLabel.setSize(currentWidth , timeLeftLabel.getHeight());
	}

	/**
	 * Initialize the Player infos view
	 * @param model the player model
	 */
	public PlayerInfosView(PlayerModel model) {
		this.model = model;
		built = false;
		model.addPropertyChangeListener(this);
		gridBagConstraintsBuilder = new GridBagConstraintsBuilder();
	}

	@Override
	public JComponent getComponent() {
		checkBuild();
		return infosPanel;
	}
	
	@Override
	public int getLayer() {
		checkBuild();
		return 0;
	}

	@Override
	public void setParent(JComponent parent) {
		checkBuild();
		this.parent = parent;
		timeLeftMaxSize = (int)Math.ceil(0.04 * this.parent.getWidth());
	}

	public void build() {
		try {
			SwingUtilities.invokeAndWait(this::buildComponents);
			built = true;
		} catch (InterruptedException | InvocationTargetException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propertyName = evt.getPropertyName();
		switch (propertyName) {
		case "score":
			updateScore();
			break;
		case "lives":
			updateLives();
			break;
		case "remainingLiveTimeMs":
			updateTimeLeft();
			break;
		}
	}
}
