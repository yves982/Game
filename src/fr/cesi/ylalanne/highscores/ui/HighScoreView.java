package fr.cesi.ylalanne.highscores.ui;

import java.awt.Dialog.ModalityType;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import fr.cesi.ylalanne.contracts.ui.IView;
import fr.cesi.ylalanne.highscores.model.HighScore;
import fr.cesi.ylalanne.highscores.model.HighScoresModel;
import fr.cesi.ylalanne.highscores.model.HighScoresStrings;
import fr.cesi.ylalanne.lang.LocaleManager;
import fr.cesi.ylalanne.utils.ui.ComponentLocation;
import fr.cesi.ylalanne.utils.ui.GridBagConstraintsAnchor;
import fr.cesi.ylalanne.utils.ui.GridBagConstraintsBuilder;
import fr.cesi.ylalanne.utils.ui.GridBagConstraintsFill;

public class HighScoreView implements IView, ActionListener {
	private JDialog highScoreDialog;
	private JPanel highScorePanel;
	private JTable highscoreTable;
	private JButton okButton;
	private GridBagConstraintsBuilder builder;
	private JScrollPane highScoreScrollPane;
	
	
	public HighScoreView() {
		builder = new GridBagConstraintsBuilder();
	}


	private void buildComponents() {
		buildTable();
		buildButton();
		buildPanel();
		buildDialog();
	}


	private void buildButton() {
		okButton = new JButton(LocaleManager.getString(HighScoresStrings.OK.getKey()));
		okButton.addActionListener(this);
		okButton.setVisible(true);
	}


	private void buildDialog() {
		highScoreDialog = new JDialog(null, ModalityType.APPLICATION_MODAL);
		highScoreDialog.add(highScorePanel);
		highScoreDialog.setResizable(false);
		highScoreDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		highScoreDialog.pack();
		highScoreDialog.setLocation(ComponentLocation.getCenteredLocation(highScoreDialog));
		highScoreDialog.setTitle(LocaleManager.getString(HighScoresStrings.VIEW_TITLE.getKey()));
	}


	private void buildPanel() {
		highScorePanel= new JPanel();
		highScoreScrollPane = new JScrollPane(highscoreTable);
		GridBagLayout layout = new GridBagLayout();
		highScorePanel.setLayout(layout);
		GridBagConstraints tableConstraint = builder
				.position(0, 0)
				.weight(1, 1)
				.fill(GridBagConstraintsFill.BOTH)
				.build();
		
		GridBagConstraints buttonConstraint = builder
				.position(0, 1)
				.weight(2, 1)
				.anchor(GridBagConstraintsAnchor.BELOW_BASELINE)
				.margins(4, 0, 4, 0)
				.build();
		
		highScoreScrollPane = new JScrollPane(highscoreTable);
		highScorePanel.add(highScoreScrollPane, tableConstraint);
		highScorePanel.add(okButton, buttonConstraint);
		highScorePanel.setVisible(true);
		
	}

	private void buildTable() {
		
		highscoreTable = new JTable();
	}
	
	/**
	 * Updates this view with actual data
	 * @param model
	 */
	public void update(HighScoresModel model) {
		List<HighScore> scores = model.getHighscores();
		String[] columns = new String[] {
				LocaleManager.getString(HighScoresStrings.NAME.getKey()),
				LocaleManager.getString(HighScoresStrings.SCORE.getKey())
		};
		highscoreTable.setModel(new DefaultTableModel() {
			private static final long serialVersionUID = 7038601066405152752L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
			
			@Override
			public String getColumnName(int column) {
				return columns[column];
			}
		});
		DefaultTableModel tableModel = (DefaultTableModel)highscoreTable.getModel();
		tableModel.setColumnCount(2);
		tableModel.setColumnIdentifiers(columns);
		
		for(HighScore score : scores) {
			tableModel.addRow(new Object[] { score.getName(), score.getScore() });
		}
		
		
	}

	public void show() {
		SwingUtilities.invokeLater( () -> {
			highScoreDialog.setVisible(true);
		});
	}
	
	
	@Override
	public void build() {
		try {
			SwingUtilities.invokeAndWait( () -> buildComponents() );
		} catch (InvocationTargetException | InterruptedException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		highScoreDialog.dispose();
	}
}
