package fr.cesi.ylalanne.settings.ui;

import java.awt.Dialog.ModalityType;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import fr.cesi.ylalanne.settings.model.Actions;
import fr.cesi.ylalanne.settings.model.Difficulties;
import fr.cesi.ylalanne.settings.model.Resolutions;
import fr.cesi.ylalanne.settings.model.SettingsProperties;
import fr.cesi.ylalanne.settings.model.SettingsViewModel;
import fr.cesi.ylalanne.utils.ui.ComponentLocation;
import fr.cesi.ylalanne.utils.ui.GridBagConstraintsAnchor;
import fr.cesi.ylalanne.utils.ui.GridBagConstraintsBuilder;

public class SettingsView implements PropertyChangeListener, ActionListener {
	private JDialog settingsDialog;
	private JPanel difficultyPanel;
	private JPanel resolutionPanel;
	private JButton okBtn;
	private JButton cancelBtn;
	private GridBagConstraintsBuilder constraintBuilder;
	private SettingsViewModel model;
	private Map<Difficulties, JRadioButton> difficultyButtons;
	private Map<Resolutions, JRadioButton> resolutionButtons;
	private Map<JComponent, Consumer<Void>> actionHandlers;
	private ButtonGroup difficultyButtonGroup;
	private ButtonGroup resolutionButtonGroup;
	private PropertyChangeSupport propertyChange;
	
	public SettingsView(SettingsViewModel model) {
		this.model = model;
		constraintBuilder = new GridBagConstraintsBuilder();
		difficultyButtons = new HashMap<Difficulties, JRadioButton>();
		difficultyButtonGroup = new ButtonGroup();
		resolutionButtons = new HashMap<Resolutions, JRadioButton>();
		resolutionButtonGroup = new ButtonGroup();
		actionHandlers = new HashMap<JComponent, Consumer<Void>>();
		propertyChange = new PropertyChangeSupport(this);
		SwingUtilities.invokeLater( () -> buildComponents() );
	}

	private void buildComponents() {
		buildDifficultyPanel();
		buildResolutionPanel();
		buildButtons();
		buildSettingsDialog();
		model.addPropertyChangeListener(this);
	}

	private JRadioButton buildRadioButton(Difficulties difficulty) {
		JRadioButton radioButton = new JRadioButton(model.getDifficulty(difficulty));
		actionHandlers.put(radioButton, (v) -> propertyChange.firePropertyChange(SettingsProperties.DIFFICULTY.toString(), 
				model.getSelectedDifficulty(),
				difficulty));
		
		if(model.getSelectedDifficulty().equals(difficulty)) {
			radioButton.setSelected(true);
		}
		difficultyButtons.put(difficulty, radioButton);
		
		radioButton.addActionListener(this);
		return radioButton;
	}

	private void buildDifficultyPanel() {
		GridBagLayout layout = new GridBagLayout();
		difficultyPanel = new JPanel();
		difficultyPanel.setLayout(layout);
		Border titledBorder = BorderFactory.createTitledBorder(model.getDifficultyTitle());
		difficultyPanel.setBorder(titledBorder);
		
		JRadioButton rdbEasy = buildRadioButton(Difficulties.EASY);
		JRadioButton rdbNormal = buildRadioButton(Difficulties.NORMAL);
		JRadioButton rdbHard = buildRadioButton(Difficulties.HARD);
		
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

	private JRadioButton buildRadioButton(Resolutions resolution) {
		JRadioButton radioButton = new JRadioButton(model.getResolution(resolution));
		
		if(model.getSelectedResolution().equals(resolution)) {
			radioButton.setSelected(true);
		}
		resolutionButtons.put(resolution,  radioButton);
		
		actionHandlers.put(radioButton, (v) -> propertyChange.firePropertyChange(SettingsProperties.RESOLUTION.toString(),
				model.getSelectedResolution(),
				resolution));
		
		radioButton.addActionListener(this);
		return radioButton;
	}
	
	private void buildResolutionPanel() {
		GridBagLayout layout = new GridBagLayout();
		resolutionPanel = new JPanel();
		resolutionPanel.setLayout(layout);
		Border titledBorder = BorderFactory.createTitledBorder(model.getResolutionTitle());
		resolutionPanel.setBorder(titledBorder);
		
		JRadioButton rdbLow = buildRadioButton(Resolutions.LOW);
		JRadioButton rdbStandard = buildRadioButton(Resolutions.STANDARD);
		JRadioButton rdbHigh = buildRadioButton(Resolutions.HIGH);
		
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

	private JButton buildButton(Actions action) {
		JButton button = new JButton(model.getAction(action));
		button.addActionListener(this);
		actionHandlers.put(button, (v) -> propertyChange.firePropertyChange(SettingsProperties.ACTION.toString()
				, null,
				action));
		return button;
	}
	
	private void buildButtons() {
		okBtn = buildButton(Actions.OK);
		cancelBtn = buildButton(Actions.CANCEL);
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
				
		settingsDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		settingsDialog.pack();
		settingsDialog.setResizable(false);
		settingsDialog.setLocation(ComponentLocation.getCenteredLocation(settingsDialog));
	}
	
	

	public void show() {
		SwingUtilities.invokeLater( () -> {
			settingsDialog.setVisible(true);
		});
	}

	public void close() {
		settingsDialog.dispose();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propertyName = evt.getPropertyName();
		switch(propertyName) {
			case "selectedDifficulty":
				Difficulties difficulty = (Difficulties)evt.getNewValue();
				difficultyButtonGroup.setSelected(difficultyButtons.get(difficulty).getModel(), true);
				break;
			case "selectedResolution":
				Resolutions resolution = (Resolutions)evt.getNewValue();
				resolutionButtonGroup.setSelected(resolutionButtons.get(resolution).getModel(), true);
				break;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JComponent source = (JComponent)e.getSource();
		actionHandlers.get(source).accept(null);
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
