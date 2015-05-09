package ui;

import java.awt.Dialog.ModalityType;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import model.settings.Difficulty;
import model.settings.DifficultyButtonModel;
import model.settings.Resolution;
import model.settings.ResolutionButtonModel;
import model.settings.SettingsViewModel;
import ui.utils.GridBagConstraintsAnchor;
import ui.utils.GridBagConstraintsBuilder;
import ui.utils.Utils;

public class SettingsView implements PropertyChangeListener {
	private JDialog settingsDialog;
	private ActionListener actionHandler;
	private JPanel difficultyPanel;
	private JPanel resolutionPanel;
	private JButton okBtn;
	private JButton cancelBtn;
	private GridBagConstraintsBuilder constraintBuilder;
	private SettingsViewModel model;
	private Map<Difficulty, JRadioButton> difficultyButtons;
	private Map<Resolution, JRadioButton> resolutionButtons;
	private ButtonGroup difficultyButtonGroup;
	private ButtonGroup resolutionButtonGroup;
	
	public SettingsView(ActionListener listener, SettingsViewModel model) {
		actionHandler = listener;
		this.model = model;
		constraintBuilder = new GridBagConstraintsBuilder();
		difficultyButtons = new HashMap<Difficulty, JRadioButton>();
		difficultyButtonGroup = new ButtonGroup();
		resolutionButtons = new HashMap<Resolution, JRadioButton>();
		resolutionButtonGroup = new ButtonGroup();
		SwingUtilities.invokeLater( () -> buildComponents() );
	}

	private void buildComponents() {
		buildDifficultyPanel();
		buildResolutionPanel();
		buildButtons();
		buildSettingsDialog();
		model.addPropertyChangeListener(this);
	}

	private JRadioButton buildRadioButton(Difficulty difficulty) {
		JRadioButton radioButton = new JRadioButton(model.getDifficulty(difficulty));
		DifficultyButtonModel buttonModel = new DifficultyButtonModel(difficulty);
		radioButton.setModel(buttonModel);
		
		if(model.getSelectedDifficulty().equals(difficulty)) {
			radioButton.setSelected(true);
		}
		difficultyButtons.put(difficulty, radioButton);
		
		radioButton.addActionListener(actionHandler);
		return radioButton;
	}
	
	private void buildDifficultyPanel() {
		GridBagLayout layout = new GridBagLayout();
		difficultyPanel = new JPanel();
		difficultyPanel.setLayout(layout);
		Border titledBorder = BorderFactory.createTitledBorder(model.getDifficultyTitle());
		difficultyPanel.setBorder(titledBorder);
		
		JRadioButton rdbEasy = buildRadioButton(Difficulty.EASY);
		JRadioButton rdbNormal = buildRadioButton(Difficulty.NORMAL);
		JRadioButton rdbHard = buildRadioButton(Difficulty.HARD);
		
		difficultyButtonGroup.add(rdbEasy);
		difficultyButtonGroup.add(rdbNormal);
		difficultyButtonGroup.add(rdbHard);
		
		GridBagConstraints easyConstraint = constraintBuilder
				.position(0, 0)
				.span(1, 1)
				.anchor(GridBagConstraintsAnchor.LINE_START).build();
		
		GridBagConstraints normalConstraint = constraintBuilder
				.position(0, 1)
				.span(1, 1)
				.anchor(GridBagConstraintsAnchor.LINE_START).build();
		
		GridBagConstraints hardConstraint = constraintBuilder
				.position(0,  2)
				.span(1, 1)
				.anchor(GridBagConstraintsAnchor.LINE_START).build();
		
		difficultyPanel.add(rdbEasy, easyConstraint);
		difficultyPanel.add(rdbNormal, normalConstraint);
		difficultyPanel.add(rdbHard, hardConstraint);
	}

	private JRadioButton buildRadioButton(Resolution resolution) {
		JRadioButton radioButton = new JRadioButton(model.getResolution(resolution));
		ResolutionButtonModel buttonModel = new ResolutionButtonModel(resolution);
		radioButton.setModel(buttonModel);
		
		if(model.getSelectedResolution().equals(resolution)) {
			radioButton.setSelected(true);
		}
		resolutionButtons.put(resolution,  radioButton);
		
		radioButton.addActionListener(actionHandler);
		return radioButton;
	}
	
	private void buildResolutionPanel() {
		GridBagLayout layout = new GridBagLayout();
		resolutionPanel = new JPanel();
		resolutionPanel.setLayout(layout);
		Border titledBorder = BorderFactory.createTitledBorder(model.getResolutionTitle());
		resolutionPanel.setBorder(titledBorder);
		
		JRadioButton rdbLow = buildRadioButton(Resolution.LOW);
		JRadioButton rdbStandard = buildRadioButton(Resolution.STANDARD);
		JRadioButton rdbHigh = buildRadioButton(Resolution.HIGH);
		
		resolutionButtonGroup.add(rdbLow);
		resolutionButtonGroup.add(rdbStandard);
		resolutionButtonGroup.add(rdbHigh);
		
		GridBagConstraints lowConstraint = constraintBuilder
				.position(0, 0)
				.span(1, 1)
				.anchor(GridBagConstraintsAnchor.LINE_START).build();
		
		GridBagConstraints standardConstraint = constraintBuilder
				.position(0, 1)
				.span(1, 1)
				.anchor(GridBagConstraintsAnchor.LINE_START).build();
		
		GridBagConstraints highConstraint = constraintBuilder
				.position(0,  2)
				.span(1, 1)
				.anchor(GridBagConstraintsAnchor.LINE_START).build();
		
		resolutionPanel.add(rdbLow, lowConstraint);
		resolutionPanel.add(rdbStandard, standardConstraint);
		resolutionPanel.add(rdbHigh, highConstraint);
	}

	private void buildButtons() {
		okBtn = new JButton(model.getOk());
		cancelBtn = new JButton(model.getCancel());
	}

	private void addSettingsDialogComponents() {
		GridBagConstraints difficultyConstraint = constraintBuilder
			.position(0, 0)
			.span(1, 1)
			.margins(0, 5, 5, 0).build();

		GridBagConstraints resolutionConstraint = constraintBuilder
				.position(1,  0)
				.span(1, 1)
				.margins(0, 5, 5, 0).build();
		
		GridBagConstraints okConstraint = constraintBuilder
				.position(0, 1)
				.span(1, 1).build();
		
		GridBagConstraints cancelConstraint = constraintBuilder
				.position(1, 1)
				.span(1, 1).build();
		
		settingsDialog.add(difficultyPanel, difficultyConstraint);
		settingsDialog.add(resolutionPanel, resolutionConstraint);
		settingsDialog.add(okBtn, okConstraint);
		settingsDialog.add(cancelBtn, cancelConstraint);
	}
	
	private void buildSettingsDialog() {
		settingsDialog = new JDialog();
		settingsDialog.setTitle(model.getSettingsTitle());
		LayoutManager layout = new GridBagLayout();
		settingsDialog.setLayout(layout);
		settingsDialog.setModalityType(ModalityType.APPLICATION_MODAL);
		
		addSettingsDialogComponents();
				
		settingsDialog.setResizable(false);
		settingsDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		settingsDialog.pack();
		settingsDialog.setLocation(Utils.getCenteredLocation(settingsDialog));
	}
	
	

	public void show() {
		SwingUtilities.invokeLater( () -> {
			settingsDialog.setVisible(true);
		});
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propertyName = evt.getPropertyName();
		switch(propertyName) {
			case "selectedDifficulty":
				Difficulty difficulty = (Difficulty)evt.getNewValue();
				difficultyButtonGroup.setSelected(difficultyButtons.get(difficulty).getModel(), true);
				break;
			case "selectedResolution":
				Resolution resolution = (Resolution)evt.getNewValue();
				resolutionButtonGroup.setSelected(resolutionButtons.get(resolution).getModel(), true);
				break;
		}
	}
}
