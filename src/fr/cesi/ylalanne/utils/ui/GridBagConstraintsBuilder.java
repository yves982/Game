package fr.cesi.ylalanne.utils.ui;

import java.awt.GridBagConstraints;
import java.awt.Insets;

/**
 * Builds {@link GridBagConstraints} for use with {@link GridBagLayout}.
 */
public class GridBagConstraintsBuilder {

	private GridBagConstraints constraint;

	/**
	 * Initialize an instance.
	 */
	public GridBagConstraintsBuilder() {
		this.constraint = new GridBagConstraints();
	}
	
	
	
	/**
	 * Defines the constraint's position within the components grid.
	 *
	 * @param gridX the grid x
	 * @param gridY the grid y
	 * @return the grid bag constraints builder
	 */
	public GridBagConstraintsBuilder position(int gridX, int gridY) {
		constraint.gridx = gridX;
		constraint.gridy = gridY;
		return this;
	}
	
	/**
	 * Defines the constraint's occupied cells count within x and y axis.
	 *
	 * @param spanCols the span cols
	 * @param spanRows the span rows
	 * @return the grid bag constraints builder
	 */
	public GridBagConstraintsBuilder span(int spanCols, int spanRows) {
		constraint.gridwidth = spanCols;
		constraint.gridheight = spanRows;
		return this;
	}
	
	/**
	 * Defines how the associated component will use extra space.
	 *
	 * @param fill the fill
	 * @return the grid bag constraints builder
	 */
	public GridBagConstraintsBuilder fill(GridBagConstraintsFill fill) {
		constraint.fill = fill.getValue();
		return this;
	}
	
	/**
	 * Defines the constraint's anchor.
	 *
	 * @param anchor the anchor
	 * @return the grid bag constraints builder
	 */
	public GridBagConstraintsBuilder anchor(GridBagConstraintsAnchor anchor) {
		constraint.anchor = anchor.getValue();
		return this;
	}
	
	/**
	 * Defines the constraint's weight for a component to handle extra space.
	 *
	 * @param weightX the weight x
	 * @param weightY the weight y
	 * @return the grid bag constraints builder
	 */
	public GridBagConstraintsBuilder weight(double weightX, double weightY) {
		constraint.weightx = weightX;
		constraint.weighty = weightY;
		return this;
	}
	
	/**
	 * Defines internal empty space of the component (padding).
	 *
	 * @param ipadX the ipad x
	 * @param ipadY the ipad y
	 * @return the grid bag constraints builder
	 */
	public GridBagConstraintsBuilder iPad(int ipadX, int ipadY) {
		constraint.ipadx = ipadX;
		constraint.ipady = ipadY;
		return this;
	}
	
	/**
	 * Defines margins (external space around a component).
	 *
	 * @param top the top
	 * @param right the right
	 * @param bottom the bottom
	 * @param left the left
	 * @return the grid bag constraints builder
	 */
	public GridBagConstraintsBuilder margins(int top, int right, int bottom, int left) {
		constraint.insets = new Insets(top, left, bottom, right);
		return this;
	}



	/**
	 * Builds a {@link GridBagContraints}.
	 *
	 * @return the grid bag constraints
	 */
	public GridBagConstraints build() {
		GridBagConstraints oldConstraint = constraint;
		constraint = new GridBagConstraints();
		return oldConstraint;
	}
}
