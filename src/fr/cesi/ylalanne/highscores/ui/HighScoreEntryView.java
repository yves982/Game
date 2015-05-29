package fr.cesi.ylalanne.highscores.ui;

import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import fr.cesi.ylalanne.contracts.ui.IView;
import fr.cesi.ylalanne.highscores.model.HighScoresStrings;
import fr.cesi.ylalanne.lang.LocaleManager;
import fr.cesi.ylalanne.utils.ui.ComponentLocation;
import fr.cesi.ylalanne.utils.ui.GridBagConstraintsAnchor;
import fr.cesi.ylalanne.utils.ui.GridBagConstraintsBuilder;

/**
 * HighScore entry view
 * <p>It has the following bound properties</p>
 * <ul>
 *   <li>name</li>
 * </ul>
 */
public class HighScoreEntryView implements IView, ActionListener {
	private PropertyChangeSupport propertyChange;
	private JDialog mainDialog;
	private JTextField nameTextField;
	private JButton okBtn;
	private JLabel scoreLabel;
	private GridBagConstraintsBuilder builder;
	private int highScore;
	
	private void buildComponents() {
		buildName();
		buildScore();
		buildOk();
		buildMainDialog();
	}
	
	private void buildOk() {
		okBtn = new JButton(LocaleManager.getString(HighScoresStrings.OK.getKey()));
		okBtn.addActionListener(this);
		okBtn.setVisible(true);
	}

	private void buildMainDialog() {
		mainDialog = new JDialog(null, ModalityType.APPLICATION_MODAL);
		GridBagLayout layout = new GridBagLayout();
		mainDialog.setLayout(layout);
		
		GridBagConstraints nameConstraint = builder
				.position(0, 0)
				.weight(1, 1)
				.margins(15, 0, 10, 0)
				.anchor(GridBagConstraintsAnchor.BASELINE)
				.build();
		
		GridBagConstraints okConstraint = builder
				.position(0, 1)
				.weight(10, 1)
				.anchor(GridBagConstraintsAnchor.BASELINE)
				.build();
		
		
		GridBagConstraints scoreConstraint = builder
				.position(1, 0)
				.weight(1, 1)
				.margins(15, 0, 10, 0)
				.anchor(GridBagConstraintsAnchor.BASELINE)
				.build();
		
		mainDialog.add(nameTextField, nameConstraint);
		mainDialog.add(scoreLabel, scoreConstraint);
		mainDialog.add(okBtn, okConstraint);
		mainDialog.pack();
		mainDialog.setResizable(false);
		mainDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		mainDialog.setLocation(ComponentLocation.getCenteredLocation(mainDialog));
		mainDialog.setTitle(LocaleManager.getString(HighScoresStrings.ENTRY_VIEW_TITLE.getKey()));
	}

	private void buildScore() {
		scoreLabel = new JLabel(String.format("%d", highScore));
		scoreLabel.setPreferredSize(new Dimension(140, 16));
		scoreLabel.setVisible(true);
	}

	private void buildName() {
		nameTextField = new JTextField();
		nameTextField.setPreferredSize(new Dimension(140, 16));
		nameTextField.setVisible(true);
	}

	public HighScoreEntryView(int score) {
		builder = new GridBagConstraintsBuilder();
		propertyChange = new PropertyChangeSupport(this);
		highScore = score;
	}

	@Override
	public void build() {
		try {
			SwingUtilities.invokeAndWait(() -> buildComponents());
		} catch (InvocationTargetException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Shows this view
	 */
	public void show() {
		SwingUtilities.invokeLater(() -> { mainDialog.setVisible(true); });
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(nameTextField.getText().trim().equals("") || nameTextField.getText() == null) {
			JOptionPane.showMessageDialog(null, LocaleManager.getString(HighScoresStrings.WARNING_EMPTY_NAME.getKey()));
		} else {
			propertyChange.firePropertyChange("name", null, nameTextField.getText());
			mainDialog.dispose();
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
