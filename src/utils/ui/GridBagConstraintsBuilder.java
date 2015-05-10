package utils.ui;

import java.awt.GridBagConstraints;
import java.awt.Insets;

public class GridBagConstraintsBuilder {

	private GridBagConstraints constraint;

	/**
	 * @param constraint
	 */
	public GridBagConstraintsBuilder() {
		this.constraint = new GridBagConstraints();
	}
	
	
	
	public GridBagConstraintsBuilder position(int gridX, int gridY) {
		constraint.gridx = gridX;
		constraint.gridy = gridY;
		return this;
	}
	
	public GridBagConstraintsBuilder span(int spanCols, int spanRows) {
		constraint.gridwidth = spanCols;
		constraint.gridheight = spanRows;
		return this;
	}
	
	public GridBagConstraintsBuilder fill(GridBagConstraintsFill fill) {
		constraint.fill = fill.getValue();
		return this;
	}
	
	public GridBagConstraintsBuilder anchor(GridBagConstraintsAnchor anchor) {
		constraint.anchor = anchor.getValue();
		return this;
	}
	
	public GridBagConstraintsBuilder weight(double weightX, double weightY) {
		constraint.weightx = weightX;
		constraint.weighty = weightY;
		return this;
	}
	
	public GridBagConstraintsBuilder iPad(int ipadX, int ipadY) {
		constraint.ipadx = ipadX;
		constraint.ipady = ipadY;
		return this;
	}
	
	public GridBagConstraintsBuilder margins(int top, int right, int bottom, int left) {
		constraint.insets = new Insets(top, left, bottom, right);
		return this;
	}



	public GridBagConstraints build() {
		GridBagConstraints oldConstraint = constraint;
		constraint = new GridBagConstraints();
		return oldConstraint;
	}
}
